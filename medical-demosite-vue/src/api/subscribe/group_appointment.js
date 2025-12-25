// 团体预约 开发人：麦沃德科技 予安
import request from '@/utils/request'

// 分页查询
export function getListData(params) {
  return request({
    url: '/reservation/group/page',
    method: 'get',
    params
  })
}
// 新增预约
export function addListData(data) {
  return request({
    url: '/reservation/group',
    method: 'POST',
    data
  })
}
// 预约详情
export function groupList(data) {
  return request({
    url: '/reservation/Setting/groupList',
    method: 'post',
    data
  })
}
// 预约详情
export function getDetails(id) {
  return request({
    url: '/reservation/group/' + id,
    method: 'get',
    params: {
      id
    }
  })
}
// 修改预约
export function editListData(data) {
  return request({
    url: '/reservation/group',
    method: 'put',
    data
  })
}
// 删除预约
export function deleteListData(ids) {
  return request({
    url: '/reservation/group/' + ids,
    method: 'DELETE',
    params: {
      ids
    }
  })
}

// 获取可预约vip人数
export function getReVipNumber(params) {
  return request({
    url: '/reservation/group/getReVipNumber',
    method: 'get',
    params
  })
}
