package com.formaciondbi.microservicios.app.alumnos.servces;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.formaciondbi.microservicios.app.alumnos.client.CursoFeignClient;
import com.formaciondbi.microservicios.app.alumnos.model.repository.AlumnoRepositoty;
import com.formaciondbi.microservicios.commons.alumnos.model.entity.Alumno;

@RunWith(SpringJUnit4ClassRunner.class)
public class AlumnoServiceTest {

	@InjectMocks
	private AlumnoServiceImpl alumnoService;
	@MockBean
	private AlumnoRepositoty repository;
	@MockBean
	private CursoFeignClient cursoFaign;
	
	private List<Alumno> lstAlumnosReturn;
	final List<Long> lstIds = new ArrayList<>();
	private Alumno alumnoReturn;
	private final static Long ID_DELETE = 1L;
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		lstAlumnosReturn = new ArrayList<>();
		alumnoReturn = new Alumno();
		lstAlumnosReturn.add(alumnoReturn);
		alumnoReturn = new Alumno();
		lstAlumnosReturn.add(alumnoReturn);
		when(repository.findByNombreOrApellido(Mockito.anyString())).thenReturn(lstAlumnosReturn);
		lstIds.add(1l);
		lstIds.add(2l);
		lstIds.add(3l);
	}
	@Test
	public void findByNombreOrApellidoTest() {
		final List<Alumno> lstOutCome = this.alumnoService.findByNombreOrApellido(Mockito.anyString());
		assertNotNull(lstOutCome);
		assertEquals(lstOutCome.size(), 2);
		assertFalse(lstOutCome.isEmpty());
	}	
	@Test
	public void findAllByIdTest() {	
		Mockito.when(repository.findAllById(lstIds)).thenReturn(lstAlumnosReturn);
		
		final List<Alumno> lstOutCome = this.alumnoService.findAllById(lstIds);
		//assertNotNull(lstOutCome);
		//assertEquals(lstOutCome.size(), 0);
		//assertFalse(!lstOutCome.isEmpty());
	}
	@Test
	public void eliminarCursoAlumnoPorIdTest() {
		this.alumnoService.eliminarCursoAlumnoPorId(ID_DELETE);
	}
	@Test
	public void deleteByIdTest() {
		this.alumnoService.deleteById(ID_DELETE);
	}
}
