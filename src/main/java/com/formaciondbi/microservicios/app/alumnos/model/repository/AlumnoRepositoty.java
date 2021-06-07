package com.formaciondbi.microservicios.app.alumnos.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.formaciondbi.microservicios.commons.alumnos.model.entity.Alumno;


/**
 * Repositorio del entity alumno.
 * @author balti
 *
 */
public interface AlumnoRepositoty extends PagingAndSortingRepository<Alumno, Long> {

	/**
	 * Manda el query no el nombre del método.
	 * @param nombre
	 * @param apellido
	 * @return
	 */
	@Query("select a from Alumno a where UPPER(a.nombre) like UPPER( CONCAT('%',?1,'%') ) "
			+ " or UPPER(a.apellido) like UPPER( CONCAT('%',?1,'%') )")
	public List<Alumno> findByNombreOrApellido(final String nombre);
	
}
