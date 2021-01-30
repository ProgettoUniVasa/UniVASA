package it.univaq.disim.ing.univasa.business.impl.db;

import it.univaq.disim.ing.univasa.business.*;

public class DbUnivasaBusinessFactoryImpl extends UnivasaBusinessFactory {

	private UtenteService utenteService;
	private EventoService eventoService;
	private TurnazioneService turnazioneService;
	private PrenotazioneService prenotazioneService;

	public DbUnivasaBusinessFactoryImpl() {
		eventoService = new DbEventoServiceImpl();
		utenteService = new DbUtenteServiceImpl();
		prenotazioneService = new DbPrenotazioneServiceImpl(eventoService, utenteService);
		turnazioneService = new DbTurnazioneServiceImpl(eventoService, utenteService);
	}

	@Override
	public UtenteService getUtenteService() {
		return utenteService;
	}

	@Override
	public PrenotazioneService getPrenotazioneService() {
		return prenotazioneService;
	}

	@Override
	public TurnazioneService getTurnazioneService() {
		return turnazioneService;
	}

	@Override
	public EventoService getEventoService() {
		return eventoService;
	}

}
