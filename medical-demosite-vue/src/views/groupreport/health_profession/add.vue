<!-- 健康团检样本 开发人：麦沃德科技暴雨 -->
<template>
  <div class="add-container">
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body destroy-on-close :close-on-click-modal="false">
      <el-form ref="form" :model="form" :inline="true" label-width="120px" hide-required-asterisk :rules="rules" v-loading="loading">
        <el-form-item label="团体名称" prop="orgName">
          <input-select ref="groupName" :selectData="ptcodeData" :initialValue="form.orgName" selectWidth="290" @change="ptcodeDataChange"></input-select>
        </el-form-item>
        <el-form-item label="样本类型" prop="sampleType">
          <el-input v-model="form.sampleType" readonly style="width: 290px" />
        </el-form-item>
        <el-form-item label="样本名称" prop="sampleName">
          <el-input v-model="form.sampleName" placeholder="请输入" clearable style="width: 290px" />
        </el-form-item>
        <el-form-item label="订单号" prop="orderId">
          <template v-if="!form.groupId || (form.groupId && orderList.length == 0)">
            <el-input v-model="form.orderId" placeholder="请输入订单号后回车搜索" clearable style="width: 290px" @input="orderReset" @keyup.enter.native="orderSearch" />
          </template>
          <template v-else>
            <el-select v-model="form.orderId" placeholder="请选择" clearable style="width: 290px" @change="orderChange" :disabled="orderList.length == 0">
              <el-option v-for="item in orderList" :key="item.id" :label="item.ddh" :value="item.ddh" />
            </el-select>
          </template>
        </el-form-item>
        <el-form-item label="开始登记时间" prop="beginTime">
          <el-date-picker v-model="form.beginTime" style="width: 290px" value-format="yyyy-MM-dd" type="date" :placeholder="form.beginTime"></el-date-picker>
        </el-form-item>
        <el-form-item label="结束登记时间" prop="endTime">
          <el-date-picker v-model="form.endTime" style="width: 290px" value-format="yyyy-MM-dd" type="date" :placeholder="form.endTime"></el-date-picker>
        </el-form-item>
        <el-form-item label="体检团体类型" prop="idOrgclass">
          <el-select v-model="form.idOrgclass" placeholder="请选择" clearable style="width: 290px" disabled>
            <el-option label="普通客户" :value="0" />
            <el-option label="vip客户" :value="1" />
            <el-option label="流失客户" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="上次是否来检" prop="scsflj">
          <el-select v-model="form.scsflj" placeholder="请选择" style="width: 290px">
            <el-option label="否" :value="0" />
            <el-option label="是" :value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="是否含未检" prop="hasUnchecked">
          <el-checkbox v-model="form.hasUnchecked" style="width: 290px"></el-checkbox>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="form.sex" placeholder="通用" clearable style="width: 290px">
            <el-option label="男" :value="0" />
            <el-option label="女" :value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="部门" prop="orgDepart">
          <el-input v-model="form.orgDepart" placeholder="请输入" clearable style="width: 290px" />
        </el-form-item>
        <el-form-item label="分中心" prop="fzxid">
          <el-select v-model="form.fzxid" placeholder="请选择分中心" multiple style="width: 290px">
            <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="报告分析" prop="bgfx">
          <el-input v-model="form.bgfx" readonly style="width: 710px" type="textarea" :rows="8" resize="none" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button type="primary" plain @click="reset">重 置</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { checkReportOrder, checkType, addOrUpdate, getDetails } from '@/api/groupreport/health_profession.js'
