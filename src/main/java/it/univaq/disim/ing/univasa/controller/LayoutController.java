package it.univaq.disim.ing.univasa.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.domain.Utente;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import javax.swing.*;

public class LayoutController implements Initializable, DataInitializable<Utente> {

    @FXML
    private VBox menuBar;

    private Utente utente;

    private ViewDispatcher dispatcher;

    private static final MenuElement MENU_HOME = new MenuElement("Home", "home");

    private static final MenuElement[] MENU_PAZIENTI = {
            new MenuElement("Area Riservata", "areaRiservata")};
    };

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

        if (utente instanceof Paziente) {
            for (MenuElement menu : MENU_PAZIENTI) {
                menuBar.getChildren().add(createButton(menu));
            }
        }
        if (utente instanceof Medico) {
            for (MenuElement menu : MENU_MEDICI) {
                menuBar.getChildren().add(createButton(menu));
            }
        }
        if (utente instanceof Farmacista) {
            for (MenuElement menu : MENU_FARMACISTI) {
                menuBar.getChildren().add(createButton(menu));
            }
        }
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

    private Button createButton(MenuElement viewItem){
            Button button=new Button(viewItem.getNome());
            button.setStyle("-fx-background-color: transparent; -fx-font-size: 14;");
            button.setTextFill(Paint.valueOf("white"));
            button.setPrefHeight(10);
            button.setPrefWidth(180);
            button.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event){
                        dispatcher.renderView(viewItem.getVista(),utente);
                }
            });
            return button;
    }

}
