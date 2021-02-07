package it.univaq.disim.ing.univasa.business.impl.db;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.PrenotazioneService;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.domain.*;

import java.sql.*;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

//aggiungere l'ora
public class DbPrenotazioneServiceImpl implements PrenotazioneService {

	EventoService eventoService;
	UtenteService utenteService;

	private static final String url = "jdbc:mysql://localhost:3306/univasa?noAccessToProcedureBodies=true&serverTimezone=Europe/Rome";
	private static final String user = "root";
	private static final String pwd = "";


	// creaElettoreInSede & creaElettoreOnline
	private static final String prenotazioneInSede = "insert into prenotazione (id_utente,id_evento,tipo_prenotazione,stato) values (?,?,'in presenza','no')";
	private static final String prenotazioneOnline = "insert into prenotazione (id_utente,id_evento,tipo_prenotazione) values (?,?,'online')";
	private static final String trovaPrenotazioniElettore = "select * from prenotazione where id_utente=?";
	private static final String trovaPrenotazioneDaId = "select * from prenotazione where id=?";

	public DbPrenotazioneServiceImpl(EventoService eventoService, UtenteService utenteService) {
		this.eventoService=eventoService;
		this.utenteService=utenteService;
	}

	@Override
	public void prenotazioneInSede(Elettore elettore, Evento evento) throws BusinessException {
		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(prenotazioneInSede);

			ps.setLong(1, elettore.getId());
			ps.setLong(2, evento.getId());

			ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void prenotazioneOnline(Elettore elettore, Evento evento) throws BusinessException {
		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(prenotazioneOnline);

			ps.setLong(1, elettore.getId());
			ps.setLong(2, evento.getId());

			ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public Prenotazione trovaPrenotazioneDaId(Long id) throws BusinessException {
		Prenotazione prenotazione = new Prenotazione();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(trovaPrenotazioneDaId);
			ps.setLong(1, id);
			r = ps.executeQuery();

			while (r.next()) {
				prenotazione.setId(id);
				prenotazione.setElettore((Elettore) utenteService.trovaUtenteDaId(r.getLong(2)));
				prenotazione.setEvento(eventoService.trovaEventoDaId(r.getLong(3)));
				if (r.getString(4).equals("in presenza"))
					prenotazione.setTipoPrenotazione(TipoPrenotazione.valueOf("in_presenza"));
				else
					prenotazione.setTipoPrenotazione(TipoPrenotazione.valueOf(r.getString(4)));
				prenotazione.setStato(Stato.valueOf(r.getString(5)));
				prenotazione.setCertificato(r.getBlob(6));

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
		return prenotazione;
	}

	@Override
	public List<Prenotazione> trovaPrenotazioniElettore(Elettore elettore) throws BusinessException {
		List<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(trovaPrenotazioniElettore);
			ps.setLong(1, elettore.getId());
			r = ps.executeQuery();

			while (r.next()) {
				Prenotazione prenotazione = new Prenotazione();
				prenotazione = trovaPrenotazioneDaId(r.getLong(1));
				prenotazioni.add(prenotazione);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return prenotazioni;
	}

	@Override			// DA FARE
	public List<Prenotazione> trovaPrenotazioniOnlineInCorso(Elettore elettore) {
		List<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();
		return prenotazioni;
	}

}
