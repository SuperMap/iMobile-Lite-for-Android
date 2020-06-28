package com.supermap.imobilelite.layersamples;

import java.util.Map;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.supermap.imobilelite.layersamples.R;
import com.supermap.imobilelite.layersamples.service.PreferencesService;

public class WMSLayerLauncher extends Activity {
    private EditText serviceUrlEditText;
    private PreferencesService service;
    private static final String LOG_TAG = "com.supermap.imobilelite.layersamples.WMSLayerLauncher";
    private Bundle bundle;
    private static final String WMSLAYER_LAUNCHER = "WMSLayer_launcher";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baseurl_dialog);
        bundle = new Bundle();

        Button confirmBtn = (Button) this.findViewById(R.id.bd_btn_confirm);
        confirmBtn.setOnClickListener(new ConfirmButtonClicked());

        service = new PreferencesService(this);
        Map<String, String> params = service.getBaseUrl(WMSLAYER_LAUNCHER);
        serviceUrlEditText = (EditText) this.findViewById(R.id.bd_service_url_et);
        String preferenceService = params.get("service");
        if (!preferenceService.trim().equals("")) {
            serviceUrlEditText.setText(preferenceService);
        }
    }

    Intent getSampleListIntent() {
        Intent intent = new Intent();
        intent.setClass(WMSLayerLauncher.this, WMSLayerList.class);
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
                service.saveBaseUrl(WMSLAYER_LAUNCHER, serviceUrl);
                bundle.putString("service_url", serviceUrl);
                startActivity(getSampleListIntent());
                finish();
            }
        }
    }
}
