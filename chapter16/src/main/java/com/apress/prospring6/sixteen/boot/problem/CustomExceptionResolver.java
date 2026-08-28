package com.apress.prospring6.sixteen.boot.problem;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.jspecify.annotations.Nullable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected @Nullable GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof DataIntegrityViolationException violationException) {
            return GraphqlErrorBuilder.newError()
                    .errorType(ErrorType.BAD_REQUEST)
                    .message("Cannot create duplicate entry: " + ex.getCause().getCause()
                            .getMessage())
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation())
                    .build();
        }

        return super.resolveToSingleError(ex, env);
    }

    @Override
    protected @Nullable List<GraphQLError> resolveToMultipleErrors(Throwable ex, DataFetchingEnvironment env) {
        return super.resolveToMultipleErrors(ex, env);
    }
}
