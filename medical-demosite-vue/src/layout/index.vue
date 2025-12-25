<template>
  <div :class="classObj" class="app-wrapper" :style="{ '--current-color': themeCom }">
    <div v-if="device === 'mobile' && sidebar.opened" class="drawer-bg" @click="handleClickOutside"></div>
    <!-- <sidebar v-if="!sidebar.hide" class="sidebar-container" /> -->
    <div :class="{ hasTagsView: needTagsView, sidebarHide: sidebar.hide }" class="main-container">
      <div :class="{ 'fixed-header': fixedHeader }">
        <navbar :expiredTime="expiredTime" :versionInfo="versionInfo" />
        <top-nav />
        <tags-view v-if="needTagsView" />
      </div>
      <app-main />
      <right-panel>
        <settings />
      </right-panel>
    </div>
    <!-- 服务过期弹窗 -->
    <expiration-dialog ref="expirationDialog" />
    <!-- 已经是最新弹窗 -->
    <dialog-update-description ref="dialogUpdateDescription" :versionInfo="versionInfo"></dialog-update-description>
  </div>
</template>

<script>
import { isExpired } from '@/api/menu.js'
import { getVersion } from '@/api/system/version_control/version_info'
import TopNav from '@/components/TopNav'
import RightPanel from '@/components/RightPanel'
import expirationDialog from './components/expiration_dialog.vue'
import dialogUpdateDescription from './components/navbarDialog/dialog_update_description.vue'
import { AppMain, Navbar, Settings, Sidebar, TagsView } from './components'
import ResizeMixin from './mixin/ResizeHandler'
import { mapState } from 'vuex'
import variables from '@/assets/styles/variables.scss'
const version = require('element-ui/package.json').version // element-ui version from node_modules
const ORIGINAL_THEME = '#409EFF' // default color

