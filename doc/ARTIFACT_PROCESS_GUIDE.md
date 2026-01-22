# 工艺流程管理系统 - 使用指南

## 📋 功能概述

本系统提供了完整的生产设备工艺流程管理解决方案，支持：

1. **生产设备管理** - 管理AGV、堆垛机等各类生产设备
2. **工艺流程编辑** - 使用BPMN编辑器拖放定义工艺流程
3. **工艺节点配置** - 为流程节点配置装载对接方式
4. **连线设备组配置** - 为连线配置执行的设备组列表
5. **场景集成** - 在babylonjs场景中右键设备显示其工艺流程

## 🏗️ 系统架构

### 后端API接口

#### 设备管理API (`/tansci/api/equipment`)

| 方法 | 端点 | 描述 |
|------|------|------|
| POST | `/` | 创建设备 |
| PUT | `/{id}` | 更新设备 |
| DELETE | `/{id}` | 删除设备 |
| GET | `/{id}` | 获取设备详情 |
| GET | `/` | 获取所有设备 |
| GET | `/type/{type}` | 按类型查询设备 |
| POST | `/{equipmentId}/bind-artifact/{artifactId}` | 绑定工艺流程 |
| GET | `/{equipmentId}/artifact` | 获取设备关联的工艺流程 |

#### 工艺流程API (`/tansci/api/artifact`)

| 方法 | 端点 | 描述 |
|------|------|------|
| POST | `/` | 创建工艺流程 |
| PUT | `/{id}` | 更新工艺流程 |
| DELETE | `/{id}` | 删除工艺流程 |
| GET | `/{id}` | 获取工艺流程详情 |
| GET | `/` | 获取所有工艺流程 |
| POST | `/{artifactId}/bpmn` | 保存BPMN定义 |
| GET | `/{artifactId}/bpmn` | 获取BPMN定义 |

#### 工艺节点属性API (`/tansci/api/artifact/node-property`)

| 方法 | 端点 | 描述 |
|------|------|------|
| POST | `/` | 保存节点属性 |
| DELETE | `/{propertyId}` | 删除节点属性 |
| GET | `/{propertyId}` | 获取节点属性 |
| GET | `/query?artifactId={id}&nodeId={id}` | 查询特定节点属性 |
| GET | `/artifact/{artifactId}` | 获取流程的所有节点属性 |

#### 连线设备组API (`/tansci/api/artifact/flow-device-group`)

| 方法 | 端点 | 描述 |
|------|------|------|
| POST | `/` | 保存连线设备组 |
| DELETE | `/{groupId}` | 删除连线设备组 |
| GET | `/{groupId}` | 获取连线设备组 |
| GET | `/query?artifactId={id}&flowId={id}` | 查询特定连线设备组 |
| GET | `/artifact/{artifactId}` | 获取流程的所有连线设备组 |
| GET | `/artifact/{artifactId}/device-type/{type}` | 按设备类型查询连线 |

## 📝 使用示例

### 1. 创建设备

```bash
curl -X POST http://localhost:7000/tansci/api/equipment \
  -H "Content-Type: application/json" \
  -d '{
    "name": "AGV-01",
    "type": "agv",
    "description": "自动导向车-01"
  }'
```

### 2. 创建工艺流程

```bash
curl -X POST http://localhost:7000/tansci/api/artifact \
  -H "Content-Type: application/json" \
  -d '{
    "name": "标准装载流程",
    "description": "AGV标准装载工艺流程"
  }'
```

### 3. 绑定工艺流程到设备

```bash
curl -X POST http://localhost:7000/tansci/api/equipment/{equipmentId}/bind-artifact/{artifactId}
```

### 4. 编辑工艺流程

使用ArtifactEditor.vue组件：

```vue
<template>
  <ArtifactEditor :artifactId="artifactId" />
</template>

<script setup>
import ArtifactEditor from '@/components/ArtifactEditor.vue'

const artifactId = 'artifact-001'
</script>
```

### 5. 在Babylon场景中集成

```typescript
import { BabylonArtifactManager } from '@/utils/babylonArtifactManager'

// 初始化管理器
const manager = new BabylonArtifactManager(babylonScene)

// 加载所有设备
await manager.loadEquipments()

// 为AGV设备注册右键菜单
manager.getEquipments()
  .filter(e => e.type === 'agv')
  .forEach(equipment => {
    const mesh = babylonScene.getMeshByName(equipment.id)
    if (mesh) {
      manager.registerEquipmentContextMenu(mesh, equipment.id)
    }
  })
```

## 🎨 前端组件

### ArtifactEditor.vue

完整的BPMN工艺流程编辑器，支持：

- 拖放创建工艺节点
- 连接工艺流程
- 节点属性编辑（装载方式、参数、超时等）
- 连线设备组配置（AGV列表、路由策略等）
- BPMN导入导出

