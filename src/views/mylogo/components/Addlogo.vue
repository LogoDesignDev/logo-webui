<template>
  <el-dialog title="添加" :visible.sync="AddLogoVisible" :before-close="modalClose"
  :modal-append-to-body="false" :close-on-click-modal="false">
    <el-image :src="previewImageSrc" class="preImg" :fit="fit"></el-image>
    <el-input
      placeholder="请输入Logo名称"
      v-model="input"
      clearable>
    </el-input>
    <el-button>
      <input type="file" @change="displayImage" ref="fileInput">
    </el-button>
    <div slot="footer" class="dialog-footer">
      <el-button @click="modalClose">取 消</el-button>
      <el-button type="primary" @click="uploadImage">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getToken } from 'utils/auth'
import { upload, addlogo } from 'api/mylogo'
import store from 'store'
export default {
  name: 'DetailAdd',
  props: {
    AddLogoVisible: {
      type: Boolean,
      default: false
    },
    galleryid: {
      type: Number
    }
  },
  data () {
    return {
      previewImageSrc: null,
      file: null,
      fit: 'contain',
      input: ''
    };
  },
  methods: {
    displayImage () {
      const file = this.$refs.fileInput.files[0]
      this.file = file
      const fr = new FileReader()
      fr.onload = (e) => {
        this.previewImageSrc = e.target.result
      }
      fr.readAsDataURL(file)
    },
    uploadImage () {
      const formdata = new FormData()
      formdata.append('file', this.file)
      upload(formdata).then(this.AddLogo)
    },
    AddLogo (res) {
      const data = res.data
      console.log(data.url)
      if (data.code === 200) {
        const postdata = {
          token: getToken(),
          galleryid: this.$props.galleryid,
          name: this.input,
          author: store.state.userInfo.username,
          imgUrl: 'http://47.115.52.184:8900/' + data.url
        }
        addlogo(postdata).then(this.handleAddLogoSucc)
      }
    },
    handleAddLogoSucc (res) {
      res = res.data
      const code = res.code
      this.$emit('Add-succ', code)
    },
    modalClose () {
      this.$emit('Add-cancel')
    }
  }
}
</script>

<style lang="less" scoped>
  .preImg {
    width: 50px;
    height: 50px;
  }
</style>
