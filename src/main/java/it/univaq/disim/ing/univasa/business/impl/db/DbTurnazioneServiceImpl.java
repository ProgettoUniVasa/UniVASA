package it.univaq.disim.ing.univasa.business.impl.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.TurnazioneService;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.domain.Operatore;
import it.univaq.disim.ing.univasa.domain.TipologiaTurno;
import it.univaq.disim.ing.univasa.domain.Turnazione;

public class DbTurnazioneServiceImpl implements TurnazioneService {

	private EventoService eventoService;
	private UtenteService utenteService;

	private static final String url = "jdbc:mysql://localhost:3306/univasa?noAccessToProcedureBodies=true&serverTimezone=Europe/Rome";
	private static final String user = "dbuser";
	private static final String password = "sql_password123";

	/* -------------------------------------------------------------------------------------------------------------------------------------------- */

	// Definizione query in Java
	private static final String associaTurnazione = "insert into turnazione(id_utente, id_evento, fascia, data_giorno) values(?,?,?,?)";
	private static final String visualizzaTurnazioni = "select * from turnazione where id_utente=?";


	public DbTurnazioneServiceImpl(EventoService eventoService, UtenteService utenteService) {
		this.eventoService = eventoService;
		this.utenteService = utenteService;
	}


	@Override
	public void creaTurnazione() throws BusinessException {

	}

	@Override
	public List<Turnazione> visualizzaTurnazioni(Operatore operatore) throws BusinessException {
		return null;
	}

	@Override
	public void eliminaTurnazione(Turnazione turnazione) throws BusinessException {

	}

}
