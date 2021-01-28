package it.univaq.disim.ing.univasa.controller.operatorecontroller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.ElettoreInSede;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.domain.Operatore;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class EventoOperatoreController implements Initializable, DataInitializable<Evento> {

    @FXML
    private Button indietroButton;

    @FXML
    private TableView<ElettoreInSede> eventoTable;

    @FXML
    private TableColumn<ElettoreInSede, String> nomeTableColumn;

    @FXML
    private TableColumn<ElettoreInSede, String> cognomeTableColumn;

    @FXML
    private TableColumn<ElettoreInSede, String> matricolaTableColumn;

    private Evento evento;
    private Operatore operatore;

    private ViewDispatcher dispatcher;

    private UtenteService utenteService;

    public EventoOperatoreController() {
        dispatcher = ViewDispatcher.getInstance();
        MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
        utenteService = factory.getUtenteService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nomeTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ElettoreInSede, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<ElettoreInSede, String> param) {
                        return new SimpleStringProperty(param.getValue().getNome());
                    }
                });

        cognomeTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ElettoreInSede, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<ElettoreInSede, String> param) {
                        return new SimpleObjectProperty<String>(param.getValue().getCognome());
                    }
                });

        matricolaTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ElettoreInSede, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<ElettoreInSede, String> param) {
                        return new SimpleObjectProperty<String>(param.getValue().getMatricola());
                    }
                });

    }

    @Override
    public void initializeData(Evento evento) {
        try {
            this.evento = evento;
            List<ElettoreInSede> elettoriInSede = utenteService.visualizzaPrenotatiInSede(evento);
            ObservableList<ElettoreInSede> elettoriInSedeData = FXCollections
                    .observableArrayList(elettoriInSede);
            eventoTable.setItems(elettoriInSedeData);
        } catch (BusinessException e) {
            dispatcher.renderError(e);
        }
    }

    @FXML
    public void indietroAction(ActionEvent event) throws BusinessException {
        dispatcher.renderView("dashboardOperatore", operatore);
    }

}