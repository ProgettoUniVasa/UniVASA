package it.univaq.disim.oop.pharma.business.impl.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.oop.pharma.business.BusinessException;
import it.univaq.disim.oop.pharma.business.FarmacoService;
import it.univaq.disim.oop.pharma.domain.Farmaco;

public class FileFarmacoServiceImpl implements FarmacoService {

	private String farmaciFileName;

	public FileFarmacoServiceImpl(String farmaciFileName) {
		this.farmaciFileName = farmaciFileName;
	}

	@Override
	public void creaFarmaco(Farmaco farmaco) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(farmaciFileName);
			try (PrintWriter writer = new PrintWriter(new File(farmaciFileName))) {
				long contatore = fileData.getContatore();
				writer.println((contatore + 1));
				for (String[] righe : fileData.getRighe()) {
					writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
				}
				StringBuilder row = new StringBuilder();
				row.append(contatore);
				farmaco.setId((int) contatore);
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(farmaco.getNome());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(farmaco.getPrincipioAttivo());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(farmaco.getProduttore());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(farmaco.getScadenza().toString());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(farmaco.getCosto());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(farmaco.getQuantitaDisponibile());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(farmaco.getQuantitaMinima());
				writer.println(row.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public void aggiornaFarmaco(Farmaco farmaco) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(farmaciFileName);
			try (PrintWriter writer = new PrintWriter(new File(farmaciFileName))) {
				writer.println(fileData.getContatore());
				for (String[] righe : fileData.getRighe()) {

					if (Long.parseLong(righe[0]) == farmaco.getId()) {
						StringBuilder row = new StringBuilder();
						row.append(farmaco.getId());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(farmaco.getNome());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(farmaco.getPrincipioAttivo());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(farmaco.getProduttore());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(farmaco.getScadenza().toString());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(farmaco.getCosto());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(farmaco.getQuantitaDisponibile());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(farmaco.getQuantitaMinima());
						writer.println(row.toString());

					} else {
						writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));

					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public void eliminaFarmaco(Farmaco farmaco) throws BusinessException {
		int id = farmaco.getId();
		try {
			FileData fileData = Utility.readAllRows(farmaciFileName);
			try (PrintWriter writer = new PrintWriter(new File(farmaciFileName))) {
				long contatore = fileData.getContatore();
				writer.println(contatore);
				for (String[] righe : fileData.getRighe()) {
					if (Integer.parseInt(righe[0]) != id) {
						writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public List<Farmaco> trovaTuttiFarmaci() throws BusinessException {
		List<Farmaco> result = new ArrayList<>();
		try {
			FileData fileData = Utility.readAllRows(farmaciFileName);
			for (String[] colonne : fileData.getRighe()) {
				Farmaco farmaco = new Farmaco();
				farmaco.setId(Integer.parseInt(colonne[0]));
				farmaco.setNome(colonne[1]);
				farmaco.setPrincipioAttivo(colonne[2]);
				farmaco.setProduttore(colonne[3]);
				farmaco.setScadenza(LocalDate.parse(colonne[4]));
				farmaco.setCosto(Double.parseDouble(colonne[5]));
				farmaco.setQuantitaDisponibile(Integer.parseInt(colonne[6]));
				farmaco.setQuantitaMinima(Integer.parseInt(colonne[7]));
				result.add(farmaco);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return result;
	}

	@Override
	public Farmaco trovaFarmacoDaId(int id) throws BusinessException {

		Farmaco farmaco = new Farmaco();
		try {
			FileData fileData = Utility.readAllRows(farmaciFileName);
			for (String[] colonne : fileData.getRighe()) {
				if (Integer.parseInt(colonne[0]) == id) {
					farmaco.setId(Integer.parseInt(colonne[0]));
					farmaco.setNome(colonne[1]);
					farmaco.setPrincipioAttivo(colonne[2]);
					farmaco.setProduttore(colonne[3]);
					farmaco.setScadenza(LocalDate.parse(colonne[4]));
					farmaco.setCosto(Double.parseDouble(colonne[5]));
					farmaco.setQuantitaDisponibile(Integer.parseInt(colonne[6]));
					farmaco.setQuantitaMinima(Integer.parseInt(colonne[7]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return farmaco;
	}

	@Override
	public List<String> nomiFarmaci() throws BusinessException {
		List<String> result = new ArrayList<>();
		try {
			FileData fileData = Utility.readAllRows(farmaciFileName);
			for (String[] colonne : fileData.getRighe()) {
				Farmaco farmaco = new Farmaco();
				farmaco.setId(Integer.parseInt(colonne[0]));
				farmaco.setNome(colonne[1]);
				farmaco.setPrincipioAttivo(colonne[2]);
				farmaco.setProduttore(colonne[3]);
				farmaco.setScadenza(LocalDate.parse(colonne[4]));
				farmaco.setCosto(Double.parseDouble(colonne[5]));
				farmaco.setQuantitaDisponibile(Integer.parseInt(colonne[6]));
				farmaco.setQuantitaMinima(Integer.parseInt(colonne[7]));
				result.add(farmaco.getNome());
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return result;
	}

	@Override
	public List<Farmaco> farmaciInEsaurimento() throws BusinessException {
		List<Farmaco> result = new ArrayList<>();
		try {
			FileData fileData = Utility.readAllRows(farmaciFileName);
			for (String[] colonne : fileData.getRighe()) {
				Farmaco farmaco = new Farmaco();
				farmaco.setId(Integer.parseInt(colonne[0]));
				farmaco.setNome(colonne[1]);
				farmaco.setPrincipioAttivo(colonne[2]);
				farmaco.setProduttore(colonne[3]);
				farmaco.setScadenza(LocalDate.parse(colonne[4]));
				farmaco.setCosto(Double.parseDouble(colonne[5]));
				farmaco.setQuantitaDisponibile(Integer.parseInt(colonne[6]));
				farmaco.setQuantitaMinima(Integer.parseInt(colonne[7]));
				if (farmaco.getQuantitaDisponibile() <= farmaco.getQuantitaMinima()) {
					result.add(farmaco);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

		return result;
	}

}
