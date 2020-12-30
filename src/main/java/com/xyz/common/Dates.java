package com.xyz.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Dates {

	public static LocalDate fromString(String fecha, String formato) {
		return LocalDate.parse(fecha, DateTimeFormatter.ofPattern(formato));
	}
	
	public static LocalDate firstDay(LocalDate fecha) {
		return fecha.withDayOfMonth(1);
	}
	
	public static LocalDate finalDay(LocalDate fecha) {
		return fecha.withDayOfMonth(fecha.lengthOfMonth());
	}
	
	public static long diffDays(LocalDate fechaIni, LocalDate fechaFin) {
		return ChronoUnit.DAYS.between(fechaIni, fechaFin);
	}
}
