<!-- 个检收费统计  开发人：麦沃德科技暴雨/清风 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form size="mini" ref="queryForm" :model="queryParams" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item label="分中心" prop="branchIds">
        <el-tooltip popper-class="tooltip-item" effect="dark" :content="branchNames" :disabled="!branchNames" placement="left">
          <el-select class="multiple-select" v-model="queryParams.branchIds" placeholder="请选择分中心" multiple clearable style="width: 240px" @change="changeBranchIds">
            <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId"></el-option>
          </el-select>
        </el-tooltip>
      </el-form-item>
      <el-form-item label="部门" prop="ksid">
        <input-select :selectData="departData" selectWidth="160" @change="departChange" style="width: 160px"></input-select>
      </el-form-item>
      <el-form-item label="开单医师" prop="xsjl" style="margin-right: 16px">
        <el-input style="width: 160px" placeholder="请输入开单医师" clearable v-model="queryParams.xsjl"></el-input>
      </el-form-item>
      <el-form-item prop="startTime" label="登记日期">
        <el-date-picker v-model="queryParams.startTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="endTime" label="至">
        <el-date-picker v-model="queryParams.endTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="isContainNuclein">
        <el-checkbox v-model="queryParams.isContainNuclein" :true-label="1" :false-label="0">含核酸</el-checkbox>
      </el-form-item>
      <el-form-item prop="isContainVaccine">
        <el-checkbox v-model="queryParams.isContainVaccine" :true-label="1" :false-label="0">含疫苗</el-checkbox>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="handleexport" v-hasPermi="['statistical:inspectCost:personalTotal:export']">导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" :disabled="single" @click="handleExportbyday" v-hasPermi="['statistical:inspectCost:personalTotal:exportbyday']">按天导出</el-button>
      </el-col>
    </el-row>

    <dragRow left-size="50%" right-size="50%">
      <template #leftBox>
        <div class="item-box flex-direction-column">
          <div class="table-box">
            <!-- 表1 -->
            <el-table :data="tableData" ref="tableData" v-loading="loading" @row-click="handleRowClick" :border="true" :stripe="true" @selection-change="handleSelectionChange" height="100%">
              <el-table-column type="selection" align="center"></el-table-column>
              <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
              <el-table-column prop="fzx" min-width="120" show-overflow-tooltip label="分中心" align="center"></el-table-column>
              <el-table-column prop="depart" min-width="140" show-overflow-tooltip label="部门" align="center"></el-table-column>
              <el-table-column prop="xsjl" min-width="120" label="开单医师" align="center"></el-table-column>
              <el-table-column prop="yjhj" min-width="120" label="原价合计" align="center"></el-table-column>
              <el-table-column prop="sshj" min-width="120" label="实收合计" align="center"></el-table-column>
              <el-table-column prop="addorgprice" min-width="120" label="加项原价合计" align="center"></el-table-column>
              <el-table-column prop="addprice" min-width="120" label="科室加项费用" align="center"></el-table-column>
              <el-table-column prop="count" min-width="120" label="人数" align="center"></el-table-column>
              <el-table-column prop="zk" min-width="120" label="折扣" align="center"></el-table-column>
              <el-table-column prop="kdj" min-width="120" label="客单价" align="center"></el-table-column>
            </el-table>
          </div>

          <!-- 分页 -->
          <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
        </div>
      </template>

      <template #rightBox>
        <!-- 表2 -->
        <div class="item-box">
          <div class="flex-direction-column">
            <div class="table-box">
              <el-table :data="tableDatas" v-loading="loadingr" style="max-width: 100%" :border="true" :stripe="true" height="100%">
                <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
                <el-table-column prop="tjh" label="体检号" align="center"></el-table-column>
                <el-table-column prop="gjrymc" label="个检人员名称" align="center"></el-table-column>
                <el-table-column prop="tjtcmc" label="套餐名称" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column prop="yj" label="原价" align="center"></el-table-column>
                <el-table-column prop="ss" label="实收" align="center"></el-table-column>
                <el-table-column prop="zk" label="折扣" align="center"></el-table-column>
                <el-table-column prop="regDate" label="登记日期" align="center" width="140"></el-table-column>
              </el-table>
            </div>
            <!-- 分页 -->
            <pagination v-show="rightTotal > 0" :total="rightTotal" :page.sync="queryRightParams.current" :limit.sync="queryRightParams.size" @pagination="getRightBasic" />
          </div>
        </div>
      </template>
    </dragRow>
  </div>
</template>

<script>
import dragRow from '@/components/DragRow'
import { getBranchData } from '@/api/statistical/professionchecks/conclusion_table.js' //获取分中心
import { page, getListData, exportByDay, exportTable } from '@/api/statistical/inspect_cost/sell_personal.js'
import { getCookie } from '@/utils/getCookie.js'

