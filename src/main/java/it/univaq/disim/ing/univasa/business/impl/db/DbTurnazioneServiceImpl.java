package it.univaq.disim.ing.univasa.business.impl.db;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.Value;
import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.TurnazioneService;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.domain.*;

public class DbTurnazioneServiceImpl implements TurnazioneService {

	private EventoService eventoService;
	private UtenteService utenteService;

	private static final String url = "jdbc:mysql://localhost:3306/univasa?noAccessToProcedureBodies=true&serverTimezone=Europe/Rome";
	private static final String user = "root";
	private static final String password = "";

	/* -------------------------------------------------------------------------------------------------------------------------------------------- */

	// Definizione query in Java
	private static final String creaTurnazione = "insert into turnazione(id_utente, id_evento, fascia, data_giorno) values(?,?,?,?)";
	private static final String visualizzaTurnazioni = "select * from turnazione where id_utente=?";
	private static final String eliminaTurnazione = "delete from turnazione where id=?";
	private static final String visualizzaTutteLeTurnazioni = "select * from turnazione";
	private static final String trovaTurnazioneDaId = "select * from turnazione where id=?";

	public DbTurnazioneServiceImpl(EventoService eventoService, UtenteService utenteService) {
		this.eventoService = eventoService;
		this.utenteService = utenteService;
	}

	@Override
	public void creaTurnazione(Turnazione turnazione) throws BusinessException {
		// Conversione da LocalDate a Date
		Date data_giorno = java.sql.Date.valueOf(turnazione.getData_turno());

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(creaTurnazione);
			ps.setLong(1, turnazione.getEvento().getId());
			ps.setLong(2, turnazione.getOperatore().getId());
			ps.setString(3, String.valueOf(turnazione.getFascia()));
			ps.setDate(4, data_giorno);

			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public List<Turnazione> visualizzaTurnazioni(Operatore operatore) throws BusinessException {
		List<Turnazione> result = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(visualizzaTurnazioni);
			ps.setLong(1, operatore.getId());
			r = ps.executeQuery();

			while (r.next()) {

				Turnazione turnazione = new Turnazione();
				turnazione.setId(r.getLong(1));
				turnazione.setOperatore(operatore);
				turnazione.setEvento(eventoService.trovaEventoDaId(r.getLong(3)));
				turnazione.setFascia(TipologiaTurno.valueOf(r.getString(4)));
				turnazione.setData_turno(
						Instant.ofEpochMilli(r.getDate(5).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				result.add(turnazione);
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
	public void eliminaTurnazione(Turnazione turnazione) throws BusinessException {
		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(eliminaTurnazione);

			ps.setLong(1, turnazione.getId());

			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public Turnazione trovaTurnazioneDaId(Long id) throws BusinessException {
		Turnazione turnazione = new Turnazione();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(trovaTurnazioneDaId);
			ps.setLong(1, id);
			r = ps.executeQuery();

			while (r.next()) {
				turnazione.setId(id);
				turnazione.setOperatore((Operatore) utenteService.trovaUtenteDaId(r.getLong(2)));
				turnazione.setEvento(eventoService.trovaEventoDaId(r.getLong(3)));
				turnazione.setFascia(TipologiaTurno.valueOf(r.getString(4)));
				turnazione.setData_turno(
						Instant.ofEpochMilli(r.getDate(5).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
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
		return turnazione;
	}

	@Override
	public List<Turnazione> visualizzaTutteLeTurnazioni() {
		List<Turnazione> result = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(visualizzaTutteLeTurnazioni);
			r = ps.executeQuery();

			while (r.next()) {

				Turnazione turnazione = new Turnazione();
				turnazione = trovaTurnazioneDaId(r.getLong(1));
				result.add(turnazione);
			}

			r.close();
		} catch (SQLException | BusinessException ex) {
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
