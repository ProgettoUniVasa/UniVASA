package it.univaq.disim.ing.univasa.domain;

import java.time.LocalDate;
import java.util.Set;

public class Amministratore extends Utente {

	public Amministratore(Long id, String nome, String cognome, String email, String username, String password,
			String telefono, LocalDate data_nascita, Professione professione, String nome_università,
			String dipartimento, Set<Evento> evento) {
		super(id, nome, cognome, email, username, password, telefono, data_nascita, professione, nome_università,
				dipartimento);
	}

	public Amministratore() {
		super();
	}

	@Override
	public String toString() {
		return "Amministratore [getId()=" + getId() + ", getNome()=" + getNome() + ", getCognome()=" + getCognome()
				+ ", getEmail()=" + getEmail() + ", getUsername()=" + getUsername() + ", getPassword()=" + getPassword()
				+ ", getTelefono()=" + getTelefono() + ", getData_nascita()=" + getData_nascita()
				+ ", getProfessione()=" + getProfessione() + ", getNome_università()=" + getNome_università()
				+ ", getDipartimento()=" + getDipartimento() + ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}
}
