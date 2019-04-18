package ch.makery.address.view;

import java.io.File;

import ch.makery.address.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

public class RootLayoutController {
	// Reference to the main application
    private MainApp mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	/**
     * Creates an empty address book.
     */
    @FXML
    private void handleAddWebsite() {
        mainApp.getWebsiteData().clear();
        mainApp.setWebsiteFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select to load.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadWebsiteDataFromFile(file);
        }
    }

    /**
     * Saves the file to the person file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSave() {
        File personFile = mainApp.getWebsiteFilePath();
        if (personFile != null) {
            mainApp.saveWebsiteDataToFile(personFile);
        } else {
        	 handleSaveAs();
        }
    }



	/**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.saveWebsiteDataToFile(file);
        }
    }


    @FXML
	public void alert(){
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Student Information");
		alert.setContentText("Student Name:Hashim\n Student No:535600\n i used the same classes and packages\n as the javafx tutorial\n"
				+ "http://code.makery.ch/library/javafx-8-tutorial/part7/");
		alert.showAndWait();
	}
    @FXML
    public void graphAlert(){
    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
    	alert.setTitle("Menu information");
    	alert.setContentText("menu definition will be!");
    	alert.showAndWait();
    }
	@FXML
	private void exit(){
		System.exit(0);
	}

}
