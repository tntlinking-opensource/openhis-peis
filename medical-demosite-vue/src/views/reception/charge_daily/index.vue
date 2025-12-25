<!-- 收费日报  开发人：麦沃德科技半夏 -->
<template>
  <div class="app-container tab-container">
    <el-tabs type="border-card">
      <el-tab-pane>
        <span slot="label">每日客服报表统计</span>
        <service-table></service-table>
      </el-tab-pane>
      <el-tab-pane>
        <span slot="label">每日记账报表统计</span>
        <bill-table></bill-table>
      </el-tab-pane>
      <el-tab-pane>
        <span slot="label">每日通联明细</span>
        <contact-table></contact-table>
      </el-tab-pane>
      <el-tab-pane>
        <span slot="label">每日疫苗收费统计</span>
        <vaccine-statistics></vaccine-statistics>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import serviceTable from './service'
import billTable from './bill'
import contactTable from './contact'
import vaccineStatistics from './vaccine_statistics'
export default {
  name: 'Charge_daily',
  components: { billTable, serviceTable, contactTable, vaccineStatistics },
}
</script>
<style lang="scss">
.tab-container {
  padding: 0;
  height: 100%;

  .el-tabs {
    height: 100%;
    display: flex;
    flex-direction: column;

    .el-tabs__content {
      flex: 1;
      box-sizing: border-box;
      padding: 12px 20px !important;

      .el-tab-pane {
        height: 100%;
      }
    }
  }

  .el-tabs--border-card {
    box-shadow: none;
    border: none;
  }

  .el-tabs__header {
    border: none !important;
  }

  .el-tabs__item {
    background: #f6f7fb !important;
    border-radius: 5px 5px 0px 0px !important;
    border: none !important;

    &.is-active {
      background: #fff !important;
    }
  }
}
</style>
