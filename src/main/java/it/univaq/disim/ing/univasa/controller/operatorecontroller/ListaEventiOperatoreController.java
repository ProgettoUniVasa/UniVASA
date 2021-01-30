package it.univaq.disim.ing.univasa.controller.operatorecontroller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
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
import javafx.util.Callback;

public class ListaEventiOperatoreController implements Initializable, DataInitializable<Operatore> {

    @FXML
    private Button indietroButton;

    @FXML
    private TableView<Evento> listaEventiTable;

    @FXML
    private TableColumn<Evento, String> nomeTableColumn;

    @FXML
    private TableColumn<Evento, LocalDate> aperturaSeggioTableColumn;

    @FXML
    private TableColumn<Evento, LocalDate> chiusuraSeggioTableColumn;

    @FXML
    private TableColumn<Evento, String> luogoTableColumn;

    @FXML
    private TableColumn<Evento, Button> azioneTableColumn;

    private Operatore operatore;

    private ViewDispatcher dispatcher;

    private EventoService eventoService;

    public ListaEventiOperatoreController() {
        dispatcher = ViewDispatcher.getInstance();
        UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
        eventoService = factory.getEventoService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nomeTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Evento, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Evento, String> param) {
                        return new SimpleStringProperty(param.getValue().getNome());
                    }
                });

        aperturaSeggioTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Evento, LocalDate>, ObservableValue<LocalDate>>() {
                    @Override
                    public ObservableValue<LocalDate> call(CellDataFeatures<Evento, LocalDate> param) {
                        return new SimpleObjectProperty<LocalDate>(param.getValue().getDataInizio());
                    }
                });

        chiusuraSeggioTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Evento, LocalDate>, ObservableValue<LocalDate>>() {
                    @Override
                    public ObservableValue<LocalDate> call(CellDataFeatures<Evento, LocalDate> param) {
                        return new SimpleObjectProperty<LocalDate>(param.getValue().getDataFine());
                    }
                });

        luogoTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Evento, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Evento, String> param) {
                        return new SimpleStringProperty(param.getValue().getLuogo());
                    }
                });

        azioneTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Evento, Button>, ObservableValue<Button>>() {
                    @Override
                    public ObservableValue<Button> call(CellDataFeatures<Evento, Button> param) {
                        final Button azioneButton = new Button("Apri evento");
                        azioneButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                dispatcher.renderView("eventoOperatore", param.getValue());
                            }
                        });
                        return new SimpleObjectProperty<Button>(azioneButton);
                    }
                });

    }

    @Override
    public void initializeData(Operatore operatore) {
        try {
            this.operatore = operatore;
            List<Evento> eventiOperatore = eventoService.trovaTuttiEventi();
            ObservableList<Evento> eventiOperatoreData = FXCollections
                    .observableArrayList(eventiOperatore);
            listaEventiTable.setItems(eventiOperatoreData);
        } catch (BusinessException e) {
            dispatcher.renderError(e);
        }
    }

    @FXML
    public void indietroAction(ActionEvent event) throws BusinessException {
        dispatcher.renderView("dashboardOperatore", operatore);
    }

}
