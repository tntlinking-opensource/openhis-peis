import request from '@/utils/request'

// 登录方法
export function login(username, password, code, uuid) {
  const data = {
    username,
    password,
    code,
    uuid
  }
  return request({
    url: '/login',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

// 获取背景图配置
export function getBackgroundConfig() {
  return request({
    url: '/getBackgroundConfig',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })
}

// 注册方法
export function register(data) {
  return request({
    url: '/register',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

// 获取用户详细信息
export function getInfo() {
  return request({
    url: '/getInfo',
    method: 'get'
  })
}

// 获取资源地址
export function getDomain() {
  return request({
    url: '/common/getDomain',
    method: 'get'
  })
}

// 退出方法
export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}

// 获取验证码
export function getCodeImg() {
  return request({
    url: '/captchaImage',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })
}
// 获取搜索的用户名列表
export function getLogUsers(params) {
  return request({
    url: '/getLogUsers',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000,
    params
  })
}

// 获取默认分中心
export function getDefaultBranch() {
  return request({
    url: '/reception/register/getDefaultBranch',
    method: 'get'
  })
}