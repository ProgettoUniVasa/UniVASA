package it.univaq.disim.oop.pharma.domain;

import java.time.LocalDate;

public class Farmaco {
	private Integer id;
	private String nome;

	private String principioAttivo;
	private String produttore;
	private LocalDate scadenza;

	private double costo;
	private int quantitaDisponibile;
	private int quantitaMinima = 1;

	public Farmaco() {
	}

	public Farmaco(Integer id, String nome, String principioAttivo, String produttore, LocalDate scadenza, double costo,
			int quantitaDisponibile, int quantitaMinima) {
		this.id = id;
		this.nome = nome;
		this.principioAttivo = principioAttivo;
		this.produttore = produttore;
		this.scadenza = scadenza;
		this.costo = costo;
		this.quantitaDisponibile = quantitaDisponibile;
		this.quantitaMinima = quantitaMinima;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPrincipioAttivo() {
		return principioAttivo;
	}

	public void setPrincipioAttivo(String principioAttivo) {
		this.principioAttivo = principioAttivo;
	}

	public String getProduttore() {
		return produttore;
	}

	public void setProduttore(String produttore) {
		this.produttore = produttore;
	}

	public LocalDate getScadenza() {
		return scadenza;
	}

	public void setScadenza(LocalDate scadenza) {
		this.scadenza = scadenza;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public int getQuantitaDisponibile() {
		return quantitaDisponibile;
	}

	public void setQuantitaDisponibile(int quantitaDisponibile) {
		this.quantitaDisponibile = quantitaDisponibile;
	}

	public int getQuantitaMinima() {
		return quantitaMinima;
	}

	public void setQuantitaMinima(int quantitaMinima) {
		this.quantitaMinima = quantitaMinima;
	}

	@Override
	public String toString() {
		return "Farmaco [id=" + id + ", nome=" + nome + ", principioAttivo=" + principioAttivo + ", produttore="
				+ produttore + ", scadenza=" + scadenza + ", costo=" + costo + ", quantitaDisponibile="
				+ quantitaDisponibile + ", quantitaMinima=" + quantitaMinima + "]";
	}

}
