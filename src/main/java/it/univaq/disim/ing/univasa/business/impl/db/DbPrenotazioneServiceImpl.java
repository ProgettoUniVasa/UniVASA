package it.univaq.disim.ing.univasa.business.impl.db;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.PrenotazioneService;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.domain.ElettoreInSede;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.domain.Prenotazione;

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
	private static final String user = "dbuser";
	private static final String pwd = "sql_password123";


	// creaElettoreInSede & creaElettoreOnline
	private static final String prenotazioneInSede = "insert into prenotazione (id_utente,id_evento,tipo_prenotazione,stato) values (?,?,'in presenza','no')";
	private static final String prenotazioneOnline = "insert into prenotazione (id_utente,id_evento,tipo_prenotazione) values (?,?,'online')";


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
		return null;
	}

	@Override
	public List<Prenotazione> trovaPrenotazioniOnlineInCorso(Elettore elettore) {
		return null;
	}

	/* -------------------------------------------------------------------------------------------------------------------------------------------- */

	// Definizione query in Java





}
