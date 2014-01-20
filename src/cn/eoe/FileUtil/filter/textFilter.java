package cn.eoe.FileUtil.filter;

import java.io.File;


public class textFilter extends AbFileFilter{
	public textFilter(AbFileFilter filter) {
		super(filter);
		// TODO Auto-generated constructor stub
	}

	
	public boolean isaccept(File dir, String filename) {
		// TODO Auto-generated method stub
		return (filename.endsWith(".txt")
				||filename.endsWith(".text")
				||filename.endsWith(".TXT")
				||filename.endsWith(".TEXT"));
	}
}
