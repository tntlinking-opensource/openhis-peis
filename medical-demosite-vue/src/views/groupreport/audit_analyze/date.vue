<!-- 团检报告审批-综合样本分析-日期分布  开发人：麦沃德科技半夏 -->
<template>
  <div class="main-date">
    <!-- 表格 -->
    <div class="content-table">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe>
        <el-table-column label="日期" align="center">
          <el-table-column label="登记日期" prop="date0" align="center" />
          <el-table-column label="星期" prop="date1" align="center" />
        </el-table-column>
        <el-table-column label="人数" align="center">
          <el-table-column label="男性" prop="date2" align="center" />
          <el-table-column label="女性" prop="date3" align="center" />
          <el-table-column label="合计" prop="date4" align="center" />
        </el-table-column>
      </el-table>
    </div>
    <!-- 图表 -->
    <div class="content-chart">
      <div class="chart-box" id="date1"></div>
    </div>
  </div>
</template>
<script>
import { getDateList } from "@/api/groupreport/audit_analyze";
import * as echarts from "echarts";
export default {
  components: {},
  props: [],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 排检表格数据
      tableList: [],
    };
  },
  computed: {},
  watch: {},
  created() {
    this.getList();
  },
  mounted() { },
  methods: {
    // 查询列表
    getList() {
      this.loading = true;
      getDateList(this.$route.query.id).then(response => {
        this.tableList = response.data
        this.initCharts()
        this.loading = false;
      });
    },
    // 基于准备好的dom，初始化echarts图表
    initCharts() {
      var data = this.tableList
      var name = new Array(data.length);
      var att1 = new Array(data.length);
      for (var i = 0; i < data.length; i++) {
        name[i] = data[i]["date0"];
        att1[i] = data[i]["date4"];
      }

      var date1 = echarts.init(document.getElementById('date1'));
      var option1 = {
        title: {
          text: '数据统计',
          subtext: ''
        },
        tooltip: {
          show: true
        },
        grid: {
          left: 0,
          right: 0,
          bottom: 1,
          containLabel: true
        },
        legend: {
          data: ['人数统计']
        },
        xAxis: [{
          type: 'category',
          data: []
        }],
        yAxis: [{
          type: 'value',
          minInterval: 1
        }],
        series: [{
          type: 'bar',
          data: [],
          label: {
            normal: {
              show: true,
              position: 'top',
              textStyle: {
                fontSize: 24
              }
            }
          },
          itemStyle: {
            normal: {
              color: function (value) {
                return "#" + ("00000" + ((Math.random() * 16777215 + 0.5) >> 0).toString(16))
                  .slice(-6);
              }
            }
          }

        }]
      };

      option1.xAxis[0].data = name;
      option1.series[0].data = att1;
      date1.setOption(option1);

    }
  },
};
</script>
<style lang="scss">
.main-date {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  .content-table {
    height: 60%;
  }

  .content-chart {
    height: calc(40% - 4px);

    .chart-box {
      width: 100%;
      height: 100%;
      background: #fff;
      padding: 12px;
      box-sizing: border-box;
    }
  }
}
</style>