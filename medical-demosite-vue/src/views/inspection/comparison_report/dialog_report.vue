<template>
  <el-dialog :title="title" id="reportDialog" :visible.sync="open" width="1550px" append-to-body :close-on-click-modal="false">
    <div class="flex-direction-column dialog-report">
      <div style="display: flex; flex-direction: row; height: 233px">
        <div class="table-box" style="width: 720px">
          <el-table :data="tableDataLeft" v-loading="loading" border stripe size="small" ref="tableDataLeft" @selection-change="handleSelectionLeft" @row-click="rowClickLeft" height="100%">
            <el-table-column type="selection" align="center"></el-table-column>
            <el-table-column label="体检号" prop="patientcode" align="center"></el-table-column>
            <el-table-column label="联系电话" prop="phone" align="center"></el-table-column>
            <el-table-column label="登记日期" prop="dateregister" align="center"></el-table-column>>
            <el-table-column label="体检套餐" prop="examsuiteName" align="center"></el-table-column>
          </el-table>
        </div>
        <div style="width: 82px; flex-shrink: 0; display: flex; justify-content: center">
          <div class="center-btn">
            <el-button type="primary" plain size="mini" class="el-icon-arrow-right" @click="selectOption"></el-button>
            <el-button type="primary" plain size="mini" class="el-icon-d-arrow-right" @click="selectOptionAll"></el-button>
            <el-button type="primary" plain size="mini" class="el-icon-d-arrow-left" @click="removeOptionAll"></el-button>
            <el-button type="primary" plain size="mini" class="el-icon-arrow-left" @click="removeOption"></el-button>
          </div>
        </div>
        <div class="table-box" style="width: 720px">
          <el-table :data="tableDataRight" border stripe size="small" ref="tableDataRight" @selection-change="handleSelectionRight" @row-click="rowClickRight" height="100%">
            <el-table-column type="selection" align="center"></el-table-column>
            <el-table-column label="体检号" prop="patientcode" align="center"></el-table-column>
            <el-table-column label="联系电话" prop="phone" align="center"></el-table-column>
            <el-table-column label="登记日期" prop="dateregister" align="center"></el-table-column>>
            <el-table-column label="体检套餐" prop="examsuiteName" align="center"></el-table-column>
          </el-table>
        </div>
      </div>
      <div class="table-box" style="margin-top: 25px">
        <el-table ref="mergeTable" id="setTable" :data="tableDataMerge" border stripe size="small" v-loading="loadingB" @selection-change="handleSelectionMerge" @row-click="rowClickMerge" height="100%">
          <el-table-column type="selection" align="center"></el-table-column>
          <el-table-column label="档案号" prop="patientarchiveno" align="center"></el-table-column>
          <el-table-column label="本届体检号" prop="patientcodeThis" align="center"></el-table-column>
          <el-table-column label="本届登记时间" prop="registerThis" align="center"></el-table-column>
          <el-table-column label="上届体检号" prop="patientcodeBefore" align="center"></el-table-column>
          <el-table-column label="上届登记时间" prop="registerBefore" align="center"></el-table-column>
          <el-table-column label="创建时间" prop="createdate" align="center"></el-table-column>
        </el-table>
      </div>
      <!-- 操作按钮 -->
      <el-row :gutter="10" class="mb8 align-right">
        <el-col :span="1.5">
          <el-button size="mini" type="primary" @click="createReportWindow" v-hasPermi="['inspection:comparisonReport:excle']">生成报告并预览</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button size="mini" type="warning" plain @click="previewWindow(0)" v-hasPermi="['inspection:comparisonReport:excle']">预览</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button size="mini" type="success" plain @click="handleDownload" v-hasPermi="['inspection:comparisonReport:excle']">下载word</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button size="mini" type="success" plain @click="handleUpload" v-hasPermi="['inspection:comparisonReport:excle']">上传word</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button size="mini" type="primary" plain @click="refreshWindow" v-hasPermi="['inspection:comparisonReport:excle']">刷新</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button size="mini" @click="cancelWindow()" v-hasPermi="['inspection:comparisonReport:excle']">取消</el-button>
        </el-col>
      </el-row>
    </div>
    <!-- 上传word弹窗 -->
    <upload-items ref="uploadItems" @refreshWindow="refreshWindow"></upload-items>
  </el-dialog>
