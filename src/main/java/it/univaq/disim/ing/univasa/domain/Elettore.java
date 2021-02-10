package it.univaq.disim.ing.univasa.domain;

import java.time.LocalDate;

public class Elettore extends Utente {

	private String matricola;

	public Elettore() {
		super();
	}

	public Elettore(Long id, String nome, String cognome, String email, String username, String password,
			String telefono, LocalDate data_nascita, Professione professione, String nome_università,
			String dipartimento, String matricola) {
		super(id, nome, cognome, email, username, password, telefono, data_nascita, professione, nome_università,
				dipartimento);
		this.matricola = matricola;
	}

	public String getMatricola() {
		return matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

	@Override
	public String toString() {
		return "Elettore [matricola=" + matricola + ", getId()=" + getId() + ", getNome()=" + getNome()
				+ ", getCognome()=" + getCognome() + ", getEmail()=" + getEmail() + ", getUsername()=" + getUsername()
				+ ", getPassword()=" + getPassword() + ", getTelefono()=" + getTelefono() + ", getData_nascita()="
				+ getData_nascita() + ", getProfessione()=" + getProfessione() + ", getNome_università()="
				+ getNome_università() + ", getDipartimento()=" + getDipartimento() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
