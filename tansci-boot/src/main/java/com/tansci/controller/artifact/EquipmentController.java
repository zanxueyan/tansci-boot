package com.tansci.controller.artifact;

import com.tansci.model.artifact.Equipment;
import com.tansci.service.artifact.IEquipmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * 生产设备管理API
 */
@Slf4j
@RestController
@RequestMapping("/tansci/api/equipment")
@RequiredArgsConstructor
public class EquipmentController {
    
    private final IEquipmentService equipmentService;
    
    /**
     * 创建设备
     */
    @PostMapping
    public Map<String, Object> createEquipment(@RequestBody Equipment equipment) {
        log.info("创建设备: {}", equipment.getName());
        Equipment result = equipmentService.createEquipment(equipment);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "创建成功");
        response.put("data", result);
        return response;
    }
    
    /**
     * 更新设备
     */
    @PutMapping("/{id}")
    public Map<String, Object> updateEquipment(@PathVariable String id, @RequestBody Equipment equipment) {
        log.info("更新设备: {}", id);
        equipment.setId(id);
        Equipment result = equipmentService.updateEquipment(equipment);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "更新成功");
        response.put("data", result);
        return response;
    }
    
    /**
     * 删除设备
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteEquipment(@PathVariable String id) {
        log.info("删除设备: {}", id);
        equipmentService.deleteEquipment(id);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "删除成功");
        return response;
    }
    
    /**
     * 获取设备详情
     */
    @GetMapping("/{id}")
    public Map<String, Object> getEquipment(@PathVariable String id) {
        log.info("查询设备: {}", id);
        Equipment equipment = equipmentService.getEquipment(id);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "查询成功");
        response.put("data", equipment);
        return response;
    }
    
    /**
     * 获取所有设备
     */
    @GetMapping
    public Map<String, Object> listEquipments() {
        log.info("查询所有设备");
        List<Equipment> equipments = equipmentService.listEquipments();
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "查询成功");
        response.put("data", equipments);
        return response;
    }
    
    /**
     * 按类型查询设备
     */
    @GetMapping("/type/{type}")
    public Map<String, Object> listEquipmentsByType(@PathVariable String type) {
        log.info("按类型查询设备: {}", type);
        List<Equipment> equipments = equipmentService.listEquipmentsByType(type);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "查询成功");
        response.put("data", equipments);
        return response;
    }
    
    /**
     * 绑定工艺流程到设备
     */
    @PostMapping("/{equipmentId}/bind-artifact/{artifactId}")
    public Map<String, Object> bindArtifact(@PathVariable String equipmentId, @PathVariable String artifactId) {
        log.info("绑定工艺流程到设备: equipmentId={}, artifactId={}", equipmentId, artifactId);
        equipmentService.bindArtifact(equipmentId, artifactId);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "绑定成功");
        return response;
    }
    
    /**
     * 获取设备关联的工艺流程ID
     */
    @GetMapping("/{equipmentId}/artifact")
    public Map<String, Object> getDeviceArtifact(@PathVariable String equipmentId) {
        log.info("查询设备工艺流程: {}", equipmentId);
        String artifactId = equipmentService.getDeviceArtifact(equipmentId);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "查询成功");
        response.put("data", artifactId);
        return response;
    }
}
