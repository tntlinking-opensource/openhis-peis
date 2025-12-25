<!-- 职业总检-查看详情-复查通知 麦沃德科技 开发人:清风 -->
<template>
  <el-dialog :title="title" :visible.sync="open" @close="closeHandle" width="1642px" append-to-body :close-on-click-modal="false" class="occupational-inspection-review">
    <div style="display: inline-block; vertical-align: top">
      <div style="display: flex; flex-direction: row; height: 630px">
        <div class="table-box">
          <el-table :data="tableDataLeft" border stripe size="small" ref="tableData" style="width: 280px" @selection-change="handleSelectionLeft" @row-click="rowClickL" height="100%">
            <el-table-column type="selection" align="center"></el-table-column>
            <el-table-column prop="examfeeitemName" label="套餐收费项目"></el-table-column>
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
        <div class="table-box">
          <el-table :data="tableDataRight" border stripe size="small" ref="tableDataR" style="width: 280px" @selection-change="handleSelectionRight" @row-click="rowClickR" height="100%">
            <el-table-column type="selection" align="center"></el-table-column>
            <el-table-column prop="examfeeitemName" label="复查收费项目"></el-table-column>
          </el-table>
        </div>
      </div>
    </div>
    <div style="display: inline-block; width: 900px; height: 630px; margin: 0 8px; padding: 20px; border: 2px solid #aaa; position: relative">
      <div style="position: absolute; top: -13px; left: 20px; font-size: 20px; padding: 0 2px; background: #ffffff">【复查信息】</div>
      <el-form ref="form" :model="form" :inline="true" label-width="100px">
        <el-form-item label="姓名">
          <el-input readonly style="width: 310px" v-model="form.patientname"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-input readonly style="width: 310px" :value="['男', '女', '通用'][form.idSex]"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input readonly style="width: 310px" v-model="form.age"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input readonly style="width: 310px" v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="体检号">
          <el-input readonly style="width: 310px" v-model="form.patientcode"></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-input readonly style="width: 310px" :value="['健康总检', '职业总检', '综合', '复查'][form.idSex]"></el-input>
        </el-form-item>
        <el-form-item label="体检日期">
          <el-date-picker v-model="form.dateregister" readonly style="width: 730px" placeholder="选择日期" value-format="yyyy-MM-dd HH:mm:ss" type="date"></el-date-picker>
        </el-form-item>
        <el-form-item label="复查开始日期">
          <el-date-picker v-model="form.dateregisternotime" readonly style="width: 310px" placeholder="选择日期" value-format="yyyy-MM-dd HH:mm:ss" type="date"></el-date-picker>
        </el-form-item>
        <el-form-item label="复查结束日期">
          <el-date-picker v-model="form.endTime" readonly style="width: 310px" placeholder="选择日期" value-format="yyyy-MM-dd HH:mm:ss" type="date"></el-date-picker>
        </el-form-item>
        <el-form-item label="复查注意事项" id="setTextarea">
          <el-input type="textarea" style="width: 730px" v-model="form.noticeOfProceedingText"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="save">保存</el-button>
      <el-button type="success" @click="tryInterface">刷新</el-button>
      <el-button @click="cancle">关闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getItemByPeople, getRightItemByPeople, saveReview, review } from '@/api/inspection/occupational_inspection.js'

