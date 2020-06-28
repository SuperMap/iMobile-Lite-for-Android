package com.supermap.imobilelite.maps;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.supermap.imobilelite.data.Point2D;
import com.supermap.imobilelite.data.PrjCoordSys;
import com.supermap.imobilelite.data.Rectangle2D;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by wnmng on 2017/5/5.
 */

public class RMGLCanvas extends SurfaceView implements DownloadConfigeListener,DownloadTileListener {


    boolean touch = false;
    PrjCoordSys mprjCoordSys=null;
    DownloadConfige mDownloadConfige = null;
    DownloadTile mDownloadTile = null;
    GLCacheFile mGlCacheFile;
    boolean m_bCreate;
    private boolean m_ismvt=false;
    protected Context mContext = null;

    protected long m_SiCanvas = 0;

    SurfaceHolder mSurfaceHolder;
    Surface mSurface;

    /**
     * 记录上次的event
     */
    private MotionEvent mPreEvent;

    private GestureDetector mGestureDetector = null;

    public RMGLCanvas(Context context){
        super(context);
        init(context);
    }

    public RMGLCanvas(Context context, AttributeSet attr){
        super(context,attr);
        init(context);
    }

    public void CheckFile(Context context){
        if(FileUtil.checkFile(context)){
        }else{
            FileUtil util = new FileUtil();
            util.upLoadConfigFile(context);
        }
        RMGLCanvasNative.jni_JniLoading(context);
    }

    private void init(Context context){
        mContext = context;

        double dpi = context.getResources().getDisplayMetrics().densityDpi;
        double scaleDpi = context.getResources().getDisplayMetrics().scaledDensity;
        double density = context.getResources().getDisplayMetrics().density;

        RMGLCanvasNative.jni_SetScaledDisplayDensity(scaleDpi,scaleDpi,0);


        m_SiCanvas = RMGLCanvasNative.jni_New(this);


        mSurfaceHolder = this.getHolder();

        mSurfaceHolder.setFormat(android.graphics.PixelFormat.TRANSPARENT);
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
                // TODO Auto-generated method stub
                RMGLCanvasNative.jni_OnSize(m_SiCanvas,(int)width,(int)height);
                RMGLCanvasNative.jni_Render(m_SiCanvas);
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // TODO Auto-generated method stub
                //mIsCreated = true;
                mSurface = holder.getSurface();
                RMGLCanvasNative.jni_InitSurface(mSurface,m_SiCanvas);
                RMGLCanvasNative.jni_OnCreate(m_SiCanvas);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // TODO Auto-generated method stub
                //mIsCreated = false;
                RMGLCanvasNative.jni_OnDestroy(m_SiCanvas);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        onMultiTouch(event);
        if (touch){
            return false;
        }
        return true;
    }



    /**
     * 设置地图是否响应手势
     * @param event
     * @return true为禁止手势触摸，默认问false
     */
    public void setOnTouchEvent(boolean event){
        touch = event;
    }

    /**
     * 判断事件是否已经处理过
     * @param event
     * @return 处理过返回true,反之返回false
     */
    private boolean eventHasDispatch(MotionEvent event){
        if(mPreEvent == null){ //第一次肯定是不同的，所以没有处理过
            mPreEvent = MotionEvent.obtain(event);
            return false;
        }else
        if(mPreEvent.getEventTime() != event.getEventTime() //如果是时间不同，那么一定是新的Event
                || mPreEvent.getActionIndex() != event.getActionIndex() //如果手指的ID不同
                || mPreEvent.getPointerCount() != event.getPointerCount() //如果手指数量不同
                )
        {
            mPreEvent = MotionEvent.obtain(event);
            return false;
        }
        mPreEvent = MotionEvent.obtain(event);
        return true;
    }
    long downtime = 0;

    /**
     * 地图多点触摸
     * @param event 屏幕事件
     * @return
     */
    public boolean onMultiTouch(MotionEvent event){

        //如果事件已经处理过了，就不再处理了,不支持三指操作
        //if(eventHasDispatch(event) || m_action.getValue() == Action.NULL.getValue()||event.getPointerCount()>2){
        if(eventHasDispatch(event) ||event.getPointerCount()>2){
            return true;
        }

        if(mGestureDetector != null){
            mGestureDetector.onTouchEvent(event);
        }
        int action = 0;
        int ptcout = 0;
        int[] x = new int[2];
        int[] y = new int[2];
        int index = event.getActionIndex();
        //curScale = getMap().getScale();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                action = 1;
                break;
            case MotionEvent.ACTION_MOVE:
                action = 2;
                if(event.getPointerCount()==1){
//                    for(MapParameterChangedListener p:getMap().mMapParamChangedListener){
//                        p.boundsChanged(getMap().getViewBounds().getCenter());
//                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                action = 3;
                break;
            default:
                break;
        }
        ptcout = event.getPointerCount();
        for(int i=0;i<ptcout;i++){
            x[i] = (int) event.getX(i);
            y[i] = (int) event.getY(i);
        }

        RMGLCanvasNative.jni_OnTouchEvent(m_SiCanvas,action,ptcout,index,x,y);

        // 手指后抬起， 底层没有调用刷新，此处刷新一次，否则在双指缩放地图时，手指停留一段时间后再抬起，会出现地图不再刷新的情况
        if (action == 3 && event.getPointerCount() == 2)
            Refresh();


        return true;
    }

