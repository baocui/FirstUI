package com.bc.firstui;

import android.app.Application;

import com.bc.firstui.net.NetManager;
import com.tencent.tinker.lib.service.PatchResult;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;
import com.tinkerpatch.sdk.tinker.callback.ResultCallBack;
import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.tip.ToastUtils;

public class MyApplication extends Application {

  public static String Token;

    @Override
    public void onCreate() {
        super.onCreate();
        XUtil.init(this);
        NetManager.getInstance().init();
        initTinkerPatch();
    }

    private void initTinkerPatch() {
        // 我们可以从这里获得Tinker加载过程的信息
        ApplicationLike tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();
        // 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化SDK
        TinkerPatch.init(tinkerApplicationLike)
                .reflectPatchLibrary()
                .setPatchRollbackOnScreenOff(true)
                .setPatchRestartOnSrceenOff(true)
                .setPatchResultCallback(new ResultCallBack() {
                    @Override
                    public void onPatchResult(PatchResult patchResult) {
                        ToastUtils.toast("补丁修复:" + (patchResult.isSuccess ? "成功" : "失败"));
                    }
                })
                .setFetchPatchIntervalByHours(3);
        // 每隔3个小时(通过setFetchPatchIntervalByHours设置)去访问后台时候有更新,通过handler实现轮询效果
        TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
    }
}
