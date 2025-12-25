<!-- 查看科室 开发人： 麦沃德科技 予安 -->
<template>
  <el-dialog title="查看科室" :visible.sync="open" width="1580px" append-to-body style="overflow: hidden" class="section-dialog" :close-on-click-modal="false" destroy-on-close>
    <el-row>
      <el-col :span="12">
        <!-- 表格 -->
        <el-table border v-loading="loading" v-if="tableList && tableList.length" ref="tableData" :data="tableList" height="670px" stripe @selection-change="handleSelectionChange" @row-click="rowClick" :span-method="objectSpanMethod">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序列" width="55" type="index" align="center" />
          <el-table-column label="科室名称" prop="co1" align="center" show-overflow-tooltip min-width="130" />
          <el-table-column label="收费项目" prop="co2" align="center" show-overflow-tooltip min-width="130" />
          <el-table-column label="检查项目" prop="co3" align="center" show-overflow-tooltip min-width="130" />
          <el-table-column label="体征词" prop="co4" align="center" show-overflow-tooltip min-width="100" />
          <el-table-column label="结论词" prop="co5" align="center" show-overflow-tooltip min-width="100" />
          <el-table-column label="是否阳性" prop="co6" align="center" show-overflow-tooltip min-width="80">
            <template slot-scope="scope">
              <el-tag type="danger" v-if="scope.row.co6 == 1">是</el-tag>
              <el-tag v-else>否</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="重症级" prop="co7" align="center" show-overflow-tooltip min-width="80">
            <template slot-scope="scope">
              <span v-text="scope.row.co7 == 0 ? '无' : 1 <= scope.row.co7 <= 3 ? '低' : 4 <= scope.row.co7 <= 6 ? '低' : '低'"></span>
            </template>
          </el-table-column>
          <el-table-column label="是否弃检" prop="co8" align="center" show-overflow-tooltip min-width="80">
            <template slot-scope="scope">
              <el-tag type="danger" v-if="scope.row.co8 == 0">是</el-tag>
              <el-tag v-else>否</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="更新时间" prop="co9" align="center" show-overflow-tooltip min-width="120">
            <template slot-scope="scope">
              <span>{{ scope.row.co9 ? scope.row.co9.split(' ')[0] : '' }}</span>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="12">
        <span style="margin: 30px; font-size: 20px; font-weight: 600; color: #000">{{ patientCode }}</span>
        <span style="font-size: 20px; font-weight: 600; color: #000">{{ patientname }}</span>
        <div class="box content" style="margin-top: 8px">
          <h3>【小结】</h3>
          <!-- 提醒内容 -->
          <el-input v-model="queryParams.ver7" placeholder="无" type="textarea" :rows="15" style="margin-bottom: 13px" readonly> </el-input>
          <el-form :model="queryParams" ref="patientForm" size="small" :inline="true" label-width="70px" label-position="left" style="pointer-events: none">
            <el-form-item label="录入人" prop="ver2">
              <el-input v-model="queryParams.ver2" clearable style="width: 200px"> </el-input>
            </el-form-item>
            <el-form-item label="录入时间" prop="ver5">
              <el-date-picker v-model="queryParams.ver5" type="date" placeholder="选择日期时间" style="width: 200px"> </el-date-picker>
            </el-form-item>
            <el-form-item label="审核人" prop="ver4">
              <el-input v-model="queryParams.ver4" clearable style="width: 200px"> </el-input>
            </el-form-item>
            <el-form-item label="审核时间" prop="ver6">
              <el-date-picker v-model="queryParams.ver6" type="date" placeholder="选择日期时间" style="width: 200px"> </el-date-picker>
            </el-form-item>
          </el-form>
        </div>
      </el-col>
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button class="section-btn-style1" @click="open = false">关 闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getCommonData, getInspectChargeListData } from '@/api/funcdept/section_list/dialog.js'
export default {
  data() {
    return {
      // 打开主弹窗
      open: false,
      // 筛选参数
      queryParams: {
        content: '',
        lrr: undefined,
        lrsj: undefined,
        shr: undefined,
        shsj: undefined,
      },
      // 表格数据
      tableList: [],
      // 计算合并的表格
      cellList: [],
      cellList2: [],
      // 表格加载中
      loading: false,
      // 体检号
      patientCode: undefined,
      // 体检姓名
      patientname: undefined,
    }
  },
  methods: {
    showDialog(patientcode, patientname) {
      this.open = true
      this.patientCode = patientcode
      this.patientname = patientname
      this.queryParams = {
        content: '',
        lrr: undefined,
        lrsj: undefined,
        shr: undefined,
        shsj: undefined,
      }
      this.getList()
    },
    // 获取表格数据
    getList() {
      this.loading = true
      let query = {
        patientno: this.patientCode,
      }
      this.tableList = []
      getCommonData(query).then(({ data }) => {
        this.loading = false
        this.computeCell(data)
      })
    },
    // 左下表格多选框选中数据
    handleSelectionChange(selection) {
      this.selectData = selection.map((item) => item)
      if (selection.length == 1) {
        let query = {
          patientcode: this.patientCode,
          sectionId: this.selectData[0].co10,
        }
        getInspectChargeListData(query).then(({ data }) => {
          this.queryParams = data[0]
        })
      } else if (selection.length > 1) {
        this.$refs.tableData.clearSelection() //清空表格数据
        this.$refs.tableData.toggleRowSelection(selection.pop()) //最后一条数据
      }
    },
    // 单击某行
    rowClick(row) {
      this.$refs.tableData.toggleRowSelection(row, true)
    },
    // 计算要合并的单元格
    computeCell(tableBody) {
      this.cellList = []
      this.cellList2 = []
      let same = ''
      let same2 = ''
      tableBody.forEach((el, index) => {
        if (el.co1 != same) {
          this.cellList.push(index)
          same = el.co1
        }
        if (el.co2 != same2) {
          this.cellList2.push(index)
          same2 = el.co2
        }
      })
      this.tableList = tableBody
      this.$nextTick(() => {
        this.$refs.tableData.toggleRowSelection(this.tableList[0]) //最后一条数据
      })
    },
    // 合并相同收费项目
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      if (columnIndex == 2) {
        if (this.cellList.includes(rowIndex)) {
          let index = this.cellList.indexOf(rowIndex)
          let rowspan = undefined
          if (index == this.cellList.length - 1) {
            rowspan = this.tableList.length - this.cellList[index]
          } else {
            rowspan = this.cellList[index + 1] - this.cellList[index]
          }
          return {
            rowspan,
            colspan: 1,
          }
        } else {
          return {
            rowspan: 0,
            colspan: 0,
          }
        }
      }
      if (columnIndex == 3) {
        if (this.cellList2.includes(rowIndex)) {
          let index = this.cellList2.indexOf(rowIndex)
          let rowspan = undefined
          if (index == this.cellList2.length - 1) {
            rowspan = this.tableList.length - this.cellList2[index]
          } else {
            rowspan = this.cellList2[index + 1] - this.cellList2[index]
          }
          return {
            rowspan,
            colspan: 1,
          }
        } else {
          return {
            rowspan: 0,
            colspan: 0,
          }
        }
      }
    },
  },
}
</script>

<style lang="scss">
.section-dialog {
  .el-textarea__inner {
    background: #f6f7fb;
  }

  .box {
    padding: 0 15px;
    margin-left: 15px;
    border: 1px solid #dfe6ec;

    h3 {
      font-weight: 600;
      font-size: 16px;
      line-height: 22px;
      color: #333333;
    }
  }
}
</style>
<style scoped>
.section-dialog /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
.section-dialog /deep/ .el-button span {
  font-size: 16px;
}
.section-dialog /deep/ .el-input__inner {
  font-size: 16px;
}

.section-dialog /deep/ .el-textarea__inner {
  color: #000;
  font-size: 20px;
  font-weight: 600;
}
.section-dialog /deep/ table .cell {
  font-size: 18px !important;
}

.section-dialog /deep/ .el-form-item__content {
  font-size: 15px !important;
  color: #000 !important;
}

/* 修改火狐浏览器字体 */
@-moz-document url-prefix() {
  body,
  .el-input__inner,
  .el-button,
  .el-form .el-form-item__label,
  .checkbox {
    font-family: '宋体', Arial, sans-serif;
  }

  .el-form .el-form-item__label {
    font-weight: 400;
  }
}
</style>
