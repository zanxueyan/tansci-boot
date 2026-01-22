package com.tansci.service.artifact;

import com.tansci.model.artifact.ArtifactFlowDeviceGroup;
import java.util.List;

/**
 * 工艺流连线设备组管理Service接口
 */
public interface IArtifactFlowDeviceGroupService {
    
    /**
     * 创建或更新连线设备组配置
     */
    ArtifactFlowDeviceGroup saveFlowDeviceGroup(ArtifactFlowDeviceGroup group);
    
    /**
     * 删除连线设备组配置
     */
    void deleteFlowDeviceGroup(String groupId);
    
    /**
     * 查询连线设备组配置
     */
    ArtifactFlowDeviceGroup getFlowDeviceGroup(String groupId);
    
    /**
     * 根据流程和连线ID查询设备组配置
     */
    ArtifactFlowDeviceGroup getFlowDeviceGroupByArtifactAndFlowId(String artifactId, String flowId);
    
    /**
     * 查询流程下所有连线设备组配置
     */
    List<ArtifactFlowDeviceGroup> listFlowDeviceGroupsByArtifact(String artifactId);
    
    /**
     * 根据设备类型查询连线配置
     */
    List<ArtifactFlowDeviceGroup> listFlowDeviceGroupsByDeviceType(String artifactId, String deviceType);
    
    /**
     * 批量删除流程下的连线设备组配置
     */
    void deleteFlowDeviceGroupsByArtifact(String artifactId);
}
