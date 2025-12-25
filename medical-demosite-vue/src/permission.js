import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { getToken } from '@/utils/auth'
import { isRelogin } from '@/utils/request'

NProgress.configure({ showSpinner: false })
// 白名单
const whiteList = ['/login', '/auth-redirect', '/bind', '/register']
// 注：用户查询报告时使用
const otherList = ['/preview/search_report/search', '/preview/search_report/list', '/preview/search_report/report_view', '/preview/report_share', '/approval/approval_online', '/approval/approval_online_item',]
// 需要查询的报告页面
const reportList = ['/preview/individual_report/health']

router.beforeEach((to, from, next) => {
  NProgress.start()
  // 查询报告及分享报告时加入白名单
  if (reportList.indexOf(to.path) !== -1 && (to.query.from == 1 || to.query.from == 2)) {
    otherList.push(to.path)
  }
  if (getToken() && otherList.indexOf(to.path) === -1) {
    to.meta.title && store.dispatch('settings/setTitle', to.meta.title)
    /* has token*/
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done()
    } else {
      if (store.getters.roles.length === 0) {
        isRelogin.show = true
        // 判断当前用户是否已拉取完user_info信息
        store.dispatch('GetInfo').then(() => {
          isRelogin.show = false
          store.dispatch('GenerateRoutes').then(accessRoutes => {
            // 根据roles权限生成可访问的路由表
            router.addRoutes(accessRoutes) // 动态添加可访问路由表
            next({ ...to, replace: true }) // hack方法 确保addRoutes已完成
            store.dispatch('SectionRouters').then(accessRoutes => {
              // 根据roles权限生成可访问的路由表
              router.addRoutes(accessRoutes) // 动态添加可访问路由表
              next({ ...to, replace: true }) // hack方法 确保addRoutes已完成
            })
          })
        }).catch(err => {
          store.dispatch('LogOut').then(() => {
            Message.error(err)
            next({ path: '/' })
          })
        })
      } else {
        next()
      }
    }
  } else {
    // 没有token 或 查询报告相关页面
    if (whiteList.indexOf(to.path) !== -1 || otherList.indexOf(to.path) !== -1) {
      // 在免登录白名单，直接进入
      next()
    } else {
      if (window.location.pathname.includes('approval_online')) {
        next(`/approval/approval_online`) // 否则全部重定向到登录页
      } else {
        next(`/login?redirect=${to.fullPath}`) // 否则全部重定向到登录页
      }
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
