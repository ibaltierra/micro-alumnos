package com.formaciondbi.microservicios.app.alumnos.controllers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.NestedServletException;

import java.beans.PropertyEditor;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.formaciondbi.microservicios.app.alumnos.controllers.constantes.ConstantesController;
import com.formaciondbi.microservicios.app.alumnos.error.AlumnoError;
import com.formaciondbi.microservicios.app.alumnos.servces.IAlumnoService;
import com.formaciondbi.microservicios.commons.alumnos.model.entity.Alumno;


@RunWith(SpringRunner.class)
public class AlumnoControllerTest {
	
			
	@Mock
	private IAlumnoService alumnoService;
	@InjectMocks
	private AlumnoController alumnoController;
	private static Alumno ALUMNOB = new Alumno();
	private static Alumno ALUMNO_IVAN = new Alumno();
	private static final Alumno ALUMNO = new Alumno();
	private final List<Alumno> lstAlumnos = new ArrayList<>();
	private ObjectMapper mapper = new ObjectMapper();
	
	private MockMvc mockMvc;
	@After
	public void finich() {
		
	}
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(alumnoController)
				.addPlaceholderValue("metodo", "/filtrar/{termino}")				
				//.addPlaceholderValue("metodo2", "/{id}")
				.build();
		ALUMNOB.setNombre("Ivan");
		ALUMNOB.setApellido("Baltierra");
		ALUMNOB.setEmail("ivan.baltierra");
		ALUMNOB.setId(1L);
		ALUMNOB.setCreateAt(new Date());
		this.lstAlumnos.add(ALUMNOB);
		
