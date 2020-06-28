package com.supermap.imobilelite.mapsamples;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.supermap.imobilelite.mapsamples.R;
import com.supermap.imobilelite.mapsamples.util.Constants;
import com.supermap.imobilelite.samples.domain.MapResourceInfo;
import com.supermap.imobilelite.samples.service.MapResourceService;
import com.supermap.imobilelite.samples.service.PreferencesService;

public class SampleLauncher extends Activity {

    private EditText serviceUrlEditText;
    private EditText instanceEditText;
    private PreferencesService service;
    private static final String ISERVER_TAG = "iserver";
    private Handler handler;
    private Bundle bundle;
    private static final int GET_JSON_SUCCESS = 0;
    private static final int GET_JSON_FAILED = 1;
    private Dialog progressDialog;
    private static final String SAMPLE_LAUNCHER = "sample_launcher";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_launcher);

        handler = new GetJSONFinished();
        bundle = new Bundle();

        Button confirmBtn = (Button) this.findViewById(R.id.btn_confirm);
        confirmBtn.setOnClickListener(new ConfirmButtonClicked());

        service = new PreferencesService(this);
        Map<String, String> params = service.getBaseUrl(SAMPLE_LAUNCHER);

        serviceUrlEditText = (EditText) this.findViewById(R.id.service_url_et);
        String preferenceService = params.get("service");
        if (!preferenceService.trim().equals("")) {
            serviceUrlEditText.setText(preferenceService);
        }

        instanceEditText = (EditText) this.findViewById(R.id.instance_et);
        String preferenceInstance = params.get("instance");
        if (!preferenceInstance.trim().equals("")) {
            instanceEditText.setText(preferenceInstance);
        }
    }

    void showProgressDialog() {
        progressDialog = ProgressDialog.show(this, getResources().getString(R.string.treating), getResources().getString(R.string.getting_map_param), true);
    }

    Intent getSampleListIntent() {
        Intent intent = new Intent();
        intent.setClass(SampleLauncher.this, SampleList.class);
        intent.putExtras(bundle);
        return intent;
    }

    /**
     * 确定按钮事件 ，利用服务地址获取地图全幅JSON
     * 
     */
    class ConfirmButtonClicked implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String serviceUrl = serviceUrlEditText.getText().toString();
            String instanceUrl = instanceEditText.getText().toString();

            if (!serviceUrl.equals("") && !instanceUrl.equals("")) {
                service.saveBaseUrl(SAMPLE_LAUNCHER, serviceUrl, instanceUrl);
                showProgressDialog();
                try {
                    save(serviceUrl, instanceUrl);
                } catch (FileNotFoundException e) {
                    Log.w(Constants.ISERVER_TAG, "Failed to write mapresourceinfos.xml", e);
                }
                new Thread(new GetJSONRunnable(serviceUrl + instanceUrl)).start();

            } else {
                bundle.putString("map_url", "");
                Log.i(ISERVER_TAG, "Map url is needed");

                startActivity(getSampleListIntent());
                finish();
            }
        }

        private void save(String serviceUrl, String instanceUrl) throws FileNotFoundException {
            MapResourceInfo mapResourceInfo = new MapResourceInfo();
            mapResourceInfo.setMapName(Uri.parse(instanceUrl).getLastPathSegment());
            mapResourceInfo.setService(serviceUrl);
            mapResourceInfo.setInstance(instanceUrl);
            MapResourceService service = new MapResourceService();
            List<MapResourceInfo> list = new ArrayList<MapResourceInfo>();
            list.add(mapResourceInfo);
            File xmlFile = new File(SampleLauncher.this.getFilesDir(), Constants.INIT_CONFIG_FILE_NAME);
            service.save(list, new FileOutputStream(xmlFile));
        }
    }

    /**
     * 从服务中获取全幅地图的JSON数据
     * 
     */
    class GetJSONRunnable implements Runnable {

        private String baseUrl;

        public GetJSONRunnable(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        @Override
        public void run() {
            Message msg = new Message();
            msg.what = GET_JSON_SUCCESS;
            if (baseUrl != null) {
                bundle.putString("map_url", baseUrl);
            } else {
                bundle.putString("map_url", "");
            }
            handler.sendMessage(msg);
        }
    }

    /**
     * 获取全幅数据后启动界面
     * 
     */
    class GetJSONFinished extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case GET_JSON_SUCCESS:
                progressDialog.dismiss();
                // Toast.makeText(SampleLauncher.this, R.string.get_map_param_success, Toast.LENGTH_SHORT).show();
                startActivity(getSampleListIntent());
                super.handleMessage(msg);
                break;
            case GET_JSON_FAILED:
                progressDialog.dismiss();
                Toast.makeText(SampleLauncher.this, R.string.get_map_param_failed, Toast.LENGTH_SHORT).show();
                break;
            default:
                progressDialog.dismiss();
                break;
            }
        }
    }
}