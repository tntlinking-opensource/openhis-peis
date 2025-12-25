//报告发放方式 开发人：麦沃德科技暴雨
import request from '@/utils/request'

// 查询通知方式（领取方式）列表
export function listNotificationmethod(query) {
  return request({
    url: '/notificationmethod/getListData',
    method: 'get',
    params: query
  })
}

// 查询通知方式（领取方式）详细
export function getNotificationmethod(id) {
  return request({
    url: '/notificationmethod/' + id,
    method: 'get'
  })
}

// 新增通知方式（领取方式）
export function addNotificationmethod(data) {
  return request({
    url: '/notificationmethod/saveOrUpdate',
    method: 'post',
    data: data
  })
}

// 修改通知方式（领取方式）
export function updateNotificationmethod(data) {
  return request({
    url: '/notificationmethod/saveOrUpdate',
    method: 'POST',
    data: data
  })
}

// 删除通知方式（领取方式）
export function delNotificationmethod(id) {
  return request({
    url: '/notificationmethod/remove/' + id,
    method: 'delete'
  })
}
