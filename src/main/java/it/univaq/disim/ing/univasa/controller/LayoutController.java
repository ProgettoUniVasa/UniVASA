package it.univaq.disim.ing.univasa.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Utente;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class LayoutController implements Initializable, DataInitializable<Utente> {

	private static final MenuElement MENU_HOME = new MenuElement("Home", "dashboardAmministratore");

	private static final MenuElement[] MENU_AMMINISTRATORE = { new MenuElement("Area Riservata", "areaRiservataAmministratore") };
	@FXML
	private VBox menuBar;

	private Utente utente;

	private ViewDispatcher dispatcher;

	public LayoutController() {
        dispatcher = ViewDispatcher.getInstance();
    }

	@Override
    public void initialize(URL location, ResourceBundle resources) {
    }

	@Override
    public void initializeData(Utente utente) {
        this.utente = utente;

       menuBar.getChildren().addAll(createButton(MENU_HOME));
       menuBar.getChildren().add(new Separator());
        
        if (utente instanceof Amministratore) {
            for (MenuElement menu : MENU_AMMINISTRATORE) {
                menuBar.getChildren().add(createButton(menu));
            }
        }
    }

	@FXML
    public void esciAction(MouseEvent event) {
        dispatcher.logout();
    }

	private Button createButton(MenuElement viewItem) {
		Button button = new Button(viewItem.getNome());
		button.setStyle("-fx-background-color: transparent; -fx-font-size: 14;");
		button.setTextFill(Paint.valueOf("white"));
		button.setPrefHeight(10);
		button.setPrefWidth(180);
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dispatcher.renderView(viewItem.getVista(), utente);
			}
		});
		return button;
	}

}
