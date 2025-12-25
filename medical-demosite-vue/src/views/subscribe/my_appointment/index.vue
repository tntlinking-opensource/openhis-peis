<!-- 预约列表  开发人：麦沃德科技矢北 / 暴雨-->
<template>
  <!-- 筛选 -->
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="分中心" prop="branchIds">
        <el-select v-model="queryParams.branchIds" placeholder="请选择分中心" clearable style="width: 160px" @change="handleQuery">
          <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="预约号" prop="reservationNo">
        <el-input v-model="queryParams.reservationNo" placeholder="请输入预约号" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="预约手机号" prop="mobile">
        <el-input v-model="queryParams.mobile" placeholder="请输入预约手机号" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="预约类型" prop="levelId">
        <el-select v-model="queryParams.levelId" placeholder="请选择预约类型" @change="typeChanges" clearable style="width: 160px">
          <el-option v-for="options in levelIdOptions" :key="options.levelId" :label="options.levelName" :value="options.levelId" />
        </el-select>
      </el-form-item>
      <el-form-item label="预约单位" prop="khdwmc">
        <el-input v-model="queryParams.khdwmc" placeholder="请输入预约单位" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="预约日期" prop="startTime">
        <el-date-picker v-model="queryParams.startTime" style="width: 130px" value-format="yyyy-MM-dd " type="date" placeholder="请选择日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="-" prop="endTime">
        <el-date-picker v-model="queryParams.endTime" style="width: 130px" value-format="yyyy-MM-dd " type="date" placeholder="请选择日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="销售经理" prop="xsjl">
        <input-select :selectData="xsjlData" selectWidth="150" @change="xsjlChange"></input-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查询</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['subscribe:myAppointment:add']">新增预约</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-check" @click="complete" :disabled="single" v-hasPermi="['subscribe:myAppointment:edit']">完成预约</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-close" @click="dcomplete" :disabled="single" v-hasPermi="['subscribe:myAppointment:remove']">取消预约</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" @click="handleDelete" :disabled="multiple" v-hasPermi="['subscribe:myAppointment:removes']">批量删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-edit-outline" @click="handleuodata" :disabled="multiple" v-hasPermi="['subscribe:myAppointment:removes']">批量修改预约信息</el-button>
      </el-col>
    </el-row>

    <!-- 表格部分 -->
    <el-table ref="tableList" border v-loading="loading" :data="tableList" stripe @selection-change="handleSelectionChange" @row-click="rowClick" height="100%">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序列" width="55" type="index" align="center" />
      <el-table-column prop="branchName" label="分中心" align="center" min-width="110" show-overflow-tooltip />
      <el-table-column prop="reservationNo" label="预约号" align="center" min-width="70" show-overflow-tooltip />
      <el-table-column prop="levelId" label="预约类型" align="center" min-width="90">
        <template slot-scope="scope">
          <span v-if="scope.row.levelId == 1">普通会员</span>
          <span v-if="scope.row.levelId == 2">VIP</span>
          <span v-if="scope.row.levelId == 3">VVIP</span>
          <span v-if="scope.row.levelId == 4">贵宾</span>
        </template>
      </el-table-column>
      <el-table-column prop="reservationDate" label="预约时间" align="center" min-width="136" show-overflow-tooltip />
      <el-table-column prop="realName" label="体检者" align="center" min-width="80" />
      <el-table-column prop="khdwmc" label="体检单位" align="center" min-width="130" show-overflow-tooltip />
      <el-table-column prop="tjtcmc" label="体检套餐名称" align="center" min-width="120" show-overflow-tooltip />
      <el-table-column prop="patientcode" label="体检号" align="center" min-width="120" />
      <el-table-column prop="idcard" label="身份证号" align="center" min-width="140" show-overflow-tooltip />
      <el-table-column prop="mobile" label="预约手机号" align="center" min-width="100" show-overflow-tooltip />
      <el-table-column prop="remark" label="预约备注" align="center" min-width="90" show-overflow-tooltip />
      <el-table-column prop="status" label="预约状态" align="center" min-width="100">
        <template slot-scope="scope">
          <el-tag type="danger" v-if="scope.row.status == -1">预约失败</el-tag>
          <el-tag type="warning" v-if="scope.row.status == 1">待预约</el-tag>
          <el-tag type="success" v-if="scope.row.status == 2">已预约</el-tag>
          <el-tag v-if="scope.row.status == 3">预约结束</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="questionnaire" label="问卷状态" align="center" min-width="100">
        <template slot-scope="scope">
          <el-tag type="danger" v-if="scope.row.questionnaire == 0">未填写</el-tag>
          <el-tag type="success" v-else>已填写</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="failReason" label="预约失败原因" align="center" min-width="110" show-overflow-tooltip />
      <el-table-column fixed="right" label="操作" align="center" width="120">
        <template slot-scope="scope">
          <el-button size="mini" type="text" style="color: #0059ff" @click="editList(scope.row)">查看预约详情</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <pagination class="pagination1" v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />

    <!-- 弹窗部分 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" label-width="110px" :inline="true" hide-required-asterisk>
        <el-form-item label="预约类型" prop="levelId">
          <el-select v-model="form.levelId" placeholder="请选择预约类型" @change="typeChange" clearable style="width: 350px">
            <el-option v-for="options in levelIdOptions" :key="options.levelId" :label="options.levelName" :value="options.levelId" />
          </el-select>
        </el-form-item>
        <el-form-item label="姓名" prop="realName">
          <el-input v-model="form.realName" placeholder="请输入体检者姓名" style="width: 350px" />
        </el-form-item>
        <el-form-item label="性别" prop="idSex">
          <el-select style="width: 350px" v-model="form.idSex" placeholder="请选择性别">
            <el-option label="男" :value="0"></el-option>
            <el-option label="女" :value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="身份证号" prop="idcard">
          <el-input v-model="form.idcard" placeholder="请输入体检者身份证号" style="width: 350px" />
        </el-form-item>
        <el-form-item label="手机号" prop="mobile">
          <el-input v-model="form.mobile" placeholder="请输入体检者手机号" style="width: 350px" />
        </el-form-item>
        <el-form-item label="预约备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入预约备注" style="width: 350px" />
        </el-form-item>
        <el-form-item label="预约日期" prop="reservationDate">
          <el-date-picker style="width: 350px" v-model="forms.reservationDates" placeholder="请选择预约日期" type="date" value-format="yyyy-MM-dd HH:mm:ss"> </el-date-picker>
        </el-form-item>
        <el-form-item label="预约分中心" prop="fzx">
          <el-select v-model="form.branchId" placeholder="请选择预约的分中心" clearable ref="fzx" style="width: 350px">
            <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.id" />
          </el-select>
        </el-form-item>
      </el-form>
      <!-- 操作按钮 -->
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="getTimeslot">查询当日预约时间段</el-button>
        </el-col>
      </el-row>
      <el-tag style="width: 100%; font-size: 15px">请选择预约时间段</el-tag>
      <el-table border :data="examList" @selection-change="handleSelectionChanges" ref="tables" stripe v-loading="loading" height="300px" @row-click="handleRowClick">
        <el-table-column type="selection" align="center" />
        <el-table-column label="序列" type="index" width="60" align="center" />
        <el-table-column label="分中心" prop="branchName" align="center" show-overflow-tooltip min-width="90" />
        <el-table-column label="预约类型" prop="levelName" align="center" show-overflow-tooltip min-width="80" />
        <el-table-column label="预约状态" prop="status" align="center" show-overflow-tooltip min-width="80">
          <template slot-scope="scope">
            <span v-if="scope.row.ableNum == 0" style="color: red">已约满</span>
            <span v-if="scope.row.status == 1 && scope.row.ableNum != 0" style="color: green">开放预约</span>
          </template>
        </el-table-column>
        <el-table-column label="开始时间" prop="startTime" align="center" show-overflow-tooltip min-width="80" />
        <el-table-column label="结束时间" prop="endTime" align="center" show-overflow-tooltip min-width="80" />
        <el-table-column label="人数上限" prop="maxNum" align="center" show-overflow-tooltip min-width="80" />
        <el-table-column label="已预约人数" prop="doneNum" align="center" show-overflow-tooltip min-width="90" />
        <el-table-column label="可预约人数" prop="ableNum" align="center" show-overflow-tooltip min-width="90" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button type="primary" plain @click="reset">重 置</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!--查看详情弹窗-->
    <el-dialog title="预约详情" :visible.sync="opens" width="1000px" append-to-body>
      <el-form ref="form" :model="formss" label-width="110px" :inline="true" hide-required-asterisk v-loading="loadingForm">
        <el-form-item label="姓名" prop="realName">
          <el-input v-model="formss.realName" placeholder="暂无数据" :readonly="true" style="width: 350px" />
        </el-form-item>
        <el-form-item label="分中心" prop="branchName">
          <el-input v-model="formss.branchName" placeholder="暂无数据" :readonly="true" style="width: 350px" />
        </el-form-item>
        <el-form-item label="预约号" prop="reservationNo">
          <el-input v-model="formss.reservationNo" placeholder="暂无数据" :readonly="true" style="width: 350px" />
        </el-form-item>
        <el-form-item label="预约类型" prop="levelName">
          <el-input v-model="formss.levelName" placeholder="暂无数据" :readonly="true" style="width: 350px" />
        </el-form-item>
        <el-form-item label="预约日期" prop="reservationDate">
          <el-input v-model="formss.reservationDate" placeholder="暂无数据" :readonly="true" style="width: 350px" />
        </el-form-item>
        <el-form-item label="身份证号" prop="idcard">
          <el-input v-model="formss.idcard" placeholder="暂无数据" :readonly="true" style="width: 350px" />
        </el-form-item>
        <el-form-item label="手机号" prop="mobile">
          <el-input v-model="formss.mobile" placeholder="暂无数据" :readonly="true" style="width: 350px" />
        </el-form-item>
        <el-form-item label="预约备注" prop="remark">
          <el-input v-model="formss.remark" placeholder="暂无数据" :readonly="true" style="width: 350px" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关闭</el-button>
      </div>
    </el-dialog>

    <!--批量修改弹窗-->
    <el-dialog title="批量修改" :visible.sync="openss" width="1000px" append-to-body>
      <el-form ref="form" :model="formss" label-width="110px" :inline="true" hide-required-asterisk v-loading="loadingForm">
        <el-form-item label="预约类型" prop="levelName">
          <el-select v-model="plupdat.levelId" placeholder="请选择预约类型" @change="typeChanges" clearable style="width: 300px">
            <el-option v-for="options in levelIdOptions" :key="options.levelId" :label="options.levelName" :value="options.levelId" />
          </el-select>
        </el-form-item>
        <el-form-item label="预约日期" prop="reservationDate">
          <el-date-picker v-model="plupdat.reservationDate" style="width: 300px" value-format="yyyy-MM-dd hh:mm:ss" type="datetime" placeholder="请选择预约日期"></el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="plupdata">确 定</el-button>
        <el-button @click="cancels">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getListData, getBranchData, getListUserData, getLists, setstatus, addListData, delData, ck, plupadata } from '@/api/subscribe/my_appointment/my_appointment'
