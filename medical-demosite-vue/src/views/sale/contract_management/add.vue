<!-- 合同管理-添加 麦沃得科技 开发人: 清风 / 暴雨 / 予安-->
<template>
  <el-dialog :title="title" :visible.sync="open" width="910px" append-to-body :close-on-click-modal="false" destroy-on-close>
    <el-form ref="queryForm" :inline="true" label-width="110px" :model="form" :rules="rules" size="small" hide-required-asterisk>
      <el-form-item label="客户单位名称" prop="khdwmcid">
        <el-input style="width: 308px" v-model="form.khdwmc" :readonly="isRead" v-if="isRead"></el-input>
        <input-select :selectData="selectData" @change="selectChange" :initialValue="form.khdwmc" v-else></input-select>
      </el-form-item>
      <el-form-item label="合同名称" prop="htmc">
        <el-input style="width: 308px" v-model="form.htmc" :readonly="isRead" placeholder="请输入合同名称"></el-input>
      </el-form-item>
      <el-form-item label="合同编号" prop="htbh">
        <el-input style="width: 308px" v-model="form.htbh" readonly></el-input>
      </el-form-item>
      <el-form-item label="销售经理" prop="xsjl">
        <el-input style="width: 308px" v-model="form.xsjl" :readonly="isRead" placeholder="请输入销售经理"></el-input>
      </el-form-item>
      <el-form-item label="体检开始日期" prop="tjksrq">
        <el-date-picker style="width: 308px" type="date" v-model="form.tjksrq" :readonly="isRead" value-format="yyyy-MM-dd hh:mm:ss"></el-date-picker>
      </el-form-item>
      <el-form-item label="体检结束日期" prop="tjjsrq">
        <el-date-picker style="width: 308px" type="date" v-model="form.tjjsrq" :readonly="isRead" value-format="yyyy-MM-dd hh:mm:ss"></el-date-picker>
      </el-form-item>
      <el-form-item label="合同签订日期" prop="htqdrq">
        <el-date-picker style="width: 308px" type="date" v-model="form.htqdrq" :readonly="isRead" value-format="yyyy-MM-dd hh:mm:ss"></el-date-picker>
      </el-form-item>
      <el-form-item label="联系电话" prop="lxdh">
        <el-input style="width: 308px" v-model="form.lxdh" :readonly="isRead"></el-input>
      </el-form-item>
      <el-form-item label="体检人数" prop="tjrs">
        <el-input style="width: 308px" v-model="form.tjrs" :readonly="isRead"></el-input>
      </el-form-item>
      <el-form-item label="预算" prop="ys">
        <el-input style="width: 308px" v-model="form.ys" :readonly="isRead"></el-input>
      </el-form-item>
      <el-form-item label="文件上传" prop="modelUrls" v-if="isRead == false">
        <file-upload ref="uploadFile" :uploadData="uploadData" @uploadFinish="uploadFinish" uploadWidth="736px"></file-upload>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <div style="width: 100%; text-align: right; padding-right: 35px">
        <el-button type="primary" @click="submitForm" v-if="!isRead">保存</el-button>
        <el-button @click="onCancel()">关闭</el-button>
      </div>
    </div>
  </el-dialog>
</template>

