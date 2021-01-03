package main.java.it.univaq.disim.es.univasa.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Evento {

	private Long id;
	private String nome;
	private String regolamento;
	private LocalDateTime data_ora_inizio;
	private LocalDateTime data_ora_fine;
	private String luogo;
	private List<Elettore> lista_prenotati = new ArrayList<>();
	private List<Elettore> lista_candidati = new ArrayList<>();
	private List<Operatore> lista_operatori = new ArrayList<>();
	private String report_risultati;
	private Integer report_statistiche;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getRegolamento() {
		return regolamento;
	}
	public void setRegolamento(String regolamento) {
		this.regolamento = regolamento;
	}
	public LocalDateTime getData_ora_inizio() {
		return data_ora_inizio;
	}
	public void setData_ora_inizio(LocalDateTime data_ora_inizio) {
		this.data_ora_inizio = data_ora_inizio;
	}
	public LocalDateTime getData_ora_fine() {
		return data_ora_fine;
	}
	public void setData_ora_fine(LocalDateTime data_ora_fine) {
		this.data_ora_fine = data_ora_fine;
	}
	public String getLuogo() {
		return luogo;
	}
	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}
	public List<Elettore> getLista_prenotati() {
		return lista_prenotati;
	}
	public void setLista_prenotati(List<Elettore> lista_prenotati) {
		this.lista_prenotati = lista_prenotati;
	}
	public List<Elettore> getLista_candidati() {
		return lista_candidati;
	}
	public void setLista_candidati(List<Elettore> lista_candidati) {
		this.lista_candidati = lista_candidati;
	}
	public List<Operatore> getLista_operatori() {
		return lista_operatori;
	}
	public void setLista_operatori(List<Operatore> lista_operatori) {
		this.lista_operatori = lista_operatori;
	}
	public String getReport_risultati() {
		return report_risultati;
	}
	public void setReport_risultati(String report_risultati) {
		this.report_risultati = report_risultati;
	}
	public Integer getReport_statistiche() {
		return report_statistiche;
	}
	public void setReport_statistiche(Integer report_statistiche) {
		this.report_statistiche = report_statistiche;
	}
		
}