import { listBranch } from '@/api/system/branch.js'
import { getUserCid } from '@/api/system/branch.js'

export default {
  name: 'My_appointment',
  data() {
    return {
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      ///弹出框的表1
      examList: [],
      //  标题
      title: '新增预约',
      // 显示搜索条件
      showSearch: true,
      // 表单加载中
      loadingForm: false,
      // 分中心列表
      fzxOptions: [],
      //弹窗表单
      form: {
        branchId: undefined,
        levelId: undefined,
        branchId: undefined,
        realName: undefined,
        idcard: undefined,
        mobile: undefined,
        remark: undefined,
        reservationDate: undefined,
        timeId: undefined,
        reservationDates: undefined,
      },
      formss: {
        levelId: undefined,
        realName: undefined,
        branchName: undefined,
        reservationNo: undefined,
        levelName: undefined,
        reservationDate: undefined,
        idcard: undefined,
        mobile: undefined,
        remark: undefined,
      },
      forms: {
        branchId: undefined,
        reservationDate: undefined,
        reservationDates: undefined,
      },
      plupdat: {
        levelId: undefined,
        reservationDate: undefined,
      },
      xsjlData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '销售经理', //第二列标题
        firstName: 'inputCode', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'userName', //接口返回值对应第二列的参数名(不传默认为'name')
        url: '/crm/clientcommon/getXsryData', //请求连接
        bindValue: '',
        queryData: 'key', //向接口传递的参数名
      },
      //文本域
      textare: '',
      // 是否显示弹出层
      open: false,
      opens: false,
      openss: false,
      //信息总条数
      total: 0,
      loading: true,
      //预约类型
      levelIdOptions: [],
      // 查询参数
      queryParams: {
        date: '',
        levelId: '',
        xsjl: '',
        current: 1,
        size: 20,
        branchIds: undefined,
        khdwmc: undefined,
        inputCode: undefined,
        reservationNo: undefined,
        realName: undefined,
        reservationDate: undefined,
        startTime: undefined,
        endTime: undefined,
      },
      tableList: [],
      levelIds: undefined,
      // 是否为线上
      isOnline: false,
    }
  },
  created() {
    this.queryParams.branchIds = this.$getCookie('cid')
    this.isOnline = this.$getCookie('isOnline') == '1' ? true : false
    this.queryParams.startTime = this.$getDate().split(' ')[0]
    let toData = new Date(new Date().toLocaleDateString()).getTime()
    let past7daysStart = toData + 7 * 3600 * 24 * 1000
    let past7daysEnd = past7daysStart + 24 * 60 * 60 * 1000 - 1
    this.queryParams.endTime = this.$getDate(past7daysEnd).split(' ')[0]
    this.getUserData()
    this.handleQuery()
    if (this.isOnline) {
      listBranch({
        current: 1,
        size: 99999,
      }).then(({ data }) => {
        this.fzxOptions = data.records
      })
    } else {
      getUserCid().then(({ data }) => {
        this.fzxOptions = data
      })
    }
  },
  methods: {
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      if (this.queryParams.startTime) {
        this.queryParams.startTime = this.queryParams.startTime.substring(0, 10) + ' 00:00:00'
      }
      if (this.queryParams.endTime) {
        this.queryParams.endTime = this.queryParams.endTime.substring(0, 10) + ' 23:59:59'
      }
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    //查看弹窗
    handleuodata() {
      this.reset()
      this.openss = true
    },
    cancels() {
      this.reset()
      this.openss = false
    },
    plupdata() {
      let datas = {
        ids: this.ids,
        levelId: this.plupdat.levelId,
        reservationDate: this.plupdat.reservationDate,
        branchId: this.queryParams.branchIds,
      }
      this.levelIdOptions.forEach((el) => {
        if (el.levelId == this.plupdat.levelId) {
          datas.levelName = el.levelName
        }
      })
      plupadata(datas).then((response) => {
        if (response.code == 200) {
          this.$modal.msgSuccess('修改成功!')
        }
        this.cancels()
      })
    },
    // 选择类型
    xsjlChange(value) {
      this.queryParams.xsjl = value.userNo
    },
    handleRowClick(row) {
      this.$refs.tables.toggleRowSelection(row)
    },
    //弹窗保存按钮 
    submitForm() {
      if (this.form.timeId == undefined) {
        this.$alert('请选择一个时间段进行预约 !', '提示', {
          confirmButtonText: '确定',
        })
      } else if (this.form.mobile == undefined || this.form.idcard == undefined || this.form.realName == undefined) {
        this.$alert('请完善预约信息后提交(信息不能留空) !', '提示', {
          confirmButtonText: '确定',
        })
      } else {
        addListData(this.form).then((response) => {
          this.$modal.msgSuccess('预约成功!')
          this.open = false
          this.getList()
          this.levelIds = undefined
        })
      }
    },
    typeChange() {
      for (var i in this.levelIdOptions) {
        if (this.levelIdOptions[i].levelId == this.form.levelId) {
          this.form.levelName = this.levelIdOptions[i].levelName
        }
      }
    },
    typeChanges() {
      for (var i in this.levelIdOptions) {
        if (this.levelIdOptions[i].levelId == this.form.levelId) {
          this.form.levelName = this.levelIdOptions[i].levelName
        }
      }
    },
    handleSelectionChanges(selection) {
      this.levelIds = selection.map((item) => item.levelId).toString()
      this.branchIds = selection.map((item) => item.branchId).toString()
      // 单选
      if (selection.length > 1) {
        this.$refs.tables.clearSelection()
        this.$alert('请选择一个时间段进行预约!', '提示', {
          confirmButtonText: '确定',
        })
      }
      if (this.levelIds != '') {
        //判断预约类型,分中心
        if (this.levelIds > this.form.levelId) {
          this.$alert('请根据预约类型选择对应的时间段!', '提示', {
            confirmButtonText: '确定',
          })
          this.$refs.tables.clearSelection()
        } else {
          this.reservationDate = selection.map((item) => item.startTime)
          this.ids = selection.map((item) => item.id)
          this.form.timeId = this.ids.toString()
          this.form.reservationDate = this.forms.reservationDates.slice(0, 10) + ' ' + this.reservationDate.toString()
        }
      }
    },
    // 弹窗打开
    handleAdd() {
      this.reset()
      this.open = true
    },
    //重置
    reset() {
      this.examList = []
      this.form = {
        levelId: undefined,
        branchId: undefined,
        realName: undefined,
        idcard: undefined,
        mobile: undefined,
        remark: undefined,
        reservationDate: undefined,
        timeId: undefined,
        reservationDates: undefined,
      }
      this.forms = {
        reservationDate: undefined,
        reservationDates: undefined,
      }
    },
    // 查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    //查看弹窗
    editList(row) {
      this.reset()
      this.opens = true
      const id = row.id
      this.loadingForm = true
      ck(id)
        .then((response) => {
          this.formss = response.data
          this.loadingForm = false
        })
        .catch((error) => {
          console.error(error)
          this.loadingForm = false
        })
    },
    //获取当日时间段
    getTimeslot() {
      if (this.forms.reservationDates == undefined) {
        this.$alert('请选择预约日期后查询!', '提示', {
          confirmButtonText: '确定',
        })
      } else if (this.form.branchId == undefined) {
        this.$alert('请选择预约分中心后查询!', '提示', {
          confirmButtonText: '确定',
        })
      } else {
        this.loading = true
        let param = {
          reservationDate: this.forms.reservationDates,
          branchId: this.form.branchId,
          levelId: this.form.levelId,
        }

        getLists(param).then((response) => {
          this.examList = response.data
          this.loading = false
        })
      }
    },
    //删除
    handleDelete() {
      const ids = this.ids
      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(function () {
          return delData(ids)
          return
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {})
    },
    //完成预约
    complete() {
      if (this.status == 3) {
        this.$modal.msgWarning('本次预约已完成,请勿重复提交!')
      } else if (this.status == -1) {
        this.$modal.msgWarning('本次预约已取消,无法完成预约!')
      } else {
        this.$confirm('是否确认完成预约?', '预约提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        })
          .then(() => {
            this.rows[0].status = 3
            return setstatus(this.rows[0])
          })
          .then(() => {
            this.$modal.msgSuccess('预约已完成')
            this.getList()
          })
          .catch(() => {})
      }
    },
    //取消预约
    dcomplete() {
      if (this.status == 3) {
        this.$modal.msgWarning('不能取消已经结束的预约!')
      } else if (this.status == -1) {
        this.$modal.msgWarning('本次预约已取消,请勿重复提交!')
      } else {
        this.$confirm('是否确认取消预约?', '预约提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        })
          .then(() => {
            this.rows[0].status = -1
            return setstatus(this.rows[0])
          })
          .then(() => {
            this.$modal.msgSuccess('预约已取消')
            this.getList()
          })
          .catch(() => {})
      }
    },

    //获取预约类型
    getUserData() {
      getListUserData().then((response) => {
        this.levelIdOptions = response.data
      })
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.status = selection.map((item) => item.status)
      this.single = selection.length != 1
      this.multiple = !selection.length
      this.rows = selection
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },

    ///取消按钮
    cancel() {
      this.open = false
      this.opens = false
      this.reset()
    },
  },
}
</script>

<style lang="scss" scoped>
.el-dialog {
  display: flex;
  flex-direction: column;
  margin: 0 !important;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  /*height:600px;*/
  max-height: calc(100% - 200px);
  max-width: calc(100% - 30px);
}

.pagination1 {
  text-align: center;
}
</style>
