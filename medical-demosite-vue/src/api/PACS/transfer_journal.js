//  PACS传图日志  开发人：麦沃德科技 予安
import request from '@/utils/request'


// 分页查询
export function getPage(params) {
  return request({
    url: "/pacs/failedDicomFile/fail/page",
    method: "get",
    params
  })
}

// 提交处理
export function dealApi(rkeys) {
  return request({
    url: "/pacs/failedDicomFile/fail/deal/" + rkeys,
    method: "get",
    params: {
      rkeys
    }
  })
}

