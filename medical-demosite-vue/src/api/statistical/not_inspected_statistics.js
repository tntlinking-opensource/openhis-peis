// 已登记未检客户统计  开发人:麦沃德科技 予安
import request from '@/utils/request'

// 分页查询
export function getListData(params){
  return request({
    url:'/statistics/registrationNotCheck/page',
    method:"get",
    params
  })
}
