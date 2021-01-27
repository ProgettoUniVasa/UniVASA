package it.univaq.disim.oop.pharma.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Paziente extends Utente {

	private Set<Prescrizione> prescrizioni = new HashSet<>();
	private double saldo;

	public Paziente() {
		super();
	}

	public Paziente(String nome, String cognome, String telefono, String sesso, LocalDate dataNascita,
			String luogoNascita, String cf, String residenza, Integer id, String password, String username,
			Set<Prescrizione> prescrizioni, double saldo) {
		super(nome, cognome, telefono, sesso, dataNascita, luogoNascita, cf, residenza, id, password, username);
		this.prescrizioni = prescrizioni;
		this.saldo = saldo;
	}

	public Set<Prescrizione> getPrescrizioni() {
		return prescrizioni;
	}

	public void setPrescrizioni(Set<Prescrizione> prescrizioni) {
		this.prescrizioni = prescrizioni;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	@Override
	public String toString() {
		return "Paziente [prescrizioni=" + prescrizioni + ", saldo=" + saldo + ", getId()=" + getId()
				+ ", getPassword()=" + getPassword() + ", getUsername()=" + getUsername() + ", toString()="
				+ super.toString() + ", getNome()=" + getNome() + ", getCognome()=" + getCognome() + ", getTelefono()="
				+ getTelefono() + ", getSesso()=" + getSesso() + ", getDataNascita()=" + getDataNascita()
				+ ", getLuogoNascita()=" + getLuogoNascita() + ", getCf()=" + getCf() + ", getResidenza()="
				+ getResidenza() + "]";
	}

}