import { getBranchData } from '@/api/statistical/professionchecks/conclusion_table.js' //获取分中心
export default {
  data() {
    var validatePass2 = (rule, value, callback) => {
      if (this.form.endTime < this.form.beginTime) {
        callback(new Error('日期格式不正确'))
      } else {
        callback()
      }
    }
    return {
      // 表单加载中
      loading: false,
      ptcodeData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '团体名称', //第二列标题
        url: '/pacs/itemList/getOrgs', //请求连接
        bindValue: '', //初始值(必传)
        firstName: 'khdwsrm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key',
      },
      // 订单号列表
      orderList: [],
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 表单参数
      form: {},
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 表单验证
      rules: {
        groupId: [{ required: true, message: '请选择团体名称', trigger: 'change' }],
        sampleName: [{ required: true, message: '请输入样本名称', trigger: 'blur' }],
        ddid: [{ required: true, message: '请选择订单号', trigger: 'change' }],
        fzxid: [{ required: true, message: '请选择分中心', trigger: 'change' }],
        beginTime: [
          { required: true, message: '请选择开始登记时间', trigger: 'blur' },
          { validator: validatePass2, trigger: 'blur' },
        ],
        endTime: [
          { required: true, message: '请选择结束登记时间', trigger: 'blur' },
          { validator: validatePass2, trigger: 'blur' },
        ],
      },
      // 分中心列表
      fzxOptions: [],
    }
  },
  methods: {
    // 团体名称选择
    ptcodeDataChange(value) {
      if (value) {
        checkReportOrder(value.id).then(({ data }) => {
          if (!data.length) {
            this.form.groupId = undefined
            this.form.orgName = undefined
            this.form.sampleName = undefined
            this.form.orderId = undefined
            this.form.idOrgclass = undefined
            this.orderList = []
            this.$alert('当前团体未生成订单号,请重新选择!', '提示').then(() => {
              if (this.$refs?.groupName) this.$refs.groupName.initData()
            }).catch(() => {
              if (this.$refs?.groupName) this.$refs.groupName.initData()
            })
            return
          } else {
            this.orderList = data
            this.form.groupId = value.id
            this.form.sampleName = value.khdwmc
            this.form.orgName = JSON.parse(JSON.stringify(value.khdwmc))
          }
        })
      } else {
        this.form.groupId = undefined
        this.form.orgName = undefined
        this.form.sampleName = undefined
        this.form.orderId = undefined
        this.form.idOrgclass = undefined
        this.orderList = []
      }
      this.form.orderId = undefined
    },
    // 选择订单号
    orderChange(val) {
      checkType(val).then(({ data }) => {
        this.form.ddid = this.orderList.filter((item) => item.ddh == val)[0].id
        this.form.orderId = val
        this.form.idOrgclass = data.idOrgclass
      })
    },
    // 订单号搜索
    orderSearch() {
      this.orderList = []
      checkType(this.form.orderId).then(({ data }) => {
        this.form.ddid = data.id
        this.form.idOrgclass = data.idOrgclass
        this.form.groupId = data.idOrg
        this.form.sampleName = data.orgName
        this.form.orgName = JSON.parse(JSON.stringify(data.orgName))
      })
    },
    // 订单号修改
    orderReset() {
      this.form.ddid = undefined
      this.form.idOrgclass = undefined
      this.form.groupId = undefined
      this.form.sampleName = undefined
      this.form.orgName = undefined
    },
    // 添加
    handleAdd() {
      this.form.id = undefined
      this.reset()
      this.getBranchList()
      this.open = true
      this.title = '新增样本'
    },
    // 编辑
    handleUpdate(id) {
      this.open = true
      this.getBranchList()
      this.title = '编辑样本'
      this.loading = true
      getDetails(id).then(({ data }) => {
        data.fzxid = data.fzxid ? data.fzxid.split(',') : []
        this.form = data
        this.form.ddid = data.ddh
        checkReportOrder(this.form.groupId).then(({ data }) => {
          this.orderList = data
        })
        this.$nextTick(() => {
          this.$refs.groupName.initData(this.form.orgName)
        })
        if (this.form.hasUnchecked == 1) {
          this.form.hasUnchecked = true
        } else {
          this.form.hasUnchecked = false
        }
        this.loading = false
      })
    },
    // 获取分中心列表
    getBranchList() {
      getBranchData().then((res) => {
        this.fzxOptions = res.data
      })
    },
    // 表单重置
    reset() {
      if (this.form.id) {
        this.loading = true
        getDetails(this.form.id).then(({ data }) => {
          data.fzxid = data.fzxid ? data.fzxid.split(',') : []
          this.form = data
          this.form.ddid = data.ddh
          checkReportOrder(this.form.groupId).then(({ data }) => {
            this.orderList = data
          })
          this.$nextTick(() => {
            this.$refs.groupName.initData(this.form.orgName)
          })
          if (this.form.hasUnchecked == 1) {
            this.form.hasUnchecked = true
          } else {
            this.form.hasUnchecked = false
          }
          this.loading = false
        })
      } else {
        if (this.$refs?.groupName) this.$refs.groupName.initData()
        this.form = {
          groupId: undefined,
          orgName: undefined,
          sampleType: '按团体',
          sampleName: undefined,
          orderId: undefined,
          ddid: undefined,
          beginTime: undefined,
          endTime: undefined,
          idOrgclass: undefined,
          scsflj: 0,
          hasUnchecked: true,
          sex: '',
          orgDepart: '',
          fzxid: [this.$getCookie('cid')],
          bgfx: '',
        }
        this.loading = false
      }
    },
    // 取消按钮
    cancel() {
      this.open = false
    },
    // 提交按钮
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (!this.form.ddid) {
            this.$alert('请先选择订单', '提示')
            return
          }
          let form = JSON.parse(JSON.stringify(this.form))
          form.beginTime = this.form.beginTime + ' 00:00:00'
          form.endTime = this.form.endTime + ' 23:59:59'
          form.fzxid = form.fzxid.join(',')
          if (this.form.hasUnchecked) {
            form.hasUnchecked = 1
          } else {
            form.hasUnchecked = 0
          }
          form.diseaseHealth = 0
          if (this.form.id != null) {
            addOrUpdate(form).then(({ data }) => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.$emit('getList')
            })
          } else {
            form.id = ''
            addOrUpdate(form).then(({ data }) => {
              this.$modal.msgSuccess('提交成功')
              this.open = false
              this.$emit('getList')
            })
          }
        }
      })
    },
  },
}
</script>
<style lang="scss"></style>
