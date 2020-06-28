package com.supermap.imobilelite.datasamples;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.supermap.imobilelite.datasamples.R;


/**
 * <p>
 * 数据查询操作提示窗口类，提示具体工功能的介绍和操作按钮，并相应按钮操作
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class DataQueryDialog extends Dialog {
    private Context context;
    private TextView readmeTextView;
    private Dialog progressDialog;
    private String demoName;
    private DataQueryDemo demo;
    // 记录按下事件点
    private float mTouchX;
    private float mTouchY;
    // 记录抬起事件点
    private float mTouchUpX;
    private float mTouchUpY;

    /**
     * <p>
     * 当前窗口的布局，支持拖动动态布局
     * </p>
     */
    private WindowManager.LayoutParams lp;

    public DataQueryDialog(Context context) {
        super(context);
        this.context = context;
        demo = (DataQueryDemo) context;
    }

    public DataQueryDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
        demo = (DataQueryDemo) context;
    }

    public DataQueryDialog(Context context, int theme, String demoName) {
        super(context, theme);
        this.context = context;
        demo = (DataQueryDemo) context;
        this.demoName = demoName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dataquery_dialog);
        lp = getWindow().getAttributes();
        lp.y =demo.titleBarHeight;
        getWindow().setGravity(Gravity.LEFT | Gravity.TOP);
        getWindow().setAttributes(lp);
        Button closeBtn = (Button) this.findViewById(R.id.readme_close_button);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                demo.clearAllTouchOverlay();
                dismiss();
            }
        });

        Button pointQueryBtn = (Button) this.findViewById(R.id.point_query_button);
        pointQueryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                demo.pointQuery();
            }
        });

        Button lineQueryBtn = (Button) this.findViewById(R.id.line_query_button);
        lineQueryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                 demo.lineQuery();
            }
        });

        Button regionQueryBtn = (Button) this.findViewById(R.id.region_query_button);
        regionQueryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                 demo.regionQuery();
            }
        });

         Button clearBtn = (Button) this.findViewById(R.id.clear_button);
         clearBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View arg0) {
          demo.clear();
         }
         });

        Button commitBtn = (Button) this.findViewById(R.id.commit_button);
        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                 demo.showProgressDialog();
                 //demo.commit();               
                  demo.drawQueryThread().start();
            }
        });
       
        readmeTextView = (TextView) this.findViewById(R.id.readme_text);
       
    }
      
    public void setReadmeText(String text) {
        readmeTextView.setText(text);
    }

    public void setReadmeText(int textId) {
        readmeTextView.setText(textId);
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
        demo.clearAllTouchOverlay();
        super.onBackPressed();
    }

}
