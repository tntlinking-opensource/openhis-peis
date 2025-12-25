<!-- PACS登记信息查询 开发人：麦沃德科技矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form size="mini" :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
      <el-form-item prop="num" label="体检号">
        <el-input style="width:160px"  v-model="queryParams.num" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item prop="name" label="姓名">
        <el-input v-model="queryParams.name"  placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item prop="phone"  label="电话">
        <el-input v-model="queryParams.phone" style="width:160px" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item prop="id" label="身份证">
        <el-input v-model="queryParams.id" style="width:160px" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item prop="type"  label="体检类型">
              <el-select v-model="queryParams.type" style="width:120px;" placeholder="请选择">
            <el-option :value="1" label="健康体检"></el-option>
            <el-option :value="0" label="职业体检"></el-option>
            <el-option :value="2" label="综合体健"></el-option>
          </el-select>
            </el-form-item>
       <el-form-item prop="date"  label="登记日期">
        <el-date-picker v-model="queryParams.date" type="date" style="width:240px" placeholder="选择日期"></el-date-picker>
       </el-form-item>
       <el-form-item prop="teamName"  label="团体名称">
        <input-select :selectData="labTypeData" style="width:160px" @change="labTypeChange"></input-select>
       </el-form-item>
       <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
      </el-form>
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="showDialog">导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" @click="showDialog">导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-plus" @click="showDialog">提取</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-plus" @click="showDialog2">导出DCM</el-button>
      </el-col>
      </el-row>
      <div class="table-box">
      <el-table style="width:100%" :data="tableList"  v-loading="loading" @selection-change="handleSelectionChange"
        height="200px">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column prop="regDate" label="登记日期" align="center" />
        <el-table-column prop="name" label="登记员" align="center" />
        <el-table-column prop="inputCode" label="体检号" align="center" />
        <el-table-column prop="name" label="姓名" align="center"  />
        <el-table-column prop="sex" label="性别" align="center"  />
        <el-table-column prop="age" label="年龄" align="center"  />
        <el-table-column prop="phone" label="电话" align="center"  />
        <el-table-column prop="birth" label="出生日期" align="center"  />
        <el-table-column prop="id" label="身份证号" align="center"  />
        <el-table-column prop="type" label="体检者类型" align="center"  />
        <el-table-column prop="testDate" label="体检时间" align="center"  />
        </el-table>
        </div>
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
      @pagination="getList" />

    <div class="chart-data">
      <div class="bar-chart">
        <div class="echart" id="barChart"></div>
      </div>
      <div class="pie-chart">
        <div class="echart" id="pieChart"></div>
      </div>
    </div>

       <add ref="add" ></add>
        <exportDCM ref="exportDCM"></exportDCM>
  </div>
</template>

<script>
import add from './add'
import exportDCM from './exportDCM.vue'
import * as echarts from 'echarts'
export default {
  data()
  {
    return{
      tableList:[],
      //遮罩层
      loading:false,
      //总数
      total:50,
      //
      showSearch:true,
      //查询参数
      queryParams:{},
        // 科室名称
        labTypeData: {
        placeholder: '请输入输入码选择',
        key: '科室码',
        value: '科室名称',
        url: '/basconclusion/findAllDepartment',
        bindValue: '', //初始值(必传)

      },
    }
  },
  components:{add, exportDCM},
  created()
  {
    this.getList();
  },
  mounted() {
    this.getList();
    this.initBarEcharts();
    this.initPieEcharts();
  },
  methods:{
    initBarEcharts() {
      // 基本柱状图
      const option = {
        color: ['#41E4BB', '#1890FF'],
        tooltip: {
          trigger: 'axis'
        },
        title: {
          text: '柱状图统计',
        },
        legend: {
          data: ["健康体检","职业体检"],
          top: 'top',
          left: "right",
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '10%',
          containLabel: true
        },
        xAxis: {
          type: 'value'
        },
        yAxis: {
          type: 'category',
          //boundaryGap: false,
          data: ['职业体检','健康体检'],
        },


        series: [
          {
            data: [
              {
                value: 120,
                itemStyle: {
                  color: '#1890FF'
                }
              },
              100
            ],
            type: 'bar'
          },
        ]
      };
      const barChart = echarts.init(document.getElementById("barChart"));
      barChart.setOption(option);
      //随着屏幕大小调节图表
      window.addEventListener("resize", () => {
        barChart.resize();
      });
    },
    // //柱状图
    // initBarEcharts() {
    //   // 基本柱状图
    //   const option = {
    //     color: ['#41E4BB', '#1890FF'],
    //     tooltip: {
    //       trigger: 'axis'
    //     },
    //     title: {
    //       text: '柱状图统计',
    //     },
    //     legend: {
    //       data: ["健康体检","职业体检"],
    //       top: 'top',
    //       left: "right",
    //     },
    //     grid: {
    //       left: '3%',
    //       right: '4%',
    //       bottom: '10%',
    //       containLabel: true
    //     },
    //     xAxis: {
    //       type: 'value'
    //     },
    //     yAxis: {
    //       type: 'category',
    //       //boundaryGap: false,
    //       data: ["健康体检","职业体检"],
    //     },
    //
    //     series: [
    //       {
    //         name: "健康体检",
    //         type: 'bar',
    //         data: [100],
    //       },
    //       {
    //         name: "职业体检",
    //         type: 'bar',
    //         data: [120],
    //       },
    //     ]
    //   };
    //   const barChart = echarts.init(document.getElementById("barChart"));
    //   barChart.setOption(option);
    //   //随着屏幕大小调节图表
    //   window.addEventListener("resize", () => {
    //     barChart.resize();
    //   });
    // },
    // 柱状图
    initPieEcharts() {
      // 基本柱状图
      const option = {
        color: ['#22C58B', '#00CFDF'],
        tooltip: {
          trigger: 'item',
          formatter: '{b} : {c} ({d}%)'
        },
        title: {
          text: '饼状图统计',
        },
        legend: {
          type: 'scroll',
          top: 'bottom',
          data: ["个人","团体"],
        },
        series: [
          {
            type: 'pie',
            data: [
              { name: '个人', value: 55, color: '#22C58B', },
              { name: '团体', value: 35, color: '#00CFDF',  },
            ],
            label: {
              formatter: '{b} {c}'
            },
            top: "40px"
          }
        ]
      };
      const pieChart = echarts.init(document.getElementById("pieChart"));
      pieChart.setOption(option);
      //随着屏幕大小调节图表
      window.addEventListener("resize", () => {
        pieChart.resize();
      });
    },
     // 科室选项
     labTypeChange(value) {
      this.queryParams.depName = value.name
      this.selectData.bindValue = value.name;
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    //重置按钮
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
      ///搜素
      handleQuery() {
      this.queryParams.current = 1;
      this.getList();
    },
      // 查询列表
      getList() {
      this.loading = true;
      this.loading = false;
    },
    showDialog()
    {
      this.$refs.add.handleAdd()
    },
    showDialog2()
    {
      this.$refs.exportDCM.handleAdd()
    }
  }
}
</script>

<style>
  .chart-data {
    flex: 1;
    margin-top: 4px;
    display: flex;
  }

  .bar-chart {
    background: #fff;
    width: 66%;
    padding: 20px;
  }

  .pie-chart {
    background: #fff;
    margin-left: 4px;
    flex: 1;
    padding: 20px;
  }

  .echart {
    width: 100%;
    height: 100%;
  }

</style>
