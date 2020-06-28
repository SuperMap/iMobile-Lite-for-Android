package com.supermap.imobilelite.datasamples;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.supermap.imobilelite.datasamples.R;
import com.supermap.imobilelite.samples.service.PreferencesService;

public class ReadmeDialog extends Dialog{
	
	private Context context;
	
	private TextView readmeTextView;
	
	private CheckBox checkBox;
	
	private PreferencesService service;
	
	private String demoName;

	public ReadmeDialog(Context context) {
		super(context);
	}
	
	public ReadmeDialog(Context context, int theme, String demoName) {
		super(context, theme);
		this.context = context;
		this.demoName = demoName;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.readme_dialog);
		
		Button closeBtn = (Button) this.findViewById(R.id.readme_close_button); 
		closeBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				service.saveReadmeEnable(demoName, !checkBox.isChecked());
				dismiss();
			}
		});
		
		readmeTextView = (TextView) this.findViewById(R.id.readme_text);
		checkBox = (CheckBox) this.findViewById(R.id.donotremind_checkbox);
		service = new PreferencesService(context);
	}
	
	public void setReadmeText(String text) {
		readmeTextView.setText(text);
	}
}
