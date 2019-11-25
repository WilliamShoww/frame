package com.xcf.util.json;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface JacksonTypeHandler {

    JavaType build(ObjectMapper mapper);
}
