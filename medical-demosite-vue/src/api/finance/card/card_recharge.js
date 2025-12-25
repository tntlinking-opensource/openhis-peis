// 卡充值 开发人：麦沃德矢北 
import request from '@/utils/request'

export function getListData(query) {
  return request({
    url: '/finance/rechargeCard/page',
    method: 'get',
    params: query
  })
}

//获取右侧数据
export function getRightData(query) {
  return request({
    url: '/finance/rechargeCard/getCardForChargeList',
    method: 'get',
    params: query
  })
}
//获取分中心类型
export function fzxData(query) {
  return request({
    url: '/finance/physicalCheckout/fzx',
    method: 'get',
    params: query
  })
}
//获取卡类型
export function cardTypeData(query) {
  return request({
    url: '/finance/rechargeCard/getTypeData',
    method: 'get',
    params: query
  })
}
//获取卡号详情
 export function getDetailData(query){
  return request({
    url:'/finance/rechargeCard/getCardData',
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
    export function saveOrUpdate(data){
      return request({
        url:'/finance/rechargeCard/saveOrUpdate',
        method:'post',
        data:data
      })  
    }
    //更新保存
     export function spendDataSave(data){
      return request({
        url:'/finance/rechargeCard/saveOrUpdateFee',
        method:'post',
        data:data
      })
     }