package com.bokine.notas;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bokine.DAO.JdbcDaoImpl;
import com.bokine.config.Configuracao;
import com.bokine.modelo.Nota;
import com.bokine.util.Comparador;
import com.bokine.util.Formatador;
import com.bokine.util.Validador;

import br.com.samuelweb.nfe.ConfiguracoesIniciaisNfe;

public class App 
{
	private static String database;
	private static String host;
	private static String user;
	private static String pass;
	public static Long maiorNumero=0L;
	
	public static List<Nota> NotasAnalisar = new ArrayList<Nota>();
	
	public static Set<Nota> NotasParaTriar = new HashSet<Nota>();
	public static  Map<String, Nota> notasComProtocolo = new HashMap<String, Nota>();
	public static  Map<String, Nota> notasSemRecibo = new HashMap<String, Nota>();
	public static  Map<String, Nota> notasSemProtocolo = new HashMap<String, Nota>();
	public static  Map<String, Nota> todasNfce = new HashMap<String, Nota>();
	
	public static Formatador formatador = new Formatador(){};
	public static Comparador comparador = new Comparador();
	public static ConfiguracoesIniciaisNfe config = Configuracao.iniciaConfigurações();
	
	 private static final Logger logger = 
		      LogManager.getLogger(App.class.getName());

	public static void main( String[] args ){
        //database = "C:/sys/base/BKN00001";
		//database =  "/home/saturno/Java-Projects/database/BKN00001.fdb";
		database =  "/home/kleber/Documentos/Repositorios_Java/Data Base Firebird/BKN00001.fdb";
        host = "127.0.0.1"; user = "SYSDBA"; pass = "masterkey";
        
        JdbcDaoImpl firebirdDao = new JdbcDaoImpl(host, database,user, pass);
        logger.debug("BOKINE - SISTEMA DE NFCe" );
        
        maiorNumero=firebirdDao.maiorNota();
		
		Nota danfe = new Nota("135520", "13171003351649000145650000001355201000000013", "", "", LocalDateTime.now());
		
		Comparador comparator = new Comparador();
		
		//logger.debug("NOTAS COM PROTOCOLO NA BASE");
		notasComProtocolo = firebirdDao.NfceComProtocolo();
		//imprimirRangerNotas(notasComProtocolo);
		
		//logger.debug("NOTAS SEM PROTOCOLO NA BASE");
		notasSemProtocolo = firebirdDao.NfceSemProtocolo();
		//imprimirRangerNotas(notasSemProtocolo);
		
		//logger.debug("NOTAS SEM RECIBO NA BASE");
		notasSemRecibo = firebirdDao.NfceSemRecibo();
		//imprimirRangerNotas(notasSemRecibo);
		
		todasNfce = firebirdDao.todasNfce();
		
		unirListasParaImpressao(notasComProtocolo, notasSemProtocolo,notasSemRecibo);
		logger.debug("NOTAS NAO ENCONTRADAS" );
		imprimirNotasNaoRegistradas(todasNfce,maiorNumero);
		
		/*notasFirebird.values().stream()
		.sorted(comparator)
		.forEach(nota->
			logger.debug(
					nota.getNota()+"\t"+
					nota.getIdNfe()+"\t"+
					nota.getMotivo()+"\t"+
					nota.getStatus()+"\t"+
					nota.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
		*/
//		Formatador.imprimir(notasFirebird.values().stream().mapToInt(x->Integer.parseInt(x.getNota()))
//					.collect(ArrayList::new, ArrayList::add,ArrayList::addAll));
		
//		maiorNota(NotasParaTriar);
		//triar(notasFirebird,maiorNumero);
//		for (Nota nota : NotasParaTriar) {
//			System.out.println("Nota:"+nota.getNota()+"|"+nota.getIdNfe());
//		}
//		NotasNaoEncontradas.forEach(n->System.out.println(nota.getNota()));
    }
	
	private static void unirListasParaImpressao(Map<String, Nota> notasComProtocolo,
		Map<String, Nota> notasSemProtocolo, Map<String,Nota> notasSemRecibo) {
		Map<String, Nota> result = notasSemProtocolo.values().stream().filter(nota -> !notasComProtocolo.containsKey(nota.getNota()))
 				.collect(Collectors.toMap(Nota::getNota, n -> n, // key = name, value = websites
						(oldValue, newValue) -> oldValue, // if same key, take the old key
						LinkedHashMap::new));
		
		Map<String, Nota> result2 = notasSemRecibo.values().stream().filter(nota -> !notasComProtocolo.containsKey(nota.getNota()))
 				.collect(Collectors.toMap(Nota::getNota, n -> n, // key = name, value = websites
						(oldValue, newValue) -> oldValue, // if same key, take the old key
						LinkedHashMap::new));

		logger.debug("NOTAS SEM PROTOCOLO");
		result.putAll(result2);
		imprimirRangerNotas(result);
		
	}

	private static void imprimirNotasNaoRegistradas(Map<String,Nota> notas, Long maiorNota) {
		logger.debug("NOTAS NAO ENCONTRADAS NA BASE");
		
		Collection<Integer> ranger = new ArrayList<>();
		
	 	for (int i = 0; i <= maiorNota; i++) {ranger.add(i);}
	 	
		Set<String> idsStrings = notas.entrySet().stream().map(u->u.getValue().getNota()).collect(toSet()); 
		 	
		List<Integer> idsInteger = ranger.stream().filter(id -> !idsStrings.contains(id.toString()))
		 				.collect(toList());
		
		Formatador.imprimir(idsInteger);
		
	}
	
	private static void imprimirRangerNotas(Map<String,Nota> notas) {
	 	
		List<Integer> idsInteger = notas.entrySet().stream().mapToInt(u->Integer.parseInt(u.getValue().getNota())).collect(ArrayList::new, ArrayList::add,ArrayList::addAll); 
		
		Formatador.imprimir(idsInteger);
		
	}
	
//	
//	private static int maiorNota(Set<Nota> notasParaTriar){
//		int maiorNumero = 0;
//		for(Nota nota : notasParaTriar){
//			if(Integer.parseInt(nota.getNota())>maiorNumero){
//				maiorNumero = Integer.parseInt(nota.getNota());
//			}
//		}
//		return maiorNumero;
//	}
//
	private static void validarNF(Nota nota){
		new Validador().validador(nota,config);
	}
	
	private static Map<String, Nota> getMap(Collection<Nota> notas) {
		return notas.stream().collect(toMap(Nota::getNota,n->n));
	}
	
}
