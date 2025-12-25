<!-- 健康报告 开发人：麦沃德科技暴雨/矢北 -->
<template>
  <div class="app-container flex-direction-column health-report">
    <!--页面-->
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="no-margin-bottom">
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
        <input-select ref="khdwmcid" :selectData="selectData" @change="selectChange" :showTooltip="true"></input-select>
      </el-form-item>
      <el-form-item label="开单医生" prop="idOpendoctor">
        <input-select ref="idOpendoctor" :selectData="selectData2" @change="selectChange2"></input-select>
      </el-form-item>
      <el-form-item label="订单号" prop="numorgresv">
        <el-input v-model="queryParams.numorgresv" style="width: 160px" placeholder="请输入订单号" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="登记日期" prop="date">
        <el-date-picker v-model="queryParams.date" style="width: 300px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" @change="handleQuery"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" style="margin-left: 10px" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" style="margin-left: 10px" @click="resetQuery">重置</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="secHandleQuery">需生成</el-button>
      </el-form-item>
      <el-form-item prop="isPrint">
        <el-checkbox :false-label="0" :true-label="1" v-model="queryParams.isPrint" @change="
          () => {
            queryParams.isNotPrint = 0
            handleQuery()
          }
        ">已打印</el-checkbox>
      </el-form-item>
      <el-form-item prop="isNotPrint">
        <el-checkbox :false-label="0" :true-label="1" v-model="queryParams.isNotPrint" @change="
          () => {
            queryParams.isPrint = 0
            handleQuery()
          }
        ">未打印</el-checkbox>
      </el-form-item>
      <el-form-item prop="containOldSystem">
        <el-checkbox v-model="queryParams.containOldSystem" :false-label="0" :true-label="1" @change="handleQuery">含老系统</el-checkbox>
      </el-form-item>
      <el-form-item prop="containsPrivate">
        <el-checkbox v-model="queryParams.containsPrivate" :false-label="0" :true-label="1" @change="handleQuery">隐私报告</el-checkbox>
      </el-form-item>
      <el-form-item prop="showAllImage">
        <el-checkbox v-model="queryParams.showAllImage" :false-label="0" :true-label="1" @change="handleQuery">展示全部图像</el-checkbox>
      </el-form-item>
      <el-form-item prop="showNotToatal">
        <el-checkbox v-model="queryParams.showNotToatal" :false-label="0" :true-label="1" @change="handleQuery">展示未总检</el-checkbox>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-time" :disabled="multiple" @click="handleCreateReport(0)" v-hasPermi="['report:reportPrint:healthReport:quickreport']">速成 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-coin" :disabled="multiple" @click="handleCreateReport(1)" v-hasPermi="['report:reportPrint:healthReport:createIreport']">生成检验报告 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit-outline" :disabled="single" @click="handleView()" v-hasPermi="['report:reportPrint:healthReport:sample']">预览 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-printer" :disabled="multiple" @click="handleSampleBatch" v-hasPermi="['report:reportPrint:healthReport:sampleBatch']">批量打印 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" @click="handleBackOut" icon="el-icon-plus" :disabled="multiple" v-hasPermi="['report:reportPrint:healthReport:backout']">已打印 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-edit-outline" :disabled="multiple" @click="handleelectronicReportHandover" v-hasPermi="['report:reportPrint:healthReport:electronicReportHandover']">电子版交接 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-coin" :disabled="multiple" @click="handleCreatePrivate" v-hasPermi="['report:reportPrint:healthReport:createPrivate']">隐私报告生成 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-coin" :disabled="single || selection[0].containsPrivate != '是' || selection[0].orgDepartsubb != 1" @click="handleViewPrivate" v-hasPermi="['report:reportPrint:healthReport:previewPrivate']">隐私报告预览 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit-outline" :disabled="multiple" @click="handleCreateTempReport" v-hasPermi="['report:reportPrint:healthReport:createReport']">临时报告生成 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-search" :disabled="single || selection[0].lsbg != 1" @click="handlePreviewTempReport" v-hasPermi="['report:reportPrint:healthReport:previewReport']">临时报告预览</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-edit-outline" :disabled="single || selection[0].lsbg != 1" @click="handleReviewHandoverTemp" v-hasPermi="['report:reportPrint:healthReport:reviewhandovertemp']">临时报告终审交接 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-edit-outline" @click="handleReviewHandover" v-hasPermi="['report:reportPrint:healthReport:reviewhandover']">终审交接 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-share" :disabled="multiple" @click="handleShare" v-hasPermi="['report:reportPrint:healthReport:handleShare']">分享报告</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-download" :disabled="single" @click="handleDownloadPdf" v-hasPermi="['report:reportPrint:healthReport:downloadPdf']">下载PDF
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-download" :disabled="multiple" @click="handleDownload" v-hasPermi="['report:reportPrint:healthReport:handleDownload']">批量下载pdf</el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border ref="tableList" v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick" @row-dblclick="rowDblClick">
        <el-table-column type="selection" align="center" />
        <el-table-column label="序列" prop="rownum" width="65" align="center" />
        <el-table-column label="体检号" min-width="150px" prop="patientcode" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="性别" prop="idSex" width="80" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.idSex == 0">男</el-tag>
            <el-tag v-else type="danger">女</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="age" width="60" align="center" show-overflow-tooltip />
        <el-table-column label="登记时间" prop="dateregister" width="160" align="center" show-overflow-tooltip />
        <el-table-column label="生成人" prop="generateName" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="生成时间" prop="generateDate" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="生成报告" prop="createReportNum" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="!scope.row.createReportNum" type="danger">未生成</el-tag>
            <el-tag type="success" v-else>已生成</el-tag>
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
            <span v-if="scope.row.bgzt == -1">总检未开始</span>
            <span v-else-if="scope.row.bgzt == 0">总检开始</span>
            <span v-else-if="scope.row.bgzt == 1">总检完成</span>
            <span v-else-if="scope.row.bgzt == 2">报告已打印</span>
            <span v-else-if="scope.row.bgzt == 3">报告一审通过</span>
            <span v-else-if="scope.row.bgzt == 4">报告一审不通过</span>
            <span v-else-if="scope.row.bgzt == 5">二审通过</span>
            <span v-else-if="scope.row.bgzt == 6">二审不通过</span>
            <span v-else-if="scope.row.bgzt == 7">终审通过</span>
            <span v-else-if="scope.row.bgzt == 8">终审不通过</span>
            <span v-else-if="scope.row.bgzt == 9">报告已交接</span>
            <span v-else-if="scope.row.bgzt == 10">报告已通知</span>
            <span v-else-if="scope.row.bgzt == 11">报告已领取</span>
            <span v-else></span>
          </template>
        </el-table-column>
        <el-table-column label="隐私报告状态" min-width="120px" prop="orgDepartsubb" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.orgDepartsubb == 1" type="success">已生成</el-tag>
            <el-tag v-else type="danger">未生成</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="临时报告状态" min-width="120px" prop="lsbg " align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.lsbg == 1" type="success">已生成</el-tag>
            <el-tag v-else type="danger">未生成</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="报告发放形式" min-width="120px" prop="informway" align="center" show-overflow-tooltip />
        <el-table-column label="开单医师" min-width="100px" prop="kdys" align="center" show-overflow-tooltip />
        <el-table-column label="单位" min-width="120px" prop="orgName" align="center" show-overflow-tooltip />
        <el-table-column label="订单号" min-width="100px" prop="numorgresv" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.numorgresv == -1"></span>
            <span v-else>{{ scope.row.numorgresv }}</span>
          </template>
        </el-table-column>
        <el-table-column label="错误信息" min-width="120px" prop="generateHint" align="center" show-overflow-tooltip />
        <el-table-column label="含隐私项目" min-width="100px" prop="containsPrivate" align="center" show-overflow-tooltip />
        <el-table-column label="操作" fixed="right" width="120" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="handleView(scope.row)" v-hasPermi="['report:reportPrint:healthReport:viewreport']">查看报告</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" :pageSizes="[20, 50, 100, 200]" />
    <zj-items ref="zjItems" @getList="getList"></zj-items>
    <!-- 分享报告 -->
    <share-report ref="shareReport"></share-report>
    <!-- 批量下载 -->
    <download ref="download"></download>
    <!-- 单独下载 -->
    <onedownload ref="onedownload"></onedownload>
  </div>