</template>
<script>
import { getPeispatient, getCompareReport, previewContrastReport, sample, downloadWord } from '@/api/inspection/comparison_report.js'
import { createReport } from '@/api/preview/contrast_report.js'
import uploadItems from './upload'
export default {
  components: { uploadItems },
  data() {
    return {
      title: '对比报告',
      open: false,
      tableDataLeft: [],
      tableDataRight: [],
      tableDataMerge: [],
      // 左上加载中
      loading: false,
      // 下表格加载中
      loadingB: false,
      ids: [],
      chooseIds: [],
      patientarchiveno: '',
      selectionLeft: [],
      selectionRight: [],
      selectionMerge: [],
      //预览
      openPreview: false,
    }
  },
  mounted() {},
  methods: {
    dialogReportWindow(row) {
      this.tableDataRight = []
      this.tableDataMerge = []
      this.patientarchiveno = row.patientarchiveno
      this.open = true
      var obj = {
        patientarchiveno: row.patientarchiveno,
      }
      //获取档案号下的所有体检者信息
      this.loading = true
      getPeispatient(obj)
        .then((res) => {
          if (res.code == 200) {
            this.tableDataLeft = res.data
          }
          this.loading = false
        })
        .catch((error) => {
          this.loading = false
          console.error(error)
        })
      //获取对比报告列表信息(tableDataMerge表)
      this.loadingB = true
      getCompareReport(obj)
        .then((res) => {
          if (res.code == 200) {
            this.tableDataMerge = res.data
          }
          this.loadingB = false
        })
        .catch((error) => {
          console.error(error)
          this.loadingB = false
        })
    },
    //左表点击行
    handleSelectionLeft(selection) {
      this.ids = selection.map((item) => {
        return item.id
      })
      this.selectionLeft = selection
    },
    // 表格单击事件
    rowClickLeft(row, col) {
      if (col.type != 'selection') this.$refs.tableDataLeft.clearSelection()
      this.$refs.tableDataLeft.toggleRowSelection(row)
    },
    //右表点击行
    handleSelectionRight(selection) {
      this.chooseIds = selection.map((item) => {
        return item.id
      })
      this.selectionRight = selection
    },
    // 表格单击事件
    rowClickRight(row, col) {
      if (col.type != 'selection') this.$refs.tableDataRight.clearSelection()
      this.$refs.tableDataRight.toggleRowSelection(row)
    },
    //下表点击行
    handleSelectionMerge(selection) {
      if (selection.length == 1) {
        this.selectionMerge = selection[0]
      } else if (selection.length > 1) {
        this.$refs.mergeTable.clearSelection() //清空表格数据
        this.$refs.mergeTable.toggleRowSelection(selection.pop()) //最后一条数据
      } else {
        this.selectionMerge = {}
      }
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClickMerge(row, col) {
      if (col.type != 'selection') this.$refs.mergeTable.clearSelection()
      this.$refs.mergeTable.toggleRowSelection(row)
    },
    //从左到右
    selectOption() {
      for (var i in this.tableDataRight) {
        if (this.ids.indexOf(this.tableDataRight[i].id) > -1) {
          this.$modal.msgWarning(`不能添加重复的收费项目【${this.tableDataRight[i].sfxmsrm}】`)
          return
        }
      }
      for (var i in this.ids) {
        let index = this.tableDataLeft.findIndex((item) => {
          if (this.ids[i] == item.id) {
            return true
          }
        })
        this.tableDataLeft[index].showColor = 1
        this.tableDataRight.push(this.tableDataLeft[index])
        this.$delete(this.tableDataLeft, index)
      }
    },
    selectOptionAll() {
      this.tableDataRight.push(...this.tableDataLeft)
      this.tableDataLeft = []
    },
    //从右到左
    removeOption() {
      for (var i in this.chooseIds) {
        let projectIndex = this.tableDataLeft.findIndex((item) => {
          if (this.chooseIds[i] == item.id) {
            return true
          }
        })
        let index = this.tableDataRight.findIndex((item) => {
          if (this.chooseIds[i] == item.id) {
            return true
          }
        })
        if (projectIndex > -1) {
          this.$delete(this.tableDataRight, index)
          continue
        }
        this.tableDataLeft.push(this.tableDataRight[index])
        this.$delete(this.tableDataRight, index)
      }
    },
    removeOptionAll() {
      this.tableDataLeft.push(...this.tableDataRight)
      this.tableDataRight = []
    },
    // 刷新
    refreshWindow() {
      this.tableDataRight = []
      var obj = {
        patientarchiveno: this.patientarchiveno,
      }
      this.loading = true
      //体检者信息
      getPeispatient(obj)
        .then((res) => {
          if (res.code == 200) {
            this.tableDataLeft = res.data
          }
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
      //对比报告
      this.loadingB = true
      getCompareReport(obj)
        .then((res) => {
          if (res.code == 200) {
            this.tableDataMerge = res.data
          } else if (res.code == 500) {
            // this.tableDataMerge = []
          }
          this.loadingB = false
        })
        .catch((error) => {
          console.error(error)
          this.loadingB = false
        })
    },
    cancelWindow() {
      this.open = false
    },
    // 生成报告并预览
    createReportWindow() {
      if (this.selectionRight.length != 2 && this.selectionRight.length != 3) {
        this.$modal.alertWarning('请选择两条或者三条记录', '提示')
        return
      }
      var query = {
        patientarchiveno: this.patientarchiveno,
        patientcode: this.selectionRight[0].patientcode,
        patientcodeBefore: this.selectionRight[1].patientcode,
        patientcodeFirst: this.selectionRight.length == 3 ? this.selectionRight[2].patientcode : undefined,
      }
      this.loading = true
      createReport(query)
        .then((res) => {
          getCompareReport({
            patientarchiveno: this.patientarchiveno,
          })
            .then((res) => {
              this.loading = false
              if (res.code == 200) {
                this.tableDataMerge = res.data
                let url = this.$getCookie('imgPath') + this.tableDataMerge.pathPdf
                window.open(url, '_blank')
              }
            })
            .catch((error) => {
              console.error(error)
              this.loading = false
            })
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    //预览
    previewWindow() {
      if (!this.selectionMerge.id) {
        this.$alert('请选择一条已生成的报告!', '提示', {
          type: 'warning',
        })
        return
      }
      let url = this.$getCookie('imgPath') + this.selectionMerge.pathPdf
      window.open(url, '_blank')
    },
    // 下载word
    handleDownload() {
      if (!this.selectionMerge.id) {
        this.$alert('请选择一条已生成的报告!', '提示', {
          type: 'warning',
        })
        return
      }
      this.getBlob(this.$getCookie('imgPath') + this.selectionMerge.pathWord).then((blob) => {
        this.saveAs(blob, '对比报告.docx')
      })
    },
    getBlob(url) {
      return new Promise((resolve) => {
        const xhr = new XMLHttpRequest()
        xhr.open('GET', url, true)
        xhr.responseType = 'blob'
        xhr.onload = () => {
          if (xhr.status === 200) {
            resolve(xhr.response)
          }
        }
        xhr.send()
      })
    },
    saveAs(blob, filename) {
      var link = document.createElement('a')
      link.href = window.URL.createObjectURL(blob)
      link.download = filename
      link.click()
    },
    // 上传word
    handleUpload() {
      if (!this.selectionMerge.id) {
        this.$alert('请选择一条已生成的报告!', '提示', {
          type: 'warning',
        })
        return
      }
      this.$refs.uploadItems.handleShow(this.selectionMerge.id)
    },
  },
}
</script>

<style scoped>
#reportDialog /deep/ .el-dialog {
  height: 700px;
}

.align-right {
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin-top: 10px;
}

#setTable /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>

<style lang="scss">
.dialog-report {
  .center-btn {
    width: 82px;
    background: #fff;
    height: auto;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    border-left: 1px solid #ebeef5;

    .el-button {
      margin: 2px 0 !important;
      padding: 0;
      width: 40px;
      height: 40px;
      font-size: 20px;
      border-radius: 5px;
    }
  }
}
</style>
