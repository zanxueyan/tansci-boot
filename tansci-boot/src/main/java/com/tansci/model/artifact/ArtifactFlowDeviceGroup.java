package com.tansci.model.artifact;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * 工艺流连线设备组模型
 * 存储BPMN流程中连线对应的执行设备组
 * 例如：某条连线由AGV-01、AGV-02执行
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtifactFlowDeviceGroup {
    /** 配置ID */
    private String id;

    /** 流程ID */
    private String artifactId;

    /** 连线ID (BPMN中的connectionId) */
    private String flowId;

    /** 源节点ID */
    private String sourceNodeId;

    /** 目标节点ID */
    private String targetNodeId;

    /** 执行设备组名称 */
    private String deviceGroupName;

    /** 执行设备名称列表 (JSON格式，如["AGV-01", "AGV-02"]) */
    private String deviceList;

    /** 设备组类型 (agv/stacker/other) */
    private String deviceType;

    /** 优先级 (用于多个设备组时的优先级选择) */
    private Integer priority;

    /** 路由策略 (round_robin/load_balance/fixed) */
    private String routingStrategy;

    /** 创建时间 */
    private Long createTime;

    /** 更新时间 */
    private Long updateTime;
}
