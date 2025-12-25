<!-- 传图日志 开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <!-- <el-form size="small" :model="queryParams" ref="queryForm" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item prop="name" label="姓名">
        <el-input style="width: 230px" v-model="queryParams.name" placeholder="请输入名称或英文名" @keyup.enter.native="getList"></el-input>
      </el-form-item>
      <el-form-item prop="name" label="体检号">
        <el-input style="width: 230px" v-model="queryParams.name" placeholder="请输入名称或英文名" @keyup.enter.native="getList"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="getList">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form> -->
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">刷新</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-edit" :disabled="multiple" @click="handleDeal">处理</el-button>
      </el-col>
    </el-row>
    <div class="table-box">
      <el-table ref="table" id="setTable" :data="tableList" v-loading="loading" stripe @selection-change="handleSelectionChange" height="100%" @row-click="rowClick" row-key="id" border>
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column align="center" prop="patientcode" label="体检号" />
        <el-table-column align="center" prop="bodyPart" label="部位" />
        <el-table-column align="center" prop="jklx" label="接口类型" />
        <el-table-column align="center" prop="isProcessed" label="已处理">
          <template slot-scope="scope">
            {{ ['', '已处理'][scope.row.isProcessed] }}
          </template>
        </el-table-column>
        <el-table-column align="center" prop="remark" label="备注说明" />
        <el-table-column align="center" prop="createTime" label="报错时间" />
        <el-table-column class-name="small-padding fixed-width" label="操作" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #ffba00" @click="handleDeal(scope.row)">处理</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>

<script>
import { getPage, dealApi } from '@/api/PACS/transfer_journal.js'
export default {
  data() {
    return {
      queryParams: {
        current: 1,
        size: 20,
        name: '',
      },
      // 总数
      total: 0,
      // 表格数据
      tableList: [],
      // 遮罩层
      loading: false,
      // 非多个禁用
      multiple: true,
      // 非单个禁用
      single: true,
      // 选中数据
      selections: [],
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      getPage(this.queryParams)
        .then(({ data }) => {
          this.tableList = data.records
          this.total = data.total
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 重置参数
    resetQuery() {
      this.queryParams.name = ''
      this.getList()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.selections = selection
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    //单击事件选中当前行
    rowClick(row, col) {
      if (col && col.label == '操作') {
        return
      }
      if (col && col.type != 'selection') {
        this.$refs.table.clearSelection()
      }
      this.$refs.table.toggleRowSelection(row)
    },
    // 处理按钮
    handleDeal(selection) {
      this.$confirm('是否处理选择的数据?', '提示')
        .then(() => {
          this.loading = true
          var key = selection.rkey ? selection.rkey : this.selections.map((item) => item.rkey).join(',')
          dealApi(key)
            .then((res) => {
              this.$modal.msgSuccess('处理成功')
              this.getList()
              this.loading = false
            })
            .catch((err) => {
              console.error(err)
              this.loading = false
            })
        })
        .catch(() => {})
    },
  },
}
</script>
