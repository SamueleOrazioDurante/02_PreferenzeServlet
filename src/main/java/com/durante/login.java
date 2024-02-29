package com.durante;


import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.*;
import java.time.format.DateTimeFormatter;


public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public login() {
        // TODO Auto-generated constructor stub
    }
    
    String db_url = null;
    String db_usr = null;
    String db_psw = null;
    Connection conn = null;
    
	
	public void init(ServletConfig cfg){
    	try{
	    	super.init(cfg);
	    	
	    	db_url = cfg.getInitParameter("db_url");
	    	db_usr = cfg.getInitParameter("db_usr");
	    	db_psw = cfg.getInitParameter("db_psw");
	    	
	    	db_url="jdbc:mysql://"+db_url;
	    	
	    	Class.forName("com.mysql.cj.jdbc.Driver");
	    	
	    	conn = DriverManager.getConnection(db_url,db_usr,db_psw);
	    	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
	}
	
	public void doGet(HttpServletRequest reqt, HttpServletResponse res)
	{
		
		
		
		String email = reqt.getParameter("email");
		String password = reqt.getParameter("password");
		//password = encryptPassword(password);
		
		String query = "SELECT email,classe FROM studente WHERE email = '"+ email + "' AND password = '"+ password + "'";
		
		try {
			res.setContentType("text/html");
			PrintWriter out = res.getWriter();
			StringBuffer buffer = new StringBuffer();
			
			ResultSet result = conn.createStatement().executeQuery(query);
			if(result.next()) {
				
				HttpSession sess = reqt.getSession();

				if(sess != null) {
					sess.invalidate();
				}
				sess = reqt.getSession();
				sess.setAttribute("email", email);
				sess.setAttribute("password", password);

				buffer.append("<html><title>Logged user: "+email+"</title>");
				buffer.append("<body>");
                buffer.append("<p>E-mail: <INPUT TYPE='text' NAME='email' SIZE=50 value='" + email + "' readonly disabled></p>");
				buffer.append("<p>Data e ora corrente: </p>" + ZonedDateTime.now(ZoneId.of("Europe/Rome")));

				String classe = result.getString("classe");

				query = "SELECT titolo FROM libro INNER JOIN catalogo ON catalogo.idLibro=libro.idLibro WHERE nomeClasse= '"+ classe +"'";
				ResultSet libri= conn.createStatement().executeQuery(query);

				while(libri.next()){

					String titolo = libri.getString("titolo");
					buffer.append("<p> "+ titolo +" </p>");
				}

				buffer.append("</form></body></html>");
				
				
			}else {
				
				buffer.append("<html><title>Error Page</title>");
				buffer.append("<body><h1>Username o Password errati</h2>");
				buffer.append("<p><a href='index.html'>Torna al Login </a><p></body></html>");			
				
			}
			
			out.println(buffer.toString());
			out.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	public void doPost(HttpServletRequest reqt, HttpServletResponse res)
	{
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}
	
	private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}









