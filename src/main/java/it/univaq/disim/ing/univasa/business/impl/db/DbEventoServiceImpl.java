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
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.domain.*;

//aggiungere l'ora
public class DbEventoServiceImpl implements EventoService {

	private static final String url = "jdbc:mysql://localhost:3306/univasa?noAccessToProcedureBodies=true&serverTimezone=Europe/Rome";
	private static final String user = "root";
	private static final String password = "";

	/* -------------------------------------------------------------------------------------------------------------------------------------------- */

	// Definizione query in Java
	private static final String creaEvento = "insert into evento (nome, regolamento, data_inizio, data_fine, ora_inizio, ora_fine, luogo, numero_preferenze_esprimibili,stato) values (?,?,?,?,?,?,?,?,'programmato') ";
	private static final String creaReport = "insert into evento (report_risultati, report_statistiche) values (?,?) where id=?";
	private static final String eliminaEvento = "delete from evento where id=?";
	private static final String trovaTuttiEventi = "select * from evento";
	private static final String trovaEventoDaId = "select * from evento where id=?";
	private static final String trovaNomiEventi = "select nome from evento";
	private static final String eventoDaNome = "select * from evento where nome=?";
	private static final String trovaEventiDaLuogo = "select * from evento where luogo=?";
	private static final String trovaEventiDaPrenotare = "SELECT * from evento where id not in (select e.id from evento e join prenotazione p on e.id=p.id_evento where e.data_inizio>now() and p.id_utente=?)";
	private static final String trovaEventiFinitiPrenotati = "select * from evento e join prenotazione p on e.id=p.id_evento where e.data_fine<now() and p.id_utente=?";
	private static final String visualizzaCandidati = "select * from utente u join candidato c on u.email=c.email where c.id_evento=?";
	// visualizzaPrenotatiInSede
	private static final String visualizzaPrenotatiInSede = "select * from utente u join prenotazione p on p.id_utente=u.id where id_evento=? and tipo_prenotazione='in presenza'";

	// si deve ripetere per ogni candidato che riceve voti
	private static final String caricaRisultatiInPresenza = "update from candidatura set voti_ricevuti=voti_ricevuti+? where id_utente=? and id_evento=?";	// ciclica
	private static final String trovaEventiPrenotatiElettore = "select * from evento e join prenotazione p on e.id=p.id_utente";


