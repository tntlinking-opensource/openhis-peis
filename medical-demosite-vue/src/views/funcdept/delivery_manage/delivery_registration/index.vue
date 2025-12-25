<!-- 外送登记 开发人：麦沃德科技矢北/暴雨 -->
<template>
  <div class="app-container flex-direction-column">
    <!--页面-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="创建日期">
        <el-date-picker v-model="queryParams.date" style="width: 350px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="外送机构" prop="wsjg">
        <input-select :selectData="wsjgData" selectWidth="160" @change="wsjgDataChange"></input-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['funcdept:deliveryManage:deliveryRegistration:add']">登记 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" @click="handleDelete" v-hasPermi="['funcdept:deliveryManage:deliveryRegistration:remove']">删除 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['funcdept:deliveryManage:deliveryRegistration:export']">导出 </el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box" ref="tableBox">
      <el-table ref="tableList" border v-loading="loading" :data="tableList" stripe height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column fixed type="selection" align="center" />
        <el-table-column label="序列" type="index" align="center" />
        <el-table-column label="项目名称" prop="xmmc" min-width="200px" align="center" show-overflow-tooltip />
        <el-table-column label="体检号" prop="patientcode" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="xm" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="性别" prop="xb" min-width="120px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.xb == 0">男</span>
            <span v-else-if="scope.row.xb == 1">女</span>
          </template>
        </el-table-column>
        <el-table-column label="承送人" prop="sendPeople" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="外送机构" prop="wsjg" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="外送时间" prop="sendDate" min-width="120px" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改对话框 -->
    <add-items @getList="getList" ref="addItems"></add-items>
  </div>
</template>
<script>
import { getListData, deleteListData } from '@/api/funcdept/delivery_manage/delivery_registration'
import addItems from './add'
export default {
  components: { addItems },
  data() {
    return {
      wsjgData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '名称', //第二列标题
        url: '/outside/sendRegister/getWsjgData', //请求连接
        bindValue: '',
        firstName: 'inputCode',
        secondName: 'wsjg',
        queryData: 'key',
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
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        patientcode: undefined,
        patientname: undefined,
        date: undefined,
        startTime: undefined,
        endTime: undefined,
        wsjg: undefined,
        // 分中心id
        branchIds: undefined,
      },
      // 表格展示数据
      tableList: [],
    }
  },
  created() {
    this.getList()
  },
  methods: {
    wsjgDataChange(value) {
      this.queryParams.wsjg = value.id
      this.wsjgData.bindValue = value.name
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.auditStatus = selection.map((item) => item.auditStatus)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 查询列表
    getList() {
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.loading = true
      getListData(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    // 搜索
    handleQuery() {
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      }
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.wsjgData.bindValue = ''
      this.queryParams.data = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 添加
    handleAdd() {
      this.$refs.addItems.handleAdd()
    },
    // 删除
    handleDelete() {
      const ids = this.ids
      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(function () {
          return deleteListData(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        '/outside/sendRegister/export',
        {
          ...this.queryParams,
        },
        `外送登记.xlsx`
      )
    },
  },
}
</script>
