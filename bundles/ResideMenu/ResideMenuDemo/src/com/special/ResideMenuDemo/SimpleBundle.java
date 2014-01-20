package com.special.ResideMenuDemo;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class SimpleBundle implements BundleActivator
{
    private BundleContext mcontext = null;

    public void start(BundleContext context) throws Exception
    {
        System.out.println("Simple Bundle " + context.getBundle().getBundleId()
            + " has started.");
        
        this.mcontext = context;
        
    }
   
    public void stop(BundleContext context)
    {
        System.out.println("Simple Bundle " + context.getBundle().getBundleId()
            + " has stopped.");
      
    }

}
