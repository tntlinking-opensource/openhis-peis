<!-- 预约设置  开发人：麦沃德科技矢北 / 暴雨-->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form size="small" :model="queryParams" ref="queryForm" :inline="true" class="no-margin-bottom">
      <el-form-item prop="branchIds" label="分中心">
        <el-select v-model="queryParams.branchIds" placeholder="请选择分中心" clearable @change="handleQuery">
          <el-option v-for="item in branchIdOptions" :key="item.id" :label="item.fzx" :value="item.branchId" />
        </el-select>
      </el-form-item>
      <el-form-item prop="levelId" label="预约类型">
        <el-select v-model="queryParams.levelId" placeholder="请选择预约类型" style="width: 230px" @change="handleQuery">
          <el-option v-for="options in levelIdOptions" :key="options.levelId" :label="options.levelName" :value="options.levelId" />
        </el-select>
      </el-form-item>
      <el-form-item prop="status" label="预约状态">
        <el-select v-model="queryParams.status" placeholder="请选择预约状态" style="width: 230px" clearable @change="handleQuery">
          <el-option label="关闭" value="0" />
          <el-option label="开放" value="1" />
          <el-option label="过期" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item prop="startTime" label="预约日期">
        <el-date-picker style="width: 130px" v-model="queryParams.startTime" type="date" placeholder="请选择日期" value-format="yyyy-MM-dd" @change="handleQuery"> </el-date-picker>
      </el-form-item>
      <el-form-item label="-" prop="endTime">
        <el-date-picker style="width: 130px" v-model="queryParams.endTime" value-format="yyyy-MM-dd" type="date" placeholder="请选择预约日期" @change="handleQuery"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8" style="flex: 1">
      <el-col :span="8" style="height: 100%; display: flex; flex-direction: column">
        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain size="mini" icon="el-icon-edit" v-hasPermi="['subscribe:appointmentDetails:edits']" :disabled="multiple" @click="handleEdits">批量设置</el-button>
          </el-col>
        </el-row>
        <!-- 表格部分 -->
        <div class="table-box">
          <el-table ref="tableListL" border v-loading="loadingL" :data="tableListL" stripe height="calc(100% - 70px)" @selection-change="handleSelectionChange" @row-click="rowClick">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="序列" width="55" type="index" align="center" />
            <el-table-column prop="branchName" label="分中心" align="center" />
            <el-table-column prop="reservationDate" label="预约日期" align="center" :formatter="formatterTime" />
          </el-table>
          <!-- 分页器 -->
          <pagination v-show="totalL > 0" :total="totalL" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getLeftTabel" />
        </div>
      </el-col>
      <el-col :span="16" style="height: 100%; display: flex; flex-direction: column">
        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain size="mini" icon="el-icon-plus" v-hasPermi="['subscribe:appointmentDetails:add']" @click="showDialog">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain size="mini" :disabled="singleR" icon="el-icon-edit" v-hasPermi="['subscribe:appointmentDetails:edit']" @click="editList">编辑 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain size="mini" :disabled="multipleR" icon="el-icon-delete" v-hasPermi="['subscribe:appointmentDetails:remove']" @click="handleDelete">删除 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain size="mini" :disabled="multipleR" icon="el-icon-circle-close" v-hasPermi="['subscribe:appointmentDetails:edit']" @click="handleClose">批量关闭</el-button>
          </el-col>
        </el-row>
        <!-- 表格部分 -->
        <div class="table-box">
          <el-table ref="tableListR" border v-loading="loadingR" :data="tableListR" stripe height="calc(100% - 70px)" @selection-change="handleSelectionChangeR" @row-click="rowClickR">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="序列" width="55" type="index" align="center" />
            <el-table-column prop="startTime" label="开始时间" align="center" show-overflow-tooltip />
            <el-table-column prop="endTime" label="结束时间" align="center" show-overflow-tooltip />
            <el-table-column prop="levelName" label="预约类型" align="center" />
            <el-table-column prop="maxNum" label="预约人数上限" align="center" />
            <el-table-column prop="doneNum" label="已预约人数" align="center" />
            <el-table-column prop="ableNum" label="剩余可预约人数" align="center" />
            <el-table-column prop="status" label="开放状态" align="center">
              <template slot-scope="scope">
                <span v-if="scope.row.status == 0" style="color: red">未开放预约</span>
                <span v-if="scope.row.status == 1" style="color: green">开放预约</span>
                <span v-if="scope.row.status == 2" style="color: orange">预约过期</span>
              </template>
            </el-table-column>
            <el-table-column prop="remark" label="备注" align="center" show-overflow-tooltip />
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button size="mini" type="text" style="color: #ffba00" @click="editList(scope.row)">编辑</el-button>
                <el-button size="mini" type="text" style="color: #ff2727" @click="handleDelete(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <!-- 分页器 -->
          <pagination v-show="totalR > 0" :total="totalR" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getRightTabel" />
        </div>
      </el-col>
    </el-row>

    <!-- 弹窗部分 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" v-loading="loading" label-width="140px" hide-required-asterisk>
        <el-form-item label="预约类型" prop="levelId">
          <el-select v-model="form.levelId" placeholder="请选择预约类型" clearable style="width: 308px">
            <el-option v-for="options in levelIdOptions" :key="options.levelId" :label="options.levelName" :value="options.levelId" />
          </el-select>
        </el-form-item>
        <!--        <el-form-item label="分中心" prop="branchId">-->
        <!--          <el-select v-model="form.branchId" placeholder="请选择分中心" clearable style="width: 308px;">-->
        <!--            <el-option v-for="options in branchIdOptions" :key="options.branchId" :label="options.fzx" :value="options.branchId" />-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->
        <el-form-item label="可预约人数上限" prop="maxNum">
          <el-input-number v-model="form.maxNum" controls-position="right" :min="0" style="width: 308px" :precision="0" />
        </el-form-item>
        <el-form-item label="已预约人数" prop="doneNum">
          <el-input-number v-model="form.doneNum" controls-position="right" :min="0" style="width: 308px" />
        </el-form-item>
        <el-form-item label="剩余可预约人数" prop="ableNum">
          <el-input v-model="ableNum" controls-position="right" :min="0" style="width: 308px" :readonly="true" />
        </el-form-item>
        <el-form-item label="预约日期" prop="reservationDate">
          <el-date-picker v-model="form.reservationDate" type="date" placeholder="选择预约日期" style="width: 308px" value-format="yyyy-MM-dd HH:mm:ss" />
        </el-form-item>
        <el-form-item label="预约开始时间" prop="startTime">
          <!-- <el-date-picker v-model="form.startTime" type="datetime" placeholder="选择开始时间" style="width: 308px" value-format="yyyy-MM-dd HH:mm:ss" /> -->
          <el-time-select
            v-model="form.startTime"
            :picker-options="{
              start: '07:30',
              step: '00:30',
              end: '10:30',
              maxTime: form.endTime ? form.endTime : '',
            }"
            placeholder="选择时间"
            value-format="HH:mm:ss"
            style="width: 308px"
          >
          </el-time-select>
        </el-form-item>
        <el-form-item label="预约结束时间" prop="endTime">
          <!-- <el-date-picker v-model="form.endTime" type="datetime" placeholder="选择结束时间" style="width: 308px" value-format="yyyy-MM-dd HH:mm:ss" /> -->
          <el-time-select
            v-model="form.endTime"
            :picker-options="{
              start: '07:30',
              step: '00:30',
              end: '10:30',
              minTime: form.startTime,
            }"
            placeholder="选择时间"
            value-format="HH:mm:ss"
            style="width: 308px"
          >
          </el-time-select>
        </el-form-item>
        <el-form-item label="是否开放预约" prop="status">
          <template>
            <el-radio v-model="form.status" :label="1">是</el-radio>
            <el-radio v-model="form.status" :label="0">否</el-radio>
          </template>
        </el-form-item>
        <el-form-item label="预约备注" prop="remark">
          <el-input type="textarea" :rows="3" placeholder="请输入预约备注" v-model="form.remark" />
        </el-form-item>
      </el-form>
      <!-- 尾部 -->
      <div slot="footer" class="dialog-footer" v-if="title == '新增预约'">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
      <div slot="footer" class="dialog-footer" v-if="title == '编辑'">
        <el-button type="primary" @click="defaultForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 批量设置 -->
    <batch-setting ref="batchSetting" @getRightTabel="getRightTabel"></batch-setting>
  </div>
