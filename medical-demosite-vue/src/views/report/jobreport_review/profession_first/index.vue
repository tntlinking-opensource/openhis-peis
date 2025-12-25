<!-- 职业初审 开发人：麦沃德科技暴雨/半夏 -->
<template>
  <div class="app-container flex-direction-column">
    <!--页面-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="体检号:" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 180px" />
      </el-form-item>
      <el-form-item label="是否补全:" prop="autoFill">
        <el-checkbox v-model="queryParams.autoFill"></el-checkbox>
      </el-form-item>
      <el-form-item label="任务ID:" prop="numorgresv">
        <el-input v-model="queryParams.numorgresv" placeholder="请输入任务ID" clearable style="width: 180px" />
      </el-form-item>
      <el-form-item label="审核状态:" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 180px;">
          <el-option label="未审核" :value="0" />
          <el-option label="一审通过" :value="3" />
          <el-option label="一审未通过" :value="4" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" :disabled="multiple" icon="el-icon-circle-check" @click="handlePass"
          v-hasPermi="['report:jobreportReview:professionFirst:pass']">审核通过
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" :disabled="single" icon="el-icon-circle-close" @click="handleUnpass"
          v-hasPermi="['report:jobreportReview:professionFirst:unpass']">审核不通过
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" :disabled="multiple" icon="el-icon-bottom-left"
          @click="handleUnaudit" v-hasPermi="['report:jobreportReview:professionFirst:unaudit']">反审
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAudit"
          v-hasPermi="['report:jobreportReview:professionFirst:audit']">批量通过
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe
        @selection-change="handleSelectionChange">
        <el-table-column fixed type="selection" width="55" align="center" />
        <el-table-column fixed label="序列" type="index" width="65" align="center" />
        <el-table-column fixed label="审核状态" prop="status" min-width="140" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span :style="'color: ' + auditColor(scope.row.status)">
              {{ auditStatus(scope.row.status) }}
            </span>
          </template>
        </el-table-column>
        <el-table-column fixed label="体检号" prop="patientcode" min-width="140" align="center"
          show-overflow-tooltip />
        <el-table-column fixed label="姓名" prop="patientname" min-width="80" align="center"
          show-overflow-tooltip />
        <el-table-column label="性别" prop="sex" min-width="80" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <div v-if="scope.row.sex == 0">男</div>
            <div v-if="scope.row.sex == 1">女</div>
            <div v-if="scope.row.sex == 2">通用</div>
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="age" min-width="80" align="center" show-overflow-tooltip />
        <el-table-column label="公司" prop="orgName" min-width="160" align="center" show-overflow-tooltip />
        <el-table-column label="登记时间" prop="dateregister" min-width="160" align="center"
          show-overflow-tooltip />
        <el-table-column label="电话" prop="phone" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="开单医师" min-width="100" prop="doctorapply" align="center" show-overflow-tooltip />
        <el-table-column label="任务" min-width="120" prop="numorgresv" align="center" show-overflow-tooltip />
        <el-table-column label="总检时间" prop="datefinalexamed" min-width="160" align="center"
          show-overflow-tooltip />
        <el-table-column label="总检大夫" min-width="120" prop="doctorfinalNameR" align="center"
          show-overflow-tooltip />
        <el-table-column label="打印时间" min-width="120" prop="datereportprinted" align="center"
          show-overflow-tooltip />
        <el-table-column label="打印人" min-width="120" prop="printMan" align="center" show-overflow-tooltip />
        <el-table-column label="一审人" min-width="120" prop="firstMan" align="center" show-overflow-tooltip />
        <el-table-column label="一审时间" min-width="120" prop="firstTime" align="center" show-overflow-tooltip />
        <el-table-column label="操作" fixed="right" width="200" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #0059FF" @click="handlePass(scope.row)"
              v-hasPermi="['report:jobreportReview:professionFirst:pass']">通过</el-button>
            <el-button size="mini" type="text" style="color: #ff2727" @click="handleUnpass(scope.row)"
              v-hasPermi="['report:jobreportReview:professionFirst:unpass']">不通过</el-button>
            <el-button size="mini" type="text" style="color: #ffba00" @click="handleUnaudit(scope.row)"
              v-hasPermi="['report:jobreportReview:professionFirst:unaudit']">反审</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
      @pagination="getList" />
    <btg-items ref="btgItems" @getList="getList"></btg-items>
    <pltg-items ref="pltgItems" @getList="getList"></pltg-items>
  </div>
