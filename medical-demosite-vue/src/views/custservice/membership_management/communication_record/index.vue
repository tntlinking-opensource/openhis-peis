<!-- 沟通记录 麦沃德科技 开发人:清风/暴雨 -->
<template>
    <div class="app-container flex-direction-column">
        <el-form size="small" :inline="true" label-width="50px" :model="queryParams">
            <el-form-item label="姓名" style="margin-left:16px;">
                <el-input style="width:230px;" placeholder="请输入姓名" v-model="queryParams.patientname" clearable></el-input>
            </el-form-item>
            <el-form-item label="电话" style="margin-left:16px;">
                <el-input style="width:230px;" placeholder="请输入电话" v-model="queryParams.phone" clearable></el-input>
            </el-form-item>
            <el-form-item label="选择日期" label-width="auto" style="margin-left:16px;">
                <el-date-picker value-format="yyyy-MM-dd" type="daterange" v-model="queryParams.date"
                    range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
            </el-form-item>
            <el-form-item label="跟进内容" label-width="80px" style="margin-right:24px;">
              <el-select style="width:200px;" v-model="queryParams.text" clearable placeholder="请选择">
                <el-option :key="0" label="生日回访" :value="0" />
                <el-option :key="1" label="前台满意度回访" :value="1" />
                <el-option :key="2" label="科室满意度回访" :value="2" />
                <el-option :key="3" label="阳性结果回访" :value="3" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button size="mini" type="warning" icon="el-icon-upload2" plain @click="handleExport"
                    v-hasPermi="['custservice:membershipManagement:communicationRecord:export']">导出Excel</el-button>
            </el-col>
			<right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
        <!-- 表格数据 -->
        <div class="table-box">
            <el-table :data="tableData" v-loading="loading" :border="true" :stripe="true"  height="100%">
                <el-table-column type="selection" align="center"></el-table-column>
                <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
                <el-table-column prop="patientname" label="姓名" align="center"></el-table-column>
                <el-table-column prop="idSex" label="性别" align="center">
                  <template slot-scope="scope">
                    <div v-if="scope.row.idSex == 0">男</div>
                    <div v-if="scope.row.idSex == 1">女</div>
                    <div v-if="scope.row.idSex == 2">通用</div>
                  </template>
                </el-table-column>
                <el-table-column prop="phone" label="电话" align="center"></el-table-column>
                <el-table-column prop="idCard" label="身份证号" align="center"></el-table-column>
                <el-table-column prop="memberLeaver" label="会员级别" align="center"></el-table-column>
                <el-table-column prop="fzx" label="分中心" align="center"></el-table-column>
                <el-table-column prop="khjl" label="客户经理" align="center"></el-table-column>
                <el-table-column prop="dw" label="单位" align="center"></el-table-column>
                <el-table-column prop="text" label="跟进内容" align="center"></el-table-column>
                <el-table-column prop="memo" label="备注" align="center"></el-table-column>
            </el-table>
        </div>
        <!-- 分页 -->
		<pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
			@pagination="getList" />
    </div>
</template>

<script>
    import { getListData } from '@/api/custservice/membership_management/communication_record/communication_record'

    export default{
        data(){
            return{
                showSearch:true,
                total:30,//总页数
                queryParams:{
                  current:1,//初始页数
                  size:10,//页值
                },
                loading:false,
                form:{
                    patientname:"",
                    phone:"",
                    dateValue:"",
                    text:""
                },
                tableData:[],
                ids:[],//选中的数组
                single:"",//必选且单选
                multiple:"",//必选
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
          /** 导出按钮操作 */
          handleExport() {
            this.download('/member/interflow/export', {
            }, `沟通记录.xlsx`)
          },
        }
    }
</script>
