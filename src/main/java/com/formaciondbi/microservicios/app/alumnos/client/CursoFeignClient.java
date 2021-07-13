package com.formaciondbi.microservicios.app.alumnos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-cursos")
public interface CursoFeignClient {

	/**
	 * EndPoint que elimina el curso alumno por el id del alumno.
	 * @param id
	 */
	@DeleteMapping("/eliminar-alumno/{id}")
    public void eliminarCursoAlumnoPorId(@PathVariable final Long id);
}
