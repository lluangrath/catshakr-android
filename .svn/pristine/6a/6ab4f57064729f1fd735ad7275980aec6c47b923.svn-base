package catshakr.main;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.graphics.drawable.Drawable;

import catshakr.models.imagemodel;

public class GalleryWrapper {
	//private Gallery gallery;
	//private ImageSwitcher imgLols;
	private imagemodel[] images;
    public imagemodel[] getImages() {
		return images;
	}
	public void setImages(imagemodel[] images) {
		this.images = images;
	}

	public void parseXml(String xml){
    	try {
	    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder db = dbf.newDocumentBuilder();
	    	InputStream is = new ByteArrayInputStream(xml.getBytes());
			Document doc = db.parse(is);
			
			//Using Xpath
			XPathExpression xpe = null;
			XPathFactory xpf = XPathFactory.newInstance();
			XPath xp = xpf.newXPath();
			xpe = xp.compile("//Picture/LolImageUrl/text()");
			
			Object result = xpe.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
			images = new imagemodel[nodes.getLength()];
			for (int i = 0; i < nodes.getLength(); i++) {
				String url = nodes.item(i).getNodeValue();
				if(images[i] == null){
					images[i] = new imagemodel(i, url, null, null);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
    * Sends an HTTP GET request to a url
    *
    * @param endpoint - The URL of the server. (Example: " http://www.yahoo.com/search")
    * @param requestParameters - all the request parameters (Example: "param1=val1&param2=val2"). Note: This method will add the question mark (?) to the request - DO NOT add it yourself
    * @return - The response from the end point
    */
    public String sendGetRequest(String endpoint, String requestParameters)
    {
    	String result = null;
	    if (endpoint.startsWith("http://"))
	    {
		    // Send a GET request to the servlet
		    try
		    {			
			    // Send data
			    String urlStr = endpoint;
			    if (requestParameters != null && requestParameters.length () > 0)
			    {
			    	urlStr += "?" + requestParameters;
			    }
			    URL url = new URL(urlStr);
			    URLConnection conn = url.openConnection ();
			
			    // Get the response
			    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			    StringBuffer sb = new StringBuffer();
			    String line;
			    while ((line = rd.readLine()) != null)
			    {
			    	sb.append(line);
			    }
			    rd.close();
			    result = sb.toString();
			    
		    } catch (Exception e)
		    {
		    	e.printStackTrace();
		    }
	    }
	    return result;
    }
    
	public Drawable LoadImageFromWeb(String url){
		try{
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			is.close();
			return d;
		}catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
}
