public class HelloServiceImpl implements HelloService{
    @Override
    public String hello(HelloObject object) {
        System.out.println("接收到消息：" + object.toString());
        return object.toString();
    }
}
