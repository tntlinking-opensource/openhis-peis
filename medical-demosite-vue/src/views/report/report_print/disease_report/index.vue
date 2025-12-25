<!-- 职业报告 开发人：麦沃德科技暴雨/矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <!--页面-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="分中心" prop="branchIds">
        <el-select v-model="queryParams.branchIds" placeholder="请选择分中心" clearable style="width: 160px" @change="handleQuery">
          <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="单位" prop="khdwmcid">
        <input-select :selectData="selectData" @change="selectChange" :showTooltip="true"></input-select>
      </el-form-item>
      <el-form-item label="开单医生" prop="idOpendoctor">
        <el-input v-model="queryParams.idOpendoctor" placeholder="请输入开单医生" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="订单号" prop="numorgresv">
        <el-input v-model="queryParams.numorgresv" placeholder="请输入订单号" style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="登记日期" prop="date">
        <el-date-picker v-model="queryParams.date" style="width: 300px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="handleQuery">需生成</el-button>
      </el-form-item>
    </el-form>
    <!-- 多选按钮 -->
    <el-row :gutter="10" class="mb8" style="margin-bottom: 8px">
      <el-col :span="1.5">
        <el-checkbox v-model="queryParams.isNotPrint" :false-label="0" :true-label="1" @change="handleQuery">未打印</el-checkbox>
      </el-col>
      <el-col :span="1.5">
        <el-checkbox v-model="queryParams.isPrint" :false-label="0" :true-label="1" @change="handleQuery">已打印</el-checkbox>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-checkbox v-model="queryParams.containsPrivate">隐私报告</el-checkbox>
      </el-col> -->
      <el-col :span="1.5">
        <el-checkbox v-model="queryParams.containOldSystem" :false-label="0" :true-label="1" @change="handleQuery">含老系统</el-checkbox>
      </el-col>
      <el-col :span="1.5">
        <el-checkbox v-model="queryParams.showAllImage" :false-label="0" :true-label="1" @change="handleQuery">展示全部图像</el-checkbox>
      </el-col>
    </el-row>
    <!-- <br /> -->
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" :disabled="multiple" icon="el-icon-time" @click="handleCreateReport(0)" v-hasPermi="['report:reportPrint:diseaseReport:quickreport']">速成 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" :disabled="multiple" icon="el-icon-coin" @click="handleCreateReport(1)" v-hasPermi="['report:reportPrint:diseaseReport:createIreport']">生成检验报告 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" :disabled="single" icon="el-icon-edit-outline" @click="handleView(1)" v-hasPermi="['report:reportPrint:diseaseReport:sample']">预览 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" :disabled="multiple" icon="el-icon-plus" @click="handleSampleBatch" v-hasPermi="['report:reportPrint:diseaseReport:sampleBatch']">批量打印 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" :disabled="multiple" icon="el-icon-plus" @click="handleBackOut" v-hasPermi="['report:reportPrint:diseaseReport:backout']">已打印 </el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-share" :disabled="multiple" @click="handleShare" v-hasPermi="['report:reportPrint:diseaseReport:handleShare']">分享报告</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-download" :disabled="single" @click="handleDownloadPdf" v-hasPermi="['report:reportPrint:healthReport:downloadPdf']">下载PDF
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-download" :disabled="multiple" @click="handleDownload">批量下载pdf</el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box" ref="tableBox">
      <el-table border ref="tableList" v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick" @row-dblclick="rowDblClick">
        <el-table-column fixed type="selection" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="体检号" prop="patientcode" min-width="140px" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" min-width="80px" align="center" show-overflow-tooltip />
        <el-table-column label="性别" prop="idSex" min-width="80" align="center" show-overflow-tooltip />
        <el-table-column label="年龄" prop="age" min-width="100" align="center" show-overflow-tooltip />
        <el-table-column label="登记时间" prop="dateregister" min-width="160" align="center" show-overflow-tooltip />
        <el-table-column label="生成人" prop="generateName" min-width="100" align="center" show-overflow-tooltip />
        <el-table-column label="生成时间" prop="generateDate" min-width="100" align="center" show-overflow-tooltip />
        <el-table-column label="生成报告" prop="createReportNum" min-width="100" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="!scope.row.createReportNum" type="danger">未生成</el-tag>
            <el-tag v-else>已生成</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="报告打印" prop="printNum" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="!scope.row.printNum" type="danger">未打印</el-tag>
            <el-tag v-else type="success">已打印</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="报告状态" prop="bgzt" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ ['总检开始', '总检完成', '报告已打印', '报告一审通过', '报告一审不通过', '二审通过', '二审不通过', '终审通过', '终审不通过', '报告已交接', '报告已通知', '报告已领取'][scope.row.bgzt] }}</span>
          </template>
        </el-table-column>
        <el-table-column label="开单医师" min-width="100px" prop="kdys" align="center" show-overflow-tooltip />
        <el-table-column label="单位" min-width="120px" prop="orgName" align="center" show-overflow-tooltip />
        <el-table-column label="订单号" min-width="100px" prop="numorgresv" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.numorgresv == -1"></span>
            <span v-else>{{ scope.row.numorgresv }}</span>
          </template>
        </el-table-column>
        <el-table-column label="错误信息" min-width="120px" prop="generateHint" align="center" show-overflow-tooltip />
        <el-table-column label="操作" fixed="right" width="180" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="handleView(2, scope.row)" v-hasPermi="['report:reportPrint:diseaseReport:viewReport']">查看报告</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" :pageSizes="[20, 50, 100, 200]" />
    <!-- 分享报告 -->
    <share-report ref="shareReport"></share-report>
    <!-- 批量下载 -->
    <download ref="download" fileName="2"></download>
    <!-- 单独下载 -->
    <onedownload ref="onedownload" fileName="2"></onedownload>
  </div>
