package com.example.demoJUnit1.services;

import java.util.List;

import com.example.demoJUnit1.model.Articulo;

public interface CarritoCompraServiceI{
	
	public void limpiarCesta();
	public void addArticulo(Articulo a);
	public int getNumArticulo();
	public List<Articulo> getArticulos(); 
	public Double totalPrice(); 
	public Double calculadorDescuento(double precio, double porcentajeDescuento);
	public List<Articulo> getArticulosBBDD();
	public Double aplicarDescuento(int idArticulo, double porcentajeDescuento);
	public Integer insertarArticuloEnBBDD(Articulo articulo);

}
