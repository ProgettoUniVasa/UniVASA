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
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Candidato;
import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.domain.Operatore;
import it.univaq.disim.ing.univasa.domain.Professione;
import it.univaq.disim.ing.univasa.domain.Utente;

public class DbUtenteServiceImpl implements UtenteService {

	private static final String url = "jdbc:mysql://localhost:3306/univasa?noAccessToProcedureBodies=true&serverTimezone=Europe/Rome";
	private static final String user = "root";
	private static final String pwd = "";

	// trovaUtenteById
	private static final String trovaUtenteDaId = "select * from utente where id=?";
	// trovaTuttiUtenti
	private static final String trovaTuttiUtenti = "select * from utente";
	private static final String trovaTuttiAmministratori = "select * from utente where tipo_utente='amministratore'";
	private static final String trovaTuttiOperatori = "select * from utente where tipo_utente='operatore'";
	private static final String trovaTuttiElettori = "select * from utente where tipo_utente='elettore'";
	private static final String trovaTuttiCandidati = "select * from candidato where id_evento=?";
	private static final String trovaEmailOperatori = "select email from utente where tipo_utente='operatore'";
	// creaAmministratore
	private static final String creaAmministratore = "insert into utente (nome, cognome, email, username, password, telefono, data_nascita, professione, nome_universita, dipartimento, tipo_utente) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'amministratore')";
	// creaOperatore
	private static final String creaOperatore = "insert into utente (nome, cognome, email, username, password, telefono, data_nascita, professione, nome_universita, dipartimento, tipo_utente) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'operatore')";
	// creaElettore
	private static final String creaElettore = "insert into utente (nome, cognome, email, username, password, telefono, data_nascita, professione, nome_universita, dipartimento, matricola, tipo_utente) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'elettore')";
	// creaCandidato
	private static final String creaCandidato = "insert into candidato (nome,cognome,email,telefono,nome_universita,data_nascita,id_evento,voti_ricevuti) values (?,?,?,?,?,?,?,0)";
	// accetta certificato & rifiutaCertificato
	private static final String accettaCertificato = "update set stato='no' from prenotazione where id_evento=? and id_utente=?";
	private static final String rifiutaCertificato = "delete from prenotazione where id_evento=? and id_utente=?";
	// gestionePrenotazioni
	private static final String gestionePrenotazioni = "select utente.id, utente.nome, utente.cognome, utente.email, utente.username, utente.password, utente.telefono, utente.data_nascita, utente.professione, utente.nome_universita, utente.dipartimento, utente.matricola\r\n"
			+ " from prenotazione join utente on prenotazione.id_utente = utente.id join evento on prenotazione.id_evento = evento.id where id_evento=? and (tipo_prenotazione='in presenza' or (tipo_prenotazione='online' and prenotazione.stato is not null))";
	// modificaAmministratore
	private static final String modificaAmministratore = "update utente set telefono=?, email=?, nome_universita=?, dipartimento=? where id=?";
	// modificaOperatore
	private static final String modificaOperatore = "update utente set telefono=?, email=?, nome_universita=?, dipartimento=? where id=?";
	// visualizzaTurnazioni
	private static final String visualizzaTurnazioni = "select * from turnazione where id_utente=?";
	// vota
	private static final String vota = "update from candidato set voti_ricevuti=voti_ricevuti+1 where id_utente=? and id_evento=?";
	private static final String aggiornaPrenotazione = "update prenotazione set stato='si' where id_utente=? and id_evento=?";
	// eliminaUtente
	private static final String eliminaUtente = "delete from utente where id=?";
	private static final String eliminaCandidato = "delete from candidato where email=?";
	// utenteDaEmail
	private static final String utenteDaEmail = "select * from utente where email=?";