	@Override
	public void creaEvento(Evento evento) throws BusinessException {
		// Conversione da LocalDate a Date
		Date data_inizio = java.sql.Date.valueOf(evento.getDataInizio());
		Date data_fine = java.sql.Date.valueOf(evento.getDataFine());

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(creaEvento);
			ps.setString(1, evento.getNome());
			ps.setString(2, evento.getRegolamento());
			ps.setDate(3, data_inizio);
			ps.setDate(4, data_fine);
			ps.setString(5, evento.getOraInizio());
			ps.setString(6, evento.getOraFine());
			ps.setString(7, evento.getLuogo());
			ps.setInt(8, evento.getNumero_preferenze_esprimibili());

			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void creaReport(Evento evento) throws BusinessException {
		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(creaReport);
			ps.setString(1, evento.getReport_risultati());
			ps.setString(2, evento.getReport_statistiche());
			ps.setLong(3, evento.getId());

			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void caricaRisultatiInPresenza(Evento evento) throws BusinessException {
		// ......
	}

	@Override
	public List<Elettore> visualizzaPrenotatiInSede(Evento evento) throws BusinessException {
		List<Elettore> elettori = new ArrayList<Elettore>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(visualizzaPrenotatiInSede);
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
	public void eliminaEvento(Evento evento) throws BusinessException {
		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(eliminaEvento);

			ps.setLong(1, evento.getId());

			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public List<Evento> trovaTuttiEventi() throws BusinessException {
		List<Evento> result = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(trovaTuttiEventi);
			r = ps.executeQuery();

			while (r.next()) {
				Evento evento = new Evento();
				evento = trovaEventoDaId(r.getLong(1));
				result.add(evento);
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
	public List<Candidato> visualizzaCandidati(Evento evento) throws BusinessException {
		List<Candidato> candidati = new ArrayList<Candidato>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(visualizzaCandidati);
			ps.setLong(1, evento.getId());
			r = ps.executeQuery();

			while (r.next()) {
				Candidato candidato = new Candidato();
				candidato.setId(r.getLong(1));
				candidato.setNome(r.getString(2));
				candidato.setCognome(r.getString(3));
				candidato.setEmail(r.getString(4));
				candidato.setVotiRicevuti(r.getInt(5));
				candidato.setEvento(evento);
				candidati.add(candidato);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return candidati;
	}

	@Override
	public List<Evento> trovaEventiDaPrenotare(Elettore elettore) throws BusinessException {
		List<Evento> result = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(trovaEventiDaPrenotare);
			ps.setLong(1,elettore.getId());
			r = ps.executeQuery();

			while (r.next()) {
				Evento evento = new Evento();
				evento.setId(r.getLong(1));
				evento.setNome(r.getString(2));
				evento.setRegolamento(r.getString(3));
				// Conversione da Date a LocalDateTime
				evento.setDataInizio(
						Instant.ofEpochMilli(r.getDate(4).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				evento.setDataFine(
						Instant.ofEpochMilli(r.getDate(5).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				evento.setOraInizio(r.getString(6));
				evento.setOraFine(r.getString(7));
				evento.setLuogo(r.getString(8));
				evento.setReport_risultati(r.getString(9));
				evento.setReport_statistiche(r.getString(10));
				evento.setNumero_preferenze_esprimibili(r.getInt(11));
				result.add(evento);
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
	public Evento trovaEventoDaId(Long id) throws BusinessException {
		Evento evento = new Evento();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(trovaEventoDaId);
			ps.setLong(1, id);
			r = ps.executeQuery();

			while (r.next()) {
				evento.setId(id);
				evento.setNome(r.getString(2));
				evento.setRegolamento(r.getString(3));
				// Conversione da Date a LocalDateTime
				evento.setDataInizio(
						Instant.ofEpochMilli(r.getDate(4).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				evento.setDataFine(
						Instant.ofEpochMilli(r.getDate(5).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				evento.setOraInizio(r.getString(6));
				evento.setOraFine(r.getString(7));
				evento.setLuogo(r.getString(8));
				evento.setReport_risultati(r.getString(9));
				evento.setReport_statistiche(r.getString(10));
				evento.setNumero_preferenze_esprimibili(r.getInt(11));
				evento.setStatoEvento(StatoEvento.valueOf(r.getString(12)));
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
		return evento;
	}

	@Override
	public List<Evento> trovaEventiDaLuogo(String luogo) throws BusinessException {
		List<Evento> eventi = new ArrayList<Evento>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(trovaEventiDaLuogo);

			ps.setString(1, luogo);
			r = ps.executeQuery();

			while (r.next()) {
				Evento evento = null;
				evento.setId(r.getLong(1));
				evento.setNome(r.getString(2));
				evento.setRegolamento(r.getString(3));
				// Conversione da Date a LocalDateTime
				evento.setDataInizio(
						Instant.ofEpochMilli(r.getDate(4).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				evento.setDataFine(
						Instant.ofEpochMilli(r.getDate(5).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				evento.setOraInizio(r.getString(6));
				evento.setOraFine(r.getString(7));
				evento.setLuogo(luogo);
				evento.setReport_risultati(r.getString(9));
				evento.setReport_statistiche(r.getString(10));
				evento.setNumero_preferenze_esprimibili(r.getInt(11));
				eventi.add(evento);
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
		return eventi;
	}

	@Override
	public List<String> nomiEventi() throws BusinessException {
		List<String> result = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(trovaNomiEventi);
			r = ps.executeQuery();

			while (r.next()) {
				String nomeEvento;
				nomeEvento = (r.getString(1));
				result.add(nomeEvento);
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
	public Evento eventodaNome(String nome) {
		Evento evento = new Evento();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(eventoDaNome);

			ps.setString(1, nome);
			r = ps.executeQuery();

			while (r.next()) {
				evento.setId(r.getLong(1));
				evento.setNome(r.getString(nome));
				evento.setRegolamento(r.getString(3));
				// Conversione da Date a LocalDateTime
				evento.setDataInizio(
						Instant.ofEpochMilli(r.getDate(4).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				evento.setDataFine(
						Instant.ofEpochMilli(r.getDate(5).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				evento.setOraInizio(r.getString(6));
				evento.setOraFine(r.getString(7));
				evento.setLuogo(r.getString(8));
				evento.setReport_risultati(r.getString(9));
				evento.setReport_statistiche(r.getString(10));
				evento.setNumero_preferenze_esprimibili(r.getInt(11));
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
		return evento;
	}

	@Override
	public List<Evento> trovaEventiFinitiPrenotati(Elettore elettore) throws BusinessException {
		List<Evento> result = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(trovaEventiFinitiPrenotati);
			r = ps.executeQuery();

			while (r.next()) {
				Evento evento = new Evento();
				evento.setId(r.getLong(1));
				evento.setNome(r.getString(2));
				evento.setRegolamento(r.getString(3));
				// Conversione da Date a LocalDateTime
				evento.setDataInizio(
						Instant.ofEpochMilli(r.getDate(4).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				evento.setDataFine(
						Instant.ofEpochMilli(r.getDate(5).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				evento.setOraInizio(r.getString(6));
				evento.setOraFine(r.getString(7));
				evento.setLuogo(r.getString(8));
				evento.setReport_risultati(r.getString(9));
				evento.setReport_statistiche(r.getString(10));
				evento.setNumero_preferenze_esprimibili(r.getInt(11));
				result.add(evento);
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
