<!-- 预约短信回访 开发人：麦沃德科技暴雨 -->
<template>
	<div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="预约时间">
        <el-date-picker v-model="queryParams.date" style="width: 350px;" value-format="yyyy-MM-dd" type="daterange"
                        range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="通知状态" prop="notifyResult">
        <el-select v-model="queryParams.notifyResult" placeholder="请选择" clearable style="width: 180px;">
          <el-option label="未通知" :value="0" />
          <el-option label="已通知" :value="1" />
          <el-option label="等待通知" :value="2" />
          <el-option label="通知失败" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="体检分类" prop="useCodeHiden">
        <el-select v-model="queryParams.useCodeHiden" placeholder="请选择" clearable style="width: 180px;">
          <el-option label="个检" :value="0" />
          <el-option label="团检" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="5" class="mb8">
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit-outline" @click="handleAdd" :disabled="multiple"
                   v-hasPermi="['custservice:customerservice:recordManage:add']">发送编辑
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" @click="handlecancel" :disabled="multiple"
                   v-hasPermi="['custservice:customerservice:recordManage:cancel']">取消发送
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleView" :disabled="single"
                   v-hasPermi="['custservice:customerservice:recordManage:View']">查看短信
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
		<!-- 表格 -->
		<div class="table-box" ref="tableBox">
			<el-table border v-loading="loading" :data="tableList" height="100%" stripe  @selection-change="handleSelectionChange" >
				<el-table-column fixed type="selection" align="center" />
				<el-table-column label="序列"  type="index"   align="center" />
				<el-table-column label="预约时间"  prop="dateguidancereturned"   align="center" show-overflow-tooltip />
				<el-table-column label="体检号"  prop="patientcode"  align="center" show-overflow-tooltip />
				<el-table-column label="姓名" prop="patientname"   align="center" show-overflow-tooltip />
        <el-table-column label="性别"  prop="sex"   align="center" show-overflow-tooltip />
        <el-table-column label="年龄"  prop="age"  align="center" show-overflow-tooltip />
        <el-table-column label="手机号码" prop="phone"   align="center" show-overflow-tooltip />
        <el-table-column label="团体名称" prop="orgName"   align="center" show-overflow-tooltip />
        <el-table-column label="通知状态"  prop="isNoticed"   align="center" show-overflow-tooltip >
          <template slot-scope="scope">
            <el-tag v-if="scope.row.isNoticed == 1">已通知</el-tag>
            <el-tag v-else>未通知</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作人"  prop="creater"  align="center" show-overflow-tooltip />
        <el-table-column label="短信状态" prop="notifyResult"   align="center" show-overflow-tooltip >
          <template slot-scope="scope">
            <el-tag v-if="scope.row.notifyResult == 0">通知失败</el-tag>
            <el-tag v-if="scope.row.notifyResult == 1">已取消</el-tag>
            <el-tag v-if="scope.row.notifyResult == 2">等待通知</el-tag>
            <el-tag v-if="scope.row.notifyResult == 3" type="danger">已通知</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="通知时间" prop="notifyTime"   align="center" show-overflow-tooltip />
			</el-table>
		</div>
		<!-- 分页 -->
		<pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
			@pagination="getList" />
		<!-- 添加或修改对话框 -->
		<add-items ref="addItems"></add-items>
    <!-- 检查项目对话框 -->
    <el-dialog title="查看短信" :visible.sync="showExam" width="1000px" append-to-body>
      <div style="min-height: 400px;">
        <el-table border v-loading="loading" :data="examList" stripe >
          <el-table-column label="序列" type="index" width="65" align="center" />
          <el-table-column label="短信内容" prop="notifyContent" align="center" show-overflow-tooltip />
          <el-table-column label="发送时间" prop="notifyTime"  align="center" show-overflow-tooltip />
          <el-table-column label="发送状态" prop="notifyResult"  align="center" show-overflow-tooltip >
            <template slot-scope="scope">
              <el-tag v-if="scope.row.finish == 0">已发送</el-tag>
              <el-tag type="danger" v-else>未发送</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作人" prop="creater"  align="center" show-overflow-tooltip />
        </el-table>
      </div>
    </el-dialog>
	</div>
</template>
<script>
import { cancleSMS, listPrinttype ,getSmsData } from '@/api/custservice/customerservice/record_manage/record_manage.js'
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
        djdate: undefined,
        cldate: undefined,
        ycldate: undefined,
        dateguidancereturned:undefined,
        notifyResult:undefined,
        useCodeHiden:undefined,
			},
      // 检查项目列表
      examList: [],
			// 表格展示数据
			tableList: [],
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
    // 查询列表
    getList() {
      this.loading = true;
      listPrinttype(this.queryParams).then(response => {
        this.tableList = response.data.records;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    // 取消发送
    handlecancel() {
      this.$confirm('确定要取消发送吗?', '取消发送提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let parameter = {
          patientcode:this.patientcode.toString()
        }
        cancleSMS(parameter).then(response => {
          this.$modal.msgSuccess("取消发送成功");
        });
      }).catch(() => { });
    },
    // 查看短信数据
    handleView() {
      if(this.rows == undefined){
        this.$alert("请选择一条或多条记录!", "提醒", {
          confirmButtonText: "确定",
          type: "warning",
        })
      }else{
        let parameter = {
          patientcode:this.patientcode.toString(),
          size:999
        }
        getSmsData(parameter).then(response => {
          this.examList = response.data.records;
        });
        this.showExam = true
      }

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
		// 添加
		handleAdd() {
        this.$refs.addItems.handleAdd(this.patientcode)
		},
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.patientcode = selection.map((item) => item.patientcode);
      this.rows = selection;
      this.single = selection.length != 1;
      this.multiple = !selection.length;
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
