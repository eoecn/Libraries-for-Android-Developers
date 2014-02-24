package cn.eoe.android.libraries.entity;

import java.util.List;

import org.apache.http.Header;

import android.content.Context;
import cn.eoe.android.libraries.entity.SlideFactory.SlideRevHandler;
import cn.eoe.android.libraries.util.ACache;

import com.google.gson.Gson;
import com.loopj.android.HttpUtil.HttpUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

/**
 * 插件网络列表数据管理器
 * 底部有测试代码
 * @author 梁前武
 * QQ:1587790525
 * www.apkplug.com
 */
public class ProductFactory {
	private static String eoe_product_cache_sort_page="eoe_product_cache_%s_%d";
	private static ProductFactory uniqueInstance = null;
	private static int cache_timeout=60*60*24*5;//默认缓存保存5天
	private String base_url="http://www.1sters.com/api/v1/libraries/list?sort=%s&page=%d";
	private ACache mCache = null;
	private Gson gson = null;
    private ProductFactory(Context mContext) {
    	mCache = ACache.get(mContext);
    	gson = new Gson();
    }
    public static ProductFactory getInstance(Context mContext) {
       if (uniqueInstance == null) {
           uniqueInstance = new ProductFactory(mContext);
       }
       return uniqueInstance;
    }
	
    //实现代码
    /**
     * 异步获取插件列表数据
     * @param isCache  是否允许从缓存中查询
     * @param handler
     * @param slideitme
     * @param page
     */
    public void getProducts(boolean isCache,String slideitme,int page,ProductsRevHandler handler){
    	if(isCache){
	    	Object prodects=mCache.getAsObject(String.format(eoe_product_cache_sort_page, slideitme,page));
			if(prodects!=null){
				handler.onSuccess(ProductsRevHandler.prodect_cache,(LibProducts)prodects,slideitme,page);
				return ;
			}
    	}
		//没有缓存就从网络更新
		this.update(slideitme,page,handler);
    }
    /**
     * 从网络上更新
     * @param handler
     * @param slideitme
     * @param page
     */
    public  void update(final String slideitme,final int page,final ProductsRevHandler handler){
    	String url=String.format(base_url, slideitme,page);
		HttpUtil.get(url, new BaseJsonHttpResponseHandler<LibProducts>() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String rawJsonResponse, LibProducts response) {
					mCache.put(String.format(eoe_product_cache_sort_page, slideitme,page), 
							response,ProductFactory.cache_timeout);
					handler.onSuccess(ProductsRevHandler.prodect_url,response,slideitme,page);
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable throwable, String rawJsonData,
						LibProducts errorResponse) {
					handler.onFailure(statusCode, throwable);
				}
				@Override
				protected LibProducts parseResponse(String rawJsonData,
						boolean isFailure) throws Throwable {
					// TODO Auto-generated method stub
					return gson.fromJson(rawJsonData, LibProducts.class);
				};
	   });
	}
	
	public interface ProductsRevHandler {
		public static int prodect_cache=0;
		public static int prodect_url=1;
		public void onSuccess(int statusCode,LibProducts products,String slideitme ,int page);
		public void onFailure(int statusCode,Throwable throwable);
	}
	
	
	/*
	 * //更新插件数据
    ProductFactory.getInstance(Context).getProducts(true,"new" ,1,
    		new ProductsRevHandler(){
				@Override
				public void onSuccess(int statusCode,LibProducts products,String slideitme ,int page) {
					System.err.println(statusCode+" "+products.getExpires());
					List<LibProduct> items=products.getItems();
					
				}

				@Override
				public void onFailure(int statusCode,Throwable throwable) {
					throwable.printStackTrace();
				}
    	
    });
    */
}
