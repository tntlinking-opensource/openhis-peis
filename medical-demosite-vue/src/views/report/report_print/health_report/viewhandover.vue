<!-- 健康报告 开发人：麦沃德科技暴雨/张孟 -->
<template>
  <el-dialog title="终审交接" :visible.sync="open" width="800" append-to-body>
    <el-form ref="queryForm" :model="queryParams" size="small" :inline="true" @submit.native.prevent>
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 200px" @keyup.enter.native="getList" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="getList" v-hasPermi="['report:reportPrint:healthReport:add']">新增 </el-button>
        <el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="!multiple" @click="handleRemove" v-hasPermi="['report:reportPrint:healthReport:remove']">删除 </el-button>
      </el-form-item>
    </el-form>
    <div style="height: 400px">
      <el-table ref="tableList" border height="100%" :data="tableList" v-loading="loading" stripe @selection-change="handSelectionChange" @row-click="rowClick">
        <el-table-column fixed type="selection" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="体检号" prop="patientcode" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" align="center" show-overflow-tooltip />
        <el-table-column label="开单医师" prop="doctorapply" align="center" show-overflow-tooltip />
        <el-table-column label="公司名称" prop="orgName" align="center" show-overflow-tooltip />
        <el-table-column label="订单号" prop="numorgresv" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">终审交接</el-button>
      <el-button type="primary" plain @click="reset">重 置</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { searchZsjjCode, saveZsjj } from '@/api/report/report_print/health_report'
export default {
  data() {
    return {
      // 查询参数
      queryParams: {
        patientcode: undefined,
      },
      // 遮罩层
      loading: false,
      // 是否显示弹出层
      open: false,
      // 表格数据
      tableList: [],
      // 选中数组
      ids: [],
      // 是否选中多个
      multiple: false,
    }
  },
  methods: {
    // 终审交接
    handleShow(patientcode) {
      this.reset()
      if (patientcode) {
        this.queryParams.patientcode = patientcode
        this.getList()
      } else {
        this.open = true
      }
    },
    //删除
    handleRemove() {
      this.$confirm('是否确认删除', '提示')
        .then(() => {
          for (let i = this.tableList.length - 1; i >= 0; i--) {
            this.ids.forEach((el) => {
              if (el == this.tableList[i].id) {
                this.$delete(this.tableList, i)
              }
            })
          }
        })
        .catch()
    },
    // 表单重置
    reset() {
      this.tableList = []
      this.resetForm('queryParams')
    },
    //选框内容改变
    handSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      if (selection.length == 0) {
        this.multiple = false
      } else {
        this.multiple = true
      }
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 获取数据
    getList() {
      if (!this.queryParams.patientcode) {
        this.$modal.alertWarning('请输入体检号', '提醒')
      }
      const patientcode = this.queryParams.patientcode
      this.loading = true
      searchZsjjCode(patientcode)
        .then((response) => {
          this.open = true
          this.queryParams.patientcode = ''
          this.tableList = [...this.tableList, ...response.data]
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
          this.queryParams.patientcode = ''
        })
    },
    // 提交按钮
    submitForm() {
      if (this.ids.length > 0) {
        saveZsjj(this.ids.join(','))
          .then(() => {
            this.$message.success('操作成功')
            this.open = false
            this.$emit('getList')
          })
          .catch((error) => {
            console.error(error)
            this.open = false
          })
      } else {
        this.$message.error('请选择需要终审的体检者')
      }
    },
  },
}
</script>