### 工艺流程右键菜单

在babylon场景中右键点击设备时显示：

```
┌─────────────────────────────┐
│ 📋 AGV-01 的工艺            │
├─────────────────────────────┤
│ 🔧 工艺工序                 │
│   ├─ 装载 (固定式)          │
│   ├─ 运输                   │
│   └─ 卸载 (升降式)          │
├─────────────────────────────┤
│ 🚚 执行设备组               │
│   ├─ AGV池-1 (AGV-01, ...)  │
│   └─ AGV池-2 (AGV-02, ...)  │
├─────────────────────────────┤
│ 工艺流程: 标准装载流程       │
└─────────────────────────────┘
```

## 🔧 节点装载对接方式

### 1. 固定式 (loadingMode: "1")

设备在固定位置进行装载，不需要移动。

```json
{
  "loadingMode": "1",
  "loadingParams": {
    "position": "A1",
    "capacity": 100,
    "loadTime": 30
  }
}
```

### 2. 升降式 (loadingMode: "2")

设备通过升降机制进行装载。

```json
{
  "loadingMode": "2",
  "loadingParams": {
    "maxHeight": 300,
    "minHeight": 100,
    "speed": 50,
    "liftTime": 20
  }
}
```

### 3. 传送式 (loadingMode: "3")

设备通过传送带进行装载。

```json
{
  "loadingMode": "3",
  "loadingParams": {
    "beltSpeed": 100,
    "beltLength": 200,
    "transportTime": 40
  }
}
```

## 🚚 连线路由策略

### 1. 轮询 (round_robin)
循环分配设备，确保负载均匀分布。

### 2. 负载均衡 (load_balance)
根据设备当前负载选择最空闲的设备。

### 3. 固定设备 (fixed)
固定使用指定的设备。

## 📊 数据模型

### Equipment (设备)
```typescript
interface Equipment {
  id: string              // 设备ID
  name: string            // 设备名称
  type: string            // 设备类型 (agv/stacker/other)
  artifactId?: string     // 关联的工艺流程ID
  description?: string    // 设备描述
  createTime: number      // 创建时间
  updateTime: number      // 更新时间
}
```

### ProcessArtifact (工艺流程)
```typescript
interface ProcessArtifact {
  id: string              // 流程ID
  name: string            // 流程名称
  bpmnXml: string         // BPMN流程定义
  description?: string    // 流程描述
  version: number         // 版本号
  isLatest: boolean       // 是否最新版本
  createTime: number      // 创建时间
  updateTime: number      // 更新时间
}
```

### ArtifactNodeProperty (节点属性)
```typescript
interface ArtifactNodeProperty {
  id: string              // 属性ID
  artifactId: string      // 流程ID
  nodeId: string          // 节点ID
  nodeName: string        // 节点名称
  loadingMode: string     // 装载方式 (1/2/3)
  loadingModeDesc: string // 装载描述
  loadingParams: string   // 装载参数 (JSON)
  executionTimeout: number // 执行超时(ms)
  retryCount: number      // 重试次数
  createTime: number      // 创建时间
  updateTime: number      // 更新时间
}
```

### ArtifactFlowDeviceGroup (连线设备组)
```typescript
interface ArtifactFlowDeviceGroup {
  id: string              // 配置ID
  artifactId: string      // 流程ID
  flowId: string          // 连线ID
  sourceNodeId: string    // 源节点ID
  targetNodeId: string    // 目标节点ID
  deviceGroupName: string // 设备组名称
  deviceList: string      // 设备列表 (JSON)
  deviceType: string      // 设备类型
  priority: number        // 优先级
  routingStrategy: string // 路由策略
  createTime: number      // 创建时间
  updateTime: number      // 更新时间
}
```

## 🔄 工作流程

```
1. 创建设备 (Equipment)
   ↓
2. 创建工艺流程 (ProcessArtifact)
   ↓
3. 编辑BPMN流程图
   ├─ 拖放创建工艺节点
   ├─ 配置节点属性（装载方式）
   └─ 配置连线属性（设备组）
   ↓
4. 绑定工艺流程到设备
   ↓
5. 在场景中右键设备查看工艺流程
   ↓
6. 点击工艺工序执行流程
```

## 📦 依赖项

- bpmn-js - BPMN流程编辑器
- @bpmn-io/properties-panel - BPMN属性面板
- babylon.js - 3D场景引擎

## 🚀 下一步计划

- [ ] 工艺流程执行引擎
- [ ] 工艺流程版本控制
- [ ] 工艺参数模板库
- [ ] 设备性能监控
- [ ] 流程执行日志记录
- [ ] 流程性能分析

---

**最后修改:** 2026-01-22
**版本:** 1.0.0
