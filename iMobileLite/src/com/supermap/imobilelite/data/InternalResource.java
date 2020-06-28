package com.supermap.imobilelite.data;

import java.util.ResourceBundle;

/**
 * <p>
 * imobile移植类
 * </p>
 */

public class InternalResource {

	private InternalResource() {
	}

	public static String loadString(String message, String key, String baseBundleName) {
		ResourceBundle bundle;

		String preMsg = "";
		if (message != null && !message.trim().equals("")) {
			preMsg = message.trim() + "\n";
		}
		try {
			bundle = ResourceBundle.getBundle(baseBundleName);
			return preMsg + bundle.getString(key);
		} catch (Exception e) {
			return preMsg;
		}
	}

	public static final String BundleName = "data_resources";

	public static final String GlobalEnumValueIsError = "Global_EnumValueIsError";

	// 不是有效的路径
	public static final String GlobalPathIsNotValid = "Global_PathIsNotValid";
	
	//add by huangkj
	//路径截断后数目不对
	public static final String GlobalPathSplitNumNotValid = "GlobalPathSplitNumNotValid";
	//路径截断后类型不对
	public static final String GlobalPathSplitTypeNotValid = "GlobalPathSplitTypeNotValid";

	// 不是有效的枚举类，要求所有的枚举类都派生至Enum
	public static final String GlobalEnumInvalidDerivedClass = "GlobalEnum_InvalidDerivedClass";

	// 构造函数的参数不合法
	public static final String GlobalInvalidConstructorArgument = "Global_InvalidConstructorArgument";

	// 索引越界
	public static final String GlobalIndexOutOfBounds = "Global_IndexOutOfBounds";

	// 传入的参数对象已经释放掉了
	public static final String GlobalArgumentObjectHasBeenDisposed = "GlobalArgument_ObjectHasBeenDisposed";

	// 参数的类型不正确
	public static final String GlobalArgumentTypeInvalid = "Global_ArgumentTypeInvalid";

	// 参数为空
	public static final String GlobalArgumentNull = "Global_ArgumentNull";

	// 插入的位置不正确，插入的index只能为0-count(包括count)
	public static final String GlobalInvalidInsertPosition = "Global_InvalidInsertPosition";

	// 底层的错误，导致返回结果错误
	public static final String GlobalUGCFailed = "Global_UGCFailed";

	// 对象所属的对象已经释放
	public static final String GlobalOwnerHasBeenDisposed = "Global_OwnerHasBeenDisposed";

	// Handle
	// ---------------------------------------------------------------------------------------
	// InternalHandleDisposable不能通过调用setHandle来初始化对象
	public static final String HandleDisposableCantCreate = "Handle_DisposableCantCreate";

	// 原对象尚未释放
	public static final String HandleOriginalObjectHasNotBeenDisposed = "Handle_OriginalObjectHasNotBeenDisposed";

	/**
	 * 对象不能被释放，不能调用Dispose方法
	 */
	public static final String HandleUndisposableObject = "Handle_UndisposableObject";

	/**
	 * 对象已经被释放
	 */
	public static final String HandleObjectHasBeenDisposed = "Handle_ObjectHasBeenDisposed";

	// 不能设置IsDisposable属性
	public static final String HandleCantSetIsDisposable = "Handle_CantSetIsDisposable";

	// Geometry protected
	// ----------------------------------------------------------------------------------------
	/**
	 * 非法的容限值，容限值应该大于等于0
	 */
	public static final String GeometryInvalidTolerance = "Geometry_InvalidTolerance";

	/**
	 * 调整大小时目标矩形宽度为0
	 */
	public static final String GeometryResizeBoundsWidthIsZero = "Geometry_ResizeBoundsWidthIsZero";

	/**
	 * 调整大小时目标矩形宽度为0
	 */
	public static final String GeometryResizeBoundsHeightIsZero = "Geometry_ResizeBoundsHeightIsZero";

	// the start point is equal to the end point when mirro the geometry
	public static final String GeometryMirroStartEqualToEnd = "Geometry_MirroStartEqualToEnd";

	// the points length should >= 3
	public static final String GeoRegionInvalidPointsLength = "GeoRegion_InvalidPointsLength";

	// geotext can't set style
	public static final String GeoTextUnsupprotStyle = "GeoText_UnsupprotStyle";

	// the points length shoudl >=2
	public static final String GeoLineInvalidPointsLength = "GeoLine_InvalidPointsLength";

	// the distance should >= 0
	public static final String GeoLineArgumentShouldNotBeNegative = "GeoLine_ArgumentShouldNotBeNegative";

	// geoLine.convertToRegion is InvalidOperation
	public static final String GeoLienUnsupportOperation = "GeoLine_UnsupportOperation";

	// 参数线的宽度应该为正数。
	public static final String GeoStyleArgumentOfLineWidthShouldBePositive = "GeoStyle_TheArgumentOfLineWidthShouldBePositive";

	// 当Point2Ds作为GeoLine的part是，执行移除操作后，总个数不可小于2。
	public static final String Point2DsInvalidPointLength = "Point2Ds_InvalidPointsLength";

	// 当Point2Ds是GeoLine或GeoRegion的part时，怎执行clear操作错误
	public static final String Point2DsCannotDoClearOperation = "Point2Ds_CannotDoClearOperation";

	// 所属的工作空间为空，或者已经释放
	public static final String DatasourcesWorkspaceIsInvalid = "Datasources_WorkspaceIsInvalid";

	// 数据源连接信息为空或者已经释放
	public static final String DatasourcesConnectionInfoIsInvalid = "Datasources_ConnectionInfoIsInvalid";

	// 指定别名的数据源不存在
	public static final String DatasourcesAliasIsNotExsit = "Datasources_AliasIsNotExsit";

	// 数据源别名为空
	public static final String DatasourcesAliasIsEmpty = "Datasources_AliasIsEmpty";

	// 数据源别名已经被占用
	public static final String DatasourcesAliasIsAlreadyExsit = "Datasources_AliasIsAlreadyExsit";

	// 打开数据源失败
	public static final String DatasourcesFailToOpenDatasource = "Datasources_FailToOpenDatasource";

	// 创建数据源失败
	public static final String DatasourcesFailToCreateDatasource = "Datasources_FailToCreateDatasource";

	// 数据源所属的工作空间为空或者已经释放
	public static final String DatasourceTheWorkspaceIsInvalid = "Datasource_TheWorkspaceIsInvalid";

	// 数据集名称为空或者已经被占用
	public static final String DatasourceDatasetNameIsInvalid = "Datasource_DatasetNameIsInvalid";

	// 当利用Info创建数据源后，这些属性不能被修改
	public static final String DatasourceConnInfoCantSetProperty = "DatasourceConnInfo_CantSetProperty";

	// 不是有效的枚举类，要求所有的枚举类都派生至Enum
	public static final String EnumInvalidDerivedClass = "Enum_InvalidDerivedClass";

	// 不能创建的数据集类型
	public static final String DatasetsUnsupportToCreate = "Datasets_UnsupportToCreate";

	// 数据集所属的工作空间或者数据源已经被释放或者为空
	public static final String DatasetsParentIsNotValid = "Datasets_ParentIsNotValid";

	// name is occupied
	public static final String DatasetsNameIsOccupied = "Datasets_NameIsOccupied";

	// name is empty
	public static final String DatasetsNameIsEmpty = "Datasets_NameIsEmpty";

	// 数据集已经存在
	public static final String DatasetsDatasetIsAlreadyExist = "Datasets_DatasetIsAlreadyExist";

	// 由于数据集类型不正确，创建数据集失败
	public static final String DatasetsFailToCreateBecauseOfDatasetType = "Datasets_FailToCreateBecauseOfType";

	// 由于编码方式不正确，不能成功创建
	public static final String DatasetsFailToCreateBecauseOfEncodeType = "Datasets_FailToCreateBecauseOfEncodeType";

	// 数据集名称不可取
	public static final String DatasetNameIsNotAvailabe = "Dataset_NameIsNotAvailabe";

	// 数据集名为空
	public static final String DatasetNameIsEmpty = "Dataset_NameIsEmpty";

	// 与系统名称重复
	public static final String DatasetNameAgainstSys = "Dataset_NameAgainstSys";

	// 与系统名称重复
	public static final String DatasetNameErrorPrefix = "Dataset_NameErrorPrefix";

