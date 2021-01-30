package it.univaq.disim.ing.univasa.business;

import it.univaq.disim.ing.univasa.business.impl.db.DbUnivasaBusinessFactoryImpl;

public abstract class UnivasaBusinessFactory {

	private static UnivasaBusinessFactory factory = new DbUnivasaBusinessFactoryImpl();

	public static UnivasaBusinessFactory getInstance() {
		return factory;
	}

	public abstract UtenteService getUtenteService();

    public abstract PrenotazioneService getPrenotazioneService();

    public abstract TurnazioneService getTurnazioneService();

	public abstract EventoService getEventoService();

}