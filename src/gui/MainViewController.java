package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;
import model.services.SellerService;

public class MainViewController implements Initializable {

	// Atributos dos itens de menu
	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuItemAbout;
	
	// Métodos para tratar os itens de menu
	@FXML
	public void onMenuItemSellerAction() {
		loadView("/gui/SellerList.fxml", (SellerListController controller) -> {
			controller.setSellerService(new SellerService());
			controller.updateTableView();
		});
	}
	@FXML
	public void onMenuItemDepartmentAction() {
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		});
	}
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x -> {});
	}
	
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
	}
	
	// Função para abrir outra tela
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene(); // obtendo a referencia da scene do main
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent(); // Buscando a referencia do VBox da janela principal
			
			Node mainMenu = mainVBox.getChildren().get(0); // Obtendo o primeiro children da VBox principal, que no caso é o menu que está na posição 0
			mainVBox.getChildren().clear(); // limpando os filhos da VBox principal
			
			// Montar o novo VBox preservando o menu
			mainVBox.getChildren().add(mainMenu); // Adicionando o menu novamente
			mainVBox.getChildren().addAll(newVBox.getChildren()); // Adicionando uma coleção addAll com os filhos da nova VBox
			
			T controller = loader.getController();
			initializingAction.accept(controller);
			
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}	
}
