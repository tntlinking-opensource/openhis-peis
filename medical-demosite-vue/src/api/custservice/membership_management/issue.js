// 中心会员 开发人：麦沃德科技矢北
import request from '@/utils/request'

// 分页查询
export  function getListData(query){
  return request({
    url:'/member/card/page',
    method:'get',
    params:query
  })
}
 //删除
 export function deleteList(ids)
 {
  return request({
    url:'/finance/sendCard/remove',
    method:'delete',
    params:ids
  })
 }
 //获取卡类型改变后的字段信息
  export function getCardDetail(query)
  {
    return request({
      url:'/finance/sendCard/getChangeData',
      method:'get',
      params:query
    })
  }
  //保存 
  export function saveCard(data)
  {
    return request({
      url:'/finance/sendCard/saveOrUpdate',
      method:'POST',
      data:data
    })
  }
