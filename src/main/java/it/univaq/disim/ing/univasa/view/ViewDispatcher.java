package it.univaq.disim.ing.univasa.view;

import java.io.IOException;

import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.domain.Operatore;
import it.univaq.disim.ing.univasa.domain.Utente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ViewDispatcher {

	private static final String FXML_SUFFIX = ".fxml";
	private static final String RESOURCE_BASE = "/viste/";

	private static ViewDispatcher instance = new ViewDispatcher();

	private Stage stage;
	private BorderPane layout;

	private ViewDispatcher() {
	}

	// Visualizza la scena di login
	public void loginView(Stage stage) throws ViewException {
		this.stage = stage;
		try {
			Parent login = loadView("login").getView();
			Scene scene = new Scene(login);
			stage.setScene(scene);
			stage.show();
		} catch (ViewException e) {
			e.printStackTrace();

			throw new ViewException(e);
		}

	}

	// Visualizza la scena di benvenuto se l'autenticazione va a buon fine
	public void loggedIn(Utente utente) {
		try {
			View<Utente> layoutView = loadView("layout");
			DataInitializable<Utente> layoutController = layoutView.getController();
			layoutController.initializeData(utente);

			layout = (BorderPane) layoutView.getView();
			if (utente instanceof Amministratore) {
				renderView("dashboardAmministratore", utente);
			} else if (utente instanceof Operatore) {
				renderView("dashboardOperatore", utente);
			} else if (utente instanceof Elettore) {
				renderView("homeElettore", utente);
			}
			Scene scene = new Scene(layout);
			stage.setScene(scene);

		} catch (ViewException e) {
			renderError(e);
		}

	}

	// Esce dall'applicazione e visualizza la scena di login
	public void logout() {
		try {
			Parent loginView = loadView("login").getView();
			Scene scene = new Scene(loginView);
			stage.setScene(scene);
		} catch (ViewException e) {
			renderError(e);
		}
	}

	// Visualizza una generica vista e la pone al centro del layout
	public <T> void renderView(String viewName, T data) {
		try {
			View<T> view = loadView(viewName);
			DataInitializable<T> controller = view.getController();
			controller.initializeData(data);
			layout.setCenter(view.getView());

		} catch (ViewException e) {
			renderError(e);
		}
	}

	// Metodo per la registrazione di un elettore
	public void registratiAction(Elettore elettore) {
		try {
			View<Utente> layoutView = loadView("registraElettore");
			DataInitializable<Utente> layoutController = layoutView.getController();
			layoutController.initializeData(elettore);

			layout = (BorderPane) layoutView.getView();

			Scene scene = new Scene(layout);

			stage.setScene(scene);
		} catch (ViewException e) {
			e.printStackTrace();
		}
	}

	public void registraElettore(Elettore elettore) {
		try {
			View<Elettore> layoutView = loadView("registraElettore");
			DataInitializable<Elettore> layoutController = layoutView.getController();
			layoutController.initializeData(elettore);

			layout = (BorderPane) layoutView.getView();

			Scene scene = new Scene(layout);

			stage.setScene(scene);
		} catch (ViewException e) {
			e.printStackTrace();
		}
	}

	// Metodo che cattura le eccezioni
	public void renderError(Exception e) {
		e.printStackTrace();
		System.exit(1);
	}

	public static ViewDispatcher getInstance() {
		return instance;
	}

	// Metodo per ritornare gli oggetti di una vista
	private <T> View<T> loadView(String view) throws ViewException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(RESOURCE_BASE + view + FXML_SUFFIX));
			Parent parent = (Parent) loader.load();
			return new View<>(parent, loader.getController());

		} catch (IOException e) {
			throw new ViewException(e);
		}
	}

}
