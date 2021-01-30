package it.univaq.disim.ing.univasa.controller.operatorecontroller;

import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Operatore;
import it.univaq.disim.ing.univasa.domain.Utente;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardOperatoreController implements Initializable, DataInitializable<Utente> {

    @FXML
    private Button eventiButton;

    @FXML
    private Button lavoroButton;

    private ViewDispatcher dispatcher;

    private UtenteService utenteService;

    private Operatore operatore;

    public DashboardOperatoreController() {
        dispatcher = ViewDispatcher.getInstance();
        UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
        utenteService = factory.getUtenteService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void initializeData(Utente utente) {
    }

    @FXML
    public void mostraEventi(ActionEvent event) {
        dispatcher.renderView("listaEventiOperatore", operatore);
    }

    @FXML
    public void mostraLavoro(ActionEvent event) {
        dispatcher.renderView("lavoroOperatore", operatore);
    }

    @FXML
    public void logoutAction(ActionEvent event) {
        dispatcher.logout();
    }

}