export default {
  name: 'Layout',
  components: {
    TopNav,
    AppMain,
    Navbar,
    RightPanel,
    Settings,
    Sidebar,
    TagsView,
    expirationDialog,
    dialogUpdateDescription,
  },
  mixins: [ResizeMixin],
  data() {
    return {
      chalk: '', // content of theme-chalk css
      theme: '',
      // 到期信息
      expirationInfo: {},
      // 到期时间
      expiredTime: '',
      // 版本信息
      versionInfo: {
        version: {
          versionItemList: [],
        },
      },
    }
  },
  computed: {
    defaultTheme() {
      return this.$store.state.settings.theme
    },
    ...mapState({
      themeCom: (state) => state.settings.theme,
      sideTheme: (state) => state.settings.sideTheme,
      sidebar: (state) => state.app.sidebar,
      device: (state) => state.app.device,
      needTagsView: (state) => state.settings.tagsView,
      fixedHeader: (state) => state.settings.fixedHeader,
    }),
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === 'mobile',
      }
    },
    variables() {
      return variables
    },
  },
  created() {
    if (this.defaultTheme !== ORIGINAL_THEME) {
      this.setTheme(this.defaultTheme)
    }
    this.getExpirationTime()
    this.getVersionNum()
  },
  watch: {
    defaultTheme: {
      handler: function (val, oldVal) {
        this.theme = val
      },
      immediate: true,
    },
  },
  methods: {
    handleClickOutside() {
      this.$store.dispatch('app/closeSideBar', { withoutAnimation: false })
    },
    setThemeColor(val) {
      this.$store.dispatch('settings/changeSetting', {
        key: 'theme',
        value: val,
      })
    },
    async setTheme(val) {
      const oldVal = this.chalk ? this.theme : ORIGINAL_THEME
      if (typeof val !== 'string') return
      const themeCluster = this.getThemeCluster(val.replace('#', ''))
      const originalCluster = this.getThemeCluster(oldVal.replace('#', ''))

      const getHandler = (variable, id) => {
        return () => {
          const originalCluster = this.getThemeCluster(ORIGINAL_THEME.replace('#', ''))
          const newStyle = this.updateStyle(this[variable], originalCluster, themeCluster)

          let styleTag = document.getElementById(id)
          if (!styleTag) {
            styleTag = document.createElement('style')
            styleTag.setAttribute('id', id)
            document.head.appendChild(styleTag)
          }
          styleTag.innerText = newStyle
        }
      }

      if (!this.chalk) {
        const url = `https://unpkg.com/element-ui@${version}/lib/theme-chalk/index.css`
        await this.getCSSString(url, 'chalk')
      }

      const chalkHandler = getHandler('chalk', 'chalk-style')

      chalkHandler()

      const styles = [].slice.call(document.querySelectorAll('style')).filter((style) => {
        const text = style.innerText
        return new RegExp(oldVal, 'i').test(text) && !/Chalk Variables/.test(text)
      })
      styles.forEach((style) => {
        const { innerText } = style
        if (typeof innerText !== 'string') return
        style.innerText = this.updateStyle(innerText, originalCluster, themeCluster)
      })

      this.setThemeColor(val)
    },

    updateStyle(style, oldCluster, newCluster) {
      let newStyle = style
      oldCluster.forEach((color, index) => {
        newStyle = newStyle.replace(new RegExp(color, 'ig'), newCluster[index])
      })
      return newStyle
    },

    getCSSString(url, variable) {
      return new Promise((resolve) => {
        const xhr = new XMLHttpRequest()
        xhr.onreadystatechange = () => {
          if (xhr.readyState === 4 && xhr.status === 200) {
            this[variable] = xhr.responseText.replace(/@font-face{[^}]+}/, '')
            resolve()
          }
        }
        xhr.open('GET', url)
        xhr.send()
      })
    },

    getThemeCluster(theme) {
      const tintColor = (color, tint) => {
        let red = parseInt(color.slice(0, 2), 16)
        let green = parseInt(color.slice(2, 4), 16)
        let blue = parseInt(color.slice(4, 6), 16)

        if (tint === 0) {
          // when primary color is in its rgb space
          return [red, green, blue].join(',')
        } else {
          red += Math.round(tint * (255 - red))
          green += Math.round(tint * (255 - green))
          blue += Math.round(tint * (255 - blue))

          red = red.toString(16)
          green = green.toString(16)
          blue = blue.toString(16)

          return `#${red}${green}${blue}`
        }
      }

      const shadeColor = (color, shade) => {
        let red = parseInt(color.slice(0, 2), 16)
        let green = parseInt(color.slice(2, 4), 16)
        let blue = parseInt(color.slice(4, 6), 16)

        red = Math.round((1 - shade) * red)
        green = Math.round((1 - shade) * green)
        blue = Math.round((1 - shade) * blue)

        red = red.toString(16)
        green = green.toString(16)
        blue = blue.toString(16)

        return `#${red}${green}${blue}`
      }

      const clusters = [theme]
      for (let i = 0; i <= 9; i++) {
        clusters.push(tintColor(theme, Number((i / 10).toFixed(2))))
      }
      clusters.push(shadeColor(theme, 0.1))
      return clusters
    },

    // 获取到期时间
    getExpirationTime() {
      isExpired().then(({ data }) => {
        this.expirationInfo = data
        if (data.isPop) {
          this.$refs.expirationDialog.showDialog(this.expirationInfo)
        }
        this.expiredTime = data.expiredTime ? data.expiredTime.slice(0, 10) : ''
      })
    },
    // 获取当前版本号
    getVersionNum() {
      getVersion().then(({ data }) => {
        if (data.lastVersion == '1') {
          data.curVersion = data.version.versionNumber
        }
        this.versionInfo = data
        if (data.firstShow == '1') {
          this.$refs.dialogUpdateDescription.showDialog()
        }
      })
    },
  },
}
</script>

<style lang="scss" scoped>
@import '~@/assets/styles/mixin.scss';
@import '~@/assets/styles/variables.scss';

.app-wrapper {
  @include clearfix;
  position: relative;
  height: 100%;
  width: 100%;

  &.mobile.openSidebar {
    position: fixed;
    top: 0;
  }
}

.drawer-bg {
  background: #000;
  opacity: 0.3;
  width: 100%;
  top: 0;
  height: 100%;
  position: absolute;
  z-index: 999;
}

.fixed-header {
  position: fixed;
  top: 0;
  right: 0;
  z-index: 9;
  width: calc(100% - #{$base-sidebar-width});
  transition: width 0.28s;
}

.hideSidebar .fixed-header {
  width: calc(100% - 54px);
}

.sidebarHide .fixed-header {
  width: 100%;
}

.mobile .fixed-header {
  width: 100%;
}
</style>
