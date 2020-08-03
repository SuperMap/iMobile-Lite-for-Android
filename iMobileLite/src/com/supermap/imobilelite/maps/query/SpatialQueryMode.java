package com.supermap.imobilelite.maps.query;

/**
 * <p>
 * 空间查询模式枚举，定义空间查询操作模式常量。
 * </p>
 * 
 * <p>
 * 空间查询是通过几何对象之间的空间位置关系来构建过滤条件的一种查询方式。例如：通过空间查询可以找到被包含在面中的空间对象，相离或者相邻的空间对象等。
 * </p>
 * <p>
 * 注意：当前版本提供对点、线、面、网络和文本类型数据的空间查询，其中文本类型仅支持 Intersect 和 Contain 两种空间查询模式，而且只能作为被搜索对象不能作为搜索对象。
 * </p>
 * 
 * @author ${Author}
 * @version ${Version}
 */
public enum SpatialQueryMode {

    /**
     * <p>
     * 无空间查询。
     * </p>
     */
    NONE,

    /**
     * <p>
     * 重合空间查询模式。
     * </p>
     * <p>
     * 返回被搜索图层中与搜索对象完全重合的对象。包括对象类型和坐标。
     * </p>
     * <p>
     * 注意：搜索对象与被搜索对象的类型必须相同；且两个对象的交集不为空，搜索对象的边界及内部分别和被搜索对象的外部交集为空。
     * </p>
     * <p>
     * 该关系适合的对象类型：搜索对象：点、线、面；被搜索对象：点、线、面。
     * </p>
     * <p>
     * 如图所示为重合查询的示例：
     * </p>
     * <p style="text-align:center">
     * <img src=../../../../../resources/services_components_commontypes/SpatialQueryMode/SQIdentical.png>
     * </p>
     * <p>
     * 其中搜索对象用绿色表示，被搜索图层上的对象用黑色表示，结果记录集几何对象用红色表示。
     * </P>
     * <p>
     * 注意：
     * </p>
     * <ol>
     * <li>
     * 对于完全重合的两个面对象，如果这两个面对象的起始点和终止点不相同，那么这两个面对象也不符合 Identity 空间查询模式。
     * </li>
     * <li>
     * 对于线和线对象，如果两个线对象满足重合条件，但是其中一个线对象存在自交叠的情况，那么这两个线对象也不符合 Identity 模式；如果两个线对象满足重合条件，并且两个对象都存在自交叠情况，而且自交叠的状态完全相同，则这两个线对象也符合 Identity 模式。
     * </li>
     * </ol>
     */
    IDENTITY,

    /**
     * <p>
     * 分离空间查询模式。
     * </p>
     * <p>
     * 该关系适合的对象类型：搜索对象：点、线、面；被搜索对象：点、线、面。
     * </p>
     * <p>
     * 如图所示，分离空间查询的示例：
     * </p>
     * <p style="text-align:center">
     * <img src=../../../../../resources/services_components_commontypes/SpatialQueryMode/SQDisjoint.png>
     * </p>
     * <p>
     * 其中搜索对象用绿色表示，被搜索图层上的对象用黑色表示，结果记录集几何对象用红色表示。
     * </p>
     */
    DISJOINT,

    /**
     * <p>
     * 相交空间查询模式。
     * </p>
     * <p>
     * 返回与搜索对象相交的所有对象。
     * </p>
     * <p>
     * 注意：如果搜索对象是面，返回全部或部分被搜索对象包含的对象以及全部或部分包含搜索对象的对象；如果搜索对象不是面，返回全部或部分包含搜索对象的对象。
     * </p>
     * <p>
     * 该关系适合的对象类型：搜索对象：点、线、面；被搜索对象：点、线、面。
     * </p>
     * <p>
     * 如图所示，相交空间查询的示例：
     * </p>
     * <p style="text-align:center">
     * <img src=../../../../../resources/services_components_commontypes/SpatialQueryMode/SQIntersect.png>
     * </p>
     * <p>
     * 其中搜索对象用绿色表示，被搜索图层上的对象用黑色表示，结果记录集几何对象用红色表示。
     * </p>
     */
    INTERSECT,

