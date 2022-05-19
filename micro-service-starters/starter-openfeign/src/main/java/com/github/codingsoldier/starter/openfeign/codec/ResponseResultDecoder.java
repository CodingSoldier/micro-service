package com.github.codingsoldier.starter.openfeign.codec;

import com.github.codingsoldier.common.enums.ResponseCodeEnum;
import com.github.codingsoldier.common.exception.ResultNotSuccessFeignException;
import com.github.codingsoldier.common.resp.Result;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Optional;

/**
 * 改造 feign.optionals.OptionalDecoder
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Slf4j
public class ResponseResultDecoder implements Decoder {
    final Decoder delegate;

    public ResponseResultDecoder(Decoder delegate) {
        Objects.requireNonNull(delegate, "Decoder must not be null. ");
        this.delegate = delegate;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        Object resp;
        if (!isOptional(type)) {
            resp = delegate.decode(response, type);
        } else if (response.status() == 404 || response.status() == 204) {
            resp = Optional.empty();
        } else {
            Type enclosedType = Util.resolveLastTypeParameter(type, Optional.class);
            resp = Optional.ofNullable(delegate.decode(response, enclosedType));
        }
        if (resp instanceof Result) {
            Result result = (Result) resp;
            if (!ResponseCodeEnum.SUCCESS.getCode().equals(result.getCode())) {
                log.error("feign调用下游服务，返回结果Result的code不是成功。result={}", result.toString());
                throw new ResultNotSuccessFeignException(result.getCode(), result.getMessage());
            }
        }
        return resp;
    }

    static boolean isOptional(Type type) {
        if (!(type instanceof ParameterizedType)) {
            return false;
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;
        return parameterizedType.getRawType().equals(Optional.class);
    }
}