package com.rpc.transport.netty.client;

import com.rpc.serializer.JsonSerializer;
import com.rpc.transport.RpcClient;
import com.rpc.transport.netty.codec.CommonDecoder;
import com.rpc.transport.netty.codec.CommonEncoder;
import entity.RpcRequest;
import entity.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class NettyClient implements RpcClient {
    private final String host;
    private final Integer port;
    private static final Bootstrap bootstrap;

    public NettyClient(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    static {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel)  {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(
                                new CommonEncoder(new JsonSerializer()),
                                new CommonDecoder(),
                                new NettyClientHandler()
                        );

                    }
                });
    }

    @Override
    public Object sendRequest(RpcRequest request) {
        try {
            ChannelFuture future = bootstrap.connect(host, port);
            log.info("客户端连接到服务器 {}:{}", host, port);
            Channel channel = future.channel();
            if (channel != null) {
                channel.writeAndFlush(request).addListener(future1 -> {
                    if (future1.isSuccess()) {
                        log.info(String.format("客户端发送消息: %s", request.toString()));
                    } else {
                        log.error("发送消息时有错误发生: ", future1.cause());
                    }
                });
                channel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
                RpcResponse rpcResponse = channel.attr(key).get();
                return rpcResponse.getData();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
