//合并结论词维护 开发人：麦沃德科技暴雨/矢北
import request from '@/utils/request'

// 查询合并结论词列表
export function listbasMerge(query) {
  return request({
    url: '/basMerge/list',
    method: 'get',
    params: query
  })
}
// 查询合并结论词详细
export function getbasMerge(id) {
  return request({
    url: '/basMerge/' + id,
    method: 'get'
  })
}

// 新增合并结论词
export function addbasMerge(data) {
  return request({
    url: '/basMerge',
    method: 'post',
    data: data
  })
}

// 修改合并结论词
export function updatebasMerge(data) {
  return request({
    url: '/basMerge',
    method: 'POST',
    data: data
  })
}

// 删除合并结论词
export function delbasMerge(id) {
  return request({
    url: '/basMerge/' + id,
    method: 'delete'
  })
}
  //获取扩展数据
  export function getExpendData(query){
    return request({
      url:'/basMerge/getInfoListData',
      method:'get',
      params:query
    })
  }
  //获取弹窗扩展数据
  export function getAddExpendData(query){
    return request({
      url:'/basMerge/getInfoList',
      method:'get',
      params:query
    })
  }