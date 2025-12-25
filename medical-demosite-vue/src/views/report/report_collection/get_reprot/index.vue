<!-- 职业报告领取 开发人：麦沃德科技暴雨 -->
<template>
  <div class="app-container flex-direction-column">
    <!--页面-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="姓名" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入姓名" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="queryParams.phone" placeholder="请输入电话" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="领取日期" prop="date">
        <el-date-picker v-model="queryParams.date" style="width: 360px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="订单号" prop="numorgresv">
        <el-input v-model="queryParams.numorgresv" placeholder="请输入订单号" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="公司" prop="idOrg">
        <input-select :selectData="labTypeData" :showTooltip="true" @change="labTypeChange"></input-select>
      </el-form-item>
      <el-form-item label="部门" prop="orgDepart">
        <el-input v-model="queryParams.orgDepart" placeholder="请输入部门" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="领取人" prop="getterName">
        <el-input v-model="queryParams.getterName" placeholder="请输入领取人" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="发放人" prop="issueId">
        <input-select :selectData="issueIdData" @change="issueIdChange"></input-select>
      </el-form-item>
      <el-form-item label="领取状态" prop="receiveStatus">
        <el-select v-model="queryParams.receiveStatus" placeholder="请选择" clearable style="width: 160px">
          <el-option label="未领取" :value="0" />
          <el-option label="已领取" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="柜子号" prop="chestNum">
        <el-input v-model="queryParams.chestNum" placeholder="请输入柜子号" clearable style="width: 160px" />
      </el-form-item>
      <el-form-item label="发放方式" prop="grantId">
        <el-select v-model="queryParams.grantId" placeholder="请选择发放方式" clearable style="width: 160px">
          <el-option v-for="item in options" :label="item.methodName" :value="item.id" :key="item.id"> </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="排序方式" prop="sort">
        <el-select v-model="queryParams.sort" placeholder="请选择排序方式" clearable style="width: 160px">
          <el-option label="交接时间" :value="0" />
          <el-option label="体检时间" :value="1" />
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
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleReceive" v-hasPermi="['report:reportCollection:getReprot:receive']">领取 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-circle-close" @click="handleUnReceive" v-hasPermi="['report:reportCollection:getReprot:unReceive']">反领取 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['report:reportCollection:getReprot:export']">导出Excel </el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box" ref="tableBox">
      <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="体检号" prop="patientcode" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="体检者姓名" prop="patientname" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="报告状态" prop="status" min-width="160px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span :style="'color: ' + auditColor(scope.row.status)">
              {{ auditStatus(scope.row.status) }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="性别" prop="sex" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <div v-if="scope.row.sex == 0">男</div>
            <div v-if="scope.row.sex == 1">女</div>
            <div v-if="scope.row.sex == 2">通用</div>
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="age" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="开单医师" prop="doctorapply" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="公司名称" prop="orgName" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="部门" prop="orgDepart" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="电话" prop="phone" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="发放方式" prop="methodName" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="柜子号" prop="chestNum" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="登记时间" prop="dateregister" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="交接时间" prop="joinTime" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="快递号" prop="expressNum" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="快递公司" prop="expressId" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="交接人" prop="joinPersonMan" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="通知时间" prop="notifyDate" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="通知备注" prop="notifyMemo" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="发放人" prop="issueId" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="领取人" prop="getterName" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="领取时间" prop="getTime" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="领取人联系方式" prop="getterPhone" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="订单号" prop="numorgresv" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="备注" prop="note" min-width="180px" align="center" show-overflow-tooltip />
        <el-table-column prop="signUrl" width="100px" align="center" fixed="right">
          <template slot-scope="scope">
            <span style="color: orange; cursor: pointer" @click="checkPic(scope.row.signUrl)">查看签名</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改对话框 -->
    <receive-items ref="receiveItems" @getList="getList"></receive-items>
    <!-- 查看图片 -->
    <el-dialog title="查看签名" :visible.sync="signUrl" class="sign-dialog" append-to-body>
      <img :src="signUrl" alt="" style="width: 500px" />
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="signUrl = undefined">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import receiveItems from './receive'
import { getListData, getBoxData, getUnnotice } from '@/api/report/report_collection/get_reprot'

export default {
  name: 'Get_reprot',
  components: { receiveItems },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 发送方式
      options: [],
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
        sfxmsrm: undefined,
        examfeeitemName: undefined,
        tjlx: undefined,
        xb: undefined,
        idDepart: undefined,
        examfeeitemCode: undefined,
        idLabtype: undefined,
        djdate: undefined,
        cldate: undefined,
        ycldate: undefined,
      },
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
      // 公司名称
      labTypeData: {
        placeholder: '请输入拼音码或名称回车查询',
        key: '拼音码',
        value: '公司名称',
        url: '/sell/customer/getAllOrg',
        bindValue: '', //初始值(必传)
        firstName: 'khdwsrm',
        secondName: 'khdwmc',
        queryData: 'key',
        selectWidth: '220',
      },
      //发放人
      issueIdData: {
        placeholder: '请输入输入码或名称回车查询',
        key: '输入码',
        value: '用户名',
        url: '/report/healthGetReport/getAllUserData',
        bindValue: '', //初始值(必传)
        firstName: 'inputCode',
        secondName: 'username',
        queryData: 'key',
        selectWidth: '220',
      },
      // 表格展示数据
      tableList: [],
      // 查看图片
      signUrl: undefined,
    }
  },
  created() {
    this.getList()
    this.getBoxData()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    //领取
    handleReceive() {
      for (var i in this.rows) {
        if (this.rows[i].status && this.rows[i].status == 11) {
          this.$alert('体检号:' + this.rows[i].patientcode + '  报告已领取,请勿重复领取!', '提醒', {
            confirmButtonText: '确定',
            type: 'warning',
          })
          return
        }
      }

      var zhi = this.rows
      this.$refs.receiveItems.handleReceive(zhi)
    },
    //反领取
    handleUnReceive() {
      for (var i in this.rows) {
        if (this.rows[i].status && this.rows[i].status != 11) {
          this.$alert('请选择已领取的报告', '提醒', {
            confirmButtonText: '确定',
            type: 'warning',
          })
          return
        }
      }
      const ids = this.ids
      this.$modal
        .confirm('确定反领取吗？')
        .then(function () {
          return getUnnotice(ids)
        })
        .then((res) => {
          this.getList()
          this.$modal.msgSuccess('反领取成功')
        })
        .catch(() => { })
    },
    //获取发放方式
    getBoxData() {
      getBoxData().then((response) => {
        this.options = response.data
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
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
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
    // 公司选项
    labTypeChange(value) {
      this.queryParams.idOrg = value.id
      this.labTypeData.bindValue = value.khdwmc
    },
    //发放人选项
    issueIdChange(value) {
      this.queryParams.issueId = value.inputCode
      this.issueIdData.bindValue = value.username
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
    // 查看图片
    checkPic(signUrl) {
      this.signUrl = undefined
      if (signUrl) {
        this.signUrl = this.$getCookie('imgPath') + signUrl
      } else {
        this.$modal.msgWarning('暂未上传签名图片')
      }
    },
    // 添加
    handleAdd() {
      this.$refs.addItems.handleAdd()
    },
    // 删除
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(function () {
          // return delPrinttype(ids);
          return
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => { })
    },
    // 编辑
    handleUpdate(row) {
      this.$refs.addItems.handleUpdate(row)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        '/report/getReprot/export',
        {
          ...this.queryParams,
        },
        `职业报告领取.xlsx`
      )
    },
  },
}
</script>
