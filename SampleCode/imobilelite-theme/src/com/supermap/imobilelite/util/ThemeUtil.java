package com.supermap.imobilelite.util;

import java.util.ArrayList;
import java.util.List;

import com.supermap.imobilelite.commons.EventStatus;
import com.supermap.imobilelite.serverType.ServerColor;
import com.supermap.imobilelite.serverType.ServerStyle;
import com.supermap.imobilelite.serverType.ServerTextStyle;
import com.supermap.imobilelite.theme.GraduatedMode;
import com.supermap.imobilelite.theme.LabelBackShape;
import com.supermap.imobilelite.theme.LabelMatrixCell;
import com.supermap.imobilelite.theme.LabelThemeCell;
import com.supermap.imobilelite.theme.RangeMode;
import com.supermap.imobilelite.theme.RemoveThemeParameters;
import com.supermap.imobilelite.theme.RemoveThemeResult;
import com.supermap.imobilelite.theme.RemoveThemeService;
import com.supermap.imobilelite.theme.RemoveThemeService.RemoveThemeServiceEventListener;
import com.supermap.imobilelite.theme.Theme;
import com.supermap.imobilelite.theme.ThemeDotDensity;
import com.supermap.imobilelite.theme.ThemeFlow;
import com.supermap.imobilelite.theme.ThemeGraduatedSymbol;
import com.supermap.imobilelite.theme.ThemeGraduatedSymbolStyle;
import com.supermap.imobilelite.theme.ThemeGraph;
import com.supermap.imobilelite.theme.ThemeGraphAxes;
import com.supermap.imobilelite.theme.ThemeGraphItem;
import com.supermap.imobilelite.theme.ThemeGraphSize;
import com.supermap.imobilelite.theme.ThemeGraphText;
import com.supermap.imobilelite.theme.ThemeGraphTextFormat;
import com.supermap.imobilelite.theme.ThemeGraphType;
import com.supermap.imobilelite.theme.ThemeGridRange;
import com.supermap.imobilelite.theme.ThemeGridRangeItem;
import com.supermap.imobilelite.theme.ThemeGridUnique;
import com.supermap.imobilelite.theme.ThemeGridUniqueItem;
import com.supermap.imobilelite.theme.ThemeLabel;
import com.supermap.imobilelite.theme.ThemeLabelBackground;
import com.supermap.imobilelite.theme.ThemeLabelItem;
import com.supermap.imobilelite.theme.ThemeParameters;
import com.supermap.imobilelite.theme.ThemeRange;
import com.supermap.imobilelite.theme.ThemeRangeItem;
import com.supermap.imobilelite.theme.ThemeResult;
import com.supermap.imobilelite.theme.ThemeService;
import com.supermap.imobilelite.theme.ThemeService.ThemeServiceEventListener;
import com.supermap.imobilelite.theme.ThemeUnique;
import com.supermap.imobilelite.theme.ThemeUniqueItem;

/**
 * <p>
 * 专题图工具类
 * </p>
 * @author ${Author}
 * @version ${Version}
 * @since 6.1.3
 * 
 */
public class ThemeUtil {
    public static String layerID;

