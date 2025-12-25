//体检基础套餐维护 开发人：麦沃德科技暴雨/矢北

import request from '@/utils/request'

// 查询最小套餐列表
export function listCreatecombo(query) {
  return request({
    url: '/sell/createcombo/list',
    method: 'get',
    params: query
  })
}

//扩展列表的分页查询
export function expendListData(query) {
  return request({
    url: '/sell/createmeal/getSmallItems',
    method: 'get',
    params: query
  })
}
// 根据输入套餐名称或拼音码分页查询
export function tjList(query) {
  return request({
    url: '/sell/createcombo/getAutoCompleteData',
    method: 'get',
    params: query
  })
}

// 查询最小套餐详细
export function getCreatecombo(id) {
  return request({
    url: '/sell/createcombo/' + id,
    method: 'get'
  })
}
// 分页保存
export function saveDetailData(data) {
  return request({
    url: '/sell/createcombo',
    method: 'post',
    data: data
  })
}
// 获取分中心信息
export function getfzxList() {
  return request({
    url: '/sell/createorder/getBranchData',
    method: 'get',
    params: {
      current: 1,
      size: 99999,
    }
  })
}

// 新增最小套餐
export function addCreatecombo(data) {
  return request({
    url: '/sell/createcombo',
    method: 'post',
    data: data
  })
}

// 修改最小套餐
export function updateCreatecombo(data) {
  return request({
    url: '/sell/createcombo',
    method: 'put',
    data: data
  })
}

// 删除最小套餐
export function delCreatecombo(id) {
  return request({
    url: '/sell/createcombo/' + id,
    method: 'delete'
  })
}

// 获取和套餐关联的收费项目
export function getcombo(id) {
  return request({
    url: '/sell/createcombo/items/' + id,
    method: 'get'
  })
}
// 设置禁用与反禁用
export function setBan(query) {
  return request({
    url: '/sell/createcombo/setBan',
    method: 'put',
    params: query
  })
}
  //同步套餐
  export function synchronous(ids){
    return request({
      url:'/sell/createcombo/synchronous/'+ids,
      method:'put',
      
    })
  }


//推荐套餐
export function recommendData(query) {
  return request({
    url: '/sell/createcombo/setRecommend', 
    method: 'put',
    params: query
  })
}
//活动套餐
export function activeData(query) {
  return request({
    url: '/sell/createcombo/setActive',
    method: 'put',
    params: query
  })
}
// 根据id获取相关项目
export function getItemMes(id) {
  return request({
    url: '/sell/createmeal/getItemsData/' + id,
    method: 'get',
  })
}
// 根据id 获取详情
export function getDetailData(id) {
  return request({
    url: '/sell/createcombo/' + id,
    method: 'get'
  })
}
// 根据id 获取相关收费项目
export function getDetailItem(query) {
  return request({
    url: '/sell/createcombo/getItems',
    method: 'get',
    params: query
  })
}
// 获取详情 保存
export function copyOrder(data) {
  return request({
    url: '/sell/createcombo/copy',
    method: 'post',
    data: data
  })
}
//重复/sell/createcombo/repeat
export function repeatData(query) {
  return request({
    url: '/sell/createcombo/repeat',
    method: 'get',
    params: query
  })
}

//获取分中心数据
export function fzxData(query) {
  return request({
    url: '/sell/createorder/getBranchData',
    method: 'get',
    params: query
  })
}
