package com.supermap.imobilelite.commons.utils;



import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.util.Log;

import com.supermap.imobilelite.resources.UtilsCommon;
import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * 交通换乘分析服务的工具类
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class ServicesUtil {
    private static final String LOG_TAG = "com.supermap.imobilelite.data.servicesutil";
    private static ResourceManager resource = new ResourceManager("com.supermap.imobilelite.UtilsCommon");

    /**
     * <p>
     * 对 url 最后的一个"/"后面的内容进行编码后返回
     * </p>
     * @param url
     * @return
     */
    public static String getFormatUrl(String url) {
        if (url != null && url.trim().length() > 0) {
            url = url.trim();
            while (url.endsWith("/")) {
                url = url.substring(0, url.length() - 2);
            }
            if (url.contains("/")) {
                String netWorkName = url.substring(url.lastIndexOf('/') + 1);
                try {
                    netWorkName = URLEncoder.encode(netWorkName, "utf-8");// 进行编码
                } catch (UnsupportedEncodingException e) {
                    Log.w(LOG_TAG, resource.getMessage(UtilsCommon.SERVICESUTIL_GETFORMATURL, e.getMessage()));
                }
                url = url.substring(0, url.lastIndexOf('/') + 1);
                url = url + netWorkName;
            }
            return url;
        }
        return null;
    }
}
