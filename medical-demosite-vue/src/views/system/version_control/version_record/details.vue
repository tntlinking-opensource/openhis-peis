<!-- 添加或修改合作机构对话框 -->
<template>
  <el-dialog :title="title" :visible.sync="open" width="860px" class="sub-center report-setting" destroy-on-close append-to-body :close-on-click-modal="false" @close="reset">
    <el-form ref="form" :model="form" label-width="120px" :inline="true" hide-required-asterisk v-loading="loading">
      <el-form-item label="ip" prop="address">
        <el-input v-model="form.address" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="主机名" prop="hostName">
        <el-input v-model="form.hostName" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="执行时间" prop="executeTime">
        <el-input v-model="form.executeTime" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="是否人工处理" prop="isManual">
        <el-input :value="form.isManual == 1 ? '是' : '否'" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="人工处理人员" prop="executer">
        <el-input v-model="form.executer" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="人工处理时间" prop="manualExecuteTime">
        <el-input v-model="form.manualExecuteTime" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="执行结果" prop="message">
        <el-input v-model="form.message" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="form.remark" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-input :value="form.status == -1 ? '更新失败' : form.status == 0 ? '待更新' : form.status == -1 ? '正在更新' : '已完成'" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="科室IP配置ID" prop="ksIpId">
        <el-input v-model="form.ksIpId" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="版本信息ID" prop="versionId">
        <el-input v-model="form.versionId" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="安装包文件" prop="filePath">
        <el-input v-model="form.filePath" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="数据库文件" prop="sqlFile">
        <el-input v-model="form.sqlFile" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="数据库更新状态" prop="sqlStatus">
        <el-input :value="form.sqlStatus == -1 ? '更新失败' : form.sqlStatus == 0 ? '未更新' : form.sqlStatus == -1 ? '部分更新' : '全部更新'" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="数据库更新备注" prop="sqlRemark">
        <el-input v-model="form.sqlRemark" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="数据库更新时间" prop="sqlUpdateTime">
        <el-input v-model="form.sqlUpdateTime" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="创建时间" prop="createdate">
        <el-input v-model="form.createdate" readonly style="width: 220px" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="cancel">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { ConfigDetails } from '@/api/system/version_control/version_record'
export default {
  data() {
    return {
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 遮罩层
      loading: true,
      // 表单参数
      form: {},
    }
  },
  methods: {
    // 打开对话框
    showDialog(id) {
      this.open = true
      this.title = '更新记录详情'
      this.loading = true
      this.reset()
      ConfigDetails(id)
        .then((response) => {
          this.form = response.data
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 取消按钮
    cancel() {
      this.open = false
    },
    // 表单重置
    reset() {
      this.form = {
        address: '',
        hostName: '',
        executeTime: '',
        isManual: '',
        executer: '',
        manualExecuteTime: '',
        message: '',
        remark: '',
        status: '',
        ksIpId: '',
        versionId: '',
        filePath: '',
        sqlFile: '',
        sqlStatus: '',
        sqlRemark: '',
        sqlUpdateTime: '',
        createdate: '',
      }
      this.resetForm('form')
    },
  },
}
</script>
