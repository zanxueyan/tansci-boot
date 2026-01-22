package com.tansci.service.artifact;

import com.tansci.model.artifact.ProcessArtifact;
import java.util.List;

/**
 * 工艺流程管理Service接口
 */
public interface IProcessArtifactService {
    
    /**
     * 创建工艺流程
     */
    ProcessArtifact createArtifact(ProcessArtifact artifact);
    
    /**
     * 更新工艺流程
     */
    ProcessArtifact updateArtifact(ProcessArtifact artifact);
    
    /**
     * 删除工艺流程
     */
    void deleteArtifact(String artifactId);
    
    /**
     * 查询工艺流程详情
     */
    ProcessArtifact getArtifact(String artifactId);
    
    /**
     * 查询所有工艺流程
     */
    List<ProcessArtifact> listArtifacts();
    
    /**
     * 保存BPMN流程定义
     */
    ProcessArtifact saveBpmnXml(String artifactId, String bpmnXml);
    
    /**
     * 获取BPMN流程定义
     */
    String getBpmnXml(String artifactId);
}
