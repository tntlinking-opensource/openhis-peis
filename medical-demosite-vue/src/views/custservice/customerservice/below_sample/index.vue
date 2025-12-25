<!-- 不合格样本回访 开发人：麦沃德科技暴雨 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="queryParams.phone" placeholder="请输入电话" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="不合格原因" prop="belowquestion">
        <el-select v-model="queryParams.belowquestion" placeholder="请选择" clearable style="width: 160px">
          <el-option label="溶血" :value="1" />
          <el-option label="脂血" :value="2" />
          <el-option label="凝血" :value="3" />
          <el-option label="多采集" :value="4" />
          <el-option label="容器不符" :value="5" />
          <el-option label="标本量错误" :value="6" />
          <el-option label="标识不清" :value="7" />
          <el-option label="TCL" :value="8" />
        </el-select>
      </el-form-item>
      <el-form-item label="体检时间">
        <el-date-picker v-model="queryParams.date" style="width: 300px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="体检分类" prop="useCodeHiden">
        <el-select v-model="queryParams.useCodeHiden" placeholder="请选择" clearable style="width: 120px">
          <el-option label="个检" :value="0" />
          <el-option label="团检" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="完成状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择" clearable style="width: 120px">
          <el-option label="未完成" :value="0" />
          <el-option label="已完成" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="2.5">
        <el-button type="warning" plain size="mini" icon="el-icon-coin" @click="handleAdd" v-hasPermi="['custservice:customerservice:belowSample:add']">不合格样本回访 </el-button>
      </el-col>
      <el-col :span="2.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['custservice:customerservice:belowSample:export']">导出Excel </el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box" ref="tableBox">
      <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column fixed type="selection" width="50" align="center" />
        <el-table-column label="序列" type="index" width="55" align="center" />
        <el-table-column label="体检号" prop="patientcode" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="性别" prop="idSex" min-width="120px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.idSex == 0">男</el-tag>
            <el-tag v-else-if="scope.row.idSex == 1">女</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="电话" prop="phone" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="体检时间" prop="dateregister" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="不合格样本数量" prop="count" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="不合格原因" prop="belowquestion" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="样本是否全部回访" prop="allProcess" min-width="140px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag size="small" v-if="scope.row.allProcess == 1">是</el-tag>
					  <el-tag type="danger" size="small" v-else>否</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="再通知" prop="noticeAgain" min-width="120px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="danger" v-if="scope.row.noticeAgain == 1">再通知</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="备注" prop="note" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="回访人" prop="visitId" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="回访时间" prop="visitTime" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="回访备注" prop="memo" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="健康体检状态" prop="jktjzt" min-width="120px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.jktjzt == -2">检验报告</el-tag>
            <el-tag v-if="scope.row.jktjzt == -1">总检未开始</el-tag>
            <el-tag v-if="scope.row.jktjzt == 0">总检开始</el-tag>
            <el-tag v-if="scope.row.jktjzt == 1">总检完成</el-tag>
            <el-tag v-if="scope.row.jktjzt == 2">报告已打印</el-tag>
            <el-tag v-if="scope.row.jktjzt == 3">报告一审通过</el-tag>
            <el-tag v-if="scope.row.jktjzt == 4">报告一审不通过</el-tag>
            <el-tag v-if="scope.row.jktjzt == 5">二审通过</el-tag>
            <el-tag v-if="scope.row.jktjzt == 6">二审不通过</el-tag>
            <el-tag v-if="scope.row.jktjzt == 7">终审通过</el-tag>
            <el-tag v-if="scope.row.jktjzt == 8">终审不通过</el-tag>
            <el-tag v-if="scope.row.jktjzt == 9">报告已交接</el-tag>
            <el-tag v-if="scope.row.jktjzt == 10">报告已通知</el-tag>
            <el-tag v-if="scope.row.jktjzt == 11">报告已领取</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="职业体检状态" prop="zytjzt" min-width="120px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.zytjzt == -2">检验报告</el-tag>
            <el-tag v-if="scope.row.zytjzt == -1">总检未开始</el-tag>
            <el-tag v-if="scope.row.zytjzt == 0">总检开始</el-tag>
            <el-tag v-if="scope.row.zytjzt == 1">总检完成</el-tag>
            <el-tag v-if="scope.row.zytjzt == 2">报告已打印</el-tag>
            <el-tag v-if="scope.row.zytjzt == 3">报告一审通过</el-tag>
            <el-tag v-if="scope.row.zytjzt == 4">报告一审不通过</el-tag>
            <el-tag v-if="scope.row.zytjzt == 5">二审通过</el-tag>
            <el-tag v-if="scope.row.zytjzt == 6">二审不通过</el-tag>
            <el-tag v-if="scope.row.zytjzt == 7">终审通过</el-tag>
            <el-tag v-if="scope.row.zytjzt == 8">终审不通过</el-tag>
            <el-tag v-if="scope.row.zytjzt == 9">报告已交接</el-tag>
            <el-tag v-if="scope.row.zytjzt == 10">报告已通知</el-tag>
            <el-tag v-if="scope.row.zytjzt == 11">报告已领取</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改对话框 -->
    <add-items ref="addItems" @getList="getList"></add-items>
  </div>
</template>
<script>
import { listPrinttype } from '@/api/custservice/customerservice/below_sample/below_sample.js'
import addItems from './add'
export default {
  components: { addItems },
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
      // 查询参数
      queryParams: {
        current: 1,
        size: 10,
        sfxmsrm: undefined,
        examfeeitemName: undefined,
        tjlx: undefined,
      },
      // 检查项目列表
      examList: [],
      // 表格展示数据
      tableList: [],
      // 显示模态框
      showExam: false,
      showUpload: false,
    }
  },
  computed: {},
  watch: {},
  created() {
    this.getList()
  },
  mounted() {},
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      listPrinttype(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.rows = selection
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      if (this.queryParams.date) {
        this.queryParams.startDate = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endDate = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startDate = undefined
        this.queryParams.endDate = undefined
      }
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 添加
    handleAdd() {
      if (this.rows != undefined) {
        if (this.rows.length > 1 || undefined) {
          this.$alert('请选择一条记录修改!', '提醒', {
            confirmButtonText: '确定',
            type: 'warning',
          })
        } else if (this.rows.length == 1) {
          this.$refs.addItems.handleAdd(this.ids)
        } else {
          this.$alert('请选择一条记录后操作!', '提醒', {
            confirmButtonText: '确定',
            type: 'warning',
          })
        }
      } else {
        this.$alert('请选择一条记录后操作!', '提醒', {
          confirmButtonText: '确定',
          type: 'warning',
        })
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/member/belowSample/export', {}, `不合格样本回访.xlsx`)
    },
  },
}
</script>