	// 数据集名称中包含非法字符
	public static final String DatasetNameIncludeInvalidChar = "Dataset_NameIncludeInvalidChar";

	// 数据集名称超过了30个的长度限制
	public static final String DatasetNameBeyondLimit = "Dataset_NameBeyondLimit";

	// 数据集为只读，不能对数据集进行操作
	public static final String DatasetIsReadOnly = "Dataset_IsReadOnly";

	// 所属的数据源为空或者已经释放
	public static final String DatasetDatasoureIsEmpty = "Dataset_DatasoureIsEmpty";

	// 所属的工作空间为空或者已经释放
	public static final String DatasetWorkspaceIsEmpty = "Dataset_WorkspaceIsEmpty";

	// 添加字段失败
	public static final String FieldInfosFailToAdd = "FieldInfos_FailToAdd";

	// 插入字段失败
	public static final String FieldInfosFailToInsert = "FieldInfos_FailToInsert";

	// 字段的精度不能
	public static final String FieldInfoPrecisionShouldntBeNegative = "FieldInfo_MaxLengthShouldntBeNegative";

	// 字段的小数点位数不能为负数
	public static final String FieldInfoScaleShouldntBeNegative = "FieldInfo_MaxLengthShouldntBeNegative";

	// 字段所属的字段集合已经释放
	public static final String FieldInfoFieldInfosIsInvalid = "FieldInfo_FieldInfosIsInvalid";

	// 字段的最大长度不能为负数
	public static final String FieldInfoMaxLengthShouldntBeNegative = "FieldInfo_MaxLengthShouldntBeNegative";

	// 当字段属于某个datasetVector的时候不容许设置任何属性
	public static final String FieldInfoCantModifyTheProperty = "FieldInfo_CantModifyTheProperty";

	// 不能克隆系统字段
	public static final String FieldInfoCantCloneSystemField = "FieldInfo_CantCloneSystemField";

	// 字段名称不可取
	public static final String FieldInfoNameIsNotAvaliable = "FieldInfo_NameIsNotAvaliable";

	// 不能修改系统字段
	public static final String FieldInfoCantModifySystemField = "FieldInfoCantModifySystemField";

	// 字段名称为空
	public static final String FieldInfoNameIsEmpty = "FieldInfo_NameIsEmpty";

	// 字段名以sm打头
	public static final String FieldInfoNameBeginsWithSm = "FieldInfo_NameBeginsWithSm";

	// 字段名超过长度限制
	public static final String FieldInfoNameLengthBeyondLimit = "FieldInfo_NameLengthBeyondLimit";

	// 字段名中包括非法字符
	public static final String FieldInfoNameContainInvalidChar = "FieldInfo_NameContainInvalidChar";

	// 字段名以数字或下划线打头
	public static final String FieldInfoNameInvalidPrefix = "FieldInfo_NameInvalidPrefix";

	// 表名称为空
	public static final String FieldInfoTableNameIsEmpty = "FieldInfo_TableNameIsEmpty";

	// 表名超过长度限制
	public static final String FieldInfoTableNameLengthBeyondLimit = "FieldInfo_TableNameLengthBeyondLimit";

	// 表名中包括非法字符
	public static final String FieldInfoTableNameContainInvalidChar = "FieldInfo_TableNameContainInvalidChar";

	// 表名以数字或下划线打头
	public static final String FieldInfoTableNameInvalidPrefix = "FieldInfo_TableNameInvalidPrefix";

	// 指定名称的字段不纯在
	public static final String FieldInfosNameIsNotExist = "FieldInfos_NameIsNotExist";

	// 名称已被占用
	public static final String FieldInfosNameIsOccupied = "FieldInfos_NameIsOccupied";

	// 字段不能被添加至数组中，要添加的字段名不合法或者为系统字段
	public static final String FieldInfosCantAddTheFieldInfo = "FieldInfos_CantAddTheFieldInfo";

	// fieldInfos不支持的操作，如inster exchange 等
	public static final String FieldInfosUnsupported = "FieldInfos_Unsupported";

	// 不可用的字段名
	public static final String DatasetVectorUnAvalibleFieldInfo = "DatasetVector_UnAvalibleFieldInfo";

	// 指定的源字段与目标字段不匹配
	public static final String DatasetVectorAppendDontMatch = "DatasetVector_AppendDontMatch";

	// 宽度应大于0
	public static final String DatasetVectorBuildTileWidthInvalid = "DatasetVector_BuildTileWidthInvalid";

	// 高度应大于0
	public static final String DatasetVectorBuildTileHeightInvalid = "DatasetVector_BuildTileHeightInvalid";

	// 用于建立索引的字段名为空
	public static final String DatasetVectorBuildTileFieldNameIsEmpty = "DatasetVector_BuildTileFieldNameIsEmpty";

	// 用于建立索引的字段不存在
	public static final String DatasetVectorBuildTileFieldIsNotExsit = "DatasetVector_BuildTileFieldIsNotExsit";

	// 缓冲区大小应大于或等于0
	public static final String DatasetVectorQueryBufferInvalid = "DatasetVector_QueryBufferInvalid";

	// 重采样的容限不能为负
	public static final String DatasetVectorResampleToleranceInvalid = "DatasetVector_ResampleToleranceInvalid";

	// 指定字段不存在
	public static final String DatasetVectorFieldIsNotExsit = "DatasetVector_FieldIsNotExsit";

	// 空间索引名为空
	public static final String DatasetVectorSpatialIndexIsEmpty = "DatasetVector_SpatialIndexIsEmpty";

	// 网格索引的格网大小为正数
	public static final String DatasetVectorGirdInvalid = "DatasetVector_GirdInvalid";

	// 后一级别的网格应小于前一级别的
	public static final String DatasetVectorGridOrderError = "DatasetVector_GridOrderError";

	// 不是变体所能支持的类型
	public static final String InternalVariantUnsupportType = "InternalVariant_UnsupportType";

	// 容限所属的对象已经释放
	public static final String ToleranceObjectIsDisposed = "Tolerance_ObjectIsDisposed";

	// 用户创建的容限对象不能调用setDefualt，只有属于某一数据集的方法才可以
	public static final String ToleranceCantInvokeSetDefualt = "Tolerance_CantInvokeSetDefualt";

	// 地图不存在
	public static final String MapsNameIsNotInMaps = "Maps_NameIsNotInMaps";

	// 地图名为空
	public static final String MapsNameIsEmpty = "Maps_NameIsEmpty";

	// 地图名已被占用
	public static final String MapsNameIsOcuupied = "Maps_NameIsOcuupied";

	public static final String WorkspaceCantChangePassword = "Workspace_CantChangePassword";

	// 不能设置属性
	public static final String WorkspaceConnectionInfoCantSetProperty = "WorkspaceConnectionInfo_CantSetProperty";

	// cout超出了范围，集合中没有这么多元素
	public static final String JoinItemsRemoveRangeCountInvalid = "JoinItems_RemoveRangeCountInvalid";

	// 传入的JoinItem已经释放
	public static final String JoinItemsJoinItemDisposed = "JoinItems_SetJoinItemDisposed";

	// cout超出了范围，集合中没有这么多元素
	public static final String LinkItemsRemoveRangeCountInvalid = "LinkItems_RemoveRangeCountInvalid";

	// 传入的JoinItem已经释放
	public static final String LinkItemsLinkItemDisposed = "LinkItems_SetLinkItemDisposed";

	// 自定义的空间查询表达式不合法
	// 必须为3*3的SpatialComparePattern非空数组
	public static final String QueryParameterInvalidSpatialFilter = "QueryParameter_InvalidSpatialFilter";

	// 查询对象不符合要求
	// 可以是Point2D,Rectangle2D,Geometry,DatasetVector,Recordset
	public static final String QueryParameterInvalidQueryObject = "QueryParameter_InvalidQueryObject";

	// 当属于某个Recodset的时候不容许设置属性
	public static final String QueryParameterOfRecordsetCantSet = "QueryParameter_OfRecordsetCantSet";

	// 设置该属性的时候，传入的参数已经释放了
	public static final String QueryParameterJoinItemsDisposed = "QueryParameter_JoinItemsDisposed";

	// 颜色数应大于0
	public static final String ColorsCountShouldBePositive = "Colors_CountShouldBePositive";

	// IntervalColors的长度至少为2
	public static final String ColorsIntervalColorsLengthInvalid = "Colors_IntervalColorsLengthInvalid";

