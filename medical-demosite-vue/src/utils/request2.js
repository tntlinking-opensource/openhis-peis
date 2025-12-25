// 针对超时时间过长
import axios from 'axios'
import { Notification, MessageBox, Message, Loading } from 'element-ui'
import store from '@/store'
import { getToken, removeToken } from '@/utils/auth'
import errorCode from '@/utils/errorCode'
import { tansParams, blobValidate } from "@/utils/ruoyi";
import cache from '@/plugins/cache'
import { saveAs } from 'file-saver'

let downloadLoadingInstance;
// 是否显示重新登录
export let isRelogin = { show: false };

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// 创建axios实例
const service = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: process.env.VUE_APP_BASE_API,
  // 超时
  timeout: 0,
})
let avoidUrl = ''
// request拦截器
service.interceptors.request.use(config => {
  // 是否需要设置 token
  const isToken = (config.headers || {}).isToken === false
  // 是否需要防止数据重复提交
  const isRepeatSubmit = (config.headers || {}).repeatSubmit === false
  if (getToken() && !isToken) {
    config.headers['Authorization'] = 'Bearer ' + getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
  }
  // get请求映射params参数
  if (config.method === 'get' && config.params) {
    let url = config.url + '?' + tansParams(config.params);
    url = url.slice(0, -1);
    config.params = {};
    config.url = url;
  }
  if (!isRepeatSubmit && (config.method === 'post' || config.method === 'put')) {
    avoidUrl = config.url
    const requestObj = {
      url: config.url,
      data: typeof config.data === 'object' ? JSON.stringify(config.data) : config.data,
      time: new Date().getTime()
    }
    const sessionObj = cache.session.getJSON('sessionObj')
    cache.session.setJSON('sessionObj', requestObj)
  }
  if (config.isOnline) {
    config.baseURL = process.env.VUE_APP_ONLINE_API
  }
  return config
}, error => {
  console.error(error)
  Promise.reject(error)
})
// 响应拦截器
service.interceptors.response.use(res => {
  var w = res.config.url
  var a = w.indexOf("?") == -1 ? res.config.url.length : w.indexOf("?")
  var url = w.substring(0, a);
  var notIntercept = ['/pacs_list/middleDbInterface/insert', '/SignInInspection/checkDanger', '/SignInInspection/getPatientData']
  if (notIntercept.indexOf(url) > -1) {
    return res.data
  }
  // 未设置状态码则默认成功状态
  const code = res.data.code || 200;
  // 获取错误信息
  const msg = errorCode[code] || res.data.msg || errorCode['default']
  // 二进制数据则直接返回
  if (res.request.responseType === 'blob' || res.request.responseType === 'arraybuffer') {
    return res.data
  }
  if (code === 401) {
    if (window.location.pathname.includes('search_report')) {
      MessageBox.alert('登录状态已过期，请重新登录', '系统提示', {
        confirmButtonText: '重新登录',
        type: 'warning'
      }
      ).then(() => {
        localStorage.removeItem('searchReport')
        let routeUrl = this.$router.resolve({
          name: 'SearchReport',
        })
        window.open(routeUrl.href, '_self')
      })
    } else if (window.location.pathname.includes('approval_online')) {
      MessageBox.alert('登录状态已过期，请重新登录', '系统提示', {
        confirmButtonText: '重新登录',
        type: 'warning'
      }
      ).then(() => {
        isRelogin.show = false;
        store.dispatch('LogOut').then(() => {
          location.href = '/approval/approval_online';
        })
      }).catch(() => {
        isRelogin.show = false;
      });
    } else {
      if (!isRelogin.show) {
        isRelogin.show = true;
        MessageBox.confirm('登录状态已过期，您可以继续留在该页面，或者重新登录', '系统提示', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }
        ).then(() => {
          isRelogin.show = false;
          store.dispatch('LogOut').then(() => {
            location.href = '/index';
          })
        }).catch(() => {
          isRelogin.show = false;
        });
      }
      return Promise.reject('无效的会话，或者会话已过期，请重新登录。')
    }
  } else if (code === 500 && msg.indexOf("error@") === -1) {
    let message = msg
    Message({
      message,
      type: 'error'
    })
    return Promise.reject(new Error(msg))
  } else if (code !== 200 && msg.indexOf("error@") === -1) {
    let title = msg
    Notification.error({
      title
    })
    return Promise.reject('error')
  } else {
    return res.data
  }
},
  error => {
    // console.error('err', error)
    let { message } = error;
    if (message == "Network Error") {
      message = "后端接口连接异常";
    } else if (message.includes("timeout") && avoidUrl != '/SignInInspection/uploadThirdReports') {
      message = "系统接口请求超时";
    }
    else if (message.includes("Request failed with status code 400")) {
      message = error.response.data;
    }
    else if (message.includes("Request failed with status code") && !window.location.href.includes('/preview/search_report/search')) {
      message = "系统接口" + message.substr(message.length - 3) + "异常";
    } else if (message.includes("timeout") && avoidUrl == '/SignInInspection/uploadThirdReports') {
      return
    }
    Message({
      message: message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

// 通用下载方法
export function download(url, params, filename, config) {
  downloadLoadingInstance = Loading.service({ text: "正在下载数据，请稍候", spinner: "el-icon-loading", background: "rgba(0, 0, 0, 0.7)", })
  return service.post(url, params, {
    transformRequest: [(params) => { return tansParams(params) }],
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    responseType: 'blob',
    ...config
  }).then(async (data) => {
    const isLogin = await blobValidate(data);
    if (isLogin) {
      const blob = new Blob([data])
      saveAs(blob, filename)
    } else {
      const resText = await data.text();
      const rspObj = JSON.parse(resText);
      const errMsg = errorCode[rspObj.code] || rspObj.msg || errorCode['default']
      Message.error(errMsg);
    }
    downloadLoadingInstance.close();
  }).catch((r) => {
    console.error(r)
    Message.error('下载文件出现错误，请联系管理员！')
    downloadLoadingInstance.close();
  })
}

// 下载压缩包
export function downloadZip(url, filename, config) {
  downloadLoadingInstance = Loading.service({ text: "正在下载数据，请稍候", spinner: "el-icon-loading", background: "rgba(0, 0, 0, 0.7)", })
  return service.get(url, {
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    responseType: 'blob',
    ...config
  }).then(async (data) => {
    const isLogin = await blobValidate(data);
    if (isLogin) {
      const blob = new Blob([data], { type: 'application/zip' })
      saveAs(blob, filename)
    } else {
      const resText = await data.text();
      const rspObj = JSON.parse(resText);
      const errMsg = errorCode[rspObj.code] || rspObj.msg || errorCode['default']
      Message.error(errMsg);
    }
    downloadLoadingInstance.close();
  }).catch((r) => {
    console.error(r)
    Message.error('下载文件出现错误，请联系管理员！')
    downloadLoadingInstance.close();
  })
}

export default service
