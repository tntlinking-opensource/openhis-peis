<!-- 团检报告审批-综合样本分析-完成情况  开发人：麦沃德科技半夏 -->
<template>
  <div class="main-finish">
    <div class="table-box">
      <!-- 表格 -->
      <el-table border v-loading="loading" :data="tableList" height="calc(100% - 70px)" stripe>
        <el-table-column label="行号" width="60" type="index" align="center" />
        <el-table-column label="体检号" prop="patientcode" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" min-width="100" align="center" show-overflow-tooltip />
        <el-table-column label="性别" prop="sex" min-width="100" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.sex == 0">男</span>
            <span v-if="scope.row.sex == 1">女</span>
          </template>
        </el-table-column>
        <el-table-column label="婚姻" prop="idMarriage" min-width="100" align="center">
          <template slot-scope="scope">
            {{ ['', '未婚', '已婚', '离异', '丧偶', '其他'][scope.row.idMarriage] }}
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="age" min-width="100" align="center" />
        <el-table-column label="单位" prop="orgName" min-width="160" align="center" show-overflow-tooltip />
        <el-table-column label="团体部门" prop="orgDepart" min-width="140" align="center" show-overflow-tooltip />
        <!-- 职业 -->
        <template v-if="$route.query.type == 1">
          <el-table-column label="工种" prop="trades" min-width="120" align="center" show-overflow-tooltip />
          <el-table-column label="接害工龄" prop="jhgl" min-width="120" align="center" />
          <el-table-column label="职业病危害因素" prop="jhys" min-width="140" align="center" show-overflow-tooltip />
          <el-table-column label="未检项目" prop="uncheckedItems" min-width="200" align="center" show-overflow-tooltip />
        </template>
        <el-table-column label="体检时间" prop="medicaldate" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="体检状态" prop="fexamstarted" min-width="100" align="center">
          <template slot-scope="scope">
            <span v-if="$route.query.type == 1">{{ examType[scope.row.zytjzt] ? examType[scope.row.zytjzt].text : '' }}</span>
            <span v-else>{{ examType[scope.row.jktjzt] ? examType[scope.row.jktjzt].text : '' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="分检完成" prop="freadytofinal" min-width="100" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.freadytofinal == 1">是</span>
            <span v-else>否</span>
          </template>
        </el-table-column>
        <el-table-column label="总检完成" min-width="100" align="center">
          <template slot-scope="scope">
            <span v-if="$route.query.type == 1">{{ scope.row.zytjzt > 0 ? '完成' : '未完成' }}</span>
            <span v-else>{{ scope.row.jktjzt > 0 ? '完成' : '未完成' }}</span>
          </template>
        </el-table-column>
        <!-- 健康 -->
        <template v-if="$route.query.type == 0">
          <el-table-column label="报告已打" prop="isPrint" min-width="100" align="center">
            <template slot-scope="scope">
              {{ ['未打印', '已打印'][scope.row.isPrint] }}
            </template>
          </el-table-column>
          <el-table-column label="未检项目" prop="uncheckedItems" min-width="200" align="center" show-overflow-tooltip />
        </template>
      </el-table>
      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" :page-sizes="[20, 50, 100, 200]" />
    </div>
  </div>
</template>
<script>
import { getFinishList } from '@/api/groupreport/audit_analyze'
import { reportType } from '@/utils/dataList.js'
export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      // 排检表格数据
      tableList: [],
      // 体检状态
      examType: reportType(),
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
      getFinishList(this.$route.query.id, this.queryParams)
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
.main-finish {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background-color: #fff;
}
</style>
