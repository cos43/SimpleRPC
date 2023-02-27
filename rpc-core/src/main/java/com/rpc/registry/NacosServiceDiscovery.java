package com.rpc.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.rpc.loadbalance.LoadBalance;
import com.rpc.loadbalance.RandomLoadBalance;

import lombok.extern.slf4j.Slf4j;
import com.rpc.utils.NacosUtils;

import java.net.InetSocketAddress;
import java.util.List;

@Slf4j
public class NacosServiceDiscovery implements ServiceDiscovery {
    private LoadBalance loadBalancer = null;

    public NacosServiceDiscovery(LoadBalance loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    public InetSocketAddress lookupService(String serviceName) {
        try {
            List<Instance> instances = NacosUtils.getAllInstance(serviceName);
            Instance instance = loadBalancer.select(instances);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e) {
            log.error("获取服务时有错误发生:", e);
        }
        return null;
    }

}
