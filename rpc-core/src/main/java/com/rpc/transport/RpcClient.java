package com.rpc.transport;

import com.rpc.entity.RpcRequest;
import com.rpc.serializer.Serializer;

public interface RpcClient {
    int DEFAULT_SERIALIZER = Serializer.KRYO_SERIALIZER;
    Object sendRequest(RpcRequest request);
}
