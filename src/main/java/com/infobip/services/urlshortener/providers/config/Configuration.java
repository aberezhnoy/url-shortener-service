package com.infobip.services.urlshortener.providers.config;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by andrew
 */
public class Configuration implements Map<String, Object> {
    private Map source;

    public Configuration(Map source) {
        this.source = source;
    }

    @Override
    public int size() {
        return source.size();
    }

    @Override
    public boolean isEmpty() {
        return source.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return source.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return source.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return source.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return source.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return source.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        source.putAll(m);
    }

    @Override
    public void clear() {
        source.clear();
    }

    @Override
    public Set<String> keySet() {
        return source.keySet();
    }

    @Override
    public Collection<Object> values() {
        return source.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return source.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return source.equals(o);
    }

    @Override
    public int hashCode() {
        return source.hashCode();
    }

    public Boolean getBoolean(String key) {
        Object value = source.get(key);

        if (value == null) {
            return null;
        }

        if (value instanceof String) {
            return Boolean.valueOf((String) value);
        } else if (value instanceof Boolean) {
            return (Boolean) value;
        } else {
            return Boolean.FALSE;
        }
    }

    public Integer getInteger(String key) {
        Object value = source.get(key);

        if (value == null) {
            return null;
        }

        if (value instanceof String) {
            return Integer.valueOf((String) value);
        } else if (value instanceof Boolean) {
            return (Integer) value;
        } else {
            return 0;
        }
    }

    public String getString(String key) {
        Object value = source.get(key);

        if (value == null) {
            return null;
        }

        return value.toString();
    }
}
