package it.univaq.disim.ing.univasa.business;

import it.univaq.disim.ing.univasa.domain.Operatore;
import it.univaq.disim.ing.univasa.domain.Turnazione;

public interface TurnazioneService {

	void associaTurnazione(Turnazione turnazione, Operatore operatore) throws BusinessException;

}