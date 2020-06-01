<template>
  <el-dialog title="添加" :visible.sync="AddLogoVisible" :before-close="modalClose"
  :modal-append-to-body="false" :close-on-click-modal="false">
    <el-upload
      action="https://jsonplaceholder.typicode.com/posts/"
      list-type="picture-card"
      :on-preview="handlePictureCardPreview"
      :on-remove="handleRemove">
      <i class="el-icon-plus"></i>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible" append-to-body="true">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
    <div slot="footer" class="dialog-footer">
      <el-button @click="modalClose">取 消</el-button>
      <el-button type="primary" @click="modalClose">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import axios from 'axios'
import { getToken } from 'utils/auth'
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
      dialogImageUrl: '',
      dialogVisible: false
    };
  },
  methods: {
    modalClose () {
      this.$emit('Add-cancel')
    },
    handleRemove (file, fileList) {
      console.log(file, fileList)
    },
    handlePictureCardPreview (file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },
    AddLogo () {
      const postdata = {
        name: this.form.name,
        galleryid: this.$props.galleryid,
        token: getToken()
      }
      axios.post('api/mylogo/addlogo', postdata,
        {
          headers: {
            'Content-Type': 'application/json'
          }
        }).then(this.handleAddLogoSucc)
    },
    handleAddLogoSucc (res) {
      res = res.data
      const code = res.code
      this.$emit('Add-succ', code)
    }
  }
}
</script>

<style lang="less" scoped>

</style>
