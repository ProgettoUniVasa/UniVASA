package it.univaq.disim.oop.pharma.business.impl.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
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

public class DbPrescrizioneServiceImpl implements PrescrizioneService {

	private UtenteService utenteService;
	private FarmacoService farmacoService;
	private PrescrizioneService prescrizioneService;

	private static final String url = "jdbc:mysql://localhost:3306/pharmadb?noAccessToProcedureBodies=true&serverTimezone=Europe/Rome";
	private static final String user = "dbuser";
	private static final String password = "sql_password123";

	// Definizione query in Java
	private static final String inserisciPrescrizione = "insert into prescrizione (numero, data, firma, costoPrescrizione, evasione, idpaziente, idmedico) values (?,?,?,?,?,?,?) ";
	private static final String aggiornaPrescrizione = "update prescrizione set numero=?, data=?, firma=?, costoPrescrizione=?, evasione=?, idpaziente=?, idmedico=? where id=? ";
	private static final String eliminaPrescrizione = "delete from prescrizione where id=? ";

	private static final String inserisciFarmacoPrescritto = "insert into farmacoprescritto (quantita, idprescrizione, idfarmaco) values (?,?,?) ";
	private static final String eliminaFarmaciPrescritti = "delete from farmacoprescritto where idprescrizione=? ";
	private static final String eliminaFarmacoPrescritto = "delete from farmacoprescritto where id=? ";

	private static final String selezionaPrescrizioni = "select * from prescrizione ";
	private static final String selezionaPrescrizioneById = "select * from prescrizione where id=? ";
	private static final String selezionaFarmacoPrescrittoById = "select * from farmacoprescritto where id=? ";
	private static final String selezionaPrescrizioniMedico = "select * from prescrizione where idmedico=? ";
	private static final String selezionaPrescrizioniPaziente = "select * from prescrizione where idpaziente=? ";
	private static final String selezionaFarmaciPrescrizione = "select * from farmacoprescritto where idprescrizione=? ";
	private static final String selezionaNumeriPrescrizioni = "select numero from prescrizione";
	private static final String selezionaUltimoId = "SELECT MAX(id) FROM pharmadb.prescrizione";
	private static final String selezionaUltimoIdFarmacoPrescritto = "SELECT MAX(id) FROM farmacoprescritto";

	public DbPrescrizioneServiceImpl(UtenteService utenteService, FarmacoService farmacoService) {
		this.utenteService = utenteService;
		this.farmacoService = farmacoService;
	}

