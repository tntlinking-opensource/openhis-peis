<template>
  <el-dialog class="registration-order-list" title="未登记体检号列表" :visible.sync="open" width="1048px" append-to-body :close-on-click-modal="false" @close="cancel">
    <div class="title">请选择已备单未登记的体检号</div>
    <el-table ref="dataList" border :data="dataList" height="500px" stripe @selection-change="handleSelectionChange" @row-click="rowClick" @row-dblclick="submitForm">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序列" width="55" type="index" align="center" />
      <el-table-column label="订单号" prop="orderNum" align="center" min-width="100"></el-table-column>
      <el-table-column label="体检号" prop="patientcode" align="center" min-width="120"></el-table-column>
      <el-table-column label="姓名" prop="patientname" align="center" min-width="80"></el-table-column>
      <el-table-column label="身份证号" prop="idcardno" align="center" min-width="170"></el-table-column>
      <el-table-column label="性别" prop="idSex" align="center">
        <template slot-scope="scope">
          {{ ['男', '女', '通用'][scope.row.idSex] }}
        </template>
      </el-table-column>
      <el-table-column label="体检类型" prop="idExamtype" align="center">
        <template slot-scope="scope">
          {{ ['健康', '职业', '综合', '复查'][scope.row.idExamtype] }}
        </template>
      </el-table-column>
      <el-table-column label="团体名称" prop="orgName" align="center" min-width="100"></el-table-column>
      <el-table-column label="部门名称" prop="orgDepart" align="center" min-width="100"></el-table-column>
      <el-table-column label="套餐名称" prop="examsuiteName" align="center" min-width="100"></el-table-column>
    </el-table>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      // 打开弹窗
      open: false,
      //  数据列表
      dataList: [],
      // 选中的数据项
      ids: undefined,
    }
  },
  methods: {
    // 打开弹窗
    handleShow(dataList) {
      this.open = true
      this.dataList = dataList
    },
    // 表格选中
    handleSelectionChange(selection) {
      if (selection.length == 0) {
        this.ids = undefined
      } else if (selection.length == 1) {
        this.ids = selection[0]
      } else if (selection.length > 1) {
        this.$refs.dataList.clearSelection() //清空表格数据
        this.$refs.dataList.toggleRowSelection(selection.pop()) //最后一条数据
      }
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.dataList.clearSelection()
      this.$refs.dataList.toggleRowSelection(row)
    },
    // 取消按钮
    cancel() {
      this.$emit('cancelIdCard',0)
      this.open = false
    },
    // 提交按钮
    submitForm() {
      if (!this.ids) {
        this.$modal.alertWarning('请先选择一条体检号')
      } else {
        this.open = false
        this.$emit('selectIdCard', this.ids.patientcode)
      }
    },
  },
}
</script>
<style lang="scss">
.registration-order-list {
  .title {
    margin-bottom: 10px;
    font-size: 16px;
  }
}
</style>
<style scoped>
.registration-order-list /deep/ .el-dialog .el-dialog__body {
  padding-top: 0;
}
.registration-order-list /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>
