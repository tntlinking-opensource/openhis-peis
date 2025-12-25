<!-- 数据召回 开发人：麦沃德科技暴雨 -->
<template>
	<div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="80px" v-show="showSearch">
      <el-form-item label="选择日期">
        <el-date-picker v-model="queryParams.date" style="width: 350px;" value-format="yyyy-MM-dd" type="daterange"
                        range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 150px;"/>
      </el-form-item>
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 150px;"/>
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="queryParams.phone" placeholder="请输入电话" clearable style="width: 150px;"/>
      </el-form-item>
      <el-form-item label="身份证号" prop="idcardno">
        <el-input v-model="queryParams.idcardno" placeholder="请输入身份证号" clearable style="width: 150px;"/>
      </el-form-item>
      <el-form-item label="订单号" prop="numorgresv">
        <el-input v-model="queryParams.numorgresv" placeholder="请输入订单号" clearable style="width: 150px;"/>
      </el-form-item>
      <el-form-item label="拼音码" prop="inputCode">
        <el-input v-model="queryParams.inputCode" placeholder="请输入拼音码" clearable style="width: 150px;"/>
      </el-form-item>
      <el-form-item label="登记状态" prop="fRegistered">
        <el-select v-model="queryParams.fRegistered" placeholder="请选择" clearable style="width: 150px;">
          <el-option label="已登记" :value="0" />
          <el-option label="未登记" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
		<!-- 操作按钮 -->
		<el-row :gutter="10" class="mb8">
			<el-col :span="2">
				<el-button type="success" plain size="mini" icon="el-icon-s-promotion" @click="handleUpdate"
					v-hasPermi="['reception:dataRecall:recall']">召回
				</el-button>
			</el-col>
		</el-row>
		<!-- 表格 -->
		<div class="table-box" ref="tableBox">
			<el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
				<el-table-column fixed type="selection"  align="center" />
				<el-table-column label="序列"  type="index"  align="center" />
				<el-table-column label="体检号"  prop="patientcode"  align="center"
					show-overflow-tooltip />
				<el-table-column label="姓名"  prop="patientname"  align="center" show-overflow-tooltip />
				<el-table-column label="年龄" prop="age"  align="center" show-overflow-tooltip />
				<el-table-column label="电话" prop="phone"  align="center" show-overflow-tooltip />
				<el-table-column label="身份证号" prop="idcardno"  align="center" show-overflow-tooltip />
        <el-table-column label="订单号" prop="numorgresv"  align="center" show-overflow-tooltip />
        <el-table-column label="单位名称" prop="orgName"  align="center" show-overflow-tooltip />
        <el-table-column label="创建时间" prop="createdate"  align="center" show-overflow-tooltip />
        <el-table-column label="登记状态" prop="fRegistered" align="center" show-overflow-tooltip>
        <template slot-scope="scope">
          <el-tag v-if="scope.row.finish == 0">未登记</el-tag>
          <el-tag type="danger" v-else>登记</el-tag>
        </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="130">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #13ce66" @click="handleUpdate(scope.row)"
                       v-hasPermi="['basis:chargeCategory:edit']">召回</el-button>
          </template>
        </el-table-column>
			</el-table>
		</div>
		<!-- 分页 -->
		<pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
			@pagination="getList" />
		<!-- 添加或修改对话框 -->
		<add-items ref="addItems"></add-items>
	</div>
</template>
<script>
// import { getPrinttype, listPrinttype, addPrinttype, updatePrinttype, delPrinttype } from "@/api/basis/charge";
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
			// 表格展示数据
			tableList: [
				{
          "patientcode": "24",
          "patientname": "张三",
          "sex": "男",
          "age": "33",
          "phone": "1588888899999",
          "isjj": "0",
				}
			],
			// 显示模态框
			showExam: false,
			showUpload: false,
		};
	},
	computed: {},
	watch: {},
	created() {
		this.getList();
	},
	mounted() {},
	methods: {
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
		// 查询列表
		getList() {
			this.loading = true;
			// listPrinttype(this.queryParams).then(response => {
			// 	this.printtypeList = response.rows;
			// 	this.total = response.total;
			this.loading = false;
			// });
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
			this.$refs.addItems.handleAdd()
		},
		// 删除
		handleDelete(row) {
			const ids = row.id || this.ids;
			this.$modal
				.confirm('是否确认删除该数据项？')
				.then(function () {
					// return delPrinttype(ids);
					return;
				})
				.then(() => {
					this.getList();
					this.$modal.msgSuccess("删除成功");
				})
				.catch(() => { });
		},
		// 编辑
		handleUpdate(row) {
			this.$refs.addItems.handleUpdate(row)
		},
	},
};
</script>
