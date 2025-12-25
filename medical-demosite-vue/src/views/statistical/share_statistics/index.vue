<!-- 分享报告统计  开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column share-statistics">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item prop="startTime" label="分享日期">
        <el-date-picker v-model="queryParams.startTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="endTime" label="至">
        <el-date-picker v-model="queryParams.endTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择" clearable style="width: 160px" @change="handleQuery">
          <el-option label="生效" :value="0"></el-option>
          <el-option label="过期" :value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row style="flex: 1">
      <el-col :span="15" style="height: 100%; display: flex; flex-direction: column; padding-right: 8px">
        <!-- 表格 -->
        <div class="table-box">
          <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="序列" width="55" type="index" align="center" />
            <el-table-column label="分享链接" prop="path" align="center" width="200" show-overflow-tooltip />
            <el-table-column label="提取码" prop="extractedCode" align="center" />
            <el-table-column label="已选人数" prop="num" align="center" />
            <el-table-column label="有效期" prop="expirationDate" align="center">
              <template slot-scope="scope">
                <span v-if="scope.row.expirationDate == 999">永久</span>
                <span v-else>{{ scope.row.expirationDate }}天</span>
              </template>
            </el-table-column>
            <el-table-column label="填充提取码" prop="autofill" align="center" width="95">
              <template slot-scope="scope">
                <span v-if="scope.row.autofill == 1" style="color: green">是</span>
                <span v-else style="color: red">否</span>
              </template>
            </el-table-column>
            <el-table-column label="状态" prop="status" align="center">
              <template slot-scope="scope">
                <span v-if="scope.row.status == 1" style="color: red">已过期</span>
                <span v-else style="color: green">生效中</span>
              </template>
            </el-table-column>
            <el-table-column label="分享时间" prop="createdate" align="center" width="140" />
            <el-table-column label="分享人" prop="createName" align="center" />
            <el-table-column fixed="right" label="操作" align="center">
              <template slot-scope="scope">
                <span style="color: blue; cursor: pointer" @click="handleSetting(scope.row)">设置</span>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <!-- 分页 -->
        <pagination :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
      </el-col>
      <el-col :span="9" style="height: 100%; display: flex; flex-direction: column">
        <!-- 表格 -->
        <div class="table-box">
          <el-table border v-loading="loadingR" :data="tableListR" height="100%" stripe>
            <el-table-column type="selection" width="45" align="center" />
            <el-table-column label="序列" width="55" type="index" align="center" />
            <el-table-column label="体检号" prop="patientcode" align="center" width="110" />
            <el-table-column label="单位" prop="orgName" align="center" width="130" show-overflow-tooltip />
            <el-table-column label="姓名" prop="patientname" align="center" />
            <el-table-column label="性别" prop="idSex" align="center">
              <template slot-scope="scope">
                {{ ['男', '女'][scope.row.idSex] }}
              </template>
            </el-table-column>
            <el-table-column label="年龄" prop="age" align="center" />
            <el-table-column label="登记时间" prop="dateregister" align="center" width="140" />
          </el-table>
        </div>
        <pagination :total="totalR" :page.sync="queryParamsR.current" :limit.sync="queryParamsR.size" @pagination="handleDetails" />
      </el-col>
    </el-row>
    <!-- 设置分享报告 -->
    <share-report ref="shareReport"></share-report>
  </div>
</template>
<script>
import { getListData, getDetails } from '@/api/statistical/share_statistics.js'
import shareReport from '@/views/report/report_print/health_report/share_report'
export default {
  name: 'Share_statistics',
  components: { shareReport },
  data() {
    return {
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        startTime: undefined,
        endTime: undefined,
        state: undefined,
      },
      queryParamsR: {
        current: 1,
        size: 20,
      },
      // 遮罩层
      loading: false,
      loadingR: false,
      // 总条数
      total: 0,
      totalR: 0,
      // 表格数据
      tableList: [],
      tableListR: [],
      // 选中项的id
      selectId: undefined,
    }
  },
  created() {
    let nowTime = this.$getDate().split(' ')[0]
    this.queryParams.startTime = nowTime
    this.queryParams.endTime = nowTime
    this.getList()
  },
  methods: {
    // 表格选中
    handleSelectionChange(selection) {
      if (selection.length == 0) {
        this.tableListR = []
      } else if (selection.length == 1) {
        this.selectId = selection[0].id
        this.handleDetails()
      } else if (selection.length > 1) {
        this.$refs.tableList.clearSelection() //清空表格数据
        this.$refs.tableList.toggleRowSelection(selection.pop()) //最后一条数据
      }
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams)
        .then((response) => {
          this.tableList = response.data.records
          this.total = response.data.total
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 获取详情
    handleDetails() {
      this.loadingR = true
      getDetails({
        id: this.selectId,
        ...this.queryParamsR,
      })
        .then(({ data }) => {
          this.tableListR = data.records
          this.totalR = data.total
          this.loadingR = false
        })
        .catch((error) => {
          console.error(error)
          this.loadingR = false
        })
    },
    handleSetting(row) {
      this.$refs.shareReport.handleSetting(row)
    },
  },
}
</script>
<style scoped>
.share-statistics /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>
