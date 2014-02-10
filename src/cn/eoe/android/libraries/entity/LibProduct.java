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
    private String lib_name;
    private String lib_submitter;
    private String lib_brief;

    /**
     * @brief 以下扩展详情内容
     */
    // private String urls.....
    // private String details
    public String getLib_name() {
        return lib_name;
    }

    public void setLib_name(String lib_name) {
        this.lib_name = lib_name;
    }

    public String getLib_submitter() {
        return lib_submitter;
    }

    public void setLib_submitter(String lib_submitter) {
        this.lib_submitter = lib_submitter;
    }

    public String getLib_brief() {
        return lib_brief;
    }

    public void setLib_brief(String lib_brief) {
        this.lib_brief = lib_brief;
    }
}
