package domain;

import java.util.ArrayList;
import java.util.List;

public class Votazione {

	private Long id;
	private Elettore elettore;
	private Evento evento;
	private List<Elettore> preferenzeEspresse = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Elettore getElettore() {
		return elettore;
	}

	public void setElettore(Elettore elettore) {
		this.elettore = elettore;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public List<Elettore> getPreferenzeEspresse() {
		return preferenzeEspresse;
	}

	public void setPreferenzeEspresse(List<Elettore> preferenzeEspresse) {
		this.preferenzeEspresse = preferenzeEspresse;
	}
	
	
}
