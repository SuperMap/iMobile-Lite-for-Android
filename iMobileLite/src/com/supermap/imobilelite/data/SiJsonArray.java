package com.supermap.imobilelite.data;

//handle信息
public class SiJsonArray extends InternalHandleDisposable{
	private long mHandle = 0;
	
	public SiJsonArray(String json){
		mHandle = createInstance(json);
	}
	
	public SiJsonArray(){
		this("");
	}
	
	SiJsonArray(long handle) {
		mHandle = handle;
	}
	
	public long getHandle(){
		return mHandle;
	}
	
	public int getArraySize(){
		return getArraySize(mHandle);
	}
	
	public SiJsonObject getJsonObject(int index){
		long handle = getJsonObject(mHandle, index);
		if(handle != 0){
			return new SiJsonObject(handle);
		}
		return null;
	}
	
	public int getInt(int index){
		return getInt(mHandle, index);
	}
	public float getFloat(int index){
		return getFloat(mHandle, index);
	}
	public double getDouble(int index){
		return getDouble(mHandle, index);
	}
	public String getString(int index){
		return getString(mHandle, index);
	}
	
	public boolean add(int value){
		return addInt(mHandle, value);
	}
	public boolean add(float value){
		return addFloat(mHandle, value);
	}
	public boolean add(double value){
		return addDouble(mHandle,value);
	}
	public boolean add(String value){
		return addString(mHandle, value);
	}
	
	public boolean addJsonObject(SiJsonObject obj)
	{
		return addJsonObject(mHandle,obj.getHandle());
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return toString(mHandle);
	}
	
	@Override
	protected void finalize() {
		// TODO Auto-generated method stub
		super.finalize();
		dispose(mHandle);
		mHandle = 0;
	}
	
	private native long createInstance(String json);
	private native int getArraySize(long handle);
	private native long getJsonObject(long handle,int index);
	private native int getInt(long handle,int index);
	private native float getFloat(long handle,int index);
	private native double getDouble(long handle,int index);
	private native String getString(long handle,int index);
	private native boolean addInt(long handle,int index);
	private native boolean addFloat(long handle,float index);
	private native boolean addDouble(long handle,double index);
	private native boolean addString(long handle,String index);
	private native boolean addJsonObject(long handle,long obj);
	private native String toString(long handle);
	private native void dispose(long handle);

	public void dispose() {
		// TODO Auto-generated method stub
		dispose(mHandle);
		mHandle = 0;
	}
}
