package com.example.smp.notification.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.example.smp.notification.contants.AppConstants;
import com.example.smp.types.AppHttpStatusCode;
import com.example.smp.dto.authorization.Authorization;
import com.example.smp.dto.response.AppStatus;
import com.example.smp.exception.AppException;
import com.example.smp.utils.JwtAuth;
import reactor.core.publisher.Mono;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppUtils {

    public static Mono<? extends ServerResponse> returnError(Throwable error) {
        log.error("Error: ", error);
        if (error instanceof AppException) {
            return Mono.error(error);
        }
        return Mono.error(new AppException(
                AppConstants.Errors.GENERIC_ERROR_CODE,
                AppConstants.Errors.GENERIC_ERROR_MESSAGE,
                AppHttpStatusCode.INTERNAL_SERVER_ERROR));
    }

    public static Mono<? extends Throwable> handleFunctionalError(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(AppStatus.class)
                .flatMap(body -> Mono.error(new AppException(
                        body.getCode(), body.getMessage(), AppHttpStatusCode.FUNCTIONAL_ERROR)));
    }

    public static Authorization extractAuthorization(String authorizationHeader,
                                                     ObjectMapper objectMapper,
                                                     JwtAuth jwtAuth) throws AppException {
        String[] parts = authorizationHeader.split(" ");
        try {
            JsonNode jsonNode = jwtAuth.decodeJwt(parts[1]);
            return objectMapper.treeToValue(jsonNode, Authorization.class);
        } catch (Exception e) {
            throw new AppException(
                    AppConstants.Errors.GENERIC_ERROR_CODE,
                    AppConstants.Errors.GENERIC_ERROR_MESSAGE,
                    AppHttpStatusCode.INTERNAL_SERVER_ERROR);
        }
    }
}
