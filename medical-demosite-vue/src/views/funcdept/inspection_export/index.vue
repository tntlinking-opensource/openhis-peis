<!-- 检验结果导出 开发人：麦沃德科技暴雨 -->
<template>
	<div class="app-container flex-direction-column">
    <!--页面-->
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
          <el-form-item label="订单号" prop="ddh">
          <el-input v-model="queryParams.ddh" placeholder="请输入订单号" clearable style="width: 160px;"
                    @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="体检号" prop="patientcode">
            <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 160px;"
                      @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="开始时间">
            <el-date-picker 
              v-model="startTimeRange" 
              style="width: 300px;" 
              value-format="yyyy-MM-dd" 
              type="daterange"
              range-separator="至" 
              start-placeholder="开始日期" 
              end-placeholder="结束日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="检验时间">
            <el-date-picker 
              v-model="examDateRange" 
              style="width: 300px;" 
              value-format="yyyy-MM-dd" 
              type="daterange"
              range-separator="至" 
              start-placeholder="开始日期" 
              end-placeholder="结束日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="handleExport"
                       v-hasPermi="['funcdept:inspectionExport:export']">导出
            </el-button>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
      </el-form>
        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="handleExport"
                       v-hasPermi="['funcdept:inspectionExport:export']">导出
            </el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
        <!-- 表格 -->
        <div class="table-box" ref="tableBox">
          <el-table border v-loading="loading" :data="tableList" height="100%" stripe >
            <el-table-column label="序列" type="index" align="center" />
            <el-table-column label="体检号" prop="patientcode" align="center" show-overflow-tooltip />
            <el-table-column label="姓名" prop="patientname" align="center" show-overflow-tooltip />
            <el-table-column label="单位" prop="orgName" align="center" show-overflow-tooltip />
            <el-table-column label="订单号" prop="ddh" align="center" show-overflow-tooltip />
            <el-table-column label="检验项目" prop="examitemName" align="center" show-overflow-tooltip />
            <el-table-column label="正常" prop="status" align="center" show-overflow-tooltip >
              <template slot-scope="scope">
                <el-tag v-if="scope.row.status == 0">异常</el-tag>
                <el-tag v-else>正常</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="单位" prop="units" align="center" show-overflow-tooltip />
            <el-table-column label="LIS范围" prop="refrange" align="center" show-overflow-tooltip />
            <el-table-column label="检验师" prop="inspectName" align="center" show-overflow-tooltip />
            <el-table-column label="审核医师" prop="auditName" align="center" show-overflow-tooltip />
            <el-table-column label="检验时间" prop="examDateTime" align="center" show-overflow-tooltip />
            <el-table-column label="登记时间" prop="dateregister" align="center" show-overflow-tooltip />
          </el-table>
        </div>
		<!-- 分页 -->
		<pagination 
      v-show="total > 0" 
      :total="total" 
      :page.sync="queryParams.current" 
      :limit.sync="queryParams.size"
		  @pagination="getList" />

	</div>
</template>

<script>
import { getListData } from "@/api/funcdept/inspection_export/inspection_export";

// 获取今天日期
function getToday() {
  const today = new Date();
  const year = today.getFullYear();
  const month = String(today.getMonth() + 1).padStart(2, '0');
  const day = String(today.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
}

export default {
	components: {},
	props: [],
	data() {
		const today = getToday();
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
      // 开始时间范围，默认选中今天
      startTimeRange: [today, today],
      // 检验时间范围
      examDateRange: [],
			// 查询参数
			queryParams: {
				current: 1,
				size: 10,
				ddh: undefined,
				patientcode: undefined,
        startTime: today + ' 00:00:00',
        endTime: today + ' 23:59:59'
			},
			// 表格展示数据
			tableList: [],
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
      
      // 处理时间参数
      const params = {...this.queryParams};
      
      // 处理开始时间，添加时间后缀
      if (this.startTimeRange && this.startTimeRange.length === 2) {
        params.startTime = this.startTimeRange[0] + ' 00:00:00';
        params.endTime = this.startTimeRange[1] + ' 23:59:59';
      }
      
      // 处理检验时间，添加时间后缀
      if (this.examDateRange && this.examDateRange.length === 2) {
        params.startExamTime = this.examDateRange[0] + ' 00:00:00';
        params.endExamTime = this.examDateRange[1] + ' 23:59:59';
      }
      
			getListData(params).then(response => {
				this.tableList = response.data.records;
				this.total = response.data.total;
        this.loading = false;
			}).catch(() => {
        this.loading = false;
      });
		},
    
		// 搜索
		handleQuery() {
			this.queryParams.current = 1;
      // 更新 queryParams 中的时间参数
      if (this.startTimeRange && this.startTimeRange.length === 2) {
        this.queryParams.startTime = this.startTimeRange[0] + ' 00:00:00';
        this.queryParams.endTime = this.startTimeRange[1] + ' 23:59:59';
      } else {
        // 如果没有选择时间范围，清除时间参数
        delete this.queryParams.startTime;
        delete this.queryParams.endTime;
      }
      
      // 更新 queryParams 中的检验时间参数
      if (this.examDateRange && this.examDateRange.length === 2) {
        this.queryParams.startExamTime = this.examDateRange[0] + ' 00:00:00';
        this.queryParams.endExamTime = this.examDateRange[1] + ' 23:59:59';
      } else {
        // 如果没有选择检验时间范围，清除时间参数
        delete this.queryParams.startExamTime;
        delete this.queryParams.endExamTime;
      }
      
			this.getList();
		},
    
		// 重置
		resetQuery() {
			this.resetForm("queryForm");
      // 重置开始时间为今天
      const today = getToday();
      this.startTimeRange = [today, today];
      this.examDateRange = [];
      this.queryParams = {
        current: 1,
        size: 10,
        ddh: undefined,
        patientcode: undefined,
        startTime: today + ' 00:00:00',
        endTime: today + ' 23:59:59'
      };
			this.handleQuery();
		},
    
    
    /** 导出按钮操作 */
    handleExport() {
      // 确保导出时包含最新的时间参数
      const exportParams = {...this.queryParams};
      
      // 处理开始时间参数
      if (this.startTimeRange && this.startTimeRange.length === 2) {
        exportParams.startTime = this.startTimeRange[0] + ' 00:00:00';
        exportParams.endTime = this.startTimeRange[1] + ' 23:59:59';
      }
      
      // 处理检验时间参数
      if (this.examDateRange && this.examDateRange.length === 2) {
        exportParams.startExamTime = this.examDateRange[0] + ' 00:00:00';
        exportParams.endExamTime = this.examDateRange[1] + ' 23:59:59';
      }
      
      this.download('abteilung/inspectionExport/export', exportParams, `检验结果.xlsx`)
    },
	},
};
</script>