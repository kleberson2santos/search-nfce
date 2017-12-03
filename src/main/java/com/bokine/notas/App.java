package com.bokine.notas;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.bokine.DAO.Firebird;
import com.bokine.config.Configuracao;
import com.bokine.modelo.Nota;
import com.bokine.util.Formatador;
import com.bokine.util.Validador;

import br.com.samuelweb.nfe.ConfiguracoesIniciaisNfe;
import br.com.samuelweb.nfe.exception.NfeException;

public class App 
{
	private static String database;
	private static String host;
	private static String user;
	private static String pass;
	private static Nota nota;
	public static int maiorNumero=0;
	
	public static List<Nota> NotasAnalisar = new ArrayList<Nota>();
	
	public static Set<Nota> NotasParaTriar = new HashSet<Nota>();
	public static  Map<String, Nota> notasFirebird = new HashMap<String, Nota>();
	
	public static Formatador formatador = new Formatador(){};
	public static ConfiguracoesIniciaisNfe config = Configuracao.iniciaConfigurações();

	public static void main( String[] args ){
        //database = "C:/sys/base/BKN00001";
		database =  "/home/saturno/Java-Projects/database/BKN00001.fdb";
		System.out.println("base de dados "+database);
        host = "127.0.0.1";
        user = "SYSDBA";
        pass = "masterkey";
        
        maiorNumero=135963;
  
        System.out.println("BOKINE - SISTEMA DE NFCe" );
		Firebird firebird = new Firebird(host, database,user, pass);
		System.out.println("Firebird conectado?:"+firebird.connect());	
		
		Nota danfe = new Nota("135520", "13171003351649000145650000001355201000000013", "", "", "");
		validarNF(danfe);
//		
		String sql = "select nf.nota,nf.idnfe,nf.modelo,nf.data from nf "+
				"where nf.idrecibo is not null "+
				"and nf.modelo<>32 "+
				"and nf.modelo <> 33 "+
				"and nf.data BETWEEN '07.11.2014' and '18.10.2017' "+
				"and nf.filial=5 ";
//		
		ResultSet rs = firebird.executar(sql);
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
					nota.setIdNfe("");
					//System.out.println("Erro ao capturar ID da nota");
				}
				try {
					nota.setData(rs.getString("DATA"));
				} catch (Exception e) {
					nota.setData("");
					System.out.println("Erro ao capturar Data da nota");
				}
				//notasFirebird.put(nota.getNota(), nota);
				//NotasParaTriar.add(nota);
		 	}
		} catch (Exception e) {
			System.out.println("Erro ao buscar elemento: "+e);
		}
		 
		
//		maiorNota(NotasParaTriar);
		//triar(notasFirebird,maiorNumero);
//		for (Nota nota : NotasParaTriar) {
//			System.out.println("Nota:"+nota.getNota()+"|"+nota.getIdNfe());
//		}
//		NotasNaoEncontradas.forEach(n->System.out.println(nota.getNota()));
    }
	
	private static void triar(Map<String,Nota> notas, int maiorNota) {
		//notas.keySet().stream().forEach(); 
		
		Collection<Integer> clients = new ArrayList<>();
	 	for (int i = 0; i <= maiorNota; i++) {
			clients.add(i);
		}
	 	
		Set<String> acceptableNames = notas.entrySet().stream().map(u->u.getValue().getNota()).collect(Collectors.toSet()); 
		 	
			List<Integer> cli = clients .stream().filter(c -> !acceptableNames.contains(c.toString()))
		 				.collect(Collectors.toList());

			Formatador.imprimir(cli);
		
	}
	
	private static int maiorNota(Set<Nota> notasParaTriar){
		int maiorNumero = 0;
		for(Nota nota : notasParaTriar){
			if(Integer.parseInt(nota.getNota())>maiorNumero){
				maiorNumero = Integer.parseInt(nota.getNota());
			}
		}
		return maiorNumero;
	}

	private static void validarNF(Nota nota){
		new Validador().validador(nota,config);
	}
	
}