</template>
<script>
import { getListData, professionFirstPass, professionFirstUnaudit } from "@/api/report/jobreport_review/profession_first";
import btgItems from './unpass'
import pltgItems from './audit'
export default {
  name: "Profession_first",
  components: { btgItems, pltgItems },
  props: [],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组-id
      ids: [],
      // 选中数组-row
      rows: [],
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
        size: 20,
        patientcode: undefined,
        autoFill: true,
        numorgresv: undefined,
        status: undefined,
      },
      // 审核状态
      reportStatus: [
        { id: 0, text: '总检开始' }, { id: 1, text: '总检完成' },
        { id: 2, text: '报告已打印' }, { id: 3, text: '一审通过' },
        { id: 4, text: '一审不通过' }, { id: 5, text: '二审通过' },
        { id: 6, text: '二审不通过' }, { id: 7, text: '终审通过' },
        { id: 8, text: '终审不通过' }, { id: 9, text: '报告已交接' },
        { id: 10, text: '报告已通知' }, { id: 11, text: '报告已领取' }
      ],
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
  mounted() { },
  methods: {
    // 审核状态
    auditStatus(value) {
      for (var i in this.reportStatus) {
        if (value == this.reportStatus[i].id) {
          return this.reportStatus[i].text
        }
      }
    },
    // 审核状态颜色
    auditColor(value) {
      for (var i in this.reportStatus) {
        var g = this.reportStatus[i];
        if (g.id == value) {
          if (value == 4 || value == 6 || value == 8) {
            return "#FF2727"
          } else if (value == 7) {
            return "#00AF66"
          } else if (value == 3 || value == 5) {
            return "#0059FF"
          } else if (value > 9) {
            return "#FF7A00"
          } else {
            return "";
          }
        }
      }
      return "";
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
      this.getList();
    },
    // 重置
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    //审核通过
    handlePass(row) {
      if (row.id) {
        if (row.status && (row.status == 3 || row.status > 4)) {
          this.$alert("体检号:" + row.patientcode + "已通过审核，操作失败！", "提醒", {
            confirmButtonText: "确定",
            type: "warning",
          })
          return;
        }
      } else {
        for (var i in this.rows) {
          if (this.rows[i].status && (this.rows[i].status == 3 || this.rows[i].status > 4)) {
            this.$alert("体检号:" + this.rows[i].patientcode + "已通过审核，操作失败！", "提醒", {
              confirmButtonText: "确定",
              type: "warning",
            })
            return;
          }
        }
      }
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认通过该数据项？')
        .then(function () {
          return professionFirstPass(ids);
        })
        .then((res) => {
          this.getList();
          this.$modal.msgSuccess("审核成功");
        })
        .catch(() => { });
    },
    //审核不通过
    handleUnpass(row) {
      if (!row.id) {
        row = this.rows[0]
      }
      if (row.status) {
        if (row.status != 4) {
          if (row.status == 3) {
            this.$alert("体检号:" + row.patientcode + "已通过审核，请先反审再操作！", "提醒", {
              confirmButtonText: "确定",
              type: "warning",
            })
            return;
          } else if (row.status < 2) {
            this.$alert("体检号:" + row.patientcode + "的报告尚未打印，操作失败！", "提醒", {
              confirmButtonText: "确定",
              type: "warning",
            })
            return;
          } else if (row.status > 4) {
            this.$alert("体检号:" + row.patientcode + "不在一审范围内，操作失败！", "提醒", {
              confirmButtonText: "确定",
              type: "warning",
            })
            return;
          }
        }
      } else {
        this.$alert("体检号:" + row.patientcode + "状态异常，操作失败！", "提醒", {
          confirmButtonText: "确定",
          type: "warning",
        })
        return;
      }
      this.$refs.btgItems.handleUnpass(row)
    },
    //反审
    handleUnaudit(row) {
      if (row.id) {
        if (row.status < 3 || row.status == 4) {
          this.$alert("体检号:" + row.patientcode + "尚未通过审核，操作失败！", "提醒", {
            confirmButtonText: "确定",
            type: "warning",
          })
          return;
        } else if (row.status > 3) {
          this.$alert("体检号:" + row.patientcode + "不在一审范围内，操作失败！", "提醒", {
            confirmButtonText: "确定",
            type: "warning",
          })
          return;
        }
      } else {
        for (var i in this.rows) {
          if (this.rows[i].status) {
            if (this.rows[i].status < 3 || this.rows[i].status == 4) {
              this.$alert("体检号:" + this.rows[i].patientcode + "尚未通过审核，操作失败！", "提醒", {
                confirmButtonText: "确定",
                type: "warning",
              })
              return;
            } else if (this.rows[i].status > 3) {
              this.$alert("体检号:" + this.rows[i].patientcode + "不在一审范围内，操作失败！", "提醒", {
                confirmButtonText: "确定",
                type: "warning",
              })
              return;
            }
          } else {
            this.$alert("体检号:" + this.rows[i].patientcode + "状态异常，操作失败！", "提醒", {
              confirmButtonText: "确定",
              type: "warning",
            })
            return;
          }
        }
      }
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认反审该数据项？')
        .then(function () {
          return professionFirstUnaudit(ids);
        })
        .then((res) => {
          this.getList();
          this.$modal.msgSuccess("反审成功");
        })
        .catch(() => { });
    },
    //批量通过
    handleAudit() {
      this.$refs.pltgItems.handleAudit()
    },
  },
};
</script>
