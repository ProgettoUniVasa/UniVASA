package it.univaq.disim.ing.univasa.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Candidato {

	private Long id;
	private String nome, cognome, email, telefono, nomeUniversita;
	private LocalDate dataNascita;

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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getNomeUniversita() {
		return nomeUniversita;
	}

	public void setNomeUniversita(String nomeUniversita) {
		this.nomeUniversita = nomeUniversita;
	}

	@Override
	public String toString() {
		return "Candidato [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", email=" + email
				+ ", votiRicevuti=" + votiRicevuti + ", evento=" + evento + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
}
