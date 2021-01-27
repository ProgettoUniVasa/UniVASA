package it.univaq.disim.oop.pharma.business.impl.db;

import it.univaq.disim.oop.pharma.business.FarmacoService;
import it.univaq.disim.oop.pharma.business.MyPharmaBusinessFactory;
import it.univaq.disim.oop.pharma.business.PrescrizioneService;
import it.univaq.disim.oop.pharma.business.UtenteService;

public class DbMyPharmaBusinessFactoryImpl extends MyPharmaBusinessFactory {

	private UtenteService utenteService;
	private PrescrizioneService prescrizioneService;
	private FarmacoService farmacoService;

	public DbMyPharmaBusinessFactoryImpl() {
		farmacoService = new DbFarmacoServiceImpl();
		utenteService = new DbUtenteServiceImpl();
		prescrizioneService = new DbPrescrizioneServiceImpl(utenteService, farmacoService);
	}

	@Override
	public UtenteService getUtenteService() {
		return utenteService;
	}

	@Override
	public PrescrizioneService getPrescrizioneService() {
		return prescrizioneService;
	}

	@Override
	public FarmacoService getFarmacoService() {
		return farmacoService;
	}

}
