<!-- 备单-编辑  开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column prepare-order-edit">
    <!-- <el-dialog title="备单管理" :visible.sync="open" width="1780px" append-to-body :close-on-click-modal="false" @close="cancel"> -->
    <!-- 订单信息 -->
    <el-form :model="orderData" ref="queryForm" size="small" :inline="true" label-width="120px" class="orderDataList" v-loading="loading">
      <el-form-item label="订单号" prop="ordernum">
        <el-input :value="orderData.ordernum" style="width: 230px" readonly></el-input>
      </el-form-item>
      <el-form-item label="客户单位名称" prop="orgName">
        <el-input :value="orderData.peisorgreservation ? orderData.peisorgreservation.orgName : orderData.orgName" style="width: 230px" readonly></el-input>
      </el-form-item>
      <el-form-item label="体检团体类型" prop="idOrgclass">
        <el-input
          :value="(orderData.peisorgreservation && orderData.peisorgreservation.idOrgclass == 0) || orderData.idOrgclass == 0 ? '普通客户' : (orderData.peisorgreservation && orderData.peisorgreservation.idOrgclass == 1) || orderData.idOrgclass == 1 ? 'vip客户' : '流失客户'"
          style="width: 230px"
          readonly
        ></el-input>
      </el-form-item>
      <el-form-item label="销售人员" prop="saleName">
        <el-input :value="orderData.saleName" style="width: 230px" readonly></el-input>
      </el-form-item>
      <el-form-item label="已结束" prop="FFinished">
        <el-checkbox :value="orderData.FFinished == 1" border style="width: 230px"></el-checkbox>
      </el-form-item>
      <!-- <div></div> -->
      <el-form-item label="任务预定日期" prop="datereservation">
        <el-input :value="orderData.peisorgreservation ? (orderData.peisorgreservation.datereservation ? orderData.peisorgreservation.datereservation.split(' ')[0] : '') : orderData.datereservation ? orderData.datereservation.split(' ')[0] : ''" style="width: 230px" readonly></el-input>
      </el-form-item>
      <el-form-item label="计划结束日期" prop="planenddate">
        <el-input :value="orderData.peisorgreservation ? (orderData.peisorgreservation.planenddate ? orderData.peisorgreservation.planenddate.split(' ')[0] : '') : orderData.planenddate ? orderData.planenddate.split(' ')[0] : ''" style="width: 230px" readonly></el-input>
      </el-form-item>
      <el-form-item label="预计人数" prop="countexaminee">
        <el-input :value="orderData.peisorgreservation ? orderData.peisorgreservation.countexaminee : orderData.countexaminee" style="width: 230px" readonly></el-input>
      </el-form-item>
      <el-form-item label="团体联系方式" prop="phone">
        <el-input :value="orderData.peisorgreservation ? orderData.peisorgreservation.phone : orderData.phone" style="width: 230px" readonly></el-input>
      </el-form-item>
      <el-form-item label="执行市价" prop="isMarket">
        <el-checkbox :value="orderData.isMarket == 1" border style="width: 230px"></el-checkbox>
      </el-form-item>
      <!-- <div></div> -->
      <el-form-item label="团体地址" prop="address" style="margin-bottom: 0">
        <el-input :value="orderData.peisorgreservation ? orderData.peisorgreservation.address : orderData.address" type="textarea" style="width: 590px" readonly></el-input>
      </el-form-item>
      <el-form-item label="前台须知" prop="qtxz" style="margin-bottom: 0">
        <el-input :value="orderData.peisorgreservation ? orderData.peisorgreservation.qtxz : orderData.qtxz" type="textarea" style="width: 590px" readonly></el-input>
      </el-form-item>
      <el-form-item label="分中心" prop="phone">
        <el-input :value="orderData.branchName ? orderData.branchName.join(',') : orderData.branchName" style="width: 230px" readonly></el-input>
      </el-form-item>
    </el-form>
    <!-- 左右容器 -->
    <el-row style="min-height: 0; padding: 0 20px; flex: 1; overflow: hidden" type="flex">
      <!-- 左侧 -->
      <edit-left-item :idExamsuite="orderId" :isOnline="isOnline" :isOnlineTB="isOnlineTB"  @getDetails="getDetails"></edit-left-item>
      <!-- 右侧 -->
      <edit-right-item :nationList="nationList" :orderId="orderId" :orderName="orderName" :fzxOptions="fzxOptions"> </edit-right-item>
    </el-row>

    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="dialogOpen" width="470px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="序列号" prop="serialNo">
          <el-input v-model="form.serialNo" placeholder="请输入症状代码" />
        </el-form-item>
        <el-form-item label="检查结论名称" prop="occupationSummary">
          <el-input v-model="form.occupationSummary" placeholder="请输入检查结论名称" @input="nameChange" />
        </el-form-item>
        <el-form-item label="打印中文简称" prop="printForShort">
          <el-input v-model="form.printForShort" placeholder="请输入打印中文简称" />
        </el-form-item>
        <el-form-item label="输入码" prop="inputCode">
          <el-input v-model="form.inputCode" placeholder="输入检查结论名称后自动生成" disabled />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel2">取 消</el-button>
      </div>
    </el-dialog>
    <!-- </el-dialog> -->
  </div>
