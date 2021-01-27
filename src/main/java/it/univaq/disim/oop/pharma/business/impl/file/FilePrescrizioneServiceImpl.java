package it.univaq.disim.oop.pharma.business.impl.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.oop.pharma.business.BusinessException;
import it.univaq.disim.oop.pharma.business.FarmacoService;
import it.univaq.disim.oop.pharma.business.PrescrizioneService;
import it.univaq.disim.oop.pharma.business.UtenteService;
import it.univaq.disim.oop.pharma.domain.Farmaco;
import it.univaq.disim.oop.pharma.domain.FarmacoPrescritto;
import it.univaq.disim.oop.pharma.domain.Medico;
import it.univaq.disim.oop.pharma.domain.Paziente;
import it.univaq.disim.oop.pharma.domain.Prescrizione;
import it.univaq.disim.oop.pharma.domain.Utente;

public class FilePrescrizioneServiceImpl implements PrescrizioneService {

	private String prescrizioniFilename;
	private String farmaciprescrittiFilename;
	private UtenteService utenteService;
	private FarmacoService farmacoService;

	public FilePrescrizioneServiceImpl(String prescrizioniFileName, String farmaciprescrittiFileName,
			UtenteService utenteService, FarmacoService farmacoService) {
		this.prescrizioniFilename = prescrizioniFileName;
		this.farmaciprescrittiFilename = farmaciprescrittiFileName;
		this.utenteService = utenteService;
		this.farmacoService = farmacoService;
	}

