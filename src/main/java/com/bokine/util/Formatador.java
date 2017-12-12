package com.bokine.util;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bokine.notas.App;

public interface Formatador {
	static final Logger logger = LogManager.getLogger(App.class.getName());

	public static void imprimir(List<Integer> lista){
		logger.debug("------- RESULTADO ------- ");
		int proximo = lista.get(0)+1;
		 int fim = lista.get(0);
		 int inicio = lista.get(0);
		 String intervalo = "";
		 for (Integer e : lista) {
			if(e == proximo){
				proximo++;
				fim=e;
			}else{
				if(e > proximo){
					intervalo = intervalo.concat(String.valueOf(fim)).concat("]");
					logger.debug(intervalo+" qtde:"+(proximo-1-inicio+1)+"\n");
					intervalo = "";
					fim=e;
					inicio=e;
					proximo=e+1;
				}if(inicio == e){
					intervalo = intervalo.concat("["+e.toString()+"-");
				}
			}
		}
		intervalo = intervalo.concat(fim+"]");
		 logger.debug(intervalo+" qtde:"+(proximo-1-inicio+1));
	}
}