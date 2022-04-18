package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;

public class Unserial extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String source = req.getParameter("source");
            byte[] decode = Base64.getDecoder().decode(source);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decode);
            ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream);
            inputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
