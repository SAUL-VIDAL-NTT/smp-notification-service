package com.example.smp.notification.utils;

import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.Optional;

public record ValidationBuilder<H>(
        RequestHelper requestHelper, ServerRequest request, Mono<H> headersMono
) {

    public <T> ValidationBuilder<T> validateHeaders(Class<T> headersClass) {
        Mono<T> mono = requestHelper.validateHeaders(request, headersClass);
        return new ValidationBuilder<>(requestHelper, request, mono);
    }

    public <B> Mono<Request<H, B>> validateBody(Class<B> bodyClass) {
        Mono<Optional<B>> bodyOpt = requestHelper.validateBody(request, bodyClass)
                .map(Optional::of)
                .switchIfEmpty(Mono.just(Optional.empty()));
        if (headersMono == null) {
            return bodyOpt.map(opt -> new Request<>(null, opt.orElse(null)));
        }
        return Mono.zip(headersMono, bodyOpt)
                .map(tuple -> new Request<>(tuple.getT1(), tuple.getT2().orElse(null)));
    }

    public Mono<Request<H, Void>> execute() {
        if (headersMono == null) {
            return Mono.just(new Request<>(null, null));
        }
        return headersMono.map(headers -> new Request<>(headers, null));
    }

    public record Request<H, B>(H headers, B body) {
    }
}
