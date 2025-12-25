<template>
  <div class="process">
    <div class="process_box">
      <el-row v-for="(row, index) in process" :key="index" class="process_list"
        :ref="(row.num != 1 ? 'ref_' + getRandom() : null)">
        <div v-if="(row.num != 1)" :class="[(row.num != 1 ? 'row_line' : ''), row.unset_border]">
        </div>
        <div v-for="(item, key) in row.list" :key="key" :style="{ width: item.width, height: item.height, flexDirection: item.direction }" 
        :class="['box_li',(item.clear == 'before' ? 'clear_before' :(item.clear == 'after' ? 'clear_after' : '')) 
        ]" @click="goTarget(item.name,item.label)">
          <img :src="item.image" alt="item.label" :style="{ width: item.iconSize, height: item.iconSize }">
          <span>{{ item.label }}</span>
        </div>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: "Process",
  props: ["process"],
  data() {
    return {}
  },
  mounted() {
    let _this = this;
    this.$nextTick(() => {
      for (let ref in _this.$refs) {
        if (_this.$refs[ref][0].$el.children == undefined) {
          continue;
        }
        let _children = _this.$refs[ref][0].$el.children;
        let widthArr = [];
        let leftArr = [];
        let lineObj;
        let width;
        let left;
        for (let child in _children) {
          if (typeof _children[child] != 'object') {
            continue;
          }
          if (_children[child].className.indexOf('row_line') != -1) {
            lineObj = _children[child];
            continue;
          }
          widthArr.push(_children[child].clientWidth);
          leftArr.push(_children[child].offsetLeft);
        }
        let firstWidth = widthArr.shift();
        let endWidth = widthArr.pop();
        let firstLeft = leftArr.shift();
        let endLeft = leftArr.pop();
        width = (lineObj.clientWidth - ((firstWidth + endWidth) / 2 + (lineObj.clientWidth - endLeft - endWidth) + firstLeft)) / lineObj.clientWidth;
        left = (firstLeft + firstWidth / 2 + 1) / lineObj.clientWidth;
        lineObj.style.width = width * 100 + '%';
        lineObj.style.left = left * 100 + '%';
      }
    });
  },
  methods: {
    getRandom() { // 随机生成6位数，保持ref的唯一性
      let number = parseInt(Math.random() * 1000000);
      return number;
    },
    goTarget(name,label) {
      this.$emit('goTarget', name,label)
    }
  }
}
</script>

<style scoped lang="scss">
.process {
  .process_box {
    width: 100%;
    height: auto;

    .process_list {
      width: 100%;
      display: flex;
      justify-content: space-between;

      .row_line {
        width: 100%;
        height: 90px;
        position: absolute;
        border-top: 4px solid rgba(255, 255, 255, 0.5);
        border-bottom: 4px solid rgba(255, 255, 255, 0.5);

        &.unset_bottom {
          border-bottom: 0 !important;
        }
      }

      .box_li {
        cursor: pointer;
        position: relative;
        margin: 30px 0;
        font-weight: 600;
        font-size: 16px;
        line-height: 22px;
        text-align: center;
        color: #333333;
        background: rgba(255, 255, 255, 0.5);
        border-radius: 24px;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;

        span {
          margin: 6px;
        }

        &:after {
          content: '';
          display: block;
          height: 30px;
          width: 4px;
          position: absolute;
          bottom: -30px;
          background-color: rgba(255, 255, 255, 0.5);
          left: 50%;
          transform: translateX(-50%);
          -webkit-transform: translateX(-50%);
        }

        &:before {
          content: '';
          display: block;
          height: 30px;
          width: 4px;
          position: absolute;
          top: -30px;
          background-color: rgba(255, 255, 255, 0.5);
          left: 50%;
          transform: translateX(-50%);
          -webkit-transform: translateX(-50%);
        }

        &.clear_before {
          margin-top: 0 !important;
        }

        &.clear_before:before {
          content: '';
          height: 0 !important;
          background-color: unset !important;
        }

        &.clear_after {
          margin-bottom: 0 !important;
        }

        &.clear_after:after {
          content: '';
          height: 0 !important;
          background-color: unset !important;
        }
      }
    }
  }
}
</style>