<!-- 危急值提报 开发人：麦沃德科技暴雨/清风 -->
<template>
  <div class="app-container flex-direction-column crisis-value-main">
    <!--页面-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="提报时间">
        <el-date-picker v-model="queryParams.date" style="width: 320px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="姓名" prop="gwrymc">
        <el-input v-model="queryParams.gwrymc" placeholder="请输入姓名  " clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检号" prop="tjid">
        <el-input v-model="queryParams.tjid" placeholder="请输入体检号" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="公司" prop="idOrg">
        <input-select :selectData="idOrgData" selectWidth="160" @change="idOrgDataChange"></input-select>
      </el-form-item>
      <el-form-item label="科室" prop="reportDivision">
        <input-select :selectData="ksData" selectWidth="160" @change="ksDataChange"></input-select>
      </el-form-item>
      <el-form-item label="危急程度" prop="wjzjb">
        <el-select v-model="queryParams.wjzjb" placeholder="请选择" clearable style="width: 160px">
          <el-option label="低" value="3" />
          <el-option label="中" value="2" />
          <el-option label="高" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="体检分类" prop="useCodeHiden">
        <el-select v-model="queryParams.useCodeHiden" placeholder="请选择" clearable style="width: 160px">
          <el-option label="加项" :value="0" />
          <el-option label="弃检" :value="1" />
          <el-option label="补检" :value="2" />
          <el-option label="迟检补检" :value="3" />
          <el-option label="退项" :value="4" />
        </el-select>
      </el-form-item>
      <el-form-item label="开单医师" prop="idOpendoctor">
        <input-select :selectData="OpendoctorData" :isTrim="true" selectWidth="160" @change="OpendoctorDataChange"></input-select>
      </el-form-item>
      <el-form-item label="订单号" prop="numorgresv">
        <el-input v-model="queryParams.numorgresv" placeholder="请输入订单号" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="处理人" prop="hfclr">
        <el-input v-model="queryParams.hfclr" placeholder="请输入处理人" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['funcdept:crisisValue:add']">危急值添加 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit-outline" @click="handleUpdate" :disabled="single" v-hasPermi="['funcdept:crisisValue:edit']">编辑危急值 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" @click="handleDelete" v-hasPermi="['funcdept:crisisValue:delete']">删除 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-circle-check" :disabled="single" @click="handlebp" v-hasPermi="['funcdept:crisisValue:businessProcessing']">业务处理 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" size="mini" icon="el-icon-circle-check" :disabled="single" @click="handlerv" v-hasPermi="['funcdept:crisisValue:returnVisit']">回访处理 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-circle-check" :disabled="single" @click="handleeh" v-hasPermi="['funcdept:crisisValue:expertHandling']">专家处理 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['funcdept:crisisValue:export']">导出Excel </el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box" ref="tableBox">
      <el-table id="setTable" ref="tableData" border v-loading="loading" :data="tableList" height="100%" stripe @row-dblclick="handleDoubleClickbp" @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="体检号" prop="tjid" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="gwrymc" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="性别" prop="xb" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <div v-if="scope.row.xb == 0">男</div>
            <div v-else>女</div>
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="nl" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="危急程度" prop="wjzjb" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ ['', '高', '中', '低'][scope.row.wjzjb] }}
          </template>
        </el-table-column>
        <el-table-column label="公司" prop="orgName" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="提报时间" prop="reportTime" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="体检结果" prop="wjzxj" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="业务处理状态" prop="ywclzt" min-width="120px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.ywclzt == 1">已处理</el-tag>
            <el-tag v-else>未处理</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="回访处理状态" prop="hfclzt" min-width="120px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.hfclzt == 0">已提报</el-tag>
            <el-tag v-else-if="scope.row.hfclzt == 1">继续提报</el-tag>
            <el-tag type="success" v-else-if="scope.row.hfclzt == 2">已完成</el-tag>
            <el-tag v-else-if="scope.row.hfclzt == 3">已取消</el-tag>
            <span v-else></span>
          </template>
        </el-table-column>
        <el-table-column label="专家处理状态" prop="zjclzt" min-width="120px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.zjclzt == 1" type="success">已处理</el-tag>
            <el-tag v-else>未处理</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="业务处理人" prop="ywclr" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="业务处理时间" prop="ywclsj" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="业务处理备注" prop="ywbz" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="提报科室" prop="reportDivision" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="订单号" prop="numorgresv" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="部门" prop="orgDepart" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="电话" prop="lxdh" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="开单医生" prop="doctorapply" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="回访处理人" prop="hfclr" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="回访处理时间" prop="hfclsj" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="回访处理方式" prop="hfclfs" min-width="120px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.hfclfs == 0">当面告知</el-tag>
            <el-tag v-if="scope.row.hfclfs == 1">电话通知</el-tag>
            <el-tag v-if="scope.row.hfclfs == 2">短信通知</el-tag>
            <el-tag v-if="scope.row.hfclfs == 3">继续提报</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="回访处理备注" prop="hfbz" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="专家处理人" prop="zjclr" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="专家处理时间" prop="zjclsj" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="专家处理方式" prop="zjclfs" min-width="120px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.zjclfs == 0">当面告知</el-tag>
            <el-tag v-if="scope.row.zjclfs == 1">电话通知</el-tag>
            <el-tag v-if="scope.row.zjclfs == 2">短信通知</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="专家处理备注" prop="zjbz" min-width="120px" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改对话框 -->
    <add-items ref="addItems" @getList="getList"></add-items>
    <!-- 业务处理 -->
    <yw-items ref="ywItems" @getList="getList"></yw-items>
    <!-- 回访处理 -->
    <hf-items ref="hfItems" @getList="getList"></hf-items>
    <!-- 专家处理 -->
    <zj-items ref="zjItems" @getList="getList"></zj-items>
  </div>
