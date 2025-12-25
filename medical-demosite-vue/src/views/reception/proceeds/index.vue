<!-- 费用管理 开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 主体 -->
    <div class="table-box">
      <el-row :gutter="10" class="mb8" style="height: 100%">
        <!-- 左侧 -->
        <index-left :payOptions="payOptions"></index-left>

        <!-- 右侧 -->
        <index-right :payOptions="payOptions" :registrantList="registrantList"></index-right>
      </el-row>
    </div>
  </div>
</template>

<script>
import { getPaywayData } from '@/api/reception/prepare_order.js'
import { getAllUserSql2 } from '@/api/reception/proceeds.js'

import indexLeft from './index_left.vue'
import indexRight from './index_right.vue'
export default {
  name: 'Proceeds',
  components: {
    indexLeft,
    indexRight,
  },
  data() {
    return {
      // 支付方式选项列表
      payOptions: [],
      // 登记人列表
      registrantList: [],
    }
  },
  created() {
    getPaywayData().then(({ data }) => {
      this.payOptions = data
    })
    getAllUserSql2().then(({ data }) => {
      this.registrantList = data
    })
  },
  methods: {},
}
</script>

<style scoped>
.mb8 {
  display: flex;
}
</style>
