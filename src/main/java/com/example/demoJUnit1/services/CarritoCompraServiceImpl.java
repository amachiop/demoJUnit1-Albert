package com.example.demoJUnit1.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demoJUnit1.bbdd.BaseDatosI;
import com.example.demoJUnit1.model.Articulo;

@Service
public class CarritoCompraServiceImpl implements CarritoCompraServiceI{
	
	private List<Articulo> cesta = new ArrayList<Articulo>();
	
	@Autowired
	private BaseDatosI baseDatos;
	
	public CarritoCompraServiceImpl() {
		
	}

	@Override
	public void limpiarCesta() {
		cesta.clear();
	}

	@Override
	public void addArticulo(Articulo a) {
		cesta.add(a);
	}

	@Override
	public int getNumArticulo() {
		return cesta.size();
	}

	@Override
	public List<Articulo> getArticulos() {
		return cesta;
	}
	
	@Override
	public List<Articulo> getArticulosBBDD(){
		baseDatos.iniciarBbdd();
		return baseDatos.getArticulos();
	}
	
	

	@Override
	public Double totalPrice() {
		Double totalPrice = Double.valueOf(0);
		for(Articulo articulo : cesta) {
			totalPrice+=articulo.getPrecio();
		}
		return totalPrice;
	}

	@Override
	public Double calculadorDescuento(double precio, double porcentajeDescuento) {
		return precio-(precio*porcentajeDescuento);
	}

	@Override
	public Double aplicarDescuento(int idArticulo, double porcentajeDescuento) {
		Double descuentoCalculado = null;
		Articulo articuloEncontrado = baseDatos.findArticuloById(idArticulo);
		if(articuloEncontrado != null) {
			descuentoCalculado = calculadorDescuento(articuloEncontrado.getPrecio(), porcentajeDescuento);
		}
		return descuentoCalculado;
	}

	@Override
	public Integer insertarArticuloEnBBDD(Articulo articulo) {
		Integer idArticuloInsertado = baseDatos.insertarArticulo(articulo);
		cesta.add(articulo);
		return idArticuloInsertado;
	}
	
	
	
}
