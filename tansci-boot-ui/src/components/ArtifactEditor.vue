<template>
  <div class="artifact-editor-container">
    <!-- 编辑器工具栏 -->
    <div class="toolbar">
      <button @click="saveArtifact" class="btn-save">💾 保存工艺流程</button>
      <button @click="exportBpmn" class="btn-export">📥 导出BPMN</button>
      <button @click="importBpmn" class="btn-import">📤 导入BPMN</button>
    </div>

    <!-- BPMN编辑器 -->
    <div class="editor-wrapper">
      <div ref="canvas" class="bpmn-canvas"></div>
      
      <!-- 属性面板 -->
      <div class="properties-panel">
        <!-- 节点属性面板 -->
        <div v-if="selectedElement && selectedElement.type === 'bpmn:Task'" class="node-properties">
          <h3>🔧 工艺节点配置</h3>
          <div class="form-group">
            <label>节点名称:</label>
            <input v-model="nodeForm.nodeName" type="text" class="input-field" />
          </div>
          <div class="form-group">
            <label>装载对接方式:</label>
            <select v-model="nodeForm.loadingMode" class="input-field" @change="updateLoadingMode">
              <option value="">-- 选择方式 --</option>
              <option value="1">固定式</option>
              <option value="2">升降式</option>
              <option value="3">传送式</option>
            </select>
          </div>
          <div v-if="nodeForm.loadingMode" class="form-group">
            <label>装载参数 (JSON):</label>
            <textarea v-model="nodeForm.loadingParams" class="textarea-field" rows="4"></textarea>
          </div>
          <div class="form-group">
            <label>执行超时 (ms):</label>
            <input v-model.number="nodeForm.executionTimeout" type="number" class="input-field" />
          </div>
          <div class="form-group">
            <label>重试次数:</label>
            <input v-model.number="nodeForm.retryCount" type="number" class="input-field" />
          </div>
          <button @click="saveNodeProperty" class="btn-save-node">💾 保存节点配置</button>
        </div>

        <!-- 连线属性面板 -->
        <div v-if="selectedElement && selectedElement.type === 'bpmn:SequenceFlow'" class="flow-properties">
          <h3>🔗 连线设备组配置</h3>
          <div class="form-group">
            <label>设备组名称:</label>
            <input v-model="flowForm.deviceGroupName" type="text" class="input-field" />
          </div>
          <div class="form-group">
            <label>设备类型:</label>
            <select v-model="flowForm.deviceType" class="input-field">
              <option value="">-- 选择类型 --</option>
              <option value="agv">AGV</option>
              <option value="stacker">堆垛机</option>
              <option value="other">其他设备</option>
            </select>
          </div>
          <div class="form-group">
            <label>执行设备列表 (逗号分隔):</label>
            <textarea v-model="flowForm.deviceListText" class="textarea-field" rows="4" 
              placeholder="示例: AGV-01, AGV-02, AGV-03"></textarea>
          </div>
          <div class="form-group">
            <label>路由策略:</label>
            <select v-model="flowForm.routingStrategy" class="input-field">
              <option value="round_robin">轮询 (Round Robin)</option>
              <option value="load_balance">负载均衡 (Load Balance)</option>
              <option value="fixed">固定设备 (Fixed)</option>
            </select>
          </div>
          <div class="form-group">
            <label>优先级:</label>
            <input v-model.number="flowForm.priority" type="number" class="input-field" />
          </div>
          <button @click="saveFlowDeviceGroup" class="btn-save-flow">💾 保存连线配置</button>
        </div>

        <!-- 无选中元素 -->
        <div v-if="!selectedElement" class="empty-state">
          <p>👆 在左侧编辑器中选择节点或连线进行配置</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, reactive } from 'vue'
import BpmnModeler from 'bpmn-js/lib/Modeler'
import { BpmnPropertiesPanel } from '@bpmn-io/properties-panel'

const canvas = ref(null)
let modeler: any = null

const selectedElement = ref<any>(null)

// 节点属性表单
const nodeForm = reactive({
  nodeName: '',
  loadingMode: '',
  loadingModeDesc: '',
  loadingParams: '{}',
  executionTimeout: 30000,
  retryCount: 0
})

