<!-- 团检报告审批-综合样本分析-生成报告  开发人：麦沃德科技半夏 -->
<template>
  <div class="main-report-group">
    <!-- 图表 -->
    <div class="content-chart">
      <div class="chart-box" id="people1" style="width: 300px; height: 150px"></div>
      <div class="chart-box" id="people2" style="width: 300px; height: 150px"></div>
      <div class="chart-box" id="people3" style="width: 300px; height: 330px"></div>
      <div class="chart-box" id="age1" style="width: 610px; height: 256px"></div>
      <div class="chart-box" id="age2" style="width: 293px; height: 256px"></div>
      <div class="chart-box" id="age3" style="width: 293px; height: 256px"></div>
      <div class="chart-box" id="age4" style="width: 456px; height: 256px"></div>
      <div class="chart-box" id="check1" style="width: 610px; height: 196px"></div>
      <div class="chart-box" id="check2" style="width: 610px; height: 196px"></div>
      <div class="chart-box" id="check3" style="width: 610px; height: 196px"></div>
    </div>
  </div>
</template>
<script>
import { getPersonnelList, getAgeList, getAnalyzeList, saveAllImgJk } from '@/api/groupreport/audit_analyze'
import * as echarts from 'echarts'

import { uploadFile } from '@/api/funcdept/section_list/dialog.js'
import { createReportData } from '@/api/preview/group_report.js'
export default {
  data() {
    return {
      // 人员构成数据
      tableList: [],
      // 年龄分布数据
      tableList2: [],
      // 检出统计数据
      tableList3: [],
      // 上传的图片路径
      picPath: {
        reportId: '',
        pic_inspect_m: '',
        pic_inspect_w: '',
        pic_inspect_t: '',
        pic_age_columnar: '',
        pic_age_m: '',
        pic_age_w: '',
        pic_age_pie: '',
        exception_m: '',
        exception_w: '',
        exception_t: '',
      },
      // 上传加载中
      loading: null,
    }
  },
  created() {
    this.picPath.reportId = this.$route.query.id
    this.getList()
    this.getList2()
    this.getChartList()
  },
  methods: {
    // 人员构成
    // 查询列表
    getList() {
      getPersonnelList(this.$route.query.id).then((response) => {
        this.tableList = response.data
        this.initCharts()
      })
    },
    // 基于准备好的dom，初始化echarts图表
    initCharts() {
      var data = this.tableList
      var people1 = echarts.init(document.getElementById('people1'))
      var people2 = echarts.init(document.getElementById('people2'))
      var people3 = echarts.init(document.getElementById('people3'))

      var option1 = {
        title: {
          text: '男性人数',
          subtext: '',
          left: 'right',
        },
        tooltip: {
          trigger: 'axis',
        },
        legend: {
          orient: 'vertical',
          left: 'right',
          top: 'bottom',
          data: [
            {
              name: data[0]['boy1'] + '  A.计划',
            },
            {
              name: data[1]['boy1'] + '  B.参检',
            },
            {
              name: data[2]['boy1'] + '  C.未检',
            },
          ],
          textStyle: {
            color: '#fft',
          },
        },
        grid: {
          top: '20%',
          left: 0,
          right: '30%',
          bottom: 1,
          containLabel: true,
        },
        xAxis: {
          type: 'category',
          data: [''],
        },
        yAxis: {
          type: 'value',
          minInterval: 1,
        },
        series: [
          {
            name: data[0]['boy1'] + '  A.计划',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#41E4BB',
              },
            },
            data: [
              {
                name: '男性人数',
                value: data[0]['boy1'],
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 24,
                    },
                  },
                },
              },
            ],
          },
          {
            name: data[1]['boy1'] + '  B.参检',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#1890FF',
              },
            },
            data: [
              {
                value: data[1]['boy1'],
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 24,
                    },
                  },
                },
              },
            ],
          },
          {
            name: data[2]['boy1'] + '  C.未检',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#FF7A00',
              },
            },
            data: [
              {
                value: data[2]['boy1'],
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 24,
                    },
                  },
                },
              },
            ],
          },
        ],
      }
      people1.setOption(option1)

      var option2 = {
        title: {
          text: '女性人数',
          subtext: '',
          left: 'right',
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'none',
          },
        },
        legend: {
          orient: 'vertical',
          left: 'right',
          top: 'bottom',
          data: [
            {
              name: data[0]['girl1'] + '  A.计划',
            },
            {
              name: data[1]['girl1'] + '  B.参检',
            },
            {
              name: data[2]['girl1'] + '  C.未检',
            },
          ],
          textStyle: {
            color: '#fft',
          },
        },
        grid: {
          top: '20%',
          left: 0,
          right: '30%',
          bottom: 1,
          containLabel: true,
        },
        xAxis: {
          type: 'category',
          data: [''],
        },
        yAxis: {
          type: 'value',
          minInterval: 1,
        },
        series: [
          {
            name: data[0]['girl1'] + '  A.计划',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#41E4BB',
              },
            },
            data: [
              {
                name: '女性人数',
                value: data[0]['girl1'],
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 24,
                    },
                  },
                },
              },
            ],
          },
          {
            name: data[1]['girl1'] + '  B.参检',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#1890FF',
              },
            },
            data: [
              {
                value: data[1]['girl1'],
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 24,
                    },
                  },
                },
              },
            ],
          },
          {
            name: data[2]['girl1'] + '  C.未检',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#FF7A00',
              },
            },
            data: [
              {
                value: data[2]['girl1'],
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 24,
                    },
                  },
                },
              },
            ],
          },
        ],
      }
      people2.setOption(option2)

      var option3 = {
        title: {
          text: '合计人数',
          subtext: '',
        },
        tooltip: {
          trigger: 'axis',
        },
        legend: {
          top: 28,
          right: 0,
          data: [
            {
              name: data[0]['total1'] + '  A.计划',
            },
            {
              name: data[1]['total1'] + '  B.参检',
            },
            {
              name: data[2]['total1'] + '  C.未检',
            },
          ],
          textStyle: {
            color: '#fft',
          },
        },
        grid: {
          top: 80,
          left: 0,
          right: 0,
          bottom: 1,
          containLabel: true,
        },
        xAxis: {
          type: 'category',
          data: [''],
        },
        yAxis: {
          type: 'value',
          minInterval: 1,
        },
        series: [
          {
            name: data[0]['total1'] + '  A.计划',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#41E4BB',
              },
            },
            data: [
              {
                name: '合计人数',
                value: data[0]['total1'],
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 24,
                    },
                  },
                },
              },
            ],
          },
          {
            name: data[1]['total1'] + '  B.参检',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#1890FF',
              },
            },
            data: [
              {
                value: data[1]['total1'],
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 24,
                    },
                  },
                },
              },
            ],
          },
          {
            name: data[2]['total1'] + '  C.未检',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#FF7A00',
              },
            },
            data: [
              {
                value: data[2]['total1'],
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 24,
                    },
                  },
                },
              },
            ],
          },
        ],
      }
      people3.setOption(option3)
    },

    // 年龄分布
    // 查询列表
    getList2() {
      getAgeList(this.$route.query.id).then((response) => {
        this.tableList2 = response.data
        this.initCharts2()
      })
    },
    // 基于准备好的dom，初始化echarts图表
    initCharts2() {
      var data = this.tableList2
      var name = new Array(data.length)
      var att1 = new Array(data.length)
      for (var i = 0; i < data.length; i++) {
        name[i] = data[i]['agegroup']
        att1[i] = data[i]['boy1']
      }
      let ageArray = []
      name.forEach((el) => {
        let age = el.split('~')
        if (age[0] >= 20 && age[0] <= 60) {
          ageArray.push(el)
        }
      })
      var att2 = new Array(data.length)
      for (var i = 0; i < data.length; i++) {
        att2[i] = data[i]['girl1']
      }
      var att3 = new Array(data.length)
      for (var i = 0; i < data.length; i++) {
        att3[i] = data[i]['total1']
      }
      var age1 = echarts.init(document.getElementById('age1'))
      var age2 = echarts.init(document.getElementById('age2'))
      var age3 = echarts.init(document.getElementById('age3'))
      var age4 = echarts.init(document.getElementById('age4'))
      var option1 = {
        title: {
          text: '男女合计各年龄段构成',
          subtext: '',
          top: 'bottom',
          left: 'center',
        },
        tooltip: {
          trigger: 'axis',
        },
        grid: {
          top: 30,
          left: 0,
          right: 0,
          bottom: 30,
          containLabel: true,
        },
        legend: {
          data: ['人数统计'],
        },
        xAxis: [
          {
            type: 'category',
            data: name,
          },
        ],
        yAxis: [
          {
            type: 'value',
            minInterval: 1,
          },
        ],
        series: [
          {
            type: 'bar',
            data: att3,
            label: {
              normal: {
                show: true,
                position: 'top',
                textStyle: {
                  fontSize: 24,
                },
              },
            },
            itemStyle: {
              normal: {
                color: function (value) {
                  return '#' + ('00000' + ((Math.random() * 16777215 + 0.5) >> 0).toString(16)).slice(-6)
                },
              },
            },
          },
        ],
      }
      age1.setOption(option1)

      var option2 = {
        animation: false,
        title: {
          left: 'center',
          text: '男性',
        },
        tooltip: {
          show: true,
        },
        legend: {
          orient: 'vertical',
          left: 'right',
          top: 'center',
          textStyle: {
            color: '#fft',
          },
          data: ageArray,
          formatter: function (name1) {
            let num = undefined
            name.forEach((el, index) => {
              if (el == name1) {
                num = att1[index]
              }
            })
            return name1 + ' ' + num
          },
        },
        series: [
          {
            type: 'pie',
            radius: '45%',
            center: ['40%', '56%'],
            data: (function () {
              var res = []
              var len = name.length
              while (len--) {
                res.push({
                  name: name[len],
                  value: att1[len],
                })
              }
              return res
            })(),
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)',
              },
            },
          },
        ],
      }
      age2.setOption(option2)

      var option3 = {
        animation: false,
        title: {
          left: 'center',
          text: '女性',
        },
        tooltip: {
          show: true,
        },
        legend: {
          orient: 'vertical',
          left: 'right',
          top: 'center',
          textStyle: {
            color: '#fft',
          },
          data: ageArray,
          formatter: function (name1) {
            let num = undefined
            name.forEach((el, index) => {
              if (el == name1) {
                num = att2[index]
              }
            })
            return name1 + ' ' + num
          },
        },
        series: [
          {
            type: 'pie',
            radius: '45%',
            center: ['40%', '56%'],
            data: (function () {
              var res = []
              var len = name.length
              while (len--) {
                res.push({
                  name: name[len],
                  value: att2[len],
                })
              }
              return res
            })(),
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)',
              },
            },
          },
        ],
      }
      age3.setOption(option3)

      var option4 = {
        animation: false,
        title: {
          left: 'center',
          text: '合计',
        },
        tooltip: {
          show: true,
        },
        legend: {
          orient: 'vertical',
          left: 'right',
          top: 'center',
          textStyle: {
            color: '#fft',
          },
          data: ageArray,
          formatter: function (name1) {
            let num = undefined
            name.forEach((el, index) => {
              if (el == name1) {
                num = att3[index]
              }
            })
            return name1 + ' ' + num
          },
        },
        series: [
          {
            type: 'pie',
            radius: '56%',
            center: ['50%', '56%'],
            data: (function () {
              var res = []
              var len = name.length
              while (len--) {
                res.push({
                  name: name[len],
                  value: att3[len],
                })
              }
              return res
            })(),
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)',
              },
            },
          },
        ],
      }
      age4.setOption(option4)
    },

    // 检出统计
    // 查询统计图数据
    getChartList() {
      getAnalyzeList(this.$route.query.id, this.$route.query.groupId).then((response) => {
        this.tableList3 = response.data
        this.initCharts3()
      })
    },
    // 基于准备好的dom，初始化echarts图表
    initCharts3() {
      var data = this.tableList3
      var att1 = new Array(data.boy.length)
      for (var i = 0; i < data.boy.length; i++) {
        att1[i] = data.boy[i]['name']
      }
      var att2 = new Array(data.girl.length)
      for (var i = 0; i < data.girl.length; i++) {
        att2[i] = data.girl[i]['name']
      }
      var att3 = new Array(data.total.length)
      for (var i = 0; i < data.total.length; i++) {
        att3[i] = data.total[i]['name']
      }
      var check1 = echarts.init(document.getElementById('check1'))
      var check2 = echarts.init(document.getElementById('check2'))
      var check3 = echarts.init(document.getElementById('check3'))
      var option1 = {
        title: {
          text: '男性前十大异常结果(单位：人)',
        },
        tooltip: {
          show: true,
        },
        grid: {
          left: 0,
          right: 0,
          bottom: 1,
          containLabel: true,
        },
        legend: {
          data: [],
        },
        xAxis: [
          {
            type: 'category',
            data: [],
            axisLabel: {
              interval: 0,
              formatter: function (value) {
                var ret = '' //拼接加\n返回的类目项
                var maxLength = 4 //每项显示文字个数
                var valLength = value.length //X轴类目项的文字个数
                var rowN = Math.ceil(valLength / maxLength) //类目项需要换行的行数
                if (rowN > 1) {
                  //如果类目项的文字大于5,
                  for (var i = 0; i < rowN; i++) {
                    var temp = '' //每次截取的字符串
                    var start = i * maxLength //开始截取的位置
                    var end = start + maxLength //结束截取的位置
                    //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧
                    temp = value.substring(start, end) + '\n'
                    ret += temp //凭借最终的字符串
                  }
                  return ret
                } else {
                  return value
                }
              },
            },
          },
        ],
        yAxis: [
          {
            type: 'value',
            minInterval: 1,
          },
        ],
        series: [
          {
            type: 'bar',
            data: [],
            label: {
              normal: {
                show: true,
                position: 'top',
                textStyle: {
                  fontSize: 24,
                },
              },
            },
            itemStyle: {
              normal: {
                color: function (value) {
                  return '#' + ('00000' + ((Math.random() * 16777215 + 0.5) >> 0).toString(16)).slice(-6)
                },
              },
            },
          },
        ],
      }
      var option2 = {
        title: {
          text: '女性前十大异常结果(单位：人)',
        },
        tooltip: {
          show: true,
        },
        grid: {
          left: 0,
          right: 0,
          bottom: 1,
          containLabel: true,
        },
        legend: {
          data: [],
        },
        xAxis: [
          {
            type: 'category',
            data: [],
            axisLabel: {
              interval: 0,
              formatter: function (value) {
                var ret = '' //拼接加\n返回的类目项
                var maxLength = 4 //每项显示文字个数
                var valLength = value.length //X轴类目项的文字个数
                var rowN = Math.ceil(valLength / maxLength) //类目项需要换行的行数
                if (rowN > 1) {
                  //如果类目项的文字大于5,
                  for (var i = 0; i < rowN; i++) {
                    var temp = '' //每次截取的字符串
                    var start = i * maxLength //开始截取的位置
                    var end = start + maxLength //结束截取的位置
                    //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧
                    temp = value.substring(start, end) + '\n'
                    ret += temp //凭借最终的字符串
                  }
                  return ret
                } else {
                  return value
                }
              },
            },
          },
        ],
        yAxis: [
          {
            type: 'value',
            minInterval: 1,
          },
        ],
        series: [
          {
            type: 'bar',
            data: [],
            label: {
              normal: {
                show: true,
                position: 'top',
                textStyle: {
                  fontSize: 24,
                },
              },
            },
            itemStyle: {
              normal: {
                color: function (value) {
                  return '#' + ('00000' + ((Math.random() * 16777215 + 0.5) >> 0).toString(16)).slice(-6)
                },
              },
            },
          },
        ],
      }
      var option3 = {
        title: {
          text: '男女综合前十大异常结果(单位：人)',
        },
        tooltip: {
          show: true,
        },
        grid: {
          left: 0,
          right: 0,
          bottom: 1,
          containLabel: true,
        },
        legend: {
          data: [],
        },
        xAxis: [
          {
            type: 'category',
            data: [],
            axisLabel: {
              interval: 0,
              formatter: function (value) {
                var ret = '' //拼接加\n返回的类目项
                var maxLength = 4 //每项显示文字个数
                var valLength = value.length //X轴类目项的文字个数
                var rowN = Math.ceil(valLength / maxLength) //类目项需要换行的行数
                if (rowN > 1) {
                  //如果类目项的文字大于5,
                  for (var i = 0; i < rowN; i++) {
                    var temp = '' //每次截取的字符串
                    var start = i * maxLength //开始截取的位置
                    var end = start + maxLength //结束截取的位置
                    //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧
                    temp = value.substring(start, end) + '\n'
                    ret += temp //凭借最终的字符串
                  }
                  return ret
                } else {
                  return value
                }
              },
            },
          },
        ],
        yAxis: [
          {
            type: 'value',
            minInterval: 1,
          },
        ],
        series: [
          {
            type: 'bar',
            data: [],
            label: {
              normal: {
                show: true,
                position: 'top',
                textStyle: {
                  fontSize: 24,
                },
              },
            },
            itemStyle: {
              normal: {
                color: function (value) {
                  return '#' + ('00000' + ((Math.random() * 16777215 + 0.5) >> 0).toString(16)).slice(-6)
                },
              },
            },
          },
        ],
      }
      option1.series[0].data = data.boy
      option2.series[0].data = data.girl
      option3.series[0].data = data.total
      option1.xAxis[0].data = att1
      option2.xAxis[0].data = att2
      option3.xAxis[0].data = att3
      check1.setOption(option1)
      check2.setOption(option2)
      check3.setOption(option3)
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

    // 生成图片
    handleCreatePic() {
      this.loading = this.$loading({
        lock: true,
        text: '图片上传中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.1)',
      })
      const base64String = echarts.init(document.getElementById('people1')).getDataURL()
      // 创建 FormData 对象，用于上传文件
      const file = this.base64ToFile(base64String, 'chart.png')
      const formData = new FormData()
      formData.append('file', file)
      uploadFile(formData).then(({ data }) => {
        this.picPath.pic_inspect_m = data
        this.handleUploadImg()
      })
      const base64String2 = echarts.init(document.getElementById('people2')).getDataURL()
      // 创建 FormData 对象，用于上传文件
      const file2 = this.base64ToFile(base64String2, 'chart.png')
      const formData2 = new FormData()
      formData2.append('file', file2)
      uploadFile(formData2).then(({ data }) => {
        this.picPath.pic_inspect_w = data
        this.handleUploadImg()
      })
      const base64String3 = echarts.init(document.getElementById('people3')).getDataURL()
      // 创建 FormData 对象，用于上传文件
      const file3 = this.base64ToFile(base64String3, 'chart.png')
      const formData3 = new FormData()
      formData3.append('file', file3)
      uploadFile(formData3).then(({ data }) => {
        this.picPath.pic_inspect_t = data
        this.handleUploadImg()
      })
      const base64String4 = echarts.init(document.getElementById('age1')).getDataURL()
      // 创建 FormData 对象，用于上传文件
      const file4 = this.base64ToFile(base64String4, 'chart.png')
      const formData4 = new FormData()
      formData4.append('file', file4)
      uploadFile(formData4).then(({ data }) => {
        this.picPath.pic_age_columnar = data
        this.handleUploadImg()
      })
      const base64String5 = echarts.init(document.getElementById('age2')).getDataURL()
      // 创建 FormData 对象，用于上传文件
      const file5 = this.base64ToFile(base64String5, 'chart.png')
      const formData5 = new FormData()
      formData5.append('file', file5)
      uploadFile(formData5).then(({ data }) => {
        this.picPath.pic_age_m = data
        this.handleUploadImg()
      })
      const base64String6 = echarts.init(document.getElementById('age3')).getDataURL()
      // 创建 FormData 对象，用于上传文件
      const file6 = this.base64ToFile(base64String6, 'chart.png')
      const formData6 = new FormData()
      formData6.append('file', file6)
      uploadFile(formData6).then(({ data }) => {
        this.picPath.pic_age_w = data
        this.handleUploadImg()
      })
      const base64String7 = echarts.init(document.getElementById('age4')).getDataURL()
      // 创建 FormData 对象，用于上传文件
      const file7 = this.base64ToFile(base64String7, 'chart.png')
      const formData7 = new FormData()
      formData7.append('file', file7)
      uploadFile(formData7).then(({ data }) => {
        this.picPath.pic_age_pie = data
        this.handleUploadImg()
      })
      const base64String8 = echarts.init(document.getElementById('check1')).getDataURL()
      // 创建 FormData 对象，用于上传文件
      const file8 = this.base64ToFile(base64String8, 'chart.png')
      const formData8 = new FormData()
      formData8.append('file', file8)
      uploadFile(formData8).then(({ data }) => {
        this.picPath.exception_m = data
        this.handleUploadImg()
      })
      const base64String9 = echarts.init(document.getElementById('check2')).getDataURL()
      // 创建 FormData 对象，用于上传文件
      const file9 = this.base64ToFile(base64String9, 'chart.png')
      const formData9 = new FormData()
      formData9.append('file', file9)
      uploadFile(formData9).then(({ data }) => {
        this.picPath.exception_w = data
        this.handleUploadImg()
      })
      const base64String10 = echarts.init(document.getElementById('check3')).getDataURL()
      // 创建 FormData 对象，用于上传文件
      const file10 = this.base64ToFile(base64String10, 'chart.png')
      const formData10 = new FormData()
      formData10.append('file', file10)
      uploadFile(formData10).then(({ data }) => {
        this.picPath.exception_t = data
        this.handleUploadImg()
      })
    },
    // 上传图片并生成报告
    handleUploadImg() {
      if (this.picPath.pic_inspect_m && this.picPath.pic_inspect_w && this.picPath.pic_inspect_t && this.picPath.pic_age_columnar && this.picPath.pic_age_m && this.picPath.pic_age_w && this.picPath.pic_age_pie && this.picPath.exception_m && this.picPath.exception_w && this.picPath.exception_t) {
        this.loading.close()
        saveAllImgJk(this.picPath).then((res) => {
          this.picPath = {
            reportId: '',
            pic_inspect_m: '',
            pic_inspect_w: '',
            pic_inspect_t: '',
            pic_age_columnar: '',
            pic_age_m: '',
            pic_age_w: '',
            pic_age_pie: '',
            exception_m: '',
            exception_w: '',
            exception_t: '',
          }
          let data = {
            dh: this.$route.query.dh, //体检状态 0 健康 1 职业
            analyzeId: this.$route.query.id, //样本Id
            orderId: this.$route.query.ddh, //订单id
          }
          createReportData(data).then((response) => {
            if (response.msg.indexOf('成功') != -1) {
              this.$modal.alertSuccess('报告生成成功！', '提醒')
            } else {
              this.$modal.alertWarning(response.msg, '提醒')
            }
          })
        })
      }
    },
  },
}
</script>
<style lang="scss">
.main-report-group {
  .content-chart {
    display: flex;
    flex-wrap: wrap;
    .chart-box {
      background: #fff;
    }
  }
}
</style>
