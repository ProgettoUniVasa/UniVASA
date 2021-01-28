package it.univaq.disim.ing.univasa.domain;

import java.time.LocalDate;

public class Turnazione {

	// Questa association???? Ci andrebbe Operatore, ma a che mi serve se l'Operatore ha un set dei suoi turni?
	private Long id;
	private Evento evento;
	private Operatore operatore;
	private TipologiaTurno fascia;
	private LocalDate data_turno;

	public Turnazione(Long id, TipologiaTurno fascia, Evento evento) {
		super();
		this.id = id;
		this.fascia = fascia;
		this.evento = evento;
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

}
