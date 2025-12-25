// 体检状态统计  开发人：麦沃德科技暴雨
import request from '@/utils/request'

// 查询收费项目分类设置列表
export function getListdata(query) {
  return request({
    url: '/statistics/examinerStatus/page',
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

