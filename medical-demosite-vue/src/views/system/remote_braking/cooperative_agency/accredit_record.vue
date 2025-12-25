<template>
  <el-dialog title="查看授权记录" :visible.sync="open" width="1400px" append-to-body :close-on-click-modal="false" :destroy-on-close="true" class="section-queue-dia">
    <!-- 第一行筛选 -->
    <div class="first-line">
      <div>
        <span class="top-style">机构名称</span>
        <el-input style="margin-right: 12px" class="input-style" v-model="agencyInfo.typeName" readonly></el-input>
        <el-button class="zk-btn-style2" icon="el-icon-refresh" size="mini" @click="resetQuery">刷新</el-button>
        <el-button class="zk-btn-style2" icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['system:branch:add']">新增</el-button>
      </div>
    </div>
    <!-- 表格 -->
    <el-table ref="dataList" border v-loading="loading" :data="dataList" height="500px" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序列" width="55" type="index" align="center" />
      <el-table-column label="记录ID" align="center" prop="id" width="80" show-overflow-tooltip />
      <el-table-column label="业务标识" align="center" prop="bsFlag" width="160" show-overflow-tooltip />
      <el-table-column label="授权码类型" align="center" prop="codeType" width="160" show-overflow-tooltip>
        <template slot-scope="scope">
          {{ ['系统提供', '外部合作商提供'][scope.row.codeType] }}
        </template>
      </el-table-column>
      <el-table-column label="有效期" align="center" prop="expiryDate" width="160" show-overflow-tooltip />
      <el-table-column label="记录说明" align="center" prop="remark" min-width="200" show-overflow-tooltip />
      <el-table-column label="状态" align="center" width="90" prop="status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == 1">正常</el-tag>
          <el-tag type="danger" v-else>失效</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createdate" width="160" show-overflow-tooltip />
      <el-table-column label="操作" align="center" width="80" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:branch:edit']">详情</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <pagination :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="handleQuery" />
    <!-- 授权详情 -->
    <accredit-details ref="accreditDetails"></accredit-details>
    <!-- 添加授权信息 -->
    <add-accredit ref="addAccredit" :agencyInfo="agencyInfo" @handleQuery="handleQuery"></add-accredit>
  </el-dialog>
</template>

<script>
import { getListApi } from '@/api/system/remote_braking/accredit_record'
import accreditDetails from '../accredit_record/details'
import addAccredit from './add_accredit'
export default {
  components: {
    accreditDetails,
    addAccredit,
  },
  data() {
    return {
      // 打开主弹窗
      open: false,
      // 合作机构信息
      agencyInfo: {},
      // 筛选参数
      queryParams: {
        current: 1,
        size: 20,
        bsFlag: undefined,
      },
      // 表格加载中
      loading: false,
      // 总数
      total: 0,
      // 表格数据
      dataList: [],
      // 表格选中的数据
      selectData: [],
    }
  },
  methods: {
    showDialog(data) {
      this.open = true
      this.agencyInfo = data
      this.queryParams.current = 1
      this.queryParams.bsFlag = data.sourceId
      this.handleQuery()
    },
    // 查询
    handleQuery() {
      this.loading = true
      getListApi(this.queryParams).then(({ data }) => {
        this.dataList = data.records
        this.total = data.total
        this.loading = false
      })
    },
    // 重置
    resetQuery() {
      this.handleQuery()
    },
    // 添加授权信息
    handleAdd() {
      this.$refs.addAccredit.showDialog()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.selectData = selection
    },
    /** 授权详情按钮操作 */
    handleUpdate(row) {
      this.$refs.accreditDetails.showDialog(row)
    },
  },
}
</script>

<style lang="scss" scoped>
.section-queue-dia {
  .flex {
    display: flex;
    align-items: center;
    justify-content: space-between;

    span {
      font-size: 14px;
      line-height: 20px;
      letter-spacing: 0.04em;
      color: #000000;
      margin-right: 5px;
    }
  }
  .first-line {
    display: flex;
    flex-wrap: wrap;
    margin-bottom: 8px;
    .select-style {
      width: 160px;
      height: 30px;
      margin-right: 10px;
      font-size: 16px;
    }
    .input-style {
      width: 260px;
      font-size: 16px;
    }
    .top-style {
      // width: 70px;
      margin: 0 5px;
      color: #000;
      font-size: 16px;
      font-weight: 600;
    }
  }
}
</style>

<style scoped>
.section-queue-dia /deep/ .el-input__inner,
.section-queue-dia /deep/ .el-textarea__inner {
  font-size: 16px;
}
.section-queue-dia /deep/ table .cell {
  font-size: 16px !important;
}

.section-queue-dia /deep/ .el-form-item__content {
  font-size: 15px !important;
  color: #000 !important;
}
</style>
