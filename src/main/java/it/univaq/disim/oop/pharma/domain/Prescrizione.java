package it.univaq.disim.oop.pharma.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Prescrizione {
	private Integer id;
	private String numero;
	private LocalDate data;
	private String firma;
	private double costoPrescrizione;

	private String evasione = "NO";
	private Paziente paziente;
	private Medico medico;
	private Set<FarmacoPrescritto> farmaciPrescritti = new HashSet<>();

	public Prescrizione() {
	};

	public Prescrizione(Integer id, String numero, LocalDate data, double costoPrescrizione, String evasione,
			Paziente paziente, Medico medico, Set<FarmacoPrescritto> farmaciPrescritti) {
		super();
		this.id = id;
		this.numero = numero;
		this.data = data;
		this.firma = medico.getNome() + " " + medico.getCognome();
		this.costoPrescrizione = costoPrescrizione;
		this.evasione = evasione;
		this.paziente = paziente;
		this.medico = medico;
		this.farmaciPrescritti = farmaciPrescritti;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public double getCostoPrescrizione() {
		return costoPrescrizione;
	}

	public void setCostoPrescrizione(double costoPrescrizione) {
		this.costoPrescrizione = costoPrescrizione;
	}

	public String getEvasione() {
		return evasione.toUpperCase();
	}

	public void setEvasione(String evasione) {
		this.evasione = evasione.toUpperCase();
	}

	public Paziente getPaziente() {
		return paziente;
	}

	public void setPaziente(Paziente paziente) {
		this.paziente = paziente;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Set<FarmacoPrescritto> getFarmaciPrescritti() {
		return farmaciPrescritti;
	}

	public void setFarmaciPrescritti(Set<FarmacoPrescritto> farmaciPrescritti) {
		this.farmaciPrescritti = farmaciPrescritti;
	}

	@Override
	public String toString() {
		return "Prescrizione [id=" + id + ", numero=" + numero + ", data=" + data + ", firma=" + firma
				+ ", costoPrescrizione=" + costoPrescrizione + ", paziente=" + paziente + ", medico=" + medico
				+ ", evasione=" + evasione + ", farmaciPrescritti=" + farmaciPrescritti + "]";
	}

}
