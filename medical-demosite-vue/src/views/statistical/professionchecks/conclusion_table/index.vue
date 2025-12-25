<!-- 职业健康检查结果结论附表	 开发人：麦沃德科技矢北/清风 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" v-show="showSearch" @submit.native.prevent class="no-margin-bottom">
      <el-form-item prop="startTime" label="登记日期">
        <el-date-picker v-model="queryParams.startTime" :picker-options="pickerOptions" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="endTime" label="至">
        <el-date-picker v-model="queryParams.endTime" :picker-options="pickerOptions" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="idOrg" label="单位">
        <input-select ref="idOrg" :selectData="selectData" @change="selectChange"></input-select>
      </el-form-item>
      <el-form-item prop="patientname" label="姓名">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item prop="ddh" label="订单号">
        <el-input v-model="queryParams.ddh" placeholder="请输入订单号"></el-input>
      </el-form-item>
      <el-form-item prop="geStartTime" label="报告交接日期从">
        <el-date-picker v-model="queryParams.geStartTime" :picker-options="pickerOptions" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="geEndTime" label="至">
        <el-date-picker v-model="queryParams.geEndTime" :picker-options="pickerOptions" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="occupationSummary" label="检查结论">
        <input-select ref="occupationSummary" :selectData="selectData1" @change="selectChange1"></input-select>
      </el-form-item>
      <el-form-item prop="counterreportprinted" label="复查次数">
        <el-input-number v-model="queryParams.counterreportprinted" controls-position="right"></el-input-number>
      </el-form-item>
      <!-- fzx or cid -->
      <el-form-item prop="branchIds" label="分中心">
        <el-select v-model="queryParams.branchIds" style="width: 220px" clearable filterable :filter-method="filterMethod" @clear="fzxOptions = filterList">
          <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="medicaltype" label="体检类别">
        <el-select v-model="queryParams.medicaltype" style="width: 220px" clearable>
          <el-option v-for="item in tjlbOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="kdxs" label="销售经理">
        <input-select ref="kdxs" :selectData="selectData2" @change="selectChange2"></input-select>
      </el-form-item>
      <el-form-item prop="orgDepart" label="部门">
        <el-input v-model="queryParams.orgDepart" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-upload2" @click="handExport" v-hasPermi="['statistical:professionChecks:conclusionTable:export']">导出</el-button>
      </el-col>
    </el-row>
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
        <el-table-column fixed type="selection" width="50" align="center" />
        <!-- <el-table-column label="序列" type="index" width="60" align="center" /> -->
        <el-table-column label="序列" width="60" align="center">
          <template v-slot="scope">
            <!-- 计算索引：当前页码 * 每页显示数量 + 当前行的索引（从0开始，所以需要+1） -->
            {{ (queryParams.current - 1) * queryParams.size + scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column label="体检号" show-overflow-tooltip prop="patientcode" width="130" align="center" />
        <el-table-column label="订单号" show-overflow-tooltip prop="ddh" width="120" align="center" />
        <el-table-column label="销售经理" show-overflow-tooltip prop="doctorapply" width="100" align="center" />
        <el-table-column label="姓名" show-overflow-tooltip prop="patientname" width="80" align="center" />
        <el-table-column label="身份证号" show-overflow-tooltip prop="idcardno" width="160" align="center" />
        <el-table-column label="登记时间" show-overflow-tooltip prop="dateregister" width="120" align="center" />
        <el-table-column label="电话" show-overflow-tooltip prop="phone" width="120" align="center" />
        <el-table-column label="性别" prop="sex" width="60" align="center">
          <template slot-scope="scope">
            <div v-for="item in sexOptions" :key="item.id">
              <span v-if="item.id == scope.row.sex">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="年龄" show-overflow-tooltip prop="age" width="60" align="center" />
        <el-table-column label="体检类别" prop="medicaltype" width="100" align="center">
          <template slot-scope="scope">
            <div v-for="item in medicaltypeOptions" :key="item.id">
              <span v-if="item.id == scope.row.medicaltype">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="复查次数" show-overflow-tooltip prop="counterreportprinted" width="100" align="center" />
        <el-table-column label="复查项目" show-overflow-tooltip prop="items" width="140" align="center" />
        <el-table-column label="总工龄(年)" show-overflow-tooltip prop="zgl" width="100" align="center" />
        <el-table-column label="接害年龄(年)" show-overflow-tooltip prop="jhgl" width="120" align="center" />
        <el-table-column label="工种" show-overflow-tooltip prop="trades" width="100" align="center" />
        <el-table-column label="危害因素" show-overflow-tooltip prop="jhys" width="120" align="center" />
        <el-table-column label="检查结果" show-overflow-tooltip prop="posistive" width="140" align="center" />
        <el-table-column label="检查结论" show-overflow-tooltip prop="occupationSummary" width="140" align="center" />
        <el-table-column label="处理意见" show-overflow-tooltip prop="summaryText" width="140" align="center" />
        <el-table-column label="体检单位" show-overflow-tooltip prop="org" width="140" align="center" />
        <el-table-column label="部门" show-overflow-tooltip prop="orgDepart" width="140" align="center" />
        <el-table-column label="末次体检" show-overflow-tooltip prop="isFinal" width="140" align="center" />
        <el-table-column label="分中心" show-overflow-tooltip prop="fzx" width="140" align="center" />
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>

<script>
import { page, getBranchData } from '@/api/statistical/professionchecks/conclusion_table.js'
import { physicalType1 } from '@/utils/dataList.js'
import { filterMethod } from '@/utils/filterMethod.js'
import { getUserCid } from '@/api/system/branch.js'
export default {
  name: 'Conclusion_table',
  data() {
    return {
      tableList: [],
      total: 0,
      showSearch: true,
      loading: false,
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
      // 单位
      selectData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '单位名称', //第二列标题
        url: '/statistics/positiveQuery/getAllOrg', //请求连接
        firstName: 'khdwsrm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 检查结论
      selectData1: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '检查结论', //第二列标题
        url: '/zyVsSummary/getJcjlData', //检查结论
        secondName: 'occupationSummary', //第二行显示内容
      },
      // 销售经理
      selectData2: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '销售经理', //第二列标题
        url: 'sell/customerTransfer/getXsryData', //请求连接
        secondName: 'username', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 分中心
      fzxOptions: [],
      // 体检类别选项
      tjlbOptions: physicalType1(),
      //单位
      departmentOptions: [],
      // 性别
      sexOptions: [
        { id: 0, text: '男' },
        { id: 1, text: '女' },
        { id: 2, text: '通用' },
      ],
      //体检类别
      medicaltypeOptions: [
        { id: '0', text: '上岗前' },
        { id: '1', text: '在岗期间' },
        { id: '2', text: '离岗时' },
        { id: '3', text: '离岗后' },
        { id: '4', text: '应急' },
      ],
      queryParams: {
        current: 1,
        size: 20,
        startTime: undefined,
        endTime: undefined,
        idOrg: undefined,
        kdxs: undefined,
        ddh: undefined,
        occupationSummary: undefined,
        counterreportprinted: undefined,
        cid: undefined,
        patientname:undefined,
        geStartTime:undefined,
        geEndTime:undefined,
        orgDepart:undefined,
      },
    }
  },
  async created() {
    this.$set(this.queryParams, 'branchIds', this.$getCookie('cid'))
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
    //  当月开始时间戳
    const timeStamp = new Date().toLocaleDateString()
    //  上个月的天数
    const days = this.lastMonthDats()
    //  上月开始时间戳
    const lastMonthStart = new Date(timeStamp).getTime() - days * 24 * 3600 * 1000
    this.queryParams.startTime = this.$getDate(lastMonthStart).slice(0, 10) + ' 00:00:00'
    this.queryParams.endTime = this.$getDate().slice(0, 10) + ' 23:59:59'
  },
  mounted() {
    this.getBasic()
    this.getList()
  },
  methods: {
    //获取基础属性
    getBasic() {
      getBranchData().then((res) => {
        this.fzxOptions = res.data
        this.filterList = JSON.parse(JSON.stringify(res.data))
      })
    },
    // 单位名称选择结果
    selectChange(value) {
      this.queryParams.idOrg = value.id
    },
    // 检查结论选择结果
    selectChange1(value) {
      this.queryParams.occupationSummary = value.serialNo
    },
    // 检查结论选择结果
    selectChange2(value) {
      this.queryParams.kdxs = value.id
    },
    // 分中心条件查询选择
    filterMethod(value) {
      this.fzxOptions = this.filterList
      this.fzxOptions = filterMethod(value, this.fzxOptions, 'fzx')
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    ///搜素
    handleQuery() {
      this.queryParams.current = 1
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.queryParams.geStartTime = this.queryParams.geStartTime ? this.queryParams.geStartTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.geEndTime = this.queryParams.geEndTime ? this.queryParams.geEndTime.slice(0, 10) + ' 23:59:59' : undefined
      this.getList()
    },
    ///重置
    resetQuery() {
      this.$refs.idOrg.initData()
      this.$refs.occupationSummary.initData()
      this.$refs.kdxs.initData()
      this.resetForm('queryForm')
      this.handleQuery()
    },
    handExport() {
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.queryParams.geStartTime = this.queryParams.geStartTime ? this.queryParams.geStartTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.geEndTime = this.queryParams.geEndTime ? this.queryParams.geEndTime.slice(0, 10) + ' 23:59:59' : undefined
      this.download('/statistics/positiveQuery/export', this.queryParams, `${this.tableList[0].org + this.$getDate().split(' ')[0]}.xlsx`)
    },
    // 查询列表
    getList() {
      this.loading = true
      this.queryParams.cid = this.queryParams.branchIds
      page(this.queryParams)
        .then((res) => {
          this.tableList = res.data.records
          this.total = res.data.total
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    //远程选框的方法
    remoteMethod(query) {
      if (query !== '') {
        this.loading = true
        setTimeout(() => {
          this.loading = false
          this.options = this.list.filter((item) => {
            return item.label.toLowerCase().indexOf(query.toLowerCase()) > -1
          })
        }, 200)
      } else {
        this.options = []
      }
    },
    // 获取上个月共几天
    lastMonthDats() {
      const date = new Date()
      const year = date.getFullYear()
      //  上个月月份
      let month = date.getMonth() + 1 - 1 //  0-11 表示 1月-12月
      //  0 表示12月
      month = month || 12
      //  30天的月份
      const arr30 = [4, 6, 9, 11]
      //  31天的月份
      const arr31 = [1, 3, 5, 7, 8, 10, 12]
      if (arr30.indexOf(month) !== -1) {
        //  上个月是 30 天
        return 30
      } else if (arr31.indexOf(month) !== -1) {
        //  上个月是 31 天
        return 31
      } else {
        //  2月
        if (this.isRunYear(year)) {
          return 29
        } else {
          return 28
        }
      }
    },
    isRunYear(year) {
      //  条件:能被4整除并且不能被100整除，或者被400整除的
      let flag = false
      if ((year % 4 === 0 && year % 100 !== 0) || year % 400 === 0) {
        flag = true
      }
      return flag
    },
  },
}
</script>