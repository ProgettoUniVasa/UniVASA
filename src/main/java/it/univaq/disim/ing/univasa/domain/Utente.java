package it.univaq.disim.ing.univasa.domain;

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

	private Professione professione;

	private String nome_università;

	private String dipartimento;

	public Utente(Long id, String nome, String cognome, String email, String username, String password, String telefono,
			LocalDate data_nascita, Professione professione, String nome_università, String dipartimento) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.username = username;
		this.password = password;
		this.telefono = telefono;
		this.data_nascita = data_nascita;
		this.professione = professione;
		this.nome_università = nome_università;
		this.dipartimento = dipartimento;
	}

	public Utente() {
		super();
	}

	// Operations

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getTelefono() {
		return telefono;
	}

	public LocalDate getData_nascita() {
		return data_nascita;
	}

	public Professione getProfessione() {
		return professione;
	}

	public String getNome_università() {
		return nome_università;
	}

	public String getDipartimento() {
		return dipartimento;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setData_nascita(LocalDate data_nascita) {
		this.data_nascita = data_nascita;
	}

	public void setProfessione(Professione professione) {
		this.professione = professione;
	}

	public void setNome_università(String nome_università) {
		this.nome_università = nome_università;
	}

	public void setDipartimento(String dipartimento) {
		this.dipartimento = dipartimento;
	}

	@Override
	public String toString() {
		return "Utente [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", email=" + email + ", username="
				+ username + ", password=" + password + ", telefono=" + telefono + ", data_nascita=" + data_nascita
				+ ", professione=" + professione + ", nome_università=" + nome_università + ", dipartimento="
				+ dipartimento + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	// operations
	/*
	 * public logIn() { //TODO }
	 * 
	 * public logOut() { //TODO }
	 * 
	 * public signUp() { //TODO }
	 */

}
