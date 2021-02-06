package it.univaq.disim.ing.univasa.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteNotFoundException;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.domain.Utente;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController implements Initializable, DataInitializable<Object> {

	@FXML
	private Label loginErrorLabel;

	@FXML
	private TextField username;

	@FXML
	private PasswordField password;

	@FXML
	private Button loginButton;

	@FXML
	private Label registratiLabel;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	public LoginController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginButton.disableProperty().bind(username.textProperty().isEmpty().or(password.textProperty().isEmpty()));
	}

	@FXML
	private void loginAction(ActionEvent event) {
		try {
			Utente utente = utenteService.autenticazione(username.getText(), password.getText());
			dispatcher.loggedIn(utente);
		} catch (UtenteNotFoundException e) {
			loginErrorLabel.setText("Username e/o password errati!");
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	private void registratiAction(MouseEvent click) {
		Elettore elettore = new Elettore();
		dispatcher.registraElettore(elettore);
	}
}