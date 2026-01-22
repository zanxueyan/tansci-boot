package com.tansci.service.artifact;

import com.tansci.model.artifact.ArtifactNodeProperty;
import java.util.List;

/**
 * 工艺节点属性管理Service接口
 */
public interface IArtifactNodePropertyService {
    
    /**
     * 创建或更新节点属性
     */
    ArtifactNodeProperty saveNodeProperty(ArtifactNodeProperty property);
    
    /**
     * 删除节点属性
     */
    void deleteNodeProperty(String propertyId);
    
    /**
     * 查询节点属性
     */
    ArtifactNodeProperty getNodeProperty(String propertyId);
    
    /**
     * 根据流程和节点ID查询节点属性
     */
    ArtifactNodeProperty getNodePropertyByArtifactAndNodeId(String artifactId, String nodeId);
    
    /**
     * 查询流程下所有节点属性
     */
    List<ArtifactNodeProperty> listNodePropertiesByArtifact(String artifactId);
    
    /**
     * 批量删除流程下的节点属性
     */
    void deleteNodePropertiesByArtifact(String artifactId);
}
