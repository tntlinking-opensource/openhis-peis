//短信模板维护 开发人：麦沃德科技暴雨
import request from '@/utils/request'

// 查询列表
export function listshortmessage(query) {
  return request({
    url: '/shortmessage/getListData',
    method: 'get',
    params: query
  })
}

// 详细
export function getshortmessage(id) {
  return request({
    url: '/shortmessage/' + id,
    method: 'get'
  })
}

// 新增
export function addshortmessage(data) {
  return request({
    url: '/shortmessage/saveOrUpdate',
    method: 'post',
    data: data
  })
}

// 修改
export function updateshortmessage(data) {
  return request({
    url: '/shortmessage/saveOrUpdate',
    method: 'POST',
    data: data
  })
}

// 删除
export function delshortmessage(id) {
  return request({
    url: '/shortmessage/remove/' + id,
    method: 'delete'
  })
}

// 获取短信通知类型
export function getMessageTypeData() {
  return request({
    url: '/shortmessage/getMessageTypeData',
    method: 'get'
  })
}
