/*
 * Copyright (C) 2018 nl-xx(xx@aecg.com.cn)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bc.firstui.util;

import android.content.Context;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nl.util.NLUtil;
import com.nl.util.R;
import com.nl.util.app.AppExecutors;
import com.nl.util.resource.ResUtils;

/**
 * <pre>
 *     desc   : 显示全局的Toast，防止Toast的重复弹出
 *     author : xuexiang
 *     e-mail : xx@aecg.com.cn
 *     time   : 2018/4/20 下午6:34
 * </pre>
 */
public class ToastUtil {
    private static ToastUtil mInstance = null;
    private Toast mToast = null;

    private ToastUtil() {

    }

    public static ToastUtil getInstance() {
        if (mInstance == null) {
            synchronized (ToastUtil.class) {
                if (mInstance == null) {
                    mInstance = new ToastUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * 显示提示信息
     *
     * @param msg 提示信息
     */
    public static void toast(final String msg) {
        toast(msg, Toast.LENGTH_SHORT);
    }

    /**
     * 提示信息
     *
     * @param resourceId
     */
    public static void toast(int resourceId) {
        toast(ResUtils.getString(resourceId), Toast.LENGTH_SHORT);
    }

    /**
     * 显示提示信息
     *
     * @param msg      提示信息
     * @param duration 提示持续时间
     */
    public static void toast(final String msg, final int duration) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            ToastUtil.getInstance().showToast(msg, duration);
        } else {
            AppExecutors.get().mainThread().execute(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.getInstance().showToast(msg, duration);
                }
            });
        }
    }

    /**
     * 显示提示信息
     *
     * @param text     提示信息
     * @param duration 提示持续时间
     */
    public void showToast(String text, int duration) {
        if (mToast == null) {
            mToast = makeText(NLUtil.getContext(), text, duration);
        } else {
            ((TextView) mToast.getView().findViewById(R.id.tv_info)).setText(text);
        }
        mToast.show();
    }

    private Toast makeText(Context context, String msg, int duration) {
        View view = LayoutInflater.from(context).inflate(R.layout.util_layout_toast, null);
        Toast toast = new Toast(context);
        toast.setView(view);
        TextView tv = (TextView) view.findViewById(R.id.tv_info);
        tv.getBackground().setAlpha(100);
        tv.setText(msg);
        toast.setDuration(duration);
        return toast;
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
