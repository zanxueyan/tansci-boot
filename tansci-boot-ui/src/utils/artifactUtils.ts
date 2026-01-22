/**
 * babylonjs场景中设备右键菜单功能
 * 右键点击AGV或其他设备，显示其关联的工艺流程
 */

export interface ArtifactNode {
  id: string
  name: string
  loadingMode: string
  loadingModeDesc: string
}

export interface ArtifactFlow {
  id: string
  sourceNodeId: string
  targetNodeId: string
  deviceGroupName: string
  deviceList: string[]
  deviceType: string
}

export interface EquipmentArtifact {
  equipmentId: string
  equipmentName: string
  equipmentType: string
  artifactId: string
  artifactName: string
  nodes: ArtifactNode[]
  flows: ArtifactFlow[]
}

/**
 * 获取设备的工艺流程
 */
export async function getEquipmentArtifact(equipmentId: string): Promise<EquipmentArtifact | null> {
  try {
    // 1. 获取设备信息
    const equipmentRes = await fetch(`/tansci/api/equipment/${equipmentId}`)
    const equipmentData = await equipmentRes.json()
    
    if (equipmentData.code !== 0 || !equipmentData.data) {
      return null
    }
    
    const equipment = equipmentData.data
    const artifactId = equipment.artifactId
    
    if (!artifactId) {
      return null // 设备未绑定工艺流程
    }
    
    // 2. 获取工艺流程信息
    const artifactRes = await fetch(`/tansci/api/artifact/${artifactId}`)
    const artifactData = await artifactRes.json()
    
    if (artifactData.code !== 0 || !artifactData.data) {
      return null
    }
    
    const artifact = artifactData.data
    
    // 3. 获取所有节点属性
    const nodesRes = await fetch(`/tansci/api/artifact/node-property/artifact/${artifactId}`)
    const nodesData = await nodesRes.json()
    const nodes: ArtifactNode[] = (nodesData.data || []).map((node: any) => ({
      id: node.nodeId,
      name: node.nodeName,
      loadingMode: node.loadingMode,
      loadingModeDesc: node.loadingModeDesc
    }))
    
    // 4. 获取所有连线设备组
    const flowsRes = await fetch(`/tansci/api/artifact/flow-device-group/artifact/${artifactId}`)
    const flowsData = await flowsRes.json()
    const flows: ArtifactFlow[] = (flowsData.data || []).map((flow: any) => ({
      id: flow.flowId,
      sourceNodeId: flow.sourceNodeId,
      targetNodeId: flow.targetNodeId,
      deviceGroupName: flow.deviceGroupName,
      deviceList: JSON.parse(flow.deviceList || '[]'),
      deviceType: flow.deviceType
    }))
    
    return {
      equipmentId: equipment.id,
      equipmentName: equipment.name,
      equipmentType: equipment.type,
      artifactId: artifact.id,
      artifactName: artifact.name,
      nodes,
      flows
    }
  } catch (error) {
    console.error('获取设备工艺流程失败:', error)
    return null
  }
}

/**
 * 显示设备工艺流程右键菜单
 */
export function showArtifactContextMenu(
  equipment: any,
  mouseX: number,
  mouseY: number,
  onSelectNode: (node: ArtifactNode) => void
) {
  // 创建菜单容器
  const contextMenu = document.createElement('div')
  contextMenu.className = 'artifact-context-menu'
  contextMenu.style.left = mouseX + 'px'
  contextMenu.style.top = mouseY + 'px'
  
  // 菜单内容
  let html = `<div class="menu-header">📋 ${equipment.equipmentName} 的工艺</div>`
  
  if (equipment.nodes && equipment.nodes.length > 0) {
    html += '<div class="menu-section">'
    html += '<div class="section-title">🔧 工艺工序</div>'
    equipment.nodes.forEach((node: ArtifactNode, index: number) => {
      html += `<div class="menu-item" data-node-id="${node.id}" data-index="${index}">
        <span class="node-name">${node.name}</span>
        <span class="loading-mode">${node.loadingModeDesc || '未配置'}</span>
      </div>`
    })
    html += '</div>'
  }
  
  if (equipment.flows && equipment.flows.length > 0) {
    html += '<div class="menu-section">'
    html += '<div class="section-title">🚚 执行设备组</div>'
    equipment.flows.forEach((flow: ArtifactFlow, index: number) => {
      const deviceList = flow.deviceList.join(', ')
      html += `<div class="menu-item flow-item" data-flow-id="${flow.id}">
        <span class="flow-name">${flow.deviceGroupName}</span>
        <span class="device-list">${deviceList}</span>
      </div>`
    })
    html += '</div>'
  }
  
  html += '<div class="menu-footer"><small>工艺流程: ' + equipment.artifactName + '</small></div>'
  
  contextMenu.innerHTML = html
  document.body.appendChild(contextMenu)
  
  // 添加事件监听
  const menuItems = contextMenu.querySelectorAll('.menu-item:not(.flow-item)')
  menuItems.forEach((item) => {
    item.addEventListener('click', () => {
      const nodeId = item.getAttribute('data-node-id')
      const node = equipment.nodes.find((n: ArtifactNode) => n.id === nodeId)
      if (node) {
        onSelectNode(node)
      }
      contextMenu.remove()
    })
  })
  
  // 点击外部关闭菜单
  const closeMenu = (e: MouseEvent) => {
    if (!contextMenu.contains(e.target as Node)) {
      contextMenu.remove()
      document.removeEventListener('mousedown', closeMenu)
    }
  }
  
  setTimeout(() => {
    document.addEventListener('mousedown', closeMenu)
  }, 100)
}

/**
 * 在babylon场景中渲染工艺流程（可视化）
 */
export function renderArtifactProcessInScene(
  scene: any,
  artifact: EquipmentArtifact,
  position: any
) {
  // 这里可以在场景中创建3D文本或其他视觉效果来显示工艺流程
  // 例如：使用babylon的文本纹理显示流程信息
  
  console.log('在场景中渲染工艺流程:', artifact)
  
  // 示例：创建一个透明的UI面板显示工艺信息
  const artifactInfo = `
设备: ${artifact.equipmentName}
工艺: ${artifact.artifactName}
工序数: ${artifact.nodes.length}
连线数: ${artifact.flows.length}
  `.trim()
  
  return artifactInfo
}