</template>

<script>
import { addListData, defaultData, getBranchData, getListData, getListUserData, deleteData, getData, queryReservationDate, queryReservationTime, batchClose } from '@/api/subscribe/appointment_details/appointment_details'
import { getUserCid } from '@/api/system/branch.js'
import batchSetting from './batch_setting'
export default {
  components: {
    batchSetting,
  },
  data() {
    return {
      // 非多个禁用
      multiple: true,
      // 非单个禁用
      singleR: true,
      // 非多个禁用
      multipleR: true,
      // 遮罩层
      loading: false,
      loadingL: false,
      loadingR: false,
      // 是否显示弹出层
      open: false,
      title: '',
      value: '',
      value2: '',
      tableListL: [],
      tableListR: [],
      //信息总条数
      totalL: 0,
      totalR: 0,
      // 选中的表格项id
      ids: [],
      //页码信息
      queryParams: {
        current: 1,
        size: 20,
        startTime: undefined,
        endTime: undefined,
        levelId: undefined,
        branchIds: undefined,
        status: undefined,
      },
      queryParamsR: {
        current: 1,
        size: 20,
      },
      //预约类型
      levelIdOptions: [],
      //分中心
      branchIdOptions: [],
      // 表单参数
      form: {
        levelId: undefined,
        branchId: undefined,
        maxNum: undefined,
        doneNum: undefined,
        ableNum: undefined,
        reservationDate: undefined,
        startTime: undefined,
        endTime: undefined,
        status: undefined,
        remark: undefined,
      },
      formatterTime(row, column) {
        let data = row[column.property]
        return /\d{4}-\d{1,2}-\d{1,2}/g.exec(data)
      },
      // 批量设置的日期
      reservationDate: '',
      // 选中的日期
      selectDate: '',
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
    this.getLeftTabel()
    this.getfzxData()
    this.getUserData()
  },
  computed: {
    ableNum: {
      get() {
        return parseInt(this.form.maxNum - this.form.doneNum) || 0
      },
      set(newValue) {
        return newValue
      },
    },
  },
  methods: {
    //获取预约类型
    getUserData() {
      getListUserData().then((response) => {
        this.levelIdOptions = response.data
      })
    },
    //获取分中心
    getfzxData() {
      if (this.isOnline) {
        getBranchData().then((response) => {
          this.branchIdOptions = response.data
        })
      } else {
        getUserCid().then(({ data }) => {
          this.branchIdOptions = data
        })
      }
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      if (this.queryParams.startTime) {
        this.queryParams.startTime = this.queryParams.startTime.substring(0, 10) + ' 00:00:00'
      }
      if (this.queryParams.endTime) {
        this.queryParams.endTime = this.queryParams.endTime.substring(0, 10) + ' 23:59:59'
      }
      this.getLeftTabel()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.tableListR = []
      this.handleQuery()
    },
    // 获取左侧表格数据
    getLeftTabel() {
      this.loadingL = true
      queryReservationDate(this.queryParams)
        .then((response) => {
          this.tableListL = response.data.records
          this.totalL = response.data.total
          if (this.tableListL.length) {
            this.$nextTick(() => {
              // this.$refs.tableListL.toggleRowSelection(this.tableListL[this.tableListL.length - 1])
              this.rowClick(this.tableListL[this.tableListL.length - 1], {})
            })
          }
          this.loadingL = false
        })
        .catch((err) => {
          console.error(err)
          this.loadingL = false
        })
    },
    // 获取右侧表格数据
    getRightTabel() {
      this.loadingR = true
      queryReservationTime({ ...this.queryParams, ...this.queryParamsR, reservationDate: this.selectDate })
        .then((response) => {
          this.tableListR = response.data.records
          this.totalR = response.data.total
          this.loadingR = false
        })
        .catch((err) => {
          console.error(err)
          this.loadingR = false
        })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      // this.ids = selection.map((item) => item.id)
      if (!selection || !selection[0]) {
        return
      }
      this.multiple = !selection.length
      this.reservationDate = selection[0].reservationDate + ' 00:00:00'
      if (selection.length) {
        this.$nextTick(() => {
          this.selectDate = selection[selection.length - 1].reservationDate
          this.getRightTabel()
        })
      }
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableListL.clearSelection()
      this.$refs.tableListL.toggleRowSelection(row)
    },
    // 多选框选中数据
    handleSelectionChangeR(selection) {
      this.ids = selection.map((item) => item.id)
      this.singleR = selection.length != 1
      this.multipleR = !selection.length
    },
    // 表格单击事件
    rowClickR(row, col) {
      if (col.type != 'selection') this.$refs.tableListR.clearSelection()
      this.$refs.tableListR.toggleRowSelection(row)
    },
    // 批量设置
    handleEdits() {
      this.$refs.batchSetting.showDialog(this.reservationDate)
    },

    // 新增
    showDialog() {
      this.reset()
      this.open = true
      this.title = '新增预约'
    },
    //新增提交
    submitForm() {
      if (this.form.levelId == undefined || this.form.maxNum == undefined || this.form.startTime == undefined || this.form.endTime == undefined || this.form.status == undefined || this.form.doneNum == undefined || this.ableNum == undefined || this.form.reservationDate == undefined) {
        this.$modal.msgWarning('请完善全部信息后提交!')
      } else {
        this.form.ableNum = this.ableNum
        let form = JSON.parse(JSON.stringify(this.form))
        form.startTime += ':00'
        form.endTime += ':00'
        addListData(form).then((response) => {
          this.$modal.msgSuccess('添加成功')
          this.open = false
          this.getLeftTabel()
        })
      }
    },
    //编辑提交
    defaultForm() {
      this.form.ableNum = this.ableNum
      let form = JSON.parse(JSON.stringify(this.form))
      form.startTime = form.startTime.slice(0, 5) + ':00'
      form.endTime = form.endTime.slice(0, 5) + ':00'
      defaultData(form).then((response) => {
        this.$modal.msgSuccess('编辑成功')
        this.open = false
        this.getRightTabel()
      })
    },
    //计算剩余预约人数
    compute() {
      this.form.ableNum = this.form.maxNum - this.form.doneNum
    },
    // 编辑
    editList(row) {
      this.reset()
      this.loading = true
      const ids = row.id || this.ids[0]
      getData(ids).then((response) => {
        this.form = response.data
        this.loading = false
      })
      this.open = true
      this.title = '编辑'
    },
    //表单删除
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal
        .confirm('是否确认删除该数据项？', '提示')
        .then(function () {
          return deleteData(ids)
        })
        .then(() => {
          this.getLeftTabel()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {})
    },
    // 批量关闭选择预约场次
    handleClose() {
      const ids = this.ids
      this.$modal
        .confirm('是否确认关闭选择预约场次？', '提示')
        .then(function () {
          return batchClose(ids)
        })
        .then(() => {
          this.getLeftTabel()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {})
    },
    // 表单重置
    reset() {
      this.form = {
        levelId: undefined,
        branchId: undefined,
        maxNum: undefined,
        doneNum: undefined,
        ableNum: undefined,
        reservationDate: undefined,
        startTime: undefined,
        endTime: undefined,
        status: undefined,
        remark: undefined,
      }
    },
    ///取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
  },
}
</script>

<style></style>
