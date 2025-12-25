<template>
  <el-dialog title="查看档案" :visible.sync="open" width="1580px" append-to-body style="overflow: hidden" class="check-record" :close-on-click-modal="false">
    <!-- 表格 -->
    <div class="flex">
      <div class="left-table">
        <el-table border ref="tableData" v-loading="loading" :data="leftList" style="width: 100%" stripe height="540px" @selection-change="leftChange" @row-click="rowClick">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="序列" width="55" type="index" align="center" />
          <el-table-column label="体检号" prop="patientcode" min-width="140" align="center" show-overflow-tooltip />
          <el-table-column label="姓名" prop="patientname" min-width="100" align="center" show-overflow-tooltip />
          <el-table-column label="体检日期" prop="medicaldate" min-width="120" align="center" show-overflow-tooltip />
          <el-table-column label="说明" prop="note" min-width="140" align="center" show-overflow-tooltip />
          <el-table-column label="档案号" prop="patientarchiveno" min-width="140" align="center" show-overflow-tooltip />
        </el-table>
        <!-- 分页 -->
        <pagination v-show="leftTotal > 0" :total="leftTotal" :page.sync="leftpage.current" :limit.sync="leftpage.size" @pagination="getList" style="background: none" />
      </div>
      <div class="right-table">
        <el-table border v-loading="loadingR" :data="rightList" stripe height="540px">
          <el-table-column label="科室" prop="deptName" align="center" width="120px" show-overflow-tooltip />
          <el-table-column label="医师" prop="userName" align="center" width="120px" show-overflow-tooltip />
          <el-table-column label="小结" prop="conclusions" align="center" min-width="160px" show-overflow-tooltip />
        </el-table>
        <!-- 分页 -->
        <pagination v-show="rightTotal > 0" :total="rightTotal" :page.sync="rightpage.current" :limit.sync="rightpage.size" @pagination="getList" style="background: none" />
      </div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button class="section-btn-style1" @click="open = false">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getArchiveData, getResultData } from '@/api/funcdept/section_list/dialog.js'
export default {
  data() {
    return {
      // 打开主弹窗
      open: false,
      // 表格加载中
      loading: false,
      // 左侧分页器
      leftpage: {
        current: 1,
        size: 10,
        patientcode: undefined,
      },
      // 左侧数据
      leftList: [],
      // 左侧选中
      selectData: [],
      leftTotal: 0,

      // 右侧表格加载中
      loadingR: false,
      // 右侧数据
      rightList: [],
      // 右侧分页器
      rightpage: {
        current: 1,
        size: 10,
        patientcode: undefined,
      },
      rightTotal: 0,
    }
  },
  methods: {
    showDialog(patientcode) {
      this.open = true
      this.leftpage.patientcode = patientcode
      this.getList()
    },
    // 获取表格数据
    getList() {
      this.loading = true
      getArchiveData(this.leftpage).then(({ data }) => {
        this.leftList = data.records
        this.leftTotal = data.total
        this.loading = false
      })
    },
    // 单击某行
    rowClick(row) {
      this.$refs.tableData.toggleRowSelection(row, true)
    },
    // 左侧表格选中
    leftChange(selection) {
      this.selectData = selection.map((item) => item)
      if (selection.length == 1) {
        this.rightpage.patientcode = selection[0].patientcode
        this.loadingR = true
        getResultData(this.rightpage).then(({ data }) => {
          this.rightList = data.records
          this.rightTotal = data.total
          this.loadingR = false
        })
      } else if (selection.length > 1) {
        this.$refs.tableData.clearSelection() //清空表格数据
        this.$refs.tableData.toggleRowSelection(selection.pop()) //最后一条数据
      } else if (selection.length == 0) {
        // this.bpid = "";
      }
    },
    // 保存
    handleSave() {
      this.$modal.msgSuccess('保存成功')
      this.open = false
    },
  },
}
</script>

<style lang="scss">
.check-record {
  .flex {
    display: flex;
  }

  .left-table {
    width: 50%;
    min-width: 600px;
  }

  .right-table {
    width: 50%;
    min-width: 600px;
    flex: 1;
  }
}
</style>
<style scoped>
.check-record /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>
