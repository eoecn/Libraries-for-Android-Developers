/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.eoe.util;

/**
 *  检测类 用于提供检测的功能,如果类是否存在
 *  数组下标有无溢出等
 * @author 梁前武 QQ1587790525
 */
public class check {
    /**
     * 检查对象是否为空
     * @param o 对象
     * @return  true false
     */
    public static boolean checkObjectEixst(Object o){
        if(o!=null){
            //System.out.println("class is :"+o.toString());
            return true;
        }else{
            System.out.println("class is no exist");
            return false;
        }
    }
    
    public static boolean checkObjectEqu(Object o1,Object o2){
        if(o1==o2){
            System.out.println("two class is equal");
            return true;
        }else{
            System.out.println("two class is inequality");
            return false;
        }
    }
    
    public static boolean isNumeric(String str){
    	if(str==null||str.equals(""))
    		return false;
    	try{
    		        new Float(str);
    		      return true;
    		      }catch(Exception e2){
    		    		for (int i = str.length();--i>=0;){   
    		    	    	   if (!Character.isDigit(str.charAt(i))){ 
    		    	    		  return false;
    		    	    	   } 
    		    	    	} 
    		          }
    
    	return true; 
    	} 
    
    public static boolean checkString(String o){
    	if(o==null||o.equals("")){
    		return false;
    	}
    		return true;
    }
    
    public static  float strToFloat(String f){
    	if(check.isNumeric(f)){
    		return Float.parseFloat(f);
    	}else{
    		
    		return 0;
    	}
    	
    }
}
