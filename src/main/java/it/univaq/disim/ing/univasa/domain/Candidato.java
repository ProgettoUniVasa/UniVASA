package it.univaq.disim.ing.univasa.domain;

import java.util.HashSet;
import java.util.Set;

public class Candidato extends Elettore {

	private int voti_ricevuti;
	private Set<SchedaElettorale> schedaElettorale = new HashSet<>();
	private Set<Evento> evento_candidato = new HashSet<>();

	public Candidato(int voti_ricevuti, Set<SchedaElettorale> schedaElettorale, Set<Evento> evento_candidato) {
		super();
		this.voti_ricevuti = voti_ricevuti;
		this.schedaElettorale = schedaElettorale;
		this.evento_candidato = evento_candidato;
	}

	public Candidato() {
		super();
	}

	private int getVoti_ricevuti() {
		return this.voti_ricevuti;
	}

	private void setVoti_ricevuti(int voti_ricevuti) {
		this.voti_ricevuti = voti_ricevuti;
	}

	public Set<SchedaElettorale> getSchedaElettorale() {
		return this.schedaElettorale;
	}

	public void setSchedaElettorale(Set<SchedaElettorale> schedaElettorale) {
		this.schedaElettorale = schedaElettorale;
	}

	public Set<Evento> getEvento_Candidato() {
		return this.evento_candidato;
	}

	public void setEvento_Candidato(Set<Evento> evento) {
		this.evento_candidato = evento;
	}

}
