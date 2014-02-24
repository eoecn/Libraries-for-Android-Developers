/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.eoe.android.libraries.entity;

import java.io.Serializable;


/**
 * @author haoxq
 * @version 0.1 注：时间关系，待修改成Parcelable以便更具android代表性，当然及时这样改进性能提升微乎其微
 * @brief 该JAVA Bean类用于库的详情页内容传递
 */
public class LibProduct implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5438196753574315136L;
    //插件名字
    private String title;
    //插件ID
    private String lid;
    //插件唯一 ID
    private String pkg;
    
	//插件简介
    private String lede;
    //插件详细信息地址（直接拿 webview 包）
    private String m_show_url;
    //提交时间
    private String created_at;
    //提交者信息（aid：提交者唯一 ID；avatar：提交者头像；uname：提交者名字）
    private account account;
    
    private String apk_url;
    
    //class account
    class account implements Serializable{
    	private String aid=null;
    	private String avatar=null;
    	private String uname=null;
		public String getAid() {
			return aid;
		}
		public void setAid(String aid) {
			this.aid = aid;
		}
		public String getAvatar() {
			return avatar;
		}
		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}
		public String getUname() {
			return uname;
		}
		public void setUname(String uname) {
			this.uname = uname;
		}
    	
    }
    
    // set() get()
    
 
	public String getApk_url() {
		return apk_url;
	}
	public void setApk_url(String apk_url) {
		this.apk_url = apk_url;
	}
	
	/**
     * 插件名字
     * @return
     */
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 插件ID
	 * @param title
	 */
	public String getLid() {
		return lid;
	}
	public void setLid(String lid) {
		this.lid = lid;
	}
	/**
	 * 插件唯一 ID
	 * @return
	 */
	public String getPkg() {
		return pkg;
	}
	public void setPkg(String pkg) {
		this.pkg = pkg;
	}
	/**
	 * 插件简介
	 * @return
	 */
	public String getLede() {
		return lede;
	}
	public void setLede(String lede) {
		this.lede = lede;
	}
	/**
	 * 插件详细信息地址（直接拿 webview 包）；
	 * @return
	 */
	public String getM_show_url() {
		return m_show_url;
	}
	public void setM_show_url(String m_show_url) {
		this.m_show_url = m_show_url;
	}
	/**
	 * 提交时间
	 * @return
	 */
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	/**
	 * 提交者信息（aid：提交者唯一 ID；avatar：提交者头像；uname：提交者名字）
	 * @return
	 */
	public account getAccount() {
		return account;
	}
	public void setAccount(account account) {
		this.account = account;
	}

    
}
