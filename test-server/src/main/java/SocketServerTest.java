import com.rpc.transport.socket.server.SocketServer;

public class SocketServerTest {
    public static void main(String[] args) {
        SocketServer socketServer = new SocketServer();
        HelloService helloService = new HelloServiceImpl();
        socketServer.register(helloService, 9000);
    }
}
