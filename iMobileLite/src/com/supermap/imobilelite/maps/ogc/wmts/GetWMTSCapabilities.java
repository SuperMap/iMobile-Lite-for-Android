package com.supermap.imobilelite.maps.ogc.wmts;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.supermap.imobilelite.commons.Credential;
import com.supermap.imobilelite.maps.BoundingBox;
import com.supermap.imobilelite.maps.Point2D;

//import com.supermap.services.util.XMLTool;
import android.util.Log;

/**
 * <p>
 * WMTS 服务元数据信息获取类。该类用于获取 WMTS 服务元数据信息。
 * WMTS 服务的元数据主要由 ServiceIdentification、ServiceProvider、OperationsMetadata、Contents 和 Themes 几个元素组成。
 * 其中 ServiceIdentification 是对服务的整体介绍；ServiceProvider 是关于服务提供商的信息；
 * OperationsMetadata 描述了当前服务中支持的所有操作（如：GetTile、GetCapabilities、GetFeatureInfo）以及操作请求的 URI；
 * Contents 是对服务中可用图层的整体描述，包括瓦片矩阵集、可操作图层等等；Themes 是描述专题图层的元数据。
 * </p>
 * @author ${huangqh}
 * @version ${Version}
 * @since 7.0.0
 * 
 */
public class GetWMTSCapabilities {
    private static final String LOG_TAG = "com.supermap.imobilelite.maps.ogc.wmts.GetWMTSCapabilities";
    public static final String ENTITY = "ENTITY";
    /**
     * <p>
     * WMTS 服务地址。
     * </p>
     * @since 7.0.0
     */
    private String serviceRootURL;
    /**
     * <p>
     * 请求的 WMTS 服务版本号。
     * </p>
     * @since 7.0.0
     */
    private String version = "1.0.0";
    private List<String> supportVersion = new ArrayList<String>();

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public GetWMTSCapabilities() {
        this.supportVersion.add(version);
    }

    /**
     * <p>
     * 构造函数。根据url和版本构造
     * </p>
     * @param url
     * @param version
     */
    public GetWMTSCapabilities(String url, String version) {
        this();
        this.serviceRootURL = url;
        if (version != null) {
            this.version = version;
        }
    }

    /**
     * <p>
     * 构造函数。根据url和默认版本构造
     * </p>
     * @param url
     */
    public GetWMTSCapabilities(String url) {
        this(url, null);
    }

    private String buildURL(String wmtsVersion) {
        StringBuilder sb = new StringBuilder();
        int index = this.serviceRootURL.indexOf("?") == -1 ? this.serviceRootURL.length() : this.serviceRootURL.indexOf("?");
        sb.append(this.serviceRootURL.subSequence(0, index));// 兼容
        sb.append("?VERSION=");
        sb.append(wmtsVersion);
        sb.append("&SERVICE=WMTS");
        sb.append("&REQUEST=GetCapabilities");
        if (Credential.CREDENTIAL != null) {
            sb.append("&" + Credential.CREDENTIAL.name + "=" + Credential.CREDENTIAL.value);
        }
        Log.i(LOG_TAG, "getCapabilities url:" + sb.toString());
        return sb.toString();
    }

    /**
     * <p>
     * 发送 获取WMTS 服务元数据信息请求并封装结果
     * </p>
     * @return
     * @since 7.0.0
     */
    public WMTSCapabilitiesResult getCapabilities() {
        return getCapabilities("1.0.0");
    }

