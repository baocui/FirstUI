package com.bc.firstui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bc.firstui.net.IApi;
import com.bc.firstui.net.NetManager;
import com.bc.firstui.net.response.LoginResponseEntity;
import com.bc.firstui.util.ReopenAppUtils;
import com.bc.firstui.util.ToastUtil;
import com.noober.background.BackgroundLibrary;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.xuexiang.xutil.app.ActivityUtils;
import com.xuexiang.xutil.app.IntentUtils;
import com.xuexiang.xutil.app.PathUtils;
import com.xuexiang.xutil.net.JsonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 登录
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_CODE_GET_PATCH_PACKAGE = 20;

    private Unbinder mUnbinder;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.et_name)
    EditText mEtName;

    @BindView(R.id.et_password)
    EditText mEtPassword;

    @BindView(R.id.btn_login)
    Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BackgroundLibrary.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUnbinder = ButterKnife.bind(this);
        initToolbarView();
    }

    /**
     * 初始化toolbar
     */
    private void initToolbarView() {
        //设置title要在setSupportActionBar之前
        mToolbar.setTitle("登录");
        setSupportActionBar(mToolbar);
        //setNavigationOnClickListener要在setSupportActionBar之后
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_setting:
                        ToastUtil.getInstance(LoginActivity.this).toast("点击了设置");
                        break;
                    case R.id.menu_loginout:
                        ToastUtil.getInstance(LoginActivity.this).toast("点击了退出");
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        //显示返回的按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fix:
                ActivityUtils.startActivityForResult(this, IntentUtils.getDocumentPickerIntent(IntentUtils.DocumentType.ANY), REQUEST_CODE_GET_PATCH_PACKAGE);
                break;
            case R.id.btn_reopen:
                ReopenAppUtils.reopenApp(this);
                break;
            case R.id.btn_login:
                login();
                break;
            default:
                break;
        }
    }

    private void login() {
        IApi api = NetManager.getInstance().getRetrofit().create(IApi.class);
        Call<LoginResponseEntity> call = api.loginPost(mEtName.getText().toString().trim(), mEtPassword.getText().toString().trim());
        call.enqueue(new Callback<LoginResponseEntity>() {
            @Override
            public void onResponse(Call<LoginResponseEntity> call, Response<LoginResponseEntity> response) {
                LoginResponseEntity entity = response.body();
                Log.e("baocui", "onResponse: " + JsonUtil.toJson(entity.Data));
            }

            @Override
            public void onFailure(Call<LoginResponseEntity> call, Throwable t) {
                Log.e("baocui", "onFailure" + t.getMessage());
            }
        });
    }

    @Override
    @SuppressLint("MissingPermission")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK || requestCode == REQUEST_CODE_GET_PATCH_PACKAGE) {
            if (data != null) {
                String path = PathUtils.getFilePathByUri(data.getData());
                TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), path);
            }
        }
    }
}
