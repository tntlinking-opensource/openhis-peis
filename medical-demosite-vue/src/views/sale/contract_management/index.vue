<!-- 合同管理 麦沃得科技 开发人: 清风 / 暴雨 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form ref="queryForm" size="small" :inline="true" label-width="80px;" :model="queryParams" class="no-margin-bottom">
      <el-form-item label="合同名称" prop="htmc">
        <el-input style="width: 230px" placeholder="请输入合同名称" clearable @keyup.enter.native="handleQuery" v-model="queryParams.htmc"></el-input>
      </el-form-item>
      <el-form-item label="合同编号" prop="htbh">
        <el-input style="width: 230px" placeholder="请输入合同编号" clearable @keyup.enter.native="handleQuery" v-model="queryParams.htbh"></el-input>
      </el-form-item>
      <el-form-item label="单位名称" prop="khdwmc">
        <el-input style="width: 230px" placeholder="请输入单位名称" clearable @keyup.enter.native="handleQuery" v-model="queryParams.khdwmc"></el-input>
      </el-form-item>
      <el-form-item label="合同签订日期" prop="htqdrq">
        <el-date-picker style="width: 230px" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" v-model="queryParams.htqdrq"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-plus" plain @click="addWindow()" v-hasPermi="['sale:contract_management:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="success" icon="el-icon-edit-outline" plain @click="editWindow()" :disabled="single" v-hasPermi="['sale:contract_management:edit']">编辑</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="danger" icon="el-icon-delete" plain @click="deleteWindow()" :disabled="multiple" v-hasPermi="['sale:contract_management:delete']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-plus" plain @click="findWindow()" :disabled="single" v-hasPermi="['sale:contract_management:find']">查看</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="warning" icon="el-icon-download" plain @click="handleExport()" :disabled="single" v-hasPermi="['sale:contract_management:download']">下载合同</el-button>
      </el-col>
    </el-row>

    <div class="table-box">
      <el-table ref="tableData" :data="tableData" v-loading="loading" border stripe row-key="id" height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" align="center"></el-table-column>
        <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
        <el-table-column prop="htmc" label="合同名称" align="center" show-overflow-tooltip min-width="160"></el-table-column>
        <el-table-column prop="htbh" label="合同编号" align="center" show-overflow-tooltip min-width="150"></el-table-column>
        <el-table-column prop="khdwmc" label="客户单位名称" align="center" show-overflow-tooltip min-width="120"></el-table-column>
        <el-table-column prop="lxdh" label="联系电话" align="center" min-width="120"></el-table-column>
        <el-table-column prop="tjrs" label="体检人数" align="center" width="90"></el-table-column>
        <el-table-column prop="ys" label="预算" align="center"></el-table-column>
        <el-table-column prop="xsjl" label="销售经理" align="center" width="90"></el-table-column>
        <el-table-column prop="htqdrq" label="合同签订日期" align="center" min-width="120"></el-table-column>
        <el-table-column prop="tjksrq" label="体检开始日期" align="center" min-width="120"></el-table-column>
        <el-table-column prop="tjjsrq" label="体检结束日期" align="center" min-width="120"></el-table-column>
        <el-table-column prop="wjgs" label="文件个数" align="center" min-width="90"></el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <addItems ref="addItems" @getList="getList"></addItems>
  </div>
</template>

<script>
import addItems from './add.vue'
import { Dsellpact, getListData, downloadFiles } from '@/api/sale/contract_management'
export default {
  name:'Contract_management',
  components: { addItems },
  data() {
    return {
      ids: [],
      single: true,
      multiple: true,
      loading: false,
      // 查询参数
      total: 0,
      queryParams: {
        current: 1,
        size: 20,
        htmc: undefined,
        htbh: undefined,
        khdwmc: undefined,
        htqdrq: undefined,
      },
      tableData: [],
      open: false,
    }
  },
  created() {
    this.getList()
  },
  methods: {
    //查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams).then((response) => {
        this.tableData = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    // 表格选中时间
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.tableData.clearSelection()
      this.$refs.tableData.toggleRowSelection(row)
    },
    addWindow() {
      this.$refs.addItems.addWindow(0)
    },
    editWindow() {
      this.open = true
      this.$refs.addItems.addWindow(1, this.ids)
    },
    deleteWindow() {
      Dsellpact(this.ids).then((response) => {
        this.$modal.msgSuccess('删除成功')
        this.getList()
      })
    },
    findWindow() {
      this.$refs.addItems.addWindow(2, this.ids)
    },
    //下载
    handleExport() {
      this.downloadZip('/sell/sellpact/download?id=' + this.ids[0])
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1;
      this.getList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
  },
}
</script>
