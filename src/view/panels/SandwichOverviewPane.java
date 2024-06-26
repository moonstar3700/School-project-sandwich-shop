package view.panels;


import controller.AdminViewController;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.Beleg;
import model.Broodje;

import java.util.List;



public class SandwichOverviewPane extends GridPane{
	private List<Broodje> broodjes;
	private List<Beleg> beleggen;
	private ObservableList<Broodje> broodjesList;
	private ObservableList<Beleg> beleggenList;
	private TableView<Broodje> broodjesTabel;
	private TableView<Beleg> belegTabel;
	private AdminViewController controller;
	
	public SandwichOverviewPane(AdminViewController controller) {
		this.broodjes = controller.getBroodjes();
		this.beleggen = controller.getBeleggen();
		this.controller = controller;
		this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        //Broodjes
		this.add(new Label("Broodjes:"), 0, 0, 1, 1);
		broodjesTabel = new TableView<>();
		refreshBroodjes();
		// De colom van de broodjesnamen
		TableColumn<Broodje, String> colNaamBroodje = new TableColumn<Broodje, String>("Broodjesnaam");
		colNaamBroodje.setMinWidth(400);
		colNaamBroodje.setCellValueFactory(new PropertyValueFactory<Broodje, String>("naam"));
		// De colom van de prijzen
		TableColumn<Broodje, Double> colPrijsBroodje = new TableColumn<Broodje, Double>("Prijs");
		colPrijsBroodje.setMinWidth(100);
		colPrijsBroodje.setCellValueFactory(new PropertyValueFactory<Broodje, Double>("prijs"));
		// De colom van de voorraad
		TableColumn<Broodje, Integer> colVoorraadBroodje = new TableColumn<Broodje, Integer>("Voorraad");
		colVoorraadBroodje.setMinWidth(100);
		colVoorraadBroodje.setCellValueFactory(new PropertyValueFactory<Broodje, Integer>("voorraad"));
		// Voeg alles toe aan de tableview
		broodjesTabel.getColumns().addAll(colNaamBroodje, colPrijsBroodje, colVoorraadBroodje);
		this.add(broodjesTabel, 0, 1, 1, 1);

		//Beleg
		this.add(new Label("belegen:"), 0, 2, 1, 1);
		belegTabel = new TableView<>();
		refreshBeleggen();

		// De colom van de belegnamen
		TableColumn<Beleg, String> colNaamBeleg = new TableColumn<Beleg, String>("Belegnaam");
		colNaamBeleg.setMinWidth(400);
		colNaamBeleg.setCellValueFactory(new PropertyValueFactory<Beleg, String>("naam"));
		// De colom van de prijzen
		TableColumn<Beleg, Double> colPrijsBeleg = new TableColumn<Beleg, Double>("Prijs");
		colPrijsBeleg.setMinWidth(100);
		colPrijsBeleg.setCellValueFactory(new PropertyValueFactory<Beleg, Double>("prijs"));
		// De colom van de voorraad
		TableColumn<Beleg, Integer> colVoorraadBeleg = new TableColumn<Beleg, Integer>("Voorraad");
		colVoorraadBeleg.setMinWidth(100);
		colVoorraadBeleg.setCellValueFactory(new PropertyValueFactory<Beleg, Integer>("voorraad"));
		// Voeg alles toe aan de tableview
		belegTabel.getColumns().addAll(colNaamBeleg, colPrijsBeleg, colVoorraadBeleg);
		this.add(belegTabel, 0,3,1,1);
	}

	public void refreshBeleggen() {
		this.beleggen = controller.getBeleggen();
		beleggenList = FXCollections.observableList(this.beleggen);
		belegTabel.setItems(beleggenList);
		belegTabel.refresh();
	}

	public void refreshBroodjes() {
		this.broodjes = controller.getBroodjes();
		broodjesList = FXCollections.observableArrayList(this.broodjes);
		broodjesTabel.setItems(broodjesList);
		broodjesTabel.refresh();
	}
}