    /**
     * <p>
     * 点密度专题图
     * </p>
     * @param url
     * @return
     * @since 6.1.3
     */
    public static ThemeResult creatThemeDotDensity(String url) {
        // 定义点密度专题图
        ThemeDotDensity theme = new ThemeDotDensity();
        theme.dotExpression = "Pop_1994";
        theme.value = 5000000;
        ServerStyle dotStyle = new ServerStyle();
        dotStyle.markerSize = 3;
        dotStyle.markerSymbolID = 12;
        theme.style = dotStyle;

        // 定义获取专题图时所需参数
        ThemeParameters themeParam = new ThemeParameters();
        themeParam.themes = new Theme[] { theme };
        themeParam.dataSourceNames = new String[] { "World" };
        themeParam.datasetNames = new String[] { "Countries" };

        // 获取专题图
        ThemeService themeservice = new ThemeService(url);
        MyThemeServiceEventListener listener = new MyThemeServiceEventListener();
        themeservice.process(themeParam, listener);
        try {
            listener.waitUntilProcessed();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ThemeResult result = listener.getResult();
        setLayerID(result);
        return result;
    }

    /**
     * <p>
     * 等级符号专题图
     * </p>
     * @param url
     * @return
     * @since 6.1.3
     */
    public static ThemeResult createThemeGraduatedSymbol(String url) {
        // 定义等级符号样式
        ThemeGraduatedSymbolStyle graStyle = new ThemeGraduatedSymbolStyle();
        ServerStyle positiveStyle = new ServerStyle();
        positiveStyle.markerSize = 50;
        positiveStyle.markerSymbolID = 0;
        positiveStyle.lineColor = new ServerColor(255, 165, 0);
        positiveStyle.fillBackColor = new ServerColor(255, 0, 0);
        graStyle.positiveStyle = positiveStyle;

        // 定义等级符号专题图
        ThemeGraduatedSymbol theme = new ThemeGraduatedSymbol();
        theme.baseValue = 3000000000000.00;
        theme.expression = "SMAREA";
        theme.graduatedMode = GraduatedMode.CONSTANT;
        theme.flow = new ThemeFlow();
        theme.flow.flowEnabled = true;
        theme.style = graStyle;

        // 定义获取专题图时所需参数
        ThemeParameters themeParam = new ThemeParameters();
        themeParam.themes = new Theme[] { theme };
        themeParam.dataSourceNames = new String[] { "China400" };
        themeParam.datasetNames = new String[] { "China_Province_R" };

        // 获取专题图
        ThemeService themeservice = new ThemeService(url);
        MyThemeServiceEventListener listener = new MyThemeServiceEventListener();
        themeservice.process(themeParam, listener);
        try {
            listener.waitUntilProcessed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ThemeResult result = listener.getResult();
        setLayerID(result);
        return result;
    }

    /**
     * <p>
     * 统计专题图
     * </p>
     * @param url
     * @return
     * @since 6.1.3
     */
    public static ThemeResult creatThemeGraph(String url) {
        // 定义统计专题图子项
        ThemeGraphItem item1 = new ThemeGraphItem();
        item1.caption = "1992-1995人口增长率";
        item1.graphExpression = "Pop_Rate95";
        ServerStyle style1 = new ServerStyle();
        style1.fillForeColor = new ServerColor();
        style1.fillForeColor.blue = 234;
        style1.fillForeColor.green = 73;
        style1.fillForeColor.red = 92;
        item1.uniformStyle = style1;

        ThemeGraphItem item2 = new ThemeGraphItem();
        item2.caption = "1992-1995人口增长率";
        item2.graphExpression = "Pop_Rate99";
        ServerStyle style2 = new ServerStyle();
        style2.fillForeColor = new ServerColor();
        style2.fillForeColor.blue = 240;
        style2.fillForeColor.green = 111;
        style2.fillForeColor.red = 211;
        item2.uniformStyle = style2;

        // 定义统计专题图
        ThemeGraph theme = new ThemeGraph();
        theme.items = new ThemeGraphItem[] { item1, item2 };
        theme.barWidth = 0.05;
        theme.graduatedMode = GraduatedMode.SQUAREROOT;
        theme.graphAxes = new ThemeGraphAxes();
        theme.graphAxes.axesDisplayed = true;
        theme.graphSize = new ThemeGraphSize();
        theme.graphSize.maxGraphSize = 1;
        theme.graphSize.minGraphSize = 0.35;
        theme.graphText = new ThemeGraphText();
        theme.graphText.graphTextDisplayed = true;
        theme.graphText.graphTextFormat = ThemeGraphTextFormat.VALUE;
        theme.graphType = ThemeGraphType.BAR3D;

        // 定义获取专题图时所需参数
        ThemeParameters themeParam = new ThemeParameters();
        themeParam.themes = new Theme[] { theme };
        themeParam.dataSourceNames = new String[] { "Jingjin" };
        themeParam.datasetNames = new String[] { "BaseMap_R" };

        // 获取专题图
        ThemeService themeservice = new ThemeService(url);
        MyThemeServiceEventListener listener = new MyThemeServiceEventListener();
        themeservice.process(themeParam, listener);
        try {
            listener.waitUntilProcessed();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ThemeResult result = listener.getResult();
        setLayerID(result);
        return result;

    }

    /**
     * <p>
     * 栅格分段专题图
     * </p>
     * @param url
     * @return
     * @since 6.1.3
     */
    public static ThemeResult createThemeGridRange(String url) {
        // 定义栅格分段专题图子项
        ThemeGridRangeItem item1 = new ThemeGridRangeItem();
        item1.start = -4;
        item1.end = 120;
        item1.color = new ServerColor(198, 244, 240);

        ThemeGridRangeItem item2 = new ThemeGridRangeItem();
        item2.start = 120;
        item2.end = 240;
        item2.color = new ServerColor(176, 244, 188);

        ThemeGridRangeItem item3 = new ThemeGridRangeItem();
        item3.start = 240;
        item3.end = 360;
        item3.color = new ServerColor(218, 251, 178);

        ThemeGridRangeItem item4 = new ThemeGridRangeItem();
        item4.start = 360;
        item4.end = 480;
        item4.color = new ServerColor(220, 236, 145);

        ThemeGridRangeItem item5 = new ThemeGridRangeItem();
        item5.start = 480;
        item5.end = 600;
        item5.color = new ServerColor(96, 198, 66);

        ThemeGridRangeItem item6 = new ThemeGridRangeItem();
        item6.start = 600;
        item6.end = 720;
        item6.color = new ServerColor(20, 142, 53);

        ThemeGridRangeItem item7 = new ThemeGridRangeItem();
        item7.start = 720;
        item7.end = 840;
        item7.color = new ServerColor(85, 144, 55);

        ThemeGridRangeItem item8 = new ThemeGridRangeItem();
        item8.start = 840;
        item8.end = 960;
        item8.color = new ServerColor(171, 168, 38);

        ThemeGridRangeItem item9 = new ThemeGridRangeItem();
        item9.start = 960;
        item9.end = 1100;
        item9.color = new ServerColor(235, 165, 9);

        ThemeGridRangeItem item10 = new ThemeGridRangeItem();
        item10.start = 1100;
        item10.end = 1220;
        item10.color = new ServerColor(203, 89, 2);

        ThemeGridRangeItem item11 = new ThemeGridRangeItem();
        item11.start = 1220;
        item11.end = 1340;
        item11.color = new ServerColor(157, 25, 1);

        ThemeGridRangeItem item12 = new ThemeGridRangeItem();
        item12.start = 1340;
        item12.end = 1460;
        item12.color = new ServerColor(118, 15, 3);

        ThemeGridRangeItem item13 = new ThemeGridRangeItem();
        item13.start = 1460;
        item13.end = 1600;
        item13.color = new ServerColor(112, 32, 7);

        ThemeGridRangeItem item14 = new ThemeGridRangeItem();
        item14.start = 1600;
        item14.end = 1800;
        item14.color = new ServerColor(106, 45, 12);

        ThemeGridRangeItem item15 = new ThemeGridRangeItem();
        item15.start = 1800;
        item15.end = 2000;
        item15.color = new ServerColor(129, 80, 50);

        ThemeGridRangeItem item16 = new ThemeGridRangeItem();
        item16.start = 2000;
        item16.end = 2167;
        item16.color = new ServerColor(160, 154, 146);

        // 定义栅格分段专题图
        ThemeGridRange theme = new ThemeGridRange();
        theme.reverseColor = false;
        theme.rangeMode = RangeMode.EQUALINTERVAL;
        theme.items = new ThemeGridRangeItem[] { item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11, item12, item13, item14, item15,
                item16 };

        // 定义获取专题图时所需参数
        ThemeParameters themeParam = new ThemeParameters();
        themeParam.themes = new Theme[] { theme };
        themeParam.dataSourceNames = new String[] { "Jingjin" };
        themeParam.datasetNames = new String[] { "JingjinTerrain" };

        // 获取专题图
        ThemeService themeservice = new ThemeService(url);
        MyThemeServiceEventListener listener = new MyThemeServiceEventListener();
        themeservice.process(themeParam, listener);
        try {
            listener.waitUntilProcessed();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ThemeResult result = listener.getResult();
        setLayerID(result);
        return result;

    }

    /**
     * <p>
     * 栅格单值专题图
     * </p>
     * @param url
     * @return
     * @since 6.1.3
     */
    public static ThemeResult createThemeGridUnique(String url) {
        // 定义栅格单值专题图
        ThemeGridUnique theme = new ThemeGridUnique();
        theme.defaultcolor = new ServerColor(0, 0, 0);
        theme.items = setItems();

        // 定义获取专题图时所需参数
        ThemeParameters themeParam = new ThemeParameters();
        themeParam.themes = new Theme[] { theme };
        themeParam.dataSourceNames = new String[] { "Jingjin" };
        themeParam.datasetNames = new String[] { "JingjinTerrain" };
        //Log.d("ThemeUtil.themeParam", JsonConverter.toJson(themeParam));

        // 获取专题图
        ThemeService themeservice = new ThemeService(url);
        MyThemeServiceEventListener listener = new MyThemeServiceEventListener();
        themeservice.process(themeParam, listener);
        themeservice.setTimeout(0);
        try {
            listener.waitUntilProcessed();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ThemeResult result = listener.getResult();
        setLayerID(result);
        //Log.d("result", JsonConverter.toJson(result));
        return result;

    }

    /**
     * <p>
     * 矩阵标签专题图
     * </p>
     * @param url
     * @return
     * @since 6.1.3
     */
    public static ThemeResult createThemeLabel(String url) {
        //定义文本风格
        ServerTextStyle style1 = new ServerTextStyle();
        style1.fontHeight =4;
        style1.foreColor =new ServerColor(100,20,50);
        style1.sizeFixed =true;
        style1.bold =true;
        
        ServerTextStyle style2 =new ServerTextStyle();
        style2.fontHeight =4;
        style2.foreColor =new ServerColor(250,0,0);
        style2.sizeFixed =true;
        style2.bold =true;
        
        ServerTextStyle style3 =new ServerTextStyle();
        style3.fontHeight =4;
        style3.foreColor =new ServerColor(93,95,255);
        style3.sizeFixed =true;
        style3.bold =true;
        
        //定义标签专题图子项
        ThemeLabelItem themeLabelItem1 =new ThemeLabelItem();
        themeLabelItem1.start =0.0;
        themeLabelItem1.end =7800000.0;
        themeLabelItem1.style =style1;
        
        ThemeLabelItem themeLabelItem2 =new ThemeLabelItem();
        themeLabelItem2.start =7800000.0;
        themeLabelItem2.end =15000000.0;
        themeLabelItem2.style =style2;
        
        ThemeLabelItem themeLabelItem3 =new ThemeLabelItem();
        themeLabelItem3.start =15000000.0;
        themeLabelItem3.end =30000000.0;
        themeLabelItem3.style =style3;
        
        ThemeLabelItem themeLabelItem4 =new ThemeLabelItem();
        themeLabelItem4.start =0.0;
        themeLabelItem4.end =55.0;
        themeLabelItem4.style =style1;
        
        ThemeLabelItem themeLabelItem5 =new ThemeLabelItem();
        themeLabelItem5.start =55.0;
        themeLabelItem5.end =109.0;
        themeLabelItem5.style =style2;
        
        ThemeLabelItem themeLabelItem6 =new ThemeLabelItem();
        themeLabelItem6.start =109.0;
        themeLabelItem6.end =300.0;
        themeLabelItem6.style =style3;
        
        ThemeLabel themeLabelOne =new ThemeLabel();
        themeLabelOne.labelExpression ="CAPITAL";
        themeLabelOne.rangeExpression ="SMID";
        themeLabelOne.numericPrecision =0;
        themeLabelOne.items =new ThemeLabelItem[]{themeLabelItem4,themeLabelItem5,themeLabelItem6};
        
        ThemeLabel themeLabelTwo =new ThemeLabel();
        themeLabelTwo.labelExpression ="CAP_POP";
        themeLabelTwo.rangeExpression ="CAP_POP";
        themeLabelTwo.numericPrecision =0;
        themeLabelTwo.items =new ThemeLabelItem[]{themeLabelItem1,themeLabelItem2,themeLabelItem3};
        
        //创建矩阵标签元素
        LabelThemeCell labelThemeCellOne =new LabelThemeCell();
        labelThemeCellOne.themeLabel =themeLabelOne;
        
        LabelThemeCell labelThemeCellTwo =new LabelThemeCell();
        labelThemeCellTwo.themeLabel =themeLabelTwo;
        
        ServerStyle backStyle = new ServerStyle();
        backStyle.fillForeColor =new ServerColor(255,255,0);
        backStyle.fillOpaqueRate =60;
        backStyle.lineWidth =0.1;
        
        //创建矩阵标签专题图
        ThemeLabel themelabel =new ThemeLabel();
        themelabel.matrixCells =new LabelMatrixCell[][]{{labelThemeCellOne},{labelThemeCellTwo}};
        themelabel.background = new ThemeLabelBackground();
        themelabel.background.backStyle =new ServerStyle();
        themelabel.background.backStyle =backStyle;
        themelabel.background.labelBackShape =LabelBackShape.RECT;
                   
       //定义获取专题图时所需参数
        ThemeParameters themeParam= new ThemeParameters();
        themeParam.themes = new Theme[]{themelabel};             
        themeParam.dataSourceNames = new String[]{"World"};
        themeParam.datasetNames =new String[]{"Capitals"};
        
        // 获取专题图
        ThemeService themeservice = new ThemeService(url);
        MyThemeServiceEventListener listener = new MyThemeServiceEventListener();
        themeservice.process(themeParam, listener);
        try {
            listener.waitUntilProcessed();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ThemeResult result = listener.getResult();
        setLayerID(result);
        return result;

    }

    /**
     * <p>
     * 分段专题图
     * </p>
     * @param url
     * @return
     * @since 6.1.3
     */
    public static ThemeResult createThemeRange(String url) {

        // 定义分段专题图子项
        ThemeRangeItem item1 = new ThemeRangeItem();
        item1.start = 0;
        item1.end = 500000000000.0;
        ServerStyle style1 = new ServerStyle();
        style1.fillForeColor = new ServerColor(137, 203, 187);
        style1.lineColor = new ServerColor(0, 0, 0);
        style1.lineWidth = 0.1;
        item1.style = style1;

        ThemeRangeItem item2 = new ThemeRangeItem();
        item2.start = 500000000000.0;
        item2.end = 1000000000000.0;
        ServerStyle style2 = new ServerStyle();
        style2.fillForeColor = new ServerColor(233, 235, 171);
        style2.lineColor = new ServerColor(0, 0, 0);
        style2.lineWidth = 0.1;
        item2.style = style2;

        ThemeRangeItem item3 = new ThemeRangeItem();
        item3.start = 1000000000000.0;
        item3.end = 3000000000000.0;
        ServerStyle style3 = new ServerStyle();
        style3.fillForeColor = new ServerColor(135, 157, 157);
        style3.lineColor = new ServerColor(0, 0, 0);
        style3.lineWidth = 0.1;
        item3.style = style3;

        // 定义分段专题图
        ThemeRange theme = new ThemeRange();
        theme.items = new ThemeRangeItem[] { item1, item2, item3 };
        theme.rangeExpression = "SMAREA";
        theme.rangeMode = RangeMode.EQUALINTERVAL;

        // 定义获取专题图时所需参数
        ThemeParameters themeParam = new ThemeParameters();
        themeParam.themes = new Theme[] { theme };
        themeParam.dataSourceNames = new String[] { "China400" };
        themeParam.datasetNames = new String[] { "China_Province_R" };

        // 获取专题图
        ThemeService themeservice = new ThemeService(url);
        MyThemeServiceEventListener listener = new MyThemeServiceEventListener();
        themeservice.process(themeParam, listener);
        try {
            listener.waitUntilProcessed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ThemeResult result = listener.getResult();
        setLayerID(result);
        return result;

    }

    /**
     * <p>
     * 单值专题图
     * </p>
     * @param url
     * @return
     * @since 6.1.3
     */
    public static ThemeResult createThemeUnique(String url) {

        // style1
        ServerStyle style1 = new ServerStyle();
        style1.fillForeColor = new ServerColor(248, 203, 249);
        style1.lineColor = new ServerColor(0, 0, 0);
        style1.lineWidth = 0.1;

        // style2
        ServerStyle style2 = new ServerStyle();
        style2.fillForeColor = new ServerColor(196, 255, 189);
        style2.lineColor = new ServerColor(0, 0, 0);
        style2.lineWidth = 0.1;

        // style3
        ServerStyle style3 = new ServerStyle();
        style3.fillForeColor = new ServerColor(255, 173, 173);
        style3.lineColor = new ServerColor(0, 0, 0);
        style3.lineWidth = 0.1;

        // style4
        ServerStyle style4 = new ServerStyle();
        style4.fillForeColor = new ServerColor(255, 239, 168);
        style4.lineColor = new ServerColor(0, 0, 0);
        style4.lineWidth = 0.1;

        // style5
        ServerStyle style5 = new ServerStyle();
        style5.fillForeColor = new ServerColor(173, 209, 255);
        style5.lineColor = new ServerColor(0, 0, 0);
        style5.lineWidth = 0.1;

        // style6
        ServerStyle style6 = new ServerStyle();
        style6.fillForeColor = new ServerColor(132, 164, 232);
        style6.lineColor = new ServerColor(0, 0, 0);
        style6.lineWidth = 0.1;

        // 子项1
        ThemeUniqueItem item1 = new ThemeUniqueItem();
        item1.unique = "黑龙江省";
        item1.style = style1;

        // 子项2
        ThemeUniqueItem item2 = new ThemeUniqueItem();
        item2.unique = "湖北省";
        item2.style = style2;

        // 子项3
        ThemeUniqueItem item3 = new ThemeUniqueItem();
        item3.unique = "吉林省";
        item3.style = style3;

        // 子项4
        ThemeUniqueItem item4 = new ThemeUniqueItem();
        item4.unique = "内蒙古自治区";
        item4.style = style4;

        // 子项5
        ThemeUniqueItem item5 = new ThemeUniqueItem();
        item5.unique = "青海省";
        item5.style = style5;

        // 子项6
        ThemeUniqueItem item6 = new ThemeUniqueItem();
        item6.unique = "新疆维吾尔自治区";
        item6.style = style6;

        // 子项7
        ThemeUniqueItem item7 = new ThemeUniqueItem();
        item7.unique = "云南省";
        item7.style = style1;

        // 子项8
        ThemeUniqueItem item8 = new ThemeUniqueItem();
        item8.unique = "四川省";
        item8.style = style4;

        // 子项9
        ThemeUniqueItem item9 = new ThemeUniqueItem();
        item9.unique = "贵州省";
        item9.style = style3;

        // 子项10
        ThemeUniqueItem item10 = new ThemeUniqueItem();
        item10.unique = "甘肃省";
        item10.style = style3;

        // 子项11
        ThemeUniqueItem item11 = new ThemeUniqueItem();
        item11.unique = "宁夏回族自治区";
        item11.style = style5;

        // 子项12
        ThemeUniqueItem item12 = new ThemeUniqueItem();
        item12.unique = "重庆市";
        item12.style = style6;

        // 子项13
        ThemeUniqueItem item13 = new ThemeUniqueItem();
        item13.unique = "山东省";
        item13.style = style1;

        // 子项14
        ThemeUniqueItem item14 = new ThemeUniqueItem();
        item14.unique = "安徽省";
        item14.style = style2;

        // 子项15
        ThemeUniqueItem item15 = new ThemeUniqueItem();
        item15.unique = "江西省";
        item15.style = style3;

        // 子项16
        ThemeUniqueItem item16 = new ThemeUniqueItem();
        item16.unique = "浙江省";
        item16.style = style4;

        // 子项17
        ThemeUniqueItem item17 = new ThemeUniqueItem();
        item17.unique = "台湾省";
        item17.style = style2;

        // 子项18
        ThemeUniqueItem item18 = new ThemeUniqueItem();
        item18.unique = "江苏省";
        item18.style = style6;

        // 子项19
        ThemeUniqueItem item19 = new ThemeUniqueItem();
        item19.unique = "湖南省";
        item19.style = style5;

        // 子项20
        ThemeUniqueItem item20 = new ThemeUniqueItem();
        item20.unique = "河南省";
        item20.style = style4;

        // 子项21
        ThemeUniqueItem item21 = new ThemeUniqueItem();
        item21.unique = "河北省";
        item21.style = style3;

        // 子项22
        ThemeUniqueItem item22 = new ThemeUniqueItem();
        item22.unique = "福建省";
        item22.style = style5;

        // 子项23
        ThemeUniqueItem item23 = new ThemeUniqueItem();
        item23.unique = "广西壮族自治区";
        item23.style = style6;

        // 子项24
        ThemeUniqueItem item24 = new ThemeUniqueItem();
        item24.unique = "西藏自治区";
        item24.style = style2;

        // 子项25
        ThemeUniqueItem item25 = new ThemeUniqueItem();
        item25.unique = "广东省";
        item25.style = style4;

        // 子项26
        ThemeUniqueItem item26 = new ThemeUniqueItem();
        item26.unique = "山西省";
        item26.style = style2;

        // 子项27
        ThemeUniqueItem item27 = new ThemeUniqueItem();
        item27.unique = "陕西省";
        item27.style = style1;

        // 子项28
        ThemeUniqueItem item28 = new ThemeUniqueItem();
        item28.unique = "天津市";
        item28.style = style5;

        // 子项29
        ThemeUniqueItem item29 = new ThemeUniqueItem();
        item29.unique = "北京市";
        item29.style = style2;

        // 子项30
        ThemeUniqueItem item30 = new ThemeUniqueItem();
        item30.unique = "辽宁省";
        item30.style = style1;

        // 定义单值专题图
        ThemeUnique theme = new ThemeUnique();
        theme.items = new ThemeUniqueItem[] { item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11, item12, item13, item14, item15,
                item16, item17, item18, item19, item20, item21, item22, item23, item24, item25, item26, item27, item28, item29, item30 };
        theme.uniqueExpression = "Name";
        theme.defaultStyle = style1;

        // 定义获取专题图时所需参数
        ThemeParameters themeUniqueParam = new ThemeParameters();
        themeUniqueParam.themes = new Theme[] { theme };
        themeUniqueParam.dataSourceNames = new String[] { "China400" };
        themeUniqueParam.datasetNames = new String[] { "China_Province_R" };

        // 获取专题图
        ThemeService themeservice = new ThemeService(url);
        MyThemeServiceEventListener listener = new MyThemeServiceEventListener();
        themeservice.process(themeUniqueParam, listener);
        try {
            listener.waitUntilProcessed();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ThemeResult result = listener.getResult();
        setLayerID(result);
        return result;

    }

    // 定义栅格单值专题图子项
    public static ThemeGridUniqueItem[] setItems() {
        List<ThemeGridUniqueItem> items = new ArrayList<ThemeGridUniqueItem>();
        for (int i = -4; i < 2197; i++) {
            int num = i / 135;
            ThemeGridUniqueItem item = new ThemeGridUniqueItem();
            item.caption = "1";
            item.unique = i;
            item.visible = true;
            switch (num) {
            case 0:
                item.color = new ServerColor(198, 244, 240);
                break;
            case 1:
                item.color = new ServerColor(176, 244, 188);
                break;
            case 2:
                item.color = new ServerColor(218, 251, 178);
                break;
            case 3:
                item.color = new ServerColor(220, 236, 145);
                break;
            case 4:
                item.color = new ServerColor(96, 198, 66);
                break;
            case 5:
                item.color = new ServerColor(20, 142, 53);
                break;
            case 6:
                item.color = new ServerColor(85, 144, 55);
                break;
            case 7:
                item.color = new ServerColor(171, 168, 38);
                break;
            case 8:
                item.color = new ServerColor(235, 165, 9);
                break;
            case 9:
                item.color = new ServerColor(203, 89, 2);
                break;
            case 10:
                item.color = new ServerColor(157, 25, 1);
                break;
            case 11:
                item.color = new ServerColor(118, 15, 3);
                break;
            case 12:
                item.color = new ServerColor(112, 32, 7);
                break;
            case 13:
                item.color = new ServerColor(106, 45, 12);
                break;
            case 14:
                item.color = new ServerColor(129, 80, 50);
                break;
            case 15:
                item.color = new ServerColor(160, 154, 146);
                break;
            default:
                item.color = new ServerColor(198, 244, 240);
                break;

            }
            items.add(item);
        }
        ThemeGridUniqueItem[] uniqueItems = new ThemeGridUniqueItem[items.size()];
        for (int k = 0; k < items.size(); k++) {
            uniqueItems[k] = items.get(k);
        }
        return uniqueItems;

    }

    /**
     * <p>
     * 移除专题图
     * </p>
     * @param url
     * @return
     * @since 6.1.3
     */
    public static RemoveThemeResult removeTheme(String url) {
        RemoveThemeParameters themeRemoveParam = new RemoveThemeParameters();
        themeRemoveParam.newResourceID = layerID;
        // Log.d("themeRemoveParam",JsonConverter.toJson(themeRemoveParam));
        // 移除专题图
        RemoveThemeService themeRemove = new RemoveThemeService(url);
        MyRemoveThemeServiceEventListener listener = new MyRemoveThemeServiceEventListener();
        themeRemove.process(themeRemoveParam, listener);
        try {
            listener.waitUntilProcessed();
        } catch (Exception e) {
            e.printStackTrace();
        }

        RemoveThemeResult removeResult = listener.getResult();
        // Log.d("removeResult",JsonConverter.toJson(removeResult));
        return removeResult;

    }

    private static void setLayerID(ThemeResult result) {
        if (result != null && result.resourceInfo != null && result.resourceInfo.newResourceID != null) {
            layerID = result.resourceInfo.newResourceID;
        }
    }

    /**
     * <p>
     * 专题图监控器类
     * </p>
     * 
     * @author ${Author}
     * @version ${Version}
     * 
     */
    static class MyThemeServiceEventListener extends ThemeServiceEventListener {
        private ThemeResult lastResult;

        public MyThemeServiceEventListener() {
            super();
        }

        public ThemeResult getResult() {
            return lastResult;
        }

        @Override
        public void onThemeServiceStatusChanged(Object sourceObject, EventStatus status) {
            // 分析结果
            lastResult = (ThemeResult) sourceObject;
        }
    }

    /**
     * <p>
     * 移除专题图监控器类
     * </p>
     * 
     * @author ${Author}
     * @version ${Version}
     * 
     */
    static class MyRemoveThemeServiceEventListener extends RemoveThemeServiceEventListener {
        private RemoveThemeResult result;

        public MyRemoveThemeServiceEventListener() {
            super();
        }

        public RemoveThemeResult getResult() {
            return result;
        }

        @Override
        public void onRemoveThemeServiceStatusChanged(Object sourceObject, EventStatus status) {
            // 分析结果
            result = (RemoveThemeResult) sourceObject;
        }
    }

}
