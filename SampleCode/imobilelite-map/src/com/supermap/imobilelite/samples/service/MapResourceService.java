package com.supermap.imobilelite.samples.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.util.Log;
import android.util.Xml;

import com.supermap.imobilelite.mapsamples.util.Constants;
import com.supermap.imobilelite.samples.domain.MapResourceInfo;

/**
 *  <?xml version="1.0" encoding="utf-8"?>
 *	<mapresourceinfos>
 *	    <mapresourceinfo>
 *	        <mapname>China</mapname>
 *	        <service>http://www.supermap.com:8090/iserver/services</service>
 *	        <instance>/map-china400/rest/maps/China</instance>
 *	    </mapresourceinfo>
 *	</mapresourceinfos>
 *
 * 解析形如以上的xml
 */
public class MapResourceService {
	
	public static List<MapResourceInfo> getMapResourceInfos(InputStream xml) {
		List<MapResourceInfo> mapResourceInfos = null;
		MapResourceInfo mapResourceInfo = null;

		XmlPullParser pullParser = Xml.newPullParser();
		try {
			pullParser.setInput(xml, Constants.UTF8);
			int event = pullParser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					mapResourceInfos = new ArrayList<MapResourceInfo>();
					break;
				case XmlPullParser.START_TAG:
					if ("mapresourceinfo".equals(pullParser.getName())) {
						mapResourceInfo = new MapResourceInfo();
					}
					if ("mapname".equals(pullParser.getName())) {
						String mapname = pullParser.nextText();
						mapResourceInfo.setMapName(mapname);
					}
					if ("service".equals(pullParser.getName())) {
						String service = pullParser.nextText();
						mapResourceInfo.setService(service);
					}
					if ("instance".equals(pullParser.getName())) {
						String instance = pullParser.nextText();
						mapResourceInfo.setInstance(instance);
					}
					break;
				case XmlPullParser.END_TAG:
					if ("mapresourceinfo".equals(pullParser.getName())) {
						mapResourceInfos.add(mapResourceInfo);
						mapResourceInfo = null;
					}
					break;

				default:
					break;
				}

				event = pullParser.next();
			}
		} catch (XmlPullParserException e) {
			Log.i(Constants.ISERVER_TAG, "xml is not available", e);
		} catch (IOException e) {
			Log.i(Constants.ISERVER_TAG, "xml is not found", e);
		}
		return mapResourceInfos;
	}
	
	public static void save(List<MapResourceInfo> mapResourceInfos, OutputStream out) {
		XmlSerializer serializer = Xml.newSerializer();
		try {
			serializer.setOutput(out, Constants.UTF8);
			serializer.startDocument(Constants.UTF8, true);
			serializer.startTag(null, "mapresourceinfos");
			for (MapResourceInfo mapResourceInfo : mapResourceInfos) {
				serializer.startTag(null, "mapresourceinfo");
				
				serializer.startTag(null, "mapname");
				serializer.text(mapResourceInfo.getMapName());
				serializer.endTag(null, "mapname");
				
				serializer.startTag(null, "service");
				serializer.text(mapResourceInfo.getService());
				serializer.endTag(null, "service");
				
				serializer.startTag(null, "instance");
				serializer.text(mapResourceInfo.getInstance());
				serializer.endTag(null, "instance");
				
				serializer.endTag(null, "mapresourceinfo");
			}
			serializer.endTag(null, "mapresourceinfos");
			serializer.endDocument();
			out.flush();
			out.close();
		} catch (Exception e) {
			Log.i(Constants.ISERVER_TAG, "save xml error", e);
		}
	}
}
