package cn.eoe.FileUtil.filter;

import java.io.File;


/**
 *过滤出音频文件
 *.mp3 .mpga .ogg .wav .wma
 * @author liangqianwu
 *
 */
public class audioFilter extends AbFileFilter{

	public audioFilter(AbFileFilter filter) {
		super(filter);
		// TODO Auto-generated constructor stub
	}

	
	public boolean isaccept(File dir, String filename) {
		// TODO Auto-generated method stub
		return (filename.endsWith(".mp3")
				||filename.endsWith(".mpga")
				||filename.endsWith(".ogg")
				||filename.endsWith(".wav")
				||filename.endsWith(".wma")
				||filename.endsWith(".wmv"));
	}
    
}
