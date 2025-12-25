// 预约短信回访 开发人：麦沃德科技暴雨
import request from '@/utils/request'

// 预约短信回访列表
export function listPrinttype(query) {
  return request({
    url: '/member/smsFollowUp/page',
    method: 'get',
    params: query
  })
}

// 预约短信回访保存
export function saveGroupData(query) {
  return request({
    url: '/member/smsFollowUp/saveGroupData',
    method: 'POST',
    data: query
  })
}

// 预约短信回访取消发送
export function cancleSMS(query) {
  return request({
    url: '/member/smsFollowUp/cancleSMS',
    method: 'PUT',
    params: query
  })
}

// 预约短信回访查看短信数据
export function getSmsData(query) {
  return request({
    url: '/member/smsFollowUp/getSmsData',
    method: 'get',
    params: query
  })
}
