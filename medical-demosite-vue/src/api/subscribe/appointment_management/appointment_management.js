// 默认设置  开发人：麦沃德科技 暴雨
import request from '@/utils/request'

// 分页查询
export function getListData(query) {
  return request({
    url: '/reservation/default/page',
    method: 'get',
    params: query
  })
}

// 添加预约
export function addListData(query) {
  return request({
    url: '/reservation/default',
    method: 'POST',
    data: query
  })
}

// 删除
export function deleteData(id) {
  return request({
    url: '/reservation/default/' + id,
    method: 'DELETE',
  })
}

// 详情
export function getData(id) {
  return request({
    url: '/reservation/default/' + id,
    method: 'GET',
  })
}

// 更新预约
export function defaultData(query) {
  return request({
    url: '/reservation/default',
    method: 'PUT',
    data: query
  })
}

// 获取全部会员类型
export function getListUserData() {
  return request({
    url: '/member/userLevel/list',
    method: 'get',
  })
}

// 获取分中心数据
export function getBranchData() {
  return request({
    url: '/sell/sellDate/getBranchData',
    method: 'get',
  })
}


