<!-- 会员积分 麦沃德科技 开发人：清风 / 暴雨-->
<template>
    <div class="app-container flex-direction-column">
        <!-- 搜索栏 -->
        <el-form size="small" :inline="true" :model="queryParams"  label-width="80px;">
            <el-form-item label="会员卡号" style="margin-right: 16px;">
                <el-input placeholder="请输入会员卡号" style="width:160px;" v-model="queryParams.cardId" clearable></el-input>
            </el-form-item>
            <el-form-item label="会员姓名" style="margin-right: 16px;">
                <el-input placeholder="请输入会员姓名" style="width:160px;" v-model="queryParams.memberName" clearable></el-input>
            </el-form-item>
            <el-form-item label="会员卡类型" style="margin-right: 16px;">
              <el-select v-model="queryParams.cardType"  placeholder="请选择会员卡类型" style="width: 160px" clearable>
                <el-option :key="11" label="普通会员卡" :value="11" />
                <el-option :key="12" label="VIP会员卡" :value="12" />
              </el-select>
            </el-form-item>
            <el-form-item label="积分类型" style="margin-right: 16px;">
                <el-select placeholder="请选择" style="width:160px;" v-model="queryParams.isAdd" clearable>
                  <el-option :key="0" label="充值" :value="11" />
                  <el-option :key="1" label="消费" :value="12" />
                </el-select>
            </el-form-item>
            <el-form-item label="选择日期" style="margin-right: 18px;">
                <el-date-picker value-format="yyyy-MM-dd" type="daterange" v-model="queryParams.dateValue"
					range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
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
                       v-hasPermi="['custservice:membershipManagement:memberPoints:export']">导出Excel</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
        <!-- 表格数据 -->
        <div class="table-box" style="height:100%;">
            <el-table :data="tableData" v-loading="loading" :border="true" :stripe="true"  height="100%">
                <el-table-column type="selection" align="center"></el-table-column>
                <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
                <el-table-column prop="cardId" label="会员卡号" align="center"></el-table-column>
                <el-table-column prop="cardType" label="会员卡类型" align="center">
                  <template slot-scope="scope">
                    <el-tag v-if="scope.row.cardType == 11" type="success">普通会员卡</el-tag>
                    <el-tag v-else-if="scope.row.cardType == 12" type="danger">VIP会员卡</el-tag>
                  </template>
                </el-table-column>
                <el-table-column prop="memberName" label="姓名" align="center"></el-table-column>
                <el-table-column prop="patientcode" label="体检号" align="center"></el-table-column>
                <el-table-column prop="handleIntegral" label="剩余积分" align="center"></el-table-column>
                <el-table-column prop="isAdd" label="积分类型" align="center">
                  <template slot-scope="scope">
                    <el-tag v-if="scope.row.isAdd == 0" type="success">充值</el-tag>
                    <el-tag v-else-if="scope.row.isAdd == 1" type="danger">消费</el-tag>
                  </template>
                </el-table-column>
                <el-table-column prop="limit" label="分值" align="center"></el-table-column>
                <el-table-column prop="operationId" label="操作人" align="center"></el-table-column>
                <el-table-column prop="branchCenter" label="分中心" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column prop="operationTime" label="操作时间" align="center"></el-table-column>
                <el-table-column prop="memotext" label="备注" align="center"></el-table-column>
            </el-table>
        </div>

        <!-- 分页 -->
		<pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
			@pagination="getList" />
    </div>
</template>

<script>
    import { getListData } from '@/api/custservice/membership_management/member_points/member_points'

    export default{
        data(){
            return{
                showSearch:true,
                loading:false,
                ids:[],//选中的数组
                single:"",//必选且单选
                multiple:"",//必选
                total:10,//总数
                queryParams:{
                  current:1,//初始页数
                  size:10,//页值
                },
                tableData:[],
                form:{

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
              this.tableData = response.data.records;
              this.total = response.data.total;
              this.loading = false;
            });
          },
          /** 导出按钮操作 */
          handleExport() {
            this.download('/member/integral/export', {
            }, `会员积分.xlsx`)
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
