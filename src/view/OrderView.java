package view;

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
import model.Beleg;
import model.Broodje;
import model.bestelling.Bestellijn;
import model.bestelling.Bestelling;
import model.comparators.BelegComparatorByNaam;
import model.comparators.BroodjesComparatorByNaam;
import model.korting.KortingEnum;

import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;



public class OrderView {
	private int volgnr;
	private Stage stage = new Stage();
	private Button nieuweBestellingButton,
			zelfdeBroodje,
			verwijderBroodje,
			annuleer,
			afsluiten,
			betaal,
			naarKeuken;
	private Label volgnrLabel;
	private Pane kolomKeuze, kolomKeuzeBroodjes, kolomKeuzeBeleg;
	private TableColumn<Bestellijn, String> colBroodje, colBeleg;
	private TableView bestellijnTabel;
	private OrderViewController controller;
	private ObservableList<Bestellijn> bestellijnObservableList;
	private Alert alert = new Alert(Alert.AlertType.ERROR);
	private boolean actief = false;
	private Label prijs;
	private ChoiceBox promoties;

	public OrderView(OrderViewController controller){
		this.controller = controller;
		this.controller.setView(this);
		Bestelling bestelling = controller.voegBestellingToe();
		this.volgnr = bestelling.getVolgnr();
		stage.setTitle("ORDER VIEW");
		stage.initStyle(StageStyle.UTILITY);
		stage.setX(20);
		stage.setY(20);
		Group root = new Group();
		Scene scene = new Scene(root, 650, 650);
		Pane pane = createNodeHierarchy(controller);
		root.getChildren().add(pane);


		stage.setScene(scene);
		stage.sizeToScene();			
		stage.show();		
	}

