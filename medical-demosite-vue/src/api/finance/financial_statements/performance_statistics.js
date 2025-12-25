//开发人：麦沃德矢北 线下业绩统计
 
import request from '@/utils/request' 
//获取活动套餐列表
export function getMealList(){
  return request(
    {
      url:'/finance/statements/daySalesVolume/filterCriteria',
      method:'get',
      
    }
  )
}
//获取活动套餐列表
export function getListData(query){
  return request(
    {
      url:'/finance/statements/daySalesVolume/list',
      method:'get',
      params:query
    }
  )
}
