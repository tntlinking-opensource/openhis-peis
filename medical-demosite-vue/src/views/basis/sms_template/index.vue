<!-- 短信模板维护 开发人：麦沃德科技暴雨 -->
<template>
	<div class="app-container flex-direction-column">
		<el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="80px" v-show="showSearch">
			<el-form-item label="消息类型:" prop="typeName">
				<input-select ref="typeName1" :selectData="selectData" @change="currencyDataChange"></input-select>
			</el-form-item>
			<el-form-item label="输入码:" prop="inputcode">
				<el-input v-model="queryParams.inputcode" placeholder="请输入输入码" clearable style="width: 160px;" @keyup.enter.native="handleQuery" />
			</el-form-item>
			<el-form-item>
				<el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
				<el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
			</el-form-item>
		</el-form>
		<!-- 操作按钮 -->
		<el-row :gutter="10" class="mb8">
			<el-col :span="1.5">
				<el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['basis:smsTemplate:add']">添加</el-button>
			</el-col>
			<el-col :span="1.5">
				<el-button type="success" plain size="mini" icon="el-icon-edit" @click="handleUpdate" :disabled="single" v-hasPermi="['basis:smsTemplate:edit']">编辑
				</el-button>
			</el-col>
			<el-col :span="1.5">
				<el-button type="danger" plain size="mini" icon="el-icon-delete" @click="handleDelete" :disabled="multiple" v-hasPermi="['basis:smsTemplate:remove']">删除
				</el-button>
			</el-col>
			<right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
		</el-row>
		<!-- 表格 -->
		<div class="table-box">
			<el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
				<el-table-column fixed type="selection" align="center" />
				<el-table-column label="序列" fixed type="index" align="center" />
				<el-table-column label="短消息类型" fixed prop="messageType" align="center" show-overflow-tooltip>
					<template slot-scope="scope">
						<div v-for="item in messageTypeOptions" :key="item.id">
							<span v-if="scope.row.messageType == item.id">{{ item.typeName }}</span>
						</div>
					</template>
				</el-table-column>
				<el-table-column label="短消息名称" fixed prop="messageName" align="center" show-overflow-tooltip />
				<el-table-column label="输入码" prop="inputcode" align="center" show-overflow-tooltip />
				<el-table-column label="模板ID" prop="templateId" align="center" show-overflow-tooltip />
				<el-table-column label="APPID" prop="appid" align="center" show-overflow-tooltip />
				<el-table-column label="参数" prop="params" align="center" show-overflow-tooltip />
				<el-table-column label="短消息正文" prop="messageText" align="center" show-overflow-tooltip />
				<el-table-column label="操作" fixed="right" align="center" class-name="small-padding fixed-width">
					<template slot-scope="scope">
						<el-button size="mini" type="text" style="color: #ffba00" @click="handleUpdate(scope.row)" v-hasPermi="['basis:smsTemplate:edit']">编辑</el-button>
						<el-button size="mini" type="text" style="color: #ff2727" @click="handleDelete(scope.row)" v-hasPermi="['basis:smsTemplate:remove']">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>
		<!-- 分页 -->
		<pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
		<!-- 添加或修改对话框 -->
		<add-items ref="addItems" @getList="getList"></add-items>
	</div>
</template>
<script>
import { listshortmessage, delshortmessage, getMessageTypeData } from "@/api/basis/smstemplate";
import addItems from './add'
export default {
	name: 'Sms_template',
	components: { addItems },
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
			selectData: {
				placeholder: '请输入输入码选择',
				key: '输入码',//第一列标题
				value: '职业病分类名称',//第二列标题
				url: '/shortmessage/findAllTypeName',//请求连接
				firstName: 'inputcode',
				secondName: 'typeName',
				bindValue: '',
			},
			// 表格展示数据
			tableList: [],
			// 显示模态框
			showExam: false,
			showUpload: false,
			messageTypeOptions: []
		};
	},
	computed: {},
	watch: {},
	created() {
		this.getBasic();
		this.getList();
	},
	mounted() { },
	methods: {
		//获取短信数据类型
		getBasic() {
			getMessageTypeData().then((res) => {
				this.messageTypeOptions = res.data.records;
			})
		},
		// 查询分类列表
		getList() {
			this.loading = true;
			listshortmessage(this.queryParams).then(({ data }) => {
				this.tableList = data.records
				this.total = data.total
				this.loading = false;
			})
		},
		// 搜索
		handleQuery() {
			this.queryParams.current = 1;
			this.getList();
		},
		// 重置
		resetQuery() {
			this.resetForm("queryForm");
			this.$refs.typeName1.initData();
			this.handleQuery();
		},
		// 多选框选中数据
		handleSelectionChange(selection) {
			this.ids = selection.map((item) => item.id);
			this.single = selection.length != 1;
			this.multiple = !selection.length;
		},
		// 选择类型
		currencyDataChange(value) {
			this.queryParams.inputcode = value.inputCode
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
					return delshortmessage(ids);
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
			this.$refs.addItems.handleUpdate(row, this.ids)
		},

	},
};
</script>