	@Override
	public Utente autenticazione(String username, String password) throws UtenteNotFoundException, BusinessException {
		ResultSet r = null;
		Utente utente = null;
		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {
			PreparedStatement ps = c.prepareStatement(trovaTuttiUtenti);
			r = ps.executeQuery();
			while (r.next()) {
				if (r.getString(5).equals(username) && r.getString(6).equals(password)) {
					switch (r.getString(13)) {
					case "elettore":
						utente = new Elettore();
						break;
					case "operatore":
						utente = new Operatore();
						break;
					case "amministratore":
						utente = new Amministratore();
						break;
					default:
						break;
					}
					if (utente != null) {
						utente.setId(r.getLong(1));
						utente.setUsername(username);
						utente.setPassword(password);
						utente.setNome(r.getString(2));
						utente.setCognome(r.getString(3));
						utente.setEmail(r.getString(4));
						// Conversione da Date a LocalDate
						utente.setData_nascita(Instant.ofEpochMilli(r.getDate(8).getTime())
								.atZone(ZoneId.systemDefault()).toLocalDate());
						utente.setProfessione(Professione.valueOf(r.getString(9)));
						utente.setNome_università(r.getString(10));
						utente.setDipartimento(r.getString(11));
						utente.setTelefono(r.getString(7));
					} else {
						throw new BusinessException("Errore nella lettura del database");
					}
					if (utente instanceof Elettore) {
						((Elettore) utente).setMatricola(r.getString(12));
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
	public Utente trovaUtenteDaId(Long id) throws UtenteNotFoundException, BusinessException {
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(trovaUtenteDaId);
			ps.setLong(1, id);
			r = ps.executeQuery();

			while (r.next()) {
				if (r.getString(13).equals("amministratore")) {
					Amministratore amministratore = new Amministratore();

					amministratore.setId(id);
					amministratore.setUsername(r.getString(5));
					amministratore.setPassword(r.getString(6));
					amministratore.setNome(r.getString(2));
					amministratore.setCognome(r.getString(3));
					amministratore.setEmail(r.getString(4));
					// Conversione da Date a LocalDate
					amministratore.setData_nascita(
							Instant.ofEpochMilli(r.getDate(8).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
					amministratore.setProfessione(Professione.valueOf(r.getString(9)));
					amministratore.setTelefono(r.getString(7));
					amministratore.setNome_università(r.getString(10));
					amministratore.setDipartimento(r.getString(11));
					r.close();
					return amministratore;
				}

				if (r.getString(13).equals("operatore")) {
					Operatore operatore = new Operatore();

					operatore.setId(id);
					operatore.setUsername(r.getString(5));
					operatore.setPassword(r.getString(6));
					operatore.setNome(r.getString(2));
					operatore.setCognome(r.getString(3));
					operatore.setEmail(r.getString(4));
					// Conversione da Date a LocalDate
					operatore.setData_nascita(
							Instant.ofEpochMilli(r.getDate(8).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
					operatore.setProfessione(Professione.valueOf(r.getString(9)));
					operatore.setTelefono(r.getString(7));
					operatore.setNome_università(r.getString(10));
					operatore.setDipartimento(r.getString(11));
					r.close();
					return operatore;
				}

				if (r.getString(13).equals("elettore")) {
					Elettore elettore = new Elettore();

					elettore.setId(id);
					elettore.setUsername(r.getString(5));
					elettore.setPassword(r.getString(6));
					elettore.setNome(r.getString(2));
					elettore.setCognome(r.getString(3));
					elettore.setEmail(r.getString(4));
					// Conversione da Date a LocalDate
					elettore.setData_nascita(
							Instant.ofEpochMilli(r.getDate(8).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
					elettore.setProfessione(Professione.valueOf(r.getString(9)));
					elettore.setTelefono(r.getString(7));
					elettore.setNome_università(r.getString(10));
					elettore.setDipartimento(r.getString(11));
					elettore.setMatricola(r.getString(12));
					r.close();
					return elettore;
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

				utente.setId(r.getLong(1));
				utente.setUsername(r.getString(5));
				utente.setPassword(r.getString(6));
				utente.setNome(r.getString(2));
				utente.setCognome(r.getString(3));
				utente.setEmail(r.getString(4));
				// Conversione da Date a LocalDate
				utente.setData_nascita(
						Instant.ofEpochMilli(r.getDate(8).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				utente.setProfessione(Professione.valueOf(r.getString(9)));
				utente.setNome_università(r.getString(10));
				utente.setDipartimento(r.getString(11));
				utente.setTelefono(r.getString(7));

				result.add(utente);

				if (utente instanceof Elettore) {
					((Elettore) utente).setMatricola(r.getString(12));
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
		List<Amministratore> result = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(trovaTuttiAmministratori);
			r = ps.executeQuery();

			while (r.next()) {
				Amministratore amministratore = new Amministratore();

				amministratore.setId(r.getLong(1));
				amministratore.setUsername(r.getString(5));
				amministratore.setPassword(r.getString(6));
				amministratore.setNome(r.getString(2));
				amministratore.setCognome(r.getString(3));
				amministratore.setEmail(r.getString(4));
				// Conversione da Date a LocalDate
				amministratore.setData_nascita(
						Instant.ofEpochMilli(r.getDate(8).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				amministratore.setProfessione(Professione.valueOf(r.getString(9)));
				amministratore.setNome_università(r.getString(10));
				amministratore.setDipartimento(r.getString(11));
				amministratore.setTelefono(r.getString(7));

				result.add(amministratore);

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
	public List<Operatore> trovaTuttiOperatori() throws BusinessException {
		List<Operatore> result = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(trovaTuttiOperatori);
			r = ps.executeQuery();

			while (r.next()) {
				Operatore operatore = new Operatore();

				operatore.setId(r.getLong(1));
				operatore.setUsername(r.getString(5));
				operatore.setPassword(r.getString(6));
				operatore.setNome(r.getString(2));
				operatore.setCognome(r.getString(3));
				operatore.setEmail(r.getString(4));
				// Conversione da Date a LocalDate
				operatore.setData_nascita(
						Instant.ofEpochMilli(r.getDate(8).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				operatore.setProfessione(Professione.valueOf(r.getString(9)));
				operatore.setNome_università(r.getString(10));
				operatore.setDipartimento(r.getString(11));
				operatore.setTelefono(r.getString(7));

				result.add(operatore);

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
	public List<Elettore> trovaTuttiElettori() throws BusinessException {
		List<Elettore> result = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(trovaTuttiElettori);
			r = ps.executeQuery();

			while (r.next()) {
				Elettore elettore = new Elettore();

				elettore.setId(r.getLong(1));
				elettore.setUsername(r.getString(5));
				elettore.setPassword(r.getString(6));
				elettore.setNome(r.getString(2));
				elettore.setCognome(r.getString(3));
				elettore.setEmail(r.getString(4));
				// Conversione da Date a LocalDate
				elettore.setData_nascita(
						Instant.ofEpochMilli(r.getDate(8).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				elettore.setProfessione(Professione.valueOf(r.getString(9)));
				elettore.setNome_università(r.getString(10));
				elettore.setDipartimento(r.getString(11));
				elettore.setTelefono(r.getString(7));
				elettore.setMatricola(r.getString(12));

				result.add(elettore);

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
	public void creaAmministratore(Amministratore amministratore) throws BusinessException {
		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(creaAmministratore);

			// Conversione da LocalDate a Date
			Date data = java.sql.Date.valueOf(amministratore.getData_nascita());

			ps.setString(1, amministratore.getNome());
			ps.setString(2, amministratore.getCognome());
			ps.setString(3, amministratore.getEmail());
			ps.setString(4, amministratore.getUsername());
			ps.setString(5, amministratore.getPassword());
			ps.setString(6, amministratore.getTelefono());
			ps.setDate(7, data);
			ps.setString(8, String.valueOf(amministratore.getProfessione()));
			ps.setString(9, amministratore.getNome_università());
			ps.setString(10, amministratore.getDipartimento());

			ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void creaOperatore(Operatore operatore) throws BusinessException {
		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(creaOperatore);

			// Conversione da LocalDate a Date
			Date data = java.sql.Date.valueOf(operatore.getData_nascita());

			ps.setString(1, operatore.getNome());
			ps.setString(2, operatore.getCognome());
			ps.setString(3, operatore.getEmail());
			ps.setString(4, operatore.getUsername());
			ps.setString(5, operatore.getPassword());
			ps.setString(6, operatore.getTelefono());
			ps.setDate(7, data);
			ps.setString(8, String.valueOf(operatore.getProfessione()));
			ps.setString(9, operatore.getNome_università());
			ps.setString(10, operatore.getDipartimento());

			ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void creaElettore(Elettore elettore) throws BusinessException {
		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(creaElettore);

			// Conversione da LocalDate a Date
			Date data = java.sql.Date.valueOf(elettore.getData_nascita());

			ps.setString(1, elettore.getNome());
			ps.setString(2, elettore.getCognome());
			ps.setString(3, elettore.getEmail());
			ps.setString(4, elettore.getUsername());
			ps.setString(5, elettore.getPassword());
			ps.setString(6, elettore.getTelefono());
			ps.setDate(7, data);
			ps.setString(8, String.valueOf(elettore.getProfessione()));
			ps.setString(9, elettore.getNome_università());
			ps.setString(10, elettore.getDipartimento());
			ps.setString(11, elettore.getMatricola());

			ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void creaCandidato(Candidato candidato, Evento evento) throws BusinessException {
		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(creaCandidato);

			// Conversione da LocalDate a Date
			Date data = java.sql.Date.valueOf(candidato.getDataNascita());

			ps.setString(1, candidato.getNome());
			ps.setString(2, candidato.getCognome());
			ps.setString(3, candidato.getEmail());
			ps.setString(4, candidato.getTelefono());
			ps.setString(5, candidato.getNomeUniversita());
			ps.setDate(6, data);
			ps.setLong(7, evento.getId());

			ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public List<Elettore> gestionePrenotazioni(Evento evento) throws BusinessException {
		List<Elettore> elettori = new ArrayList<Elettore>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(gestionePrenotazioni);
			ps.setLong(1, evento.getId());
			r = ps.executeQuery();

			while (r.next()) {
				Elettore elettore = new Elettore();
				elettore.setId(r.getLong(1));
				elettore.setNome(r.getString(2));
				elettore.setCognome(r.getString(3));
				elettore.setEmail(r.getString(4));
				elettore.setUsername(r.getString(5));
				elettore.setPassword(r.getString(6));
				elettore.setTelefono(r.getString(7));
				// Conversione da Date a LocalDate
				elettore.setData_nascita(
						Instant.ofEpochMilli(r.getDate(8).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				elettore.setProfessione(Professione.valueOf(r.getString(9)));
				elettore.setNome_università(r.getString(10));
				elettore.setDipartimento(r.getString(11));
				elettore.setMatricola(r.getString(12));
				elettori.add(elettore);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return elettori;
	}

	@Override
	public void modificaAmministratore(Amministratore amministratore) throws BusinessException {
		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(modificaAmministratore);

			ps.setString(1, amministratore.getTelefono());
			ps.setString(2, amministratore.getEmail());
			ps.setString(3, amministratore.getNome_università());
			ps.setString(4, amministratore.getDipartimento());
			ps.setLong(5, amministratore.getId());

			ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void modificaOperatore(Operatore operatore) throws BusinessException {
		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(modificaOperatore);

			ps.setString(1, operatore.getTelefono());
			ps.setString(2, operatore.getEmail());
			ps.setString(3, operatore.getNome_università());
			ps.setString(4, operatore.getDipartimento());
			ps.setLong(5, operatore.getId());

			ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void accettaCertificato(String certificato) throws BusinessException {
	}

	@Override
	public void rifiutaCertificato(String certificato) throws BusinessException {
	}

	@Override
	public void eliminaUtente(Utente utente) throws UtenteNotFoundException, BusinessException {
		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(eliminaUtente);

			ps.setLong(1, utente.getId());

			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public Utente utenteDaEmail(String email) {
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(utenteDaEmail);
			ps.setString(1, email);
			r = ps.executeQuery();

			while (r.next()) {
				if (r.getString(13).equals("amministratore")) {
					Amministratore amministratore = new Amministratore();

					amministratore.setId(r.getLong(1));
					amministratore.setUsername(r.getString(5));
					amministratore.setPassword(r.getString(6));
					amministratore.setNome(r.getString(2));
					amministratore.setCognome(r.getString(3));
					amministratore.setEmail(email);
					// Conversione da Date a LocalDate
					amministratore.setData_nascita(
							Instant.ofEpochMilli(r.getDate(8).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
					amministratore.setProfessione(Professione.valueOf(r.getString(9)));
					amministratore.setTelefono(r.getString(7));
					amministratore.setNome_università(r.getString(10));
					amministratore.setDipartimento(r.getString(11));
					r.close();
					return amministratore;
				}

				if (r.getString(13).equals("operatore")) {
					Operatore operatore = new Operatore();

					operatore.setId(r.getLong(1));
					operatore.setUsername(r.getString(5));
					operatore.setPassword(r.getString(6));
					operatore.setNome(r.getString(2));
					operatore.setCognome(r.getString(3));
					operatore.setEmail(email);
					// Conversione da Date a LocalDate
					operatore.setData_nascita(
							Instant.ofEpochMilli(r.getDate(8).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
					operatore.setProfessione(Professione.valueOf(r.getString(9)));
					operatore.setTelefono(r.getString(7));
					operatore.setNome_università(r.getString(10));
					operatore.setDipartimento(r.getString(11));
					r.close();
					return operatore;
				}

				if (r.getString(13).equals("elettore")) {
					Elettore elettore = new Elettore();

					elettore.setId(r.getLong(1));
					elettore.setUsername(r.getString(5));
					elettore.setPassword(r.getString(6));
					elettore.setNome(r.getString(2));
					elettore.setCognome(r.getString(3));
					elettore.setEmail(email);
					// Conversione da Date a LocalDate
					elettore.setData_nascita(
							Instant.ofEpochMilli(r.getDate(8).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
					elettore.setProfessione(Professione.valueOf(r.getString(9)));
					elettore.setTelefono(r.getString(7));
					elettore.setNome_università(r.getString(10));
					elettore.setDipartimento(r.getString(11));
					elettore.setMatricola(r.getString(12));
					r.close();
					return elettore;
				}

			}
			r.close();
			throw new UtenteNotFoundException();
		} catch (SQLException | UtenteNotFoundException ex) {
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
	public void eliminaCandidato(Candidato candidato) throws BusinessException {
		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(eliminaCandidato);

			System.out.println(candidato.getId());
			ps.setString(1, candidato.getEmail());

			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public List<Candidato> trovaTuttiCandidati(Evento evento) throws BusinessException {
		List<Candidato> candidati = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(trovaTuttiCandidati);

			ps.setLong(1, evento.getId());
			r = ps.executeQuery();

			while (r.next()) {
				Candidato candidato = new Candidato();
				candidato.setId(r.getLong(1));
				candidato.setNome(r.getString(2));
				candidato.setCognome(r.getString(3));
				candidato.setEmail(r.getString(4));
				candidato.setEvento(evento);
				candidati.add(candidato);
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
		return candidati;
	}

	@Override
	public List<String> trovaEmailTuttiOperatori() throws BusinessException {
		List<String> result = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(trovaEmailOperatori);
			r = ps.executeQuery();

			while (r.next()) {
				String emailOperatore;
				emailOperatore = (r.getString(1));
				result.add(emailOperatore);
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
