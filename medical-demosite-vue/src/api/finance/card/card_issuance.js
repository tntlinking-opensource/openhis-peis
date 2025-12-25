// 体检卡管理 开发人：麦沃德矢北 
import request from '@/utils/request'

export function getListData(query){
  return request({
   url:'/finance/cardHandle/page',
   method:'get',
   params:query
  })
} 
 export function cardTypeData(query)
 {
  return request({
    url:'/finance/sendCard/getTypeData',
    method:'get',
    params:query
  })
 }
  //获取右侧数据
   export function getRightData(query)
   {
    return request({
      url:'/finance/cardHandle/getChargeListData',
      method:'get',
      params:query
    })
   }
   //获取付款方式
   export function getPayWay()
   {
    return request({
      url:'/finance/cardHandle/getPayWayData',
      method:'get',
      
    })
   }
  //保存
  export function saveData(data)
  {
    return request({
      url:'/finance/cardHandle/saveOrUpdate',
      method:'post',
      data:data
    })
  }
  //调用详情页面
  export function getDetail(id){
    return request({
      url:'/finance/sendCard/'+id,
      method:'get',
     
    })
  }