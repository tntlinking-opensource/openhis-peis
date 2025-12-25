 //撞单排查 开发人：麦沃德矢北
import request from '@/utils/request'
// 查询列表
export function getListData(query) {
  return request({
    url: '/sell/orderConflict/page',
    method: 'Get',
    params: query
  })
} 
//删除
  export function deleteListData(ids){
    return request({
      url:'/sell/orderConflict/'+ids,
      method:'Delete',
      
    })
  }
  //详情
   export function getDetailData(id){
    return request({
      url:'/sell/orderPlan/'+id,
      method:'get'
    })
   }
   export function updatedData(data) {
    return request({
      url:'/sell/orderConflict',
      method:'put',
      data:data
    })
   }
   //添加
    export function addItem(data){
      return request({
        url:'/sell/orderConflict/deal',
        method:'put',
        data:data
      })
    }