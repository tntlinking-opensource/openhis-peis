<!-- 统计管理 开发人：麦沃德科技清风/矢北 -->
<template>
  <div class="app-container flex-direction-column" style="padding:12px 0">
    <el-form :inline="true" size="mini" :model="form"
      style="border-bottom: 8px solid rgb(246, 247, 251); padding: 0 20px;">
      <el-form-item label="时间筛选">
        <el-date-picker value-format="yyyy-MM-dd" clearable v-model="form.date" type="month" placeholder="选择日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
      </el-form-item>
    </el-form>
    <div class="echartsParents">
      <div class="left-charts">
        <div class="charts-box-left">
          <div class="echart" id="newCustomers" :style="myChartStyle"></div>
        </div>
        <div class="charts-box-right">
          <div class="echart" id="keyAccount" :style="myChartStyle"></div>
        </div>
      </div>
      <div class="right-charts">
        <div class="charts-box-top">
          <div class="echart" id="inCome" :style="myChartStyle"></div>
        </div>
        <div class="charts-box-bottom">
          <div class="echart" id="discount" :style="myChartStyle"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from "echarts";
import { getReport } from '@/api/finance/financial_statements/statistical_management.js'
export default {
  data() {
    return {
      form: {
        date: undefined
      },
      ///搜索的时间
      searchTime: "2023-02",
      timeOptions: ["2023-02", "2023-03", "2023-04", "2023-05"],
      newCustomers: {
        nameTitle: "新客户金额及数量",
        barOptions: [],
        showdataprice: [],
        showdatanumber: [],
      },
      keyAccount: {
        nameTitle: "体检机构大客户",
        barOptions: [],
        showdataprice: [],
        showdatanumber: [],
      },
      inCome: {
        nameTitle: "来检人数/体检收入",
        barOptions: [],
        income2022: [],
        number2022: [],
        income2023: [],
        number2023: [],
      },
      inCome2: {
        nameTitle: "来检人数/体检收入",
        barOptions: ["聊城中心", "济宁中心", "潍坊中心", "平度中心", "胶州中心", "城阳中心", "国贸", "长沙中心", "西海岸东区", "淮南中心"],
        income2022: ["32018", "33767", "34762", "37879", "38423", "39763", "41544", "43768", "47697", "56869"],
        number2022: ["201", "112", "443", "659", "840", "987", "456", "223", "745", "945"],
        income2023: ["32018", "33767", "34762", "37879", "38423", "39763", "41544", "43768", "47697", "56869"],
        number2023: ["501", "512", "543", "559", "570", "587", "616", "623", "645", "845"],
      },
      discount: {
        nameTitle: "客单价/折扣率",
        barOptions: [],
        price2022: [],
        discount2022: [],
        price2023: [],
        discount2023: []
      },
      i:0,
      //年份
      year:0,
      lastYear:0,
      discount2: {
        nameTitle: "客单价/折扣率",
        barOptions: ["聊城中心", "济宁中心", "潍坊中心", "平度中心", "胶州中心", "城阳中心", "国贸", "长沙中心", "西海岸东区", "淮南中心"],
        price2022: ["32018", "33767", "34762", "37879", "38423", "39763", "41544", "43768", "47697", "56869"],
        discount2022: ["21", "12", "43", "69", "80", "97", "46", "23", "75", "95"],
        price2023: ["32018", "33767", "34762", "37879", "38423", "39763", "41544", "43768", "47697", "56869"],
        discount2023: ["51", "52", "53", "59", "50", "57", "66", "63", "65", "85"],
      },
      myChartStyle: { width: "100%", height: "100%" },
    }
  },
  mounted() {
    //  当月开始时间戳
    const timeStamp = new Date().toLocaleDateString()
    const MonthStart = new Date(timeStamp).getTime()
    this.form.date = this.$getDate(MonthStart)
    this.handleQuery()
    this.initNewCustomersEcharts();
    this.initKeyAccountEcharts();
    this.initIncomeEcharts();
    this.initDiscountEcharts()
  },
  methods: {
    reset() {
      this.discount = {
        nameTitle: "客单价/折扣率",
        barOptions: [],
        price2022: [],
        discount2022: [],
        price2023: [],
        discount2023: [],
      }
     this. inCome= {
        nameTitle: "来检人数/体检收入",
        barOptions: [],
        income2022: [],
        number2022: [],
        income2023: [],
        number2023: [],
      },
      this.keyAccount={
        nameTitle: "体检机构大客户",
        barOptions: [],
        showdataprice: [],
        showdatanumber: [],
      }
     this.newCustomers= {
        nameTitle: "新客户金额及数量",
        barOptions: [],
        showdataprice: [],
        showdatanumber: [],
      }

    },
    handleQuery() {
      this.getData()
    },
    getData() {
      this.reset()
      getReport(this.form).then(response => {
        var data = response.data
        this.year=data.customerPrice[0].thisYear
        this.lastYear=data.customerPrice[0].lastYear
     
        ///赋值新用户
        for (var i in data.newCustomers) {
          this.newCustomers.barOptions.push(data.newCustomers[i].name)
          this.newCustomers.showdataprice.push(data.newCustomers[i].money)
          this.newCustomers.showdatanumber.push(data.newCustomers[i].count)
        }
        ///赋值关键客户
        for (var i in data.keyAccount) {
          this.keyAccount.barOptions.push(data.keyAccount[i].name)
          this.keyAccount.showdataprice.push(data.keyAccount[i].fiveHundred)
          this.keyAccount.showdatanumber.push(data.keyAccount[i].twoHundred)
        }


        ///赋值收入
        for (var i in data.income) {
          this.inCome.barOptions.push(data.income[i].name)
          this.inCome.number2022.push(data.income[i].lastCount)
          this.inCome.number2023.push(data.income[i].thisCount)
          this.inCome.income2022.push(data.income[i].lastIncome)
          this.inCome.income2023.push(data.income[i].thisIncome)
        }
        ///赋值折扣
        for (var i in data.customerPrice) {
          this.discount.barOptions.push(data.customerPrice[i].name)
          this.discount.price2022.push(data.customerPrice[i].lastCustomerPrice)
          this.discount.discount2022.push(data.customerPrice[i].lastDiscountRate)
          this.discount.price2023.push(data.customerPrice[i].thisCustomerPrice)
          this.discount.discount2023.push(data.customerPrice[i].thisDiscountRate)
        }

        this.initNewCustomersEcharts();
        this.initKeyAccountEcharts();
        this.initIncomeEcharts();
        this.initDiscountEcharts()
      })
    },
    //初始化新客户
    initNewCustomersEcharts() {
      var barOptions = this.newCustomers.barOptions;//x轴数据
      var showdataprice = this.newCustomers.showdataprice;//y轴数据
      var showdatanumber = this.newCustomers.showdatanumber;//y轴数据
      var nameTitle = this.newCustomers.nameTitle;
      var newCustomers = echarts.init(document.getElementById('newCustomers'));
      var option = {
        color: ["#41E4BB", "#1890FF",],
        tooltip: {
          trigger: 'axis'
        },
        //显示多个展示图图标
        toolbox: {
          show: true,
        },
        legend: {
          // orient: "horizontal",
          x: "right",
        },
        grid: {
          top: "45px",
          bottom: "2px",
          left: "2px",
          right: "40px",
          containLabel: true
        },
        title: {
          text: nameTitle,
          textStyle: {
            fontSize: 16,
          },
        },
        xAxis: [{
          type: 'value',//展示数据指向data
          boundaryGap: true,//是否留白
          position: 'top',
          axisLine: { show: false },
          axisTick: { show: false },
          splitLine: {
            lineStyle: {          // 属性lineStyle（详见lineStyle）控制线条样式
              color: ['#ccc'],
              width: 1,
              type: 'dotted'
            }
          },
          axisLabel: {
            formatter: function (value, index) {
              let remainder = index % 2;
              if (remainder == 0) {
                return value;
              }
              else {
                return "";
              }
            },
            textStyle: {
              // "margin-right": 80,
              color: '#999999',
              fontSize: "12"
            }
          },
        }, {
          type: 'value',//展示数据指向data
          boundaryGap: true,//是否留白
          position: 'bottom',
          axisLine: { show: false },
          axisTick: { show: false },
          splitLine: {
            show: false,
          },
          axisLabel: {
            formatter: '{value}',
            textStyle: {
              color: '#999999',
              fontSize: "12"
            }
          },
        },
        ],
        yAxis: {
          // name: nameTitle,
          nameTextStyle: {
            padding: [0, 20, 45, 0],
            verticalAlign: "bottom",
            fontSize: '14',
            color: '#333333',
          },
          type: 'category',//展示数据指向series
          data: barOptions,
          axisLabel: {
            interval: 0,//让数据全部显示
            showMinLabel: true,
            textStyle: {
              color: '#999999',
              fontSize: "14"
            }
          },
          axisTick: {
            alignWidthLabel: true
          }
        },
        series: [{
          name: '金额',
          type: 'bar',
          // barWidth: "20px",
          // barGap: '10%',
          itemStyle: {
            color: '#5470C6',
            verticalAlign: 'top'
          },
          label: {
            show: true,
            position: 'right'
          },
          data: showdataprice
        }, {
          name: '人数',
          type: 'bar',
          xAxisIndex: 1,
          // barWidth: "20px",
          // barGap: '10%',
          itemStyle: {
            color: '#91CC75',
            verticalAlign: 'top'
          },
          label: {
            show: true,
            position: 'right'
          },
          data: showdatanumber
        },
        ]
      }
      newCustomers.setOption(option);
    },

    initKeyAccountEcharts() {
      var barOptions = this.keyAccount.barOptions;//x轴数据
      var showdataprice = this.keyAccount.showdataprice;//y轴数据
      var showdatanumber = this.keyAccount.showdatanumber;//y轴数据
      var nameTitle = this.keyAccount.nameTitle;
      var keyAccount = echarts.init(document.getElementById('keyAccount'));
      var option = {
        color: ["#41E4BB", "#1890FF",],
        tooltip: {
          trigger: 'axis'
        },
        title: {
          text: nameTitle,
          textStyle: {
            fontSize: 16,
          },
        },
        //显示多个展示图图标
        toolbox: {
          show: true,
        },
        legend: {
          orient: "horizontal",
          x: "right",
        },
        grid: {
          top: "45px",
          bottom: "2px",
          left: "2px",
          right: "40px",
          containLabel: true
        },

        xAxis: [
          {
            type: 'value',//展示数据指向data
            boundaryGap: true,//是否留白
            position: 'top',
            axisLine: { show: false },
            axisTick: { show: false },
            splitLine: {
              lineStyle: {          // 属性lineStyle（详见lineStyle）控制线条样式
                color: ['#ccc'],
                width: 1,
                type: 'dotted'
              }
            },
            axisLabel: {
              formatter: function (value, index) {
                let remainder = index % 1;
                if (remainder == 0) {
                  return value;
                }
                else {
                  return "";
                }
              },
              textStyle: {
                // "margin-right": 80,
                color: '#999999',
                fontSize: "12"
              },

              textStyle: {
                color: '#999999',
                fontSize: "14"
              }
            },
          }, {
            type: 'value',//展示数据指向data
            boundaryGap: true,//是否留白
            position: 'bottom',
            axisLine: { show: false },
            axisTick: { show: false },
            splitLine: {
              show: false,
            },
            axisLabel: {
              formatter: '{value}',
              textStyle: {
                color: '#999999',
                fontSize: "14"
              }
            },
          }
        ],
        yAxis: {

          nameTextStyle: {
            padding: [0, 20, 45, 0],
            verticalAlign: "bottom",
            fontSize: '16',
            color: '#333333',
            fontWeight: 400
          },
          type: 'category',//展示数据指向series
          data: barOptions,
          axisLabel: {
            interval: 0,//让数据全部显示
            showMinLabel: true,
            textStyle: {
              color: '#999999',
              fontSize: "14"
            }
          },
          axisTick: {
            alignWidthLabel: true
          }
        },
        series: [
          {
            name: '50万',
            type: 'bar',
            // barWidth: "20px",
            // barGap: '20%',
            itemStyle: {
              color: '#EF6667',
              verticalAlign: 'top'
            },
            label: {
              show: true,
              position: 'right'
            },
            data: showdataprice
          }, {
            name: '20万-50万',
            type: 'bar',
            xAxisIndex: 1,
            // barWidth: "20px",
            // barGap: '20%',
            itemStyle: {
              color: '#FAC958',
              verticalAlign: 'top'
            },
            label: {
              show: true,
              position: 'right'
            },
            data: showdatanumber
          },
        ]
      }
      keyAccount.setOption(option);
    },
    initIncomeEcharts() {
      var barOptions = this.inCome.barOptions;//x轴数据
      var price2022 = this.inCome.income2022;//y轴数据
      var price2023 = this.inCome.income2023;//y轴数据
      var number2022 = this.inCome.number2022;
      var number2023 = this.inCome.number2023;
      var newCustomers = echarts.init(document.getElementById('inCome'));
      var option = {
        title: {
          text: "来检人数/体检收入",
          textStyle: {
            fontweight: 600,
            fontSize: 16,
            lineheight: 22,
            color: "#333333",
          },

        },
        grid: {
          bottom: "2px",
          left: "2px",
          right: "2px",
          containLabel: true
        },
        dataZoom: [
          {
            type: "inside",
            startValue: barOptions[0],
            endValue: barOptions[barOptions.length],
            minValueSpan: 1
          }
        ],
        tooltip: {
          trigger: 'axis',
          axisPointer: { type: 'cross' }
        },
        legend: {
          // Try 'horizontal'
          orient: 'horizontal',
          right: 10,

        },
        xAxis: [
          {
            type: 'category',
            axisTick: {
              show: false // 不显示坐标轴刻度线
            },
            axisLabel: {
              interval: 0,//让数据全部显示
              showMinLabel: true,
              textStyle: {
                color: '#999999',
                fontSize: "14"
              }
            },
            data: barOptions,
            splitLine: {
              show: false
            },
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '单位(万元)',
            position: 'left',
            splitLine: {
              show: true,
              lineStyle: {          // 属性lineStyle（详见lineStyle）控制线条样式
                color: ['#ccc'],
                width: 1,
                type: 'dotted'
              }
            },
            axisLabel: {
              interval: 0,//让数据全部显示
              showMinLabel: true,
              textStyle: {
                color: '#999999',
                fontSize: "14"
              }
            },
            axisLine: { show: false },
            axisTick: {
              show: false // 不显示坐标轴刻度线
            },
          },
          {
            type: 'value',
            name: '人次',
            position: 'right',
            splitLine: {
              show: false,
            },
            axisLabel: {
              interval: 0,//让数据全部显示
              showMinLabel: true,
              textStyle: {
                color: '#999999',
                fontSize: "14"
              }
            },
            axisLine: { show: false },
            axisTick: {
              show: false // 不显示坐标轴刻度线
            },
          },

        ],
        series: [
          {
            name: `${this.lastYear}体检收入`,
            type: 'bar',
            barWidth: '20px',
            yAxisIndex: 0,
            itemStyle: {
              color: this.getColor(0)
            },
            data: price2022
          },
          {
            name: `${this.year}体检收入`,
            type: 'bar',
            barWidth: '20px',
            itemStyle: {
              color: this.getColor(1)
            },
            yAxisIndex: 0,
            data: price2023
          },
          {
            name: `${this.lastYear}体检人数`,
            type: 'line',
            itemStyle: {
              color: '#FF2020'
            },
            yAxisIndex: 1,
            data: number2022
          },
          {
            name: `${this.year}体检人数`,
            type: 'line',
            itemStyle: {
              color: '#FFC700'
            },
            yAxisIndex: 1,
            data: number2023
          }
        ]
      };
      newCustomers.setOption(option);
    },
    initDiscountEcharts() {
      var barOptions = this.discount.barOptions;//x轴数据
      var price2022 = this.discount.price2022;//y轴数据
      var price2023 = this.discount.price2023;//y轴数据
      var number2022 = this.discount.discount2022;
      var number2023 = this.discount.discount2023;
      var newCustomers = echarts.init(document.getElementById('discount'));

      var option = {
        title: {
          text: "客户单价/客户折扣率",
          textStyle: {
            fontfamily: 'PingFang SC',
            fontweight: 600,
            fontSize: 16,
            lineheight: 22,
            color: "#333333",
          },

        },
        grid: {
          bottom: "2px",
          left: "2px",
          right: "2px",
          containLabel: true
        },
        dataZoom: [
          {
            type: "inside",
            startValue: barOptions[0],
            endValue: barOptions[barOptions.length],
            minValueSpan: 1
          }
        ],
        tooltip: {
          trigger: 'axis',
          axisPointer: { type: 'cross' }
        },
        legend: {
          // Try 'horizontal'
          orient: 'horizontal',
          right: 10,

        },
        xAxis: [
          {
            type: 'category',

            axisLabel: {
              interval: 0,//让数据全部显示
              showMinLabel: true,
              textStyle: {
                color: '#999999',
                fontSize: "14"
              }
            },
            axisTick: {
              show: false // 不显示坐标轴刻度线
            },
            data: barOptions,
            splitLine: {
              show: false
            }
          }

        ],
        yAxis: [
          {
            type: 'value',
            name: '客单价(元)'  ,
            position: 'left',
            splitLine: {
              show: true,
              lineStyle: {          // 属性lineStyle（详见lineStyle）控制线条样式
                color: ['#ccc'],
                width: 1,
                type: 'dotted'
              }
            },
            axisTick: {
              show: false // 不显示坐标轴刻度线
            },
            axisLabel: {
              interval: 5,//让数据全部显示
              showMinLabel: true,
              textStyle: {
                color: '#999999',
                fontSize: "14"
              }
            },
            axisLine: {
              show: false
            },//不用刻度轴线}, 
          },
          {
            max: 100,
            min: 0,
            type: 'value',
            name: '折扣率',
            interval: 10,
            position: 'right',
            splitLine: {
              show: false,
            },
            axisTick: {
              show: false // 不显示坐标轴刻度线
            },
            axisLabel: {
              interval: 5,//让数据全部显示
              formatter: function (value, index) {
              
                let remainder = value % 20;
                if (remainder == 0) {
                  return value + '%';
                }
                else {
                  return "";
                }
              },
              showMinLabel: true,
              textStyle: {
                color: '#999999',
                fontSize: "14"
              }
            },
            axisLine: { show: false },
          },
        ],
        series: [
          {
            name:`${this.lastYear}客户单价` ,
            type: 'bar',
            barWidth: '20px',
            yAxisIndex: 0,
            itemStyle: {
              color: this.getColor(2)
            },

            data: price2022
          },
          {
            name: `${this.year}客户单价`,
            type: 'bar',
            barWidth: '20px',

            itemStyle: {
              color: this.getColor(3)
            },
            yAxisIndex: 0,
            data: price2023
          },
          {
            name: `${this.lastYear}客户折扣率`,
            type: 'line',
            itemStyle: {
              color: '#FF8A00'
            },
            yAxisIndex: 1,
            data: number2022
          },
          {
            name: `${this.year}客户折扣率`,
            type: 'line',
            itemStyle: {
              color: '#206CFF'
            },
            yAxisIndex: 1,
            data: number2023
          }
        ]
      };
      newCustomers.setOption(option);
    },
    //渐变色设置
    getColor(i) {
      let color;
      if (i == 0) {
        color = new echarts.graphic.LinearGradient(0, 0, 0, 1, ['#7BE6F5', '#187BF0'].map((color, offset) => ({ color, offset })))
      } else if (i % 4 == 1) {
        color = new echarts.graphic.LinearGradient(0, 0, 0, 1, ['#7BB3F5', '#1848F0'].map((color, offset) => ({ color, offset })))
      } else if (i % 4 == 2) {
        color = new echarts.graphic.LinearGradient(0, 0, 0, 1, ['#F57B7B', '#F01818'].map((color, offset) => ({ color, offset })))
      } else if (i % 4 == 3) {
        color = new echarts.graphic.LinearGradient(0, 0, 0, 1, ['#F5D258', '#F09F41'].map((color, offset) => ({ color, offset })))
      }
      return color
    }
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

.left-charts {
  height: calc(100% - 10px);
  width: 45%;

  .charts-box-left {
    display: inline-block;
    width: 48%;
    height: 100%;
    border: 1px solid #C4C4C4;
    margin: 4px;
    padding: 8px;
  }

  .charts-box-right {
    display: inline-block;
    width: 48%;
    height: 100%;
    border: 1px solid #C4C4C4;
    margin: 4px;
    padding: 8px;
  }
}

.right-charts {
  height: 100%;
  width: 55%;

  .charts-box-top {
    display: inline-block;
    width: 100%;
    height: calc(49% - 4px);
    border: 1px solid #C4C4C4;
    margin: 4px;
    padding: 8px;
  }

  .charts-box-bottom {
    display: inline-block;
    width: 100%;
    height: calc(49% - 4px);
    border: 1px solid #C4C4C4;
    margin: 0px 4px 4px 4px;
    padding: 8px;
  }
}
</style>