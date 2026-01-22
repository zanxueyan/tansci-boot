package com.tansci.service.artifact;

import com.tansci.model.artifact.Equipment;
import java.util.List;

/**
 * 设备管理Service接口
 */
public interface IEquipmentService {
    
    /**
     * 创建设备
     */
    Equipment createEquipment(Equipment equipment);
    
    /**
     * 更新设备
     */
    Equipment updateEquipment(Equipment equipment);
    
    /**
     * 删除设备
     */
    void deleteEquipment(String equipmentId);
    
    /**
     * 查询设备详情
     */
    Equipment getEquipment(String equipmentId);
    
    /**
     * 查询所有设备
     */
    List<Equipment> listEquipments();
    
    /**
     * 根据类型查询设备
     */
    List<Equipment> listEquipmentsByType(String type);
    
    /**
     * 绑定工艺流程到设备
     */
    void bindArtifact(String equipmentId, String artifactId);
    
    /**
     * 获取设备关联的工艺流程
     */
    String getDeviceArtifact(String equipmentId);
}
