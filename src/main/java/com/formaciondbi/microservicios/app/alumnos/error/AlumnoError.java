package com.formaciondbi.microservicios.app.alumnos.error;

public class AlumnoError extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlumnoError(String strError) {
		super(strError);
	}
}
