package ch.makery.address;

import java.net.URI;
import java.net.URISyntaxException;

public class Data {
	
	public static String getDomain(String url){
		String start = "";
		if (url == null || url.length() == 0)
			return "";

		int doubleslash = url.indexOf("//");
		if (doubleslash == -1) {
			doubleslash = 0;
		} else {
			doubleslash += 2;
			start = url.substring(0, doubleslash);
		}
		URI uri;
		String domain;
		try {
			uri = new URI(url);
			domain = uri.getHost();
			if (!domain.isEmpty()) {
				return start + domain;
			}
		} catch (URISyntaxException e) {
		}

		return "";
	}

}
