package it.univaq.disim.oop.pharma.business.impl.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.oop.pharma.business.BusinessException;
import it.univaq.disim.oop.pharma.business.UtenteNotFoundException;
import it.univaq.disim.oop.pharma.business.UtenteService;
import it.univaq.disim.oop.pharma.domain.Amministratore;
import it.univaq.disim.oop.pharma.domain.Farmacista;
import it.univaq.disim.oop.pharma.domain.Medico;
import it.univaq.disim.oop.pharma.domain.Paziente;
import it.univaq.disim.oop.pharma.domain.TipologiaMedico;
import it.univaq.disim.oop.pharma.domain.Utente;

public class FileUtenteServiceImpl implements UtenteService {

	private String utentiFilename;

	public FileUtenteServiceImpl(String utentiFilename) {
		this.utentiFilename = utentiFilename;
	}

	@Override
	public Utente autenticazione(String username, String password) throws UtenteNotFoundException, BusinessException {
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			for (String[] colonne : fileData.getRighe()) {
				if (colonne[1].equals(username) && colonne[2].equals(password)) {
					Utente utente = null;
					switch (colonne[3]) {
					case "medico":
						utente = new Medico();
						break;
					case "paziente":
						utente = new Paziente();
						break;
					case "farmacista":
						utente = new Farmacista();
						break;
					case "amministratore":
						utente = new Amministratore();
						break;
					default:
						break;
					}
					if (utente != null) {
						utente.setId(Integer.parseInt(colonne[0]));
						utente.setUsername(username);
						utente.setPassword(password);
						utente.setNome(colonne[4]);
						utente.setCognome(colonne[5]);
						utente.setCf(colonne[6]);
						utente.setDataNascita(LocalDate.parse(colonne[7]));
						utente.setLuogoNascita(colonne[8]);
						utente.setResidenza(colonne[9]);
						utente.setTelefono(colonne[10]);
						utente.setSesso(colonne[11]);
					} else {
						throw new BusinessException("Errore nella lettura del file");
					}
					if (utente instanceof Medico) {
						((Medico) utente).setCodiceAlboMedici(colonne[12]);
						((Medico) utente).setTipologia(TipologiaMedico.valueOf(colonne[13]));
					}
					if (utente instanceof Paziente) {
						((Paziente) utente).setSaldo(Double.parseDouble(colonne[12]));
					}

					return utente;
				}
			}
			throw new UtenteNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

	}

