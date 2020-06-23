package mypack;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class HelloClient {

    public static void main(String[] args) throws Exception {
        // ����Web����Ŀͻ��˴�����
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        // ע��Web����ӿ�
        factory.setServiceClass(HelloService.class);
        // ����Web����ĵ�ַ
        factory.setAddress("http://localhost:8080/helloweb/WS/HelloService?wsdl");
        // ��÷���ӿڵĿͻ��˴������
        HelloService service = (HelloService) factory.create();
        //���÷��񷽷�
        System.out.println(service.sayHello("weiqin"));
    }

}