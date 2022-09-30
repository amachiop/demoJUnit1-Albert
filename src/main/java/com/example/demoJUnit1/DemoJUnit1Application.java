package com.example.demoJUnit1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demoJUnit1.model.Articulo;
import com.example.demoJUnit1.services.CarritoCompraServiceI;

@SpringBootApplication
public class DemoJUnit1Application implements CommandLineRunner {
	
	@Autowired
	private CarritoCompraServiceI carrito;

	public static void main(String[] args) {
		SpringApplication.run(DemoJUnit1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Articulo> lista = carrito.getArticulosBBDD();
		for(Articulo articulo : lista) {
			System.out.println(articulo.getNombre());
		}
	}

}