	// Info所属的数据集已经释放
	public static final String DatasetVectorInfoDatasetIsDisposed = "DatasetVectorInfo_DatasetIsDisposed";

	// 当Info属于某个数据集的时候是不可以设置的
	public static final String DatasetVectorInfoCantsetProperty = "DatasetVectorInfo_CantsetProperty";

	// 当属于某个数据集时不容许设置属性
	public static final String DatasetGridInfoCantSetProperty = "DatasetGridInfoCantSetProperty";

	// width属性应为正数
	public static final String DatasetGridWidthShouldBePositive = "DatasetGrid_WidthShouldBePositive";

	// height属性应为正数
	public static final String DatasetGridHeightShouldBePositive = "DatasetGrid_HeightShouldBePositive";

	// blockSize属性应为正数
	public static final String DatasetGridBlockSizeShouldBePositive = "DatasetGrid_BlockSizeShouldBePositive";

	// 当属于某个数据集时不容许设置属性
	public static final String DatasetImageInfoCantSetProperty = "DatasetImageInfoCantSetProperty";

	// width属性应为正数
	public static final String DatasetImageWidthShouldBePositive = "DatasetImage_WidthShouldBePositive";

	// height属性应为正数
	public static final String DatasetImageHeightShouldBePositive = "DatasetImage_HeightShouldBePositive";

	// blockSize属性应为正数
	public static final String DatasetImageBlockSizeShouldBePositive = "DatasetImage_BlockSizeShouldBePositive";

	// RollBackCount只能为>=-1
	public static final String LogFileRollBackCountIsInvalid = "LogFile_RollBackCountIsInvalid";

	// 字段不存在
	public static final String RecordsetFieldIsNotExsit = "Recordset_FieldIsNotExsit";

	/*------------------------>
	 //    以上的资源为Alpha版本的，已经经过整理。
	 */
	// 当PointMs是GeoLineM的part时，执行clear操作错误
	public static final String PointMsCannotDoClearOperation = "PointMs_CannotDoClearOperation";

	// 错误的矢量数据集类型
	public static final String DatasetVectorInfoIllegalDatasetType = "DatasetVectorInfo_IllegalDatasetType";

	// 错误的编码类型
	public static final String DatasetVectorInfoIllegalEncodeType = "DatasetVectorInfo_IllegalEncodeType";

	// 数据集没有内殿，因此不支持InnerPoint To Dataset
	// 包括Tabular与Linktable
	public static final String DatasourceDatasetHasNoInnerPoint = "Datasource_DatasetHasNoInnerPoint";

	// 数据集不存在
	public static final String DatasetsDatasetIsNotExist = "Datasets_DatasetIsNotExist";

	// 先打开数据集才能执行指定的操作
	public static final String DatasetOpenItFirst = "Dataset_OpenItFirst";

	// 如果不是自定义类型，不可以设置该属性
	public static final String IfTypeNotUserDefinedCantSetProperty = "IfTypeNotUserDefinedCantSetProperty";

	// 如果是平面坐标系，该属性不支持
	public static final String ThisPropertyNotSupportedIfSpatialRefTypeNoneEarth = "ThisPropertyNotSupportedIfSpatialRefTypeNoneEarth";

	// 数据源是只读的，不能进行这些操作
	public static final String DatasetsDatasourceIsReadOnly = "Datasets_DatasourceIsReadOnly";

	// Point2Ds的长度应大于0
	public static final String Point2DsIsEmpty = "Point2DsIsEmpty";

	// 保存地图的xml字符串为空
	public static final String MapsXMLIsEmpty = "MapsXMLIsEmpty";

	// 不支持该数据集类型
	public static final String RecordsetDatasetTypeIsNotSupported = "RecordsetDatasetTypeIsNotSupported";

	// 字体的高度应该为正数。
	public static final String TextStyleTheValueOfFontHeightShouldBePositive = "TextStyle_TheValueOfFontHeightShouldBePositive";

	// 字体的宽度应该为正数。
	public static final String TextStyleTheValueOfFontWidthShouldBePositive = "TextStyle_TheValueOfFontWidthShouldBePositive";

	// 几何风格对象的符号ID不可为负数。
	public static final String GeoStyleTheValueOfSymbolIDShouldNotBeNegative = "GeoStyle_TheValueOfSymbolIDShouldNotBeNegative";

	// 符号的大小不可为负数。
	public static final String GeoStyleTheValueOfMarkerSizeShouldNotBeNeagtive = "GeoStyle_TheValueOfMarkerSizeShouldNotBeNeagtive";

	// Geometry不能为Empty
	public static final String GeometryShouldNotBeEmpty = "GeometryShouldNotBeEmpty";

	public static final String GlobalProductionVersion = "Global_ProductionVersion";

	public static final String GlobalProductionAdditionMessage = "Global_ProductionAdditionMessage";

	/*------------------------>
	 |   以上的资源为Beta版本的，已经经过整理。
	 △
	 */

	// Sting 执行trim后为空
	public static final String GlobalStringIsEmpty = "Global_StringIsEmpty";

	// String为null或者为空
	public static final String GlobalStringIsNullOrEmpty = "Global_StringIsNullOrEmpty";

	// GlobalSpecifiedNameNotExist,指定名称的对象不存在
	public static final String GlobalSpecifiedNameNotExist = "Global_SpecifiedNameNotExist";

	// GlobalSpecifiedNameAlreadyExist,指定名称的对象已经存在
	public static final String GlobalSpecifiedNameAlreadyExist = "Global_SpecifiedNameAlreadyExist";

	// 不合法的经纬度坐标
	public static final String InvalidLongitudeLatitudeCoord = "InvalidLongitudeLatitudeCoord";

	// 对应的fieldName是系统字段，不可执行更新
	public static final String DatasetVectorCannotUpdateSystemField = "DatasetVector_CannotUpdateSystemField";

	// 对象是只读的，不可修改。
	public static final String DatasourceTheDatasourceIsReadOnly = "Datasource_TheDatasourceIsReadOnly";

	// 该数据集或所在的数据源是只读的，不可修改。
	public static final String DatasetVectorTheDatasourceOrDatasetIsReadOnly = "DatasetVector_TheDatasourceOrDatasetIsReadOnly";

	// 该数据集或所在的数据源是只读的，不可修改。
	public static final String DatasetImageTheDatasourceOrDatasetIsReadOnly = "DatasetImage_TheDatasourceOrDatasetIsReadOnly";

	// 该数据集或所在的数据源是只读的，不可修改。
	public static final String DatasetGridTheDatasourceOrDatasetIsReadOnly = "DatasetGrid_TheDatasourceOrDatasetIsReadOnly";

	// 记录集是只读的，不可修改。
	public static final String RecordsetRecordsetIsReadOnly = "Recordset_RecordsetIsReadOnly";

	// 点是空的
	public static final String Point2DIsEmpty = "Point2D_IsEmpty";

	// TileWidth必须大于0
	public static final String SpatialIndexInfoTileWidthShouldGreaterThanZero = "SpatialIndexInfo_TileWidthShouldGreaterThanZero";

	// TileHeight必须大于0
	public static final String SpatialIndexInfoTileHeightShouldGreaterThanZero = "SpatialIndexInfo_TileHeightShouldGreaterThanZero";

	// GridSize必须大于0
	public static final String SpatialIndexInfoGridSizeShouldGreaterThanZero = "SpatialIndexInfo_GridSizeShouldGreaterThanZero";

	// MaxConnection应该为正数
	public static final String DatasourceConnectionInfoMaxConnectionShouldBePositive = "DatasourceConnectionInfo_MaxConnectionShouldBePositive";

	// MinConnection应该为正数
	public static final String DatasourceConnectionInfoMinConnectionShouldBePositive = "DatasourceConnectionInfo_MinConnectionShouldBePositive";

	// ConnectionIncrementStep不应该为负数
	public static final String DatasourceConnectionInfoConnectionIncrementStepShouldNotBeNegative = "DatasourceConnectionInfo_ConnectionIncrementStepShouldNotBeNegative";

	// 不支持该编码类型
	public static final String GlobalUnsupportedEncodeType = "Global_UnsupportedEncodeType";

	// column不在范围内
	public static final String DatasetGridColumnIsOutOfRange = "DatasetGrid_ColumnIsOutOfRange";

	// row不在范围内
	public static final String DatasetGridRowIsOutOfRange = "DatasetGrid_RowIsOutOfRange";

