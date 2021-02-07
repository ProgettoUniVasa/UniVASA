package it.univaq.disim.ing.univasa.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Operatore extends Utente {

	private Set<Turnazione> turnazioni = new HashSet<>();
	
	public Operatore(Long id, String nome, String cognome, String email, String username, String password,
			String telefono, LocalDate data_nascita, Professione professione, String nome_università,
			String dipartimento) {
		super(id, nome, cognome, email, username, password, telefono, data_nascita, professione, nome_università,
				dipartimento);
	}

	public Operatore() {
		super();
	}

	public Set<Turnazione> getTurnazioni() {
		return turnazioni;
	}

	public void setTurnazioni(Set<Turnazione> turnazioni) {
		this.turnazioni = turnazioni;
	}

	@Override
	public String toString() {
		return "Operatore [turnazioni=" + turnazioni + ", getId()=" + getId() + ", getNome()="
				+ getNome() + ", getCognome()=" + getCognome() + ", getEmail()=" + getEmail() + ", getUsername()="
				+ getUsername() + ", getPassword()=" + getPassword() + ", getTelefono()=" + getTelefono()
				+ ", getData_nascita()=" + getData_nascita() + ", getProfessione()=" + getProfessione()
				+ ", getNome_università()=" + getNome_università() + ", getDipartimento()=" + getDipartimento()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}

}
