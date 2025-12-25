<!-- CDC职业病直报数据查询	 开发人：麦沃德科技矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
      @submit.native.prevent>
      <el-form-item prop="num" label="体检号">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable></el-input>
      </el-form-item>
      <el-form-item prop="type" label="体检类型">
        <el-select v-model="queryParams.medicaltype" placeholder="请选择体检类型" clearable>
          <el-option label="上岗前" :value="0"></el-option>
          <el-option label="在岗期" :value="1"></el-option>
          <el-option label="离岗时" :value="2"></el-option>
          <el-option label="离岗后" :value="3"></el-option>
          <el-option label="应急" :value="4"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="orgIdData" label="体检单位">
        <input-select :selectData="orgIdData" selectWidth="200" @change="orgIdDataChange"></input-select>
      </el-form-item>
      <el-form-item prop="startTime" label="登记日期">
        <el-date-picker v-model="queryParams.startTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="endTime" label="至">
        <el-date-picker v-model="queryParams.endTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2"  @click="handExport"
          v-hasPermi="['statistical:professionChecks:occupationalDisease:export']">导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="queryReport" :disabled="single"
          v-hasPermi="['statistical:professionChecks:occupationalDisease:view']">查看报告</el-button>
      </el-col>
    </el-row>
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe
        @selection-change="handleSelectionChange">
        <el-table-column fixed type="selection" width="55" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="姓名" prop="patientname" width="120" align="center" show-overflow-tooltip />
        <el-table-column label="性别" prop="idSex"  min-width="100" align="center" show-overflow-tooltip >
          <template slot-scope="scope">
              <span v-if="scope.row.idSex == 0">男</span>
              <span v-if="scope.row.idSex == 1">女</span>
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="age" width="100" align="center" show-overflow-tooltip />
        <el-table-column label="联系方式" prop="phone" min-width="140" align="center" />
        <el-table-column label="体检号" prop="patientcode" width="140" align="center" show-overflow-tooltip />
        <el-table-column label="用人单位" prop="orgName" min-width="160" align="center" show-overflow-tooltip />
        <el-table-column label="登记时间" prop="dateregister" min-width="140" align="center" />
        <el-table-column label="体检结论" prop="occupationSummary" min-width="120" align="center" />
        <el-table-column label="工种" prop="trades" min-width="120" align="center" />
        <el-table-column label="接害因素" prop="jhys" min-width="140" align="center" show-overflow-tooltip />
        <el-table-column prop="medicaltype" label="体检类型" align="center" min-width="120">
          <template slot-scope="scope">
              <span v-if="scope.row.medicaltype == 0">上岗前</span>
              <span v-if="scope.row.medicaltype == 1">在岗期间</span>
              <span v-if="scope.row.medicaltype == 2">离岗时</span>
              <span v-if="scope.row.medicaltype == 3">离岗后</span>
              <span v-if="scope.row.medicaltype == 4">应急</span>
          </template>
        </el-table-column>
        <el-table-column label="总工龄-年" prop="zglyear" min-width="120" align="center" />
        <el-table-column label="总工龄-月" prop="zglmonth" min-width="120" align="center" />
        <el-table-column label="接害工龄-年" prop="jhglyear" min-width="120" align="center" />
        <el-table-column label="接害工龄-月" prop="jhglmonth" min-width="120" align="center" />
        <el-table-column label="左耳气导500Hz" prop="airLeft500" min-width="140" align="center" />
        <el-table-column label="左耳气导1000Hz" prop="airLeft1000" min-width="140" align="center" />
        <el-table-column label="左耳气导2000Hz" prop="airLeft2000" min-width="140" align="center" />
        <el-table-column label="左耳气导3000Hz" prop="airLeft3000" min-width="140" align="center" />
        <el-table-column label="左耳气导4000Hz" prop="airLeft4000" min-width="140" align="center" />
        <el-table-column label="左耳气导6000Hz" prop="airLeft6000" min-width="140" align="center" />
        <el-table-column label="右耳气导500Hz" prop="airRight500" min-width="140" align="center" />
        <el-table-column label="右耳气导1000Hz" prop="airRight1000" min-width="140" align="center" />
        <el-table-column label="右耳气导2000Hz" prop="airRight2000" min-width="140" align="center" />
        <el-table-column label="右耳气导3000Hz" prop="airRight3000" min-width="140" align="center" />
        <el-table-column label="右耳气导4000Hz" prop="airRight4000" min-width="140" align="center" />
        <el-table-column label="右耳气导6000Hz" prop="airRight6000" min-width="140" align="center" />
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
      @pagination="getList" />
  </div>
