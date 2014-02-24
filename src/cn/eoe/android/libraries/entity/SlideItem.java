package cn.eoe.android.libraries.entity;

import java.io.Serializable;

public class SlideItem implements Serializable{
	//权限级别
	private int auth=1;
	//插件数
	private int count=0;
	//类别标识
	private String label=null;
	//类别名
	private String title=null;
	//获取该分类下插件列表的 api 接口地址；
	private String uri=null;
	
	
	
	
	// set() get()
	public int getAuth() {
		return auth;
	}
	public void setAuth(int auth) {
		this.auth = auth;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}


}
