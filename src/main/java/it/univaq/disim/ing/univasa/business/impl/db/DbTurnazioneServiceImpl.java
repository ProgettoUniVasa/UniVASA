package it.univaq.disim.ing.univasa.business.impl.db;

import it.univaq.disim.ing.univasa.business.*;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.domain.Operatore;
import it.univaq.disim.ing.univasa.domain.Turnazione;

public class DbTurnazioneServiceImpl implements TurnazioneService {

	// Invece operatore???? Forse non ci va!!
	private EventoService eventoService;

	private static final String url = "jdbc:mysql://localhost:3306/pharmadb?noAccessToProcedureBodies=true&serverTimezone=Europe/Rome";
	private static final String user = "dbuser";
	private static final String password = "sql_password123";

	// Definizione query in Java
	private static final String creaTurnazione = "insert into turnazione(Evento_idEvento, Operatore_idOperatore, fascia_oraria,data_turno) values(?,?,?,?,?)";

	public DbTurnazioneServiceImpl(EventoService eventoService) {
		this.eventoService = eventoService;
	}

	@Override
	public void creaTurnazione(Turnazione turnazione, Operatore operatore) throws BusinessException {

	}

}
