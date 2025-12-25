<template>
  <div class="Container" v-loading="loading">
    <!-- 按钮 -->
    <div class="second-tool-bar">
      <el-button class="section-btn-style2" @click="cornerstoneToolsSelect('Wwwc')">灰度</el-button>
      <el-button class="section-btn-style2" @click="cornerstoneToolsSelect('Pan')">移动</el-button>
      <el-button class="section-btn-style2" @click="cornerstoneToolsSelect('Rotate')">旋转</el-button>
      <el-button class="section-btn-style2" @click="showInfo">图像信息</el-button>
      <el-button class="section-btn-style2" @click="reset">重置</el-button>
      <el-button class="section-btn-style2" @click="saveImage">保存</el-button>
      <el-button class="section-btn-style2" @click="cornerstoneToolsSelect('ArrowAnnotate')">注释</el-button>
      <el-button class="section-btn-style2" @click="cornerstoneToolsSelect('Length')">测距</el-button>
      <el-button class="section-btn-style2" @click="cornerstoneToolsSelect('Angle')">角度</el-button>
      <el-button class="section-btn-style2" @click="cornerstoneToolsSelect('RectangleRoi')">矩形</el-button>
      <el-button class="section-btn-style2" @click="cornerstoneToolsSelect('EllipticalRoi')">CT值(椭圆)</el-button>
      <el-button class="section-btn-style2" @click="cornerstoneToolsSelect('FreehandRoi')">自定义</el-button>
      <el-button class="section-btn-style2" @click="cornerstoneToolsSelect('Eraser')">橡皮擦</el-button>
      <el-button class="section-btn-style2" @click="toggleInvert">反色</el-button>
      <el-button class="section-btn-style2" @click="cornerstoneToolsSelect('Magnify')">放大镜</el-button>
    </div>
    <!-- 图像区域 -->
    <div class="mainBar">
      <div id="test" class="img-container" @contextmenu.prevent :key="openWid"></div>
      <div id="test2" class="img-container" style="position: absolute; right: 100vw"></div>
      <template v-if="isShowInfo">
        <div class="img-info left-top">
          <p v-if="imageInfo.patientName">{{ imageInfo.patientName }}</p>
          <p v-if="imageInfo.patientSex">
            <span v-if="imageInfo.patientSex">{{ imageInfo.patientSex }}</span>
            <span v-if="imageInfo.age">{{ ' - ' + imageInfo.age.slice(1, 3) }}</span>
            <!-- <span style="margin-left: 8px;">{{  }}</span> -->
          </p>
          <p v-if="imageInfo.patientcode">ID:{{ imageInfo.patientcode }}</p>
        </div>
        <div class="img-info right-top">
          <p v-if="imageInfo.institutionName">{{ imageInfo.institutionName }}</p>
          <p v-if="imageInfo.contentDate">
            <span>Image</span>
            <span style="margin-left: 8px">date:{{ imageInfo.contentDate }}</span>
            <span style="margin-left: 8px">{{ getWeekDay(imageInfo.contentDate) }}</span>
          </p>
          <p v-if="imageInfo.contentTime">
            <span>Image</span>
            <span style="margin-left: 8px">time:{{ imageInfo.contentTime }}</span>
          </p>
          <p v-if="imageInfo.mag">Mag={{ imageInfo.mag }}</p>
        </div>
        <!-- <div class="img-info right-bottom">
          <p>DICOM3.CN Activex Control</p>
        </div> -->
        <div class="img-info left-bottom">
          <p>
            <span>W:{{ parseFloat(imageInfo.windowWidth).toFixed(0) }}</span>
            <span style="margin-left: 8px">L:{{ parseFloat(imageInfo.windowCenter).toFixed(0) }}</span>
          </p>
          <p v-if="imageInfo.kv">kV:{{ imageInfo.kv }}</p>
          <p v-if="imageInfo.ma">mA:{{ imageInfo.ma }}</p>
          <p v-if="imageInfo.exposureTime">Exposure time:{{ imageInfo.exposureTime }}</p>
          <p v-if="imageInfo.slicePos || imageInfo.sliceST">
            <span>Slice</span>
            <span style="margin-left: 8px" v-if="imageInfo.slicePos">pos:{{ imageInfo.slicePos }}</span>
            <span style="margin-left: 8px" v-if="imageInfo.sliceST">ST:{{ imageInfo.sliceST }}</span>
          </p>
        </div>
      </template>
    </div>
  </div>
