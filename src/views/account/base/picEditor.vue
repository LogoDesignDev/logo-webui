<template>
  <div id="uploadPicDialog">
    <div id="topPicContainer">
      <div id="leftPicContainer">
        <!-- 裁剪区 -->
        <canvas
          id="picCanvasMain" width="300px" height="300px"
          @mousemove="searchArea" />
        <span class="tips">
          <i class="el-icon-warning-outline">
            仅支持JPG格式，文件小于1M
          </i>
        </span>
      </div>
      <div id="rightPicContainer">
        <canvas id="picCanvasSquare" width="120px" height="120px" />
        <span class="tips">方形头像预览</span>
        <canvas id="picCanvasCircle" />
        <span class="tips">圆形头像预览</span>
      </div>
    </div>
    <div id="bottomBtnContainer">
      <el-button id="choosePicBtn">
        <input type="file" @change="choosePic" />
        选择图片
      </el-button>
      <el-button
        type="primary" :loading="uploadLoading" :disabled="!canvasImage.img"
        @click="uploadPic">保存头像</el-button>
    </div>
  </div>
</template>

<style lang="less" scoped>
#uploadPicDialog {
  height: 450px;
}

#topPicContainer {
  height: 350px;
  display: flex;
  justify-content: space-around;
}

#bottomBtnContainer {
  height: 100px;
  display: flex;
  justify-content: center;
  align-items: flex-end;
}

#leftPicContainer {
  width: 320px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

#rightPicContainer {
  width: 120px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

#picCanvasMain {
  width: 300px;
  height: 300px;
  margin-bottom: 10px;
  border: 1px solid #EBEEF5;
  background-color: rgb(245, 245, 245);
}

#picCanvasSquare {
  width: 120px;
  height: 120px;
  margin-bottom: 10px;
  border: 1px solid #EBEEF5;
  background-color: rgb(245, 245, 245);
}

#picCanvasCircle {
  margin-top: 30px;
  width: 120px;
  height: 120px;
  border-radius: 75px;
  margin-bottom: 10px;
  border: 1px solid #EBEEF5;
  background-color: rgb(245, 245, 245);
}

#choosePicBtn {
  height: 40px;
  width: 100px;
  padding: 0;
}

#choosePicBtn input{
  height: 40px;
  width: 100px;
  color: transparent;
  opacity: 0;
  position: absolute;
  margin-left: -22px;
  margin-top: -12px;
}
</style>

<script>
import { getToken } from 'utils/auth'
import { uploadUserPic } from 'api/user'

