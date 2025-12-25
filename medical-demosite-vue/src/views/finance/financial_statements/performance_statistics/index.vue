<!-- 线下业绩统计 开发人：麦沃德科技矢北 -->
<template>
  <div class="app-container flex-direction-column" style="padding: 12px 0">
    <el-form :inline="true" size="mini" :model="form" style="border-bottom: 8px solid rgb(246, 247, 251); padding: 0 20px" class="no-margin-bottom">
      <el-form-item label="时间筛选">
        <el-date-picker value-format="yyyy-MM" clearable v-model="form.date" type="month" placeholder="选择日期"> </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-checkbox-group :max="3" v-model="search">
          <el-checkbox v-for="item in checkList" @change="changeEvent" :key="item.typeId" class="setLabel" :label="item.typeName"></el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="getChartsListData">搜索</el-button>
      </el-form-item>
    </el-form>
    <div class="echartsParents">
      <div class="right-charts">
        <div class="charts-box-top">
          <div class="echart" id="inCome" :style="myChartStyle"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { getMealList, getListData } from '@/api/finance/financial_statements/performance_statistics.js'
export default {
  data() {
    return {
      checkList: [],
      form: {
        date: undefined,
      },
      queryData: {
        ids: [],
      },
      search: [],
      ///搜索的时间
      inCome: {
        barOptions: [],
        income2022: [],
        number2022: [],
        income2023: [],
        number2023: [],
      },
      inCome2: {
        barOptions: [],
        item1: [],
        item2: [],
        item3: [],
      },

      i: 0,
      //年份
      year: 0,
      item1Name: '',
      item2Name: '',
      item3Name: '',
      myChartStyle: { width: '100%', height: '100%' },
    }
  },
  mounted() {
    //  当月开始时间戳
    const timeStamp = new Date().toLocaleDateString()
    const MonthStart = new Date(timeStamp).getTime()
    this.form.date = this.$getDate(MonthStart).substring(0, 7)
    this.handleQuery()
    this.getChartsListData()
  },
  methods: {
    reset() {},
    getChartsListData() {
      var queryData = {
        ids: [],
        time: this.form.date + '-01 00::00::00',
      }
      //获取查询业务类别id
      for (var i in this.checkList) {
        for (var j in this.search) {
          if (this.checkList[i].typeName == this.search[j]) {
            queryData.ids.push(this.checkList[i].typeId)
          }
        }
      }
      getListData(queryData).then((response) => {
        this.inCome2 = {
          item1: [],
          item2: [],
          item3: [],
          fzx: [],
        }
        var fzx = [],
          item1 = [],
          item2 = [],
          item3 = []
        if (response.data.option) {
          for (var i in response.data.option) {
            if (i == 0) {
              var data1 = response.data.option[i]
              for (var j in data1) {
                fzx.push(data1[j].fzx)
                item1.push(data1[j].amount)
                this.item1Name = data1[j].typeName
              }
            } else if (i == 1) {
              var data2 = response.data.option[i]
              for (var j in data2) {
                item2.push(data2[j].amount)
                this.item2Name = data2[j].typeName
              }
            } else if (i == 2) {
              var data3 = response.data.option[i]
              for (var j in data3) {
                item3.push(data3[j].amount)
                this.item3Name = data3[j].typeName
              }
            }
          }
          //解析接口返回值
          this.inCome2.fzx = fzx
          this.inCome2.item1 = item1
          this.inCome2.item2 = item2
          this.inCome2.item3 = item3
        } else {
          for (var i in response.data.total) {
            fzx.push(response.data.total[i].fzx)
            item1.push(response.data.total[i].amount)
          }
          this.inCome2.fzx = fzx
          this.inCome2.item1 = item1
          this.item1Name = '合计金额'
        }
        this.initIncomeEcharts()
      })
    },
    //查询选项列表
    handleQuery() {
      this.getData()
    },
    //选项列表方法方法
    getData() {
      getMealList().then((response) => {
        this.reset()
        this.checkList = response.data
      })
    },
    //选框改变方法
    changeEvent(val) {
      //查询参数
      var queryData = {
        ids: [],
        time: this.form.date,
      }

      for (var i in this.checkList) {
        for (var j in this.search) {
          if (this.checkList[i].typeName == this.search[j]) {
            queryData.ids.push(this.checkList[i].typeId)
          }
        }
      }
    },
    initIncomeEcharts() {
      var barOptions = this.inCome2.fzx //x轴数据
      var price2022 = this.inCome2.item1 //y轴数据
      var price2023 = this.inCome2.item2 //y轴数据
      var number2022 = this.inCome2.item3

      var newCustomers = echarts.init(document.getElementById('inCome'))
      var option = {
        title: {
          textStyle: {
            fontfamily: 'PingFang SC',
            fontweight: 600,
            fontSize: 16,
            lineheight: 22,
            color: '#333333',
          },
        },
        grid: {
          bottom: '2px',
          left: '2px',
          right: '20px',
          containLabel: true,
        },
        dataZoom: [
          {
            type: 'inside',
            startValue: barOptions[0],
            endValue: barOptions[barOptions.length],
            minValueSpan: 1,
          },
        ],
        tooltip: {
          trigger: 'axis',
          axisPointer: { type: 'cross' },
        },

        xAxis: [
          {
            type: 'category',
            axisTick: {
              show: false, // 不显示坐标轴刻度线
            },
            axisLabel: {
              interval: 0, //让数据全部显示
              showMinLabel: true,
              textStyle: {
                color: '#999999',
                fontSize: '14',
              },
            },
            data: barOptions,
            splitLine: {
              show: false,
            },
          },
        ],
        yAxis: [
          {
            type: 'value',
            name: '单位(万元)',
            nameTextStyle: {
              padding: [0, 0, 20, 0],
            },
            position: 'left',
            splitLine: {
              show: true,
              lineStyle: {
                // 属性lineStyle（详见lineStyle）控制线条样式
                color: ['#ccc'],
                width: 1,
                type: 'dotted',
              },
            },
            axisLabel: {
              interval: 0, //让数据全部显示
              showMinLabel: true,
              textStyle: {
                color: '#999999',
                fontSize: '14',
              },
            },
            axisLine: { show: false },
            axisTick: {
              show: false, // 不显示坐标轴刻度线
            },
          },
        ],
        series: [],
      }
      if (price2022.length > 0) {
        var data = {
          name: `${this.item1Name}`,
          type: 'bar',
          barGap: '0.7px',
          barWidth: '20px',
          yAxisIndex: 0,
          itemStyle: {
            color: this.getColor(0),
          },
          label: {
            show: true,
            position: 'top',
            formatter: '{c}' + '万',
          },
          data: price2022,
        }

        option.series.push(data)
      }
      if (price2023.length > 0) {
        var data = {
          name: `${this.item2Name}`,
          type: 'bar',
          barGap: '0.7px',
          barWidth: '20px',
          yAxisIndex: 0,
          itemStyle: {
            color: this.getColor(1),
          },
          label: {
            show: true,
            position: 'top',
            formatter: '{c}' + '万',
          },
          data: price2023,
        }
        option.series.push(data)
      }
      if (number2022.length > 0) {
        var data = {
          name: `${this.item3Name}`,
          type: 'bar',
          barGap: '0.7px',
          barWidth: '20px',
          yAxisIndex: 0,
          itemStyle: {
            color: this.getColor(2),
          },
          label: {
            show: true,
            position: 'top',
            formatter: '{c}' + '万',
          },
          data: number2022,
        }
        option.series.push(data)
      }
      var notMerge = true
      newCustomers.setOption(option, notMerge)
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
          ['#7BE6F5', '#187BF0'].map((color, offset) => ({ color, offset }))
        )
      } else if (i % 4 == 1) {
        color = new echarts.graphic.LinearGradient(
          0,
          0,
          0,
          1,
          ['#7BB3F5', '#1848F0'].map((color, offset) => ({ color, offset }))
        )
      } else if (i % 4 == 2) {
        color = new echarts.graphic.LinearGradient(
          0,
          0,
          0,
          1,
          ['#F57B7B', '#F01818'].map((color, offset) => ({ color, offset }))
        )
      } else if (i % 4 == 3) {
        color = new echarts.graphic.LinearGradient(
          0,
          0,
          0,
          1,
          ['#F5D258', '#F09F41'].map((color, offset) => ({ color, offset }))
        )
      }
      return color
    },
  },
}
</script>

<style lang="scss" scoped>
.echartsParents {
  // overflow: auto;
  height: 90%;
  width: 100%;
  display: flex;
}

.right-charts {
  height: 100%;
  width: 100%;

  .charts-box-top {
    display: inline-block;
    width: 100%;
    height: calc(100% - 4px);
    border: 1px solid #c4c4c4;
    margin: 4px;
    padding: 10px;
  }
}
</style>
