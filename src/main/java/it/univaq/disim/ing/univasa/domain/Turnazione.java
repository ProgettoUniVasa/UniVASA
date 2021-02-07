package it.univaq.disim.ing.univasa.domain;

import java.time.LocalDate;

public class Turnazione {

	private Long id;
	private Evento evento;
	private Operatore operatore;
	private TipologiaTurno fascia;
	private LocalDate data_turno;


	public Turnazione(Long id, Evento evento, Operatore operatore, TipologiaTurno fascia, LocalDate data_turno) {
		super();
		this.id = id;
		this.evento = evento;
		this.operatore = operatore;
		this.fascia = fascia;
		this.data_turno = data_turno;
	}

	public Turnazione() {
		super();
	}

	public Long getId() {
		return id;
	}

	public TipologiaTurno getFascia() {
		return fascia;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFascia(TipologiaTurno fascia) {
		this.fascia = fascia;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public LocalDate getData_turno() {
		return data_turno;
	}

	public void setData_turno(LocalDate data_turno) {
		this.data_turno = data_turno;
	}

	public Operatore getOperatore() {
		return operatore;
	}

	public void setOperatore(Operatore operatore) {
		this.operatore = operatore;
	}
	
	@Override
	public String toString() {
		return "Turnazione [id=" + id + ", evento=" + evento + ", operatore=" + operatore + ", fascia=" + fascia
				+ ", data_turno=" + data_turno + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}


}
