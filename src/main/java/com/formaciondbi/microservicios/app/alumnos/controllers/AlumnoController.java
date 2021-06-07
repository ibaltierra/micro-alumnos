package com.formaciondbi.microservicios.app.alumnos.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.formaciondbi.microservicios.app.alumnos.controllers.constantes.ConstantesController;
import com.formaciondbi.microservicios.app.alumnos.error.AlumnoError;
import com.formaciondbi.microservicios.app.alumnos.servces.IAlumnoService;
import com.formaciondbi.microservicios.commons.alumnos.model.entity.Alumno;
import com.formaciondbi.microservicios.commons.controllers.CommonController;

/*
 * El api gateway enruta de forma dinamica el acceso a cada microservicio, como unico punto de entrada
 * , a travez de filtros y usa balanceo de carga en automatico, seguriza las rutas con spring security.
 * cada 30 segundos los microservicios se comunican con EUREKA para indicar que esta activo, 
 * si se baja una instancia en 90 segudos EUREKA lo elimina, en 90 segundos EUREKA lo registra cuando se levanta
 * el microservicio.
 *
 * 
 * ICommonService se reemplaza por el IAlumnoService y se quita el tipo,
 * ya que este hereda de ICommonService.
 */
@RestController
public class AlumnoController extends CommonController<Alumno, IAlumnoService >{
		/**
	 * Método que busca los alumnos de un curso por los id's de alumnos del microservicio
	 * cursos.
	 * @param ids
	 * @return
	 */
	@GetMapping("/alumnos-por-curso")
	public ResponseEntity<?> buscarAlumnosPorCurso(@RequestParam List<Long> ids){	
		return ResponseEntity.ok(this.service.findAllById(ids));
	}
	/**
	 * Método que realiza la busqueda de la imagen del alumno por su id.
	 * @param id
	 * @return
	 */
	@GetMapping("/uploads/img/{id}")
	public ResponseEntity<?> verFoto(@PathVariable final Long id){
		final Optional<Alumno> tmp = this.service.findById(id);
		if(!tmp.isPresent() || tmp.get().getFoto()==null) {
			return ResponseEntity.notFound().build();		
		}
		final Resource imagen =new  ByteArrayResource(tmp.get().getFoto());
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
	}
	/**
	 * La variable "service" es atributo del padre controller generico.
	 * @param alumno
	 * @param lId
	 * @return
	 * @throws AlumnoError 
	 */
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody final Alumno alumno, BindingResult result, @PathVariable("id")final Long lId) throws AlumnoError{
		/*if(result.hasErrors()) {
			return validar(result);
		}*/
		if(alumno.getNombre().isEmpty()) {
			throw new AlumnoError("El nombre esta vacio!!");
		}
		
		final Optional<Alumno> tmp = this.service.findById(lId);
		if(tmp.isPresent()) {
			final Alumno alumnoDb = tmp.get();
			alumnoDb.setNombre(alumno.getNombre());
			alumnoDb.setApellido(alumno.getApellido());
			alumnoDb.setEmail(alumno.getEmail());
			return ResponseEntity.status(HttpStatus.CREATED).body(this.service.guardar(alumnoDb));
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	/**
	 * Método que realiza la busqueda del alumno por el nombre o el apellido.
	 * @param strTermino
	 * @return
	 */
	@GetMapping("/filtrar/{termino}")
	public ResponseEntity<?> buscarAlumnos(@PathVariable("termino") final String strTermino){
		final List<Alumno> lstAlumnos = this.service.findByNombreOrApellido(strTermino);
		if(lstAlumnos.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(lstAlumnos);
		}						
	}
	/**
	 * Método que realiza el guardado del alumno con la foto, este no requiere @RequesBody por que es una petición con 
	 * multipart.
	 * @param alumno
	 * @param result
	 * @param archivo
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/guardar-con-foto")
	public ResponseEntity<?> guardarConFoto(@Valid @RequestBody Alumno alumno, BindingResult result, @RequestParam MultipartFile archivo) throws IOException {
		if(!archivo.isEmpty()) {
			alumno.setFoto(archivo.getBytes());
		}
		System.out.println(alumno.getApellido());
		return super.guardar(alumno, result);
	}
	/**
	 * Para este metodo y el de guardar no va el @RequestBody, ya que se envia un miltipart para el archivo, es otro tipo de requeest.
	 * Se envia un forma date para campo para cada atributo.
	 * @param alumno
	 * @param result
	 * @param lId
	 * @param archivo
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/editar-con-foto/{id}")
	public ResponseEntity<?> editarConFoto(@Valid @RequestBody final Alumno alumno, BindingResult result, @PathVariable("id")final Long lId
			, @RequestParam MultipartFile archivo) throws IOException{
		if(result.hasErrors()) {
			return validar(result);
		}
		final Optional<Alumno> tmp = this.service.findById(lId);
		if(tmp.isPresent()) {
			final Alumno alumnoDb = tmp.get();
			alumnoDb.setNombre(alumno.getNombre());
			alumnoDb.setApellido(alumno.getApellido());
			alumnoDb.setEmail(alumno.getEmail());
			if(!archivo.isEmpty()) {
				alumnoDb.setFoto(archivo.getBytes());
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(this.service.guardar(alumnoDb));
		}else {
			return ResponseEntity.notFound().build();
		}
	}
}
