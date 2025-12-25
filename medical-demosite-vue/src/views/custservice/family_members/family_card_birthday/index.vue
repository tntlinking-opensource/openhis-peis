<!-- 家庭卡生日  麦沃德科技 开发人:清风 / 暴雨-->
<template>
  <div class="app-container flex-direction-column">
    <el-form size="small" :inline="true" label-width="60px" :model="queryParams">
      <el-form-item label="姓名" style="margin-right:44px;">
        <el-input style="width:230px;" placeholder="请输入姓名" v-model="queryParams.patientname" clearable></el-input>
      </el-form-item>
      <el-form-item label="电话" style="margin-right:24px;">
        <el-input style="width:230px;" placeholder="请输入电话" v-model="queryParams.phone" clearable></el-input>
      </el-form-item>
      <el-form-item label="身份证号" label-width="74px" style="margin-right:24px;">
        <el-input style="width:230px;" placeholder="请输入身份证号" v-model="queryParams.idcardno" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 表格数据 -->
    <div class="table-box">
      <el-table :data="tableData" v-loading="loading" :border="true" :stripe="true"
                @selection-change="handleSelectionChange" height="100%"
      >
        <el-table-column type="selection" align="center"></el-table-column>
        <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
        <el-table-column prop="patientcardno" label="家庭卡号" align="center"></el-table-column>
        <el-table-column prop="balanceJf" label="积分" align="center"></el-table-column>
        <el-table-column prop="balanceMoney" label="金额" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="patientarchiveno" label="档案号" align="center"></el-table-column>
        <el-table-column prop="patientname" label="姓名" align="center"></el-table-column>
        <el-table-column prop="idSex" label="性别" align="center">
          <template slot-scope="scope">
            <div v-if="scope.row.idSex == 0">男</div>
            <div v-if="scope.row.idSex == 1">女</div>
            <div v-if="scope.row.idSex == 2">通用</div>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="电话" align="center"></el-table-column>
        <el-table-column prop="idcardno" label="身份证号" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="birthdate" label="出生日期" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="patientclass" label="会员等级" align="center"></el-table-column>
        <el-table-column prop="dw" label="单位" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="typeName" label="卡类型" align="center"></el-table-column>
        <el-table-column prop="isMain" label="是否主持卡人" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="primary" v-if="scope.row.isMain == true">是</el-tag>
            <el-tag type="warning" v-else>否</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
                @pagination="getList" />
  </div>
</template>

<script>
  import { getListData } from '@/api/custservice/family_members/family_card_birthday/family_card_birthday'

  export default{
    data(){

      return{
        showSearch:true,
        loading:false,
        ids:[],//选中的数组
        single:"",//必选且单选
        multiple:"",//必选
        total:30,//总数
        queryParams: {
          current: 1,//初始页数
          size: 10,//页值
          cardno: '',
          patientname: '',
          phone: '',
          idcardno: '',
          valueTime: '',//两个值
          type: '',
          consumetype: '',
          startMoney: '',
          endMoney: ''
        },
        tableData:[]
      }
    },
    created() {
      this.getList()
    },
    methods:{
      //查询列表
      getList(){
        this.loading = true;
        getListData(this.queryParams).then(response => {
          this.tableData = response.data.records;
          this.total = response.data.total;
          this.loading = false;
        });
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map((item) => item.id);
        this.single = selection.length != 1;
        this.multiple = !selection.length;
      },
      // 搜索
      handleQuery() {
        this.queryParams.current = 1;
        if (this.queryParams.date) {
          this.queryParams.startTime = this.queryParams.date[0] + " 00:00:00"
          this.queryParams.endTime = this.queryParams.date[1] + " 23:59:59"
        } else {
          this.queryParams.startTime = undefined
          this.queryParams.endTime = undefined
        }
        this.getList();
      },
      // 重置
      resetQuery() {
        this.resetForm("queryParams");
        this.handleQuery();
      },
      /** 报告交接统计 */
      handleExports() {
        this.download('/member/familyCardCharge/export', {
        }, `家庭卡消费记录.xlsx`)
      },

    }
  }
</script>
