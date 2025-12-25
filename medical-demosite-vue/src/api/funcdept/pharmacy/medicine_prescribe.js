// 药品管理  开发人：麦沃德科技 矢北
import request from '@/utils/request'
// 查询药品管理列表
export function getListData(query) {
  return request({
    url: '/drugstore/prescribe/page',
    method: 'get',
    params: query
  })
}
 
//药房取药
 export function takeDrug(query)
 {
  return request({
    url:'/drugstore/prescribe/takeDrug',
    method:'put',
    params:query
  })
 }
 // 退药 
 export function getBackDrug(query)
 {
  return request({
    url:'/drugstore/prescribe/drugRepercussion',
    method:'put',
    params:query
  })
 }