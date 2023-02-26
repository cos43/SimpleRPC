import com.rpc.registry.DefaultServiceRegistry;
import com.rpc.registry.ServiceRegistry;
import com.rpc.transport.netty.server.NettyServer;

public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService=new HelloServiceImpl();
        ServiceRegistry registry=new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server=new NettyServer();
        server.start(9999);
    }
}
