<!-- 报告配置  开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">刷新</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['system:branch:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['system:branch:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleRemove" v-hasPermi="['system:branch:remove']">删除</el-button>
      </el-col>
    </el-row>
    <div class="table-box">
      <el-table ref="reportList" border v-loading="loading" :data="reportList" height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="分中心名字" align="center" prop="branchName" show-overflow-tooltip />
        <el-table-column label="备注" align="center" prop="memo" show-overflow-tooltip />
        <el-table-column label="是否默认" align="center" prop="isDefault">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.isDefault == 1">是</el-tag>
            <el-tag type="danger" v-else>否</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status == 0">正常</el-tag>
            <el-tag type="danger" v-else>下线</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createdate" show-overflow-tooltip />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:branch:edit']">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleRemove(scope.row)" v-hasPermi="['system:branch:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改报告配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="860px" class="sub-center report-setting" destroy-on-close append-to-body :close-on-click-modal="false" @close="reset">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" :inline="true" hide-required-asterisk v-loading="loading">
        <el-form-item label="分中心" prop="branchId">
          <el-select v-model="form.branchId" placeholder="请选择" clearable ref="branchId" @change="handleVerifyFzx" style="width: 220px">
            <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId" />
          </el-select>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入地址" clearable style="width: 220px" />
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入电话" clearable style="width: 220px" />
        </el-form-item>
        <el-form-item label="传真" prop="fax">
          <el-input v-model="form.fax" placeholder="请输入传真" clearable style="width: 220px" />
        </el-form-item>
        <el-form-item label="邮编" prop="postalCode">
          <el-input v-model="form.postalCode" placeholder="请输入邮编" clearable style="width: 220px" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" clearable style="width: 220px" />
        </el-form-item>
        <el-form-item label="检字" prop="inspect">
          <el-input v-model="form.inspect" placeholder="请输入检字" clearable style="width: 220px" />
        </el-form-item>
        <el-form-item label="资质证书号" prop="certificateNo">
          <el-input v-model="form.certificateNo" placeholder="请输入资质证书号" clearable style="width: 220px" />
        </el-form-item>
        <el-form-item label="首页印制" prop="producer">
          <el-input v-model="form.producer" placeholder="请输入首页印制" clearable style="width: 220px" />
        </el-form-item>
        <el-form-item label="备注" prop="memo">
          <el-input v-model="form.memo" placeholder="请输入备注" clearable style="width: 220px" />
        </el-form-item>
        <el-form-item label="生成正式报告的隐私报告尾页" prop="generatePrivacyReport">
          <el-select 
            v-model="form.generatePrivacyReport" 
            placeholder="请选择" 
            clearable 
            ref="generatePrivacyReport" 
            style="width: 220px"
          >
            <el-option label="是" value="1" />
            <el-option label="否" value="0" />
          </el-select>
        </el-form-item>
        <el-form-item label="分中心logo" prop="logo" style="display: block">
          <div class="setimg">
            <image-upload class="upload-report-setting" ref="uploadT" :uploadData="uploadData" :typeChoose="1" @submitFile="submitFileT" @uploadFinish="uploadFinishT" />
            <template v-if="form.id && form.logo">
              <el-image :src="imgPath + form.logo" :preview-src-list="[imgPath + form.logo]" style="width: 100px; height: 100px"></el-image>
              <span class="delete-btn" @click="deletePic(1)">删除</span>
            </template>
          </div>
        </el-form-item>
        <el-form-item label="二维码" prop="QRCode" style="display: block">
          <div class="setimg">
            <image-upload class="upload-report-setting" ref="uploadQ" :uploadData="uploadData" :typeChoose="1" @submitFile="submitFileQ" @uploadFinish="uploadFinishQ" />
            <template v-if="form.id && form.QRCode">
              <el-image :src="imgPath + form.QRCode" :preview-src-list="[imgPath + form.QRCode]" style="width: 100px; height: 100px"></el-image>
              <span class="delete-btn" @click="deletePic(2)">删除</span>
            </template>
          </div>
        </el-form-item>
        <el-form-item label="小程序码" prop="miniProgramCode" style="display: block">
          <div class="setimg">
            <image-upload class="upload-report-setting" ref="uploadP" :uploadData="uploadData" :typeChoose="1" @submitFile="submitFileP" @uploadFinish="uploadFinishP" />
            <template v-if="form.id && form.miniProgramCode">
              <el-image :src="imgPath + form.miniProgramCode" :preview-src-list="[imgPath + form.miniProgramCode]" style="width: 100px; height: 100px"></el-image>
              <span class="delete-btn" @click="deletePic(3)">删除</span>
            </template>
          </div>
        </el-form-item>
        <el-form-item label="团检报告附件7(职业健康检查机构备案回执)" prop="pic" style="display: block">
          <div class="setimg">
            <image-upload class="upload-report-setting" ref="uploadS" :uploadData="uploadData" :typeChoose="1" @submitFile="submitFileS" @uploadFinish="uploadFinishS" />
            <template v-if="form.id && form.pic">
              <el-image v-if="form.id && form.pic" :src="imgPath + form.pic" :preview-src-list="[imgPath + form.pic]" style="width: 100px; height: 100px"></el-image>
              <span class="delete-btn" @click="deletePic(4)">删除</span>
            </template>
          </div>
        </el-form-item>
        <el-form-item label="职业团检报告附件9(技术人员资质一览表)" prop="pic" style="display: block">
          <!-- <el-input type="textarea" :rows="3" v-model="form.personAptitude" placeholder="请输入技术人员信息" clearable style="width: 570px" /> -->
          <el-button size="mini" type="primary" style="margin-bottom: 8px" @click="handleAddPerson">添加</el-button>
          <el-table ref="tableList" style="width: 800px" border :data="form.personAptitude" height="400px">
            <el-table-column label="序列" width="55" type="index" align="center" />
            <el-table-column prop="name" label="名称" align="center" width="120">
              <template slot-scope="scope">
                <el-input v-model="scope.row.name"></el-input>
              </template>
            </el-table-column>
            <el-table-column prop="sex" label="性别" align="center" width="70">
              <template slot-scope="scope">
                <el-input v-model="scope.row.sex"></el-input>
              </template>
            </el-table-column>
            <el-table-column prop="post" label="职称" align="center" width="120">
              <template slot-scope="scope">
                <el-input v-model="scope.row.post"></el-input>
              </template>
            </el-table-column>
            <el-table-column prop="ks" label="科室" align="center" width="120">
              <template slot-scope="scope">
                <el-input v-model="scope.row.ks"></el-input>
              </template>
            </el-table-column>
            <el-table-column prop="major" label="专业" align="center" width="120">
              <template slot-scope="scope">
                <el-input v-model="scope.row.major"></el-input>
              </template>
            </el-table-column>
            <el-table-column prop="workYears" label="工龄" align="center" width="70">
              <template slot-scope="scope">
                <el-input v-model="scope.row.workYears"></el-input>
              </template>
            </el-table-column>
            <el-table-column prop="number" label="证书号" align="center" width="120">
              <template slot-scope="scope">
                <el-input v-model="scope.row.number"></el-input>
              </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" align="center">
              <template slot-scope="scope">
                <el-button size="mini" type="text" style="color: #ff2727" @click="handleDelete(scope.$index)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
        <el-form-item label="电子签章" prop="stampPic" style="display: block">
          <div class="setimg">
            <image-upload class="upload-report-setting" ref="uploadStampPic" :uploadData="uploadData" :typeChoose="1" @submitFile="submitFileStampPic" @uploadFinish="uploadFinishStampPic" />
            <el-image v-if="form.id && form.stampPic" :src="imgPath + form.stampPic" :preview-src-list="[imgPath + form.stampPic]" style="width: 100px; height: 100px"></el-image>
          </div>
        </el-form-item>
        <el-form-item label="职业盖章" prop="professionalSeal" style="display: block">
          <div class="setimg">
            <image-upload class="upload-report-setting" ref="uploadProfession" :uploadData="uploadData" :typeChoose="1" @submitFile="submitFileProfession" @uploadFinish="uploadFinishProfession" />
            <el-image v-if="form.id && form.professionalSeal" :src="imgPath + form.professionalSeal" :preview-src-list="[imgPath + form.professionalSeal]" style="width: 100px; height: 100px"></el-image>
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
import { getListApi, addReportConfig, removeReportConfig, reportConfigDetails, editreportConfig } from '@/api/system/report_setting'
import { getBranchData } from '@/api/subscribe/appointment_details/appointment_details'

import { getCookie } from '@/utils/getCookie.js'

export default {
  name: 'ReportSetting',
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
      reportList: [],
      // 是否显示弹出层
      open: false,
      // 弹出层标题
      title: '',
      // 遮罩层
      loading: true,
      // 分中心列表
      fzxOptions: [],
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
      imgPath: getCookie('imgPath'),
      // 表单参数
      form: {
        picture: '', //缩略图
        pics: '', //图片
      },
      // 表单校验
      rules: {
        branchId: [{ required: true, message: '请选择分中心', trigger: 'change' }],
        address: [{ required: true, message: '地址不能为空', trigger: 'change' }],
        phone: [{ required: true, message: '电话不能为空', trigger: 'change' }],
        inspect: [{ required: true, message: '检字不能为空', trigger: 'change' }],
        certificateNo: [{ required: true, message: '资质证书号不能为空', trigger: 'change' }],
        logo: [{ required: true, message: '请上传分中心logo', trigger: 'change' }],
      },

      selectTime: undefined,
      selectTime2: undefined,
    }
  },
  created() {
    getBranchData().then(({ data }) => {
      this.fzxOptions = data
    })
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
          let reportList = []
          data.records.forEach((el) => {
            reportList.push({ ...el, ...JSON.parse(el.content) })
          })
          this.reportList = reportList
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
      this.title = '新增报告配置'
    },
    // 校验当前分中心是否已添加
    handleVerifyFzx() {
      let has = false
      this.reportList.forEach((el) => {
        if (el.branchId == this.form.branchId) {
          this.$alert('所选分中心已存在,请重新选择')
          this.form.branchId = ''
          this.form.name = ''
          has = true
        }
      })
      if (has) return
      this.fzxOptions.forEach((el) => {
        if (el.branchId == this.form.branchId) {
          this.form.name = el.fzx
        }
      })
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
    //确认分中心二维码
    submitFileQ() {
      var msg = this.$refs.uploadQ.isUpload()
      this.loading = this.$loading({
        lock: true,
        text: '加载中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      if (msg === true) {
        this.$refs.uploadQ.handelUpload()
      } else {
        this.uploadFinishQ(1)
      }
    },
    //接收分中心二维码返回地址
    uploadFinishQ(value, res) {
      if (value == 1) {
        this.$modal.msgSuccess('上传成功!')
      } else {
        this.$modal.msgSuccess('上传失败!')
      }
      this.loading.close()
      this.loading = false
      this.form.QRCode = res.data
    },
    //确认小程序码
    submitFileP() {
      var msg = this.$refs.uploadP.isUpload()
      this.loading = this.$loading({
        lock: true,
        text: '加载中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      if (msg === true) {
        this.$refs.uploadP.handelUpload()
      } else {
        this.uploadFinishP(1)
      }
    },
    //接收小程序码返回地址
    uploadFinishP(value, res) {
      if (value == 1) {
        this.$modal.msgSuccess('上传成功!')
      } else {
        this.$modal.msgSuccess('上传失败!')
      }
      this.loading.close()
      this.loading = false
      this.form.miniProgramCode = res.data
    },
    //确认附件7
    submitFileS() {
      var msg = this.$refs.uploadS.isUpload()
      this.loading = this.$loading({
        lock: true,
        text: '加载中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      if (msg === true) {
        this.$refs.uploadS.handelUpload()
      } else {
        this.uploadFinishS(1)
      }
    },
    //接收附件7返回地址
    uploadFinishS(value, res) {
      if (value == 1) {
        this.$modal.msgSuccess('上传成功!')
      } else {
        this.$modal.msgSuccess('上传失败!')
      }
      this.loading.close()
      this.loading = false
      this.form.pic = res.data
    },
    //确认附件7
    submitFileStampPic() {
      var msg = this.$refs.uploadStampPic.isUpload()
      this.loading = this.$loading({
        lock: true,
        text: '加载中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      if (msg === true) {
        this.$refs.uploadStampPic.handelUpload()
      } else {
        this.uploadFinishStampPic(1)
      }
    },
    //接收附件7返回地址
    uploadFinishStampPic(value, res) {
      if (value == 1) {
        this.$modal.msgSuccess('上传成功!')
      } else {
        this.$modal.msgSuccess('上传失败!')
      }
      this.loading.close()
      this.loading = false
      this.form.stampPic = res.data
    },
    // 职业盖章
    submitFileProfession() {
      var msg = this.$refs.uploadProfession.isUpload()
      this.loading = this.$loading({
        lock: true,
        text: '加载中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      if (msg === true) {
        this.$refs.uploadProfession.handelUpload()
      } else {
        this.uploadFinishProfession(1)
      }
    },
    //接收附件7返回地址
    uploadFinishProfession(value, res) {
      if (value == 1) {
        this.$modal.msgSuccess('上传成功!')
      } else {
        this.$modal.msgSuccess('上传失败!')
      }
      this.loading.close()
      this.loading = false
      this.form.professionalSeal = res.data
    },
    // 添加附件9人员信息
    handleAddPerson() {
      let obj = {
        name: '',
        sex: '',
        post: '',
        ks: '',
        major: '',
        workYears: '',
        number: '',
      }
      this.form.personAptitude.push(obj)
      this.$nextTick(() => {
        this.$refs.tableList.bodyWrapper.scrollTop = this.$refs.tableList.bodyWrapper.scrollHeight
      })
    },
    // 删除附件9人员信息
    handleDelete(index) {
      this.$confirm('确认删除当前信息?', '提示')
        .then(() => {
          this.$delete(this.form.personAptitude, index)
        })
        .catch(() => {})
    },
    // 删除图片
    deletePic(type) {
      if (type == '1') {
        this.form.logo = ''
      } else if (type == '2') {
        this.form.QRCode = ''
      } else if (type == '3') {
        this.form.miniProgramCode = ''
      } else if (type == '4') {
        this.form.pic = ''
      }
    },

    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        branchId: '',
        name: '',
        address: '',
        phone: '',
        fax: '',
        postalCode: '',
        email: '',
        inspect: '',
        certificateNo: '',
        memo: '',
        producer: '',
        logo: '',
        QRCode: '',
        miniProgramCode: '',
        pic: '',
        personAptitude: [],
      }
      this.resetForm('form')
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.reportList.clearSelection()
      this.$refs.reportList.toggleRowSelection(row)
    },
    /** 删除按钮操作 */
    handleRemove(row) {
      const ids = row.id || this.ids.join(',')
      this.$modal
        .confirm('您确定要删除该信息吗？')
        .then(function () {
          return removeReportConfig(ids)
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
      this.title = '编辑报告配置'
      this.loading = true
      reportConfigDetails(id)
        .then((response) => {
          response.data = { ...response.data, ...JSON.parse(response.data.content) }
          this.form = response.data
          if (!this.form.personAptitude) {
            this.form.personAptitude = []
          }
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
          let form = JSON.parse(JSON.stringify(this.form))
          let content = {
            miniProgramCode: form.miniProgramCode,
            QRCode: form.QRCode,
            logo: form.logo,
            pic: form.pic,
            stampPic: form.stampPic,
            professionalSeal: form.professionalSeal,
            name: form.name,
            address: form.address,
            phone: form.phone,
            email: form.email,
            postalCode: form.postalCode,
            fax: form.fax,
            certificateNo: form.certificateNo,
            inspect: form.inspect,
            producer: form.producer,
            memo: form.memo,
            personAptitude: form.personAptitude,
            generatePrivacyReport: form.generatePrivacyReport
          }
          form.content = JSON.stringify(content)
          if (this.form.id != null) {
            editreportConfig(form).then((response) => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addReportConfig(form).then((response) => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        'system/branch/export',
        {
          ...this.queryParams,
        },
        `branch_${new Date().getTime()}.xlsx`
      )
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
  .delete-btn {
    margin-left: 10px;
    color: #ff2020;
    cursor: pointer;
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
