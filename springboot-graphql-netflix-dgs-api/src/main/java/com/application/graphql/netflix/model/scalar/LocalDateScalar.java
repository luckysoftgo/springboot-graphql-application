package com.application.graphql.netflix.model.scalar;

import com.netflix.graphql.dgs.DgsScalar;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@DgsScalar(name = "LocalDate")
public class LocalDateScalar implements Coercing<LocalDate, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String serialize(Object input) throws CoercingSerializeException {
        if (input instanceof LocalDate date) {
            return date.format(FORMATTER);
        }
        throw new CoercingSerializeException("转换日期失败");
    }

    @Override
    public LocalDate parseValue(Object input) throws CoercingParseValueException {
        try {
            return LocalDate.parse(input.toString(), FORMATTER);
        } catch (Exception e) {
            throw new CoercingParseValueException("日期格式必须 yyyy-MM-dd");
        }
    }

    @Override
    public LocalDate parseLiteral(Object input) throws CoercingParseLiteralException {
        if (input instanceof StringValue str) {
            return parseValue(str.getValue());
        }
        throw new CoercingParseLiteralException("日期字面量格式错误");
    }
}
