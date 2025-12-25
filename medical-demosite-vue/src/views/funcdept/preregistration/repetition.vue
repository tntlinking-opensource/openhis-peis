<template>
  <el-dialog title="批量交单" :visible.sync="open" width="1580px" append-to-body style="overflow: hidden" :close-on-click-modal="false">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent>
      <el-form-item label="体检号" prop="patientcode">
        <el-input ref="patientcode" v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 200px" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查询</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table border v-loading="loading" :data="tableList" height="500px" stripe @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序列" width="55" type="index" align="center" />
      <el-table-column label="体检号" prop="patientcode" align="center" show-overflow-tooltip />
      <el-table-column label="姓名" prop="patientname" align="center" show-overflow-tooltip />
      <el-table-column label="性别" prop="idSex" align="center" show-overflow-tooltip>
        <template slot-scope="scope">
          <div v-if="scope.row.idSex == 0">男</div>
          <div v-if="scope.row.idSex == 1">女</div>
        </template>
      </el-table-column>
      <el-table-column label="年龄" prop="age" align="center" show-overflow-tooltip />
      <el-table-column label="体检套餐" prop="examsuiteName" align="center" show-overflow-tooltip />
      <el-table-column label="团体名称" prop="orgName" align="center" show-overflow-tooltip />
      <el-table-column label="团体分组" prop="orgreservationgroupname" align="center" show-overflow-tooltip />
    </el-table>
    <!-- 分页 -->
    <!-- <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
      @pagination="getList" /> -->
    <div slot="footer" class="dialog-footer" style="text-align: center">
      <el-button type="primary" @click="handleJds()">确认批量交单</el-button>
      <el-button type="primary" @click="open = false">取消交单</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getPatientData, submitForm } from '@/api/funcdept/preregistration'
export default {
  data() {
    return {
      // 打开主弹窗
      open: false,
      // 筛选参数
      queryParams: {
        current: 1,
        size: 10,
        patientcode: undefined,
        inputCode: undefined,
        date: undefined,
      },
      // 表格数据
      tableList: [],
      // 表格加载中
      loading: false,
      // 总数
      total: 0,
      // 表格选中的数据
      selectData: [],
      ids: [],
    }
  },
  methods: {
    //交单
    handleJds() {
      if (this.ids.length <= 1) {
        this.$message({
          message: '请勾选两条或两条以上记录进行批量交单',
          type: 'warning',
        })
      } else {
        var patientCode = this.selectData.map((item) => item.patientcode).join(',')
        this.$modal
          .confirm('确定要交单?')
          .then(function () {
            return submitForm({ patientCode })
          })
          .then(() => {
            this.$modal.msgSuccess('交单成功')
            this.open = false
          })
      }
    },
    reset() {
      this.tableList = []
      this.queryParams.patientcode = ''
    },
    showDialog() {
      this.reset()
      this.open = true
    },
    // 获取表格数据
    getList() {
      this.loading = true
    },
    // 查询
    handleQuery() {
      let error = ''
      this.tableList.forEach((el) => {
        if (el.patientcode == this.queryParams.patientcode) {
          error = '体检号' + this.queryParams.patientcode + '已存在,请重新输入'
        }
      })
      if (error) {
        this.$alert(error, '提示')
        this.queryParams.patientcode = ''
        this.$refs.patientcode.focus()
        return
      }
      const queryParams = {
        patientCode: this.queryParams.patientcode,
        autoFill: true,
      }
      getPatientData(queryParams)
        .then((response) => {
          this.tableList.push(response.data.patientData)
          this.queryParams.patientcode = ''
        })
        .catch(() => {})
      // this.$modal.msg('查询成功')
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.selectData = selection.map((item) => item)
      this.ids = selection.map((item) => item.id)
      this.typeId = selection.map((item) => item.typeId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
  },
}
</script>
