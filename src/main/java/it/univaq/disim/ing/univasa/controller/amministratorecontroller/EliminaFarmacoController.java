package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.FarmacoService;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Farmaco;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class EliminaFarmacoController implements Initializable, DataInitializable<Farmaco> {

	@FXML
	private TextField nome;

	@FXML
	private TextField principioAttivo;

	@FXML
	private TextField produttore;

	@FXML
	private DatePicker scadenza;

	@FXML
	private TextField costo;

	@FXML
	private TextField quantitaDisponibile;

	@FXML
	private Button eliminaButton;

	@FXML
	private Button annullaButton;

	private ViewDispatcher dispatcher;

	private FarmacoService farmacoService;

	private Farmaco farmaco;

	private Amministratore amministratore;

	public EliminaFarmacoController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		farmacoService = factory.getFarmacoService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Farmaco farmaco) {
		this.farmaco = farmaco;
		this.nome.setText(farmaco.getNome());
		this.principioAttivo.setText(farmaco.getPrincipioAttivo());
		this.produttore.setText(farmaco.getProduttore());
		this.scadenza.setValue(farmaco.getScadenza());
		this.scadenza.setEditable(false);
		this.costo.setText("" + farmaco.getCosto());
		this.quantitaDisponibile.setText("" + farmaco.getQuantitaDisponibile());

		// Si disabilita il bottone se i campi di seguito non rispettano le proprietà
		// definite
		eliminaButton.disableProperty()
				.bind((nome.textProperty().isEmpty()
						.or(principioAttivo.textProperty().isEmpty().or(produttore.textProperty().isEmpty())
								.or(scadenza.valueProperty().isNull().or(costo.textProperty().isEqualTo("0.0")
										.or(quantitaDisponibile.textProperty().isEqualTo("0")))))));
	}

	@FXML
	public void eliminaAction(ActionEvent event) {

		try {

			farmacoService.eliminaFarmaco(farmaco);
			dispatcher.renderView("listaFarmaciAmministratore", amministratore);

		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.renderView("listaFarmaciAmministratore", amministratore);
	}
}