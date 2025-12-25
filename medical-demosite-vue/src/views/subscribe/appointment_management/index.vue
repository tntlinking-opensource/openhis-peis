<!-- 默认设置  开发人：麦沃德科技矢北 / 暴雨-->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form size="small" :model="queryParams" ref="queryForm" :inline="true" class="no-margin-bottom">
      <el-form-item prop="levelId" label="预约类型">
        <el-select v-model="queryParams.levelId" placeholder="请选择预约类型" style="width: 230px">
          <el-option v-for="options in levelIdOptions" :key="options.levelId" :label="options.levelName" :value="options.levelId" />
        </el-select>
      </el-form-item>
      <el-form-item prop="branchId" label="分中心">
        <el-select v-model="queryParams.branchId" placeholder="请选择分中心" clearable>
          <el-option v-for="item in branchIdOptions" :key="item.id" :label="item.fzx" :value="item.branchId" />
        </el-select>
      </el-form-item>
      <el-form-item prop="status" label="预约状态">
        <el-select v-model="queryParams.status" placeholder="请选择预约状态" style="width: 230px">
          <el-option label="关闭" value="0" />
          <el-option label="开放" value="1" />
          <el-option label="过期" value="2" />
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
        <el-button type="primary" plain size="mini" icon="el-icon-plus" v-hasPermi="['subscribe:appointmentManagement:add']" @click="showDialog">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" :disabled="single" icon="el-icon-edit" v-hasPermi="['subscribe:appointmentManagement:remove']" @click="editList">编辑 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" :disabled="multiple" icon="el-icon-delete" v-hasPermi="['subscribe:appointmentManagement:edit']" @click="handleDelete">批量删除 </el-button>
      </el-col>
    </el-row>
    <!-- 表格部分 -->
    <div class="table-box">
      <el-table ref="tableList" border v-loading="loading" :data="tableList" stripe height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column prop="branchName" label="分中心" align="center" />
        <el-table-column prop="startTime" label="时间段开始时间" align="center" />
        <el-table-column prop="endTime" label="时间段结束时间" align="center" />
        <el-table-column prop="maxNum" label="可预约人数上限" align="center" />
        <el-table-column prop="levelName" label="预约类型" align="center" />
        <el-table-column prop="openDay" label="预约开放日" align="center" width="130">
          <template slot-scope="scope"> 星期:{{ scope.row.openDay }} </template>
        </el-table-column>
        <el-table-column prop="status" label="预约开放状态" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.status == 0" style="color: red">未开放预约</span>
            <span v-if="scope.row.status == 1" style="color: green">开放预约</span>
            <span v-if="scope.row.status == 2" style="color: orange">预约过期</span>
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="预约备注" align="center" show-overflow-tooltip />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #ffba00" @click="editList(scope.row)">编辑</el-button>
            <el-button size="mini" type="text" style="color: #ff2727" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页器 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 弹窗部分 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" label-width="140px" hide-required-asterisk>
        <el-form-item label="预约类型" prop="levelId">
          <el-select v-model="form.levelId" placeholder="请选择预约类型" clearable style="width: 308px">
            <el-option v-for="options in levelIdOptions" :key="options.levelId" :label="options.levelName" :value="options.levelId" />
          </el-select>
        </el-form-item>
        <el-form-item label="分中心" prop="branchId">
          <el-select v-model="form.branchId" placeholder="请选择分中心" style="width: 308px">
            <el-option v-for="options in branchIdOptions" :key="options.branchId" :label="options.fzx" :value="options.branchId" />
          </el-select>
        </el-form-item>
        <el-form-item label="可预约人数上限" prop="maxNum">
          <el-input-number v-model="form.maxNum" controls-position="right" :min="0" style="width: 308px" />
        </el-form-item>
        <el-form-item label="设置预约开放日" prop="openDay">
          <el-checkbox-group v-model="form.openDay">
            <el-checkbox :label="1">星期一</el-checkbox>
            <el-checkbox :label="2">星期二</el-checkbox>
            <el-checkbox :label="3">星期三</el-checkbox>
            <el-checkbox :label="4">星期四</el-checkbox>
            <el-checkbox :label="5">星期五</el-checkbox>
            <el-checkbox :label="6">星期六</el-checkbox>
            <el-checkbox :label="7">星期日</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="设置预约时间段" prop="data">
          <el-time-select placeholder="预约起始时间" v-model="form.startTime" style="width: 308px" :picker-options="{ start: '07:30', step: '00:30', end: '11:30' }"> </el-time-select>
          <el-time-select placeholder="预约结束时间" v-model="form.endTime" style="width: 308px; margin-top: 10px" :picker-options="{ start: '08:00', step: '00:30', end: '12:00', minTime: form.startTime }"> </el-time-select>
        </el-form-item>
        <el-form-item label="是否开放预约" prop="afternoon">
          <template>
            <el-radio v-model="form.status" :label="1">是</el-radio>
            <el-radio v-model="form.status" :label="0">否</el-radio>
          </template>
        </el-form-item>
        <el-form-item label="预约备注" prop="afternoon">
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
  </div>
