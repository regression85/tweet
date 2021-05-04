package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TweetEntity {


	@Id
	private Long id;
	
	public TweetEntity() {
		
		
	}

	public TweetEntity(Long id, String usuario, String texto, boolean validacion) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.texto = texto;
		this.validacion = validacion;
	}
	private String usuario;
	private String texto;
	private boolean validacion;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public boolean isValidacion() {
		return validacion;
	}
	public void setValidacion(boolean validacion) {
		this.validacion = validacion;
	}
	
}
