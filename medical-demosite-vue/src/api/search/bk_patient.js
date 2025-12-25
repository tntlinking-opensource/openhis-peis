// 历史档案查询 开发人：麦沃德科技 暴雨
import request from '@/utils/request'

// 分页查询
export function getListData(params) {
  return request({
    url: '/query/bkPatient/page',
    method: 'get',
    params
  })
}

