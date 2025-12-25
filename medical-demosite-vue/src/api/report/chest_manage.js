//柜子管理  开发人：麦沃德科技矢北
import request from '@/utils/request'

// 分页查询健康一审页面数据
export function getListData(query) {
  return request({
    url: '/report/chest/page',
    method: 'get',
    params: query
  })
}

// 添加或者更新
export function upDataList(data) {
  return request({
    url: '/report/chest',
    method: 'post',
    data: data
  })
}
//删除
export function deleteList(ids) {
  return request({
    url: '/report/chest/' + ids,
    method: 'delete',
  })
}
//获取详情
export function getDetail(id) {
  return request({
    url: '/report/chest/' + id,
    method: 'get',

  })
}
//导出
export function exportList(query) {
  return request({
    url: '/report/chest/export',
    method: 'post',
    params: query
  })
}
