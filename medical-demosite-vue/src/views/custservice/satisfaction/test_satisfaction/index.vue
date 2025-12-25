<!-- 体检满意度  开发人：麦沃德科技矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="70px"
			v-show="showSearch">
			<el-form-item label="分中心" prop="fzx" v-if="userName == 'admin'">
				<input-select ref="fzx" :selectData="fzxData" selectWidth="150" @change="fzxDataChange"></input-select>
			</el-form-item>
			<el-form-item label="姓名" prop="patientname">
				<el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 130px;"
					@keyup.enter.native="handleQuery" />
			</el-form-item>
			<el-form-item label="体检号" prop="patientcode">
				<el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 130px;"
					@keyup.enter.native="handleQuery" />
			</el-form-item>
			<!-- <el-form-item label="评价结果" prop="appraiseResult">
				<el-select v-model="queryParams.appraiseResult" placeholder="请选择满意度" clearable style="width: 130px;">
					<el-option v-for="options in pjjgOptions" :key="options.id" :label="options.text"
						:value="options.id" />
				</el-select>
			</el-form-item> -->
			<el-form-item label="评价时间">
				<el-date-picker v-model="queryParams.date1" style="width: 300px;" value-format="yyyy-MM-dd"
					type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期">
				</el-date-picker>
			</el-form-item>
            <el-form-item label="登记时间">
				<el-date-picker v-model="queryParams.date2" style="width: 300px;" value-format="yyyy-MM-dd"
					type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期">
				</el-date-picker>
			</el-form-item>
			<el-form-item > 
						<el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
						<el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
					</el-form-item>
            </el-form>
			<el-row :gutter="10" class="mb8">
				<el-col :span="1.5">
					<el-button type="success" plain size="mini" icon="el-icon-help" :disabled="single" @click="handleEdit"
						v-hasPermi="['custservice:satisfaction:testSatisfaction:informationedit']">编辑客户满意度</el-button>
				</el-col>
				<!-- <el-col :span="1.5">
					<el-button type="primary" plain size="mini" icon="el-icon-help" @click="handleLook"
						v-hasPermi="['custservice:satisfaction:testSatisfaction:informationlookup']">查看客户评价</el-button>
				</el-col> -->
				<el-col :span="1.5">
					<el-button type="warning" plain size="mini" icon="el-icon-help" @click="handleExport"
						v-hasPermi="['custservice:satisfaction:testSatisfaction:informationexport']">导出</el-button>
				</el-col>
			</el-row>
			<!-- 表格部分 -->
			<div class="table-box">
				 <el-table 
                :data="tableData" 
                border 
				 @selection-change="handleSelectionChange"
                style="width: 100%">
                
                <!-- 空白列 -->
                <el-table-column fixed type="selection" width="55" align="center" />
                
                <!-- 基本信息列组 -->
                <el-table-column label="基本信息" align="center">
                    <el-table-column prop="patientname" label="姓名" width="120" align="center"></el-table-column>
                    <el-table-column prop="phone" label="手机号码" width="150" align="center"></el-table-column>
                    <el-table-column prop="patientcode" label="体检号码" width="120" align="center"></el-table-column>
                    <el-table-column prop="fzx" label="体检中心" width="200" align="center"></el-table-column>
                </el-table-column>
                
                <!-- 服务体验评价列组 -->
                 <el-table-column label="服务体验评价" align="center">
					<el-table-column prop="question1" label="预约流程便捷性" width="140" align="center">
					<template #default="scope">
						{{ scope.row.question1 }} 分
					</template>
					</el-table-column>
					<el-table-column prop="question2" label="体检环境整洁舒适度" width="150" align="center">
					<template #default="scope">
						{{ scope.row.question2 }} 分
					</template>
					</el-table-column>
					<el-table-column prop="question3" label="医护人员服务态度" width="140" align="center">
					<template #default="scope">
						{{ scope.row.question3 }} 分
					</template>
					</el-table-column>
					<el-table-column prop="question4" label="检查项目指引清晰度" width="150" align="center">
					<template #default="scope">
						{{ scope.row.question4 }} 分
					</template>
					</el-table-column>
					<el-table-column prop="question5" label="等待时间合理性" width="140" align="center">
					<template #default="scope">
						{{ scope.row.question5 }} 分
					</template>
					</el-table-column>
				</el-table-column>
                
                <!-- 开放式问题 -->
                <el-table-column label="开放式问题" align="center">
                    <el-table-column prop="question7" label="您对本次体检服务最满意的方面是?" width="260" align="center"></el-table-column>
                    <el-table-column prop="question8" label="您认为需要改进的地方是?" width="200" align="center"></el-table-column>
                    <el-table-column prop="question9" label="其它建议或意见" width="180" align="center"></el-table-column>
                </el-table-column>

				<!-- 总体评价 -->
                <el-table-column label="总体评价" align="center">
					<el-table-column prop="question10" label="您对本次体检服务的整体满意度" width="140" align="center">
					   <template slot-scope="scope">
							<div v-if="scope.row.question10 == 1">非常满意</div>
							<div v-else-if="scope.row.question10 == 2">满意</div>
							<div v-else-if="scope.row.question10 == 3">一般</div>
							<div v-else-if="scope.row.question10 == 4">不满意</div>
							<div v-else-if="scope.row.question10 == 5">非常不满意</div>
						</template>
					</el-table-column>
                    <el-table-column prop="question11" label="您是否会推荐他人选择本中心?" width="230" align="center">
						<template slot-scope="scope">
							<div v-if="scope.row.question11 == 1">一定会</div>
							<div v-else-if="scope.row.question11 == 2">可能会</div>
							<div v-else-if="scope.row.question11 == 3">不确定</div>
							<div v-else-if="scope.row.question11 == 4">不会</div>
						</template>
					</el-table-column>
                </el-table-column>
            </el-table>
			<!-- <el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
				<el-table-column fixed type="selection" width="55" align="center" />
				<el-table-column label="序列"  type="index" width="60" align="center" />
				<el-table-column label="评价结果"  prop="appraiseResult" min-width="200" align="center"
					show-overflow-tooltip >
					<template slot-scope="scope">
						<span v-if="scope.row.appraiseResult == 0" >未评价</span>
            <span v-else-if="scope.row.appraiseResult == 1" >非常满意</span>
            <span v-else-if="scope.row.appraiseResult == 2" >满意</span>
						<span v-else-if="scope.row.appraiseResult == 3" >基本满意</span>
            <span v-else-if="scope.row.appraiseResult == 4" >不满意</span>
						<span v-else >未评价</span>
					</template>
					</el-table-column>
				<el-table-column label="体验号" prop="patientcode" min-width="160" align="center" show-overflow-tooltip />
				<el-table-column label="姓名" prop="patientname" width="140" align="center" show-overflow-tooltip />
				<el-table-column label="性别" prop="idSex" min-width="140" align="center" show-overflow-tooltip >
					<template slot-scope="scope">
						<div v-for="item in xbOptions" :key="item.id">
							<span v-if="(item.id == scope.row.idSex)">{{ item.text }}</span>
						</div>
					</template>
				</el-table-column>
				<el-table-column label="年龄" prop="age" min-width="160" align="center" show-overflow-tooltip />
				<el-table-column label="公司" prop="orgName" min-width="100" align="center" />
				<el-table-column label="部门" prop="orgDepart" min-width="100" align="center" />
				<el-table-column label="电话" prop="phone" min-width="100" align="center" />
				<el-table-column label="开单医生" prop="doctorapply" min-width="100" align="center" />
				<el-table-column label="评价时间" prop="appraiseTime" min-width="100" align="center" />
				<el-table-column label="评价备注" prop="note" min-width="100" align="center" />
			</el-table> -->
			</div>
			 <!-- 分页 -->
			 <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
      @pagination="getList" />
			<addView ref="addView" @getList="getList"></addView>
  </div>
