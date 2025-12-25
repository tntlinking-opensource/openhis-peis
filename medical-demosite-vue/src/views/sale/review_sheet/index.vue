<!-- 复查单打印 麦沃德科技 开发人: 清风/半夏 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item label="选择日期" prop="date">
        <el-date-picker v-model="queryParams.date" style="width: 360px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="团体名称" prop="idOrg">
        <input-select :selectData="nameData" selectWidth="230" @change="nameChange"></input-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-printer" :disabled="single" plain @click="handelPrint" v-hasPermi="['sale:reviewSheet:print']">打印</el-button>
      </el-col>
    </el-row>
    <div class="table-box">
      <el-table ref="tableList" :data="tableList" v-loading="loading" align="center" :border="true" :stripe="true" @selection-change="handleSelectionChange" @row-click="rowClick" row-key="id" height="100%">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column type="index" width="60" label="序列" align="center" />
        <el-table-column prop="patientcode" min-width="140" label="体检号" align="center" />
        <el-table-column prop="orgName" min-width="200" label="单位名称" align="center" show-overflow-tooltip />
        <el-table-column prop="callbackStation" min-width="120" label="复查状态" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.callbackStation == 1">已复查</el-tag>
            <el-tag type="danger" v-else>未复查</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="dateFrom" min-width="160" label="复查起始时间" align="center" />
        <el-table-column prop="dateTo" min-width="160" label="复查终止时间" align="center" />
        <el-table-column prop="itemsName" min-width="200" label="复查收费项目" align="center" show-overflow-tooltip />
        <el-table-column prop="patientname" min-width="120" label="姓名" align="center" />
        <el-table-column prop="idcardno" min-width="180" label="身份证号" align="center" />
        <el-table-column prop="idSex" min-width="80" label="性别" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.idSex == 0">男</span>
            <span v-if="scope.row.idSex == 1">女</span>
          </template>
        </el-table-column>
        <el-table-column prop="age" min-width="80" label="年龄" align="center" />
        <el-table-column prop="noticeOfProceedingText" min-width="260" label="复查注意事项" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 弹窗 -->
    <el-dialog title="复查打印" :visible.sync="open" width="886px" height="550px" append-to-body :close-on-click-modal="false">
      <el-button size="mini" icon="el-icon-plus" type="primary">打印</el-button>
      <div class="print-container"></div>
    </el-dialog>
    <el-dialog title="复查打印" :visible.sync="openPrint" width="886px" height="550px" append-to-body :close-on-click-modal="false">
      <div class="print-container" ref="print">
        <div class="container-fc">
          <div class="fc-title">青岛沃德国际健康体检中心-复查打印单</div>
          <div class="fc-content">
            <div class="content-img">
              <img class="info-barcode" id="barcode" />
            </div>
            <div class="content-code">
              <span>{{ reportData.patientcode }}</span>
            </div>
          </div>
        </div>
        <div class="container-table">
          <table>
            <!-- 标题行 -->
            <tr>
              <td>姓名</td>
              <td>{{ reportData.patientname }}</td>
              <td>性别</td>
              <td>
                <span v-if="reportData.idSex == 0">男</span>
                <span v-else-if="reportData.idSex == 1">女</span>
              </td>
              <td>年龄</td>
              <td>{{ reportData.age }}</td>
            </tr>
            <tr>
              <td>证件号码</td>
              <td colspan="3">{{ reportData.idcardno }}</td>
              <td colspan="2">复查</td>
            </tr>
            <tr>
              <td>复查开始时间</td>
              <td colspan="2">{{ reportData.dateFrom }}</td>
              <td>复查结束时间</td>
              <td colspan="2">{{ reportData.dateTo }}</td>
            </tr>
          </table>
        </div>
        <div class="sf-table">
          <table>
            <!-- 标题行 -->
            <tr>
              <td style="font-size: 16px; font-weight: 600">检查科室</td>
              <td style="font-size: 16px; font-weight: 600">收费项目</td>
            </tr>
            <!-- 内容行 -->
            <tr v-for="(item, index) in reportData.items" :key="index">
              <td>{{ item.ks }}</td>
              <td>{{ item.xm }}</td>
            </tr>
          </table>
        </div>
        <!-- 打印按钮 -->
        <el-tag class="print-fixed" @click="printMethods">确认打印</el-tag>
        <!-- 固定 -->
        <div class="absolute-bottom">
          <div class="tips">呵护健康, 从沃德开始</div>
          <div class="address">地址: {{ reportData.fzxAddress }} {{ reportData.dateTo }}</div>
        </div>
        <div class="absolute-top-left">
          <img :src="logo" alt="" />
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPrint, printData } from '@/api/sale/review_sheet'
import imgLogo from '@/assets/logo/logo.png'
import JsBarcode from 'jsbarcode'
export default {
  name:'Review_sheet',
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
        date: undefined,
        startTime: undefined,
        endTime: undefined,
        patientcode: undefined,
        idOrg: undefined,
      },
      // 团体名称
      nameData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '团检单位', //第二列标题
        url: '/abteilung/sectionResultPlan/getOrgs', //请求连接`
        bindValue: '',
        firstName: 'khdwsrm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
      },
      // 排检表格数据
      tableList: [],
      // 表单参数
      form: {},
      // 弹出层加载动画
      popLoading: false,
      // 是否显示弹出层
      open: false,
      openPrint: false,
      logo: imgLogo,
      reportData: {},
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      listPrint(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
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
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 选择团体名称
    nameChange(value) {
      this.queryParams.idOrg = value.id
      this.nameData.bindValue = value.khdwmc
    },
    // 打印
    handelPrint() {
      this.openPrint = true
      let ids = ''
      for (let index in this.ids) {
        ids = ids + this.ids[index] + ','
      }
      ids = ids.substring(0, ids.length - 1)
      printData(ids).then((res) => {
        this.reportData = res.data[0]
        this.$nextTick(() => {
          JsBarcode('#barcode', res.data[0].patientcode, {
            format: 'CODE39', //条形码的格式
            width: 1, //线宽
            height: 40, //条码高度
            fontSize: 16,
            lineColor: '#000', //线条颜色
            displayValue: false, //是否显示文字
            margin: 2, //设置条形码周围的空白区域
          })
        })
      })
    },
    // 主页-打印
    windowPrint() {
      this.open = true
    },
    // 打印
    printMethods() {
      this.$print(this.$refs.print)
    },
  },
}
</script>
<style lang="scss" scoped>
.print-fixed {
  position: absolute;
  left: 50%;
  bottom: -10px;
  transform: translateX(-50%);
  text-align: center;
  width: 120px;
  height: 35px;
  line-height: 35px;
  cursor: pointer;
}

