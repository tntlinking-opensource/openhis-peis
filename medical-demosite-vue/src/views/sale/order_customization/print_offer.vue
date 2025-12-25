<!-- 套餐打印  开发人：麦沃德科技矢北、予安 -->
<template>
  <el-dialog title="套餐打印" :visible.sync="open" class="add-charge" width="1200px" append-to-body>
    <el-form size="small" :inline="true" ref="queryParams" :model="queryParams">
      <el-form-item label="体验套餐名称" prop="tjtcmc">
        <el-input style="width: 180px" v-model="queryParams.tjtcmc" placeholder="请输入体检套餐名称"></el-input>
      </el-form-item>
      <el-form-item label="体验套餐输入码" prop="tjtcsrm">
        <el-input style="width: 180px" v-model="queryParams.tjtcsrm" placeholder="请输入输入码"></el-input>
      </el-form-item>
      <el-form-item label="体检类型" prop="tjlx">
        <el-select v-model="queryParams.tjlx" placeholder="请选择体检类型" style="width: 180px">
          <el-option label="健康体检" :value="0" />
          <el-option label="职业体检" :value="1" />
          <el-option label="综合" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="适用性别" prop="syxb">
        <el-select v-model="queryParams.syxb" placeholder="请选择是否启用" style="width: 180px">
          <el-option label="男" :value="0" />
          <el-option label="女" :value="1" />
          <el-option label="通用" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="search">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格部分 -->
    <div class="table-box">
      <el-table ref="tableData" border v-loading="loading" :data="tableList" height="500px" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" min-width="120" align="center" />
        <el-table-column prop="tjtcmc" label="体检套餐名称" min-width="160" align="center" show-overflow-tooltip />
        <el-table-column prop="tjlx" label="体检类型" align="center" min-width="120">
          <template slot-scope="scope">
            {{ ['健康体检', '职业体检', '综合'][scope.row.tjlx] }}
          </template>
        </el-table-column>
        <el-table-column prop="tjtcjc" label="体检套餐简称" align="center" min-width="160" show-overflow-tooltip />
        <el-table-column prop="tjtcsrm" label="体检套餐输入码" align="center" min-width="140" show-overflow-tooltip />
        <el-table-column prop="jhysName" label="接害因素" align="center" min-width="120" show-overflow-tooltip />
        <el-table-column prop="syxb" label="适用性别" align="center" min-width="120">
          <template slot-scope="scope">
            {{ ['男', '女', '通用'][scope.row.syxb] }}
          </template>
        </el-table-column>
        <el-table-column prop="tcysjg" label="套餐原始价格" align="center" min-width="120" />
        <el-table-column prop="tczk" label="套餐折扣" align="center" min-width="120" />
        <el-table-column prop="khdwmc" label="客户单位名称" align="center" min-width="160" show-overflow-tooltip />
        <el-table-column prop="sfybd" label="是否已备单" align="center" min-width="120">
          <template slot-scope="scope">
            {{ ['否', '是'][scope.row.sfybd] }}
          </template>
        </el-table-column>
        <el-table-column prop="sfyhtc" label="是否已婚套餐" align="center" min-width="120">
          <template slot-scope="scope">
            {{ ['是', '否'][scope.row.sfyhtc] || '通用' }}
          </template>
        </el-table-column>
        <el-table-column prop="nlz" label="年龄自" align="center" min-width="120" />
        <el-table-column prop="nld" label="年龄至" align="center" min-width="120" />
        <el-table-column prop="fkfs" label="付款方式" align="center" min-width="120">
          <template slot-scope="scope">
            {{ ['统收', '自费'][scope.row.fkfs] }}
          </template>
        </el-table-column>
        <el-table-column prop="zytjlb" label="职业体检类别" align="center" min-width="120">
          <template slot-scope="scope">
            {{ ['上岗前', '在岗期间', '离岗时', '离岗后', '应急'][scope.row.zytjlb] }}
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <div slot="footer" class="dialog-footer">
      <el-button size="mini" type="primary" @click="handlePreview">预览</el-button>
      <el-button size="mini" @click="cancel">取 消</el-button>
    </div>

    <!-- 打印套餐样式 -->
    <check-query ref="checkQuery"></check-query>
  </el-dialog>
</template>

<script>
import { getApproveTjtcData } from '@/api/sale/order_customization.js'

import checkQuery from './query.vue'

export default {
  components: {
    checkQuery,
  },
  data() {
    return {
      //页面开关
      open: false,
      // 查询参数
      queryParams: {
        current: 1,
        size: 10,
        tjtcmc: undefined,
        tjtcsrm: undefined,
        tjlx: undefined,
        syxb: undefined,
      },
      // 遮罩层
      loading: true,
      // 表格数据
      tableList: [],
      //页面总数
      total: 0,
      // 选中的数据
      ids: [],
    }
  },
  created() {
    this.getList()
  },
  methods: {
    //打开对话框
    showDialog(apprddId) {
      this.reset()
      this.queryParams.apprddId = apprddId
      this.open = true
      this.getList()
    },
    // 查询列表
    getList() {
      this.loading = true
      getApproveTjtcData(this.queryParams).then(({ data: res }) => {
        let itemIndex = 0
        res.data.forEach((el) => {
          el.itemIndex = itemIndex++
        })
        this.tableList = res.data
        this.total = res.total
        this.loading = false
      })
    },
    // 执行搜索
    search() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.reset()
      this.search()
    },
    // 预览
    handlePreview() {
      if (!this.ids.length) {
        this.$alert('请选择一条或多条记录', '提示')
        return
      }
      let itemIndex = this.ids.map((item) => item.itemIndex)
      // 选中套餐进行下一步
      this.$refs.checkQuery.queryWindow(this.queryParams.apprddId, itemIndex)
    },
    // 表单重置
    reset() {
      this.resetForm('queryParams')
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item)
    },
    // 单击某行
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.tableData.clearSelection()
      this.$refs.tableData.toggleRowSelection(row, true)
    },
    // 取消按钮
    cancel() {
      this.open = false
    },
  },
}
</script>

<style></style>
