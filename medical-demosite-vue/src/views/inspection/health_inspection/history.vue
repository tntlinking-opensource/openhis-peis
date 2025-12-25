<!-- 健康总检-查看详情-历史 麦沃德科技 开发人:清风 -->
<template>
  <el-dialog title="历史" class="add-items inspection-history" :visible.sync="open" width="1600px" append-to-body :close-on-click-modal="false">
    <div style="width: 100%; height: 806px; display: flex; flex-direction: row">
      <div style="width: 520px; display: flex; flex-direction: column">
        <div style="width: 520px; flex-grow: 1">
          <el-table id="setTable" ref="tableData" :data="tableData" v-loading="loading" max-width="520px" :border="true" :stripe="true" @selection-change="handleSelectionChange" @row-click="rowClick" height="100%">
            <el-table-column type="selection" align="center"></el-table-column>
            <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
            <el-table-column prop="patientcode" label="体检号" align="center"></el-table-column>
            <el-table-column prop="totalTime" label="总检时间" align="center"></el-table-column>
          </el-table>
        </div>
        <!-- 分页 -->
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="historyWindow(id)" />
      </div>
      <div style="flex-grow: 1; height: 100%; margin-left: 16px">
        <el-tabs v-model="name" id="setTabs" :before-leave="beforeLeave" type="card" v-loading="loading">
          <el-tab-pane label="综述" name="first" style="border: 1px solid rgb(225, 225, 225); border-top: 0; height: 750px; overflow: auto">
            <el-input type="textarea" :value="selection.summarize" readonly style="padding: 16px; border: 0; height: 100%"></el-input>
          </el-tab-pane>
          <el-tab-pane label="健康建议" name="second" style="border: 1px solid rgb(225, 225, 225); border-top: 0; height: 750px; overflow: auto">
            <el-input type="textarea" :value="selection.offer" readonly style="padding: 16px; border: 0; height: 100%"></el-input>
          </el-tab-pane>
          <el-tab-pane label="查看报告" name="third" style="border: 1px solid rgb(225, 225, 225); border-top: 0px; height: 761px"></el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </el-dialog>
</template>

<script>
import { getHistoryData } from '@/api/inspection/health_inspection.js'
export default {
  data() {
    return {
      open: false,
      // 遮罩层
      loading: false,
      // 总条数
      total: 1,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 10,
      },
      tableData: [],
      name: 'first',
      selection: {},
    }
  },
  methods: {
    historyWindow(id) {
      this.open = true
      this.loading = true
      var obj = {
        current: this.queryParams.current,
        dh: 0,
        id,
        size: this.queryParams.size,
      }
      this.selection = {
        summarize:'',
        offer:'',
      }
      this.tableData = []
      getHistoryData(obj).then((res) => {
        this.tableData = res.data.records
        this.loading = false
      })
    },
    handleSelectionChange(selection) {
      this.single = selection.length != 1
      this.multiple = !selection.length
      if (selection.length == 1) {
        this.selection = selection[0]
      } else if (selection.length > 1) {
        this.$refs.tableData.clearSelection() //清空表格数据
        this.$refs.tableData.toggleRowSelection(selection.pop()) //最后一条数据
      }
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.tableData.clearSelection()
      this.$refs.tableData.toggleRowSelection(row)
    },
    beforeLeave(tab) {
      if (tab == 'third') {
        if (this.selection.patientcode) {
          if (!this.selection.totalTime) {
            this.$modal.alertWarning('请完成总检后查看', '提示')
            return false
          }
          if (this.selection.urlPdf) {
            const link = document.createElement('a')
            link.href = 'http://XXX.XXX.XXX:8095/mec/' + this.selection.urlPdf // Replace with the actual file path or URL
            // Set the file name (optional)
            // link.download = this.selection.patientcode + '历史报告.pdf' // Replace with the desired file name
            // Programmatically trigger the download
            // link.click()
            window.open('http://XXX.XXX.XXX:8095/mec/' + this.selection.urlPdf, '_blank')
          } else {
            this.viewReport()
          }
        } else {
          this.$modal.alertWarning('请选择体检号', '提示')
        }
        return false
      }
    },
    //查看报告
    viewReport() {
      var query = {
        patientcode: this.selection.patientcode,
      }
      let routeUrl = this.$router.resolve({
        name: 'IndividualHealthReport',
        query: query,
      })
      window.open(routeUrl.href)
    },
  },
}
</script>

<style scoped>
.inspection-history #setTabs /deep/ .el-tabs__header {
  margin-bottom: 0 !important;
}

.inspection-history #setTabs /deep/ .el-tabs__item {
  height: 44px;
}

.inspection-history #setTable /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}

.inspection-history #setTabs /deep/ .el-tabs__item:focus.is-active.is-focus:not(:active) {
  box-shadow: none;
}

.inspection-history #setTabs /deep/ .el-textarea__inner {
  border: 0;
  height: 100%;
}

.inspection-history #setTabs /deep/ table .cell {
  font-size: 16px !important;
  /* color: black !important; */
}
</style>
