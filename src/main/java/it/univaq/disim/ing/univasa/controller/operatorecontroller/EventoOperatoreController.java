package it.univaq.disim.ing.univasa.controller.operatorecontroller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.*;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class EventoOperatoreController implements Initializable, DataInitializable<Turnazione> {

    @FXML
    private Button indietroButton;

    @FXML
    private TableView<Elettore> eventoTable;

    @FXML
    private TableColumn<Elettore, String> nomeTableColumn;

    @FXML
    private TableColumn<Elettore, String> cognomeTableColumn;

    @FXML
    private TableColumn<Elettore, String> emailTableColumn;

    @FXML
    private TableColumn<Elettore, String> matricolaTableColumn;

    @FXML
    private TableColumn<Elettore, Button> segnaVotazioneTableColumn;

    private Evento evento;
    private Operatore operatore;
    private Turnazione turnazione;

    private ViewDispatcher dispatcher;

    private EventoService eventoService;

    public EventoOperatoreController() {
        dispatcher = ViewDispatcher.getInstance();
        UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
        eventoService = factory.getEventoService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nomeTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Elettore, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Elettore, String> param) {
                        return new SimpleStringProperty(param.getValue().getNome());
                    }
                });

        cognomeTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Elettore, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Elettore, String> param) {
                        return new SimpleObjectProperty<String>(param.getValue().getCognome());
                    }
                });

        emailTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Elettore, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Elettore, String> param) {
                        return new SimpleObjectProperty<String>(param.getValue().getEmail());
                    }
                });

        matricolaTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Elettore, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Elettore, String> param) {
                        return new SimpleObjectProperty<String>(param.getValue().getMatricola());
                    }
                });

        segnaVotazioneTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Elettore, Button>, ObservableValue<Button>>() {
                    @Override
                    public ObservableValue<Button> call(CellDataFeatures<Elettore, Button> param) {
                        try {
                            if (eventoService.verificaHaVotato(evento, param.getValue())) {
                                final Button azioneButton = new Button("Segna voto");
                                azioneButton.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        try {
                                            eventoService.votaInPresenza(evento, param.getValue());
                                        } catch (BusinessException e) {
                                            e.printStackTrace();
                                        }
                                        dispatcher.renderView("eventoOperatore", turnazione);
                                    }
                                });
                                return new SimpleObjectProperty<Button>(azioneButton);
                            } else {
                                return null;
                            }
                        } catch (BusinessException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                });
    }

    @Override
    public void initializeData(Turnazione turnazione) {
        try {
            this.turnazione = turnazione;
            this.evento = turnazione.getEvento();
            this.operatore = turnazione.getOperatore();
            List<Elettore> elettoriInSede = eventoService.visualizzaPrenotatiInSede(evento);
            ObservableList<Elettore> elettoriInSedeData = FXCollections.observableArrayList(elettoriInSede);
            eventoTable.setItems(elettoriInSedeData);
        } catch (BusinessException e) {
            dispatcher.renderError(e);
        }
    }

    @FXML
    public void indietroAction(ActionEvent event) throws BusinessException {
        dispatcher.renderView("listaEventiOperatore", operatore);
    }

}