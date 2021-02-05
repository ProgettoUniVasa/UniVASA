package it.univaq.disim.ing.univasa.domain;

import java.util.HashSet;
import java.util.Set;

public class Candidato {

	private Long id;
	private String nome, cognome, email;
	private int votiRicevuti;
	private Evento evento;

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

	public int getVotiRicevuti() {
		return votiRicevuti;
	}

	public void setVotiRicevuti(int votiRicevuti) {
		this.votiRicevuti = votiRicevuti;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}
}
