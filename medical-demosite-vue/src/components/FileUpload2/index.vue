<template>
  <div class="stencil-upload-files" :style="{ width: uploadWidth ? uploadWidth : '300px' }">
    <!-- 模板1-输入框单选 -->
    <el-upload ref="upload" class="upload-files-input" action="" :show-file-list="false" :on-change="uploadChange" :http-request="handelUpload" :on-remove="uploadRemove" :auto-upload="false" :style="'width' + uploadWidth" v-if="uploadModel == 1">
      <el-input style="width: 100%" :value="showText">
        <el-button type="primary" slot="append">浏览</el-button>
      </el-input>
    </el-upload>
    <!-- 模板2-按钮 -->
    <div class="upload-files-button" v-else-if="uploadModel == 2">
      <el-upload ref="upload" action="" :multiple="uploadData.multiple || false" :limit="uploadData.limit" :show-file-list="false" :auto-upload="uploadData.autoUpload || false" :on-change="uploadChange" :on-exceed="uploadExceed" :http-request="handelUpload" :on-remove="uploadRemove">
        <el-button type="primary" @click="checkFiles">选择文件</el-button>
      </el-upload>
      <div class="files-list" v-if="files.length > 0">{{ files.length > 1 ? '已选择个' + files.length + '文件' : files[0].name }}</div>
    </div>
    <!-- 常规模板 -->
    <el-upload
      ref="upload"
      class="upload-files"
      drag
      action=""
      :multiple="uploadData.multiple || false"
      :limit="uploadData.limit"
      :on-change="uploadChange"
      :on-exceed="uploadExceed"
      :http-request="handelUpload"
      :on-remove="uploadRemove"
      :auto-upload="uploadData.autoUpload || false"
      :style="'width' + uploadWidth"
      v-else
    >
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <div class="upload-tip" v-if="uploadData.limit">最多可上传{{ uploadData.limit }}个附件</div>
    </el-upload>
  </div>
</template>

<script>
// uploadData: {
//   url: '',//文件上传地址
//   multiple: false,//是否可以上传多个
//   limit: 1,//文件上传个数限制
//   fileType: [],//文件类型
//   data: {},//上传时附带的额外参数
//   autoUpload:false,
// }
// uploadWidth: 300px,//组件宽度
// uploadModel: '',//特殊模板

//导入
// import UploadFile from '@/components/UploadFile'
// components: {
//    UploadFile
// },

// 使用
// <upload-file :uploadData="uploadData" @uploadFinish="uploadFinish" uploadWidth="300px"></upload-file >
// 上传文件成功
// uploadFinish(val，res) {
//   this.参数 = val
// },
import request from '@/utils/request2'
export default {
  props: ['uploadData', 'uploadWidth', 'uploadModel', 'requestType'],
  data() {
    return {
      // 已选择文件
      files: [],
      // 显示文字
      showText: '',
      // 是否重置文件列表
      isReset: false,
      // 临时数据
      tempData: {},
    }
  },
  methods: {
    // 选择文件
    checkFiles() {
      this.isReset = true
    },
    // 文件改变
    uploadChange(file) {
      if (this.isReset) {
        this.files = []
        this.showText = ''
        this.$refs.upload.clearFiles()
        this.isReset = false
      }
      if (!this.checkType(file)) {
        this.$refs.upload.clearFiles()
        this.files = []
        this.showText = ''
        return this.$message.error(`文件格式不正确, 请上传${this.uploadData.fileType.join('/')}格式文件!`)
      } else {
        if (this.uploadData.multiple) {
          this.files.push(file)
          if (this.showText) this.showText += '、' + file.name
          else this.showText = file.name
        } else {
          this.files[0] = file
          this.showText = file.name
        }
      }
      if (this.uploadData.autoUpload) {
        this.$emit('handleUploading')
      }
    },
    // 校检文件类型
    checkType(file) {
      if (this.uploadData.fileType && this.uploadData.fileType.length) {
        let fileExtension = ''
        if (file.name.lastIndexOf('.') > -1) {
          fileExtension = file.name.slice(file.name.lastIndexOf('.') + 1)
        }
        const isTypeOk = this.uploadData.fileType.some((type) => {
          if (file.raw.type.indexOf(type) > -1) return true
          if (fileExtension && fileExtension.indexOf(type) > -1) return true
          return false
        })
        if (!isTypeOk) {
          return false
        }
      }
      return true
    },
    // 上传请求
    handelUpload(data) {
      this.tempData = data
      let num = 0
      this.files.map((file) => {
        let formData = new FormData()
        formData.append('files', file.raw)
        for (var key in this.uploadData.data) {
          formData.append(key, this.uploadData.data[key])
        }
        request({
          url: this.uploadData.url,
          method: 'post',
          data: formData,
        })
          .then((res) => {
            num++
            if (num != this.files.length) {
              return
            }
            if (this.requestType == 2 && res === undefined) {
              this.$emit('uploadFinish', 1, { data: false, timeout: true })
            } else {
              this.$emit('uploadFinish', 1, res, this.tempData)
            }
          })
          .catch((err) => {
            this.$emit('uploadFinish', 0, err, this.tempData, formData)
          })
      })
    },
    // 文件超出
    uploadExceed() {
      this.$modal.msgError(`上传文件数量不能超过 ${this.uploadData.multiple ? this.uploadData.limit : 1} 个!`)
    },
    // 删除文件
    uploadRemove(file) {
      let index = this.files.findIndex((item) => {
        if (file.uid == item.uid) {
          return true
        }
      })
      this.$delete(this.files, index)
    },
    // 判断是否可以上传
    isUpload() {
      if (this.files.length == 0) {
        return '请选择要上传的文件！'
      }
      return true
    },
    // 重置
    resetUpload() {
      this.$refs.upload.clearFiles()
      this.files = []
      this.showText = ''
    },
  },
}
</script>

<style lang="scss">
.stencil-upload-files {
  .el-upload-dragger {
    border: 1px dashed #d4d6d9;
    border-radius: 8px;
    width: 100%;
    height: 100%;
    padding: 32px;
  }

  .el-upload__text {
    font-size: 14px;
    line-height: 14px;
    color: #333333;
  }

  .upload-tip {
    font-size: 12px;
    line-height: 12px;
    color: #bbbdbf;
    margin-top: 16px;
  }

  .el-icon-upload {
    margin-top: 0 !important;
  }

  .el-upload-list {
    overflow: auto;
    max-height: 200px;

    .el-upload-list__item:first-child {
      margin-top: 0;
    }
  }

  .upload-files-input {
    .el-input-group__append {
      background: #1890ff;
      border-color: #1890ff;
      color: #fff;
    }
  }

  .upload-files-button {
    display: flex;
    align-items: center;

    .el-upload {
      width: 98px;

      .el-button {
        width: 100%;
      }
    }

    .files-list {
      margin-left: 8px;
      flex: 1;
      display: -webkit-box;
      -webkit-box-orient: vertical;
      overflow: hidden;
      -webkit-line-clamp: 1;
    }
  }
}
</style>
