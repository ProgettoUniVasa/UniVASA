package it.univaq.disim.ing.univasa.business;


import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.domain.Operatore;
import it.univaq.disim.ing.univasa.domain.Turnazione;

public interface TurnazioneService {

    void creaTurnazione(Turnazione turnazione) throws BusinessException;
    void assegnaTurnazione(Turnazione turnazione, Operatore operatore) throws BusinessException;

}