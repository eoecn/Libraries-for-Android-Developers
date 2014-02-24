package cn.eoe.android.libraries.entity;

import java.io.Serializable;
import org.apache.http.Header;
import com.google.gson.Gson;
import com.loopj.android.HttpUtil.HttpUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import android.content.Context;
import cn.eoe.android.libraries.util.ACache;

/**
 * 侧边栏 数据更新与获取
 * 有本地缓存功能
 * 底部有测试代码
 * @author 梁前武
 * QQ:1587790525
 * www.apkplug.com
 */
public class SlideFactory {
	private static String eoe_slide_cache="eoe_slide_cache";
	private static SlideFactory uniqueInstance = null;
	private static int cache_timeout=60*60*24*5;//默认缓存保存5天
	private String urlslides="http://www.1sters.com/api/v1/libraries/slides";
	private ACache mCache = null;
	private Gson gson = null;
    private SlideFactory(Context mContext) {
    	mCache = ACache.get(mContext);
    	gson = new Gson();
    }
    public static SlideFactory getInstance(Context mContext) {
       if (uniqueInstance == null) {
           uniqueInstance = new SlideFactory(mContext);
       }
       return uniqueInstance;
    }

    
    //实现代码
	/**
	 * 获取侧栏
	 * 1.从本地缓存查找,如果存在就立即返回
	 * 2.本地缓存中没有就从网络上更新
	 * 注:本接口是异步的
	 * @param isCache  是否允许从缓存中查询
     * @param handler
	 * @return
	 */
	public  void getSlide(boolean isCache,SlideRevHandler handler){
		if(isCache){
			Object slides=mCache.getAsObject(eoe_slide_cache);
			if(slides!=null){
				handler.onSuccess(SlideRevHandler.slide_cache,(LibSlides)slides);
				return ;
			}
		}
		//没有缓存就从网络更新
		this.update(handler);
	}

	public  void update(final SlideRevHandler handler){
		HttpUtil.get(urlslides, new BaseJsonHttpResponseHandler<LibSlides>() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String rawJsonResponse, LibSlides response) {
					mCache.put(eoe_slide_cache, response,SlideFactory.cache_timeout);
					handler.onSuccess(SlideRevHandler.slide_url,response);
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable throwable, String rawJsonData,
						LibSlides errorResponse) {
					handler.onFailure(statusCode, throwable);
				}
				@Override
				protected LibSlides parseResponse(String rawJsonData,
						boolean isFailure) throws Throwable {
					// TODO Auto-generated method stub
					return gson.fromJson(rawJsonData, LibSlides.class);
				};
	   });
	}
	
	public interface SlideRevHandler {
		public static int slide_cache=0;
		public static int slide_url=1;
		public void onSuccess(int statusCode,LibSlides slides);
		public void onFailure(int statusCode,Throwable throwable);
	}
	
	/*
	//获取侧栏目数据
    SlideFactory.getInstance(Context).getSlide(true,
    		new SlideRevHandler(){

				@Override
				public void onSuccess(int statusCode, LibSlides slides) {
					//异步获取的，如果有缓存数据就直接返回
					System.err.println(statusCode+" "+slides.getData().get(0).getItems().get(0).getTitle());
				}

				@Override
				public void onFailure(int statusCode, Throwable throwable) {
					throwable.printStackTrace();
				}
    	
    });
    */
}
