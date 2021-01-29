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
import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.domain.Evento;

//aggiungere l'ora
public class DbEventoServiceImpl implements EventoService {

	private static final String url = "jdbc:mysql://localhost:3306/univasa?noAccessToProcedureBodies=true&serverTimezone=Europe/Rome";
	private static final String user = "dbuser";
	private static final String password = "sql_password123";

	/* -------------------------------------------------------------------------------------------------------------------------------------------- */

	// Definizione query in Java
	private static final String creaEvento = "insert into evento (nome, regolamento, data_inizio, data_fine, ora_inizio, ora_fine, luogo, numero_preferenze_esprimibili,stato) values (?,?,?,?,?,?,?,?,'programmato') ";
	private static final String creaReport = "insert into evento (report_risultati, report_statistiche) values (?,?) where id=?";
	private static final String cancellaEvento = "delete from evento where id=?";
	private static final String trovaTuttiEventi = "select * from evento";
	private static final String trovaEventoDaId = "select * from evento where id=?";
	private static final String trovaNomiEventi = "select nome from evento";
	
	// si deve ripetere per ogni candidato che riceve voti
	private static final String caricaRisultatiInPresenza = "update from candidatura set voti_ricevuti=voti_ricevuti+? where id_utente=? and id_evento=?";
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
			ps.setString(5, evento.getLuogo());
			ps.setInt(6, evento.getNumero_preferenze_esprimibili());

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

			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public boolean caricaRisultatiInPresenza(Evento evento) throws BusinessException {
		return false;
	}

	@Override
	public void eliminaEvento(Evento evento) throws BusinessException {
		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(cancellaEvento);

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
				evento.setId(r.getLong(1));
				evento.setNome(r.getString(2));
				evento.setRegolamento(r.getString(3));
				// Conversione da Date a LocalDateTime
				evento.setDataInizio(
						Instant.ofEpochMilli(r.getDate(4).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				evento.setDataFine(
						Instant.ofEpochMilli(r.getDate(5).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				evento.setLuogo(r.getString(6));
				evento.setReport_risultati(r.getString(7));
				evento.setReport_statistiche(r.getString(8));
				evento.setNumero_preferenze_esprimibili(r.getInt(9));
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
				evento.setLuogo(r.getString(6));
				evento.setReport_risultati(r.getString(7));
				evento.setReport_statistiche(r.getString(8));
				evento.setNumero_preferenze_esprimibili(r.getInt(9));
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
	public List<Evento> trovaEventiPrenotatiElettore(Elettore elettore) throws BusinessException {
		return null;
	}

}
