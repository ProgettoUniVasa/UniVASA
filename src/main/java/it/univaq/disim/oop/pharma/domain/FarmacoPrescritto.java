package it.univaq.disim.oop.pharma.domain;

public class FarmacoPrescritto {

	private Integer id;
	private Prescrizione prescrizione;
	private Farmaco farmaco;
	private int quantita;

	public FarmacoPrescritto() {
	}

	public FarmacoPrescritto(Integer id, Prescrizione prescrizione, Farmaco farmaco, int quantita) {
		this.id = id;
		this.prescrizione = prescrizione;
		this.farmaco = farmaco;
		this.quantita = quantita;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Prescrizione getPrescrizione() {
		return prescrizione;
	}

	public void setPrescrizione(Prescrizione prescrizione) {
		this.prescrizione = prescrizione;
	}

	public Farmaco getFarmaco() {
		return farmaco;
	}

	public void setFarmaco(Farmaco farmaco) {
		this.farmaco = farmaco;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	@Override
	public String toString() {
		return "FarmacoPrescritto [id=" + id + ", prescrizione=" + prescrizione + ", farmaco=" + farmaco + ", quantita="
				+ quantita + "]";
	}

}