</template>

<script>
import Cookies from 'js-cookie'
import { editApi, getNationData } from '@/api/reception/prepare_order.js'
import { getBranchData } from '@/api/subscribe/appointment_details/appointment_details'
import editLeftItem from './edit_left.vue'
import editRightItem from './edit_right.vue'
import pinyin from '@/utils/pinyin.js'
export default {
  name: 'PrepareOrderEdit',
  components: {
    editLeftItem,
    editRightItem,
  },
  data() {
    return {
      // 订单id
      orderId: undefined,
      // 订单名称
      orderName: undefined,
      // 弹窗是否显示
      open: false,
      // 加载中
      loading: true,
      // 订单数据
      orderData: {
        ddh: undefined,
        orgName: undefined,
        idOrgclass: undefined,
        idSalesperson: undefined,
        FFinished: false,
        datereservation: undefined,
        planenddate: undefined,
        countexaminee: undefined,
        phone: undefined,
        isMarket: false,
        address: undefined,
        qtxz: undefined,
      },

      // ******************内部添加修改对话框
      dialogOpen: false,
      // 标题
      title: '',
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 民族列表
      nationList: [],
      // 分中心列表
      fzxOptions: [],
      // 是否为线上
      isOnline: false,
      // 线下要求线上按钮
      isOnlineTB:false,
    }
  },
  created() {
    this.orderId = this.$route.query.orderId
    this.orderName = this.$route.query.orderName
    this.isOnline = this.$getCookie('isOnline') == 1 ? true : false
    // 如果是淮南或者霸州,则显示保存和新增
    if(Cookies.get('cid') == '402848e36b551aab016b5df90c8412e8' || Cookies.get('cid') == '402848e36c2cf334016c32a3e73320d1'){
      this.isOnlineTB = true
    }
    this.getDetails()
    getNationData().then(({ data }) => {
      this.nationList = data
    })
    getBranchData().then(({ data }) => {
      this.fzxOptions = data
    })
  },
  methods: {
    getDetails() {
      // this.orderId = id
      // this.open = true
      this.loading = true
      this.bus.$emit('handlePeopleData')
      editApi({ id: this.orderId })
        .then(({ data }) => {
          this.orderData = data
          this.loading = false
          this.bus.$emit('getLeftData', data)
          this.bus.$emit('handleDataId', data.id)
        })
        .catch(() => {
          this.loading = false
        })
    },

    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },

    // 编辑
    handleUpdate() {
      this.reset()
      this.dialogOpen = true
      this.title = '编辑'
    },
    // 表单重置
    reset() {
      this.form = {
        serialNo: null,
        occupationSummary: null,
        printForShort: null,
        inputCode: null,
      }
      this.resetForm('form')
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
      this.$emit('getList')
    },
    // 输入名称自动生成代码
    nameChange(value) {
      this.form.inputCode = pinyin(value)
    },
    // 提交按钮
    submitForm: function () {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            // this.form.menuIds = this.getMenuAllCheckedKeys();
            // updateRole(this.form).then(response => {
            this.$modal.msgSuccess('编辑成功')
            this.dialogOpen = false
            //   this.getList();
            // });
          } else {
            // this.form.menuIds = this.getMenuAllCheckedKeys();
            // addRole(this.form).then(response => {
            this.$modal.msgSuccess('添加成功')
            this.dialogOpen = false
            //   this.getList();
            // });
          }
        }
      })
    },
    // 内部对话框取消按钮
    cancel2() {
      this.dialogOpen = false
      this.reset()
    },
  },
}
</script>

<style lang="scss">
.prepare-order-edit {
  overflow: auto;
  table .cell {
    color: black !important;
  }
}
.table-main {
  height: 100%;
  border: 1px solid #d4d6d9;
}
</style>
