import com.rpc.transport.RpcClientProxy;
import com.rpc.transport.socket.client.SocketClient;

public class SocketClientTest {
    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy("localhost",9000);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res=helloService.hello(object);
        System.out.println(res);
    }
}
