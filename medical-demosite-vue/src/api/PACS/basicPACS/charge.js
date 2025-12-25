// PACS收费项目  开发人：麦沃德科技半夏
import request from '@/utils/request'

// 获取pacs收费项目
export function getItemsList(query) {
  return request({
    url: '/pacs/items/page',
    method: 'get',
    params: query
  })
}

// pacs收费项目详情
export function getChargeData(id) {
  return request({
    url: '/pacs/items/'+id,
    method: 'get',
  })
}

// 查询全部pacs检查项目
export function getSignList(query) {
  return request({
    url: '/pacs/items/exam/list',
    method: 'get',
    params: query
  })
}

// 删除pacs收费项目
export function delItems(ids) {
  return request({
    url: '/pacs/items/' + ids,
    method: 'delete',
  })
}

// 添加pacs收费项目
export function saveItems(data) {
  return request({
    url: '/pacs/items/save',
    method: 'post',
    data: data
  })
}

// 编辑pacs收费项目
export function updateItems(data) {
  return request({
    url: '/pacs/items/update',
    method: 'put',
    data: data
  })
}