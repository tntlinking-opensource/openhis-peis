// 客户阶段追踪  开发人：麦沃德科技 矢北
import request from '@/utils/request'

export function getList(query) {
  return request({
    url: '/sell/customerfollow/page',
    method: 'get',
    params: query
  })
}
 export function getData(id){
  return request({
    url:'/sell/customerfollow/'+ id,
    method:'get'
  })
 }
 export function getRightData(id){
  return request({
    url:'/sell/customerfollow/getListByCumId/'+ id,
    method:'get'
  })
 }