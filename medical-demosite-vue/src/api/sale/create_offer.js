// 创建套餐  开发人：麦沃德科技半夏
import request from '@/utils/request'

// 查询普通套餐列表
export function listCreatemeal(query) {
  return request({
    url: '/sell/createmeal/getPage',
    method: 'get',
    params: query
  })
}

// 查询普通套餐详细
export function getCreatemeal(id) {
  return request({
    url: '/sell/createmeal/' + id,
    method: 'get'
  })
}

// 双击获取收费项目信息
export function getItemsData(id) {
  return request({
    url: '/sell/createmeal/getItemsData/' + id,
    method: 'get'
  })
}

// 获取收费项目子表格数据
export function getExamsByItemId(query) {
  return request({
    url: '/sell/createmeal/getExamsByItemId',
    method: 'get',
    params: query
  })
}

// 新增/修改普通套餐
export function saveOrUpdate(data) {
  return request({
    url: '/sell/createmeal/saveOrUpdate',
    method: 'post',
    data: data
  })
}

// 判断套餐能否编辑
export function isEdit(id) {
  return request({
    url: '/sell/createmeal/isEdit/' + id,
    method: 'get'
  })
}

// 判断套餐能否删除
export function isRemove(ids) {
  return request({
    url: '/sell/createmeal/isRemove?tcId=' + ids,
    method: 'get',
  })
}

// 删除普通套餐
export function delCreatemeal(id) {
  return request({
    url: '/sell/createmeal/' + id,
    method: 'delete'
  })
}

// 复制套餐
export function repeatCreatemeal(data) {
  return request({
    url: '/sell/createmeal/repeat',
    method: 'post',
    data: data
  })
}

// 禁用-反禁用套餐
export function setBan(data) {
  return request({
    url: '/sell/createmeal/setBan',
    method: 'put',
    params: data
  })
}

// 设置平安ID
export function setPinganId(data) {
  return request({
    url: '/sell/createmeal/setPinganId',
    method: 'post',
    params: data
  })
}

// 获取关联的收费项目
export function getSfxmsData(id) {
  return request({
    url: '/sell/createmeal/getSfxmsData/' + id,
    method: 'get',
  })
}

// 获取关联的收费项目
export function getSfxm(query) {
  return request({
    url: '/sell/createmeal/getSfxm',
    method: 'get',
    params: query
  })
}

// 获取关联的套餐
export function getListDataAddMeal(query) {
  return request({
    url: '/sell/createmeal/getListDataAddMeal',
    method: 'get',
    params: query
  })
}

// 根据接害因素和职业体检类别获取关联的收费项目
export function getPpZxtcData(data) {
  return request({
    url: '/sell/createmeal/getPpZxtcData',
    method: 'post',
    data: data
  })
}

// 验证当前输入的套餐折扣是否小于之前设定的折扣下限
export function getValidaZkLeader(query) {
  return request({
    url: '/sell/createmeal/onValidaZkLeader',
    method: 'get',
    params: query
  })
}

// 判断是否必检
export function getSfbj(data) {
  return request({
    url: '/sell/createmeal/getSfbj',
    method: 'post',
    data: data
  })
}

// 检查添加的收费项目下是否有检查项目重复
export function isJcxmReport(query) {
  return request({
    url: '/sell/createmeal/isJcxmReport',
    method: 'get',
    params: query
  })
}

// 根据最小套餐id获取收费项目
export function getItemsDataAjax(query) {
  return request({
    url: '/sell/createmeal/getItemsDataAjax',
    method: 'get',
    params: query
  })
}

// 获取季度最低折扣
export function getSeasonZk() {
  return request({
    url: '/sell/createorder/getSeasonZk',
    method: 'get',
  })
}

// 设为app套餐
export function setAppMeal(params) {
  return request({
    url: '/app/createmealApp/setAppMeal',
    method: 'post',
    params
  })
}