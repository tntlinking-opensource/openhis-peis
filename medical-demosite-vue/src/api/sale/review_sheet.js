// 复查单打印  开发人：麦沃德科技半夏
import request from '@/utils/request'

// 分页查询
export function listPrint(query) {
	return request({
		url: '/crm/recheckePrint/page',
		method: 'get',
		params: query
	})
}

// 打印
export function printData(query) {
	return request({
		url: `/crm/recheckePrint/printData?ids=${query}`,
		method: 'get'
	})
}