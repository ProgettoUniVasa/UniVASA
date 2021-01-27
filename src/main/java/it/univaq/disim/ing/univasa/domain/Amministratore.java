package it.univaq.disim.ing.univasa.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Amministratore extends Utente {
    
    private Set<Evento> evento = new HashSet<>();
    
    
    public Amministratore(Long id, String nome, String cognome, String email, String username, String password,
			String telefono, LocalDate data_nascita, Enum professione, String nome_università, String dipartimento,
			Set<Evento> evento) {
		super(id, nome, cognome, email, username, password, telefono, data_nascita, professione, nome_università,
				dipartimento);
		this.evento = evento;
	}
    
    
    public Amministratore() {
		super();
	}


	public Set<Evento> getEvento() {
        return this.evento;
    }
    
    public void setEvento(Set<Evento> evento) {
        this.evento = evento;
    }
    //                          Operations                                  
    
    /*
    public creaEvento() {
        //TODO
    }
    
    public eliminaEvento() {
        //TODO
    }
    
    public creaReport() {
        //TODO
    }
    
    public verificaCertificato() {
        //TODO
    }
    
    public gestionePrenotazioni() {
        //TODO
    }
    
    public creaTurnazione() {
        //TODO
    }
    
    public associaTurnazione() {
        //TODO
    }
    
    public visualizzaUtenti() {
        //TODO
    }
    
    public operation() {
        //TODO
    }
    
    public () {
        //TODO
    }
    
    public visualizzaCandidati() {
        //TODO
    }
    */
}