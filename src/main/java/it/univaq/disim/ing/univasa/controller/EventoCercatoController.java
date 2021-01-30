package it.univaq.disim.ing.univasa.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class EventoCercatoController implements Initializable, DataInitializable<Evento> {

	@FXML
	private TableView<Evento> eventiTable;

	@FXML
	private TableColumn<Evento, String> nomeTableColumn;

	@FXML
	private TableColumn<Evento, String> regolamentoTableColumn;

	@FXML
	private TextField nome;

	@FXML
	private DatePicker dataInizio;

	@FXML
	private TextField oraInizio;

	@FXML
	private TextField luogo;

	@FXML
	private Button indietroButton;

	private ViewDispatcher dispatcher;

	private EventoService eventoService;

	private Evento evento;

	private Long id;

	public EventoCercatoController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		eventoService = factory.getEventoService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nomeTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Evento, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Evento, String> param) {
						return new SimpleStringProperty(param.getValue().getNome());
					}
				});

		regolamentoTableColumn.setCellValueFactory(new PropertyValueFactory<>("regolamento"));
	}

	@Override
	public void initializeData(Evento evento) {
		List<Evento> eventi;

		try {
			this.evento = evento;
			this.nome.setText(evento.getNome());
			this.dataInizio.setValue(evento.getDataInizio());
			this.luogo.setText(evento.getLuogo());
			this.oraInizio.setText(evento.getOraInizio());

			eventi = eventoService.trovaEventiDaLuogo(evento.getLuogo());
			ObservableList<Evento> eventiData = FXCollections.observableArrayList(eventi);
			eventiTable.setItems(eventiData);
		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void indietroAction(ActionEvent event) {
		dispatcher.renderView("elencoTuttiGliEventiElettore", evento);
	}

}
