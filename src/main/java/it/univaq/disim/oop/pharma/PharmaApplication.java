package it.univaq.disim.oop.pharma;

import it.univaq.disim.oop.pharma.view.ViewDispatcher;
import it.univaq.disim.oop.pharma.view.ViewException;
import javafx.application.Application;
import javafx.stage.Stage;

public class PharmaApplication extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		try {
			ViewDispatcher viewDispatcher = ViewDispatcher.getInstance();
			viewDispatcher.loginView(stage);
		} catch (ViewException e) {
			e.printStackTrace();
		}

	}

}
