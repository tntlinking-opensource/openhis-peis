<!-- 导入名单 开发人：麦沃德科技矢北 -->
<template>
  <el-dialog title="导入名单" class="import-list-dialog" :visible.sync="open" width="40%" append-to-body>
    <el-form size="small" :inline="true" ref="form" :model="form">
      <el-form-item>
        <file-upload ref="uploadFile" :uploadData="uploadData" :uploadModel="1" @uploadFinish="uploadFinish" uploadWidth="100%"></file-upload>
      </el-form-item>
      <el-form-item>
        <el-button size="mini" type="success" icon="el-icon-upload2" @click="uploadFile">上传</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>
<script>
export default {
  data() {
    return {
      // 打开对话框
      open: false,
      // 表单数据
      form: {},
      // 遮罩层
      loading: false,
      //表格数据
      tableList: [],
      // 非多个禁用
      multiple: true,
      // 上传组件参数
      uploadData: {
        url: '/items/importItems', //文件上传地址
        fileType: ['xls', 'xlsx'], //文件类型
        data: {}, //上传时附带的额外参数
      },
      // 列表索引
      tableIndex: 0,
      // 删除时保存的临时数据
      tempList: [],
      selection: [],
    }
  },
  created() {},
  methods: {
    //打开按钮
    handleShow(id) {
      this.open = true
    },
    // 上传文件成功
    uploadFinish(value, res) {
      if (value == 1) {
        this.$modal.msgSuccess(res.data, '提醒')
      }
      this.loading = false
    },

    // 提交
    uploadFile() {
      var msg = this.$refs.uploadFile.isUpload()
      if (msg === true) {
        this.loading = true
        this.$refs.uploadFile.handelUpload()
      } else {
        this.$modal.msgWarning(msg, '提醒')
        this.loading = false
      }
    },
  },
}
</script>
<style lang="scss">
.import-list-dialog {
  .el-dialog {
    max-width: 1400px;
    height: 20%;

    .el-dialog__body {
      display: flex;
      flex-direction: column;

      .table-box {
        flex: 1;
        overflow: hidden;
      }
    }
  }
}
</style>
