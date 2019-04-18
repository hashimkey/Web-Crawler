package ch.makery.address.view;

import ch.makery.address.MainApp;
import ch.makery.address.model.Website;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class WebsiteOverviewController {
	@FXML
	private TableView<Website> websiteTable;
	@FXML
	private TableColumn<Website, String> urlColumn;
	@FXML
	private TableColumn<Website, Number> occurencesColumn;
	@FXML
	private Label urlLabel;
	@FXML
	private Label linksLabel;
	@FXML
	private TextArea linksToThis;
	private MainApp mainApp;

	public WebsiteOverviewController() {
	}

	@FXML
	private void initialize() {
		urlColumn.setCellValueFactory(cellData -> cellData.getValue().contentTypeProperty());
		occurencesColumn.setCellValueFactory(cellData -> cellData.getValue().occurencesProperty());
		showWebsiteDetails(null);
		websiteTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showWebsiteDetails(newValue));





    }



	@FXML
	private void handleStart() {
		mainApp.getEngine().setRun(true);
	}

	@FXML
	private void handleStop() {
		if (mainApp.getEngine().getRun()) {
			mainApp.getEngine().getExecutor().shutdownNow();
			mainApp.getEngine().setRun(false);
		}
	}

	private void showWebsiteDetails(Website website) {
		if (website != null) {
			urlLabel.setText(website.getUrl());
			linksLabel.setText(website.getLastName());
			linksToThis.setText("");
			for (Website link : website.getLinksToThis()) {
				linksToThis.appendText(link.getUrl() + "\n");
			}

		} else {
			urlLabel.setText("");
			linksLabel.setText("");
			linksToThis.setText("");
		}
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		websiteTable.setItems(mainApp.getWebsiteData());
	}
}