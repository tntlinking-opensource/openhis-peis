<!-- 财务管理-体检卡办理 麦沃得科技 开发人:清风/矢北 -->
<template>
  <div class="app-container flex-direction-column experience-card">
    <el-form :model="form" :inline="true" size="mini" ref="queryForm">
      <el-form-item label="体检卡号" prop="startNo">
        <el-input style="width: 180px" placeholder="起始卡号" v-model="form.startNo"></el-input>
      </el-form-item>
      <el-form-item label="—" prop="endNo">
        <el-input style="width: 180px" placeholder="终止卡号" v-model="form.endNo"></el-input>
      </el-form-item>
      <el-form-item label="活动专属卡名" prop="tcName">
        <el-input style="width: 200px" placeholder="请输入套餐名称或拼音码" v-model="form.tcName"></el-input>
      </el-form-item>
      <el-form-item label="体检卡类型" prop="typeId">
        <el-select style="width: 140px" placeholder="请选择" v-model="form.typeId">
          <el-option v-for="(item, index) in typeOptions" :key="index" :label="item.text" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="是否已售" prop="sellStatus">
        <el-select style="width: 100px" v-model="form.sellStatus" placeholder="全部">
          <el-option label="是" :value="1"></el-option>
          <el-option label="否" :value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="售卡人" prop="sellId">
        <input-select :selectData="selectDataId" :selectChange="selectChangeId" :initialValue="form.sellId">
        </input-select>
      </el-form-item>
      <el-form-item label="售卡时间">
        <el-date-picker @change="cChange" style="width: 306px" value-format="yyyy-MM-dd" type="daterange"
          v-model="form.dateValue" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="使用时间">
        <el-date-picker @change="cChange" style="width: 306px" value-format="yyyy-MM-dd" type="daterange"
          v-model="form.useTime" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" :disabled="single" @click="sendcardsWindow()"
          v-hasPermi="['finance:management:experience_card:sendcards']">卡办理 </el-button>
      </el-col>
      <el-button type="warning" plain icon="el-icon-upload2" size="mini" @click="handerWindow()"
        v-hasPermi="['finance:management:experience_card:export']">导出 </el-button>
    </el-row>
    <dragRow left-size="60%" right-size="40%">
      <template #leftBox>
        <div class="item-box flex-direction-column">
          <div class="table-box">
            <el-table :data="tableData" ref="leftTable" style="display: inline-block" height="calc(100% - 60px)"
              v-loading="loading" border stripe @selection-change="handleSelectionChange" @row-click="rowClick">
              <el-table-column type="selection" align="center" fixed="left"></el-table-column>
              <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
              <el-table-column prop="seller" label="售卡人" align="center"></el-table-column>
              <el-table-column prop="sellTime" min-width="160px" label="售卡时间" show-overflow-tooltip
                align="center"></el-table-column>
              <el-table-column prop="cardNo" min-width="120px" label="卡号" align="center"></el-table-column>
              <el-table-column prop="tcprice" label="面值" align="center"></el-table-column>
              <el-table-column prop="typeName" label="卡类型" align="center" min-width="130"></el-table-column>
              <el-table-column prop="sellStatus" label="是否已售" align="center">
                <template slot-scope="scope">
                  <span v-if="scope.row.sellStatus == 1">是</span>
                  <span v-else>否</span>
                </template>
              </el-table-column>
              <el-table-column prop="tcChecked" label="是否使用" align="center">
                <template slot-scope="scope">
                  <span v-if="scope.row.tcChecked == 1">是</span>
                  <span v-else>否</span>
                </template>
              </el-table-column>
              <el-table-column prop="tcDateregister" label="使用时间" align="center" min-width="160px"></el-table-column>
              <el-table-column prop="tcPatientcode" min-width="120px" label="使用体检号" align="center"></el-table-column>
              <el-table-column prop="sellPrice" label="售价" align="center"></el-table-column>
              <el-table-column prop="purchaser" label="购卡人" align="center"></el-table-column>
              <el-table-column prop="phone" min-width="120px" label="购卡手机号" align="center"></el-table-column>
              <el-table-column prop="cardPrefix" label="卡前缀" align="center"></el-table-column>
              <el-table-column prop="cardLogo" label="卡标识" align="center"></el-table-column>
              <el-table-column prop="validity" min-width="120px" label="有效期" show-overflow-tooltip
                align="center"></el-table-column>
              <el-table-column prop="cardState" label="卡说明" show-overflow-tooltip align="center"
                min-width="160px"></el-table-column>
              <el-table-column prop="cardState" label="套餐" show-overflow-tooltip align="center"
                min-width="160px"></el-table-column>
              <el-table-column prop="memo" label="备注" align="center" width="100px"
                show-overflow-tooltip></el-table-column>
            </el-table>
            <!-- 分页 -->
            <pagination :total="total" :page.sync="form.current" :limit.sync="form.size" @pagination="getList" />
          </div>
        </div>
      </template>
      <template #rightBox>
        <div class="item-box flex-direction-column">
          <div class="table-box">
            <el-table :data="tableDatas" v-loading="loading1" style="display: inline-block" :border="true" :stripe="true"
              height="100%">
              <el-table-column type="selection" align="center"></el-table-column>
              <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
              <el-table-column prop="moneyamountpaid" label="收款金额" min-width="140px" align="center"></el-table-column>
              <el-table-column prop="paywayName" label="付款方式" min-width="140px" align="center"></el-table-column>
              <el-table-column prop="cardno" label="卡号" min-width="140px" align="center"></el-table-column>
              <el-table-column prop="feechargerName" label="收费员" min-width="140px" align="center"></el-table-column>
              <el-table-column prop="moneyamountpaiddate" label="收费时间" min-width="160px" align="center"></el-table-column>
              <el-table-column prop="note" label="备注" min-width="140px" align="center"></el-table-column>
            </el-table>
          </div>
        </div>
      </template>
    </dragRow>

    <sendcardsItems ref="sendcardsItems"></sendcardsItems>
  </div>
