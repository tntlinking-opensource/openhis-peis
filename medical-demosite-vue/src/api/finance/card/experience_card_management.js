
// 卡发放 开发人：麦沃德矢北 
import request from '@/utils/request'

 export function getListData(query){
   return request({
    url:'/finance/sendCard/page',
    method:'get',
    params:query
   })
 }
  //获取下拉卡类型数据
  export function cardTypeData(query){
    return request({
      url:'/finance/sendCard/getTypeData',
      method:'get',
      params:query
    })
  }
    //获取卡类型改变后的数据
    export function cardChangeData(query){
      return request({
        url:'/finance/sendCard/getChangeData',
        method:'get',
        params:query
      })
    }
    
    //修改领取人
    export function reviseGetter(data){
      return request({
        url:'/finance/sendCard/updateGotMan',
        method:'put',
        params:data
      })
    }
    //删除卡
    export function deleteCard(ids){
      return request({
        url:'/finance/sendCard/remove',
        method:'delete',
        params:ids
      })
    }
      //新增卡保存
      export function addCard(data){
        return request({
          url:'/finance/sendCard/saveOrUpdate',
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