	// column不在范围内
	public static final String DatasetImageColumnIsOutOfRange = "DataseImage_ColumnIsOutOfRange";

	// row不在范围内
	public static final String DatasetImageRowIsOutOfRange = "DatasetImage_RowIsOutOfRange";

	// 文本类型不支持距离查询操作。
	public static final String DatasetVectorGeoTextIsUnsupported = "DatasetVector_GeoTextIsUnsupported";

	// 属性数据集不支持HasGeometry的查询。
	public static final String DatasetVector_TabularUnsupport = "DatasetVector_TabularUnsupport";

	// 悬线容限不应为负数
	public static final String ToleranceDangleShouldNotBeNegative = "Tolerance_DangleShouldNotBeNegative";

	// Fuzzy不应为负数
	public static final String ToleranceFuzzyShouldNotBeNegative = "Tolerance_FuzzyShouldNotBeNegative";

	// 颗粒容限不应为负数
	public static final String ToleranceGrainShouldNotBeNegative = "Tolerance_GrainShouldNotBeNegative";

	// 结点捕捉的容限不应为负数
	public static final String ToleranceNodeSnapShouldNotBeNegative = "Tolerance_NodeSnapShouldNotBeNegative";

	// 最小多边形容限不应为负数
	public static final String ToleranceSmallPolygonShouldNotBeNegative = "Tolerance_SmallPolygonShouldNotBeNegative";

	// 不存在该字段
	public static final String DatasourceFieldIsNotExist = "Datasource_FieldIsNotExist";

	// 系统字段是不可以修改的
	public static final String RecordsetSystemFieldIsReadOnly = "Recordset_SystemFieldIsReadOnly";

	// 容限必须大于0
	public static final String GlobalToleranceShouldGreaterThanZero = "Global_ToleranceShouldGreaterThanZero";

	// 必填字段必须设置默认值
	public static final String FieldInfosRequiredFeildInfoMustBeSettedDefaultValue = "FieldInfos_RequiredFeildInfoMustBeSettedDefaultValue";

	// 求并几何对象必须为相同类型
	public static final String GeometristTheTypeOfGeometryToUnionMustBeSame = "Geometrist_TheTypeOfGeometryToUnionMustBeSame";

	// 非法许可
	public static final String LicenseInvalid = "License_Invalid";

	// 合法许可
	public static final String LicenseValid = "License_Valid";

	// 没有找到加密锁或者加密许可文件
	public static final String LicenseContainerNotFound = "License_ContainerNotFound";

	// 没有找到许可模块
	public static final String LicenseFeatureNotFound = "License_FeatureNotFound";

	// 必填字段不能为Null
	public static final String RecordsetRequiredFieldShouldNotBeNull = "Recordset_RequiredFieldShouldNotBeNull";

	// 不支持的PixelFormat类型
	public static final String DatasetImageInfoUnSupportedPixelFormat = "DatasetImageInfo_UnSupportedPixelFormat";

	// 数据集的类型与几何对象的类型不相同
	public static final String RecordsetDatasetTypeAndGeometryTypeIsDifferent = "Recordset_DatasetTypeAndGeometryTypeIsDifferent";

	// 几何对象为空
	public static final String RecordsetGeometryIsEmpty = "Recordset_GeometryIsEmpty";

	// 关于SuperMap Objects Java 2008
	public static final String AboutBoxWelcomeTitle = "AboutBox_WelcomeTitle";

	// Logo
	public static final String AboutBoxLogoRes = "AboutBox_LogoRes";

	/*------------------------>
	 |
	 |
	 |   以上的资源为正式版本的，已经经过整理。
	 |   以后添加的部分放到下面，待以后统一整理。
	 |
	 |
	 △
	 */
	public static final String Point3DsInvalidCount = "Point3Ds_TheCountIsInvalid";

	// the points length shoudl >=2
	public static final String GeoLine3DInvalidPointsLength = "GeoLine3D_InvalidPointsLength";

	// the points length should >= 3
	public static final String GeoRegion3DInvalidPointsLength = "GeoRegion3D_InvalidPointsLength";

	// 当Point3Ds作为GeoLine3D的part是，执行移除操作后，总个数不可小于2。
	public static final String Point3DsInvalidPointLength = "Point3Ds_InvalidPointsLength";

	// 当Point3Ds是GeoLine3D或GeoRegion3D的part时，怎执行clear操作错误
	public static final String Point3DsCannotDoClearOperation = "Point3Ds_CannotDoClearOperation";

	// GeoPoint3D
	public static final String GeoPoint3DUnsupprotStyle2D = "GeoPoint3D_UnsupprotStyle2D";

	// GeoLine3D can't set style
	public static final String GeoLine3DUnsupprotStyle2D = "GeoLine3D_UnsupprotStyle2D";

	// GeoRegion3D can't set style
	public static final String GeoRegion3DUnsupprotStyle2D = "GeoRegion3D_UnsupprotStyle2D";

	public static final String GeoPieCylinderUnsupprotStyle2D = "GeoPieCylinder_UnsupprotStyle2D";

	public static final String GeoSphereUnsupprotStyle2D = "GeoSphere_UnsupprotStyle2D";

	public static final String GeoHemiSphereUnsupprotStyle2D = "GeoHemiSphere_UnsupprotStyle2D";

	public static final String GeoEllipsoidUnsupprotStyle2D = "GeoEllipsoid_UnsupprotStyle2D";

	public static final String GeoPie3DUnsupprotStyle2D = "GeoPie3D_UnsupprotStyle2D";

	public static final String GeoCircle3DUnsupprotStyle2D = "GeoCircle3D_UnsupprotStyle2D";

	// 参数线的宽度应该为正数。
	public static final String GeoStyle3DArgumentOfLineWidthShouldBePositive = "GeoStyle3D_TheArgumentOfLineWidthShouldBePositive";

	// 三维图标的缩放比率应该为正数。
	public static final String GeoStyle3DArgumentOfMarkerIconScaleShouldBePositive = "GeoStyle3D_TheArgumentOfMarkerIconScaleShouldBePositive";

	// 符号的大小不可为负数。
	public static final String GeoStyle3DTheValueOfMarkerSizeShouldNotBeNeagtive = "GeoStyle3D_TheValueOfMarkerSizeShouldNotBeNeagtive";

	// 侧面纹理U方向（横向）的重复次数可为负数。
	public static final String GeoStyle3DTheValueOfTilingUShouldNotBeNeagtive = "GeoStyle3D_TheValueOfTilingUShouldNotBeNeagtive";

	// 侧面纹理V方向（纵向）的重复次数可为负数。
	public static final String GeoStyle3DTheValueOfTilingVShouldNotBeNeagtive = "GeoStyle3D_TheValueOfTilingVShouldNotBeNeagtive";
	
	// 顶面纹理U方向（横向）的重复次数可为负数。
	public static final String GeoStyle3DTheValueOfTopTilingUShouldNotBeNeagtive = "GeoStyle3D_TheValueOfTopTilingUShouldNotBeNeagtive";

	// 顶面纹理V方向（纵向）的重复次数可为负数。
	public static final String GeoStyle3DTheValueOfTopTilingVShouldNotBeNeagtive = "GeoStyle3D_TheValueOfTopTilingVShouldNotBeNeagtive";

	// add by xuzw 2008-11-26
	// GeoArc类中，构造GeoArc失败
	public static final String GeoArcFailConstruct = "GeoArc_FailConstruct";

	// add by xuzw 2008-12-03
	// GeoChord类中，sweepAngle越界
	public static final String GeoChordSweepAngleRange = "GeoChord_SweepAngleRange:(-360,0)||(0,360)";

	// GeoChord中的SweepAngle不能为0
	public static final String GeoChordSweepAngleShouldNotBeZero = "GeoChord_SweepAngleShouldNotBeZero";

	// GeoPie中，SweepAngle的范围应该是(-360,0)||(0,360)
	public static final String GeoPieSweepAngleRange = "GeoPie_SweepAngleRange:(-360,0)||(0,360)";

	// GeoPie中的SweepAngle不能为0
	public static final String GeoPieSweepAngleShouldNotBeZero = "GeoPie_SweepAngleShouldNotBeZero";

	// GeoPie3D类中，sweepAngle越界
	public static final String GeoPie3DSweepAngleRange = "GeoPie3D_SweepAngleRange:(-360,0)||(0,360)";

