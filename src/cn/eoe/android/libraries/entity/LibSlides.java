package cn.eoe.android.libraries.entity;

import java.io.Serializable;
import java.util.List;

public class LibSlides implements Serializable{
	private String background=null;
	private String expires=null;
	private String status=null;
	private List<Slide> data=null;
	
	public LibSlides(){
		data=new java.util.ArrayList<Slide>();
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
	public List<Slide> getData() {
		return data;
	}
	public void setData(List<Slide> data) {
		this.data = data;
	}
}
