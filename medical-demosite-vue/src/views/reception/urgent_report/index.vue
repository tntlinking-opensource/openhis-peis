<!-- 加急报告 开发人：麦沃德科技暴雨、予安 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 150px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 150px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="联系方式" prop="phone">
        <el-input v-model="queryParams.phone" placeholder="请输入联系方式" clearable style="width: 150px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="2">
        <el-button type="success" plain size="mini" icon="el-icon-s-promotion" @click="handleUpdate" v-hasPermi="['reception:urgentReport:edit']">加急</el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
        <el-table-column fixed type="selection" align="center"/>
        <el-table-column label="序列" type="index" width="65" align="center"/>
        <el-table-column label="体检号" prop="patientcode" align="center" />
        <el-table-column label="姓名" prop="patientname" align="center" />
        <el-table-column label="性别" prop="sex" align="center">
          <template slot-scope="scope">
            <span>{{ ['男', '女'][scope.row.sex] }}</span>
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="age" align="center" />
        <el-table-column label="联系方式" prop="phone" align="center" />
        <el-table-column label="加急状态" prop="isjj" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.isjj == 0">未加急</el-tag>
            <el-tag type="danger" v-else>加急</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改对话框 -->
    <add-items ref="addItems" @getList="getList"></add-items>
  </div>
</template>
<script>
import { getListData } from '@/api/reception/urgent_report.js'
import addItems from './add'
export default {
  components: { addItems },
  props: [],
  data() {
    return {
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
      // 显示搜索条件
      showSearch: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 10,
        patientcode: undefined,
        patientname: undefined,
        phone: undefined,
      },
      // 表格展示数据
      tableList: [],
      // 显示模态框
      showExam: false,
      showUpload: false,
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams)
        .then(({ data }) => {
          this.tableList = data.records
          this.total = data.total
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
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
    // 添加
    handleAdd() {
      this.$refs.addItems.handleAdd()
    },
    // 删除
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(function () {
          // return delPrinttype(ids);
          return
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {})
    },
    // 编辑
    handleUpdate(row) {
      this.$refs.addItems.handleUpdate(row)
    },
  },
}
</script>
