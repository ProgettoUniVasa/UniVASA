package it.univaq.disim.ing.univasa.domain;

import java.time.LocalDate;

public class Turnazione {
	private Long id;
	private Enum fascia;
	private LocalDate data_evento;

	public Turnazione(Long id, Enum fascia, LocalDate data_evento) {
		super();
		this.id = id;
		this.fascia = fascia;
		this.data_evento = data_evento;
	}

	public Turnazione() {
		super();
	}

	public Long getId() {
		return id;
	}

	public Enum getFascia() {
		return fascia;
	}

	public LocalDate getData_evento() {
		return data_evento;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFascia(Enum fascia) {
		this.fascia = fascia;
	}

	public void setData_evento(LocalDate data_evento) {
		this.data_evento = data_evento;
	}

}