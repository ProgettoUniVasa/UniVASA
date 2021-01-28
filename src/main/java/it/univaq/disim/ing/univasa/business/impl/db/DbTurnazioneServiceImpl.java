package it.univaq.disim.ing.univasa.business.impl.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.TurnazioneService;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.domain.Operatore;
import it.univaq.disim.ing.univasa.domain.Turnazione;

public class DbTurnazioneServiceImpl implements TurnazioneService {

	private EventoService eventoService;
	private UtenteService utenteService;

	private static final String url = "jdbc:mysql://localhost:3306/univasa?noAccessToProcedureBodies=true&serverTimezone=Europe/Rome";
	private static final String user = "dbuser";
	private static final String password = "sql_password123";

	// Definizione query in Java
	private static final String associaTurnazione = "insert into turnazione(Evento_idEvento, Operatore_idOperatore, fascia_oraria, data_turno) values(?,?,?,?)";

	public DbTurnazioneServiceImpl(EventoService eventoService, UtenteService utenteService) {
		this.eventoService = eventoService;
		this.utenteService = utenteService;
	}

	@Override
	public void associaTurnazione(Turnazione turnazione, Operatore operatore) throws BusinessException {
		// Conversione da LocalDate a Date
		Date data_turno = java.sql.Date.valueOf("" + turnazione.getData_turno());

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(associaTurnazione);
			ps.setLong(1, turnazione.getEvento().getId());
			ps.setLong(2, turnazione.getOperatore().getId());
			ps.setString(3, turnazione.getFascia().toString());
			ps.setDate(4, data_turno);

			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}
