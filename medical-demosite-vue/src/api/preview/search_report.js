// 报告搜索  开发人：麦沃德科技半夏
import request from '@/utils/request'

// 发送验证码
export function sendLoginCode(data) {
  return request({
    url: '/user/sendRegisterSms',
    isOnline: true,
    method: 'PUT',
    data
  })
}

// 验证码登录
export function smsLogin(data) {
  return request({
    url: '/user/phoneRegister',
    isOnline: true,
    method: 'post',
    data
  })
}

//查询报告列表(新系统数据)
export function getOnlineList(data) {
  return request({
    url: '/api/v1/mobileReport/list',
    headers: {
      isToken: false,
      Authorization: data.token
    },
    isOnline: true,
    method: 'post',
    params: {
      phone: data.mobile,
      year: data.year
    }
  })
}
//查询报告列表(老系统数据)
export function getOnlinelistOld(data) {
  return request({
    url: '/api/v1/mobileReport/listOld',
    headers: {
      isToken: false,
      Authorization: data.token
    },
    isOnline: true,
    method: 'post',
    params: {
      phone: data.mobile,
      year: data.year
    }
  })
}
// 查询报告详情(老系统数据)
export function getOnlineDetailsOld(data) {
  return request({
    url: '/api/v1/mobileReport/oldDetails',
    headers: {
      isToken: false,
      Authorization: data.token
    },
    isOnline: true,
    method: 'post',
    params: {
      id: data.id,
    }
  })
}
// 将pdf转jep图片的Base64
export function pdfToBase64(data) {
  return request({
    url: '/api/v1/mobileReport/pdfToBase64',
    headers: {
      isToken: false,
      Authorization: data.token
    },
    isOnline: true,
    method: 'post',
    params: {
      remotePath: data.remotePath,
      curPage: data.curPage,
    }
  })
}

//查询报告
export function getOnlineData(data) {
  return request({
    url: '/api/v1/mobileReport/details',
    headers: {
      isToken: false,
      Authorization: data.token
    },
    isOnline: true,
    method: 'post',
    params: {
      id: data.id
    }
  })
}

// 获取资源链接
export function getOnlineDomain(data) {
  return request({
    url: '/api/v1/mobileReport/getDomain',
    headers: {
      isToken: false,
      Authorization: data.token
    },
    isOnline: true,
    method: 'get',
  })
}

// 获取报告配置
export function getOnlineConfig(data) {
  return request({
    url: '/api/v1/mobileReport/getBranchConfig',
    headers: {
      isToken: false,
      Authorization: data.token
    },
    isOnline: true,
    method: 'get',
    params: {
      branchId: data.branchId
    }
  })
}

// 判断是否绑定过身份证号
export function checkIdCard(data) {
  return request({
    url: '/api/v1/userIdcard/checkIdCard',
    headers: {
      isToken: false,
      Authorization: data.token
    },
    isOnline: true,
    method: 'post',
    params: {
      phone: data.mobile
    }
  })
}
// 分页查询身份证列表
export function idcardPage(data) {
  return request({
    url: '/api/v1/userIdcard/page',
    headers: {
      isToken: false,
      Authorization: data.token
    },
    isOnline: true,
    method: 'get',
    params: {
      phone: data.mobile,
      current: 1,
      size: 100,
    }
  })
}
// 分页查询身份证列表
export function saOrUp(token, data) {
  return request({
    url: '/api/v1/userIdcard/saOrUp',
    headers: {
      isToken: false,
      Authorization: token
    },
    isOnline: true,
    method: 'post',
    data
  })
}