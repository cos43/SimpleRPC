package com.rpc.hook;

import com.rpc.factory.ThreadPoolFactory;
import lombok.extern.slf4j.Slf4j;

import com.rpc.utils.NacosUtils;

import java.util.concurrent.ExecutorService;

@Slf4j
public class ShutdownHook {
    private static final ShutdownHook shutdownHook = new ShutdownHook();
    private final ExecutorService threadPool = ThreadPoolFactory.createDefaultThreadPool("shutdown-hook");
    public static ShutdownHook getShutdownHook() {
        return shutdownHook;
    }
    public void addClearAllHook() {
        log.info("关闭后将自动注销所有服务");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            NacosUtils.clearRegistry();
            threadPool.shutdown();
        }));
    }


}
