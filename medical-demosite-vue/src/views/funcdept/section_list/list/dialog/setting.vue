<template>
  <el-dialog class="dialog-setting" title="设置艾迪康代码" :visible.sync="open" width="1000px" append-to-body style="overflow: hidden" :close-on-click-modal="false">
    <el-row>
      <el-col :span="12">
        <el-table v-loading="loading" :data="tableData" stripe border height="400px">
          <el-table-column label="序列" align="center" width="55px" type="index" />
          <el-table-column label="收费项目" align="center" prop="examfeeitemName" show-overflow-tooltip min-width="150" />
          <el-table-column label="检查项目" align="center" prop="examitemName" show-overflow-tooltip min-width="150" />
          <el-table-column label="艾迪康代码" align="center" prop="adiconCode" show-overflow-tooltip min-width="110" style="padding: 0">
            <template slot-scope="scope">
              <el-input type="text" v-model="scope.row.adiconCode" style="width: 100%" />
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="12">
        <el-table v-loading="loading2" :data="tableData2" stripe border height="400px">
          <el-table-column label="序列" align="center" width="60px" type="index" />
          <el-table-column label="检查项目" align="center" prop="itemNameCn" show-overflow-tooltip min-width="160" />
          <el-table-column label="艾迪康代码" align="center" prop="itemCode" show-overflow-tooltip min-width="120" style="padding: 0"> </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button class="section-btn-style1" @click="handleSave">保 存</el-button>
      <el-button class="section-btn-style3" plain @click="reset">重 置</el-button>
      <el-button class="section-btn-style2" @click="cancle">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getAdiconGridData, setAdicon, getAdiconList } from '@/api/funcdept/section_list/dialog.js'
export default {
  props: [],
  data() {
    return {
      patientcode: undefined,
      // 弹窗
      open: false,
      loading: false,
      loading2: false,
      tableData: [],
      tableData2: [],
    }
  },
  methods: {
    showDialog(patientcode) {
      this.open = true
      this.patientcode = patientcode
      this.reset()
    },
    // 获取艾迪康代码数据
    getAdiconGrid() {
      this.loading = true
      getAdiconGridData({
        patientCode: this.patientcode,
      })
        .then(({ data }) => {
          if (data) {
            data.forEach((el) => {
              el.adiconCode = el.examitemCode3
            })
            this.tableData = data
          }
          this.loading = false
        })
        .catch((err) => {
          console.error(err)
          this.loading = false
        })
    },
    // 获取艾迪康结果
    handleGetAdiconList() {
      this.loading2 = true
      this.tableData2 = []
      getAdiconList({
        patientcode: this.patientcode,
      })
        .then(({ data }) => {
          if (data) {
            this.tableData2 = data
          }
          this.loading2 = false
        })
        .catch((err) => {
          console.error(err)
          this.loading2 = false
        })
    },
    // 保存
    handleSave() {
      this.tableData.forEach((el) => {
        if (el.adiconCode) {
          el.examitemCode3 = el.adiconCode
        }
      })
      setAdicon(this.tableData).then(({ data }) => {
        this.cancle()
        this.$modal.msgSuccess('保存成功')
      })
    },
    // 重置
    reset() {
      this.getAdiconGrid()
      this.handleGetAdiconList()
    },
    // 取消
    cancle() {
      this.open = false
    },
  },
}
</script>

<style lang="scss">
.dialog-setting {
  input {
    text-align: center;
    border: none;
    background-color: rgba(0, 0, 0, 0);
  }
}
</style>
<style scoped>
.dialog-setting /deep/ .el-table--medium .el-table__cell {
  padding: 0;
}
</style>
