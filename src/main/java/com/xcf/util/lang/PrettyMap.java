package com.xcf.util.lang;

import java.util.LinkedHashMap;

public class PrettyMap extends LinkedHashMap<String, Object> {

    public PrettyMap put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public PrettyMap putBoolean(String key, Boolean flag) {
        super.put(key, flag);
        return this;
    }

    public PrettyMap putInteget(String key, Integer value) {
        super.put(key, value);
        return this;
    }

    public PrettyMap putLong(String key, Long value) {
        super.put(key, value);
        return this;
    }

    public PrettyMap putShort(String key, Short value) {
        super.put(key, value);
        return this;
    }

    public PrettyMap getDouble(String key, Double value) {
        super.put(key, value);
        return this;
    }

    public PrettyMap putString(String key, String value) {
        super.put(key, value);
        return this;
    }

    @Override
    public Object get(Object key) {
        return super.get(key);
    }

    public Boolean getBoolean(String key) {
        super.get(key);
        return (Boolean) super.get(key);
    }

    public Number getNumber(String key) {
        return (Number) super.get(key);
    }

    public Integer getInteget(String key) {
        Number number = this.getNumber(key);
        return null == number ? null : number.intValue();
    }

    public Long getLong(String key) {
        Number number = this.getNumber(key);
        return null == number ? null : number.longValue();
    }

    public Short getShort(String key) {
        Number number = this.getNumber(key);
        return null == number ? null : number.shortValue();
    }

    public Double getDouble(String key) {
        Number number = this.getNumber(key);
        return null == number ? null : number.doubleValue();
    }

    public String getString(String key) {
        return (String) super.get(key);
    }
}
