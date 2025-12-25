// 分享报告  开发人：麦沃德科技 予安
import request from '@/utils/request'

// 验证提取码
export function validationCode(data) {
  return request({
    url: '/open/api/report/mobileReport/validationCode',
    headers: {
      isToken: false,
      Authorization: undefined,
      'Upgrade-Insecure-Requests': '1'
    },
    method: 'POST',
    data
  })
}
// 分享报告-访问次数和ip
export function lastAccess(data) {
  return request({
    url: '/open/api/report/mobileReport/lastAccess',
    headers: {
      isToken: false,
      Authorization: undefined,
      'Upgrade-Insecure-Requests': '1'
    },
    method: 'POST',
    data
  })
}
// 分享报告-详情
export function detailsApi(params) {
  return request({
    url: '/open/api/report/mobileReport/details',
    headers: {
      isToken: false,
      Authorization: undefined
    },
    method: 'POST',
    params
  })
}
// 分享报告-详情
export function getShareConfig(params) {
  return request({
    url: '/open/api/report/mobileReport/getBranchConfig',
    headers: {
      isToken: false,
      Authorization: undefined
    },
    method: 'POST',
    params
  })
}