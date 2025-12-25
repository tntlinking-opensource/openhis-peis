<!-- 销售主页  开发人：麦沃德科技矢北、予安 -->
<template>
  <div class="sale-index">
    <el-row :gutter="10" style="height: 100%">
      <el-col :span="9" style="height: 100%">
        <div style="height: 100%; border: 1px solid #d4d6d9; padding: 12px; overflow: auto">
          <!-- 基本信息表单 -->
          <div style="display: flex; align-items: center; justify-content: space-between">
            <span class="label-title">快捷操作</span>
            <div @click="goTarget('Registration', 1)" class="enter-btn" style="">登记入口 ▶</div>
          </div>
          <ul class="index-nav">
            <li v-for="item in logoList" :key="item.index" @click="goTarget(item.name)">
              <img :src="item.image" alt="" />
              <span>{{ item.label }}</span>
            </li>
          </ul>
        </div>
      </el-col>
      <el-col :span="15" style="height: 100%">
        <div class="top-table">
          <div style="display: flex; margin-bottom: 7px; justify-content: space-between">
            <span class="label-title">销售订单</span>
            <div @click="goTarget('Order_customization')" class="enter-btn">快速入口 ▶</div>
          </div>
          <div class="table-box" style="height: calc(100% - 10px)">
            <el-table :data="orderData" v-loading="loadingT" align="center" :stripe="true" height="70%">
              <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
              <el-table-column prop="ddh" label="订单号" align="center" show-overflow-tooltip />
              <el-table-column prop="ddmc" label="客户单位名称" align="center" show-overflow-tooltip />
              <el-table-column prop="spzt" label="状态" align="center">
                <template slot-scope="scope">
                  <el-tag type="danger" v-if="scope.row.spzt == 0">审核未通过</el-tag>
                  <el-tag type="warning" v-else-if="scope.row.spzt == 1">已撤回</el-tag>
                  <el-tag type="info" v-else-if="scope.row.spzt == 2">草稿</el-tag>
                  <el-tag type="primary" v-else-if="scope.row.spzt == 3">已提交</el-tag>
                  <el-tag type="success" v-else-if="scope.row.spzt == 4">审核通过</el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="ddjg" label="预算" align="center" />
              <el-table-column prop="ddyhj" label="优惠价" align="center" />
              <el-table-column prop="xsjl" label="销售经理" align="center" />
              <el-table-column prop="fzx" label="分中心" align="center" show-overflow-tooltip />
              <el-table-column prop="lxdh" label="体检计划日期" align="center" />
            </el-table>
            <pagination :total="totalT" :page.sync="pageT.current" :limit.sync="pageT.size" @pagination="getOrder" />
          </div>
        </div>
        <div class="bottom-table">
          <div style="display: flex; margin-bottom: 7px; justify-content: space-between">
            <span class="label-title">异常数据</span>
            <div @click="goTarget('register_list', 2)" class="enter-btn">登记列表 ▶</div>
          </div>
          <div class="table-box" style="height: calc(100% - 10px)">
            <el-table :data="EnListData" v-loading="loadingB" align="center" :stripe="true" height="70%">
              <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
              <el-table-column prop="patientcode" label="体检号" align="center" show-overflow-tooltip />
              <el-table-column prop="itemsCount" label="项目数量" align="center" show-overflow-tooltip />
              <el-table-column prop="pfCount" label="收费项目数量" align="center" show-overflow-tooltip />
              <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200" fixed="right">
              <template slot-scope="scope">
                <el-button size="mini" type="text" style="color: #fe8761" @click="handleIgnore (scope.row)" v-hasPermi="['basis:occupation:dealOpinion:edit']">忽略此数据</el-button>
              </template>
            </el-table-column>
            </el-table>
            <pagination :total="totalE" :page.sync="pageE.current" :limit.sync="pageE.size" @pagination="getDataEnList" />
          </div>
        </div>
        <div class="bottom-table">
          <div style="display: flex; margin-bottom: 7px; justify-content: space-between">
            <span class="label-title">预检跟踪</span>
            <div @click="goTarget('Inspection_tracking')" class="enter-btn">快速入口 ▶</div>
          </div>
          <div class="table-box" style="height: calc(100% - 10px)">
            <el-table :data="trackData" v-loading="loadingB" align="center" :stripe="true" height="70%">
              <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
              <el-table-column prop="khdwmc" label="客户单位名称" align="center" show-overflow-tooltip />
              <el-table-column prop="lxdh" label="上次体检日期" align="center" />
              <el-table-column prop="处理状态" label="状态" align="center">
                <template slot-scope="scope">
                  <el-tag v-if="scope.row.clzt == 0" type="danger">未处理</el-tag>
                  <el-tag v-else>已处理</el-tag>
                </template>
              </el-table-column>
            </el-table>
            <pagination :total="totalB" :page.sync="pageB.current" :limit.sync="pageB.size" @pagination="getTrack" />
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getList } from '@/api/customer/inspection_tracking'
import { listOrder,dataEnList,ignore } from '@/api/sale/order_customization.js'
export default {
  name: 'SaleIndex',
  data() {
    return {
      // 销售订单数据
      orderData: [],
      // 预见跟踪数据
      trackData: [],
      // 异常数据
      EnListData: [],
      loadingT: false,
      loadingB: false,
      loadingE: false,
      pageT: {
        current: 1,
        size: 20,
      },
      pageB: {
        current: 1,
        size: 20,
      },
      pageE: {
        current: 1,
        size: 20,
      },
      totalT: 0,
      totalB: 0,
      totalE: 0,
      logoList: [
        { id: 'prepareOrder', image: require('../../assets/images/sale/1.png'), label: '我的客户', name: 'Customer_list' },
        { id: 'prepareOrder', image: require('../../assets/images/sale/2.png'), label: '客户公共池', name: 'Public_pool' },
        { id: 'prepareOrder', image: require('../../assets/images/sale/3.png'), label: '客户预检跟踪', name: 'Inspection_tracking' },
        { id: 'prepareOrder', image: require('../../assets/images/sale/4.png'), label: '创建套餐', name: 'Create_offer' },
        { id: 'prepareOrder', image: require('../../assets/images/sale/5.png'), label: '订单制定', name: 'Order_customization' },
        { id: 'prepareOrder', image: require('../../assets/images/sale/6.png'), label: '销售合同维护', name: 'Contract_management' },
        { id: 'prepareOrder', image: require('../../assets/images/sale/7.png'), label: '销售同期', name: 'Contract_management' },
        { id: 'prepareOrder', image: require('../../assets/images/sale/8.png'), label: '销售目标', name: 'Monthly_sales_target' },
        { id: 'prepareOrder', image: require('../../assets/images/sale/9.png'), label: '财务录入', name: 'Prepare_order' },
        { id: 'prepareOrder', image: require('../../assets/images/sale/10.png'), label: '高危人员管理', name: 'Crisis_value' },
        { id: 'prepareOrder', image: require('../../assets/images/sale/11.png'), label: '体检报告领取提醒', name: 'Receive_report' },
        { id: 'prepareOrder', image: require('../../assets/images/sale/12.png'), label: '销售团检统计', name: 'Prepare_order' },
      ],
    }
  },
  created() {
    this.getOrder()
    this.getTrack()
    this.getDataEnList()
  },
  methods: {
    // 忽略异常数据
    handleIgnore(row){
      this.$confirm('确定忽略该异常数据吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
        ignore(row.id)
        .then(() => {
            this.getDataEnList()
            this.$modal.msgSuccess("忽略成功");
        })
        .catch((error) => {
          console.error(error)
        })
        })
        .catch(() => {})
        
    },
    // 获取销售订单数据   
    getOrder() {
      this.loadingT = true
      listOrder(this.pageT)
        .then(({ data }) => {
          this.orderData = data.records
          this.totalT = data.total
          this.loadingT = false
        })
        .catch((error) => {
          console.error(error)
          this.loadingT = false
        })
    },
    // 获取预检跟踪数据 
    getTrack() {
      this.loadingB = true
      getList(this.pageB)
        .then(({ data }) => {
          this.trackData = data.records
          this.totalB = data.total
          this.loadingB = false
        })
        .catch((error) => {
          console.error(error)
          this.loadingB = false
        })
    },
    // 获取异常数据
    getDataEnList() {
      this.loadingE = true
      dataEnList(this.pageE)
        .then(({ data }) => {
          this.EnListData = data.records
          this.totalE = data.total
          this.loadingE = false
        })
        .catch((error) => {
          console.error(error)
          this.loadingE = false
        })
    },
    // 跳转路由
    goTarget(href, type) {
      if (type == 1) {
        this.$tab.openPage('个检录入', '/reception/registration/index')
      } else if (type == 2) {
        this.$tab.openPage('登记列表', '/reception/register_list')
      } else {
        this.$router.push({ name: href })
      }
    },
  },
}
</script>

