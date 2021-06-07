package com.formaciondbi.microservicios.app.alumnos.servces;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formaciondbi.microservicios.app.alumnos.client.CursoFeignClient;
import com.formaciondbi.microservicios.app.alumnos.model.repository.AlumnoRepositoty;
import com.formaciondbi.microservicios.commons.alumnos.model.entity.Alumno;
import com.formaciondbi.microservicios.commons.services.CommonServiceImpl;



/**
 * Esta clase es la que se registrara como un servicio.
 * De quien extiende es la reutilización del codigo.
 * @author balti
 *
 */
@Service
public class AlumnoServiceImpl extends CommonServiceImpl<Alumno, AlumnoRepositoty> implements IAlumnoService{
	@Autowired
	private CursoFeignClient cursoFaign;
	/**
	 * Método que busca alumnos por nombre y/o apellido.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Alumno> findByNombreOrApellido(final String nombre){
		return this.repo.findByNombreOrApellido(nombre);
	}
	/**
	 * Método que busca alumnos por una lista de id's de alumnos.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Alumno> findAllById(List<Long> ids){
		return (List<Alumno>) this.repo.findAllById(ids);
	}
	/**
	 * Método que elimina un curso alumno por id del alumno del microservicio cursos.
	 * @param id
	 */
	@Override
	public void eliminarCursoAlumnoPorId(final Long id) {
		cursoFaign.eliminarCursoAlumnoPorId(id);
	}
	@Override
	@Transactional
	public void deleteById(Long lId) {
		super.deleteById(lId);
		eliminarCursoAlumnoPorId(lId);
	}
}
