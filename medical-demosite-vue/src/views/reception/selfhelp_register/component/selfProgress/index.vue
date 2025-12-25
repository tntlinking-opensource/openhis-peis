<template>
  <div class="body-main">
    <div class="body-header">
      <div class="process">
        <div v-for="(item, index) in progressItem" :key="index" style="display: flex; align-items: center">
          <div class="process-item">
            <div class="number" :style="{ 'background-color': step >= index + 1 ? themeColor : '' }">
              <span v-if="step <= index + 1">
                {{ index + 1 }}
              </span>
              <i class="el-icon-check" v-else></i>
            </div>
            <div class="process-text" :style="{ color: step >= index + 1 ? themeColor : '' }">{{ item }}</div>
          </div>
          <div class="number-line" :style="{ 'border-color': step >= index + 1 ? themeColor : '' }" v-if="index != progressItem.length - 1"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ['step', 'themeColor', 'progressItem'],
}
</script>

<style lang="scss" scoped>
.body-main {
  display: flex;
  justify-content: center;
  padding-top: 10px;
  width: 100%;
  .body-header {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-around;
    background-color: #ffffff;

    .process {
      padding: 0 6px;
      display: flex;
      align-items: center;
      justify-content: space-around;

      .process-item {
        display: flex;
        flex-direction: column;
        align-items: center;

        .number {
          display: flex;
          align-items: center;
          justify-content: center;
          width: 40px;
          height: 40px;
          margin-bottom: 7px;
          border-radius: 50%;
          font-size: 24px;
          color: #ffffff;
          background-color: #e0e0e0;
        }
        .process-text {
          padding: 0 10px;
          font-size: 28px;
          color: #e0e0e0;
          text-align: center;
        }
      }

      .number-line {
        width: 140px;
        height: 0.5px;
        border: 1px #e0e0e0 solid;
        display: flex;
        align-items: center;
        justify-content: center;
      }
    }
  }
}
@media screen and (max-width: 1950px) {
  .body-main {
    padding-top: 40px;
    .body-header {
      .process {
        .process-item {
          .process-text {
            font-size: 20px;
          }
        }
        .number-line {
          width: 120px;
        }
      }
    }
  }
}
@media screen and (max-width: 1200px) {
  .body-main {
    .body-header {
      .process {
        .process-item {
          .process-text {
            font-size: 16px;
          }
        }
        .number-line {
          width: 75px;
        }
      }
    }
  }
}
</style>
