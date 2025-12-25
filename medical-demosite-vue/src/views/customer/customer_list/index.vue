<!-- 客户管理  开发人：麦沃德科技 半夏/予安/矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="客户单位名称" prop="khdwmc">
        <el-input v-model="queryParams.khdwmc" placeholder="请输入客户单位名称" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="客户单位输入码" prop="khdwsrm">
        <el-input v-model="queryParams.khdwsrm" placeholder="请输入客户单位输入码" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="客户单位团体号" prop="intId">
        <el-input v-model="queryParams.intId" placeholder="请输入客户单位团体号" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['customer:customerList:add']">增加</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit" :disabled="single" @click="handleUpdate" v-hasPermi="['customer:customerList:edit']">编辑 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['customer:customerList:remove']">删除 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-view" :disabled="single" @click="handleView" v-hasPermi="['customer:customerList:view']">查看 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-download" :disabled="single" @click="handleDownload" v-hasPermi="['customer:customerList:download']">下载 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-download" @click="handleMouldDownload" v-hasPermi="['customer:customerList:mould']">导入模板下载 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-download" @click="handleImport" v-hasPermi="['customer:customerList:import']">导入 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['customer:customerList:export']">导出 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-user" :disabled="single" @click="handleFollow" v-hasPermi="['customer:customerList:follow']">客户跟进 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-unlock" :disabled="multiple" @click="handleRelease" v-hasPermi="['customer:customerList:release']">客户释放 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-remove-outline" :disabled="multiple" @click="handleLoss" v-hasPermi="['customer:customerList:loss']">客户流失 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-data-line" :disabled="single" @click="handleUpgrade" v-hasPermi="['customer:customerList:upgrade']">客户升级 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-sort" :disabled="multiple" @click="handleTransfer" v-hasPermi="['customer:customerList:transfer']">客户转移 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-refresh-left" :disabled="multiple" @click="handleReset" v-hasPermi="['customer:customerList:reset']">密码重置 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-document-add" :disabled="multiple" @click="handleCreate" v-hasPermi="['customer:customerList:create']">生成账号 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-aim" @click="handleTest" v-hasPermi="['customer:customerList:test']">检验匹配团体金蝶名 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-refresh" @click="handleRefresh" v-hasPermi="['customer:customerList:update']">金蝶客户数据更新 </el-button>
      </el-col>
      <!-- 合同管理跳转链接 -->
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-position" @click="salesContract">销售合同</el-button>
      </el-col>
      <!-- 订单制定跳转链接 -->
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-position" @click="createCombo">创建套餐</el-button>
      </el-col>
      <!-- 订单制定跳转链接 -->
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-position" @click="orderCreate">订单制定</el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border ref="tableList" v-loading="loading" @row-click="rowClick" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="客户单位名称" prop="khdwmc" min-width="200" align="center" show-overflow-tooltip />
        <el-table-column label="客户单位输入码" prop="khdwsrm" min-width="160" align="center" show-overflow-tooltip />
        <el-table-column label="客户单位联系人" prop="khdwlxr" min-width="140" align="center" show-overflow-tooltip />
        <el-table-column label="客户电话" prop="khdh" min-width="140" align="center" show-overflow-tooltip />
        <el-table-column label="销售经理" prop="xsjl" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="单位机构代码" prop="dwjgdm" min-width="160" align="center" show-overflow-tooltip />
        <el-table-column label="所属行业" prop="sshy" align="center" min-width="140" show-overflow-tooltip />
        <el-table-column label="客户单位注册地址" prop="khdwzcdz" min-width="200" align="center" show-overflow-tooltip />
        <el-table-column label="备注" prop="bz" min-width="160" align="center" show-overflow-tooltip />
        <el-table-column label="文件个数" prop="wjgs" min-width="100" align="center" show-overflow-tooltip />
        <el-table-column label="客户状态" prop="khzt" min-width="100" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="warning" v-if="scope.row.khzt == 0">潜在</el-tag>
            <el-tag type="success" v-else-if="scope.row.khzt == 1">正式</el-tag>
            <el-tag type="danger" v-else-if="scope.row.khzt == 2">释放</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="团体ID" prop="intId" min-width="100" align="center" show-overflow-tooltip />
        <el-table-column label="账号" prop="username" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="上传状态" prop="jinanStatus" min-width="120" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.jinanStatus == 0">上传</span>
            <span v-else-if="scope.row.jinanStatus == -1">未上传</span>
            <span v-else></span>
          </template>
        </el-table-column>
        <el-table-column label="失败信息" prop="jinanMsg" min-width="120" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <upload-items ref="uploadItems"></upload-items>
    <!-- 添加或修改对话框 -->
    <add-items ref="addItems" @getList="getList"> </add-items>
    <!-- 客户转移 -->
    <transfer-items ref="transferItems" @getList="getList"></transfer-items>
    <!-- 查看页面 -->
    <handleView ref="handleView"></handleView>
    <!-- 升级对话框组件 -->
    <upgrade-dialog ref="upgradeDialog"></upgrade-dialog>
    <!-- 填写跟进记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" class="index-customer" width="458px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px" hide-required-asterisk>
        <el-form-item label="客户单位名称" prop="khdwmc">
          <input-select :selectData="selectData" v-model="form.khdwmc" selectWidth="308" @change="selectChange"></input-select>
        </el-form-item>
        <el-form-item label="主题" prop="zt">
          <el-input v-model="form.zt" placeholder="请输入" clearable style="width: 308px" />
        </el-form-item>
        <el-form-item label="客户负责人" prop="khdwlxr">
          <el-input v-model="form.khdwlxr" placeholder="请输入" :readonly="true" clearable style="width: 308px" />
        </el-form-item>
        <el-form-item label="销售经理" prop="xsjl">
          <el-input v-model="form.xsjl" placeholder="请输入" clearable style="width: 308px" />
        </el-form-item>
        <el-form-item label="跟进日期" prop="gjrq">
          <el-date-picker v-model="form.gjrq" style="width: 308px" :class="{ 'el-date-hover': form.gjrq }" disabled value-format="yyyy-MM-dd" type="date" key="date" placeholder="选择日期"> </el-date-picker>
        </el-form-item>
        <el-form-item label="跟进阶段" prop="gjjd">
          <el-select v-model="form.gjjd" placeholder="请选择" style="width: 308px">
            <el-option v-for="options in stageOptions" :key="options.id" :label="options.text" :value="options.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="阶段结束" prop="jdjs">
          <el-checkbox v-model="form.jdjs" style="width: 308px" border />
        </el-form-item>
        <el-form-item label="跟进内容" prop="gjnr">
          <el-input v-model="form.gjnr" placeholder="请输入跟进内容" type="textarea" :autosize="autosize" clearable style="width: 308px" />
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
import uploadItems from './upload'
import addItems from './add'
import transferItems from './transfer'
import handleView from './handView'
import upgradeDialog from './upgrade'
import { listSellcustomer, delSellcustomer, setBan, upgrade, generateAccount, getDetailData, saveData, isJDHT, upgradeCustomer, checkOrgName, resetPassword } from '@/api/customer/customer_list.js'
export default {
  name: 'Customer_list',
  components: { addItems, uploadItems, transferItems, handleView, upgradeDialog },
  data() {
    return {
      downLoadParams: {
        saveFilePathId: undefined,
      },
      read: undefined,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        khdwmc: undefined,
        khdwsrm: undefined,
        intId: undefined,
      },
      releaseParams: {
        ids: undefined,
        state: undefined,
      },
      //状态
      tyoe: [],
      // 表格数据
      tableList: [],
      // 跟进阶段
      stageOptions: [
        { id: '0', text: '需求交流' },
        { id: '1', text: '方案谈判' },
        { id: '2', text: '价格谈判' },
        { id: '3', text: '体检确认' },
        { id: '4', text: '合同签订' },
      ],
      // 输入码选择
      selectData: {
        placeholder: '请输入客户单位输入码',
        key: '输入码', //第一列标题
        value: '名称', //第二列标题
        url: '/sell/customer/getKhdwmcData', //请求连接
        bindValue: '',
        firstName: 'khdwsrm',
        secondName: 'khdwmc',
      },
      //客户跟进用
      fellowData: undefined,
      //内容数据
      contData: [],
      // 表单参数
      form: {},
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 文本域自适应内容高度参数
      autosize: { minRows: 4, maxRows: 4 },
      // 表单校验
      rules: {
        khdwmcid: [{ required: true, message: '客户单位名称不能为空', trigger: 'change' }],
        khdwlxr: [{ required: true, message: '客户负责人不能为空', trigger: 'change' }],
        xsjl: [{ required: true, message: '销售经理不能为空', trigger: 'change' }],
        gjjd: [{ required: true, message: '跟进阶段不能为空', trigger: 'change' }],
      },
    }
  },
  created() {
    this.getList()
  },
  activated() {
    let khdwmc = this.$route.params.khdwmc
    if (khdwmc) {
      this.queryParams.khdwmc = khdwmc
      this.handleQuery()
    }
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      listSellcustomer(this.queryParams).then(({ data }) => {
        this.tableList = data.records
        this.total = data.total
        this.loading = false
      })
    },
    // 获取当前日期
    getCurrentTime() {
      let currentTime = new Date(),
        year = currentTime.getFullYear(),
        month = currentTime.getMonth() + 1 < 10 ? '0' + (currentTime.getMonth() + 1) : currentTime.getMonth() + 1,
        day = currentTime.getDate() < 10 ? '0' + currentTime.getDate() : currentTime.getDate()
      return year + '-' + month + '-' + day
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 添加
    handleAdd() {
      this.$refs.addItems.handleAdd()
    },
    // 删除
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(function () {
          return delSellcustomer(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => { })
    },
    // 编辑
    handleUpdate(row) {
      const id = row.id || this.ids
      this.$refs.addItems.handleUpdate(id)
    },
    // 查看
    handleView(row) {
      const id = row.id || this.ids
      this.$refs.handleView.lookupDia(id)
    },
    // 下载
    handleDownload() {
      this.downloadZip('/sell/customer/downLoad?id=' + this.ids[0])
    },
    // 导入模板下载
    handleMouldDownload() {
      this.downloadStatic('/static/stencil/customerModel_kh.xlsx', '我的客户导入模板.xlsx')
    },
    // 导出
    handleExport() {
      this.download(
        '/sell/customer/export',
        {
          ...this.queryParams,
        },
        `我的客户.xlsx`
      )
    },
    // 导入
    handleImport() {
      this.$refs.uploadItems.showDialog()
    },
    // 客户跟进
    handleFollow() {
      const query = {
        sellCusId: this.ids[0],
      }
      getDetailData(query).then((response) => {
        this.form.gjrq = this.getCurrentTime()
        this.form = response.data
        this.selectData.bindValue = this.form.khdwmc
        if (this.form.gjrq == undefined) {
          this.form.gjrq = this.getCurrentTime()
        }
      })

      this.open = true
      this.title = '填写跟进记录'
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 客户释放
    handleRelease() {
      this.releaseParams.state = 1
      for (var i in this.ids) {
        if (i == 0) this.releaseParams.ids = this.ids[i]
        else this.releaseParams.ids += ',' + this.ids[i]
      }

      this.$confirm('确定释放选中的记录？', '提醒', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          setBan(this.releaseParams).then((response) => {
            this.$modal.msgSuccess('操作成功')
            this.getList()
          })
        })
        .catch(() => { })
    },
    // 客户流失
    handleLoss() {
      this.releaseParams.state = 0
      for (var i in this.ids) {
        if (i == 0) this.releaseParams.ids = this.ids[i]
        else this.releaseParams.ids += ',' + this.ids[i]
      }
      this.$confirm('确定流失选中的记录？', '提醒', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          setBan(this.releaseParams).then((response) => {
            this.$modal.msgSuccess('操作成功')
            this.getList()
          })
        })
        .catch(() => { })
    },
    // 客户升级
    handleUpgrade() {
      for (var i in this.ids) {
        if (i == 0) this.releaseParams.ids = this.ids[i]
        else this.releaseParams.ids += ',' + this.ids[i]
      }
      const query = {
        isZskhId: this.releaseParams.ids,
      }
      // 检查是否签过合同
      isJDHT(query).then((response) => {
        if (response.data == '' || response.data == null || response.data == undefined) {
          this.$refs.upgradeDialog.show(
            this.handleUpgradeSuccess.bind(this),
            this.handleUpgradeError.bind(this)
          )
        } else {
          this.$alert(`${response.data}`, {
            dangerouslyUseHTMLString: true,
          })
        }
      }).catch((err) => {
        console.error('检查合同状态失败:', err)
        this.$message.error('检查合同状态失败，请稍后重试')
      })
    },
    // 升级成功回调
    handleUpgradeSuccess(formData) {
      this.$modal.msgWarning('操作中,请等待')
      this.loading = true
      upgrade({
        ...this.releaseParams,
        centerorgname: formData.centerorgname,
      }).then((response) => {
        this.loading = false
        this.getList()
        this.$modal.msgSuccess('操作成功')
        this.$confirm('是否前往创建套餐?', '提示')
          .then(() => {
            this.$router.push({ name: 'Create_offer' })
          })
          .catch(() => { })
      }).catch((err) => {
        this.loading = false
        console.error('升级失败:', err)
        this.$message.error('升级失败，请稍后重试')
      })
    },
    // 升级失败回调
    handleUpgradeError(error) {
      console.error('升级过程中出现错误:', error)
    },
    // 客户转移
    handleTransfer() {
      for (var i in this.type) {
        if (this.type[i] != 1) {
          this.$modal.msgWarning('请选择正式客户进行转移')
          break
        } else {
          this.$refs.transferItems.handleShow(this.ids)
        }
      }
    },
    //前往销售合同
    salesContract() {
      this.$router.push({ name: 'Contract_management' })
    },
    //前往创建套餐
    createCombo() {
      this.$router.push({ name: 'Create_offer' })
    },
    //前往订单制定
    orderCreate() {
      this.$router.push({ name: 'Order_customization' })
    },
    // 密码重置
    handleReset() {
      this.$confirm('确定要重置所选客户的密码吗？', '提醒', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          resetPassword(this.ids).then(() => {
            this.$modal.msgSuccess('操作成功')
          })
        })
        .catch(() => { })
    },
    // 生成账号
    handleCreate() {
      // for (var i in this.ids) {
      //   if (i == 0) this.releaseParams.ids = this.ids[i]
      //   else this.releaseParams.ids += ',' + this.ids[i]
      // }
      this.$confirm('确定要为所选客户生成网站账号吗？', '提示', {
        type: 'warning',
      })
        .then(() => {
          let err = ''
          for (var i = 0; i < this.contData.length; i++) {
            if (this.contData[i].khzt != 1) {
              err = '请选择正式客户'
            }
          }
          if (err) {
            this.$modal.msgWarning(err)
            return
          }
          this.releaseParams.ids = this.ids
          generateAccount(this.ids).then(() => {
            this.$modal.msgSuccess('已经为选中用户生成账号')
          })
        })
        .catch(() => { })
    },
    // 检验匹配团体金蝶名
    handleTest() {
      checkOrgName().then((res) => {
        this.$modal.msgSuccess(res.data)
      })
    },
    // 金蝶客户数据更新
    handleRefresh() {
      upgradeCustomer().then((res) => {
        this.$modal.msgSuccess(res.data)
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.fellowData = selection[0]
      this.contData = selection
      this.ids = selection.map((item) => item.id)
      this.type = selection.map((item) => item.khzt)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 输入码选择
    selectChange(value) {
      this.form.khdwmc = value.khdwmc
      this.form.khdwlxr = value.khdwlxr
      this.selectData.bindValue = value.khdwmc
    },
    // 表单重置
    reset() {
      this.form = {
        khdwmcid: undefined,
        zt: undefined,
        khdwlxr: undefined,
        xsjl: undefined,
        gjrq: this.getCurrentTime(),
        gjjd: undefined,
        jdjs: undefined,
        gjnr: undefined,
      }
      this.selectData.bindValue = undefined
      this.resetForm('form')
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 提交按钮
    submitForm: function () {
      this.form.gjrq += ' 00:00:00'
      if (this.form.jdjs == undefined) {
        this.form.jdjs = false
      }
      this.form.khdwmcid = this.form.id
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            saveData(this.form).then((response) => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            saveData(this.form).then((response) => {
              this.$modal.msgSuccess('添加成功')
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
.index-customer {
  .el-date-editor {
    &.el-date-hover:hover .el-input__prefix {
      display: none;
    }

    &.is-disabled .el-input__prefix {
      display: inline-block !important;
    }

    .el-input__inner {
      padding-left: 8px;
    }

    .el-input__prefix {
      left: auto;
      right: 5px;
    }
  }
}
</style>