	// GeoPie3D中的SweepAngle不能为0
	public static final String GeoPie3DSweepAngleShouldNotBeZero = "GeoGeoPie3D_SweepAngleShouldNotBeZero";

	// GeoPieCylinder类中，sweepAngle越界
	public static final String GeoPieCylinderSweepAngleRange = "GeoPieCylinder_SweepAngleRange:(-360,0)||(0,360)";

	// GeoPieCylinder中的SweepAngle不能为0
	public static final String GeoPieCylinderSweepAngleShouldNotBeZero = "GeoPieCylinder_SweepAngleShouldNotBeZero";

	// add by xuzw 2008-12-03
	// GeoCardinal类中，控制点数应该大于等于2
	public static final String GeoCardinalControlPointsLengthShouldNotLessThanTwo = "GeoCardinal_ControlPointsLengthShouldNotLessThanTwo";

	public static final String GeoCurveControlPointsLengthShouldNotLessThanSix = "GeoCurve_ControlPointsLengthShouldNotLessThanSix";

	// add by xuzw 2008-12-04
	// GeoBSpline类中，控制点数应该大于等于4
	public static final String GeoBSplineControlPointsLengthShouldNotLessThanFour = "GeoBSpline_ControlPointsLengthShouldNotLessThanFour";

	// 点重合
	public static final String PointsAreSame = "PointsAreSame";

	// 三点共线
	public static final String ThreePointsAreInOneLine = "ThreePointsAreInOneLine";

	// 矩形宽度必须为正数
	public static final String GeoRectangleWidthShouldPositive = "GeoRectangleWidthShouldPositive";

	// 矩形高度必须为正数
	public static final String GeoRectangleHeightShouldPositive = "GeoRectangleHeightShouldPositive";

	// 圆角矩形宽度必须为正数
	public static final String GeoRoundRectangleWidthShouldBePositive = "GeoRoundRectangle_WidthShouldBePositive";

	// 圆角矩形高度必须为正数
	public static final String GeoRoundRectangleHeightShouldBePositive = "GeoRoundRectangle_HeightShouldBePositive";

	// 圆角矩形RadiusX必须为正数
	public static final String GeoRoundRectangleRadiusXShouldNotBeNeagtive = "GeoRoundRectangle_RadiusXShouldNotBeNeagtive";

	// 圆角矩形RadiusY必须为正数
	public static final String GeoRoundRectangleRadiusYShouldNotBeNeagtive = "GeoRoundRectangle_RadiusYShouldNotBeNeagtive";

	// 圆角矩形宽度必须为>=2*RadiusX
	public static final String GeoRoundRectangleWidthShouldNotLessThan2RadiusX = "GeoRoundRectangle_WidthShouldNotLessThan2RadiusX";

	public static final String Rectangle2DWidthAndHeightShouldMoreThanZero = "Rectangle2DWidthAndHeightShouldMoreThanZero";

	// 圆角矩形高度必须为>=2*RadiusY
	public static final String GeoRoundRectangleHeightShouldNotLessThan2RadiusY = "GeoRoundRectangle_HeightShouldNotLessThan2RadiusY";

	// 圆角矩形转成GeoLine的段数>1
	public static final String GeoRoundRectangleGeoLineSegmentCountShouldGreaterThanOne = "GeoRoundRectangle_GeoLineSegmentCountShouldGreaterThanOne";

	// 圆角矩形转成GeoRegion的段数>1
	public static final String GeoRoundRectangleGeoRegionSegmentCountShouldGreaterThanOne = "GeoRoundRectangle_GeoRegionSegmentCountShouldGreaterThanOne";

	// 椭圆长半轴必须为正数
	public static final String GeoEllipseSemiMajorAxisShouldBePositive = "GeoEllipse_SemimajorAxisShouldBePositive";

	// 椭圆短半轴必须为正数
	public static final String GeoEllipseSemiMinorAxisShouldBePositive = "GeoEllipse_SemiMinorAxisShouldBePositive";

	// 椭圆转成GeoLine的段数>1
	public static final String GeoEllipseGeoLineSegmentCountShouldGreaterThanOne = "GeoEllipse_GeoLineSegmentCountShouldGreaterThanOne";

	// 椭圆转成GeoRegion的段数>1
	public static final String GeoEllipseGeoRegionSegmentCountShouldGreaterThanOne = "GeoEllipse_GeoRegionSegmentCountShouldGreaterThanOne";

	// 椭圆弧长半轴必须为正数
	public static final String GeoEllipticArcSemiMajorAxisShouldBePositive = "GeoEllipticArc_SemimajorAxisShouldBePositive";

	// 椭圆弧短半轴必须为正数
	public static final String GeoEllipticArcSemiMinorAxisShouldBePositive = "GeoEllipticArc_SemiMinorAxisShouldBePositive";

	// 椭圆弧扫描角 -360~360
	public static final String GeoEllipticArcSweepAngleRange = "GeoEllipticArc_SweepAngleRange(-360,0)||(0,360)";

	public static final String GeoEllipticArcStartAngleShouldBe360_360 = "GeoEllipticArc_StartAngleShouldBe-360_360";

	public static final String GeoPieCylinderStartAngleShouldBe360_360 = "GeoPieCylinder_StartAngleShouldBe-360_360";

	public static final String GeoPieStartAngleShouldBe360_360 = "GeoPie_StartAngleShouldBe-360_360";

	public static final String GeoPie3DStartAngleShouldBe360_360 = "GeoPie3D_StartAngleShouldBe-360_360";

	// 椭圆弧扫描角 -360~360
	public static final String GeoEllipticArcStartAngleShouldNotBeZero = "GeoEllipticArc_StartAngleShouldNotBeZero";

	public static final String GeoPieCylinderStartAngleShouldNotBeZero = "GeoPieCylinderStartAngleShouldNotBeZero";

	public static final String GeoPieStartAngleShouldNotBeZero = "GeoPie_StartAngleShouldNotBeZero";

	public static final String GeoPie3DStartAngleShouldNotBeZero = "GeoPie3D_StartAngleShouldNotBeZero";

	// 椭圆弧转成GeoLine的段数>=0
	public static final String GeoEllipticArcGeoLineSegmentCountShouldNotBeNeagtive = "GeoEllipticArc_GeoLineSegmentCountShouldNotBeNeagtive";

	// 长方体对象的长>0
	public static final String GeoBoxLengthShouldBePositive = "GeoBox_LengthShouldBePositive";

	// 长方体对象的宽>0
	public static final String GeoBoxWidthShouldBePositive = "GeoBox_WidthShouldBePositive";

	// 长方体对象的高>0
	public static final String GeoBoxHeightShouldBePositive = "GeoBox_HeightShouldBePositive";

	// 圆锥体对象底圆的半径>0
	public static final String GeoConeBottomRadiusShouldBePositive = "GeoCone_BottomRadiusShouldBePositive";

	// 圆锥体对象高>0
	public static final String GeoConeHeightShouldBePositive = "GeoCone_HeightShouldBePositive";

	// 二维图片宽>0
	public static final String GeoPictureWidthShouldBePositive = "GeoPicture_WidthShouldBePositive";

	// 二维图片高>0
	public static final String GeoPictureHeightShouldBePositive = "GeoPicture_HeightShouldBePositive";

	// 三 维图片宽>0
	public static final String GeoPicture3DWidthShouldBePositive = "GeoPicture3D_WidthShouldBePositive";

	// 三 维图片高>0
	public static final String GeoPicture3DHeightShouldBePositive = "GeoPicture3D_HeightShouldBePositive";

	// 二维图片高>0
	public static final String GeoPictureFailConstruct = "GeoPicture_FailConstruct";

	// add by xuzw 2008-12-03
	// 弓形长半轴必须为正数
	public static final String GeoChordSemiMajorAxisShouldBePositive = "GeoChord_SemimajorAxisShouldBePositive";

	// 弓形短半轴必须为正数
	public static final String GeoChordSemiMinorAxisShouldBePositive = "GeoChord_SemiMinorAxisShouldBePositive";

	// 圆台的顶圆半径必须为正数
	public static final String GeoCylinderTopRadiusShouldBePositive = "GeoCylinder_TopRadiusShouldBePositive";

	// 圆台的底圆半径必须为正数
	public static final String GeoCylinderBottomRadiusShouldBePositive = "GeoCylinder_BottomRadiusShouldBePositive";

	// 圆台的高度必须为正数
	public static final String GeoCylinderHeightShouldBePositive = "GeoCylinder_HeightShouldBePositive";

