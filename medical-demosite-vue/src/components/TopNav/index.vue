<template>
  <div class="top-nav">
    <el-menu :default-active="activeMenu" mode="horizontal" @select="handleSelect" popper-append-to-body>
      <scroll-pane ref="scrollPane" class="tags-view-wrapper">
        <el-submenu :style="{ '--theme': theme }" v-for="item in topMenus" :index="item.path" :key="item.path">
          <template slot="title">
            <svg-icon :icon-class="item.meta.icon" />
            <span>{{ item.meta.title }}</span>
          </template>
          <div class="submenu-child" v-for="child in item.children" :key="child.path">
            <div v-if="!child.children">
              <el-menu-item :index="child.path" :style="{ '--theme': theme }" v-if="!child.hidden">
                {{ child.meta.title }}
              </el-menu-item>
            </div>
            <el-submenu :index="child.path" v-else>
              <template slot="title">{{ child.meta.title }}</template>
              <div v-for="(tag, tagIndex) in child.children" :key="tagIndex">
                <el-menu-item :index="child.path + '/' + tag.path" :style="{ '--theme': theme }" v-if="!tag.hidden">
                  {{ tag.meta.title }}
                </el-menu-item>
              </div>
            </el-submenu>
          </div>
        </el-submenu>
      </scroll-pane>
    </el-menu>
  </div>
</template>

<script>
import { constantRoutes } from '@/router'
import ScrollPane from './ScrollPane'

// 隐藏侧边栏路由
const hideList = ['/index', '/user/profile']

export default {
  components: { ScrollPane },
  data() {
    return {
      // 当前激活菜单的 index
      currentIndex: undefined,
    }
  },
  computed: {
    theme() {
      return this.$store.state.settings.theme
    },
    // 顶部显示菜单
    topMenus() {
      let topMenus = []
      this.routers.map((menu) => {
        if (menu.hidden !== true) {
          // 兼容顶部栏一级菜单内部跳转
          if (menu.path === '/') {
            topMenus.push(menu.children[0])
          } else {
            topMenus.push(menu)
          }
        }
      })
      return topMenus
    },
    // 所有的路由信息
    routers() {
      return this.$store.state.permission.topbarRouters
    },
    // 设置子路由
    childrenMenus() {
      var childrenMenus = []
      this.routers.map((router) => {
        for (var item in router.children) {
          if (router.children[item].parentPath === undefined) {
            if (router.path === '/') {
              router.children[item].path = '/' + router.children[item].path
            } else {
              if (!this.ishttp(router.children[item].path)) {
                router.children[item].path = router.path + '/' + router.children[item].path
              }
            }
            router.children[item].parentPath = router.path
          }
          childrenMenus.push(router.children[item])
        }
      })
      return constantRoutes.concat(childrenMenus)
    },
    // 默认激活的菜单
    activeMenu() {
      const path = this.$route.path
      let activePath = path
      if (path !== undefined && path.lastIndexOf('/') > 0 && hideList.indexOf(path) === -1) {
        const tmpPath = path.substring(1, path.length)
        activePath = '/' + tmpPath.substring(0, tmpPath.indexOf('/'))
        this.$store.dispatch('app/toggleSideBarHide', false)
      } else if (!this.$route.children) {
        activePath = path
        this.$store.dispatch('app/toggleSideBarHide', true)
      }
      this.activeRoutes(activePath)
      return activePath
    },
  },
  methods: {
    // 菜单选择事件
    handleSelect(key, keyPath) {
      this.currentIndex = key

      const route = this.routers.find((item) => item.path === key)
      if (this.ishttp(key)) {
        // http(s):// 路径新窗口打开
        window.open(key, '_blank')
      } else if (!route || !route.children) {
        // 没有子路由路径内部打开
        this.$router.push({ path: key })
        this.$store.dispatch('app/toggleSideBarHide', true)
      } else {
        // 显示左侧联动菜单
        this.activeRoutes(key)
        this.$store.dispatch('app/toggleSideBarHide', false)
      }
    },
    // 当前激活的路由
    activeRoutes(key) {
      var routes = []
      if (this.childrenMenus && this.childrenMenus.length > 0) {
        this.childrenMenus.map((item) => {
          if (key == item.parentPath || (key == 'index' && '' == item.path)) {
            routes.push(item)
          }
        })
      }
      if (routes.length > 0) {
        this.$store.commit('SET_SIDEBAR_ROUTERS', routes)
      }
    },
    ishttp(url) {
      return url.indexOf('http://') !== -1 || url.indexOf('https://') !== -1
    },
  },
}
</script>
<style lang="scss">
.submenu-child {
  .el-menu-item,
  .el-submenu__title {
    width: auto !important;
    min-width: 0 !important;
    padding: 0 12px !important;
    height: 32px !important;
    line-height: 32px !important;
    color: #333333 !important;

    &:hover {
      color: #{'var(--theme)'} !important;
      background-color: rgba(255, 0, 0, 0.1) !important;
    }
  }

  .el-submenu__icon-arrow {
    font-size: 16px;
  }
}

.top-nav {
  background: #f6f7fb;
  padding: 4px 0;

  .el-menu--horizontal {
    height: 22px;
    background: #f6f7fb;

    .el-scrollbar__wrap {
      height: 40px;
      overflow: hidden;

      .el-scrollbar__view {
        white-space: nowrap;

        .el-submenu {
          display: inline-block;
          height: 22px;
          line-height: 22px;
          background: #fff;
          margin-left: 4px;

          &:first-child {
            margin-left: 0;
          }

          .el-submenu__title {
            height: 22px;
            line-height: 22px;
            padding: 0;
            padding-left: 4px;

            &:hover {
              background: transparent !important;
            }

            .svg-icon {
              margin: 0 8px 0 0 !important;
              width: 1.1em;
              height: 1.1em;
              position: relative;
              top: 1px;
            }

            .el-submenu__icon-arrow {
              right: 16px;
            }

            span {
              display: inline-block;
              margin-right: 24px;
            }

            .el-submenu__icon-arrow {
              font-size: 16px;
              right: 4px;
            }
          }
        }
      }
    }
  }
}

.el-menu--horizontal {
  .el-menu {
    min-width: 150px !important;
  }
}
</style>
<style scoped>
.top-nav /deep/ .el-submenu__title {
  font-size: 14px;
}
.top-nav /deep/ .el-submenu__icon-arrow {
  margin-top: -7px;
}
</style>
