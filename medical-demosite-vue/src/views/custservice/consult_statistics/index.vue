<!-- 咨询与随访统计 开发人：麦沃德科技暴雨 -->
<template>
	<div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="咨询医生" prop="doctorUsername">
        <input-select :selectData="doctorData" selectWidth="160" @change="doctorDataChange"></input-select>
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
		<!-- 表格 -->
		<div class="table-box" ref="tableBox">
			<el-table border v-loading="loading" :data="tableList" height="100%" stripe >
				<el-table-column fixed type="selection"  align="center" />
				<el-table-column label="序列"  type="index" align="center" />
        <el-table-column label="医生"  prop="doctorUsername"  align="center" show-overflow-tooltip />
        <el-table-column label="类型"  prop="consultType" align="center" show-overflow-tooltip >
          <template slot-scope="scope">
            <el-tag v-if="scope.row.consultType == 1" type="success">现场咨询</el-tag>
            <el-tag v-if="scope.row.consultType == 2" type="danger">来电咨询</el-tag>
            <el-tag v-if="scope.row.consultType == 3">电话回访</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="数量" prop="count"  align="center" show-overflow-tooltip />
			</el-table>
		</div>
		<!-- 分页 -->
		<pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
			@pagination="getList" />
		<!-- 添加或修改对话框 -->
		<add-items ref="addItems"></add-items>
	</div>
</template>
<script>
import {listPrinttype} from "@/api/custservice/consult_statistics/consult_statistics.js";
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
        current: 1,
        size: 10,
				sfxmsrm: undefined,
				examfeeitemName: undefined,
				tjlx: undefined,
				xb: undefined,
				idDepart: undefined,
				examfeeitemCode: undefined,
				idLabtype: undefined,
			},
			// 表格展示数据
			tableList: [],
      doctorData: {
        placeholder: '请输入输入码选择',
        key: '输入码',//第一列标题
        value: '医生名称',//第二列标题
        firstName:'inputCode',//接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName:'username',//接口返回值对应第二列的参数名(不传默认为'name')
        url: '/member/consulation/getAllUserData',//请求连接
        bindValue: '',
      },
		};
	},
	computed: {},
	watch: {},
	created() {
		this.getList();
	},
	mounted() {},
	methods: {
    doctorDataChange(value) {
      this.queryParams.doctorUsername = value.username
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
			this.resetForm("queryForm");
			this.handleQuery();
		},
	},
};
</script>