</template>

<script>
import { getListdata } from '@/api/statistical/professionchecks/cdc'

export default {
  data() {
    return {
      total:50,
      //遮罩层
      loading: false,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      //查询参数
      queryParams:
      {
        current: 1,
        size: 10,
        patientcode: undefined,
        medicaltype: undefined,
        startTime: undefined,
        endTime: undefined,
        idOrg: undefined,
      },
      //总数
      total: 0,
      showSearch: true,
      //表格数据
      tableList: [
        {
          idSex:1,
      },
      {
          idSex:0,
      },
      {
          idSex:1,
      }
    ],
     // 性别
     sexOptions: [
        { id: 0, text: "男" },
        { id: 1, text: "女" },
        { id: 2, text: "通用" }
      ],
      // 科室列表
      selectData: {
        placeholder: '请输入输入码选择',
        key: '输入码',//第一列标题
        value: '团体名称',//第二列标题
        url: '',//请求连接
        bindValue: '',
      },
       orgIdData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '团检单位', //第二列标题
        url: '/abteilung/sectionResultPlan/getOrgs', //请求连接`
        bindValue: '',
        firstName: 'khdwsrm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key',
      },
    }
  },
  created() {
    //  当月开始时间戳
    const timeStamp = new Date().toLocaleDateString()
    //  上个月的天数
    const days = this.lastMonthDats()
    //  上月开始时间戳
    const lastMonthStart = new Date(timeStamp).getTime() - days * 24 * 3600 * 1000
    this.queryParams.startTime = this.$getDate(lastMonthStart).split(' ')[0]
    this.queryParams.endTime = this.$getDate().split(' ')[0]
    this.getList();
  },
  methods: {
    // 查看报告
    queryReport(){
      console.log('urlPdf:', this.selection.urlPdf);
      if (!this.selection.urlPdf) {
        this.$alert('请先生成报告', '提示')
        return
      }
      let url = this.$getCookie('imgPath') + this.selection.urlPdf
      window.open(url, '_blank')
      return
    },
    // 获取上个月共几天
    lastMonthDats() {
      const date = new Date()
      const year = date.getFullYear()
      //  上个月月份
      let month = date.getMonth() + 1 - 1 //  0-11 表示 1月-12月
      //  0 表示12月
      month = month || 12
      //  30天的月份
      const arr30 = [4, 6, 9, 11]
      //  31天的月份
      const arr31 = [1, 3, 5, 7, 8, 10, 12]
      if (arr30.indexOf(month) !== -1) {
        //  上个月是 30 天
        return 30
      } else if (arr31.indexOf(month) !== -1) {
        //  上个月是 31 天
        return 31
      } else {
        //  2月
        if (this.isRunYear(year)) {
          return 29
        } else {
          return 28
        }
      }
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
        this.selection = selection[0];
        this.single = selection.length != 1;
        this.multiple = !selection.length;
    },
    //搜素
    handleQuery() {
        this.queryParams.current = 1;
        this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
        this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
        this.getList();
    },
    //团检单位
    orgIdDataChange(value) {
      this.queryParams.idOrg = value.id
    },
    // 查询列表
    getList() {
        this.loading = true
        getListdata(this.queryParams).then(response => {
          this.tableList = response.data.records;
          this.total = response.data.total;
          this.loading = false;
        });
    },
    //重置
    resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
    },
    handExport() {
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.queryParams.geStartTime = this.queryParams.geStartTime ? this.queryParams.geStartTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.geEndTime = this.queryParams.geEndTime ? this.queryParams.geEndTime.slice(0, 10) + ' 23:59:59' : undefined
      this.download('/query/cdc/export', this.queryParams, "CDC职业病直报数据.xlsx")
    },
  },

}
</script>

<style>

</style>
