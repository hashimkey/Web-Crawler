package ch.makery.address.engine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ch.makery.address.MainApp;
import ch.makery.address.model.Website;

public class DataManager {

	private ExecutorService executor;
	private boolean run;
	private static MainApp mainApp;

	public ExecutorService getExecutor() {
		return executor;
	}

	public DataManager() {
		run = false;
	}

	void dataManager() {
		executor = Executors.newFixedThreadPool(10);
		for (Website website : mainApp.getMap().keySet()) {
			if (!website.getCrawled()) {
				submit(new Crawler(website));
				website.setCrawled(true);
			}
		}
	}

	public void setRun(boolean bolean) {
		run = bolean;
		if (run) {
			dataManager();
		}
	}

	public boolean getRun() {
		return run;
	}

	public void submit(Crawler website) {
		executor.submit(website);
	}

	static public void setMainApp(MainApp mainapp) {
		mainApp = mainapp;
	}
}
