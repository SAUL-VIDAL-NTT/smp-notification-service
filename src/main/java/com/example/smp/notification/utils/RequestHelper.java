package com.example.smp.notification.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import com.example.smp.notification.contants.AppConstants;
import com.example.smp.types.AppHttpStatusCode;
import com.example.smp.exception.AppException;
import com.example.smp.models.dtos.response.OauthModel;
import reactor.core.publisher.Mono;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestHelper {

    private final Validator validator;
    private final ObjectMapper objectMapper;

    public ValidationBuilder<Object> of(ServerRequest request) {
        return new ValidationBuilder<>(this, request, null);
    }

    public <T> Mono<T> validateHeaders(@Nonnull ServerRequest request, Class<T> clazz) {
        T customHeaders = objectMapper.convertValue(getHeadersInLowercase(request), clazz);
        return performValidation(customHeaders, "headers");
    }

    public <T> Mono<T> validateBody(@Nonnull ServerRequest request, Class<T> clazz) {
        return request.bodyToMono(clazz)
                .flatMap(body -> performValidation(body, "body"));
    }

    private <T> Mono<T> performValidation(T object, String fieldName) {
        Errors errors = new BeanPropertyBindingResult(object, fieldName);
        validator.validate(object, errors);

        if (errors.hasErrors()) {
            return Mono.error(new AppException(
                    AppConstants.Errors.GENERIC_ERROR_CODE,
                    AppConstants.Errors.GENERIC_ERROR_MESSAGE,
                    AppHttpStatusCode.BAD_REQUEST));
        }

        return Mono.just(object);
    }

    private Map<String, String> getHeadersInLowercase(@Nonnull ServerRequest request) {
        return request.headers()
                .asHttpHeaders()
                .toSingleValueMap()
                .entrySet()
                .stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static HttpHeaders buildHeaders(String bearerToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(AppConstants.Headers.AUTHORIZATION, "Bearer " + bearerToken);
        return headers;
    }

    public static Mono<OauthModel> validOauth(ResponseEntity<OauthModel> response) {
        OauthModel oauthModel = response.getBody();
        if (oauthModel == null || oauthModel.getAccessToken() == null || oauthModel.getAccessToken().isBlank()) {
            return Mono.error(new AppException(
                    AppConstants.Errors.GENERIC_ERROR_CODE,
                    AppConstants.Errors.GENERIC_ERROR_MESSAGE,
                    AppHttpStatusCode.BAD_REQUEST));
        }
        return Mono.just(oauthModel);
    }
}