<style lang="scss" scoped>
.sale-index {
  height: 100%;
  padding: 12px;
  // background-color: #fff;
  .index-nav {
    margin-right: 40px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    text-align: center;
    flex-wrap: wrap;
    li {
      margin-bottom: 24px;
      cursor: pointer;
      background: #fff;
      border-radius: 24px;
      width: 180px;
      height: 138px;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: space-around;
      img {
        width: 80px;
        height: 80px;
      }

      span {
        font-weight: 600;
        font-size: 16px;
        line-height: 22px;
        text-align: center;
        color: #333333;
        margin-top: 6px;
      }
    }
  }

  .label-title {
    display: block;
    font-weight: 600;
    font-size: 20px;
    line-height: 20px;
    color: #333333;
    margin-bottom: 12px;
  }
  .top-table {
    overflow: hidden !important;
    height: 32%;
    border: 1px solid #d4d6d9;
    padding: 12px;
    overflow: auto;
    margin-bottom: 10px;
  }
  .bottom-table {
    overflow: hidden !important;
    height: 33%;
    border: 1px solid #d4d6d9;
    padding: 12px;
    overflow: auto;
    margin-bottom: 10px;
  }
  // .top-table {
  //   overflow: hidden !important;
  //   height: calc(50% - 10px);
  //   margin-bottom: 10px;
  //   border: 1px solid #d4d6d9;
  //   padding: 12px;
  //   overflow: auto;
  // }
  // .bottom-table {
  //   overflow: hidden !important;
  //   height: 50%;
  //   border: 1px solid #d4d6d9;
  //   padding: 12px;
  //   overflow: auto;
  // }
  .enter-btn {
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 4px 8px;
    font-size: 14px;
    color: #ffffff;
    background: linear-gradient(rgb(254, 105, 57) 0%, rgb(255, 158, 104) 100%);
    border-radius: 5px;
    cursor: pointer;
  }
}
</style>
