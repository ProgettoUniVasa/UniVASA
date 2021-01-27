package SchedaElettorale;


/**
* @generated
*/
public class SchedaElettorale {
    
    /**
    * @generated
    */
    private Long id;
    
    /**
    * @generated
    */
    private List<Elettore> preferenzeEspresse;
    
    /**
    * @generated
    */
    private enum tipologia;
    
    /**
    * @generated
    */
    private List<Candidato> preferenze_espresse;
    
    /**
    * @generated
    */
    private Boolean prenotazione;
    
    /**
    * @generated
    */
    private enum tipologia_prenotazione;
    
    
    /**
    * @generated
    */
    private Set<Candidato> candidato;
    
    /**
    * @generated
    */
    private Operatore operatore;
    
    /**
    * @generated
    */
    private Evento evento;
    
    /**
    * @generated
    */
    private Risultato risultato;
    
    /**
    * @generated
    */
    private ElettoreOnline ;
    
    
    /**
    * @generated
    */
    private Long getId() {
        return this.id;
    }
    
    /**
    * @generated
    */
    private Long setId(Long id) {
        this.id = id;
    }
    
    /**
    * @generated
    */
    private List<Elettore> getPreferenzeEspresse() {
        return this.preferenzeEspresse;
    }
    
    /**
    * @generated
    */
    private List<Elettore> setPreferenzeEspresse(List<Elettore> preferenzeEspresse) {
        this.preferenzeEspresse = preferenzeEspresse;
    }
    
    /**
    * @generated
    */
    private enum getTipologia() {
        return this.tipologia;
    }
    
    /**
    * @generated
    */
    private enum setTipologia(enum tipologia) {
        this.tipologia = tipologia;
    }
    
    /**
    * @generated
    */
    private List<Candidato> getPreferenze_espresse() {
        return this.preferenze_espresse;
    }
    
    /**
    * @generated
    */
    private List<Candidato> setPreferenze_espresse(List<Candidato> preferenze_espresse) {
        this.preferenze_espresse = preferenze_espresse;
    }
    
    /**
    * @generated
    */
    private Boolean getPrenotazione() {
        return this.prenotazione;
    }
    
    /**
    * @generated
    */
    private Boolean setPrenotazione(Boolean prenotazione) {
        this.prenotazione = prenotazione;
    }
    
    /**
    * @generated
    */
    private enum getTipologia_prenotazione() {
        return this.tipologia_prenotazione;
    }
    
    /**
    * @generated
    */
    private enum setTipologia_prenotazione(enum tipologia_prenotazione) {
        this.tipologia_prenotazione = tipologia_prenotazione;
    }
    
    
    /**
    * @generated
    */
    public Risultato getRisultato() {
        return this.risultato;
    }
    
    /**
    * @generated
    */
    public Risultato setRisultato(Risultato risultato) {
        this.risultato = risultato;
    }
    
    /**
    * @generated
    */
    public Evento getEvento() {
        return this.evento;
    }
    
    /**
    * @generated
    */
    public Evento setEvento(Evento evento) {
        this.evento = evento;
    }
    
    /**
    * @generated
    */
    public Candidato getCandidato() {
        return this.candidato;
    }
    
    /**
    * @generated
    */
    public Candidato setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }
    
    /**
    * @generated
    */
    public Operatore getOperatore() {
        return this.operatore;
    }
    
    /**
    * @generated
    */
    public Operatore setOperatore(Operatore operatore) {
        this.operatore = operatore;
    }
    
    /**
    * @generated
    */
    public ElettoreOnline get() {
        return this.;
    }
    
    /**
    * @generated
    */
    public ElettoreOnline set(ElettoreOnline ) {
        this. = ;
    }
    

    //                          Operations                                  
    
    /**
    * @generated
    */
    public visualizzaPrenotatiOnline() {
        //TODO
    }
    
}
