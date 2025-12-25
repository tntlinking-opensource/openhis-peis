  //签单计划 开发人：麦沃德科技矢北
import request from '@/utils/request'
// 查询普通套餐列表
export function getListData(query) {
  return request({
    url: '/sell/orderPlan/page',
    method: 'Get',
    params: query
  })
}
//查询
  export function deleteListData(ids){
    return request({
      url:'/sell/orderPlan/'+ids,
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
   //更新数据
   export function updatedData(data) {
    return request({
      url:'/sell/orderPlan/',
      method:'put',
      data:data
    })
   }
   //增加保存
    export function addItem(data){
      return request({
        url:'/sell/orderPlan',
        method:'post',
        data:data
      })
    }
