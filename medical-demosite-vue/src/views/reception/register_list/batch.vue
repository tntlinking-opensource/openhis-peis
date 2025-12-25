<!-- 批量登记  开发人：麦沃德科技半夏 -->
<template>
  <div class="batch-container">
    <el-dialog title="批量登记" :visible.sync="open" class="batch-items" width="1348px" append-to-body :close-on-click-modal="false">
      <div class="flex-direction-column">
        <!-- 筛选 -->
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent>
          <el-form-item>
            <el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['reception:registerList:remove']">删除 </el-button>
          </el-form-item>
          <el-form-item style="margin-bottom: 0">
            <el-checkbox v-model="onlineCode"> APP02 </el-checkbox>
          </el-form-item>
          <el-form-item label="体检号" prop="patientcode">
            <el-input ref="patientcode" v-model="queryParams.patientcode" placeholder="请输入体检号搜索" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item prop="autoFill" style="margin-bottom: 0">
            <el-checkbox v-model="queryParams.autoFill"></el-checkbox>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          </el-form-item>
        </el-form>
        <!-- 表格 -->
        <div class="table-box">
          <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
            <el-table-column fixed type="selection" width="55" align="center" />
            <el-table-column fixed label="序列" width="55" type="index" align="center" />
            <el-table-column fixed label="订单号" prop="ddh" min-width="140" align="center" show-overflow-tooltip />
            <el-table-column fixed label="体检号" prop="patientcode" min-width="140" align="center" show-overflow-tooltip />
            <el-table-column label="体检者" prop="patientname" min-width="100" align="center" show-overflow-tooltip />
            <el-table-column label="性别"  prop="idSex"  align="center" min-width="80" show-overflow-tooltip >
              <template slot-scope="scope">
                <div v-if="scope.row.idSex == 0">男</div>
                <div v-if="scope.row.idSex == 1">女</div>
              </template>
            </el-table-column>
            <el-table-column label="年龄" prop="age" align="center" min-width="80" show-overflow-tooltip />
            <el-table-column label="婚姻" prop="idMarriage" align="center" min-width="80" show-overflow-tooltip />
            <el-table-column label="民族" prop="nation" align="center" min-width="100" show-overflow-tooltip />
            <el-table-column label="手机号码" prop="phone" align="center" min-width="120" show-overflow-tooltip />
            <el-table-column label="身份证号" prop="idcardno" align="center" min-width="160" show-overflow-tooltip />
            <el-table-column label="体检团体" prop="orgName" align="center" min-width="120" show-overflow-tooltip />
            <el-table-column label="部门" prop="orgDepart" align="center" min-width="80" show-overflow-tooltip />
            <el-table-column label="工号" prop="workno" align="center" min-width="80" show-overflow-tooltip />
            <el-table-column label="销售经理" prop="sellname" align="center" min-width="80" show-overflow-tooltip />
          </el-table>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">批量登记</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getPatientForCode, saveBatchRegister } from '@/api/reception/register_list'
export default {
  components: {},
  props: [],
  data() {
    return {
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 总条数
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        patientcode: undefined,
        autoFill: true,
      },
      // 排检表格数据
      tableList: [],
      // 是否显示弹出层
      open: false,
      // 拼接添加体检号（临时功能）
      onlineCode: false,
    }
  },
  methods: {
    // 显示
    handleShow() {
      this.open = true
      this.tableList = []
      this.onlineCode = false
      this.queryParams.patientcode = ''
      this.$nextTick(() => {
        this.$refs.patientcode.focus()
      })
    },
    // 查询列表
    getList() {
      this.loading = true
      let queryParams = JSON.parse(JSON.stringify(this.queryParams))
      if (this.onlineCode) {
        queryParams.patientcode = `APP02${queryParams.patientcode}`
      }
      getPatientForCode(queryParams)
        .then((response) => {
          if (response.data) {
            if (!this.tableList.map((item) => item.id).includes(response.data.id)) {
              this.tableList.push(response.data)
            } else {
              this.$alert(`体检号${response.data.patientcode}已添加`, '提示')
            }

            this.tableList = JSON.parse(JSON.stringify(this.tableList))
            // this.tableList = [...this.tableList, ...response.data]
          } else {
            this.$modal.alertWarning('体检号不存在', '提示')
          }
          this.$nextTick(() => {
            this.$refs.patientcode.focus()
          })
          this.queryParams.patientcode = undefined
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 搜索
    handleQuery() {
      if (!this.queryParams.patientcode) {
        this.$modal.msgWarning('请输入体检号')
        return
      }
      this.getList()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 删除
    handleDelete() {
      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(() => {
          for (let i in this.ids) {
            let index = this.tableList.findIndex((item) => {
              if (this.ids[i] == item.id) {
                return true
              }
            })
            this.$delete(this.tableList, index)
          }
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {})
    },
    // 登记
    submitForm() {
      if (!this.tableList.length) {
        this.$alert('请先添加体检号', '提示')
        return
      }
      this.loading = true
      saveBatchRegister({ ids: this.tableList.map((item) => item.id).join(',') })
        .then(() => {
          this.$modal.msgSuccess('批量登记成功')
          this.tableList = []
          this.$emit('getList')
          this.loading = false
        })
        .catch((err) => {
          console.error(err)
          this.loading = false
        })
    },
    // 取消
    cancel() {
      this.open = false
    },
  },
}
</script>
<style lang="scss">
.batch-items {
  .el-dialog {
    height: 100%;
  }
}
</style>
