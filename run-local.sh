#!/bin/bash
# run-local.sh — boots smp-notification-service locally using profile=test + mocks

set -e

source .env.local

echo "==> Starting smp-notification-service locally (profile=$PROFILE, mocks=$MOCKS)..."

PROFILE=$PROFILE \
MOCKS=$MOCKS \
LOG_LEVEL=DEBUG \
mvn spring-boot:run \
  -Dspring-boot.run.jvmArguments="-DPROFILE=$PROFILE -DMOCKS=$MOCKS -DLOG_LEVEL=DEBUG" \
  --settings settings.xml \
  -Durl_artifactory=$url_artifactory \
  -Duser_artifactory=$user_artifactory \
  -Dpassword_artifactory=$password_artifactory
