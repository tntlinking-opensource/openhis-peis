<!-- 平安对账单 开发人：麦沃德科技暴雨、予安 -->
<template>
  <div class="app-container flex-direction-column safety-bill">
    <!--上方表格-->
    <el-row :gutter="20" class="flex-direction-column" style="height: 50%">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
        <el-form-item label="分中心" prop="center" style="margin-bottom: 8px">
          <el-select v-model="queryParams.center" placeholder="请选择分中心" clearable style="width: 230px">
            <el-option v-for="options in fzxOptions" :key="options.id" :label="options.fzx" :value="options.branchId" />
          </el-select>
        </el-form-item>
        <el-form-item label="订单号" prop="order" style="margin-bottom: 8px">
          <el-input v-model="queryParams.order" placeholder="以英文逗号分割" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="体检团体" prop="idOrg" style="margin-bottom: 8px">
          <input-select :selectData="selectData" @change="selectChange" :initialValue="queryParams.idOrg"></input-select>
        </el-form-item>
        <el-form-item label="团体ID" prop="intId" style="margin-bottom: 8px">
          <el-input v-model="queryParams.intId" placeholder="请输入团体ID" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item style="margin-bottom: 8px">
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <!-- 表格 -->
      <div class="table-box">
        <el-table border v-loading="loading" ref="tableList" :data="tableList" size="mini" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
          <el-table-column type="selection" align="center" />
          <el-table-column label="序列" type="index" width="65" align="center" />
          <el-table-column label="订单号" prop="ddh" min-width="200px" align="center" show-overflow-tooltip />
          <el-table-column label="订单名称" prop="ddmc" min-width="400px" align="center" show-overflow-tooltip />
          <el-table-column label="团体ID" prop="intId" min-width="110px" align="center" show-overflow-tooltip />
          <el-table-column label="状态" align="center">
            <el-table-column label="任务结束" prop="ffinished" min-width="150px" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <el-checkbox :value="scope.row.ffinished == 1"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="结算完成" prop="ffinished" min-width="150px" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <el-checkbox :value="scope.row.ffinished == 1"></el-checkbox>
              </template>
            </el-table-column>
          </el-table-column>
          <el-table-column label="参检人数" align="center">
            <el-table-column label="预计" prop="placeCount" min-width="125px" align="center" show-overflow-tooltip />
            <el-table-column label="备单" prop="bdcount" min-width="125px" align="center" show-overflow-tooltip />
            <el-table-column label="未登" prop="noreg" min-width="125px" align="center" show-overflow-tooltip />
            <el-table-column label="登记" prop="reg" min-width="125px" align="center" show-overflow-tooltip />
          </el-table-column>
          <el-table-column label="任务预定" min-width="250px" prop="ydDate" align="center" show-overflow-tooltip />
        </el-table>
      </div>
      <!-- 分页 -->
      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    </el-row>

    <!--下方表格-->
    <el-row :gutter="20" style="height: 50%" class="flex-direction-column">
      <!--操作按钮-->
      <el-form :model="belowParams" ref="patientParams" size="small" :inline="true">
        <el-form-item style="margin-bottom: 0px">
          <el-button type="warning" plain size="mini" icon="el-icon-upload2" :disabled="!ids" @click="handleExport" v-hasPermi="['finance:safetyBill:export']">导出 </el-button>
        </el-form-item>
        <el-form-item label="含未检" prop="containUnchecked" style="margin-bottom: 8px" label-width="80px">
          <el-checkbox v-model="belowParams.containUnchecked" :disabled="!ids" @change="handleQueryP"></el-checkbox>
        </el-form-item>
        <el-form-item label="日期从" prop="startTime" style="margin-bottom: 8px" label-width="80px">
          <el-date-picker v-model="belowParams.startTime" type="date" @change="handleQueryP" :disabled="!ids" value-format="yyyy-MM-dd"></el-date-picker>
        </el-form-item>
        <el-form-item label="至" prop="endTime" style="margin-bottom: 8px">
          <el-date-picker v-model="belowParams.endTime" type="date" @change="handleQueryP" :disabled="!ids" value-format="yyyy-MM-dd"></el-date-picker>
        </el-form-item>
        <el-form-item style="margin-bottom: 0px">
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQueryP" :disabled="!ids">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQueryP" :disabled="!ids">重置</el-button>
        </el-form-item>
      </el-form>
      <!-- 表格 -->
      <div class="table-box" ref="tableBox">
        <el-table border v-loading="belowLoading" :data="tableListBelow" size="mini" height="100%" stripe>
          <!-- <el-table-column type="selection" align="center" /> -->
          <el-table-column label="序列" type="index" width="65" align="center" />
          <el-table-column label="体检号" prop="patientcode" align="center" min-width="140px" show-overflow-tooltip />
          <el-table-column label="平安订单ID" prop="patientnamereceipt" min-width="120px" align="center" show-overflow-tooltip />
          <el-table-column label="体检状态" prop="line" min-width="120px" align="center" show-overflow-tooltip />
          <el-table-column label="禁检" prop="fpaused" min-width="120px" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <el-checkbox :value="scope.row.fpaused == 1"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="分组" prop="groupName" min-width="120px" align="center" show-overflow-tooltip />
          <el-table-column label="姓名" prop="patientname" min-width="120px" align="center" show-overflow-tooltip />
          <el-table-column label="登记" prop="fregistered" min-width="100px" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <el-checkbox :value="scope.row.fregistered == 1"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="性别" prop="sex" min-width="60px" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <span>{{ ['男', '女'][scope.row.sex] }}</span>
            </template>
          </el-table-column>
          <el-table-column label="婚姻" prop="idMarriage" min-width="80px" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <span>{{ ['', '未婚', '已婚', '离异', '丧偶', '其他'][scope.row.idMarriage] }}</span>
            </template>
          </el-table-column>
          <el-table-column label="年龄" prop="age" min-width="80px" align="center" show-overflow-tooltip />
          <el-table-column label="是否替检" prop="countreportxml" min-width="100px" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <el-checkbox :value="scope.row.countreportxml == 1"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="原体检者" prop="tjr" min-width="100px" align="center" show-overflow-tooltip />
          <el-table-column label="套餐" prop="examName" min-width="160px" align="center" show-overflow-tooltip />
          <el-table-column label="原价" prop="price" min-width="100px" align="center" show-overflow-tooltip />
          <el-table-column label="套餐原价" prop="tcyj" min-width="100px" align="center" show-overflow-tooltip />
          <el-table-column label="套餐优惠价" prop="tcyhj" min-width="100px" align="center" show-overflow-tooltip />
          <el-table-column label="实收" align="center">
            <el-table-column label="统收" prop="ssts" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="其他" prop="ssqt" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="合计" prop="sshj" min-width="100px" align="center" show-overflow-tooltip />
          </el-table-column>
          <el-table-column label="记账统计" align="center">
            <el-table-column label="记账" prop="jz" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="记账人" prop="jzr" min-width="100px" align="center" show-overflow-tooltip />
          </el-table-column>
          <el-table-column label="部门" prop="orgDepart" min-width="120px" align="center" show-overflow-tooltip />
          <el-table-column label="体检时间" prop="medicaldate" min-width="155px" align="center" show-overflow-tooltip />
          <el-table-column label="登记员" prop="doctorreg" min-width="120px" align="center" show-overflow-tooltip />
          <el-table-column label="付款方式" prop="chargePayway" min-width="100px" align="center" show-overflow-tooltip />
          <el-table-column label="输入码" prop="inputCode" min-width="100px" align="center" show-overflow-tooltip />
          <el-table-column label="档案号" prop="chiveNo" min-width="120px" align="center" show-overflow-tooltip />
          <el-table-column label="身份证号" prop="idcardno" min-width="170px" align="center" show-overflow-tooltip />
          <el-table-column label="备注" prop="note" min-width="140px" align="center" show-overflow-tooltip />
          <el-table-column label="体检号生成人" prop="guidancenote2" min-width="140px" align="center" show-overflow-tooltip />
        </el-table>
      </div>
      <!-- 分页 -->
      <pagination :total="belowTotal" :page.sync="belowParams.current" :limit.sync="belowParams.size" @pagination="handlePatientList" />
    </el-row>
  </div>
