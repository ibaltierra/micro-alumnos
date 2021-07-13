package com.formaciondbi.microservicios.app.alumnos.model.to;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlumnoTO {

	private Long id;
	@NotEmpty
	@Size(min=3, max = 20)
	private String nombre;
	@NotEmpty
	@Size(min=3, max = 20)
	private String apellido;
	@NotEmpty
	@Email
	@Size(min=5)
	private String email;	
	private byte[] foto;
	private Date createAt;
}
