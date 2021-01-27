package it.univaq.disim.oop.pharma.domain;

import java.time.LocalDate;

public class Persona {
	private String nome;
	private String cognome;
	private String telefono;
	private String sesso;
	private LocalDate dataNascita;
	private String luogoNascita;
	private String cf;
	private String residenza;

	public Persona() {
	}

	public Persona(String nome, String cognome, String telefono, String sesso, LocalDate dataNascita,
			String luogoNascita, String cf, String residenza) {
		this.nome = nome;
		this.cognome = cognome;
		this.telefono = telefono;
		this.sesso = sesso;
		this.dataNascita = dataNascita;
		this.luogoNascita = luogoNascita;
		this.cf = cf;
		this.residenza = residenza;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getLuogoNascita() {
		return luogoNascita;
	}

	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}

	public String getCf() {
		return cf.toUpperCase();
	}

	public void setCf(String cf) {
		this.cf = cf.toUpperCase();
	}

	public String getResidenza() {
		return residenza;
	}

	public void setResidenza(String residenza) {
		this.residenza = residenza;
	}

	@Override
	public String toString() {
		return "Persona [nome=" + nome + ", cognome=" + cognome + ", telefono=" + telefono + ", sesso=" + sesso
				+ ", dataNascita=" + dataNascita + ", luogoNascita=" + luogoNascita + ", cf=" + cf + ", residenza="
				+ residenza + "]";
	}

}
