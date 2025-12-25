// 自助登机  开发人：麦沃德科技 矢北
import request from '@/utils/request' 

//登记提交信息
 export function getItems(query){
   return request({
     url:'/machine/additionalPay/items',
     method: 'get',
     params:query
   })
 }