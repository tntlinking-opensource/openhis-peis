// PACS部位维护  开发人：麦沃德科技半夏
import request from '@/utils/request'

// 分页查询
export function getConfigList(query) {
  return request({
    url: '/lis/attachmentConfig/page',
    method: 'get',
    params: query
  })
}

// 详情
export function getConfig(id) {
  return request({
    url: '/lis/attachmentConfig/' + id,
    method: 'get',
  })
}

// 添加或修改
export function saveOrUpdate(data) {
  return request({
    url: '/lis/attachmentConfig/saveOrUpdate',
    method: 'post',
    data: data
  })
}

// 删除
export function delConfig(ids) {
  return request({
    url: '/lis/attachmentConfig/' + ids,
    method: 'delete',
  })
}