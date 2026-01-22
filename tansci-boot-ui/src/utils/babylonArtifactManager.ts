/**
 * babylonjs场景集成示例
 * 展示如何在babylon场景中集成工艺流程功能
 */

import { getEquipmentArtifact, showArtifactContextMenu } from '@/utils/artifactUtils'

export class BabylonArtifactManager {
  private scene: any
  private equipments: Map<string, any> = new Map()

  constructor(scene: any) {
    this.scene = scene
  }

  /**
   * 注册设备右键菜单
   */
  public registerEquipmentContextMenu(mesh: any, equipmentId: string) {
    this.scene.onPointerObservable.add((pointerInfo: any) => {
      if (pointerInfo.type === 1 && pointerInfo.event.button === 2) { // 右键
        const hit = this.scene.pick(
          this.scene.pointerX,
          this.scene.pointerY,
          (mesh: any) => mesh === mesh
        )

        if (hit && hit.hit) {
          this.onEquipmentRightClick(equipmentId, pointerInfo.event)
        }
      }
    })
  }

  /**
   * 处理设备右键点击
   */
  private async onEquipmentRightClick(equipmentId: string, event: MouseEvent) {
    event.preventDefault()

    try {
      const artifact = await getEquipmentArtifact(equipmentId)
      if (artifact) {
        showArtifactContextMenu(
          artifact,
          event.clientX,
          event.clientY,
          (node) => {
            console.log('选择工艺工序:', node)
            this.onArtifactNodeSelected(node)
          }
        )
      } else {
        alert('该设备未配置工艺流程')
      }
    } catch (error) {
      console.error('获取工艺流程失败:', error)
      alert('获取工艺流程失败，请检查网络连接')
    }
  }

  /**
   * 处理工艺节点选中事件
   */
  private onArtifactNodeSelected(node: any) {
    console.log('工艺节点被选中:', node)
    
    // 在这里可以实现：
    // 1. 高亮显示该节点在编辑器中的位置
    // 2. 显示节点的详细配置信息
    // 3. 启动该工艺流程
    // 等其他业务逻辑
    
    // 示例：显示节点信息提示
    const tooltip = document.createElement('div')
    tooltip.className = 'artifact-info-tooltip'
    tooltip.textContent = `工艺工序: ${node.name}\n装载方式: ${node.loadingModeDesc}\n\n(点击即可启动该工序)`
    tooltip.style.left = event.clientX + 'px'
    tooltip.style.top = (event.clientY + 20) + 'px'
    document.body.appendChild(tooltip)

    setTimeout(() => tooltip.remove(), 3000)
  }

  /**
   * 获取所有设备列表
   */
  public async loadEquipments() {
    try {
      const response = await fetch('/tansci/api/equipment')
      const data = await response.json()
      
      if (data.code === 0 && data.data) {
        data.data.forEach((equipment: any) => {
          this.equipments.set(equipment.id, equipment)
        })
      }
    } catch (error) {
      console.error('加载设备列表失败:', error)
    }
  }

  /**
   * 根据设备类型加载设备
   */
  public async loadEquipmentsByType(type: string) {
    try {
      const response = await fetch(`/tansci/api/equipment/type/${type}`)
      const data = await response.json()
      
      if (data.code === 0 && data.data) {
        data.data.forEach((equipment: any) => {
          this.equipments.set(equipment.id, equipment)
        })
      }
    } catch (error) {
      console.error(`加载类型${type}的设备列表失败:`, error)
    }
  }

  /**
   * 获取设备列表
   */
  public getEquipments(): any[] {
    return Array.from(this.equipments.values())
  }

  /**
   * 获取特定设备
   */
  public getEquipment(equipmentId: string): any {
    return this.equipments.get(equipmentId)
  }

  /**
   * 绑定工艺流程到设备
   */
  public async bindArtifactToEquipment(equipmentId: string, artifactId: string) {
    try {
      const response = await fetch(
        `/tansci/api/equipment/${equipmentId}/bind-artifact/${artifactId}`,
        { method: 'POST' }
      )
      
      const data = await response.json()
      if (data.code === 0) {
        const equipment = this.equipments.get(equipmentId)
        if (equipment) {
          equipment.artifactId = artifactId
        }
        return true
      }
      return false
    } catch (error) {
      console.error('绑定工艺流程失败:', error)
      return false
    }
  }
}

// 导出使用示例
export function createBabylonArtifactManagerExample() {
  // const scene = ... // babylon场景对象
  
  // const manager = new BabylonArtifactManager(scene)
  
  // // 加载所有设备
  // manager.loadEquipments()
  
  // // 为每个设备注册右键菜单
  // manager.getEquipments().forEach(equipment => {
  //   const mesh = scene.getMeshByName(equipment.id)
  //   if (mesh) {
  //     manager.registerEquipmentContextMenu(mesh, equipment.id)
  //   }
  // })
}
