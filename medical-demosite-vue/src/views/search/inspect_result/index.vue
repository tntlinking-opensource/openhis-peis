<!-- 健康检查结果  开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名"  style="width: 180px"  clearable/>
      </el-form-item>
      <el-form-item label="体检类型" prop="idExamtype">
        <el-select v-model="queryParams.idExamtype" placeholder="请选择体检类型" style="width: 160px">
          <el-option label="健康体检" :value="0" />
          <el-option label="职业体检" :value="1" />
          <el-option label="综合" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="体检单位" prop="idOrg">
        <input-select ref="idOrg" :selectData="selectData" @change="selectChange"></input-select>
      </el-form-item>
      <el-form-item label="分中心" prop="fzx">
        <el-select v-model="queryParams.fzx" placeholder="请选择分中心" clearable style="width: 160px" @change="handleQuery">
          <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="订单号" prop="ddh">
        <el-input v-model="queryParams.ddh" placeholder="请输入订单号" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="登记日期从" prop="startTime">
        <el-date-picker v-model="queryParams.startTime" style="width: 160px" value-format="yyyy-MM-dd" type="date" placeholder="请选择日期" :picker-options="pickerOptions"></el-date-picker>
      </el-form-item>
      <el-form-item label="到" prop="endTime">
        <el-date-picker v-model="queryParams.endTime" style="width: 160px" value-format="yyyy-MM-dd" type="date" placeholder="请选择日期" :picker-options="pickerOptions"></el-date-picker>
      </el-form-item>
      <el-form-item label="报告生成日期从" prop="reportStartTime">
        <el-date-picker v-model="queryParams.reportStartTime" style="width: 160px" value-format="yyyy-MM-dd" type="date" placeholder="请选择日期" :picker-options="pickerOptions"></el-date-picker>
      </el-form-item>
      <el-form-item label="到" prop="reportEndTime">
        <el-date-picker v-model="queryParams.reportEndTime" style="width: 160px" value-format="yyyy-MM-dd" type="date" placeholder="请选择日期" :picker-options="pickerOptions"></el-date-picker>
      </el-form-item>
      <el-form-item prop="xsjlid" label="销售经理">
        <input-select ref="xsjlid" :selectData="selectData2" @change="selectChange2" style="width: 160px"></input-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleExport">导出Excel</el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @row-click="rowClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="体检号" prop="patientcode" align="center" />
        <el-table-column label="订单号	" prop="ddh" align="center" />
        <el-table-column label="姓名" prop="patientname" align="center" />
        <el-table-column label="身份证号" prop="idcardno" align="center" />
        <el-table-column label="登记时间" prop="dateregister" align="center" />
        <el-table-column label="电话" prop="phone" align="center" />
        <el-table-column label="性别" prop="sex" align="center" width="80">
          <template slot-scope="scope">
            {{ ['男', '女'][scope.row.sex] }}
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="age" align="center" width="80" />
        <el-table-column label="综述" prop="summarize" align="center" width="200" show-overflow-tooltip />
        <el-table-column label="健康建议" prop="offer" align="center" width="200" show-overflow-tooltip />
        <el-table-column label="体检单位" prop="org" align="center" width="200" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>
<script>
import { getListData } from '@/api/search/inspect_result.js'
import { getBranchData } from '@/api/statistical/professionchecks/conclusion_table.js' //获取分中心
import { getUserCid } from '@/api/system/branch.js'

export default {
  name: 'Inspect_result',
  data() {
    return {
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
      // 体检单位传入数据模板  
      selectData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '体检单位', //第二列标题
        url: 'sell/customer/getAllOrg', //请求连接
        selectWidth: 180, //选择器宽度（选填，默认230）不加px,传100%则为100%
        firstName: 'khdwsrm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
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
      // 遮罩层
      loading: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        startTime: undefined,
        endTime: undefined,
        reportStartTime:undefined,
        reportEndTime:undefined,
        idOrg: undefined,
        ddh: undefined,
        patientname:undefined,
        fzx:undefined,
        xsjlid:undefined
      },
      // 总条数
      total: 0,
      // 排检表格数据
      tableList: [],
      // 分中心列表
      fzxOptions: [],
      // 是否为线上
      isOnline: false,
    }
  },
  created() {
    //  当月开始时间戳
    const timeStamp = new Date().toLocaleDateString()
    //  上个月的天数
    const days = this.lastMonthDats()
    //  上月开始时间戳
    const lastMonthStart = new Date(timeStamp).getTime() - days * 24 * 3600 * 1000
    this.queryParams.startTime = this.$getDate(lastMonthStart).split(' ')[0]
    this.queryParams.endTime = this.$getDate().split(' ')[0]
    this.getList()
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
  },
  methods: {
    // 检查结论选择结果
      selectChange2(value) {
      this.queryParams.xsjlid = value.id
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
    // 体检单位返回选中的值
    selectChange(value) {
      this.queryParams.idOrg = value.id
    },
    // 查询列表
    getList() {
      this.loading = true
      let queryParams = JSON.parse(JSON.stringify(this.queryParams))
      if (queryParams.startTime) {
        queryParams.startTime += ' 00:00:00'
      }
      if (queryParams.endTime) {
        queryParams.endTime += ' 23:59:59'
      }
      if (queryParams.reportStartTime) {
        queryParams.reportStartTime += ' 00:00:00'
      }
      if (queryParams.reportEndTime) {
        queryParams.reportEndTime += ' 23:59:59'
      }
      getListData(queryParams)
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
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.$refs.idOrg.initData()
      this.$refs.xsjlid.initData()
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 导出
    handleExport() {
      let queryParams = JSON.parse(JSON.stringify(this.queryParams))
      if (queryParams.startTime) {
        queryParams.startTime += ' 00:00:00'
      }
      if (queryParams.endTime) {
        queryParams.endTime += ' 23:59:59'
      }
      if (queryParams.reportStartTime) {
        queryParams.reportStartTime += ' 00:00:00'
      }
      if (queryParams.reportEndTime) {
        queryParams.reportEndTime += ' 23:59:59'
      }
      this.download('/statistics/healthResult/export', queryParams, `健康检查结果 .xlsx`)
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
  },
}
</script>
