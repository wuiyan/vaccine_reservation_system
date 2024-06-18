<template>
  <view class="flex-col page">
    <view class="flex-row items-center section">
      <image
          class="image"
          src="/static/admin/personCenter/avatar.png"
      />
      <text class="ml-48 text">{{ admin.account }}</text>
    </view>
    <view class="flex-col list mt-33">
      <view class="flex-row justify-between items-center section_2" @tap="redirectToOtherPage('/pages/admin/person_center/admin_personInfo/admin_personInfo')">
        <view class="flex-row items-center" >
          <image
              class="shrink-0 image_2"
              src="/static/admin/personCenter/my1.png"
          />
          <text class="ml-18 font text_2">个人信息</text>
        </view>
        <image
            class="image_3 image_1"
            src="/static/admin/personCenter/enter.png"
        />
      </view>
      <view class="mt-42 flex-row justify-between items-center section_3" @tap="logout">
        <view class="flex-row items-center">
          <image
              class="shrink-0 image_4"
              src="/static/admin/personCenter/my4.png"
          />
          <text class="font ml-21">退出登录</text>
        </view>
        <image
            class="image_3"
            src="/static/admin/personCenter/enter.png"
        />
      </view>
    </view>
    <view class="tabBar-bottom">
      <CommonAdminTabBar :currentTab="currentTab"></CommonAdminTabBar>
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
      currentTab:"/pages/admin/person_center/person_center",
      admin:{
        account:"管理员"
      }
    };
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
    redirectToOtherPage(pathUrl){
      uni.navigateTo({url: pathUrl});
    },
    // 展示提示信息
    showToast(message){
      uni.showToast({
        title: message,
        icon: 'none', // 不显示图标
        duration: 2000 // 提示持续时间，单位毫秒
      });
    },
  },
};
</script>

<style scoped lang="css">
.mt-33 {
  margin-top: 2.06rem;
}

.ml-21 {
  margin-left: 1.31rem;
}

.page {
  padding-bottom: 24.5rem;
  background-color: #f2f2f2;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}

.section {
  padding: 5.16rem 2.13rem 2.14rem;
  background-color: #00c29d;
  border-radius: 0.31rem;
}

.image {
  border-radius: 5.29rem;
  width: 8.13rem;
  height: 8rem;
}

.text {
  color: #000000;
  font-size: 2.25rem;
  font-family: SourceHanSansCN;
  line-height: 2.09rem;
}

.list {
  padding-left: 1.25rem;
  padding-right: 1rem;
}

.section_2 {
  padding: 2.06rem 1.56rem 2rem;
  background-color: #ffffff;
  border-radius: 0.63rem;
}

.image_2 {
  width: 3.44rem;
  height: 2.81rem;
}

.font {
  font-size: 1.88rem;
  font-family: SourceHanSerifCN;
  line-height: 1.72rem;
  color: #000000;
}

.text_2 {
  line-height: 1.74rem;
}

.image_3 {
  width: 3.13rem;
  height: 2.69rem;
}

.image_1 {
  margin-right: 0.13rem;
}

.section_3 {
  padding: 2.06rem 1.69rem 2.13rem 1.88rem;
  background-color: #ffffff;
  border-radius: 0.63rem;
}

.image_4 {
  width: 3.13rem;
  height: 2.56rem;
}
</style>