<!-- 销售个检费用  开发人：麦沃德科技暴雨/清风 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form size="mini" ref="queryForm" :model="queryParams" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item label="分中心" prop="branchIds">
        <el-select v-model="queryParams.branchIds" placeholder="请选择分中心" clearable style="width: 160px" @change="handleQuery" :disabled="!isOnline">
          <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="开单医师" style="margin-right: 16px" prop="xsjl">
        <el-input style="width: 160px" placeholder="请输入开单医师" v-model="queryParams.xsjl" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <!-- <el-form-item label="登记日期" prop="date">
        <el-date-picker v-model="queryParams.date" style="width: 350px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" @change="handleQuery"></el-date-picker>
      </el-form-item> -->
      <el-form-item label="登记日期" prop="startTime">
        <el-date-picker v-model="queryParams.startTime" style="width: 160px" value-format="yyyy-MM-dd" type="date" placeholder="请选择日期" :picker-options="pickerOptions" @change="handleQuery"></el-date-picker>
      </el-form-item>
      <el-form-item label="至" prop="endTime">
        <el-date-picker v-model="queryParams.endTime" style="width: 160px" value-format="yyyy-MM-dd" type="date" placeholder="请选择日期" :picker-options="pickerOptions" @change="handleQuery"></el-date-picker>
      </el-form-item>
      <el-form-item prop="isContainNuclein">
        <el-checkbox v-model="queryParams.isContainNuclein" :true-label="1" :false-label="0" @change="handleQuery">含核酸</el-checkbox>
      </el-form-item>
      <el-form-item prop="isContainVaccine">
        <el-checkbox v-model="queryParams.isContainVaccine" :true-label="1" :false-label="0" @change="handleQuery">含疫苗</el-checkbox>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="handleexport" v-hasPermi="['statistical:inspectCost:sellPersonal:export']">导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" :disabled="single" @click="handleExportbyday" v-hasPermi="['statistical:inspectCost:sellPersonal:exportbyday']">按天导出</el-button>
      </el-col>
    </el-row>

    <dragRow left-size="50%" right-size="50%">
      <template #leftBox>
        <div class="item-box flex-direction-column">
          <div class="table-box">
            <!-- 表1 -->
            <el-table :data="tableData" ref="tableData" v-loading="loading" style="max-width: 1024px" :border="true" :stripe="true" @selection-change="handleSelectionChange" @row-click="handleRowClick" height="100%">
              <el-table-column type="selection" align="center"></el-table-column>
              <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
              <el-table-column prop="fzx" width="120" label="分中心" align="center" show-overflow-tooltip></el-table-column>
              <el-table-column prop="xsjl" width="120" label="开单医师" align="center"></el-table-column>
              <el-table-column prop="yjhj" width="120" label="原价合计" align="center"></el-table-column>
              <el-table-column prop="sshj" width="120" label="实收合计" align="center"></el-table-column>
              <el-table-column prop="addprice" width="120" label="科室加项费用" align="center"></el-table-column>
              <el-table-column prop="addorgprice" width="120" label="加项原价合计" align="center"></el-table-column>
              <el-table-column prop="count" width="120" label="人数" align="center"></el-table-column>
              <el-table-column prop="zk" width="120" label="折扣" align="center"></el-table-column>
              <el-table-column prop="kdj" width="120" label="客单价" align="center"></el-table-column>
              <el-table-column prop="costprice" width="120" label="成本价" align="center"></el-table-column>
            </el-table>
          </div>

          <!-- 分页 -->
          <pagination :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
        </div>
      </template>

      <template #rightBox>
        <!-- 表2 -->
        <div class="item-box">
          <div class="flex-direction-column">
            <div class="table-box">
              <el-table :data="tableDatas" v-loading="loadingr" style="max-width: 100%" :border="true" :stripe="true" height="100%">
                <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
                <el-table-column prop="tjh" label="体检号" align="center"></el-table-column>
                <el-table-column prop="gjrymc" label="个检人员名称" align="center"></el-table-column>
                <el-table-column prop="tjtcmc" label="套餐名称" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column prop="yj" label="原价" align="center"></el-table-column>
                <el-table-column prop="ss" label="实收" align="center"></el-table-column>
                <el-table-column prop="zk" label="折扣" align="center"></el-table-column>
                <el-table-column prop="regDate" label="登记日期" align="center" show-overflow-tooltip></el-table-column>
              </el-table>
            </div>
            <!-- 分页 -->
            <pagination :total="rightTotal" :page.sync="queryRightParams.current" :limit.sync="queryRightParams.size" @pagination="getRightBasic" />
          </div>
        </div>
      </template>
    </dragRow>
  </div>
</template>

<script>
import dragRow from '@/components/DragRow'
import { getBranchData } from '@/api/statistical/professionchecks/conclusion_table.js' //获取分中心
import { page, getListData } from '@/api/statistical/inspect_cost/sell_personal.js'
import { getCookie } from '@/utils/getCookie.js'

