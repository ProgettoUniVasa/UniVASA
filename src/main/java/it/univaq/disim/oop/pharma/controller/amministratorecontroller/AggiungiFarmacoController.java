package it.univaq.disim.oop.pharma.controller.amministratorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.pharma.business.BusinessException;
import it.univaq.disim.oop.pharma.business.FarmacoService;
import it.univaq.disim.oop.pharma.business.MyPharmaBusinessFactory;
import it.univaq.disim.oop.pharma.controller.DataInitializable;
import it.univaq.disim.oop.pharma.domain.Amministratore;
import it.univaq.disim.oop.pharma.domain.Farmaco;
import it.univaq.disim.oop.pharma.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AggiungiFarmacoController implements Initializable, DataInitializable<Farmaco> {

	@FXML
	private TextField nome;

	@FXML
	private TextField principioAttivo;

	@FXML
	private TextField produttore;

	@FXML
	private TextField costo;

	@FXML
	private TextField quantitaDisponibile;

	@FXML
	private DatePicker scadenza;

	@FXML
	private Button salvaButton;

	@FXML
	private Button annullaButton;

	private ViewDispatcher dispatcher;

	private FarmacoService farmacoService;

	private Farmaco farmaco;

	private Amministratore amministratore;

	public AggiungiFarmacoController() {
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
		this.costo.setText("" + farmaco.getCosto());
		this.quantitaDisponibile.setText("" + farmaco.getQuantitaDisponibile());

		// Si disabilita il bottone se i campi di seguito non rispettano le proprietà
		// definite
		salvaButton.disableProperty()
				.bind((nome.textProperty().isEmpty()
						.or(principioAttivo.textProperty().isEmpty().or(produttore.textProperty().isEmpty())
								.or(scadenza.valueProperty().isNull().or(costo.textProperty().isEqualTo("0.0")
										.or(quantitaDisponibile.textProperty().isEqualTo("0")))))));
	}

	@FXML
	public void salvaAction(ActionEvent event) {
		try {
			farmaco.setNome(nome.getText());
			farmaco.setPrincipioAttivo(principioAttivo.getText());
			farmaco.setProduttore(produttore.getText());
			farmaco.setScadenza(scadenza.getValue());
			farmaco.setCosto((Double.parseDouble(costo.getText())));
			farmaco.setQuantitaDisponibile(Integer.parseInt(quantitaDisponibile.getText()));

			if (farmaco.getId() == null) {
				farmacoService.creaFarmaco(farmaco);

			}

			dispatcher.renderView("listaFarmaciAmministratore", amministratore);

		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.renderView("listaFarmaciAmministratore", amministratore);
	}
}