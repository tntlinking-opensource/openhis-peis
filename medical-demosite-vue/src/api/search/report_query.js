//报告信息查询  开发人麦沃德矢北
import request from '@/utils/request' 
export function getListData(params){
  return request({
    url:'query/reportQuery/page',
    method:'get',
    params
  })
} 
//报告信息查询  
export function getTypeData(){
  return request({
    url:'/query/reportQuery/getOrderNotifycationMethods',
    method:'get',
  })
}  
//保存信息
 export function saveOrUpData(data){
  return request({
    url:'/query/reportQuery/saveInfoXx',
    method:'post',
    data:data
  })
 }