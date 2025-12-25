<template>
  <!-- 添加弹窗 -->
  <el-dialog title="推荐项目" :visible.sync="open" width="1780px" append-to-body style="overflow: hidden">
    <!-- 筛选 -->
    <el-form ref="queryForm" size="small" :inline="true">
      <el-form-item label="收费项目输入码" prop="xmsrm">
        <el-input v-model="queryParams.xmsrm" placeholder="请输入收费项目输入码" clearable style="width: 230px" @keyup.enter.native="handleSearch"></el-input>
      </el-form-item>
      <el-form-item label="价格从" prop="price_from">
        <el-input type="number" v-model="queryParams.price_from" clearable style="width: 120px" @keyup.enter.native="handleSearch"> </el-input>
      </el-form-item>
      <el-form-item label="到" prop="price_to">
        <el-input type="number" v-model="queryParams.price_to" clearable style="width: 120px" @keyup.enter.native="handleSearch"> </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="handleSearch">查询</el-button>
        <el-button type="warning" plain icon="el-icon-folder-add" size="mini" @click="handleQuestion">问卷</el-button>
      </el-form-item>
    </el-form>
    <el-row type="flex" justify="space-between" style="width: 100%">
      <!-- 左侧表格 -->
      <slot name="recommendLeft"></slot>

      <!-- 中间移入移除 -->
      <!-- <el-col :span="2" class="middle">
        <div class="center-btn" :style="{ '--theme': theme }">
          <el-button type="primary" plain size="mini" @click="moveInData" :style="{ '--theme': theme }">移入选项<i class="el-icon-upload2 el-icon--right"></i></el-button>
          <el-button type="primary" plain size="mini" @click="moveOutData" style="margin: 24px 0 0 !important" :style="{ '--theme': theme }"><i class="el-icon-upload2 el-icon--left"></i>移出选项</el-button>
        </div>
      </el-col> -->

      <!-- 右侧表格 -->
      <el-col :span="13" style="border: 1px solid #d4d6d9">
        <div class="grid-content bg-purple-light">
          <el-table :data="tempList" stripe @selection-change="handleSelectionChangeRight" border max-height="600">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="序列" align="center" width="100px" type="index" />
            <el-table-column label="收费项目名称" prop="sfxmmc" align="center" show-overflow-tooltip width="140px" />
            <el-table-column label="价格" prop="jg" align="center" show-overflow-tooltip width="80px" />
            <el-table-column label="检查意义" prop="jcyy" align="center" />
            <el-table-column label="科室" prop="ssks" align="center" show-overflow-tooltip width="100px" />
            <el-table-column label="查看图片" prop="ssks" align="center" show-overflow-tooltip width="120px">
              <template slot-scope="scope">
                <div @click="checkPic(scope.row.id)" class="checkPic">查看图片</div>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-col>
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>

    <!-- 问卷弹窗 -->
    <el-dialog title="问卷" :visible.sync="questionDialog" append-to-body width="550px">
      <div class="questionBox" v-for="item in questionList" :key="item.id">
        <div style="font-size: 18px; margin-bottom: 5px">{{ item.title }}</div>
        <el-checkbox-group v-model="item.answer" style="padding: 10px">
          <el-checkbox v-for="(val, index) in item.list" :key="index" border :label="val.label" style="font-size: 18px; margin-bottom: 5px; margin-left: 0">{{ val.label }} </el-checkbox>
        </el-checkbox-group>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitQuestion">提 交</el-button>
        <el-button type="primary" plain @click="resetQuestion">重 置</el-button>
        <el-button @click="cancelQuestion">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 查看图片弹窗 -->
    <el-dialog title="查看图片" :visible.sync="picDialog" append-to-body width="550px">
      <div class="">暂未查询到相关图片</div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="picDialog = false">确 定</el-button>
      </div>
    </el-dialog>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      // 打开主弹窗
      open: false,
      // 查询参数
      queryParams: {
        xmsrm: undefined,
        price_from: undefined,
        price_to: undefined,
      },
      // #region 问卷相关
      // 打开问卷
      questionDialog: false,
      // 问卷中的问题
      questionList: [
        {
          id: 1,
          title: '1、现在和过去如有以下疾病，请在对应的框内打钩：',
          list: [
            {
              label: '基本正常',
              value: 1,
            },
            {
              label: '高血压',
              value: 2,
            },
            {
              label: '糖尿病(或疑似)',
              value: 3,
            },
            {
              label: '高尿酸症(包括痛风)',
              value: 4,
            },
            {
              label: '肾,脏病(肾炎 肾结石等)',
              value: 5,
            },
            {
              label: '心脏病(心肌梗塞 心律不齐等)',
              value: 6,
            },
            {
              label: '呼吸系统疾病(喘息 肺结核等)',
              value: 7,
            },
            {
              label: '癌症',
              value: 8,
            },
            {
              label: '肝脏疾病(肝炎 脂肪肝等)',
              value: 9,
            },
          ],
          answer: [],
        },
        {
          id: 2,
          title: '2、有血缘关系的家庭成员（父母 兄弟姐妹 祖父母）中，是否有人患有以下疾病，请在对应框中标记：',
          list: [
            {
              label: '基本正常',
              value: 1,
            },
            {
              label: '高血压',
              value: 2,
            },
            {
              label: '糖尿病(或疑似)',
              value: 3,
            },
            {
              label: '高尿酸症(包括痛风)',
              value: 4,
            },
            {
              label: '肾,脏病(肾炎 肾结石等)',
              value: 5,
            },
            {
              label: '心脏病(心肌梗塞 心律不齐等)',
              value: 6,
            },
            {
              label: '呼吸系统疾病(喘息 肺结核等)',
              value: 7,
            },
            {
              label: '癌症',
              value: 8,
            },
            {
              label: '肝脏疾病(肝炎 脂肪肝等)',
              value: 9,
            },
          ],
          answer: [],
        },
      ],
      // #endregion

      // 左上表格选择数据
      topSelect: [],
      // 左下表格选择数据
      belowSelect: [],

      // 右侧筛选数据
      tempList: [],
      // 右侧表格中选择的数据
      rightSelect: [],
      // 查看图片弹窗
      picDialog: false,
    }
  },
  created() {
    this.bus.$on('selectionChangeTop', (data) => {
      this.topSelect = data
    })
    this.bus.$on('selectionChangeBelow', (data) => {
      this.belowSelect = data
    })
  },
  beforeDestory() {
    this.bus.$off('selectionChangeTop')
    this.bus.$off('selectionChangeBelow')
  },
  methods: {
    // 显示弹窗
    showDialog() {
      this.open = true
    },
    // 查询分类列表
    getList() {
      // this.loading = true;
      // listPost(this.queryParams).then(response => {
      //   this.postList = response.rows;
      //   this.total = response.total;
      this.loading = false
      // });
    },
    // 搜索
    handleSearch() {
      this.getList()
    },
    // 提交表单
    submitForm() {
      this.$modal.msg('提交成功')
      this.open = false
    },
    // 取消按钮
    cancel() {
      this.open = false
    },

    // #region 问卷弹窗***********
    // 打开问卷弹窗
    handleQuestion() {
      this.questionDialog = true
    },
    // 表单重置
    resetQuestion() {
      this.questionList.forEach((el) => {
        el.answer = []
      })
    },
    // 关闭问卷弹窗
    cancelQuestion() {
      this.questionDialog = false
    },
    // 问卷提交按钮
    submitQuestion() {
      let last = this.questionList.length
      this.questionList.forEach((el, index) => {
        
        if (last != -1) {
          if (el.answer.length == 0) {
            this.$message({
              message: '请填写完整问卷',
              type: 'warning',
            })
            last = -1
          } else if (last == index + 1) {
            this.$message({
              message: '提交成功',
              type: 'success',
              duration: 500,
              onClose: () => {
                this.resetQuestion()
                this.questionDialog = false
              },
            })
          }
        } else {
          return false
        }
      })
    },
    // #endregion

    // 移入选项
    moveInData() {
      if (this.topSelect.length == 0 && this.belowSelect.length == 0) {
        this.$message({
          message: '请选择病症名称',
          type: 'warning',
        })
        return
      }
      if (this.topSelect.length != 0) {
        this.topSelect.forEach((el) => {
          if (!this.tempList.includes(el)) {
            this.tempList.push(el)
          }
        })
      }
      if (this.belowSelect.length != 0) {
        this.belowSelect.forEach((el) => {
          if (!this.tempList.includes(el)) {
            this.tempList.push(el)
          }
        })
      }
    },
    // 移出选项
    moveOutData() {
      if (this.rightSelect.length == 0) {
        this.$modal.msg('请选择数据')
        return
      }
     
      this.rightSelect.forEach((el) => {
        if (this.tempList.includes(el)) {
          this.tempList.splice(this.tempList.indexOf(el), 1)
        }
      })
    },

    // 右侧表格选中的数据
    handleSelectionChangeRight(val) {
      this.rightSelect = val.map((item) => item)
    },
    // 查看图片
    checkPic(id) {
      this.picDialog = true
    },
  },
}
</script>

<style lang="scss">
.tableList {
  overflow-y: auto;
  height: 450px;
}

.middle {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 540px;

  .move {
    display: flex;
    align-items: center;
    padding: 7px 10px;
    margin-bottom: 24px;
    background: #e8f4ff;
    border-radius: 5px;
    font-size: 12px;
    line-height: 17px;
    color: #0059ff;
    color: #{'var(--theme)'} !important;

    &:hover {
      cursor: pointer;
    }

    img {
      margin-right: 5px;
    }
  }

  .el-pagination__total {
    display: none;
  }

  .center-btn {
    background: #fff;
    height: auto;
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

  // 问卷弹窗
  .questionBox {
    font-size: 18px;
    line-height: 20px;
    color: #333333;
  }
}

.checkPic {
  color: #0059ff;

  &:hover {
    cursor: pointer;
  }
}
</style>
