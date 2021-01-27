package it.univaq.disim.oop.pharma.domain;

import java.time.LocalDate;

public class Farmacista extends Utente {

	public Farmacista() {
		super();
	}

	public Farmacista(String nome, String cognome, String telefono, String sesso, LocalDate dataNascita,
			String luogoNascita, String cf, String residenza, Integer id, String password, String username) {
		super(nome, cognome, telefono, sesso, dataNascita, luogoNascita, cf, residenza, id, password, username);
	}
}