export default {
  data() {
    return {
      title: '复查通知',
      open: false,
      patientno: '',
      tableDataLeft: [],
      tableDataRight: [],
      ids: [], //左表id
      selectionLeft: [], //左表选中项
      chooseIds: [], //右表id
      selectionRight: [], //右表选中项
      form: {
        patientname: '',
        sex: '',
        age: '',
        phone: '',
        patientcode: '',
        idExamsuite: '',
        dateregister: '',
        dateFrom: '',
        dateTo: '',
        noticeOfProceedingText: '',
      },
      idExamsuite: [
        { id: '0', text: '健康总检' },
        { id: '1', text: '职业总检' },
        { id: '2', text: '综合' },
        { id: '3', text: '复查' },
      ],
      sex: [
        { id: 0, text: '男' },
        { id: 1, text: '女' },
        { id: 2, text: '通用' },
      ],
      inputCode: '',
      inputId: '',
    }
  },
  computed: {
    watchNoticeOfProceedingText() {
      let reviewMatters = ''
      this.tableDataRight.forEach((el) => {
        if (el.reviewMatters) {
          reviewMatters += el.reviewMatters
        }
      })
      return reviewMatters
    },
  },
  watch: {
    watchNoticeOfProceedingText(newVal, oldVal) {
      this.$delete(this.form, 'noticeOfProceedingText')
      this.$set(this.form, 'noticeOfProceedingText', newVal)
    },
  },
  methods: {
    openReviewWindow(rowData) {
      this.patientno = rowData.patientno
      this.inputCode = rowData.inputCode
      this.inputId = rowData.inputId
      this.open = true
      this.gogetItemByPeople()
      this.gogetRightItemByPeople()
      this.getReview()
    },
    tryInterface() {
      this.form.noticeOfProceedingText = ''
      this.gogetItemByPeople()
      this.gogetRightItemByPeople()
      this.getReview()
    },
    //右栏表单数据
    getReview() {
      var obj = {
        patientno: this.patientno,
      }
      review(obj).then((res) => {
        if (res.code == 200) {
          res.data.peispatient.endTime = res.data.endTime
          this.form = { ...this.form, ...res.data.review, ...res.data.peispatient }
        }
      })
    },
    //左表数据
    gogetItemByPeople() {
      var obj = {
        patientno: this.patientno, //体检号
      }
      getItemByPeople(obj).then((res) => {
        if (res.code == 200) {
          this.tableDataLeft = res.data
        }
      })
    },
    //右表数据
    gogetRightItemByPeople() {
      var obj = {
        patientno: this.patientno, //体检号
      }
      getRightItemByPeople(obj).then((res) => {
        if (res.code == 200) {
          this.tableDataRight = res.data
        }
      })
    },
    //保存按钮
    save() {
      let text = []
      this.tableDataRight.forEach((el) => {
        text.push(el.examfeeitemName)
      })
      this.$confirm(`是否确认复查<font color="red">${text.join(',')}</font>项目?`, '提示', {
        dangerouslyUseHTMLString: true,
      })
        .then(() => {
          var obj = {
            dateFrom: this.form.dateFrom,
            dateTo: this.form.dateTo,
            noticeOfProceedingText: this.form.noticeOfProceedingText,
            patientno: this.patientno,
            reGriddatas: this.tableDataRight,
          }
          saveReview(obj).then((res) => {
            if (res.code == 200) {
              this.$message({
                message: '操作成功',
                type: 'success',
              })
              let obj = {
                inputCode: this.inputCode,
                id: this.inputId,
              }
              this.$emit('optionsWindows', obj, obj.id,false,true)
              // this.$emit('noticeFunc', this.form.noticeOfProceedingText)
              this.cancle()
            }
          })
        })
        .catch(() => {})
    },
    //关闭按钮
    cancle() {
      this.open = false
      // let obj = {
      //   inputCode: this.inputCode,
      //   id: this.inputId,
      // }
      // this.$emit('optionsWindows', obj, obj.id)
    },
    //左表点击行
    handleSelectionLeft(selection) {
      this.ids = selection.map((item) => {
        return item.id
      })
      this.selectionLeft = selection
    },
    // 表格单击事件
    rowClickL(row) {
      this.$refs.tableData.clearSelection()
      this.$refs.tableData.toggleRowSelection(row)
    },
    //右表点击行
    handleSelectionRight(selection) {
      this.chooseIds = selection.map((item) => {
        return item.id
      })
      this.selectionRight = selection
    },
    // 表格单击事件
    rowClickR(row) {
      this.$refs.tableDataR.clearSelection()
      this.$refs.tableDataR.toggleRowSelection(row)
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
    //退出dialog弹窗
    closeHandle() {
      this.form = {}
      this.tableDataLeft = []
      this.tableDataRight = []
    },
  },
}
</script>

<style lang="scss">
.occupational-inspection-review {
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

<style scoped>
#setTextarea /deep/ .el-textarea__inner {
  height: 150px;
}
</style>
