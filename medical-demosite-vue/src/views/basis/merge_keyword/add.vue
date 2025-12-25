<!-- 合并结论词维护  开发人：麦沃德科技暴雨/矢北 -->
<template>
  <!-- 添加或修改对话框 -->
  <el-dialog :title="title" :visible.sync="open" class="add-items" width="80%" append-to-body
    :close-on-click-modal="false">
    <span style="font-weight: bolder;font-size: 15px">合并结论词</span>
    <el-form ref="form" :model="form" :inline="true" label-width="100px" style="margin-top: 20px" hide-required-asterisk>
      <el-form-item label="名称:" prop="mergeName">
        <el-input v-model="form.mergeName" @input="nameChange" placeholder="请输入名称" clearable style="width: 610px" />
      </el-form-item>
      <el-form-item label="输入码:" prop="inputCode">
        <el-input v-model="form.inputCode" placeholder="请输入输入码" clearable style="width: 610px" />
      </el-form-item>
      <el-form-item label="总检建议:" prop="suggestion">
        <el-input v-model="form.suggestion" placeholder="请输入总检建议" resize="none" clearable style="width: 610px"
          type="textarea" :rows="3" />
      </el-form-item>
      <el-form-item label="团检建议:" prop="tjjy">
        <el-input v-model="form.tjjy" placeholder="请输入团检建议" resize="none" clearable style="width: 610px" type="textarea"
          :rows="3" />
      </el-form-item>
    </el-form>
    <div class="add-table" style="height: 400px; padding: 0; overflow: auto">
      <!-- 操作按钮 -->
      <el-row :gutter="10" class="table-btn">
        <el-col :span="1.5">
          <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAddRow">新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple"
            @click="handleDeleteRow">删除
          </el-button>
        </el-col>
      </el-row>
      <!-- 表格 -->
      <div class="table-box">
        <el-table border :data="tableList" @selection-change="handleSelectionChange" style="width: 100%;">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="序列" type="index" width="65" align="center" />
          <el-table-column label="结论词" prop="name" align="center" min-width="30px">
            <template slot-scope="scope">
              <input-select ref="inputSelect" :selectData="selectData" @change="selectChange"
                :initialValue="scope.row.name" :currentIndex="scope.row.index"></input-select>
            </template>
          </el-table-column>
          <el-table-column label="科室" prop="depName" align="center" min-width="30px" />
        </el-table>
      </div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button type="primary" plain @click="reset">重 置</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
