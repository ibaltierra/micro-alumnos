package com.formaciondbi.microservicios.app.alumnos.model.mapper;

import org.modelmapper.ModelMapper;

import com.formaciondbi.microservicios.app.alumnos.model.to.AlumnoTO;
import com.formaciondbi.microservicios.commons.alumnos.model.entity.Alumno;

public class MapperAlumno {

	private MapperAlumno(){}
	private static ModelMapper mapper = new ModelMapper();
	
	public static AlumnoTO convertEntityToObjectTO(final Alumno alumno) {
		return mapper.map(alumno, AlumnoTO.class);
	}
	public static Alumno convertObjectTOToEntity(final AlumnoTO alumno) {
		return mapper.map(alumno, Alumno.class);
	}
}
