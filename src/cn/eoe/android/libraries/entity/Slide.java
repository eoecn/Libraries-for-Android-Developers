package cn.eoe.android.libraries.entity;

import java.io.Serializable;
import java.util.List;

public class Slide implements Serializable{
	private List<SlideItem> items=null;
	
	public Slide(){
		items=new java.util.ArrayList<SlideItem>();
	}
	
	// set() get()
	public List<SlideItem> getItems() {
		return items;
	}
	public void setItems(List<SlideItem> items) {
		this.items = items;
	}
}