export default {
  name: 'Sell_personal',
  components: { dragRow },
  data() {
    return {
      // 日期选择参数
      pickerOptions: {
        shortcuts: [
          {
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date())
            },
          },
          {
            text: '昨天',
            onClick(picker) {
              const date = new Date()
              date.setTime(date.getTime() - 3600 * 1000 * 24)
              picker.$emit('pick', date)
            },
          },
          {
            text: '一周前',
            onClick(picker) {
              const date = new Date()
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
              picker.$emit('pick', date)
            },
          },
        ],
      },
      total: 0,
      rightTotal: 0,
      queryParams: {
        current: 1,
        size: 20,
        branchIds: undefined,
        order: undefined,
        idOrg: undefined,
        intId: undefined,
        isContainNuclein: '0',
        isContainVaccine: '0',
        xsjl: '',
        startTime: '',
        endTime: '',
      },
      //右表分页器数据
      queryRightParams: {
        current: 1,
        size: 20,
        branchIds: undefined,
        order: undefined,
        idOrg: undefined,
        intId: undefined,
      },
      ids: [],
      single: '',
      multiple: '',
      loading: false,
      loadingr: false,
      tableData: [],
      tableDatas: [],
      //右表分页查询
      getRightSearch: {},
      // 分中心列表
      fzxOptions: [],
      // 是否为线上
      isOnline: false,
    }
  },
  created() {
    this.queryParams.branchIds = this.$getCookie('cid')
    this.isOnline = this.$getCookie('isOnline') == '1' ? true : false

    this.queryParams.startTime = this.$getDate().split(' ')[0]
    this.queryParams.endTime = this.$getDate().split(' ')[0]
    this.getBasic()
    this.getList()
  },
  methods: {
    getBasic() {
      getBranchData().then((res) => {
        this.fzxOptions = res.data
      })
    },
    //左表选中事件
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
      if (selection.length > 1) {
        this.$refs.tableData.clearSelection()
        this.$refs.tableData.toggleRowSelection(selection.pop())
      }
    },
    //左表单击行
    handleRowClick(row) {
      this.$refs.tableData.toggleRowSelection(row, true)
      this.loadingr = true
      let startTime, endTime
      startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : ''
      endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : ''
      let obj = {
        current: this.queryParams.current,
        size: this.queryParams.size,
        branchIds: this.queryParams.branchIds,
        id: row.id,
        isContainNuclein: this.queryParams.isContainNuclein,
        isContainVaccine: this.queryParams.isContainVaccine,
        startTime,
        endTime,
        type: '1', //默认写死
      }
      this.getRightSearch = obj
      if (!row.id) {
        this.$alert('	该记录不存在体检号！', '提示', {
          type: 'warning',
        })
        this.$refs.tableData.clearSelection()
        this.loadingr = false
        return
      }
      getListData(obj)
        .then((res) => {
          this.tableDatas = res.data.records
          this.rightTotal = res.data.total
          this.loadingr = false
        })
        .catch(() => {
          this.loadingr = false
        })
    },
    //右表分页
    getRightBasic() {
      this.getRightSearch.current = this.queryRightParams.current
      this.getRightSearch.size = this.queryRightParams.size
      getListData(this.getRightSearch)
        .then((res) => {
          this.tableDatas = res.data.records
          this.rightTotal = res.data.total
          this.loadingr = false
        })
        .catch(() => {
          this.loadingr = false
        })
    },
    //导出
    handleexport() {
      this.queryParams.isByDay = 0
      let startTime, endTime
      startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : ''
      endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : ''
      let obj = {
        id: this.ids[0],
        branchIds: this.queryParams.branchIds || getCookie('cid'),
        isByDay: 0, //是否按天1-是,0-否;
        isContainNuclein: this.queryParams.isContainNuclein,
        isContainVaccine: this.queryParams.isContainVaccine,
        startTime,
        endTime,
        // type:"1",//0 客服 1 销售
      }
      // exportTable(obj).then((res)=>{
      // 	this.$modal.msgSuccess("操作成功")
      // })

      this.download(
        '/finance/individualStatistics/export',
        {
          ...obj,
        },
        `销售个检费用导出.xlsx`
      )
    },
    //按天导出
    handleExportbyday() {
      this.queryParams.isByDay = 0
      let startTime, endTime
      startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : ''
      endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : ''
      let obj = {
        id: this.ids[0],
        branchIds: this.queryParams.branchIds || getCookie('cid'),
        isByDay: 1, //是否按天1-是,0-否;
        isContainNuclein: this.queryParams.isContainNuclein,
        isContainVaccine: this.queryParams.isContainVaccine,
        startTime,
        endTime,
        // type:"1",//0 客服 1 销售
      }
      // exportByDay(obj).then((res)=>{
      // 	this.$modal.msgSuccess("操作成功")
      // })
      this.download(
        '/finance/individualStatistics/export',
        {
          ...obj,
        },
        `销售个检费用导出(按天).xlsx`
      )
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    //左表分页查询
    getList() {
      this.loading = true
      let startTime, endTime
      startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : ''
      endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : ''
      page({ ...this.queryParams, startTime, endTime })
        .then((res) => {
          this.tableData = res.data.records
          this.total = res.data.total
          this.loading = false
          this.tableDatas = []
          this.rightTotal = 0
        })
        .catch(() => {
          this.loading = false
        })
    },
  },
}
</script>

<style scoped>
.item-box {
  background: #fff;
  height: 100%;
}
</style>
