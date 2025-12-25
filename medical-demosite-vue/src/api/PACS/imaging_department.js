// 影像科室  开发人：麦沃德科技予安
import request from '@/utils/request'

// 获取科室列表
export function getListData(query) {
  return request({
    url: '/pacs/sysytem/pacsAbteilungs/abteilung/list',
    method: 'get',
    params: query
  })
}

