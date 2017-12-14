package com.bokine.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bokine.util.CriadorDeLista;

public class NotasJaInutilizadasL04 {
	List<Integer> lista = new ArrayList<Integer>();
	
	public List<Integer> notasJaInutilizadas = new ArrayList<Integer>();
	
	public List<Integer> getLista(){
		
		notasJaInutilizadas.addAll(new CriadorDeLista(9865).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(9605).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(8894).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(1016).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(1018).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(616,617).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(588).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(85,86).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(17899).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(17658).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(17211).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(17053).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(17164,17165).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(17003).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(16985).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(16800).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(16923).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(16709).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(16500).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(16485).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(16425).getLista());
		
		Collections.sort(notasJaInutilizadas);
		return this.notasJaInutilizadas;
	
	}
}
