package application;
	
import controller.AdminViewController;
import controller.KitchenViewController;
import controller.OrderViewController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.BestelFacade;
import view.AdminView;
import view.KitchenView;
import view.OrderView;

import java.io.File;
import java.io.IOException;



public class BroodjeszaakMain extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		BestelFacade facade = new BestelFacade();

		AdminViewController adcontroller = new AdminViewController(facade);
		AdminView adminView = new AdminView(adcontroller);
		OrderViewController orderViewController = new OrderViewController(facade);
		OrderView orderView = new OrderView(orderViewController);
		KitchenViewController kitController = new KitchenViewController(facade);
		KitchenView kitchenView = new KitchenView(kitController);
	}
	
	public static void main(String[] args) {launch(args);}
}
