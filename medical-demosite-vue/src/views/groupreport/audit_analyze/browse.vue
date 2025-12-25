<!-- 团检报告审批-综合样本分析-疑似职业病人员一览表/职业禁忌证人员一览表/需复查人员一览表/其他疾病异常结果人员一览表/未见异常人员一览表  开发人：麦沃德科技半夏 -->
<template>
  <div class="main-suspect">
    <!-- 表格 -->
    <el-table border v-loading="loading" :data="tableList" height="100%" stripe>
      <el-table-column label="行号" width="60" type="index" align="center" />
      <el-table-column label="体检号" prop="patientcode" align="center" show-overflow-tooltip />
      <el-table-column label="姓名" prop="patientname" align="center" show-overflow-tooltip />
      <el-table-column label="性别" prop="sex" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.sex == 0">男</span>
          <span v-if="scope.row.sex == 1">女</span>
        </template>
      </el-table-column>
      <el-table-column label="年龄" prop="age" align="center" />
      <el-table-column label="工种" prop="trades" align="center" show-overflow-tooltip />
      <el-table-column label="接害工龄" prop="jhgl" align="center" show-overflow-tooltip />
      <el-table-column label="职业病危害因素" prop="jhys" align="center" show-overflow-tooltip />
      <el-table-column label="检查结果" prop="positives" align="center" show-overflow-tooltip v-if="flag != 5" />
      <el-table-column label="结论" prop="occupationSummary" align="center" show-overflow-tooltip />
      <el-table-column label="处理意见" prop="summaryText" align="center" show-overflow-tooltip />
    </el-table>
  </div>
</template>
<script>
import { getBrowseList } from "@/api/groupreport/audit_analyze";
export default {
  components: {},
  props: ["flag"],
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
      var flag = this.flag
      getBrowseList(this.$route.query.id, flag).then(response => {
        this.tableList = response.data
        this.loading = false;
      });
    },
  },
};
</script>
<style lang="scss">
.main-suspect {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
</style>