<!-- 每日体检明细 开发人：麦沃德科技暴雨/矢北/予安 -->
<template>
  <div div class="app-container flex-direction-column general-inspection-day" style="height: 100%; min-height: auto; overflow: auto">
    <!--页面-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="分中心" prop="branchIds">
        <el-select v-model="queryParams.branchIds" placeholder="请选择分中心" clearable style="width: 160px" @change="handleQuery">
          <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="体检日期" prop="date">
        <el-date-picker v-model="queryParams.date" style="width: 260px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" @change="handleQuery"></el-date-picker>
      </el-form-item>
      <el-form-item label="体检团体" prop="idOrg">
        <el-input v-model="queryParams.idOrg" placeholder="请输入体检团体" clearable style="width: 140px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="订单号" prop="ddh">
        <el-input v-model="queryParams.ddh" placeholder="请输入订单号" clearable style="width: 130px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 140px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 120px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="SABC级别" prop="sabc">
        <!--        <el-input v-model="queryParams.sabc" placeholder="请输入客户SABC级别" clearable style="width: 120px" @keyup.enter.native="handleQuery" />-->
        <el-select v-model="queryParams.sabc" placeholder="请选择SABC级别" clearable style="width: 100px" @change="handleQuery">
          <el-option label="S" value="S" />
          <el-option label="A" value="A" />
          <el-option label="B" value="B" />
          <el-option label="C" value="C" />
          <el-option label="空" value="" />
          <el-option label="全部" value="全部" />
        </el-select>
      </el-form-item>
      <el-form-item label="团检/个检" prop="useCodeHiden">
        <el-select v-model="queryParams.useCodeHiden" placeholder="请选择" clearable style="width: 100px" @change="handleQuery">
          <el-option label="个检" :value="0" />
          <el-option label="团检" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="体检者类型" prop="idPatientclass">
        <el-select v-model="queryParams.idPatientclass" placeholder="请选择" clearable style="width: 120px" @change="handleQuery">
          <el-option v-for="item in examType" :key="item.levelId" :label="item.levelName" :value="item.levelId" />
        </el-select>
      </el-form-item>
      <el-form-item label="开单医师" prop="idOpendoctor">
        <input-select :selectData="idoctorData" selectWidth="160" @change="idoctorDataChange"></input-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
      <!-- <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button> -->
      <el-button type="primary" plain size="mini" @click="handleExport(1)" v-hasPermi="['search:everyExaminer:export']">导出体检明细</el-button>
      <el-button style="margin-top: -10px; margin-bottom: 8px" type="primary" plain size="mini" @click="handleExport(2)" v-hasPermi="['search:everyExaminer:export']">导出体检合计</el-button>
    </el-form>
    <!-- 上方部分 -->
    <div class="table-box" style="height: 100%">
      <el-row :gutter="5" style="height: 60%">
        <el-col :span="15" style="height: 100%">
          <!-- 左侧表格 -->
          <div class="table-box no-padding-table" style="height: calc(100% - 60px)">
            <el-table border ref="leftTable" v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
              <el-table-column fixed type="selection" align="center" />
              <el-table-column fixed label="序列" type="index" width="55" align="center" />
              <el-table-column fixed label="体检号" prop="patientcode" min-width="140px" align="center" show-overflow-tooltip />
              <el-table-column fixed label="姓名" prop="patientname" min-width="90px" align="center" show-overflow-tooltip />
              <el-table-column fixed label="SABC级别" prop="sabc" min-width="90px" align="center" show-overflow-tooltip />
              <el-table-column label="原价合计" prop="yjhj" min-width="120px" align="center" show-overflow-tooltip />
              <el-table-column label="缴费金额" align="center" min-width="120px">
                <el-table-column label="应付合计" prop="moneyamountpaid" min-width="120px" align="center" show-overflow-tooltip />
                <el-table-column label="记账未结" prop="jzwjdc" min-width="120px" align="center" show-overflow-tooltip />
              </el-table-column>
              <el-table-column label="开单医师" prop="doctorapply" min-width="120px" align="center" show-overflow-tooltip />
              <el-table-column label="套餐" prop="idTjtc" min-width="140px" align="center" show-overflow-tooltip />
              <el-table-column label="订单号" prop="ddh" min-width="140px" align="center" show-overflow-tooltip />
              <el-table-column label="体检团体" prop="orgName" min-width="140px" align="center" show-overflow-tooltip />
              <el-table-column label="付款方式" prop="idPayway" min-width="120px" align="center" show-overflow-tooltip />
              <el-table-column label="性别" prop="idSex" min-width="60px" align="center" show-overflow-tooltip>
                <template slot-scope="scope">
                  <span v-if="scope.row.idSex == 0">男</span>
                  <span v-else-if="scope.row.idSex == 1">女</span>
                  <span v-else-if="scope.row.idSex == 3">通用</span>
                  <span v-else></span>
                </template>
              </el-table-column>
              <el-table-column label="年龄" prop="age" min-width="60px" align="center" show-overflow-tooltip />
              <el-table-column label="团体预订" prop="fisforprepare" min-width="120px" align="center" show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-tag v-if="scope.row.fisforprepare == 1" type="success">√</el-tag>
                  <span v-else></span>
                </template>
              </el-table-column>
              <el-table-column label="登记" prop="fregistered" min-width="60px" align="center" show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-tag v-if="scope.row.fregistered == 1" type="success">√</el-tag>
                  <span v-else></span>
                </template>
              </el-table-column>
              <el-table-column label="收费" prop="ffeecharged" min-width="60px" align="center" show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-tag v-if="scope.row.ffeecharged == 1" type="success">√</el-tag>
                  <span v-else></span>
                </template>
              </el-table-column>
              <el-table-column label="折扣率" prop="zkl" min-width="120px" align="center" show-overflow-tooltip />
              <el-table-column label="分检完成" prop="freadytofinal" min-width="120px" align="center" show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-tag v-if="scope.row.freadytofinal == 1" type="success">√</el-tag>
                  <span v-else></span> </template
              ></el-table-column>
              <el-table-column label="健康总检完成" prop="ffinalexamed" min-width="120px" align="center" show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-tag v-if="scope.row.ffinalexamed == 1" type="success">√</el-tag>
                  <span v-else></span> </template
              ></el-table-column>
              <el-table-column label="职业总检完成" prop="ffinalexamedZy" min-width="120px" align="center" show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-tag v-if="scope.row.ffinalexamedZy == 1" type="success">√</el-tag>
                  <span v-else></span> </template
              ></el-table-column>
              <el-table-column label="健康报告已打" prop="freportprinted" min-width="120px" align="center" show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-tag v-if="scope.row.freportprinted == 1" type="success">√</el-tag>
                  <span v-else></span> </template
              ></el-table-column>
              <el-table-column label="职业报告已打" prop="freportprintedZy" min-width="120px" align="center" show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-tag v-if="scope.row.freportprintedZy == 1" type="success">√</el-tag>
                  <span v-else></span> </template
              ></el-table-column>
              <el-table-column label="结案" prop="fclosed" min-width="100px" align="center" show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-tag v-if="scope.row.fclosed == 1" type="success">√</el-tag>
                  <span v-else></span> </template
              ></el-table-column>
              <el-table-column label="体检分类" prop="useCodeHiden" min-width="120px" align="center" show-overflow-tooltip>
                <template slot-scope="scope">
                  <span v-if="scope.row.useCodeHiden == 1">团检</span>
                  <span v-else-if="scope.row.useCodeHiden == 0">个检</span>
                  <span v-else></span>
                </template>
              </el-table-column>
              <el-table-column label="登记时间" prop="dateregister" min-width="140px" align="center" show-overflow-tooltip />
              <el-table-column label="登记员" prop="doctorreg" min-width="120px" align="center" show-overflow-tooltip />
              <el-table-column label="电话" prop="phone" min-width="140px" align="center" show-overflow-tooltip />
            </el-table>
          </div>
          <!-- 分页 -->
          <pagination :total="total" :page.sync="page1.current" :limit.sync="page1.size" :page-sizes="[20, 50, 100, 200 ,500]" @pagination="getUpList" />
        </el-col>
        <el-col :span="9" style="height: 80%">
          <!-- 右侧表格 -->
          <div class="table-box no-padding-table" ref="tableBox" style="height: 100%">
            <el-table border v-loading="loading1" :data="tableRightList" height="100%" stripe>
              <el-table-column type="selection" align="center" />
              <el-table-column label="序列" type="index" width="65" align="center" />
              <el-table-column label="科室" prop="idKs" align="center" show-overflow-tooltip />
              <el-table-column label="收费项目" prop="idExamfeeitem" align="center" show-overflow-tooltip />
              <el-table-column label="收费" prop="ffeecharged" align="center" show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-tag v-if="scope.row.ffeecharged == 1" type="success">√</el-tag>
                  <span v-else></span>
                </template>
              </el-table-column>
              <el-table-column label="项目弃检" prop="fgiveup" align="center" show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-tag v-if="scope.row.fgiveup == 1" type="success">√</el-tag>
                  <span v-else></span>
                </template>
              </el-table-column>
              <el-table-column label="项目已检" prop="fexaminated" align="center" show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-tag v-if="scope.row.fexaminated == 1" type="success">√</el-tag>
                  <span v-else></span>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <!-- 分页 -->
          <pagination :total="total2" :page.sync="page2.current" :limit.sync="page2.size" @pagination="getRightList" :page-sizes="[20, 50, 100, 200 ,500]" />
        </el-col>
      </el-row>

      <!-- 下方部分 -->
      <el-row :gutter="5" style="height: 40%">
        <el-col :span="15" style="height: 100%">
          <!-- 左侧表格 -->
          <div class="table-box no-padding-table" style="height: calc(100% - 33px)">
            <el-table border v-loading="loadingDown" :data="downTableList" height="100%" stripe>
              <el-table-column fixed type="selection" align="center" />
              <el-table-column fixed label="序列" type="index" width="65" align="center" />
              <el-table-column fixed label="体检团体" prop="tjtt" min-width="120px" align="center" show-overflow-tooltip />
              <el-table-column label="登记" align="center" min-width="120px">
                <el-table-column label="已登" prop="ydj" min-width="120px" align="center" />
                <el-table-column label="未登" prop="wdj" min-width="120px" align="center" />
              </el-table-column>
              <el-table-column label="客单价" prop="unitprice" min-width="120px" align="center" show-overflow-tooltip />
              <el-table-column label="原价合计" prop="yjhj" min-width="120px" align="center" show-overflow-tooltip />
              <el-table-column label="缴费金额" align="center" min-width="120px">
                <el-table-column label="应付合计" prop="yfhj" min-width="120px" align="center" />
                <el-table-column label="记账未结" prop="jzwj" min-width="120px" align="center" />
              </el-table-column>
              <el-table-column label="折扣率" prop="zkl" min-width="120px" align="center" />
              <el-table-column label="加项" align="center" min-width="120px">
                <el-table-column label="人次" prop="jxrc" min-width="120px" align="center" />
                <el-table-column label="费用" prop="jxfy" min-width="120px" align="center" />
              </el-table-column>
              <el-table-column label="列表人数" align="center" min-width="120px">
                <el-table-column label="健康人数" prop="jkrs" min-width="120px" align="center" />
                <el-table-column label="职业人数" prop="zyrs" min-width="120px" align="center" />
                <el-table-column label="综合人数" prop="zhrs" min-width="120px" align="center" />
                <el-table-column label="复查人数" prop="fcrs" min-width="120px" align="center" />
              </el-table-column>
              <el-table-column label="性别" align="center" min-width="120px">
                <el-table-column label="男" prop="nan" min-width="120px" align="center" />
                <el-table-column label="女" prop="nv" min-width="120px" align="center" />
              </el-table-column>
              <el-table-column label="分检" align="center" min-width="120px">
                <el-table-column label="完成" prop="ffwc" min-width="120px" align="center" />
                <el-table-column label="未完成" prop="fjww" min-width="120px" align="center" />
              </el-table-column>
              <el-table-column label="健康总检" align="center" min-width="120px">
                <el-table-column label="完成" prop="jkwc" min-width="120px" align="center" />
                <el-table-column label="未完成" prop="jkww" min-width="120px" align="center" />
              </el-table-column>
              <el-table-column label="职业总检" align="center" min-width="120px">
                <el-table-column label="完成" prop="zywc" min-width="120px" align="center" />
                <el-table-column label="未完成" prop="zyww" min-width="120px" align="center" />
              </el-table-column>
              <el-table-column label="健康报告" align="center" min-width="120px">
                <el-table-column label="打印" prop="jkbgyd" min-width="120px" align="center" />
                <el-table-column label="未打印" prop="jkbgwd" min-width="120px" align="center" />
              </el-table-column>
              <el-table-column label="职业报告" align="center" min-width="120px">
                <el-table-column label="打印" prop="zybgyd" min-width="120px" align="center" />
                <el-table-column label="未打印" prop="zybgwd" min-width="120px" align="center" />
              </el-table-column>
            </el-table>
          </div>
        </el-col>
        <el-col :span="9" style="height: 100%">
          <!-- 右侧页面 -->
          <div style="height: 100%">
            <div id="chart04" class="chart" style="height: 90%; margin-top: 0px"></div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
