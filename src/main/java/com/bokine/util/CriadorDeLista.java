package com.bokine.util;

import java.util.ArrayList;
import java.util.List;

public class CriadorDeLista {
	List<Integer> lista = new ArrayList<Integer>();
	
	public CriadorDeLista(int i) {
		for (int z = i; z <= i; z++) {
			lista.add(z);
		}
	}
	
	public CriadorDeLista(int i, int j) {
		for (int z = i; z <= j; z++) {
			lista.add(z);
		}
	}
	
	public List<Integer> getLista(){
		return this.lista;
	}

}
