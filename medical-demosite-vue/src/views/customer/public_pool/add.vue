<!-- 公共池-添加 麦沃德科技 开发人:清风/予安 -->
<template>
    <div class="add-container">
        <el-dialog :title="title" class="add-items" :visible.sync="open" width="860px" height="auto" append-to-body
            :close-on-click-modal="false">
            <el-form ref="addForm" :inline="true" label-width="130px" :model="form" :rules="rules">
                <el-form-item label="客户单位名称" style="margin-right:32px;" prop="khdwmc">
                    <el-input placeholder="请输入单位名称" style="width:260px;" v-model="form.khdwmc"
                        :readonly="isRead"></el-input>
                </el-form-item>
                <el-form-item label="客户单位联系人" style="margin-right:0;" prop="khdwlxr">
                    <el-input placeholder="请输入单位联系人" style="width:260px;" v-model="form.khdwlxr"
                        :readonly="isRead"></el-input>
                </el-form-item>
                <el-form-item label="客户电话" style="margin-right:32px;" prop="khdh">
                    <el-input placeholder="请输入电话" style="width:260px;" v-model="form.khdh" :readonly="isRead"></el-input>
                </el-form-item>
                <el-form-item label="所属行业" style="margin-right:0;" prop="sshy">
                    <input-select style="width:260px;" :initialValue="form.sshy" :selectData="labTypeData" v-model="form.sshy" @change="labTypeChange"
                        :readonly="isRead"></input-select>
                </el-form-item>
                <el-form-item label="销售经理" style="margin-right:32px;" prop="xsjl">
                    <el-input placeholder="请输入销售经理" style="width:260px;" v-model="form.xsjl" :readonly="isRead"></el-input>
                </el-form-item>
                <el-form-item label="单位机构代码" style="margin-right:0;" prop="dwjgdm">
                    <el-input placeholder="请输入单位机构代码" style="width:260px;" v-model="form.dwjgdm"
                        :readonly="isRead"></el-input>
                </el-form-item>
                <el-form-item label="客户单位地址" prop="khdwdz">
                    <el-input type="textarea" placeholder="请输入" style="width:680px;" v-model="form.khdwdz"
                        :readonly="isRead"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="onOk()">保存</el-button>
                <el-button @click="onCancel()">取消</el-button>
            </div>
        </el-dialog>

    </div>
</template>
<script>
import { updatePool, AddPool,getDetail } from '@/api/customer/public_pool.js'
export default {
    data() {
        return {
            open: false,
            // 所属行业
            labTypeData: {
                placeholder: '请输入输入码选择',
                key: '行业码',
                value: '行业名称',
                url: '/crm/clientcommon/getHymcData',
                bindValue: '', //初始值(必传)
                firstName: 'id',
                secondName: 'hymc',
            },
            form: {
                khdwmc: "",
                khdwlxr: "",
                khdh: "",
                sshy: "",
                xsjl: "",
                dwjgdm: "",
                khdwdz: "",
            },
            title: "",
            isRead: false,
            rules: {
                khdwmc: [{ required: true, message: "请输入客户单位名称", trigger: "blur" }],
                khdwlxr: [{ required: true, message: "请输入客户单位联系人", trigger: "blur" }],
                sshy: [{ required: true, message: "请输入行业", trigger: "blur" }],
                khdh: [{ required: true, message: '手机号必填', trigger: 'blur' },
                { pattern: /^1[3456789]\d{9}$/, message: '手机号码格式不正确', trigger: 'blur' }
                ],
            }
        }
    },
    methods: {
        addWindow(context, row, dataList) {
         
            this.$nextTick(() => {
                if (this.$refs.addForm !== undefined) {
                    this.$refs.addForm.clearValidate()
                }
            })
            if (context == 1) {
                // 新增
                this.title = '新增';
                this.form = {
                    khdwmc: "",
                    khdwlxr: "",
                    khdh: "",
                    sshy: "",
                    xsjl: "",
                    dwjgdm: "",
                    khdwdz: "",
                };
                this.isRead = false;
            } else if (context == 0) {
                // 编辑
             
                this.title = '编辑';
                getDetail(row).then(response=>{
             
                    this.form=response.data
                })
                
                this.isRead = false;

            } else if (context == 2) {
                // 查看
                let object = {};
                this.title = '查看';
                object = row;
                object.xsjl = row.xsjl;
                this.form = object;
                this.isRead = true;
            }
            this.open = true;
        },
        // 科室选项
        labTypeChange(value) {
      
            this.form.sshy = value.id
            this.labTypeData.bindValue = value.hymc;
        },
        onOk() {
            this.$refs["addForm"].validate(valid => {
                if (valid) {
                    if (this.form.id != null) {
                        updatePool(this.form).then(response => {
                            this.$modal.msgSuccess("修改成功");
                            this.open = false;
                            this.$emit("getList")
                        });
                    } else {
                        AddPool(this.form).then(response => {
                            this.$modal.msgSuccess("添加成功");
                            this.open = false;
                            this.$emit("getList")
                        });
                    }
                }
            });
        },
        onCancel() {
            this.open = false;
        }
    }
}
</script>
