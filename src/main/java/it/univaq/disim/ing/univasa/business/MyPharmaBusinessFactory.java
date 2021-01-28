package it.univaq.disim.ing.univasa.business;

import it.univaq.disim.ing.univasa.business.impl.db.DbMyPharmaBusinessFactoryImpl;

public abstract class MyPharmaBusinessFactory {

	private static MyPharmaBusinessFactory factory = new DbMyPharmaBusinessFactoryImpl();

	public static MyPharmaBusinessFactory getInstance() {
		return factory;
	}

	public abstract UtenteService getUtenteService();

	public abstract TurnazioneService getTurnazioneService();

	public abstract EventoService getEventoService();

}