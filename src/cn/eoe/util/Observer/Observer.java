package cn.eoe.util.Observer;
/**
 * 观测者接口
 * @author liangqianwu
 * @param <T>
 *
 */
public interface Observer {
   public void update(Subject subject,int stutas,Object o);
}
