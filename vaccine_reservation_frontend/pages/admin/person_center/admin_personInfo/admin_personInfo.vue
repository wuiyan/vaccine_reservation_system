<template>
  <view class="flex-col justify-start page">
    <view class="flex-col section">
      <view class="flex-row group">
        <text class="font text">账户名</text>
        <view class="ml-82 font_2 text_2">
          <input type="text" v-model="admin.account" placeholder="请输入管理员账户" />
        </view>
      </view>
      <view class="flex-row group">
        <text class="font text_3">旧密码</text>
        <view class="font_2 text_4 ml-81">
          <input type="text" v-model="admin.password" />
        </view>
      </view>
      <view class="flex-row justify-center relative group">
        <text class="font text_5 pos">新密码</text>
        <view class="font_2">
          <input type="text" v-model="admin.password" />
        </view>
      </view>
    </view>
  </view>
</template>

<script>

export default {
  components: {},
  props: {},
  onLoad() {
    this.admin = uni.getStorageSync("adminInfo");
  },
  data() {
    return {
      admin:{},
    };
  },

  methods: {
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
.ml-81 {
  margin-left: 5.06rem;
}
.page {
  padding: 1.19rem 0 44.31rem;
  background-color: #f2f2f2;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}
.section {
  margin: 0 0.56rem;
  padding: 1.63rem 0 1.75rem;
  background-color: #ffffff;
  border-radius: 0.63rem;
}
.group {
  padding: 1.13rem 2.25rem 0.75rem;
  border-bottom: solid 0.063rem #a6a6a6;
}
.font {
  font-size: 1.25rem;
  font-family: SourceHanSansCN;
  line-height: 1.15rem;
  color: #000000;
}
.text {
  line-height: 1.16rem;
}
.font_2 {
  font-size: 1.25rem;
  font-family: SourceHanSerifCN;
  line-height: 1.15rem;
  color: #000000;
}
.text_2 {
  line-height: 1.15rem;
}
.text_3 {
  line-height: 1.16rem;
}
.text_4 {
  line-height: 1.11rem;
}
.text_5 {
  line-height: 1.16rem;
}
.pos {
  position: absolute;
  left: 2.18rem;
  bottom: 0.8rem;
}
</style>