    /**
     * <p>
     * 发送 获取WMTS 服务元数据信息请求并封装结果
     * </p>
     * @param version WMTS 服务版本
     * @return
     * @since 7.0.0
     */
    public WMTSCapabilitiesResult getCapabilities(String version) {
        String wmtsVersion = version;
        if (!isSupportVersion(wmtsVersion)) {
            wmtsVersion = this.supportVersion.get(0);
        }
        String serviceURL = this.buildURL(wmtsVersion);

        Document document = null;
        long startSendUrl = System.currentTimeMillis();
        try {
            document = getDocument(serviceURL);
        } catch (HttpException e) {
            Log.e(LOG_TAG, e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e(LOG_TAG, e.getMessage());
            return null;
        } catch (ParserConfigurationException e) {
            Log.e(LOG_TAG, e.getMessage());
            return null;
        }
        Log.i(LOG_TAG, "发送getCapabilities请求获取结果消耗时间为：" + (System.currentTimeMillis() - startSendUrl) + "ms");
        if (document == null) {
            return null;
        }
        long startParser = System.currentTimeMillis();
        WMTSCapabilitiesResult wmtsCapabilities = new WMTSCapabilitiesResult();
        Node capabiliNode = getChildNode(document, "Capabilities");
        Node contentNode = getChildNode(capabiliNode, "Contents");
        Node[] layerNodes = getChildNodes(contentNode, "Layer");
        wmtsCapabilities.layerInfos = this.parseWMTSLayers(layerNodes);
        Node[] tileMatrixSetNodes = getChildNodes(contentNode, "TileMatrixSet");
        if (tileMatrixSetNodes != null && tileMatrixSetNodes.length > 0) {
            List<WMTSTileMatrixSetInfo> tileMatrixSets = new ArrayList<WMTSTileMatrixSetInfo>();
            for (Node matrixSetNode : tileMatrixSetNodes) {
                WMTSTileMatrixSetInfo tileMatrixSet = this.parseMatrixSet(matrixSetNode);
                tileMatrixSets.add(tileMatrixSet);
            }
            wmtsCapabilities.tileMatrixSetInfos = tileMatrixSets;
        }
        Log.i(LOG_TAG, "解析WMTS Capabilities消耗时间为：" + (System.currentTimeMillis() - startParser) + "ms");
        return wmtsCapabilities;
    }

    private WMTSTileMatrixSetInfo parseMatrixSet(Node matrixSetNode) {
        WMTSTileMatrixSetInfo matrixSet = null;
        if (matrixSetNode != null) {
            matrixSet = new WMTSTileMatrixSetInfo();
            Node identifierNode = getChildNode(matrixSetNode, "ows:Identifier");
            if (identifierNode != null) {
                matrixSet.name = identifierNode.getTextContent();
            }
            Node supportedCRSNode = getChildNode(matrixSetNode, "ows:SupportedCRS");
            if (supportedCRSNode != null) {
                matrixSet.supportedCRS = supportedCRSNode.getTextContent();
            }
            Node wellKnownScaleSetNode = getChildNode(matrixSetNode, "WellKnownScaleSet");
            if (wellKnownScaleSetNode != null) {
                matrixSet.wellKnownScaleSet = wellKnownScaleSetNode.getTextContent();
            }
            Node[] matrixNodes = getChildNodes(matrixSetNode, "TileMatrix");
            if (matrixNodes != null && matrixNodes.length > 0) {
                List<TileMatrix> tileMatrixes = new ArrayList<TileMatrix>();
                for (Node matrixNode : matrixNodes) {
                    TileMatrix tileMatrix = this.parseMatrix(matrixNode);
                    tileMatrixes.add(tileMatrix);
                }
                matrixSet.tileMatrixs = tileMatrixes;
            }
        }

        return matrixSet;
    }

    /**
     * <p>
     * 获取某父节点 parentNode 下第一个名称为 name 的子节点。
     * </p>
     * @param parentNode 父节点
     * @param name 子节点名称
     * @return
     * @since 7.0.0
     */
    public static Node getChildNode(Node parentNode, String name) {
        Node childNode = null;
        if (parentNode != null && parentNode.hasChildNodes()) {
            NodeList nodeList = parentNode.getChildNodes();
            // try {
            int iLength = nodeList.getLength();
            for (int i = 0; i < iLength; i++) {
                Node node = nodeList.item(i);
                if (isMatchByLocalNameOrNodeName(node, name)) {
                    childNode = node;
                    break;
                }
            }
        }
        return childNode;
    }

    /**
     * <p>
     *  获取某父节点 parentNode 下名称为 name 的所有子节点。
     * </p>
     * @param parentNode 父节点
     * @param name 子节点名称
     * @return
     * @since 7.0.0
     */
    public static Node[] getChildNodes(Node parentNode, String name) {
        ArrayList<Node> childNodeList = new ArrayList<Node>();
        if (parentNode != null && parentNode.hasChildNodes()) {
            NodeList nodeList = parentNode.getChildNodes();
            int iLength = nodeList.getLength();
            for (int i = 0; i < iLength; i++) {
                Node node = nodeList.item(i);
                if (isMatchByLocalNameOrNodeName(node, name)) {
                    childNodeList.add(node);
                }
            }
        }

        return childNodeList.toArray(new Node[childNodeList.size()]);
    }

    /**
     * <p>
     * 获取节点的文本内容
     * </p>
     * @param node
     * @return
     * @since 7.0.0
     */
    public static String getNodeText(Node node) {
        String text = null;
        if (node == null) {
            return text;
        }
        if (node.hasChildNodes()) {
            NodeList nodeList = node.getChildNodes();
            int iLength = nodeList.getLength();
            for (int i = 0; i < iLength; i++) {
                Node nodeItem = nodeList.item(i);
                short nodeType = nodeItem.getNodeType();
                if (nodeType == Node.TEXT_NODE || nodeType == Node.CDATA_SECTION_NODE) {
                    text = nodeItem.getNodeValue();
                    break;
                }
            }
        }
        return text;
    }

    /**
     * <p>
     * 判断节点的LocalName或者NodeName是否与指定的字符串匹配。
     * 优先使用LocalName进行比较，如果LocalName为null或者Local不匹配，则使用NodeName进行比较。
     * </p>
     * @param node
     * @param name
     * @return
     * @since 7.0.0
     */
    private static boolean isMatchByLocalNameOrNodeName(Node node, String name) {
        String localPart = node.getLocalName();
        String nodeName = node.getNodeName();
        return localPart == null ? nodeName.equalsIgnoreCase(name) : localPart.equalsIgnoreCase(name) || nodeName.equalsIgnoreCase(name);
    }

    private TileMatrix parseMatrix(Node matrixNode) {
        TileMatrix tileMatrix = null;
        if (matrixNode != null) {
            tileMatrix = new TileMatrix();
            Node identifierNode = getChildNode(matrixNode, "ows:Identifier");
            if (identifierNode != null) {
                tileMatrix.id = identifierNode.getTextContent();
            }
            Node scaleNode = getChildNode(matrixNode, "ScaleDenominator");
            if (scaleNode != null) {
                tileMatrix.scaleDenominator = Double.parseDouble(scaleNode.getTextContent());
            }
            Node topLeftCornerNode = getChildNode(matrixNode, "TopLeftCorner");
            if (topLeftCornerNode != null) {
                tileMatrix.topLeftCorner = topLeftCornerNode.getTextContent();
            }
            Node tileWidthNode = getChildNode(matrixNode, "TileWidth");
            if (tileWidthNode != null) {
                tileMatrix.tileWidth = Integer.parseInt(tileWidthNode.getTextContent());
            }
            Node tileHeightNode = getChildNode(matrixNode, "TileHeight");
            if (tileHeightNode != null) {
                tileMatrix.tileHeight = Integer.parseInt(tileHeightNode.getTextContent());
            }
            Node matrixWidthNode = getChildNode(matrixNode, "MatrixWidth");
            if (matrixWidthNode != null) {
                tileMatrix.matrixWidth = Integer.parseInt(matrixWidthNode.getTextContent());
            }
            Node matrixHeightNode = getChildNode(matrixNode, "MatrixHeight");
            if (matrixHeightNode != null) {
                tileMatrix.matrixHeight = Integer.parseInt(matrixHeightNode.getTextContent());
            }
        }
        return tileMatrix;
    }

    private List<WMTSLayerInfo> parseWMTSLayers(Node[] layerNodes) {
        List<WMTSLayerInfo> wmtsLayers = null;
        if (layerNodes != null && layerNodes.length > 0) {
            wmtsLayers = new ArrayList<WMTSLayerInfo>();
            for (Node node : layerNodes) {
                WMTSLayerInfo wmtsLayer = this.parseWMTSLayer(node);
                wmtsLayers.add(wmtsLayer);
            }
        }

        return wmtsLayers;
    }

    private WMTSLayerInfo parseWMTSLayer(Node layerNode) {
        WMTSLayerInfo wmtsMapLayer = null;
        if (layerNode == null) {
            return wmtsMapLayer;
        }
        wmtsMapLayer = new WMTSLayerInfo();
        Node identifierNode = getChildNode(layerNode, "ows:Identifier");
        if (identifierNode != null) {
            wmtsMapLayer.name = identifierNode.getTextContent();
        }
        // TileMatrixSet
        // iServer中layer只对应一个TileMatrixSet
        // geoServer和ArcGIS中layer可以对应多个TileMatrixSet
        Node[] tileMatrixSetLinkNodes = getChildNodes(layerNode, "TileMatrixSetLink");
        if (tileMatrixSetLinkNodes != null) {
            List<String> tileMatrixSets = new ArrayList<String>();
            for (Node tileMatrixSetLinkNode : tileMatrixSetLinkNodes) {
                Node tileMatrixSetNode = getChildNode(tileMatrixSetLinkNode, "TileMatrixSet");
                if (tileMatrixSetLinkNode == null) {
                    continue;
                }
                // if (wmtsMapLayer.tileMatrixSetLinks == null) {
                // wmtsMapLayer.tileMatrixSetLinks = tileMatrixSetNode.getTextContent().trim();
                // }
                tileMatrixSets.add(tileMatrixSetNode.getTextContent().trim());
            }
            wmtsMapLayer.tileMatrixSetLinks = tileMatrixSets;
        }
        // format
        Node[] formatNodes = getChildNodes(layerNode, "Format");
        List<String> formats = new ArrayList<String>();
        if (formatNodes != null) {
            for (Node fomatNode : formatNodes) {
                if (wmtsMapLayer.imageFormat == null) {
                    wmtsMapLayer.imageFormat = fomatNode.getTextContent().trim();
                }
                formats.add(fomatNode.getTextContent().trim());
            }
        }
        wmtsMapLayer.formats = new String[formats.size()];
        formats.toArray(wmtsMapLayer.formats);
        // ows:BoundingBox
        // iServer和geoServer发布的WMTS服务均没有BoundsBox节点，但是ogc规范里说明了BoundingBox是可选节点【节点数：0-*】
        // ArcGIS发布的WMTS服务BoundingBox节点格式:<ows:BoundingBox crs="urn:ogc:def:crs:EPSG:102100"></ows:BoundingBox>
        Node[] boundingBoxNodes = getChildNodes(layerNode, "ows:BoundingBox");
        if (boundingBoxNodes != null) {
            List<BoundsWithCRS> boundsBoxes = new ArrayList<BoundsWithCRS>();
            for (Node boundingBoxNode : boundingBoxNodes) {
                BoundingBox bounds = getBoundsFromBoundsNode(boundingBoxNode);
                Node crsNode = boundingBoxNode.getAttributes().getNamedItem("crs");
                String crs = "";
                if (crsNode != null) {
                    crs = crsNode.getNodeValue().trim();// getNodeText(crsNode).trim();
                    Log.d(LOG_TAG, "BoundingBox crs:" + crs);
                }
                BoundsWithCRS boundsWithCrs = new BoundsWithCRS(bounds, crs);
                if (wmtsMapLayer.bounds == null) {
                    wmtsMapLayer.bounds = boundsWithCrs;
                }
                boundsBoxes.add(boundsWithCrs);
            }
            wmtsMapLayer.boundingBoxes = boundsBoxes;
        }
        // ows:WGS84BoundingBox
        // iServer和geoServer发布的WMTS服务中该节点无"crs"属性；ArcGIS发布的WMTS服务 有"crs"属性
        Node wgs84BBoxNode = getChildNode(layerNode, "ows:WGS84BoundingBox");
        if (wgs84BBoxNode != null) {
            BoundingBox bounds = getBoundsFromBoundsNode(wgs84BBoxNode);
            Node crsNode = wgs84BBoxNode.getAttributes().getNamedItem("crs");
            String crs = "";
            if (crsNode != null) {
                crs = crsNode.getNodeValue().trim();
                Log.d(LOG_TAG, "WGS84BoundingBox crs:" + crs);
            }
            wmtsMapLayer.wgs84BoundingBox = new BoundsWithCRS(bounds, crs);
        }
        // style ISVJ-637 没有解析WMTS中的style
        // List<String> styles = new ArrayList<String>();
        Node[] styleNodes = getChildNodes(layerNode, "Style");
        for (int i = 0; styleNodes != null && i < styleNodes.length; i++) {
            Node styleNode = styleNodes[i];
            identifierNode = getChildNode(styleNode, "ows:Identifier");
            if (identifierNode != null) {
                wmtsMapLayer.style = identifierNode.getTextContent();
                break;
                // styles.add(identifierNode.getTextContent());
            }
        }
        return wmtsMapLayer;
    }

    private BoundingBox getBoundsFromBoundsNode(Node bboxNode) {
        String left = "0";
        String bottom = "0";
        String right = "0";
        String top = "0";
        if (bboxNode != null) {
            Node node1 = getChildNode(bboxNode, "ows:LowerCorner");
            if (node1 != null) {
                String leftBottom = node1.getTextContent();
                if (leftBottom != null && leftBottom.length() > 0) {
                    int index = leftBottom.trim().indexOf(" ");
                    if (index != -1) {
                        left = leftBottom.trim().substring(0, index).trim();
                        bottom = leftBottom.trim().substring(index + 1).trim();
                    }
                }
            }
            Node node2 = getChildNode(bboxNode, "ows:UpperCorner");
            if (node2 != null) {
                String rightTop = node2.getTextContent();
                if (rightTop != null && rightTop.length() > 0) {
                    int index = rightTop.trim().indexOf(" ");
                    if (index != -1) {
                        right = rightTop.trim().substring(0, index).trim();
                        top = rightTop.trim().substring(index).trim();
                    }
                }
            }
        }
        BoundingBox result = new BoundingBox(new Point2D(Double.parseDouble(left), Double.parseDouble(top)), new Point2D(Double.parseDouble(right),
                Double.parseDouble(bottom)));
        return result;
    }

    /**
     * <p>
     * 发送 获取WMTS 服务元数据信息请求
     * </p>
     * @param serviceURL 请求地址
     * @return
     * @throws HttpException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @since 7.0.0
     */
    public static Document getDocument(String serviceURL) throws HttpException, IOException, ParserConfigurationException, SAXException {
        Document document = getDocumentByPraseUrl(serviceURL);
        if (document == null) {
            document = getContent(serviceURL, "GET");
        }
        return document;
    }

    /**
     * <p>
     * 使用httpClient发送 获取WMTS 服务元数据信息请求
     * </p>
     * @param url 请求地址
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @since 7.0.0
     */
    public static Document getDocumentByPraseUrl(String url) throws ClientProtocolException, IOException {
        Document document = null;
        // 设置HttpParams
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, "UTF-8");
        HttpProtocolParams.setUseExpectContinue(params, true);
        HttpConnectionParams.setTcpNoDelay(params, true);
        HttpConnectionParams.setSocketBufferSize(params, 4 * 8192);
        HttpConnectionParams.setConnectionTimeout(params, 60000);
        HttpConnectionParams.setSoTimeout(params, 60000);
        DefaultHttpClient httpClient = new DefaultHttpClient(params);
        HttpGet getMethod = new HttpGet(url);
        HttpResponse response = httpClient.execute(getMethod);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            InputStream is = null;
            try {
                is = response.getEntity().getContent();
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                // builder.setEntityResolver(new NoOpEntityResovler());
                document = builder.parse(is);
            } catch (SAXException ex) {
                Log.i(LOG_TAG, ex.getMessage());
            } catch (IOException ex) {
                Log.i(LOG_TAG, ex.getMessage());
            } catch (ParserConfigurationException ex) {
                Log.i(LOG_TAG, ex.getMessage());
            } finally {
                IOUtils.closeQuietly(is);
            }
        } else {
            getMethod.abort();
        }
        httpClient.getConnectionManager().shutdown();
        return document;
    }

    /**
     * <p>
     * 发送 获取WMTS 服务元数据信息请求
     * </p>
     * @param url
     * @param method
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @since 7.0.0
     */
    public static Document getContent(String url, String method) throws IOException, ParserConfigurationException, SAXException {
        Document doc = null;
        int code = 0;
        InputStream responseStream = null;
        Map<String, Object> response = sendRequestByHttpURLConnection(url, method);
        code = (Integer) response.get("status");
        if (code == 200) {
            try {
                responseStream = (InputStream) response.get(ENTITY);
                DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
                DocumentBuilder dombuilder = domfac.newDocumentBuilder();
                doc = dombuilder.parse(responseStream);
            } finally {
                IOUtils.closeQuietly(responseStream);
            }
        }
        return doc;
    }

    /**
     * <p>
     * 使用HttpURLConnection发送 获取WMTS 服务元数据信息请求
     * </p>
     * @param urlStr
     * @param method
     * @return
     * @throws IOException
     * @since 7.0.0
     */
    public static Map<String, Object> sendRequestByHttpURLConnection(String urlStr, String method) throws IOException {
        InputStream responseStream = null;
        HttpURLConnection urlConnection = null;
        Map<String, Object> responseMap = new HashMap<String, Object>();
        URL url = new URL(urlStr);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod(method);
        if (method.equals("POST") || method.equals("PUT")) {
            urlConnection.setDoOutput(true);
        }
        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        urlConnection.setConnectTimeout(40000);
        urlConnection.setReadTimeout(20000);
        urlConnection.connect();
        int code = urlConnection.getResponseCode();
        responseMap.put("status", code);
        if (code >= 400) {
            responseStream = urlConnection.getErrorStream();
        } else {
            responseStream = urlConnection.getInputStream();
        }
        if (responseStream != null) {
            responseMap.put(ENTITY, responseStream);
        }
        return responseMap;
    }

    private boolean isSupportVersion(String version) {
        if (this.supportVersion != null && this.supportVersion.size() > 0) {
            for (String str : this.supportVersion) {
                if (str.equals(version)) {
                    return true;
                }
            }
        }
        return false;
    }
}
