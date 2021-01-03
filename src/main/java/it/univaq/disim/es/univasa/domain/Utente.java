package main.java.it.univaq.disim.es.univasa.domain;

import java.time.LocalDate;

public class Utente {
	
	private Long id;
	private String nome;
	private String cognome;
	private String email;
	private String username;
	private String password;
	private String telefono;
	private LocalDate data_nascita;
	private enum professione {
		disoccupato,
		imprenditore,
		operaio,
		studente
	};
	private String nome_universita;
	private String dipartimento;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public LocalDate getData_nascita() {
		return data_nascita;
	}
	public void setData_nascita(LocalDate data_nascita) {
		this.data_nascita = data_nascita;
	}
	public String getNome_universita() {
		return nome_universita;
	}
	public void setNome_universita(String nome_universita) {
		this.nome_universita = nome_universita;
	}
	public String getDipartimento() {
		return dipartimento;
	}
	public void setDipartimento(String dipartimento) {
		this.dipartimento = dipartimento;
	}
	
}
