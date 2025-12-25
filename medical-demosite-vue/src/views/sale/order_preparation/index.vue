<!-- 线上备单 麦沃德科技 开发人:清风 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 搜索栏 -->
    <el-form ref="queryForm" size="small" :inline="true" label-width="80px;" v-show="showSearch" @submit.native.prevent>
      <el-form-item label="订单号">
        <el-input style="width: 230px" placeholder="请输入订单号" v-model="queryParams.ddh"></el-input>
      </el-form-item>
      <el-form-item label="客户单位">
        <input-select :selectData="khdwmc" selectWidth="220" :initialValue="queryParams.khdwmcid" @change="companyChange"></input-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button size="mini" type="success" icon="el-icon-edit-outline" :disabled="single" plain @click="editWindow()" v-hasPermi="['sale:orderPreparation:edit']">编辑</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-upload2" plain @click="exportWindow" v-hasPermi="['sale:orderPreparation:exportExcel']">导出Excel</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="success" icon="el-icon-download" plain @click="healthDownload" v-hasPermi="['sale:orderPreparation:healthDownload']">健康导入模板下载</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="success" icon="el-icon-download" plain @click="occupationDownload" v-hasPermi="['sale:orderPreparation:occupationDownload']">职业导入模板下载</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="warning" icon="el-icon-notebook-2" plain @click="preparedDocuments" v-hasPermi="['sale:orderPreparation:preparedDocuments']">已备单</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="danger" icon="el-icon-switch-button" plain @click="overWindow" v-hasPermi="['sale:orderPreparation:over']">结束</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getPageOrder"></right-toolbar>
    </el-row>
    <!-- 表格数据 -->
    <div class="table-box">
      <el-table ref="tableList" :data="tableList" v-loading="loading" :border="true" @row-click="rowClick" @row-dblclick="handleDblClick" :stripe="true" @selection-change="handleSelectionChange" height="100%">
        <el-table-column type="selection" align="center"></el-table-column>
        <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
        <el-table-column prop="ddh" width="120px" show-overflow-tooltip label="订单号" align="center"></el-table-column>
        <el-table-column prop="ddmc" width="200px" show-overflow-tooltip label="订单名称" align="center"></el-table-column>
        <el-table-column prop="khdwmc" width="200px" show-overflow-tooltip label="团体单位名称" align="center"></el-table-column>
        <el-table-column prop="xsjl" width="120px" show-overflow-tooltip label="销售经理" align="center"></el-table-column>
        <el-table-column prop="submitTime" width="120px" show-overflow-tooltip label="提交审核日期" align="center"></el-table-column>
        <el-table-column prop="bdrq" width="120px" show-overflow-tooltip label="完成日期" align="center"></el-table-column>
        <el-table-column prop="jhjqc" width="120px" show-overflow-tooltip label="任务预定日期" align="center"></el-table-column>
        <el-table-column prop="jhjqd" width="120px" show-overflow-tooltip label="计划结束日期" align="center"></el-table-column>
        <el-table-column prop="yjrs" width="100px" show-overflow-tooltip label="预计人数" align="center"></el-table-column>
        <el-table-column prop="nums" width="100px" show-overflow-tooltip label="已备单人数" align="center"></el-table-column>
        <el-table-column prop="khdwdh" width="120px" show-overflow-tooltip label="团体联系方式" align="center"></el-table-column>
        <el-table-column prop="khdwzcdz" width="120px" show-overflow-tooltip label="团体地址" align="center"></el-table-column>
        <el-table-column prop="urls" width="120px" show-overflow-tooltip label="名单数量" align="center"></el-table-column>
        <el-table-column prop="qtxz" width="120px" show-overflow-tooltip label="前台须知" align="center"></el-table-column>
        <el-table-column width="140px" label="结束状态" align="center" fixed="right" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-tag type="danger" v-if="scope.row.fFinished == '1'">已结束</el-tag>
            <el-tag type="primary" v-else>未结束</el-tag>
          </template>
        </el-table-column>
        <el-table-column width="140px" label="状态" align="center" fixed="right">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.tbzt == 1">已备单</el-tag>
            <el-tag type="danger" v-else>未备单</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getPageOrder" />
    <editItems ref="editItems"></editItems>
  </div>
