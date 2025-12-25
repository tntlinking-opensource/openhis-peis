<!-- 科室工作量  开发人：麦沃德科技 予安 / 暴雨-->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item label="体检科室" prop="ks">
        <input-select :selectData="selectData" @change="selectChange"></input-select>
      </el-form-item>
      <el-form-item label="订单号" prop="numorgresv">
        <el-input v-model="queryParams.numorgresv" placeholder="请输入订单号" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="团检单位" prop="idOrg">
        <input-select :selectData="selectData2" @change="selectChange2"></input-select>
      </el-form-item>
      <el-form-item label="审核状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择" style="width: 200px" clearable>
          <el-option label="未审核" :value="0"></el-option>
          <el-option label="已审核" :value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="医师" prop="name">
        <!-- <el-input v-model="queryParams.name" placeholder="请输入医师" clearable style="width: 200px" @keyup.enter.native="handleQuery" /> -->
        <input-select :selectData="selectData3" @change="selectChange3"></input-select>
      </el-form-item>
      <el-form-item label="录入人" prop="writename">
        <el-input v-model="queryParams.writename" placeholder="请输入录入人" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="审核人" prop="auditName">
        <el-input v-model="queryParams.auditName" placeholder="请输入审核人" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item prop="startTime" label="登记日期">
        <el-date-picker v-model="queryParams.startTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="endTime" label="至">
        <el-date-picker v-model="queryParams.endTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="startTime2" label="审核日期">
        <el-date-picker v-model="queryParams.startTime2" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="endTime2" label="至">
        <el-date-picker v-model="queryParams.endTime2" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['statistical:workload:divisionWorkload:export']">导出Excel</el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="体检科室" prop="division0" align="center" min-width="200" show-overflow-tooltip />
        <el-table-column label="体检号" prop="division1" align="center" min-width="130" show-overflow-tooltip />
        <el-table-column label="姓名" prop="division2" align="center" min-width="80" show-overflow-tooltip />
        <el-table-column label="性别" prop="division3" align="center" min-width="60" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.division3 == 0">男</span>
            <span v-if="scope.row.division3 == 1">女</span>
            <span v-if="scope.row.division3 == 2">通用</span>
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="division4" align="center" min-width="60" show-overflow-tooltip />
        <el-table-column label="联系电话" prop="division15" align="center" min-width="130" show-overflow-tooltip />
        <el-table-column label="身份证号" prop="idcardno" align="center" min-width="190" show-overflow-tooltip />
        <el-table-column label="会员等级" prop="division14" align="center" min-width="120" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.division14 == 1">普通会员</span>
            <span v-if="scope.row.division14 == 2">VIP</span>
            <span v-if="scope.row.division14 == 3">VVIP</span>
          </template>
        </el-table-column>
        <el-table-column label="团检单位" prop="division5" align="center" min-width="160" show-overflow-tooltip />
        <el-table-column label="部门" prop="orgDepart" align="center" min-width="120" show-overflow-tooltip />
        <el-table-column label="医师" prop="division10" align="center" min-width="120" show-overflow-tooltip />
        <el-table-column label="订单号" prop="numorgresv" align="center" min-width="120" show-overflow-tooltip />
        <el-table-column label="审核情况" prop="division11" align="center" min-width="120" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.division11 == 1" style="color: green">已审核</span>
            <span v-else style="color: red">未审核</span>
          </template>
        </el-table-column>
        <el-table-column label="审核日期" prop="division12" align="center" min-width="120" show-overflow-tooltip />
        <el-table-column label="开单医师" prop="doctorapply" align="center" min-width="120" show-overflow-tooltip />
        <el-table-column label="登记日期" prop="division7" align="center" min-width="120" show-overflow-tooltip />
        <el-table-column label="体检时间" prop="division8" align="center" min-width="120" show-overflow-tooltip />
        <el-table-column label="录入人" prop="division9" align="center" min-width="120" show-overflow-tooltip />
        <el-table-column label="小结" prop="division13" align="center" min-width="160" show-overflow-tooltip />
      </el-table>
    </div>
    <div style="display: flex; align-items: center; justify-content: space-between; width: 100%">
      <span>
        合计工作量:
        <span>{{ total }}</span>
      </span>
      <!-- 分页 -->
      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    </div>
  </div>
</template>
<script>
import { getListdata } from '@/api/statistical/division_workload'
export default {
  name: 'Division_workload',
  data() {
    return {
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        divisionId: undefined,
        status: undefined,
        name: undefined,
        writename: undefined,
        idOrg: undefined,
        ks: undefined,
        startTime: undefined,
        endTime: undefined,
        startTime2: undefined,
        endTime2: undefined,
        auditName:undefined
      },
      // 科室参数
      selectData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '科室', //第二列标题
        url: '/reception/register/getKsData', //请求连接
        selectWidth: 200, //选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(必传)
        firstName: 'inputCode', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'name', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'inputCode', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 团检单位参数
      selectData2: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '团检单位', //第二列标题
        url: '/abteilung/sectionResultPlan/getOrgs', //请求连接`
        bindValue: '',
        firstName: 'khdwsrm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 医师参数
      selectData3: {
        placeholder: '请输入输入码选择',
        value: '医师姓名', //第二列标题
        url: '/drugstore/prescribe/getDoctor', //请求连接
        selectWidth: 160, //选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(必传)
        secondName: '', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 表格数据
      tableList: [],
    }
  },
  created() {
    this.queryParams.startTime = this.$getDate().split(' ')[0]
    this.queryParams.endTime = this.$getDate().split(' ')[0]
    this.handleQuery()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      getListdata(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.queryParams.startTime2 = this.queryParams.startTime2 ? this.queryParams.startTime2.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime2 = this.queryParams.endTime2 ? this.queryParams.endTime2.slice(0, 10) + ' 23:59:59' : undefined
      this.getList()
    },
    // 科室选择返回值
    selectChange(value) {
      this.queryParams.ks = value.id
    },
    // 团检单位选择返回值
    selectChange2(value) {
      this.queryParams.idOrg = value.id
    },
    // 医师选择返回值
    selectChange3(value) {
      this.queryParams.name = value.name
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.$delete(this.selectData, 'bindValue')
      this.$delete(this.selectData2, 'bindValue')
      this.$delete(this.selectData3, 'bindValue')
      this.$set(this.selectData, 'bindValue', '')
      this.$set(this.selectData2, 'bindValue', '')
      this.$set(this.selectData3, 'bindValue', '')
      this.handleQuery()
    },
    // 导出
    handleExport() {
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.queryParams.startTime2 = this.queryParams.startTime2 ? this.queryParams.startTime2.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime2 = this.queryParams.endTime2 ? this.queryParams.endTime2.slice(0, 10) + ' 23:59:59' : undefined
      this.download('/statistics/divisionWorkload/export', this.queryParams, `工作量统计-科室工作量.xlsx`)
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
  },
}
</script>
