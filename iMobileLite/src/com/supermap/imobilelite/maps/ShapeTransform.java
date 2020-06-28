package com.supermap.imobilelite.maps;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class ShapeTransform {
    private final int binaryChunkSize = 5;
    private final int minASCII = 63;
    private int precision = 5;
    private boolean optimizeShape;
    private List<Integer> culledShapeOffsets;

    public ShapeTransform() {
        this(5);
    }

    public ShapeTransform(int precision) {
        this.precision = precision;
    }

    public String encodeCompressed(ArrayList<Point2D> shape) {
        int plat = 0;
        int plng = 0;
        int len = shape.size();

        StringBuilder encoded_points = new StringBuilder();
        double mul = Math.pow(10.0D, this.precision);
        for (int i = 0; i < len; i += 2) {
            Point2D point = (Point2D) shape.get(i);
            int late5 = (int) (point.getY() * mul);// 有误差，之前是经纬度的E6取整
            int lnge5 = (int) (point.getX() * mul);

            encoded_points.append(encodeSignedNumber(late5 - plat));
            encoded_points.append(encodeSignedNumber(lnge5 - plng));

            plat = late5;
            plng = lnge5;
        }
        return encoded_points.toString();
    }

    private String encodeSignedNumber(int num) {
        int sgn_num = num << 1;
        if (num < 0) {
            sgn_num ^= -1;
        }
        return encodeNumber(sgn_num);
    }

    private String encodeNumber(int num) {
        StringBuilder encodeString = new StringBuilder();
        while (num >= 32) {
            encodeString.append((char) ((0x20 | num & 0x1F) + 63));

            num >>= 5;
        }
        encodeString.append((char) (num + 63));
        return encodeString.toString();
    }

    public List<Point2D> decodeCompressed(String encoded) {
        if (encoded == null)
            return null;
        ArrayList locs = new ArrayList();
        if (this.optimizeShape) {
            this.culledShapeOffsets = new ArrayList();
        }
        int lat = 0;
        int lng = 0;
        int lastLat = 9999;
        int lastLng = 9999;

        int len = encoded.length();
        double mul = Math.pow(10.0D, -this.precision);
        int index = 0;
        AtomicInteger counter = new AtomicInteger(0);
        while (counter.get() < len) {
            lat += decodePoint(encoded, counter);
            lng += decodePoint(encoded, counter);

            if ((!this.optimizeShape) || (lat != lastLat) || (lng != lastLng)) {
                locs.add(new Point2D(lng * mul, lat * mul));
                lastLat = lat;
                lastLng = lng;
            } else {
                this.culledShapeOffsets.add(Integer.valueOf(index));
            }
            index++;
        }
        this.culledShapeOffsets.add(Integer.valueOf(index));
        return locs;
    }

    private int decodePoint(String encoded, AtomicInteger indexObj) {
        int shift = 0;
        int result = 0;
        int startindex = indexObj.get();
        int b;
        do {
            b = encoded.charAt(startindex++) - '?';

            result |= (b & 0x1F) << shift;

            shift += 5;
        } while (b >= 32);

        int dlat = (result & 0x1) > 0 ? result >> 1 ^ 0xFFFFFFFF : result >> 1;

        indexObj.getAndSet(startindex);
        return dlat;
    }

    public boolean isOptimizedShape() {
        return this.optimizeShape;
    }

    public void setOptimizeShape(boolean optimizeShape) {
        this.optimizeShape = optimizeShape;
    }

    public List<Integer> getCulledShapeOffsets() {
        return this.culledShapeOffsets;
    }
}