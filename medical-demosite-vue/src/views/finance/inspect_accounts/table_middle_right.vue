<!-- 体检结账单-中右表格  开发人：麦沃德科技清风 -->
<template>
  <div class="table-container flex-direction-column">
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8" style="padding: 8px 8px 0">
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-refresh-right" @click="handleRefresh">刷新</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['finance:inspectAccounts:middleRight:export']">导出 </el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" fixed="left" />
        <el-table-column label="序列" width="55" type="index" align="center" fixed="left" />
        <el-table-column label="收费项目" min-width="120px" prop="examfeeitemName" align="center" fixed="left" show-overflow-tooltip />
        <el-table-column label="分组条件" min-width="160px" align="center" show-overflow-tooltip>
          <el-table-column label="原价" min-width="80px" align="center" prop="price" show-overflow-tooltip></el-table-column>
          <el-table-column label="优惠价" min-width="80px" align="center" prop="factprice" show-overflow-tooltip></el-table-column>
        </el-table-column>
        <el-table-column label="付款方式" min-width="120px" prop="idPayway" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <div v-for="(item, index) in idPaywayOptions" :key="index">
              <span v-if="item.id == scope.row.idPayway">{{ item.paywayName }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="加项医师" min-width="100px" prop="jxys" align="center" show-overflow-tooltip />
        <el-table-column label="登记" min-width="60px" prop="FRegistered" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.FRegistered" :true-label="1" :false-label="0" class="triggerFailure"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="退项" min-width="60px" prop="changeItem" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.changeItem" :true-label="1" :false-label="0" class="triggerFailure"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="已退" min-width="60px" prop="FMarkFeereturn" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.FMarkFeereturn" :true-label="1" :false-label="0" class="triggerFailure"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="已收" min-width="60px" prop="FFeecharged" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.FFeecharged" :true-label="1" :false-label="0" class="triggerFailure"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="已发" min-width="60px" prop="FLabsendtolis" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.FLabsendtolis" :true-label="1" :false-label="0" class="triggerFailure"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="已检" min-width="60px" prop="FExaminated" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.FExaminated" :true-label="1" :false-label="0" class="triggerFailure"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="弃检" min-width="60px" prop="FGiveup" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.FGiveup" :true-label="1" :false-label="0" class="triggerFailure"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="迟检" min-width="60px" prop="FDelayed" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.FDelayed" :true-label="1" :false-label="0" class="triggerFailure"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="科室" min-width="120px" prop="idKs" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <div v-for="(item, index) in idKsOptions" :key="index">
              <span v-if="item.id == scope.row.idKs">{{ item.name }}</span>
            </div>
          </template> </el-table-column>>
        <el-table-column label="登记人" min-width="120px" prop="idDoctorreg" align="center" show-overflow-tooltip />
        <el-table-column label="收费时间" min-width="120px" prop="feechargetime" align="center" show-overflow-tooltip />
      </el-table>
    </div>
  </div>
</template>
<script>
import { getItemListData, getAllks, getPayWayData, exportItems } from '@/api/finance/inspect_accounts'

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
      tableList: [],
      idKsOptions: [],
      isTrue: false,
      idPaywayOptions: [],
      patientcode: '',
    }
  },
  created() {
    this.bus.$off('rightMiddleHandler')
    this.bus.$on('rightMiddleHandler', (patientcode) => {
      this.getList(patientcode)
    })
  },
  methods: {
    // 查询列表
    async getList(patientcode) {
      if (patientcode) {
        this.patientcode = patientcode
      }
      let isTrue = await this.setBasic()
      if (!isTrue) return
      this.loading = true
      let obj = { patientcode }
      getItemListData(obj)
        .then((res) => {
          this.tableList = res.data
        })
        .finally(() => {
          this.loading = false
        })
    },
    async setBasic() {
      //获取科室
      await getAllks().then((res) => {
        this.idKsOptions = res.data
      })
      //获取付款方式
      await getPayWayData().then((res) => {
        this.idPaywayOptions = res.data.records
      })
      return true
    },
    // 刷新
    handleRefresh() {
      this.queryParams.pageNum = 1
      this.loading = true
      let obj = { patientcode: this.patientcode }
      getItemListData(obj)
        .then((res) => {
          this.tableList = res.data
        })
        .finally(() => {
          this.loading = false
        })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    //导出
    handleExport() {
      let obj = { patientCode: this.patientcode }

      // exportItems(obj).then((res)=>{

      // })
      this.download(
        '/finance/physicalCheckout/exportItems',
        {
          ...obj,
        },
        `个检收费统计表导出.xlsx`
      )
    },
  },
}
</script>

<style scoped>
.triggerFailure {
  pointer-events: none;
}
</style>
