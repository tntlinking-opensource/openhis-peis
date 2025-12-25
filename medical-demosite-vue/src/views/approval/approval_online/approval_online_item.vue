<!-- 线上审批  开发人：麦沃德科技 予安 -->
<template>
  <div ref="top" class="approval-online-item">
    <div class="title" @click="toOrderList"><i class="el-icon-arrow-left"></i>{{ typeFlag.includes('ORDER_FLOW') ? '订单审批' : typeFlag == 'OVER_RESERVATION' ? '跨级预约审批' : typeFlag == 'EXCESS_RESERVATION' ? '超量预约审批' : '' }}</div>
    <div style="flex: 1; overflow: auto; padding-top: 50px">
      <div style="display: flex; justify-content: space-between; align-items: center">
        <div class="center">分中心: {{ orderInfo.fzx }}</div>
        <div style="margin-right: 16px">
          <el-button type="primary" @click="handleCheck" v-if="typeFlag.includes('ORDER_FLOW')">查看套餐</el-button>
        </div>
      </div>
      <div class="submit-name">提交人: {{ orderInfo.xsjl }} / {{ orderInfo.xsdh }}</div>
      <el-divider></el-divider>
      <div class="order-info">
        <div class="info-item1">订单名称</div>
        <div class="info-item2">{{ orderInfo.ddmc }}</div>
      </div>
      <div class="order-info">
        <div class="info-item1">订单号</div>
        <div class="info-item2">{{ orderInfo.ddh }}</div>
      </div>
      <div class="order-info">
        <div class="info-item1">客户单位名称</div>
        <div class="info-item2">{{ orderInfo.khdwmc }}</div>
      </div>
      <template v-if="typeFlag.includes('ORDER_FLOW')">
        <el-divider></el-divider>
        <div class="order-info">
          <div class="info-item1">计划检期</div>
          <div class="info-item2">{{ orderInfo.jhjqc ? orderInfo.jhjqc.slice(0, 10) : '' }} ~ {{ orderInfo.jhjqd ? orderInfo.jhjqd.slice(0, 10) : '' }}</div>
        </div>
        <el-divider></el-divider>
        <div class="order-info">
          <div class="info-item1">体检形式</div>
          <div class="info-item2">{{ ['内检', '外检', '内检加外检'][orderInfo.tjxs] }}</div>
        </div>
        <el-divider></el-divider>
        <div class="order-info">
          <div class="info-item1">人数</div>
          <div class="info-item2">男: {{ orderInfo.nxtjrs }}人, 女: {{ orderInfo.vxtjrs }}人</div>
        </div>
        <el-divider></el-divider>
        <div class="order-info">
          <div class="info-item1">体检类型</div>
          <div class="info-item2">{{ ['健康', '职业', '综合'][orderInfo.tjlx] }}</div>
        </div>
        <el-divider></el-divider>
        <div class="order-info" style="display: block">
          <div class="info-item1">既往订单平均折扣及变动成本率</div>
          <!-- <div class="info-item2" style="margin-top: 4px">{{ orderInfo.bgmemo }}</div> -->
          <el-input type="textarea" resize="none" :rows="3" v-model="orderInfo.orderHistoryRates" readonly style="margin-top: 4px"></el-input>
        </div>
        <template v-if="orderInfo.bgmemo">
          <el-divider></el-divider>
          <div class="order-info" style="display: block">
            <div class="info-item1">变更备注</div>
            <!-- <div class="info-item2" style="margin-top: 4px">{{ orderInfo.bgmemo }}</div> -->
            <el-input type="textarea" resize="none" v-model="orderInfo.bgmemo" readonly style="margin-top: 4px"></el-input>
          </div>
        </template>
        <div class="order-price">
          <div class="item">
            <div class="price">{{ orderInfo.ddzk }}</div>
            <div class="name">订单折扣</div>
          </div>
          <div class="item">
            <div class="price">{{ orderInfo.ddyhj }}</div>
            <div class="name">订单优惠价</div>
          </div>
          <div class="item">
            <div class="price">{{ orderInfo.varCostRate ? Math.round(orderInfo.varCostRate * 100) + '%' : '-' }}</div>
            <div class="name">变动成本率</div>
          </div>
        </div>
        <!-- <el-collapse accordion> -->
        <el-collapse>
          <el-collapse-item :name="index + 1" class="accordion-item" v-for="(item, index) in comboInfo" :key="index">
            <template slot="title">
              <div class="combo-title">{{ item.tjtcmc }}</div>
            </template>
            <div class="combo-item">
              <div class="chick-item" v-for="(value, index2) in item.itemData" :key="index2">
                <div class="top">
                  <div class="name" style="width: 60%">{{ value.sfxmmc }}</div>
                  <div class="price">
                    &yen;{{ value.zhjg ? value.zhjg.toFixed(2) : '' }} /
                    <span style="color: gray"
                      >&yen;{{ value.jg }} <span v-if="value.costprice !== null">/ &yen;{{ value.costprice }}</span></span
                    >
                  </div>
                </div>
                <div class="bottom">{{ value.jcyy }}</div>
              </div>
            </div>
            <div class="order-price combo-sum">
              <div class="item">
                <div class="price">{{ item.totalDiscountedPrice ? item.totalDiscountedPrice.toFixed(2) : '0' }}</div>
                <div class="name">优惠价合计</div>
              </div>
              <div class="item">
                <div class="price">{{ item.totalCost }}</div>
                <div class="name">成本合计</div>
              </div>
              <div class="item">
                <div class="price">{{ item.variableCostRate ? (Number(item.variableCostRate) * 100).toFixed(2) : '' }}%</div>
                <div class="name">变动成本率</div>
              </div>
            </div>
          </el-collapse-item>
        </el-collapse>
      </template>
      <template v-else>
        <el-divider></el-divider>
        <div class="order-info">
          <div class="info-item1">销售经理</div>
          <div class="info-item2">{{ orderInfo.kdzlName }}</div>
        </div>
        <el-divider></el-divider>
        <div class="order-info">
          <div class="info-item1">订单价格</div>
          <div class="info-item2">{{ orderInfo.ddjg }}</div>
        </div>
        <el-divider></el-divider>
        <div class="order-info">
          <div class="info-item1">订单折扣</div>
          <div class="info-item2">{{ orderInfo.ddzk }}</div>
        </div>
        <el-divider></el-divider>
        <div class="order-info">
          <div class="info-item1">订单优惠价</div>
          <div class="info-item2">{{ orderInfo.ddyhj }}</div>
        </div>
        <el-divider></el-divider>
        <template v-if="typeFlag == 'OVER_RESERVATION'">
          <div class="order-info">
            <div class="info-item1">备注</div>
            <div class="info-item2">{{ remark }}</div>
          </div>
          <el-divider></el-divider>
          <div class="order-info" style="display: block">
            <div class="info-item1">人员列表</div>
            <el-table ref="memberList" border :data="memberList" style="margin-top: 4px">
              <el-table-column label="姓名" align="center" prop="patientname" />
              <el-table-column label="性别" align="center" width="60">
                <template slot-scope="scope">
                  {{ ['男', '女'][scope.row.idSex] }}
                </template>
              </el-table-column>
              <el-table-column label="类型" align="center" width="60">
                <template slot-scope="scope">
                  <span v-if="scope.row.idPatientclass == 1">vip</span>
                  <span v-else>vvip</span>
                </template>
              </el-table-column>
              <el-table-column label="金额" align="center">
                <template slot-scope="scope"> {{ scope.row.moneyamount }}元 </template>
              </el-table-column>
              <el-table-column label="电话" align="center" prop="phone" width="100" />
            </el-table>
          </div>
        </template>
        <template v-else>
          <div class="order-info">
            <div class="info-item1">预约类型</div>
            <div class="info-item2">{{ dataObject.levelName }}</div>
          </div>
          <el-divider></el-divider>

          <div class="order-info">
            <div class="info-item1">超量人数</div>
            <div class="info-item2">{{ dataObject.countAm }}</div>
          </div>
          <el-divider></el-divider>

          <div class="order-info">
            <div class="info-item1">预约日期</div>
            <div class="info-item2">{{ dataObject.reservationDate }}</div>
          </div>
          <el-divider></el-divider>

          <div class="order-info">
            <div class="info-item1">提交人</div>
            <div class="info-item2">{{ dataObject.creator }}</div>
          </div>
        </template>
      </template>

      <div class="approval-right">
        <div class="approval-right-item" v-for="(item, index) in approvalList" :key="index">
          <div style="display: flex; width: 100%">
            <div class="item-index" :style="index == approvalList.length - 1 ? 'background-color: #fff' : ''">
              <div class="index-num" :style="{ 'background-color': item.status == 0 ? 'grey' : item.status == 1 ? 'orange' : item.status == 2 ? 'green' : 'red' }">{{ index + 1 }}</div>
            </div>
            <div class="item-info">
              <div class="item-info-top">
                <div class="top-name">{{ item.itemName + ': ' + item.userName }}</div>
                <div style="flex: 1">
                  <span class="state" :style="{ 'background-color': item.status == 0 ? 'grey' : item.status == 1 ? 'orange' : item.status == 2 ? 'green' : 'red' }">{{ ['等待开始', '进行中', '已通过', '被驳回'][item.status] }}</span>
                </div>
              </div>
              <el-input type="textarea" v-model="item.remark" :readonly="item.status != 1" placeholder="处理意见:" :rows="3"></el-input>
              <div class="btn-list" v-if="item.status == 1 && userNo == item.userNo && approvalStatus != -1">
                <div class="options-btn red" @click="handlePass(2, item)">驳回</div>
                <div class="options-btn blue" @click="handlePass(1, item)">通过</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="go-top" @click="handleGoTop">
      <i class="el-icon-arrow-up"></i>
    </div>
    <!-- 查看套餐 -->
    <queryItems ref="queryItems" :autoWidth="true"></queryItems>
  </div>
