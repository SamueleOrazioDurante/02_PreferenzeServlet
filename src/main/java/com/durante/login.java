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
		
		String query = "SELECT username,idClasse FROM studenti WHERE username = '"+ username + "' AND password = '"+ password + "'";
		
		try {
			res.setContentType("text/html");
			PrintWriter out = res.getWriter();
			StringBuffer buffer = new StringBuffer();
			
			ResultSet result = conn.createStatement().executeQuery(query);
			if(result.next()) {
                
				int idClasse = result.getInt("idClasse");

				buffer.append("<html><title>Logged user: "+username+"</title>");
				buffer.append("<body><form name='preferenza' action='http://localhost:8080/01_TestMavenEclipse/preferenza' method='GET'>");
                buffer.append("<p>Username: <INPUT TYPE='text' NAME='usrStudente' SIZE=30 value='" + username + "' disabled></p>");
				buffer.append("<p>Cognome Prof.: <SELECT NAME='cognomeProf'>");
                buffer.append("<option value='-1' selected> Seleziona una preferenza </option>");

				query = "SELECT idProfessore,cognomeProfessore FROM professori WHERE idClasse= '"+ idClasse+"'";
				ResultSet prof= conn.createStatement().executeQuery(query);
				while(prof.next()){
					int idProfessore = result.getInt("idProfessore");
					String cognomeProfessore = result.getString("cognomeProfessore");
					buffer.append("<option value='"+ idProfessore +"'> "+ cognomeProfessore +" </option>");
				}

				buffer.append("</select> </p>");
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









