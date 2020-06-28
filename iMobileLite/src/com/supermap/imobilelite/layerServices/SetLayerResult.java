package com.supermap.imobilelite.layerServices;

public class SetLayerResult {
    public boolean succeed;// 创建临时图层集是否成功。如果不成功会有错误信息。
    public String newResourceID;// 查询结果资源的 ID。
    public String newResourceLocation;// 创建的临时图层集的 URI，标识一个 tempLayers 资源。
    public String error;// 出错信息，如果创建成功，则没有本字段。
}
