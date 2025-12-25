<!-- 平台会员 麦沃德科技 开发人：清风 / 暴雨 -->
<template>
    <div class="app-container flex-direction-column">
        <!-- 搜索栏 -->
        <el-form size="small" :inline="true" label-width="80px;" :model="queryParams">
            <el-form-item label="分中心" style="margin-left:20px; margin-right: 16px;">
                <el-select placeholder="请选择" style="width:230px;" v-model="queryParams.fzx">
<!--                   <el-option  :label="item.label" :value="item.value"></el-option>-->
                </el-select>
            </el-form-item>
            <el-form-item label="会员卡号" style="margin-right: 16px;">
                <el-input placeholder="请输入会员卡号" style="width:230px;" v-model="queryParams.patientcardno" clearable></el-input>
            </el-form-item>
            <el-form-item label="会员姓名" style="margin-right: 16px;">
                <el-input placeholder="请输入会员姓名" style="width:230px;" v-model="queryParams.patientname" clearable></el-input>
            </el-form-item>
            <el-form-item label="联系电话" style="margin-right: 16px;">
                <el-input placeholder="请输入联系电话" style="width:230px;" v-model="queryParams.phone" clearable></el-input>
            </el-form-item>
            <el-form-item label="身份证号" style="margin-right: 16px;">
                <el-input placeholder="请输入身份证号" style="width:230px;" v-model="queryParams.idcardno" clearable></el-input>
            </el-form-item>
            <el-form-item style="margin-right: 0;">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button size="mini" type="primary" icon="el-icon-upload2" plain @click="handleExport"
                    v-hasPermi="['custservice:membershipManagement:platform:export']">导出Excel</el-button>
            </el-col>
			<right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
        <!-- 表格数据 -->
        <div class="table-box">
            <el-table :data="tableData" v-loading="loading" :border="true" :stripe="true"  height="100%">
                <el-table-column type="selection" align="center"></el-table-column>
                <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
                <el-table-column prop="patientcardno" label="会员卡号" min-width="160" align="center"></el-table-column>
                <el-table-column prop="patientname" label="姓名" min-width="120" align="center"></el-table-column>
                <el-table-column prop="balanceJf" label="可用积分" min-width="100" align="center"></el-table-column>
                <el-table-column prop="idSex" label="性别" min-width="100" align="center">
                  <template slot-scope="scope">
                    <div v-if="scope.row.idSex == 0">男</div>
                    <div v-if="scope.row.idSex == 1">女</div>
                    <div v-if="scope.row.idSex == 2">通用</div>
                  </template>
                </el-table-column>
                <el-qtable-column prop="phone" label="电话" min-width="120" align="center"></el-qtable-column>
                <el-table-column prop="memberlevel" label="会员级别" min-width="120" align="center">
                  <template slot-scope="scope">
                    <div v-if="scope.row.memberlevel == 1">普通会员</div>
                    <div v-if="scope.row.memberlevel == 2">VIP会员</div>
                    <div v-if="scope.row.memberlevel == 3">VVIP会员</div>
                  </template>
                </el-table-column>
                <el-table-column prop="fzx" label="分中心" min-width="140" align="center"></el-table-column>
                <el-table-column prop="membercreate" label="创建人" min-width="120" align="center"></el-table-column>
                <el-table-column prop="khjl" label="客户经理" min-width="120" align="center"></el-table-column>
                <el-table-column prop="birthdate" label="生日" min-width="120" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column prop="idcardno" label="身份证号" min-width="160" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column prop="dw" label="单位" min-width="160" align="center"></el-table-column>
                <el-table-column prop="tcmc" label="套餐" min-width="160" align="center"></el-table-column>
                <el-table-column prop="tcChecked" label="套餐已使用" min-width="160" align="center">
                  <template slot-scope="scope">
                    <div v-if="scope.row.tcChecked == 0">否</div>
                    <div v-if="scope.row.tcChecked == null">否</div>
                    <div v-if="scope.row.tcChecked == 1">是</div>
                  </template>
                </el-table-column>
                <el-table-column prop="tcPatientcode" label="套餐体检号" min-width="160" align="center"></el-table-column>
                <el-table-column prop="tcDateregister" label="套餐登记时间" min-width="160" align="center"></el-table-column>
                <el-table-column prop="cards" label="绑定体检卡" min-width="160" align="center"></el-table-column>
                <el-table-column prop="recheckMoney" label="复查额度" min-width="160" align="center"></el-table-column>
                <el-table-column prop="note" label="备注" min-width="160" align="center"></el-table-column>
                <el-table-column prop="createdate" label="创建日期" min-width="160" align="center"></el-table-column>
            </el-table>
        </div>
        <!-- 分页 -->
		<pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
			@pagination="getList" />

    </div>
</template>

<script>
  import { getListData } from '@/api/custservice/membership_management/platform_member/platform_member'
    export default{
        data(){
            return{
                showSearch:true,
                loading:"",
                ids:[],//选中的数组
                single:true,//必选且单选
                multiple:true,//必选
				        total:10,//总数
				        queryParams:{
                  current:1,//初始页数
                  size:10,//页值
				        },
                tableData:[],
                form:{
                    patientcardno:"",
                    patientname:"",
                    phone:"",
                    idcardno:""
                }
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
                response.data.records.forEach(el=>{
                  if(el.birthdate){
                    el.birthdate = el.birthdate.split(' ')[0]
                  }
                })
                this.tableData = response.data.records;
                this.total = response.data.total;
                this.loading = false;
              });
            },
            /** 导出按钮操作 */
            handleExport() {
              this.download('/member/online/export', {
              }, `平台会员.xlsx`)
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
            this.queryParams.date=undefined,
              this.queryParams.text=undefined,
              this.queryParams.phone=undefined,
              this.queryParams.patientname=undefined
            this.handleQuery();
          },
        }
    }
</script>
