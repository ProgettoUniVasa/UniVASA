package it.univaq.disim.ing.univasa.controller.operatorecontroller;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Candidato;
import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.domain.Operatore;
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
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CaricaVotiOperatoreController implements Initializable, DataInitializable<Evento> {

    @FXML
    private TableView<Candidato> votiTable;

    @FXML
    private TableColumn<Candidato, String> nomeTableColumn;

    @FXML
    private TableColumn<Candidato, String> cognomeTableColumn;

    @FXML
    private TableColumn<Candidato, String> emailTableColumn;

    @FXML
    private TableColumn<Candidato, Button> azioneTableColumn;

    private Evento evento;
    private Operatore operatore;

    private ViewDispatcher dispatcher;

    private EventoService eventoService;

    public CaricaVotiOperatoreController() {
        dispatcher = ViewDispatcher.getInstance();
        UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
        eventoService = factory.getEventoService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nomeTableColumn.setCellValueFactory(
                new Callback<CellDataFeatures<Candidato, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Candidato, String> param) {
                        return new SimpleStringProperty(param.getValue().getNome());
                    }
                });

        cognomeTableColumn.setCellValueFactory(
                new Callback<CellDataFeatures<Candidato, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Candidato, String> param) {
                        return new SimpleObjectProperty<String>(param.getValue().getCognome());
                    }
                });

        emailTableColumn.setCellValueFactory(
                new Callback<CellDataFeatures<Candidato, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Candidato, String> param) {
                        return new SimpleObjectProperty<String>(param.getValue().getEmail());
                    }
                });

        azioneTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Candidato, Button>, ObservableValue<Button>>() {
                    @Override
                    public ObservableValue<Button> call(CellDataFeatures<Candidato, Button> param) {
                        final Button azioneButton = new Button("Aggiungi voti");
                        azioneButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                dispatcher.renderView("aggiungiVotiOperatore", param.getValue());
                            }
                        });
                        return new SimpleObjectProperty<Button>(azioneButton);
                    }
                });

    }

    @Override
    public void initializeData(Evento evento) {
        try {
            this.evento = evento;
            List<Candidato> candidati = eventoService.visualizzaCandidati(evento);
            ObservableList<Candidato> candidatiData = FXCollections.observableArrayList(candidati);
            votiTable.setItems(candidatiData);
        } catch (BusinessException e) {
            dispatcher.renderError(e);
        }
    }

    @FXML
    public void indietroAction(ActionEvent event) throws BusinessException {
        dispatcher.renderView("listaEventiOperatore", operatore);
    }

}