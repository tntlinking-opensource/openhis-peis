<!-- pacs科室-历史 麦沃德科技 开发人 清风、予安 -->
<template>
  <el-dialog title="历史列表" :visible.sync="open" width="1400px" append-to-body class="pacs-history-main" :close-on-click-modal="false" @close="handleClose">
    <!-- 筛选 -->
    <el-form size="mini" :model="queryParams" ref="queryForm" :inline="true" @submit.native.prevent>
      <el-form-item prop="describe" label="描述关键词">
        <el-input style="width: 230px" v-model="queryParams.describe" placeholder="请输入描述关键词" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <div class="table-box" style="min-height: 520px">
      <el-table :data="tableData" v-loading="loading" border stripe height="100%">
        <!-- <el-table-column type="selection" align="center" min-width="60"></el-table-column> -->
        <el-table-column prop="patientcode" label="体检号" align="center" min-width="120"></el-table-column>
        <el-table-column prop="patientname" label="姓名" align="center" min-width="90"></el-table-column>
        <el-table-column prop="idSex" label="性别" align="center" min-width="60">
          <template slot-scope="scope">
            <span>{{ ['男', '女', '通用'][scope.row.idSex] }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="age" label="年龄" align="center" min-width="60"></el-table-column>
        <el-table-column prop="feeitemName" label="项目名称" align="center" min-width="220" show-overflow-tooltip></el-table-column>
        <el-table-column prop="description" label="描述" align="center" min-width="250">
          <template slot-scope="scope">
            <el-tooltip :content="scope.row.description" placement="top" :disabled="!scope.row.description" popper-class="history-popper-class">
              <div class="text-hide">{{ scope.row.description }}</div>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="conclusions" label="小结" align="center" min-width="220">
          <template slot-scope="scope">
            <el-tooltip :content="scope.row.conclusions" placement="top" :disabled="!scope.row.conclusions" popper-class="history-popper-class">
              <div class="text-hide">{{ scope.row.conclusions }}</div>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="dateregister" label="登记时间" align="center" min-width="160"></el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </el-dialog>
</template>

<script>
import { historyApi } from '@/api/PACS/section_modal.js'
export default {
  data() {
    return {
      open: false,
      // 加载中
      loading: false,
      // 体检号
      patientcode: undefined,
      queryParams: {
        current: 1,
        size: 10,
        describe: '',
      },
      total: 0,
      tableData: [],
    }
  },
  methods: {
    historyWindow(patientcode) {
      this.tableData = []
      this.patientcode = patientcode
      this.open = true
      this.getList()
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.handleClose()
      this.getList()
    },
    getList() {
      this.loading = true
      historyApi({
        deptNo: this.$route.meta.deptNo,
        patientcode: this.patientcode,
        ...this.queryParams,
      })
        .then(({ data }) => {
          this.tableData = data.records
          this.total = data.total
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 关闭
    handleClose() {
      this.queryParams = {
        current: 1,
        size: 10,
        describe: '',
      }
    },
  },
}
</script>

<style scoped>
.pacs-history-main /deep/ .el-dialog {
  height: 690px;
}

.pacs-history-main /deep/ .el-dialog__body {
  display: flex;
  flex-direction: column;
}

.pacs-history-main .text-hide {
  overflow: hidden;
  /*超出部分隐藏*/
  text-overflow: ellipsis;
  /* 超出部分显示省略号 */
  white-space: nowrap;
  /*规定段落中的文本不进行换行 */
  width: 100%;
  /*需要配合宽度来使用*/
}
</style>
<style>
.history-popper-class {
  font-size: 20px !important;
}
</style>
