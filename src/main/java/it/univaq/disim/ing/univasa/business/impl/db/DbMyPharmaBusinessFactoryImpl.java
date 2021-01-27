package it.univaq.disim.ing.univasa.business.impl.db;

import it.univaq.disim.ing.univasa.business.FarmacoService;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.business.PrescrizioneService;
import it.univaq.disim.ing.univasa.business.UtenteService;

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
