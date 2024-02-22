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
		//password = encryptPassword(password);
		
		String query = "SELECT usernameStudente,idClasse,matricola FROM studenti WHERE usernameStudente = '"+ username + "' AND password = '"+ password + "'";
		
		try {
			res.setContentType("text/html");
			PrintWriter out = res.getWriter();
			StringBuffer buffer = new StringBuffer();
			
			ResultSet result = conn.createStatement().executeQuery(query);
			if(result.next()) {
                
				int idClasse = result.getInt("idClasse");
				int matricola = result.getInt("matricola");

				buffer.append("<html><title>Logged user: "+username+"</title>");
				buffer.append("<body><form name='preferenza' action='http://localhost:8080/02_PreferenzeServlet/preferenza' method='GET'>");
				buffer.append("<INPUT TYPE='hidden' NAME='matricola' SIZE=30 value='" + matricola + "' readonly>");
                buffer.append("<p>Username: <INPUT TYPE='text' NAME='username' SIZE=30 value='" + username + "' readonly disabled></p>");
				buffer.append("<p>Cognome Prof.: <SELECT NAME='idProfessore'>");
                buffer.append("<option value='-1' selected> Seleziona una preferenza </option>");

				query = "SELECT idProfessore,cognomeProfessore FROM professori WHERE idClasse= '"+ idClasse+"'";
				ResultSet prof= conn.createStatement().executeQuery(query);
				while(prof.next()){
					int idProfessore = prof.getInt("idProfessore");
					String cognomeProfessore = prof.getString("cognomeProfessore");
					buffer.append("<option value='"+ idProfessore +"'> "+ cognomeProfessore +" </option>");
				}

				buffer.append("</select> </p>");
				buffer.append("<p><INPUT TYPE='submit'></p>");
				buffer.append("<p><INPUT TYPE='reset' NAME='resetbutton' VALUE='Clear data'> </p>");
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









