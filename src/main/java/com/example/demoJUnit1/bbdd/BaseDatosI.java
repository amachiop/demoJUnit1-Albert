package com.example.demoJUnit1.bbdd;

import java.util.List;

import com.example.demoJUnit1.model.Articulo;

public interface BaseDatosI {
	
	public void iniciarBbdd();
	
	public List<Articulo> getArticulos();
	public Articulo findArticuloById(int idArticulo);
	public Integer insertarArticulo(Articulo artiulo);

}
