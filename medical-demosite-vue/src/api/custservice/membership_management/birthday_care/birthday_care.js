// 生日关怀 开发人：麦沃德科技暴雨
import request from '@/utils/request'

// 生日关怀列表
export function getListData(query) {
  return request({
    url: '/member/birthday/page',
    method: 'get',
    params: query
  })
}

// 生日关怀取消发送
export function cancleSMS(query) {
  return request({
    url: '/member/birthday/cancelSMS',
    method: 'PUT',
    params: query
  })
}

// 生日关怀获取选项
export function getoption() {
  return request({
    url: '/shortmessage/getMessageTypeData',
    method: 'GET',

  })
}

// 生日关怀保存
export function saOrUp(query) {
  return request({
    url: '/member/birthday/saOrUp',
    method: 'POST',
    data: query
  })
}