	@Override
	public Utente trovaUtenteDaId(int id) throws UtenteNotFoundException, BusinessException {

		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			for (String[] colonne : fileData.getRighe()) {
				if (Integer.parseInt(colonne[0]) == id) {
					if ((colonne[3]).equals("medico")) {
						Medico medico = new Medico();

						medico.setId(id);
						medico.setUsername(colonne[1]);
						medico.setPassword(colonne[2]);
						medico.setNome(colonne[4]);
						medico.setCognome(colonne[5]);
						medico.setCf(colonne[6]);
						medico.setDataNascita(LocalDate.parse(colonne[7]));
						medico.setLuogoNascita(colonne[8]);
						medico.setResidenza(colonne[9]);
						medico.setTelefono(colonne[10]);
						medico.setSesso(colonne[11]);
						medico.setCodiceAlboMedici(colonne[12]);
						medico.setTipologia(TipologiaMedico.valueOf(colonne[13]));
						return medico;
					}
					if ((colonne[3]).equals("paziente")) {
						Paziente paziente = new Paziente();

						paziente.setId(id);
						paziente.setUsername(colonne[1]);
						paziente.setPassword(colonne[2]);
						paziente.setNome(colonne[4]);
						paziente.setCognome(colonne[5]);
						paziente.setCf(colonne[6]);
						paziente.setDataNascita(LocalDate.parse(colonne[7]));
						paziente.setLuogoNascita(colonne[8]);
						paziente.setResidenza(colonne[9]);
						paziente.setTelefono(colonne[10]);
						paziente.setSesso(colonne[11]);
						paziente.setSaldo(Double.parseDouble(colonne[12]));
						return paziente;
					}
					if ((colonne[3]).equals("farmacista")) {
						Farmacista farmacista = new Farmacista();

						farmacista.setId(id);
						farmacista.setUsername(colonne[1]);
						farmacista.setPassword(colonne[2]);
						farmacista.setNome(colonne[4]);
						farmacista.setCognome(colonne[5]);
						farmacista.setCf(colonne[6]);
						farmacista.setDataNascita(LocalDate.parse(colonne[7]));
						farmacista.setLuogoNascita(colonne[8]);
						farmacista.setResidenza(colonne[9]);
						farmacista.setTelefono(colonne[10]);
						farmacista.setSesso(colonne[11]);
						return farmacista;
					}
					if ((colonne[3]).equals("amministratore")) {
						Amministratore amministratore = new Amministratore();

						amministratore.setId(id);
						amministratore.setUsername(colonne[1]);
						amministratore.setPassword(colonne[2]);
						amministratore.setNome(colonne[4]);
						amministratore.setCognome(colonne[5]);
						amministratore.setCf(colonne[6]);
						amministratore.setDataNascita(LocalDate.parse(colonne[7]));
						amministratore.setLuogoNascita(colonne[8]);
						amministratore.setResidenza(colonne[9]);
						amministratore.setTelefono(colonne[10]);
						amministratore.setSesso(colonne[11]);
						return amministratore;
					}
				}
			}
			throw new UtenteNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public List<Utente> trovaTuttiUtenti() throws BusinessException {
		List<Utente> result = new ArrayList<>();
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			for (String[] colonne : fileData.getRighe()) {
				Utente utente = new Utente();
				utente.setId(Integer.parseInt(colonne[0]));
				utente.setNome(colonne[4]);
				utente.setCognome(colonne[5]);
				utente.setCf(colonne[6]);
				utente.setDataNascita(LocalDate.parse(colonne[7]));
				utente.setLuogoNascita(colonne[8]);
				utente.setResidenza(colonne[9]);
				utente.setTelefono(colonne[10]);
				utente.setSesso(colonne[11]);
				result.add(utente);
				if (result instanceof Medico) {
					((Medico) result).setCodiceAlboMedici(colonne[12]);
					((Medico) result).setTipologia(TipologiaMedico.valueOf(colonne[13]));
					return result;
				} else if (result instanceof Paziente) {
					((Paziente) result).setSaldo(Double.parseDouble(colonne[12]));

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

		return result;
	}

	@Override
	public List<Farmacista> trovaTuttiFarmacisti() throws BusinessException {
		List<Farmacista> result = new ArrayList<>();
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			for (String[] colonne : fileData.getRighe()) {
				if (colonne[3].equals("farmacista")) {
					Farmacista farmacista = new Farmacista();
					farmacista.setId(Integer.parseInt(colonne[0]));
					farmacista.setUsername(colonne[1]);
					farmacista.setPassword(colonne[2]);
					farmacista.setNome(colonne[4]);
					farmacista.setCognome(colonne[5]);
					farmacista.setCf(colonne[6]);
					farmacista.setDataNascita(LocalDate.parse(colonne[7]));
					farmacista.setLuogoNascita(colonne[8]);
					farmacista.setResidenza(colonne[9]);
					farmacista.setTelefono(colonne[10]);
					farmacista.setSesso(colonne[11]);
					result.add(farmacista);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

		return result;
	}

	@Override
	public List<Paziente> trovaTuttiPazienti() throws BusinessException {
		List<Paziente> result = new ArrayList<>();
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			for (String[] colonne : fileData.getRighe()) {
				if (colonne[3].equals("paziente")) {
					Paziente paziente = new Paziente();
					paziente.setId(Integer.parseInt(colonne[0]));
					paziente.setUsername(colonne[1]);
					paziente.setPassword(colonne[2]);
					paziente.setNome(colonne[4]);
					paziente.setCognome(colonne[5]);
					paziente.setCf(colonne[6]);
					paziente.setDataNascita(LocalDate.parse(colonne[7]));
					paziente.setLuogoNascita(colonne[8]);
					paziente.setResidenza(colonne[9]);
					paziente.setTelefono(colonne[10]);
					paziente.setSesso(colonne[11]);
					paziente.setSaldo(Double.parseDouble(colonne[12]));
					result.add(paziente);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

		return result;
	}

	@Override
	public List<Medico> trovaTuttiMedici() throws BusinessException {
		List<Medico> result = new ArrayList<>();
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			for (String[] colonne : fileData.getRighe()) {
				if (colonne[3].equals("medico")) {
					Medico medico = new Medico();
					medico.setId(Integer.parseInt(colonne[0]));
					medico.setUsername(colonne[1]);
					medico.setPassword(colonne[2]);
					medico.setNome(colonne[4]);
					medico.setCognome(colonne[5]);
					medico.setCf(colonne[6]);
					medico.setDataNascita(LocalDate.parse(colonne[7]));
					medico.setLuogoNascita(colonne[8]);
					medico.setResidenza(colonne[9]);
					medico.setTelefono(colonne[10]);
					medico.setSesso(colonne[11]);
					medico.setCodiceAlboMedici(colonne[12]);
					medico.setTipologia(TipologiaMedico.valueOf(colonne[13]));
					result.add(medico);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

		return result;
	}

	@Override
	public void creaFarmacista(Farmacista farmacista) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			try (PrintWriter writer = new PrintWriter(new File(utentiFilename))) {
				long contatore = fileData.getContatore();
				writer.println((contatore + 1));
				for (String[] righe : fileData.getRighe()) {
					writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
				}
				StringBuilder row = new StringBuilder();
				row.append(contatore);
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(farmacista.getUsername());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(farmacista.getPassword());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append("farmacista");
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(farmacista.getNome());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(farmacista.getCognome());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(farmacista.getCf());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(farmacista.getDataNascita().toString());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(farmacista.getLuogoNascita());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(farmacista.getResidenza());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(farmacista.getTelefono());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(farmacista.getSesso());
				writer.println(row.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public void creaPaziente(Paziente paziente) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			try (PrintWriter writer = new PrintWriter(new File(utentiFilename))) {
				long contatore = fileData.getContatore();
				writer.println((contatore + 1));
				for (String[] righe : fileData.getRighe()) {
					writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
				}
				StringBuilder row = new StringBuilder();
				row.append(contatore);
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(paziente.getUsername());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(paziente.getPassword());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append("paziente");
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(paziente.getNome());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(paziente.getCognome());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(paziente.getCf());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(paziente.getDataNascita().toString());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(paziente.getLuogoNascita());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(paziente.getResidenza());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(paziente.getTelefono());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(paziente.getSesso());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(paziente.getSaldo());
				writer.println(row.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public void creaMedico(Medico medico) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			try (PrintWriter writer = new PrintWriter(new File(utentiFilename))) {
				long contatore = fileData.getContatore();
				writer.println((contatore + 1));
				for (String[] righe : fileData.getRighe()) {
					writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
				}
				StringBuilder row = new StringBuilder();
				row.append(contatore);
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(medico.getUsername());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(medico.getPassword());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append("medico");
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(medico.getNome());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(medico.getCognome());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(medico.getCf());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(medico.getDataNascita().toString());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(medico.getLuogoNascita());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(medico.getResidenza());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(medico.getTelefono());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(medico.getSesso());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(medico.getCodiceAlboMedici());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(medico.getTipologia().toString());

				writer.println(row.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public void aggiornaFarmacista(Farmacista farmacista) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			try (PrintWriter writer = new PrintWriter(new File(utentiFilename))) {
				writer.println(fileData.getContatore());
				for (String[] righe : fileData.getRighe()) {
					if (Long.parseLong(righe[0]) == farmacista.getId()) {
						StringBuilder row = new StringBuilder();
						row.append(farmacista.getId());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(farmacista.getUsername());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(farmacista.getPassword());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append("farmacista");
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(farmacista.getNome());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(farmacista.getCognome());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(farmacista.getCf());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(farmacista.getDataNascita().toString());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(farmacista.getLuogoNascita());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(farmacista.getResidenza());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(farmacista.getTelefono());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(farmacista.getSesso());
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
	public void aggiornaPaziente(Paziente paziente) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			try (PrintWriter writer = new PrintWriter(new File(utentiFilename))) {
				writer.println(fileData.getContatore());
				for (String[] righe : fileData.getRighe()) {
					if (Long.parseLong(righe[0]) == paziente.getId()) {
						StringBuilder row = new StringBuilder();
						row.append(paziente.getId());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(paziente.getUsername());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(paziente.getPassword());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append("paziente");
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(paziente.getNome());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(paziente.getCognome());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(paziente.getCf());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(paziente.getDataNascita().toString());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(paziente.getLuogoNascita());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(paziente.getResidenza());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(paziente.getTelefono());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(paziente.getSesso());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(paziente.getSaldo());
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
	public void aggiornaMedico(Medico medico) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			try (PrintWriter writer = new PrintWriter(new File(utentiFilename))) {
				writer.println(fileData.getContatore());
				for (String[] righe : fileData.getRighe()) {
					if (Long.parseLong(righe[0]) == medico.getId()) {
						StringBuilder row = new StringBuilder();
						row.append(medico.getId());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(medico.getUsername());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(medico.getPassword());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append("medico");
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(medico.getNome());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(medico.getCognome());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(medico.getCf());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(medico.getDataNascita().toString());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(medico.getLuogoNascita());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(medico.getResidenza());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(medico.getTelefono());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(medico.getSesso());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(medico.getCodiceAlboMedici());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(medico.getTipologia().toString());

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
	public void eliminaUtente(Utente utente) throws UtenteNotFoundException, BusinessException {
		int id = utente.getId();
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			try (PrintWriter writer = new PrintWriter(new File(utentiFilename))) {
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

}