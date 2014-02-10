package cn.eoe.util.Observer;


/**
 * 被观察者接口
 * @author liangqianwu
 * @param <T>
 *
 */
public interface Subject <T>{
	/**
	 * 增加观察者
	 * @param observer
	 */
   public Subject attach(Observer observer);
    /**
     * 删除观察者
     * @param observer
     */
   public Subject detach(Observer observer);
   
   /**
    * 删除观察者
    * @param observer
    */
  public Subject detachAll();
   /**
    * 通知所有观察者
    */
   public void notifyObserver(int Stutas,T o);
   /**
    * 自身的操作接口
    */
   public void operation();
   
   /**
    * 监听者数量
    */
   public int ObserverCount();

}
