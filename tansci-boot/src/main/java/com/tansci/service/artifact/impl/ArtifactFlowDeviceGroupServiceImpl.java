package com.tansci.service.artifact.impl;

import com.tansci.model.artifact.ArtifactFlowDeviceGroup;
import com.tansci.service.artifact.IArtifactFlowDeviceGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 工艺流连线设备组管理Service实现
 */
@Slf4j
@Service
public class ArtifactFlowDeviceGroupServiceImpl implements IArtifactFlowDeviceGroupService {
    
    private final Map<String, ArtifactFlowDeviceGroup> groupStore = new ConcurrentHashMap<>();
    
    @Override
    public ArtifactFlowDeviceGroup saveFlowDeviceGroup(ArtifactFlowDeviceGroup group) {
        if (group.getId() == null) {
            group.setId(UUID.randomUUID().toString());
            group.setCreateTime(System.currentTimeMillis());
        }
        group.setUpdateTime(System.currentTimeMillis());
        groupStore.put(group.getId(), group);
        log.info("保存连线设备组: artifactId={}, flowId={}", group.getArtifactId(), group.getFlowId());
        return group;
    }
    
    @Override
    public void deleteFlowDeviceGroup(String groupId) {
        groupStore.remove(groupId);
        log.info("删除连线设备组: {}", groupId);
    }
    
    @Override
    public ArtifactFlowDeviceGroup getFlowDeviceGroup(String groupId) {
        return groupStore.get(groupId);
    }
    
    @Override
    public ArtifactFlowDeviceGroup getFlowDeviceGroupByArtifactAndFlowId(String artifactId, String flowId) {
        return groupStore.values().stream()
            .filter(g -> artifactId.equals(g.getArtifactId()) && flowId.equals(g.getFlowId()))
            .findFirst()
            .orElse(null);
    }
    
    @Override
    public List<ArtifactFlowDeviceGroup> listFlowDeviceGroupsByArtifact(String artifactId) {
        return groupStore.values().stream()
            .filter(g -> artifactId.equals(g.getArtifactId()))
            .toList();
    }
    
    @Override
    public List<ArtifactFlowDeviceGroup> listFlowDeviceGroupsByDeviceType(String artifactId, String deviceType) {
        return groupStore.values().stream()
            .filter(g -> artifactId.equals(g.getArtifactId()) && deviceType.equals(g.getDeviceType()))
            .toList();
    }
    
    @Override
    public void deleteFlowDeviceGroupsByArtifact(String artifactId) {
        groupStore.entrySet().removeIf(e -> artifactId.equals(e.getValue().getArtifactId()));
        log.info("批量删除流程的连线设备组: {}", artifactId);
    }
}
