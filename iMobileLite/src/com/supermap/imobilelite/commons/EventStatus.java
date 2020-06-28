package com.supermap.imobilelite.commons;
/**
 * <p>
 * 事件响应状态枚举类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public enum EventStatus {
    /**
     * <p>
     * 执行完成。
     * </p>
     */
    PROCESS_COMPLETE("Process complete.", 1),
    /**
     * <p>
     * 执行失败。
     * </p>
     */
    PROCESS_FAILED("Process failed.", 0);
    private String description;
    private int value;
    
    private EventStatus(String description, int value) {
        this.description = description;
        this.value = value;
    }
    /**
     * <p>
     * 返回描述信息。
     * </p>
     * @return
     */
    public String getDescription() {
        return this.description;
    }
    /**
     * <p>
     * 设置描述信息。
     * </p>
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * <p>
     * 返回值。
     * </p>
     */
    public int getValue() {
        return this.value;
    }
    /**
     * <p>
     * 将返回值转换为字符串。
     * </p>
     */
    public String toString() {
        if (this.value == 1) {
            return "PROCESS_COMPLETE:" + description;
        } else {
            return "LAYER_REFRESH:" + description;
        }
    }
}
