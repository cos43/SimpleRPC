package com.rpc.transport;

import entity.RpcRequest;

public interface RpcClient {
    Object sendRequest(RpcRequest request);
}
