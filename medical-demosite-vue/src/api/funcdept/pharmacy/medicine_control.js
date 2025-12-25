// 药品管理  开发人：麦沃德科技 矢北
import request from '@/utils/request'
// 查询药品管理列表
export function getListData(query) {
  return request({
    url: '/drugstore/drug/page',
    method: 'get',
    params: query
  })
}
//删除药品列表
export function deleteListData(id) {
  return request({
    url: '/drugstore/drug/'+id,
    method: 'delete',
  })
}
//查询药品分类
export function detailListData(id) {
  return request({
    url: '/drugstore/drug/' + id,
    method: 'get',
  })
}
//添加或保存药品分类
export function saveListData(data) {
  return request({
    url: '/drugstore/drug/saveOrUpdate',
    method: 'post',
    data:data
  })
}
//获取药品下拉
