package view;

import controller.AdminViewController;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class AdminView {
	private Stage stage = new Stage();
	private AdminMainPane adminMainPane;
	private AdminViewController controller;
		
	public AdminView(AdminViewController controller){
		controller.setView(this);
		this.controller = controller;
		stage.setTitle("ADMIN VIEW");
		stage.initStyle(StageStyle.UTILITY);
		stage.setX(680);
		stage.setY(20);
		stage.setOnCloseRequest(event -> slaVoorraadVeranderingOp());
		Group root = new Group();
		Scene scene = new Scene(root, 650, 400);
		adminMainPane = new AdminMainPane(controller);
		adminMainPane.prefHeightProperty().bind(scene.heightProperty());
		adminMainPane.prefWidthProperty().bind(scene.widthProperty());
		root.getChildren().add(adminMainPane);
		stage.setScene(scene);
		stage.sizeToScene();			
		stage.show();		
	}

	private void slaVoorraadVeranderingOp() {
		controller.slaVoorraadVeranderingOp();
	}

	public void updateVoorraad(){
		adminMainPane.updateVoorraad();
	}
}
