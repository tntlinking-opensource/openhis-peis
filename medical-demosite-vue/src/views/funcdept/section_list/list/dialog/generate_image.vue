<template>
  <el-dialog :title="type == 0 ? '生成图像' : '查看图像'" :visible.sync="open" width="980px" append-to-body class="generate-image" :close-on-click-modal="false">
    <div class="image-box" v-if="type == 1">
      <img class="chart" :src="srcPath + airImgPath" />
      <img class="chart" :src="srcPath + boneImgPath" />
    </div>
    <div class="image-box" v-else>
      <div class="chart" id="airChart"></div>
      <div class="chart" id="boneChart"></div>
    </div>
  </el-dialog>
</template>

<script>
import * as echarts from 'echarts'
import airLeft from '@/assets/audiometry/lanx.png'
import boneRight from '@/assets/audiometry/gy.png'
import boneLeft from '@/assets/audiometry/gz.png'

import { uploadFile } from '@/api/funcdept/section_list/dialog.js'
import { UploadImg } from '@/api/funcdept/section_list/electro_audiometry.js'
export default {
  data() {
    return {
      // 打开主弹窗
      open: false,
      // 表格加载中
      loading: false,
      // 图像数据
      chartData: {},
      // 体检类型
      idExamType: '',
      // 生成/查看
      type: 0,
      srcPath: this.$getCookie('imgPath'),
      // 图片
      airImgPath: '',
      boneImgPath: '',
      // 图片数据
      audiometry: {},
    }
  },
  methods: {
    // 生成图像
    handleMakeImage(value, idExamType, audiometry, auto) {
      this.type = 0
      this.open = true
      this.chartData = JSON.parse(value)
      this.idExamType = idExamType
      this.audiometry = audiometry
      this.$nextTick(() => {
        this.$modal.msgWarning('图片生成中,请稍等', '提示')
        this.initChart(auto)
      })
    },
    // 查看图像
    handleViewImage(airImgPath, boneImgPath) {
      this.type = 1
      this.open = true
      this.airImgPath = airImgPath
      this.boneImgPath = boneImgPath
    },
    // 初始化图表
    initChart(auto) {
      var yAxis = [-10, 0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 120]
      var data = this.chartData
      var xAxistop = ['125', '', '250', '', '500', '', '1k', '', '2k', '', '4k', '', '8k']
      var xAxisbottom = ['', '', '', '', '', '750', '', '1.5k', '', '3k', '', '6k', '']
      var air_chart, bone_chart
      if (this.idExamType === '0') {
        //气导
        var air_option = {
          animation: false,
          grid: {
            borderColor: '#3e3eff',
            borderWidth: 3,
            show: true,
            width: 400,
            height: 500,
            containLabel: true,
          },
          title: {
            text: '听力检测图（左耳）',
            right: '50%',
            top: '0%',
          },
          tooltip: {
            trigger: 'axis',
          },
          legend: {
            data: ['气左', '骨左'],
            right: '0%',
            top: '0%',
            selectedMode: false,
          },
          xAxis: [
            {
              type: 'category',
              boundaryGap: false,
              data: xAxisbottom,
              name: '',
              nameLocation: 'middle',
              nameGap: '30',
              splitLine: {
                show: false,
              },
              axisLabel: {
                interval: 0,
              },
            },
            {
              type: 'category',
              boundaryGap: false,
              data: xAxistop,
              name: '',
              nameLocation: 'middle',
              nameGap: '30',
              splitLine: {
                show: true,
              },
              axisLabel: {
                interval: 0,
              },
            },
          ],
          yAxis: {
            type: 'value',
            data: yAxis,
            min: '-10',
            max: '120',
            name: '听力阈值（dB）',
            nameLocation: 'middle',
            nameGap: '30',
            splitNumber: '10',
            inverse: true,
          },
          series: [
            {
              name: '气左',
              type: 'line',
              symbolSize: 20,
              connectNulls: true,
              xAxisIndex: 1,
              symbol: 'image://' + airLeft,
              data: this.getHealthAir(data.air_left),
              itemStyle: {
                normal: {
                  borderType: 'solid',
                  lineStyle: {
                    color: 'blue',
                  },
                },
              },
            },
            {
              name: '骨左',
              type: 'line',
              symbolSize: 20,
              xAxisIndex: 0,
              connectNulls: true,
              data: this.getHealthBone(data.bone_left),
              symbol: 'image://' + boneRight,
              itemStyle: {
                normal: {
                  borderType: 'solid',
                  lineStyle: {
                    color: 'blue',
                  },
                },
              },
            },
          ],
          textStyle: {
            fontSize: 20,
          },
          animation: false,
        }
        air_chart = echarts.init(document.getElementById('airChart'))
        air_chart.setOption(air_option)

        //右耳
        var bone_option = {
          animation: false,
          grid: {
            borderColor: 'red',
            borderWidth: 3,
            show: true,
            width: 400,
            height: 500,
            containLabel: true,
          },
          title: {
            text: '听力检测图（右耳）',
            right: '50%',
            top: '0%',
          },
          tooltip: {
            trigger: 'axis',
          },
          legend: {
            data: ['气右', '骨右'],
            right: '0%',
            top: '0%',
            selectedMode: false,
          },
          xAxis: [
            {
              type: 'category',
              boundaryGap: false,
              data: xAxisbottom,
              name: '',
              nameLocation: 'middle',
              nameGap: '30',
              axisLabel: {
                show: true,
                interval: 0,
              },
              splitLine: {
                show: false,
              },
            },
            {
              type: 'category',
              boundaryGap: false,
              data: xAxistop,
              name: '',
              nameLocation: 'middle',
              nameGap: '30',
              axisLabel: {
                show: true,
                interval: 0,
              },
              splitLine: {
                show: true,
              },
            },
          ],
          yAxis: {
            type: 'value',
            data: yAxis,
            min: '-10',
            max: '120',
            name: '听力阈值（dB）',
            nameLocation: 'middle',
            nameGap: '30',
            splitNumber: '10',
            inverse: true,
            axisLabel: {
              show: true,
            },
          },
          series: [
            {
              name: '气右',
              type: 'line',
              data: this.getHealthAir(data.air_right),
              symbolSize: 20,
              connectNulls: true,
              xAxisIndex: 1,
              itemStyle: {
                normal: {
                  borderType: 'solid',
                  lineStyle: {
                    color: 'red',
                  },
                  color: 'red',
                },
              },
            },
            {
              name: '骨右',
              type: 'line',
              data: this.getHealthBone(data.bone_right),
              symbolSize: 20,
              connectNulls: true,
              xAxisIndex: 0,
              symbol: 'image://' + boneLeft,
              itemStyle: {
                normal: {
                  borderType: 'solid',
                  lineStyle: {
                    color: 'red',
                  },
                },
              },
            },
          ],
          textStyle: {
            fontSize: 20,
          },
          animation: false,
        }
        bone_chart = echarts.init(document.getElementById('boneChart'))
        bone_chart.setOption(bone_option)

        // echarts图转化为图片并上传
        this.$nextTick(() => {
          let leftPath = ''
          let rightPath = ''
          const base64String = air_chart.getDataURL()
          // 创建 FormData 对象，用于上传文件
          const file = this.base64ToFile(base64String, 'chart.png')
          const formData = new FormData()
          formData.append('file', file)
          uploadFile(formData).then(({ data }) => {
            leftPath = data
            this.handleUploadImg(leftPath, rightPath, auto)
          })

          // echarts图转化为图片
          const base64StringR = bone_chart.getDataURL()
          // 创建 FormData 对象，用于上传文件
          const fileR = this.base64ToFile(base64StringR, 'chart2.png')
          const formDataR = new FormData()
          formDataR.append('file', fileR)
          setTimeout(() => {
            uploadFile(formDataR).then(({ data }) => {
              rightPath = data
              this.handleUploadImg(leftPath, rightPath, auto)
            })
          }, 810)
        })
      } else {
        //左耳
        var air_option = {
          grid: {
            borderColor: '#3e3eff',
            borderWidth: 3,
            show: true,
            width: 400,
            height: 500,
            containLabel: true,
          },
          title: {
            text: '听力检测图（左耳）',
            right: '50%',
            top: '0%',
          },
          tooltip: {
            trigger: 'axis',
          },
          legend: {
            data: ['气左', '骨左'],
            right: '0%',
            top: '0%',
            selectedMode: false,
          },
          xAxis: [
            {
              type: 'category',
              boundaryGap: false,
              data: xAxisbottom,
              name: '',
              nameLocation: 'middle',
              nameGap: '30',
              splitLine: {
                show: false,
              },
              axisLabel: {
                interval: 0,
              },
            },
            {
              type: 'category',
              boundaryGap: false,
              data: xAxistop,
              name: '',
              nameLocation: 'middle',
              nameGap: '30',
              splitLine: {
                show: true,
              },
              axisLabel: {
                interval: 0,
              },
            },
          ],
          yAxis: {
            type: 'value',
            data: yAxis,
            min: '-10',
            max: '120',
            name: '听力阈值（dB）',
            nameLocation: 'middle',
            nameGap: '30',
            splitNumber: '10',
            inverse: true,
          },
          series: [
            {
              name: '气左',
              type: 'line',
              symbolSize: 20,
              connectNulls: true,
              symbol: 'image://' + airLeft,
              data: this.getProAir(data.air_left),
              xAxisIndex: 1,
              itemStyle: {
                normal: {
                  borderType: 'solid',
                  lineStyle: {
                    color: 'blue',
                  },
                },
              },
            },
            {
              name: '骨左',
              type: 'line',
              connectNulls: true,
              symbolSize: 20,
              data: this.getProBone(data.bone_left),
              xAxisIndex: 0,
              symbol: 'image://' + boneRight,
              itemStyle: {
                normal: {
                  borderType: 'solid',
                  lineStyle: {
                    color: 'blue',
                  },
                },
              },
            },
          ],
          textStyle: {
            fontSize: 20,
          },
          animation: false,
        }
        air_chart = echarts.init(document.getElementById('airChart'))
        air_chart.setOption(air_option)
        //右耳
        var bone_option = {
          animation: false,
          grid: {
            borderColor: 'red',
            borderWidth: 3,
            show: true,
            width: 400,
            height: 500,
            containLabel: true,
          },
          title: {
            text: '听力检测图（右耳）',
            right: '50%',
            top: '0%',
          },
          tooltip: {
            trigger: 'axis',
          },
          legend: {
            data: ['气右', '骨右'],
            right: '0%',
            top: '0%',
            selectedMode: false,
          },
          xAxis: [
            {
              type: 'category',
              boundaryGap: false,
              data: xAxisbottom,
              name: '',
              nameLocation: 'middle',
              nameGap: '30',
              splitLine: {
                show: false,
              },
              axisLabel: {
                interval: 0,
              },
            },
            {
              type: 'category',
              boundaryGap: false,
              data: xAxistop,
              name: '',
              nameLocation: 'middle',
              nameGap: '30',
              splitLine: {
                show: true,
              },
              axisLabel: {
                interval: 0,
              },
            },
          ],
          yAxis: {
            type: 'value',
            data: yAxis,
            min: '-10',
            max: '120',
            name: '听力阈值（dB）',
            nameLocation: 'middle',
            nameGap: '30',
            splitNumber: '10',
            inverse: true,
          },
          series: [
            {
              name: '气右',
              type: 'line',
              connectNulls: true,
              data: this.getProAir(data.air_right),
              symbolSize: 20,
              xAxisIndex: 1,
              itemStyle: {
                normal: {
                  borderType: 'solid',
                  lineStyle: {
                    color: 'red',
                  },
                  color: 'red',
                },
              },
            },
            {
              name: '骨右',
              type: 'line',
              connectNulls: true,
              xAxisIndex: 0,
              data: this.getProBone(data.bone_right),
              symbolSize: 20,
              symbol: 'image://' + boneLeft,
              itemStyle: {
                normal: {
                  borderType: 'solid',
                  lineStyle: {
                    color: 'red',
                  },
                },
              },
            },
          ],
          textStyle: {
            fontSize: 20,
          },
        }
        bone_chart = echarts.init(document.getElementById('boneChart'))
        bone_chart.setOption(bone_option)

        // echarts图转化为图片并上传
        this.$nextTick(() => {
          let leftPath = ''
          let rightPath = ''
          const base64String = air_chart.getDataURL()
          // 创建 FormData 对象，用于上传文件
          const file = this.base64ToFile(base64String, 'chart.png')
          const formData = new FormData()
          formData.append('file', file)
          uploadFile(formData).then(({ data }) => {
            leftPath = data
            this.handleUploadImg(leftPath, rightPath, auto)
          })

          // echarts图转化为图片
          const base64StringR = bone_chart.getDataURL()
          // 创建 FormData 对象，用于上传文件
          const fileR = this.base64ToFile(base64StringR, 'chart2.png')
          const formDataR = new FormData()
          formDataR.append('file', fileR)
          setTimeout(() => {
            uploadFile(formDataR).then(({ data }) => {
              rightPath = data
              this.handleUploadImg(leftPath, rightPath, auto)
            })
          }, 810)
        })
      }
    },
    base64ToFile(base64, fileName) {
      const arr = base64.split(',')
      const mime = arr[0].match(/:(.*?);/)[1]
      const bstr = atob(arr[1])
      let n = bstr.length
      const u8arr = new Uint8Array(n)
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n)
      }
      return new File([u8arr], fileName, { type: mime })
    },
    // 上传生成的图片
    handleUploadImg(airImgPath, boneImgPath, auto) {
      if (airImgPath && boneImgPath) {
        let data = {
          ...this.audiometry,
          airImgPath,
          boneImgPath,
        }
        UploadImg(data).then(() => {
          this.bus.$emit('electroUpload', auto)
        })
      }
    },
    getHealthAir(data) {
      return data ? [data[0], '', data[1], '', data[2], '', data[3], '', data[4], '', data[5], '', data[6]] : data
    },
    getHealthBone(data) {
      return data ? ['', '', data[1], '', data[2], '', data[3], '', data[4], '', data[5], '', ''] : data
    },
    getProAir(data) {
      return data ? ['', '', '', '', data[0], '', data[1], '', data[2], data[3], data[4], data[5], ''] : data
    },
    getProBone(data) {
      return data ? ['', '', '', '', data[0], '', data[1], '', data[2], data[3], data[4], data[5], ''] : data
    },
  },
}
</script>

<style lang="scss">
.generate-image {
  .image-box {
    display: flex;
    justify-content: space-between;

    .chart {
      width: 460px;
      height: 700px;
    }
  }
}
</style>
