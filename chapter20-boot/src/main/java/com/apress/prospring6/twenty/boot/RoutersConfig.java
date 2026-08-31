package com.apress.prospring6.twenty.boot;

import com.apress.prospring6.twenty.boot.handler.HomeHandler;
import com.apress.prospring6.twenty.boot.handler.SingerHandler;
import com.apress.prospring6.twenty.boot.problem.MissingValueException;
import com.apress.prospring6.twenty.boot.problem.SaveException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;

import static org.springframework.web.reactive.function.server.RequestPredicates.queryParam;
import static org.springframework.web.reactive.function.server.RouterFunctions.resources;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Slf4j
@Configuration
public class RoutersConfig {

    final static Logger LOGGER = LoggerFactory.getLogger(RoutersConfig.class);

    @Bean
    public RouterFunction<ServerResponse> singerRoutes(HomeHandler homeHandler, SingerHandler singerHandler) {
        return route()
                .GET("/", homeHandler::view)
                .GET("/home", homeHandler::view)
                .GET("/handler/singer", queryParam("name", t -> true),
                        singerHandler::searchSingers)
                .GET("/handler/singer", RequestPredicates.all()
                        .and(queryParam("fn", t -> true))
                        .and(queryParam("ln", t -> true)),
                        singerHandler::searchSinger)
                .GET("/handlers/singer", singerHandler.list)
                .POST("/handler/singer", singerHandler::create)
                .GET("/handler/singer/{id}", singerHandler::findById)
                .PUT("/handler/singer/{id}", singerHandler::updateById)
                .DELETE("/handler/singer/{id}", singerHandler.deleteById)
                .GET("/singers/search", singerHandler::searchView)
                .POST("/singers/go", singerHandler::search)
                .filter((request, next) -> {
                    LOGGER.info("Before handler invocation: {}", request.path());
                    return next.handle(request);
                })
                .build();
    }

    @Bean
    @Order(-2)
    public WebExceptionHandler exceptionHandler() {
        return (ServerWebExchange exchange, Throwable ex) -> {
            if (ex instanceof SaveException se) {
                log.debug("RouterConfig:: handling exception :: ", se);
                exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
                return exchange.getResponse().setComplete();
            } else if (ex instanceof  IllegalArgumentException iae) {
                log.debug("RouterConfig:: handling exception :: ", iae);
                exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
                return exchange.getResponse().setComplete();
            } else if (ex instanceof MissingValueException mve) {
                exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
                exchange.getResponse().getHeaders().add("Content-Type", "application/json");
                final String message;
                try {
                    message = new JsonMapper().writeValueAsString(mve.getFieldNames());
                    var buffer = exchange.getResponse().bufferFactory().wrap(message.getBytes());
                    return exchange.getResponse().writeWith(Flux.just(buffer));
                } catch (JacksonException e) {

                }
            }

            return Mono.error(ex);
        };
    }

    public RouterFunction<ServerResponse> staticRouter() {
        return resources("/images/**", new ClassPathResource("static/images/"))
                .and(resources("/styles/**", new ClassPathResource("static/styles/")))
                .and(resources("/js/**", new ClassPathResource("static/js/")));
    }
}
