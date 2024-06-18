<template>
<view class="flex-col justify-start page">
  <view class="flex-row justify-between items-center section"@tap="logout" >
    <text class="text">退出登录</text>
    <image
      class="image"
      src="/static/user/my/setting/enter.png"
    />
  </view>
</view>
</template>

<script>
export default {
  components: {},
  props: {},
  data() {
    return {};
  },

  methods: {
    logout(){
      uni.removeStorageSync("sessionId");
      uni.removeStorageSync("userInfo");
      uni.removeStorageSync("adminInfo");
      this.showToast("退出成功！");
      setTimeout(()=>{
        uni.redirectTo({
          url:"/pages/login_page/login_page"
        })
      },500);
    },
    // 展示提示信息
    showToast(message){
      uni.showToast({
        title: message,
        icon: 'none', // 不显示图标
        duration: 2000 // 提示持续时间，单位毫秒
      });
    }
  },
};
</script>

<style scoped lang="css">
.page {
  padding: 2.31rem 0 51.38rem;
  background-color: #f2f2f2;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}
.section {
  padding: 1.63rem 0.94rem 1.63rem 2rem;
  background-color: #ffffff;
}
.text {
  color: #000000;
  font-size: 1.25rem;
  font-family: SourceHanSerifCN;
  line-height: 1.15rem;
}
.image {
  width: 1.25rem;
  height: 1.25rem;
}
</style>