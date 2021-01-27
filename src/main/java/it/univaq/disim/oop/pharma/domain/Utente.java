package it.univaq.disim.oop.pharma.domain;

import java.time.LocalDate;

public class Utente extends Persona {
	private Integer id;
	private String password;
	private String username;

	public Utente() {
		super();
	}

	public Utente(String nome, String cognome, String telefono, String sesso, LocalDate dataNascita,
			String luogoNascita, String cf, String residenza, Integer id, String password, String username) {
		super(nome, cognome, telefono, sesso, dataNascita, luogoNascita, cf, residenza);
		this.id = id;
		this.password = password;
		this.username = username;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Utente [id=" + id + ", password=" + password + ", username=" + username + ", getId()=" + getId()
				+ ", getPassword()=" + getPassword() + ", getUsername()=" + getUsername() + ", getNome()=" + getNome()
				+ ", getCognome()=" + getCognome() + ", getTelefono()=" + getTelefono() + ", getSesso()=" + getSesso()
				+ ", getDataNascita()=" + getDataNascita() + ", getLuogoNascita()=" + getLuogoNascita() + ", getCf()="
				+ getCf() + ", getResidenza()=" + getResidenza() + "]";
	}

}
