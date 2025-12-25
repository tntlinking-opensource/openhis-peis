<!-- 团检报告审批-综合样本分析-复查情况  开发人：麦沃德科技半夏 -->
<template>
  <div class="main-review">
    <!-- 表格 -->
    <el-table border v-loading="loading" :data="tableList" height="100%" stripe>
      <el-table-column label="行号" width="60" type="index" align="center" />
      <el-table-column label="体检号" prop="patientCode" align="center" show-overflow-tooltip />
      <el-table-column label="姓名" prop="patientname" align="center" show-overflow-tooltip />
      <el-table-column label="性别" prop="sex" min-width="140" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.sex == 0">男</span>
          <span v-if="scope.row.sex == 1">女</span>
        </template>
      </el-table-column>
      <el-table-column label="年龄" prop="age" align="center" />
      <el-table-column label="职业病危害因素" prop="jhys" align="center" show-overflow-tooltip />
      <el-table-column label="复查情况" prop="reviewStatus" align="center" show-overflow-tooltip />
      <el-table-column label="复查结果" prop="positives" align="center" show-overflow-tooltip />
      <el-table-column label="复查结论" prop="occupationSummary" align="center" />
      <el-table-column label="复查处理意见" prop="summaryText" align="center" show-overflow-tooltip />
    </el-table>
  </div>
</template>
<script>
import { getReviewList } from "@/api/groupreport/audit_analyze";
export default {
  components: {},
  props: [],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 排检表格数据
      tableList: [],
    };
  },
  computed: {},
  watch: {},
  created() {
    this.getList();
  },
  mounted() { },
  methods: {
    // 查询列表
    getList() {
      this.loading = true;
      getReviewList(this.$route.query.id).then(response => {
        this.tableList = response.data
        this.loading = false;
      });
    },
  },
};
</script>
<style lang="scss">
.main-review {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
</style>