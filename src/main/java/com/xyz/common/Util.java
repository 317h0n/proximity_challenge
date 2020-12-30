package com.xyz.common;

public class Util {
	
	public static Double round(Double numero, Integer numeroDecimales) {
		return Math.round(numero * Math.pow(10, numeroDecimales)) / Math.pow(10, numeroDecimales);
	}
	
	public static int[] vuelto(Double valor, Double[] monedas) {
		int[] vuelto = new int[monedas.length];
		for (int j = 0; j < monedas.length; j++) {
			vuelto[j] = Double.valueOf(valor / monedas[j]).intValue();
			valor = round(valor % monedas[j], 2);
			if (valor == 0) break;
		}
		return vuelto;
	}
}
