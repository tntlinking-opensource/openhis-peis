<!-- 健康报告领取通知 开发人：麦沃德科技暴雨/予安 -->
<template>
  <div class="app-container flex-direction-column">
    <!--页面-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="姓名" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入姓名" clearable style="width: 150px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 150px"  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="体检单位" prop="idOrg">
        <input-select ref="orgType" :selectData="selectData" @change="selectChange"></input-select>
      </el-form-item>
      <el-form-item label="通知结果" prop="notificationResult">
        <el-select v-model="queryParams.notificationResult" placeholder="请选择通知结果" clearable style="width: 150px"  @change="handleQuery">
          <el-option label="号码错" :value="1" />
          <el-option label="未接通" :value="2" />
          <el-option label="已电话通知" :value="3" />
          <el-option label="已短信通知" :value="4" />
          <el-option label="再通知" :value="5" />
        </el-select>
      </el-form-item>
      <el-form-item label="交接日期" prop="date">
        <el-date-picker v-model="queryParams.date" style="width: 290px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" @change="handleQuery"></el-date-picker>
      </el-form-item>
      <el-form-item label="柜子号" prop="chestNum">
        <el-input v-model="queryParams.chestNum" placeholder="请输入柜子号" clearable style="width: 150px"  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="通知状态" prop="noticeStatus">
        <el-select v-model="queryParams.noticeStatus" placeholder="请选择通知状态" clearable style="width: 150px"  @change="handleQuery">
          <el-option label="未通知" :value="0" />
          <el-option label="已通知" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="年龄" prop="startAge">
        <el-input-number v-model="queryParams.startAge" controls-position="right" :min="1" :max="100" @keyup.enter.native="handleQuery"></el-input-number>
      </el-form-item>
      <el-form-item label="-" prop="endAge">
        <el-input-number v-model="queryParams.endAge" controls-position="right" :min="1" :max="100" @keyup.enter.native="handleQuery"></el-input-number>
      </el-form-item>
      <el-form-item label="发放方式" prop="grantId">
        <el-select v-model="queryParams.grantId" placeholder="请选择发放方式" clearable style="width: 150px" @change="handleQuery">
          <el-option v-for="item in grantOptions" :key="item.id" :label="item.methodName" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleqNotice(1)" :disabled="multiple" v-hasPermi="['report:reportReceive:healthNotify:notice']">电话通知 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-chat-line-square" @click="handleSendMsg" v-hasPermi="['report:reportReceive:healthNotify:sendMsg']">短信通知 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-circle-close" @click="handleqNotice(2)" :disabled="multiple" v-hasPermi="['report:reportReceive:healthNotify:noticeerror']">号码错误 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button plain size="mini" icon="el-icon-download" @click="handleqNotice(3)" :disabled="multiple" v-hasPermi="['report:reportReceive:healthNotify:nonotice']">未接通 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-circle-close" @click="handleCancelSMS" :disabled="multiple" v-hasPermi="['report:reportReceive:healthNotify:cancelsms']">取消发送 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-search" @click="handleViewsms" :disabled="single" v-hasPermi="['report:reportReceive:healthNotify:viewsms']">查看短信 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['report:reportReceive:healthNotify:export']">导出Excel </el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border ref="selectTable" v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column fixed type="selection" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" fixed />
        <el-table-column label="体检号" prop="patientcode" min-width="160px" align="center" show-overflow-tooltip fixed />
        <el-table-column label="姓名" prop="patientname" min-width="100px" align="center" show-overflow-tooltip fixed />
        <el-table-column label="电话" prop="phone" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="单位名称" prop="khdwmc" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="开单医师" prop="doctorapply" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="性别" prop="sex" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ ['男', '女', '通用'][scope.row.sex] }}</span>
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="age" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="通知状态" prop="status" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status == 9" type="danger">未通知</el-tag>
            <el-tag v-else-if="scope.row.status > 9">已通知</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="通知结果" prop="notificationResult" min-width="100px" align="center">
          <template slot-scope="scope">
            <span>{{ ['', '号码错', '未接通', '已电话通知', '已短信通知', '再通知'][scope.row.notificationResult] }}</span>
          </template>
        </el-table-column>
        <el-table-column label="短信状态" prop="notifyResult" min-width="100px" align="center">
          <template slot-scope="scope">
            <span>{{ ['通知失败', '已取消', '等待通知', '已通知'][scope.row.notifyResult] }}</span>
          </template>
        </el-table-column>
        <el-table-column label="备注" prop="notifyMemo" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="柜子号" prop="chestNum" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="发放方式" prop="methodName" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="登记时间" prop="dateregister" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="通知时间" prop="notifyDate" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="交接时间" prop="revTime" min-width="160px" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改对话框 -->
    <notice-items ref="noticeItems" @handleQuery="handleQuery"></notice-items>
    <sms-items ref="smsItems" @handleQuery="handleQuery"></sms-items>
    <showsms-items ref="showsmsItems"></showsms-items>
  </div>