    /**
     * <p>
     * 邻接空间查询模式。
     * </p>
     * <p>
     * 返回被搜索图层中其边界与搜索对象边界相触的对象。
     * </p>
     * <p>
     * 注意：搜索对象和被搜索对象的内部交集为空。
     * </p>
     * <p>
     * 该关系不适合的对象类型为：点查询点的空间关系。
     * </p>
     * <p>
     * 如图所示，邻接空间查询的示例：
     * </p>
     * <p style="text-align:center">
     * <img src=../../../../../resources/services_components_commontypes/SpatialQueryMode/SQTouch.png>
     * </p>
     * <p>
     * 其中搜索对象用绿色表示，被搜索图层上的对象用黑色表示，结果记录集几何对象用红色表示。
     * </p>
     */
    TOUCH,

    /**
     * <p>
     * 叠加空间查询模式。
     * </p>
     * <p>
     * 返回被搜索图层中与搜索对象部分重叠的对象。
     * </p>
     * <p>
     * 该关系适合的对象类型为：线/线，面/面。其中，两个几何对象的维数必须一致，而且他们交集的维数也应该和几何对象的维数一样。
     * </p>
     * <p>
     * 注意：点与任何一种几何对象都不存在部分重叠的情况。
     * </p>
     * <p>
     * 如图所示为叠加查询的图示：
     * </p>
     * <p style="text-align:center">
     * <img src=../../../../../resources/services_components_commontypes/SpatialQueryMode/SQOverlap.png>
     * </p>
     * <p>
     * 其中搜索对象用绿色表示，被搜索图层上的对象用黑色表示，结果记录集几何对象用红色表示。
     * </p>
     */
    OVERLAP,

    /**
     * <p>
     * 交叉空间查询模式。
     * </p>
     * 
     * <p>
     * 返回被搜索图层中与搜索对象（线）相交的所有对象（线或面）。
     * </p>
     * <p>
     * 注意：搜索对象和被搜索对象内部的交集不能为空；参与交叉（Cross）关系运算的两个对象必须有一个是线对象。
     * </p>
     * <p>
     * 该关系适合的对象类型：搜索对象：线；被搜索对象：线、面。
     * </p>
     * <p>
     * 如图所示为交叉查询示例。
     * </p>
     * <p style="text-align:center">
     * <img src=../../../../../resources/services_components_commontypes/SpatialQueryMode/SQCross.png>
     * </p>
     * <p>
     * 其中搜索对象用绿色表示，被搜索图层上的对象用黑色表示，结果记录集几何对象用红色表示。
     * </p>
     */
    CROSS,

    /**
     * <p>
     * 被包含空间查询模式。
     * </p>
     * <p>
     * 返回被搜索图层中完全包含搜索对象的对象。如果返回的对象是面，其必须全部包含（包括边接触）搜索对象；如果返回的对象是线，其必须完全包含搜索对象；如果返回的对象是点，其必须与搜索对象重合。该类型与包含（Contain）的查询模式正好相反。
     * </p>
     * <p>
     * 该关系适合的对象类型：搜索对象：点、线、面；被搜索对象：点、线、面。
     * </p>
     * <p>
     * 注意：线查询点，面查询线或面查询点都不存在被包含的情况。
     * </p>
     * <p>
     * 如图所示为点查询面的示例：
     * </p>
     * <p style="text-align:center">
     * <img src=../../../../../resources/services_components_commontypes/SpatialQueryMode/SQWithin.png>
     * </p>
     * <p>
     * 其中搜索对象用绿色表示，被搜索图层上的对象用黑色表示，结果记录集几何对象用红色表示。
     * </p>
     */
    WITHIN,

    /**
     * <p>
     * 包含空间查询模式。
     * </p>
     * <p>
     * 返回被搜索图层中完全被搜索对象包含的对象。
     * </p>
     * <p>
     * 注意：搜索对象和被搜索对象的边界交集可以不为空；点查线/点查面/线查面，不存在包含情况。
     * </p>
     * <p>
     * 该关系适合的对象类型：搜索对象：点、线、面；被搜索对象：点、线、面。
     * </p>
     * <p>
     * 如图所示，包含空间查询的图示：
     * </p>
     * <p style="text-align:center">
     * <img src=../../../../../resources/services_components_commontypes/SpatialQueryMode/SQContain.png>
     * </p>
     * <p>
     * 其中搜索对象用绿色表示，被搜索图层上的对象用黑色表示，结果记录集几何对象用红色表示。
     * </p>
     */
    CONTAIN;
}