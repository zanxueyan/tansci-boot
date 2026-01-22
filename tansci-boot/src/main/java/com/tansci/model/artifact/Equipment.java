package com.tansci.model.artifact;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 生产设备模型
 * 代表AGV、堆垛机等生产设备
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {
    /** 设备ID */
    private String id;

    /** 设备名称 */
    private String name;

    /** 设备类型 (AGV/堆垛机/其他) */
    private String type;

    /** 关联的工艺流程ID */
    private String artifactId;

    /** 设备描述 */
    private String description;

    /** 创建时间 */
    private Long createTime;

    /** 更新时间 */
    private Long updateTime;
}
