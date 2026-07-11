# 1st stage. Build maven app
ARG acr_registry
FROM ${acr_registry}/external/maven:3.9.9-amazoncorretto-21 AS MAVEN_BUILD

############################# jFrog Artifactory #############################
RUN mkdir -p /root/.m2 \
    && mkdir /root/.m2/repository

COPY settings.xml /root/.m2

ARG url_artifactory
ARG user_artifactory
ARG password_artifactory
############################# jFrog Artifactory #############################

WORKDIR /build/

COPY pom.xml .

# add all source code and start compiling
COPY ./src ./src

RUN mvn clean package

# copy jar from build stage
RUN cp /build/target/smp-notification-service-1.0.0.jar app.jar

# extract jar as layers for optimized Docker caching
RUN java -Djarmode=layertools -jar app.jar extract

# 2nd stage. Copy all layers to root directory
FROM ${acr_registry}/external/amazoncorretto:21-alpine-jdk

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app/

COPY --chown=appuser:appgroup --from=maven_build /build/dependencies/ ./
COPY --chown=appuser:appgroup --from=maven_build /build/spring-boot-loader/ ./
COPY --chown=appuser:appgroup --from=maven_build /build/snapshot-dependencies/ ./

RUN true

COPY --chown=appuser:appgroup --from=maven_build /build/application/ ./

# install curl and unzip for health checks, add permissions
RUN apk update && \
 apk upgrade && \
 apk --no-cache add curl unzip && \
 rm -rf /var/hazelCastCache/apk/* && \
 chown -R appuser:appgroup /app

# change user id for security
USER appuser

# Start app with JVM options from environment
ENTRYPOINT ["sh","-c","exec java $JAVA_OPTS org.springframework.boot.loader.launch.JarLauncher"]
