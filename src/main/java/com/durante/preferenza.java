package com.durante;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class preferenza extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public preferenza() {
        // TODO Auto-generated constructor stub
    }
    
    String db_url = null;
    String db_usr = null;
    String db_psw = null;
    Connection conn = null;
    
	@Override
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
	
	@Override
	public void doGet(HttpServletRequest reqt, HttpServletResponse res)
	{
		
		try {
			res.setContentType("text/html");
			String nomeProf = reqt.getParameter("nomeProf");
			String cognomeProf = reqt.getParameter("cognomeProf");
			String materia = reqt.getParameter("materia");

			String sql = "INSERT INTO preferenze (nomeProf, cognomeProf, materia) VALUES ('"+nomeProf+"', '"+cognomeProf+"', '"+materia+"')";
			
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int rowsInserted = statement.executeUpdate();
			
			if (rowsInserted > 0) {
			    System.out.println("Preferenza inserita!");
			} else {
			    System.out.println("Errore.");
			}

			statement.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		
		
	}
	@Override
	public void doPost(HttpServletRequest reqt, HttpServletResponse res)
	{
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

}
