package com.apress.prospring6.twenty.boot.handler;

import com.apress.prospring6.twenty.boot.model.Singer;
import com.apress.prospring6.twenty.boot.problem.MissingValueException;
import com.apress.prospring6.twenty.boot.service.SingerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;


@Component
public class SingerHandler {

    private final SingerService singerService;

    public HandlerFunction<ServerResponse> list;
    public HandlerFunction<ServerResponse> deleteById;

    public SingerHandler(SingerService singerService) {
        this.singerService = singerService;

        list = serverRequest -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(singerService.findAll(), Singer.class);

        deleteById = serverRequest -> ServerResponse.noContent()
                .build(singerService.delete(Long.parseLong(serverRequest.pathVariable("id"))));
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        var id = Long.parseLong(serverRequest.pathVariable("id"));
        return singerService.findById(id)
                .flatMap(singer -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(singer))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        Mono<Singer> singerMono = serverRequest.bodyToMono(Singer.class);
        return singerMono
                .flatMap(singerService::save)
                .log()
                .flatMap(s -> ServerResponse.created(URI.create("/singer/" + s.getId()))
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(s));
    }

    public Mono<ServerResponse> updateById(ServerRequest serverRequest) {
        var id = Long.parseLong(serverRequest.pathVariable("id"));
        return singerService.findById(id)
                .flatMap(fromDb -> serverRequest.bodyToMono(Singer.class)
                        .flatMap(s -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(singerService.update(id, s), Singer.class)))
                .switchIfEmpty(ServerResponse.badRequest().bodyValue("Failure to update user!"));
    }

    public Mono<ServerResponse> searchSingers(ServerRequest serverRequest) {
        var name = serverRequest.queryParam("name").orElse(null);
        if (StringUtils.isBlank(name)) {
            return ServerResponse.badRequest().bodyValue("Missing request parameter 'name'");
        }

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON).body(singerService
                        .findByFirstName(name), Singer.class);
    }

    public Mono<ServerResponse> searchSinger(ServerRequest serverRequest) {
        var fn = serverRequest.queryParam("fn").orElse(null);
        var ln = serverRequest.queryParam("ln").orElse(null);

        if ((StringUtils.isBlank(fn) || StringUtils.isBlank(ln))) {
            return ServerResponse.badRequest().bodyValue("Missing request parameter, one of {fn, ln}");
        }

        return singerService.findByFirstNameAndLastName(fn, ln)
                .flatMap(singer -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(singer));
    }

    public Mono<ServerResponse> search(ServerRequest serverRequest) {
        var criteriaMono = serverRequest.bodyToMono(SingerService.CriteriaDto.class);
        return criteriaMono.log()
                .flatMap(this::validate)
                .flatMap(criteria -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(singerService.findByCriteriaDto(criteria), Singer.class));
    }

    private Mono<SingerService.CriteriaDto> validate(SingerService.CriteriaDto criteria) {
        var validator = new SingerService.CriteriaValidator();
        var errors = new BeanPropertyBindingResult(criteria, "criteria");
        validator.validate(criteria, errors);
        if (errors.hasErrors()) {
            throw MissingValueException.of(errors.getAllErrors());
        }

        return Mono.justOrEmpty(criteria);
    }

    public Mono<ServerResponse> searchView(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_HTML)
                .render("singers/search", new SingerService.CriteriaDto());
    }


}
