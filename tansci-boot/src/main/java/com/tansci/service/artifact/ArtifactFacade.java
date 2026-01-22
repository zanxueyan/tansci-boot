package com.tansci.service.artifact;

import com.tansci.model.artifact.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Artifact系统外观类 - 为Magic-API提供统一接口
 * 在Magic-API SQL脚本中可通过 @artifact 访问
 */
@Slf4j
@Service("artifact")
@RequiredArgsConstructor
public class ArtifactFacade {

    private final IEquipmentService equipmentService;
    private final IProcessArtifactService processArtifactService;
    private final IArtifactNodePropertyService nodePropertyService;
    private final IArtifactFlowDeviceGroupService flowDeviceGroupService;

    // ==================== Equipment API ====================
    
    public Equipment createEquipment(Equipment equipment) {
        if (equipment.getId() == null) {
            equipment.setId(java.util.UUID.randomUUID().toString());
        }
        return equipmentService.createEquipment(equipment);
    }

    public Equipment getEquipment(String id) {
        return equipmentService.getEquipment(id);
    }

    public List<Equipment> listEquipments() {
        return equipmentService.listEquipments();
    }

    public List<Equipment> listEquipmentsByType(String type) {
        return equipmentService.listEquipmentsByType(type);
    }

    public Equipment updateEquipment(Equipment equipment) {
        return equipmentService.updateEquipment(equipment);
    }

    public void deleteEquipment(String id) {
        equipmentService.deleteEquipment(id);
    }

    // ==================== ProcessArtifact API ====================

    public ProcessArtifact createArtifact(ProcessArtifact artifact) {
        if (artifact.getId() == null) {
            artifact.setId(java.util.UUID.randomUUID().toString());
        }
        return processArtifactService.createArtifact(artifact);
    }

    public ProcessArtifact getArtifact(String id) {
        return processArtifactService.getArtifact(id);
    }

    public List<ProcessArtifact> listArtifacts() {
        return processArtifactService.listArtifacts();
    }

    public ProcessArtifact updateArtifact(ProcessArtifact artifact) {
        return processArtifactService.updateArtifact(artifact);
    }

    public void deleteArtifact(String id) {
        processArtifactService.deleteArtifact(id);
    }

    public ProcessArtifact saveBpmnXml(String artifactId, String bpmnXml) {
        return processArtifactService.saveBpmnXml(artifactId, bpmnXml);
    }

    public String getBpmnXml(String artifactId) {
        return processArtifactService.getBpmnXml(artifactId);
    }

    // ==================== NodeProperty API ====================

    public ArtifactNodeProperty saveNodeProperty(ArtifactNodeProperty property) {
        if (property.getId() == null) {
            property.setId(java.util.UUID.randomUUID().toString());
        }
        return nodePropertyService.saveNodeProperty(property);
    }

    public ArtifactNodeProperty getNodeProperty(String id) {
        return nodePropertyService.getNodeProperty(id);
    }

    public ArtifactNodeProperty getNodePropertyByArtifactAndNodeId(String artifactId, String nodeId) {
        return nodePropertyService.getNodePropertyByArtifactAndNodeId(artifactId, nodeId);
    }

    public List<ArtifactNodeProperty> listNodePropertiesByArtifact(String artifactId) {
        return nodePropertyService.listNodePropertiesByArtifact(artifactId);
    }

    public void deleteNodeProperty(String id) {
        nodePropertyService.deleteNodeProperty(id);
    }

    public void deleteNodePropertiesByArtifact(String artifactId) {
        nodePropertyService.deleteNodePropertiesByArtifact(artifactId);
    }

    // ==================== FlowDeviceGroup API ====================

    public ArtifactFlowDeviceGroup saveFlowDeviceGroup(ArtifactFlowDeviceGroup group) {
        if (group.getId() == null) {
            group.setId(java.util.UUID.randomUUID().toString());
        }
        return flowDeviceGroupService.saveFlowDeviceGroup(group);
    }

    public ArtifactFlowDeviceGroup getFlowDeviceGroup(String id) {
        return flowDeviceGroupService.getFlowDeviceGroup(id);
    }

    public ArtifactFlowDeviceGroup getFlowDeviceGroupByArtifactAndFlowId(String artifactId, String flowId) {
        return flowDeviceGroupService.getFlowDeviceGroupByArtifactAndFlowId(artifactId, flowId);
    }

    public List<ArtifactFlowDeviceGroup> listFlowDeviceGroupsByArtifact(String artifactId) {
        return flowDeviceGroupService.listFlowDeviceGroupsByArtifact(artifactId);
    }

    public List<ArtifactFlowDeviceGroup> listFlowDeviceGroupsByDeviceType(String artifactId, String deviceType) {
        return flowDeviceGroupService.listFlowDeviceGroupsByDeviceType(artifactId, deviceType);
    }

    public void deleteFlowDeviceGroup(String id) {
        flowDeviceGroupService.deleteFlowDeviceGroup(id);
    }

    public void deleteFlowDeviceGroupsByArtifact(String artifactId) {
        flowDeviceGroupService.deleteFlowDeviceGroupsByArtifact(artifactId);
    }
}
