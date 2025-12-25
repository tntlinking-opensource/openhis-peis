<!-- 折叠式布局-纵向  开发人：麦沃德科技半夏 -->
<template>
  <div ref="dragBox" class="drag-column">
    <div class="top-box" :style="'height:' + topLen">
      <slot name="topBox"></slot>
    </div>
    <div class="line-column" @mousedown="onMouseDown"
      :style="(topLen == 0 || bottomLen == 0) ? 'cursor: auto;' : 'cursor: n-resize;'">
      <div class="top-btn" @mousedown.stop @click="onAllLeft" v-if="topLen != 0">
        <i class="el-icon-caret-top"></i>
      </div>
      <div class="bottom-btn" @mousedown.stop @click="onAllRight" v-if="bottomLen != 0">
        <i class="el-icon-caret-bottom"></i>
      </div>
    </div>
    <div class="bottom-box" :style="'height:' + bottomLen">
      <slot name="bottomBox"></slot>
    </div>
  </div>
</template>
<script>
// 使用
// import dragColumn from '@/components/DragColumn'
// components: { dragColumn },
// <drag-column top-size="50%" bottom-size="50%"></drag-column>
// top-size 上边盒子宽度，bottom-size 下边盒子宽度
export default {
  props: ["topSize","bottomSize"],
  data() {
    return {
      topLen: 0,
      bottomLen: 0,
      topOldLen: 0,
      bottomOldLen: 0,
    };
  },
  created() {
    this.topLen = this.topSize
    this.bottomLen = this.bottomSize
  },
  methods: {
    onMouseDown(e) {
      if (this.topLen == 0 || this.bottomLen == 0) {
        return
      }
      this.resizeBox = e.target
      this.topLen = this.resizeBox.previousSibling.clientHeight
      this.bottomLen = this.resizeBox.nextElementSibling.clientHeight
      this.topOldLen = this.resizeBox.previousSibling.clientHeight
      this.bottomOldLen = this.resizeBox.nextElementSibling.clientHeight
      // 颜色改变提醒
      this.startY = e.clientY
      document.addEventListener('mousemove', this.onMousemove)
      document.addEventListener('mouseup', this.onMouseup)
    },
    onMousemove(e) {
      const endY = e.clientY
      const moveLen = endY - this.startY
      const topBoxLen = this.topOldLen + moveLen
      const bottomBoxLen = this.$refs.dragBox.clientHeight - topBoxLen - 18
      // 当最小宽度时，无法继续拖动
      if (topBoxLen <= 50 || bottomBoxLen <= 50) return
      this.topLen = topBoxLen + 'px'
      this.bottomLen = bottomBoxLen + 'px'
    },
    onMouseup() {
      // 颜色恢复
      document.removeEventListener('mousedown', this.onMouseDown)
      document.removeEventListener('mousemove', this.onMousemove)
    },
    onAllLeft() {
      if (this.bottomLen == 0) {
        this.topLen = isNaN(this.topOldLen) ? this.topOldLen : this.topOldLen + "px"
        this.bottomLen = isNaN(this.bottomOldLen) ? this.bottomOldLen : this.bottomOldLen + "px"
      } else {
        this.topOldLen = this.topLen
        this.bottomOldLen = this.bottomLen
        this.topLen = "0"
        this.bottomLen = "100%"
      }

    },
    onAllRight() {
      if (this.topLen == 0) {
        this.topLen = isNaN(this.topOldLen) ? this.topOldLen : this.topOldLen + "px"
        this.bottomLen = isNaN(this.bottomOldLen) ? this.bottomOldLen : this.bottomOldLen + "px"
      } else {
        this.topOldLen = this.topLen
        this.bottomOldLen = this.bottomLen
        this.topLen = "100%"
        this.bottomLen = "0"
      }
    },
  },
};
</script>
<style lang="scss">
.drag-column {
  display: flex;
  flex-direction: column;
  height: 100%;

  .top-box,
  .bottom-box {
    overflow: hidden;
  }

  .line-column {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 18px;
    cursor: n-resize;

    .top-btn,
    .bottom-btn {
      width: 65px;
      height: 10px;
      cursor: pointer;
      background: #fff;
      position: relative;
      margin: 0 2px;
      border-radius: 2px;

      i {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
      }
    }
  }
}
</style>