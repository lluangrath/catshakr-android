package catshakr.models;

public class imagemodel {
	private int id;
	private String url;
	private String name;
	private String caption;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public imagemodel(){}
	public imagemodel(int id, String url, String name, String caption){
		this.id = id;
		this.url = url;
		this.name = name;
		this.caption = caption;
	}	
}