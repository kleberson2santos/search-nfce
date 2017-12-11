package com.bokine.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bokine.util.CriadorDeLista;

public class NotasJaInutilizadasL04 {
	List<Integer> lista = new ArrayList<Integer>();
	
	public List<Integer> notasJaInutilizadas = new ArrayList<Integer>();
	
	public List<Integer> getLista(){
		
		notasJaInutilizadas.addAll(new CriadorDeLista(13397,13895).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(12897,13396).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(12397,12896).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(11897,12396).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(11397,11896).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(10897,11396).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(10397,10896).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(9897,10396).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(8897,9396).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(9397,9896).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(8397,8896).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(7897,8396).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(7397,7896).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(6897,7396).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(6397,6896).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(5897,6396).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(5397,5896).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(4397,4896).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(4897,5396).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(3896,4396).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(3131).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(1529,1533).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(1426,1527).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(832,1319).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(466).getLista());
		notasJaInutilizadas.addAll(new CriadorDeLista(105).getLista());
		
		Collections.sort(notasJaInutilizadas);
		return this.notasJaInutilizadas;
	
	}
}
