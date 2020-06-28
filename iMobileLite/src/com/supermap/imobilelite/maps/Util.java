package com.supermap.imobilelite.maps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;

import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.rest.util.GRRJsonDecoderResolver;
import com.supermap.services.rest.util.JsonConverter;
import com.supermap.services.rest.util.MTSPPathJsonDecoderResolver;
//import com.supermap.services.util.MTSPPathJsonDecoderResolver;
//import com.supermap.services.util.GRRJsonDecoderResolver;
import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * 工具类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class Util {
    private static final String ANDROID_API_VERSION_NUMBER = "android-api-1.0";
    private static final String LOG_TAG = "com.supermap.android.maps.util";
    private static ResourceManager resourceManager = new ResourceManager("com.supermap.android.MapCommon");
    private static final JsonConverter jsConverer = new JsonConverter();
    static {
        jsConverer.addDecoderResolver(new MTSPPathJsonDecoderResolver());
        jsConverer.addDecoderResolver(new GRRJsonDecoderResolver());
    }

    // 暂不用，先不公开
    private static double from1E6(int md1e6) {
        return md1e6 * 1.0E-006D;
    }

    /**
     * <p>
     * 
     * </p>
     * @param degrees
     * @return
     */
    static int to1E6(double degrees) {
        return (int) (degrees * 1000000.0D);
    }

    // 暂不用，先不公开
    public static int hypotenuse(int opp, int adj) {
        return (int) hypotenuse(opp * 1.0D, adj * 1.0D);
    }

    /**
     * <p>
     * 直角三角形的斜边值。
     * </p>
     * @param opp 直角三角形的一条直角边值。
     * @param adj 直角三角形的另一条直角边值。
     * @return 直角三角形的斜边值。
     */
    public static float hypotenuse(float opp, float adj) {
        return (float) hypotenuse(opp * 1.0D, adj * 1.0D);
    }

    /**
     * <p>
     * 直角三角形的斜边值。
     * </p>
     * @param opp 直角三角形的一条直角边值。
     * @param adj 直角三角形的另一条直角边值。
     * @return 直角三角形的斜边值。
     */
    public static double hypotenuse(double opp, double adj) {
        return Math.sqrt(opp * opp + adj * adj);
    }

    /**
     * <p>
     * 两点间的距离。
     * </p>
     * @param x1 第一个点的X坐标值。
     * @param y1 第一个点的Y坐标值。
     * @param x2 第二个点的X坐标值。
     * @param y2 第二个点的Y坐标值。
     * @return 两点间的距离。
     */
    public static float distance(float x1, float y1, float x2, float y2) {
        float x_sq = (x1 - x2) * (x1 - x2);
        float y_sq = (y1 - y2) * (y1 - y2);

        return (float) Math.sqrt(x_sq + y_sq);
    }

    /**
     * <p>
     * 两点间的距离。
     * </p>
     * @param p1 第一个点对象。
     * @param p2 第二个点对象。
     * @return 两点间的距离。
     */
    public static float distance(Point p1, Point p2) {
        return distance(p1.x, p1.y, p2.x, p2.y);
    }

    /**
     * <p>
     * 两点间的距离。
     * </p>
     * @param p1 第一个点对象。
     * @param p2 第二个点对象。
     * @return 两点间的距离。
     */
    public static float distance(PointF p1, PointF p2) {
        return distance(p1.x, p1.y, p2.x, p2.y);
    }

    /**
     * <p>
     * 两点间的距离的平方。
     * </p>
     * @param x1 第一个点的X坐标值。
     * @param y1 第一个点的Y坐标值。
     * @param x2 第二个点的X坐标值。
     * @param y2 第二个点的Y坐标值。
     * @return 两点间的距离的平方。
     */
    public static int distanceSquared(int x1, int y1, int x2, int y2) {
        x1 -= x2;
        y1 -= y2;
        return x1 * x1 + y1 * y1;
    }

    static double log2(double n) {
        return Math.log(n) / Math.log(2.0D);
    }

    static Rect createOriginRectFromBoundingBox(BoundingBox bbox, MapView mapView) {
        // Projection projection = mapView.getProjection();
        // if ((projection instanceof RotatableProjection))
        // projection = ((RotatableProjection) projection).getProjection();
        ProjectionUtil projectionUtil = mapView.getProjection().getProjectionUtil();
        Point ul = projectionUtil.toPixels(bbox.leftTop, null);
        Point lr = projectionUtil.toPixels(bbox.rightBottom, null);
        Rect rect = new Rect(ul.x, ul.y, lr.x, lr.y);
        return rect;
    }

    // 暂不用，先不公开
    private static BoundingBox createOriginBoundingBoxFromRect(Rect rect, MapView mapView) {
        BoundingBox bbox = null;
        try {
            // Projection projection = mapView.getProjection();
            // if ((projection instanceof RotatableProjection))
            // projection = ((RotatableProjection) projection).getProjection();
            ProjectionUtil projectionUtil = mapView.getProjection().getProjectionUtil();
            Point2D ul = projectionUtil.fromPixels(rect.left, rect.top);
            Point2D lr = projectionUtil.fromPixels(rect.right, rect.bottom);
            bbox = new BoundingBox(new Point2D(ul.getX(), ul.getY()), new Point2D(lr.getX(), lr.getY()));
        } catch (Exception ex) {
            Log.e(LOG_TAG, resourceManager.getMessage(MapCommon.UTIL_CREATBOUNDINGBOX_ERROR, ex.getMessage()));
        }

        return bbox;
    }

    static Rect createRectFromBoundingBox(BoundingBox bbox, MapView mapView) {
        if (bbox == null || bbox.leftTop == null || bbox.rightBottom == null || mapView == null) {
            return new Rect(0, 0, 0, 0);
        }
        Projection projection = mapView.getProjection();
        Point ul = projection.toPixels(new Point2D(bbox.leftTop.getX(), bbox.leftTop.getY()), null);
        Point ur = projection.toPixels(new Point2D(bbox.rightBottom.getX(), bbox.leftTop.getY()), null);
        Point ll = projection.toPixels(new Point2D(bbox.leftTop.getX(), bbox.rightBottom.getY()), null);
        Point lr = projection.toPixels(new Point2D(bbox.rightBottom.getX(), bbox.rightBottom.getY()), null);
        Point[] points = new Point[4];
        points[0] = ul;
        points[1] = ur;
        points[2] = ll;
        points[3] = lr;
        int l = 0;
        int r = 0;
        int t = 0;
        int b = 0;
        for (int i = 0; i < points.length; i++) {
            if ((points[i].x < l) || (l == 0))
                l = points[i].x;
            if ((points[i].x > r) || (r == 0))
                r = points[i].x;
            if ((points[i].y < t) || (t == 0))
                t = points[i].y;
            if ((points[i].y > b) || (b == 0))
                b = points[i].y;
        }
        Rect image_region = new Rect(l, t, r, b);

        return image_region;
    }

    static BoundingBox createBoundingBoxFromRect(Rect rect, MapView mapView) {
        Projection projection = mapView.getProjection();

        Point2D ul = projection.fromPixels(rect.left, rect.top);
        Point2D ur = projection.fromPixels(rect.right, rect.top);
        Point2D ll = projection.fromPixels(rect.left, rect.bottom);
        Point2D lr = projection.fromPixels(rect.right, rect.bottom);
        Point2D[] points = new Point2D[4];
        points[0] = ul;
        points[1] = ur;
        points[2] = ll;
        points[3] = lr;

        double l = ll.x;
        double r = ll.x;
        double t = ll.y;
        double b = ll.y;
        for (int i = 0; i < points.length; i++) {
            if (points[i].getX() < l)
                l = points[i].getX();
            if (points[i].getX() > r)
                r = points[i].getX();
            if (points[i].getY() > t)
                t = points[i].getY();
            if (points[i].getY() < b) {
                b = points[i].getY();
            }
        }
        BoundingBox bbox = new BoundingBox(new Point2D(l, t), new Point2D(r, b));
        return bbox;
    }

    /**
     * <p>
     * 三点中查找最近的点。
     * </p>
     * @param p 第一个点对象。
     * @param p1 第二个点对象。
     * @param p2 第三个点对象。
     * @param out 结果点对象。
     * @return 最近的点对象。
     */
    public static Point closestPoint(Point p, Point p1, Point p2, Point out) {
        if (out == null) {
            out = new Point();
        }
        int x0 = p1.x;
        int y0 = p1.y;
        int v1x = p2.x - x0;
        int v1y = p2.y - y0;

        if ((v1x == 0) && (v1y == 0)) {
            out.set(p1.x, p1.y);
            return out;
        }

        int v2x = p.x - x0;
        int v2y = p.y - y0;

        int v1Dotv2 = v1x * v2x + v1y * v2y;
        if (v1Dotv2 <= 0) {
            out.set(p1.x, p1.y);
            return out;
        }

        int v1Dotv1 = v1x * v1x + v1y * v1y;
        if (v1Dotv1 <= v1Dotv2) {
            out.set(p2.x, p2.y);
            return out;
        }
        double comp = v1Dotv1 / (double)v1Dotv2;

        int x = (int) (x0 + v1x * comp);
        int y = (int) (y0 + v1y * comp);
        out.set(x, y);
        return out;
    }

    /**
     * <p>
     * 查找最近的点。
     * </p>
     * @param gp
     * @param shape
     * @return
     */
    public static Point2D closestPoint(Point2D gp, List<Point2D> shape) {
        boolean pastEnd = true;

        double minDist = 2147483647.0D;
        double testX = gp.getX();
        double testY = gp.getY();
        double nearestLatitudeE6 = 0;
        double nearestLongitudeE6 = 0;
        int idxEnd = shape.size() - 1;

        for (int i = 0; i < idxEnd; i++) {
            double x0 = ((Point2D) shape.get(i)).getX();
            double y0 = ((Point2D) shape.get(i)).getY();
            double v1x = ((Point2D) shape.get(i + 1)).getX() - x0;
            double v1y = ((Point2D) shape.get(i + 1)).getY() - y0;
            if (((double) v1x == 0.0D) && ((double) v1y == 0.0D)) {
                continue;
            }
            double v2x = testX - x0;
            double v2y = testY - y0;

            double v1Dotv2 = v1x * v2x + v1y * v2y;
            if ((float) v1Dotv2 <= 0.0F) {
                pastEnd = false;
                double dist = v2x * v2x + v2y * v2y;
                if (dist < minDist) {
                    minDist = dist;
                    nearestLatitudeE6 = ((Point2D) shape.get(i)).getY();
                    nearestLongitudeE6 = ((Point2D) shape.get(i)).getX();
                }

            } else {
                double comp = v1Dotv2 / (v1x * v1x + v1y * v1y);

                if (comp >= 1.0D) {
                    pastEnd = true;
                } else {
                    pastEnd = false;
                    double xInt = x0 + (v1x * comp);
                    double yInt = y0 + (v1y * comp);
                    double dist = distSqr(testX - xInt, testY - yInt);
                    if (dist < minDist) {
                        minDist = dist;
                        nearestLongitudeE6 = xInt;
                        nearestLatitudeE6 = yInt;
                    }
                }
            }
        }

        if (pastEnd) {
            double dist = distSqr(testX - ((Point2D) shape.get(idxEnd)).getX(), testY - ((Point2D) shape.get(idxEnd)).getY());

            if (dist < minDist) {
                minDist = dist;
                nearestLatitudeE6 = ((Point2D) shape.get(idxEnd)).getY();
                nearestLongitudeE6 = ((Point2D) shape.get(idxEnd)).getX();
            }
        }
        return new Point2D(nearestLongitudeE6, nearestLatitudeE6);
    }
    
    /**
     * <p>
     * 判断一个点是否包含在面内，在面内返回true，否则返回false
     * </p>
     * @param gp 指定的点即判断点
     * @param polygon 构成指定面 的所有端点集合
     * @return boolean true：在面内
     * @since 7.0.0
     */
    public static boolean pointInPolygon(Point2D gp, List<Point2D> polygon) {
        // 是否是奇数个点，如果是点在面内
        boolean oddNodes = false;
        int j = 0;
        int N = polygon.size();
        double x = gp.getX();
        double y = gp.getY();
        for (int i = 0; i < N; i++) {
            j++;
            if (j == N) {
                j = 0;
            }
            // 判断多边形任意一条边的两个端点分别在该点的上下方(异侧)才不执行continue
            if (((((Point2D) polygon.get(i)).getY() >= y) || (((Point2D) polygon.get(j)).getY() < y))
                    && ((((Point2D) polygon.get(j)).getY() >= y) || (((Point2D) polygon.get(i)).getY() < y)))
                continue;
            // 满足x1+(y-y1)/(y2-y1)*(x2-x1)>=x即执行continue
            if (((Point2D) polygon.get(i)).getX() + (y - ((Point2D) polygon.get(i)).getY())
                    / (((Point2D) polygon.get(j)).getY() - ((Point2D) polygon.get(i)).getY())
                    * (((Point2D) polygon.get(j)).getX() - ((Point2D) polygon.get(i)).getX()) >= x) {
                continue;
            }
            oddNodes = !oddNodes;
        }
        return oddNodes;
    }

    // 暂不用，先不公开
    private static double distSqr(double dx, double dy) {
        return dx * dx + dy * dy;
    }

    // 暂不用，先不公开
    static BitmapDrawable getDrawable(Context context, String resourceName) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);

        StringBuilder resource = new StringBuilder();
        resource.append(resourceName);
        switch (metrics.densityDpi) {
        case 240:
            resource.append("_hdpi.png");
            break;
        case 160:
        default:
            resource.append("_mdpi.png");
            break;
        }

        InputStream is = null;

        File f = new File(context.getCacheDir() + File.separator + resource.toString());
        if (f.exists()) {
            try {
                is = new FileInputStream(f);
            } catch (Exception e) {
                Log.w(LOG_TAG, resourceManager.getMessage(MapCommon.UTIL_UNABLE_LOADRESOURCE, f.getAbsolutePath(), e));
            }
        }

        if (is == null) {
            is = context.getClass().getResourceAsStream("/com/supermap/android/maps/" + resource.toString());
        }

        BitmapDrawable drawable = new BitmapDrawable(is);
