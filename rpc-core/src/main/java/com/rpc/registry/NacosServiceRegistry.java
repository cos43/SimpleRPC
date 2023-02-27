package com.rpc.registry;

import com.alibaba.nacos.api.exception.NacosException;

import com.rpc.constants.RpcError;
import com.rpc.exception.RpcException;
import lombok.extern.slf4j.Slf4j;
import com.rpc.utils.NacosUtils;

import java.net.InetSocketAddress;

@Slf4j
public class NacosServiceRegistry implements ServiceRegistry {
    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            NacosUtils.registerService(serviceName, inetSocketAddress);
        } catch (NacosException e) {
            log.error("注册服务时有错误发生:", e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }

}