	@Override
	public Prescrizione trovaPrescrizioneDaId(int id) throws BusinessException {
		Prescrizione result = new Prescrizione();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(selezionaPrescrizioneById);
			r = ps.executeQuery();

			result.setId(r.getInt(1));
			result.setNumero(r.getString(2));
			// Conversione da Date a LocalDate
			result.setData(Instant.ofEpochMilli(r.getDate(3).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
			result.setFirma(r.getString(4));
			result.setCostoPrescrizione(r.getDouble(5));
			result.setEvasione(r.getString(6));

			Utente utente = utenteService.trovaUtenteDaId(r.getInt(7));
			result.setPaziente((Paziente) utente);
			utente = utenteService.trovaUtenteDaId(r.getInt(8));
			result.setMedico((Medico) utente);

			r.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	public FarmacoPrescritto trovaFarmacoPrescrittoDaId(int id) throws BusinessException {
		FarmacoPrescritto result = new FarmacoPrescritto();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(selezionaFarmacoPrescrittoById);
			ps.setInt(1, id);
			r = ps.executeQuery();

			result.setId(r.getInt(1));
			result.setQuantita(r.getInt(2));

			Prescrizione prescrizione = prescrizioneService.trovaPrescrizioneDaId(r.getInt(3));
			result.setPrescrizione(prescrizione);

			Farmaco farmaco = farmacoService.trovaFarmacoDaId(r.getInt(4));
			result.setFarmaco(farmaco);

			r.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	public List<Prescrizione> trovaTuttePrescrizioni() throws BusinessException {
		List<Prescrizione> result = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(selezionaPrescrizioni);
			r = ps.executeQuery();

			while (r.next()) {
				Prescrizione prescrizione = new Prescrizione();
				prescrizione.setId(r.getInt(1));
				prescrizione.setNumero(r.getString(2));
				// Conversione da Date a LocalDate
				prescrizione.setData(
						Instant.ofEpochMilli(r.getDate(3).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				prescrizione.setFirma(r.getString(4));
				prescrizione.setCostoPrescrizione(r.getDouble(5));
				prescrizione.setEvasione(r.getString(6));
				prescrizione.setPaziente((Paziente) utenteService.trovaUtenteDaId(r.getInt(7)));
				prescrizione.setMedico((Medico) utenteService.trovaUtenteDaId(r.getInt(8)));

				result.add(prescrizione);
			}
			r.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	public List<Prescrizione> trovaPrescrizioniMedico(Medico medico) throws BusinessException {
		List<Prescrizione> result = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(selezionaPrescrizioniMedico);
			ps.setInt(1, medico.getId());
			r = ps.executeQuery();

			while (r.next()) {
				Prescrizione prescrizione = new Prescrizione();
				prescrizione.setId(r.getInt(1));
				prescrizione.setNumero(r.getString(2));
				// Conversione da Date a LocalDate
				prescrizione.setData(
						Instant.ofEpochMilli(r.getDate(3).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				prescrizione.setFirma(r.getString(4));
				prescrizione.setCostoPrescrizione(r.getDouble(5));
				prescrizione.setEvasione(r.getString(6));
				prescrizione.setPaziente((Paziente) utenteService.trovaUtenteDaId(r.getInt(7)));
				prescrizione.setMedico((Medico) utenteService.trovaUtenteDaId(r.getInt(8)));

				result.add(prescrizione);
			}
			r.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	public List<Prescrizione> trovaPrescrizioniPaziente(Paziente paziente) throws BusinessException {
		List<Prescrizione> result = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(selezionaPrescrizioniPaziente);
			ps.setInt(1, paziente.getId());
			r = ps.executeQuery();

			while (r.next()) {
				Prescrizione prescrizione = new Prescrizione();
				prescrizione.setId(r.getInt(1));
				prescrizione.setNumero(r.getString(2));
				// Conversione da Date a LocalDate
				prescrizione.setData(
						Instant.ofEpochMilli(r.getDate(3).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				prescrizione.setFirma(r.getString(4));
				prescrizione.setCostoPrescrizione(r.getDouble(5));
				prescrizione.setEvasione(r.getString(6));
				prescrizione.setPaziente((Paziente) utenteService.trovaUtenteDaId(r.getInt(7)));
				prescrizione.setMedico((Medico) utenteService.trovaUtenteDaId(r.getInt(8)));

				// Calcolo del costo della prescrizione
				ResultSet r1 = null;
				Double costo;

				PreparedStatement ps1 = c.prepareStatement(selezionaFarmaciPrescrizione);
				ps1.setInt(1, r.getInt(1));
				r1 = ps1.executeQuery();

				while (r1.next()) {
					Farmaco farmaco = farmacoService.trovaFarmacoDaId(r1.getInt(4));
					costo = farmaco.getCosto() * (r1.getInt(2));
					costo = (double) Math.round(costo * 100);
					costo = costo / 100;
					prescrizione.setCostoPrescrizione(Math.round(prescrizione.getCostoPrescrizione() * 100) / 100);
					prescrizione.setCostoPrescrizione(prescrizione.getCostoPrescrizione() + costo);
				}

				result.add(prescrizione);
			}
			r.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	public List<FarmacoPrescritto> trovaFarmaciPrescritti(Prescrizione prescrizione) throws BusinessException {
		List<FarmacoPrescritto> result = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {
			PreparedStatement ps = c.prepareStatement(selezionaFarmaciPrescrizione);
			ps.setInt(1, prescrizione.getId());
			r = ps.executeQuery();

			while (r.next()) {
				FarmacoPrescritto farmacoPrescritto = new FarmacoPrescritto();
				farmacoPrescritto.setId(r.getInt(1));
				farmacoPrescritto.setQuantita(r.getInt(2));
				farmacoPrescritto.setPrescrizione(prescrizione);
				farmacoPrescritto.setFarmaco((Farmaco) farmacoService.trovaFarmacoDaId(r.getInt(4)));

				result.add(farmacoPrescritto);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	public void creaPrescrizione(Prescrizione prescrizione) throws BusinessException {
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(inserisciPrescrizione);

			// Conversione da LocalDate a Date
			Date data = java.sql.Date.valueOf(prescrizione.getData());

			PreparedStatement ps1 = c.prepareStatement(selezionaUltimoId);
			r = ps1.executeQuery();

			while (r.next()) {

				prescrizione.setId(r.getInt(1) + 1);
				ps.setString(1, prescrizione.getNumero());
				ps.setDate(2, data);
				ps.setString(3, prescrizione.getFirma());
				ps.setDouble(4, prescrizione.getCostoPrescrizione());
				ps.setString(5, prescrizione.getEvasione());
				ps.setInt(6, prescrizione.getPaziente().getId());
				ps.setInt(7, prescrizione.getMedico().getId());

				ps.executeUpdate();

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@Override
	public void creaFarmacoPrescritto(FarmacoPrescritto nuovoFarmacoPrescritto) throws BusinessException {
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(inserisciFarmacoPrescritto);

			PreparedStatement ps1 = c.prepareStatement(selezionaUltimoIdFarmacoPrescritto);
			r = ps1.executeQuery();

			while (r.next()) {
				nuovoFarmacoPrescritto.setId(r.getInt(1) + 1);
				ps.setInt(1, nuovoFarmacoPrescritto.getQuantita());
				ps.setInt(2, nuovoFarmacoPrescritto.getPrescrizione().getId());
				ps.setInt(3, nuovoFarmacoPrescritto.getFarmaco().getId());

				ps.executeUpdate();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@Override
	public void aggiornaPrescrizione(Prescrizione prescrizione) throws BusinessException {

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(aggiornaPrescrizione);

			// Conversione da LocalDate a Date
			Date data = java.sql.Date.valueOf(prescrizione.getData());

			ps.setString(1, prescrizione.getNumero());
			ps.setDate(2, data);
			ps.setString(3, prescrizione.getFirma());
			ps.setDouble(4, prescrizione.getCostoPrescrizione());
			ps.setString(5, prescrizione.getEvasione());
			ps.setInt(6, prescrizione.getPaziente().getId());
			ps.setInt(7, prescrizione.getMedico().getId());
			ps.setInt(8, prescrizione.getId());

			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void eliminaPrescrizione(Prescrizione prescrizione) throws BusinessException {

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(eliminaPrescrizione);

			ps.setInt(1, prescrizione.getId());

			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void eliminaFarmaciPrescritti(Prescrizione prescrizione) throws BusinessException {

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(eliminaFarmaciPrescritti);

			ps.setInt(1, prescrizione.getId());

			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void eliminaFarmacoPrescritto(FarmacoPrescritto farmacoPrescritto) throws BusinessException {

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(eliminaFarmacoPrescritto);

			ps.setInt(1, farmacoPrescritto.getId());

			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public List<String> numeriPrescrizioni() throws BusinessException {
		List<String> result = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(selezionaNumeriPrescrizioni);
			r = ps.executeQuery();

			while (r.next()) {
				String prescrizione;

				prescrizione = r.getString(1);

				result.add(prescrizione);
			}
			r.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		return result;
	}

}
