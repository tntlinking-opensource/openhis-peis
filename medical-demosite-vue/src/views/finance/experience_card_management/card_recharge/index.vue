<!-- 财务管理-卡充值 麦沃得科技 开发人:清风/矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" :inline="true" ref="queryForm" size="small" label-width="80px">
      <el-form-item label="体检卡号" prop="cardId">
        <el-input style="width:200px;" placeholder="请输入体检卡号" clearable v-model="queryParams.cardId" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="分中心" prop="branchIds">
        <el-select style="width:200px;" v-model="queryParams.branchIds" clearable>
          <el-option v-for="item, index in fzxOptions" :key="index" :label="item.fzx" :value="item.branchId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="领取时间">
        <el-date-picker style="width:300px;" value-format="yyyy-MM-dd" clearable type="daterange" v-model="queryParams.date" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="消费类型">
        <el-select style="width:200px;" v-model="queryParams.consumetype" clearable>
          <el-option v-for="item, index in customerType" :key="index" :label="item.text" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="金额类型">
        <el-select style="width:200px;" v-model="queryParams.isAdd" clearable>
          <el-option v-for="item, index in add" :key="index" :label="item.text" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button type="primary" plain icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="rechargeWindow()" v-hasPermi="['finance:management:card_recharge:recharge']">充值
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-connection" size="mini" @click="consumptionWindow()" v-hasPermi="['finance:management:card_recharge:consumption']">消费
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-upload2" size="mini" @click="exportWindow()" v-hasPermi="['finance:management:card_recharge:export']">导出
        </el-button>
      </el-col>
    </el-row>
    <dragRow left-size="60%" right-size="40%">
      <template #leftBox>
        <div class="item-box flex-direction-column">
          <div class="table-box">
            <el-table :data="tableData" ref="leftTable" id="setTable" style="display:inline-block;" @selection-change="handleSelectionChange" @row-click="rowClick" :border="true" v-loading="loading" :stripe="true" height="100%">
              <el-table-column type="selection" align="center"></el-table-column>
              <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
              <el-table-column prop="cardId" width="200px" label="体检卡号" align="center"></el-table-column>
              <el-table-column prop="cardType" width="160px" label="卡类型" align="center"></el-table-column>
              <el-table-column prop="isAdd" width="120px" label="金额类型" align="center">
                <template slot-scope="scope">
                  <span v-if="scope.row.isAdd == 0">充值 </span>
                  <span v-else-if="scope.row.isAdd == 1">消费 </span>
                  <span v-else></span>
                </template>
              </el-table-column>
              <el-table-column prop="consumeType" width="120px" label="消费类型" align="center">
                <template slot-scope="scope">
                  <span v-if="scope.row.consumetype == 0">体检 </span>
                  <span v-else-if="scope.row.consumetype == 1">药房 </span>
                  <span v-else-if="scope.row.consumetype == 2">口腔科</span>
                  <span v-else-if="scope.row.consumetype == 3">眼镜店</span>
                  <span v-else-if="scope.row.consumetype == 4">合作 </span>
                  <span v-else-if="scope.row.consumetype == 5">保健品</span>
                  <span v-else></span>
                </template>
              </el-table-column>
              <el-table-column prop="limit" width="120px" label="金额" align="center"></el-table-column>
              <el-table-column prop="handleMoney" width="120px" label="操作后金额" align="center"></el-table-column>
              <el-table-column prop="chargeId" width="140px" label="体检号" align="center"></el-table-column>
              <el-table-column prop="tjzxm" width="140px" label="体检者姓名" align="center"></el-table-column>
              <el-table-column prop="jsf" width="120px" label="结算费" align="center"></el-table-column>
              <el-table-column prop="memotext" width="140px" label="备注" align="center"></el-table-column>
              <el-table-column prop="operationId" width="140px" label="操作人" align="center"></el-table-column>
              <el-table-column prop="branchCenter" width="140px" label="分中心" align="center">
                <template slot-scope="scope">
                  <span>{{ getFzxName(scope.row.branchCenter) }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="kbz" width="108px" label="卡备注" show-overflow-tooltip align="center"></el-table-column>
              <el-table-column prop="yxq" width="120px" label="有效期" show-overflow-tooltip align="center"></el-table-column>
              <el-table-column prop="operationTime" width="120px" label="操作时间" show-overflow-tooltip align="center"></el-table-column>
              <el-table-column prop="kbs" width="108px" label="卡标识" align="center"></el-table-column>
              <el-table-column prop="ksm" width="220px" show-overflow-tooltip label="卡说明" align="center"></el-table-column>
            </el-table>
          </div>
          <!-- 分页 -->
          <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
        </div>
      </template>
      <template #rightBox>
        <div class="item-box flex-direction-column">
          <div class="table-box">
            <el-table :data="tableDatas" v-loading="loading1" style="display:inline-block; " :border="true" :stripe="true" height="100%">
              <el-table-column type="selection" align="center"></el-table-column>
              <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
              <el-table-column prop="moneyamountpaid" label="收款金额" min-width="140px" align="center"></el-table-column>
              <el-table-column prop="idPayway" label="付款方式" min-width="140px" align="center"></el-table-column>
              <el-table-column prop="idFeecharger" label="收费员" min-width="140px" align="center"></el-table-column>
              <el-table-column prop="moneyamountpaiddate" label="收费时间" min-width="140px" align="center"></el-table-column>
              <el-table-column prop="cardno" label="卡号" min-width="140px" align="center"></el-table-column>
              <el-table-column prop="kashou" label="卡号收款" min-width="140px" align="center"></el-table-column>
              <el-table-column prop="status" label="备注" min-width="140px" align="center"></el-table-column>
            </el-table>
          </div>
        </div>
      </template>
    </dragRow>
    <rechargeItems ref="rechargeItems" @getList="getList"></rechargeItems>
    <consumptionItems ref="consumptionItems" @getList="getList"></consumptionItems>
  </div>
</template>

<script>
import { getListData, getRightData, fzxData } from '@/api/finance/card/card_recharge.js'
import dragRow from '@/components/DragRow'
import rechargeItems from './recharge.vue'
import consumptionItems from './consumption.vue'
export default {
  components: { rechargeItems, consumptionItems, dragRow },
  data() {
    return {
      // 消费类型
      customerType: [{ id: 0, text: '体检' }, { id: 1, text: '药房' }, { id: 2, text: '口腔科' }, { id: 3, text: '眼镜店' }, { id: 4, text: '合作' }, { id: 5, text: '保健品' }],
      // 金额类型
      add: [{ text: '消费', id: 1 }, { text: '充值', id: 0 }],
      //遮罩层
      loading: false,
      loading1: false,
      selectId: [],//选中的数组
      total: 0,//总数
      queryParams: {
        cardId: undefined,
        date: undefined,//startTime 和 endTime
        consumetype: undefined,
        isAdd: undefined,
      },
      fzxOptions: [],
      tableData: [],
      tableDatas: []
    }
  },
  created() {
    //获取分中心数据
    fzxData().then(response => {
      this.fzxOptions = response.data
    })
    this.getList();
  },
  methods: {
    // 分页查询
    handleQuery() {
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + " 00:00:00"
        this.queryParams.endTime = this.queryParams.date[1] + " 23:59:59"
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.queryParams.current = 1;
      this.getList();
    },
    // 获取分中心名称
    getFzxName(id) {
      for(var i in this.fzxOptions) {
        if(id == this.fzxOptions[i].branchId) {
       
          return this.fzxOptions[i].fzx
        }
      }
    },
    //重置按钮
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    //充钱
    rechargeWindow() {
      this.$refs.rechargeItems.rechargeWindow();
    },
    //消费窗口
    consumptionWindow() {
      this.$refs.consumptionItems.consumptionWindow();
    },
    getList() {
      this.loading = true;
      getListData(this.queryParams).then(response => {
        this.tableData = response.data.records
        this.total = response.data.total;
        this.loading = false;
      })
    },
    //导出
    exportWindow() {
      this.download('/finance/rechargeCard/export', {
        ...this.queryParams
      }, `体检卡充值数据文件${new Date().getTime()}.xlsx`)
    },
    handleSelectionChange(selection) {
      if (selection.length == 1) {
				this.selectId = selection[0].id
        this.getRightList()
			} else if (selection.length > 1) {
				this.$refs.leftTable.clearSelection();//清空表格数据
				this.$refs.leftTable.toggleRowSelection(selection.pop());//最后一条数据
			} else {
        this.selectId = ""
      }
    },
    //单击事件选中当前行
		rowClick(row, col) {
      if (col.type != 'selection') this.$refs.leftTable.clearSelection()
			this.$refs.leftTable.toggleRowSelection(row, true);
		},
    //获取右边表格数据
    getRightList() {
      const query = {
        id: this.selectId
      }
      this.loading1 = true
      getRightData(query).then(response => {
        this.tableDatas = response.data.records
        this.loading1 = false
      })
    }
  }
}
</script>
<style scoped>
#setTable /deep/ .el-table__header-wrapper .el-checkbox {
	display: none;
}
</style>