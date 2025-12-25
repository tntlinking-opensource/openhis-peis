<!-- 财务管理-卡消费明细 麦沃得科技暴雨/矢北-->
<template>
  <div class="app-container flex-direction-column">
    <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="80px">
      <el-form-item size="small" label="体检卡号" prop="cardId">
        <el-input style="width:200px;" placeholder="请输入体检卡号" v-model="queryParams.cardId"></el-input>
      </el-form-item>
      <el-form-item size="small" label="分中心" prop="branchCenter">
        <el-select style="width:200px;" v-model="queryParams.branchCenter">
          <el-option v-for="item, index in fzx" :key="index" :label="item.fzx" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item size="small" label="消费类型" prop="consumetype">
        <el-select style="width:200px;" placeholder="请输入" v-model="queryParams.consumetype"
          @keyup.enter.native="handleQuery">
          <el-option v-for="item, index in typeData" :key="index" :label="item.text" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item size="small" label="金额类型" prop="isAdd">
        <el-select style="width:200px;" v-model="queryParams.isAdd">
          <el-option v-for="item, index in moneyType" :value="item.id" :label="item.text" :key="index"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item size="small" label="领取时间" prop="dateValue">
        <el-date-picker style="width:366px;" value-format="yyyy-MM-dd" type="daterange" v-model="queryParams.dateValue"
          range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
        <el-button type="primary" size="mini" @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="exportdata"
          v-hasPermi="['finance:experienceCardManagement:cardConsumption:export']">导出
        </el-button>
      </el-col>
    </el-row>

    <div class="table-box">
      <el-table :data="tableData" :border="true" v-loading="loading" :stripe="true" height="100%">
        <el-table-column type="selection" align="center"></el-table-column>
        <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
        <el-table-column prop="cardId" label="体检卡号" align="center"></el-table-column>
        <el-table-column prop="cardType" label="卡类型" align="center"></el-table-column>
        <el-table-column prop="chargeId" label="体检号" align="center"></el-table-column>
        <el-table-column prop="operationId" label="操作人" align="center"></el-table-column>
        <el-table-column prop="operationTime" label="操作时间" align="center"></el-table-column>
        <el-table-column prop="consumeType" label="消费类型" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.consumeType == 0">体检  </span>
            <span v-else-if="scope.row.consumeType == 1">药房  </span>
            <span v-else-if="scope.row.consumeType == 2">口腔科  </span>
            <span v-else-if="scope.row.consumeType == 3">眼镜店  </span>
            <span v-else-if="scope.row.consumeType == 4">合作  </span>
            <span v-else-if="scope.row.consumeType == 5">保健品 </span>
            <span v-else></span>
          </template>
        </el-table-column>
        <el-table-column prop="isAdd" label="金额类型" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.isAdd == 0">充值 </span>
            <span v-else-if="scope.row.isAdd == 1">消费 </span>
            <span v-else></span>
          </template>
        </el-table-column>
        <el-table-column prop="limit" label="金额" align="center"></el-table-column>
        <el-table-column prop="handleMoney" label="剩余金额" align="center"></el-table-column>
        <el-table-column prop="branchCenter" label="分中心" align="center"></el-table-column>
        <el-table-column prop="memotext" label="备注" align="center"></el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />
    <rechargeItems ref="rechargeItems"></rechargeItems>
    <consumptionItems ref="consumptionItems"></consumptionItems>
  </div>
</template>

<script>
import rechargeItems from './recharge.vue'
import consumptionItems from './consumption.vue'
import { getListData, GetFZXData } from '@/api/finance/card/card_consumption.js'
export default {
  components: { rechargeItems, consumptionItems },
  data() {
    return {
      typeData: [
        { id: 0, text: '体检' }, { id: 1, text: '药房' }, { id: 2, text: '口腔科' }, { id: 3, text: '眼镜店' }, { id: 4, text: '合作' }, { id: 5, text: '保健品' }
      ],
      moneyType: [
        { id: 0, text: '充值' }, { id: 1, text: '消费' }
      ],
      fzx: [],
      showSearch: true,
      ids: [],//选中的数组
      single: true,//必选且单选
      multiple: true,//必选
      loading: true,
      total: 30,//总数
      queryParams: {
          cardId:'',
          branchCenter:'',
          consumetype:'',
          dateValue:[],
          isAdd:'',
        pageNum: 1,//初始页数
        pageSize: 10,//页值
      },
      form: {

      },
      tableData: [

      ],
    }
  },
  created() {
    this.getfzxData()
    this.getList();
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true;
      getListData(this.queryParams).then(response => {
        this.tableData = response.data.records;
        this.total = response.data.total;
        for( let i in this.fzx){
                    for(let j in this.tableData){
                        if(this.fzx[i].branchId==this.tableData[j].branchCenter)
                        {
                            this.tableData[j].branchCenter=this.fzx[i].fzx
                        }
                    }
                }
        this.loading = false;
      });
    },

    //获取分中心数据
    getfzxData() {
      GetFZXData({ }).then(response => {
        this.fzx = response.data.records
        
      })
    },
    // 搜索
    handleQuery() {
      if (this.queryParams.dateValue) {
        this.queryParams.startTime = this.queryParams.dateValue[0] + " 00:00:00"
        this.queryParams.endTime = this.queryParams.dateValue[1] + " 23:59:59"
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.queryParams.pageNum = 1;
      this.getList();
    },
    //重置方法
    handleReset() {
      this.resetForm("queryForm");
      this.queryParams.dateValue = []
      this.queryParams == {
        size: 10,
        current: 1
      }
      this.handleQuery();
    },
    exportdata() {
      if (this.queryParams.dateValue) {
        this.queryParams.startTime = this.queryParams.dateValue[0] + " 00:00:00"
        this.queryParams.endTime = this.queryParams.dateValue[1] + " 23:59:59"
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.download('/finance/spendDetail/export', {
        ...this.queryParams
      }, `体检卡发放_${new Date().getTime()}.xlsx`)
    },
  }
}
</script>
