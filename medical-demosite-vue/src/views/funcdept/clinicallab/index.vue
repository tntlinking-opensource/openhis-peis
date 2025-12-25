<!-- 加项处理 开发人：麦沃德科技暴雨/矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <!--页面-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="科室" prop="type">
        <input-select ref="idLabtype" :selectData="typeData" selectWidth="160" @change="typeDataChange"></input-select>
      </el-form-item>
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入姓名" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检团体" prop="khdwmcid">
        <el-input v-model="queryParams.khdwmcid" placeholder="请输入体检团体" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="处理状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择" clearable style="width: 180px" @change="handleQuery">
          <el-option label="未处理" :value="0" />
          <el-option label="处理中" :value="1" />
          <el-option label="检验结束" :value="2" />
          <el-option label="需要重检" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="选择日期" prop="date">
        <el-date-picker v-model="queryParams.date" style="width: 360px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" @change="handleQuery"></el-date-picker>
      </el-form-item>
      <el-form-item label="类型" prop="lx">
        <el-select v-model="queryParams.lx" placeholder="请选择" clearable style="width: 180px" @change="handleQuery">
          <el-option label="加项(含检验科普通增加)" :value="0" />
          <el-option label="弃检" :value="1" />
          <el-option label="补检" :value="2" />
          <el-option label="迟检(检验科)" :value="3" />
          <el-option label="退项(检验科)" :value="4" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleeditRow" :disabled="multiple" v-hasPermi="['funcdept:clinicallab:editRow']">处理 </el-button>
      </el-col>
      <el-col :span="2">
        <el-button type="danger" plain size="mini" icon="el-icon-plus" @click="handlenoeditRow" v-hasPermi="['funcdept:clinicallab:noeditRow']">反处理 </el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" @selection-change="handleSelectionChange" height="100%" stripe>
        <el-table-column fixed type="selection" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="团体名称" prop="orgName" align="center" show-overflow-tooltip />
        <el-table-column label="体检号" prop="patientcode" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" align="center" show-overflow-tooltip />
        <el-table-column label="性别" prop="idSex" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.idSex == 0" type="primary">男</el-tag>
            <el-tag v-else type="danger">女</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="age" align="center" show-overflow-tooltip />
        <el-table-column label="收费项目名称" prop="examfeeitemName" align="center" show-overflow-tooltip />
        <el-table-column label="项目科室" prop="ksname" align="center" show-overflow-tooltip />
        <el-table-column label="加项医生" prop="jxys" align="center" show-overflow-tooltip />
        <el-table-column label="登记人" prop="user" align="center" show-overflow-tooltip />
        <el-table-column label="处理人" prop="handleNameId" align="center" show-overflow-tooltip />
        <el-table-column label="处理状态" prop="status" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="danger" v-if="scope.row.status == 0">未处理</el-tag>
            <el-tag type="warning" v-else-if="scope.row.status == 1">处理中</el-tag>
            <el-tag type="success" v-else-if="scope.row.status == 2">检验结束</el-tag>
            <el-tag type="primary" v-else-if="scope.row.status == 3">需要重检</el-tag>
            <el-tag v-else></el-tag>
          </template>
        </el-table-column>
        <el-table-column label="处理意见" prop="idea" align="center" show-overflow-tooltip />
        <el-table-column label="创建日期" prop="createdate" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改对话框 -->
    <add-items ref="addItems"></add-items>
  </div>
</template>
<script>
import { getListData, handleDetail } from '@/api/funcdept/clinicallab'
import addItems from './add'
export default {
  components: { addItems },
  data() {
    return {
      typeData: {
        placeholder: '请输入输入码',
        key: '输入码', //第一列标题
        value: '科室', //第二列标题
        url: '/reception/register/getKsData', //请求连接
      },
      handleParams: {
        //	处理0，反处理1
        type: 0,
        // 选中数组
        ids: undefined,
      },
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      state: [
        { id: 0, text: '未处理' },
        { id: 1, text: '处理中' },
        { id: 2, text: '检验结束' },
        { id: 3, text: '需要重检' },
      ],
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        idLabtype: undefined,
        patientcode: undefined,
        name: undefined,
        khdwmcid: undefined,
        status: 0,
        date: undefined,
        type: undefined,
        lx: undefined,
      },
      ids: [],
      // 表格展示数据
      tableList: [],
    }
  },
  created() {
    this.queryParams.date = [this.$getDate().split(' ')[0], this.$getDate().split(' ')[0]]
    this.handleQuery()
  },
  methods: {
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.multiple = !selection.length
    },
    typeDataChange(value) {
      this.queryParams.type = value.id
      this.typeData.bindValue = value
    },
    // 处理
    handleeditRow() {
      for (var i in this.ids) {
        if (i == 0) this.handleParams.ids = this.ids[0]
        else this.handleParams.ids += ',' + this.ids[0]
      }
      this.handleParams.type = 0
      const data = this.handleParams
      this.$modal
        .confirm('确定要处理？')
        .then(function () {
          return handleDetail(data)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('处理成功')
        })
        .catch(() => {})
    },
    // 反处理
    handlenoeditRow() {
      for (var i in this.ids) {
        if (i == 0) this.handleParams.ids = this.ids[0]
        else this.handleParams.ids += ',' + this.ids[0]
      }
      this.handleParams.type = 1
      const data = this.handleParams
      this.$modal
        .confirm('确定要反处理？')
        .then(function () {
          return handleDetail(data)
        })
        .then(() => {
          this.$modal.msgSuccess('反处理成功')
          this.getList()
        })
        .catch(() => {})
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
      this.queryParams.current = 1
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.$refs.idLabtype.initData()
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
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        'system/user/export',
        {
          ...this.queryParams,
        },
        `user_${new Date().getTime()}.xlsx`
      )
    },
  },
}
</script>
