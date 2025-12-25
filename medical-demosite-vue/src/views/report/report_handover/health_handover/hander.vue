<!-- 健康报告交接-交接 麦沃德科技 开发人:清风/暴雨 -->
<template>
  <el-dialog :title="title" class="add-items" :visible.sync="open" width="1500px" append-to-body :close-on-click-modal="false" @close="cancel">
    <div class="add-container flex-direction-column">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
        <el-form-item label="体检号" prop="patientcode">
          <el-input ref="patientcode" placeholder="请输入体检号" clearable style="width: 160px" v-model="queryParams.patientcode" @keyup.enter.native="handleQuery"></el-input>
        </el-form-item>
        <!-- 设计稿没有 -->
        <!-- <el-form-item label="是否补全" label-width="90px">
          <el-checkbox v-model="form.autoFill" checked="true"></el-checkbox>
        </el-form-item> -->
        <el-form-item label="承接人">
          <input-select ref="inputSelect" :selectData="revPersonIdData" clearable selectWidth="150" @change="issueIdChange"></input-select>
        </el-form-item>
        <el-form-item label="承接人密码" label-width="100px" prop="password">
          <el-input placeholder="请输入承接人密码" clearable style="width: 180px" v-model="queryParams.password"></el-input>
        </el-form-item>
        <el-form-item label="交接/反交接日期" prop="revTime">
          <el-date-picker v-model="queryParams.revTime" clearable value-format="yyyy-MM-dd HH:mm:ss" type="datetime" style="width: 220px" placeholder="请选择日期时间"> </el-date-picker>
        </el-form-item>
        <el-form-item label="交接/反交接人">
          <el-input style="width: 160px" clearable v-model="queryParams.name" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleQuery">查询</el-button>
          <el-button type="primary" @click="handleremove" :disabled="multiple" icon="el-icon-delete">删除</el-button>
        </el-form-item>
      </el-form>
      <div class="table-box" style="min-height: 650px">
        <el-table border v-loading="loading" :data="tableData" height="670px" style="width: 100%" stripe @selection-change="handleSelectionChange">
          <el-table-column type="selection" align="center"></el-table-column>
          <el-table-column type="index" label="序列" width="55" align="center" fixed></el-table-column>
          <el-table-column prop="jktjzt" label="审核状态" min-width="140px" align="center" fixed>
            <template slot-scope="scope">
              <span :style="'color: ' + auditColor(scope.row.jktjzt)">
                {{ auditStatus(scope.row.jktjzt) }}
              </span>
            </template>
          </el-table-column>
          <el-table-column prop="patientcode" label="体检号" min-width="140px" align="center" fixed></el-table-column>
          <el-table-column prop="patientname" label="姓名" min-width="80px" align="center" fixed></el-table-column>
          <el-table-column prop="idSex" label="性别" min-width="80px" align="center">
            <template slot-scope="scope">
              <div v-if="scope.row.idSex == 0">男</div>
              <div v-if="scope.row.idSex == 1">女</div>
              <div v-if="scope.row.idSex == 2">通用</div>
            </template>
          </el-table-column>
          <el-table-column prop="age" label="年龄" min-width="80px" align="center"></el-table-column>
          <el-table-column prop="isjj" label="加急" min-width="80px" align="center">
            <template slot-scope="scope">
              <div v-if="scope.row.isjj == 0">否</div>
              <div v-if="scope.row.isjj == 1" style="color: green">是</div>
            </template>
          </el-table-column>
          <el-table-column prop="chestNum" label="柜子号" min-width="120px" align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.chestNum" placeholder=""></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="grantId" label="发放方式" min-width="120px" align="center">
            <template slot-scope="scope">
              <!-- <div v-for="item in options" :key="item.id">
                <span v-if="scope.row.grantId == item.id">{{ item.methodName }}</span>
              </div> -->
              <el-select v-model="scope.row.grantId" placeholder="">
                <el-option :label="item.methodName" :value="item.id" v-for="(item, index) in options" :key="index"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column prop="orgName" label="单位" min-width="170px" align="center"></el-table-column>
          <el-table-column prop="dateregister" label="登记时间" min-width="160px" align="center"></el-table-column>
          <el-table-column prop="phone" label="电话" min-width="120px" align="center"></el-table-column>
          <el-table-column prop="doctorapply" label="开单医生" min-width="100px" align="center"></el-table-column>
          <el-table-column prop="numorgresv" label="任务" min-width="120px" align="center"></el-table-column>
          <el-table-column prop="datefinalexamed" label="总检时间" min-width="160px" align="center"></el-table-column>
          <el-table-column prop="doctorfinalNameR" label="总检大夫" min-width="120px" align="center"></el-table-column>
        </el-table>
      </div>
    </div>

    <div slot="footer" class="dialog-footer" v-if="isBtn">
      <el-button type="primary" @click="submitForm(1)">交接</el-button>
      <el-button type="primary" plain @click="reset">重置</el-button>
      <el-button @click="cancel">取消</el-button>
    </div>
    <div slot="footer" class="dialog-footer" v-else>
      <el-button type="primary" @click="submitForm(2)">反交接</el-button>
      <el-button type="primary" plain @click="reset">重置</el-button>
      <el-button @click="cancel">取消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { getBoxData, getListData, getLoginName, getPatientData, saveOrUpdate, unaudit } from '@/api/report/health_handover/health_handover'
