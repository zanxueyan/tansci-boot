package com.tansci.controller.artifact;

import com.tansci.model.artifact.ArtifactFlowDeviceGroup;
import com.tansci.service.artifact.IArtifactFlowDeviceGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * 工艺流连线设备组管理API
 * 用于配置连线对应的执行设备组（AGV、堆垛机等）
 */
@Slf4j
@RestController
@RequestMapping("/tansci/api/artifact/flow-device-group")
@RequiredArgsConstructor
public class ArtifactFlowDeviceGroupController {
    
    private final IArtifactFlowDeviceGroupService flowDeviceGroupService;
    
    /**
     * 保存或更新连线设备组配置
     */
    @PostMapping
    public Map<String, Object> saveFlowDeviceGroup(@RequestBody ArtifactFlowDeviceGroup group) {
        log.info("保存连线设备组: artifactId={}, flowId={}", group.getArtifactId(), group.getFlowId());
        ArtifactFlowDeviceGroup result = flowDeviceGroupService.saveFlowDeviceGroup(group);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "保存成功");
        response.put("data", result);
        return response;
    }
    
    /**
     * 删除连线设备组配置
     */
    @DeleteMapping("/{groupId}")
    public Map<String, Object> deleteFlowDeviceGroup(@PathVariable String groupId) {
        log.info("删除连线设备组: {}", groupId);
        flowDeviceGroupService.deleteFlowDeviceGroup(groupId);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "删除成功");
        return response;
    }
    
    /**
     * 获取连线设备组配置
     */
    @GetMapping("/{groupId}")
    public Map<String, Object> getFlowDeviceGroup(@PathVariable String groupId) {
        log.info("查询连线设备组: {}", groupId);
        ArtifactFlowDeviceGroup group = flowDeviceGroupService.getFlowDeviceGroup(groupId);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "查询成功");
        response.put("data", group);
        return response;
    }
    
    /**
     * 根据流程和连线ID查询设备组配置
     */
    @GetMapping("/query")
    public Map<String, Object> getFlowDeviceGroupByArtifactAndFlowId(
            @RequestParam String artifactId,
            @RequestParam String flowId) {
        log.info("查询连线设备组: artifactId={}, flowId={}", artifactId, flowId);
        ArtifactFlowDeviceGroup group = flowDeviceGroupService.getFlowDeviceGroupByArtifactAndFlowId(artifactId, flowId);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "查询成功");
        response.put("data", group);
        return response;
    }
    
    /**
     * 查询流程下所有连线设备组配置
     */
    @GetMapping("/artifact/{artifactId}")
    public Map<String, Object> listFlowDeviceGroupsByArtifact(@PathVariable String artifactId) {
        log.info("查询流程下所有连线设备组: {}", artifactId);
        List<ArtifactFlowDeviceGroup> groups = flowDeviceGroupService.listFlowDeviceGroupsByArtifact(artifactId);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "查询成功");
        response.put("data", groups);
        return response;
    }
    
    /**
     * 根据设备类型查询连线配置
     */
    @GetMapping("/artifact/{artifactId}/device-type/{deviceType}")
    public Map<String, Object> listFlowDeviceGroupsByDeviceType(
            @PathVariable String artifactId,
            @PathVariable String deviceType) {
        log.info("按设备类型查询连线配置: artifactId={}, deviceType={}", artifactId, deviceType);
        List<ArtifactFlowDeviceGroup> groups = flowDeviceGroupService.listFlowDeviceGroupsByDeviceType(artifactId, deviceType);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "查询成功");
        response.put("data", groups);
        return response;
    }
}
