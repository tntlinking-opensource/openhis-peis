<!-- 团检报告审批-综合样本分析-团体小结  开发人：麦沃德科技半夏 -->
<template>
  <div class="main-group">
    <div class="table-box">
      <!-- 表格 -->
      <el-table border v-loading="loading" :data="tableList" height="calc(100% - 70px)" stripe>
        <el-table-column label="行号" width="60" type="index" align="center" />
        <el-table-column label="分类" prop="depName" align="center" show-overflow-tooltip />
        <el-table-column label="结论" prop="conclusion" align="center" show-overflow-tooltip />
        <el-table-column label="检出人数" align="center">
          <el-table-column label="男性" prop="detectionMale" align="center" />
          <el-table-column label="女性" prop="detectionFemale" align="center" />
          <el-table-column label="合计" prop="detectionTotal" align="center" />
        </el-table-column>
        <el-table-column label="总体人数" align="center">
          <el-table-column label="男性" prop="allMale" align="center" />
          <el-table-column label="女性" prop="allFemale" align="center" />
          <el-table-column label="合计" prop="allTotal" align="center" />
        </el-table-column>
        <el-table-column label="总体百分比" align="center">
          <el-table-column label="男性" prop="checkMale" align="center" />
          <el-table-column label="女性" prop="checkFemale" align="center" />
          <el-table-column label="合计" prop="checkTotal" align="center" />
        </el-table-column>
        <el-table-column label="体检者" align="center">
          <el-table-column label="体检号" prop="patientCode" align="center" />
          <el-table-column label="姓名" prop="patientName" align="center" />
          <el-table-column label="性别" prop="sex" align="center" />
          <el-table-column label="年龄" prop="age" align="center" />
          <el-table-column label="部门" prop="orgDepart" align="center" show-overflow-tooltip />
        </el-table-column>
      </el-table>
      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" :page-sizes="[20, 50, 100, 200]" />
    </div>
  </div>
</template>
<script>
import { getGroupList } from '@/api/groupreport/audit_analyze'
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
      getGroupList(this.$route.query.id, this.$route.query.groupId)
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
.main-group {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background-color: #fff;
}
</style>