</template>
<script>
import { getListData, printAlready, electronicReport, sampleBatch, createReport, createPrivate, zsjjTempApi, downloadPDF } from '@/api/report/report_print/health_report'
import { createTemp } from '@/api/search/check_query.js'
import { getBranchData } from '@/api/statistical/professionchecks/conclusion_table.js' //获取分中心
import { getUserCid } from '@/api/system/branch.js'
import zjItems from './viewhandover'
import shareReport from './share_report'
import download from './download'
import onedownload from '../health_report/onedownload'

export default {
  name: 'HealthReport',
  components: { zjItems, shareReport, download, onedownload },
  data() {
    return {
      //体检号
      patientcode: [],
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
        date: undefined,
        patientcode: '',
        patientname: '',
        khdwmcid: '',
        idOpendoctor: '',
        isPrint: 0,
        isNotPrint: 0,
        containsPrivate: 0,
        containOldSystem: 0,
        showAllImage: 0,
        showNotToatal: 1,
        current: 1,
        size: 50,
        autoFill: true,
        branchIds: undefined,
      },
      // 展示全部图像
      // 表格展示数据
      tableList: [],
      // 客户列表信息
      selectData: {
        placeholder: '请输入输入码选择',
        value: '单位名称', //第二列标题
        url: '/sell/customer/getAllOrg', //请求连接
        selectWidth: 160, //选择器宽度（选填，默认230）不加px,传100%则为100%
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 开单医师信息
      selectData2: {
        placeholder: '请输入输入码选择',
        value: '医师名称', //第二列标题
        url: '/member/previewingTrack/getKdys', //请求连接
        selectWidth: 160, //选择器宽度（选填，默认230）不加px,传100%则为100%
        secondName: 'name', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
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
    let toData = new Date(new Date().toLocaleDateString()).getTime()
    let past7daysStart = toData - 30 * 3600 * 24 * 1000
    this.queryParams.date = [this.$getDate(past7daysStart).split(' ')[0], this.$getDate().split(' ')[0]]
    this.handleQuery()
  },
  mounted() {
    document.addEventListener('visibilitychange', () => {
      if (document.visibilityState === 'visible') {
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
    // 下载PDF
    // 只有一个时不压缩
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
    // 选中客户单位
    selectChange(value) {
      this.queryParams.khdwmcid = value.id
      this.handleQuery()
    },
    // 选中开单医师
    selectChange2(value) {
      this.queryParams.idOpendoctor = value.name
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
      this.handleView(row)
    },
    //速成/生成检验报告
    handleCreateReport(isJy) {
      this.$modal
        .confirm('确定生成报告?', '生成')
        .then(() => {
          var data = {
            dh: 0,
            isJy: isJy,
            patientcode: this.patientcode,
            showAllImage: this.queryParams.showAllImage,
          }
          const loading = this.$loading({
            lock: true,
            text: '报告生成中',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)',
          })
          createReport(data)
            .then((response) => {
              loading.close()
              if (response.msg.indexOf('成功') != -1) {
                this.$modal.msgSuccess('报告生成成功！', '提示')
                this.getList()
              } else {
                this.$modal.alertWarning(response.msg, '提示')
              }
            })
            .catch(() => {
              loading.close()
            })
        })
        .catch(() => {
          loading.close()
        })
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
        type: 0,
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
      this.loading = true
      printAlready(this.patientcode.join(','))
        .then(() => {
          this.$message({
            message: '修改成功',
            type: 'success',
          })
          this.getList()
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 电子版交接
    handleelectronicReportHandover() {
      var i = 0
      for (var i in this.selection) {
        if (this.selection[i].informway == '电子版报告') {
          if (this.selection[i].bgzt != 9) {
            if (i == this.selection.length - 1) {
              electronicReport(this.ids).then((response) => {
                this.$message({
                  message: '下载成功',
                  type: 'success',
                })
              })
            }
          } else {
            this.$modal.alertWarning('请选择未交接报告')
            return
          }
        } else {
          this.$modal.alertWarning('请选择电子版报告进行交接')
          return
        }
      }
    },
    //隐私报告生成
    handleCreatePrivate() {
      for (var i in this.selection) {
        var row = this.selection[i]
        if (row.containsPrivate != '是') {
          this.$modal.alertWarning('体检号' + row.patientcode + '没有隐私项目，不能生成隐私报告。', '提示')
          return
        }
      }
      const loading = this.$loading({
        lock: true,
        text: '报告生成中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      createPrivate(this.patientcode)
        .then((response) => {
          loading.close()
          if (response.msg.indexOf('成功') != -1) {
            this.$modal.alertSuccess('隐私报告生成成功！', '提示')
            this.getList()
          } else {
            this.$modal.alertWarning(response.msg, '提示')
          }
        })
        .catch(() => {
          loading.close()
        })
    },
    //隐私报告预览
    handleViewPrivate() {
      var data = this.selection[0]
      //现在都用pdf了,没有pdf就去重新生成
      if (!data.ysPdf) {
        this.$alert('请先生成隐私报告', '提示')
        return
      }
      let url = this.$getCookie('imgPath') + data.ysPdf
      var query = {
        url: encodeURIComponent(url),
      }
      let routeUrl = this.$router.resolve({
        name: 'PreviewReport',
        query,
      })
      window.open(routeUrl.href)
    },
    //临时报告生成
    handleCreateTempReport() {
      this.$modal
        .confirm('确定生成临时报告?', '生成')
        .then(() => {
          let patientcode = this.selection.map((item) => item.patientcode)
          var data = {
            dh: 0,
            patientcode: patientcode.join(),
          }
          const loading = this.$loading({
            lock: true,
            text: '报告生成中',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)',
          })
          createTemp(data)
            .then((response) => {
              loading.close()
              if (response.msg.indexOf('成功') != -1) {
                this.$modal.alertSuccess('报告生成成功！', '提醒')
                this.getList()
              } else {
                this.$modal.alertWarning(response.msg, '提醒')
              }
            })
            .catch(() => {
              loading.close()
            })
        })
        .catch(() => {
          loading.close()
        })
    },
    //临时报告预览
    handlePreviewTempReport() {
      var data = this.selection[0]
      if (data.lsbg != 1) {
        this.$alert('请先生成临时报告报告', '提示')
        return
      }
      if (data.lsPdf) {
        let url = this.$getCookie('imgPath') + data.lsPdf
        var query = {
          url: encodeURIComponent(url),
        }
        let routeUrl = this.$router.resolve({
          name: 'PreviewReport',
          query,
        })
        window.open(routeUrl.href)
        return
      }
      var query = {
        patientcode: this.selection[0].patientcode,
        idExamtype: this.selection[0].idExamtype,
      }
      let routeUrl = this.$router.resolve({
        name: 'IndividualTemporaryReport',
        query: query,
      })
      window.open(routeUrl.href)
    },
    // 临时报告终审交接
    handleReviewHandoverTemp() {
      this.loading = true
      zsjjTempApi({
        ids: this.selection.map((item) => item.pid).join(','),
      })
        .then(() => {
          this.$message.success('操作成功')
          this.getList()
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    //终审交接
    handleReviewHandover() {
      this.$refs.zjItems.handleShow(this.patientcode.join(',') || '')
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
    // 搜索
    secHandleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.$refs.khdwmcid.initData()
      this.$refs.idOpendoctor.initData()
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 预览报告
    handleView(row) {
      var data = row || this.selection[0]
      if (!data.createReportNum) {
        this.$alert('请先生成报告', '提示')
        return
      }
      if (data.bgzt < 1) {
        this.$alert('请选择总检完成阶段之后的报告', '提示')
        return
      }
      // if (data.urlPdf && !(this.$getCookie('cid') == 'ff8080817ee18637017ee77dc0322d8c' || window.location.href.includes('http://XXX.XXX.XXX'))) {
      if (data.urlPdf) {
        printAlready(data.patientcode)
        let url = this.$getCookie('imgPath') + data.urlPdf
        var query = {
          url: encodeURIComponent(url),
        }
        let routeUrl = this.$router.resolve({
          name: 'PreviewReport',
          query,
        })
        window.open(routeUrl.href)
        return
      }
      var query = {
        patientcode: data.patientcode,
      }
      let routeUrl = this.$router.resolve({
        name: 'IndividualHealthReport',
        query,
      })
      window.open(routeUrl.href)
    },
  },
}
</script>
<style scoped>
.health-report /deep/ .cell .el-tooltip {
  width: auto;
}
</style>
