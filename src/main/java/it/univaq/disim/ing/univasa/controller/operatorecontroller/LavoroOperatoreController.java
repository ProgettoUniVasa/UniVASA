package it.univaq.disim.ing.univasa.controller.operatorecontroller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.domain.Operatore;
import it.univaq.disim.ing.univasa.domain.TipologiaTurno;
import it.univaq.disim.ing.univasa.domain.Turnazione;
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

public class LavoroOperatoreController implements Initializable, DataInitializable<Operatore> {

    @FXML
    private Button indietroButton;

    @FXML
    private TableView<Turnazione> lavoroTable;

    @FXML
    private TableColumn<Turnazione, String> eventoNomeTableColumn;

    @FXML
    private TableColumn<Turnazione, LocalDate> giornoTableColumn;

    @FXML
    private TableColumn<Turnazione, TipologiaTurno> fasciaTableColumn;

    private Operatore operatore;

    private ViewDispatcher dispatcher;

    private UtenteService utenteService;

    public LavoroOperatoreController() {
        dispatcher = ViewDispatcher.getInstance();
        MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
        utenteService = factory.getUtenteService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        eventoNomeTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Turnazione, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Turnazione, String> param) {
                        return new SimpleStringProperty(param.getValue().getEvento().getNome());
                    }
                });

        giornoTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Turnazione, LocalDate>, ObservableValue<LocalDate>>() {
                    @Override
                    public ObservableValue<LocalDate> call(CellDataFeatures<Turnazione, LocalDate> param) {
                        return new SimpleObjectProperty<LocalDate>(param.getValue().getData_turno());
                    }
                });

        fasciaTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Turnazione, TipologiaTurno>, ObservableValue<TipologiaTurno>>() {
                    @Override
                    public ObservableValue<TipologiaTurno> call(CellDataFeatures<Turnazione, TipologiaTurno> param) {
                        return new SimpleObjectProperty<TipologiaTurno>(param.getValue().getFascia());
                    }
                });

    }

    @Override
    public void initializeData(Operatore operatore) {
        try {
            this.operatore = operatore;
            List<Turnazione> lavoroOperatore = utenteService.visualizzaTurnazioni(operatore);
            ObservableList<Turnazione> lavoroOperatoreDate = FXCollections
                    .observableArrayList(lavoroOperatore);
            lavoroTable.setItems(lavoroOperatoreDate);
        } catch (BusinessException e) {
            dispatcher.renderError(e);
        }
    }

    @FXML
    public void indietroAction(ActionEvent event) throws BusinessException {
        dispatcher.renderView("dashboardOperatore", operatore);
    }

}
