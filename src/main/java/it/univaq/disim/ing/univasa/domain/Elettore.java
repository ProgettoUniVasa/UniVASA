package it.univaq.disim.ing.univasa.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Elettore extends Utente {

	private String matricola;
	private boolean certificato;
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
		this.certificato = certificato;
		this.evento = evento;
	}

	public String getMatricola() {
		return matricola;
	}

	public boolean isCertificato() {
		return certificato;
	}

	public Set<Evento> getEvento() {
		return evento;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

	public void setCertificato(boolean certificato) {
		this.certificato = certificato;
	}

	public void setEvento(Set<Evento> evento) {
		this.evento = evento;
	}

	
	// Operations
	/*
	 * public candidatura() { //TODO }
	 * 
	 * public prenotazioneInSede() { //TODO }
	 * 
	 * public prenotazioneOnline() { //TODO }
	 */
}
