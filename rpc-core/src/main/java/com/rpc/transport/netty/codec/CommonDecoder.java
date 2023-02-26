package com.rpc.transport.netty.codec;

import com.rpc.serializer.Serializer;
import constants.PackageType;
import constants.RpcError;
import entity.RpcRequest;
import entity.RpcResponse;
import exception.RpcException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Slf4j
public class CommonDecoder extends ReplayingDecoder {
    private static final int MAGIC_NUMBER = 0xCAFEBABE;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int magic=in.readInt();
        if (magic != MAGIC_NUMBER) {
            log.error("不识别的包协议：{}",magic);
            throw new RpcException(RpcError.UNKNOWN_PROTOCOL);
        }
        int packageCode=in.readInt();
        Class<?> packageClass;
        if(packageCode== PackageType.REQUEST_PACK.getCode()){
            packageClass= RpcRequest.class;
        }else if (packageCode== PackageType.RESPONSE_PACK.getCode()){
            packageClass= RpcResponse.class;
        }else{
            log.error("不识别的数据包：{}",packageCode);
            throw new RpcException(RpcError.UNKNOWN_PACKAGE_TYPE);
        }
        int serializeCode=in.readInt();
        Serializer serializer=Serializer.getByCode(serializeCode);
        if (serializer == null) {
            log.error("不识别的反序列化器：{}",serializeCode);
        }
        int length=in.readInt();
        byte[] bytes=new byte[length];
        in.readBytes(bytes);
        Object obj=serializer.deserialize(bytes,packageClass);
        out.add(obj);
    }
}
