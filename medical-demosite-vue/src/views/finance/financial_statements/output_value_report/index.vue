<!-- 产值报表 开发人：麦沃德科技清风/矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :inline="true" size="mini" id="setForm">
      <el-form-item label="时间筛选">
        <el-date-picker clearable value-format="yyyy-MM" v-model="query.startTime" type="month" placeholder="选择日期"> </el-date-picker>
      </el-form-item>
      <el-form-item label="对比日期">
        <el-date-picker style="" value-format="yyyy-MM" clearable v-model="query.endTime" type="month" placeholder="选择对比日期"> </el-date-picker>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getData" size="mini">搜索</el-button>
    </el-form>
    <!-- echarts -->
    <div style="height: 100%" id="setCharts">
      <!-- 循环图表--默认选中的前四条 -->
      <div style="height: 95%">
        <div class="charts-box" v-for="(item, index) in formData" :key="item.id">
          <template v-if="item">
            <el-form :inline="true" :model="item" size="mini" class="setLeftForm">
              <el-form-item style="margin: 12px 16px">
                <template slot="label">
                  <div class="setTextBasic">{{ item.optionsName }}</div>
                </template>
                <div class="box-tittle" style="color: #389cf2; font-size: 24px" v-if="item.form">{{ item.form.value }}</div>
              </el-form-item>
              <el-form-item style="margin: 12px 16px">
                <template slot="label">
                  <div class="setTextBasic">同比增长率：</div>
                </template>
                <div style="color: #91cd76; font-size: 24px" v-if="item.form">{{ item.form.growthRate }}</div>
              </el-form-item>
            </el-form>
          </template>
          <div class="echart" :id="'mychart' + `${index}`" :style="myChartStyle"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { getReport, getInspectOutputValue, getVaccinesOutputValue } from '@/api/finance/financial_statements/output_value_report.js'
export default {
  data() {
    return {
      //日期
      query: {
        startTime: undefined,
        endTime: undefined,
      },

      //筛选数组
      search: [],
      // searchOptions: ['体检产值(不含疫苗)', '疫苗产值', '产品产值', '中药产值'],
      searchOptions: ['体检产值(不含疫苗)', '疫苗产值'],
      formData: ['', ''],
      myChartStyle: { width: '100%', height: '85%' },
      checkOptions: [], //checkbox选用
      checkBoxNum: true,
    }
  },
  mounted() {
    // 开始时间戳
    const timeStamp = new Date().toLocaleDateString()
    let date = this.formatDate(timeStamp)
    this.query.startTime = date
    this.getData()
    for (var i = 0; i < this.searchOptions.length; i++) {
      this.search.push(this.searchOptions[i])
    }
  },
  methods: {
    getData() {
      if (this.query.startTime == this.query.endTime) {
        this.$modal.msgWarning('请选择不同的时间节点进行对比')
        return
      }
      getInspectOutputValue(this.query).then((response) => {
        this.formData[0] = response.data
        this.formData[0].form.growthRate = this.formData[0].form.growthRate + '%'
        this.formData = JSON.parse(JSON.stringify(this.formData))
        this.$nextTick(() => {
          this.initEcharts(0)
        })
      })
      getVaccinesOutputValue(this.query).then((response) => {
        this.formData[1] = response.data
        this.formData[1].form.growthRate = this.formData[1].form.growthRate + '%'
        this.formData = JSON.parse(JSON.stringify(this.formData))
        this.$nextTick(() => {
          this.initEcharts(1)
        })
      })
    },
    //获取年份方法
    formatDate(date) {
      date = new Date(date)
      let myyear = date.getFullYear()
      let mymonth = date.getMonth() + 1
      mymonth < 10 ? (mymonth = '0' + mymonth) : mymonth
      return `${myyear}-${mymonth}`
    },

    //折线图左上js
    initEcharts(i) {
      // 指定图表的配置项和数据
      var barOptions = this.formData[i].barOptions //x轴数据
      var showdata = this.formData[i].showdata //y轴数据
      var mychart = echarts.init(document.getElementById(`mychart${i}`))

      var option = {
        dataZoom: [
          {
            type: 'inside',
            startValue: barOptions[0],
            endValue: barOptions[7],
            minValueSpan: 7,
          },
        ],
        grid: {
          top: '45px',
          bottom: '2px',
          left: '2px',
          right: '40px',
          containLabel: true,
        },
        color: ['#41E4BB', '#1890FF'],
        tooltip: {
          trigger: 'axis',
        },
        //显示多个展示图图标
        toolbox: {
          show: true,
          feature: {
            magicType: { type: ['bar', 'line'] }, //设置点击图像切换展示类型数组
          },
        },
        xAxis: {
          type: 'category', //展示数据指向data
          boundaryGap: true, //是否留白
          data: barOptions,
          axisLabel: {
            interval: 0, //让数据全部显示
            showMinLabel: true,
            textStyle: {
              color: '#999999',
              fontSize: '14',
            },
          },
        },
        yAxis: [
          {
            type: 'value', //展示数据指向series
            name: '单位(万元)', //y轴标题
            nameTextStyle: {
              padding: [0, 50, 10, 0],
              verticalAlign: 'bottom',
            },
            splitLine: {
              show: true,
              lineStyle: {
                type: 'dashed', //设置为虚线
              },
            },
            axisLabel: {
              formatter: '{value}',
              textStyle: {
                color: '#999999',
                fontSize: '14',
              },
            },
            axisLine: { show: false },
            axisTick: { show: false },
          },
        ],
        series: [
          {
            type: 'bar',
            barWidth: '20px', //y项宽度
            itemStyle: {
              color: this.getColor(i),
            },
            data: showdata,
          },
        ],
      }
      if (this.formData[i].showdata2 != null) {
        var data = {
          type: 'bar',
          barWidth: '20px', //y项宽度
          itemStyle: {
            color: this.getColor(i),
          },
          data: this.formData[i].showdata2,
        }
        option.series.push(data)
      }
      mychart.setOption(option)
    },
    //渐变色设置
    getColor(i) {
      let color
      if (i == 0) {
        color = new echarts.graphic.LinearGradient(
          0,
          0,
          0,
          1,
          ['#7BBBF5', '#188DF0'].map((color, offset) => ({ color, offset }))
        )
      } else if (i % 4 == 1) {
        color = new echarts.graphic.LinearGradient(
          0,
          0,
          0,
          1,
          ['#F5987B', '#F01818'].map((color, offset) => ({ color, offset }))
        )
      }
      return color
    },
  },
}
</script>

<style scoped>
.app-container {
  width: auto;
  height: 100%;
}

.charts-box {
  display: inline-block;
  width: 100%;
  height: 50%;
  border: 1px solid #c4c4c4;
  margin: 4px;
  padding: 8px;
}

#setCharts {
  display: inline-block;
}

#setCharts /deep/ canvas {
  height: calc(100% - 10px) !important;
}

#setCharts /deep/ .setLeftForm {
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
}

#setCharts /deep/ .setTextBasic {
  font-size: 16px;
  font-weight: 600;
  color: #333333;
}

#setForm /deep/ .showText {
  display: inline-block;
  height: 28px;
  color: #666666;
  font-size: 16px;
  line-height: 28px;
  margin: 0 40px 0 16px;
}

#setForm /deep/ .setLabel {
  font-size: 16px;
  color: #333333;
}
</style>
