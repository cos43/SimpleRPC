package com.rpc.transport;

import com.rpc.serializer.Serializer;

public interface RpcServer {
    int DEFAULT_SERIALIZER = Serializer.KRYO_SERIALIZER;
    void start();
    public <T> void publishService(T service, String serviceName);
}