	// add by xuzw 2008-12-04
	// 四棱锥的底面长度必须为正数
	public static final String GeoPyramidLengthShouldPositive = "GeoPyramid_LengthShouldPositive";

	// 四棱锥的底面宽度必须为正数
	public static final String GeoPyramidWidthShouldPositive = "GeoPyramid_WidthShouldPositive";

	// 四棱锥的高度必须为正数
	public static final String GeoPyramidHeightShouldPositive = "GeoPyramid_HeightShouldPositive";

	// 传入的参数应该非负>=0
	public static final String GlobalArgumentShouldNotBeNegative = "Global_ArgumentShouldNotBeNegative";

	// 传入的参数应该>0
	public static final String GlobalArgumentShouldMoreThanZero = "Global_ArgumentShouldMoreThanZero";

	// 传入的参数应该>=1
	public static final String GlobalArgumentShouldNotSmallerThanOne = "Global_ArgumentShouldNotSmallerThanOne";

	// 传入的参数应该>=2
	public static final String GlobalArgumentShouldNotSmallerThanTwo = "Global_ArgumentShouldNotSmallerThanTwo";

	// add by xuzw 2008-12-09
	// GeoArc类中，sweepAngle的范围是(-360,0)||(0,360)
	public static final String GeoArcSweepAngleRange = "GeoArc_SweepAngleRange:(-360,0)||(0,360)";

	public static final String GeoArcSweepAngleShouldNotBeZero = "GeoArc_SweepAngleShouldNotBeZero";

	// GeoArc中，FindPointOnArc方法传入角度越界
	public static final String GeoArcSweepAngleOutOfBounds = "GeoArc_SweepAngleOutOfBounds";

	// add by xuzw 2008-12-10
	// GeoCylinder can't set style
	public static final String GeoCylinderUnsupprotStyle2D = "GeoCylinder_UnsupprotStyle2D";

	// GeoPyramid can't set style
	public static final String GeoPyramidUnsupprotStyle2D = "GeoPyramid_UnsupprotStyle2D";

	// GeoEllipticArc类中，角度越界异常
	public static final String GeoEllipticArcArgumentOutOfBounds = "GeoEllipticArc_ArgumentOutOfBounds";

	// 批量添加操作中，不允许回退
	public static final String BatchAddOperationUnsupportedUndo = "BatchAddOperation_Unsupport_Undo";

	// 批量添加操作中，不允许前进
	public static final String BatchAddOperationUnsupportedRedo = "BatchAddOperation_Unsupport_Redo";

	// 已有事件发生，无法处理
	public static final String BatchHasBegun = "BatchHasBegun";

	// 符号库添加了不同类型的符号
	public static final String SymbolLibraryUnsupportedType = "SymbolLibrary_Unsupported_Add";

	// 符号库已存在ID
	public static final String SymbolLibraryHasContaintID = "SymbolLibrary_HasContaintID";

	// 符号库ID必须大于等于 0
	public static final String SymbolIDShouldNotBeNegative = "Symbol_IDShouldNotBeNegative";

	public static final String TwoPointsShouldNotBeEqual = "TwoPoints_ShouldNotBeEqual";

	// 文件不存在
	public static final String GeoModelTheFileIsNotExist = "GeoModel_TheFileIsNotExist";

	public static final String GlobalArgumentOutOfBounds = "Global_ArgumentOutOfBounds";

	// 波段的名称不合法
	public static final String DatasetGridBandNameIsNotValid = "DatasetGrid_BandNameIsNotValid";

	// 不是多波段，不支持该操作
	public static final String DatasetGridThisOperationIsAvailableForMultibandsDataOnly = "DatasetGrid_ThisOperationIsAvailableForMultibandsDataOnly";

	// 参数必须是多波段
	public static final String DatasetGridTheArgumentMustBeMultibandsData = "DatasetGrid_TheArgumentMustBeMultibandsData";

	// 个数不正确
	public static final String DatasetGridInvalidCount = "DatasetGrid_InvalidCount";

	// 波段的名称不合法
	public static final String DatasetImageBandNameIsNotValid = "DatasetImage_BandNameIsNotValid";

	// 不是多波段，不支持该操作
	public static final String DatasetImageThisOperationIsAvailableForMultibandsDataOnly = "DatasetImage_ThisOperationIsAvailableForMultibandsDataOnly";

	// 参数必须是多波段
	public static final String DatasetImageTheArgumentMustBeMultibandsData = "DatasetImage_TheArgumentMustBeMultibandsData";

	// 个数不正确
	public static final String DatasetImageInvalidCount = "DatasetImage_InvalidCount";

	// GeoStyle size2D大小不能全负
	public static final String GeoStyleTheValueOfMarkerSizeIsNotValid = "GeoStyleTheValueOfMarkerSize_IsNotValid";

	// 标准图幅图框的图幅名不合法
	public static final String StandardMarginSheetNameIsNotValid = "StandardMargin_SheetName_IsNotValid";

	// 拓扑检查项源数据集必须在拓扑预处理项中
	public static final String TopologyValidatingItemSourceDatasetMustBeInTopologyDatasetRelationItems = "TopologyValidatingItem_SourceDatasetMustBeInTopologyDatasetRelationItems";

	// 拓扑检查项目标数据集必须在拓扑预处理项中
	public static final String TopologyValidatingItemValidatingDatasetMustBeInTopologyDatasetRelationItems = "TopologyValidatingItem_ValidatingDatasetMustBeInTopologyDatasetRelationItems";

	// 传入的拓扑数据集关联项的数据集必须在拓扑数据集所在的数据源中
	public static final String TopologyDatasetRelationItemDatasetMustBeInTopologyDatasetDatasource = "TopologyDatasetRelationItem_DatasetMustBeInTopologyDatasetDatasource";

	// 这个数据集已经参与了拓扑
	public static final String TopologyDatasetRelationItemDatasetAttachTopo = "TopologyDatasetRelationItem_DatasetAttachTopo";

	// 关联数据集精度顺序号超出范围
	public static final String TopologyDatasetRelationItemPrecisionOrderOutOfBounds = "TopologyDatasetRelationItem_PrecisionOrderOutOfBounds";

	// 三维场景名已被占用
	public static final String ScenesNameIsOcuupied = "Scenes_NameIsOcuupied";

	// 三维场景不存在
	public static final String SceneNameIsNotInScenes = "Scene_NameIsNotInScenes";

	// geoLine3D.convertToRegion is InvalidOperation
	public static final String GeoLine3DUnsupportOperation = "GeoLine3D_UnsupportOperation";

	// 版本ID不存在
	public static final String VersionIsNotExsit = "Version_IDIsNotExsit";

	// 不能删除当前版本
	public static final String VersionManagerCurrentVersionCanNotBeDeleted = "VersionManager_CurrentVersionCanNotBeDeleted";

	// 不能删除根版本
	public static final String VersionManagerRootVersionCanNotBeDeleted = "VersionManager_RootVersionCanNotBeDeleted";

	// 当前版本存在子版本，不能删除
	public static final String VersionManagerCurrentVersionHasChildVersionCanNotBeDeleted = "VersionManager_CurrentVersionHasChildVersionCanNotBeDeleted";

	// 数据集必须在数据源中
	public static final String DatasetMustContainInDatasource = "DatasetMustContainInDatasource";

	// 目标版本不是当前版本的父版本
	public static final String TagetVersionIsNotCurrentVersionParent = "TagetVersionIsNotCurrentVersionParent";

	// 当前版本正在其它地方处于编辑状态,不能协调
	public static final String CurrentVersionIsBeingEdited = "CurrentVersionIsBeingEdited";

	// 当前版本正在被协调,不能协调
	public static final String CurrentVersionIsBeingReconciled = "CurrentVersionIsBeingReconciled";

	// 目标版本正在协调,不能协调
	public static final String TagetVersionIsBeingReconciled = "TagetVersionIsBeingReconciled";

	public static final String QueryParameterMustBeStaticCursor = "QueryParameterMustBeStaticCursor";

	// 不支持的数据类型
	public static final String UnsupprotDatasetType = "UnsupprotDatasetType";

	// 布局名字已被占用
	public static final String LayoutsNameIsOcuupied = "Layouts_NameIsOcuupied";

	// 布局名字不存在
	public static final String LayoutsNameIsNotInLayouts = "Layouts_NameIsNotInLayouts";

