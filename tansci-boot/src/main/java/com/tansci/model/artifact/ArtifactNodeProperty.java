package com.tansci.model.artifact;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工艺节点属性模型
 * 存储BPMN流程中每个节点的工艺配置
 * 例如：装载对接方式（固定式/升降式/传送式）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtifactNodeProperty {
    /** 属性ID */
    private String id;

    /** 流程ID */
    private String artifactId;

    /** 节点ID (BPMN中的elementId) */
    private String nodeId;

    /** 节点名称 */
    private String nodeName;

    /** 工艺名称 */
    private String artifactName;

    /** 装载对接方式
     * 1: 固定式
     * 2: 升降式
     * 3: 传送式
     */
    private String loadingMode;

    /** 装载模式描述 */
    private String loadingModeDesc;

    /** 装载参数 (JSON格式，用于存储特定装载模式的参数) */
    private String loadingParams;

    /** 节点执行超时时间(毫秒) */
    private Long executionTimeout;

    /** 重试次数 */
    private Integer retryCount;

    /** 创建时间 */
    private Long createTime;

    /** 更新时间 */
    private Long updateTime;
}
