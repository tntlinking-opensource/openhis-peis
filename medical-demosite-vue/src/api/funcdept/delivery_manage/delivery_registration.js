
// 外送登记 开发人：麦沃德矢北 
import request from '@/utils/request'

// 查询数据
export function getListData(query){
  return request({
    url:'/outside/sendRegister/page',
    method:'get',
    params:query
  })
}
 //删除
export function deleteListData(ids){
  return request({
    url:'/outside/sendRegister/'+ids,
    method:'DELETE',
  })
}
// 获取上半部分数据
 export function getListDataInAddUp(query)
 {
  return request({
    url:'/outside/sendRegister/getPatientData',
    method:'get',
    params:query,
  })
 }
  //获取下半部分数据
 export function getListDataInAddDown(query)
 {
  return request({
    url:'outside/sendRegister/getItemData',
    method:'get',
    params:query
  })
 }
 // 保存
  export function saveDataList(data)
  {
    return request ({
      url:'/outside/sendRegister/saveOrUpdate',
      method:'post',
      data:data
    })
  }

