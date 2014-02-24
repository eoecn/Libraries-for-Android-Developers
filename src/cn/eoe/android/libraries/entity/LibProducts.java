package cn.eoe.android.libraries.entity;

import java.io.Serializable;
import java.util.List;

public class LibProducts implements Serializable{
	private String background=null;
	private String expires=null;
	private String status=null;
	
	private List<LibProduct> items=null;
	public LibProducts(){
		items=new java.util.ArrayList<LibProduct>();
	}
	
	
	// set() get()
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	public String getExpires() {
		return expires;
	}
	public void setExpires(String expires) {
		this.expires = expires;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<LibProduct> getItems() {
		return items;
	}
	public void setItems(List<LibProduct> items) {
		this.items = items;
	}
}
