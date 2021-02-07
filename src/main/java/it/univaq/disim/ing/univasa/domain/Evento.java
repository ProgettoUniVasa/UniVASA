package it.univaq.disim.ing.univasa.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Evento {

	private Long id;

	private String nome;

	private String regolamento;

	private LocalDate dataInizio;

	private LocalDate dataFine;

	private String oraInizio;

	private String oraFine;

	private String luogo;

	private String report_risultati;

	private String report_statistiche;

	private int numero_preferenze_esprimibili;

	private StatoEvento statoEvento;

	private Set<Candidato> candidati = new HashSet<>();
	private Set<Operatore> operatori = new HashSet<>();

	public Evento() {
		super();
	}

	public Evento(Long id, String nome, String regolamento, LocalDate dataInizio, LocalDate dataFine, String oraInizio,
			String oraFine, String luogo, String report_risultati, String report_statistiche,
			int numero_preferenze_esprimibili, StatoEvento statoEvento, Set<Amministratore> amministratori,
			Set<Candidato> candidati, Set<Operatore> operatori, Set<SchedaElettorale> schedeOnline,
			Set<ElettoreInSede> elettoriInSede) {
		super();
		this.id = id;
		this.nome = nome;
		this.regolamento = regolamento;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.luogo = luogo;
		this.report_risultati = report_risultati;
		this.report_statistiche = report_statistiche;
		this.numero_preferenze_esprimibili = numero_preferenze_esprimibili;
		this.statoEvento = statoEvento;
		this.candidati = candidati;
		this.operatori = operatori;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getRegolamento() {
		return regolamento;
	}

	public LocalDate getDataInizio() {
		return dataInizio;
	}

	public LocalDate getDataFine() {
		return dataFine;
	}

	public String getOraInizio() {
		return oraInizio;
	}

	public String getOraFine() {
		return oraFine;
	}

	public String getLuogo() {
		return luogo;
	}

	public String getReport_risultati() {
		return report_risultati;
	}

	public String getReport_statistiche() {
		return report_statistiche;
	}

	public int getNumero_preferenze_esprimibili() {
		return numero_preferenze_esprimibili;
	}

	public Set<Candidato> getCandidati() {
		return candidati;
	}

	public Set<Operatore> getOperatori() {
		return operatori;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setRegolamento(String regolamento) {
		this.regolamento = regolamento;
	}

	public void setDataInizio(LocalDate dataInizio) {
		this.dataInizio = dataInizio;
	}

	public void setDataFine(LocalDate dataFine) {
		this.dataFine = dataFine;
	}

	public void setOraInizio(String oraInizio) {
		this.oraInizio = oraInizio;
	}

	public void setOraFine(String oraFine) {
		this.oraFine = oraFine;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	public void setReport_risultati(String report_risultati) {
		this.report_risultati = report_risultati;
	}

	public void setReport_statistiche(String report_statistiche) {
		this.report_statistiche = report_statistiche;
	}

	public void setNumero_preferenze_esprimibili(int numero_preferenze_esprimibili) {
		this.numero_preferenze_esprimibili = numero_preferenze_esprimibili;
	}

	public StatoEvento getStatoEvento() {
		return statoEvento;
	}

	public void setStatoEvento(StatoEvento statoEvento) {
		this.statoEvento = statoEvento;
	}


	public void setCandidati(Set<Candidato> candidati) {
		this.candidati = candidati;
	}

	public void setOperatori(Set<Operatore> operatori) {
		this.operatori = operatori;
	}


	/*@Override
	public String toString() {
		return "Evento [id=" + id + ", nome=" + nome + ", regolamento=" + regolamento + ", dataInizio=" + dataInizio
				+ ", dataFine=" + dataFine + ", oraInizio=" + oraInizio + ", oraFine=" + oraFine + ", luogo=" + luogo
				+ ", report_risultati=" + report_risultati + ", report_statistiche=" + report_statistiche
				+ ", numero_preferenze_esprimibili=" + numero_preferenze_esprimibili + ", statoEvento=" + statoEvento
				+ ", amministratori=" + amministratori + ", candidati=" + candidati + ", operatori=" + operatori
				+ ", schedeOnline=" + schedeOnline + ", elettoriInSede=" + elettoriInSede + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}*/
	
// operations
	/*
	 * 
	 * public prenotazioneInSede() { //TODO }
	 * 
	 * public prenotazioneOnline() { //TODO }
	 * 
	 * public visualizzaAmministratori() { //TODO }
	 */

}