	private Pane createNodeHierarchy(OrderViewController controller){
		VBox mainPane = new VBox(15);
		mainPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

		//eerste rij
		HBox eersteRij = new HBox(100);
		eersteRij.setSpacing(125);
		nieuweBestellingButton = new Button("Nieuwe bestelling");
		nieuweBestellingButton.setOnAction(e -> nieuweBestelling());
		volgnrLabel = new Label("Volgnr: " + this.volgnr);
		promoties = new ChoiceBox();
		promoties.setMinWidth(200);
		promoties.setPrefWidth(150);
		List kortingen = Arrays.asList(KortingEnum.values());
		for (Object korting: kortingen){
			promoties.getItems().add(korting.toString());
		}
		selectStandaardKorting();

		eersteRij.getChildren().addAll(nieuweBestellingButton, volgnrLabel, promoties);

		// kolomkeuze maken
		kolomKeuze = new VBox(15);
		kolomKeuze.setPadding(new Insets(5,5,5,5));
		kolomKeuze.setMinWidth(650);
		kolomKeuzeBroodjes = new HBox(15);
		kolomKeuzeBeleg = new HBox(15);

		kolomKeuze.setBackground(
				new Background(
						new BackgroundFill(Color.LIGHTYELLOW,
								CornerRadii.EMPTY, Insets.EMPTY)));
		kolomKeuze.setBorder(new Border(new BorderStroke(Color.BLACK,
				BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));




		//Alle broodjes
		List<Broodje> broodjes = controller.getBroodjes();
		broodjes.sort(new BroodjesComparatorByNaam());
		for (Broodje broodje : broodjes) {
			if (broodje.getVoorraad() > 0) {
				Button button = new Button(broodje.getNaam());
				button.setOnAction(e -> {
					try {
						toevoegenBroodje(broodje.getNaam());
					} catch (IOException ioException) {
						ioException.printStackTrace();
					}
				});
				button.setDisable(true);
				kolomKeuzeBroodjes.getChildren().add(button);
			}
		}

		//Alle beleg
		List<Beleg> beleggen = controller.getBeleggen();
		beleggen.sort(new BelegComparatorByNaam());
		for (Beleg beleg : beleggen) {
			if (beleg.getVoorraad() > 0){
				Button button = new Button(beleg.getNaam());
				button.setOnAction(e -> addBeleg(beleg.getNaam()));
				button.setDisable(true);
				kolomKeuzeBeleg.getChildren().add(button);
			}
		}
		kolomKeuze.getChildren().add(kolomKeuzeBroodjes);
		kolomKeuze.getChildren().add(kolomKeuzeBeleg);

		//Rij 3
		HBox rij3 = new HBox();
		Label aantalBroodjes = new Label("Aantal broodjes: ");
		rij3.getChildren().add(aantalBroodjes);

		//Rij 4
		HBox rij4 = new HBox(15);
		//Tabel
		bestellijnTabel = new TableView();
		bestellijnTabel.setMaxHeight(375);
		//Broodje
		colBroodje = new TableColumn<Bestellijn, String>("Broodje");
		colBroodje.setMinWidth(150);
		colBroodje.setCellValueFactory(new PropertyValueFactory<Bestellijn, String>("naamBroodje"));
		//Beleg
		colBeleg = new TableColumn<>("Beleg");
		colBeleg.setMinWidth(250);
		colBeleg.setCellValueFactory(new PropertyValueFactory<Bestellijn, String>("namenBeleg"));

		bestellijnTabel.getColumns().addAll(colBroodje, colBeleg);

		//Laatste kolom
		VBox kolom2 = new VBox(15);

		//ButtonBox
		VBox buttonBox = new VBox(15);

		buttonBox.setPadding(new Insets(20,20,20,20));
		Label lijnLijst = new Label("Selecteer een lijn uit de lijst");

		zelfdeBroodje = new Button("Voeg zelfde broodje toe");
		zelfdeBroodje.setDisable(true);
		zelfdeBroodje.setOnAction(e -> voegZelfdeBroodjeToe());

		verwijderBroodje = new Button("Verwijder broodje");
		verwijderBroodje.setDisable(true);
		verwijderBroodje.setOnAction(e -> verwijderBestellijn());

		buttonBox.getChildren().addAll(lijnLijst, zelfdeBroodje, verwijderBroodje);
		buttonBox.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
		buttonBox.setBorder(new Border(new BorderStroke(Color.BLACK,
				BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		//Annuleer button
		annuleer = new Button("Annuleer bestelling");
		annuleer.setDisable(true);
		annuleer.setOnAction(e -> annuleerBestelling());

		kolom2.getChildren().addAll(buttonBox, annuleer);
		rij4.getChildren().addAll(bestellijnTabel, kolom2);

		//Rij 5
		HBox rij5 = new HBox(15);
		rij5.setPadding(new Insets(20,20,20,20));
		afsluiten = new Button("Afsluiten bestelling");
		afsluiten.setDisable(true);
		afsluiten.setOnAction(e -> sluitBestellingAf());
		Label teBetalen = new Label("Te betalen:");
		this.prijs = new Label("");
		betaal = new Button("Betaal");
		betaal.setDisable(true);
		betaal.setOnAction(e -> betaal());
		naarKeuken = new Button("Naar keuken");
		naarKeuken.setDisable(true);
		naarKeuken.setOnAction(e -> naarKeuken());
		rij5.getChildren().addAll(afsluiten, teBetalen, prijs, betaal, naarKeuken);
		rij5.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
		rij5.setBorder(new Border(new BorderStroke(Color.BLACK,
				BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		//panes in elkaar zetten
		mainPane.getChildren().addAll(eersteRij, kolomKeuze, rij3, rij4, rij5);

		return mainPane;
	}


	private void selectStandaardKorting() {
		Properties properties = new Properties();
		try {
			InputStream is = new FileInputStream("src/bestanden/settings.properties");
			properties.load(is);
			String korting = properties.getProperty("korting");
			promoties.getSelectionModel().select(korting);
		} catch (IOException e){
			System.out.println("Probleem met properties");
		}
	}

	private void voegZelfdeBroodjeToe() {
		Bestellijn bestellijn = (Bestellijn) bestellijnTabel.getSelectionModel().getSelectedItem();
		if (bestellijn != null) {
			try {
				controller.voegZelfdeBroodjeToe(bestellijn, controller.getBestelling(volgnr));
			} catch (Exception e){
				alert.setContentText("Er is niet genoeg voorraad om dit broodje terug toe te voegen");
				alert.show();
			}
		}else {
			alert.setContentText("Je moet een bestellijn selecteren voordat je een eenzelfde broodje kan toevoegen");
			alert.show();
		}
	}

	private void verwijderBestellijn() {
		Bestellijn bestellijn = (Bestellijn) bestellijnTabel.getSelectionModel().getSelectedItem();
		if (bestellijn != null) {
			controller.verwijderBestellijn(bestellijn, controller.getBestelling(volgnr));
		} else {
			alert.setContentText("Je moet een bestellijn selecteren voordat je een broodje kan verwijderen");
			alert.show();
		}
	}

	public void toevoegenBroodje(String broodje) throws IOException {
		controller.voegBestellijnToe(broodje, controller.getBestelling(volgnr));
	}

	public void addBeleg(String beleg){
		Bestellijn bestellijn = (Bestellijn) bestellijnTabel.getSelectionModel().getSelectedItem();
		if (bestellijn != null) {
			controller.voegBelegToeAanBestellijn(bestellijn, beleg, controller.getBestelling(volgnr));
		} else {
			alert.setContentText("Je moet een bestellijn selecteren voordat je beleg kan toevoegen");
			alert.show();
		}
	}

	public void nieuweBestelling(){
		selectStandaardKorting();
		actief = true;
		this.prijs.setText("");
		this.volgnrLabel.setText("Volgnr: " + volgnr);
		nieuweBestellingButton.setDisable(true);
		zelfdeBroodje.setDisable(false);
		verwijderBroodje.setDisable(false);
		annuleer.setDisable(false);
		afsluiten.setDisable(false);
		controller.startBestelling(controller.getBestelling(this.volgnr));

		disableKeuzeButtons(false);
	}

	public void annuleerBestelling(){
		actief = false;
        this.prijs.setText("");
		this.volgnrLabel.setText("Volgnr: ");
		nieuweBestellingButton.setDisable(false);
		zelfdeBroodje.setDisable(true);
		verwijderBroodje.setDisable(true);
		annuleer.setDisable(true);
		afsluiten.setDisable(true);
		betaal.setDisable(true);
		naarKeuken.setDisable(true);

		disableKeuzeButtons(true);
		controller.verwijderBestelling(controller.getBestelling(volgnr));
		volgnr = controller.voegBestellingToe().getVolgnr();
	}

	private void disableKeuzeButtons(boolean b) {
		List<Node> broodjesChildren = kolomKeuzeBroodjes.getChildren();
		for (Node broodje : broodjesChildren) {
			Button button = (Button) broodje;
			button.setDisable(b);
		}

		List<Node> belegChildren = kolomKeuzeBeleg.getChildren();
		for (Node beleg : belegChildren) {
			Button button = (Button) beleg;
			button.setDisable(b);
		}
	}

	public void updateBestellijnen(List<Bestellijn> bestellijnen){
		bestellijnObservableList = FXCollections.observableArrayList(bestellijnen);
		bestellijnTabel.setItems(bestellijnObservableList);
		bestellijnTabel.refresh();
	}

	public void updateBroodjesKnoppen(Map<String, Integer> voorraadLijst){
		List<Node> broodjesChildren = kolomKeuzeBroodjes.getChildren();
		for (Node broodje : broodjesChildren){
			Button button = (Button) broodje;
			int voorraad = voorraadLijst.get(button.getText());
			if (voorraad > 0 && actief) {
				button.setDisable(false);
			} else {
				button.setDisable(true);
			}
		}
	}

	public void updateBelegKnoppen(Map<String, Integer> voorraadLijstBeleg) {
		List<Node> belegChildren = kolomKeuzeBeleg.getChildren();
		for (Node beleg : belegChildren){
			Button button = (Button) beleg;
			int voorraad = voorraadLijstBeleg.get(button.getText());
			if (voorraad > 0 && actief){
				button.setDisable(false);
			}else {
				button.setDisable(true);
			}
		}
	}

	public int getVolgnr() {
		return volgnr;
	}

	public void getPrijsBestelling(){
		String promotie = promoties.getSelectionModel().getSelectedItem().toString();
		Double totPrijs = controller.berekenPrijs(controller.getBestelling(this.volgnr), promotie);
		betaal.setDisable(false);
		String w = totPrijs.toString();
		this.prijs.setText(w);
    }

	public void betaal(){
		/**all other buttons inactive functions**/
		betaal.setDisable(true);
		naarKeuken.setDisable(false);
		controller.betaalBestelling(controller.getBestelling(volgnr));
		/** set state to IN wacht **/
	}

	public void naarKeuken(){
		actief = false;
		Bestelling bestelling = controller.getBestelling(this.volgnr);
		controller.naarKeuken(bestelling);
		naarKeuken.setDisable(true);
		annuleer.setDisable(true);
		nieuweBestellingButton.setDisable(false);
		bestelling = controller.voegBestellingToe();
		this.volgnr = bestelling.getVolgnr();
		volgnrLabel.setText("Volgnr: " + this.volgnr);
	}


	private void sluitBestellingAf() {
		controller.sluitBestellingAf(controller.getBestelling(volgnr));
		getPrijsBestelling();
		zelfdeBroodje.setDisable(true);
		verwijderBroodje.setDisable(true);
		afsluiten.setDisable(true);
		disableKeuzeButtons(true);
	}

}



/*BorderPane borderPane = new OrderViewMainPane();
		borderPane.prefHeightProperty().bind(scene.heightProperty());
		borderPane.prefWidthProperty().bind(scene.widthProperty());
		root.getChildren().add(borderPane);*/