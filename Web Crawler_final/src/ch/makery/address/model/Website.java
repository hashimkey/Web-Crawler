package ch.makery.address.model;

import java.util.ArrayList;

import ch.makery.address.Data;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Website implements Comparable<Website> {

	private final StringProperty URL;
	private StringProperty contentType;
	private ArrayList<Website> SitesLinkToThis;
	private boolean leaf;
	private IntegerProperty numberOfLinks;


	public Website(String url) {
		this(url, 1);
	}

	public Website(String url, int numberOfLinks) {
		this.URL = new SimpleStringProperty(url);
		this.numberOfLinks = new SimpleIntegerProperty(numberOfLinks);
		leaf = false;
		SitesLinkToThis = new ArrayList<Website>();
		contentType = new SimpleStringProperty(Data.getDomain(url));
	}

	public String getUrl() {
		return URL.get();
	}

	public String getContentType() {
		return contentType.get();
	}

	public StringProperty contentTypeProperty() {
		return contentType;
	}

	public StringProperty urlProperty() {
		return URL;
	}

	public IntegerProperty occurencesProperty() {
		return numberOfLinks;
	}

	public String getLastName() {
		return Integer.toString(numberOfLinks.intValue());
	}

	@Override
	public int compareTo(Website website) {
		return getContentType().compareTo(website.getContentType());
	}

	public ArrayList<Website> getLinksToThis() {
		return SitesLinkToThis;
	}

	public void addedLink() {
		numberOfLinks = new SimpleIntegerProperty(numberOfLinks.get() + 1);
	}

	public int getNumberOfLinks() {
		return numberOfLinks.get();
	}

	public boolean getCrawled() {
		return leaf;
	}

	public void setCrawled(boolean c) {
		leaf = c;
	}


}