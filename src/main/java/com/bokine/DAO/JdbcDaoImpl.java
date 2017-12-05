package com.bokine.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bokine.modelo.Nota;

public class JdbcDaoImpl {

    private String host;
    private String user;
    private String pass;
    private String database;
   
    public Connection c;
    
    private static final Logger logger = 
		      LogManager.getLogger(JdbcDaoImpl.class.getName());
   
    /**
     * Construtor da classe
     *
     * @param host Host em que se deseja conectar
     * @param database Nome do database em que se deseja conectar
     * @param user Nome do usuário
     * @param pass Senha do usuário
     */
    public JdbcDaoImpl ( String host, String database, String user, String pass ) {
        this.pass = pass;
        this.user = user;
        this.host = host;
        this.database = database;
    }
    
    public Map<String, Nota> NfceFilial03() {
    	Map<String, Nota> notasFirebird = new HashMap<String, Nota>();
    	String sql ="select "
    			+ "nf.nota,nf.idnfe,nf.modelo,nf.data from nf where nf.idrecibo is not null "
    			+ "and nf.filial=5 "
    			+ "and nf.modelo<>32 "
    			+ "and nf.modelo=35 "
    			+ "order by 1";
         
    	 String portNumber = "3050";
         String url = "jdbc:firebirdsql:"+ this.host+"/" +portNumber + ":" +this.database;;
         String userName   = this.user;
         String passName   = this.pass;
         
         try {
             Class.forName("org.firebirdsql.jdbc.FBDriver").newInstance();
             this.c = DriverManager.getConnection(url,userName, passName);
         } catch( SQLException e ) {
             e.printStackTrace();
             System.out.println(e.getMessage());
         } catch ( ClassNotFoundException e ) {
             e.printStackTrace();
             System.out.println(e.getMessage());
         } catch ( InstantiationException e ) {
             e.printStackTrace();
             System.out.println(e.getMessage());
         } catch ( IllegalAccessException e ) {
             e.printStackTrace();
             System.out.println(e.getMessage());
         }
    	
        ResultSet rs = executar(sql);
      
		 try {
			while(rs.next()){
				Nota nota = new Nota();
				
				try {
					nota.setNota(rs.getString("NOTA")); 
				} catch (Exception e) {
					nota.setNota("");
					System.out.println("Erro ao capturar numero da nota");
				}
				try {
					if(rs.getString(2).length()>0){
						nota.setIdNfe(rs.getString(2));
					}
				} catch (Exception e) {
					nota.setIdNfe(null);
				}
				try {
					nota.setData(rs.getTimestamp("DATA").toLocalDateTime());
				} catch (Exception e) {
					nota.setData(null);
					System.out.println("Erro ao capturar Data");
				}
				notasFirebird.put(nota.getNota(), nota);
				
		 	}
		} catch (Exception e) {
			System.out.println("Erro ao buscar elemento: "+e);
		}
    	return notasFirebird;
    }
    
    public Long maiorNota() {
    	String sql ="select "
    			+"max(nf.nota) from nf "
    			+"where nf.idrecibo is not null "
    			+"and nf.filial=5 "
    			+"and nf.modelo<>32 "
    			+"and nf.modelo=35 "
    			+"order by 1";
    	
    	String portNumber = "3050";
        String url = "jdbc:firebirdsql:"+ this.host+"/" +portNumber + ":" +this.database;;
        String userName   = this.user;
        String passName   = this.pass;
        
        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver").newInstance();
            this.c = DriverManager.getConnection(url,userName, passName);
        } catch( SQLException e ) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch ( InstantiationException e ) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch ( IllegalAccessException e ) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        
        ResultSet rs = executar(sql);
        Long maxID = null;
        try {
        	while ( rs.next() ){
        		  maxID = rs.getLong("MAX");
        		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return maxID;
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
    private ResultSet executar( String query ) {
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