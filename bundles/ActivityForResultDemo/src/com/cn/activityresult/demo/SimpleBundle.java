/*
 * Simple Example Bundle
 * Copyright (c) 2004, Richard S. Hall
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in
 *     the documentation and/or other materials provided with the
 *     distribution.
 *   * Neither the name of the ungoverned.org nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Contact: Richard S. Hall (heavy@ungoverned.org)
 * Contributor(s):
 *
**/
package com.cn.activityresult.demo;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * A very simple bundle that prints out a message when it is started and
 * stopped; it includes an embedded JAR file which is used on its internal
 * class path. This bundle is merely a "hello world" example; it does not
 * implement any services nor does it offer any configurable properties.
**/
public class SimpleBundle implements BundleActivator
{
    private BundleContext mcontext = null;
    public native String foo();

    public void start(BundleContext context) throws Exception
    {
        System.out.println("Simple Bundle " + context.getBundle().getBundleId()
            + " has started.");
        
        this.mcontext = context;
        BundleContextFactory.getInstance().setBundleContext(context);
    
        //getActivityService();
    }
   
    public void stop(BundleContext context)
    {
        System.out.println("Simple Bundle " + context.getBundle().getBundleId()
            + " has stopped.");
      
    }

}
