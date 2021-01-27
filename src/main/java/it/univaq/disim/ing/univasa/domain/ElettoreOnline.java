package it.univaq.disim.ing.univasa.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ElettoreOnline extends Elettore {

	private Set<SchedaElettorale> schede_votate = new HashSet<>();

	public ElettoreOnline() {
		super();
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
	// Operations

	/*
	 * public operation() { //TODO }
	 * 
	 * public vota() { //TODO } public riconoscimentoOnline() { //TODO }
	 */
}
