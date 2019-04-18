package ch.makery.address.engine;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ch.makery.address.MainApp;
import ch.makery.address.Data;
import ch.makery.address.model.Website;

public class Crawler implements Runnable {

	Website website;
	static MainApp mainApp;

	@Override
	public void run() {
		String name;
		Elements links;
		Document document;

    try {
			document = Jsoup.connect(website.getUrl()).get();
			links = document.select("a[href]");

			for (Element e : links) {
				name = e.attr("abs:href");
				name = Data.getDomain(name);
				if (name.isEmpty() || e == null || name.endsWith(".pdf") || name.endsWith(".png") || name.endsWith(".jpg")
						|| name.endsWith(".jpeg")) {
				} else if (isIk(name)) {
				} else if (exists(name)) {
				} else {
					Website website = new Website(name);
					website.getLinksToThis().add(website);
					mainApp.getMap().put(website, 1);
					mainApp.getWebsiteData().add(website);
					mainApp.getEngine().submit(new Crawler(website));
				}
				website.setCrawled(true);
			}
		} catch (IOException e) {
		}
	}

	public boolean exists(String name) {
		for (Website web : mainApp.getMap().keySet()) {
			if (name.equals(web.getContentType())) {
				if (!web.getLinksToThis().contains(website)) {
					web.getLinksToThis().add(website);
					mainApp.getWebsiteData().indexOf(web);
					mainApp.getWebsiteData().get(mainApp.getWebsiteData().indexOf(web)).addedLink();
					mainApp.getMap().put(web, web.getNumberOfLinks());
				}
				return true;
			}
		}
		return false;
	}

	static public void setMainApp(MainApp mainapp) {
		mainApp = mainapp;
	}

	public Crawler(Website website) {
		this.website = website;
	}

	public boolean isIk(String isik) {
		return isik.equals(website.getContentType());
	}
}