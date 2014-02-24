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

package cn.eoe.android.libraries.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import cn.eoe.android.libraries.R;
import cn.eoe.android.libraries.entity.LibProduct;

import java.util.ArrayList;

/**
 * @author haoxq
 *         <p/>
 *         注：未采用内部类原因是首页类太庞大了，不宜修改
 * @brief 首页List的Adapter
 */
public class HomeListAdapter extends ListAdapter {

    private LibProduct mLibProduct;

    /**
     * @param _list
     * @param _context
     */
    public HomeListAdapter(Context _context, ArrayList<LibProduct> _list) {
        super(_context, _list);
    }

    @Override
    public View initView(int position, View convertView) {

        ViewHolder viewHolder;

        if (null == convertView) {

            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_list_item, null);
            viewHolder.tv_lib_name = (TextView) convertView.findViewById(R.id.tv_lib_name);
            viewHolder.tv_lib_submitters = (TextView) convertView.findViewById(R.id.tv_lib_submitter);
            viewHolder.tv_lib_brief = (TextView) convertView.findViewById(R.id.tv_lib_brief);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        mLibProduct = (LibProduct) list.get(position);

        viewHolder.tv_lib_name.setText(mLibProduct.getTitle());
        viewHolder.tv_lib_submitters.setText(mLibProduct.getLid());
        viewHolder.tv_lib_brief.setText(mLibProduct.getLede());

        return convertView;
    }

    class ViewHolder {
        private TextView tv_lib_name;
        private TextView tv_lib_submitters;
        private TextView tv_lib_brief;
    }

}
