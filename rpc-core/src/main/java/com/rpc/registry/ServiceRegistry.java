package com.rpc.registry;

import com.alibaba.nacos.api.exception.NacosException;

import java.net.InetSocketAddress;

public interface ServiceRegistry {
    void register(String serviceName, InetSocketAddress inetSocketAddress);
}
