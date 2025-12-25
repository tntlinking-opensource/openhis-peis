<!-- 生日关怀 麦沃德科技 开发人:清风/暴雨 -->
<template>
    <div class="app-container flex-direction-column">
        <el-form size="small" :inline="true" label-width="50px;">
            <el-form-item label="姓名" style="margin-right:16px;">
                <el-input style="width:160px" v-model="form.patientname" placeholder="请输入姓名"></el-input>
            </el-form-item>
            <el-form-item label="性别" style="margin-right:16px;">
              <el-select style="width:160px;" v-model="queryParams.idSex" clearable placeholder="请选择">
                <el-option :key="0" label="男" :value="0" />
                <el-option :key="1" label="女" :value="1" />
              </el-select>
            </el-form-item>
            <el-form-item label="电话" style="margin-right:16px;">
                <el-input style="width:230px" v-model="form.phone" placeholder="请输入电话"></el-input>
            </el-form-item>
            <el-form-item label="生日" style="margin-right:16px;">
                <el-date-picker v-model="form.startTime" type="date" placeholder="选择日期"></el-date-picker>
            </el-form-item>
            <el-form-item label="年龄段" style="margin-right:16px;">
              <el-select style="width:200px;" v-model="queryParams.age" clearable placeholder="请选择">
                <el-option
                  v-for="item in cities"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                  <span style="float: left">{{ item.label }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ item.labels }}</span>
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="身份证号" style="margin-right:16px;">
                <el-input style="width:230px" v-model="form.idcardno" placeholder="请输入身份证号"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>

        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button type="primary" plain icon="el-icon-message" size="mini" @click="visit()" :disabled="multiple"
                    v-hasPermi="['custservice:membershipManagement:birthdayCare:information_follow']">短信回访
                </el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" plain icon="el-icon-edit" size="mini" @click="handlecancel" :disabled="multiple"
                           v-hasPermi="['custservice:membershipManagement:birthdayCare:information_no']">取消短信发送
                </el-button>
            </el-col>
			<right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
        <!-- 表格数据 -->
        <div class="table-box" style="height:100%;">
            <el-table :data="tableData" v-loading="loading" :border="true" :stripe="true" @selection-change="handleSelectionChange" height="100%">
                <el-table-column type="selection" align="center"></el-table-column>
                <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
                <el-table-column prop="patientcardno" label="会员卡号" min-width="120px" align="center"></el-table-column>
                <el-table-column prop="patientname" label="姓名" min-width="120px" align="center"></el-table-column>
                <el-table-column prop="idSex" label="性别" min-width="120px" align="center">
                    <template slot-scope="scope">
                      <div v-if="scope.row.idSex == 0">女</div>
                      <div v-if="scope.row.idSex == 1">男</div>
                      <div v-if="scope.row.idSex == 2">通用</div>
                    </template>
                </el-table-column>
                <el-table-column prop="idcardno" label="身份证号" min-width="180px" align="center"></el-table-column>
                <el-table-column prop="memberlevel" label="会员级别" min-width="120px" align="center">
                    <template slot-scope="scope">
                      <div v-if="scope.row.memberlevel == 1">普通会员</div>
                      <div v-if="scope.row.memberlevel == 2">VIP会员</div>
                      <div v-if="scope.row.memberlevel == 3">VVIP会员</div>
                    </template>
                </el-table-column>
                <el-table-column prop="birthdate" label="生日" min-width="120px" align="center"  show-overflow-tooltip></el-table-column>
                <el-table-column prop="age" label="年龄" min-width="120px" align="center"></el-table-column>
                <el-table-column prop="phone" label="电话" min-width="120px" align="center"></el-table-column>
                <el-table-column prop="fzx" label="分中心" min-width="120px" align="center"></el-table-column>
                <el-table-column prop="dw" label="单位" min-width="120px" align="center"></el-table-column>
                <el-table-column label="回访状态" min-width="120px" align="center">
                    <template slot-scope="scope">
                        <el-tag size="small" v-if="scope.row.visitStatus == 0">已回访</el-tag>
					            	<el-tag type="danger" size="small" v-else>未回访</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="notifyResult" label="短信状态" min-width="120px" align="center">
                    <template slot-scope="scope">
                      <el-tag size="small" v-if="scope.row.notifyResult == 0">通知失败</el-tag>
                      <el-tag size="small" v-if="scope.row.notifyResult == 1">已取消</el-tag>
                      <el-tag size="small" v-if="scope.row.notifyResult == 2">等待通知</el-tag>
                      <el-tag size="small" v-if="scope.row.notifyResult == 3">已通知</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="visitMan" label="回访人" min-width="120px" align="center"></el-table-column>
                <el-table-column prop="visitTime" label="回访时间" min-width="120px" align="center"></el-table-column>
                <el-table-column prop="visitType" label="回访方式" min-width="120px" align="center"></el-table-column>
                <el-table-column prop="visitNote" label="回访备注" min-width="120px" align="center"></el-table-column>
            </el-table>
        </div>
        <!-- 分页 -->
		<pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
			@pagination="getList" />
        <visitItems ref="visitItems" @getList="getList"></visitItems>
    </div>
</template>
<script>
  import { getListData,cancleSMS } from '@/api/custservice/membership_management/birthday_care/birthday_care'
    import visitItems from './visit.vue';

    export default {
        components:{ visitItems },
        data(){
            return{
                showSearch:true,
                loading:false,
                ids:[],//选中的数组
                single:true,//必选且单选
                multiple:true,//必选
				total:10,//总数
				queryParams:{
          current:1,//初始页数
          size:10,//页值
				},
                form:{
                    patientname:"",
                    phone:"",
                    startTime:"",
                    age:"",
                    idcardno:"",
                    idSex:"",
                },
                tableData:[],
                cities: [{
                  value: '0',
                  label: '少年',
                  labels:'7~17',
                }, {
                  value: '1',
                  label: '青年',
                  labels:'18~40',
                }, {
                  value: '2',
                  label: '中年',
                  labels:'41~65',
                }, {
                  value: '3',
                  label: '老年',
                  labels:'66以上',
                }],
                value: ''
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
            //回访短信
            visit(){
                this.$refs.visitItems.visit(this.ids);
            },
            //取消短信发送
            handlecancel(){
              this.$confirm('确定要取消发送吗?', '取消发送提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                let parameter = {
                  ids:this.ids.toString()
                }
                cancleSMS(parameter).then(response => {
                  this.$modal.msgSuccess("取消发送成功");
                });
              }).catch(() => { });
            },
		         // 多选框选中数据
            handleSelectionChange(selection) {
                this.ids = selection.map((item) => item.id);
                this.single = selection.length != 1;
                this.multiple = !selection.length;
            },
        }
    }
</script>
