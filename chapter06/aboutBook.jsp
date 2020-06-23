<%--该JSP文件已经发布到www.javathinker.net网站，HttpClient3访问该JSP文件--%>

<%@ page contentType="text/html; charset=GB2312" %>
<%@page import="java.util.HashMap" %>
<%
String title=request.getParameter("title");
HashMap<String,String> map=new HashMap<String,String>();
String data="书名：Java面向对象编程\n";
data=data+"出版社：电子工业出版社\n";
data=data+"出版时间：2017/7/1\n";
data=data+"作者：孙卫琴\n";
data=data+"价格：89";
map.put("Java面向对象编程",data);

data="书名：Tomcat与JavaWeb开发技术详解\n";
data=data+"出版社：电子工业出版社\n";
data=data+"出版时间：2018/4/1\n";
data=data+"作者：孙卫琴\n";
data=data+"价格：129";
map.put("Tomcat与JavaWeb开发技术详解",data);

data="书名：精通Struts:基于MVC的JavaWeb设计与开发\n";
data=data+"出版社：电子工业出版社\n";
data=data+"出版时间：2004/8/1\n";
data=data+"作者：孙卫琴\n";
data=data+"价格：49";
map.put("精通Struts:基于MVC的JavaWeb设计与开发",data);

data="书名：精通JPA与Hibernate:Java对象持久化技术详解\n";
data=data+"出版社：电子工业出版社\n";
data=data+"出版时间：2019/8/1\n";
data=data+"作者：孙卫琴\n";
data=data+"价格：59";
map.put("精通JPA与Hibernate:Java对象持久化技术详解",data);

data="书名：Java2认证考试指南与试题解析\n";
data=data+"出版社：上海科学技术出版社\n";
data=data+"出版时间：2002/10/1\n";
data=data+"作者：孙卫琴\n";
data=data+"价格：88";
map.put("Java2认证考试指南与试题解析",data);

data=map.get(title);
out.println(data);
%>
