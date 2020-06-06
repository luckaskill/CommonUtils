package com.las.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;

public class EntityConverter {
    public static <F, T> T convert(F fromObject, Class<T> toClass) throws JsonProcessingException {
        String json = JacksonUtil.serialize(fromObject);
        T deserialized = JacksonUtil.deserialize(json, toClass);
        return deserialized;
    }

}
