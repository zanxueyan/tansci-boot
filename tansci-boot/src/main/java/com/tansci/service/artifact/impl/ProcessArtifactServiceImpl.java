package com.tansci.service.artifact.impl;

import com.tansci.model.artifact.ProcessArtifact;
import com.tansci.service.artifact.IProcessArtifactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 工艺流程管理Service实现
 */
@Slf4j
@Service
public class ProcessArtifactServiceImpl implements IProcessArtifactService {
    
    private final Map<String, ProcessArtifact> artifactStore = new ConcurrentHashMap<>();
    
    @Override
    public ProcessArtifact createArtifact(ProcessArtifact artifact) {
        artifact.setId(UUID.randomUUID().toString());
        artifact.setVersion(1);
        artifact.setIsLatest(true);
        artifact.setCreateTime(System.currentTimeMillis());
        artifact.setUpdateTime(System.currentTimeMillis());
        artifactStore.put(artifact.getId(), artifact);
        log.info("创建工艺流程: {}", artifact.getId());
        return artifact;
    }
    
    @Override
    public ProcessArtifact updateArtifact(ProcessArtifact artifact) {
        artifact.setUpdateTime(System.currentTimeMillis());
        artifactStore.put(artifact.getId(), artifact);
        log.info("更新工艺流程: {}", artifact.getId());
        return artifact;
    }
    
    @Override
    public void deleteArtifact(String artifactId) {
        artifactStore.remove(artifactId);
        log.info("删除工艺流程: {}", artifactId);
    }
    
    @Override
    public ProcessArtifact getArtifact(String artifactId) {
        return artifactStore.get(artifactId);
    }
    
    @Override
    public List<ProcessArtifact> listArtifacts() {
        return new ArrayList<>(artifactStore.values());
    }
    
    @Override
    public ProcessArtifact saveBpmnXml(String artifactId, String bpmnXml) {
        ProcessArtifact artifact = artifactStore.get(artifactId);
        if (artifact == null) {
            artifact = new ProcessArtifact();
            artifact.setId(artifactId);
            artifact.setVersion(1);
            artifact.setIsLatest(true);
            artifact.setCreateTime(System.currentTimeMillis());
        }
        artifact.setBpmnXml(bpmnXml);
        artifact.setUpdateTime(System.currentTimeMillis());
        artifactStore.put(artifactId, artifact);
        log.info("保存BPMN流程定义: {}", artifactId);
        return artifact;
    }
    
    @Override
    public String getBpmnXml(String artifactId) {
        ProcessArtifact artifact = artifactStore.get(artifactId);
        return artifact != null ? artifact.getBpmnXml() : null;
    }
}
