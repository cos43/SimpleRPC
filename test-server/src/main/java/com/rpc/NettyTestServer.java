package com.rpc;

import com.rpc.annotation.ServiceScan;
import com.rpc.transport.netty.server.NettyServer;
@ServiceScan
public class NettyTestServer {
    public static void main(String[] args) {
        NettyServer server = new NettyServer("127.0.0.1", 9999);
        server.start();
    }
}
