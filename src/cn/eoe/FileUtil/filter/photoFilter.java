package cn.eoe.FileUtil.filter;

import java.io.File;

/**
 * 过滤出图片文件 
 * .bmp  .jpeg .jpg .png .gif
 * @author liangqianwu
 *
 */
public class photoFilter extends AbFileFilter{

	public photoFilter(AbFileFilter filter) {
		super(filter);
		// TODO Auto-generated constructor stub
	}

	
	public boolean isaccept(File dir, String filename) {
		// TODO Auto-generated method stub
		return (filename.endsWith(".bmp")
				||filename.endsWith(".jpeg")
				||filename.endsWith(".jpg")
				||filename.endsWith(".png")
				||filename.endsWith(".gif"));
	}

}
