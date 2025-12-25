<!-- 职业复查 开发人：麦沃德科技暴雨/予安  -->
<template>
  <div class="app-container flex-direction-column review-batch">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item label="订单号" prop="ddh">
        <el-input v-model="queryParams.ddh" placeholder="请输入订单号" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="团队名称" prop="orgId">
        <input-select :selectData="orgIdData" selectWidth="230" @change="orgIdDataChange" :initialValue="queryParams.orgId"></input-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="2">
        <el-button type="warning" plain size="mini" icon="el-icon-document-checked" @click="handleSave" v-hasPermi="['reception:reviewBatch:save']">保存 </el-button>
      </el-col>
    </el-row>
    <el-row class="table-box">
      <!--左侧页面-->
      <el-col :span="15" style="height: 100%">
        <el-table ref="tableData" v-loading="loading" height="100%" :data="tableList" border stripe @selection-change="handleSelectionChange" @row-click="rowClick">
          <el-table-column type="selection" min-width="55" align="center" />
          <el-table-column label="序列" type="index" width="65" align="center" />
          <el-table-column prop="patientcode" label="体检号" min-width="130" align="center" />
          <el-table-column prop="patientname" label="姓名" min-width="110" align="center" />
          <el-table-column prop="idSex" label="性别" min-width="80" align="center">
            <template slot-scope="scope">
              <span>{{ ['男', '女'][scope.row.idSex] }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="phone" label="电话" min-width="150" align="center" />
          <el-table-column prop="idcardno" label="身份证号" min-width="200" align="center" />
          <el-table-column prop="orgName" label="团体名称" min-width="200" align="center" show-overflow-tooltip />
          <el-table-column prop="numorgresv" label="订单号" min-width="100" align="center" />
          <el-table-column prop="examsuiteName" label="套餐" min-width="200" align="center" show-overflow-tooltip />
          <el-table-column prop="reviewCode" label="复查体检号" min-width="130" align="center" />
          <el-table-column prop="reviewReg" label="复查状态" min-width="90" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.reviewReg == 1" style="color: green">已登记</span>
              <span v-else-if="scope.row.reviewCode" style="color: red">未登记</span>
              <span v-else></span>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <!--右侧页面-->
      <el-col :span="9" style="height: 100%">
        <el-table v-loading="loadingR" height="100%" :data="tableListR" border stripe class="right-table">
          <el-table-column type="selection" align="center" />
          <el-table-column label="序列" type="index" width="65" align="center" />
          <el-table-column prop="name" label="项目名称" align="center" />
          <el-table-column prop="price" label="价格" align="center">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.price" :min="0" controls-position="right" :precision="1"></el-input-number>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getListData, getItemsListData, reviewBatch } from '@/api/reception/review_batch.js'

export default {
  components: {},
  props: [],
  data() {
    return {
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 团队名称数据
      orgIdData: {
        placeholder: '请输入拼音码',
        key: '输入码', //第一列标题
        value: '客户单位名称', //第二列标题
        url: '/sell/customer/getKhdwmcData', //请求连接
        firstName: 'khdwsrm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 查询参数
      queryParams: {
        ddh: undefined,
        orgId: undefined,
      },
      // 表格展示数据
      tableList: [],
      // 右侧相关******************
      // 加载中
      loadingR: false,
      // 表格数据
      tableListR: [],
    }
  },
 
  methods: {
    // 搜索
    handleQuery() {
      if (!this.queryParams.ddh && !this.queryParams.orgId) {
        this.$alert('请输入订单号或团体', '提示')
        return
      }
      this.getList()
    },
    // 查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams)
        .then(({ data }) => {
          this.tableList = data
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
      this.handleRightData()
    },
    // 获取右侧数据
    handleRightData() {
      this.loadingR = true
      getItemsListData(this.queryParams)
        .then(({ data }) => {
          this.tableListR = data
          this.loadingR = false
        })
        .catch(() => {
          this.loadingR = false
        })
    },
    // 选择团队名称
    orgIdDataChange(value) {
      this.queryParams.orgId = value.id
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 单击某行
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.tableData.clearSelection() //清空表格数据
      this.$refs.tableData.toggleRowSelection(row, true)
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
    },
    //保存
    handleSave() {
      if (!this.tableList.length || !this.tableListR.length) {
        this.$alert('没有可保存的内容', '提示')
        return
      }
      this.loading = this.loadingR = true
      reviewBatch({
        data: this.tableList,
        griddata: this.tableListR,
      })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('保存成功')
          this.loading = this.loadingR = false
        })
        .catch(() => {
          this.loading = this.loadingR = false
        })
    },
  },
}
</script>

<style scoped>
.review-batch .right-table /deep/ .el-table__row .el-table__cell {
  padding: 0 !important;
  height: 44px;
}
</style>
