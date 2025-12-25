<!-- 业务结算  开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form size="small" :model="queryParams" ref="queryForm" :inline="true" class="no-margin-bottom">
      <el-form-item prop="fzxId" label="分中心">
        <el-select v-model="queryParams.fzxId" placeholder="请选择分中心" clearable style="width: 230px" @change="handleQuery">
          <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId" />
        </el-select>
      </el-form-item>
      <el-form-item prop="userMobile" label="手机号">
        <el-input style="width: 230px" v-model="queryParams.userMobile" placeholder="请输入手机号"></el-input>
      </el-form-item>
      <el-form-item label="开始时间" prop="startTime">
        <el-date-picker v-model="queryParams.startTime" style="width: 230px" value-format="yyyy-MM-dd" type="date"></el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker v-model="queryParams.endTime" style="width: 230px" value-format="yyyy-MM-dd" type="date"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button class="zk-btn-style1" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button class="zk-btn-style2" icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <div class="table-box">
      <el-table ref="tableDate" border v-loading="loading" :data="tableDate" height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="分中心" align="center" prop="fzx" show-overflow-tooltip />
        <el-table-column label="体检号" align="center" prop="bizOrderNo" show-overflow-tooltip />
        <el-table-column label="手机号" align="center" prop="userMobile" show-overflow-tooltip />
        <el-table-column label="金额" align="center" prop="payAmount" show-overflow-tooltip />
        <el-table-column label="积分" align="center" prop="payScore" show-overflow-tooltip />
        <el-table-column label="支付方式" align="center" prop="payTypeName" show-overflow-tooltip />
        <el-table-column label="流水号" align="center" prop="bizPayNo" show-overflow-tooltip />
        <el-table-column label="是否退款" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.isClearing == 1">是</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div style="position: relative">
      <div style="position: absolute; padding-top: 12px">
        <div>金额合计：{{ totalPrice.payAmount }}</div>
        <div>积分合计：{{ totalPrice.payScore }}</div>
      </div>
      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    </div>
  </div>
</template>

<script>
import { getListApi, getTotal } from '@/api/applet/expense_detail.js'
import { getListApi as getBranchData } from '@/api/applet/center_list.js'
export default {
  name: 'Expense_detail',
  data() {
    return {
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        fzxId: '',
        userMobile: '',
        startTime: '',
        endTime: '',
      },
      // 总条数
      total: 0,
      // 维护表格数据
      tableDate: [],
      // 合计总额
      totalPrice: 0,
      // 分中心列表
      fzxOptions: [],
    }
  },
  created() {
    getBranchData({ size: 100 }).then(({ data }) => {
      this.fzxOptions = data.records
    })
    this.getList()
  },
  methods: {
    // 刷新
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 查询工作流列表 */
    getList() {
      this.loading = true
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : ''
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : ''
      getListApi(this.queryParams)
        .then(({ data }) => {
          this.tableDate = data.records
          this.total = data.total
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
      getTotal(this.queryParams).then(({ data }) => {
        this.totalPrice = data
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.imgId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableDate.clearSelection()
      this.$refs.tableDate.toggleRowSelection(row)
    },
  },
}
</script>
<style lang="scss">
.sub-center {
  .el-input-number {
    .el-input__inner {
      text-align: left;
    }
  }

  .label-title {
    display: block;
    font-weight: 600;
    font-size: 14px;
    line-height: 20px;
    color: #333333;
    margin-bottom: 12px;
  }
}
.report-setting {
  .setimg {
    display: flex;
    flex-direction: inherit;
    width: auto;
    img {
      background-color: transparent;
    }
    .btn {
      margin: 8px;
      width: 70px;
      height: 36px;
    }
  }
}
</style>

<style scoped>
.upload-report-setting /deep/ .el-upload-dragger {
  padding: 0;
  border: 0;
}

.upload-report-setting /deep/ .el-upload-list__item-name {
  display: none;
  /* max-width: 80px;
  padding: 0; */
}
</style>
