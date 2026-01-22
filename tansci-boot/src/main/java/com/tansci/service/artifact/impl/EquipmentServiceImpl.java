package com.tansci.service.artifact.impl;

import com.tansci.model.artifact.Equipment;
import com.tansci.service.artifact.IEquipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 设备管理Service实现
 * 暂时使用内存存储，后续可集成数据库
 */
@Slf4j
@Service
public class EquipmentServiceImpl implements IEquipmentService {
    
    private final Map<String, Equipment> equipmentStore = new ConcurrentHashMap<>();
    
    @Override
    public Equipment createEquipment(Equipment equipment) {
        equipment.setId(UUID.randomUUID().toString());
        equipment.setCreateTime(System.currentTimeMillis());
        equipment.setUpdateTime(System.currentTimeMillis());
        equipmentStore.put(equipment.getId(), equipment);
        log.info("创建设备: {}", equipment.getId());
        return equipment;
    }
    
    @Override
    public Equipment updateEquipment(Equipment equipment) {
        equipment.setUpdateTime(System.currentTimeMillis());
        equipmentStore.put(equipment.getId(), equipment);
        log.info("更新设备: {}", equipment.getId());
        return equipment;
    }
    
    @Override
    public void deleteEquipment(String equipmentId) {
        equipmentStore.remove(equipmentId);
        log.info("删除设备: {}", equipmentId);
    }
    
    @Override
    public Equipment getEquipment(String equipmentId) {
        return equipmentStore.get(equipmentId);
    }
    
    @Override
    public List<Equipment> listEquipments() {
        return new ArrayList<>(equipmentStore.values());
    }
    
    @Override
    public List<Equipment> listEquipmentsByType(String type) {
        return equipmentStore.values().stream()
            .filter(e -> type.equals(e.getType()))
            .toList();
    }
    
    @Override
    public void bindArtifact(String equipmentId, String artifactId) {
        Equipment equipment = equipmentStore.get(equipmentId);
        if (equipment != null) {
            equipment.setArtifactId(artifactId);
            equipment.setUpdateTime(System.currentTimeMillis());
            log.info("绑定工艺流程: equipmentId={}, artifactId={}", equipmentId, artifactId);
        }
    }
    
    @Override
    public String getDeviceArtifact(String equipmentId) {
        Equipment equipment = equipmentStore.get(equipmentId);
        return equipment != null ? equipment.getArtifactId() : null;
    }
}
