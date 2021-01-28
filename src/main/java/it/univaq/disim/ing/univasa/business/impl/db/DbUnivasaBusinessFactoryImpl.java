package it.univaq.disim.ing.univasa.business.impl.db;

import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.TurnazioneService;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;

public class DbUnivasaBusinessFactoryImpl extends UnivasaBusinessFactory {

	private UtenteService utenteService;
	private EventoService eventoService;
	private TurnazioneService turnazioneService;

	public DbUnivasaBusinessFactoryImpl() {
		eventoService = new DbEventoServiceImpl();
		utenteService = new DbUtenteServiceImpl();
		turnazioneService = new DbTurnazioneServiceImpl(eventoService, utenteService);
	}

	@Override
	public UtenteService getUtenteService() {
		return utenteService;
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
