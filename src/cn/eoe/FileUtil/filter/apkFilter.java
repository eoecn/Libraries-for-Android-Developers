package cn.eoe.FileUtil.filter;

import java.io.File;


public class apkFilter extends AbFileFilter{
	public apkFilter(AbFileFilter filter) {
		super(filter);
		// TODO Auto-generated constructor stub
	}

	
	public boolean isaccept(File dir, String filename) {
		// TODO Auto-generated method stub
		return (filename.endsWith(".apk")
				||filename.endsWith(".APK"));
	}
}
