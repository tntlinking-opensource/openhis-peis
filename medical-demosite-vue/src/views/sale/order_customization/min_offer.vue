<template>
  <el-dialog title="引用最小套餐" :visible.sync="open" width="1480px" class="add-container min-offer" append-to-body :close-on-click-modal="false">
    <el-form ref="queryForm" size="small" :model="queryParams" :inline="true" hide-required-asterisk>
      <el-form-item label="体检套餐名称" prop="tjtcmc">
        <el-input v-model="queryParams.tjtcmc" placeholder="请输入体检套餐名称" clearable style="width: 280px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检套餐输入码" prop="tjtcsrm">
        <el-input v-model="queryParams.tjtcsrm" placeholder="请输入体检套餐输入码" clearable style="width: 280px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检类型" prop="tjlx">
        <el-select v-model="queryParams.tjlx" placeholder="请选择体检类型" clearable style="width: 280px">
          <el-option label="健康体检" :value="0"> </el-option>
          <el-option label="职业体检" :value="1"> </el-option>
          <el-option label="综合" :value="2"> </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table border ref="tableData" :data="tableList" stripe height="400px" v-loading="loading" @selection-change="handleSelectionChange" @row-click="rowClick">
      <el-table-column type="selection" width="55" align="center" fixed />
      <el-table-column label="序列" type="index" width="65" align="center" fixed />
      <el-table-column label="体检套餐名称" prop="tjtcmc" min-width="180" align="center" fixed />
      <el-table-column label="体检类型" prop="tjlx" min-width="180" align="center">
        <template slot-scope="scope">
          <span>{{ ['健康', '职业'][scope.row.tjlx] || '综合' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="体检套餐简称" prop="tjtcjc" min-width="180" align="center" />
      <el-table-column label="体检套餐输入码" prop="tjtcsrm" min-width="180" align="center" />
      <el-table-column label="接害因素" prop="jhys" min-width="180" align="center" />
      <el-table-column label="适用性别" prop="syxb" min-width="180" align="center">
        <template slot-scope="scope">
          <span>{{ ['男', '女',][scope.row.syxb] || '通用' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="原始套餐价格" prop="tcysjg" min-width="180" align="center" />
      <el-table-column label="套餐折扣" prop="tczk" min-width="180" align="center" />
      <el-table-column label="是否已婚套餐" prop="sfyhtc" min-width="180" align="center">
        <template slot-scope="scope">
          <span>{{ ['是', '否',][scope.row.syxb] || '通用' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="年龄自" prop="nlz" min-width="180" align="center" />
      <el-table-column label="年龄至" prop="nld" min-width="180" align="center" />
      <el-table-column label="职业体检类别" prop="zytjlb" min-width="180" align="center" />
    </el-table>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="diasubmitForm">新增</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
    <pagination :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </el-dialog>
</template>

<script>
import { listCreatecombo } from '@/api/basis/healthsmall.js'
export default {
  data() {
    return {
      ///页面显示控制
      open: false,
      // 筛选参数
      queryParams: {
        current: 1,
        size: 20,
        tjtcmc: null,
        tjtcsrm: null,
        tjlx: null
      },
      total: 0,
      loading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      tableList: []
    }
  },
  methods: {
    // 打开弹窗
    showDialog() {
      this.open = true
      this.getList()
    },
    // 查询列表
    getList() {
      this.loading = true
      listCreatecombo(this.queryParams).then(response => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
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
    // 单击某行
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.tableData.clearSelection() //清空表格数据
      this.$refs.tableData.toggleRowSelection(row, true)
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表单重置
    reset() {
      this.queryParams = {
        tjtcmc: undefined,
        tjtcsrm: undefined,
        tjlx: null
      }
      this.resetForm('form')
    },
    diasubmitForm() {
      if (this.ids.length == 0) {
        this.$modal.msg('请选择套餐')
      } else {
        this.$emit('minCombo', this.ids)
        this.open = false
      }
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    }
  }
}
</script>

<style lang="scss">
.min-offer {
  .el-dialog .el-dialog__body {
    padding-bottom: 0;
  }
}
</style>