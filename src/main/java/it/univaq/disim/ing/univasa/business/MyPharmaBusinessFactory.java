package it.univaq.disim.ing.univasa.business;

import it.univaq.disim.ing.univasa.business.impl.db.DbMyPharmaBusinessFactoryImpl;

public abstract class MyPharmaBusinessFactory {

	private static MyPharmaBusinessFactory factory = new DbMyPharmaBusinessFactoryImpl();

	public static MyPharmaBusinessFactory getInstance() {
		return factory;
	}

	public abstract UtenteService getUtenteService();

	public abstract PrescrizioneService getPrescrizioneService();

	public abstract FarmacoService getFarmacoService();

}