package it.univaq.disim.ing.univasa.business;

import java.util.List;

import it.univaq.disim.ing.univasa.domain.Farmaco;

public interface FarmacoService {

	void creaFarmaco(Farmaco farmaco) throws BusinessException;

	void aggiornaFarmaco(Farmaco farmaco) throws BusinessException;

	void eliminaFarmaco(Farmaco farmaco) throws BusinessException;

	List<Farmaco> trovaTuttiFarmaci() throws BusinessException;

	Farmaco trovaFarmacoDaId(int id) throws BusinessException;

	// Metodo che restituisce i nomi di tutti i farmaci
	List<String> nomiFarmaci() throws BusinessException;

	// Metodo che restituisce una lista di farmaci sotto una certa quantità che
	// vengono considerati in esaurimento
	List<Farmaco> farmaciInEsaurimento() throws BusinessException;

}