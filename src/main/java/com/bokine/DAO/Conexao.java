package com.bokine.DAO;

/*
 * Created on 09/08/2004
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * @author BBAmbrozio
 *
 * Classe responsável pela conexão com o banco de dados Firebird 1.5
 *
 * Database: horas
 * User: sysdba
 * Password: masterkey
 * 
 */

public class Conexao {

   public Connection con = null;
   public Statement stm = null;

   public Conexao() {

      try {

         Class.forName("org.firebirdsql.jdbc.FBDriver");
         con =
            DriverManager.getConnection(
               "jdbc:firebirdsql:localhost/3050:C:\\sys\\base\\BKN00001",
               "SYSDBA",
               "masterkey");
         stm = con.createStatement();

      } catch (Exception e) {
         System.out.println("Não foi possível conectar ao banco: " + e.getMessage());
      }

   }
      
}
