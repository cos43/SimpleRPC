package com.rpc;

import com.rpc.serializer.Serializer;
import com.rpc.transport.RpcClient;
import com.rpc.transport.RpcClientProxy;
import com.rpc.transport.netty.client.NettyClient;

public class NettyTestClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient(Serializer.KRYO_SERIALIZER);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
