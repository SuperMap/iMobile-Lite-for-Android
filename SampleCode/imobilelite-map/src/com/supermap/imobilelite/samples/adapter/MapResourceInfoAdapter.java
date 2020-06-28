package com.supermap.imobilelite.samples.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.supermap.imobilelite.mapsamples.R;
import com.supermap.imobilelite.samples.domain.MapResourceInfo;

public class MapResourceInfoAdapter extends BaseAdapter{
	
	private static HashMap<Integer, Boolean> itemIsChecked;
	
	private List<MapResourceInfo> mapResourceInfos;
	
	private int resource;
	
	private LayoutInflater inflater;
	
	public MapResourceInfoAdapter(Context context, List<MapResourceInfo> mapResourceInfos, int resource) {
		this.mapResourceInfos = mapResourceInfos;
		itemIsChecked = new HashMap<Integer, Boolean>();
		for (int i = 0; i < mapResourceInfos.size(); i++) {
			itemIsChecked.put(i, false);
		}
		this.resource = resource;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mapResourceInfos.size();
	}

	@Override
	public Object getItem(int position) {
		return mapResourceInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewCache viewCache = null;
		if (convertView == null) {
			convertView = inflater.inflate(resource, null);
			viewCache = new ViewCache();
			viewCache.mapTitleView = (TextView) convertView.findViewById(R.id.maptitle);
			convertView.setTag(viewCache);
		} else {
			viewCache = (ViewCache) convertView.getTag();
		}
		
		MapResourceInfo mapResourceInfo = mapResourceInfos.get(position);
		viewCache.mapTitleView.setText(mapResourceInfo.getMapName().toString());
		return convertView;
	}
	
	public static HashMap<Integer, Boolean> getItemIsChecked() {
		return itemIsChecked;
	}

	public final class ViewCache {
		public TextView mapTitleView;
	}
}
