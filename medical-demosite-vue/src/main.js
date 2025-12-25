import Vue from 'vue'

import Cookies from 'js-cookie'

import '@/assets/styles/index.scss' // global css
import '@/assets/styles/ruoyi.scss' // ruoyi css

import Element from 'element-ui'
import './assets/styles/element-variables.scss'
import './assets/styles/element-ui.scss'

import App from './App'
import store from './store'
import router from './router'
import directive from './directive' // directive
import plugins from './plugins' // plugins
import { download, downloadZip } from '@/utils/request2'
import Bus from './plugins/bus.js'
import Print from '@/utils/print.js'
// import './utils/rem.js'
import './assets/icons' // icon
import './permission' // permission control
import { getDicts } from '@/api/system/dict/data'
import { getConfigKey } from '@/api/system/config'
import { parseTime, resetForm, addDateRange, selectDictLabel, selectDictLabels, handleTree, downloadStatic } from '@/utils/ruoyi'
import { getCookie } from '@/utils/getCookie.js'
import { getDate } from '@/utils/getDate.js'
import { userLevelList } from "@/api/custservice/membership_management/center_member.js";

// 分页组件
import Pagination from '@/components/Pagination'
// 自定义表格工具组件
import RightToolbar from '@/components/RightToolbar'
// 富文本组件
import Editor from '@/components/Editor'
// 文件上传组件
import FileUpload from '@/components/FileUpload'
// 图片上传组件
import ImageUpload from '@/components/ImageUpload'
// 图片预览组件
import ImagePreview from '@/components/ImagePreview'
// 字典标签组件
import DictTag from '@/components/DictTag'
// 头部标签组件
import VueMeta from 'vue-meta'
// 字典数据组件
import DictData from '@/components/DictData'

import inputSelect from '@/components/InputSelect' 
import searchSelect from '@/components/searchSelect'

Vue.prototype.getDicts = getDicts
Vue.prototype.getConfigKey = getConfigKey
Vue.prototype.parseTime = parseTime
Vue.prototype.resetForm = resetForm
Vue.prototype.addDateRange = addDateRange
Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.selectDictLabels = selectDictLabels
Vue.prototype.download = download
Vue.prototype.downloadZip = downloadZip
Vue.prototype.handleTree = handleTree
Vue.prototype.downloadStatic = downloadStatic
Vue.prototype.$getCookie = getCookie
Vue.prototype.$getDate = getDate
Vue.prototype.$getLevelList = userLevelList

Vue.component('DictTag', DictTag)
Vue.component('Pagination', Pagination)
Vue.component('RightToolbar', RightToolbar)
Vue.component('Editor', Editor)
Vue.component('FileUpload', FileUpload)
Vue.component('ImageUpload', ImageUpload)
Vue.component('ImagePreview', ImagePreview)
Vue.component('inputSelect', inputSelect)
Vue.component('searchSelect', searchSelect)
Vue.use(directive)
Vue.use(plugins)
Vue.use(VueMeta)
Vue.use(Bus)
Vue.use(Print) // 打印

DictData.install()

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */

Vue.use(Element, {
  size: Cookies.get('size') || 'small', // set element-ui default size
})

Vue.filter('showTooltip', (msg, width) => {
  let app = document.querySelector('#app')
  let span = document.createElement('span')
  span.innerHTML = msg
  app.appendChild(span)
  let isShow = span.offsetWidth > width
  app.removeChild(span)
  return !isShow
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: (h) => h(App),
})