// import { getPrinttype, listPrinttype, addPrinttype, updatePrinttype, delPrinttype } from "@/api/basis/charge";
import pinyin from "@/utils/pinyin.js";
import { addbasMerge, getbasMerge, updatebasMerge, getAddExpendData } from '@/api/basis/mergekeyword'
export default {
  components: {},
  props: [],
  data() {
    return {
      // 输入码选择
      selectData: {
        placeholder: '请空格或输入输入码',
        key: '输入码',//第一列标题
        value: '结论词',//第二列标题
        url: '/basconclusion/getConclusion',//请求连接
        // selectWidth: ,//选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(必传)
        firstName: 'inputCode',//接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'name',//接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key',//向接口传递的参数名(不传默认为'inputCode')
      },
      // 关联搜索数据
      searchData: {
        placeholder: '',
        inputTitle: '',// 搜索标题
        inputPlaceholder: '',// 搜索placeholder
        key: '',//第一列标题
        value: '',//第二列标题
        url: '',//请求连接
      },
      query: {
        basMerge: {},
        basconclusionList: [],
      },
      // 体检类型列表
      typeOptions: [
        {
          id: 0,
          text: "健康体检"
        },
        {
          id: 1,
          text: "职业体检"
        },
        {
          id: 2,
          text: "综合"
        }
      ],

      // 表格列表
      tableList: [],
      // 非多个禁用
      multiple: true,
      // 选中数组
      ids: [],
      // 表单参数
      form: {
        depName: undefined
      },
      deleteList: [],
      // 弹出层标题
      title: "",
      // 弹出层加载动画
      popLoading: false,
      // 弹出层临时数据
      popData: undefined,
      // 是否显示弹出层
      open: false,
      // 表单校验
      rules: {
        mergeName: [{ required: true, message: "名称不能为空", trigger: "change" }],
        inputCode: [{ required: true, message: "输入码不能为空", trigger: "change" }],
        suggestion: [{ required: true, message: "总检建议不能为空", trigger: "change" }],
        tjjy: [{ required: true, message: "团检建议不能为空", trigger: "change" }],
      },
    };
  },
  computed: {},
  watch: {},
  created() {


  },
  mounted() { },
  methods: {
    // 输入码选择
    divisionIdChange(value) {
 
      this.form.divisionId = value
    },
    //组件选中赋值
    selectChange(value, index) {
      this.tableList[index].depName = value.depName
      this.tableList[index].conclusionId = value.id
      this.tableList[index].conName = value.name
    },
    // 搜索选择
    searchChange(value) {
      this.form.devicetypePositionCheckitem = value
    },
    // 添加
    handleAdd() {
      this.popData = undefined
      this.reset();
      this.open = true;
      this.title = "新增合并结论词";
    },
    // 编辑
    handleUpdate(row, ids) {
      this.open = true;
      this.popData = undefined
      this.reset();
      const id = row.id || ids
      this.title = "编辑合并结论词";
      this.popLoading = true
      const query = {
        id: id[0]
      }
      getAddExpendData(query).then(response => {
        this.tableList = response.data
        for (var i in this.tableList) {
          this.tableList[i].index = i
          this.tableList[i].conclusionId=this.tableList[i].id
        }
        this.loading = false;
      }),
        getbasMerge(query.id).then(response => {
          this.popData = JSON.parse(JSON.stringify(response.data))
          this.popLoading = false
          this.form = response.data;
        });
    },
    // 增加行
    handleAddRow() {
      var list = {
        depName: '',
        name: '',
        state: 'added',
      }
      this.tableList.push(list)
      for (var i in this.tableList) {
        this.tableList[i].index = i
      }
    },
    // 删除选中
    handleDeleteRow() {
     
      this.ids.forEach((val) => {
        this.tableList.forEach((item, i) => {
          if (item.id == val) {
            this.tableList[i].state = 'removed'
            this.deleteList.push(this.tableList[i])
            this.$delete(this.tableList, i)
          }
        })
      })
      for (var i in this.tableList) {
        this.tableList[i].index = i
      }
    },
    // 关键词单位名称改变
    nameChange(value) {
      // this.repeatName()
      this.form.inputCode = pinyin(value)
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.multiple = !selection.length;
    },
    // 表单重置
    reset() {
      // if (this.popData) {
      //   this.form = JSON.parse(JSON.stringify(this.popData))
      //   return
      // }
      this.form = {
        name: null,
        inputCode: null,
        suggestion: null,
        tjjy: null,
      };
      this.tableList = []
      this.resetForm("form");
    },
    // 打印项目分类名称改变
    nameChange(value) {
      this.form.inputCode = pinyin(value)
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },

    // 提交按钮
    submitForm() {
      this.query.basconclusionList = JSON.parse(JSON.stringify(this.deleteList))
        this.query.basMerge = this.form
      for (var i in this.tableList) {
        this.query.basconclusionList.push(this.tableList[i])
      }
      
           ///处理所有数组
           for (var i in this.query.basconclusionList) {
        if (this.query.basconclusionList[i].state) {

        } else {
          this.query.basconclusionList[i].state = 'modified'
        }
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {

            updatebasMerge(this.query).then(response => {
              this.query.basconclusionList=[],
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.$emit('getList')
            });
          } else {
            addbasMerge(this.query).then(response => {
              this.query.basconclusionList=[],
              this.$modal.msgSuccess("添加成功");
              this.open = false;
            });
          }
        }
      });
    },
  },
};
</script>
<style lang="scss">
.add-items {
  .el-dialog {
    min-width: 800px;
  }

  .el-form-item {
    margin-bottom: 20px;
  }

  .add-table {
    border: 1px solid #D4D6D9;

    .table-btn {
      margin: 0 !important;
      padding: 16px 20px;
    }

    .el-table--enable-row-hover .el-table__body tr:hover>td.el-table__cell {
      background: transparent;
    }

    .el-input__inner {
      padding: 0 8px;
      border-width: 0;
      text-align: center;
      background: transparent;

      &:focus {
        border-width: 1px;
      }
    }
  }
}
</style>
