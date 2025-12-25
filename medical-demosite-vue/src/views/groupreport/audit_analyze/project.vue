<!-- 团检报告审批-综合样本分析-项目参检  开发人：麦沃德科技半夏 -->
<template>
  <div class="main-project">
    <!-- 表格 -->
    <div class="content-table">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe>
        <el-table-column label="行号" width="60" type="index" align="center" />
        <el-table-column label="收费项目" align="center">
          <el-table-column label="检查科室" prop="depName" align="center" show-overflow-tooltip />
          <el-table-column label="项目名称" prop="itemName" align="center" show-overflow-tooltip />
        </el-table-column>
        <el-table-column label="该项参检人数" align="center">
          <el-table-column label="男性" prop="checkMale" align="center" />
          <el-table-column label="女性" prop="checkFemale" align="center" />
          <el-table-column label="合计" prop="checkTotal" align="center" />
        </el-table-column>
        <el-table-column label="总体参检人数" align="center">
          <el-table-column label="男性" prop="allMale" align="center" />
          <el-table-column label="女性" prop="allFemale" align="center" />
          <el-table-column label="合计" prop="allTotal" align="center" />
        </el-table-column>
        <el-table-column label="该项参检比例" align="center">
          <el-table-column label="男性" prop="perMale" align="center" />
          <el-table-column label="女性" prop="perFemale" align="center" />
          <el-table-column label="平均" prop="perTotal" align="center" />
        </el-table-column>
      </el-table>
    </div>
    <!-- 图表 -->
    <div class="content-chart">
      <div class="chart-box" id="exam1"></div>
    </div>
  </div>
</template>
<script>
import { getProjectList } from "@/api/groupreport/audit_analyze";
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
      getProjectList(this.$route.query.id, this.$route.query.groupId).then(response => {
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
        name[i] = i + 1;
        att1[i] = data[i]["checkTotal"];
      }
      var exam1 = echarts.init(document.getElementById('exam1'));
      var option1 = {
        title: {
          text: '各体检项目的男女合计参检人数',
        },
        animation: false,
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            animation: false
          }
        },
        grid: {
          left: 0,
          right: 0,
          bottom: 1,
          containLabel: true
        },
        xAxis: [{
          type: 'category',
          data: name,
          minInterval: 1,
          axisLine: {
            onZero: false
          },
        }],
        yAxis: [{
          type: 'value',
          minInterval: 1,
        }],
        series: [{
          name: '人数统计',
          type: 'line',
          data: att1,
        }]
      };
      exam1.setOption(option1);
    }
  },
};
</script>
<style lang="scss">
.main-project {
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