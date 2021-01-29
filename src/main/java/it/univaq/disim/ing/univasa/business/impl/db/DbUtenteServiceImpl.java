package it.univaq.disim.ing.univasa.business.impl.db;

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

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.UtenteNotFoundException;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.domain.*;

public class DbUtenteServiceImpl implements UtenteService {

	private static final String url = "jdbc:mysql://localhost:3306/univasa?noAccessToProcedureBodies=true&serverTimezone=Europe/Rome";
	private static final String user = "dbuser";
	private static final String pwd = "sql_password123";

	/* -------------------------------------------------------------------------------------------------------------------------------------------- */

	// trovaUtenteById --- dobbiamo sapere che tipo di utente si tratta
	private static final String trovaUtente = "select * from utente where id=?";
	// trovaTuttiUtenti
	private static final String trovaTuttiUtenti = "select * from utente";
	// creaAmministratore
	private static final String creaAmministratore = "insert into utente (nome, cognome, email, username, password, telefono, data_nascita, professione, nome_universita, dipartimento, tipo_utente) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'amministratore')";
	// creaOperatore
	private static final String creaOperatore = "insert into operatore (nome, cognome, email, username, password, telefono, data_nascita, professione, nome_universita, dipartimento, tipo_utente) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'operatore')";
	// creaElettore
	private static final String creaElettore = "insert into elettore (nome, cognome, email, username, password, telefono, data_nascita, professione, nome_universita, dipartimento, matricola, tipo_utente) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'elettore')";
	// creaElettoreInSede & creaElettoreOnline
	private static final String prenotazioneInSede = "insert into prenotazione (id_utente,id_evento,tipo_prenotazione,stato) values (?,?,'presenza','no')";
	private static final String prenotazioneOnline = "insert into prenotazione (id_utente,id_evento,tipo_prenotazione) values (?,?,'online')";
	// creaCandidato
	private static final String creaCandidato = "insert into candidatura (id_utente,id_evento,voti_ricevuti) values (?,?,0)";
	// accetta certificato & rifiutaCertificato
	private static final String accettaCertificato = "update set stato='no' from prenotazione where id_evento=? and id_utente=?";
	private static final String rifiutaCertificato = "delete from prenotazione where id_evento=? and id_utente=?";
	// gestionePrenotazioni
	private static final String gestionePrenotazioni = "select * from prenotazione where id_evento=?";
	// visualizzaCandidati
	private static final String visualizzaCandidati = "select * from utente u join candidatura c on u.id=c.id_utente where c.id_evento=?";
	// viusalizzaEventi
	private static final String visualizzaEventi = "select * from evento";
	// modificaOperatore
	private static final String modificaOperatore = "update set telefono=?, email=?, nome_universita=?, dipartimento=? from utente where id=?";
	// visualizzaTurnazioni
	private static final String visualizzaTurnazioni = "select * from turnazione where id_utente=?";
	// visualizzaPrenotatiInSede
	private static final String visualizzaPrenotatiInSede = "select * from prenotazione where id_evento=? and tipo_prenotazione='presenza'";
	// vota
	private static final String vota = "update set voti_ricevuti=voti_ricevuti+1 from candidatura where id_utente=? and id_evento=?";
	private static final String aggiornaPrenotazione = "update set stato='si' from prenotazione where id_utente=? and id_evento=?";
	// eliminaUtente
	private static final String eliminaUtente = "delete from utente where id_utente=?";


	@Override
	public Utente autenticazione(String username, String password) throws UtenteNotFoundException, BusinessException {
		ResultSet r = null;
		Utente utente = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(trovaTuttiUtenti);
			r = ps.executeQuery();

			while (r.next()) {

				if (r.getString(10).equals(username) && r.getString(11).equals(password)) {

					switch (r.getString(15)) {

					case "elettore":
						utente = new Elettore();
						break;
					case "operatore":
						utente = new Operatore();
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

						utente.setId(r.getInt(1));
						utente.setUsername(username);
						utente.setPassword(password);
						utente.setNome(r.getString(2));
						utente.setCognome(r.getString(3));
						utente.setCf(r.getString(8));
						// Conversione da Date a LocalDate
						utente.setDataNascita(Instant.ofEpochMilli(r.getDate(6).getTime())
								.atZone(ZoneId.systemDefault()).toLocalDate());
						utente.setLuogoNascita(r.getString(7));
						utente.setResidenza(r.getString(9));
						utente.setTelefono(r.getString(4));
						utente.setSesso(r.getString(5));
					} else {
						throw new BusinessException("Errore nella lettura del database");
					}
					if (utente instanceof Medico) {
						((Medico) utente).setCodiceAlboMedici(r.getString(13));
						((Medico) utente).setTipologia(TipologiaMedico.valueOf(r.getString(14)));
					}
					if (utente instanceof Paziente) {
						((Paziente) utente).setSaldo(r.getDouble(12));
					}
					return utente;
				}
			}
			throw new UtenteNotFoundException();
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
		return utente;
	}

