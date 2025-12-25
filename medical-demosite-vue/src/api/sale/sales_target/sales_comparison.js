import request from '@/utils/request'

// 获取分中心数据
export function getBranchData() {
  return request({
    url: '/sell/sellDate/getBranchData',
    method: 'get',
  })
}

// 获取对比数据
export function getDataApi(url, params) {
  return request({
    url,
    method: 'get',
    params
  })
}