<!-- 档案合并-查看 麦沃德科技 开发人:清风 -->
<template>
  <div class="add-container">
    <el-dialog title="查看" :visible.sync="open" width="860px" append-to-body :close-on-click-modal="false">
      <div class="table-box" style="height: 404px">
        <el-table :data="tableData" v-loading="loading" :border="true" :stripe="true" height="100%">
          <el-table-column type="selection" align="center"></el-table-column>
          <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
          <el-table-column prop="patientcode" label="体检号" align="center"></el-table-column>
          <el-table-column prop="patientname" label="姓名" align="center"></el-table-column>
          <el-table-column prop="phone" label="电话" align="center"></el-table-column>
          <el-table-column prop="idcardno" label="身份证号" align="center" show-overflow-tooltip></el-table-column>
          <el-table-column prop="address" label="地址" align="center"></el-table-column>
        </el-table>
      </div>
      <!-- 分页 -->
      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    </el-dialog>
  </div>
</template>
<script>
import { getEleInfoListData } from '@/api/custservice/membership_management/file_consolidation/file_consolidation'

export default {
  data() {
    return {
      loading: false,
      open: false,
      total: 0, //总数
      queryParams: {
        current: 1, //初始页数
        size: 10, //页值
      },
      tableData: [],
      // 体检者档案号
      patientarchiveno: undefined,
    }
  },
  methods: {
    queryWindow(patientarchiveno) {
      this.open = true
      this.patientarchiveno = patientarchiveno
      this.getList()
    },
    //分页点击事件
    getList() {
      this.loading = true
      getEleInfoListData({ patientarchiveno: this.patientarchiveno })
        .then((response) => {
          this.tableData = response.data.records
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
