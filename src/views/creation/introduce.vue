<template>
  <div class="introContainer">
    <div>
      <span class="containerTitle">LOGO制作介绍</span>
      <iconfont
        name="icon-jiankangtijianxunwenbiao"
        style="font-size: 16px; margin: 0 5px 0 20px;"/>
      <span class="containerTips">帮助您在制作的路上答疑解惑</span>
    </div>
    <!-- 智能生成介绍 -->
    <div class="AIIntro">
      <div class="introTitle">只需轻轻一点，人工智能帮你制作LOGO</div>
      <div class="cardContainer">
        <!-- card-01 -->
        <div class="AIIntroCard">
          <div class="AIIntroCard-num">01</div>
          <div class="AIIntroCard-title">输入品牌名称</div>
          <div class="AIIntroCard-desc">
            填入您需要在logo中展示的品牌、
            商标或者网站名称，
            这是logo制作中的重要信息。
          </div>
        </div>
        <!-- card-02 -->
        <div class="AIIntroCard">
          <div class="AIIntroCard-num">02</div>
          <div class="AIIntroCard-title">自动生成LOGO</div>
          <div class="AIIntroCard-desc">
            根据您输入的品牌名称等信息，AI将调整各项指标，
            生成多个logo，
            具体为文字标志设计。
          </div>
        </div>
        <!-- card-03 -->
        <div class="AIIntroCard">
          <div class="AIIntroCard-num">03</div>
          <div class="AIIntroCard-title">选择您喜爱的样式</div>
          <div class="AIIntroCard-desc">
            您选择一个喜欢的logo标志，
            将进入更为详细的操作，
            可以选择保存该logo，也可以选择发布分享等。
          </div>
        </div>
      </div>
    </div>
    <!-- 在线编辑器介绍 -->
    <div class="EditorIntro">
      <div class="introTitle">强大的在线设计工具，随时进行个性化设计</div>
      <div class="cardContainer">
        <div class="EditorIntroCard">
          <iconfont class="icon-2" name="icon-pic" />
          <div class="EditorIntroCard-title">自定义图片上传</div>
          <div class="EditorIntroCard-desc">
            支持上传本地图片，
            可作为图标的一部分。
          </div>
        </div>
        <div class="EditorIntroCard">
          <iconfont class="icon-2" name="icon-move" />
          <div class="EditorIntroCard-title">元素自由拖拽</div>
          <div class="EditorIntroCard-desc">
            对于编辑器中的图片、字体等元素可以自由移动。
          </div>
        </div>
        <div class="EditorIntroCard">
          <iconfont class="icon-2" name="icon-edit" />
          <div class="EditorIntroCard-title">支持二次创作</div>
          <div class="EditorIntroCard-desc">
            可在生成后的logo或者其他设计师公开的作品上进行再编辑。
          </div>
        </div>
        <div class="EditorIntroCard">
          <iconfont class="icon-2" name="icon-text" />
          <div class="EditorIntroCard-title">提供多种常用字体</div>
          <div class="EditorIntroCard-desc">
            对于设计中常用的字体，设计工具均有提供。
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<style scoped>
.icon-2 {
  font-size: 50px;
}

.introContainer {
  margin: 60px 0;
  width: 1000px;
}

.containerTitle {
  font-size: 16px;
  color: #303133;
}

.containerTips {
  font-size: 14px;
  color: #909399;
}

.AIIntro {
  margin-top: 60px;
}

.EditorIntro {
  margin-top: 60px;
}

.introTitle {
  width: 500px;
  font-size: 40px;
  font-weight:400;
  color: #303133;
}

.cardContainer {
  margin-top: 60px;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}

.AIIntroCard {
  width: 300px;
  height: 190px;
  border-bottom: 1px solid #303133;
}

.AIIntroCard-num {
  line-height: 40px;
  font-size: 20px;
  color: #303133;
}

.AIIntroCard-title {
  line-height: 40px;
  font-size: 20px;
  font-weight: bolder;
  color: #303133;
}

.AIIntroCard-desc {
  font-size: 16px;
  line-height: 30px;
  color: #303133;
}

.EditorIntroCard {
  width: 200px;
  height: 190px;
  text-align: center;
}

.EditorIntroCard-title {
  line-height: 40px;
  font-size: 20px;
  font-weight: bold;
  color: #303133;
}

.EditorIntroCard-desc {
  font-size: 16px;
  line-height: 30px;
  color: #303133;
}

</style>
