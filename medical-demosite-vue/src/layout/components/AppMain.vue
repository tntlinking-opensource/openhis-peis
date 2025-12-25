<template>
  <section class="app-main">
    <div class="padTop"></div>
    <!-- <transition name="fade-transform" mode="out-in"> -->
    <keep-alive :include="cachedViews">
      <router-view v-if="!$route.meta.link" :key="key" />
    </keep-alive>
    <!-- </transition> -->
    <iframe-toggle />
  </section>
</template>

<script>
import iframeToggle from './IframeToggle/index'

export default {
  name: 'AppMain',
  components: { iframeToggle },
  data() {
    return {
      btnColor: false,
    }
  },
  computed: {
    cachedViews() {
      return this.$store.state.tagsView.cachedViews
    },
    key() {
      return this.$route.path
    },
  },
  created() {
    const body = document.querySelector('body')
    body.classList.add('no-color-btn')
  },
}
</script>

<style lang="scss" scoped>
.app-main {
  /* 50= navbar  50  */
  height: calc(100vh - 90px);
  width: 100%;
  padding: 8px;
  background: #f6f7fb;
  position: relative;
  // overflow: hidden;
  overflow-y: auto;
}

.fixed-header + .app-main {
  padding-top: 50px;
}

.hasTagsView {
  .app-main {
    /* 84 = navbar + tags-view = 50 + 34 */
    height: calc(100vh - 90px);
    overflow: hidden;
    overflow-y: auto;
  }

  .fixed-header + .app-main {
    padding-top: 84px;
  }
}
</style>

<style lang="scss">
// fix css style bug in open el-dialog
.el-popup-parent--hidden {
  .fixed-header {
    padding-right: 17px;
  }
}

.el-tooltip__popper {
  max-width: 800px;
}

.el-menu--horizontal .el-menu-item {
  font-size: 14px;
}

.no-color-btn {
  /* // 按钮无颜色
  // row布局中的primary按钮颜色 */
  .el-button--success.is-plain,
  .el-button--warning.is-plain,
  .el-button--danger.is-plain,
  .el-button--primary.is-plain {
    color: rgba(254, 105, 57, 1) !important;
    border: 1px solid rgba(254, 105, 57, 1) !important;
    background: #fff !important;
    font-size: 13px;
  }

  .el-button--success.is-plain:hover,
  .el-button--warning.is-plain:hover,
  .el-button--danger.is-plain:hover,
  .el-button--primary.is-plain:hover,
  .el-button--default.is-plain:hover,
  .el-button--success.is-plain:focus,
  .el-button--warning.is-plain:focus,
  .el-button--danger.is-plain:focus,
  .el-button--primary.is-plain:focus,
  .el-button--default.is-plain:focus,
  .el-button--success.is-plain:active,
  .el-button--warning.is-plain:active,
  .el-button--danger.is-plain:active,
  .el-button--primary.is-plain:active,
  .el-button--default.is-plain:active {
    color: rgba(254, 105, 57, 0.7) !important;
    border: 1px solid rgba(254, 105, 57, 0.7) !important;
  }

  .el-button--success.is-plain.is-disabled,
  .el-button--warning.is-plain.is-disabled,
  .el-button--danger.is-plain.is-disabled,
  .el-button--primary.is-plain.is-disabled,
  .el-button--default.is-plain.is-disabled {
    color: rgba(153, 153, 153, 1) !important;
    border: 1px solid rgba(153, 153, 153, 1) !important;
    opacity: 1 !important;
  }
  .el-button--success.is-plain.is-disabled:hover,
  .el-button--warning.is-plain.is-disabled:hover,
  .el-button--danger.is-plain.is-disabled:hover,
  .el-button--primary.is-plain.is-disabled:hover,
  .el-button--default.is-plain.is-disabled:hover,
  .el-button--success.is-plain.is-disabled:focus,
  .el-button--warning.is-plain.is-disabled:focus,
  .el-button--danger.is-plain.is-disabled:focus,
  .el-button--primary.is-plain.is-disabled:focus,
  .el-button--default.is-plain.is-disabled:focus,
  .el-button--success.is-plain.is-disabled:active,
  .el-button--warning.is-plain.is-disabled:active,
  .el-button--danger.is-plain.is-disabled:active,
  .el-button--primary.is-plain.is-disabled:active,
  .el-button--default.is-plain.is-disabled:active {
    color: rgba(153, 153, 153, 0.9) !important;
    border: 1px solid rgba(153, 153, 153, 0.9) !important;
    opacity: 1 !important;
  }
}
</style>
