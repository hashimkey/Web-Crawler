package ch.makery.address;

import java.io.File;
import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import ch.makery.address.engine.Crawler;
import ch.makery.address.engine.DataManager;
import ch.makery.address.model.Website;
import ch.makery.address.model.WebsiteListWrapper;
import ch.makery.address.view.RootLayoutController;
import ch.makery.address.view.WebsiteOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private DataManager engine;

	private ObservableList<Website> websiteData = FXCollections.observableArrayList();
	private SortedMap<Website, Integer> sites = new TreeMap<Website, Integer>();


	public MainApp() {
		engine = new DataManager();
		DataManager.setMainApp(this);
		Crawler.setMainApp(this);
		Website w = new Website("http://www.google.nl/", 0);
		websiteData.add(w);
		sites.put(w, 0);

		//Collections.reverseOrder());


	}

	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			// Give the controller access to the main app.
	        RootLayoutController controller = loader.getController();
	        controller.setMainApp(this);

			primaryStage.show();
		} catch (IOException e) {
		}
		// Try to load last opened website page.
	    File file = getWebsiteFilePath();
	    if (file != null) {
	        loadWebsiteDataFromFile(file);
	    }
	}

	public void showWebsiteOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/WebsiteOverview.fxml"));
			AnchorPane websiteOverview = (AnchorPane) loader.load();

			rootLayout.setCenter(websiteOverview);
			WebsiteOverviewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
		}
	}

	public SortedMap<Website, Integer> getMap() {
		return sites;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public DataManager getEngine() {
		return engine;
	}

	public ObservableList<Website> getWebsiteData() {
		return websiteData;
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("WebCrawler 2015");
		this.primaryStage.setResizable(true);;

		initRootLayout();
		showWebsiteOverview();
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Returns the website file preference, i.e. the page that was last opened.
	 * The preference is read from the OS specific registry. If no such
	 * preference can be found, null is returned.
	 *
	 * @return
	 */
	public File getWebsiteFilePath() {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    String filePath = prefs.get("filePath", null);
	    if (filePath != null) {
	        return new File(filePath);
	    } else {
	        return null;
	    }
	}

	/**
	 * Sets the file path of the currently loaded page. The path is persisted in
	 * the OS specific registry.
	 *
	 * @param fill the page or null to remove the path
	 */
	public void setWebsiteFilePath(File file) {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    if (file != null) {
	        prefs.put("filePath", file.getPath());

	        // Update the stage title.
	        primaryStage.setTitle("WebCrawler 2015 - " + file.getName());
	    } else {
	        prefs.remove("filePath");

	        // Update the stage title.
	        primaryStage.setTitle("WebCrawler 2015");
	    }
}
	/**
	 * Loads website data from the specified file. The current website data will
	 * be replaced.
	 *
	 * @param file
	 */
	public void loadWebsiteDataFromFile(File file) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(WebsiteListWrapper.class);
	        Unmarshaller um = context.createUnmarshaller();

	        // Reading XML from the file and unmarshalling.
	        WebsiteListWrapper wrapper = (WebsiteListWrapper) um.unmarshal(file);

	        websiteData.clear();
	        websiteData.addAll(wrapper.getWebsites());

	        // Save the file path to the registry.
	        setWebsiteFilePath(file);

	    } catch (Exception e) { // catches ANY exception

	    }
	}

	/**
	 * Saves the current website data to the specified file.
	 *
	 * @param
	 */
	public void saveWebsiteDataToFile(File file) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(WebsiteListWrapper.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        // Wrapping the website data.
	        WebsiteListWrapper wrapper = new WebsiteListWrapper();
	        wrapper.setPersons(websiteData);

	        // Marshalling and saving XML to the file.
	        m.marshal(wrapper, file);

	        // Save the file path to the registry.
	        setWebsiteFilePath(file);
	    } catch (Exception e) { // catches ANY exception

	    }
	}
}