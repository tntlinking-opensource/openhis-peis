<!-- 咨询与随访 开发人：麦沃德科技暴雨 -->
<template>
	<div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="姓名" prop="consultName">
        <el-input v-model="queryParams.consultName" placeholder="请输入姓名" clearable style="width: 180px;"
                  @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="电话" prop="consultPhone">
        <el-input v-model="queryParams.consultPhone" placeholder="请输入电话" clearable style="width: 180px;"
                  @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="咨询医生" prop="doctorUsername">
        <input-select :selectData="doctorData" selectWidth="180" @change="doctoDataChange"></input-select>
      </el-form-item>
      <el-form-item label="类型" prop="consultType">
        <el-select v-model="queryParams.consultType" placeholder="请选择" clearable style="width: 180px;">
          <el-option label="现场咨询" :value="1" />
          <el-option label="来电咨询" :value="2" />
          <el-option label="电话回访" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="咨询时间">
        <el-date-picker v-model="queryParams.date" style="width: 350px;" value-format="yyyy-MM-dd" type="daterange"
                        range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="2">
        <el-button type="warning" plain size="mini" icon="el-icon-coin" @click="handleAdd"
                   v-hasPermi="['custservice:consulation:add']" >咨询与随访
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
		<!-- 表格 -->
		<div class="table-box" ref="tableBox">
			<el-table border v-loading="loading" :data="tableList" height="100%" stripe  @selection-change="handleSelectionChange">
				<el-table-column fixed type="selection"  align="center" />
				<el-table-column label="序列"  type="index" align="center" />
				<el-table-column label="姓名"  prop="consultName"  align="center" show-overflow-tooltip />
				<el-table-column label="体检号"  prop="patientcode" align="center" show-overflow-tooltip />
				<el-table-column label="电话" prop="consultPhone"  align="center" show-overflow-tooltip />
        <el-table-column label="医生"  prop="doctorUsername"  align="center" show-overflow-tooltip />
        <el-table-column label="类型"  prop="consultType" align="center" show-overflow-tooltip >
          <template slot-scope="scope">
            <el-tag v-if="scope.row.consultType == 1" type="success">现场咨询</el-tag>
            <el-tag v-if="scope.row.consultType == 2" type="danger">来电咨询</el-tag>
            <el-tag v-if="scope.row.consultType == 3">电话回访</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="内容" prop="consultContent"  align="center" show-overflow-tooltip />
        <el-table-column label="时间" prop="consultTime"  align="center" show-overflow-tooltip />
			</el-table>
		</div>
		<!-- 分页 -->
		<pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
			@pagination="getList" />
		<!-- 添加或修改对话框 -->
		<add-items ref="addItems" @getList="getList"></add-items>
	</div>
</template>
<script>
import { getPrinttype, listPrinttype, addPrinttype, updatePrinttype, delPrinttype } from "@/api/custservice/consulation/consulation.js";
import addItems from './add'
export default {
	components: { addItems },
	props: [],
	data() {
		return {
			// 遮罩层
			loading: true,
			// 选中数组
			ids: [],
			// 总条数
			total: 0,
			// 非单个禁用
			single: true,
			// 非多个禁用
			multiple: true,
			// 显示搜索条件
			showSearch: true,
			// 查询参数
			queryParams: {
				pageNum: 1,
				pageSize: 10,
				sfxmsrm: undefined,
				examfeeitemName: undefined,
				tjlx: undefined,
				xb: undefined,
				idDepart: undefined,
				examfeeitemCode: undefined,
				idLabtype: undefined,
			},
			doctorData: {
				placeholder: '请输入输入码选择',
				key: '输入码',//第一列标题
				value: '医生名称',//第二列标题
				firstName:'inputCode',//接口返回值对应第一列的参数名(不传默认为'inputCode')
				secondName:'username',//接口返回值对应第二列的参数名(不传默认为'name')
				url: '/member/consulation/getAllUserData',//请求连接
				bindValue: '',
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
			},
			// 表格展示数据
			tableList: [],
			// 显示模态框
			showExam: false,
			showUpload: false,
		};
	},
	computed: {},
	watch: {},
	mounted() {},
  created() {
    this.getList()
  },
  methods: {
    // 选择类型
    doctoDataChange(value) {
      this.queryParams.doctorUsername = value.username
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.rows = selection;
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
		// 查询列表
		getList() {
			this.loading = true;
			listPrinttype(this.queryParams).then(response => {
				this.tableList = response.data.records;
				this.total = response.data.total;
			  this.loading = false;
			});
		},
		// 搜索
		handleQuery() {
			this.queryParams.pageNum = 1;
			this.getList();
		},
		// 重置
		resetQuery() {
			this.resetForm("queryForm");
			this.handleQuery();
		},
		// 添加
		handleAdd() {
      if(this.rows != undefined){
          if(this.rows.length > 1 || undefined){
            this.$alert("请选择一条记录修改，或不选择记录添加新的记录!", "提醒", {
              confirmButtonText: "确定",
              type: "warning",
            })
          }else if(this.ids.length == 1){
           
           
            this.$refs.addItems.handleAdd(this.ids)
          }else{
         
            this.$refs.addItems.handleAdds()
          }
      }else{
       
        this.$refs.addItems.handleAdds()
      }


		},

	},
};
</script>
