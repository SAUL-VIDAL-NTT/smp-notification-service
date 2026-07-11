package com.example.smp.notification.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.example.smp.notification.contants.AppConstants;
import com.example.smp.dto.response.AppResponse;
import com.example.smp.dto.response.AppStatus;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseUtil {

    public static Mono<ServerResponse> successful() {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(successResponse());
    }

    public static Mono<ServerResponse> successful(Object body) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(successResponse(body));
    }

    public static Mono<ServerResponse> created(Object body) {
        return ServerResponse.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(successResponse(body));
    }

    public static Mono<ServerResponse> error() {
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(errorResponse(
                        AppConstants.Errors.GENERIC_ERROR_CODE,
                        AppConstants.Errors.GENERIC_ERROR_MESSAGE));
    }

    public static AppResponse successResponse() {
        return AppResponse.builder()
                .status(AppStatus.builder()
                        .code(AppConstants.General.SUCCESS_CODE)
                        .message(AppConstants.General.SUCCESS_MESSAGE)
                        .build())
                .build();
    }

    public static AppResponse successResponse(Object body) {
        return AppResponse.builder()
                .status(AppStatus.builder()
                        .code(AppConstants.General.SUCCESS_CODE)
                        .message(AppConstants.General.SUCCESS_MESSAGE)
                        .build())
                .data(body)
                .build();
    }

    public static AppResponse errorResponse(String code, String message) {
        return AppResponse.builder()
                .status(AppStatus.builder()
                        .code(code)
                        .message(message)
                        .build())
                .build();
    }
}
