package view;

import controller.KitchenViewController;
import controller.OrderViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import model.bestelling.Bestellijn;
import model.bestelling.Bestelling;
import model.korting.KortingEnum;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



public class KitchenView {
	
	private Stage stage = new Stage();
	private Button volgendeBestelling, afwerken;
	private Label volgnrLabel;
	private TextArea bestellingInhoud;
	private KitchenViewController controller;
	
	public KitchenView(KitchenViewController controller){
		stage.setTitle("KITCHEN VIEW");
		stage.initStyle(StageStyle.UTILITY);
		stage.setX(680);
		stage.setY(470);
		this.controller = controller;
		controller.setView(this);
		Group root = new Group();
		Scene scene = new Scene(root, 650, 200);
		Pane pane = createNodeHierarchy(controller);
		root.getChildren().add(pane);

		stage.setScene(scene);
		stage.sizeToScene();			
		stage.show();		
	}

	private Pane createNodeHierarchy(KitchenViewController controller){
		VBox mainPane = new VBox();
		mainPane.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));

		//wachtrij
		HBox eersteRij = new HBox(100);
		eersteRij.setSpacing(340);
		eersteRij.setPadding(new Insets(5,5,5,5));
		volgendeBestelling = new Button("Volgende bestelling");
		volgendeBestelling.setOnAction(e -> volgendeBestelling());
		afwerken = new Button("Bestelling afgewerkt");
		afwerken.setOnAction(e -> bestellingAfwerken());
		afwerken.setDisable(true);
		volgendeBestelling.setDisable(true);

		eersteRij.getChildren().addAll(volgendeBestelling, afwerken);

		//bestelling view
		VBox tweederij = new VBox();
		tweederij.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		volgnrLabel = new Label("Bestellingen in wachtrij: 0\n");
		volgnrLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		volgnrLabel.setMinWidth(650);
		volgnrLabel.setPadding(new Insets(5,5,5,5));
		bestellingInhoud = new TextArea("Hier verschijnt de huidige bestelling");
		bestellingInhoud.minWidth(650);
		bestellingInhoud.setMinHeight(30);
		bestellingInhoud.setEditable(false);

		tweederij.getChildren().addAll(volgnrLabel, bestellingInhoud);


		mainPane.getChildren().addAll(eersteRij, tweederij);
		return mainPane;
	}

	private void volgendeBestelling(){
		Bestelling bestelling = controller.getEersteBestelling();
		if (bestelling == null){
			bestellingInhoud.setText("Er is nog geen bestelling toegevoegd");
		} else {
			bestellingInhoud.setText(controller.getEersteBestelling().getBestellingAsString());
			volgendeBestelling.setDisable(true);
		}
	}

	private void bestellingAfwerken(){
		controller.werkBestellingAf();
	}

	public void updateWachtrij(int wachtrijlengte){
		volgnrLabel.setText("Bestellingen in wachtrij: " + wachtrijlengte);
		if (wachtrijlengte == 0){
			afwerken.setDisable(true);
			volgendeBestelling.setDisable(true);
		} else {
			afwerken.setDisable(false);
			volgendeBestelling.setDisable(false);
		}
	}

	public void huidigeBestellingAfgewerkt(){
		if (bestellingInhoud.getText().equals("Hier verschijnt de huidige bestelling") || bestellingInhoud.getText().equals("Er is nog geen bestelling toegevoegd")) {
		} else {
			bestellingInhoud.setText("Deze bestelling is succesvol afgewerkt");
		}
		volgendeBestelling.setDisable(false);
	}
}
