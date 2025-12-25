// 流失客户  开发人：麦沃德科技 清风/暴雨
import request from '@/utils/request'

// 流失客户-根据年份获取客户
export function getListData(query) {
  return request({
    url: '/sell/customer/lose/page',
    method: 'get',
    params: query
  })
}

// 返回年份
export function getAllYear() {
  return request({
    url: '/sell/customer/getAllYear' ,
    method: 'get'
  })
}

// 查看
export function info(id) {
  return request({
    url: '/sell/sellpact/loseCust/' + id ,
    method: 'get'
  })
}
