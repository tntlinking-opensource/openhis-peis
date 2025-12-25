<!-- 职业报告交接 麦沃德科技 开发人:清风/暴雨 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryParams" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="体检号" prop="patientcode">
        <el-input placeholder="请输入体检号" clearable v-model="queryParams.patientcode" style="width: 145px" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="订单号" prop="ddh">
        <el-input placeholder="请输入订单号" clearable v-model="queryParams.ddh" style="width: 145px" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="体检者姓名" prop="name">
        <el-input placeholder="请输入体检者姓名" clearable v-model="queryParams.name" style="width: 150px" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="交接日期" prop="date">
        <el-date-picker v-model="queryParams.date" style="width: 288px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="单位名称" prop="numorgresv">
        <el-input placeholder="请输入单位名称" clearable v-model="queryParams.numorgresv" style="width: 145px" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="销售经理" prop="xsjlid">
        <input-select :selectData="xsjlidData" selectWidth="150" @change="xsjlidDataChange" @keyup.enter.native="handleQuery"></input-select>
      </el-form-item>
      <el-form-item label="交接人" prop="xsjlid">
        <el-input placeholder="请输入交接人输入码" clearable v-model="queryParams.joinPersonId" style="width: 150px" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item>
        <el-checkbox clearable v-model="queryParams.isHandover">已交接</el-checkbox>
        <el-checkbox clearable v-model="queryParams.isNotHandover" style="width: 80px">未交接</el-checkbox>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-circle-check" size="mini" @click="handerWindow()" v-hasPermi="['report:reportHandover:careerHandover:handover']">交接 </el-button>
        <el-button type="danger" plain icon="el-icon-circle-close" size="mini" @click="returnHanderWindow()" v-hasPermi="['report:reportHandover:careerHandover:unhandover']">反交接 </el-button>
        <el-button type="success" plain icon="el-icon-edit-outline" size="mini" @click="editWindow()" v-hasPermi="['report:reportHandover:careerHandover:edit']" :disabled="multiple">编辑 </el-button>
      </el-col>
    </el-row>

    <div class="table-box" style="display: flex; flex-direction: row">
      <el-table ref="tableData" :data="tableData" style="display: inline-block; flex-grow: 1" v-loading="loading" :border="true" :stripe="true" height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" align="center" fixed="left"></el-table-column>
        <el-table-column type="index" label="序列" width="55" align="center" fixed="left"></el-table-column>
        <el-table-column prop="status" label="交接状态" min-width="140px" align="center" fixed="left">
          <template slot-scope="scope">
            <span :style="'color: ' + auditColor(scope.row.status)">
              {{ auditStatus(scope.row.status) }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="patientcode" label="体检号" min-width="140px" align="center" fixed="left"></el-table-column>
        <el-table-column prop="patientname" label="姓名" min-width="80px" align="center" fixed="left"></el-table-column>
        <el-table-column prop="sex" label="性别" min-width="80px" align="center">
          <template slot-scope="scope">
            <div v-if="scope.row.sex == 0">男</div>
            <div v-if="scope.row.sex == 1">女</div>
            <div v-if="scope.row.sex == 2">通用</div>
          </template>
        </el-table-column>
        <el-table-column prop="age" label="年龄" min-width="80px" align="center"></el-table-column>
        <el-table-column prop="orgName" label="单位" min-width="160px" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="joinPersonMan" label="交接人" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="revPersonMan" label="承接人" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="revTime" label="交接时间" min-width="120px" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="chestNum" label="柜子号" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="grantId" label="发送方式" min-width="120px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <div v-for="item in options" :key="item.id">
              <span v-if="scope.row.grantId == item.id">{{ item.methodName }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="dateregister" label="登记时间" min-width="120px" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="phone" label="电话" min-width="120px" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="doctorapply" label="开单医生" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="numorgresv" label="任务" min-width="100px" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="datefinalexamed" label="总检时间" min-width="120px" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="doctorfinalNameR" label="总检大夫" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="datereportprinted" label="打印时间" min-width="120px" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="printMan" label="打印人" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="firstMan" label="一审人" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="firstTime" label="一审时间" min-width="120px" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="secondMan" label="二审人" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="secondTime" label="二审时间" min-width="120px" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="lastMan" label="终审人" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="lastTime" label="终审时间" min-width="120px" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="操作" align="center" min-width="120px" fixed="right" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button type="text" style="color: #ffba00" @click="editWindowc(scope.row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <div style="min-height: 230px; display: flex; flex-direction: row">
      <div style="min-width: 460px; max-height: 230px; border: 1px solid rgb(225, 225, 225); padding: 16px">
        <el-form>
          <el-form-item style="margin-bottom: 12px">
            <el-button type="primary" icon="el-icon-upload2" @click="handleExport">导出Excel</el-button>
            <el-button type="primary" icon="el-icon-upload2" @click="handleExports">报告交接统计</el-button>
          </el-form-item>
          <el-form-item style="margin-bottom: 12px">
            <div style="width: 100%; min-height: 72px; border-radius: 5px; background-color: rgb(247, 248, 250); padding-left: 12px">
              <div>今日已交接</div>
              <div style="font-size: 24px; color: rgb(0, 89, 255); font-weight: 600">{{ this.overhand[0] }}</div>
            </div>
          </el-form-item>
          <el-form-item style="margin-bottom: 12px">
            <div style="width: 100%; min-height: 72px; border-radius: 5px; background-color: rgb(247, 248, 250); padding-left: 12px">
              <div>今日未交接</div>
              <div style="font-size: 24px; color: rgb(0, 89, 255); font-weight: 600">{{ this.notoverhand[0] }}</div>
            </div>
          </el-form-item>
        </el-form>
      </div>

      <div style="flex: 1; min-height: 230px; margin-left: 16px; border: 1px solid rgb(225, 225, 225)">
        <!-- 折线图 -->
        <div class="charts-box" style="min-width: 460px; max-height: 230px">
          <div class="echart" id="mychart" :style="myChartStyle"></div>
        </div>
      </div>
    </div>
    <el-dialog title="编辑" :visible.sync="open" width="400px" style="min-height: 250px" append-to-body :close-on-click-modal="false">
      <el-form :inline="true" label-width="80px" style="max-width: 400px; margin-left: 0">
        <el-form-item label="柜子号">
          <el-input style="width: 260px" v-model="editForm.chestNum" placeholder="请输入"></el-input>
        </el-form-item>
        <el-form-item label="发放方式">
          <el-select v-model="editForm.grantId" placeholder="请选择发放方式" clearable style="width: 260px">
            <el-option v-for="item in options" :label="item.methodName" :value="item.id" :key="item.id"> </el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="save">保存</el-button>
        <el-button type="primary" plain @click="tcreset">重置</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <handerItems ref="handerItems" @getFatherList="getList"></handerItems>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import handerItems from '../career_handover/hander.vue'
import { getChartData, getListData, getBoxData, saveEdit } from '@/api/report/health_handover/profession_handover'
export default {
  name: 'Career_handover',
  components: { handerItems },
  data() {
    return {
      showSearch: true,
      loading: false,
      ids: [], //选中的数组
      single: true, //必选且单选
      multiple: true, //必选
      total: 10, //总数
      queryParams: {
        current: 1, //初始页数
        size: 10, //页值
        diseaseHealth: 1, //职业
        patientcode: undefined,
        name: undefined,
        date: undefined,
        isHandover: false,
        isNotHandover: false,
        xsjlid: undefined,
        ids: undefined,
        ddh:undefined
      },
      form: {
        patientcode: '',
        name: '',
        startTime: '',
        valueDate: '', //startTime和endTime
        numorgresv: '',
        joinPersonId: '',
      },
      editForm: {
        chestNum: '',
        grantId: '',
        diseaseHealth: 1, //职业
      },
      xsjlidData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '名称', //第二列标题
        url: '/report/healthAssociate/getAllUser', //请求连接
        bindValue: '',
        queryData: 'key',
        firstName: 'inputCode',
        secondName: 'username',
      },
      // 发送方式
      options: [],
      // 审核状态
      reportStatus: [
        { id: 0, text: '总检开始' },
        { id: 1, text: '总检完成' },
        { id: 2, text: '报告已打印' },
        { id: 3, text: '一审通过' },
        { id: 4, text: '一审不通过' },
        { id: 5, text: '二审通过' },
        { id: 6, text: '二审不通过' },
        { id: 7, text: '终审通过' },
        { id: 8, text: '终审不通过' },
        { id: 9, text: '报告已交接' },
        { id: 10, text: '报告已通知' },
        { id: 11, text: '报告已领取' },
      ],
      tableData: [],
      count: 9,
      counts: 100,
      open: false,
      // 折线图
      myChartStyle: { width: '100%', height: '100%', minHeight: '230px' },
      month: ['今日', '昨日', '两天前', '三天前', '四天前', '五天前', '六天前'],
      year: 2019,
      overhand: 0,
      notoverhand: 0,
    }
  },
  created() {
    this.queryParams.date = [this.$getDate().split(' ')[0], this.$getDate().split(' ')[0]]
    this.handleQuery()
    this.getChart()
    this.getBoxData()
  },
  mounted() {
    this.initEcharts()
  },
  methods: {
    //查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams).then((response) => {
        this.tableData = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    //获取发放方式
    getBoxData() {
      getBoxData().then((response) => {
        this.options = response.data
      })
    },
    //销售经理
    xsjlidDataChange(value) {
      this.queryParams.xsjlid = value.id
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
      this.resetForm('queryParams')
      this.handleQuery()
    },
    //编辑
    editWindow(row) {
      for (var i in this.rows) {
        if (this.rows[i].status && this.rows[i].status != 9) {
          this.$alert('请选择已交接的记录!', '提醒', {
            confirmButtonText: '确定',
            type: 'warning',
          })
          return
        }
      }

      this.open = true

      this.editForm.ids = String(this.ids)

      if (!this.single) {
        ;(this.editForm.chestNum = this.rows[0].chestNum), (this.editForm.grantId = this.rows[0].grantId)
      } else {
        ;(this.editForm.chestNum = undefined), (this.editForm.grantId = undefined)
      }
    },
    //操作编辑
    editWindowc(row) {
      if (row.id) {
        if (row.status && row.status != 9) {
          this.$alert('请选择已交接的记录!', '提醒', {
            confirmButtonText: '确定',
            type: 'warning',
          })
          return
        }
      }

      this.open = true
      this.editForm.ids = row.id
      this.editForm.chestNum = row.chestNum
      this.editForm.grantId = row.grantId
    },
    //保存
    save() {
      saveEdit(this.editForm).then((response) => {
        this.loading = false
        this.getList()
        this.open = false
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
      if (col.type != 'selection') 
      this.$refs.tableData.clearSelection()
      this.$refs.tableData.toggleRowSelection(row)
    },
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
        var g = this.reportStatus[i]
        if (g.id == value) {
          if (value == 4 || value == 6 || value == 8) {
            return '#FF2727'
          } else if (value == 7) {
            return '#00AF66'
          } else if (value == 3 || value == 5) {
            return '#0059FF'
          } else if (value > 9) {
            return '#FF7A00'
          } else {
            return ''
          }
        }
      }
      return ''
    },
    //获取折线图数据
    getChart() {
      getChartData().then((response) => {
        this.overhand = response.data.overhand
        this.notoverhand = response.data.notoverhand
        this.loading = false
      })
    },
    handerWindow() {
      this.$refs.handerItems.handerWindow(0, this.rows)
    },
    returnHanderWindow() {
      this.$refs.handerItems.handerWindow(1, this.rows)
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download('/report/professionAssociate/export', {...this.queryParams}, `职业报告交接.xlsx`)
    },
    /** 报告交接统计 */
    handleExports() {
      this.download('/report/professionAssociate/exportStatistics', {...this.queryParams}, `报告交接统计.xlsx`)
    },
    cancel() {
      this.open = false
      this.tcreset()
    },
    tcreset() {
      this.editForm.chestNum = ''
      this.editForm.grantId = ''
    },
    //折线图
    initEcharts() {
      // 基于准备好的dom，初始化echarts实例
      var month = this.month
      for (var i = 0; i < month.length; i++) {
        month[i] = month[i]
      }
      var year = this.year
      var myChart = echarts.init(document.getElementById('mychart'))
      // 指定图表的配置项和数据
      var option = {
        color: ['#41E4BB', '#1890FF'],
        // title: {
        //     text: '销售同期:'+month[6]
        // },
        tooltip: {
          trigger: 'axis',
        },
        legend: {
          data: ['已交接', '终审未交接'],
        },
        toolbox: {
          show: true,
          feature: {
            magicType: { type: ['line', 'bar'] },
            saveAsImage: {},
          },
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: month,
        },
        yAxis: [
          {
            // name:'销售额(元)',
            type: 'value',
            axisLabel: {
              formatter: '{value}',
            },
            //坐标轴刻度设置为虚线
            splitLine: {
              show: true,
              lineStyle: {
                type: 'dashed', // important
              },
            },
            splitNumber: 3,
            axisLine: { show: false },
            axisTick: { show: false },
          },
        ],
        series: [
          {
            name: '已交接',
            type: 'line',
            data: this.overhand,
          },
          {
            name: '终审未交接',
            type: 'line',
            data: this.notoverhand,
          },
        ],
      }
      myChart.setOption(option)
    },
  },
}
</script>
