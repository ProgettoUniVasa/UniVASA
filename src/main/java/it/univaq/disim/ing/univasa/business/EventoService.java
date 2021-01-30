package it.univaq.disim.ing.univasa.business;

import java.util.List;

import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.domain.Evento;

public interface EventoService {

	void creaEvento(Evento evento) throws BusinessException;

	void eliminaEvento(Evento evento) throws BusinessException;

	void creaReport(Evento evento) throws BusinessException;

	void caricaRisultatiInPresenza(Evento evento) throws  BusinessException;

	List<Evento> trovaTuttiEventi() throws BusinessException;

	List<Evento> trovaEventiPrenotatiElettore(Elettore elettore) throws BusinessException;

	Evento trovaEventoDaId(Long id) throws BusinessException;

	List<Evento> trovaEventiDaLuogo(String luogo) throws BusinessException;

	// Metodo che restituisce i nomi di tutti i farmaci
	List<String> nomiEventi() throws BusinessException;

    Evento eventodaNome(String nome);
}
