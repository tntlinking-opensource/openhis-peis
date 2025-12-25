<!-- 超量审批弹窗  开发人：麦沃德科技 予安 -->
<template>
  <el-dialog title="超量审批" :visible.sync="open" style="min-height: 808px" width="1200px" append-to-body :close-on-click-modal="false">
    <div style="display: flex">
      <div style="width: 800px">
        <el-form ref="form" :model="form" :inline="true" label-width="120px" hide-required-asterisk v-loading="loading" v-if="isApproval">
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
          <el-form-item label="订单折扣" prop="ddzk">
            <el-input v-model="form.ddzk" readonly style="width: 220px" />
          </el-form-item>
          <el-form-item label="订单优惠价" prop="ddyhj">
            <el-input v-model="form.ddyhj" readonly style="width: 220px" />
          </el-form-item>
          <el-form-item label="预约类型">
            <el-input v-model="dataInfo.levelName" readonly style="width: 220px" />
          </el-form-item>
          <el-form-item label="超量人数">
            <el-input v-model="dataInfo.countAm" readonly style="width: 220px" />
          </el-form-item>
          <el-form-item label="预约日期">
            <el-date-picker v-model="dataInfo.reservationDate" type="date" readonly value-format="yyyy-MM-dd" style="width: 220px"> </el-date-picker>
          </el-form-item>
          <el-form-item label="提交人">
            <el-input v-model="dataInfo.creator" readonly style="width: 220px" />
          </el-form-item>
        </el-form>
        <!-- <el-table ref="tableList" style="width: 100%" border :data="tableList" v-loading="loading" height="300px">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="序列" width="55" type="index" align="center" />
          <el-table-column prop="idcardno" label="人数上限（上午）" align="center" />
          <el-table-column prop="idcardno" label="人数上限（下午）" align="center" />
          <el-table-column prop="idcardno" label="已预约人数（上午）" align="center" />
          <el-table-column prop="idcardno" label="已预约人数（下午）" align="center" />
          <el-table-column prop="idcardno" label="剩余可约人数（上午）" align="center" />
          <el-table-column prop="idcardno" label="剩余可约人数（下午）" align="center" />
        </el-table> -->
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
              <template v-if="item.status == 1 && userNo == item.userNo">
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
      <el-button @click="open = false">关 闭</el-button>
    </div>

    <!-- 查看套餐 -->
    <!-- <queryItems ref="queryItems"></queryItems> -->
  </el-dialog>
</template>

<script>
import { approveChange } from '@/api/sale/order_customization.js'
import { questDetails, updateItemStatus } from '@/api/approval/approval_quest.js'

// import queryItems from './query.vue'
export default {
  // components: { queryItems },
  props: ['isApproval'],
  data() {
    return {
      orderCheckId: undefined,
      // 是否为变更
      fchange: 0,
      // 打开对话框
      open: false,
      // 加载中
      loading: false,
      // 订单信息
      orderInfo: {},
      // 超量信息列表
      dataInfo: {},
      // 备注
      remark: '',
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
    }
  },
  methods: {
    // 打开对话框
    showDialog(orderId, orderName, tableList, data) {
      this.userNo = this.$getCookie('userNo')
      this.orderInfo.orderId = orderId
      this.orderInfo.orderName = orderName
      this.open = true
      if (this.isApproval) {
        this.approvalId = data.id
        this.dataInfo = data.dataObject
        this.approvalList = data.itemList
        this.remark = data.remark
        approveChange({ id: data.bizId }).then((res) => {
          this.form = res.data
        })
        // this.getApprovalDetails()
      }
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
