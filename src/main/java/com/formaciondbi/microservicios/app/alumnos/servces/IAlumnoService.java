package com.formaciondbi.microservicios.app.alumnos.servces;

import java.util.List;


import com.formaciondbi.microservicios.commons.alumnos.model.entity.Alumno;
import com.formaciondbi.microservicios.commons.services.ICommonService;

public interface IAlumnoService extends ICommonService<Alumno>{
	/**
	 * Método que realiza la busqueda de alumnos por el nombre y/o apellido.
	 * @param nombre
	 * @return
	 */
	public List<Alumno> findByNombreOrApellido(final String nombre);
	/**
	 * Método que busca alumnos por una lista de id's de alumnos.
	 */
	public List<Alumno> findAllById(List<Long> ids);
	/**
	 * Método que elimina un curso alumno por id del alumno del microservicio cursos.
	 * @param id
	 */
	public void eliminarCursoAlumnoPorId(final Long id);
}
