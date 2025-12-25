// 加项工作量  开发人：麦沃德科技暴雨
import request from '@/utils/request'

// 查询收费项目分类设置列表
export function getListdata(query) {
  return request({
    url: '/statistics/totalAddWork/page',
    method: 'get',
    params: query
  })
}

//获取分中心数据
export function getBranchData() {
  return request({
    url: '/sell/sellDate/getBranchData',
    method: 'get',
  })
}
// 获取客户单位名称
export function getKhdwmcData(query) {
  return request({
    url: '/sell/createorder/getKhdwmcData',
    method: 'get',
    params: query
  })
}

