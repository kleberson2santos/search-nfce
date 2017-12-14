package com.bokine.notas;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.bokine.service.NotasJaInutilizadasL01;
import com.bokine.util.Comparador;
import com.bokine.util.Formatador;

import br.com.samuelweb.nfe.ConfiguracoesIniciaisNfe;

public class App 
{
	private static String database;
	private static String host;
	private static String user;
	private static String pass;
	public static Long maiorNumero=0L;
	
	public static Set<Nota> NotasParaTriar = new HashSet<Nota>();
	public static  Map<String, Nota> notasComProtocolo = new HashMap<String, Nota>();
	public static  Map<String, Nota> notasSemRecibo = new HashMap<String, Nota>();
	public static  Map<String, Nota> notasSemProtocolo = new HashMap<String, Nota>();
	public static  Map<String, Nota> todasNfce = new HashMap<String, Nota>();
	public static List<Integer> notasJaInutilizadas = new ArrayList<Integer>();
	public static List<Integer> notasNaoEncontradasNaBase = new ArrayList<Integer>();
	
	public static  Map<String, Nota> listaFinal = new HashMap<String, Nota>();
	
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
		
		//Nota danfe = new Nota("135520", "13171003351649000145650000001355201000000013", "", "", LocalDateTime.now());

		notasJaInutilizadas = new NotasJaInutilizadasL01().getLista();

		notasComProtocolo = firebirdDao.NfceComProtocolo();
		
		notasSemProtocolo = firebirdDao.NfceSemProtocolo();
		
		notasSemRecibo = firebirdDao.NfceSemRecibo();
		
		todasNfce = firebirdDao.todasNfce();
		
		logger.debug("NOTAS SEM SEM RECIBO");
		notasSemRecibo = removerLista(notasSemRecibo,notasComProtocolo);
		imprimirRangerNotas(notasSemRecibo);
		
		
		logger.debug("NOTAS SEM PROTOCOLO - JA INUTILIZADAS ");
		listaFinal = removerLista(todasNfce,notasComProtocolo);
		listaFinal = removerListaInteiros(listaFinal,notasJaInutilizadas);
		imprimirRangerNotas(listaFinal);
		
		logger.debug("NOTAS NAO REGISTRADAS ");
		notasNaoEncontradasNaBase = notasNaoRegistradas(todasNfce, maiorNumero);
		notasNaoEncontradasNaBase = removerListaInteirosDeInteiros(notasNaoEncontradasNaBase, notasJaInutilizadas);
		Formatador.imprimir(notasNaoEncontradasNaBase);
		
    }
	
	private static Map<String, Nota> removerLista(Map<String,Nota> notasaAplicar, Map<String, Nota> notasaRemover ) {
			Map<String, Nota> result2 = notasaAplicar.values().stream().filter(nota -> !notasaRemover.containsKey(nota.getNota()))
	 				.collect(Collectors.toMap(Nota::getNota, n -> n, // key = name, value = websites
							(oldValue, newValue) -> oldValue, // if same key, take the old key
							LinkedHashMap::new));
			return result2;
		}
	
	private static Map<String, Nota> removerListaInteiros(Map<String,Nota> notasaAplicar, List<Integer> notasaRemover ) {
		Map<String, Nota> result2 = notasaAplicar.values().stream().filter(nota -> !notasaRemover.contains(Integer.parseInt(nota.getNota())))
 				.collect(Collectors.toMap(Nota::getNota, n -> n, // key = name, value = websites
						(oldValue, newValue) -> oldValue, // if same key, take the old key
						LinkedHashMap::new));
		return result2;
	}
	
	private static List<Integer> removerListaInteirosDeInteiros(List<Integer> notasaAplicar, List<Integer> notasaRemover ) {
		List<Integer> result2 = notasaAplicar.stream().filter(i -> !notasaRemover.contains(i))
 				.collect(Collectors.toList());
		return result2;
	}
	
	private static  List<Integer> notasNaoRegistradas(Map<String,Nota> notas, Long maiorNota) {
		Collection<Integer> ranger = new ArrayList<>();
	 	for (int i = 1; i <= maiorNota; i++) {ranger.add(i);}
		Set<String> idsStrings = notas.entrySet().stream().map(u->u.getValue().getNota()).collect(toSet()); 
		List<Integer> idsInteger = ranger.stream().filter(id -> !idsStrings.contains(id.toString()))
		 				.collect(toList());
		return idsInteger;
	}
	
	private static void imprimirRangerNotas(Map<String,Nota> notas) {
		if(!notas.isEmpty()) {
			List<Integer> idsInteger = notas.entrySet().stream().mapToInt(u->Integer.parseInt(u.getValue().getNota())).collect(ArrayList::new, ArrayList::add,ArrayList::addAll); 
			Formatador.imprimir(idsInteger);
		}else {
			Formatador.imprimir(Arrays.asList(0));
		}
	}
	
}
