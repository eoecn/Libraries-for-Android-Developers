package cn.eoe.FileUtil.filter;



import java.io.File;

public abstract class AbFileFilter implements java.io.FilenameFilter{
    protected AbFileFilter filter=null;
    public AbFileFilter(AbFileFilter filter){
    	this.filter=filter;
    }

	
	public boolean accept(File dir, String filename) {
		// TODO Auto-generated method stub
		if(isaccept(dir,filename)){
			return true;
		}else{
			if(this.filter==null)
				return false;
			    return this.filter.accept(dir, filename);
		}
		
	}
	/**
	 * 判断是否接收
	 * @param dir
	 * @param filename
	 * @return
	 */
	public abstract boolean isaccept(File dir, String filename);
}
