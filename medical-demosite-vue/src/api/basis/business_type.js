// 业务类型 开发人：麦沃德科技矢北
import request from '@/utils/request'

// 分页查询总检结论词列表
export function listBasConclusion(query) {
  return request({
    url: '/data/businessCat/page',
    method: 'get',
    params: query
  })
}
// 分页查询总检结论词列表
export function listDelete(ids) { 
  return request({   
    url: '/data/businessCat/'+ids,   
    method: 'delete',   
  })
}
//获取详情
export function detailData(typeId) { 
  return request({   
    url: '/data/businessCat/'+typeId,   
    method: 'get',   
  })
}
//保存
export function saveData(data) { 
  return request({   
    url: '/data/businessCat',   
    method: 'put',   
    data:data
  })
}
//更新
export function updateData(data) { 
  return request({   
    url: '/data/businessCat',   
    method: 'post',   
    data:data
  })
}
