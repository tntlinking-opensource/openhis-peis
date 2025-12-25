<!-- 第三方合作机构  开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column">
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
    <div class="table-box">
      <el-table ref="dataList" border v-loading="loading" :data="dataList" height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="第三方ID" align="center" prop="sourceId" show-overflow-tooltip />
        <el-table-column label="第三方名称" align="center" prop="typeName" show-overflow-tooltip />
        <el-table-column label="账号" align="center" prop="account" show-overflow-tooltip />
        <el-table-column label="密码" align="center" prop="password" show-overflow-tooltip />
        <el-table-column label="介绍" align="center" prop="tip" show-overflow-tooltip />
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status == 1">正常</el-tag>
            <el-tag type="danger" v-else>关闭</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createdate" show-overflow-tooltip />
        <el-table-column label="排序" align="center" prop="seq" width="60" show-overflow-tooltip />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:branch:edit']">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleRemove(scope.row)" v-hasPermi="['system:branch:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改合作机构对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="860px" class="sub-center report-setting" destroy-on-close append-to-body :close-on-click-modal="false" @close="reset">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" :inline="true" hide-required-asterisk v-loading="loading">
        <el-form-item label="第三方ID" prop="sourceId">
          <el-input v-model="form.sourceId" placeholder="请输入第三方ID" clearable style="width: 220px" />
        </el-form-item>
        <el-form-item label="第三方名称" prop="typeName">
          <el-input v-model="form.typeName" placeholder="请输入第三方名称" clearable style="width: 220px" />
        </el-form-item>
        <el-form-item label="账号" prop="account">
          <el-input v-model="form.account" placeholder="请输入账号" clearable style="width: 220px" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入密码" clearable style="width: 220px" />
        </el-form-item>
        <el-form-item label="排序" prop="seq">
          <el-input v-model="form.seq" placeholder="请输入排序" style="width: 220px" />
        </el-form-item>
        <el-form-item label="配置设置" prop="setting">
          <el-input v-model="form.setting" placeholder="请输入配置设置" clearable style="width: 220px" />
        </el-form-item>
        <el-form-item label="介绍" prop="tip">
          <el-input v-model="form.tip" placeholder="请输入介绍" clearable style="width: 220px" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请输入状态" style="width: 220px">
            <el-option label="开放中" :value="1"></el-option>
            <el-option label="关闭" :value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="logo" prop="logo" style="display: block">
          <div class="setimg">
            <image-upload class="upload-report-setting" ref="uploadT" :uploadData="uploadData" :typeChoose="1" @submitFile="submitFileT" @uploadFinish="uploadFinishT" />
            <el-image v-if="form.id && form.logo" :src="imgPath + form.logo" :preview-src-list="[imgPath + form.logo]" style="width: 100px; height: 100px"></el-image>
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getListApi, addConfig, removeConfig, ConfigDetails, editConfig } from '@/api/system/version_control/service_association'
export default {
  name: 'Cooperative_agency',
  data() {
    return {
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
      },
      // 选中数组
      ids: [],
      selection: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 维护表格数据
      dataList: [],
      // 是否显示弹出层
      open: false,
      // 弹出层标题
      title: '',
      // 遮罩层
      loading: true,
      // 上传图片配置参数
      uploadData: {
        url: '/common/upload', //文件上传地址
        multiple: false, //是否可以上传多个
        limit: 1,
        // fileType: ['xls', 'xlsx'],//文件类型
        data: {
          //上传时附带的额外参数
        },
        picture: '',
      },
      // 图片域名
      imgPath: '',
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        sourceId: [{ required: true, message: '第三方ID不能为空', trigger: 'change' }],
        typeName: [{ required: true, message: '第三方名称不能为空', trigger: 'change' }],
        account: [{ required: true, message: '账号不能为空', trigger: 'change' }],
        password: [{ required: true, message: '密码不能为空', trigger: 'change' }],
      },
    }
  },
  created() {
    this.imgPath = this.$getCookie('imgPath')
    this.getList()
  },
  methods: {
    // 刷新
    resetQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    /** 查询分中心维护列表 */
    getList() {
      this.loading = true
      getListApi(this.queryParams)
        .then(({ data }) => {
          this.dataList = data.records
          this.total = data.total
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.open = true
      this.title = '新增合作机构'
      this.reset()
    },
    //确认分中心logo
    submitFileT() {
      var msg = this.$refs.uploadT.isUpload()
      this.loading = this.$loading({
        lock: true,
        text: '加载中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      if (msg === true) {
        this.$refs.uploadT.handelUpload()
      } else {
        this.uploadFinishT(1)
      }
    },
    //接收分中心logo返回地址
    uploadFinishT(value, res) {
      if (value == 1) {
        this.$modal.msgSuccess('上传成功!')
      } else {
        this.$modal.msgSuccess('上传失败!')
      }
      this.loading.close()
      this.loading = false
      this.form.logo = res.data
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        sourceId: '',
        typeName: '',
        account: '',
        password: '',
        seq: '1',
        setting: '',
        tip: '',
        status: 1,
        logo: '',
      }
      this.resetForm('form')
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.selection = selection
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
      this.open = true
      this.title = '编辑合作机构'
      this.loading = true
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
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            editConfig(this.form).then((response) => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addConfig(this.form).then((response) => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
  },
}
</script>
<style lang="scss">
.sub-center {
  .el-input-number {
    .el-input__inner {
      text-align: left;
    }
  }

  .label-title {
    display: block;
    font-weight: 600;
    font-size: 14px;
    line-height: 20px;
    color: #333333;
    margin-bottom: 12px;
  }
}
.report-setting {
  .setimg {
    display: flex;
    flex-direction: inherit;
    width: auto;
    img {
      background-color: transparent;
    }
    .btn {
      margin: 8px;
      width: 70px;
      height: 36px;
    }
  }
}
</style>

<style scoped>
.upload-report-setting /deep/ .el-upload-dragger {
  padding: 0;
  border: 0;
}

.upload-report-setting /deep/ .el-upload-list__item-name {
  display: none;
  /* max-width: 80px;
  padding: 0; */
}
</style>
