package com.zgh.listener;

import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

/**
 * Application Lifecycle Listener implementation class MyContextListener
 *
 */
@WebListener	//ʹ��ע��ע�������
public class MyContextListener implements ServletContextListener, ServletContextAttributeListener {
	
	private ServletContext context=null;
    /**
     * Default constructor. 
     */
    public MyContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextAttributeListener#attributeAdded(ServletContextAttributeEvent)
     */
    public void attributeAdded(ServletContextAttributeEvent sce)  {
    	context=sce.getServletContext();
    	context.log("���һ������"+sce.getName()+":"+sce.getValue());
    	/*
    	 * sce �ķ���
    	 * public ServletContext getServletContext()  �������Է����ı��ServletContext����
    	 * public String getName()	���ط����ı����������
    	 * public Object getValue()	���ط����ı������ֵ����ע�⣺���滻����ʱ���÷������ص����滻֮ǰ�����ԡ�
    	 */
    }

	/**
     * @see ServletContextAttributeListener#attributeRemoved(ServletContextAttributeEvent)
     */
    //��ServletContext������ɾ������
    public void attributeRemoved(ServletContextAttributeEvent sce)  {
    	context=sce.getServletContext();
    	context.log("ɾ��һ������"+sce.getName()+":"+sce.getValue());
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    //��ServletContext��������ʱ����
    public void contextDestroyed(ServletContextEvent sce)  {
    	context=sce.getServletContext();
    	context.removeAttribute("dataSource");
    	context.log("Ӧ�ó����ѹر�"+new Date());
    }

	/**
     * @see ServletContextAttributeListener#attributeReplaced(ServletContextAttributeEvent)
     */
    //��ServletContext�������滻����
    public void attributeReplaced(ServletContextAttributeEvent sce)  { 
         context=sce.getServletContext();
         context.log("�滻һ������"+sce.getName()+":"+sce.getValue());
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    //��ServletContext�����ʼ��ʱ���ã���Tomcat��ʼ����ʱ
    public void contextInitialized(ServletContextEvent sce)  {
    	Context ctx=null;
    	DataSource dataSource=null;
    	context=sce.getServletContext();
    	try {
    		if(ctx==null){
				ctx=new InitialContext();
			} 
    		dataSource=(DataSource)ctx.lookup("java:comp/env/jdbc/sampleDS");
    	}catch (NamingException ne) {
			// TODO Auto-generated catch block
			context.log("Exception:"+ne);
		}
    	context.setAttribute("dataSource", dataSource);	//�������
    	context.log("Ӧ�ó�����������"+new Date());
    }
	
}
