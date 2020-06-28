package com.supermap.imobilelite.maps;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Tools {
	static Tools mTools = null;
	int lenTatal = 0;    // 小于20字节，表示 服务端没数据、或者下载中断，不需要创建文件。
	static Tools getTools(){
		if (mTools == null) {
			mTools = new Tools();
		}
		return mTools;
	}

	private Tools(){

	}

	public Boolean downloadFile(String strURI, String filePath) throws ClientProtocolException, IOException {
		HttpClient mHttpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(strURI);
		HttpResponse httpResponse = mHttpClient.execute(get);
		if(httpResponse.getEntity()!=null) {
		File fileTem = new File(filePath+"Tem");
		fileTem.getParentFile().mkdirs();
		if (fileTem.exists()) {
			fileTem.delete();
		}
		fileTem.createNewFile();
			InputStream inputStream = httpResponse.getEntity().getContent();
			byte[] temp = new byte[1024];
			int len = 0;
			FileOutputStream out = new FileOutputStream(fileTem);
			while ((len = inputStream.read(temp)) != -1) {
				out.write(temp, 0, len);
				out.flush();
				lenTatal += len;
			}
			out.close();
			inputStream.close();
			if (lenTatal > 20) {
				fileTem.renameTo(new File(filePath));
			} else {
				fileTem.delete();
			}
	}
		return lenTatal>20;
	}
}
