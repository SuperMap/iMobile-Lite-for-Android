package com.supermap.imobilelite.querysamples;

import java.util.Map;

import com.supermap.imobilelite.querysamples.R;
import com.supermap.imobilelite.service.PreferencesService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SampleLauncher extends Activity {
    private EditText serviceUrlEditText;
    private PreferencesService service;
    private static final String LOG_TAG = "com.supermap.imobilelite.querysamples.SampleLauncher";
    private static final String MAP_SERVICE_PATH = "/map-world/rest/maps/World";
    private Bundle bundle;
    private static final String SAMPLE_LAUNCHER = "sample_launcher";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baseurl_dialog);
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
    }

    Intent getSampleListIntent() {
        Intent intent = new Intent();
        intent.setClass(SampleLauncher.this, SampleList.class);
        intent.putExtras(bundle);
        return intent;
    }

    /**
     * 确定按钮事件
     * 
     */
    class ConfirmButtonClicked implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String serviceUrl = serviceUrlEditText.getText().toString();
            if (!serviceUrl.equals("")) {
                String baseUrl = serviceUrl + MAP_SERVICE_PATH;
                if (serviceUrl.endsWith("/")) {
                    baseUrl = serviceUrl.substring(0, serviceUrl.length() - 1) + MAP_SERVICE_PATH;
                }
                service.saveBaseUrl(SAMPLE_LAUNCHER, serviceUrl);
                bundle.putString("map_url", baseUrl);
                startActivity(getSampleListIntent());
                finish();
            }
        }
    }

}