	@Override
	public List<Prescrizione> trovaTuttePrescrizioni() throws BusinessException {
		List<Prescrizione> result = new ArrayList<>();
		try {
			FileData fileData = Utility.readAllRows(prescrizioniFilename);
			for (String[] colonne : fileData.getRighe()) {
				Prescrizione prescrizione = new Prescrizione();
				prescrizione.setId(Integer.parseInt(colonne[0]));
				prescrizione.setNumero(colonne[1]);
				prescrizione.setData(LocalDate.parse(colonne[2]));
				prescrizione.setFirma(colonne[3]);
				prescrizione.setCostoPrescrizione(Double.parseDouble(colonne[4]));
				prescrizione.setEvasione(colonne[5]);
				prescrizione.setPaziente((Paziente) utenteService.trovaUtenteDaId(Integer.parseInt(colonne[6])));
				prescrizione.setMedico((Medico) utenteService.trovaUtenteDaId(Integer.parseInt(colonne[7])));
				result.add(prescrizione);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

		return result;
	}

	@Override
	public Prescrizione trovaPrescrizioneDaId(int id) throws BusinessException {
		Prescrizione result = new Prescrizione();
		try {
			FileData fileData = Utility.readAllRows(prescrizioniFilename);
			for (String[] colonne : fileData.getRighe()) {
				if (Integer.parseInt(colonne[0]) == id) {
					result.setId(id);
					result.setNumero(colonne[1]);
					result.setData(LocalDate.parse(colonne[2]));
					result.setFirma(colonne[3]);
					result.setCostoPrescrizione(Double.parseDouble(colonne[4]));
					result.setEvasione(colonne[5]);

					Utente utente = utenteService.trovaUtenteDaId(Integer.parseInt(colonne[6]));
					result.setPaziente((Paziente) utente);
					utente = utenteService.trovaUtenteDaId(Integer.parseInt(colonne[7]));
					result.setMedico((Medico) utente);
					return result;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

		return result;
	}

	@Override
	public List<Prescrizione> trovaPrescrizioniMedico(Medico medico) throws BusinessException {
		List<Prescrizione> result = new ArrayList<>();
		try {
			FileData fileData = Utility.readAllRows(prescrizioniFilename);

			for (String[] colonne : fileData.getRighe()) {
				if (colonne[7].equals(medico.getId().toString())) {
					Prescrizione prescrizione = new Prescrizione();
					prescrizione.setId(Integer.parseInt(colonne[0]));
					prescrizione.setNumero(colonne[1]);
					prescrizione.setData(LocalDate.parse(colonne[2]));
					prescrizione.setFirma(colonne[3]);
					prescrizione.setCostoPrescrizione(Double.parseDouble(colonne[4]));
					prescrizione.setEvasione(colonne[5]);
					prescrizione.setPaziente((Paziente) utenteService.trovaUtenteDaId(Integer.parseInt(colonne[6])));
					prescrizione.setMedico(medico);
					medico.getPrescrizioni().add(prescrizione);

					result.add(prescrizione);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

		return result;
	}

	@Override
	public List<Prescrizione> trovaPrescrizioniPaziente(Paziente paziente) throws BusinessException {
		List<Prescrizione> result = new ArrayList<>();
		try {
			FileData fileData = Utility.readAllRows(prescrizioniFilename);
			for (String[] colonne : fileData.getRighe()) {
				if (colonne[6].equals(paziente.getId().toString())) {
					Prescrizione prescrizione = new Prescrizione();
					prescrizione.setId(Integer.parseInt(colonne[0]));
					prescrizione.setNumero(colonne[1]);
					prescrizione.setData(LocalDate.parse(colonne[2]));
					prescrizione.setFirma(colonne[3]);
					prescrizione.setCostoPrescrizione(Double.parseDouble(colonne[4]));
					prescrizione.setEvasione(colonne[5]);
					prescrizione.setPaziente(paziente);
					prescrizione.setMedico((Medico) utenteService.trovaUtenteDaId(Integer.parseInt(colonne[7])));

					paziente.getPrescrizioni().add(prescrizione);
					FileData fileData1 = Utility.readAllRows(farmaciprescrittiFilename);
					// Calcolo del costo della prescrizione sommando i costi dei farmaci prescritti
					for (String[] colonneF : fileData1.getRighe()) {
						if (colonneF[1].equals(prescrizione.getId().toString())) {
							// Arrotondamento dei costi a 2 cifre nella parte frazionaria
							Farmaco farmaco = farmacoService.trovaFarmacoDaId(Integer.parseInt(colonneF[2]));
							Double costoFarmaco = farmaco.getCosto() * (Double.parseDouble(colonneF[3]));
							costoFarmaco = (double) Math.round(costoFarmaco * 100);
							costoFarmaco = costoFarmaco / 100;
							prescrizione
									.setCostoPrescrizione(Math.round(prescrizione.getCostoPrescrizione() * 100) / 100);
							prescrizione.setCostoPrescrizione(prescrizione.getCostoPrescrizione() + costoFarmaco);
						}
					}
					result.add(prescrizione);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

		return result;
	}

	@Override
	public List<FarmacoPrescritto> trovaFarmaciPrescritti(Prescrizione prescrizione) throws BusinessException {
		List<FarmacoPrescritto> result = new ArrayList<>();
		try {
			FileData fileData = Utility.readAllRows(farmaciprescrittiFilename);
			for (String[] colonne : fileData.getRighe()) {
				if (colonne[1].equals(prescrizione.getId().toString())) {
					FarmacoPrescritto farmacoPrescritto = new FarmacoPrescritto();
					farmacoPrescritto.setId(Integer.parseInt(colonne[0]));
					farmacoPrescritto.setPrescrizione(prescrizione);
					Farmaco farmaco = farmacoService.trovaFarmacoDaId(Integer.parseInt(colonne[2]));
					farmacoPrescritto.setFarmaco(farmaco);
					farmacoPrescritto.setQuantita(Integer.parseInt(colonne[3]));
					result.add(farmacoPrescritto);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

		return result;
	}

	@Override
	public void creaPrescrizione(Prescrizione prescrizione) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(prescrizioniFilename);
			try (PrintWriter writer = new PrintWriter(new File(prescrizioniFilename))) {
				long contatore = fileData.getContatore();
				writer.println((contatore + 1));
				for (String[] righe : fileData.getRighe()) {
					writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
				}
				StringBuilder row = new StringBuilder();

				row.append(contatore);
				prescrizione.setId((int) contatore);
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(prescrizione.getNumero().toString());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(prescrizione.getData().toString());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(prescrizione.getFirma().toString());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(prescrizione.getCostoPrescrizione());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(prescrizione.getEvasione());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(prescrizione.getPaziente().getId());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(prescrizione.getMedico().getId());

				writer.println(row.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public void aggiornaPrescrizione(Prescrizione prescrizione) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(prescrizioniFilename);
			try (PrintWriter writer = new PrintWriter(new File(prescrizioniFilename))) {
				writer.println(fileData.getContatore());
				for (String[] righe : fileData.getRighe()) {
					if (Long.parseLong(righe[0]) == prescrizione.getId()) {
						StringBuilder row = new StringBuilder();
						row.append(prescrizione.getId());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(prescrizione.getNumero().toString());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(prescrizione.getData().toString());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(prescrizione.getFirma().toString());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(prescrizione.getCostoPrescrizione());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(prescrizione.getEvasione());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(prescrizione.getPaziente().getId());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(prescrizione.getMedico().getId());
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

	public FarmacoPrescritto trovaFarmacoPrescrittoDaId(int id) throws BusinessException {
		FarmacoPrescritto result = new FarmacoPrescritto();
		try {
			FileData fileData = Utility.readAllRows(farmaciprescrittiFilename);
			for (String[] colonne : fileData.getRighe()) {
				if (Integer.parseInt(colonne[0]) == id) {
					result.setId(id);

					PrescrizioneService prescrizioneService = new FilePrescrizioneServiceImpl(prescrizioniFilename,
							farmaciprescrittiFilename, utenteService, farmacoService);
					Prescrizione prescrizione = prescrizioneService.trovaPrescrizioneDaId(Integer.parseInt(colonne[1]));
					result.setPrescrizione(prescrizione);
					Farmaco farmaco = farmacoService.trovaFarmacoDaId(Integer.parseInt(colonne[2]));
					result.setFarmaco(farmaco);
					result.setQuantita(Integer.parseInt(colonne[3]));
					return result;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

		return result;
	}

	@Override
	public void creaFarmacoPrescritto(FarmacoPrescritto nuovoFarmacoPrescritto) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(farmaciprescrittiFilename);
			try (PrintWriter writer = new PrintWriter(new File(farmaciprescrittiFilename))) {
				long contatore = fileData.getContatore();
				writer.println((contatore + 1));
				for (String[] righe : fileData.getRighe()) {
					writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
				}
				StringBuilder row = new StringBuilder();
				row.append(contatore);
				nuovoFarmacoPrescritto.setId((int) contatore);
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(nuovoFarmacoPrescritto.getPrescrizione().getId());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(nuovoFarmacoPrescritto.getFarmaco().getId());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(nuovoFarmacoPrescritto.getQuantita());
				writer.println(row.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public void eliminaPrescrizione(Prescrizione prescrizione) {
		int id = prescrizione.getId();
		try {
			FileData fileData = Utility.readAllRows(prescrizioniFilename);
			try (PrintWriter writer = new PrintWriter(new File(prescrizioniFilename))) {
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
		}
	}

	@Override
	public void eliminaFarmaciPrescritti(Prescrizione prescrizione) throws BusinessException {
		int id = prescrizione.getId();
		try {
			FileData fileData = Utility.readAllRows(farmaciprescrittiFilename);
			try (PrintWriter writer = new PrintWriter(new File(farmaciprescrittiFilename))) {
				long contatore = fileData.getContatore();
				writer.println(contatore);
				for (String[] righe : fileData.getRighe()) {
					if (Integer.parseInt(righe[1]) != id) {
						writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void eliminaFarmacoPrescritto(FarmacoPrescritto farmacoPrescritto) throws BusinessException {
		int id = farmacoPrescritto.getId();
		try {
			FileData fileData = Utility.readAllRows(farmaciprescrittiFilename);
			try (PrintWriter writer = new PrintWriter(new File(farmaciprescrittiFilename))) {
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
		}
	}

	@Override
	public List<String> numeriPrescrizioni() throws BusinessException {
		List<String> result = new ArrayList<>();
		try {
			FileData fileData = Utility.readAllRows(prescrizioniFilename);
			for (String[] colonne : fileData.getRighe()) {
				Prescrizione prescrizione = new Prescrizione();
				prescrizione.setId(Integer.parseInt(colonne[0]));
				prescrizione.setNumero(colonne[1]);
				prescrizione.setData(LocalDate.parse(colonne[2]));
				prescrizione.setFirma(colonne[3]);
				prescrizione.setCostoPrescrizione(Double.parseDouble(colonne[4]));
				prescrizione.setEvasione(colonne[5]);
				prescrizione.setPaziente((Paziente) utenteService.trovaUtenteDaId(Integer.parseInt(colonne[6])));
				prescrizione.setMedico((Medico) utenteService.trovaUtenteDaId(Integer.parseInt(colonne[7])));

				result.add(prescrizione.getNumero());
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return result;
	}

}
