<!-- 外送结果上传 开发人：麦沃德科技暴雨/矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <!--页面-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="外送时间">
        <el-date-picker v-model="queryParams.date" style="width: 360px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" type="primary" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['funcdept:deliveryManage:deliveryUpload:add']">新增 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit-outline" @click="handleUpdate(1)" :disabled="single" v-hasPermi="['funcdept:deliveryManage:deliveryUpload:edit']">编辑 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" @click="handleDelete" :disabled="multiple" v-hasPermi="['funcdept:deliveryManage:deliveryUpload:remove']">删除 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-view" :disabled="single" @click="handleUpdate(2)" v-hasPermi="['funcdept:deliveryManage:deliveryUpload:see']">查看 </el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box" ref="tableBox">
      <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="体检号" prop="patientcode" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="xm" align="center" show-overflow-tooltip />
        <el-table-column label="性别" prop="xb" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <div v-if="scope.row.xb == 0">男</div>
            <div v-else>女</div>
          </template>
        </el-table-column>
        <el-table-column label="承送人" prop="sendPeople" align="center" show-overflow-tooltip />
        <el-table-column label="外送机构" prop="wsjg" align="center" show-overflow-tooltip />
        <el-table-column label="外送时间" prop="sendDate" align="center" show-overflow-tooltip />
        <el-table-column label="结果返回人" prop="backPeople" align="center" show-overflow-tooltip />
        <el-table-column label="结果返回时间" prop="backDate" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改对话框 -->
    <!-- <add-items ref="addItems" @getList="getList"></add-items> -->
    <look-Up ref="lookUp"></look-Up>
  </div>
</template>
<script>
import { getListData, deleteListData, saveDataList, getDetails, editJudgment } from '@/api/funcdept/delivery_manage/delivery_upload'
import addItems from './add'
import lookUp from './lookUp'
export default {
  name: 'Delivery_upload',
  components: { addItems, lookUp },
  data() {
    return {
      judgmentQuery: {
        id: undefined,
      },
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      // 总条数
      query: {
        patientcode: undefined,
      },
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        patientname: '',
        patientcode: '',
        data: [],
      },
      // 表格展示数据
      tableList: [],
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.query.patientcode = selection.map((item) => item.patientcode)[0]
      this.query.patientname = selection.map((item) => item.xm)[0]
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
      ;(this.queryParams.date = []), (this.queryParams.startTime = undefined)
      this.queryParams.endTime = undefined
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 添加
    handleAdd() {
      // this.$refs.addItems.handleAdd()
      const obj = { path: '/funcdept/delivery_manage/delivery_upload_edit', name: 'Delivery_upload_edit' }
      this.$tab.closePage(obj).then(() => {
        this.$tab.openPage('新增外送登记结果处理', '/funcdept/delivery_manage/delivery_upload_edit', {})
      })
    },
    // 删除
    handleDelete(row) {
      const ids = row.id || this.ids.join(',')
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
    // 编辑
    handleUpdate(type) {
      const queryParams = JSON.stringify(this.query)
      this.judgmentQuery.id = this.ids[0]
      const id = this.judgmentQuery
      const obj = { path: '/funcdept/delivery_manage/delivery_upload_edit', name: 'Delivery_upload_edit' }
      let data = type == 1 ? { queryParams, id: this.judgmentQuery.id } : { queryParams, id: this.judgmentQuery.id, onlyCheck: true }
      let title = type == 1 ? '编辑外送登记结果处理' : '查看外送登记结果处理'
      if (type == 1) {
        editJudgment(id).then((response) => {
          if (response.code == 200) {
            // this.$refs.addItems.handleAdd(queryParams, this.judgmentQuery.id)
            this.$tab.closePage(obj).then(() => {
              this.$tab.openPage(title, '/funcdept/delivery_manage/delivery_upload_edit', data)
            })
          }
        })
      } else {
        this.$tab.closePage(obj).then(() => {
          this.$tab.openPage(title, '/funcdept/delivery_manage/delivery_upload_edit', data)
        })
      }
    },
  },
}
</script>
