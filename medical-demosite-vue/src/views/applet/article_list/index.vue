<!-- 文章列表  开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form size="small" :model="queryParams" ref="queryForm" :inline="true" class="no-margin-bottom">
      <el-form-item prop="title" label="标题">
        <el-input style="width: 230px" v-model="queryParams.title" placeholder="请输入标题"></el-input>
      </el-form-item>
      <el-form-item prop="status" label="分类">
        <input-select :selectData="selectData" :isTrim="true" :onlyValue="true" @change="selectChangeQuery"></input-select>
      </el-form-item>
      <el-form-item>
        <el-button class="zk-btn-style1" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button class="zk-btn-style2" icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-refresh" size="mini" @click="resetQuery">刷新</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['applet:article_list:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['applet:article_list:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleRemove" v-hasPermi="['applet:article_list:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-setting" size="mini" :disabled="multiple" @click="handleSetting(1)" v-hasPermi="['applet:article_list:setting']">设为上线</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-setting" size="mini" :disabled="multiple" @click="handleSetting(0)" v-hasPermi="['applet:article_list:setting']">设为下线</el-button>
      </el-col>
    </el-row>
    <div class="table-box">
      <el-table ref="tableDate" border v-loading="loading" :data="tableDate" height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="标题" align="center" prop="title" show-overflow-tooltip />
        <el-table-column label="图片" align="center">
          <template slot-scope="scope">
            <el-image style="width: 100px; height: 80px" :src="imgPath + scope.row.thumbnail" :preview-src-list="imgList"> </el-image>
          </template>
        </el-table-column>
        <el-table-column label="分类" align="center" prop="classifyName" show-overflow-tooltip />
        <el-table-column label="类型" align="center">
          <template slot-scope="scope">
            {{ ['文章', '外链'][scope.row.type] }}
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center">
          <template slot-scope="scope">
            <span style="color: red" v-if="scope.row.status == 0">下线</span>
            <span style="color: green" v-else-if="scope.row.status == 1">上线</span>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createdate" show-overflow-tooltip />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['applet:article_list:edit']">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleRemove(scope.row)" v-hasPermi="['applet:article_list:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改工作流对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1060px" class="sub-center report-setting" destroy-on-close append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" :inline="true" hide-required-asterisk v-loading="loading">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" clearable style="width: 340px" />
        </el-form-item>
        <el-form-item label="缩略图" prop="thumbnail" style="display: block">
          <div class="setimg">
            <image-upload class="upload-report-setting" ref="upload" :uploadData="uploadData" :typeChoose="1" @submitFile="submitFile" @uploadFinish="uploadFinish" />
            <el-image v-if="form.id && form.thumbnail" :src="imgPath + form.thumbnail" :preview-src-list="[imgPath + form.thumbnail]" style="width: 100px; height: 100px"></el-image>
          </div>
        </el-form-item>
        <el-form-item label="分类" prop="classify">
          <input-select :selectData="selectData" :isTrim="true" :onlyValue="true" @change="selectChange" :initialValue="form.classifyName"></input-select>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-radio v-model="form.type" :label="0">文章</el-radio>
          <el-radio v-model="form.type" :label="1">外链</el-radio>
        </el-form-item>
        <el-form-item label="链接" prop="content" v-if="form.type === 1">
          <el-input type="textarea" :rows="2" v-model="form.content" placeholder="请输入链接" clearable style="width: 810px" />
        </el-form-item>
        <el-form-item label="内容" prop="content" v-if="form.type === 0">
          <editor v-model="form.content" :min-height="192" style="width: 810px" />
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
import { getListApi, saOrUp, getDetailsApi, deleteApi, settingApi } from '@/api/applet/article_list.js'
export default {
  name: 'Article_list',
  data() {
    return {
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
      },
      // 总条数
      total: 0,
      // 维护表格数据
      tableDate: [],
      // 图片地址前缀
      imgPath: '',
      // 图片列表
      imgList: [],
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

      // 是否显示弹出层
      open: false,
      // 弹出层标题
      title: '',
      // 遮罩层
      loading: true,
      // 表单参数
      form: {},
      // 分类列表
      typeOptions: [],
      // 传入数据模板
      selectData: {
        placeholder: '请输入输入码选择',
        value: '类型名称', //第二列标题
        url: '/app/appArticle/getTypeList', //请求连接
        selectWidth: 340, //选择器宽度（选填，默认230）不加px,传100%则为100%
        bindValue: '', //初始值
        secondName: 'name', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'name', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 表单校验
      rules: {
        title: [{ required: true, message: '标题不能为空', trigger: 'change' }],
        thumbnail: [{ required: true, message: '请上传缩略图', trigger: 'change' }],
        classify: [{ required: true, message: '请选择分类', trigger: 'change' }],
        type: [{ required: true, message: '请选择类型', trigger: 'change' }],
      },
    }
  },
  created() {
    this.imgPath = this.$getCookie('imgPath')
    this.getList()
  },
  methods: {
    // 刷新
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 查询工作流列表 */
    getList() {
      this.loading = true
      getListApi(this.queryParams)
        .then(({ data }) => {
          this.tableDate = data.records
          this.imgList = data.records.map((item) => this.imgPath + item.thumbnail)
          this.total = data.total
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableDate.clearSelection()
      this.$refs.tableDate.toggleRowSelection(row)
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.title = '新增'
      this.open = true
    },
    /** 删除按钮操作 */
    handleRemove(row) {
      const ids = row.id || this.ids.join(',')
      this.$modal
        .confirm('您确定要删除该信息吗？', '提示')
        .then(function () {
          return deleteApi(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {})
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids.join(',')
      this.open = true
      this.title = '编辑'
      this.loading = true
      getDetailsApi(id)
        .then((response) => {
          this.form = response.data
          this.form.branchId = this.form.branchId.toString()
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 修改上线状态
    handleSetting(status) {
      const ids = this.ids
      this.loading = true
      settingApi({ ids, status })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('设置成功')
          this.loading = false
        })
        .catch((err) => {
          console.error(err)
          this.loading = false
        })
    },
    // 修改分中心
    handleChangeFzx(val) {
      this.fzxOptions.forEach((el) => {
        if (el.branchId == val) {
          this.form.fzx = el.fzx
        }
      })
    },
    //确认分中心thumbnail
    submitFile() {
      var msg = this.$refs.upload.isUpload()
      this.loading = this.$loading({
        lock: true,
        text: '加载中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      if (msg === true) {
        this.$refs.upload.handelUpload()
      } else {
        this.uploadFinish(1)
      }
    },
    //接收分中心thumbnail返回地址
    uploadFinish(value, res) {
      if (value == 1) {
        this.$modal.msgSuccess('上传成功!')
      } else {
        this.$modal.msgSuccess('上传失败!')
      }
      this.loading.close()
      this.loading = false
      this.form.thumbnail = res.data
    },
    // 搜索栏分类
    selectChangeQuery(value) {
      this.queryParams.classify = value.id
      this.handleQuery()
    },
    // change方法返回选中的值
    selectChange(value) {
      this.form.classify = value.id
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        title: '',
        thumbnail: '',
        classify: '',
        classifyName: '',
        type: 0,
        content: '',
      }
      this.resetForm('form')
    },
    /** 提交按钮 */
    submitForm() {
      if (this.form.thumbnail == '') {
        this.$modal.msgError('请上传图片！')
        return
      }
      this.$refs['form'].validate((valid) => {
        if (valid) {
          saOrUp(this.form).then((response) => {
            if (this.form.id != null) {
              this.$modal.msgSuccess('修改成功')
            } else {
              this.$modal.msgSuccess('新增成功')
            }
            this.open = false
            this.getList()
          })
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
