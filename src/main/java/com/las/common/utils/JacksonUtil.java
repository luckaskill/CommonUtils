package com.las.common.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class JacksonUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> String serialize(T entity) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(entity);
    }

    public static <T> T deserialize(String json, Class<T> clazz) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(json, clazz);
    }

    @SneakyThrows
    public static boolean isJson(String toValidate) {
        JsonParser parser = OBJECT_MAPPER.getFactory().createParser(toValidate);
        boolean hasToken = parser.nextToken() != null;
        return hasToken;
    }
}
