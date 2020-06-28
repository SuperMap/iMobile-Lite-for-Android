package com.supermap.imobilelite.themesamples;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.supermap.imobilelite.service.PreferencesService;
import com.supermap.imobilelite.themesamples.R;


public class SampleLauncher extends Activity {

    private EditText serviceUrlEditText;
    private PreferencesService service;
    private static final String LOG_TAG = "com.supermap.android.samples.SampleLauncher";
    private Bundle bundle;
    private static final String SAMPLE_LAUNCHER = "sample_launcher";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baseurl_dialog);
        bundle = new Bundle();

        Button confirmBtn = (Button) this.findViewById(R.id.bd_btn_confirm);
        confirmBtn.setOnClickListener(new ConfirmButtonClicked());

        service = new PreferencesService(this);
        Map<String, String> params = service.getBaseUrl(SAMPLE_LAUNCHER);
        serviceUrlEditText = (EditText) this.findViewById(R.id.bd_service_url_et);
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
                service.saveBaseUrl(SAMPLE_LAUNCHER, serviceUrl);
                bundle.putString("service_url", serviceUrl);
                startActivity(getSampleListIntent());
                finish();
            }
        }

    }

}