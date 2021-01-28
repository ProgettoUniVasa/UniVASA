package it.univaq.disim.ing.univasa.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ElettoreInSede extends Elettore {

	private Set<Evento> evento = new HashSet<>();
	private Stato stato = Stato.prenotazione_in_sede;
	
	public ElettoreInSede() {
		super();
	}

	public ElettoreInSede(Set<Evento> evento, Stato stato) {
		super();
		this.evento = evento;
		this.stato = stato;
	}

	public ElettoreInSede(Long id, String nome, String cognome, String email, String username, String password,
			String telefono, LocalDate data_nascita, Professione professione, String nome_università,
			String dipartimento, String matricola, boolean certificato, Set<Evento> evento) {
		super(id, nome, cognome, email, username, password, telefono, data_nascita, professione, nome_università, dipartimento,
				matricola, certificato, evento);
	}

	public Set<Evento> getEvento() {
		return evento;
	}

	public void setEvento(Set<Evento> evento) {
		this.evento = evento;
	}

	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		this.stato = stato;
	}

}
