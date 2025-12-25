<!-- 团检报告审批-综合样本分析-阳性结果  开发人：麦沃德科技半夏 -->
<template>
  <div class="main-positive">
    <div class="table-box">
      <!-- 表格 -->
      <el-table border v-loading="loading" :data="tableList" height="calc(100% - 70px)" stripe>
        <el-table-column label="行号" width="60" type="index" align="center" />
        <el-table-column label="体检号" prop="patientcode" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" align="center" show-overflow-tooltip />
        <el-table-column label="年龄" prop="age" align="center" />
        <el-table-column label="性别" prop="sex" align="center"> </el-table-column>
        <el-table-column label="团体名称" prop="orgName" align="center" show-overflow-tooltip />
        <el-table-column label="团体部门" prop="orgDepart" align="center" show-overflow-tooltip />
        <el-table-column label="登记时间" prop="dateregister" align="center" show-overflow-tooltip />
        <el-table-column label="阳性结果" prop="positiveResult" align="center" show-overflow-tooltip />
        <el-table-column label="结论词" prop="verdict" align="center" show-overflow-tooltip />
        <el-table-column label="总检建议" prop="offer" align="center" show-overflow-tooltip />
      </el-table>
      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" :page-sizes="[20, 50, 100, 200]" />
    </div>
  </div>
</template>
<script>
import { getPositiveList } from '@/api/groupreport/audit_analyze'
export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      // 排检表格数据
      tableList: [],
      // 分页
      queryParams: {
        current: 1,
        size: 50,
      },
      total: 0,
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      getPositiveList(this.$route.query.id, this.$route.query.groupId, this.queryParams)
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
  },
}
</script>
<style lang="scss">
.main-positive {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background-color: #fff;
}
</style>
