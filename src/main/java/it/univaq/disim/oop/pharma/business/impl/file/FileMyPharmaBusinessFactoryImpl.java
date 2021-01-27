package it.univaq.disim.oop.pharma.business.impl.file;

import java.io.File;

import it.univaq.disim.oop.pharma.business.FarmacoService;
import it.univaq.disim.oop.pharma.business.MyPharmaBusinessFactory;
import it.univaq.disim.oop.pharma.business.PrescrizioneService;
import it.univaq.disim.oop.pharma.business.UtenteService;

public class FileMyPharmaBusinessFactoryImpl extends MyPharmaBusinessFactory {

	private UtenteService utenteService;
	private PrescrizioneService prescrizioneService;
	private FarmacoService farmacoService;

	private static final String REPOSITORY_BASE = "src" + File.separator + "main" + File.separator + "resources"
			+ File.separator + "dati";
	private static final String UTENTI_FILE_NAME = REPOSITORY_BASE + File.separator + "utenti.txt";
	private static final String FARMACI_PRESCRITTI_FILE_NAME = REPOSITORY_BASE + File.separator
			+ "farmaciprescritti.txt";
	private static final String FARMACI_FILE_NAME = REPOSITORY_BASE + File.separator + "farmaci.txt";
	private static final String PRESCRIZIONI_FILE_NAME = REPOSITORY_BASE + File.separator + "prescrizioni.txt";

	public FileMyPharmaBusinessFactoryImpl() {
		farmacoService = new FileFarmacoServiceImpl(FARMACI_FILE_NAME);
		utenteService = new FileUtenteServiceImpl(UTENTI_FILE_NAME);
		prescrizioneService = new FilePrescrizioneServiceImpl(PRESCRIZIONI_FILE_NAME, FARMACI_PRESCRITTI_FILE_NAME,
				utenteService, farmacoService);
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