	@Override
	public Utente trovaUtenteDaId(int id) throws UtenteNotFoundException, BusinessException {
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(trovaUtenteDaId);
			ps.setInt(1, id);
			r = ps.executeQuery();

			while (r.next()) {
				if (r.getString(15).equals("medico")) {
					Medico medico = new Medico();

					medico.setId(id);
					medico.setUsername(r.getString(10));
					medico.setPassword(r.getString(11));
					medico.setNome(r.getString(2));
					medico.setCognome(r.getString(3));
					medico.setCf(r.getString(8));
					// Conversione da Date a LocalDate
					medico.setDataNascita(
							Instant.ofEpochMilli(r.getDate(6).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
					medico.setLuogoNascita(r.getString(7));
					medico.setResidenza(r.getString(9));
					medico.setTelefono(r.getString(4));
					medico.setSesso(r.getString(5));
					medico.setCodiceAlboMedici(r.getString(13));
					medico.setTipologia(TipologiaMedico.valueOf(r.getString(14)));
					r.close();
					return medico;
				}

				if (r.getString(15).equals("paziente")) {
					Paziente paziente = new Paziente();

					paziente.setId(id);
					paziente.setUsername(r.getString(10));
					paziente.setPassword(r.getString(11));
					paziente.setNome(r.getString(2));
					paziente.setCognome(r.getString(3));
					paziente.setCf(r.getString(8));
					// Conversione da Date a LocalDate
					paziente.setDataNascita(
							Instant.ofEpochMilli(r.getDate(6).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
					paziente.setLuogoNascita(r.getString(7));
					paziente.setResidenza(r.getString(9));
					paziente.setTelefono(r.getString(4));
					paziente.setSesso(r.getString(5));
					paziente.setSaldo(r.getDouble(12));
					r.close();
					return paziente;
				}

				if (r.getString(15).equals("farmacista")) {
					Farmacista farmacista = new Farmacista();

					farmacista.setId(id);
					farmacista.setUsername(r.getString(10));
					farmacista.setPassword(r.getString(11));
					farmacista.setNome(r.getString(2));
					farmacista.setCognome(r.getString(3));
					farmacista.setCf(r.getString(8));
					// Conversione da Date a LocalDate
					farmacista.setDataNascita(
							Instant.ofEpochMilli(r.getDate(6).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
					farmacista.setLuogoNascita(r.getString(7));
					farmacista.setResidenza(r.getString(9));
					farmacista.setTelefono(r.getString(4));
					farmacista.setSesso(r.getString(5));
					r.close();
					return farmacista;
				}

				if (r.getString(15).equals("amministratore")) {
					Amministratore amministratore = new Amministratore();

					amministratore.setId(id);
					amministratore.setUsername(r.getString(10));
					amministratore.setPassword(r.getString(11));
					amministratore.setNome(r.getString(2));
					amministratore.setCognome(r.getString(3));
					amministratore.setCf(r.getString(8));
					// Conversione da Date a LocalDate
					amministratore.setDataNascita(
							Instant.ofEpochMilli(r.getDate(6).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
					amministratore.setLuogoNascita(r.getString(7));
					amministratore.setResidenza(r.getString(9));
					amministratore.setTelefono(r.getString(4));
					amministratore.setSesso(r.getString(5));
					r.close();
					return amministratore;
				}
			}
			r.close();
			throw new UtenteNotFoundException();
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
		return null;
	}

	@Override
	public List<Utente> trovaTuttiUtenti() throws BusinessException {
		List<Utente> result = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(trovaTuttiUtenti);
			r = ps.executeQuery();

			while (r.next()) {
				Utente utente = new Utente();

				utente.setId(r.getInt(1));
				utente.setNome(r.getString(2));
				utente.setCognome(r.getString(3));
				utente.setCf(r.getString(8));
				// Conversione da Date a LocalDate
				utente.setDataNascita(
						Instant.ofEpochMilli(r.getDate(6).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				utente.setLuogoNascita(r.getString(7));
				utente.setResidenza(r.getString(9));
				utente.setTelefono(r.getString(4));
				utente.setSesso(r.getString(5));

				result.add(utente);

				if (result instanceof Medico) {
					((Medico) result).setCodiceAlboMedici(r.getString(13));
					((Medico) result).setTipologia(TipologiaMedico.valueOf(r.getString(14)));
					return result;
				} else if (result instanceof Paziente) {
					((Paziente) result).setSaldo(r.getDouble(12));
				}
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
	public List<Amministratore> trovaTuttiAmministratori() throws BusinessException {
		return null;
	}

	@Override
	public List<Operatore> trovaTuttiOperatori() throws BusinessException {
		return null;
	}

	@Override
	public List<Elettore> trovaTuttiElettori() throws BusinessException {
		return null;
	}

	@Override
	public void creaAmministratore(Amministratore amministratore) throws BusinessException {

	}

	@Override
	public void creaOperatore(Operatore operatore) throws BusinessException {

	}

	@Override
	public void creaElettore(Elettore elettore) throws BusinessException {

	}

	@Override
	public void creaCandidato(Candidato candidato) throws BusinessException {

	}

	@Override
	public List<Elettore> gestionePrenotazioni(Evento evento) throws BusinessException {
		return null;
	}

	@Override
	public List<Candidato> visualizzaCandidati(Evento evento) throws BusinessException {
		return null;
	}


	@Override
	public void modificaOperatore(Operatore operatore) throws BusinessException { }



	@Override
	public void accettaCertificato(String certificato) throws BusinessException {

	}

	@Override
	public void rifiutaCertificato(String certificato) throws BusinessException {

	}

	@Override
	public List<Elettore> visualizzaPrenotatiInSede(Evento evento) throws BusinessException {
		return null;
	}

	@Override
	public void prenotazioneInSede(Elettore elettore, Evento evento) throws BusinessException {

	}

	@Override
	public void prenotazioneOnline(Elettore elettore, Evento evento) throws BusinessException {

	}

	@Override
	public void vota(ElettoreOnline elettoreOnline, Evento evento) throws BusinessException {

	}


	@Override
	public void eliminaUtente(Utente utente) throws UtenteNotFoundException, BusinessException {
		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(eliminaUtente);

			ps.setInt(1, utente.getId());

			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}



	/*
	@Override
	public List<Farmacista> trovaTuttiFarmacisti() throws BusinessException {
		List<Farmacista> result = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(trovaFarmacisti);
			r = ps.executeQuery();

			while (r.next()) {
				Farmacista farmacista = new Farmacista();

				farmacista.setId(r.getInt(1));
				farmacista.setUsername(r.getString(10));
				farmacista.setPassword(r.getString(11));
				farmacista.setNome(r.getString(2));
				farmacista.setCognome(r.getString(3));
				farmacista.setCf(r.getString(8));
				// Conversione da Date a LocalDate
				farmacista.setDataNascita(
						Instant.ofEpochMilli(r.getDate(6).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				farmacista.setLuogoNascita(r.getString(7));
				farmacista.setResidenza(r.getString(9));
				farmacista.setTelefono(r.getString(4));
				farmacista.setSesso(r.getString(5));
				result.add(farmacista);
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
	public List<Paziente> trovaTuttiPazienti() throws BusinessException {
		List<Paziente> result = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(trovaPazienti);
			r = ps.executeQuery();

			while (r.next()) {
				Paziente paziente = new Paziente();

				paziente.setId(r.getInt(1));
				paziente.setUsername(r.getString(10));
				paziente.setPassword(r.getString(11));
				paziente.setNome(r.getString(2));
				paziente.setCognome(r.getString(3));
				paziente.setCf(r.getString(8));
				// Conversione da Date a LocalDate
				paziente.setDataNascita(
						Instant.ofEpochMilli(r.getDate(6).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				paziente.setLuogoNascita(r.getString(7));
				paziente.setResidenza(r.getString(9));
				paziente.setTelefono(r.getString(4));
				paziente.setSesso(r.getString(5));
				paziente.setSaldo(r.getDouble(12));
				result.add(paziente);
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
	public List<Medico> trovaTuttiMedici() throws BusinessException {
		List<Medico> result = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(trovaMedici);
			r = ps.executeQuery();

			while (r.next()) {
				Medico medico = new Medico();

				medico.setId(r.getInt(1));
				medico.setUsername(r.getString(10));
				medico.setPassword(r.getString(11));
				medico.setNome(r.getString(2));
				medico.setCognome(r.getString(3));
				medico.setCf(r.getString(8));
				// Conversione da Date a LocalDate
				medico.setDataNascita(
						Instant.ofEpochMilli(r.getDate(6).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				medico.setLuogoNascita(r.getString(7));
				medico.setResidenza(r.getString(9));
				medico.setTelefono(r.getString(4));
				medico.setSesso(r.getString(5));
				medico.setCodiceAlboMedici(r.getString(13));
				medico.setTipologia(TipologiaMedico.valueOf(r.getString(14)));
				result.add(medico);
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
	public void creaFarmacista(Farmacista farmacista) throws BusinessException {

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(inserisciFarmacista);

			// Conversione da LocalDate a Date
			Date data = java.sql.Date.valueOf(farmacista.getDataNascita());

			ps.setString(1, farmacista.getUsername());
			ps.setString(2, farmacista.getPassword());
			ps.setString(3, farmacista.getNome());
			ps.setString(4, farmacista.getCognome());
			ps.setString(5, farmacista.getCf());
			ps.setDate(6, data);
			ps.setString(7, farmacista.getLuogoNascita());
			ps.setString(8, farmacista.getResidenza());
			ps.setString(9, farmacista.getTelefono());
			ps.setString(10, farmacista.getSesso());

			ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void creaPaziente(Paziente paziente) throws BusinessException {

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(inserisciPaziente);

			// Conversione da LocalDate a Date
			Date data = java.sql.Date.valueOf(paziente.getDataNascita());

			ps.setString(1, paziente.getUsername());
			ps.setString(2, paziente.getPassword());
			ps.setString(3, paziente.getNome());
			ps.setString(4, paziente.getCognome());
			ps.setString(5, paziente.getCf());
			ps.setDate(6, data);
			ps.setString(7, paziente.getLuogoNascita());
			ps.setString(8, paziente.getResidenza());
			ps.setString(9, paziente.getTelefono());
			ps.setString(10, paziente.getSesso());
			ps.setDouble(11, paziente.getSaldo());

			ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void creaMedico(Medico medico) throws BusinessException {

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(inserisciMedico);

			// Conversione da LocalDate a Date
			Date data = java.sql.Date.valueOf(medico.getDataNascita());

			ps.setString(1, medico.getUsername());
			ps.setString(2, medico.getPassword());
			ps.setString(3, medico.getNome());
			ps.setString(4, medico.getCognome());
			ps.setString(5, medico.getCf());
			ps.setDate(6, data);
			ps.setString(7, medico.getLuogoNascita());
			ps.setString(8, medico.getResidenza());
			ps.setString(9, medico.getTelefono());
			ps.setString(10, medico.getSesso());
			ps.setString(11, medico.getCodiceAlboMedici());
			ps.setString(12, medico.getTipologia().toString());

			ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public void aggiornaFarmacista(Farmacista farmacista) throws BusinessException {

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(modificaFarmacista);

			// Conversione da LocalDate a Date
			Date data = java.sql.Date.valueOf(farmacista.getDataNascita());

			ps.setString(1, farmacista.getUsername());
			ps.setString(2, farmacista.getPassword());
			ps.setString(3, farmacista.getNome());
			ps.setString(4, farmacista.getCognome());
			ps.setString(5, farmacista.getCf());
			ps.setDate(6, data);
			ps.setString(7, farmacista.getLuogoNascita());
			ps.setString(8, farmacista.getResidenza());
			ps.setString(9, farmacista.getTelefono());
			ps.setString(10, farmacista.getSesso());
			ps.setInt(11, farmacista.getId());

			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public void aggiornaPaziente(Paziente paziente) throws BusinessException {

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(modificaPaziente);

			// Conversione da LocalDate a Date
			Date data = java.sql.Date.valueOf(paziente.getDataNascita());

			ps.setString(1, paziente.getUsername());
			ps.setString(2, paziente.getPassword());
			ps.setString(3, paziente.getNome());
			ps.setString(4, paziente.getCognome());
			ps.setString(5, paziente.getCf());
			ps.setDate(6, data);
			ps.setString(7, paziente.getLuogoNascita());
			ps.setString(8, paziente.getResidenza());
			ps.setString(9, paziente.getTelefono());
			ps.setString(10, paziente.getSesso());
			ps.setDouble(11, paziente.getSaldo());
			ps.setInt(12, paziente.getId());

			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void aggiornaMedico(Medico medico) throws BusinessException {

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(modificaMedico);

			// Conversione da LocalDate a Date
			Date data = java.sql.Date.valueOf(medico.getDataNascita());

			ps.setString(1, medico.getUsername());
			ps.setString(2, medico.getPassword());
			ps.setString(3, medico.getNome());
			ps.setString(4, medico.getCognome());
			ps.setString(5, medico.getCf());
			ps.setDate(6, data);
			ps.setString(7, medico.getLuogoNascita());
			ps.setString(8, medico.getResidenza());
			ps.setString(9, medico.getTelefono());
			ps.setString(10, medico.getSesso());
			ps.setString(11, medico.getCodiceAlboMedici());
			ps.setString(12, medico.getTipologia().toString());
			ps.setInt(13, medico.getId());

			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	*/

}
