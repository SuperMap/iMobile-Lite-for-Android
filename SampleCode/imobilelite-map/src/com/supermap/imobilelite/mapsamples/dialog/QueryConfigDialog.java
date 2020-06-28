package com.supermap.imobilelite.mapsamples.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.mapsamples.DistanceQueryDemo;
import com.supermap.imobilelite.mapsamples.R;
import com.supermap.imobilelite.mapsamples.util.Constants;
import com.supermap.services.components.MapException;
import com.supermap.services.components.commontypes.Feature;
import com.supermap.services.components.commontypes.Geometry;
import com.supermap.services.components.commontypes.GeometryType;
import com.supermap.services.components.commontypes.QueryOption;
import com.supermap.services.components.commontypes.QueryParameter;
import com.supermap.services.components.commontypes.QueryParameterSet;
import com.supermap.services.components.commontypes.QueryResult;

public class QueryConfigDialog extends Dialog {

	private Context context;

	private Spinner spinner;

	private TextView latitudeTextView;

	private TextView longitudeTextView;
	
	private EditText distanceEditText;

	private Point2D longTouchGeoPoint;

	private Handler handler;
	
	private String currentMapName;

	private Dialog progressDialog;

	private static final int QUERY_SUCCESS = 0;

	private static final int QUERY_FAILED = 1;

	private DistanceQueryDemo demo;

	private String currentLayerName;
	
	public QueryConfigDialog(Context context) {
		super(context);
		this.context = context;
	}

	public QueryConfigDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		demo = (DistanceQueryDemo) context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.queryconfig_dialog);

		spinner = (Spinner) findViewById(R.id.querconfig_spinner);
		spinner.setSelection(0, false);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				TextView textView = (TextView) view;
				currentLayerName = textView.getText().toString();
				demo.setSelectedLayerPosition(position);
			}

			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		latitudeTextView = (TextView) findViewById(R.id.queryconfig_latitude_tv);
		longitudeTextView = (TextView) findViewById(R.id.queryconfig_longitude_tv);
		distanceEditText = (EditText) findViewById(R.id.queryconfig_distance_et);

		Button confirmBtn = (Button) findViewById(R.id.querconfig_btn_confirm);
		confirmBtn.setOnClickListener(new ConfirmBtnClick());

        handler = new QueryFinished();
        currentMapName = Uri.parse(demo.getMapView().getBaseLayer().getURL()).getLastPathSegment();
    }

    public void refreshSpinner(String[] values, int position) {
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
				context, android.R.layout.simple_spinner_item, values);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setSelection(position, true);
	}

	/**
	 * ���ó��������
	 * 
	 * @param geoPoint
	 */
	public void refreshLongTouchGeoPoint(Point2D geoPoint) {
		longTouchGeoPoint = geoPoint;
		if (latitudeTextView != null && longitudeTextView != null) {
			latitudeTextView.setText("x: "
					+ String.valueOf(geoPoint.getX()));
			longitudeTextView.setText("y: "
					+ String.valueOf(geoPoint.getY()));
		}
	}

	void showProgressDialog() {
		progressDialog = ProgressDialog.show(context, context.getResources()
				.getString(R.string.treating), context.getResources()
				.getString(R.string.querying), true);
	}

	class ConfirmBtnClick implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			double distance = 0.0;
			try {
				distance = Double.valueOf(distanceEditText.getText().toString());
			} catch (Exception e) {
				Toast.makeText(context, R.string.shouldbe_number,
						Toast.LENGTH_SHORT).show();
				return;
			}
			showProgressDialog();
			new Thread(new QueryRunnable(longTouchGeoPoint, currentLayerName, distance)).start();
		}
	}

	/**
	 * �ӷ����л�ȡȫ����ͼ��JSON����
	 * 
	 */
	class QueryRunnable implements Runnable {

		private Point2D touchPoint;

		private String layerName;

		private double distance;

		public QueryRunnable(Point2D touchPoint, String layerName,
				double distance) {
			this.touchPoint = touchPoint;
			this.layerName = layerName;
			this.distance = distance;
		}

		@Override
		public void run() {
			QueryResult queryResult = null;
			if (touchPoint != null) {
				QueryParameterSet queryParameters = new QueryParameterSet();
				QueryParameter[] queryLayerParams = new QueryParameter[1];
				queryLayerParams[0] = new QueryParameter();
				queryLayerParams[0].name = layerName;
				queryLayerParams[0].fields = new String[] {};
                queryParameters.queryParams = queryLayerParams;
                // ����ΪGEOMETRYֻ����geometry�����������ԣ���Ҫ������������ΪATTRIBUTEANDGEOMETRY(Ĭ��ֵ)
                queryParameters.queryOption = QueryOption.GEOMETRY;
                // queryParameters.resampleExpectCount = 200;
				queryParameters.expectCount = 5;
				com.supermap.services.components.commontypes.Point2D pt = new com.supermap.services.components.commontypes.Point2D(touchPoint.getX(),touchPoint.getY());
				Geometry geometry = Geometry.fromPoint2D(pt);

//				Log.d(Constants.ISERVER_TAG, "layerName:"+layerName);
//				Log.d(Constants.ISERVER_TAG, "currentMapName:"+currentMapName);
//				Log.d(Constants.ISERVER_TAG, "distance:"+distance);
//				Log.d(Constants.ISERVER_TAG, "pt:"+pt.x+","+pt.y);
//				Log.d(Constants.ISERVER_TAG, "geometry.points.length:"+geometry.points.length);
//				Log.d(Constants.ISERVER_TAG, "geometry.points:"+geometry.points[0].x+","+geometry.points[0].y);
				try {
					queryResult = demo
							.getMapView()
							.getMap()
							.queryByDistance(currentMapName, geometry, distance,
									queryParameters);
				} catch (MapException e) {
					Log.w(Constants.ISERVER_TAG, "Query error", e);
				} finally {
					Log.i(Constants.ISERVER_TAG, "result total count: " + queryResult.totalCount);
				}
			}
			Message msg = new Message();
			if (queryResult != null) {
				msg.obj = queryResult;
				msg.what = QUERY_SUCCESS;
			} else {
				msg.what = QUERY_FAILED;
			}
			handler.sendMessage(msg);
		}
	}

	/**
	 * ��ȡȫ�����ݺ���������
	 * 
	 */
	class QueryFinished extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case QUERY_SUCCESS:
				QueryResult queryResult = (QueryResult) msg.obj;
				Feature[] features = queryResult.recordsets[0].features;
				if (features == null) {
					Toast.makeText(context, R.string.query_success_zero, Toast.LENGTH_LONG).show();
					progressDialog.dismiss();
					dismiss();
					break;
				} else {
					GeometryType type = features[0].geometry.type;
					if (type.equals(GeometryType.POINT)) {
						demo.showDefaultItemizedOverlay(queryResult);
					} else if (type.equals(GeometryType.LINE)) {
						demo.showLineOverlay(queryResult);
					} else if (type.equals(GeometryType.REGION)) {
						demo.showPolygonOverlay(queryResult);
					}
					progressDialog.dismiss();
					String text = context.getResources().getString(R.string.query_success_total) + queryResult.totalCount + ", " +
							context.getResources().getString(R.string.query_success_expect) + queryResult.recordsets[0].features.length;
					Toast.makeText(context, text, Toast.LENGTH_LONG).show();
					dismiss();
				}
				break;
			case QUERY_FAILED:
				progressDialog.dismiss();
				Toast.makeText(context, R.string.query_failed,
						Toast.LENGTH_SHORT).show();
				break;
			default:
				progressDialog.dismiss();
				break;
			}
		}
	}
}
