package com.bokine.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bokine.util.CriadorDeLista;

public class NotasJaInutilizadasL06 {
	List<Integer> lista = new ArrayList<Integer>();
	
	public List<Integer> notasJaInutilizadas = new ArrayList<Integer>();
	
	public List<Integer> getLista(){
		
		notasJaInutilizadas.addAll(new CriadorDeLista(0).getLista());
		
		
		Collections.sort(notasJaInutilizadas);
		return this.notasJaInutilizadas;
	
	}
}
