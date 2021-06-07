package com.formaciondbi.microservicios.app.alumnos.error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {
	private Integer intId;
	private String strTitulo;
	
	public Response(@JsonProperty("intId") Integer intId, @JsonProperty("strTitulo")String strTitulo) {
		super();
		this.intId = intId;
		this.strTitulo = strTitulo;
	}
	public Response() {}
	public Integer getIntId() {
		return intId;
	}
	public void setIntId(Integer intId) {
		this.intId = intId;
	}
	public String getStrTitulo() {
		return strTitulo;
	}
	public void setStrTitulo(String strTitulo) {
		this.strTitulo = strTitulo;
	}
}