export default {
  data () {
    return {
      uploadLoading: false,
      // 上传的图片在canvasMain中的信息
      canvasImage: {
        img: null,
        width: 0,
        height: 0,
        x: 0,
        y: 0,
        editX: 0,
        editY: 0,
        scale: 1
      },
      // 裁剪区域
      cutArea: {
        side: 0,
        x: 0,
        y: 0
      }
    }
  },

  methods: {
    /**
     * 初始化数据
     */
    init () {
      this.canvasImage.img = null
      this.canvasImage.width = 0
      this.canvasImage.height = 0
      this.canvasImage.x = 0
      this.canvasImage.y = 0
      this.canvasImage.editX = 0
      this.canvasImage.editY = 0
      this.canvasImage.scale = 0

      this.cutArea.side = 0
      this.cutArea.x = 0
      this.cutArea.y = 0
    },

    /**
     * 处理读取成功，进行相关计算
     */
    handleAvatarSuccess (url) {
      const image = new Image()
      this.canvasImage.img = image
      image.src = url
      image.onload = () => {
        const canvas = document.getElementById('picCanvasMain')
        const ctx = canvas.getContext('2d')

        // canvas清屏
        ctx.clearRect(0, 0, canvas.width, canvas.height)
        // 计算canvas中图片的属性
        if (image.width >= image.height) {
          this.canvasImage.width = canvas.width
          this.canvasImage.height = image.height * (canvas.width / image.width)
          this.canvasImage.x = 0
          this.canvasImage.y = (canvas.height / 2) - (this.canvasImage.height / 2)
          this.canvasImage.editY = (this.canvasImage.height / 2) - (canvas.height / 2)
        } else {
          this.canvasImage.width = image.width * (canvas.height / image.height)
          this.canvasImage.height = canvas.height
          this.canvasImage.x = (canvas.width / 2) - (this.canvasImage.width / 2)
          this.canvasImage.y = 0
          this.canvasImage.editX = (this.canvasImage.width / 2) - (canvas.width / 2)
        }
        this.canvasImage.scale = this.canvasImage.img.width / this.canvasImage.width
        // 绘制
        ctx.drawImage(
          this.canvasImage.img,
          this.canvasImage.x, this.canvasImage.y,
          this.canvasImage.width, this.canvasImage.height)
        // 绘制初始剪切区域
        this.cutArea.side = this.canvasImage.width < this.canvasImage.height ? this.canvasImage.width : this.canvasImage.height
        this.cutArea.x = this.canvasImage.x
        this.cutArea.y = this.canvasImage.y
        this.printArea()
      }
    },

    /**
     * 选择图片
     */
    choosePic (e) {
      const file = e.currentTarget.files[0]
      const isJPG = file.type === 'image/jpeg'
      const isLt1M = file.size / 1024 / 1024 < 1

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      } else if (!isLt1M) {
        this.$message.error('上传头像图片大小不能超过 1MB!')
      } else {
        const reader = new FileReader()

        reader.readAsDataURL(file)
        reader.onload = (e) => {
          this.init()
          const imgUrl = e.target.result
          this.handleAvatarSuccess(imgUrl)
        }
      }
    },

    /**
     * 绘制选中区域
     *
     * 这里面各种计算....
     * 我已经不想写注释了
     * 因为根本不想继续维护它，原谅我！！！
     */
    printArea () {
      const canvas = document.getElementById('picCanvasMain')
      const ctx = canvas.getContext('2d')

      // canvas清屏
      ctx.clearRect(0, 0, canvas.width, canvas.height)
      // 绘制图片
      ctx.drawImage(
        this.canvasImage.img,
        this.canvasImage.x, this.canvasImage.y,
        this.canvasImage.width, this.canvasImage.height)
      // 绘制矩形区域
      ctx.beginPath()
      ctx.lineWidth = '2'
      ctx.setLineDash([5, 5]) // 虚线
      ctx.strokeStyle = 'black'
      ctx.rect(
        this.cutArea.x, this.cutArea.y,
        this.cutArea.side, this.cutArea.side)
      ctx.stroke()
      // // 绘制mask区域(左)
      // ctx.beginPath()
      // ctx.fillStyle = 'rgba(0, 0, 0, 0.5)'
      // ctx.fillRect(
      //   this.canvasImage.x, this.canvasImage.y,
      //   this.cutArea.x, this.canvasImage.height)
      // ctx.stroke()
      // // 绘制mask区域(右)
      // ctx.beginPath()
      // ctx.fillStyle = 'rgba(0, 0, 0, 0.5)'
      // ctx.fillRect(
      //   this.cutArea.x + this.cutArea.side, this.canvasImage.y,
      //   this.canvasImage.width - this.cutArea.x - this.cutArea.side,
      //   this.canvasImage.height)
      // ctx.stroke()
      // // 绘制mask区域(上)
      // ctx.beginPath()
      // ctx.fillStyle = 'rgba(0, 0, 0, 0.5)'
      // ctx.fillRect(
      //   this.cutArea.x, this.canvasImage.y,
      //   this.cutArea.side,
      //   this.cutArea.y - this.canvasImage.y)
      // ctx.stroke()
      // // 绘制mask区域(下)
      // ctx.beginPath()
      // ctx.fillStyle = 'rgba(0, 0, 0, 0.5)'
      // ctx.fillRect(
      //   this.cutArea.x, this.cutArea.y + this.cutArea.side,
      //   this.cutArea.side,
      //   this.cutArea.y - this.canvasImage.y - this.canvasImage.height)
      // ctx.stroke()
      // 左下角绘制一个小正方形
      ctx.beginPath()
      ctx.lineWidth = '1'
      ctx.strokeStyle = 'white'
      ctx.fillStyle = '#409EFF'
      ctx.fillRect(
        this.cutArea.x + this.cutArea.side - 4,
        this.cutArea.y + this.cutArea.side - 4,
        8, 8)
      ctx.stroke()

      // 绘制到预览canvas
      // square
      const canvasSquare = document.getElementById('picCanvasSquare')
      const ctxSquare = canvasSquare.getContext('2d')
      ctxSquare.clearRect(0, 0, canvasSquare.width, canvasSquare.height)
      ctxSquare.drawImage(
        this.canvasImage.img,
        (this.cutArea.x + this.canvasImage.editX) * this.canvasImage.scale, (this.cutArea.y + this.canvasImage.editY) * this.canvasImage.scale,
        this.cutArea.side * this.canvasImage.scale, this.cutArea.side * this.canvasImage.scale,
        0, 0,
        canvasSquare.width, canvasSquare.height)
      // circle
      const canvasCircle = document.getElementById('picCanvasCircle')
      const ctxCircle = canvasCircle.getContext('2d')
      ctxCircle.clearRect(0, 0, canvasCircle.width, canvasCircle.height)
      ctxCircle.drawImage(
        this.canvasImage.img,
        (this.cutArea.x + this.canvasImage.editX) * this.canvasImage.scale, (this.cutArea.y + this.canvasImage.editY) * this.canvasImage.scale,
        this.cutArea.side * this.canvasImage.scale, this.cutArea.side * this.canvasImage.scale,
        0, 0,
        canvasCircle.width, canvasCircle.height)
    },

    /**
     * 搜索选中区域
     */
    searchArea (e) {
      const canvas = document.getElementById('picCanvasMain')
      // 鼠标在右下角
      if (e.offsetX < this.cutArea.x + this.cutArea.side + 5 &&
          e.offsetX > this.cutArea.x + this.cutArea.side - 5 &&
          e.offsetY < this.cutArea.y + this.cutArea.side + 5 &&
          e.offsetY > this.cutArea.y + this.cutArea.side - 5) {
        canvas.style.cursor = 'nw-resize'
        // 按下了左键
        if (e.buttons === 1) {
          // 求斜方向移动距离
          let distance = parseInt(Math.sqrt(Math.pow(e.movementX, 2) + Math.pow(e.movementY, 2)))
          if (e.movementX < 0 || e.movementY < 0) {
            distance = -distance
          }
          const tempSide = this.cutArea.side + distance
          // 如果仍是正大小或不超出边界
          const imgShortSide = this.canvasImage.width < this.canvasImage.height ? this.canvasImage.width : this.canvasImage.height
          if (tempSide >= 1 && tempSide <= imgShortSide) {
            this.cutArea.side = tempSide
            this.printArea()
          }
        }
      } else if (e.offsetX < this.cutArea.x + this.cutArea.side && // 鼠标在选中区域中
                   e.offsetY < this.cutArea.y + this.cutArea.side &&
                   e.offsetX > this.cutArea.x &&
                   e.offsetY > this.cutArea.y) {
        canvas.style.cursor = 'move'
        // 按下了左键
        if (e.buttons === 1) {
          const tempX = this.cutArea.x + e.movementX
          const tempY = this.cutArea.y + e.movementY
          // 如果不超出边界
          if (tempX >= this.canvasImage.x &&
                tempY >= this.canvasImage.y &&
                tempX + this.cutArea.side <= this.canvasImage.x + this.canvasImage.width &&
                tempY + this.cutArea.side <= this.canvasImage.y + this.canvasImage.height) {
            this.cutArea.x = tempX
            this.cutArea.y = tempY
            this.printArea()
          }
        }
      } else {
        canvas.style.cursor = 'default'
      }
    },

    /**
     * 上传头像到服务器
     */
    uploadPic () {
      // 得到裁剪后的base64编码
      const canvasSquare = document.getElementById('picCanvasSquare')
      const url = canvasSquare.toDataURL('image/jpeg')

      const params = {
        token: getToken(),
        // 格式: image/jpeg
        type: url.slice(url.indexOf(':') + 1, url.indexOf(';')),
        base64: url.slice(url.indexOf(',') + 1)
      }

      this.uploadLoading = true
      uploadUserPic(params).then((res) => {
        // ———— 成功回调 ————
        const data = res.data
        switch (data.code) {
          case 200: // 上传成功
            this.$emit('uploadSuccess')
            this.$message({
              message: '上传成功',
              type: 'success'
            })
            break
          case 501:
            this.$message({
              message: '图片无效',
              type: 'error'
            })
            break
          case 502:
            this.$message({
              message: '上传失败',
              type: 'error'
            })
            break
        }
      }).catch((err) => {
        // ———— 错误处理 ————
        this.$message({
          message: '上传失败:' + err.message,
          type: 'error'
        })
      }).finally(() => {
        // ———— 最终执行 ————
        // 结束按钮加载中的状态
        this.uploadLoading = false
      })
    }
  }
}

</script>
