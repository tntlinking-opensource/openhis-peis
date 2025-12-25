// PACS部位维护  开发人：麦沃德科技半夏
import request from '@/utils/request'

// 不分页查全部
export function getAutoCompleteData(params) {
  return request({
    url: '/pacs/pacsBasePart/getAutoCompleteData',
    method: 'get',
    params
  })
}

// 详情
export function getBasePart(id) {
  return request({
    url: '/pacs/pacsBasePart/' + id,
    method: 'get',
  })
}

// 添加或修改
export function saveOrUpdate(data) {
  return request({
    url: '/pacs/pacsBasePart/saveOrUpdate',
    method: 'post',
    data: data
  })
}

// 删除
export function delBasePart(ids) {
  return request({
    url: '/pacs/pacsBasePart/' + ids,
    method: 'delete',
  })
}