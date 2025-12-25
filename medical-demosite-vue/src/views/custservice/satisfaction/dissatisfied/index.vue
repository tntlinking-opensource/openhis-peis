<!-- 不满意客户深访  开发人：麦沃德科技半夏/矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 230px;"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检号" prop="personcode">
        <el-input v-model="queryParams.personcode" placeholder="请输入体检号" clearable style="width: 230px;"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="评价结果" prop="appraiseResult">
        <el-select v-model="queryParams.appraiseResult" placeholder="请选择评价结果" clearable style="width: 230px;">
          <el-option v-for="options in resultOptions" :key="options.id" :label="options.text" :value="options.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="回访结果" prop="visitResult">
        <el-select v-model="queryParams.visitResult" placeholder="请选择回访结果" clearable style="width: 230px;">
          <el-option v-for="options in resultOptions" :key="options.id" :label="options.text" :value="options.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="登记时间">
        <el-date-picker v-model="queryParams.date" style="width: 360px;" value-format="yyyy-MM-dd" type="daterange"
          range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-bottom-left" :disabled="single" @click="handleVisit"
          v-hasPermi="['custservice:satisfaction:dissatisfied:visit']">高级客户回访</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-help" :disabled="single" @click="handleView"
          v-hasPermi="['custservice:satisfaction:dissatisfied:view']">查看非常满意
        </el-button>
      </el-col>
  
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="handleDownload"
          v-hasPermi="['custservice:satisfaction:dissatisfied:export']">导出
        </el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe
        @selection-change="handleSelectionChange">
        <el-table-column fixed type="selection" width="55" align="center" />
        <el-table-column fixed label="序列" width="55" type="index" align="center" />
        <el-table-column fixed label="评价结果" prop="appraiseResult" min-width="120" align="center"
          show-overflow-tooltip >
          <template slot-scope="scope">
						<span v-if="scope.row.appraiseResult == 0" >未评价</span>
            <span v-else-if="scope.row.appraiseResult == 1" >非常满意</span>
            <span v-else-if="scope.row.appraiseResult == 2" >满意</span>
						<span v-else-if="scope.row.appraiseResult == 3" >基本满意</span>
            <span v-else-if="scope.row.appraiseResult == 4" >不满意</span>
						<span v-else >未评价</span>
					</template> 
          </el-table-column>
        <el-table-column fixed label="回访结果1" prop="visitResult" min-width="120" align="center"
          show-overflow-tooltip >
          <template slot-scope="scope">
						<span v-if="scope.row.visitResult == 0" >未评价</span>
            <span v-else-if="scope.row.visitResult == 1" >非常满意</span>
            <span v-else-if="scope.row.visitResult == 2" >满意</span>
						<span v-else-if="scope.row.visitResult == 3" >基本满意</span>
            <span v-else-if="scope.row.visitResult == 4" >不满意</span>
						<span v-else >未评价</span>
					</template> 
          </el-table-column>
        <el-table-column fixed label="回访结果2" prop="secondResult" min-width="120" align="center"
          show-overflow-tooltip >
          <template slot-scope="scope">
						<span v-if="scope.row.secondResult == 0" >未评价</span>
            <span v-else-if="scope.row.secondResult == 1" >非常满意</span>
            <span v-else-if="scope.row.secondResult == 2" >满意</span>
						<span v-else-if="scope.row.secondResult == 3" >基本满意</span>
            <span v-else-if="scope.row.secondResult == 4" >不满意</span>
						<span v-else >未评价</span>
					</template> 
          </el-table-column>
        <el-table-column label="体检号" prop="personcode" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" min-width="80" align="center" show-overflow-tooltip />
        <el-table-column label="性别" prop="idSex" min-width="80" align="center" show-overflow-tooltip />
        <el-table-column label="年龄" prop="age" min-width="80" align="center" show-overflow-tooltip />
        <el-table-column label="公司" prop="orgName" min-width="200" align="center" show-overflow-tooltip />
        <el-table-column label="部门" prop="orgDepart" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="电话" prop="phone" min-width="140" align="center" show-overflow-tooltip />
        <el-table-column label="开单医生" prop="doctorapply" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="评价时间" prop="appraiseTime" min-width="120" align="center"
          show-overflow-tooltip />
        <el-table-column label="评价备注1" prop="note" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="回访人1" prop="visitPerson" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="回访时间1" min-width="120" prop="visitTime" align="center" show-overflow-tooltip />
        <el-table-column label="回访备注1" min-width="140" prop="visitNote" align="center" show-overflow-tooltip />
        <el-table-column label="回访人2" min-width="120" prop="secondPerson" align="center"
          show-overflow-tooltip />
        <el-table-column label="回访时间2" min-width="120" prop="secondTime" align="center" show-overflow-tooltip />
        <el-table-column label="回访备注2" min-width="140" prop="secondNote" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />
      <addView ref="addView" @getList="getList"></addView>
      <verySatisfaction ref="verySatisfaction" @getList="getList"></verySatisfaction>
  </div>
</template>
<script>
import {dissatisfiedList} from '@/api/custservice/satisfaction/dissatisfied.js'
import addView from "./view.vue"
import verySatisfaction from "./import_customer.vue"
export default {
  components: {addView,verySatisfaction},
  props: [],
  data() {
    return {
      //详情参数
      unSatisfactionQuery:{
				patientcode:undefined
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
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        patientname: undefined,
        personcode: undefined,
        appraiseResult: undefined,
        visitResult: undefined,
        date: undefined,
      },
      // 排检表格数据
      tableList: [
      ],
      // 结果
      resultOptions: [
      {
					id: 2,
					text: "满意"
				},
				{
					id: 1,
					text: "非常满意"
				},
				{
					id: 4,
					text: "不满意"
				},
				{
					id: 0,
					text: "未评价"
				},
				{
					id: 3,
					text: "基本满意"
				}
      ],
    };
  },
  computed: {},
  watch: {},
  created() {
    this.getList();
  },
  mounted() { },
  methods: {
    // 查询列表
    getList() {
      if (this.queryParams.date) {
        this.queryParams.startDate = this.queryParams.date[0] + " 00:00:00"
        this.queryParams.endDate = this.queryParams.date[1] + " 23:59:59"
      } else {
        this.queryParams.startDate = undefined
        this.queryParams.endDate = undefined
      }
      this.loading = true;
      dissatisfiedList(this.queryParams).then(response => {
      
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
      this.queryParams.patientname=undefined
				this.queryParams.personcode=undefined
				this.queryParams.appraiseResult=undefined
				this.queryParams.visitResult=undefined
				this.queryParams.date=undefined
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {

      this.ids = selection.map((item) => item.id);

      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // 高级客户回访
    handleVisit() {
      this.$refs.verySatisfaction.getNewList(this.ids[0])
    },
    // 查看非常满意
    handleView() {
      this.$refs.addView.openAdd();
    },
    // 过滤
    handleFilter() {

    },
    // 导出
    handleDownload() {
      this.download('/member/secondInterview/export',{
        ...this.queryParams
      }, `再次不满意回访.xlsx`)
    
    },
  },
};
</script>
