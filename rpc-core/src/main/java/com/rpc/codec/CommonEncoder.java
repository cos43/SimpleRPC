package com.rpc.codec;

import com.rpc.serializer.Serializer;
import com.rpc.constants.PackageType;
import com.rpc.entity.RpcRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * +---------------+---------------+-----------------+-------------+
 * |  Magic Number |  Package Type | Serializer Type | Data Length |
 * |    4 bytes    |    4 bytes    |     4 bytes     |   4 bytes   |
 * +---------------+---------------+-----------------+-------------+
 * |                          Data Bytes                           |
 * |                   Length: ${Data Length}                      |
 * +---------------------------------------------------------------+
 */
public class CommonEncoder extends MessageToByteEncoder {
    private static final int MAGIC_NUMBER = 0xCAFEBABE;
    private final Serializer serializer;
    public CommonEncoder(Serializer serializer){
        this.serializer=serializer;
    }
    @Override
    protected void encode(ChannelHandlerContext ctx, Object o, ByteBuf out) throws Exception {
        out.writeInt(MAGIC_NUMBER);
        if (o instanceof RpcRequest) {
            out.writeInt(PackageType.REQUEST_PACK.getCode());
        }else{
            out.writeInt(PackageType.RESPONSE_PACK.getCode());
        }
        out.writeInt(serializer.getCode());
        byte[] bytes=serializer.serialize(o);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}
