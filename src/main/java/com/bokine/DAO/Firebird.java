package com.bokine.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class Firebird {

    private String host;
    private String user;
    private String pass;
    private String database;
   
    public Connection c;
    
    private static final Logger logger = 
		      Logger.getLogger(Firebird.class.getName());
   
    /**
     * Construtor da classe
     *
     * @param host Host em que se deseja conectar
     * @param database Nome do database em que se deseja conectar
     * @param user Nome do usuário
     * @param pass Senha do usuário
     */
    public Firebird ( String host, String database, String user, String pass ) {
        this.pass = pass;
        this.user = user;
        this.host = host;
        this.database = database;
    }
   
    /**
     * Método que estabelece a conexão com o banco de dados
     *
     * @return True se conseguir conectar, falso em caso contrário.
     */
    public boolean connect() {
        boolean isConnected = false;
        
        String url;
        String portNumber = "3050";
        String userName   = this.user;
        String passName   = this.pass;
        url = "jdbc:firebirdsql:"+ this.host+"/" +portNumber + ":" +this.database;
        
        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver").newInstance();
            this.c = DriverManager.getConnection(url,userName, passName);
            isConnected = true;
        } catch( SQLException e ) {
            e.printStackTrace();
            logger.info("ERRAO FEIO:");
            System.out.println(e.getMessage());
            isConnected = false;
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            isConnected = false;
        } catch ( InstantiationException e ) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            isConnected = false;
        } catch ( IllegalAccessException e ) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            isConnected = false;
        }
       
        return isConnected;
    }
   
    /**
     * Método que estabelece a desconexão com o banco de dados
     *
     * @return True se conseguir desconectar, falso em caso contrário.
     */
    public boolean disconnect() {
        boolean isConnected = false;
       
        String url;
        String portNumber = "3050";
        String userName   = this.user;
        String passName   = this.pass;
        url = "jdbc:firebirdsql:"+ this.host+"/" +portNumber + ":" +this.database;
             
        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver").newInstance();
            this.c = DriverManager.getConnection(url,userName, passName);
            this.c.close();
            isConnected = true;
        } catch( SQLException e ) {
            System.out.println(e.getMessage());
            isConnected = false;
        } catch ( ClassNotFoundException e ) {
            System.out.println(e.getMessage());
            isConnected = false;
        } catch ( InstantiationException e ) {
            System.out.println(e.getMessage());
            isConnected = false;
        } catch ( IllegalAccessException e ) {
            System.out.println(e.getMessage());
            isConnected = false;
        }
       
        return isConnected;
    }

    /**
     * Esse método executa a query dada, e retorna um ResultSet
     * Talvez fosse melhor idéia fazer esse método lançar uma exception
     * a faze-lo retornar null como eu fiz, porém isso é apenas um exemplo
     * para demonstrar a funcionalidade do comando execute
     *
     * @param query String contendo a query que se deseja executar
     * @return ResultSet em caso de estar tudo Ok, null em caso de erro.
     */
    public ResultSet executar( String query ) {
        Statement st;
        ResultSet rs;
       
        try {
            st = this.c.createStatement();
            rs = st.executeQuery(query);
            return rs;
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
       
        return null;
    }
   
    /**
     * Executa uma query como update, delete ou insert.
     * Retorna o número de registros afetados quando falamos de um update ou delete
     * ou retorna 1 quando o insert é bem sucedido. Em outros casos retorna -1
     *
     * @param query A query que se deseja executar
     * @return 0 para um insert bem sucedido. -1 para erro
     */
    public int inserir( String query ) {
        Statement st;
        int result = -1;
       
        try {
            st = this.c.createStatement();
            result = st.executeUpdate(query);
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
       
        return result;
    }
    
}