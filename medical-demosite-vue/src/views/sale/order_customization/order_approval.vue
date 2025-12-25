<!-- 订单审批弹窗  开发人：麦沃德科技 矢北/予安 -->
<template>
  <el-dialog :title="title" :visible.sync="open" style="min-height: 808px" :width="isApproval ? '1200px' : '800px'" append-to-body :close-on-click-modal="false">
    <div style="display: flex">
      <div style="width: 800px">
        <el-row :gutter="10" class="mb8" style="margin-bottom: 5px">
          <el-col :span="1.5">
            <el-button type="success" plain size="mini" icon="el-icon-download" @click="handleDownload">下载名单</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="primary" plain size="mini" icon="el-icon-edit" @click="handleCombo">查看套餐</el-button>
          </el-col>
          <el-col :span="1.5" v-if="showDownloadCL">
            <el-button type="primary" plain size="mini" icon="el-icon-download" @click="handleDownload2">下载材料</el-button>
          </el-col>
        </el-row>
        <el-form ref="form" :model="form" :inline="true" label-width="120px" hide-required-asterisk v-loading="loading">
          <el-form-item label="订单名称" prop="ddmc">
            <el-input v-model="form.ddmc" style="width: 220px" readonly />
          </el-form-item>
          <el-form-item label="客户单位名称" prop="khdwmc">
            <el-input v-model="form.khdwmc" style="width: 220px" readonly />
          </el-form-item>
          <el-form-item label="提醒方式" prop="txfs">
            <el-input :value="form.txfs == 0 ? '首页提醒' : '短信提醒'" readonly style="width: 220px" />
          </el-form-item>
          <el-form-item label="销售经理" prop="xsjl">
            <el-input v-model="form.xsjl" readonly style="width: 220px" />
          </el-form-item>
          <el-form-item label="订单价格" prop="ddjg">
            <el-input v-model="form.ddjg" readonly style="width: 220px" />
          </el-form-item>
          <el-form-item label="分中心" prop="fzx">
            <el-input v-model="form.fzx" readonly style="width: 220px" />
          </el-form-item>
          <el-form-item label="计划检期从" prop="jhjqc" class="red-label">
            <el-input v-model="form.jhjqc" readonly style="width: 220px" />
          </el-form-item>
          <el-form-item label="计划检期到" prop="jhjqd" class="red-label">
            <el-input v-model="form.jhjqd" readonly style="width: 220px" />
          </el-form-item>
          <el-form-item label="男性体检人数" prop="nxtjrs">
            <el-input v-model="form.nxtjrs" readonly style="width: 220px" />
          </el-form-item>
          <el-form-item label="女性体检人数" prop="vxtjrs">
            <el-input v-model="form.vxtjrs" readonly style="width: 220px" />
          </el-form-item>
          <el-form-item label="订单折扣" prop="ddzk">
            <el-input v-model="form.ddzk" readonly style="width: 220px" />
          </el-form-item>
          <el-form-item label="订单优惠价" prop="ddyhj">
            <el-input v-model="form.ddyhj" readonly style="width: 220px" />
          </el-form-item>
          <el-form-item label="变动成本率" prop="varCostRate">
            <el-input v-model="form.varCostRate" readonly style="width: 220px" />
          </el-form-item>
          <el-form-item label="既往订单平均折扣及变动成本率" prop="a">
            <el-input v-model="form.orderHistoryRates" readonly style="width: 570px" type="textarea" :rows="3" />
          </el-form-item>
          <el-form-item label="前台须知" prop="qtxz">
            <el-input v-model="form.qtxz" readonly style="width: 570px" type="textarea" :rows="5" />
          </el-form-item>
          <el-form-item label="变更备注" prop="bgmemo" v-if="showAlter">
            <el-input v-model="form.bgmemo" readonly style="width: 570px" type="textarea" :rows="3" />
          </el-form-item>
          <el-form-item label="审批意见" prop="spyj" v-if="!isApproval">
            <el-input v-model="form.spyj" placeholder="请输入审批意见" style="width: 570px" type="textarea" :rows="3" />
          </el-form-item>
        </el-form>
      </div>
      <div v-if="isApproval" class="approval-right">
        <div class="approval-right-item" v-for="(item, index) in approvalList" :key="index">
          <div class="item-index" :style="index == approvalList.length - 1 ? 'background-color: #fff;' : ''">
            <div class="index-num" :style="{ 'background-color': item.status == 0 ? 'grey' : item.status == 1 ? 'orange' : item.status == 2 ? 'green' : 'red' }">{{ index + 1 }}</div>
          </div>
          <div class="item-info">
            <div class="item-info-top">
              <div class="top-name">{{ item.itemName + ': ' + item.userName }}</div>
              <div style="flex: 1">
                <span class="state" :style="{ 'background-color': item.status == 0 ? 'grey' : item.status == 1 ? 'orange' : item.status == 2 ? 'green' : 'red' }">{{ ['等待开始', '进行中', '已通过', '被驳回'][item.status] }}</span>
              </div>
              <template v-if="item.status == 1 && !readOnly && userNo == item.userNo">
                <div class="options-btn blue" @click="handlePass(1, item)">通过</div>
                <div class="options-btn red" @click="handlePass(2, item)">驳回</div>
              </template>
            </div>
            <el-input type="textarea" v-model="item.remark" :readonly="item.status != 1" placeholder="处理意见:" :rows="3"></el-input>
          </div>
        </div>
      </div>
    </div>
    <div slot="footer" class="dialog-footer">
      <template v-if="!isApproval">
        <el-button type="primary" @click="handleExam(0)">通过</el-button>
        <el-button type="primary" plain @click="handleExam(1)">不通过</el-button>
      </template>
      <el-button @click="cancel">关 闭</el-button>
    </div>

    <!-- 查看套餐 -->
    <queryItems ref="queryItems"></queryItems>
  </el-dialog>
