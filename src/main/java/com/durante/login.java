package com.durante;


import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
		
		
		
		String username = reqt.getParameter("username");
		String password = reqt.getParameter("password");
		
		String query = "SELECT username FROM utente WHERE username = '"+ username + "' AND password = '"+ password + "'";
		
		try {
			res.setContentType("text/html");
			PrintWriter out = res.getWriter();
			StringBuffer buffer = new StringBuffer();
			
			ResultSet result = conn.createStatement().executeQuery(query);
			if(result.next()) {
				buffer.append("<html><title>Logged user: "+username+"</title>");
				buffer.append("<body><form name='preferenza' action='http://localhost:8080/01_TestMavenEclipse/preferenza' method='GET'>");
				buffer.append("<p>Nome Prof.: <INPUT TYPE='text' NAME='nomeProf' SIZE=30></p>");
				buffer.append("<p>Cognome Prof.: <INPUT TYPE='text' NAME='cognomeProf' SIZE=30></p>");
				buffer.append("<p>Materia: <INPUT TYPE='text' NAME='materia' SIZE=30></p>");
				buffer.append("<p><INPUT TYPE='submit'></p>");
				buffer.append("<p><INPUT TYPE='reset' NAME='resetbutton' VALUE='Clear data'> </p>");
				buffer.append("</form></body></html>");
				
				out.println(buffer.toString());
				out.close();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	public void doPost(HttpServletRequest reqt, HttpServletResponse res)
	{
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

}