</template>
<script>
import { getListData, getfzxApi, getPatientListData } from '@/api/finance/safety_bill.js'
export default {
  props: [],
  data() {
    return {
      // 分中心
      fzxOptions: [],
      // 体检团体参数
      selectData: {
        placeholder: '请输入输入码选择',
        value: '团体名称', //第二列标题
        url: '/sell/customer/getListDatas', //请求连接
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        center: undefined,
        order: undefined,
        idOrg: undefined,
        intId: undefined,
      },
      // 表格展示数据
      tableList: [],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: undefined,
      // 总条数
      total: 0,
      // 下方表格查询参数
      belowLoading: false,
      belowParams: {
        current: 1,
        size: 20,
        containUnchecked: false,
        startTime: undefined,
        endTime: undefined,
      },
      belowTotal: 0,
      // 下方表格
      tableListBelow: [],
    }
  },
  computed: {},
  watch: {},
  created() {
    this.queryParams.center = this.$getCookie('cid')
    getfzxApi().then(({ data }) => {
      this.fzxOptions = data
    })
    this.getList()
  },
  methods: {
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 体检团体返回值
    selectChange(value) {
      this.queryParams.idOrg = value.id
    },
    // 查询列表
    getList() {
      console.log(this.queryParams)
      this.loading = true
      getListData(this.queryParams)
        .then(({ data }) => {
          this.tableList = data.records
          this.total = data.total
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      if (selection.length == 0) {
        this.ids = undefined
        this.tableListBelow = []
      } else if (selection.length == 1) {
        this.ids = selection[0]
        this.handlePatientList()
      } else if (selection.length > 1) {
        this.$refs.tableList.clearSelection() //清空表格数据
        this.$refs.tableList.toggleRowSelection(selection.pop()) //最后一条数据
      }
    },
    // 获取体检者列表
    handlePatientList() {
      this.belowLoading = true
      let belowParams = JSON.parse(JSON.stringify(this.belowParams))
      belowParams.containUnchecked = belowParams.containUnchecked ? 1 : 0
      belowParams.startTime = belowParams.startTime ? belowParams.startTime + ' 00:00:00' : ''
      belowParams.endTime = belowParams.endTime ? belowParams.endTime + ' 23:59:59' : ''

      getPatientListData({
        ...belowParams,
        idOrder: this.ids.ddh,
      })
        .then(({ data }) => {
          this.tableListBelow = data.records
          this.belowTotal = data.total
          this.belowLoading = false
        })
        .catch(() => {
          this.belowLoading = false
        })
    },
    // 搜索
    handleQueryP() {
      this.belowParams.current = 1
      this.$nextTick(() => {
        this.handlePatientList()
      })
    },
    // 重置
    resetQueryP() {
      this.resetForm('patientParams')
      this.handleQueryP()
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection')
        this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },

    /** 导出按钮操作 */
    handleExport() {
      this.queryParams = Object.assign(this.queryParams, {
        order: this.ids.ddh,
        containUnchecked: 0
      })
      this.download(
        '/finance/pingAnStatement/exportOrderPatient',
        {
          ...this.queryParams,
        },
        `平安对账单.xlsx`
      )
    },
  },
}
</script>
<style scoped>
.safety-bill /deep/ .el-table .el-table__cell .cell {
  font-size: 14px;
}

.safety-bill /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>
