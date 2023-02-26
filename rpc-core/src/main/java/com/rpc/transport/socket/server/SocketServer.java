package com.rpc.transport.socket.server;

import com.rpc.registry.ServiceRegistry;
import com.rpc.transport.RpcServer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

@Slf4j
public class SocketServer implements RpcServer {
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 50;
    private static final int KEEP_ALIVE_TIME = 60;
    private static final int BLOCKING_QUEUE_CAPACITY = 100;
    private final ExecutorService threadPool;
    private final ServiceRegistry serviceRegistry;

    public SocketServer(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, workingQueue, threadFactory);
    }

    public void start(Integer port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("服务器正在启动...");
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                log.info("客户端连接！Ip为：" + socket.getInetAddress());
                threadPool.execute(new SocketServerHandler(socket, serviceRegistry));
            }
        } catch (IOException e) {
            log.error("连接时有错误发生：", e);
        }
    }


}
