<template>
  <!-- 更新记录对话框 -->
  <el-dialog title="更新日志" :visible.sync="dialogLog" width="80%" class="update-log-dialog">
    <div class="update-log-list">
      <div class="update-log-item-line" :style="{ width: `calc(${100 - updateLogEmpty * 25}% - 24px` }"></div>
      <div class="update-log-item" v-for="(item, index) in updateLogList" :key="index">
        <div class="update-log-item-date">
          <div>{{ item.createdate ? item.createdate.slice(0, 10) : '' }}</div>
          <div>{{ item.versionNumber }}</div>
        </div>
        <div class="update-log-item-triangle"></div>
        <div class="update-log-item-circle">
          <div class="update-log-item-circle2"></div>
        </div>
        <div class="update-log-item-content">
          <p v-for="(val, index2) in item.versionItemList" :key="index2">{{ `${index2 + 1}.${val.content}` }}</p>
        </div>
      </div>
      <div class="update-log-item" v-for="(item, index) in updateLogEmpty" :key="index"></div>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="pageInfo.current" :limit.sync="pageInfo.size" layout="prev, pager, next" @pagination="getLastList" />
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="dialogLog = false">我知道了</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { lastList } from '@/api/system/version_control/version_info'
export default {
  props: ['versionInfo'],
  data() {
    return {
      // 更新日志对话框
      dialogLog: false,
      // 更新日志列表
      updateLogList: [],
      // flex补全数量
      updateLogEmpty: 0,
      // 更新日志分页
      pageInfo: {
        current: 1,
        size: 3,
      },
      total: 0,
    }
  },
  methods: {
    showDialog() {
      this.dialogLog = true
      this.getLastList()
    },
    // 获取版本记录
    getLastList() {
      lastList({
        ...this.pageInfo,
        status: 2,
      }).then(({ data }) => {
        this.updateLogList = data.records
        this.updateLogEmpty = 3 - (data.records.length % 3)
        if (this.updateLogEmpty == 3) {
          this.updateLogEmpty = 0
        }
        this.total = data.total
      })
    },
  },
}
</script>

<style scoped>
.update-log-dialog .update-log-list {
  position: relative;
  overflow-x: auto;
  display: flex;
  justify-content: space-between;
  width: 100%;
}
.update-log-dialog .update-log-list .update-log-item-line {
  position: absolute;
  top: 56px;
  height: 8px;
  border-radius: 4px;
  background-color: #fff5ee;
}
.update-log-dialog .update-log-list .update-log-item {
  width: 33%;
  margin-right: 24px;
  margin-bottom: 8px;
}
.update-log-dialog .update-log-list .update-log-item .update-log-item-date {
  display: flex;
  align-items: center;
  justify-content: space-between;
  min-height: 31px;
  padding: 6px 8px;
  font-size: 14px;
  color: #fe7040;
  background-color: #fff0eb;
  border-radius: 4px;
  white-space: nowrap;
}
.update-log-dialog .update-log-list .update-log-item .update-log-item-triangle {
  display: inline-block;
  margin: 2px 6px;
  border-top: 10px solid #fe7040;
  border-left: 6px solid transparent;
  border-right: 6px solid transparent;
}
.update-log-dialog .update-log-list .update-log-item .update-log-item-circle {
  position: relative;
  z-index: 2;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 24px;
  height: 24px;
  background-color: #fff0eb;
  border-radius: 50%;
}
.update-log-dialog .update-log-list .update-log-item .update-log-item-circle .update-log-item-circle2 {
  width: 16px;
  height: 16px;
  background-color: #fe7040;
  border-radius: 50%;
}

.update-log-dialog .update-log-list .update-log-item .update-log-item-content {
  box-sizing: border-box;
  overflow-y: auto;
  height: 425px;
  margin-top: 8px;
  padding: 0 16px;
  background-color: #f9fbff;
  border-radius: 8px;
  border-top: 10px solid #fe7040;
}
::-webkit-scrollbar-track {
  background: #f9fbff;
}
</style>
