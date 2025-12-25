<!-- 团检报告审批 开发人：麦沃德科技暴雨/半夏 -->
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
      <el-form-item label="审核状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择审核状态" clearable style="width: 160px">
          <el-option :label="item.text" :value="item.id" v-for="(item, index) in statusList" :key="index" />
        </el-select>
      </el-form-item>
      <el-form-item label="团体名称" prop="orgName">
        <input-select :selectData="orgNameData" selectWidth="160" :showTooltip="true" @change="orgNameDataChange"></input-select>
      </el-form-item>
      <el-form-item label="样本名称" prop="sampleName">
        <el-input v-model="queryParams.sampleName" placeholder="请输入样本名称" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="上岗类型" prop="medicaltype">
        <el-select v-model="queryParams.medicaltype" placeholder="请选择上岗类型" clearable style="width: 160px">
          <el-option :label="item.text" :value="item.id" v-for="(item, index) in medicaltype" :key="index" />
        </el-select>
      </el-form-item>
      <el-form-item label="订单号" prop="ddh">
        <el-input v-model="queryParams.ddh" placeholder="请输入订单号" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="handleQuery">查询</el-button>
        <el-button size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" :disabled="single || selectRow.status != 1" @click="handleExamine" v-hasPermi="['groupreport:groupreportApproval:examine']">审核 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="single || selectRow.status != 2" @click="handleBackexamine" v-hasPermi="['groupreport:groupreportApproval:backexamine']">反审核 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-circle-check" :disabled="single || (selectRow.status != 0 && selectRow.status != 3 && selectRow.status != 4)" @click="handleSubmit" v-hasPermi="['groupreport:groupreportApproval:submit']">提交 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-circle-close" :disabled="single || selectRow.status != 1" @click="handleWithdraw" v-hasPermi="['groupreport:groupreportApproval:withdraw']">撤回 </el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-document-add" v-if="!isOnline" :disabled="single && (selectRow.status != 6 || selectRow.status != 7)" @click="handleCreate" v-hasPermi="['groupreport:groupreportApproval:preview']">生成</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-search" :disabled="single || (selectRow.status != 5 && selectRow.status != 6 && selectRow.status != 7)" @click="handlePreview" v-hasPermi="['groupreport:groupreportApproval:preview']">预览 </el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit-outline" :disabled="single || (selectRow.status != 5 && selectRow.status != 6 && selectRow.status != 7)" @click="handleModifyReport" v-hasPermi="['groupreport:groupreportApproval:preview']">修改 </el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-coin" :disabled="single || (selectRow.status != 5 && selectRow.status != 6 && selectRow.status != 7)" @click="handleModify" v-hasPermi="['groupreport:groupreportApproval:modify']">修正 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" :disabled="single || (selectRow.status != 2 && selectRow.status != 5)" @click="handleGeneratesample" v-hasPermi="['groupreport:groupreportApproval:generatesample']">生成样本分析 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" :disabled="single || (selectRow.status != 2 && selectRow.status != 5 && selectRow.status != 6 && selectRow.status != 7)" @click="handleSamples" v-hasPermi="['groupreport:groupreportApproval:samples']">综合样本分析 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-circle-check" :disabled="single || selectRow.status != 5" @click="handleMainprocess" v-hasPermi="['groupreport:groupreportApproval:mainprocess']">主检审核 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-download" :disabled="single" @click="handleDownload" v-hasPermi="['groupreport:groupreportApproval:download']">下载Word</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" :disabled="single" @click="handleUpload" v-hasPermi="['groupreport:groupreportApproval:upload']">上传Word</el-button>
      </el-col>
    </el-row>

    <!-- 表格 -->
    <div class="table-box">
      <el-table id="setTable" ref="tableData" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="体检类型" prop="diseaseHealth" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.diseaseHealth == 0">健康</span>
            <span v-if="scope.row.diseaseHealth == 1">职业</span>
          </template>
        </el-table-column>
        <el-table-column label="样本名称" prop="sampleName" align="center" show-overflow-tooltip />
        <el-table-column label="样本类型" prop="sampleType" align="center" show-overflow-tooltip />
        <el-table-column label="上岗类型" prop="sglx" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <div v-for="item in medicaltype" :key="item.id">
              <span v-if="item.id == scope.row.sglx">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="团体名称" prop="orgName" align="center" show-overflow-tooltip />
        <el-table-column label="订单号" prop="orderId" align="center" show-overflow-tooltip />
        <el-table-column label="开始登记时间" prop="beginTime" align="center" show-overflow-tooltip />
        <el-table-column label="结束登记时间" prop="endTime" align="center" show-overflow-tooltip />
        <el-table-column label="报告状态" prop="status" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span :style="'color: ' + reportColor(scope.row.status)">
              {{ reportStatus(scope.row.status) }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="未通过原因" prop="reason" align="center" show-overflow-tooltip />
        <el-table-column label="打印次数" prop="isPrint" align="center" show-overflow-tooltip />
        <el-table-column label="生成状态" prop="wordUrl" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span style="color: green" v-if="scope.row.wordUrl">已生成</span>
            <span style="color: red" v-else>未生成</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <el-dialog title="确定要生成吗?" :visible.sync="analysisOpen" class="analysis-dialog" width="375px" append-to-body>
      <div class="content">
        <el-checkbox v-model="analysisFlag" :true-label="1" :false-label="0" size="medium">包含隐私项目</el-checkbox>
      </div>
      <div slot="footer" class="dialog-footer" style="text-align: center">
        <el-button type="primary" @click="setAnalyze">确 定</el-button>
        <el-button @click="cancel" style="margin-left: 18px">取 消</el-button>
      </div>
    </el-dialog>
    <audit-items ref="auditItems" @getList="getList"></audit-items>
    <!-- 上传word弹窗 -->
    <upload-items ref="uploadItems" @getList="getList"></upload-items>
  </div>
</template>
<script>
import { getListData, reportUnFinish, reportCommit, reportRecall, reportUndo, generateAnalyze, mainAudit } from '@/api/groupreport/groupreport_approval'
import { createReportData } from '@/api/preview/group_report.js'
import auditItems from './audit'
import uploadItems from './upload'
export default {
  name: 'Groupreport_approval',
  components: { auditItems, uploadItems },
  data() {
    return {
      // 团体名称
      orgNameData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '团体名称', //第二列标题
        url: '/pacs/itemList/getOrgs', //请求连接
        firstName: 'khdwsrm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
        bindValue: '',
      },
      // 审核状态
      statusList: [
        { id: 0, text: '草稿' },
        { id: 1, text: '已提交' },
        { id: 2, text: '通过' },
        { id: 3, text: '未通过' },
        { id: 4, text: '撤回' },
      ],
      // 上岗类型
      medicaltype: [
        { id: '0', text: '上岗前' },
        { id: '1', text: '在岗期间' },
        { id: '2', text: '离岗时' },
        { id: '3', text: '离岗后' },
        { id: '4', text: '应急' },
      ],
      // 是否显示生成样本分析弹窗
      analysisOpen: false,
      // 是否包含隐私项目
      analysisFlag: 0,
      // 遮罩层
      loading: true,
      // 选中数据
      selectRow: {},
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
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
        startTime: undefined,
        endTime: undefined,
        status: undefined,
        orgName: undefined,
        sampleName: undefined,
        medicaltype: undefined,
        ddh: undefined,
      },
      // 表格展示数据
      tableList: [],
      // 是否为线上
      isOnline: false,
      //定义新变量-当前表格高度
      scrollTop: null,
    }
  },
  mounted() {
    this.isOnline = this.$getCookie('isOnline') == '1' ? true : false
    this.getList()
  },
  activated() {
    this.saveScroll()
  },
  beforeDestroy() {
    this.$refs.tableData.bodyWrapper.removeEventListener(
      'scroll',
      (res) => {
        let height = res.target
        this.scrollTop = height.scrollTop
      },
      false
    )
  },
  methods: {
    // 设置表格滚动条位置
    saveScroll() {
      this.$nextTick(() => {
        setTimeout(() => {
          var scrollTop = this.$el.querySelector('.el-table__body-wrapper')
          scrollTop.scrollTop = this.scrollTop
        }, 50)
      })
    },
    //审核
    handleExamine() {
      this.$refs.auditItems.handleShow(this.selectRow)
    },
    //反审核
    handleBackexamine() {
      this.$modal
        .confirm('确定要反审核吗？', '提示')
        .then(() => {
          return reportUnFinish(this.selectRow.id)
        })
        .then(() => {
          this.$modal.msgSuccess('反审核成功！')
          this.getList()
        })
        .catch(() => {})
    },
    //提交
    handleSubmit() {
      this.$modal
        .confirm('确定提交选中记录？', '提示')
        .then(() => {
          return reportCommit(this.selectRow.id)
        })
        .then(() => {
          this.$modal.msgSuccess('提交成功！')
          this.getList()
        })
        .catch(() => {})
    },
    //撤回
    handleWithdraw() {
      this.$modal
        .confirm('确定撤回选中记录？')
        .then(() => {
          return reportRecall(this.selectRow.id)
        })
        .then(() => {
          this.$modal.msgSuccess('撤回成功！')
          this.getList()
        })
        .catch(() => {})
    },
    // 生成报告
    handleCreate() {
      this.loading = true
      createReportData({
        analyzeId: this.selectRow.id,
        orderId: this.selectRow.orderId,
        dh: this.selectRow.diseaseHealth,
      })
        .then(() => {
          this.$modal.msgSuccess('生成报告成功')
          this.getList()
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    //预览报告
    handlePreview() {
      if (!this.selectRow.pdfUrl) {
        this.$alert('请先生成报告', '提醒', {
          type: 'warning',
        })
        return
      }
      let url = this.$getCookie('imgPath') + this.selectRow.pdfUrl
      window.open(url, '_blank')
    },
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
    //修正
    handleModify() {
      this.$modal
        .confirm('确定要撤销吗？')
        .then(() => {
          return reportUndo(this.selectRow.id)
        })
        .then(() => {
          this.$modal.msgSuccess('撤销成功！')
          this.getList()
        })
        .catch(() => {})
    },
    //生成样本分析按钮
    handleGeneratesample() {
      this.analysisOpen = true
    },
    // 生成样本分析
    setAnalyze() {
      generateAnalyze(this.selectRow.id, this.analysisFlag).then((response) => {
        this.cancel()
        this.$modal.msgSuccess('生成成功！')
        this.getList()
      })
    },
    // 取消
    cancel() {
      this.analysisFlag = 0
      this.analysisOpen = false
    },
    //综合样本分析
    handleSamples() {
      const obj = { path: '/groupreport/audit_analyze/index', name: 'AuditAnalyze' }
      this.$tab.closePage(obj).then(() => {
        var query = {
          dh: this.selectRow.diseaseHealth, //健康0 职业1
          id: this.selectRow.id, //体检号,可以传多个
          ddh: this.selectRow.orderId, //是否展现所有图片
          type: this.selectRow.diseaseHealth,
          groupId: this.selectRow.groupId,
        }
        this.$router.push({ name: 'AuditAnalyze', query })
      })
    },
    //主检审核
    handleMainprocess() {
      if (this.selectRow.status != 5) {
        this.$alert('请选择主检未审状态的记录！', '提醒', {
          confirmButtonText: '确定',
          type: 'warning',
        })
        return
      }
      this.$modal
        .confirm('确定将所选记录主检审核通过吗？')
        .then(() => {
          return mainAudit(this.selectRow.id)
        })
        .then(() => {
          this.$modal.msgSuccess('审核成功！')
          this.getList()
        })
        .catch(() => {})
    },
    // 下载word
    handleDownload() {
      if (!this.selectRow.wordUrl) {
        this.$alert('请先生成报告', '提醒', {
          type: 'warning',
        })
        return
      }
      this.getBlob(this.$getCookie('imgPath') + this.selectRow.wordUrl).then((blob) => {
        this.saveAs(blob, this.selectRow.orgName + '团检报告.docx')
      })
    },
    getBlob(url) {
      return new Promise((resolve) => {
        const xhr = new XMLHttpRequest()
        xhr.open('GET', url, true)
        xhr.responseType = 'blob'
        xhr.onload = () => {
          if (xhr.status === 200) {
            resolve(xhr.response)
          }
        }
        xhr.send()
      })
    },
    saveAs(blob, filename) {
      var link = document.createElement('a')
      link.href = window.URL.createObjectURL(blob)
      link.download = filename
      link.click()
    },
    // 上传word
    handleUpload() {
      if (this.selectRow.status == '7') {
        this.$alert('当前报告已经主检已审,不能上传修改！', '提示', {
          type: 'warning',
        })
        return
      }
      this.$refs.uploadItems.handleShow(this.selectRow.id)
    },
    // 选择团体名称
    orgNameDataChange(value) {
      this.queryParams.orgName = value.khdwmc
      this.orgNameData.bindValue = value.khdwmc
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
        return 'orange'
      } else {
        return 'black'
      }
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      if (selection.length == 1) {
        this.selectRow = selection[0]
      } else if (selection.length > 1) {
        this.$refs.tableData.clearSelection() //清空表格数据
        this.$refs.tableData.toggleRowSelection(selection.pop()) //最后一条数据
      }
      this.single = selection.length != 1
    },
    //单击事件选中当前行
    rowClick(row, col) {
      if (col && col.label == '操作') {
        return
      }
      this.$refs.tableData.toggleRowSelection(row, true)
    },
    // 查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams)
        .then((response) => {
          this.tableList = response.data.records
          this.total = response.data.total
          this.loading = false
          this.$refs.tableData.bodyWrapper.addEventListener(
            'scroll',
            (res) => {
              let height = res.target
              this.scrollTop = height.scrollTop
            },
            false
          )
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
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
      this.queryParams.startTime = undefined
      this.queryParams.endTime = undefined
      this.orgNameData.bindValue = undefined
      this.resetForm('queryForm')
      this.handleQuery()
    },
  },
}
</script>
<style lang="scss">
.analysis-dialog {
  .content {
    text-align: center;
  }
}
</style>
<style scoped>
#setTable /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>
