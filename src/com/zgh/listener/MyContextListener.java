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
@WebListener	//使用注解注册监听器
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
    	context.log("添加一个属性"+sce.getName()+":"+sce.getValue());
    	/*
    	 * sce 的方法
    	 * public ServletContext getServletContext()  返回属性发生改变的ServletContext对象
    	 * public String getName()	返回发生改变的属性名。
    	 * public Object getValue()	返回发生改变的属性值对象，注意：当替换属性时，该方法返回的是替换之前的属性。
    	 */
    }

	/**
     * @see ServletContextAttributeListener#attributeRemoved(ServletContextAttributeEvent)
     */
    //从ServletContext对象中删除属性
    public void attributeRemoved(ServletContextAttributeEvent sce)  {
    	context=sce.getServletContext();
    	context.log("删除一个属性"+sce.getName()+":"+sce.getValue());
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    //当ServletContext对象销毁时调用
    public void contextDestroyed(ServletContextEvent sce)  {
    	context=sce.getServletContext();
    	context.removeAttribute("dataSource");
    	context.log("应用程序已关闭"+new Date());
    }

	/**
     * @see ServletContextAttributeListener#attributeReplaced(ServletContextAttributeEvent)
     */
    //从ServletContext对象中替换属性
    public void attributeReplaced(ServletContextAttributeEvent sce)  { 
         context=sce.getServletContext();
         context.log("替换一个属性"+sce.getName()+":"+sce.getValue());
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    //当ServletContext对象初始化时调用，即Tomcat开始运行时
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
    	context.setAttribute("dataSource", dataSource);	//添加属性
    	context.log("应用程序已启动："+new Date());
    }
	
}
