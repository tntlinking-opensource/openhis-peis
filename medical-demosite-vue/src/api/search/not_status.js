// 未检项目查询 开发人：麦沃德科技 暴雨
import request from '@/utils/request'

// 分页查询
export function getListData(params) {
  return request({
    url: '/query/finishStatus/page',
    method: 'get',
    params
  })
}

// 点击获取收费项目信息
export function getRListData(params) {
  return request({
    url: '/query/finishStatus/getChargeData',
    method: 'get',
    params
  })
}
