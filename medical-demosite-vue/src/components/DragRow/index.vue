<!-- 折叠式布局-横向   开发人：麦沃德科技半夏 -->
<template>
  <div ref="dragBox" class="drag-row">
    <div class="left-box" :style="'width:' + leftLen">
      <slot name="leftBox"></slot>
    </div>
    <div class="line-row" @mousedown="onMouseDown"
      :style="(leftLen == 0 || rightLen == 0) ? 'cursor: auto;' : 'cursor: e-resize;'">
      <div class="left-btn" @mousedown.stop @click="onAllLeft" v-if="leftLen != 0">
        <i class="el-icon-caret-left"></i>
      </div>
      <div class="right-btn" @mousedown.stop @click="onAllRight" v-if="rightLen != 0">
        <i class="el-icon-caret-right"></i>
      </div>
    </div>
    <div class="right-box" :style="'width:' + rightLen">
      <slot name="rightBox"></slot>
    </div>
  </div>
</template>
<script>
// 使用
// import dragRow from '@/components/DragRow'
// components: { dragRow },
// <drag-row left-size="50%" right-size="50%"></drag-row>
// left-size 左边盒子宽度，right-size 右边盒子宽度
export default {
  props: ["leftSize", "rightSize"],
  data() {
    return {
      leftLen: 0,
      rightLen: 0,
      leftOldLen: 0,
      rightOldLen: 0,
    };
  },
  created() {
    this.leftLen = this.leftSize
    this.rightLen = this.rightSize
  },
  methods: {
    onMouseDown(e) {
      if (this.leftLen == 0 || this.rightLen == 0) {
        return
      }
      this.resizeBox = e.target
      this.leftLen = this.resizeBox.previousSibling.clientWidth
      this.rightLen = this.resizeBox.nextElementSibling.clientWidth
      this.leftOldLen = this.resizeBox.previousSibling.clientWidth
      this.rightOldLen = this.resizeBox.nextElementSibling.clientWidth
      this.startX = e.clientX
      document.addEventListener('mousemove', this.onMousemove)
      document.addEventListener('mouseup', this.onMouseup)
    },
    onMousemove(e) {
      const endX = e.clientX
      const moveLen = endX - this.startX
      const leftBoxLen = this.leftOldLen + moveLen
      const rightBoxLen = this.$refs.dragBox.clientWidth - leftBoxLen - 18
      // 当最小宽度时，无法继续拖动
      if (leftBoxLen <= 50 || rightBoxLen <= 50) return
      this.leftLen = leftBoxLen + 'px' // 当前盒子的宽度
      this.rightLen = rightBoxLen + 'px'
    },
    onMouseup() {
      document.removeEventListener('mousedown', this.onMouseDown)
      document.removeEventListener('mousemove', this.onMousemove)
    },
    onAllLeft() {
      if (this.rightLen == 0) {
        this.leftLen = isNaN(this.leftOldLen) ? this.leftOldLen : this.leftOldLen + "px"
        this.rightLen = isNaN(this.rightOldLen) ? this.rightOldLen : this.rightOldLen + "px"
      } else {
        this.leftOldLen = this.leftLen
        this.rightOldLen = this.rightLen
        this.leftLen = "0"
        this.rightLen = "100%"
      }

    },
    onAllRight() {
      if (this.leftLen == 0) {
        this.leftLen = isNaN(this.leftOldLen) ? this.leftOldLen : this.leftOldLen + "px"
        this.rightLen = isNaN(this.rightOldLen) ? this.rightOldLen : this.rightOldLen + "px"
      } else {
        this.leftOldLen = this.leftLen
        this.rightOldLen = this.rightLen
        this.leftLen = "100%"
        this.rightLen = "0"
      }
    },
  },
};
</script>
<style lang="scss">
.drag-row {
  flex: 1;
  overflow: auto;
  display: flex;
  height: 100%;

  .left-box,
  .right-box {
    height: 100%;
    overflow: hidden;
  }

  .line-row {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 18px;
    height: 100%;
    cursor: e-resize;

    .left-btn,
    .right-btn {
      width: 10px;
      height: 65px;
      cursor: pointer;
      background: #fff;
      position: relative;
      margin: 2px 0;
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