		ALUMNO.setApellido("Baltierra");
		ALUMNO.setCreateAt(new Date());
		ALUMNO.setEmail("ivan@gmail.com");
		ALUMNO.setNombre("Ivan");
		Mockito.when(alumnoService.findByNombreOrApellido("Ivan")).thenReturn(lstAlumnos);
	}
	@Test
	public void buscarAlumnosPorCursoTest() throws Exception {
		final List<Long> ids = new ArrayList<>();		
		ids.add(2L);
		ids.add(50L);
		final List<Alumno> lstA = new ArrayList<>();
		Alumno alumno = new Alumno();
		alumno.setId(1L);
		lstA.add(alumno);
		alumno = new Alumno();
		alumno.setId(2L);
		lstA.add(alumno);
		Mockito.when(alumnoService.findAllById(ids)).thenReturn(lstA);
		this.mockMvc.perform(
				MockMvcRequestBuilders.get("/alumnos-por-curso")
				.contentType(MediaType.APPLICATION_JSON)
				.param("ids", "1,50")
				//.content(mapper.writeValueAsString(ids))
				).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
	}
	@Test
	public void buscarAlumnosTest() throws Exception {
		Mockito.when(alumnoService.findByNombreOrApellido(Mockito.anyString())).thenReturn(this.lstAlumnos);
		this.mockMvc.perform(
				MockMvcRequestBuilders.get("/filtrar/{termino}","ivan")
				.contentType(MediaType.APPLICATION_JSON)
				).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
	}
	@Test
	public void buscarAlumnosNotFoundTest() throws Exception {
		Mockito.when(alumnoService.findByNombreOrApellido(Mockito.anyString())).thenReturn(new ArrayList<>());
			
		this.mockMvc.perform(
				MockMvcRequestBuilders.get("/filtrar/{termino}","ivan")
				.contentType(MediaType.APPLICATION_JSON)
				).andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(MockMvcResultHandlers.print());
	}
	@Test
	public void verFotoNotFoundTest() throws JsonProcessingException, Exception {
		Optional OPTINAL_ALUMNO = Optional.of(ALUMNOB);
		Mockito.when(this.alumnoService.findById(1l)).thenReturn(OPTINAL_ALUMNO);
		
		/*ResponseEntity alumno = alumnoController.verFoto(1L);
		assertEquals(alumno.getStatusCode(), HttpStatus.NOT_FOUND);*/
		this.mockMvc.perform(
				MockMvcRequestBuilders.get("/uploads/img/{id}" ,2)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(ALUMNOB))
				).andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(MockMvcResultHandlers.print());
	}
	@Test
	public void verFotoTest() throws JsonProcessingException, Exception {
		ALUMNO_IVAN.setNombre("IVAN");
		byte[] foto = new byte[2];
		foto[0]=12;
		foto[1]=12;
		ALUMNO_IVAN.setFoto(foto);
		Optional OPTINAL_ALUMNO = Optional.of(ALUMNO_IVAN);
		Mockito.when(this.alumnoService.findById(Mockito.anyLong())).thenReturn(OPTINAL_ALUMNO);
		
		this.mockMvc.perform(
				MockMvcRequestBuilders.get("/uploads/img/{id}", 2)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(ALUMNOB))
				
				).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test(expected = NestedServletException.class)
	public void editarBadRequestTest() throws Exception {
		Optional OPTINAL_ALUMNO = Optional.of(ALUMNOB);
		Mockito.when(alumnoService.findById( Mockito.anyLong() )).thenReturn(OPTINAL_ALUMNO);
		//System.out.println(alumnoController.editar(ALUMNOB, Mockito.any(), 2l));
		ALUMNOB.setNombre("");
		this.mockMvc.perform(
				MockMvcRequestBuilders.put("/{id}" ,2)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(ALUMNOB))
				).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
	}
	@Test
	public void editarTest() throws Exception {
		
		Optional OPTINAL_ALUMNO = Optional.of(ALUMNO);
		Mockito.when(alumnoService.findById( Mockito.anyLong() )).thenReturn(OPTINAL_ALUMNO);
		
		this.mockMvc.perform(
				MockMvcRequestBuilders.put("/{id}" ,2)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(ALUMNO))
				).andExpect(MockMvcResultMatchers.status().isCreated()).andDo(MockMvcResultHandlers.print());
	}
	@Test
	public void editarNotFoundTest() throws Exception {
		
		Optional OPTINAL_ALUMNO = Optional.empty();
		Mockito.when(alumnoService.findById( Mockito.anyLong() )).thenReturn(OPTINAL_ALUMNO);
		
		this.mockMvc.perform(
				MockMvcRequestBuilders.put("/{id}" ,2)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(ALUMNO))
				).andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(MockMvcResultHandlers.print());
	}
	@Test
	public void guardarConFotoTest() throws JsonProcessingException, Exception {
			
		Mockito.when(alumnoService.guardar(ALUMNO )).thenReturn(ALUMNO);
		
		MockMultipartFile file = new MockMultipartFile(
	        "archivo", 
	        "hello.txt", 
	        MediaType.TEXT_PLAIN_VALUE, 
	        "Hello, World!".getBytes()
	      );
		this.mockMvc.perform(MockMvcRequestBuilders.multipart("/guardar-con-foto").file(file)
				.contentType(MediaType.APPLICATION_JSON)
				.content( mapper.writeValueAsString(ALUMNO) ))
		.andExpect(MockMvcResultMatchers.status().isCreated()).andDo(MockMvcResultHandlers.print());
	}
	@Test
	public void guardarConFotoBadRequestTest() throws JsonProcessingException, Exception {			
		Mockito.when(alumnoService.guardar(ALUMNO )).thenReturn(ALUMNO);
		ALUMNO.setNombre(String.valueOf(""));
		MockMultipartFile file = new MockMultipartFile(
	        "archivo", 
	        "hello.txt", 
	        MediaType.TEXT_PLAIN_VALUE, 
	        "Hello, World!".getBytes()
	      );
		this.mockMvc.perform(MockMvcRequestBuilders.multipart("/guardar-con-foto").file(file)
				.contentType(MediaType.APPLICATION_JSON)
				.content( mapper.writeValueAsString(ALUMNO) ))
		.andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(MockMvcResultHandlers.print());
	}
	@Test
	public void editarConFotoTest() throws JsonProcessingException, Exception{		
		ALUMNO.setNombre("Ivan");
		ALUMNO.setId(1l);
		
		Optional OPTINAL_ALUMNO = Optional.of(ALUMNO);
		Mockito.when(alumnoService.findById( Mockito.anyLong() )).thenReturn(OPTINAL_ALUMNO);			
		
		MockMultipartFile file = new MockMultipartFile(
	        "archivo", 
	        "hello.txt", 
	        MediaType.TEXT_PLAIN_VALUE, 
	        "Hello, World!".getBytes()
	      );
		this.mockMvc.perform(MockMvcRequestBuilders.multipart("/editar-con-foto/{id}", 1).file(file)				
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(ALUMNO))
				)
		.andExpect(MockMvcResultMatchers.status().isCreated()).andDo(MockMvcResultHandlers.print());
	}
	@Test
	public void editarConFotoBadRequestTest() throws JsonProcessingException, Exception{
		ALUMNO.setId(1l);
		
		Optional OPTINAL_ALUMNO = Optional.of(ALUMNO);
		Mockito.when(alumnoService.findById( Mockito.anyLong() )).thenReturn(OPTINAL_ALUMNO);			
		ALUMNO.setNombre(String.valueOf(""));
		MockMultipartFile file = new MockMultipartFile(
	        "archivo", 
	        "hello.txt", 
	        MediaType.TEXT_PLAIN_VALUE, 
	        "Hello, World!".getBytes()
	      );
		this.mockMvc.perform(MockMvcRequestBuilders.multipart("/editar-con-foto/{id}", 1).file(file)				
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(ALUMNO))
				)
		.andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(MockMvcResultHandlers.print());
	}
	@Test
	public void editarConFotoNotFoundTest() throws JsonProcessingException, Exception{
		ALUMNO.setNombre("Ivan");
		Optional OPTINAL_ALUMNO = Optional.empty();
		Mockito.when(alumnoService.findById( Mockito.anyLong() )).thenReturn(OPTINAL_ALUMNO);	
		MockMultipartFile file = new MockMultipartFile(
	        "archivo", 
	        "hello.txt", 
	        MediaType.TEXT_PLAIN_VALUE, 
	        "Hello, World!".getBytes()
	      );
		this.mockMvc.perform(MockMvcRequestBuilders.multipart("/editar-con-foto/{id}", 1).file(file)				
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(ALUMNO))
				)
		.andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(MockMvcResultHandlers.print());
	}
}