// 连线属性表单
const flowForm = reactive({
  deviceGroupName: '',
  deviceType: '',
  deviceListText: '',
  deviceList: '[]',
  routingStrategy: 'round_robin',
  priority: 1
})

onMounted(async () => {
  initBpmnModeler()
})

// 初始化BPMN编辑器
function initBpmnModeler() {
  modeler = new BpmnModeler({
    container: canvas.value,
    propertiesPanel: {
      parent: '#js-properties-panel'
    },
    additionalModules: [
      BpmnPropertiesPanel
    ]
  })

  // 监听元素选中事件
  modeler.on('selection.changed', (event: any) => {
    const [element] = event.newSelection
    selectedElement.value = element
    
    if (element && element.type === 'bpmn:Task') {
      loadNodeProperty(element)
    } else if (element && element.type === 'bpmn:SequenceFlow') {
      loadFlowDeviceGroup(element)
    }
  })

  // 创建新的流程
  createNewProcess()
}

// 创建新流程
async function createNewProcess() {
  const bpmnXml = `<?xml version="1.0" encoding="UTF-8"?>
<bpmn:definitions xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" id="Definitions_1">
  <bpmn:process id="Process_1" isExecutable="true">
    <bpmn:startEvent id="StartEvent_1" name="开始"/>
    <bpmn:endEvent id="EndEvent_1" name="结束"/>
  </bpmn:process>
  <bpmndi:BPMNDiagram id="BPMNDiagram_1">
    <bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="Process_1"/>
  </bpmndi:BPMNDiagram>
</bpmn:definitions>`

  try {
    await modeler.importXML(bpmnXml)
    modeler.get('canvas').zoom('fit-viewport')
  } catch (e) {
    console.error('导入BPMN失败:', e)
  }
}

// 加载节点属性
function loadNodeProperty(element: any) {
  // 从后端加载该节点的属性
  nodeForm.nodeName = element.businessObject.name || ''
  nodeForm.loadingMode = ''
  nodeForm.loadingParams = '{}'
  nodeForm.executionTimeout = 30000
  nodeForm.retryCount = 0
}

// 加载连线设备组
function loadFlowDeviceGroup(element: any) {
  // 从后端加载该连线的设备组配置
  flowForm.deviceGroupName = element.businessObject.name || ''
  flowForm.deviceType = ''
  flowForm.deviceListText = ''
  flowForm.routingStrategy = 'round_robin'
  flowForm.priority = 1
}

// 更新装载方式描述
function updateLoadingMode() {
  const modeDescMap: { [key: string]: string } = {
    '1': '固定式装载',
    '2': '升降式装载',
    '3': '传送式装载'
  }
  nodeForm.loadingModeDesc = modeDescMap[nodeForm.loadingMode] || ''
}

// 保存节点属性
async function saveNodeProperty() {
  if (!selectedElement.value) return
  
  try {
    // 调用后端API保存节点属性
    const response = await fetch('/tansci/api/artifact/node-property', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        nodeId: selectedElement.value.id,
        nodeName: nodeForm.nodeName,
        loadingMode: nodeForm.loadingMode,
        loadingModeDesc: nodeForm.loadingModeDesc,
        loadingParams: nodeForm.loadingParams,
        executionTimeout: nodeForm.executionTimeout,
        retryCount: nodeForm.retryCount
      })
    })
    
    const data = await response.json()
    if (data.code === 0) {
      alert('✅ 节点配置保存成功！')
    }
  } catch (error) {
    console.error('保存节点属性失败:', error)
    alert('❌ 保存失败')
  }
}

// 保存连线设备组
async function saveFlowDeviceGroup() {
  if (!selectedElement.value) return
  
  try {
    // 将逗号分隔的设备列表转换为JSON数组
    const devices = flowForm.deviceListText
      .split(',')
      .map(d => d.trim())
      .filter(d => d)
    
    const response = await fetch('/tansci/api/artifact/flow-device-group', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        flowId: selectedElement.value.id,
        deviceGroupName: flowForm.deviceGroupName,
        deviceType: flowForm.deviceType,
        deviceList: JSON.stringify(devices),
        routingStrategy: flowForm.routingStrategy,
        priority: flowForm.priority
      })
    })
    
    const data = await response.json()
    if (data.code === 0) {
      alert('✅ 连线配置保存成功！')
    }
  } catch (error) {
    console.error('保存连线设备组失败:', error)
    alert('❌ 保存失败')
  }
}