.print-container {
  margin-top: 12px;
  min-height: 400px;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  padding: 0 40px;
  .container-fc {
    .fc-title {
      color: #b37d1e;
      font-size: 20px;
      font-weight: 600;
    }
    .fc-content {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin: 10px 0;
      // .content-img{

      // }
      .content-code {
        margin: 0 10px;
      }
    }
  }
  .container-table {
    table {
      width: 100%;
      border: 1px solid #333333;
      border-collapse: collapse;
      tr {
        td {
          border: 1px solid #333333;
          border-collapse: collapse;
          padding: 6px;
          min-width: 68px;
        }
      }
    }
  }
  .absolute-top-left {
    position: absolute;
    top: 0;
    left: 20px;
    width: 120px;
    height: 100px;
    img {
      width: 100%;
      height: 100%;
    }
  }
  .absolute-bottom {
    display: none;
  }
  .sf-table {
    width: 100%;
    margin-top: 40px;
    table {
      width: 100%;
      border: 1px solid #333333;
      border-collapse: collapse;
      tr {
        td {
          border: 1px solid #333333;
          border-collapse: collapse;
          padding: 6px;
          min-width: 68px;
        }
      }
    }
  }
}

@media print {
  .print-fixed {
    display: none;
  }
  .absolute-bottom {
    width: 100%;
    position: fixed;
    bottom: 0;
    border-top: 2px solid #333333;
    display: flex !important;
    justify-content: space-between;
    .address {
      margin-right: 20px;
    }
  }
  .print-container {
    padding: 0;
  }
  .container-table {
    width: calc(100% - 260px) !important;
  }
  .absolute-top-left {
    left: 0 !important;
  }
}
</style>
