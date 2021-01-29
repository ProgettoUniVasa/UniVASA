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

	private Set<Amministratore> amministratori = new HashSet<>();
	private Set<Candidato> candidati = new HashSet<>();
	private Set<Operatore> operatori = new HashSet<>();
	private Set<SchedaElettorale> schedeOnline = new HashSet<>(); // elettori online
	private Set<ElettoreInSede> elettoriInSede = new HashSet<>();

	public Evento() {
		super();
	}

	public Evento(Long id, String nome, String regolamento, LocalDate dataInizio, LocalDate dataFine, String oraInizio,
			String oraFine, String luogo, String report_risultati, String report_statistiche,
			int numero_preferenze_esprimibili, Set<Amministratore> amministratori, Set<Candidato> candidati,
			Set<Operatore> operatori, Set<SchedaElettorale> schedeOnline, Set<ElettoreInSede> elettoriInSede) {
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
		this.amministratori = amministratori;
		this.candidati = candidati;
		this.operatori = operatori;
		this.schedeOnline = schedeOnline;
		this.elettoriInSede = elettoriInSede;
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

	public Set<Amministratore> getAmministratori() {
		return amministratori;
	}

	public Set<Candidato> getCandidati() {
		return candidati;
	}

	public Set<Operatore> getOperatori() {
		return operatori;
	}

	public Set<SchedaElettorale> getSchedeOnline() {
		return schedeOnline;
	}

	public Set<ElettoreInSede> getElettoriInSede() {
		return elettoriInSede;
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

	public void setAmministratori(Set<Amministratore> amministratori) {
		this.amministratori = amministratori;
	}

	public void setCandidati(Set<Candidato> candidati) {
		this.candidati = candidati;
	}

	public void setOperatori(Set<Operatore> operatori) {
		this.operatori = operatori;
	}

	public void setSchedeOnline(Set<SchedaElettorale> schedeOnline) {
		this.schedeOnline = schedeOnline;
	}

	public void setElettoriInSede(Set<ElettoreInSede> elettoriInSede) {
		this.elettoriInSede = elettoriInSede;
	}
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
