package com.bokine.util;

import java.util.Comparator;

import com.bokine.modelo.Nota;

public class Comparador implements Comparator<Nota>{


	@Override
	public int compare(Nota o1, Nota o2) {
		Integer p1,p2;
		p1=Integer.parseInt(o1.getNota());
		p2=Integer.parseInt(o2.getNota());
		return  p1.compareTo(p2);
	}

}
