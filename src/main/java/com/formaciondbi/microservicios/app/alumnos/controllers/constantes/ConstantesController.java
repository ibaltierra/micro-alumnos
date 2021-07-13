package com.formaciondbi.microservicios.app.alumnos.controllers.constantes;

import org.springframework.beans.factory.annotation.Value;

public class ConstantesController {
	public static final String PUTON ="MI COMPA ES JOTO";
	public static final int N001 = 1;
	public static final int N002 = 2;
	public static final int N003 = 3;
	public static final int N000 = 0;
	
	@Value("${constantes.controller.compa}")
	private String unCompa;
	private ConstantesController() {}
	public String getUnCompa() {
		return unCompa;
	}
	public void setUnCompa(String unCompa) {
		this.unCompa = unCompa;
	}

	
	
}
