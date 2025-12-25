<!-- 售药统计 开发人：麦沃德科技暴雨/矢北 -->
<template>
	<div class="app-container flex-direction-column">
    <!--页面-->
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
          <el-form-item label="姓名" prop="patientname">
            <el-input v-model="queryParams.patientname" placeholder="请输入姓名  " clearable style="width: 180px;"
                      @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="开药医生" prop="username">
            <input-select :selectData="usernamData" selectWidth="160" @change="usernamDataChange"></input-select>
          </el-form-item>
          <el-form-item label="开药日期">
            <el-date-picker v-model="queryParams.date" style="width: 350px;" value-format="yyyy-MM-dd" type="daterange"
                            range-separator ="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="药品" prop="drugId">
            <input-select :selectData="drugIdData" selectWidth="160" @change="drugIdDataChange"></input-select>
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="queryParams.phone" placeholder="请输入手机号" clearable style="width: 180px;"
                      @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="状态" prop="isFinished">
            <el-select v-model="queryParams.isFinished" placeholder="请选择" clearable style="width: 180px;">
              <el-option label="待处理" :value="0" />
              <el-option label="未成交" :value="1" />
              <el-option label="成交" :value="2" />
              <el-option label="退药" :value="2" />
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
                   v-hasPermi="['funcdept:drugstore:drugCensus:editRow']">导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
        <!-- 表格 -->
        <div class="table-box" ref="tableBox">
          <el-table border v-loading="loading" :data="tableList" height="100%" stripe >
            <el-table-column label="开药医生"  prop="username"   align="center" show-overflow-tooltip />
            <el-table-column label="体检号"  prop="patientcode"  align="center" show-overflow-tooltip />
            <el-table-column label="姓名" prop="patientname"  align="center" show-overflow-tooltip />
            <el-table-column label="手机号"  prop="phone"  align="center" show-overflow-tooltip />
            <el-table-column label="药品"  prop="drugName"   align="center" show-overflow-tooltip />
            <el-table-column label="开药时间"  prop="prescribeTime"  align="center" show-overflow-tooltip />
            <el-table-column label="成本价" prop="costPrice"  align="center" show-overflow-tooltip />
            <el-table-column label="成交价"  prop="price"  align="center" show-overflow-tooltip />
            <el-table-column label="状态"  prop="isFinished"   align="center" show-overflow-tooltip >
              <template slot-scope="scope">
                <el-tag v-if="scope.row.finish == 0">成交</el-tag>
                <el-tag v-if="scope.row.finish == 0">未成交</el-tag>
                <el-tag v-if="scope.row.finish == 0">退药</el-tag>
                <el-tag v-else>待处理</el-tag>
              </template>
            </el-table-column>
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
 import { getListData, } from "@/api/funcdept/pharmacy/medicine_census";
import addItems from './add'
export default {
  
	components: { addItems },
	props: [],
	data() {
		return {
      usernamData:{
        placeholder: '请输入输入码选择',
        key: '输入码',//第一列标题
        value: '名称',//第二列标题
        url: '',//请求连接
        bindValue: '',
        queryData: 'key',
      },
      drugIdData :{
        placeholder: '请输入输入码选择',
        key: '输入码',//第一列标题
        value: '名称',//第二列标题
        url: '',//请求连接
        bindValue: '',
        queryData: 'key',
      },
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
        djdate: undefined,
        cldate: undefined,
        ycldate: undefined,
			},
			// 表格展示数据
			tableList: [
				
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
    usernamDataChange(value) {
      this.queryParams.idLabtype = value
      this.usernamData.bindValue = value
    },
    drugIdDataChange(value) {
      this.queryParams.idLabtype = value
      this. drugIdData.bindValue = value
    },
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
      getListData(this.queryParams).then(response => {
        
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
      this.download('/drugstore/statistics/export', {
        ...this.queryParams
      }, `售药统计_${new Date().getTime()}.xlsx`)
    },
	},
};
</script>
