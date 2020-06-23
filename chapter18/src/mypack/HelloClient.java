package mypack;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class HelloClient {

    public static void main(String[] args) throws Exception {
        // 创建Web服务的客户端代理工厂
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        // 注册Web服务接口
        factory.setServiceClass(HelloService.class);
        // 设置Web服务的地址
        factory.setAddress("http://localhost:8080/helloweb/WS/HelloService?wsdl");
        // 获得服务接口的客户端代理对象
        HelloService service = (HelloService) factory.create();
        //调用服务方法
        System.out.println(service.sayHello("weiqin"));
    }

}