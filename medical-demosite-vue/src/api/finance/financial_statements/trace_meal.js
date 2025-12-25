//开发人：麦沃德矢北
import request from '@/utils/request' 
//获取活动套餐列表
export function getMealList(query){
  return request(
    {
      url:'/finance/statements/activityMeal/getActivityMeal',
      method:'get',
      
    }
  )
}
//获取活动套餐列表
export function getData(query){
  return request(
    {
      url:'/finance/statements/activityMeal/getActivityMealList',
      method:'get',
      params:query
    }
  )
}
