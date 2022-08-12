package com.github.codingsoldier.common.util.objectmapper.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.codingsoldier.common.util.DateUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * DateTime序列化
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class DateAllSerializer extends JsonSerializer<Object> {

    @SuppressWarnings("squid:S125")
    // @Override
    // public void serializeWithType(Object value, JsonGenerator g, SerializerProvider provider,
    //                               TypeSerializer typeSer) throws IOException {
    //     WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(value, JsonToken.VALUE_STRING));
    //     serialize(value, g, provider);
    //     typeSer.writeTypeSuffix(g, typeIdDef);
    // }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (Objects.isNull(value)) {
            return;
        }
        if (value instanceof Date) {
            Date obj = (Date) value;
            gen.writeNumber(obj.getTime());
        } else if (value instanceof LocalDate) {
            LocalDate obj = (LocalDate) value;
            long timestamp = DateUtil.toTimestamp(obj);
            gen.writeNumber(timestamp);
        } else if (value instanceof LocalDateTime) {
            LocalDateTime obj = (LocalDateTime) value;
            Long timestamp = DateUtil.toTimestamp(obj);
            gen.writeNumber(timestamp);
        } else if (value instanceof OffsetDateTime) {
            OffsetDateTime obj = (OffsetDateTime) value;
            Long timestamp = DateUtil.toTimestamp(obj);
            gen.writeNumber(timestamp);
        }
    }

}
