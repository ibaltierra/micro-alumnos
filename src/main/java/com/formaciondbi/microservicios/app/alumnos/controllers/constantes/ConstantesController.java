package com.formaciondbi.microservicios.app.alumnos.controllers.constantes;

import org.springframework.beans.factory.annotation.Value;

public class ConstantesController {
	public static final String PUTON ="MI COMPA ES JOTO";
	
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
