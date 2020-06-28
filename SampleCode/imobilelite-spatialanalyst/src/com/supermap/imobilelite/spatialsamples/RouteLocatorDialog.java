package com.supermap.imobilelite.spatialsamples;

import org.apache.commons.lang3.StringUtils;

import com.supermap.imobilelite.maps.query.QueryResult;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * <p>
 * 里程定点/定线操作提示窗口类，提示具体工功能的介绍和操作按钮，并相应按钮操作
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class RouteLocatorDialog extends Dialog {

    private Context context;
    private TextView readmeTextView;
    private String demoName;
    private RouteLocatorPointDemo demo;
    // 记录按下事件点
    private float mTouchX;
    private float mTouchY;
    // 记录抬起事件点
    private float mTouchUpX;
    private float mTouchUpY;
    private TextView endPointTextView;
    private EditText endPointEditText;
    private EditText measureEditText;
    private TextView startPointTextView;
    private Button locatorPointBtn;

    /**
     * <p>
     * 当前窗口的布局，支持拖动动态布局
     * </p>
     */
    private WindowManager.LayoutParams lp;

    public RouteLocatorDialog(Context context) {
        super(context);
        this.context = context;
        demo = (RouteLocatorPointDemo) context;
    }

    public RouteLocatorDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
        demo = (RouteLocatorPointDemo) context;
    }

    public RouteLocatorDialog(Context context, int theme, String demoName) {
        super(context, theme);
        this.context = context;
        demo = (RouteLocatorPointDemo) context;
        this.demoName = demoName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.routelocator_dialog);
        lp = getWindow().getAttributes();
        lp.y = demo.titleBarHeight;
        getWindow().setGravity(Gravity.LEFT | Gravity.TOP);
        getWindow().setAttributes(lp);
        measureEditText = (EditText) findViewById(R.id.assignRoutePoint_et);
        measureEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!StringUtils.isEmpty(s)) {
                    double m = 0.0;
                    double maxM = demo.maxM;
                    Log.d("iserver", "maxM:" + maxM);
                    try {
                        m = Double.parseDouble(s.toString());

                    } catch (NumberFormatException e) {
                        m = 0.0;
                    }

                    if (m < 0.00 || m > maxM) {
                        Toast.makeText(context, "输入框只能输入从0到" + maxM + "的数值", Toast.LENGTH_LONG).show();
                    }
                    if (m < 0.00) {
                        measureEditText.setText("0.00");
                        return;
                    }
                    if (m > maxM) {
                        measureEditText.setText(String.valueOf(maxM));
                        return;

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

        });
        endPointEditText = (EditText) findViewById(R.id.endRoutePoint_et);
        endPointEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!StringUtils.isEmpty(s)) {
                    double m = 0.0;
                    double maxM = demo.maxM;
                    Log.d("iserver", "maxM:" + maxM);
                    try {
                        m = Double.parseDouble(s.toString());

                    } catch (NumberFormatException e) {
                        m = 0.0;
                    }

                    if (m < 0.00 || m > maxM) {
                        Toast.makeText(context, "输入框只能输入从0到" + maxM + "的数值", Toast.LENGTH_LONG).show();
                    }
                    if (m < 0.00) {
                        endPointEditText.setText("0.00");
                        return;
                    }
                    if (m > maxM) {
                        endPointEditText.setText(String.valueOf(maxM));
                        return;

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

        });

        Button queryRouteObjBtn = (Button) this.findViewById(R.id.queryRouteObj_btn);
        queryRouteObjBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	final ProgressDialog  progressDoalog = new ProgressDialog(context);
            	progressDoalog.setMessage("正在请求数据");
            	progressDoalog.setCanceledOnTouchOutside(false);
            	progressDoalog.show();
            	
            	new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						 final com.supermap.services.components.commontypes.QueryResult queryResult = demo.creatRouteObj();
						 ((Activity) context).runOnUiThread(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if(progressDoalog!=null){
									progressDoalog.cancel();
								}
								if(queryResult!=null && demo!=null){
									demo.showQueryResult(demo.creatRouteObj());
								} else{
									Toast.makeText(context, "请求失败", Toast.LENGTH_LONG).show();
								}
							}
							 
						 });
					}
				}).start();
                
            }
        });

        locatorPointBtn = (Button) this.findViewById(R.id.locatorPoint_btn);
        locatorPointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (locatorPointBtn.getText().toString().equals("定位点")) {
                    demo.LocatorPoint();
                } else if (locatorPointBtn.getText().toString().equals("定位线")) {
                    demo.LocatorLine();
                }

            }
        });

        Button clearBtn = (Button) this.findViewById(R.id.clear_button);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                demo.clear();
            }
        });

        readmeTextView = (TextView) this.findViewById(R.id.readme_text);
        endPointTextView = (TextView) this.findViewById(R.id.endRoutePoint_tv);
        endPointEditText = (EditText) this.findViewById(R.id.endRoutePoint_et);
        startPointTextView = (TextView) this.findViewById(R.id.assignRoutePoint_tv);
    }

    public TextView getStartPointTV() {
        return startPointTextView;
    }

    public EditText getStartPointET() {
        return measureEditText;
    }

    public TextView getEndPointTV() {
        return endPointTextView;
    }

    public EditText getEndPointET() {
        return endPointEditText;
    }

    public Button getLocatorPointBtn() {
        return locatorPointBtn;
    }

    public void setReadmeText(String text) {
        readmeTextView.setText(text);
    }

    public void setReadmeText(int textId) {
        readmeTextView.setText(textId);
    }

    public double getMeasure() {
        double measure = 0;
        try {
            measure = Double.valueOf(measureEditText.getText().toString());
        } catch (Exception e) {
            Toast.makeText(context, R.string.shouldbe_number, Toast.LENGTH_SHORT).show();
        }

        return measure;
    }

    public double getEndMeasure() {
        double endMeasure = 0;
        try {
            endMeasure = Double.valueOf(endPointEditText.getText().toString());
        } catch (Exception e) {
            Toast.makeText(context, R.string.shouldbe_number, Toast.LENGTH_SHORT).show();
        }

        return endMeasure;
    }

    /**
     * <p>
     * 相应触碰窗口事件，实现窗口的拖动
     * </p>
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN: // 捕获手指触摸按下动作
            // 获取相对View的坐标，即以此View左上角为原点
            mTouchX = event.getX();
            mTouchY = event.getY();
            break;
        case MotionEvent.ACTION_MOVE: // 捕获手指触摸移动动作
            break;
        case MotionEvent.ACTION_UP: // 捕获手指触摸离开动作
            mTouchUpX = event.getX();
            mTouchUpY = event.getY();
            updateViewPosition();
            break;
        }
        return true;
    }

    private void updateViewPosition() {
        // 更新浮动窗口位置参数
        lp.x = (int) (lp.x + mTouchUpX - mTouchX); // 新位置X坐标
        lp.y = (int) (lp.y + mTouchUpY - mTouchY); // 新位置Y坐标
        this.onWindowAttributesChanged(lp); // 刷新显示
        this.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
