package it.univaq.disim.ing.univasa.business;

import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.domain.Operatore;
import it.univaq.disim.ing.univasa.domain.TipologiaTurno;
import it.univaq.disim.ing.univasa.domain.Turnazione;

import java.time.LocalDate;
import java.util.List;

public interface TurnazioneService {

	void creaTurnazione(Turnazione turnazione) throws BusinessException;

	List<Turnazione> visualizzaTurnazioni(Operatore operatore) throws BusinessException;

	void eliminaTurnazione(Turnazione turnazione) throws BusinessException;

	Turnazione trovaTurnazioneDaId(Long id) throws BusinessException;

    List<Turnazione> visualizzaTutteLeTurnazioni() throws BusinessException;
}