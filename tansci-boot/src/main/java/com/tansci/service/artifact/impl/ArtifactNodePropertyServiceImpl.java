package com.tansci.service.artifact.impl;

import com.tansci.model.artifact.ArtifactNodeProperty;
import com.tansci.service.artifact.IArtifactNodePropertyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 工艺节点属性管理Service实现
 */
@Slf4j
@Service
public class ArtifactNodePropertyServiceImpl implements IArtifactNodePropertyService {
    
    private final Map<String, ArtifactNodeProperty> propertyStore = new ConcurrentHashMap<>();
    
    @Override
    public ArtifactNodeProperty saveNodeProperty(ArtifactNodeProperty property) {
        if (property.getId() == null) {
            property.setId(UUID.randomUUID().toString());
            property.setCreateTime(System.currentTimeMillis());
        }
        property.setUpdateTime(System.currentTimeMillis());
        propertyStore.put(property.getId(), property);
        log.info("保存节点属性: artifactId={}, nodeId={}", property.getArtifactId(), property.getNodeId());
        return property;
    }
    
    @Override
    public void deleteNodeProperty(String propertyId) {
        propertyStore.remove(propertyId);
        log.info("删除节点属性: {}", propertyId);
    }
    
    @Override
    public ArtifactNodeProperty getNodeProperty(String propertyId) {
        return propertyStore.get(propertyId);
    }
    
    @Override
    public ArtifactNodeProperty getNodePropertyByArtifactAndNodeId(String artifactId, String nodeId) {
        return propertyStore.values().stream()
            .filter(p -> artifactId.equals(p.getArtifactId()) && nodeId.equals(p.getNodeId()))
            .findFirst()
            .orElse(null);
    }
    
    @Override
    public List<ArtifactNodeProperty> listNodePropertiesByArtifact(String artifactId) {
        return propertyStore.values().stream()
            .filter(p -> artifactId.equals(p.getArtifactId()))
            .toList();
    }
    
    @Override
    public void deleteNodePropertiesByArtifact(String artifactId) {
        propertyStore.entrySet().removeIf(e -> artifactId.equals(e.getValue().getArtifactId()));
        log.info("批量删除流程的节点属性: {}", artifactId);
    }
}
