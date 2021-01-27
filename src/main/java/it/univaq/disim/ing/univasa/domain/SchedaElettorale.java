package it.univaq.disim.ing.univasa.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SchedaElettorale {

	private Long id;
	private Evento evento;
	private ElettoreOnline elettoreOnline;
	private Set<Candidato> listaCandidati = new HashSet<>();
	private Set<Candidato> preferenze = new HashSet<>();

	public SchedaElettorale() {
		super();
	}

	public SchedaElettorale(Long id, Evento evento, ElettoreOnline elettoreOnline, Set<Candidato> listaCandidati,
			Set<Candidato> preferenze) {
		super();
		this.id = id;
		this.evento = evento;
		this.elettoreOnline = elettoreOnline;
		this.listaCandidati = listaCandidati;
		this.preferenze = preferenze;
	}

	public Long getId() {
		return id;
	}

	public Evento getEvento() {
		return evento;
	}

	public ElettoreOnline getElettoreOnline() {
		return elettoreOnline;
	}

	public Set<Candidato> getListaCandidati() {
		return listaCandidati;
	}

	public Set<Candidato> getPreferenze() {
		return preferenze;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public void setElettoreOnline(ElettoreOnline elettoreOnline) {
		this.elettoreOnline = elettoreOnline;
	}

	public void setListaCandidati(Set<Candidato> listaCandidati) {
		this.listaCandidati = listaCandidati;
	}

	public void setPreferenze(Set<Candidato> preferenze) {
		this.preferenze = preferenze;
	}

	// Operations

	/*
	 * public visualizzaPrenotatiOnline() { //TODO }
	 */
}
