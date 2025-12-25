<!-- 职业结果告知书  开发人：麦沃德科技半夏/暴雨/予安 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="70px" class="no-margin-bottom">
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="团体名称" prop="khdwmcid">
        <input-select :selectData="selectsData" selectWidth="180px" @change="divisionIdChange" :initialValue="queryParams.khdwmcid"></input-select>
      </el-form-item>
      <el-form-item label="打印类型" prop="idPatientclass">
        <el-select v-model="queryParams.idPatientclass" placeholder="请选择打印类型" style="width: 180px" @change="handleQuery">
          <el-option label="复查" :value="1" />
          <el-option label="职业禁忌证" :value="2" />
          <el-option label="职业病" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="登记日期">
        <el-date-picker v-model="queryParams.date" style="width: 300px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="销售经理" prop="xsjl">
        <el-input v-model="queryParams.xsjl" placeholder="请输入销售经理名称" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-search" @click="handleQuery" v-hasPermi="['report:printNotice:query']">查询</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit" :disabled="single" @click="handleCreate" v-hasPermi="['report:printNotice:create']">生成</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-edit-outline" :disabled="single" @click="handleDetails(1)" v-hasPermi="['report:printNotice:modify']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-printer" :disabled="single" @click="handleDetails(2)" v-hasPermi="['report:printNotice:print']">打印</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-download" :disabled="multiple" @click="handleExport" v-hasPermi="['report:printNotice:download']">下载 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-upload2" @click="handleUpload" v-hasPermi="['report:printNotice:upload']">上传 </el-button>
      </el-col> -->
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table ref="tableData" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column fixed type="selection" width="55" align="center" />
        <el-table-column label="序列" fixed type="index" width="60" align="center" />
        <el-table-column label="体检号" fixed prop="patientcode" miln-width="200" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" min-width="200" align="center" show-overflow-tooltip />
        <el-table-column label="年龄" prop="age" min-width="160" align="center" show-overflow-tooltip />
        <el-table-column label="登记时间" prop="dateregister" min-width="160" align="center" show-overflow-tooltip />
        <el-table-column label="生成状态" prop="status" min-width="160" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span :style="!scope.row.status ? 'color:red;' : 'color:green;'">
              {{ ['未生成', '生成中', '已生成'][scope.row.status] || '未生成' }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="体检团体" prop="orgName" min-width="160" align="center" show-overflow-tooltip />
        <el-table-column label="销售经理" prop="xsjl" min-width="160" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />

    <!-- 上传对话框 -->
    <!-- <upload-items ref="uploadItems"></upload-items> -->
  </div>
</template>
<script>
import { getListData, createReview } from '@/api/report/print_notice.js'
import uploadItems from './upload'
export default {
  name: 'Print_notice',
  components: { uploadItems, module },
  props: [],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中的id
      ids: [],
      // 选中的数据
      selectItem: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        patientcode: undefined,
        khdwmcid: undefined,
        idPatientclass: 1,
        date: undefined,
        startTime: undefined,
        endTime: undefined,
        xsjl: undefined,
      },
      // 选择的体检号
      patientcodes: undefined,
      // 表格数据
      tableList: [],
      // 团体名称选择
      selectsData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '团体名称', //第二列标题
        url: '/sell/customer/getAllOrg', //请求连接
        firstName: 'khdwsrm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 显示打印模态框
      showPrint: false,
      // 打印内容
      printData: {},
    }
  },
  created() {
    this.queryParams.date = [this.$getDate().split(' ')[0], this.$getDate().split(' ')[0]]
    this.getList()
  },
  methods: {
    // 查询列表
    getList() {
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.loading = true
      getListData(this.queryParams)
        .then((response) => {
          this.tableList = response.data.records
          this.total = response.data.total
          this.loading = false
        })
        .catch((err) => {
          console.error(err)
          this.loading = false
        })
    },
    // 选择科室
    divisionIdChange(value) {
      this.queryParams.khdwmcid = value.id
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.getList()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.selectItem = selection
      this.ids = selection.map((item) => item.id)
      this.patientcodes = selection.map((item) => item.patientcode)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableData.clearSelection()
      this.$refs.tableData.toggleRowSelection(row)
    },
    //上传
    handleUpload() {
      this.$refs.uploadItems.handleUpload()
    },
    // 预览
    handleDetails(isModify) {
      if (!this.selectItem[0].status) {
        this.$alert('请先生成报告后再操作', '提示')
        return
      }
      let patientcode = this.patientcodes.join(',')
      let type = this.queryParams.idPatientclass
      if (type == 1) {
        type = 3
      } else if (type == 3) {
        type = 1
      }
      let query = {
        patientcode,
        type,
      }
      if (isModify == 1) {
        query.isModify = true
      }
      let routeUrl = this.$router.resolve({
        name: 'ResultsNotice',
        query,
      })
      window.open(routeUrl.href, '_blank')
    },
    // //打印
    // handleStamp() {
    //   if (this.ids[0].status == 0) {
    //     this.$confirm('所选数据未生成,确定要打印复查通知书吗？', '提示')
    //       .then(() => {
    //         this.doPrint()
    //       })
    //       .catch(() => {})
    //     return
    //   }
    //   this.doPrint()
    // },
    // // 执行打印
    // doPrint() {
    //   let type = this.queryParams.idPatientclass
    //   let patientcode = this.patientcodes.join(',')
    //   this.showPrint = true
    //   createReview({ patientcode }).then(({ data }) => {
    //     this.printData = type == 1 ? data[0].fc : type == 2 ? data[0].jjz : data[0].zyb
    //     this.$nextTick(() => {
    //       this.$print(this.$refs.mainPrint)
    //       this.showPrint = false
    //     })
    //   })
    //   // .catch(() => {
    //   //   this.showPrint = false
    //   // })
    // },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },

    //生成
    handleCreate() {
      const patientcode = this.patientcodes.join(',')
      let idPatientclass = this.queryParams.idPatientclass
      if (idPatientclass == 1) {
        idPatientclass = 3
      } else if (idPatientclass == 3) {
        idPatientclass = 1
      }
      this.$modal
        .confirm('确定要生成报告吗？')
        .then(function () {
          return createReview({
            patientcode,
            idPatientclass,
          })
        })
        .then((res) => {
          this.getList()
          this.$modal.msgSuccess('报告生成成功')
        })
        .catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      let patientcodes = this.patientcodes.join(',')
      this.download('/report/printNotice/downloadWord', { patientcodes }, `职业结果告知书.xlsx`)
    },
  },
}
</script>
<style lang="scss">
.hide-dialog {
  display: none;
}
#media print {
  .hide-dialog {
    display: block;
  }
}
</style>
