<!-- 检验加项 开发人：麦沃德科技暴雨、予安 -->
<template>
  <div class="app-container flex-direction-column">
    <!--页面-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 150px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入姓名拼音码" clearable style="width: 150px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检团体" prop="khdwmcid">
        <input-select :selectData="selectData" @change="selectChange" :initialValue="queryParams.khdwmcid"></input-select>
      </el-form-item>
      <el-form-item label="处理状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择" clearable style="width: 120px">
          <el-option label="未处理" :value="0" />
          <el-option label="处理中" :value="1" />
          <el-option label="检验结束" :value="2" />
          <el-option label="需要重检" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="创建日期" prop="date">
        <el-date-picker v-model="queryParams.date" style="width: 300px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="类型" prop="lx">
        <el-select v-model="queryParams.lx" placeholder="请选择" clearable style="width: 120px">
          <el-option label="加项" :value="0" />
          <el-option label="弃检" :value="1" />
          <el-option label="补检" :value="2" />
          <el-option label="迟检补检" :value="3" />
          <el-option label="退项" :value="4" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" type="primary" size="mini" @click="handleQuery">查询</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleDeal" v-hasPermi="['funcdept:sample:InspectionAddition:handle']" :disabled="multiple">处理 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-plus" @click="handleRedeal" v-hasPermi="['funcdept:sample:InspectionAddition:nohandle']" :disabled="multiple">反处理 </el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column fixed type="selection" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="团体名称" prop="orgName" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="体检号" prop="patientcode" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" min-width="100px" align="center" />
        <el-table-column label="性别" prop="idSex" min-width="60px" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.idSex == 0">男</span>
            <span v-if="scope.row.idSex == 1">女</span>
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="age" min-width="60px" align="center" show-overflow-tooltip />
        <el-table-column label="收费项目名称" prop="examfeeitemName" min-width="140px" align="center" show-overflow-tooltip />
        <el-table-column label="项目科室" prop="ksname" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="类型" prop="type" min-width="120px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.type == 0">加项</el-tag>
            <el-tag type="danger" v-else-if="scope.row.type == 1">弃检</el-tag>
            <el-tag type="warning" v-else-if="scope.row.type == 2">补检</el-tag>
            <el-tag type="success" v-else-if="scope.row.type == 3">迟检补检</el-tag>
            <el-tag type="danger" v-else>退项</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="加项医生" prop="jxys" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="操作人" prop="creater" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="登记人" prop="user" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="处理人" prop="handleName" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="处理状态" prop="statuss" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="warning" v-if="scope.row.statuss == 0">未处理</el-tag>
            <el-tag v-else-if="scope.row.statuss == 1">处理中</el-tag>
            <el-tag type="success" v-else-if="scope.row.statuss == 2">检验结束</el-tag>
            <el-tag type="danger" v-else-if="scope.row.statuss == 3">需要重检</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="处理意见" prop="idea" min-width="180px" align="center" show-overflow-tooltip />
        <el-table-column label="处理日期" prop="handleTime" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="创建日期" prop="createdate" min-width="160px" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改对话框 -->
    <deal-items ref="dealItems" @getList="getList"></deal-items>
  </div>
</template>
<script>
import { getListData, handleDeals } from '@/api/funcdept/sample/inspection_addition.js'
import dealItems from './deal.vue'
export default {
  name: 'Inspection_addition',
  components: { dealItems },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 体检单位数据
      selectData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '团体名称', //第二列标题
        url: '/abteilung/CrisisValue/getListData', //请求连接
        selectWidth: 150, //选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(必传)
        firstName: 'khdwsrm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        patientcode: undefined,
        name: undefined,
        khdwmcid: undefined,
        status: 0,
        date: undefined,
        startTime: undefined,
        endTime: undefined,
        lx: undefined,
      },
      // 表格展示数据
      tableList: [],
    }
  },
  created() {
    this.queryParams.date = [this.$getDate().split(' ')[0], this.$getDate().split(' ')[0]]
    this.getList()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0]
        this.queryParams.endTime = this.queryParams.date[1]
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      getListData(this.queryParams).then(({ data }) => {
        this.tableList = data.records
        this.total = data.total
        this.loading = false
      })
    },
    // 选择体检团体返回值
    selectChange(value) {
      this.queryParams.khdwmcid = value.id
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.queryParams.date = []
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },

    // 处理
    handleDeal() {
      if (!this.single) {
        this.$refs.dealItems.handleDeal(this.ids[0])
      } else {
        this.$confirm('是否执行批量处理操作?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        })
          .then(() => {
            let query = {
              ids: this.ids.join(','),
              type: 0,
            }
            handleDeals(query).then(() => {
              this.$modal.msgSuccess('批量处理成功')
              this.getList()
            })
          })
          .catch(() => {})
      }
    },
    // 反处理
    handleRedeal() {
      if (!this.single) {
        this.$refs.dealItems.handleRedeal(this.ids[0])
      } else {
        this.$confirm('是否执行批量反处理操作?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        })
          .then(() => {
            let query = {
              ids: this.ids.join(','),
              type: 1,
            }
            handleDeals(query).then(() => {
              this.$modal.msgSuccess('批量反处理成功')
              this.getList()
            })
          })
          .catch(() => {})
      }
    },
  },
}
</script>
