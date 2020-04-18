<template>
  <div>
    <span class="titleView title-1">我关注的</span>

    <!-- 空提示 -->
    <div class="notFoundView">
      <i class="iconfont icon-not-found"/>
      <span class="tips">你还没有关注的人~</span>
    </div>
  </div>
</template>

<style lang="less" scoped>
.titleView {
  position: relative;
}

.notFoundView {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.notFoundView i {
  font-size: 150px;
  color: #E9E9EB;
}
</style>
