package it.univaq.disim.ing.univasa.business;

import it.univaq.disim.ing.univasa.domain.Operatore;
import it.univaq.disim.ing.univasa.domain.Turnazione;

import java.util.List;

public interface TurnazioneService {

	void associaTurnazione(Turnazione turnazione, Operatore operatore) throws BusinessException;

	List<Turnazione> visualizzaTurnazioni(Operatore operatore) throws BusinessException;


}