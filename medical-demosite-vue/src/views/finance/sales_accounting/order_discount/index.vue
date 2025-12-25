<!-- 团检订单折扣 开发人：麦沃德科技清风 / 暴雨-->
<template>
    <div class="app-container flex-direction-column">
        <el-form :inline="true" label-width="80px">
            <el-form-item label="订单号">
                <el-input placeholder="请输入订单号" v-model="queryParams.ddh" style="width:230px;" clearable></el-input>
            </el-form-item>
            <el-form-item label="订单名称">
                <el-input placeholder="请输入订单名称" v-model="queryParams.ddmc" style="width:230px;" clearable></el-input>
            </el-form-item>
            <el-form-item label="订单折扣">
                <el-input placeholder="请输入订单折扣" v-model="queryParams.ddzk" style="width:230px;" clearable></el-input>
            </el-form-item>
<!--            <el-form-item label="记账日期">-->
<!--                <el-date-picker  v-model="queryParams.date" style="width:366px;" value-format="yyyy-MM-dd" type="daterange"-->
<!--                        range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>-->
<!--            </el-form-item>-->
            <el-form-item>
              <el-button type="primary" size="mini" icon="el-icon-search" @click="handleSearch">搜索</el-button>
              <el-button size="mini" icon="el-icon-refresh" @click="resetQuery">重置</el-button>
              <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="queryWindow"
                         v-hasPermi="['finance:salesAccounting:orderDiscount:query']" :disabled="single">查看</el-button>
            </el-form-item>
        </el-form>

        <div class="table-box">
            <el-table border v-loading="loading" :data="tableData" height="100%" stripe
				@selection-change="handleSelectionChange">
                <el-table-column type="selection" align="center"></el-table-column>
                <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
                <el-table-column prop="ddh" label="订单号" align="center"></el-table-column>
                <el-table-column prop="ddmc" label="订单名称" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column prop="ddzk" label="订单折扣" align="center"></el-table-column>
                <el-table-column prop="khdwmc" label="客户单位名称" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column prop="khdwdh" label="客户单位电话" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column prop="xsjl" label="销售经理" align="center"></el-table-column>
                <el-table-column prop="fzx" label="分中心" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column prop="cjddrq" label="创建订单日期" align="center"></el-table-column>
                <el-table-column prop="jhjqc" label="计划检期从" align="center"></el-table-column>
                <el-table-column prop="jhjqd" label="计划检期到" align="center"></el-table-column>
            </el-table>
        </div>

      <!-- 分页 -->
		<pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
			@pagination="getList" />

      <!-- 添加或修改对话框 -->
      <add-dialog ref="addDialog" ></add-dialog>
    </div>
</template>

<script>

import { getListData } from '@/api/finance/sales_accounting/order_discount'
import addDialog from './add'

export default{
  components: { addDialog },
    data(){
      return {
            ids:[],
            single:true,
            multiple:true,
            showSearch: true,
            loading: false,
            total:0,
            queryParams:{
                current:1,
                size:10,
                orderId:"",
                fptt:"",
                ssxsjl:"",
                ssddh:"",
                status:"",
                ddh:"",
                ddmc:"",
                ddzk:"",
                date:"",
                startTime:"",
                endTime:""
            },
            //查询表单
            form:{
              current:1,
              size:10,
              orderId:"",
              fptt:"",
              ssxsjl:"",
              ssddh:"",
              status:"",
              ddh:"",
              ddmc:"",
              ddzk:"",
              date:"",
            },
            //数据表格
            tableData:[
                {
                    id:"",
                    ddh:"",
                    ddmc:"",
                    ddzk:"",
                    khdwmc:"",
                    khdwdh:"",
                    xsjl:"",
                    fzx:"",
                    cjddrq:"",
                    jhjqc:"",
                    jhjqd:"",
                },
            ],
            selection:{},
        }
    },
  created() {
      this.getList()
  },
  methods:{
        // 查询列表
        getList() {
          this.loading = true;
          getListData(this.queryParams).then(response => {
            this.tableData = response.data.records;
            this.total = response.data.total;
            this.loading = false;
          });
        },
        // 搜索
        handleSearch() {
          this.queryParams.current = 1
          if (this.queryParams.date) {
            this.queryParams.startTime = this.queryParams.date[0] + " 00:00:00"
            this.queryParams.endTime = this.queryParams.date[1] + " 23:59:59"
          } else {
            this.queryParams.startTime = undefined
            this.queryParams.endTime = undefined
          }
          this.getList()
        },
        handleSelectionChange(selection){
            this.ids = selection.map((item) => item.id);
            this.single = selection.length != 1;
            this.multiple = !selection.length;
        },
        queryWindow(){
          this.$refs.addDialog.handleAdd(this.ids)
        },
        resetQuery(){
          this.getList()
        }
    },
}
</script>
