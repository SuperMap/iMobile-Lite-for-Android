package com.supermap.imobilelite.maps.query;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.supermap.services.components.commontypes.Feature;
import com.supermap.services.components.commontypes.util.ResourceManager;

/**
 * <p>
 * 记录集。用于存放空间对象信息的记录。
 * </p>
 * <p>
 * 数据集可以看成是一个空间信息和属性信息一体化存储的表，记录集是从其中取出用来操作的一个子集。记录集中的一条记录，即一行，对应着一个要素，包含该要素的空间几何信息和属性信息。记录集中的一列对应一个字段的信息。
 * </p>
 * 
 * @see DatasourceInfo
 * @see DatasetInfo
 * @see FieldInfo
 * @see QueryResult
 * @author ${Author}
 * @version ${Version}
 */
public class Recordset implements Serializable {
    private static final long serialVersionUID = 8002795831976434292L;
    /**
     * <p>
     * 记录集中所有字段的名称。
     * </p>
     * <p>
     * 字段包括：
     * </p>
     * <ul>
     * <li>系统字段，字段名称以“SM”开头的字段。</li>
     * <li>自定义字段，字段名称以非“SM”开头的字段。</li>
     * </ul>
     * 
     * @see FieldInfo
     */
    public String[] fields;

    /**
     * <p>
     * 记录集中所有字段的别名。
     * </p>
     * 
     * @see FieldInfo
     */
    public String[] fieldCaptions;

    /**
     * <p>
     * 记录集中所有的地物要素。
     * </p>
     * <p>
     * <b>注意：</b>通过 {@link Recordset#features recordset.features} 获得的要素数组，其每一个要素对象不包含字段名称（{@link Feature#fieldNames}）。如果获取记录的要素并且要得到要素的字段名，可以使用 {@link #getFeature(int)} 方法或者 {@link #getFeatures()} 方法。
     * </p>
     */
    public Feature[] features;

    /**
     * <p>
     * 记录集中所有的字段类型。
     * </p>
     */
    public FieldType[] fieldTypes;

    /**
     * <p>
     * 数据集的名称，是数据集的唯一标识。
     * </p>
     */
    public String datasetName;

    /**
     * 构造函数。
     */
    public Recordset() {
    }

    /**
     * 根据一个 Recordset 对象构建一个新的 Recordset 实例对象。
     * 
     * @param recordset 记录集对象。该参数不能为空。
     */
    public Recordset(Recordset recordset) {
        if (recordset != null) {
            if (recordset.fields != null && recordset.fields.length > 0) {
                this.fields = new String[recordset.fields.length];
                System.arraycopy(recordset.fields, 0, this.fields, 0, recordset.fields.length);
            }
            if (recordset.fieldCaptions != null && recordset.fieldCaptions.length > 0) {
                this.fieldCaptions = new String[recordset.fieldCaptions.length];
                System.arraycopy(recordset.fieldCaptions, 0, this.fieldCaptions, 0, recordset.fieldCaptions.length);
            }
            if (recordset.features != null && recordset.features.length > 0) {
                this.features = new Feature[recordset.features.length];
                System.arraycopy(recordset.features, 0, this.features, 0, recordset.features.length);
            }
            if (recordset.fieldTypes != null && recordset.fieldTypes.length > 0) {
                this.fieldTypes = new FieldType[recordset.fieldTypes.length];
                System.arraycopy(recordset.fieldTypes, 0, this.fieldTypes, 0, recordset.fieldTypes.length);
            }
            this.datasetName = recordset.datasetName;
        }
    }

    /**
     * <p>
     * 获取记录集中指定的地物要素。
     * </p>
     * 
     * @param index {@link #features} 数组的索引号。
     * @return 地物要素。
     */
    public Feature getFeature(int index) {
        if ((index < features.length) && (index >= 0)) {
            Feature feature = new Feature(this.features[index]);
            feature.fieldNames = this.fields;
            return feature;
        } else {
            return null;
        }
    }

    /**
     * 获取记录集中所有的地物要素。
     * 
     * @return 地物要素数组。
     */
    public Feature[] getFeatures() {
        if (this.features != null) {
            int length = this.features.length;
            Feature[] retFeatures = new Feature[length];
            System.arraycopy(this.features, 0, retFeatures, 0, length);
            for (int i = 0; i < length; i++) {
                retFeatures[i].fieldNames = this.fields;
            }
            return retFeatures;
        } else {
            return new Feature[0];
        }
    }

    /**
     * <p>
     * 获取记录集对象的哈希码。
     * </p>
     * 
     * @return 哈希码值。
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(203, 205).append(fields).append(fieldCaptions).append(features).append(datasetName);
        if (fieldTypes != null && fieldTypes.length > 0) {
            int length = fieldTypes.length;
            for (int i = 0; i < length; i++) {
                if (fieldTypes[i] == null) {
                    hashCodeBuilder.append("null");
                } else {
                    hashCodeBuilder.append(fieldTypes[i].name());
                }
            }
        }
        return hashCodeBuilder.toHashCode();
    }

    /**
     * <p>
     * 比较指定对象与当前 {@link Recordset} 对象是否相等。
     * </p>
     * 
     * @param obj 与当前 {@link Recordset} 对象进行比较的对象。
     * @return 如果指定对象与当前记录集对象相等，则返回 true，否则，返回 false。
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Recordset)) {
            return false;
        }
        Recordset rhs = (Recordset) obj;
        return new EqualsBuilder().append(fields, rhs.fields).append(fieldCaptions, rhs.fieldCaptions).append(features, rhs.features)
                .append(fieldTypes, rhs.fieldTypes).append(datasetName, rhs.datasetName).isEquals();
    }
}
