package com.tansci.controller.artifact;

import com.tansci.model.artifact.ArtifactNodeProperty;
import com.tansci.service.artifact.IArtifactNodePropertyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * 工艺节点属性管理API
 * 用于配置节点的装载对接方式等信息
 */
@Slf4j
@RestController
@RequestMapping("/tansci/api/artifact/node-property")
@RequiredArgsConstructor
public class ArtifactNodePropertyController {
    
    private final IArtifactNodePropertyService nodePropertyService;
    
    /**
     * 保存或更新节点属性
     */
    @PostMapping
    public Map<String, Object> saveNodeProperty(@RequestBody ArtifactNodeProperty property) {
        log.info("保存节点属性: artifactId={}, nodeId={}", property.getArtifactId(), property.getNodeId());
        ArtifactNodeProperty result = nodePropertyService.saveNodeProperty(property);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "保存成功");
        response.put("data", result);
        return response;
    }
    
    /**
     * 删除节点属性
     */
    @DeleteMapping("/{propertyId}")
    public Map<String, Object> deleteNodeProperty(@PathVariable String propertyId) {
        log.info("删除节点属性: {}", propertyId);
        nodePropertyService.deleteNodeProperty(propertyId);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "删除成功");
        return response;
    }
    
    /**
     * 获取节点属性
     */
    @GetMapping("/{propertyId}")
    public Map<String, Object> getNodeProperty(@PathVariable String propertyId) {
        log.info("查询节点属性: {}", propertyId);
        ArtifactNodeProperty property = nodePropertyService.getNodeProperty(propertyId);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "查询成功");
        response.put("data", property);
        return response;
    }
    
    /**
     * 根据流程和节点ID查询节点属性
     */
    @GetMapping("/query")
    public Map<String, Object> getNodePropertyByArtifactAndNodeId(
            @RequestParam String artifactId,
            @RequestParam String nodeId) {
        log.info("查询节点属性: artifactId={}, nodeId={}", artifactId, nodeId);
        ArtifactNodeProperty property = nodePropertyService.getNodePropertyByArtifactAndNodeId(artifactId, nodeId);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "查询成功");
        response.put("data", property);
        return response;
    }
    
    /**
     * 查询流程下所有节点属性
     */
    @GetMapping("/artifact/{artifactId}")
    public Map<String, Object> listNodePropertiesByArtifact(@PathVariable String artifactId) {
        log.info("查询流程下所有节点属性: {}", artifactId);
        List<ArtifactNodeProperty> properties = nodePropertyService.listNodePropertiesByArtifact(artifactId);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "查询成功");
        response.put("data", properties);
        return response;
    }
}
