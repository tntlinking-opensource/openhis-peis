<!-- 体检结账单-下右表格  开发人：麦沃德科技清风 -->
<template>
  <div class="table-container flex-direction-column">
    <!-- 表格 -->
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" align="center" fixed="left" show-overflow-tooltip></el-table-column>
        <el-table-column label="应收应退" min-width="100px" prop="moneyamount" align="center" show-overflow-tooltip />
        <el-table-column label="实收实退" min-width="100px" prop="moneyamountpaid" align="center" show-overflow-tooltip />
        <el-table-column label="付款方式" min-width="100px" prop="payWayName" align="center" show-overflow-tooltip />
        <el-table-column label="统收" min-width="100px" prop="isTotalcharge" align="center" show-overflow-tooltip />
        <el-table-column label="已收已退" min-width="100px" prop="FFeecharged" align="center" show-overflow-tooltip />
        <el-table-column label="收费员" min-width="100px" prop="idFeecharger" align="center" show-overflow-tooltip />
        <el-table-column label="收退费时间" min-width="120px" prop="feechargetime" align="center" show-overflow-tooltip />
        <el-table-column label="卡号" min-width="120px" prop="kashou" align="center" show-overflow-tooltip />
        <el-table-column label="备注" min-width="120px" prop="note" align="center" show-overflow-tooltip />
      </el-table>
    </div>
  </div>
</template>
<script>
import { getChargeData } from '@/api/finance/inspect_accounts'
export default {
  components: {},
  props: [],
  data() {
    return {
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 排检表格数据
      tableList: [
        // {
        // 	moneyamount: "",
        // 	moneyamountpaid: "",
        // 	idPayway: "",
        // 	isTotalcharge: "",
        // 	FFeecharged: "",
        // 	idFeecharger: "",
        // 	feechargetime: "",
        // 	kashou: "",
        // 	note: "",
        // }
      ],
      idPaywayOptions: [],
    }
  },
  created() {
    this.bus.$off('rightButtonHandler')
    this.bus.$on('rightButtonHandler', (patientcode) => {
      this.getList(patientcode)
    })
  },
  mounted() { },
  methods: {
    // 查询列表
    getList(patientcode) {
      this.loading = true
      let obj = { patientcode }
      getChargeData(obj)
        .then((res) => {
          this.tableList = res.data
        })
        .finally(() => {
          this.loading = false
        })
      this.total = 100
      // });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
  },
}
</script>
