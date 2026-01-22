package com.tansci.controller.artifact;

import com.tansci.model.artifact.ProcessArtifact;
import com.tansci.service.artifact.IProcessArtifactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * 工艺流程管理API
 */
@Slf4j
@RestController
@RequestMapping("/tansci/api/artifact")
@RequiredArgsConstructor
public class ProcessArtifactController {
    
    private final IProcessArtifactService artifactService;
    
    /**
     * 创建工艺流程
     */
    @PostMapping
    public Map<String, Object> createArtifact(@RequestBody ProcessArtifact artifact) {
        log.info("创建工艺流程: {}", artifact.getName());
        ProcessArtifact result = artifactService.createArtifact(artifact);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "创建成功");
        response.put("data", result);
        return response;
    }
    
    /**
     * 更新工艺流程
     */
    @PutMapping("/{id}")
    public Map<String, Object> updateArtifact(@PathVariable String id, @RequestBody ProcessArtifact artifact) {
        log.info("更新工艺流程: {}", id);
        artifact.setId(id);
        ProcessArtifact result = artifactService.updateArtifact(artifact);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "更新成功");
        response.put("data", result);
        return response;
    }
    
    /**
     * 删除工艺流程
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteArtifact(@PathVariable String id) {
        log.info("删除工艺流程: {}", id);
        artifactService.deleteArtifact(id);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "删除成功");
        return response;
    }
    
    /**
     * 获取工艺流程详情
     */
    @GetMapping("/{id}")
    public Map<String, Object> getArtifact(@PathVariable String id) {
        log.info("查询工艺流程: {}", id);
        ProcessArtifact artifact = artifactService.getArtifact(id);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "查询成功");
        response.put("data", artifact);
        return response;
    }
    
    /**
     * 获取所有工艺流程
     */
    @GetMapping
    public Map<String, Object> listArtifacts() {
        log.info("查询所有工艺流程");
        List<ProcessArtifact> artifacts = artifactService.listArtifacts();
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "查询成功");
        response.put("data", artifacts);
        return response;
    }
    
    /**
     * 保存BPMN流程定义
     */
    @PostMapping("/{artifactId}/bpmn")
    public Map<String, Object> saveBpmnXml(@PathVariable String artifactId, @RequestBody Map<String, String> body) {
        log.info("保存BPMN流程定义: {}", artifactId);
        String bpmnXml = body.get("bpmnXml");
        ProcessArtifact result = artifactService.saveBpmnXml(artifactId, bpmnXml);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "保存成功");
        response.put("data", result);
        return response;
    }
    
    /**
     * 获取BPMN流程定义
     */
    @GetMapping("/{artifactId}/bpmn")
    public Map<String, Object> getBpmnXml(@PathVariable String artifactId) {
        log.info("查询BPMN流程定义: {}", artifactId);
        String bpmnXml = artifactService.getBpmnXml(artifactId);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "查询成功");
        response.put("data", bpmnXml);
        return response;
    }
}
