<%--��JSP�ļ��Ѿ�������www.javathinker.net��վ��HttpClient3���ʸ�JSP�ļ�--%>

<%@ page contentType="text/html; charset=GB2312" %>
<%@page import="java.util.HashMap" %>
<%
String title=request.getParameter("title");
HashMap<String,String> map=new HashMap<String,String>();
String data="������Java���������\n";
data=data+"�����磺���ӹ�ҵ������\n";
data=data+"����ʱ�䣺2017/7/1\n";
data=data+"���ߣ�������\n";
data=data+"�۸�89";
map.put("Java���������",data);

data="������Tomcat��JavaWeb�����������\n";
data=data+"�����磺���ӹ�ҵ������\n";
data=data+"����ʱ�䣺2018/4/1\n";
data=data+"���ߣ�������\n";
data=data+"�۸�129";
map.put("Tomcat��JavaWeb�����������",data);

data="��������ͨStruts:����MVC��JavaWeb����뿪��\n";
data=data+"�����磺���ӹ�ҵ������\n";
data=data+"����ʱ�䣺2004/8/1\n";
data=data+"���ߣ�������\n";
data=data+"�۸�49";
map.put("��ͨStruts:����MVC��JavaWeb����뿪��",data);

data="��������ͨJPA��Hibernate:Java����־û��������\n";
data=data+"�����磺���ӹ�ҵ������\n";
data=data+"����ʱ�䣺2019/8/1\n";
data=data+"���ߣ�������\n";
data=data+"�۸�59";
map.put("��ͨJPA��Hibernate:Java����־û��������",data);

data="������Java2��֤����ָ�����������\n";
data=data+"�����磺�Ϻ���ѧ����������\n";
data=data+"����ʱ�䣺2002/10/1\n";
data=data+"���ߣ�������\n";
data=data+"�۸�88";
map.put("Java2��֤����ָ�����������",data);

data=map.get(title);
out.println(data);
%>
