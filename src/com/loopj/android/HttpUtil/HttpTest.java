package com.loopj.android.HttpUtil;

import org.apache.http.Header;

import cn.eoe.android.libraries.entity.LibProducts;
import cn.eoe.android.libraries.entity.LibSlides;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

public class HttpTest {
	/**
	 * 测试 获取插件列表 获取与解析
	 */
	public static void TestLibProduct() {
		final Gson gson = new Gson();
		String urlString="http://www.1sters.com/api/v1/libraries/list?sort=pop&page=1";
		HttpUtil.get(urlString, new BaseJsonHttpResponseHandler<LibProducts>() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String rawJsonResponse, LibProducts response) {
					System.out.println("suc");
					System.err.println(response.getBackground());
					
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable throwable, String rawJsonData,
						LibProducts errorResponse) {
					System.out.println("fail");
					
				}
				@Override
				protected LibProducts parseResponse(String rawJsonData,
						boolean isFailure) throws Throwable {
					// TODO Auto-generated method stub
					return gson.fromJson(rawJsonData, LibProducts.class);
				};
	   });
	}

	/**
	 * 测试 获取插件列表 获取与解析
	 */
	public static void TestSlide() {
		final Gson gson = new Gson();
		String urlString="http://www.1sters.com/api/v1/libraries/slides";
		HttpUtil.get(urlString, new BaseJsonHttpResponseHandler<LibSlides>() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String rawJsonResponse, LibSlides response) {
					System.out.println("suc");
					System.err.println(response.getData().get(0).getItems().get(0).getLabel());
					
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable throwable, String rawJsonData,
						LibSlides errorResponse) {
					System.out.println("fail");
					
				}
				@Override
				protected LibSlides parseResponse(String rawJsonData,
						boolean isFailure) throws Throwable {
					// TODO Auto-generated method stub
					return gson.fromJson(rawJsonData, LibSlides.class);
				};
	   });
	}
}
