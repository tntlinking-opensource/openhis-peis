<!-- 团检订单折扣 - 查看 开发人：麦沃德科技暴雨 -->
<template>
	<div>
		<!-- 添加或修改对话框 -->
		<el-dialog :title="title" :visible.sync="open" class="add-charge" width="45%" append-to-body
			:close-on-click-modal="false">
			<el-form ref="form" :model="form" :inline="true" label-width="145px" hide-required-asterisk>
        <el-form-item label="合同编号" prop="htbh">
          <el-input v-model="form.htbh" placeholder="暂无数据" :readonly="true" clearable style="width: 236px"/>
        </el-form-item>
        <el-form-item label="订单号" prop="ddh">
					<el-input v-model="form.ddh" placeholder="暂无数据" :readonly="true" clearable style="width: 236px"/>
				</el-form-item>
        <el-form-item label="订单名称" prop="ddmc">
          <el-input v-model="form.ddmc" placeholder="暂无数据" :readonly="true" clearable style="width: 236px"/>
        </el-form-item>
        <el-form-item label="订单折扣" prop="ddzk">
          <el-input v-model="form.ddzk" placeholder="暂无数据" :readonly="true" clearable style="width: 236px"/>
        </el-form-item>
        <el-form-item label="客户单位名称" prop="khdwmc">
          <el-input v-model="form.khdwmc" placeholder="暂无数据" :readonly="true" clearable style="width: 236px"/>
        </el-form-item>
        <el-form-item label="客户单位电话" prop="khdwdh">
          <el-input v-model="form.khdwdh" placeholder="暂无数据" :readonly="true" clearable style="width: 236px"/>
        </el-form-item>
        <el-form-item label="销售经理" prop="xsjl">
          <el-input v-model="form.xsjl" placeholder="暂无数据" :readonly="true" clearable style="width: 236px"/>
        </el-form-item>
        <el-form-item label="订单创建日期" prop="cjddrq">
          <el-input v-model="form.cjddrq" placeholder="暂无数据" :readonly="true" clearable style="width: 236px"/>
        </el-form-item>
        <el-form-item label="计划开始日期" prop="jhjqc">
          <el-input v-model="form.jhjqc" placeholder="暂无数据" :readonly="true" clearable style="width: 236px"/>
        </el-form-item>
        <el-form-item label="计划结束日期" prop="jhjqd">
          <el-input v-model="form.jhjqd" placeholder="暂无数据" :readonly="true" clearable style="width: 236px"/>
        </el-form-item>
      </el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="cancel">关 闭</el-button>
			</div>
		</el-dialog>
	</div>
</template>
<script>
import { getItems, addItems, updateItems, getListDatas , getItemsBarcodeClass } from "@/api/basis/charge";
import { listBasexamltem } from "@/api/basis/inspect";
import pinyin from "@/utils/pinyin.js";
import { getview } from '@/api/finance/sales_accounting/order_discount'
export default {
	components: {},
	props: [],
	data() {
		return {
      title:"",
      open:false,
			rowData: undefined,
			inspectChargeList: [],
      forms:{
        id:0
      },
      form:{
        fzx:undefined,
        ddh:undefined,
        ddmc:undefined,
        ddzk:undefined,
        khdwmc:undefined,
        khdwdh:undefined,
        xsjl:undefined,
        cjddrq:undefined,
        jhjqc:undefined,
        jhjqd:undefined
      }
		};
	},
	computed: {},
	watch: {},
	created() { },
	mounted() { },
	methods: {
    // 添加
    handleAdd(id) {
      this.forms.id=id.toString()
     
      getview(this.forms).then(response => {
        this.form = response.data;
      });
      this.open = true;
      this.title = "查看订单折扣";
    },
    //取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
	},


};
</script>
<style lang="scss">
.add-charge {
	.el-dialog {
		min-width: 300px;
	}

	.el-form-item {
		margin-bottom: 20px;
	}

	.add-table {
		border: 1px solid #D4D6D9;
		min-width: 1342px;

		.el-form {
			padding: 16px 16px 0;

			.el-form-item {
				margin-bottom: 24px;
			}
		}

		.el-table__empty-block {
			width: 100% !important;
			min-height: 120px;
		}

		.el-table__body-wrapper {
			min-height: 120px;
		}

		.el-pagination {
			padding: 16px 6px 24px;
		}

		.flex {
			display: flex;
		}

		.left-table {
			width: 35%;
			min-width: 600px;
			max-height: 600px;
			margin: -1px;
		}

		.center-btn {
			width: 140px;
			background: #fff;
			height: auto;
			border: 1px solid #dfe6ec;
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;

			.el-icon--left {
				transform: rotate(-90deg);
			}

			.el-icon--right {
				transform: rotate(90deg);
			}
		}

		.right-table {
			width: calc(65% - 140px);
			min-width: 600px;
			max-height: 600px;
			flex: 1;
			margin: -1px;
		}
	}
}
</style>