</template>

<script>
import Cookies from 'js-cookie'
import addView from "./view.vue"
import {listTestSatisfaction,} from "@/api/custservice/satisfaction/test_satisfaction.js";
import { sign } from "crypto";
export default {
	components:{
		addView
	},
    data(){
        return{
			userName:Cookies.get("username"),
			total:0,
			single:true,
			showSearch:true,
        	queryParams: {
				data1:undefined,
				data2:undefined,
				current: 1,
				size: 20,
				fzxId:Cookies.get("cid"),
				patientname:undefined,
				personcode:undefined,
				appraiseResult:undefined,
			},
			 // fzx名称
			fzxData: {
				placeholder: '请输入输入码选择',
				key: '输入码',
				value: '分中心',
				url: '/sell/monthtarget/getBranchData',
				bindValue: '', //初始值
				firstName: 'srm',
				secondName: 'fzx',
				queryData: 'key',
			},
			tableList:[
			],
				// 性别
				xbOptions: [
				{ id: 0, text: "男" },
				{ id: 1, text: "女" },
				{ id: 2, text: "通用" }
			],
			///回放結果
			hfjgOptions: [
				{
					id: 0,
					text: "不评价"
				},
				{
					id: 1,
					text: "非常满意"
				},
				{
					id: 2,
					text: "满意"
				},
				{
					id: 3,
					text: "基本"
				},
				{
					id: 4,
					text: "不满意"
				}
			],
			///评价結果
			pjjgOptions: [
				{
					id: 0,
					text: "不评价"
				},
				{
					id: 1,
					text: "非常满意"
				},
				{
					id: 2,
					text: "满意"
				},
				{
					id: 3,
					text: "基本"
				},
				{
					id: 4,
					text: "不满意"
				}
			],
			loading:true,
			// 表格数据
            tableData: [],
			// 
			setvalue:{}
        }
        },
		created(){
			this.getList();
		},
		methods:{
		  // 查询列表
			getList() {
				this.loading = true;
				listTestSatisfaction(this.queryParams).then(response => {
					this.tableData = response.data.records;
					console.log("数据值是",this.tableData);
					
					this.total = response.data.total;
					this.loading = false;
				});
			},
			  // 分中心变化选项
			fzxDataChange(value) {
				this.fzx = value.id
				this.setvalue = value
				this.fzxData.bindValue = value.name
				this.queryParams.fzxId = value.branchId
				this.getList();

			},
			// 搜索
		handleQuery() {
			this.queryParams.current = 1;
			if (this.queryParams.date1) {
				this.queryParams.startTime = this.queryParams.date1[0] + " 00:00:00"
				this.queryParams.endTime= this.queryParams.date1[1] + " 23:59:59"
			} else {
				this.queryParams.startTime = undefined
				this.queryParams.endTime = undefined
			}
			if (this.queryParams.date2) {
				this.queryParams.registrationStartTime = this.queryParams.date2[0] + " 00:00:00"
				this.queryParams.registrationEndTime= this.queryParams.date2[1] + " 23:59:59"
			} else {
				this.queryParams.registrationStartTime = undefined
				this.queryParams.registrationEndTime = undefined
			}
		
			this.getList();
		
		},
		// 多选框选中数据
		handleSelectionChange(selection) {
			this.ids = selection.map((item) => item.id);
			this.single = selection.length != 1;
			this.multiple = !selection.length;
		},
		
			///重置
			resetQuery() {
			this.queryParams.date1 = undefined
			this.queryParams.startTime1= undefined
			this.queryParams.endTime1 = undefined
			this.queryParams.date2 = undefined
			this.queryParams.startTime2 = undefined
			this.queryParams.endTime2 = undefined
			this.resetForm("queryForm");
			this.handleQuery();
    },
		//编辑客户满意度
		handleLook()
		{

		},
		///编辑客户满意度
		handleEdit()
		{	
			if(this.ids[0])
			{
				this.$refs.addView.getNewList(this.ids[0])
			}else{
				this.$message({
				message: '请选择已经录入满意度的体检者查看！！！',
				type: 'warning'
				});
			}
		
		},
		//导出
		handleExport()
		{
			this.download('/member/appSatisfactionLevel/export',{
        ...this.queryParams
      }, `体检满意度.xlsx`)
		},
    },
	

		
}
</script>

<style>

</style>