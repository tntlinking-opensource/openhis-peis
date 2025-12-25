<!-- 迟补检回访 开发人：麦沃德科技暴雨 -->
<template>
	<div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 120px;"
                  @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 180px;"
                  @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="登记时间">
        <el-date-picker v-model="queryParams.djdate" style="width: 350px;" value-format="yyyy-MM-dd" type="daterange"
                        range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="queryParams.phone" placeholder="请输入电话" clearable style="width: 180px;"
                  @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="公司" prop="orgName">
        <el-input v-model="queryParams.orgName" placeholder="请输入公司" clearable style="width: 180px;"
                  @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择类型" clearable style="width: 180px;">
          <el-option label="迟检" :value="0" />
          <el-option label="补检" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="处理结果" prop="sflj">
        <el-select v-model="queryParams.sflj" placeholder="请选择处理结果" clearable style="width: 180px;" multiple>
          <el-option label="看备注" :value="0" />
          <el-option label="弃检" :value="1" />
          <el-option label="补检" :value="2" />
          <el-option label="已电话通知" :value="3" />
          <el-option label="迟检来检" :value="4" />
          <el-option label="补检来检" :value="5" />
          <el-option label="再通知" :value="6" />
        </el-select>
      </el-form-item>
      <el-form-item label="处理日期">
        <el-date-picker v-model="queryParams.cldate" style="width: 350px;" value-format="yyyy-MM-dd" type="daterange"
                        range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="预处理时间">
        <el-date-picker v-model="queryParams.ycldate" style="width: 350px;" value-format="yyyy-MM-dd" type="daterange"
                        range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="项目名称" prop="itemName">
        <el-input v-model="queryParams.itemName" placeholder="请输入项目名称" clearable style="width: 180px;"
                  @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检分类" prop="useCodeHiden">
        <el-select v-model="queryParams.useCodeHiden" placeholder="请选择体检分类" clearable style="width: 180px;">
          <el-option label="个检" :value="0" />
          <el-option label="团检" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="分中心" prop="fzx">
        <el-select v-model="queryParams.fzx" placeholder="请选择分中心" clearable style="width: 160px" @change="handleQuery">
          <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="xsjlid" label="销售经理">
        <input-select ref="xsjlid" :selectData="selectData2" @change="selectChange2" style="width: 160px"></input-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-coin" @click="handleAdd"
                   v-hasPermi="['ustservice:customerservice:forinspectionView:add']">迟补检处理
        </el-button>
      </el-col>
      <el-col :span="1">
        <el-button type="warning" plain size="mini" icon="el-icon-top" @click="handleExport"
                   v-hasPermi="['ustservice:customerservice:forinspectionView:export']">导出Excel
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
		<!-- 表格 -->
		<div class="table-box" ref="tableBox">
			<el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
				<el-table-column fixed type="selection" align="center" />
				<el-table-column label="序列"  type="index"  min-width="100px" align="center" />
				<el-table-column label="处理结果"  prop="sflj"  min-width="100px" align="center" show-overflow-tooltip >
          <template slot-scope="scope">
            <div v-if="scope.row.sflj == 0">弃检</div>
            <div v-if="scope.row.sflj == 1">迟检来检</div>
            <div v-if="scope.row.sflj == 2">补检</div>
            <div v-if="scope.row.sflj == 3">看备注</div>
            <div v-if="scope.row.sflj == 4">已电话通知</div>
            <div v-if="scope.row.sflj == 5">补检来检</div>
            <div v-if="scope.row.sflj == 6">再通知</div>
          </template>
        </el-table-column>
				<el-table-column label="体检类型"  prop="tjlx" min-width="100px" align="center" show-overflow-tooltip >
          <template slot-scope="scope">
            <div v-if="scope.row.tjlx == 0">健康</div>
            <div v-if="scope.row.tjlx == 1">职业</div>
            <div v-if="scope.row.tjlx == 2">综合</div>
            <div v-if="scope.row.tjlx == 3">复查</div>
          </template>
        </el-table-column>
				<el-table-column label="预约来检时间" prop="ljsj"  min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="登记时间"  prop="dateregister" min-width="160px"  align="center" show-overflow-tooltip />
        <el-table-column label="体检号"  prop="patientcode" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" min-width="100px"  align="center" show-overflow-tooltip />
        <el-table-column label="性别" prop="idSex" min-width="100px"  align="center" show-overflow-tooltip >
          <template slot-scope="scope">
            <div v-if="scope.row.idSex == 0">男</div>
            <div v-if="scope.row.idSex == 1">女</div>
          </template>
        </el-table-column>
        <el-table-column label="年龄"  prop="age" min-width="100px"  align="center" show-overflow-tooltip />
        <el-table-column label="类型"  prop="type" min-width="100px" align="center" show-overflow-tooltip >
          <template slot-scope="scope">
            <div v-if="scope.row.type == 0">迟检</div>
            <div v-if="scope.row.type == 4">补检</div>
          </template>
        </el-table-column>
        <el-table-column label="开单医生" prop="doctorapply" min-width="100px"  align="center" show-overflow-tooltip />
        <el-table-column label="公司" prop="orgName" min-width="180px"  align="center" show-overflow-tooltip />
        <el-table-column label="部门"  prop="orgDepart" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="电话" prop="phone" min-width="180px"  align="center" show-overflow-tooltip />
        <el-table-column label="项目名称" prop="examfeeitemName"  min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="处理人"  prop="visitId"  min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="处理时间" prop="visitTime" min-width="160px"  align="center" show-overflow-tooltip />
        <el-table-column label="处理备注" prop="memo" min-width="160px"  align="center" show-overflow-tooltip />
        <el-table-column label="预处理时间" prop="preTime"  min-width="160px" align="center" show-overflow-tooltip />
			</el-table>
		</div>
		<!-- 分页 -->
		<pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
			@pagination="getList" />
		<!-- 添加或修改对话框 -->
		<add-items ref="addItems" @getlist="getList"></add-items>
	</div>
