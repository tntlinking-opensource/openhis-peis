<!-- 家庭卡消费记录 麦沃德科技 开发人:清风 / 暴雨-->
<template>
    <div class="app-container flex-direction-column">
        <el-form size="small" :inline="true" label-width="60px">
          <el-form-item label="卡号" style="margin-left:0; margin-right:24px;" :model="queryParams">
            <el-input style="width:230px;" placeholder="请输入卡号" v-model="queryParams.cardno"></el-input>
          </el-form-item>
          <el-form-item label="姓名" style="margin-right:44px;">
            <el-input style="width:230px;" placeholder="请输入姓名" v-model="queryParams.patientname"></el-input>
          </el-form-item>
          <el-form-item label="电话" style="margin-right:24px;">
            <el-input style="width:230px;" placeholder="请输入电话" v-model="queryParams.phone"></el-input>
          </el-form-item>
          <el-form-item label="身份证号" label-width="74px" style="margin-right:24px;">
            <el-input style="width:230px;" placeholder="请输入身份证号" v-model="queryParams.idcardno"></el-input>
          </el-form-item>
            <el-form-item label="充值/消费日期" label-width="110px" style="margin-right:0;">
              <el-date-picker style="width:230px;" placeholder="请输入身份证号" value-format="yyyy-MM-dd" type="daterange"
                              range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"
                              v-model="queryParams.valueTime"
              ></el-date-picker>
            </el-form-item>
          <el-form-item label="类型" style="margin-left:0;margin-right:24px;">
            <el-select style="width:230px;" placeholder="请选择" v-model="queryParams.type">
              <el-option label="全部"/>
              <el-option :key="0" label="充值" :value="0"/>
              <el-option :key="1" label="消费" :value="1"/>
            </el-select>
          </el-form-item>
          <el-form-item label="消费类型" label-width="80px" style="margin-right:24px;">
            <el-select style="width:230px;" placeholder="请选择" v-model="queryParams.consumetype">
              <el-option :key="0" label="体检" :value="0"/>
              <el-option :key="1" label="药房" :value="1"/>
              <el-option :key="2" label="口腔科" :value="2"/>
              <el-option :key="3" label="眼镜店" :value="3"/>
              <el-option :key="4" label="合作" :value="4"/>
              <el-option :key="5" label="保健品" :value="5"/>
            </el-select>
          </el-form-item>
          <el-form-item label="充值/消费金额" label-width="110px" style="margin-right:24px;">
            <el-input-number style="width:130px;" controls-position="right" :min="0" placeholder="0"
                             v-model="queryParams.startMoney"
            ></el-input-number>
            -
            <el-input-number style="width:130px;" controls-position="right" :min="0" placeholder="0"
                             v-model="queryParams.endMoney"
            ></el-input-number>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>

        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button size="mini" type="warning" icon="el-icon-upload2" plain @click="handleExports()" style="margin-left:18px;"
                    v-hasPermi="['custservice:familyCardRecord:export']">导出</el-button>
            </el-col>
        </el-row>

        <!-- 表格数据 -->
        <div class="table-box">
            <el-table :data="tableData" v-loading="loading" :border="true" :stripe="true"
                      @selection-change="handleSelectionChange" height="100%"
            >
              <el-table-column type="selection" align="center"></el-table-column>
              <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
              <el-table-column prop="patientcardno" label="家庭卡号" align="center"></el-table-column>
              <el-table-column prop="type" label="类型" align="center">
                <template slot-scope="scope">
                  <div v-if="scope.row.type == 0">充值</div>
                  <div v-if="scope.row.type == 1">消费</div>
                </template>
              </el-table-column>
              <el-table-column prop="chargeTime" label="充值/消费时间" align="center" show-overflow-tooltip
              ></el-table-column>
              <el-table-column prop="money" label="充值/消费金额" align="center"></el-table-column>
              <el-table-column prop="jf" label="充值/消费积分" align="center"></el-table-column>
              <el-table-column prop="paywayName" label="充值/消费方式" align="center"></el-table-column>
              <el-table-column prop="consumeType" label="消费类型" align="center"></el-table-column>
              <el-table-column prop="bkr" label="办卡人" align="center"></el-table-column>
              <el-table-column prop="patientname" label="姓名" align="center"></el-table-column>
              <el-table-column prop="isMain" label="持卡人关系" align="center"></el-table-column>
              <el-table-column prop="isMpatientcodeain" label="体检号" align="center"></el-table-column>
              <el-table-column prop="endMoney" label="剩余金额" align="center"></el-table-column>
                <el-table-column prop="endJf" label="剩余积分" align="center"></el-table-column>
                <el-table-column prop="kbz" label="备注" align="center" show-overflow-tooltip></el-table-column>
            </el-table>
        </div>


        <!-- 分页 -->
		<pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
			@pagination="getList" />
    </div>
</template>

<script>
    import { getListData } from '@/api/custservice/family_members/family_card_record/family_card_record'

    export default{
        data(){

            return{
                showSearch:true,
                loading:false,
                ids:[],//选中的数组
                single:"",//必选且单选
                multiple:"",//必选
                total:30,//总数
                queryParams:{
                    current:1,//初始页数
                    size:10,//页值
                    cardno:"",
                    patientname:"",
                    phone:"",
                    idcardno:"",
                    valueTime:"",//两个值
                    type:"",
                    consumetype:"",
                    startMoney:"",
                    endMoney:""
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