</template>

<script>
import { approveChange, viewCombo, getcombo, getfzxList } from '@/api/sale/order_customization.js'
import { questDetails, updateItemStatus, getPackage } from '@/api/approval/approval_quest.js'
import queryItems from '@/views/sale/order_customization//query.vue'
import Cookies from 'js-cookie'
export default {
  name: 'Approval_online_item',
  components: { queryItems },
  data() {
    return {
      // 用户id
      userNo: '',
      // 订单id
      orderId: '',
      // 订单信息
      orderInfo: {},
      // 套餐信息
      comboInfo: [],
      // 审批id
      approvalId: '',
      // 审批类型
      typeFlag: '',
      // 审批列表
      approvalList: [],
      // 会员列表
      memberList: [],
      // 超量审批详情
      dataObject: {},
      // 备注
      remark: '',
      // 审批状态
      approvalStatus: undefined,
    }
  },
  created() {
    // console.log(123)
    this.approvalId = this.$route.query.id
    this.orderId = this.$route.query.bizId
    this.typeFlag = this.$route.query.typeFlag
    this.getDetails()
  },
  methods: {
    // 返回订单列表
    toOrderList() {
      const routeUrl = this.$router.resolve({
        name: 'ApprovalOnline', //这里是跳转页面的name
      })
      window.location = routeUrl.href
    },
    // 返回顶部
    handleGoTop() {
      document.documentElement.scrollTo(0, 0)
    },
    // 查看套餐  
    handleCheck() {
      this.$refs.queryItems.queryWindow(this.orderInfo.id)
    },
    // 获取审批详情
    getDetails() {
      approveChange({ id: this.orderId }).then(({ data }) => {
        this.orderInfo = data
        let fzx = []
        getfzxList().then(({ data }) => {
          this.orderInfo.fzxid.split(',').forEach((el) => {
            data.records.forEach((val) => {
              if (val.branchId == el) {
                fzx.push(val.fzx)
              }
            })
          })
          this.orderInfo.fzx = fzx.join(',')
        })
      })
      if (this.typeFlag.includes('ORDER_FLOW')) {
        getPackage({ id: this.orderId }).then(({ data }) => {
          this.comboInfo = data
        })
      }

      this.getApprovalDetails()
    },
    // 获取审批流程
    getApprovalDetails() {
      questDetails(this.approvalId).then((res) => {
        this.approvalStatus = res.data.status
        this.approvalList = res.data.itemList
        if (this.typeFlag == 'OVER_RESERVATION') {
          this.memberList = res.data.reservationTypeList
          this.remark = res.data.remark
        } else if (this.typeFlag == 'EXCESS_RESERVATION') {
          this.dataObject = res.data.dataObject
        }
        this.userNo = Cookies.get('userNo')
      })
    },
    // 通过/驳回
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
.approval-online-item {
  padding: 0;
  background-color: #fff;

  .title {
    position: fixed;
    top: 0;
    z-index: 99;
    width: 100%;
    padding: 14px 16px;
    font-size: 14px;
    background-color: #fff;
  }
  .center {
    padding: 16px;
    font-size: 16px;
    font-weight: 600;
  }
  .submit-name {
    padding: 16px;
    padding-top: 0;
    font-size: 14px;
  }
  .el-divider--horizontal {
    margin: 0;
  }
  .order-info {
    display: flex;
    justify-content: space-between;
    padding: 16px;
    .info-item1 {
      font-size: 14px;
      font-weight: 600;
      color: #333;
    }
    .info-item2 {
      font-size: 14px;
      color: #666;
    }
    .info-item3 {
      margin-top: 4px;
      font-size: 14px;
      color: #333;
    }
  }
  .order-price {
    display: flex;
    justify-content: space-between;
    padding: 16px;
    border-top: 8px solid #f7f8fb;
    border-bottom: 8px solid #f7f8fb;
    .item {
      flex: 1;
      text-align: center;
      &:nth-child(2) {
        border-left: 0.5px solid rgba(0, 0, 0, 0.1);
        border-right: 0.5px solid rgba(0, 0, 0, 0.1);
      }
      .price {
        color: #d41318;
        font-size: 20px;
        font-weight: 600;
      }
      .name {
        color: #999;
        font-size: 14px;
        margin-top: 4px;
      }
    }
  }
  .accordion-item {
    border-bottom: 7px solid #f7f8fb;
    .combo-title {
      font-weight: 600;
      padding: 0 8px;
      color: #d41318;
      line-height: 20px;
      font-size: 14px
    }
    .el-collapse-item__header {
      height: auto;
      padding: 12px 16px;
      border-radius: 4px;
      background: #fff6f6;
    }
    .combo-item {
      padding: 8px 16px 0;
      background-color: #fff;
      .combo-name {
        color: #333;
        font-size: 14px;
        font-weight: 600;
      }
      .chick-item {
        padding: 12px 16px;
        border-radius: 4px;
        background: #f6f7fb;
        margin-bottom: 8px;
        .top {
          display: flex;
          justify-content: space-between;
          font-size: 14px;
          .name {
            color: #333;
            font-weight: 600;
          }
          .price {
            color: #d41318;
            font-weight: 600;
          }
        }
        .bottom {
          color: #666;
          font-size: 12px;
        }
      }
    }
    .combo-sum {
      padding: 4px 8px;
      border-bottom: none;
    }
  }
  .el-collapse-item__content {
    padding-bottom: 4px;
  }

  .approval-right {
    padding: 12px;
    padding-left: 0;
    // padding-bottom: 70px;
    .approval-right-item {
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
        }
      }
    }
  }

  .btn-list {
    // position: fixed;
    // bottom: 0;
    // z-index: 99;
    display: flex;
    justify-content: space-evenly;
    width: 100%;
    padding: 12px 0;
    background-color: #fff;
    .options-btn {
      padding: 8px 50px;
      color: #fff;
      border-radius: 5px;
      white-space: nowrap;
    }
    .blue {
      background: #00b473;
    }
    .red {
      background: #d41318;
    }
  }
  .go-top {
    position: fixed;
    bottom: 10%;
    z-index: 99;
    display: flex;
    align-items: center;
    justify-content: center;
    right: 5%;
    width: 40px;
    height: 40px;
    font-size: 30px;
    color: #fff;
    background-color: gray;
    border-radius: 50%;
  }
}
</style>