export default {
  name: 'Personal_total',
  components: { dragRow },
  data() {
    return {
      total: 0,
      rightTotal: 0,
      queryParams: {
        current: 1,
        size: 20,
        center: undefined,
        order: undefined,
        idOrg: undefined,
        intId: undefined,
        isContainNuclein: '0',
        isContainVaccine: '0',
        branchIds: [],
        xsjl: '',
        idcardno: '',
        phone: '',
        startTime: '',
        endTime: '',
      },
      //分中心名称
      branchNames: '',
      departData: {
        placeholder: '科室',
        key: '输入码', //第一列标题
        value: '科室', //第二列标题
        url: '/reception/register/getAllks', //请求连接
        bindValue: '',
      },
      queryRightParams: {
        current: 1,
        size: 20,
        center: undefined,
        order: undefined,
        idOrg: undefined,
        intId: undefined,
      },
      ids: [],
      single: '',
      multiple: '',
      loading: false,
      loadingr: false,
      search: {
        patientname: '',
        idcardno: '',
        phone: '',
      },
      tableData: [],
      tableDatas: [],
      fzxOptions: [],
      //
      getRightSearch: {},
    }
  },
  mounted() {
    this.queryParams.startTime = this.$getDate().split(' ')[0]
    this.queryParams.endTime = this.$getDate().split(' ')[0]
    this.getList()
    this.getBasic()
  },
  created() {
    this.$set(this.queryParams, 'branchIds', getCookie('cid').split(','))
  },
  methods: {
    getBasic() {
      getBranchData().then((res) => {
        this.fzxOptions = res.data
      })
    },
    //分中心改变
    changeBranchIds(value) {
      this.branchNames = value.map((item) => {
        return this.fzxOptions.find((fzx) => fzx.branchId == item).fzx
      }).join('、')
    },
    //左表单击行
    handleRowClick(row) {
      if (!row.id) {
        this.$alert('	该记录不存在体检号！', '提示', {
          type: 'warning',
        })
        this.$refs.tableData.clearSelection()
        this.loadingr = false
        return
      }
      this.$refs.tableData.toggleRowSelection(row, true)
      this.loadingr = true
      let startTime, endTime
      if (this.queryParams.startTime) {
        startTime = this.queryParams.startTime.slice(0, 10) + ' 00:00:00'
      }
      if (this.queryParams.endTime) {
        endTime = this.queryParams.endTime.slice(0, 10) + ' 23:59:59'
      }
      this.queryRightParams = {
        current: 1,
        size: 20,
        branchIds: this.queryParams.branchIds,
        id: row.id,
        isContainNuclein: this.queryParams.isContainNuclein,
        isContainVaccine: this.queryParams.isContainVaccine,
        startTime,
        endTime,
        // type:"1",//默认写死
      }
      // this.queryRightParams.id = this.queryParams.id
      // this.queryRightParams.isContainNuclein = this.queryParams.isContainNuclein
      // this.queryRightParams.isContainVaccine = this.queryParams.isContainVaccine
      this.getRightBasic()
      // getListData(obj)
      //   .then((res) => {
      //     this.tableDatas = res.data.records
      //     this.rightTotal = res.data.total
      //     this.loadingr = false
      //   })
      //   .catch(() => {
      //     this.loadingr = false
      //   })
    },
    //左表选中
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
      if (selection.length > 1) {
        this.$refs.tableData.clearSelection()
        this.$refs.tableData.toggleRowSelection(selection.pop())
      }
    },
    //右表分页
    getRightBasic() {
      // if (this.queryParams.date) {
      //   this.queryRightParams.startTime = this.queryParams.date[0].substring(0, 10) + ' 00:00:00'
      //   this.queryRightParams.endTime = this.queryParams.date[1].substring(0, 10) + ' 23:59:59'
      // }
      this.loadingr = true
      getListData(this.queryRightParams)
        .then((res) => {
          this.tableDatas = res.data.records
          this.rightTotal = res.data.total
          this.loadingr = false
        })
        .catch(() => {
          this.loadingr = false
        })
    },
    //导出
    handleexport() {
      this.queryParams.isByDay = 0
      let startTime, endTime
      if (this.queryParams.startTime) {
        startTime = this.queryParams.startTime.slice(0, 10) + ' 00:00:00'
      }
      if (this.queryParams.endTime) {
        endTime = this.queryParams.endTime.slice(0, 10) + ' 23:59:59'
      }
      let obj = {
        id: this.ids[0],
        branchIds: this.queryParams.branchIds,
        isByDay: 0, //是否按天1-是,0-否;
        isContainNuclein: this.queryParams.isContainNuclein,
        isContainVaccine: this.queryParams.isContainVaccine,
        startTime,
        endTime,
        xsjl: this.queryParams.xsjl
      }
      this.download(
        '/finance/individualStatistics/export',
        {
          ...obj,
        },
        `个检收费统计表导出.xlsx`
      )
    },
    //按天导出
    handleExportbyday() {
      this.queryParams.isByDay = 1
      let startTime, endTime
      if (this.queryParams.startTime) {
        startTime = this.queryParams.startTime.slice(0, 10) + ' 00:00:00'
      }
      if (this.queryParams.endTime) {
        endTime = this.queryParams.endTime.slice(0, 10) + ' 23:59:59'
      }
      let obj = {
        id: this.ids[0],
        branchIds: this.queryParams.branchIds,
        isByDay: 1, //是否按天1-是,0-否;
        isContainNuclein: this.queryParams.isContainNuclein,
        isContainVaccine: this.queryParams.isContainVaccine,
        startTime,
        endTime,
        xsjl: this.queryParams.xsjl
      }
      this.download(
        '/finance/individualStatistics/export',
        {
          ...obj,
        },
        `个检收费统计表导出(按天).xlsx`
      )
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.$delete(this.departData, 'bindValue')
      this.$set(this.departData, 'bindValue', '')
      this.handleQuery()
    },
    departChange(value) {
      this.queryParams.ksid = value.id
    },
    getList() {
      this.loading = true
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.queryParams.cid = this.queryParams.branchIds
      page(this.queryParams)
        .then((res) => {
          this.tableData = res.data.records
          this.total = res.data.total
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
  },
}
</script>

<style lang="scss" scoped>
.app-container {
  .item-box {
    background: #fff;
    height: 100%;
  }

  .multiple-select ::v-deep .el-select__tags {
    flex-wrap: nowrap;
    overflow: hidden;
  }
}
</style>