</template>

<script>
import { getDicomInfo } from '@/api/PACS/section_modal.js'
// 渲染我们的图像，并提供有用的事件和方法来使响应视口变化的工具成为可能
import * as cornerstone from 'cornerstone-core'
import * as cornerstone2 from 'cornerstone-core'
// 某些工具用于辅助向量数学或其他复杂运算的依赖项
import cornerstoneMath from 'cornerstone-math'
import * as cornerstoneTools from '@/utils/cornerstoneTools.js'
// 触摸事件和手势的跨浏览器支持
import Hammer from 'hammerjs'
// web图片加载
import cornerstoneWADOImageLoader from 'cornerstone-wado-image-loader'
import * as dicomParser from 'dicom-parser'

cornerstoneTools.external.cornerstone = cornerstone
cornerstoneTools.external.Hammer = Hammer
cornerstoneTools.external.cornerstoneMath = cornerstoneMath
cornerstoneWADOImageLoader.external.dicomParser = dicomParser
cornerstoneWADOImageLoader.external.cornerstone = cornerstone

export default {
  name: 'HDScreen',
  data() {
    return {
      cornerstoneToolsType: 'Pan',
      openWid: 1,
      loading: false,
      // 已标注
      completeMark: require('@/assets/images/dataAnnotation/yiwancheng.png'),
      // completeMark: require('../../assets/image/dataAnnotation/yiwancheng.png'),
      // 未标注
      waitMark: require('@/assets/images/dataAnnotation/daishenhe.png'),
      // 标注笔
      rectangleRoiImg: require('@/assets/images/dataAnnotation/pencil.png'),
      // 橡皮擦
      eraserImg: require('@/assets/images/dataAnnotation/eraser.png'),
      //椭圆
      ellipseImg: require('@/assets/images/dataAnnotation/ellipse.png'),
      // 自定义
      customImg: require('@/assets/images/dataAnnotation/custom.png'),
      // 左侧菜单
      activeIndex: -1,
      group: [],
      // 中间图片标注
      imgSrc: '',
      ele: null,
      ele2: null,
      toolDatas: null,

      // defectRadio: '绿色',
      // defectTypeList: [],
      markeData: {},
      markeImgName: '',
      markImgId: '',
      // 图像信息
      imageInfo: {},
      // 是否显示图像信息
      isShowInfo: true,
      // 定时器
      timer: null,
      // 切换pacs图片节流操作
      isToggle: false,
      // 不操作20分钟后刷新页面
      countdown: null,
    }
  },
  watch: {
    menuTree() {
      ++this.openWid
    },
  },
  created() {
    const imgUrl = this.$getCookie('imgPath')
    let group = ''
    let activeIndex = ''
    setTimeout(() => {
      this.group = JSON.parse(localStorage.getItem('PACS_Dicom'))
      this.activeIndex = localStorage.getItem('PACS_Dicom_active')
      this.imageInfo = JSON.parse(this.group[this.activeIndex].dcmInfo)
      if (group == this.group && activeIndex == this.activeIndex) {
        return
      }
      group = this.group
      activeIndex = this.activeIndex
      this.imgSrc = imgUrl + JSON.parse(localStorage.getItem('PACS_Dicom'))[this.activeIndex].dcmsrc
      this.initCornerstoneTools(1)
      this.loadToolStates()
      // 开启缩放标尺
      this.cornerstoneToolsSelect('ScaleOverlay')
      // 开启方向标记
      this.cornerstoneToolsSelect('OrientationMarkers')
      cornerstoneTools.setToolActive('Wwwc', { mouseButtonMask: 2 })
      cornerstoneTools.setToolActive('Pan', { mouseButtonMask: 4 })
    }, 20)
  },
  mounted() {
    //监听鼠标滚动事件
    window.addEventListener('wheel', this.handleMouseWheel)
    // 监听键盘事件
    window.addEventListener('keydown', (event) => {
      if (event.key === 'ArrowUp' || event.key === 'ArrowLeft') {
        // 处理上箭头按下事件
        this.handleSwitch('up')
      } else if (event.key === 'ArrowDown' || event.key === 'ArrowRight') {
        // 处理下箭头按下事件
        this.handleSwitch('down')
      }
    })
    const imgUrl = this.$getCookie('imgPath')
    // clearInterval(this.timer)
    let group = ''
    let activeIndex = ''
    let PACS_DeptNo = localStorage.getItem('PACS_DeptNo')
    let PACS_Index = localStorage.getItem('PACS_Index')
    // this.timer =
    setInterval(() => {
      var updateStatus = localStorage.getItem('is_update_Dicom')
      localStorage.setItem('is_update_Dicom', 2)
      // if(updateStatus == 1){
      //   window.location.reload()
      // }
      if (updateStatus == 1) {
        this.group = JSON.parse(localStorage.getItem('PACS_Dicom'))
      }
      this.activeIndex = localStorage.getItem('PACS_Dicom_active')
      if (group == this.group && activeIndex == this.activeIndex) {
        return
      }
      clearTimeout(this.countdown)
      this.countdown = setTimeout(() => {
        window.location.reload()
      }, 3600000)
      group = this.group
      activeIndex = this.activeIndex
      this.imageInfo = JSON.parse(this.group[this.activeIndex].dcmInfo)
      this.imgSrc = imgUrl + this.group[this.activeIndex].dcmsrc
      this.initCornerstoneTools()
      this.loadToolStates()
      if (PACS_DeptNo != localStorage.getItem('PACS_DeptNo') || PACS_Index != localStorage.getItem('PACS_Index')) {
        clearTimeout(this.countdown)
        window.location.reload()
      }
    }, 50)
    // **********************************缓存加载dicom文件
    this.ele2 = document.querySelector('#test2')
    cornerstone2.enable(this.ele2)
    let tempDate = ''
    let tempTimer = null
    setInterval(() => {
      if (localStorage.getItem('DRChange') == 'true') {
        let urlArray = JSON.parse(localStorage.getItem('DRList')) || []
        if (urlArray[0] == tempDate[0] && urlArray[urlArray.length - 1] == tempDate[tempDate.length - 1]) {
          return
        }
        tempDate = urlArray
        // 获取元素标签
        let urlIndex = 0
        if (tempTimer != null) {
          clearInterval(tempTimer)
          tempTimer = null
        }
        tempTimer = setInterval(() => {
          if (urlIndex <= urlArray.length - 1) {
            cornerstone2.loadAndCacheImage('wadouri:' + imgUrl + urlArray[urlIndex]).then((image) => {
              cornerstone2.displayImage(this.ele2, image)
            })
            urlIndex++
          } else {
            clearInterval(tempTimer)
          }
        }, 1500)
      } else {
        let urlArray = JSON.parse(localStorage.getItem('PACS_Dicom'))
        if (urlArray[0] && tempDate[0] && urlArray[0].id == tempDate[0].id && urlArray[urlArray.length - 1].id == tempDate[tempDate.length - 1].id) {
          return
        }
        tempDate = JSON.parse(localStorage.getItem('PACS_Dicom'))
        // 获取元素标签
        let urlIndex = 0
        if (tempTimer != null) {
          clearInterval(tempTimer)
          tempTimer = null
        }
        tempTimer = setInterval(() => {
          if (urlIndex <= urlArray.length - 1) {
            cornerstone2.loadAndCacheImage('wadouri:' + imgUrl + urlArray[urlIndex].dcmsrc).then((image) => {
              cornerstone2.displayImage(this.ele2, image)
            })
            urlIndex++
          } else {
            clearInterval(tempTimer)
          }
        }, 500)
      }
    }, 2000)
  },
  beforeDestroy() {
    this.clearToolState()
  },
  methods: {
    // 滑轮滚动切换图片
    handleMouseWheel(event) {
      const deltaY = event.deltaY
      // 获取当前图像的viewport
      const viewport = cornerstone.getViewport(this.ele)
      // 根据滚轮方向调整缩放比例
      if (deltaY > 0) {
        viewport.scale /= 1 + deltaY * 0.005
      } else {
        viewport.scale *= 1 - deltaY * 0.005
      }
      // 更新viewport并重新渲染图像
      cornerstone.setViewport(this.ele, viewport)
      cornerstone.updateImage(this.ele)
    },
    // 切换上一张/下一张
    handleSwitch(direction) {
      if (!this.isToggle) {
        this.isToggle = true
        setTimeout(() => {
          let change = false
          if (direction == 'down' && this.activeIndex < this.group.length - 1) {
            this.activeIndex = parseInt(this.activeIndex) + 1
            change = true
          } else if (direction == 'up' && this.activeIndex > 0) {
            this.activeIndex = parseInt(this.activeIndex) - 1
            change = true
          }
          if (change) {
            clearTimeout(this.countdown)
            this.countdown = setTimeout(() => {
              window.location.reload()
            }, 3600000)
            localStorage.setItem('PACS_Dicom_active', this.activeIndex)
            this.imgSrc = this.$getCookie('imgPath') + this.group[this.activeIndex].dcmsrc
            // this.isShowInfo = false
            this.initCornerstoneTools()
            this.loadToolStates()
          }
          this.isToggle = false
        }, 20)
      }
    },
    //激活标注工具
    cornerstoneToolsSelect(type) {
      this.cornerstoneToolsType = type
      this.activeTool(this.cornerstoneToolsType)
    },
    // 设置反色
    toggleInvert() {
      const viewport = cornerstone.getViewport(this.ele)
      viewport.invert = !viewport.invert
      cornerstone.setViewport(this.ele, viewport)
      cornerstone.updateImage(this.ele)
    },
    // 获取图像信息
    getDicomInfo() {
      var data = {
        dcmsrc: this.group[this.activeIndex].dcmsrc,
      }
      getDicomInfo(data).then((res) => {
        this.imageInfo = res.data
      })
    },
    // 获取星期
    getWeekDay(date) {
      const days = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六']
      const selectedDate = new Date(date)
      const weekDay = selectedDate.getDay()
      return days[weekDay]
    },
    // 显示图像信息
    showInfo() {
      this.isShowInfo = !this.isShowInfo
      // if (this.isShowInfo) {
      //   this.getDicomInfo()
      // }
    },
    // 保存图片
    saveImage() {
      const canvas = cornerstone.getEnabledElement(this.ele).canvas
      const dataUrl = canvas.toDataURL('image/png')
      // 创建一个隐藏的链接元素
      const link = document.createElement('a')
      link.href = dataUrl
      link.download = 'image.png'
      // 触发点击事件以下载图像
      link.click()
    },
    // 初始化标注工具
    initCornerstoneTools(isFirst) {
      cornerstoneTools.init({
        showSVGCursors: true,
      })
      // 获取元素标签
      this.ele = document.querySelector('#test')
      cornerstone.enable(this.ele)
      // 加载图像
      this.loadWebImg(this.imgSrc)
      if (isFirst == 1) {
        // 添加方形选框工具
        this.addTools()
        this.activeTool(this.cornerstoneToolsType)
        // 添加选框颜色
        cornerstoneTools.toolColors.setToolColor('green')
      }
    },
    // 重置
    reset() {
      this.clearToolState()
      this.openWid++
      this.$nextTick(() => {
        this.initCornerstoneTools()
        this.loadToolStates()
        this.addTools()
        this.$nextTick(() => {
          // 开启缩放标尺
          this.cornerstoneToolsSelect('ScaleOverlay')
          // 开启方向标记
          this.cornerstoneToolsSelect('OrientationMarkers')
          cornerstoneTools.setToolActive('Wwwc', { mouseButtonMask: 2 })
          cornerstoneTools.setToolActive('Pan', { mouseButtonMask: 4 })
        })
      })
    },
    // 加载图像
    loadWebImg(url) {
      cornerstone.loadAndCacheImage('wadouri:' + url).then((image) => {
        cornerstone.displayImage(this.ele, image)
        // 调整窗宽和窗位以显示灰度参数
        const viewport = cornerstone.getViewport(this.ele)
        viewport.voi.windowWidth = parseFloat(this.imageInfo.windowWidth) // 设置窗宽
        viewport.voi.windowCenter = parseFloat(this.imageInfo.windowCenter) // 设置窗位
        // viewport.invert = false
        cornerstone.setViewport(this.ele, viewport)
      })
    },
    // 添加选框工具
    addTools() {
      // 添加工具
      // 移动
      cornerstoneTools.addTool(cornerstoneTools.PanTool, this.ele)
      // 灰度
      cornerstoneTools.addTool(cornerstoneTools.WwwcTool, this.ele)
      // 旋转
      cornerstoneTools.addTool(cornerstoneTools.RotateTool, this.ele)
      // 缩放
      cornerstoneTools.addTool(cornerstoneTools.ZoomTool, this.ele)
      // 放大
      cornerstoneTools.addTool(cornerstoneTools.MagnifyTool, this.ele)
      // 缩放叠加标记线
      cornerstoneTools.addTool(cornerstoneTools.ScaleOverlayTool, this.ele)
      // 方向标记
      cornerstoneTools.addTool(cornerstoneTools.OrientationMarkersTool, this.ele)
      // 测距
      cornerstoneTools.addTool(cornerstoneTools.LengthTool, this.ele)
      // 箭头注释
      cornerstoneTools.addTool(cornerstoneTools.ArrowAnnotateTool, this.ele)
      // 方形选框
      cornerstoneTools.addTool(cornerstoneTools.RectangleRoiTool, this.ele)
      // 角度
      cornerstoneTools.addTool(cornerstoneTools.AngleTool, this.ele)
      // 椭圆选框
      cornerstoneTools.addTool(cornerstoneTools.EllipticalRoiTool, this.ele)
      // 自定义选框
      cornerstoneTools.addTool(cornerstoneTools.FreehandRoiTool, this.ele)
      // 橡皮擦
      cornerstoneTools.addTool(cornerstoneTools.EraserTool, this.ele)
    },
    // 激活工具
    activeTool(toolName) {
      // { mouseButtonMask: 1 }  //鼠标左键点击触发
      // { mouseButtonMask: 2 }  //鼠标右键点击触发
      // { mouseButtonMask: 4 }  //鼠标滚轮键点击触发
      // 启用工具
      cornerstoneTools.setToolActive(toolName, { mouseButtonMask: 1 })
    },
    // 清除工具
    clearToolState() {
      if (!this.ele) return
      cornerstoneTools.clearToolState(this.ele, 'ArrowAnnotate')
      cornerstoneTools.clearToolState(this.ele, 'Length')
      cornerstoneTools.clearToolState(this.ele, 'Angle')
      cornerstoneTools.clearToolState(this.ele, 'RectangleRoi')
      cornerstoneTools.clearToolState(this.ele, 'EllipticalRoi')
      cornerstoneTools.clearToolState(this.ele, 'FreehandRoi')
      cornerstoneTools.clearToolState(this.ele, 'Eraser')

      cornerstone.updateImage(this.ele)
    },
    // 保存工具状态
    saveToolData() {
      let data = cornerstoneTools.getToolState(this.ele, this.cornerstoneToolsType) ? JSON.parse(JSON.stringify(cornerstoneTools.getToolState(this.ele, this.cornerstoneToolsType).data)) : null
      if (!data) return
      this.markeData[this.group[this.activeIndex].id] = data
    },
    // 加载工具状态
    loadToolStates() {
      const toolDatas = this.markeData[this.group[this.activeIndex].id]
      // const toolDatas = this.markeData[[this.markeImgName]]
      if (!toolDatas) return
      setTimeout(() => {
        toolDatas.forEach((data) => {
          cornerstoneTools.addToolState(this.ele, this.cornerstoneToolsType, data)
        })
        cornerstone.updateImage(this.ele)
      }, 300)
    },
  },
}
</script>

