<!-- 退费退项 开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column">
    <div class="table-box">
      <el-row type="flex" justify="space-between" style="width: 100%; height: 100%">
        <item-left></item-left>
        <item-right></item-right>
      </el-row>
    </div>
  </div>
</template>
<script>
import itemLeft from './return_item_left.vue'
import itemRight from './return_item_right.vue'
export default {
  name: 'ReturnItem',
  components: {
    itemLeft,
    itemRight,
  },
}
</script>