</template>

<script>
import dragRow from '@/components/DragRow'
import sendcardsItems from './sendcards.vue'
import { cardTypeData, getListData, getRightData } from '@/api/finance/card/card_issuance.js'

export default {
  components: { sendcardsItems, dragRow },
  data() {
    return {
      //左侧加载中
      loading: false,
      // 右侧加载中
      loading1: false,
      form: {
        startNo: '',
        endNo: '',
        tcName: '',
        cardNo: '',
        typeId: '',
        sellId: undefined,
        dateValue: [], //startTime和endTime
        startTime: undefined,
        endTime: undefined,
        useTime: [],
        startUseTime: undefined,
        endUseTime: undefined,
        current: 1, //初始页数
        size: 20, //页值
      },

      showSearch: true,
      ids: [], //选中的数组
      single: true, //必选且单选
      multiple: true, //必选
      total: 0, //总数

      //领取人套餐数据
      selectDataId: {
        placeholder: '请输入输入码选择',
        key: '部门', //第一列标题
        value: '领取人', //第二列标题
        url: '/finance/sendCard/getLqrData', //请求连接
        bindValue: '',
        queryData: 'key',
        firstName: 'inputCode', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'occupationSummary', //接口返回值对应第二列的参数名(不传默认为'name')
      },
      typeOptions: [],
      tableData: [],
      tableDatas: [],
    }
  },
  created() {
    cardTypeData({ type: 0 }).then((response) => {
      this.typeOptions = response.data
    })
    this.getList()
  },
  methods: {
    //强制刷新
    cChange() {
      this.$forceUpdate()
    },
    selectChangeId(value) {
      this.form.sellId = value.id
    },
    // 获取左侧数据
    getList() {
      this.loading = true
      if (this.form.dateValue && this.form.dateValue.length) {
        this.form.startTime = this.form.dateValue[0] + ' 00:00:00'
        this.form.endTime = this.form.dateValue[1] + ' 23:59:59'
      } else {
        this.form.startTime = undefined
        this.form.endTime = undefined
      }
      if (this.form.useTime && this.form.useTime.length) {
        this.form.startUseTime = this.form.useTime[0] + ' 00:00:00'
        this.form.endUseTime = this.form.useTime[1] + ' 23:59:59'
      } else {
        this.form.startUseTime = undefined
        this.form.endUseTime = undefined
      }
      getListData(this.form)
        .then((response) => {
          this.tableData = response.data.records
          this.total = response.data.total
          this.loading = false
        })
        .catch((error) => {
          this.loading = false
          console.error(error)
        })
    },
    sendcardsWindow() {
      this.$refs.sendcardsItems.sendcardsWindow(this.ids)
    },

    handleSelectionChange(selection) {
      if (selection.length == 1) {
        this.selection = selection[0]
        this.ids.push(selection[0].id)
        this.loading1 = true
        this.getRightList()
      } else if (selection.length > 1) {
        this.$refs.leftTable.clearSelection() //清空表格数据
        this.$refs.leftTable.toggleRowSelection(selection.pop()) //最后一条数据
      } else if (selection.length == 0) {
        this.tableDatas = []
        this.selection = []
        this.ids = []
      }
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.leftTable.clearSelection()
      this.$refs.leftTable.toggleRowSelection(row)
    },
    // 获取右侧表格数据
    getRightList() {
      const query = {
        id: this.ids[0],
      }
      this.loading1 = true
      getRightData(query)
        .then((response) => {
          this.tableDatas = response.data
          this.loading1 = false
        })
        .catch((error) => {
          this.loading1 = false
          console.error(error)
        })
    },
    //重置
    resetQuery() {
      this.resetForm('queryForm')
      this.form.startNo = ''
      this.form.endNo = ''
      this.selectDataId.bindValue = undefined
      this.form.dateValue = undefined
      this.form.useTime = undefined
      this.form.startTime = undefined
      this.form.endTime = undefined
      this.form.startUseTime = undefined
      this.form.endUseTime = undefined
      this.tableDatas = []
      this.handleQuery()
    },
    ///导出
    handerWindow() {
      this.download(
        '/finance/cardHandle/export',
        {
          ...this.queryParams,
        },
        `体检卡办理.xlsx`
      )
    },
    //搜索
    handleQuery() {
      this.form.current = 1
      this.getList()
    },
  },
}
</script>

<style scoped>.experience-card /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}

.experience-card /deep/ .el-table__fixed-header-wrapper .el-checkbox {
  display: none;
}</style>
