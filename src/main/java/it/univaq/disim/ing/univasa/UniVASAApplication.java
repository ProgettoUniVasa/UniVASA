package it.univaq.disim.ing.univasa;

import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import it.univaq.disim.ing.univasa.view.ViewException;
import javafx.application.Application;
import javafx.stage.Stage;

public class UniVASAApplication extends Application {

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
