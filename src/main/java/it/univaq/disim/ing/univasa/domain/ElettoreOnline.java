package it.univaq.disim.ing.univasa.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ElettoreOnline extends Elettore {
/*
	private Set<SchedaElettorale> schede_votate = new HashSet<>();
	private Stato stato = Stato.prenotazione_online;


	public ElettoreOnline() {
		super();
	}

	public ElettoreOnline(Set<SchedaElettorale> schede_votate, Stato stato) {
		super();
		this.schede_votate = schede_votate;
		this.stato = stato;
	}

	public ElettoreOnline(Long id, String nome, String cognome, String email, String username, String password,
			String telefono, LocalDate data_nascita, Professione professione, String nome_università, String dipartimento,
			String matricola, boolean certificato, Set<Evento> evento) {
		super(id, nome, cognome, email, username, password, telefono, data_nascita, professione, nome_università,
				dipartimento, matricola, certificato, evento);
	}

	public Set<SchedaElettorale> getSchede_votate() {
		return schede_votate;
	}

	public void setSchede_votate(Set<SchedaElettorale> schede_votate) {
		this.schede_votate = schede_votate;
	}

	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		this.stato = stato;
	}
	
	
	// Operations

	/*
	 * public operation() { //TODO }
	 * 
	 * public vota() { //TODO } public riconoscimentoOnline() { //TODO }
	 */
}