</template>
<script>
import noticeItems from './notice'
import smsItems from './smsnotification'
import showsmsItems from './showsms'

import { getListData, cancelSMS } from '@/api/report/report_receive/health_notify.js'
import { getBoxData } from '@/api/report/health_handover/health_handover.js'
export default {
  name: 'Health_notify',
  components: { noticeItems, smsItems, showsmsItems },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组id
      ids: [],
      // 选中数组
      patientList: [],
      // 总条数
      total: 10,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        name: undefined,
        patientcode: undefined,
        idOrg: undefined,
        notificationResult: undefined,
        date: undefined,
        startRevTime: undefined,
        endRevTime: undefined,
        chestNum: undefined,
        noticeStatus: undefined,
        startAge: undefined,
        endAge: undefined,
        grantId: undefined,
      },
      // 体检单位数据
      selectData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '体检单位', //第二列标题
        url: '/sell/customer/getAllOrg', //请求连接
        selectWidth: 150, //选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(必传)
        firstName: 'khdwsrm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 表格展示数据
      tableList: [],
      // 发放方式选项
      grantOptions: [],
    }
  },
  created() {
    this.queryParams.date = [this.$getDate().split(' ')[0], this.$getDate().split(' ')[0]]
    this.getList()
    getBoxData().then(({ data }) => {
      this.grantOptions = data
    })
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      if (this.queryParams.date) {
        this.queryParams.startRevTime = this.queryParams.date[0]
        this.queryParams.endRevTime = this.queryParams.date[1]
      } else {
        this.queryParams.startRevTime = undefined
        this.queryParams.endRevTime = undefined
      }
      getListData(this.queryParams)
        .then(({ data }) => {
          this.tableList = data.records
          this.total = data.total
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // change方法返回选中的值
    selectChange(value) {
      this.queryParams.idOrg = value.id
    },
    //执行通知
    handleqNotice(type) {
      this.$refs.noticeItems.handleqNotice(type, this.ids)
    },
    //短信通知
    handleSendMsg() {
      this.$refs.smsItems.handleSendMsg(this.patientList)
    },
    //取消发送
    handleCancelSMS() {
      let b = true
      this.patientList.forEach((el) => {
        if (el.notifyResult != 2) {
          this.$alert("体检号为<font color='red'>" + el['patientcode'] + "</font>的体检者，不是<font color='orange'>等待短信通知</font>状态，不能取消发送！", '提示', {
            dangerouslyUseHTMLString: true,
            confirmButtonText: '确定',
          })
          b = false
          return
        }
      })
      if (b) {
        cancelSMS(this.ids).then(({ data }) => {
          this.$modal.msgSuccess('取消发送成功')
          this.getList()
        })
      }
    },
    //查看短信
    handleViewsms() {
      this.$refs.showsmsItems.handleViewsms(this.patientList[0].patientcode)
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.$refs.orgType.initData()
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.patientList = selection.map((item) => item)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.selectTable.clearSelection()
      this.$refs.selectTable.toggleRowSelection(row)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        '/report/healthPhoneInform/export',
        {
          ...this.queryParams,
        },
        `健康报告领取通知.xlsx`
      )
    },
  },
}
</script>
