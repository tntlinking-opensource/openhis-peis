<!-- 健康总检-查看详情-科室图片 麦沃德科技 开发人:清风 -->
<template>
	<div class="add-container" style="min-height:700px">
		<div v-if="form.length > 0">
				<!-- 第一次循环,拆分form -->
			<div v-for="(item, index) in form" :key="index">
				<!-- 第二次循环,拆分标题和图片路径 -->
				<div style="padding: 12px; font-size:18px; font-weight:600"> {{ item.key }}	</div>
				<div v-for="child,ci in item.value" :key="ci" style="display: inline-block; margin: 10px;">
					<el-image style="width: 200px; height: 200px;" :preview-src-list="getPreviewList(item.value)" :src="imgPath + child" v-if="child" fit="cover"></el-image>
				</div>
			</div>
		</div>
		<div v-else style="text-align:center;">
			体检号 {{patientno}} 还没有上传过图片!
		</div>
	</div>
</template>

<script>
import Cookies from "js-cookie";
import {viewImgTotal} from "@/api/inspection/health_inspection.js"
export default {
	data() {
		return {
			form: [],
			patientno:"",
			imgPath: Cookies.get("imgPath"),
		}
	},
	methods:{
		getViewImgTotal(data){
			this.patientno = data;
			let obj = {
				patientno: data
			}
			viewImgTotal(obj).then((res)=>{
				if(res.code == 200) {
					var temList = []
					for(var key in res.data) {
						temList.push({
							key: key,
							value: res.data[key]
						})
					}
					this.form = temList;
				}
			}).catch(()=>{
			})
		},
		getPreviewList(list) {
			var previewList = []
			for(var i in list) {
				previewList[i] = this.imgPath + list[i]
			}
			return previewList
		},
	}
}
</script>