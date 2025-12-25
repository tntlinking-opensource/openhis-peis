<!-- 对比报告 麦沃德科技 开发人: 清风 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form size="mini" :inline="true" class="no-margin-bottom">
      <el-form-item label="姓名">
        <el-input style="width: 160px" placeholder="请输入姓名" v-model="queryParams.inputCode" @keyup.enter.native="search" />
      </el-form-item>
      <el-form-item label="手机号">
        <el-input style="width: 160px" placeholder="请输入手机号" v-model="queryParams.phone" @keyup.enter.native="search" />
      </el-form-item>
      <el-form-item label="身份证号">
        <el-input style="width: 160px" placeholder="请输入身份证号" v-model="queryParams.idcardno" @keyup.enter.native="search" />
      </el-form-item>
      <el-form-item label="选择日期">
        <el-date-picker v-model="queryParams.valueDate" placeholder="请输入名称" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" @change="search" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">搜索</el-button>
        <el-button @click="reset">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-plus" plain @click="excleWindow()" v-hasPermi="['inspection:comparisonReport:excle']">导出Excel</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-upload2" plain @click="handleImport" v-hasPermi="['inspection:comparisonReport:excle']">导入老系统</el-button>
      </el-col>
    </el-row>

    <div class="table-box">
      <el-table ref="tableData" :data="tableData" v-loading="loading" tooltip-effect="dark" :border="true" :stripe="true" @selection-change="handleSelectionChange" @row-click="rowClick" @row-dblclick="doubleClick" row-key="id" height="100%">
        <el-table-column type="selection" align="center"></el-table-column>
        <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
        <el-table-column prop="patientarchiveno" label="档案号" show-overflow-tooltip align="center"></el-table-column>
        <el-table-column prop="patientname" label="姓名" align="center"></el-table-column>
        <el-table-column prop="idSex" label="性别" align="center">
          <template slot-scope="scope">
            <div v-for="item in idSex" :key="item.id">
              <div v-if="item.id == scope.row.idSex">{{ item.text }}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="手机号" align="center"></el-table-column>
        <el-table-column prop="idcardno" label="身份证号" show-overflow-tooltip align="center"></el-table-column>
        <!-- <el-table-column prop="age" label="年龄" align="center"></el-table-column> -->
        <el-table-column prop="idNation" label="民族" align="center">
          <template slot-scope="scope">
            <div v-for="item in idNation" :key="item.id">
              <div v-if="item.id == scope.row.idNation">{{ item.name }}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="memberlevel" label="会员类型" align="center">
          <template slot-scope="scope">
            <div v-for="item in memberlevel" :key="item.levelId">
              <div v-if="item.levelId == scope.row.memberlevel">{{ item.levelName }}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="address" label="家庭住址" show-overflow-tooltip align="center"></el-table-column>
        <el-table-column prop="cultural" label="文化程度" align="center">
          <template slot-scope="scope">
            <div v-for="item in cultural" :key="item.value">
              <div v-if="item.value == scope.row.cultural">{{ item.label }}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="isHmdb" label="黑名单" align="center"></el-table-column>
        <el-table-column prop="hmdbz" label="黑名单备注" show-overflow-tooltip align="center"></el-table-column>
        <el-table-column prop="createdate" label="建档时间" width="156px" align="center"></el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getPage" />
    <dialogReportItem ref="dialogReportItem"></dialogReportItem>
  </div>
</template>

<script>
import dialogReportItem from './dialog_report.vue'
import { getCultural } from '@/utils/dataList.js'
import { getNationData, page, importComparativeReport } from '@/api/inspection/comparison_report.js'
export default {
  components: { dialogReportItem },
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
        current: 1,
        size: 20,
        patientname: '',
        phone: '',
        idcardno: '',
        valueDate: undefined,
        startTime: '',
        endTime: '',
      },
      tableData: [],
      memberlevel: [],
      idNation: [],
      idSex: [
        { id: '0', text: '男' },
        { id: '1', text: '女' },
        { id: '2', text: '综合' },
      ],
      cultural: [],
    }
  },
  async created() {
    this.memberlevel = (await this.$getLevelList()).data
    this.cultural = getCultural()
    //获取民族数据
    getNationData().then((res) => {
      this.idNation = res.data
    })
    this.getPage()
  },
  methods: {
    // 导出
    excleWindow() {
      let obj = { ids: this.ids }
      this.download('/finance/individualStatistics/export', obj, `个检收费统计表导出.xlsx`)
    },
    // 导入老系统体检号
    handleImport() {
      this.$prompt('请输入老系统体检号,多个用逗号隔开', '提示')
        .then(({ value }) => {
          value.replace(/，/gi, ',')
          this.loading = true
          importComparativeReport(value.split(','))
            .then((res) => {
              this.loading = false
              this.$modal.msgSuccess('导入成功')
              this.getPage()
            })
            .catch((err) => {
              console.error(err)
              this.loading = false
            })
        })
        .catch(() => {})
    },
    getPage() {
      this.loading = true
      if (this.queryParams.valueDate) {
        this.queryParams.startTime = this.queryParams.valueDate[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.valueDate[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      //分页查询
      page(this.queryParams).then((res) => {
        this.tableData = res.data.records
        this.total = res.data.total
        this.loading = false
      })
    },
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => {
        return item.id
      })
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableData.clearSelection()
      this.$refs.tableData.toggleRowSelection(row)
    },
    doubleClick(row) {
      this.$refs.dialogReportItem.dialogReportWindow(row)
    },
    search() {
      this.queryParams.current = 1
      this.getPage()
    },
    reset() {
      this.queryParams = {
        current: 1,
        size: 20,
        patientname: '',
        phone: '',
        idcardno: '',
        valueDate: undefined,
      }
      this.getPage()
    },
  },
}
</script>
