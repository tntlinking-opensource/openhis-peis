<template>
  <!-- 操作说明对话框 -->
  <el-dialog title="操作说明" :visible.sync="openDialog" width="750px" class="update-instructions-dialog">
    <div style="min-height:300px">
      <template v-if="useInstructionsList.length">
        <div class="instructions-title">使用说明</div>
        <div class="instructions-list">
          <div class="instructions-item" v-for="item in useInstructionsList" :key="item.id" @click="handleDownload(item)">
            <el-tooltip effect="dark" :content="item.documentname" placement="top">
              <div class="instructions-item-name blue">{{ item.documentname }}</div>
            </el-tooltip>
            <img src="@/assets/icon/topNav/link_circle.png" alt="" />
          </div>
        </div>
      </template>
      <template v-if="processInstructionsList.length">
        <div class="instructions-title" style="margin-top: 8px">流程说明</div>
        <div class="instructions-list">
          <div class="instructions-item" v-for="item in processInstructionsList" :key="item.id" @click="handleDownload(item)">
            <el-tooltip effect="dark" :content="item.documentname" placement="top">
              <div class="instructions-item-name green">{{ item.documentname }}</div>
            </el-tooltip>
            <img src="@/assets/icon/topNav/link_circle2.png" alt="" />
          </div>
        </div>
      </template>
      <template v-if="videoList.length">
        <div class="instructions-title" style="margin-top: 8px">视频讲解</div>
        <div class="instructions-list">
          <div class="instructions-item" v-for="item in videoList" :key="item.id" @click="handleDownload(item)">
            <el-tooltip effect="dark" :content="item.documentname" placement="top">
              <div class="instructions-item-name orange">{{ item.documentname }}</div>
            </el-tooltip>
            <img src="@/assets/icon/topNav/video_circle.png" alt="" />
          </div>
        </div>
      </template>
    </div>
  </el-dialog>
</template>

<script>
import { getListApi } from '@/api/system/operation_instruction'
export default {
  data() {
    return {
      // 更新日志对话框
      openDialog: false,
      // 使用说明列表
      useInstructionsList: [],
      // 流程说明列表
      processInstructionsList: [],
      // 视频讲解列表
      videoList: [],
    }
  },
  methods: {
    showDialog() {
      this.openDialog = true
      this.useInstructionsList = []
      this.processInstructionsList = []
      this.videoList = []
      this.getList()
    },
    // 获取数据
    getList() {
      getListApi({ size: 100 }).then(({ data }) => {
        data.records.forEach((el) => {
          if (el.type == '1') {
            this.useInstructionsList.push(el)
          } else if (el.type == '2') {
            this.processInstructionsList.push(el)
          } else if (el.type == '3') {
            this.videoList.push(el)
          }
        })
      })
    },
    // 下载附件
    handleDownload(item) {
      window.open(this.$getCookie('imgPath') + item.urlPdf, '_blank')
    },
  },
}
</script>

<style scoped lang="scss">
.update-instructions-dialog {
  .instructions-title {
    font-size: 16px;
    font-weight: 600;
  }
  .instructions-list {
    display: flex;
    flex-wrap: wrap;
    .instructions-item {
      display: flex;
      align-items: center;
      width: 22%;
      margin: 8px 0;
      margin-right: 3%;
      cursor: pointer;
      .instructions-item-name {
        overflow: hidden; /*超出部分隐藏*/
        width: calc(100% - 24px);
        font-size: 16px;
        text-overflow: ellipsis; /* 超出部分显示省略号 */
        white-space: nowrap; /*规定段落中的文本不进行换行 */
        &.blue {
          color: #1890ff;
        }
        &.green {
          color: #00af66;
        }
        &.orange {
          color: #fe6939;
        }
      }
      img {
        width: 16px;
        height: 16px;
      }
    }
  }
}
</style>
