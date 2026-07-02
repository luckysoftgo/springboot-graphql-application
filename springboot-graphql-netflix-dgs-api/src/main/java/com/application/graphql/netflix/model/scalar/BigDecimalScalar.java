package com.application.graphql.netflix.model.scalar;

import com.netflix.graphql.dgs.DgsScalar;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

import java.math.BigDecimal;

@DgsScalar(name = "BigDecimal")
public class BigDecimalScalar implements Coercing<BigDecimal, String> {

    @Override
    public String serialize(Object input) throws CoercingSerializeException {
        if (input instanceof BigDecimal num) {
            return num.toString();
        }
        throw new CoercingSerializeException("BigDecimal转换失败");
    }

    @Override
    public BigDecimal parseValue(Object input) throws CoercingParseValueException {
        try {
            return new BigDecimal(input.toString());
        } catch (Exception e) {
            throw new CoercingParseValueException("数值格式错误");
        }
    }

    @Override
    public BigDecimal parseLiteral(Object input) throws CoercingParseLiteralException {
        if (input instanceof StringValue str) {
            return parseValue(str.getValue());
        }
        throw new CoercingParseLiteralException("数值字面量错误");
    }
}