	// 地图边框几何对象宽度必须为正数
	public static final String GeoMapBorderWidthShouldBePositive = "GeoMapBorder_WidthShouldBePositive";

	// 地图边框几何对象间隔必须大于等于0
	public static final String GeoMapBorderIntervalShouldNotBeNegative = "GeoMapBorder_IntervalShouldNotBeNegative";

	// GeoMapBorder填充文件不存在
	public static final String GeoMapBorderTheFileIsNotExist = "GeoMapBorder_TheFileIsNotExist";

	// GeoMapScale宽度必须为正数
	public static final String GeoMapScaleWidthShouldBePositive = "GeoMapScale_WidthShouldBePositive";

	// GeoMapScale高度必须为正数
	public static final String GeoMapScaleHeightShouldBePositive = "GeoMapScale_HeightShouldBePositive";

	// GeoMapScale的比例应该大于0
	public static final String GeoMapScaleScaleShouldBePositive = "GeoMapScale_ScaleShouldBePositive";

	// GeoMapScale的小节个数应该大于等于2且小于等于20
	public static final String GeoMapScaleSegmentCountOutOfBounds = "GeoMapScale_SegmentCountOutOfBounds";

	// GeoMapScale的小节长度必须大于等于0
	public static final String GeoMapScaleSegmentLengthShouldNotBeNegative = "GeoMapScale_SegmentLengthShouldNotBeNegative";

	// GeoMap不支持的GeometryType
	public static final String GeoMapUnsupprotGeometryType = "GeoMap_UnsupprotGeometryType";

	// GeoMap在设置新的工作空间前不要释放旧的工作空间
	public static final String GeoMapDontDisposeOldWorkspace = "GeoMap_DontDisposeOldWorkspace";

	// GeoMap在设置的新地图名并不存在
	public static final String GeoMapNameIsNotInWorkspace = "GeoMap_NameIsNotInWorkspace";

	// GeoNorthArrow范围的宽度应当是正数
	public static final String GeoNorthArrowBoundsWidthShouldBePositive = "GeoNorthArrow_BoundsWidthShouldBePositive";

	// GeoNorthArrow范围的高度应当是正数
	public static final String GeoNorthArrowBoundsHeightShouldBePositive = "GeoNorthArrow_BoundsHeightShouldBePositive";

	// GeoNorthArrow图片不存在
	public static final String GeoNorthArrowTheImageFileIsNotExist = "GeoNorthArrow_TheImageFileIsNotExist";

	// GeoNorthArrow的BindingGeoMapID应当是正数
	public static final String GeoNorthArrowBindingGeoMapIDShouldBePositive = "GeoNorthArrow_BindingGeoMapIDShouldBePositive";

	// point2DS点个数必须大于2
	public static final String Point2DsPointCountShouldMoreThanTwo = "Point2Ds_PointCountShouldMoreThanTwo";

	// GeoPlacemark不允许setGeometry为GeoPlacemark类型的
	public static final String GeoPlacemarkSetGeometryShouldNotBeGeoPlacemark = "GeoPlacemark_SetGeometryShouldNotBeGeoPlacemark";

	// datasets创建数据集时，对网络数据集，若其名称字符长超过30，抛出异常
	public static final String DatasetsCreateDatasetNameLengthMoreThanThirty = "Datasets_CreateDatasetNameLengthMoreThanThirty";

	/*------------------------>
	 |
	 |
	 |   以上的资源为Alpha的，已经经过整理。
	 |   以后添加的部分放到下面，待以后统一整理。
	 |
	 |
	 △
	 */
	// SymbolGroups中没有SymbolGroup
	public static final String SymbolGroupsNotContainTheSymbolGroup = "SymbolGroups_NotContainTheSymbolGroup";

	// 当前数据集不支持此空间索引
	public static final String DatasetVectorBuildSpatialIndexUnsupportTheSpatialIndexType = "DatasetVector_BuildSpatialIndexUnsupportTheSpatialIndexType";

	// 标准图幅图框的左下角点不能是负数
	public static final String StandardMarginLeftBottomXShouldNotBeNegative = "StandardMargin_LeftBottomXShouldNotBeNegative";

	// 标准图幅图框的左下角点不能是负数
	public static final String StandardMarginLeftBottomYShouldNotBeNegative = "StandardMargin_LeftBottomYShouldNotBeNegative";

	// 多波段indexes必须 与 ColorSpaceType一致
	public static final String DatasetImageIndexesMustCompatibilityWithColorSpaceType = "DatasetImage_Indexes_Must_Compatibility_With_ColorSpaceType";

	// DatasetGrid影像数据集不支持该像素格式
	public static final String DatasetGridInfoUnSupportedPixelFormat = "DatasetGridInfo_UnSupportedPixelFormat";

	// 文件不存在
	public static final String GlobalFileNotExists = "Global_FileNotExists";

	// Geometrist的Smooth方法的光滑系数应当在[2,10]
	public static final String GeometristSmoothSmoothnessShouldBetweenTwoAndTen = "Geometrist_SmoothSmoothnessShouldBetweenTwoAndTen";

	// Geometrist的Smooth方法传入点串至少四个点
	public static final String GeometristSmoothPointsCountShouldNotSmallThanFour = "Geometrist_SmoothPointsCountShouldNotSmallThanFour";

	public static final String DatasetUnsupportedOperation = "Dataset_SetPrjCoordSysUnsupportedTheEngineType";

	public static final String RecordsetBatchEditorIsBeginning = "Recordset_BatchEditorIsBeginning";

	/*------------------------>
	 |
	 |
	 |   以上的资源为Beta的，已经经过整理。
	 |   以后添加的部分放到下面，待以后统一整理。
	 |
	 |
	 △
	 */

	public static final String RecordsetStatisticUnsupprotFieldType = "Recordset_StatisticUnsupprotFieldType";

	// 批量更新操作必须在begin之前
	public static final String SetMaxRecordCountMustBeforeBegin = "SetMaxRecordCount_Must_BeforeBegin";

	public static final String GeoLegendItemNameMustInItems = "GeoLegend_ItemNameMustInItems";

	public static final String GeoLegendWorkspaceCANNOTBENULL = "GeoLegend_WorkspaceCANNOTBENULL";

	public static final String GeoLegendMapNameMustInWorkspace = "GeoLegend_MapNameMustInWorkspace";

	// 毫米
	public static final String MILIMETER = "MiliMeter";

	// 厘米
	public static final String CENTIMETER = "CentiMeter";

	// 分米
	public static final String DECIMETER = "DeciMeter";

	// 米
	public static final String METER = "Meter";

	// 千米
	public static final String KILOMETER = "KiloMeter";

	// 英寸
	public static final String INCH = "Inch";

	// 英尺
	public static final String FOOT = "Foot";

	// 码
	public static final String YARD = "Yard";

	// 英里
	public static final String MILE = "Mile";

	// 秒
	public static final String SECOND = "Second";

	// 分
	public static final String MINUTE = "Minute";

	// 度
	public static final String DEGREE = "Degree";

	// 弧度
	public static final String RADIAN = "Radian";

	// 不能设置smid
	public static final String MAPCanNotContainSMID = "MAPCannotContainSMID";

	/*------------------------>
	 |
	 |
	 |   以上的资源为正式版前的，已经经过整理。
	 |   以后添加的部分放到下面，待以后统一整理。
	 |
	 |
	 △
	 */
	
	// 打开或创建目标数据源失败
	public static final String ImportSettingFailToOpenOrCreateTargetDatasource = "ImportSetting_FailToOpenOrCreateTargetDatasource";

	//ImportDataInfoMIF指定字段名不存在目标数据字段集中
	public static final String ImportDataInfoMIFFieldNameIsNotExist = "ImportDataInfoMIF_FieldNameIsNotExist";

	//ImportDataInfoTAB指定字段名不存在目标数据字段集中
	public static final String ImportDataInfoTABFieldNameIsNotExist = "ImportDataInfoTAB_FieldNameIsNotExist";

	//ImportDataInfoMIF指定字段名已经存在目标数据字段集中
	public static final String ImportDataInfoMIFFieldNameIsAlreadyExist = "ImportDataInfoMIF_FieldNameIsAlreadyExist";

	//ImportDataInfoTAB指定字段名已经存在目标数据字段集中
	public static final String ImportDataInfoTABFieldNameIsAlreadyExist = "ImportDataInfoTAB_FieldNameIsAlreadyExist";

