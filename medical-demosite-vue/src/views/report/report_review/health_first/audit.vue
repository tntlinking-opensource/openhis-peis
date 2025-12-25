<!-- 健康初审-批量通过 开发人：麦沃德科技暴雨/半夏 -->
<template>
  <el-dialog :title="title" :visible.sync="open" width="1200px" class="health-audit" append-to-body>
    <div class="flex-direction-column">
      <el-form ref="queryForm" :model="queryParams" :inline="true" @submit.native.prevent hide-required-asterisk>
        <el-form-item label="体检号:" prop="patientcode">
          <el-input ref="patientcode" v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 200px" @keyup.enter.native="handleQuery" size="mini" />
        </el-form-item>
        <el-form-item label="是否补全:" prop="autoFill">
          <el-checkbox v-model="queryParams.autoFill"></el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-row>
            <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleQuery">查询 </el-button>
            <el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="handleRemove">删除 </el-button>
          </el-row>
        </el-form-item>
      </el-form>
      <div class="table-box" style="min-height: 400px">
        <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
          <el-table-column fixed type="selection" width="60" align="center" />
          <el-table-column fixed label="序列" type="index" width="65" align="center" />
          <el-table-column fixed label="体检号" prop="patientcode" min-width="140" align="center" show-overflow-tooltip />
          <el-table-column fixed label="姓名" prop="patientname" min-width="80" align="center" show-overflow-tooltip />
          <el-table-column label="性别" prop="sex" min-width="80" align="center">
            <template slot-scope="scope">
              {{ ['男', '女'][scope.row.sex] }}
            </template>
          </el-table-column>
          <el-table-column label="年龄" prop="age" min-width="80" align="center" show-overflow-tooltip />
          <el-table-column label="公司" prop="orgName" min-width="160" align="center" show-overflow-tooltip />
          <el-table-column label="登记时间" prop="dateregister" min-width="160" align="center" show-overflow-tooltip />
          <el-table-column label="电话" prop="phone" min-width="120" align="center" show-overflow-tooltip />
          <el-table-column label="开单医生" prop="doctorapply" min-width="100" align="center" show-overflow-tooltip />
          <el-table-column label="任务" min-width="120" prop="numorgresv" align="center" show-overflow-tooltip />
          <el-table-column label="总检时间" min-width="160" prop="datefinalexamed" align="center" show-overflow-tooltip />
          <el-table-column label="总检大夫" prop="doctorfinalNameR" min-width="120" align="center" show-overflow-tooltip />
          <el-table-column label="打印时间" min-width="120" prop="datereportprinted" align="center" show-overflow-tooltip />
        </el-table>
      </div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">保 存</el-button>
      <el-button type="primary" plain @click="reset">重 置</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { getListData, healthFirstPass } from '@/api/report/report_review/health_first'
export default {
  data() {
    return {
      // 查询参数
      queryParams: {
        patientcode: undefined,
        autoFill: true,
      },
      // 选中数组
      ids: [],
      // 非多个禁用
      multiple: true,
      // 表格展示数据
      tableList: [],
      // 遮罩层
      loading: false,
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
    }
  },
  methods: {
    // 打开弹窗
    handleAudit(tableData) {
      this.title = '批量通过'
      this.reset()
      this.open = true
      this.tableList = tableData
      this.$nextTick(() => {
        this.$refs.patientcode.focus()
      })
    },
    //查询
    handleQuery() {
      this.getList()
    },
    //删除
    handleRemove() {
      this.ids.forEach((el) => {
        this.$delete(this.tableList, this.tableList.map((item) => item.id).indexOf(el))
      })
    },
    // 查询列表
    getList() {
      if (!this.queryParams.patientcode) {
        this.$alert('请输入体检号后重新尝试！', '提醒', {
          confirmButtonText: '确定',
          type: 'warning',
        })
        return
      }
      this.loading = true
      getListData(this.queryParams).then((response) => {
        if (!response.data.records.length) {
          this.$alert(`系统中没有体检号: ${this.queryParams.patientcode}登记数据！`, '提醒', {
            type: 'warning',
            callback: () => {
              this.queryParams.patientcode = undefined
            },
          })
        } else {
          if (this.tableList.map((item) => item.id).indexOf(response.data.records[0].id) == -1) {
            if (response.data.records[0].status == 3 || response.data.records[0].status > 4) {
              this.$alert('体检号:' + response.data.records[0].patientcode + '已通过审核，操作失败！', '提醒', {
                confirmButtonText: '确定',
                type: 'warning',
                callback: () => {
                  this.queryParams.patientcode = undefined
                },
              })
            } else {
              this.tableList.push(response.data.records[0])
              this.queryParams.patientcode = undefined
            }
          } else {
            this.$alert(`体检号${response.data.records[0].patientcode}已存在，请勿重复添加！`, '提醒', {
              confirmButtonText: '确定',
              type: 'warning',
              callback: () => {
                this.queryParams.patientcode = undefined
              },
            })
          }
        }
        this.$nextTick(() => {
          this.$refs.patientcode.focus()
        })
        this.loading = false
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 表单重置
    reset() {
      this.tableList = []
      this.resetForm('queryForm')
    },
    // 取消按钮
    cancel() {
      this.open = false
    },
    // 提交按钮
    submitForm() {
      if (!this.tableList.length) {
        this.$alert('详细信息为空，保存失败！', '提醒', {
          confirmButtonText: '确定',
          type: 'warning',
        })
        return
      }
      healthFirstPass(this.tableList.map((item) => item.id)).then((response) => {
        this.$modal.msgSuccess('审核成功')
        this.open = false
        this.$emit('getList')
        this.reset()
      })
    },
  },
}
</script>
<style lang="scss">
.health-audit {
  .el-dialog {
    height: 80%;
  }

  .el-dialog__body {
    display: flex;
    flex-direction: column;
  }
}
</style>
