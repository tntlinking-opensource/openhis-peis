<!-- 云平台上传结果	 开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item prop="date" label="登记日期">
        <el-date-picker v-model="queryParams.startDate" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择登记日期从" @change="dateChange"> </el-date-picker>
      </el-form-item>
      <el-form-item prop="date" label="至">
        <el-date-picker v-model="queryParams.endDate" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择登记日期到" @change="dateChange"> </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5" v-for="(item, index) in btnList" :key="index">
            <el-button :type="index == timeBtn ? 'primary' : ''" plain size="mini" @click="handleChangeTime(index)">{{ item }}</el-button>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item prop="patientcode" label="体检号">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号"></el-input>
      </el-form-item>
      <el-form-item label="单位" prop="idOrg">
        <input-select :selectData="selectData" @change="selectChange"></input-select>
      </el-form-item>
      <el-form-item prop="status" label="上传状态">
        <el-select v-model="queryParams.status" style="width: 180px" clearable>
          <el-option v-for="item in statusOptions" :key="item.id" :label="item.text" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="doctorapply" label="开单医生">
        <el-input v-model="queryParams.doctorapply" placeholder="请输入开单医生"></el-input>
      </el-form-item>
      <el-form-item prop="ddh" label="订单号">
        <el-input v-model="queryParams.ddh" placeholder="请输入订单号"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-upload2" @click="handleExport">导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-refresh-right" :disabled="!multiple" @click="handleReupload">重新上传</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-edit" :disabled="!multiple" @click="handleAdjust">校正工龄</el-button>
      </el-col>
    </el-row>
    <div class="table-box">
      <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column fixed type="selection" width="50" align="center" />
        <el-table-column label="序列" type="index" width="50" align="center" />
        <el-table-column label="体检号" show-overflow-tooltip prop="patientcode" width="110" align="center" />
        <el-table-column label="姓名" show-overflow-tooltip prop="patientname" width="80" align="center" />
        <el-table-column label="上传状态" prop="status" width="80" align="center">
          <template slot-scope="scope">
            <span :style="{ color: scope.row.status == 1 ? 'green' : scope.row.status == 2 ? 'red' : '' }">
              {{ statusOptions[scope.row.status].text }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="驳回原因" show-overflow-tooltip prop="msg" align="center" />
        <el-table-column label="体检类型" prop="idExamtype" width="100" align="center">
          <template slot-scope="scope">
            {{ ['', '职业', '综合', '复查'][scope.row.idExamtype] }}
          </template>
        </el-table-column>
        <el-table-column label="登记时间" show-overflow-tooltip prop="dateregister" width="140" align="center" />
        <el-table-column label="修改时间" show-overflow-tooltip prop="modifydate" width="140" align="center" />
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>

<script>
import { getPageInfo, updateStatus, calculateZglAndJhgl } from '@/api/statistical/professionchecks/upload-results.js'
export default {
  name: 'Upload_results',
  data() {
    return {
      // 筛选条件
      queryParams: {
        current: 1,
        size: 20,
        startDate: undefined,
        endDate: undefined,
        patientcode: undefined,
        status: undefined,
      },
      // 单位数据模板
      selectData: {
        placeholder: '请输入输入码选择',
        value: '单位名称', //第二列标题
        url: '/sell/customer/getAllOrg', //请求连接
        selectWidth: 180, //选择器宽度（选填，默认230）不加px,传100%则为100%
        bindValue: '', //初始值
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      timeBtn: 3,
      btnList: ['今日', '近7天', '近30天'],
      // 上传状态
      statusOptions: [
        { id: 0, text: '未上传' },
        { id: 1, text: '上传成功' },
        { id: 2, text: '上传失败' },
      ],
      // 加载中
      loading: false,
      // 列表数据
      tableList: [],
      total: 0,
      // 选中单个
      single: false,
      multiple: false,
      // 选中的体检信息
      selectInfo: [],
    }
  },
  created() {
    this.handleChangeTime(2)
    this.getList()
  },
  methods: {
    // 单位返回选中的值
    selectChange(value) {
      this.queryParams.idOrg = value.id
      this.selectData.bindValue = value.khdwmc
      this.handleQuery()
    },
    ///搜素
    handleQuery() {
      this.queryParams.startDate = this.queryParams.startDate ? this.queryParams.startDate.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endDate = this.queryParams.endDate ? this.queryParams.endDate.slice(0, 10) + ' 23:59:59' : undefined
      this.queryParams.current = 1
      this.getList()
    },
    ///重置
    resetQuery() {
      this.selectData.bindValue = ''
      this.handleChangeTime(2)
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 查询列表
    getList() {
      this.loading = true
      getPageInfo(this.queryParams)
        .then((res) => {
          this.tableList = res.data.records
          this.total = res.data.total
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 切换时间
    handleChangeTime(type) {
      let toData = new Date(new Date().toLocaleDateString()).getTime()
      let past7daysStart = toData - (type == 0 ? 0 : type == 1 ? 7 : 30) * 3600 * 24 * 1000
      this.queryParams.startDate = this.$getDate(past7daysStart).split(' ')[0]
      this.queryParams.endDate = this.$getDate().split(' ')[0]
      this.timeBtn = type
      this.handleQuery()
    },
    // 手动选择日期
    dateChange() {
      this.timeBtn = 4
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.selectInfo = selection
      this.single = selection.length == 1
      this.multiple = selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 导出
    handleExport() {
      this.queryParams.startDate = this.queryParams.startDate ? this.queryParams.startDate.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endDate = this.queryParams.endDate ? this.queryParams.endDate.slice(0, 10) + ' 23:59:59' : undefined
      this.download('/statistics/professionUploadResult/export', this.queryParams, `云平台上传结果${this.$getDate().split(' ')[0]}.xlsx`)
    },
    // 重新上传
    handleReupload() {
      // let error = ''
      // this.selectInfo.forEach((el) => {
      //   if (!error && el.status == 1) {
      //     error = `体检号${el.patientcode}已上传成功,不可重复上传`
      //   }
      // })
      // if (error) {
      //   this.$alert(error, '提示')
      //   return
      // }
      this.loading = true
      updateStatus({
        patientcodes: this.selectInfo.map((item) => item.patientcode).join(','),
      })
        .then((res) => {
          this.$modal.msgSuccess('上传成功')
          this.getList()
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 校正工龄
    handleAdjust() {
      this.loading = true
      calculateZglAndJhgl(this.selectInfo.map((item) => item.patientcode))
        .then((res) => {
          this.$modal.msgSuccess('校正成功')
          this.getList()
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
  },
}
</script>
