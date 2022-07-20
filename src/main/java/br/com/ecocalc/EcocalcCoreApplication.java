package br.com.ecocalc;

import br.com.ecocalc.domains.exception.GraphQLErrorAdapter;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.scalars.ExtendedScalars;
import graphql.kickstart.execution.error.GraphQLErrorHandler;
import graphql.language.StringValue;
import graphql.schema.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.Filter;


@SpringBootApplication
public class EcocalcCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcocalcCoreApplication.class, args);
    }

    /**
     * Register the {@link OpenEntityManagerInViewFilter} so that the
     * GraphQL-Servlet can handle lazy loads during execution.
     *
     * @return
     */
    @Bean
    public Filter OpenFilter() {
        return new OpenEntityManagerInViewFilter();
    }

    // FIXME: Substituir deprecated GraphQLBigDecimal
    @Bean
    public GraphQLScalarType bigDecimal() {
        return ExtendedScalars.GraphQLBigDecimal;
    }

    /**
     * Date scalar
     * 
     * @return
     */
    @Bean
    public GraphQLScalarType dateScalar() {
        return GraphQLScalarType.newScalar().name("DateTime").description("Java 8 OffsetDateTime as scalar.")
                .coercing(new Coercing<OffsetDateTime, String>() {
                    @Override
                    public String serialize(final Object dataFetcherResult) {
                        if (dataFetcherResult instanceof OffsetDateTime) {
                            String offsetDateTime = ((OffsetDateTime) dataFetcherResult)
                                    .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                            return offsetDateTime;
                        } else {
                            throw new CoercingSerializeException("Expected a OffsetDateTime object.");
                        }
                    }

                    @Override
                    public OffsetDateTime parseValue(final Object input) {
                        try {
                            if (input instanceof String) {
                                return OffsetDateTime.parse((String) input);
                            } else {
                                throw new CoercingParseValueException("Expected a String");
                            }
                        } catch (DateTimeParseException e) {
                            throw new CoercingParseValueException(String.format("Not a valid date: '%s'.", input), e);
                        }
                    }

                    @Override
                    public OffsetDateTime parseLiteral(final Object input) {
                        if (input instanceof StringValue) {
                            try {
                                return OffsetDateTime.parse(((StringValue) input).getValue());
                            } catch (DateTimeParseException e) {
                                throw new CoercingParseLiteralException(e);
                            }
                        } else {
                            throw new CoercingParseLiteralException("Expected a StringValue.");
                        }
                    }
                }).build();
    }

    @Bean
    public GraphQLScalarType hourScalar() {
        return GraphQLScalarType.newScalar().name("Time").description("Java 8 OffsetTime as scalar.")
                .coercing(new Coercing<OffsetTime, String>() {
                    @Override
                    public String serialize(final Object dataFetcherResult) {
                        if (dataFetcherResult instanceof OffsetTime) {
                            String offsetTime = ((OffsetTime) dataFetcherResult)
                                    .format(DateTimeFormatter.ISO_OFFSET_TIME);
                            return offsetTime;
                        } else {
                            throw new CoercingSerializeException("Expected a OffsetTime object.");
                        }
                    }

                    @Override
                    public OffsetTime parseValue(final Object input) {
                        try {
                            if (input instanceof String) {
                                OffsetTime parsedTime = OffsetTime.parse((String) input, DateTimeFormatter.ISO_OFFSET_TIME);
                                return parsedTime;
                            } else {
                                throw new CoercingParseValueException("Expected a String");
                            }
                        } catch (DateTimeParseException e) {
                            throw new CoercingParseValueException(String.format("Not a valid time: '%s'.", input), e);
                        }
                    }

                    @Override
                    public OffsetTime parseLiteral(final Object input) {
                        if (input instanceof StringValue) {
                            try {
                                return OffsetTime.parse(((StringValue) input).getValue(), DateTimeFormatter.ISO_OFFSET_TIME);
                            } catch (DateTimeParseException e) {
                                throw new CoercingParseLiteralException(e);
                            }
                        } else {
                            throw new CoercingParseLiteralException("Expected a StringValue.");
                        }
                    }
                }).build();
    }

    @Bean
    public GraphQLErrorHandler errorHandler() {
        return new GraphQLErrorHandler() {
            @Override
            public List<GraphQLError> processErrors(List<GraphQLError> errors) {
                List<GraphQLError> clientErrors = errors.stream().filter(this::isClientError)
                        .collect(Collectors.toList());

                List<GraphQLError> serverErrors = errors.stream().filter(e -> !isClientError(e))
                        .map(GraphQLErrorAdapter::new).collect(Collectors.toList());

                List<GraphQLError> e = new ArrayList<>();
                e.addAll(clientErrors);
                e.addAll(serverErrors);
                return e;
            }

            protected boolean isClientError(GraphQLError error) {
                return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
            }
        };
    }
}
