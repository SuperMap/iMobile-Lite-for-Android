package com.supermap.imobilelite.maps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

final class Tool {

    /**
     * 对比两个比例尺是否相等
     */
    public static boolean isDoubleEqual(double source1, double source2) {
        if (Math.abs(source1 - source2) < Math.abs(source1) / 100000.0) {
            return true;
        }
        return false;
    }

    public static double[] getValibScales(double[] sourceScales) {
        return mergeAndSort(sourceScales.clone());
    }

    public static double[] getResolutions(double[] resolutions) {
        double[] targetResolutions = mergeAndSort(resolutions.clone());
        ArrayUtils.reverse(targetResolutions);
        return targetResolutions;
    }

    private static double[] mergeAndSort(double[] sources) {
        if (sources == null || sources.length < 1) {
            return sources;
        }
        Arrays.sort(sources);
        List<Double> resultList = new ArrayList<Double>();
        double sourceItem = sources[0];
        int startIndex = 0;
        if (sourceItem < 1.0E-10) {
            for (int index = 1; index < sources.length; index++) {
                if (sources[index] > 1.0E-10) {
                    sourceItem = sources[index];
                    startIndex = index + 1;
                    break;
                }
            }
            if (sourceItem < 1.0E-10) {
                return null;
            }
        }
        resultList.add(sourceItem);
        for (int index = startIndex; index < sources.length; index++) {
            if (sourceItem > 1.0E-10 && !Tool.isDoubleEqual(sourceItem, sources[index])) {
                sourceItem = sources[index];
                resultList.add(sourceItem);
            }
        }
        double[] temptargets = new double[resultList.size()];
        for (int index = 0; index < resultList.size(); index++) {
            temptargets[index] = resultList.get(index);
        }
        return temptargets;
    }
}
