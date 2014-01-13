package com.cn.activityresult.demo;
import org.apkplug.Bundle.BundleInstance;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import android.content.Intent;


public class BundleContextFactory implements BundleInstance{
private static BundleContextFactory _instance=null;
   private BundleContext mcontext = null;
   synchronized public static BundleContextFactory getInstance(){
    if(_instance==null){
    _instance=new BundleContextFactory();
    }
    return _instance;
    } 
    private BundleContextFactory(){
	
	}

	public BundleContext getBundleContext() {
		// TODO Auto-generated method stub
		return this.mcontext;
	}
	public void setBundleContext(BundleContext arg0) {
		// TODO Auto-generated method stub
		this.mcontext = arg0;
	}

}