</template>

<script>
import { pageOrder, markTbzt, finishOrder } from '@/api/sale/order_preparation.js'

import editItems from './edit.vue'
export default {
  components: { editItems },
  data() {
    return {
      ids: [],
      single: true,
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      loading: false,
      // 查询参数
      total: 0,
      // 输入码选择
      khdwmc: {
        placeholder: '请输入输入码选择',
        value: '客户单位名称', //第二列标题
        url: '/sell/customer/getAllOrg', //请求连接
        bindValue: '',
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      queryParams: {
        current: 1,
        size: 10,
        ddh: '',
        khdwmcid: '',
      },
      tableList: [],
    }
  },
  created() {
    this.getPageOrder()
  },
  methods: {
    //获取page分页数据
    getPageOrder() {
      this.loading = true
      pageOrder(this.queryParams)
        .then((res) => {
          if (res.code == 200) {
            this.tableList = res.data.records
            this.queryParams.current = res.data.current
            this.queryParams.size = res.data.size
            this.total = res.data.total
            this.loading = false
          }
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getPageOrder()
    },
    // 重置
    resetQuery() {
      this.queryParams = {
        current: 1,
        size: 10,
        ddh: '',
        khdwmcid: '',
      }
      this.getPageOrder()
    },
    // 线上备单
    preparedDocuments() {
      if (this.single || this.multiple) {
        this.$message({
          message: '请选择一条或多条数据！',
          type: 'warning',
        })
        return
      }
      this.$confirm("确定要标记为'已备单'吗？", '确定？', {
        type: 'warning',
      })
        .then(() => {
          //执行备单
          const ids = this.ids.join(',')
          this.loading = true
          markTbzt({ ids })
            .then(() => {
              this.$modal.msgSuccess('已更改为已备单')
              this.loading = false
              this.getPageOrder()
            })
            .catch((error) => {
              console.error(error)
              this.loading = false
            })
        })
        .catch(() => {})
    },
    // 输入码选择
    companyChange(value) {
      this.queryParams.khdwmcid = value.id
    },
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    rowClick(row, column) {
      if (column.className == 'el-table-column--selection') {
        this.$refs.tableList.toggleRowSelection(row, true)
      } else {
        this.$refs.tableList.clearSelection()
        this.$refs.tableList.toggleRowSelection(row, true)
      }
    },
    //编辑
    editWindow() {
      this.$refs.editItems.editWindow(this.ids, '0')
    },
    //双击单元格
    handleDblClick(row) {
      this.$refs.editItems.editWindow(row.id, '1')
    },
    //结束
    overWindow() {
      if (this.single || this.multiple) {
        this.$message({
          message: '请选择一条或多条数据！',
          type: 'warning',
        })
        return
      }
      this.$confirm('确定要结束备单吗', '提醒', {
        type: 'warning',
      })
        .then(() => {
          const ids = this.ids.join(',')
          this.loading = true
          finishOrder({
            ids,
            type: '1',
          })
            .then(() => {
              this.$modal.msgSuccess('已更改为已结束')
              this.loading = false
              this.getPageOrder()
            })
            .catch((error) => {
              console.error(error)
              this.loading = false
            })
        })
        .catch(() => {})
    },
    exportWindow() {
      this.download(
        '/sell/itemListOnline/export',
        {
          ...this.queryParams,
        },
        `团体体检备单.xlsx`
      )
    },
    //健康导入模板下载
    healthDownload() {
      this.downloadStatic('/static/stencil/bdModel_jkapp.xlsx', '健康体检名单模板.xlsx')
    },
    //职业导入模板下载
    occupationDownload() {
      this.downloadStatic('/static/stencil/bdModel_zy.xlsx', '职业体检名单模板.xlsx')
    },
  },
}
</script>