</template>
<script>
import addItems from './add'
import ywItems from './business_processing'
import hfItems from './return_visit'
import zjItems from './experts_handling'
import { getPage, crisisValueDelete } from '@/api/crisis/crisis_value.js'

export default {
  name:'Manage_crisis_value',
  components: { addItems, ywItems, hfItems, zjItems },
  data() {
    return {
      OpendoctorData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '名称', //第二列标题
        url: '/report/healthAssociate/getAllUser', //请求连接--开单医师下拉路由
        secondName: 'username',
        bindValue: '',
      },
      idOrgData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '名称', //第二列标题
        url: '/abteilung/CrisisValue/getListData', //请求连接--获取公司下拉路由
        firstName: 'khdwmc',
        secondName: 'khdwsrm',
        bindValue: '',
      },
      ksData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '名称', //第二列标题
        url: '/basconclusion/findAllDepartment', //请求连接--提交科室下拉路由
        bindValue: '',
      },
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        tjid: undefined,
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
        idOrg: undefined, //公司查询
        reportDivision: undefined, //科室码查询
        openDoctorData: undefined, //开单医师
      },
      // 表格展示数据
      tableList: [],
      // 显示模态框
      showExam: false,
      showUpload: false,
      bpid: '', //业务处理id
      tjid: '', //体检id
    }
  },
  computed: {},
  watch: {},
  created() {
    const id = this.$route.params.patientCode

    this.queryParams.tjid = id
    this.getList()
  },
  mounted() {},
  methods: {
    //表格选择事件
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      if (selection.length == 1) {
        this.bpid = selection[0].id
        this.tjid = selection[0].tjid
      } else if (selection.length > 1) {
        this.$refs.tableData.clearSelection() //清空表格数据
        this.$refs.tableData.toggleRowSelection(selection.pop()) //最后一条数据
      } else if (selection.length == 0) {
        this.bpid = ''
        this.tjid = ''
      }
    },
    // 表格单击
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.tableData.clearSelection()
      this.$refs.tableData.toggleRowSelection(row)
    },
    // 选择开单医生
    OpendoctorDataChange(value) {
      this.queryParams.openDoctorData = value.id
    },
    idOrgDataChange(value) {
      this.queryParams.idOrg = value.id
    },
    ksDataChange(value) {
      this.queryParams.reportDivision = value.id
    },
    // 查询列表
    getList() {
      this.loading = true
      var obj = {
        current: this.queryParams.current,
        size: this.queryParams.size,
        branchIds: '', //分中心id
        gwrymc: this.queryParams.gwrymc, //高危人员名称
        idOpendoctor: this.OpendoctorData.bindValue, //开单医师
        idOrg: this.queryParams.idOrg, //公司名称
        isdoc: '', //是否是医师
        numorgresv: this.queryParams.numorgresv, //订单号
        hfclr: this.queryParams.hfclr, //处理人
        reportDivision: this.queryParams.reportDivision, //提报科室
        tjid: this.queryParams.tjid, //体检号
        useCodeHiden: this.queryParams.useCodeHiden, //体检分类
        wjzjb: this.queryParams.wjzjb, //危急值级别
        idOpendoctor: this.queryParams.openDoctorData //开单医生
      }
      if (this.queryParams.date) {
        obj.startTime = this.queryParams.date[0] + ' 00:00:00'
        obj.endTime = this.queryParams.date[1] + ' 23:59:59'
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      getPage(obj)
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
    //业务处理
    handlebp(row) {
      var id = row.id
      var bpid = id || this.bpid //id有值吗
      if (!this.single && this.ids[0] == bpid) {
        this.$refs.ywItems.handlebp(this.bpid, this.tjid)
      } else {
        this.$refs.ywItems.handlebp(this.bpid, this.tjid)
      }
    },
    //双击进入 业务处理
    handleDoubleClickbp(row) {
      this.$refs.ywItems.handlebp(row.id, row.tjid)
    },
    //回访处理
    handlerv() {
      this.$refs.hfItems.handlerv(this.bpid)
    },
    //专家处理
    handleeh() {
      this.$refs.zjItems.handleeh(this.bpid)
    },
    // 添加
    handleAdd() {
      this.$refs.addItems.handleAdd()
    },
    // 删除
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除该数据项？').then(() => {
        crisisValueDelete(ids)
          .then(() => {
            this.getList()
            this.$modal.msgSuccess('删除成功')
          })
          .catch(() => {})
      })
    },
    // 编辑 
    handleUpdate() {
      this.$refs.addItems.handleUpdate(this.bpid)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/abteilung/CrisisValue/export', this.queryParams, `危急值提报.xlsx`)
    },
  },
}
</script>
<style scoped>
#setTable /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>