//        if (drawable == null) {
//            Log.e(LOG_TAG, resourceManager.getMessage(MapCommon.UTIL_ERROR_FINDRESOURCENAME, resource.toString()));
//            return null;
//        }
        drawable.setTargetDensity(metrics.densityDpi);

        return drawable;
    }

    // 暂不用，先不公开
    private static Drawable addStringToMarker(Context context, Drawable papa, String msg, Paint paint) {
        int width = papa.getIntrinsicWidth();
        int height = papa.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        papa.setBounds(new Rect(0, 0, width, height));
        papa.draw(canvas);

        Rect margin = new Rect();
        paint.getTextBounds(msg, 0, msg.length(), margin);
        int x = (width - margin.width()) / 2 + (width - margin.width()) % 2 - 1;
        int y = height / 2 + 2;
        canvas.drawText(msg, x, y, paint);

        return new BitmapDrawable(context.getResources(), bitmap);
    }

    // 暂不用，先不公开
    private static String convertStreamToString(InputStream is) throws IOException {
        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1)
                    writer.write(buffer, 0, n);
            } finally {
                is.close();
            }
            return writer.toString();
        }
        return "";
    }

    static boolean checkIfSameThread(Handler handler) {
        return handler.getLooper().getThread() == Thread.currentThread();
    }

    static String getApiVersion() {
        return ANDROID_API_VERSION_NUMBER;
    }

    /**
     * 清除运行时服务器中特定地图的缓存瓦片，用来定时更新地图缓存。
     * @param curMapUrl 特定地图的url。
     * @return true表示清除缓存成功，false表示清除失败。
     */
    public static boolean clearCache(String curMapUrl) {
        Log.d(LOG_TAG, resourceManager.getMessage(MapCommon.UTIL_CURMAPURL, curMapUrl));
        boolean clearCacheFlag = false;
        if ("".equals(curMapUrl)) {
            return clearCacheFlag;
        }
        HttpClient httpClient = getHttpClient();
        // 清除瓦片缓存的请求 需要参数bounds，但是对于temp目录的缓存不需要根据bounds来清除缓存，因而这边的bounds值暂时写死
        HttpGet request = new HttpGet(
                curMapUrl
                        + File.separator
                        + "clearCache.json?bounds=%7B%22rightTop%22%3A%7B%22y%22%3A20037508.34%2C%22x%22%3A20037508.34%7D%2C%22leftBottom%22%3A%7B%22y%22%3A-20037508.34%2C%22x%22%3A-20037508.34%7D%7D");
        try {
            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_NOT_MODIFIED) {
                clearCacheFlag = true;
                Log.d(LOG_TAG,
                        resourceManager.getMessage(MapCommon.UTIL_ClEARCACHESUCCESS_TIME, new String[] { String.valueOf(System.currentTimeMillis() / 60000),
                                String.valueOf(statusCode) }));
            }
        } catch (Exception e) {
            request.abort();
            Log.w(LOG_TAG, resourceManager.getMessage(MapCommon.UTIL_ClEARCACHE_FAIL, e.getMessage()));
        }
        return clearCacheFlag;
    }

    /*public static Map<String, Object> getMapStatus(boolean isPCSNonEarth, String curMapUrl) {
        Map<String, Object> mapStatus = new HashMap<String, Object>();
        if ("".equals(curMapUrl)) {
            return mapStatus;
        }
        HttpClient httpClient = getHttpClient();
        HttpGet request = null;
        if (isPCSNonEarth) {
            request = new HttpGet(curMapUrl + ".json");
        } else {
            request = new HttpGet(curMapUrl + ".json?prjCoordSys=%7B%22epsgCode%22%3A3857%7D");
        }
        try {
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                JSONObject result = new JSONObject(EntityUtils.toString(response.getEntity()));
                if (result != null) {
                    JSONObject viewBounds = result.getJSONObject("viewBounds");
                    double viewBoundsWidth = viewBounds.getDouble("right") - viewBounds.getDouble("left");
                    double viewBoundsHeight = viewBounds.getDouble("top") - viewBounds.getDouble("bottom");
                    mapStatus.put("viewBoundsWidth", new Double(viewBoundsWidth));
                    mapStatus.put("viewBoundsHeight",new Double(viewBoundsHeight));
                    double scale = result.getDouble("scale");
                    mapStatus.put("scale", new Double(scale));
                }
            }

        } catch (Exception e) {
            Log.w(LOG_TAG, "Clear tile cache fail", e);
        }
        return mapStatus;
    }*/

    static final JSONObject getJSON(String baseUrl){
        JSONObject result = null;
        HttpClient httpClient = getHttpClient();
        HttpGet request = new HttpGet(baseUrl);
        HttpResponse response;
        try {
            response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = new JSONObject(EntityUtils.toString(response.getEntity()));
            }
        } catch (Exception e) {
            request.abort();
            Log.w(LOG_TAG, "getJSON exception:" + e.getMessage());
        } 
        return result;
    }

    /**
     * <p>
     * 发送get请求,并返回解析后的T对象。
     * </p>
     * @param baseUrl 请求的url。
     * @param clz 把响应体结果解析后的对象类。
     * @return 解析后的泛型对象。
     * @throws Exception
     */
    public static final <T> T get(String baseUrl, Class<T> clz) throws Exception {
        HttpClient httpClient = getHttpClient();
        HttpGet request = new HttpGet(baseUrl);
        Log.d(LOG_TAG, "get baseUrl:" + baseUrl);
        HttpResponse response = httpClient.execute(request);
        Log.d(LOG_TAG, "response.getStatusLine().getStatusCode()=" + response.getStatusLine().getStatusCode());
        T result = null;
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonText = EntityUtils.toString(response.getEntity());
            Log.d(LOG_TAG, "get jsonText=" + jsonText);
            Log.d(LOG_TAG, "get responseEntity length:" + jsonText.length());
            // Log.d(LOG_TAG, "get responseEntity:" + jsonText);
            result = jsConverer.to(jsonText, clz);
        }
        return result;
    }

    /**
     * <p>
     * 发送put请求。
     * </p>
     * @param baseUrl 请求的url。
     * @param entity 请求体,可以是UrlEncodedFormEntity、StringEntity和 InputStreamEntity,如(new UrlEncodedFormEntity(data,HTTP.UTF_8)),data为List<NameValuePair>对象。
     * @return 请求成功后的结果表述字符串。
     * @throws Exception
     */
    public static String put(String baseUrl, HttpEntity entity) throws Exception {
        HttpClient httpClient = getHttpClient();
        HttpPut httpPut = new HttpPut(baseUrl);
        httpPut.setEntity(entity);
        HttpResponse response = httpClient.execute(httpPut);
        String result = null;
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonText = EntityUtils.toString(response.getEntity());
            // Log.d(LOG_TAG, "put responseEntity:" + jsonText);
            result = jsonText;
        }
        return result;
    }

    /**
     * <p>
     * 发送post请求。
     * </p>
     * @param baseUrl 请求的url。
     * @param entity 请求体,可以是UrlEncodedFormEntity、StringEntity和 InputStreamEntity,如(new UrlEncodedFormEntity(data,HTTP.UTF_8)),data为List<NameValuePair>对象。
     * @param timeout 用户自定义设置超时时间。0代表无限，即代表不设置超时限制。单位默认为秒。
     * @return 请求成功后的结果表述字符串。
     * @throws Exception
     */
    public static String post(String baseUrl, HttpEntity entity, int timeout) throws Exception {
        HttpClient httpClient = getHttpClient();
        if (timeout >= 0) {
            httpClient = getHttpClient(timeout);
        }
        HttpPost httpPost = new HttpPost(baseUrl);
        // 空间分析需要设置头
        // httpPost.addHeader("Content-Type", "application/json");
        // Log.d(LOG_TAG, "The Test baseUrl=" + baseUrl);
        // InputStream in = entity.getContent();
        // StringBuffer out = new StringBuffer();
        // byte[] b = new byte[4096];
        // int n = -1;
        // // for(int n;(n=in.read(b)) !=-1){
        // while ((n = in.read(b)) != -1) {
        // out.append(new String(b, 0, n));
        // }
        // Log.d(LOG_TAG, "The entity=" + out.toString());
        httpPost.setEntity(entity);
        HttpResponse response = httpClient.execute(httpPost);
        // Log.d(LOG_TAG, "The HttpStatus=" + String.valueOf(response.getStatusLine().getStatusCode()));
        String result = null;
        // POST请求正确返回码为200或201
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED || response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonText = EntityUtils.toString(response.getEntity());
            // Log.d(LOG_TAG, "ResultText :" + jsonText);
            result = jsonText;
        } else {
            Log.d(LOG_TAG, "response Code:" + response.getStatusLine().getStatusCode());
            // Log.d(LOG_TAG, "response mes:" + EntityUtils.toString(response.getEntity()));
        }
        return result;
    }

    /**
     * <p>
     * 创建编码为utf-8的json字符串请求体。
     * </p>
     * @param text 待处理的请求体内容。
     * @return  已设置编码的json格式的请求体内容。
     * @throws UnsupportedEncodingException
     */
    public static StringEntity newJsonUTF8StringEntity(String text) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(text, "utf-8");
        entity.setContentType("application/json;charset=utf-8");
        return entity;
    }

    /**
     * <p>
     * 发送post请求。
     * </p>
     * @param baseUrl 请求的url。
     * @param entity 请求体,可以是UrlEncodedFormEntity、StringEntity和 InputStreamEntity,如(new UrlEncodedFormEntity(data,HTTP.UTF_8)),data为List<NameValuePair>对象。
     * @return 请求成功后的结果表述字符串。
     * @throws Exception
     */
    public static String post(String baseUrl, HttpEntity entity) throws Exception {
        return post(baseUrl, entity, -1);
    }

    /**
     * <p>
     * 发送delete请求。
     * </p>
     * @param baseUrl 请求的url。
     * @return 请求成功后的结果表述字符串。
     * @throws Exception
     */
    public static String delete(String baseUrl) throws Exception {
        HttpClient httpClient = getHttpClient();
        HttpDelete httpDelete = new HttpDelete(baseUrl);
        HttpResponse response = httpClient.execute(httpDelete);
        String result = null;
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonText = EntityUtils.toString(response.getEntity());
            // Log.d(LOG_TAG, "delete responseEntity:" + jsonText);
            result = jsonText;
        }
        return result;
    }

    /**
     * <p>
     * 墨卡托投影的坐标转换为经纬度的坐标。
     * </p>
     * @param mx x坐标值。
     * @param my y坐标值。
     * @return 经纬度的点对象。
     */
    public static Point2D Mercator2lonLat(double mx, double my) {
        double wx = mx / 20037508.34 * 180;
        double wy = my / 20037508.34 * 180;
        wy = 180 / Math.PI * (2 * Math.atan(Math.exp(wy * Math.PI / 180)) - Math.PI / 2);
        return new Point2D(wx, wy);
    }

    /**
     * <p>
     * 经纬度的坐标转换为墨卡托投影的坐标。
     * </p>
     * @param wx x坐标值。
     * @param wy y坐标值。
     * @return 墨卡托投影的点对象。
     */
    public static Point2D lonLat2Mercator(double wx, double wy) {
        double mx = wx * 20037508.34 / 180;
        double my = Math.log(Math.tan((90 + wy) * Math.PI / 360)) / (Math.PI / 180);
        my = my * 20037508.34 / 180;
        return new Point2D(mx, my);
    }

    /**
     * 获取一个具备请求超时和等待数据超时的HttpClient。 
     * @return
     */
    static final HttpClient getHttpClient() {
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
        HttpConnectionParams.setSoTimeout(httpParams, 5000);
        HttpClient httpClient = new DefaultHttpClient(httpParams);
        return httpClient;
    }

    /**
     * <p>
     * 获取一个具备用户自定义请求超时和等待数据超时的HttpClient。
     * </p>
     * @param timeout 用户自定义设置超时时间。0代表无限，即代表不设置超时限制。单位默认为秒。
     * @return
     */
    static final HttpClient getHttpClient(int timeout) {
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, timeout * 1000);
        HttpConnectionParams.setSoTimeout(httpParams, timeout * 1000);
        HttpClient httpClient = new DefaultHttpClient(httpParams);
        return httpClient;
    }

    /**
     * 判断是否为地理坐标系，有个缺陷，如果是自定义地理坐标系的话，不会识别，待后续改正。不公开。
     * @param crs
     * @return
     */
    // TODO 如果没有其他图层引用则直接移到LayerView类
    static boolean isGCSCoordSys(CoordinateReferenceSystem crs) {
        if (crs != null) {
            int epsgCode = crs.wkid;
            if (epsgCode == -1000) {
                return false;
            }
            if (epsgCode > 4000 && epsgCode < 5000) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * 根据url获取相应内容
     * </p>
     * @param baseUrl 
     * @return 
     * @throws Exception
     * @since 7.0.0
     */
    public static String getJsonStr(String baseUrl) throws Exception {
        HttpClient httpClient = getHttpClient(60);
        HttpGet request = new HttpGet(baseUrl);
        HttpResponse response = httpClient.execute(request);
        String result = "";
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result = EntityUtils.toString(response.getEntity());
        }
        return result;
    }
    
    /**
     * <p>
     * 判断两条线段是否相交
     * </p>
     * @param px1 线段1的起点x
     * @param py1 线段1的起点y
     * @param px2 线段1的终点x
     * @param py2 线段1的终点y
     * @param px3 线段2的起点x
     * @param py3 线段2的起点y
     * @param px4 线段2的终点x
     * @param py4 线段2的终点y
     * @return
     * @since 7.0.0
     */
    public static boolean isIntersect(double px1, double py1, double px2, double py2, double px3, double py3, double px4, double py4) {
        boolean flag = false;
        double d = (px2 - px1) * (py4 - py3) - (py2 - py1) * (px4 - px3);
        if (d != 0) {
            double r = ((py1 - py3) * (px4 - px3) - (px1 - px3) * (py4 - py3)) / d;
            double s = ((py1 - py3) * (px2 - px1) - (px1 - px3) * (py2 - py1)) / d;
            if ((r >= 0) && (r <= 1) && (s >= 0) && (s <= 1)) {
                flag = true;
            }
        } else {// 线段平行但不能排除重合
            if ((py1 - py3) * (px4 - px3) - (px1 - px3) * (py4 - py3) == 0) {// 线段重合
                flag = true;
            }
        }
        return flag;
    }
    
    /**
     * <p>
     * 判断由data构成的多边形是否包含点gp
     * </p>
     * @param gp
     * @param data
     * @return
     */
    public static boolean contians(Point2D gp, List<Point2D> data) {
        int j = 0;
        int N = data.size() - 1;
        boolean oddNodes = false;
        double x = gp.getX();
        double y = gp.getY();
        for (int i = 0; i < N; i++) {
            j++;
            if (j == N) {
                j = 0;
            }
            if (((((Point2D) data.get(i)).getY() >= y) || (((Point2D) data.get(j)).getY() < y))
                    && ((((Point2D) data.get(j)).getY() >= y) || (((Point2D) data.get(i)).getY() < y)))
                continue;
            if (((Point2D) data.get(i)).getX() + (y - ((Point2D) data.get(i)).getY()) / (((Point2D) data.get(j)).getY() - ((Point2D) data.get(i)).getY())
                    * (((Point2D) data.get(j)).getX() - ((Point2D) data.get(i)).getX()) >= x) {
                continue;
            }
            oddNodes = !oddNodes;
        }
        return oddNodes;
    }

    /**
     * <p>
     * 点到折线的最短距离，如果返回-1，说明参数不合法
     * </p>
     * @param pt 判断点
     * @param pts 组成折线的点集合
     * @return 最短距离
     * @since 7.0.0
     */
    public static double minDistPointToPoints(Point2D pt, List<Point2D> pts) {
        double minDis = -1;
        if (pts == null || pts.size() < 1 || pt == null) {
            return minDis;
        }

        if (pts.size() == 1) {
            if (pts.get(0) == null) {
                return minDis;
            }
            return lineSpace(pt.x, pt.y, pts.get(0).x, pts.get(0).y);
        }
        int index = -1;// 记录索引
        for (int i = 0; i < pts.size() - 1; i++) {
            if (pts.get(i) == null || pts.get(i + 1) == null) {
                continue;
            }
            double dis = minDistPointToLine(pts.get(i).x, pts.get(i).y, pts.get(i + 1).x, pts.get(+1).y, pt.x, pt.y);
            if (dis < minDis || minDis == -1) {
                minDis = dis;
                index = i;
            }
        }
        return minDis;
    }

    /**
     * <p>
     * 点到线段的最短距离，判断 点（x0,y0） 到由两点组成的线段（x1,y1） ,( x2,y2 )
     * </p>
     * @param x1 线段端点1的x值
     * @param y1 线段端点1的y值
     * @param x2 线段端点2的x值
     * @param y2 线段端点2的y值
     * @param x0 判断 点的x值
     * @param y0 判断 点的x值
     * @return 最短距离
     * @since 7.0.0
     */
    public static double minDistPointToLine(double x1, double y1, double x2, double y2, double x0, double y0) {
        double space = 0;
        double a, b, c;
        a = lineSpace(x1, y1, x2, y2);// 线段的长度
        b = lineSpace(x1, y1, x0, y0);// (x1,y1)到点的距离
        c = lineSpace(x2, y2, x0, y0);// (x2,y2)到点的距离
        if (c <= 0.000001 || b <= 0.000001) {
            space = 0;
            return space;
        }
        if (a <= 0.000001) {
            space = b;
            return space;
        }
        if (c * c >= a * a + b * b) {
            space = b;
            return space;
        }
        if (b * b >= a * a + c * c) {
            space = c;
            return space;
        }
        double p = (a + b + c) / 2;// 半周长
        double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// 海伦公式求面积
        space = 2 * s / a;// 返回点到线的距离（利用三角形面积公式求高）
        return space;
    }

    // 计算两点之间的距离
    public static double lineSpace(double x1, double y1, double x2, double y2) {
        double lineLength = 0;
        lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        return lineLength;
    }
    
    /**
     * <p>
     * 获取点到折线的最短距离以及最短距离所对应线段的首端点在组成折线集合中的index位置，结果数组长度为2，第一个值表示最短距离，第二个值是int值代表索引值；如果返回-1，说明参数不合法
     * </p>
     * @param pt 判断点
     * @param pts 组成折线的点集合
     * @return 最短距离和index索引
     * @since 7.0.0
     */
    public static double[] distPointToPoints(Point2D pt, List<Point2D> pts) {
        double []res = {-1,-1};
        double minDis = -1;
        if (pts == null || pts.size() < 1 || pt == null) {
            return res;
        }

        if (pts.size() == 1) {
            if (pts.get(0) == null) {
                return res;
            }
            res[0] = lineSpace(pt.x, pt.y, pts.get(0).x, pts.get(0).y);
            return res;
        }
//        int index = -1;// 记录索引
        for (int i = 0; i < pts.size() - 1; i++) {
            if (pts.get(i) == null || pts.get(i + 1) == null) {
                continue;
            }
            double dis = minDistPointToLine(pts.get(i).x, pts.get(i).y, pts.get(i + 1).x, pts.get(+1).y, pt.x, pt.y);
            if (dis < minDis || minDis == -1) {
                res[0] = dis;
                res[1] = i;
            }
        }
        return res;
    }
    
}