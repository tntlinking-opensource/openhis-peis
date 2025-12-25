<!-- 授权记录  开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-refresh" size="mini" @click="resetQuery">刷新</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['system:branch:edit']">详情</el-button>
      </el-col>
    </el-row>
    <div class="table-box">
      <el-table ref="dataList" border v-loading="loading" :data="dataList" height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
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
            <!-- <el-button size="mini" type="text" icon="el-icon-delete" @click="handleRemove(scope.row)" v-hasPermi="['system:branch:remove']">删除</el-button> -->
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 授权详情 -->
    <accredit-details ref="accreditDetails"></accredit-details>
  </div>
</template>

<script>
import { getListApi } from '@/api/system/remote_braking/accredit_record'
import accreditDetails from './details'
export default {
  name: 'Accredit_record',
  components: {
    accreditDetails,
  },
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
      },
      // 总条数
      total: 0,
      // 维护表格数据
      dataList: [],
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 刷新
    resetQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    /** 查询分中心维护列表 */
    getList() {
      this.loading = true
      getListApi(this.queryParams)
        .then(({ data }) => {
          this.dataList = data.records
          this.total = data.total
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.dataList.clearSelection()
      this.$refs.dataList.toggleRowSelection(row)
    },
    /** 授权详情按钮操作 */
    handleUpdate(row) {
      const id = row.id || this.ids.join(',')
      this.$refs.accreditDetails.showDialog(id)
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
