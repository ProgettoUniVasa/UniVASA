package it.univaq.disim.oop.pharma.business;

import it.univaq.disim.oop.pharma.business.impl.db.DbMyPharmaBusinessFactoryImpl;
import it.univaq.disim.oop.pharma.business.impl.file.FileMyPharmaBusinessFactoryImpl;

public abstract class MyPharmaBusinessFactory {

	private static MyPharmaBusinessFactory factory = new FileMyPharmaBusinessFactoryImpl();
	//private static MyPharmaBusinessFactory factory = new DbMyPharmaBusinessFactoryImpl();

	public static MyPharmaBusinessFactory getInstance() {
		return factory;
	}

	public abstract UtenteService getUtenteService();

	public abstract PrescrizioneService getPrescrizioneService();

	public abstract FarmacoService getFarmacoService();

}