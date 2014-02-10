package cn.eoe.FileUtil;

import java.io.File;

public class FileItem {
   private String name="";
   private String img="";
   public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getImg() {
	return img;
}
public void setImg(String img) {
	this.img = img;
}
public File getF() {
	return f;
}
public void setF(File f) {
	this.f = f;
}
private File f=null;


}
