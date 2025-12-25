<!-- 团检报告打印 开发人：麦沃德科技暴雨/半夏 -->
<template>
  <div class="app-container flex-direction-column">
    <!--页面-->
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="no-margin-bottom">
      <el-form-item label="创建日期">
        <el-date-picker style="width: 166px" value-format="yyyy-MM-dd" type="date" v-model="queryParams.startTime" placeholder="请选择日期" :picker-options="pickerOptions" @change="handleQuery"></el-date-picker>
      </el-form-item>
      <el-form-item label="至">
        <el-date-picker style="width: 166px" value-format="yyyy-MM-dd" type="date" v-model="queryParams.endTime" placeholder="请选择日期" :picker-options="pickerOptions" @change="handleQuery"></el-date-picker>
      </el-form-item>
      <el-form-item label="团体名称" prop="orgName">
        <el-input v-model="queryParams.orgName" placeholder="请输入团体名称" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="订单号" prop="ddh">
        <el-input v-model="queryParams.ddh" placeholder="请输入订单号" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检类型" prop="tjlx">
        <el-select v-model="queryParams.tjlx" placeholder="请选择体检类型" clearable style="width: 160px">
          <el-option label="健康" :value="0" />
          <el-option label="职业" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="handleQuery">查询</el-button>
        <el-button size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-printer" :disabled="single" @click="handlePrint" v-hasPermi="['groupreport:reportAudit:print']">打印 </el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button type="warning" pla in size="mini" icon="el-icon-upload2" :disabled="multiple" @click="handleUpload" v-hasPermi="['groupreport:reportAudit:upload']">报告上传
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit-outline" :disabled="single || (selectRow.status != 5 && selectRow.status != 6 && selectRow.status != 7)" @click="handleModifyReport" v-hasPermi="['groupreport:reportAudit:upload']">修改</el-button>
      </el-col> -->
    </el-row>

    <!-- 表格 -->
    <div class="table-box" ref="print">
      <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column fixed type="selection" align="center" />
        <el-table-column label="序列" width="80" type="index" align="center" />
        <el-table-column label="体检类型" prop="diseaseHealth" min-width="100" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.diseaseHealth == 0">健康</span>
            <span v-if="scope.row.diseaseHealth == 1">职业</span>
          </template>
        </el-table-column>
        <el-table-column label="样本名称" prop="sampleName" min-width="200" align="center" show-overflow-tooltip />
        <el-table-column label="样本类型" prop="sampleType" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="团体名称" prop="orgName" min-width="200" align="center" show-overflow-tooltip />
        <el-table-column label="订单号" prop="ddh" min-width="140" align="center" show-overflow-tooltip />
        <el-table-column label="开始登记时间" min-width="140" prop="beginTime" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ scope.row.beginTime ? scope.row.beginTime.split(' ')[0] : '' }}
          </template>
        </el-table-column>
        <el-table-column label="结束登记时间" min-width="140" prop="endTime" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ scope.row.endTime ? scope.row.endTime.split(' ')[0] : '' }}
          </template>
        </el-table-column>
        <el-table-column label="报告状态" min-width="140" prop="status" align="center">
          <template slot-scope="scope">
            <span :style="'color: ' + reportColor(scope.row.status)">
              {{ reportStatus(scope.row.status) }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="上传状态" min-width="100" prop="scbs" align="center">
          <template slot-scope="scope">
            <span style="color: green" v-if="scope.row.scbs == '1'">已上传</span>
            <span style="color: red" v-else>未上传</span>
          </template>
        </el-table-column>
        <el-table-column label="打印次数" min-width="100" prop="isPrint" align="center" />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>
<script>
import { getListData } from '@/api/groupreport/groupreport_approval'
export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      rows: [],
      ids: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 日期选择参数
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now()
        },
        shortcuts: [
          {
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date())
            },
          },
          {
            text: '昨天',
            onClick(picker) {
              const date = new Date()
              date.setTime(date.getTime() - 3600 * 1000 * 24)
              picker.$emit('pick', date)
            },
          },
          {
            text: '一周前',
            onClick(picker) {
              const date = new Date()
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
              picker.$emit('pick', date)
            },
          },
        ],
      },
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        date: undefined,
        orgName: undefined,
        ddh: undefined,
        tjlx: undefined,
      },
      // 表格展示数据
      tableList: [],
      selectRow: undefined,
    }
  },
  created() {
    this.handleQuery()
  },
  methods: {
    //打印
    handlePrint() {
      var row = this.rows[0]
      if (row.createStatus != 2) {
        this.$alert('该样本还没有生成报告，不能打印。', '提醒', {
          confirmButtonText: '确定',
          type: 'warning',
        })
        return
      }
      if (row.status != 7) {
        this.$alert("请选择<font color='green'>主审已审</font>的样本打印。", '提醒', {
          dangerouslyUseHTMLString: true,
          confirmButtonText: '确定',
          type: 'warning',
        })
        return
      }
      if (!this.row.pdfUrl) {
        this.$alert('请先生成报告', '提醒', {
          type: 'warning',
        })
        return
      }
      let url = this.$getCookie('imgPath') + this.row.pdfUrl
      window.open(url, '_blank')

      return
      let routeUrl = this.$router.resolve({
        name: row.diseaseHealth == 1 ? 'GroupDiseaseReport' : 'GroupHealthReport',
        query: {
          analyzeId: row.id,
          idExamtype: row.diseaseHealth,
          reportType: 4,
        },
      })
      window.open(routeUrl.href)
    },
    //报告上传
    // handleUpload() {
    //   this.$modal.confirm('确定要上传吗？').then(() => {
    //     return reportUnFinish(this.ids);
    //   }).then((res) => {
    //     this.$modal.msgSuccess("上传成功！");
    //     this.getList()
    //   }).catch(() => { });
    // },
    // 修改报告
    handleModifyReport() {
      let routeUrl = this.$router.resolve({
        name: this.selectRow.diseaseHealth == 1 ? 'GroupDiseaseReport' : 'GroupHealthReport',
        query: {
          analyzeId: this.selectRow.id,
          idExamtype: this.selectRow.diseaseHealth,
          reportType: 4,
          isModify: true,
        },
      })
      window.open(routeUrl.href)
    },
    // 查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.rows = selection
      this.selectRow = selection[0]
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 报告状态
    reportStatus(value) {
      if (value == 0) return '草稿'
      else if (value == 1) return '已提交'
      else if (value == 2) return '通过'
      else if (value == 3) return '未通过'
      else if (value == 4) return '撤回'
      else if (value == 5) return '主检未审'
      else if (value == 6) return '主检开审'
      else if (value == 7) return '主检已审'
      return ''
    },
    // 报告状态颜色
    reportColor(value) {
      if (value == 1) {
        return 'red'
      } else if (value == 2 || value == 7) {
        return 'green'
      } else if (value == 3) {
        return '#E6A23C'
      } else {
        return 'black'
      }
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      if (this.queryParams.startTime) {
        this.queryParams.startTime = this.queryParams.startTime.slice(0, 10) + ' 00:00:00'
      }
      if (this.queryParams.endTime) {
        this.queryParams.endTime = this.queryParams.endTime.slice(0, 10) + ' 23:59:59'
      }
      this.getList()
    },
    // 重置
    resetQuery() {
      this.queryParams.date = undefined
      this.queryParams.startTime = undefined
      this.queryParams.endTime = undefined
      this.resetForm('queryForm')
      this.handleQuery()
    },
  },
}
</script>
