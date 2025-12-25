// 检验数据导出  开发人：麦沃德科技 暴雨
import request from '@/utils/request'

// 分页查询
export function getListData(query) {
  return request({
    url: '/abteilung/inspectionExport/page',
    method: 'get',
    params: query
  })
}

