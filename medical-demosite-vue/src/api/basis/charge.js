// 收费项目分类设置 + 收费项目设置  开发人：麦沃德科技半夏/清风
import request from '@/utils/request'

// 查询收费项目分类设置列表
export function listPrinttype(query) {
  return request({
    url: '/printtype/page',
    method: 'get',
    params: query
  })
}

// 查询收费项目分类设置详细
export function getPrinttype(id) {
  return request({
    url: '/printtype/' + id,
    method: 'get'
  })
}

// 新增收费项目分类设置
export function addPrinttype(data) {
  return request({
    url: '/printtype',
    method: 'post',
    data: data
  })
}

// 修改收费项目分类设置
export function updatePrinttype(data) {
  return request({
    url: '/printtype',
    method: 'put',
    data: data
  })
}

// 删除收费项目分类设置
export function delPrinttype(id) {
  return request({
    url: '/printtype/' + id,
    method: 'delete'
  })
}

// 查询JC收费项目列表
export function listItems(query) {
  return request({
    url: '/items/page',
    method: 'get',
    params: query
  })
}

// 查询JC收费项目详细
export function getItems(id) {
  return request({
    url: '/items/' + id,
    method: 'get'
  })
}

// 新增JC收费项目
export function addItems(data) {
  return request({
    url: '/items',
    method: 'post',
    data: data
  })
}

// 修改JC收费项目
export function updateItems(data) {
  return request({
    url: '/items',
    method: 'put',
    data: data
  })
}

// 删除JC收费项目
export function delItems(id) {
  return request({
    url: '/items/' + id,
    method: 'delete'
  })
}

// JC收费项目上传图片
export function uploadItems(data) {
  return request({
    url: '/items/upload',
    method: 'post',
    data: data
  })
}

// 更新收费项目检查次数
export function updateJccs() {
  return request({
    url: '/items/updateJccs',
    method: 'get'
  })
}

// 更新收费项目检查次数
export function setApp(data) {
  return request({
    url: `/items/setApp?ids=${data.ids}&state=${data.state}`,
    method: 'post',
  })
}

// 更新收费项目检查次数
export function setBan(data) {
  return request({
    url: `/items/setBan?ids=${data.ids}&state=${data.state}`,
    method: 'post',
  })
}

//获取详情表格数据
export function getListDatas(query) {
  return request({
    url: '/items/getListDatas',
    method: 'get',
    params: query
  })
}

//获取条码打印分类
export function getItemsBarcodeClass(query) {
  return request({
    url: '/items/getItemsBarcodeClass',
    method: 'get',
    params: query
  })
}

// 获取图片
export function getPicture(id) {
  return request({
    url: '/items/getPicture/' + id,
    method: 'post'
  })
}

// 删除图片
export function removeImg(data) {
  return request({
    url: '/items/removeImg',
    method: 'delete',
    params: data
  })
}

// 导入老系统收费项目
export function importItems(data) {
  return request({
    url: '/reception/datamove/importItems',
    method: 'post',
    data
  })
}