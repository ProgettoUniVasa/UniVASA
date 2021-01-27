package it.univaq.disim.oop.pharma.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Medico extends Utente {
	private String codiceAlboMedici;
	private TipologiaMedico tipologia;

	private Set<Prescrizione> prescrizioni = new HashSet<>();

	public Medico() {
		super();
	}

	public Medico(String nome, String cognome, String telefono, String sesso, LocalDate dataNascita,
			String luogoNascita, String cf, String residenza, Integer id, String password, String username,
			String codiceAlboMedici, Set<Prescrizione> prescrizioni, TipologiaMedico tipologia) {
		super(nome, cognome, telefono, sesso, dataNascita, luogoNascita, cf, residenza, id, password, username);
		this.codiceAlboMedici = codiceAlboMedici;
		this.tipologia = tipologia;
		this.prescrizioni = prescrizioni;
	}

	public String getCodiceAlboMedici() {
		return codiceAlboMedici;
	}

	public void setCodiceAlboMedici(String codiceAlboMedici) {
		this.codiceAlboMedici = codiceAlboMedici;
	}

	public TipologiaMedico getTipologia() {
		return tipologia;
	}

	public void setTipologia(TipologiaMedico tipologia) {
		this.tipologia = tipologia;
	}

	public Set<Prescrizione> getPrescrizioni() {
		return prescrizioni;
	}

	public void setPrescrizioni(Set<Prescrizione> prescrizioni) {
		this.prescrizioni = prescrizioni;
	}

	@Override
	public String toString() {
		return "Medico [codiceAlboMedici=" + codiceAlboMedici + ", prescrizioni=" + prescrizioni + ", tipologia="
				+ tipologia + ", Nome=" + getNome() + ", Cognome=" + getCognome() + "]";
	}

}
