package it.univaq.disim.ing.univasa.business.impl.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.PrenotazioneService;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.domain.Prenotazione;
import it.univaq.disim.ing.univasa.domain.Stato;
import it.univaq.disim.ing.univasa.domain.TipoPrenotazione;

public class DbPrenotazioneServiceImpl implements PrenotazioneService {

	EventoService eventoService;
	UtenteService utenteService;

	private static final String url = "jdbc:mysql://localhost:3306/univasa?noAccessToProcedureBodies=true&serverTimezone=Europe/Rome";
	private static final String user = "root";
	private static final String pwd = "";

	private static final String prenotazioneInSede = "insert into prenotazione (id_utente,id_evento,tipo_prenotazione,stato) values (?,?,'in presenza','no')";
	private static final String prenotazioneOnline = "insert into prenotazione (id_utente,id_evento,tipo_prenotazione,stato) values (?,?,'online','no')";
	private static final String trovaPrenotazioniElettore = "select * from prenotazione where id_utente=?";
	private static final String trovaPrenotazioneDaId = "select * from prenotazione where id=?";
	private static final String trovaPrenotazioneOnlineElettore = "select * from prenotazione p join evento e on p.id_evento = e.id where p.id_utente=? and p.tipo_prenotazione='online' and e.data_inizio<=now() and e.data_fine>=now() and p.stato='no'";
	private static final String eliminaPrenotazione = "delete from prenotazione where id=?";
	private static final String cambioModalita = "update prenotazione set tipo_prenotazione='online' where id=?";
	private static final String vota = "update prenotazione set stato='si' where id_evento=? and id_utente=?";

	public DbPrenotazioneServiceImpl(EventoService eventoService, UtenteService utenteService) {
		this.eventoService = eventoService;
		this.utenteService = utenteService;
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
	public void vota(Evento evento, Elettore elettore) throws BusinessException {
		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(vota);

			ps.setLong(1, evento.getId());
			ps.setLong(2, elettore.getId());

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
				prenotazione = trovaPrenotazioneDaId(r.getLong(1));
				prenotazioni.add(prenotazione);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return prenotazioni;
	}

	@Override
	public List<Prenotazione> trovaPrenotazioniOnlineInCorso(Elettore elettore) throws BusinessException {
		List<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(trovaPrenotazioneOnlineElettore);

			ps.setLong(1, elettore.getId());
			r = ps.executeQuery();

			while (r.next()) {
				Prenotazione prenotazione = new Prenotazione();
				prenotazione = trovaPrenotazioneDaId(r.getLong(1));
				prenotazioni.add(prenotazione);
			}

			ps.executeQuery();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return prenotazioni;
	}

	@Override
	public void eliminaPrenotazione(Prenotazione prenotazione) throws BusinessException {
		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(eliminaPrenotazione);

			ps.setLong(1, prenotazione.getId());

			ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void cambioModalita(Prenotazione prenotazione) throws BusinessException {
		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, pwd);) {

			PreparedStatement ps = c.prepareStatement(cambioModalita);

			ps.setLong(1, prenotazione.getId());

			ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}
