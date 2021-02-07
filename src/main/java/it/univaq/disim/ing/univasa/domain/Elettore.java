package it.univaq.disim.ing.univasa.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Elettore extends Utente {

	private String matricola;
	
	private Set<Evento> evento = new HashSet<>();

	public Elettore() {
		super();
	}

	public Elettore(Long id, String nome, String cognome, String email, String username, String password,
			String telefono, LocalDate data_nascita, Professione professione, String nome_università,
			String dipartimento, String matricola, boolean certificato, Set<Evento> evento) {
		super(id, nome, cognome, email, username, password, telefono, data_nascita, professione, nome_università,
				dipartimento);
		this.matricola = matricola;
		this.evento = evento;
	}

	public String getMatricola() {
		return matricola;
	}

	public Set<Evento> getEvento() {
		return evento;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

	public void setEvento(Set<Evento> evento) {
		this.evento = evento;
	}

	@Override
	public String toString() {
		return "Elettore [matricola=" + matricola + ", evento=" + evento + ", getId()=" + getId() + ", getNome()="
				+ getNome() + ", getCognome()=" + getCognome() + ", getEmail()=" + getEmail() + ", getUsername()="
				+ getUsername() + ", getPassword()=" + getPassword() + ", getTelefono()=" + getTelefono()
				+ ", getData_nascita()=" + getData_nascita() + ", getProfessione()=" + getProfessione()
				+ ", getNome_università()=" + getNome_università() + ", getDipartimento()=" + getDipartimento()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}

}
