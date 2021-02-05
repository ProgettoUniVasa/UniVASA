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
				prenotazione.setId(r.getLong(1));
				prenotazione.setElettore(elettore);
				prenotazione.setEvento(eventoService.trovaEventoDaId(r.getLong(3)));
				prenotazione.setTipoPrenotazione(TipoPrenotazione.valueOf(r.getString(4)));		// tipologia
				prenotazione.setStato(Stato.valueOf(r.getString(5)));	// stato
				prenotazione.setCertificato(r.getBlob(6));	// certificato
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