</template>
<script>
import { getListData, createReport, printAlready } from '@/api/report/report_print/disease_report'
import { getBranchData } from '@/api/statistical/professionchecks/conclusion_table.js' //获取分中心
import { getUserCid } from '@/api/system/branch.js'
import shareReport from '../health_report/share_report'
import download from '../health_report/download'
import onedownload from '../health_report/onedownload'

export default {
  name: 'DiseaseReport',
  components: { shareReport, download, onedownload },
  data() {
    return {
      //体检号
      patientcode: undefined,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      selection: [],
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
        autoFill: true,
        patientcode: undefined,
        patientname: undefined,
        khdwmcid: undefined,
        idOpendoctor: undefined,
        numorgresv: undefined,
        date: undefined,
        startTime: undefined,
        endTime: undefined,
        isNotPrint: false,
        isPrint: false,
        branchIds: undefined,
      },
      // 展示全部图像
      showAllImage: 0,
      // 单位数据模板
      selectData: {
        placeholder: '请输入输入码选择',
        value: '单位名称', //第二列标题
        url: '/sell/customer/getAllOrg', //请求连接
        selectWidth: 160, //选择器宽度（选填，默认230）不加px,传100%则为100%
        bindValue: '', //初始值
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 表格展示数据
      tableList: [],
      // 分中心列表
      fzxOptions: [],
      // 是否为线上
      isOnline: false,
    }
  },
  created() {
    this.queryParams.branchIds = this.$getCookie('cid')
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
    this.handleQuery()
  },
  mounted() {
    document.addEventListener('visibilitychange', () => {
      if (document.visibilityState === 'visible') {
        // console.log('浏览器的当前页签onShow时，do something')
        this.getList()
      }
    })
  },
  activated() {
    this.getList()
  },
  destroyed() {
    document.removeEventListener('visibilitychange', () => { })
  },
  methods: {
    // 单位返回选中的值
    selectChange(value) {
      this.queryParams.khdwmcid = value.id
      this.selectData.bindValue = value.khdwmc
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.selection = selection
      this.patientcode = selection.map((item) => item.patientcode)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 单击表格
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 双击表格
    rowDblClick(row) {
      this.handleView(2, row)
    },
    //速成/生成检验报告
    handleCreateReport(isJy) {
      this.$modal
        .confirm('确定生成报告?', '生成')
        .then(() => {
          var data = {
            dh: 1,
            isJy: isJy,
            patientcode: this.patientcode,
            showAllImage: this.showAllImage,
          }
          createReport(data).then((response) => {
            if (response.msg.indexOf('成功') != -1) {
              this.$modal.alertSuccess('报告生成成功！', '提醒')
              this.getList()
            } else {
              this.$modal.alertWarning(response.msg, '提醒')
            }
          })
        })
        .catch(() => { })
    },
    //批量打印
    handleSampleBatch() {
      var errorCodes = []
      var selectCodes = []
      this.selection.forEach(el => {
        if (el.urlPdf) {
          selectCodes.push(el.patientcode)
        } else {
          errorCodes.push(el.patientcode)
        }
      });
      if (errorCodes.length) {
        if (errorCodes.length > 50) {
          this.$modal.alertWarning('存在未生成的pdf报告,请先生成报告', '提示')
        } else {
          this.$modal.alertWarning(`以下体检号未生成pdf报告,请先生成报告：${errorCodes.join('、')}`, '提示')
        }
        return
      }
      var query = {
        type: 1,
        ids: selectCodes.join(','),
      }
      let routeUrl = this.$router.resolve({
        name: 'BatchPrinting',
        query,
      })
      window.open(routeUrl.href)
    },
    //已打印
    handleBackOut() {
      if (this.patientcode.length < 2) {
        this.loading = true
        printAlready(this.patientcode).then((response) => {
          this.loading = false
          this.$message({
            message: '修改成功',
            type: 'success',
          })
          this.getList()
        })
      } else {
        this.$message({
          message: '每次只可以通过一个人的打印',
          type: 'warning',
        })
      }
    },
    // 查询列表
    getList() {
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      if (!this.queryParams.isNotPrint) {
        this.queryParams.isNotPrint = undefined
      }
      if (!this.queryParams.isPrint) {
        this.queryParams.isPrint = undefined
      }
      this.loading = true
      getListData(this.queryParams).then((response) => {
        if (this.queryParams.patientcode && response.data.records && response.data.records.length == 1) {
          this.queryParams.patientcode = response.data.records[0].patientcode
        }
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.selectData.bindValue = ''
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 分享报告
    handleShare() {
      let error = ''
      this.selection.forEach((el) => {
        if (!el.createReportNum) {
          error = '体检号' + el.patientcode + '未生成报告,请先生成报告'
        }
      })
      if (error) {
        this.$alert(error, '提示')
        return
      }
      this.$refs.shareReport.handleShow(this.selection)
    },
    // 下载PDF
    handleDownloadPdf() {
      let error = '';
      if (!this.selection[0].urlPdf) {
        error = '体检号' + this.selection[0].patientcode + '未生成pdf报告,请先生成报告';
      }
      if (error) {
        this.$alert(error, '提示');
        return;
      }
      const pdfUrl = this.$getCookie('imgPath') + this.selection[0].urlPdf;
      this.$refs.onedownload.handleShow(this.selection[0], pdfUrl)
    },
    // 批量下载pdf
    handleDownload() {
      let error = ''
      this.selection.forEach((el) => {
        if (!el.urlPdf) {
          error = '体检号' + el.patientcode + '未生成pdf报告,请先生成报告'
        }
      })
      if (error) {
        this.$alert(error, '提示')
        return
      }
      this.$refs.download.handleShow(this.selection)
    },
    //查看报告
    handleView(type, row) {
      var data = type == 1 ? this.selection[0] : row
      if (data.createReportNum) {
        // if (data.urlPdf && !(this.$getCookie('cid') == 'ff8080817ee18637017ee77dc0322d8c' || window.location.href.includes('http://XXX.XXX.XXX'))) {
        if (data.urlPdf) {
          let url = this.$getCookie('imgPath') + data.urlPdf
          printAlready(data.patientcode)
          var query = {
            url: encodeURIComponent(url),
          }
          let routeUrl = this.$router.resolve({
            name: 'PreviewReport',
            query: query,
          })
          window.open(routeUrl.href)
          return
        }
        var query = {
          patientcode: data.patientcode,
        }
        let routeUrl = this.$router.resolve({
          name: 'IndividualDiseaseReport',
          query: query,
        })
        window.open(routeUrl.href)
      } else {
        this.$message({
          message: '请选择生成报告进行打印',
          type: 'warning',
        })
      }
    },
  },
}
</script>
<style lang="scss"></style>
