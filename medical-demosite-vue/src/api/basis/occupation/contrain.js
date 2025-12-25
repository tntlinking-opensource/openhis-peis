//危害因素诊断标准依据维护 开发人: 麦沃德科技 清风
import request from '@/utils/request'

//分页查询-危害因素诊断标准依据维护
export function listData(query){
    return request({
        url:'/occupationDrug/list',
        method:'get',
        params:query,
    })
}
//根据inout获取危害因素-危害因素诊断标准依据维护
export function inputQueryData(data){
    return request({
        url:'/occupationDrug/getHarmData',
        method:'get',
        params:data,
    })
}

//删除-危害因素诊断标准依据维护
export function deleteData(id){
    return request({
        url:'/occupationDrug/remove/' + id,
        method: 'delete',
    })
}

//添加-危害因素诊断标准依据维护
export function addData(data){
    return request({
        url:'/occupationDrug/saveOrUpdate',
        method: 'post',
        data:data,
    })
}

//详情-危害因素诊断标准依据维护 
export function queryData(id) {
	return request({
		url: '/occupationDrug/' + id,
		method: 'get'
	})
}