export default {
  data() {
    return {
      loading: false,
      // ids:[],//选中的数组
      single: true, //必选且单选
      multiple: true, //必选
      queryParams: {
        current: 1, //初始页数
        size: 10, //页值
        revPersonId: undefined,
        revTime: undefined,
        password: undefined,
        diseaseHealth: 0,
        patientcode: undefined,
        id: undefined,
        autoFill: true,
      },
      revPersonIdData: {
        placeholder: '请输入输入码选择',
        key: '部门', //第一列标题
        value: '姓名', //第二列标题
        url: '/outside/sendRegister/getLqrData', //请求连接
        bindValue: '',
        queryData: 'srm',
        firstName: 'inputCode',
        secondName: 'occupationSummary',
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

      // 发送方式
      options: [],
      open: false,
      form: {
        patientcode: '',
        type: '',
        autoFill: '',
        revPersonId: '',
        password: '',
        revTime: '',
        name: '',
      },
      tableData: [],
      isBtn: true,
      title: '报告交接',
    }
  },
  created() {
    this.getlogin()
    this.getBoxData()
  },
  methods: {
    // 交接/反交接
    submitForm(type) {
      let tableData = []
      this.tableData.forEach((el) => {
        let data = {
          chestNum: el.chestNum,
          grantId: el.grantId,
          id: el.id,
          patientcode: el.patientcode,
          patientname: el.patientname,
        }
        tableData.push(data)
      })
      let queryParams = {
        formdata: this.queryParams,
        griddata: tableData,
      }
      if (this.queryParams.revTime == '') {
        this.$modal.msgWarning('交接时间不能为空!')
      } else if (this.queryParams.password == '') {
        this.$modal.msgWarning('承接人密码不能为空!')
      } else if (this.queryParams.revPersonId == '') {
        this.$modal.msgWarning('承接人不能为空!')
      } else {
        if (type == 1) {
          saveOrUpdate(queryParams).then(() => {
            this.$modal.msgSuccess('交接成功!')
            this.cancel()
            this.reset()
            this.$emit('getFatherList')
          })
        } else {
          unaudit(queryParams).then(() => {
            this.$modal.msgSuccess('反交接成功!')
            this.cancel()
            this.reset()
            this.$emit('getFatherList')
          })
        }
      }
    },
    //查询列表
    getList() {
      this.loading = true
      getPatientData(this.queryParams).then(({ data }) => {
        let jiaji = []
        if (!this.tableData.length) {
          data.forEach((el) => {
            el.grantId = el.grantId ? el.grantId : '402881ae57e49a750157e49ce0e30008'
            if (el.isjj == 1) {
              jiaji.push(el.patientcode)
            }
          })
          this.tableData = data
        } else {
          data.forEach((el) => {
            el.grantId = el.grantId ? el.grantId : '402881ae57e49a750157e49ce0e30008'
            let isHas = false
            this.tableData.forEach((val) => {
              if (!isHas && el.id == val.id) {
                isHas = true
              }
              if (el.isjj == 1) {
                jiaji.push(el.patientcode)
              }
            })
            if (!isHas) {
              this.tableData.unshift(el)
            }
          })
        }
        if (jiaji.length) {
          this.$alert(`体检号${jiaji.join(',')}的顾客已置为加急,请加急处理!`)
        }
        this.queryParams.patientcode = ''
        this.loading = false
      })
    },
    //获取发放方式
    getBoxData() {
      getBoxData().then((response) => {
        this.options = response.data
      })
    },
    // 多选框选中数据
    examChange(selection) {
      this.examItems = selection
    },
    // 执行交接/反交接
    handerWindow(context, tempData) {
      this.open = true
      if (context == 0) {
        this.isBtn = true
        this.title = '报告交接'
        this.reset()
      } else if (context == 1) {
        this.isBtn = false
        this.title = '报告反交接'
        this.reset()
      }
      if (tempData && tempData.length) {
        this.$nextTick(() => {
          this.queryParams.patientcode = tempData.map((item) => item.patientcode).join(',')
          this.getList()
        })
      }
      this.$nextTick(() => {
        this.queryParams.revTime = this.$getDate()
        this.$refs.patientcode.focus()
      })
    },
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 查询
    handleQuery() {
      if (!this.queryParams.patientcode) {
        this.$alert('请输入体检号', '提示')
        return
      }
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
    //删除
    handleremove() {
      this.ids.forEach((val) => {
        this.tableData.forEach((el, index) => {
          if (val == el.id) {
            this.$delete(this.tableData, index)
          }
        })
      })
    },
    //获取登录用户姓名
    getlogin() {
      this.loading = true
      getLoginName().then((response) => {
        this.queryParams.name = response.data
        this.loading = false
      })
    },
    //承接人选项
    issueIdChange(value) {
      this.queryParams.revPersonId = value.id
    },
    //重置
    reset() {
      this.tableData = []
      this.queryParams.revPersonId = ''
      this.queryParams.revTime = ''
      this.queryParams.password = ''
      this.queryParams.patientcode = ''
      this.queryParams.patientname = ''
    },
    cancel() {
      this.open = false
      this.$refs.inputSelect.initData('')
      this.reset()
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
  },
}
</script>