    public void dispose()
    {
        if(m_SiCanvas==0)
            return;
        RMGLCanvasNative.jni_OnDestroy(m_SiCanvas);

        if(this.getParent()!=null)
            ((RelativeLayout)this.getParent()).removeView(this);
    }


    public void Paint(){
        RMGLCanvasNative.jni_Render(m_SiCanvas);
    }



    public void Refresh(){
        RMGLCanvasNative.jni_Refresh(m_SiCanvas);
    }

    private static final int REPAINTCALLBACK = 0xff01;
    private static final int REFRESHUI = 0xff05;
    private Handler mHandle = new Handler()
    {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REPAINTCALLBACK:
                    Paint();
                    break;
                //主线程刷新地图
                case REFRESHUI:
                    Refresh();
                    break;
                default:
                    break;
            }
        };
    };

    protected void RepaintCallback(){
        mHandle.obtainMessage(REPAINTCALLBACK).sendToTarget();
    }


    private void OpenVectorCache(String server){
        RMGLCanvasNative.jni_OpenGLVectorCache(m_SiCanvas,server);
//        this.Paint();
    }


    private void OpenMVTVectorCache(String server){
        RMGLCanvasNative.jni_OpenMVTVectorCache(m_SiCanvas,server);
//        this.Paint();
    }

    //打开矢量切片
    public void openOnlineGLServer(String URL,String DataPath){
        String xml = DataPath+"VectorCache.xml";
        File f = new File(xml);
        if (!f.exists()) {
            mDownloadConfige = new DownloadConfige(URL, DataPath);
            mDownloadConfige.setDownloadConfigeListener(this);
            mDownloadConfige.downloadConfige();
        }else{
            initialSever(URL, DataPath);
        }
    }
    private String ReadstyleFile(String path){
        File file=new File(path);
        try {
            FileReader fileReader=new FileReader(file);
            Reader reader=new InputStreamReader(new FileInputStream(file),"utf-8");
            int ch=0;
            StringBuffer sb=new StringBuffer();
            while ((ch=reader.read())!=-1){
                sb.append((char)ch);
            }
            fileReader.close();
            reader.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 通过style.json来打开瓦片
     * @param URL
     * @param DataPath
     */
    public void openOnlineMVTServerByStyle(String URL,String DataPath){
        String style = DataPath+"styles/style.json";
        File f = new File(style);
        if (!f.exists()) {
            mDownloadConfige = new DownloadConfige(URL, DataPath);
            mDownloadConfige.setDownloadConfigeListener(this);
            mDownloadConfige.downloadMVTConfige2();
        }else{
            //读取本地style.json文件数据，自己定义的style.json的文件tile地址为rest地图地址，与openOnlineMVTServer传入参数一致
            String info=ReadstyleFile(style);
            JSONObject jsonObject= JSON.parseObject(info);
            JSONObject json_source=jsonObject.getJSONObject("sources");
            String name= jsonObject.getString("name");
            JSONObject json_tile=json_source.getJSONObject(name);
            String tile= json_tile.getString("tiles");
            int b=tile.lastIndexOf("\"");
            String tileservice=tile.substring(2,b);
            //
            initialMVTSever(tileservice+"/tileFeature.mvt", DataPath);
        }
    }
    //打开MVT切片
    public void openOnlineMVTServer(String URL,String DataPath){
        String style = DataPath+"styles/style.json";
        File f = new File(style);
        if (!f.exists()) {
            mDownloadConfige = new DownloadConfige(URL, DataPath);
            mDownloadConfige.setDownloadConfigeListener(this);
            mDownloadConfige.downloadMVTConfige();
        }else{
            initialMVTSever(URL+"/tileFeature.mvt", DataPath);
        }
    }

    public void downloadConfigeFinished(String URL, String Path){
        initialSever(URL, Path);
    }

    public void downloadMVTConfigeFinished(String URL, String Path){
        initialMVTSever(URL, Path);
    }

    public void initialSever(String strURL, String strPath){
        mGlCacheFile = new GLCacheFile();
        mGlCacheFile.fromCacheFile(strPath);
        mDownloadTile = new DownloadTile(strURL+"/MapTile.GLData",strPath,false);
        mDownloadTile.setDownloadTileListener(this);
        RMGLCanvasNative.jni_RegestBeforMapDrawCallback(this,this.m_SiCanvas);
        m_bCreate = true;
        this.OpenVectorCache(strPath+"VectorCache.xml");
    }

    public void initialMVTSever(String strURL, String strPath){
        mGlCacheFile = new GLCacheFile();
        mGlCacheFile.fromCacheFile(strPath);
        m_ismvt=true;
//        mDownloadTile = new DownloadTile(strURL+"d",strPath,true);
        mDownloadTile = new DownloadTile(strURL,strPath,true);
        mDownloadTile.setDownloadTileListener(this);
        RMGLCanvasNative.jni_RegestBeforMapDrawCallback(this,this.m_SiCanvas);
        m_bCreate = true;
        this.OpenMVTVectorCache(strPath+"styles/"+"style.json");
    }

    public  void beforeMapDrawCallback(int nLevel ,int nStartRow,int nEndRow, int nStartCol ,int nEndCol){
        if (!m_bCreate) {
            return;
        }
        if(m_ismvt)
        {
            int level = (int) Math.pow(2,nLevel);
            if(nEndRow>=level){
                nEndRow = level - 1;
            }
            if(nEndCol>=level){
                nEndCol=level-1;
            }
            if(nStartCol<0){
                nStartCol=0;
            }
            if(nStartRow<0){
                nStartRow=0;
            }
            for (int j = nStartCol; j <= nEndCol; j++) {
                for (int i = nStartRow; i <= nEndRow; i++) {
                    String strTilePath = mGlCacheFile.getMVTTilePath(nLevel,j,i);
                    File  file = new File(strTilePath);
                    if (!file.exists()){
                        mDownloadTile.downloadMVTTile(j,i,nLevel);
                    }
                }
            }
        }else{
            for (int i = nStartRow; i <= nEndRow; i++) {
                for (int j = nStartCol; j <= nEndCol; j++) {
                    String strTilePath = mGlCacheFile.getTilePath(nLevel,i,j);
                    File  file = new File(strTilePath);
                    if (!file.exists()){
                        mDownloadTile.downloadTile(i,j,nLevel);
                    }
                }
            }
        }
    }

    public void downloadTileFinished(int x, int y, int z, Boolean poi){
        this.Refresh();
    }

    public void setViewBounds(Rectangle2D rectangle2D){
        RMGLCanvasNative.jni_SetViewBounds(rectangle2D.getLeft(),rectangle2D.getTop(),rectangle2D.getRight(),rectangle2D.getBottom(),m_SiCanvas);
        this.Refresh();
    }

    public Rectangle2D getViewBounds(){
        double[] params = new double[4];
        RMGLCanvasNative.jni_GetViewBounds(m_SiCanvas,params);
        Rectangle2D viewBounds = new Rectangle2D(params[0], params[1],
                params[2], params[3]);
        return viewBounds;
    }

    public double getScale(){
        return RMGLCanvasNative.jni_GetScale(m_SiCanvas);
    }

    public void setScale(double scale){
        RMGLCanvasNative.jni_SetScale(m_SiCanvas,scale);
    }

    private long getprjCoordSys(){
        return RMGLCanvasNative.jni_GetPrjCoorSys(m_SiCanvas);
    }

    public PrjCoordSys getPrjCoordSys(){
        return mprjCoordSys.createInstance(this.getprjCoordSys(),false);
    }

    public void setCenter(Point2D point){
        RMGLCanvasNative.jni_SetCenter(m_SiCanvas,point.getX(), point.getY());
        this.Refresh();
    }
    public Point2D getCenter(){
        double[] buffer = new double[2];
        RMGLCanvasNative.jni_GetCenter(m_SiCanvas,buffer);
        Point2D ptCenter = new Point2D(buffer[0], buffer[1]);
        return ptCenter;
    }

//    public void setScaledDisplayDensity(double value){
//        RMGLCanvasNative.jni_SetScaledDisplayDensity(value,m_SiCanvas);
//    }

//    String version = Build.VERSION.RELEASE;
//    String v=version.substring(0,1);
//    int number = Integer.valueOf(v);
//                Log.e("++++",number+"");
//                if (number==6){
//        RMGLCanvasNative.jni_SetSystemVersion(m_SiCanvas);
//    }

}
