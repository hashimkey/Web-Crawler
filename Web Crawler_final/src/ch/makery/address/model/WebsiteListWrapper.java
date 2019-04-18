package ch.makery.address.model;


import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of websites. This is used for saving the
 * list of websites to XML.
 *
 *
 */

@XmlRootElement(name = "websites")
public class WebsiteListWrapper {

	private List<Website> websites;

    @XmlElement(name = "websites")
    public List<Website> getWebsites() {
        return websites;
    }

    public void setPersons(List<Website> websites) {
        this.websites = websites;
    }

}
