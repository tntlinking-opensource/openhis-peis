//报告存储配置 开发人：麦沃德科技暴雨
import request from '@/utils/request'

// 查询通知方式（领取方式）列表
export function listBaseAttachmentConfig(query) {
  return request({
    url: '/baseAttachmentConfig/list',
    method: 'get',
    params: query
  })
}

// 查询通知方式（领取方式）详细
export function getBaseAttachmentConfig(id) {
  return request({
    url: '/baseAttachmentConfig/' + id,
    method: 'get'
  })
}

// 新增通知方式（领取方式）
export function addBaseAttachmentConfig(data) {
  return request({
    url: '/baseAttachmentConfig/saveOrUpdate',
    method: 'post',
    data: data
  })
}

// 修改通知方式（领取方式）
export function updateBaseAttachmentConfig(data) {
  return request({
    url: '/baseAttachmentConfig/saveOrUpdate',
    method: 'POST',
    data: data
  })
}

// 删除通知方式（领取方式）
export function delBaseAttachmentConfig(id) {
  return request({
    url: '/baseAttachmentConfig/delete/' + id,
    method: 'delete'
  })
}