	// 文件的父路径不正确
	public static final String ImportSettingTheParentPathIsNotValide = "ImportSetting_TheParentPathIsNotValide";

	//ImportSettingSHP不支持此操作
	public static final String ImportSettingSHPUnsupportedOperation = "ImportSettingSHP_UnsupportedOperation";

	//	ImportDataInfoSHP指定字段名已经存在目标数据字段集中
	public static final String ImportDataInfoSHPFieldNameIsAlreadyExist = "ImportDataInfoSHP_FieldNameIsAlreadyExist";
    //ImportDataInfoConverage指定字段名不存在目标数据字段集中
	public static final String ImportDataInfoCoverageFieldNameIsNotExist = "ImportDataInfoCoverage_FieldNameIsNotExist";
	//ImportDataInfoSHP指定字段名不存在目标数据字段集中
	public static final String ImportDataInfoSHPFieldNameIsNotExist = "ImportDataInfoSHP_FieldNameIsNotExist";
	//ImportDataInfoDWG指定字段名不存在目标数据字段集中
	public static final String ImportDataInfoDWGFieldNameIsNotExist = "ImportDataInfoDWG_FieldNameIsNotExist";
	//ImportDataInfoDXF指定字段名不存在目标数据字段集中
	public static final String ImportDataInfoDXFFieldNameIsNotExist = "ImportDataInfoDXF_FieldNameIsNotExist";
	
	//ImportDataInfo指定字段名不存在目标数据字段集中
	public static final String ImportDataInfoFieldNameIsNotExist = "ImportDataInfo_FieldNameIsNotExist";
	
	//ImportDataInfo指定字段名已经存在目标数据字段集中
	public static final String ImportDataInfoFieldNameIsAlreadyExist = "ImportDataInfo_FieldNameIsNotExist";
	
	//ImportDataInfoSDEVector指定字段名已经存在目标数据字段集中
	public static final String ImportDataInfoSDEVectorFieldNameIsAlreadyExist = "ImportDataInfoDXF_FieldNameIsNotExist";
	//ImportDataInfoSDEVector指定字段名不存在目标数据字段集中
	public static final String ImportDataInfoSDEVectorFieldNameIsNotExist = "ImportDataInfoDXF_FieldNameIsNotExist";
	
	//ImportDataInfoGDBFVector指定字段名已经存在目标数据字段集中
	public static final String ImportDataInfoGDBFVectorFieldNameIsAlreadyExist = "ImportDataInfoDXF_FieldNameIsNotExist";
	//ImportDataInfoGDBFVector指定字段名不存在目标数据字段集中
	public static final String ImportDataInfoGDBFVectorFieldNameIsNotExist = "ImportDataInfoDXF_FieldNameIsNotExist";
	
	//ImportDataInfoGML指定字段名不存在目标数据字段集中
	public static final String ImportDataInfoGMLFieldNameIsNotExist = "ImportDataInfoGML_FieldNameIsNotExist";
	//缺少fme方式的许可
	public static final String Fme_Lack_FmeLicense = "Fme_Lack_FmeLicense";
	//fme方式不支持的文件类型
	public static final String Fme_UnSupportedFileType = "Fme_UnSupportedFileType";
	//非fme方式下不支持的文件类型
	public static final String UnFme_UnSupportedFileType ="UnFme_UnSupportFileType";
	
	
	// 无效的参数值
	public static final String InvalidArgumentValue = "Invalid_ArgumentValue";
	
	//ImportDataInfoSHP指定字段名不存在目标数据字段集中
	public static final String ImportDataInfoE00FieldNameIsNotExist = "ImportDataInfoE00_FieldNameIsNotExist";
	//不能设置FileType
	public static final String SetTargetFileTypeIsNotAllowed = "SetTargetFileTypeIsNotAllowed";
	
	//JPG压缩率的范围应当是[0,100]
	public static final String ExportSettingJPGTheCompressionShouldBetweenZeroAndHundred = "ExportSettingJPG_TheCompressionShouldBetweenZeroAndHundred";
	
	//Grid不支持导入为多波段
	public static final String ImportSetting_GridCannotSuportMultiBandImportMode = "ImportSetting_GridCannotSuportMultiBandImportMode";
	
	
	public static final String RecordsetValuesLengthShouldEqualsFieldCount = "Recordset_ValuesLengthShouldEqualsFieldCount";

	public static final String DatasourceRefreshUnsupportEngingType = "Datasource_RefreshUnsupportEngingType";

	public static final String DatasetVectorShouldBeClosedBeforeBuildSpatialIndex = "DatasetVector_ShouldBeClosedBeforeBuildSpatialIndex";

	public static final String DatasetImageShouldBeClosedBeforeBuildPyramid = "DatasetImage_ShouldBeClosedBeforeBuildPyramid";
	
	public static final String DatasetGridShouldBeClosedBeforeBuildPyramid = "DatasetGrid_ShouldBeClosedBeforeBuildPyramid";

	// //////////////////////////////////////////////////////

	public static final String MarkerSymbolIDShouldNotBeNeagtive = "MarkerSymbolID_ShouldNotBeNeagtive";

	public static final String LineSymbolIDShouldNotBeNeagtive = "LineSymbolID_ShouldNotBeNeagtive";

	public static final String Marker3DRotationXShouldNotBeNeagtive = "Marker3DRotationX_ShouldNotBeNeagtive";

	public static final String Marker3DRotationYShouldNotBeNeagtive = "Marker3DRotationY_ShouldNotBeNeagtive";

	public static final String Marker3DRotationZShouldNotBeNeagtive = "Marker3DRotationZ_ShouldNotBeNeagtive";

	public static final String Marker3DScaleXShouldNotBeNeagtive = "Marker3DScaleX_ShouldNotBeNeagtive";

	public static final String Marker3DScaleYShouldNotBeNeagtive = "Marker3DScaleY_ShouldNotBeNeagtive";

	public static final String Marker3DScaleZShouldNotBeNeagtive = "Maker3DScaleZ_ShouldNotBeNeagtive";

	public static final String LineSymbolSegmentCountShouldNotBeNeagtive = "LineSymbolSegmentCount_ShouldNotBeNeagtive";

	public static final String TopFillSymbolIDShouldNotBeNeagtive = "TopFillSymbolID_ShouldNotBeNeagtive";

	public static final String LineWidthShouldNotBeNegative = "LineWidth_ShouldNotBeNegative";

	public static final String TransformationControlPointsNull = "Transformation_ControlPointsNull";

	public static final String TransformationControlPointsShouldNotSmallerThanOne = "Transformation_ControlPointsShouldNotSmallerThanOne";

	public static final String TransformationControlPointsShouldNotBeZero = "Transformation_ControlPointsShouldNotBeZero";

	public static final String TransformationOriginalAndTargetControlPointsCountMustEqual = "Transformation_OriginalAndTargetControlPointsCountMustEqual";

	public static final String TransformationRectModeNeedLeastTwoControlPoints = "Transformation_RectModeNeedLeastTwoControlPoints";

	public static final String TransformationLinearModeNeedLeastFourControlPoints = "Transformation_LinearModeNeedLeastFourControlPoints";

	public static final String TransformationSquareModeNeedLeastSevenControlPoints = "Transformation_SquareModeNeedLeastSevenControlPoints";

	public static final String ArgumentShouldBePositive = "Argument_ShouldBePositive";

	public static final String MeshesRemoveRangeCountInvalid = "Meshes_RemoveRange_Count_Invalid";

	public static final String ValueShouldNotBeNeagtive = "ValueShouldNotBeNeagtive"; 
	
	public static final String TubeSidesCountShouldNotBeNeagtive ="TubeSidesCountShouldNotBeNeagtive";
	
	/**
	 * 正式许可
	 */
	public static final String FormalLicense = "Formal_License";
	/**
	 * 试用许可
	 */
	public static final String TrialLicense = "Trial_License";
	
	/**
	 * 有效许可
	 */
	public static final String ValidLicense = "Valid_License";
	/**
	 * 无效许可
	 */
	public static final String InvaliLicense = "Invalid_License";
	
	/**
	 *许可不存在
	 */
	public static final String LicenseNotExsit = "Licnse_Not_Exsit";
	
	/**
	 *许可已激活
	 */
	public static final String LicenseActivated = "Licnse_Is_Activated";
	
	/**
	 *许可未激活
	 */
	public static final String LicenseNotActivated = "Licnse_Not_Activated";
}
