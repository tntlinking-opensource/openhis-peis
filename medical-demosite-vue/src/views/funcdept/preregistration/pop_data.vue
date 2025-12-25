<!-- 护理登记 麦沃德 予安 -->
<template>
  <el-dialog title="未检项目" :visible.sync="open" width="880px" append-to-body :close-on-click-modal="false" @close="close">
    <!-- 表格 -->
    <el-table ref="tableList" border v-loading="loading" :data="tableList" height="500px" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序列" width="55" type="index" align="center" />
      <el-table-column label="收费项目" prop="examfeeitemName" align="center" show-overflow-tooltip />
      <el-table-column label="已检" prop="yj" align="center" show-overflow-tooltip>
        <template slot-scope="scope">
          <el-tag v-if="scope.row.yj == 1"><i class="el-icon-check"></i> </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="弃检" prop="examfeeitemName" align="center" show-overflow-tooltip>
        <template slot-scope="scope">
          <el-tag v-if="scope.row.qj == 1"><i class="el-icon-check"></i> </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="text" style="color: #ffba00" @click="handleComplete(scope.row)">完成检测</el-button>
          <el-button size="mini" type="text" style="color: #ff2727" @click="handleGiveup(scope.row)">弃检</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div slot="footer" class="dialog-footer" style="text-align: center">
      <el-button type="primary" :disabled="multiple" @click="handleComplete">完成检测</el-button>
      <el-button type="primary" plain :disabled="multiple" @click="handleGiveup">弃检</el-button>
    </div>
    <signature ref="signature" @saveSignPath="saveSignPath"></signature>
  </el-dialog>
</template>

<script>
import { setPopData, popGiveUp, uploadFile } from '@/api/funcdept/preregistration'
import signature from '@/views/funcdept/section_list/list/dialog/signature'
export default {
  components: {
    signature,
  },
  data() {
    return {
      // 打开主弹窗
      open: false,
      // 表格数据
      tableList: [],
      // 表格加载中
      loading: false,
      multiple: true,
      // 表格选中的数据
      ids: [],
    }
  },
  methods: {
    // 打开对话框
    showDialog(data) {
      this.resert()
      this.open = true
      this.tableList = data
    },
    // 完成检测
    handleComplete(row) {
      this.$confirm('是否执行完成检测操作?', '提示')
        .then(() => {
          let ids = row.id || this.ids.join(',')
          this.loading = true
          setPopData(ids)
            .then(() => {
              ids.split(',').forEach((el) => {
                this.tableList.forEach((val) => {
                  if (el == val.id) {
                    val.yj = 1
                  }
                })
              })
              this.tableList = JSON.parse(JSON.stringify(this.tableList))
              this.$modal.msgSuccess('操作成功')
              this.loading = false
            })
            .catch((error) => {
              console.error(error)
              this.loading = false
            })
        })
        .catch()
    },
    // 弃检
    handleGiveup(row) {
      this.$confirm('是否确认弃检并签名?', '提示', {
        cancelButtonText: '直接弃检',
        confirmButtonText: '签名弃检',
      })
        .then(() => {
          this.$refs.signature.signature('qj')
        })
        .catch(() => {
          this.handleGiveupConfirm()
        })
    },
    // 签名返回值
    saveSignPath(url) {
      var bytes = window.atob(url.split(',')[1])
      var array = []
      for (var i = 0; i < bytes.length; i++) {
        array.push(bytes.charCodeAt(i))
      }
      var blob = new Blob([new Uint8Array(array)], { type: 'image/jpeg' })
      var formData = new FormData()
      formData.append('file', blob)
      this.loading = true
      uploadFile(formData)
        .then((res) => {
          this.handleGiveupConfirm(res)
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 执行弃检操作
    handleGiveupConfirm(res) {
      let query = {
        ids: this.ids.join(','),
        path: '',
      }
      if (res) {
        query.path = res.data
      }
      // 进行拒检处理
      popGiveUp(query)
        .then(() => {
          this.ids.forEach((el) => {
            this.tableList.forEach((val) => {
              if (el == val.id) {
                val.jj = 1
              }
            })
          })
          this.tableList = JSON.parse(JSON.stringify(this.tableList))
          this.$modal.msgSuccess('操作成功')
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    resert() {
      this.tableList = []
    },
    // 关闭弹窗
    close() {
      this.$emit('closePopDialog')
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
  },
}
</script>
