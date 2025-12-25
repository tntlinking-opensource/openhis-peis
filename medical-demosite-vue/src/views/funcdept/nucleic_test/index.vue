<!-- 核酸检测 开发人：麦沃德科技暴雨 -->
<template>
	<div class="app-container flex-direction-column">
    <!--页面-->
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
          <el-form-item label="登记时间">
            <el-date-picker v-model="queryParams.date" style="width: 350px;" value-format="yyyy-MM-dd" type="daterange"
                            range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="体检号" prop="patientcode">
            <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 180px;"
                      @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="姓名" prop="patientname">
          <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 180px;"
                    @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="电话" prop="phone">
            <el-input v-model="queryParams.phone" placeholder="请输入电话" clearable style="width: 180px;"
                      @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="身份证号" prop="idcardno">
          <el-input v-model="queryParams.idcardno" placeholder="请输入身份证号" clearable style="width: 180px;"
                    @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="订单号" prop="ddh">
            <el-input v-model="queryParams.ddh" placeholder="请输入订单号" clearable style="width: 180px;"
                      @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="报告状态" prop="type">
            <el-select v-model="queryParams.type" placeholder="请选择" clearable style="width: 180px;">
              <el-option label="未上传" :value="0" />
              <el-option label="已上传" :value="1" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
          </el-form-item>
      </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="handleExport"
                   v-hasPermi="['funcdept:nucleicTest:export']">导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-upload2" @click="handleeditRow"
                   v-hasPermi="['funcdept:nucleicTest:editRow']">上传
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
        <!-- 表格 -->
        <div class="table-box" ref="tableBox">
          <el-table border v-loading="loading" :data="tableList" height="100%" stripe >
            <el-table-column fixed type="selection" align="center" />
            <el-table-column label="序列"  type="index"   align="center" />
            <el-table-column label="登记时间"  prop="dateregister" min-width="120px" align="center" show-overflow-tooltip />
            <el-table-column label="开单医生"  prop="doctorapply" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="登记员" prop="doctorreg" min-width="100px"  align="center" show-overflow-tooltip />
            <el-table-column label="订单号"  prop="orgnumresv" min-width="160px" align="center" show-overflow-tooltip />
            <el-table-column label="单位名称"  prop="orgName" min-width="120px"   align="center" show-overflow-tooltip />
            <el-table-column label="部门"  prop="orgDepart" min-width="120px" align="center" show-overflow-tooltip />
            <el-table-column label="体检号" prop="patientcode" min-width="120px" align="center" show-overflow-tooltip />
            <el-table-column label="姓名"  prop="patientname" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="性别"  prop="idSex" min-width="100px"  align="center" show-overflow-tooltip />
            <el-table-column label="年龄"  prop="age" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="电话" prop="phone" min-width="120px" align="center" show-overflow-tooltip />
            <el-table-column label="生日"  prop="birthdate" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="身份证号"  prop="idcardno" min-width="160px" align="center" show-overflow-tooltip />
            <el-table-column label="上传时间" prop="uploadDate" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="上传人"  prop="uploader" min-width="100px" align="center" show-overflow-tooltip />
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
        djdate: undefined,
        cldate: undefined,
        ycldate: undefined,
			},
			// 表格展示数据
			tableList: [
				{
          "dateregister": "2022-10-31",
          "doctorapply": "王医生",
          "doctorreg": "张三",
          "orgnumresv": "13696874545",
          "orgName": "单位名称",
          "orgDepart": "部门名称",
          "patientcode": "123123",
          "patientname": "张三",
          "idSex": "男",
          "age": "25",
          "phone": "13867678888",
          "birthdate": "2022-11-16",
          "idcardno": "370284200204478546",
          "uploadDate": "2022-11-16",
          "uploader": "李四",
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
