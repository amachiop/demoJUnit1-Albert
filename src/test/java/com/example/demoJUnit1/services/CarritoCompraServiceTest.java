package com.example.demoJUnit1.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CustomMatcher;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demoJUnit1.bbdd.BaseDatosI;
import com.example.demoJUnit1.model.Articulo;

@TestMethodOrder(MethodOrderer.MethodName.class)
@ExtendWith(MockitoExtension.class)
class CarritoCompraServiceTest {
	
	@InjectMocks
	private CarritoCompraServiceImpl carritoCompra  = new CarritoCompraServiceImpl();
	
	@Mock
	private BaseDatosI baseDatos;
	
	/*@BeforeEach
	void inicializarCarrito() {
		carritoCompra = new CarritoCompraServiceImpl();
	}*/

	@Test
	void addArticuloTest() {
		Articulo articulo = new Articulo("Ambientador", 9.99);
		carritoCompra.addArticulo(articulo);
		assertEquals(articulo, carritoCompra.getArticulos().get(0));
	}
	
	@Test
	void getArticulosTest() {
		carritoCompra.addArticulo(new Articulo("Pantalon", 20D));
		carritoCompra.addArticulo(new Articulo("Ambientador", 15D));
		List<Articulo> listado = carritoCompra.getArticulos();
		
		Matcher<Articulo> listMatcher = new CustomMatcher<Articulo>("listMacher") {

			@Override
			public boolean matches(Object argument) {
				if(argument == null || !List.class.isInstance(argument)) {
					return false;
				}
				boolean equals = true;
				List<Articulo> lista = (List<Articulo>) argument;
				equals &= lista.get(0).getNombre().equals("Pantalon");
				equals &= lista.get(0).getPrecio().equals(20D);
				equals &= lista.get(1).getNombre().equals("Ambientador");
				
				return equals;
			}
			
		};
		//assertThat(listado, listMatcher);
		assertEquals(2, listado.size());
		
	}
	
	@Test
	void limpiarCestaTest() {
		Articulo articulo = new Articulo("Ambientador", 9.99);
		Articulo articulo2 = new Articulo("Papel", 9.99);
		Articulo articulo3 = new Articulo("Madera", 9.99);
		carritoCompra.addArticulo(articulo);
		carritoCompra.addArticulo(articulo2);
		carritoCompra.addArticulo(articulo3);
		carritoCompra.limpiarCesta();
		assertEquals(0, carritoCompra.getNumArticulo());
	}
	
	@Test
	void totalPriceTest() {
		Articulo articulo = new Articulo("Ambientador", 9.99);
		Articulo articulo2 = new Articulo("Papel", 9.99);
		Articulo articulo3 = new Articulo("Madera", 9.99);
		carritoCompra.addArticulo(articulo);
		carritoCompra.addArticulo(articulo2);
		carritoCompra.addArticulo(articulo3);
		assertEquals(29.97, carritoCompra.totalPrice());
	}
	
	@Test
	void calculadorDescuentoTest() {
		assertEquals(8, carritoCompra.calculadorDescuento(10, 0.2));
	}
	
	@Test
	void getArticulosBBDDTest() {
		List<Articulo> lista = new ArrayList<>();
		lista.add(new Articulo("Pantalon", 20D));
		lista.add(new Articulo("Camisa", 30D));
		lista.add(new Articulo("Jersey", 40D));
		lista.add(new Articulo("Vestido", 50D));
		Mockito.when(baseDatos.getArticulos()).thenReturn(lista);
		List<Articulo> listado = carritoCompra.getArticulosBBDD();
		assertEquals(4, listado.size());
	}
	
	@Test
	void aplicarDescuentoTest() {
		Double descuento = 0.2;
		Articulo articuloSimulado = new Articulo("Pantalon", 20D);
		Mockito.when(baseDatos.findArticuloById(Mockito.anyInt())).thenReturn(articuloSimulado);
		Double supuestoPrecio = carritoCompra.calculadorDescuento(articuloSimulado.getPrecio(), descuento);
		assertEquals(supuestoPrecio, carritoCompra.aplicarDescuento(1, descuento));
		verify(baseDatos).findArticuloById(Mockito.anyInt());
	}
	
	@Test
	void insertarArticuloEnBBDDTest() {
		Articulo articuloInsertado = new Articulo("Vaqueros", 15D);
		Mockito.when(baseDatos.insertarArticulo(articuloInsertado)).thenReturn(1);
		Integer idArticuloInsertado = carritoCompra.insertarArticuloEnBBDD(articuloInsertado);
		
		/*
		 * Comprobamos que el id del articulo insertado sea igual que el tamaño de la lista de articulos, ya que si el tamaño
		 * es 1, hay un articulo con id 1
		 */
		assertEquals(idArticuloInsertado, carritoCompra.getArticulos().size());
		/*
		 * Comprobamos que el articulo insertado coincida con el primer elemento de la lista, como el id sera 1, hacemos 1-1, y asi
		 * hacemos conseguimos que se haga un get(0)
		 */
		assertEquals(articuloInsertado, carritoCompra.getArticulos().get(idArticuloInsertado-1));
		/*
		 * Verificamos que por lo menos se haya ejecutado 1 vez el metodo de insertarArticulo
		 */
		verify(baseDatos, atLeast(1)).insertarArticulo(articuloInsertado);
	}

}
