package com.xcf.util.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.ArrayType;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public TypeReference<?> MAP_TYPE = new MapTypeReference<String, Object>();

    public static final TypeReference<?> LIST_TYPE = new ListTypeReference<>();

    private static class MapTypeReference<K, V> extends TypeReference<Map<K, V>> {

    }

    private static class ListTypeReference<T> extends TypeReference<List<T>> {

    }

    static {
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T toObject(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T toObject(String json, JacksonTypeHandler typeHandler) {
        JavaType javaType = typeHandler.build(mapper);
        try {
            return mapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T toCollect(String json, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(json, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> toList(String json) {
        try {
            return mapper.readValue(json, new ListTypeReference<T>());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> toList(String json, Class<T> clazz) {
        try {
            JavaType type = mapper.getTypeFactory().constructParametricType(List.class, clazz);
            return mapper.readValue(json, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <K, V> Map<K, V> toMap(String json) {
        try {
            return mapper.readValue(json, new MapTypeReference<K, V>());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 校验json是否合法
     *
     * @param json 字符串
     * @return true 合法 false 不合法
     */
    public static boolean isValid(String json) {
        try {
            mapper.readTree(json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
