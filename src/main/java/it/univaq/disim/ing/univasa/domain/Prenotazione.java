package it.univaq.disim.ing.univasa.domain;

import java.sql.Blob;

public class Prenotazione {

	private Long id;

	private Evento evento;

	private Elettore elettore;

	private TipoPrenotazione tipoPrenotazione = TipoPrenotazione.in_presenza;

	private Stato stato = Stato.no; // votazione_effettuata

	private Blob Certificato; 

	public Prenotazione() {
	}
	
	public Prenotazione(Evento evento, Elettore elettore, TipoPrenotazione tipoPrenotazione) {
		super();
		this.evento = evento;
		this.elettore = elettore;
		this.tipoPrenotazione = tipoPrenotazione;
	}

	public Prenotazione(Evento evento, Elettore elettore, TipoPrenotazione tipoPrenotazione, Stato stato,
			Blob certificato) {
		super();
		this.evento = evento;
		this.elettore = elettore;
		this.tipoPrenotazione = tipoPrenotazione;
		this.stato = stato;
		Certificato = certificato;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Elettore getElettore() {
		return elettore;
	}

	public void setElettore(Elettore elettore) {
		this.elettore = elettore;
	}

	public TipoPrenotazione getTipoPrenotazione() {
		return tipoPrenotazione;
	}

	public void setTipoPrenotazione(TipoPrenotazione tipoPrenotazione) {
		this.tipoPrenotazione = tipoPrenotazione;
	}

	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		this.stato = stato;
	}

	public Blob getCertificato() {
		return Certificato;
	}

	public void setCertificato(Blob certificato) {
		Certificato = certificato;
	}

}
