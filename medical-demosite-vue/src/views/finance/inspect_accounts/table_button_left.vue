<!-- 体检结账单-下左表格  开发人：麦沃德科技清风 -->
<template>
  <div class="table-container flex-direction-column">
    <!-- 表格 -->
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe>
        <el-table-column label="统计值" min-width="100px" prop="tjz" align="center" show-overflow-tooltip />
        <el-table-column label="体检者" min-width="200px" align="center" show-overflow-tooltip>
          <el-table-column label="人数" min-width="100px" align="center" prop="totalCount" show-overflow-tooltip></el-table-column>
          <el-table-column label="年龄" min-width="100px" align="center" prop="avgAge" show-overflow-tooltip>
            <template slot-scope="scope">
              {{ scope.row.avgAge ? Number(scope.row.avgAge).toFixed(2) : '' }}
            </template>
          </el-table-column>
        </el-table-column>
        <el-table-column label="完成人数" min-width="240px" align="center" show-overflow-tooltip>
          <el-table-column label="未检" min-width="80px" align="center" prop="noExamCount" show-overflow-tooltip></el-table-column>
          <el-table-column label="部分" min-width="80px" align="center" prop="someExamCount" show-overflow-tooltip></el-table-column>
          <el-table-column label="全部" min-width="80px" align="center" prop="allExamCount" show-overflow-tooltip></el-table-column>
        </el-table-column>
        <el-table-column label="原价" min-width="100px" prop="price" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ scope.row.price ? Number(scope.row.price).toFixed(2) : '' }}
          </template>
        </el-table-column>
        <el-table-column label="套餐原价" min-width="100px" prop="tcyj" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ scope.row.tcyj ? Number(scope.row.tcyj).toFixed(2) : '' }}
          </template>
        </el-table-column>
        <el-table-column label="套餐优惠价" min-width="100px" prop="tcyhj" align="center" show-overflow-tooltip />
        <el-table-column label="实收" min-width="400px" align="center" show-overflow-tooltip>
          <el-table-column label="统收" min-width="80px" align="center" prop="ssts" show-overflow-tooltip>
            <template slot-scope="scope">
              {{ scope.row.ssts ? Number(scope.row.ssts).toFixed(2) : '' }}
            </template>
          </el-table-column>
          <el-table-column label="康淘" min-width="80px" align="center" prop="sskt" show-overflow-tooltip></el-table-column>
          <el-table-column label="其他" min-width="80px" align="center" prop="ssqt" show-overflow-tooltip></el-table-column>
          <el-table-column label="合计" min-width="80px" align="center" prop="sshj" show-overflow-tooltip>
            <template slot-scope="scope">
              {{ scope.row.sshj ? Number(scope.row.sshj).toFixed(2) : '' }}
            </template></el-table-column
          >
          <el-table-column label="加项" min-width="80px" align="center" prop="ssAdd" show-overflow-tooltip></el-table-column>
        </el-table-column>
        <el-table-column label="记账" min-width="100px" prop="jz" align="center" show-overflow-tooltip />
      </el-table>
    </div>
  </div>
</template>
<script>
import { getAccountsTotalDto } from '@/api/finance/inspect_accounts.js'
export default {
  props: ['orderId'],
  data() {
    return {
      // 遮罩层
      loading: false,
      // 排检表格数据
      tableList: [],
    }
  },
  created() {
    this.bus.$off('leftButtonHandler')
    this.bus.$on('leftButtonHandler', (query, branchIds) => {
      console.log('query的值',query);
      
      this.loading = true
      if (query.startRegTime) {
        query.startRegTime = query.startRegTime ? query.startRegTime.slice(0, 10) + ' 00:00:00' : ''
      }
      if (query.endRegTime) {
        query.endRegTime = query.endRegTime ? query.endRegTime.slice(0, 10) + ' 23:59:59' : ''
      }
      if (!query.containUnchecked) {
        query.containUnchecked = 0
      }
      if (!query.containBj) {
        query.containBj = 0
      }
      if (!query.containBan) {
        query.containBan = 0
      }
      if (!query.containOldSystem) {
        query.containOldSystem = 0
      }
      
      const requestParams = {
        ...query, // Spread the existing query parameters
        branchIds, // Include branchIds
        idOrder: this.orderId // Include idOrder
      };
      console.log(requestParams);
      getAccountsTotalDto(requestParams)
        .then((res) => {
          this.tableList = res.data
          this.loading = false
        })
        .catch((err) => {
          console.error(err)
          this.loading = false
        })
    })
  },
}
</script>
