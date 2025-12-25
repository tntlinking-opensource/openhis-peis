<!-- 外送结果录入 开发人：麦沃德科技暴雨/矢北 -->
<template>
	<div class="app-container flex-direction-column">
    <!--页面-->
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
          <el-form-item label="登记时间">
            <el-date-picker v-model="queryParams.date" style="width: 300px;" value-format="yyyy-MM-dd" type="daterange"
                            range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="获取时间">
            <el-date-picker v-model="queryParams.date" style="width: 300px;" value-format="yyyy-MM-dd" type="daterange"
                            range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="检查项目" prop="examId">
            <el-input v-model="queryParams.examId" placeholder="请输入体检号" clearable style="width: 150px;"
                      @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="姓名" prop="patientname">
            <el-input v-model="queryParams.patientname" placeholder="请输入体检号" clearable style="width: 150px;"
                      @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="体检号" prop="patientcode">
            <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 150px;"
                      @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
          </el-form-item>
      </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd"
                   v-hasPermi="['funcdept:deliveryManage:deliveryEnter:add']">重做
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
        <!-- 表格 -->
        <div class="table-box" ref="tableBox">
          <el-table border v-loading="loading" :data="tableList" height="100%" stripe >
            <el-table-column fixed type="selection" align="center" />
            <el-table-column label="序列"  type="index"   align="center" />
            <el-table-column label="体检号"  prop="patientcode"  align="center" show-overflow-tooltip />
            <el-table-column label="姓名"  prop="patientname"  align="center" show-overflow-tooltip />
            <el-table-column label="性别" prop="idSex"   align="center" show-overflow-tooltip />
            <el-table-column label="检查项目"  prop="examName"  align="center" show-overflow-tooltip />
            <el-table-column label="结果"  prop="examItemValuesReport"    align="center" show-overflow-tooltip />
            <el-table-column label="参考值"  prop="refRange"  align="center" show-overflow-tooltip />
            <el-table-column label="审核时间" prop="auditDate"  align="center" show-overflow-tooltip />
            <el-table-column label="审核人"  prop="auditName"  align="center" show-overflow-tooltip />
            <el-table-column label="是否重做" prop="examItemValuesNumber2"  align="center" show-overflow-tooltip />
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
			total: 10,
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
        djdate: undefined,
        cldate: undefined,
        ycldate: undefined,
			},
			// 表格展示数据
			tableList: [
				{
          "patientcode": "2312",
          "patientname": "李四",
          "idSex": "男",
          "examName": "胃镜",
          "examItemValuesReport": "健康",
          "refRange": "213",
          "auditDate": "2022-10-31",
          "auditName": "外送机构",
          "examItemValuesNumber2": "否",
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
    // 处理
    handleeditRow() {
      this.$modal
        .confirm('确定要处理？')
        .then(function () {
          // return delPrinttype(ids);
          return;
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("处理成功");
        })
        .catch(() => { });
    },
    // 反处理
    handlenoeditRow() {
      this.$modal
        .confirm('确定要反处理？')
        .then(function () {
          // return delPrinttype(ids);
          return;
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("反处理成功");
        })
        .catch(() => { });
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
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/user/export', {
        ...this.queryParams
      }, `user_${new Date().getTime()}.xlsx`)
    },
	},
};
</script>
