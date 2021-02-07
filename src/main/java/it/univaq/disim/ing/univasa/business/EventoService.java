package it.univaq.disim.ing.univasa.business;

import java.util.List;

import it.univaq.disim.ing.univasa.domain.Candidato;
import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.domain.Evento;

public interface EventoService {

	void creaEvento(Evento evento) throws BusinessException;

	void eliminaEvento(Evento evento) throws BusinessException;

	void caricaRisultatiInPresenza(Candidato candidato, int votiRicevuti) throws  BusinessException;

	void modificaReport(Evento evento) throws BusinessException;

	List<Evento> trovaTuttiEventi() throws BusinessException;
	List<Evento> trovaEventiInCorso() throws BusinessException;

	List<Evento> trovaEventiDaPrenotare(Elettore elettore) throws  BusinessException;

	Evento trovaEventoDaId(Long id) throws BusinessException;

	List<Evento> trovaEventiDaLuogo(String luogo) throws BusinessException;

	// Metodo che restituisce i nomi di tutti i farmaci
	List<String> nomiEventi() throws BusinessException;

	List<Elettore> visualizzaPrenotatiInSede(Evento evento) throws BusinessException;

	Evento eventodaNome(String nome);

    List<Evento> trovaEventiFinitiPrenotati (Elettore elettore) throws BusinessException;

	List<Candidato> visualizzaCandidati(Evento evento) throws BusinessException;

	List<Evento> trovaEventiFiniti() throws  BusinessException;

	List<Evento> trovaEventiDaVotare(Elettore elettore) throws  BusinessException;
}
