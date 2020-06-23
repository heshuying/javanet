package exercise;
import java.net.*;
import java.io.*;
public class FileURLStreamHandler extends URLStreamHandler{
  public int getDefaultPort(){
    return 8000;
  }
  protected URLConnection openConnection(URL url)throws IOException{
    return new FileURLConnection(url);
  }
}


/****************************************************
 * ���ߣ�������                                     *
 * ��Դ��<<Java�����̺��ļ������>>                       *
 * ����֧����ַ��www.javathinker.net                *
 ***************************************************/
