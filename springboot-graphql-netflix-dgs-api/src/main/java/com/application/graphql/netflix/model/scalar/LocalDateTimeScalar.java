package com.application.graphql.netflix.model.scalar;

import com.netflix.graphql.dgs.DgsScalar;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@DgsScalar(name = "LocalDateTime")
public class LocalDateTimeScalar implements Coercing<LocalDateTime, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String serialize(Object input) throws CoercingSerializeException {
        if (input instanceof LocalDateTime date) {
            return date.format(FORMATTER);
        }
        throw new CoercingSerializeException("转换日期失败");
    }

    @Override
    public LocalDateTime parseValue(Object input) throws CoercingParseValueException {
        try {
            return LocalDateTime.parse(input.toString(), FORMATTER);
        } catch (Exception e) {
            throw new CoercingParseValueException("日期格式必须 yyyy-MM-dd");
        }
    }

    @Override
    public LocalDateTime parseLiteral(Object input) throws CoercingParseLiteralException {
        if (input instanceof StringValue str) {
            return parseValue(str.getValue());
        }
        throw new CoercingParseLiteralException("日期字面量格式错误");
    }
}