<script>
import { addListData, editListData, sellpact, getHthData } from '@/api/sale/contract_management'
import { upgrade, isJDHT } from '@/api/customer/customer_list.js'
import { getCookie } from '@/utils/getCookie.js'
export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (new Date(this.form.tjksrq).getTime() > new Date(this.form.tjjsrq).getTime()) {
        callback(new Error('日期格式错误'))
      } else {
        callback()
      }
    }
    return {
      open: false,
      // 弹窗标题
      title: '',
      // 加载
      loading: false,
      // 客户单位名称参数
      selectData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '客户单位名称', //第二列标题
        url: '/sell/createorder/getContractUnitName', //请求连接
        selectWidth: 308, //选择器宽度（选填，默认230）不加px,传100%则为100%
        firstName: 'khdwsrm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 表单信息
      form: {
        khdwmcid: '',
        khdwmc: undefined,
        tjksrq: '',
        htmc: '',
        tjjsrq: '',
        htbh: '',
        lxdh: '',
        xsjl: '',
        tjrs: '',
        htqdrq: '',
        ys: '',
        appInput: '', //上传合同
      },
      // 校验规则
      rules: {
        khdwmc: [{ required: true, message: '客户单位不能为空', trigger: 'change' }],
        htmc: [{ required: true, message: '合同名称不能为空', trigger: 'change' }],
        xsjl: [{ required: true, message: '销售经理不能为空', trigger: 'change' }],
        tjksrq: [
          { required: true, message: '体检开始日期不能为空', trigger: 'change' },
          { validator: validatePass, trigger: 'change' },
        ],
        tjjsrq: [
          { required: true, message: '体检结束日期不能为空', trigger: 'change' },
          { validator: validatePass, trigger: 'change' },
        ],
        htqdrq: [{ required: true, message: '合同签订日期不能为空', trigger: 'change' }],
        lxdh: [{ required: true, message: '联系电话不能为空', trigger: 'change' }],
      },
      // 是否为查看
      isRead: false,
      // 上传组件参数
      uploadData: {
        url: '/sell/sellpact/uploads', //文件上传地址
        multiple: true, //是否可以上传多个
        data: {}, //上传时附带的额外参数
      },
    }
  },
  methods: {
    // 客户单位名称返回值
    selectChange(value) {
      this.form.khdwmcid = value.id
      this.form.khdwmc = value.khdwmc
      this.form.htmc = value.khdwmc
      this.form.lxdh = value.khdh
    },
    addWindow(context, id) {
      this.open = true
      this.isRead = false
      this.resetting()
      if (context == 0) {
        this.title = '新增'
        this.form.xsjl = decodeURIComponent(getCookie('username'))
        getHthData().then(({ data }) => {
          this.form.htbh = data
        })
      } else if (context == 1) {
        this.title = '编辑'
        sellpact(id).then((response) => {
          this.form = response.data
        })
      } else if (context == 2) {
        this.title = '查看'
        this.isRead = true
        sellpact(id).then((response) => {
          this.form = response.data
        })
      }
    },
    onCancel() {
      this.resetting()
      this.open = false
    },
    // 提交按钮
    submitForm() {
      this.$refs['queryForm'].validate((valid) => {
        if (valid) {
          var msg = this.$refs.uploadFile.isUpload()
          this.loading = this.$loading({
            lock: true,
            text: '加载中',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)',
          })
          if (msg === true) {
            this.$refs.uploadFile.handelUpload()
          } else {
            // this.$alert(msg, '提示')
            // this.loading.close()
            this.uploadFinish(1)
          }
        }
      })
    },
    // 上传文件成功
    uploadFinish(value, res) {
      if (value == 1) {
        var data = this.form
        if (res && res.data.urlList) data.filePath = res.data.urlList.join()
        data.filePath = '#' + data.filePath + '#'
        if (data.id) {
          editListData(data).then(() => {
            this.$modal.msgSuccess('修改成功!')
            this.open = false
            this.$emit('getList')
            this.loading.close()
            this.$confirm('是否将此客户升级为正式客户?', '提示')
              .then(() => {
                const query = {
                  isZskhId: this.form.khdwmcid,
                }
                isJDHT(query).then((response) => {
                  if (!response.data) {
                    upgrade({
                      ids: this.form.khdwmcid,
                    }).then((response) => {
                      this.$modal.msgSuccess('操作成功')
                      this.$confirm('是否前往创建套餐?', '提示')
                        .then(() => {
                          this.$router.push({ name: 'Create_offer' })
                        })
                        .catch(() => {})
                    })
                  } else {
                    this.$alert(`${response.data}`, {
                      dangerouslyUseHTMLString: true,
                    })
                  }
                })
              })
              .catch(() => {})
          })
        } else {
          addListData(data).then(() => {
            this.$modal.msgSuccess('添加成功!')
            this.open = false
            this.$emit('getList')
            this.loading.close()
            this.$confirm('是否将此客户升级为正式客户?', '提示')
              .then(() => {
                const query = {
                  isZskhId: this.form.khdwmcid,
                }
                isJDHT(query).then((response) => {
                  if (!response.data) {
                    upgrade({
                      ids: this.form.khdwmcid,
                    }).then((response) => {
                      this.$modal.msgSuccess('操作成功')
                      this.$confirm('是否前往创建套餐?', '提示')
                        .then(() => {
                          this.$router.push({ name: 'Create_offer' })
                        })
                        .catch(() => {})
                    })
                  } else {
                    this.$alert(`${response.data}`, {
                      dangerouslyUseHTMLString: true,
                    })
                  }
                })
              })
              .catch(() => {})
          })
        }
      } else {
        this.loading.close()
      }
    },
    //重置
    resetting() {
      this.form = {
        khdwmcid: '',
        tjksrq: '',
        htmc: '',
        tjjsrq: '',
        htbh: '',
        lxdh: '',
        xsjl: '',
        tjrs: '',
        htqdrq: '',
        ys: '',
        appInput: '',
      }
    },
  },
}
</script>