</template>
<script>
import { getListData } from "@/api/custservice/customerservice/forinspection_view/forinspection_view";
import { getBranchData } from '@/api/statistical/professionchecks/conclusion_table.js' //获取分中心
import { getUserCid } from '@/api/system/branch.js'
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
        fzx:undefined,
        xsjlid:undefined
			},
			// 表格展示数据
			tableList: [],
			// 显示模态框
			showExam: false,
			showUpload: false,
      // 分中心列表
      fzxOptions: [],
      // 是否为线上
      isOnline: false,
      // 销售经理
      selectData2: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '销售经理', //第二列标题
        url: 'sell/customerTransfer/getXsryData', //请求连接
        secondName: 'username', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
		};
	},
	computed: {},
	watch: {},
	created() {
		this.getList();
    this.isOnline = this.$getCookie('isOnline') == '1' ? true : false
    if (this.isOnline) {
      getBranchData().then((res) => {
        this.fzxOptions = res.data
      })
    } else {
      getUserCid().then(({ data }) => {
        this.fzxOptions = data
      })
    }
	},
	mounted() {},
	methods: {
     // 检查结论选择结果
     selectChange2(value) {
      this.queryParams.xsjlid = value.id
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
			getListData(this.queryParams).then(response => {
				this.tableList = response.data.records;
				this.total = response.data.total;
			  this.loading = false;
			});
		},
		// 搜索
		handleQuery() {
			this.queryParams.current = 1;
      if (this.queryParams.djdate) {
        this.queryParams.regStartTime = this.queryParams.djdate[0] + " 00:00:00"
        this.queryParams.regEndTime = this.queryParams.djdate[1] + " 23:59:59"
      } else{
        this.queryParams.regStartTime = undefined
        this.queryParams.regEndTime = undefined
      }
      if(this.queryParams.cldate){
        this.queryParams.visitStartTime = this.queryParams.cldate[0] + " 00:00:00"
        this.queryParams.visitEndTime = this.queryParams.cldate[1] + " 23:59:59"
      }else{
        this.queryParams.visitStartTime = undefined
        this.queryParams.visitEndTime = undefined
      }
      if(this.queryParams.ycldate) {
        this.queryParams.preStartTime = this.queryParams.ycldate[0] + " 00:00:00"
        this.queryParams.preEndTime = this.queryParams.ycldate[1] + " 23:59:59"
      }else{
        this.queryParams.preStartTime = undefined
        this.queryParams.preEndTime = undefined
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
      if(this.rows != undefined){
        if(this.rows.length > 1 || undefined){
          this.$alert("请选择一条记录修改!", "提醒", {
            confirmButtonText: "确定",
            type: "warning",
          })
        }else if(this.rows.length == 1){
          if(this.rows[0].sflj == 0){
            this.$alert("该条记录已弃检处理，无法再次处理!", "提醒", {
              confirmButtonText: "确定",
              type: "warning",
            })
          }else if(this.rows[0].sflj == 2){
            this.$alert("该条记录已补检处理，无法再次处理!", "提醒", {
              confirmButtonText: "确定",
              type: "warning",
            })
          }else{
            this.$refs.addItems.handleAdd(this.ids)
          }
        }else{
          this.$alert("请选择一条记录后操作!", "提醒", {
            confirmButtonText: "确定",
            type: "warning",
          })
        }
      }else{
        this.$alert("请选择一条记录后操作!", "提醒", {
          confirmButtonText: "确定",
          type: "warning",
        })
      }
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
      this.download('/member/forinspectionView/export', {
        ...this.queryParams
      }, `迟补检回访.xlsx`)
    },
	},
};
</script>
