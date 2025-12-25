<template>
  <el-dialog title="查看非常满意客户"   :visible.sync="open" class="add-items"  width="1400px" :close-on-click-modal="false"
    append-to-body>
    <el-form v-loading="popLoading"  ref="form" size="small" :model="form" :inline="true" label-width="70px" hide-required-asterisk>
      <el-form-item prop="patientname" label="姓名">
        <el-input v-model="form.patientname" placeholder="" style="width: 220px;"></el-input>
      </el-form-item>
      <el-form-item prop="personcode" label="体检号">
        <el-input v-model="form.personcode" placeholder="" style="width: 220px;"></el-input>
      </el-form-item>
      <el-form-item label="日期选择">
        <el-date-picker v-model="form.date"  value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary"  icon="el-icon-search" size="mini" @click="handleQuery" >搜索</el-button>
        <el-button type="primary"   icon="el-icon-search" size="mini"  @click="reset" plain>重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
			<el-col :span="1.5">
				<el-button type="success" plain size="mini" icon="el-icon-help" @click="handleExport"
					v-hasPermi="['custservice:satisfaction:returnSatisfaction:informationexport']">导出</el-button>
			</el-col>
		</el-row>
    <div class="table-box" >
      <el-table border v-loading="loading" :data="tableList" height="260px" stripe>
        <el-table-column fixed type="selection" width="55" align="center" />
        <el-table-column label="序列" fixed type="index" width="60" align="center" />
        <el-table-column label="评价结果" fixed prop="appraiseResult" min-width="200" align="center"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.appraiseResult == 0">未评价</span>
            <span v-else-if="scope.row.appraiseResult == 1">非常满意</span>
            <span v-else-if="scope.row.appraiseResult == 2">满意</span>
            <span v-else-if="scope.row.appraiseResult == 3">基本满意</span>
            <span v-else-if="scope.row.appraiseResult == 4">不满意</span>
            <span v-else>未评价</span>
          </template>
        </el-table-column>
        <el-table-column label="回访结果" fixed prop="visitResult" min-width="160" align="center"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.visitResult == 0">未评价</span>
            <span v-else-if="scope.row.visitResult == 1">非常满意</span>
            <span v-else-if="scope.row.visitResult == 2">满意</span>
            <span v-else-if="scope.row.visitResult == 3">基本满意</span>
            <span v-else-if="scope.row.visitResult == 4">不满意</span>
            <span v-else>未评价</span>
          </template>
        </el-table-column>
        <el-table-column label="体验号" prop="personcode" min-width="160" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientcode" min-width="90" align="center" show-overflow-tooltip />
        <el-table-column label="性别" prop="idSex" min-width="60" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <div v-for="item in xbOptions" :key="item.id">
              <span v-if="(item.id == scope.row.idSex)">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="age" min-width="60" align="center" show-overflow-tooltip />
        <el-table-column label="公司" prop="orgName" min-width="100" align="center" />
        <el-table-column label="部门" prop="orgDepart" min-width="100" align="center" />
        <el-table-column label="电话" prop="tel" min-width="100" align="center" />
        <el-table-column label="开单医生" prop="doctorapply" min-width="100" align="center" />
        <el-table-column label="评价时间" prop="commentsdate" min-width="100" align="center" />
        <el-table-column label="评价备注" prop="reviewnotes" min-width="100" align="center" />
      </el-table>
      <pagination v-show="total > 0" :total="total" :page.sync="form.current" :limit.sync="form.size"
			@pagination="handleQuery" />
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确定</el-button>
      <el-button type="primary" @click="reset" plain>重置</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getSatisfactionList } from "@/api/custservice/satisfaction/return_satisfaction.js";
export default {
  data() {
    return {
      total:0,
      //遮罩层
      loading:true,
      //只读
      readonly: true,
      // 弹窗开关
      open: false,
      //  遮罩层
      popLoading: false,
      form: {
        date: undefined,
        endData: undefined,
        startData: undefined,
        current:1,
        size:10
      },
      queryData: {
        personcode: undefined,
      },
      popData: {

      },
      		// 性别
			xbOptions: [
				{ id: 0, text: "男" },
				{ id: 1, text: "女" },
				{ id: 2, text: "通用" }
			],
      //表格数据
      tableList: []
    }
  },
  created(){
    
  },
  methods: {
    // 提交按钮
    submitForm(){
      this.open = false
    },
    reset() {
      this.form = {
        depName: undefined,
        name: undefined,
        isLong: undefined,
        isPublic: undefined,
        isfzx: undefined,
        suggestion: undefined,
        depiction: undefined,
        advice: undefined,
        dietguide: undefined,
        sportguide: undefined,
        knowledge: undefined,
        note: undefined,
        divisionId: undefined,
        center: undefined,
        date1: undefined,
        date2: undefined,
        visitTime: undefined
      }
      this.resetForm("form");
      // this.selectData1.bindValue = undefined
        this.handleQuery()
    },
    openAdd() {
      this.open = true;
      this.handleQuery();
    },
    ///导出
    handleExport()
    {
      this.download('/member/secondInterview/exports',{
        ...this.form
      }, `查看非常满意.xlsx`)
    },
    handleQuery() {
      this.form.current = 1;
      if (this.form.date) {
        this.form.startDate = this.form.date[0] + " 00:00:00"
        this.form.endDate= this.form.date[1] + " 23:59:59"
      } else {
        this.form.startDate = undefined
        this.form.endDate= undefined
      }
      getSatisfactionList(this.form).then(response => {
    
        this.tableList = response.data.records
        this.total=response.data.total
        this.loading=false
        this.popLoading = false
      });
    }
  },


}
</script>

<style></style>