<style lang="scss" scoped>
.Container {
  overflow: hidden;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.second-tool-bar {
  display: flex;
  align-items: center;
  width: 100%;
  padding: 5px;
  background-color: #e8ebee;
  overflow-x: auto;
  .section-btn-style2 {
    font-size: 18px;
  }
}

.mainBar {
  flex: 1;
  position: relative;

  .img-container {
    width: 100%;
    height: 100%;
  }

  .img-info {
    position: absolute;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 8px;

    p,
    span {
      font-size: 24px;
      color: #fff;
      line-height: 26px;
      margin: 0;
    }

    &.left-top {
      left: 0;
      top: 0;
    }

    &.right-top {
      right: 0;
      top: 0;
      align-items: flex-end;
    }

    &.right-bottom {
      right: 0;
      bottom: 0;
      align-items: flex-end;
    }

    &.left-bottom {
      left: 0;
      bottom: 0;
    }
  }
}

.buttonContainer {
  height: 70vh;
  //border: 1px solid $menu-blue-color;
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  .radioItem {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    height: 24px;
    line-height: 24px;

    .radioBlock {
      display: inline-block;
      width: 12px;
      height: 12px;
      border-radius: 50%;
    }
  }

  .checkItem {
    display: block;
    height: 24px;
    line-height: 24px;
  }

  .buttonBar {
    .el-row + .el-row {
      margin-top: 10px;
    }
  }
}
</style>
