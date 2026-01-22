package com.tansci.model.artifact;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工艺流程模型
 * 保存BPMN流程定义和元数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessArtifact {
    /** 流程ID */
    private String id;

    /** 流程名称 */
    private String name;

    /** BPMN格式的流程定义XML */
    private String bpmnXml;

    /** 流程描述 */
    private String description;

    /** 版本号 */
    private Integer version;

    /** 是否为最新版本 */
    private Boolean isLatest;

    /** 创建时间 */
    private Long createTime;

    /** 更新时间 */
    private Long updateTime;
}
