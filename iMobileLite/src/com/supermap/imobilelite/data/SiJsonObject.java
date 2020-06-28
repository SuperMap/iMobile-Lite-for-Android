package com.supermap.imobilelite.data;

//handle信息
public class SiJsonObject extends InternalHandleDisposable {
	private long mHandle = 0;
	
	public SiJsonObject(String json)
	{
		mHandle = createInstance(json);
	}
	
	public SiJsonObject()
	{
		this("");
	}
	
	SiJsonObject(long handle){
		mHandle = handle;
	}
	
	public long getHandle(){
		return mHandle;
	}
	
	public int getInt(String key)
	{
		return getInt(mHandle,key);
	}
	public float getFloat(String key)
	{
		return getFloat(mHandle,key);
	}
	
	public double getDouble(String key)
	{
		return getDouble(mHandle,key);
	}
	
	public String getString(String key)
	{
		return getString(mHandle,key);
	}
	
	public boolean put(String key,int value){
		return putInt(mHandle,key, value);
	}
	
	public boolean put(String key,float value){
		return putFloat(mHandle,key, value);
	}
	
	public boolean put(String key,double value){
		return putDouble(mHandle,key, value);
	}
	
	public boolean put(String key,String value){
		return putString(mHandle,key, value);
	}
	
	public boolean put(String key,SiJsonObject value){
		return putJsonObject(mHandle,key, value.getHandle());
	}
	
	public boolean put(String key,SiJsonArray value){
		return putJsonArray(mHandle,key, value.getHandle());
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return toString(mHandle);
	}
	
	public boolean containKey(String key){
		return containKey(mHandle,key);
	}
	
	public SiJsonObject getJsonObject(String key)
	{
		long handle = getJsonObject(mHandle, key);
		return new SiJsonObject(handle);
	}
	
	public SiJsonArray getJsonArray(String key){
		long handle = getJsonArray(mHandle, key);
		return new SiJsonArray(handle);
	}
	
	@Override
	protected void finalize(){
		// TODO Auto-generated method stub
		super.finalize();
		dispose(mHandle);
		mHandle = 0;
	}
	
	private native long createInstance(String json);
	private native int getInt(long handle,String key);
	private native float getFloat(long handle,String key);
	private native double getDouble(long handle,String key);
	private native String getString(long handle,String key);
	private native String toString(long hangle);
	private native boolean containKey(long handle,String key);
	private native long getJsonObject(long handle,String key);
	private native long getJsonArray(long handle,String key);
	private native boolean putInt(long handle,String key,int value);
	private native boolean putFloat(long handle,String key,float value);
	private native boolean putDouble(long handle,String key,double value);
	private native boolean putString(long handle,String key,String value);
	private native boolean putJsonObject(long handle,String key,long value);
	private native boolean putJsonArray(long handle,String key,long value);
	private native void dispose(long handle);

	public void dispose() {
		// TODO Auto-generated method stub
		dispose(mHandle);
		mHandle = 0;
	}
}
