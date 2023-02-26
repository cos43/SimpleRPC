import com.rpc.transport.RpcClient;
import com.rpc.transport.RpcClientProxy;
import com.rpc.transport.netty.client.NettyClient;

public class NettyTestClient {
    public static void main(String[] args) {
        RpcClient client=new NettyClient("localhost",9999);
        RpcClientProxy proxy=new RpcClientProxy(client);
        HelloService helloService=proxy.getProxy(HelloService.class);
        HelloObject object=new HelloObject(12,"test");
        System.out.println(helloService.hello(object));
    }
}
