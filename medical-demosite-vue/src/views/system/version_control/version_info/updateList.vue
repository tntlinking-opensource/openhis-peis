<template>
  <el-dialog title="服务更新记录" :visible.sync="open" width="760px" class="sub-center report-setting" destroy-on-close append-to-body :close-on-click-modal="false">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-refresh" size="mini" @click="resetQuery">刷新</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['system:branch:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['system:branch:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleRemove" v-hasPermi="['system:branch:remove']">删除</el-button>
      </el-col>
    </el-row>
    <el-table ref="dataList" :data="dataList" align="center" border height="500px" stripe v-loading="loading" @selection-change="handleSelectionChange" @row-click="rowClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序列" width="55" type="index" align="center" />
      <el-table-column label="服务类型" align="center" prop="serviceType" show-overflow-tooltip>
        <template slot-scope="scope">
          <span v-if="scope.row.serviceType == 1"> 前端 </span>
          <span v-if="scope.row.serviceType == 2"> 后端 </span>
        </template>
      </el-table-column>
      <el-table-column label="服务名称" align="center" prop="serviceId" width="180">
        <template slot-scope="scope">
          <div v-for="(item, index) in serviceListAll" :key="index">
            <span v-if="item.serviceId == scope.row.serviceId">{{ item.serviceName }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" show-overflow-tooltip />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == 0">正常</el-tag>
          <el-tag v-else-if="scope.row.status == 1">停用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleAdd(scope.row)" v-hasPermi="['system:branch:addChild']">执行</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加、修改对话框 -->
    <el-dialog :title="title" :visible.sync="open2" width="760px" class="sub-center report-setting" destroy-on-close append-to-body :close-on-click-modal="false" @close="reset">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" :inline="true" hide-required-asterisk v-loading="loading2">
        <el-form-item label="安装包文件" prop="filePath">
          <FileUpload :uploadData="uploadData" :uploadModel="2" @handleUploading="handleUploading" @uploadFinish="uploadFinish" uploadWidth="220px"></FileUpload>
          <div style="width: 550px" v-if="form.filePath">{{ form.filePath }}</div>
        </el-form-item>
        <br />
        <el-form-item label="数据库文件" prop="sqlFile">
          <FileUpload ref="sqlFile" :uploadData="uploadData2" :uploadModel="2" @handleUploading="handleUploading" @uploadFinish="uploadFinish2" uploadWidth="220px"></FileUpload>
          <span style="color: blue; cursor: pointer" @click="handelUpload(1)">上传</span>
          <div style="width: 550px" v-if="form.sqlFile">{{ form.sqlFile }}</div>
        </el-form-item>
        <br />
        <el-form-item label="其他文件" prop="otherFile">
          <FileUpload ref="otherFile" :uploadData="uploadData2" :uploadModel="2" @handleUploading="handleUploading" @uploadFinish="uploadFinish3" uploadWidth="220px"></FileUpload>
          <span style="color: blue; cursor: pointer" @click="handelUpload(2)">上传</span>
          <div style="width: 550px" v-if="form.otherFile">{{ form.otherFile }}</div>
        </el-form-item>
        <br />
        <el-form-item label="服务类型" prop="serviceType">
          <el-select v-model="form.serviceType" placeholder="请选择服务类型" style="width: 220px" @change="serviceTypeChange">
            <el-option label="前端" :value="1"></el-option>
            <el-option label="后端" :value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="系统服务" prop="serviceId">
          <el-select v-model="form.serviceId" placeholder="请选择系统服务" style="width: 220px" :disabled="!form.serviceType">
            <el-option v-for="(item, index) in serviceList" :key="index" :label="item.serviceName" :value="item.serviceId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态" style="width: 220px">
            <el-option label="正常" :value="0"></el-option>
            <el-option label="停用" :value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" v-model="form.remark" placeholder="请输入备注" clearable style="width: 570px" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </el-dialog>
</template>

<script>
import { getChildServiceList, getServiceType, addSysVersionService, sysVersionServiceDetails, editSysVersionService } from '@/api/system/version_control/version_info'
export default {
  data() {
    return {
      // 打开对话框
      open: false,
      // 加载中
      loading: false,
      // 所选服务信息
      serviceInfo: {},
      // 数据列表
      dataList: [],
      // 所有服务类型
      serviceListAll: [],
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,

      // ***********************更新服务弹窗
      // 标题
      title: '',
      // 打开对话框
      open2: false,
      // 加载中
      loading2: false,
      // 表单数据
      form: {},
      // 表单校验
      rules: {},
      // 上传文件配置参数
      uploadData: {
        url: '/common/upload', //文件上传地址
        multiple: false, //是否可以上传多个
        limit: 1, //文件上传个数限制
        fileType: [], //文件类型
        data: {}, //上传时附带的额外参数
        autoUpload: true,
      },
      // 上传文件配置参数
      uploadData2: {
        url: '/common/uploads', //文件上传地址
        multiple: true, //是否可以上传多个
        limit: 9, //文件上传个数限制
        fileType: [], //文件类型
        data: {}, //上传时附带的额外参数
        autoUpload: false,
      },
      // 系统服务列表
      serviceList: [],
    }
  },
  methods: {
    // 打开对话框
    showDialog(row) {
      this.serviceInfo = row
      this.dataList = []
      getServiceType({ size: 100 }).then((res) => {
        this.serviceListAll = res.data.records
      })
      this.open = true
      this.getList()
    },
    // 刷新
    resetQuery() {
      this.getList()
    },
    /** 查询分中心维护列表 */
    getList() {
      this.loading = true
      getChildServiceList({ versionId: this.serviceInfo.id })
        .then(({ data }) => {
          this.dataList = data
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.open2 = true
      this.title = '新增更新服务'
      this.reset()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.dataList.clearSelection()
      this.$refs.dataList.toggleRowSelection(row)
    },
    /** 删除按钮操作 */
    handleRemove(row) {
      const ids = row.id || this.ids.join(',')
      this.$modal
        .confirm('您确定要删除该信息吗？')
        .then(function () {
          return removeConfig(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {})
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const id = row.id || this.ids.join(',')
      this.open2 = true
      this.title = '编辑版本信息'
      this.loading2 = true
      sysVersionServiceDetails(id)
        .then((response) => {
          this.form = response.data
          this.serviceTypeChange()
          this.loading2 = false
        })
        .catch((error) => {
          console.error(error)
          this.loading2 = false
        })
    },

    // *************************
    // 表单重置
    reset() {
      this.form = {
        filePath: '',
        sqlFile: '',
        otherFile: '',
        serviceType: '',
        serviceId: '',
        status: 0,
        notes: '',
        versionId: this.serviceInfo.id,
      }
      this.resetForm('form')
    },
    // 手动执行上传文件
    handelUpload(type) {
      let _name = type == 1 ? 'sqlFile' : 'otherFile'
      let msg = this.$refs[_name].isUpload()
      if (msg === true) {
        this.$refs[_name].handelUpload()
      } else {
        this.$modal.msgWarning(msg, '提醒')
      }
    },
    // 文件上传中
    handleUploading() {
      this.$modal.msgWarning('文件上传中')
    },
    // 上传安装包文件成功
    uploadFinish(val, res) {
      if (val == 1) {
        this.form.filePath = res.data
        this.$modal.msgSuccess('安装包文件上传完成')
      } else {
        this.$modal.msgError('安装包文件上传失败')
      }
    },
    // 上传数据库文件成功
    uploadFinish2(val, res) {
      if (val == 1) {
        this.form.sqlFile = res.data.urlList.join(',')
        this.$modal.msgSuccess('数据库文件上传完成')
      } else {
        this.$modal.msgError('数据库文件上传失败')
      }
    },
    // 上传其他文件成功
    uploadFinish3(val, res) {
      if (val == 1) {
        this.form.otherFile = res.data.urlList.join(',')
        this.$modal.msgSuccess('其他文件上传完成')
      } else {
        this.$modal.msgError('其他文件上传失败')
      }
    },
    // 服务类型改变
    serviceTypeChange() {
      getServiceType({ serviceType: this.form.serviceType, size: 100 }).then((res) => {
        this.serviceList = res.data.records
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loading2 = true
          if (this.form.id != null) {
            editSysVersionService(this.form)
              .then((response) => {
                this.$modal.msgSuccess('修改成功')
                this.open2 = false
                this.getList()
                this.loading2 = false
              })
              .catch((err) => {
                console.error(err)
                this.loading2 = false
              })
          } else {
            addSysVersionService(this.form)
              .then((response) => {
                this.$modal.msgSuccess('新增成功')
                this.open2 = false
                this.getList()
                this.loading2 = false
              })
              .catch((err) => {
                console.error(err)
                this.loading2 = false
              })
          }
        }
      })
    },
    // 取消按钮
    cancel() {
      this.open2 = false
      this.reset()
    },
  },
}
</script>