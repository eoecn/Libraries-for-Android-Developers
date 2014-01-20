package cn.eoe.util.Observer;

import java.util.Enumeration;
import java.util.Vector;

public abstract class AbstractSubject implements Subject{
protected Vector vect=new Vector();


public Subject attach(Observer observer) {
	// TODO Auto-generated method stub
	if(observer!=null&&!vect.contains(observer))
	vect.add(observer);
	
	return this;
}

public Subject detach(Observer observer) {
	// TODO Auto-generated method stub
	vect.remove(observer);
	return this;
}
public int ObserverCount(){
	return vect.size();
}
public  void notifyObserver(int Stutas,Object o) {
	// TODO Auto-generated method stub
	Enumeration em=vect.elements();
	while(em.hasMoreElements()){
		
		//em.nextElement().update(this,0,o);
		this.updateObserver((Observer)em.nextElement(),Stutas,o);
	}
}

 public boolean updateObserver(Observer observer,int Stutas,Object o){
	    if(observer!=null){
	    	observer.update(this,Stutas,o);
	    	return true;
	    }else{
	    	this.detach(observer);
	    	return false;
	    }
 }

public Subject detachAll() {
	// TODO Auto-generated method stub
	vect.removeAllElements();
	return this;
}

public void operation() {
	// TODO Auto-generated method stub
	
}   
}
