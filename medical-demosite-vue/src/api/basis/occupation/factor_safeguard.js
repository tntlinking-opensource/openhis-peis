// 危害因素标准范围维护 开发人：麦沃德科技 予安
import request from '@/utils/request'

// 查询JC危害因素取值范围列表
export function listWhysqzfw(query) {
  return request({
    url: '/whysqzfw/getQzfwData',
    method: 'get',
    params: query
  })
}

// 查询JC危害因素取值范围详细
export function getWhysqzfw(id) {
  return request({
    url: '/whysqzfw/' + id,
    method: 'get'
  })
}

// 新增JC危害因素取值范围
export function addWhysqzfw(data) {
  return request({
    url: '/whysqzfw/saveOrUpdate',
    method: 'post',
    data: data
  })
}

// 修改JC危害因素取值范围
export function updateWhysqzfw(data) {
  return request({
    url: '/medical/whysqzfw',
    method: 'put',
    data: data
  })
}

// 删除JC危害因素取值范围
export function delWhysqzfw(id) {
  return request({
    url: '/whysqzfw/' + id,
    method: 'delete'
  })
}
  //获取数据 
  export function getWhysData(){
    return request({
      url:'/whysqzfw/getAllHarmname',
      method:'get',
      params:{size:999}
    })
  }
 //获取接口编码
 export function getInterFaceCode()
 {
  return request({
    url:'/whysqzfw/getXmData',
    method:'get',
    
  })
 }