// 保存工艺流程
async function saveArtifact() {
  try {
    const bpmnXml = await modeler.saveXML({ format: true })
    // 调用后端API保存BPMN
    const response = await fetch('/tansci/api/artifact/{artifactId}/bpmn', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ bpmnXml: bpmnXml.xml })
    })
    
    const data = await response.json()
    if (data.code === 0) {
      alert('✅ 工艺流程保存成功！')
    }
  } catch (error) {
    console.error('保存工艺流程失败:', error)
    alert('❌ 保存失败')
  }
}

// 导出BPMN
async function exportBpmn() {
  try {
    const bpmnXml = await modeler.saveXML({ format: true })
    const blob = new Blob([bpmnXml.xml], { type: 'text/xml' })
    const url = URL.createObjectURL(blob)
    const a = document.createElement('a')
    a.href = url
    a.download = 'artifact-process.bpmn'
    a.click()
  } catch (error) {
    console.error('导出BPMN失败:', error)
    alert('❌ 导出失败')
  }
}

// 导入BPMN
function importBpmn() {
  const input = document.createElement('input')
  input.type = 'file'
  input.accept = '.bpmn,.xml'
  input.onchange = async (e: any) => {
    const file = e.target.files[0]
    const text = await file.text()
    try {
      await modeler.importXML(text)
      modeler.get('canvas').zoom('fit-viewport')
      alert('✅ BPMN导入成功！')
    } catch (error) {
      console.error('导入BPMN失败:', error)
      alert('❌ 导入失败')
    }
  }
  input.click()
}
</script>

<style scoped lang="scss">
.artifact-editor-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  background: #f5f5f5;

  .toolbar {
    display: flex;
    gap: 10px;
    padding: 12px 16px;
    background: white;
    border-bottom: 1px solid #ddd;
    
    button {
      padding: 8px 16px;
      border: 1px solid #ddd;
      border-radius: 4px;
      background: white;
      cursor: pointer;
      font-size: 14px;
      transition: all 0.3s;

      &:hover {
        background: #f0f0f0;
        border-color: #999;
      }

      &.btn-save {
        background: #4CAF50;
        color: white;
        border-color: #4CAF50;

        &:hover {
          background: #45a049;
        }
      }
    }
  }

  .editor-wrapper {
    display: flex;
    flex: 1;
    gap: 12px;
    padding: 12px;

    .bpmn-canvas {
      flex: 1;
      background: white;
      border-radius: 4px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    }

    .properties-panel {
      width: 320px;
      background: white;
      border-radius: 4px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
      padding: 16px;
      overflow-y: auto;

      h3 {
        margin-top: 0;
        margin-bottom: 16px;
        font-size: 16px;
        border-bottom: 2px solid #f0f0f0;
        padding-bottom: 8px;
      }

      .form-group {
        margin-bottom: 16px;

        label {
          display: block;
          margin-bottom: 6px;
          font-weight: 500;
          font-size: 13px;
          color: #333;
        }

        .input-field,
        .textarea-field {
          width: 100%;
          padding: 8px;
          border: 1px solid #ddd;
          border-radius: 4px;
          font-size: 12px;
          font-family: inherit;

          &:focus {
            outline: none;
            border-color: #4CAF50;
            box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.1);
          }
        }

        .textarea-field {
          resize: vertical;
          font-family: 'Courier New', monospace;
        }
      }

      .btn-save-node,
      .btn-save-flow {
        width: 100%;
        padding: 10px;
        background: #4CAF50;
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 14px;
        transition: all 0.3s;

        &:hover {
          background: #45a049;
        }
      }

      .empty-state {
        display: flex;
        align-items: center;
        justify-content: center;
        height: 100%;
        color: #999;
        font-size: 14px;
        text-align: center;
      }
    }
  }
}
</style>