</template>

<script>
import { addListData, defaultData, getBranchData, getListData, getListUserData, deleteData, getData } from '@/api/subscribe/appointment_management/appointment_management'

export default {
  data() {
    return {
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 遮罩层
      loading: false,
      // 是否显示弹出层
      open: false,
      title: '',
      value: '',
      value2: '',
      tableList: [],
      //页码信息
      queryParams: {
        current: 1,
        size: 20,
        levelId: undefined,
        branchId: undefined,
        status: undefined,
      },
      // 表单参数
      form: {
        levelId: undefined,
        branchId: undefined,
        maxNum: undefined,
        startTime: undefined,
        endTime: undefined,
        status: undefined,
        remark: undefined,
        openDay: [],
      },
      //预约类型
      levelIdOptions: [],
      //分中心
      branchIdOptions: [],
      //信息总条数
      total: 0,
    }
  },
  created() {
    this.queryParams.branchId = this.$getCookie('cid')
    this.getUserData()
    this.getfzxData()
    this.getList()
  },
  methods: {
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
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
    //新增提交
    submitForm() {
      if (this.form.levelId == undefined || this.form.maxNum == undefined || this.form.startTime == undefined || this.form.endTime == undefined || this.form.status == undefined || this.form.openDay == '') {
        this.$modal.msgWarning('请完善全部信息后提交!')
      } else {
        let form = JSON.parse(JSON.stringify(this.form))
        form.openDay = this.form.openDay.join(',')
        form.startTime = form.startTime + ':00'
        form.endTime = form.endTime + ':00'
        addListData(form).then((response) => {
          this.$modal.msgSuccess('添加成功')
          this.open = false
          this.getList()
        })
      }
    },
    //编辑提交
    defaultForm() {
      let form = JSON.parse(JSON.stringify(this.form))
      form.openDay = this.form.openDay.join(',')
      defaultData(form).then((response) => {
        this.$modal.msgSuccess('编辑成功')
        this.open = false
        this.getList()
      })
    },
    // 编辑
    editList(row) {
      this.reset()
      this.loading = true
      const ids = row.id || this.ids
      getData(ids).then((response) => {
        this.form = response.data
        this.form.openDay = this.form.openDay.split(',')
        this.form.openDay = this.form.openDay.map((el) => parseInt(el))
        this.loading = false
      })
      this.open = true
      this.title = '编辑'
    },
    //获取预约类型
    getUserData() {
      getListUserData().then((response) => {
        this.levelIdOptions = response.data
      })
    },
    //获取分中心
    getfzxData() {
      getBranchData().then((response) => {
        this.branchIdOptions = response.data
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
    //删除
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(function () {
          return deleteData(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {})
    },
    // 表单重置
    reset() {
      this.form = {
        levelId: undefined,
        branchId: this.$getCookie('cid'),
        maxNum: undefined,
        startTime: undefined,
        endTime: undefined,
        status: undefined,
        remark: undefined,
        openDay: [],
      }
    },
    showDialog() {
      this.reset()
      this.open = true
      this.title = '新增预约'
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
