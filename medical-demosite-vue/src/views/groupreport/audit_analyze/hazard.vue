<!-- 团检报告审批-综合样本分析-危害因素人员分布  开发人：麦沃德科技半夏 -->
<template>
  <div class="main-hazard">
    <!-- 表格 -->
    <el-table border v-loading="loading" :data="tableList" height="100%" stripe>
      <el-table-column label="行号" width="60" type="index" align="center" />
      <el-table-column label="危害因素" prop="harmName" align="center" show-overflow-tooltip />
      <el-table-column label="工作状态" prop="onjobType" align="center">
        <template slot-scope="scope">
          <div v-for="item in jobType" :key="item.id">
            <span v-if="item.id == scope.row.onjobType">{{ item.text }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="应查" prop="peopleNum" align="center" />
      <el-table-column label="实查" prop="inspectNum" align="center" />
      <el-table-column label="未查" prop="unexploredNum" align="center" />
      <el-table-column label="男" prop="manNum" align="center" />
      <el-table-column label="女" prop="womenNum" align="center" />
    </el-table>
  </div>
</template>
<script>
import { getHazardList } from "@/api/groupreport/audit_analyze";
export default {
  components: {},
  props: [],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 排检表格数据
      tableList: [],
      jobType: [
        { 'id': '0', 'text': '上岗前' },
        { 'id': '1', 'text': '在岗期间' },
        { 'id': '2', 'text': '离岗时' },
        { 'id': '3', 'text': '离岗后' },
        { 'id': '4', 'text': '应急' }
      ]
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
      getHazardList(this.$route.query.id).then(response => {
        this.tableList = response.data
        this.loading = false;
      });
    },
  },
};
</script>
<style lang="scss">
.main-hazard {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
</style>