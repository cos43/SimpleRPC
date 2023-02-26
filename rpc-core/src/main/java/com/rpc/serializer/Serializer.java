package com.rpc.serializer;

public interface Serializer {
    byte[] serialize(Object obj);

    Object deserialize(byte[] bytes,Class<?> clazz);

    int getCode();

    static Serializer getByCode(int code){
        switch (code) {
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }
}