import { getListData, getGroupListData, getDetailListData } from '@/api/search/every_examiner'
import { getBranchData } from '@/api/statistical/professionchecks/conclusion_table.js' //获取分中心
import { getUserCid } from '@/api/system/branch.js'
import * as echarts from 'echarts'
export default {
  name: 'EveryExaminer',
  data() {
    return {
      chart: null,
      //底部数据
      downTableList: [],
      //下方遮罩层
      loadingDown: false,
      // 遮罩层
      loading: false,
      loading1: false,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      total2: 0,
      tableRightList: [],
      // 开单经理参数
      idoctorData: {
        placeholder: '请输入输入码选择',
        value: '姓名', //第二列标题
        url: '/sell/sellDate/getXsryByCode', //请求连接
        bindValue: '',
        firstName: 'userNo', //接口返回值对应第二列的参数名
        secondName: 'userName', //接口返回值对应第二列的参数名
        queryData: 'code',
      },
      // 查询参数
      queryParams: {
        endTime: '',
        startTime: '',
        date: undefined,
        idOrg: '',
        ddh: '',
        patientcode: '',
        patientname: '',
        useCodeHiden: '',
        idPatientclass: '',
        idOpendoctor: '',
        branchIds: undefined,
        sabc: '全部'
      },
      page1: {
        current: 1,
        size: 30,
      },
      page2: {
        current: 1,
        size: 20,
      },
      //个检团检数据
      geJian: 0,
      tuanJian: 0,
      // 表格展示数据
      tableList: [],
      // 体检者类型
      examType: [],
      // 分中心列表
      fzxOptions: [],
      // 是否为线上
      isOnline: false,
    }
  },
  async created() {
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
    this.examType = (await this.$getLevelList()).data
    this.queryParams.date = [this.$getDate().split(' ')[0], this.$getDate().split(' ')[0]]
    this.getUpList()
    this.getDownList()
  },
  methods: {
    // 创建图表
    createChartHandler() {
      let chart = echarts.init(document.getElementById('chart04'))
      var option
      option = {
        tooltip: {
          trigger: 'item',
        },
        legend: {
          // top: '220px',
          // bottom: '0',
          left: 'right',
        },
        series: [
          {
            //name: 'Access From',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            emphasis: {
              label: {
                show: true,
                fontSize: '40',
                fontWeight: 'bold',
              },
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)',
              },
            },
            data: [
              { name: '个人', value: this.geJian, color: '#22C58B' },
              { name: '团体', value: this.tuanJian, color: '#00CFDF' },
            ],
          },
        ],
      }
      option && chart.setOption(option)
    },
    //开单医师
    idoctorDataChange(value) {
      this.queryParams.idOpendoctor = value.userNo
    },
    // 查询列表
    getUpList() {
      this.loading = true
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      getListData({ ...this.queryParams, ...this.page1 })
        .then((response) => {
          this.tableList = response.data.records
          this.total = response.data.total
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    handleSelectionChange(selection) {
      if (selection.length == 1) {
        this.selection = selection[0]
        this.ids.push(selection[0].patientcode)
        this.getRightList()
      } else if (selection.length > 1) {
        this.$refs.leftTable.clearSelection() //清空表格数据
        this.$refs.leftTable.toggleRowSelection(selection.pop()) //最后一条数据
      } else if (selection.length == 0) {
        this.selection = []
        this.ids = []
        this.tableRightList = []
      }
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.leftTable.clearSelection()
      this.$refs.leftTable.toggleRowSelection(row)
    },
    // 鼠标移入图例触发
    enterHandler(item) {
      if (!this.chart) return
      this.chart.dispatchAction({
        type: 'highlight',
        name: item.name,
      })
    },
    // 鼠标移出图例触发
    leaveHandler(item) {
      if (!this.chart) return
      this.chart.dispatchAction({
        type: 'downplay',
        name: item.name,
      })
    },
    //获取右侧部分表格数据
    getRightList() {
      this.loading1 = true
      const query = {
        patientcode: this.ids[0],
        current: this.page2.current,
        size: this.page2.size,
      }
      getDetailListData(query)
        .then((response) => {
          this.tableRightList = response.data.records
          this.total2 = response.data.total
          this.loading1 = false
        })
        .catch((error) => {
          console.error(error)
          this.loading1 = false
        })
    },
    //获取下半部分表格数据
    getDownList() {
      this.loadingDown = true
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      const query = {
        ...this.queryParams,
        patientcode: this.ids[0],
      }
      this.downTableList = [{}, {}, {}, {}, {}, {}, {}]
      this.handleLoop(query, 1)
      this.handleLoop(query, 2)
      this.handleLoop(query, 3)
      this.handleLoop(query, 4)
      this.handleLoop(query, 5)
      this.handleLoop(query, 6)
      this.handleLoop(query, 7)
      this.handleLoop(query, 8)
    },
    // 循环调用防止超时
    handleLoop(query, index) {
      getGroupListData({ ...query, index }).then((response) => {
        if (index == 8) {
          this.downTableList = [...this.downTableList, ...response.data]
        } else {
          this.downTableList[index - 1] = response.data[0]
        }
        // this.geJian = this.downTableList[4] ? this.downTableList[4].yfhj : ''
        if (this.downTableList[4] && this.downTableList[5] && this.downTableList[6]) {
          this.geJian = this.downTableList[4].yfhj + this.downTableList[5].yfhj + this.downTableList[6].yfhj
        }
        if (this.downTableList[1] && this.downTableList[2] && this.downTableList[3]) {
          this.tuanJian = this.downTableList[1].yfhj + this.downTableList[2].yfhj + this.downTableList[3].yfhj
        }
        if (this.downTableList[1] && this.downTableList[4]) this.createChartHandler()
        this.downTableList = JSON.parse(JSON.stringify(this.downTableList))
        let empty = 0
        this.downTableList.forEach((el) => {
          if (el) {
            empty++
          }
        })
        if (empty >= 7) {
          this.loadingDown = false
        }
      })
    },
    // 搜索
    handleQuery() {
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.page1.current = 1
      this.page2.current = 1
      this.getUpList()
      this.getDownList()
    },
    // 重置
    resetQuery() {
      this.idoctorData.bindValue = ''
      this.resetForm('queryForm')
      this.handleQuery()
    },

    /** 导出按钮操作 */
    handleExport(type) {
      this.download('/query/everyExaminer/' + (type == 1 ? 'exportDetailTjz' : 'exportdataTt'), this.queryParams, `体检明细${type == 1 ? '数据' : '合计'}.xlsx`)
    },
  },
}
</script>
<style>
.general-inspection-day .el-table .el-table__cell,
.general-inspection-day .el-table th,
.general-inspection-day .el-table thead.is-group th.el-table__cell,
.general-inspection-day .el-table .el-table__header-wrapper th,
.general-inspection-day .el-table .el-table__fixed-header-wrapper th {
  height: auto !important;
  min-height: 0 !important;
  padding: 0 !important;
}
</style>