</template>

<script>
import { getfzxList, checkOrder, approveChange } from '@/api/sale/order_customization.js'
import { questDetails, updateItemStatus } from '@/api/approval/approval_quest.js'

import queryItems from './query.vue'
export default {
  components: { queryItems },
  props: ['isApproval', 'readOnly'],
  data() {
    return {
      title: '',
      orderCheckId: undefined,
      // 是否为变更
      fchange: 0,
      // 打开对话框
      open: false,
      // 加载中
      loading: false,
      // 弹框表单
      form: {},
      // 表单校验
      // rules: {
      //   spyj: [{ required: true, message: "审批意见不能为空", trigger: "blur" }]
      // }
      // 审批流id
      approvalId: '',
      // 审批信息列表
      approvalList: [],
      // 用户id
      userNo: '',
      // 展示变更原因
      showAlter: false,
      // 展示下载材料按钮
      showDownloadCL: false,
    }
  },
  methods: {
    showDialog(id, fchange = 0, approvalList, approvalId) {
      this.userNo = this.$getCookie('userNo')
      this.fchange = fchange
      this.orderCheckId = id
      this.loading = true
      if (fchange == 1) {
        this.title = '变更审批'
        this.showAlter = true
      } else {
        this.title = '订单审批'
        this.showAlter = false
      }
      this.open = true
      if (approvalList) {
        this.approvalList = approvalList
      }
      if (approvalId) this.approvalId = approvalId
      approveChange({ id })
        .then(({ data }) => {
          this.form = data
          this.form.fzxid = this.form.fzxid.split(',')
          this.form.jhjqc = this.form.jhjqc ? this.form.jhjqc.split(' ')[0] : ''
          this.form.jhjqd = this.form.jhjqd ? this.form.jhjqd.split(' ')[0] : ''
          this.form.varCostRate = this.form.varCostRate ? (Number(this.form.varCostRate) * 100).toFixed(2) + '%' : ''
          this.handleFzxlist()
          this.showDownloadCL = false
          if (this.form.tjlx != 1) {
            this.approvalList.forEach((el) => {
              if (el.userNo == this.userNo && el.isTotal == 1) {
                this.showDownloadCL = true
              }
            })
          }
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 获取审批流程
    getApprovalDetails() {
      questDetails(this.approvalId)
        .then((res) => {
          // this.form = res.data
          this.approvalList = res.data.itemList
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 获取分中心列表
    handleFzxlist() {
      getfzxList({
        current: 1,
        size: 99999,
      })
        .then(({ data }) => {
          let fzx = ''
          this.form.fzxid.forEach((val) => {
            data.records.forEach((el) => {
              if (val == el.id) {
                fzx += el.fzx + '、'
              }
            })
          })
          this.form.fzx = fzx.substring(0, fzx.length - 1)
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    //通过审核
    handleExam(spjgValue) {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.$confirm(spjgValue == 0 ? '确定审核通过该订单吗？' : '确定不通过该订单吗？', '提示', {
            type: 'warning',
          })
            .then(() => {
              let query = {
                orderCheckId: this.orderCheckId,
                spjgValue,
                spyjValue: this.form.spyj,
                fchange: this.fchange,
              }
              this.loading = true
              checkOrder(query)
                .then(() => {
                  this.loading = false
                  this.cancel()
                  this.$modal.msgSuccess('审核已完成')
                  this.$emit('getList')
                })
                .catch(() => {
                  this.loading = false
                })
            })
            .catch(() => {})
        }
      })
    },
    // 审批流通过
    handlePass(type, item) {
      this.$confirm('是否执行 ' + (type == 1 ? '通过' : '驳回') + ' 操作?', '提示')
        .then(() => {
          let query = {
            caseId: item.caseId,
            itemId: item.itemId,
            remark: item.remark || '无',
            status: type == 1 ? 2 : 3,
          }
          updateItemStatus(query)
            .then((res) => {
              this.$modal.msgSuccess('操作成功 ')
              this.$emit('getList')
              this.getApprovalDetails()
            })
            .catch((error) => {
              console.error(error)
            })
        })
        .catch(() => {})
    },

    // 下载名单
    handleDownload() {
      // 判断名单状态是否上传
      this.download('system/post/export', this.queryParams, `岗位列表.xlsx`)
    },
    // 下载材料
    handleDownload2() {
      this.downloadZip('/sell/createorder/downCl?id=' + this.form.id)
    },
    //查看套餐
    handleCombo() {
      this.$refs.queryItems.queryWindow(this.form.id)
    },
    // 关闭对话框
    cancel() {
      this.open = false
    },
  },
}
</script>

<style lang="scss">
.red-label .el-form-item__label {
  color: red;
}
.approval-right {
  overflow-y: auto;
  width: 390px;
  max-height: 800px;
  margin-left: 10px;
  .approval-right-item {
    display: flex;
    .item-index {
      width: 1px;
      margin: 0 20px;
      background-color: #000;
      .index-num {
        width: 25px;
        height: 25px;
        line-height: 25px;
        color: #fff;
        font-size: 16px;
        text-align: center;
        border-radius: 50%;
        background-color: green;
        transform: translateX(-50%);
      }
    }
    .item-info {
      flex: 1;
      margin-bottom: 8px;
      .item-info-top {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 8px;
        .top-name {
          font-size: 16px;
          color: #000;
        }
        .state {
          flex: 1;
          padding: 2px 7px;
          margin-left: 8px;
          color: #fff;
          background-color: green;
          border-radius: 15px;
        }
        .options-btn {
          padding: 2px 10px;
          margin-left: 8px;
          color: #fff;
          border-radius: 5px;
          cursor: pointer;
          &.blue {
            background-color: rgb(22, 155, 213);
          }
          &.red {
            background-color: red;
          }
        }
      }
    }
  }
}
</style>
