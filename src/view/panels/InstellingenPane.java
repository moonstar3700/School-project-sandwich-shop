package view.panels;

import controller.AdminViewController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.korting.KortingEnum;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class InstellingenPane extends Pane {
    private Button save;
    private ComboBox kortingStrats, saveStrategies;

    public InstellingenPane(AdminViewController controller) {
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        VBox mainPane = new VBox(20);

        //De box die alles over de save methode bevat
        VBox saveStrategie = new VBox(10);
        saveStrategie.setPadding(new Insets(15,15,15,15));
        saveStrategie.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        saveStrategie.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        saveStrategie.setMinWidth(650);
        Label kiesStrategie = new Label("Kies het formaat waarin je de gegevens wilt opslaan:");

        ArrayList<String> types = new ArrayList<>(Arrays.asList("tekst", "excel"));
        saveStrategies = new ComboBox(FXCollections.observableArrayList(types));
        saveStrategies.getSelectionModel().selectFirst();

        saveStrategie.getChildren().addAll(kiesStrategie, saveStrategies);

        //Alles over de voorkeur kortingstrategie
        VBox kortingStrategie = new VBox(10);
        kortingStrategie.setPadding(new Insets(15,15,15,15));
        kortingStrategie.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        kortingStrategie.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        kortingStrategie.setMinWidth(650);
        Label kortingLabel = new Label("Kies hier de standaard aangeduide korting strategie:");
        //TODO eens de kortingstrategies zijn aangemaakt dit aanpassen
        ArrayList<String> kortingen = new ArrayList(Arrays.asList(KortingEnum.values()));
        kortingStrats = new ComboBox(FXCollections.observableArrayList(kortingen));
        kortingStrats.getSelectionModel().selectFirst();

        kortingStrategie.getChildren().addAll(kortingLabel, kortingStrats);

        save = new Button("Save");
        save.setOnAction(e -> saveSettings());

        mainPane.getChildren().addAll(saveStrategie, kortingStrategie, save);
        this.getChildren().addAll(mainPane);

    }

    private void saveSettings() {
        Properties properties = new Properties();
        try {
            FileOutputStream os = new FileOutputStream("src/bestanden/settings.properties");
            String saveStrategy = (String) saveStrategies.getValue();
            if (saveStrategy.equals("excel")) {
                properties.setProperty("broodjesFile", "src/bestanden/broodjes.xls");
                properties.setProperty("belegFile", "src/bestanden/beleg.xls");
                properties.setProperty("fileType", "excel");
            } else if (saveStrategy.equals("tekst")){
                properties.setProperty("broodjesFile", "src/bestanden/broodjes.txt");
                properties.setProperty("belegFile", "src/bestanden/beleg.txt");
                properties.setProperty("fileType", "tekst");
            }
            properties.setProperty("korting", kortingStrats.getValue().toString());
            properties.store(os, "");
            os.close();
        }catch (Exception e){
            System.out.println("Fout bij opslaan van instellingen");
        }
    }
}
