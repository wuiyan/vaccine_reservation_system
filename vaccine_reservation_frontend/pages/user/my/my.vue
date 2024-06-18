<template>
<view class="flex-col page">
  <view class="flex-row items-center section">
    <image
      class="image"
      src="/static/user/my/my0.png"
    />
    <text class="text ml-49">{{user.name}}</text>

  </view>
  <view class="flex-col list mt-45">
    <view class="flex-row justify-between items-center section_2" @tap="redirectToOtherPage('/pages/user/my/user_my_personInfo/user_my_personInfo')">
      <view class="flex-row items-center">
        <image
          class="shrink-0 image_2"
          src="../../../static/user/my/my1.png"
        />
        <text class="ml-18 font text_2">个人信息</text>
      </view>
      <image
        class="image_3 image_1"
        src="../../../static/user/my/my5.png"
      />
    </view>
    <view class="flex-row justify-between items-center section_3 mt-21" @tap="redirectToOtherPage('/pages/user/my/reservationInfo/reservationInfo')">
      <view class="flex-row items-center">
        <image
          class="shrink-0 image_5"
          src="../../../static/user/my/my2.png"
        />
        <text class="ml-24 font text_3">预约信息</text>
      </view>
      <image
        class="image_3 image_4"
        src="../../../static/user/my/my5.png"
      />
    </view>
    <view class="flex-row justify-between items-center list-item_2 mt-21" @tap="notifications">
      <view class="flex-row items-center">
        <image
          class="shrink-0 image_6"
          src="../../../static/user/my/my3.png"
        />
        <text class="ml-24 font">订阅通知</text>
      </view>
      <image
        class="image_3"
        src="../../../static/user/my/my5.png"
      />
    </view>
    <view class="flex-row justify-between items-center list-item_2 mt-21" @tap="redirectToOtherPage('/pages/user/my/setting/setting')">
      <view class="flex-row items-center">
        <image
          class="shrink-0 image_5"
          src="../../../static/user/my/my4.png"
        />
        <text class="ml-20 font text_4">设置</text>
      </view>
      <image
        class="image_3"
        src="../../../static/user/my/my5.png"
      />
    </view>
  </view>
  <view class="tabBar-bottom">
    <commonUserTabBar :currentTab="currentTab"></commonUserTabBar>
  </view>
</view>
</template>

<script>
import request from "@/api/api";
import {SUBSCRIPTION_TMPLIDS} from "@/common/constants";

export default {
  components: {},
  props: {},
  onLoad() {
    this.user = uni.getStorageSync("userInfo");
  },
  data() {
    return {
      currentTab:"/pages/user/my/my",
      user:{}
    };
  },

  methods: {
    // 订阅通知
    notifications(){
      uni.requestSubscribeMessage({
        tmplIds: SUBSCRIPTION_TMPLIDS,
        success: res =>{
          console.log(res);
          console.log(JSON.stringify(res).includes("accept"));
          let flag = JSON.stringify(res).includes("accept");
          if (flag){
            let userId = this.user.id;
            const url = `user/remind?userId=${userId}`;
            request(url, "PUT")
                .then(responseData => {
                  if (responseData.code === 0) {
                    this.showToast("订阅成功！")
                  } else {
                    this.showToast("Request failed:" + responseData.message);
                  }
                })
                .catch(error => {
                  this.showToast("Request failed:" + error);
                });
          }
        }
      })
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
.ml-49 {
  margin-left: 3.06rem;
}
.mt-45 {
  margin-top: 2.81rem;
}
.mt-21 {
  margin-top: 1.31rem;
}
.page {
  padding-bottom: 9.44rem;
  background-color: #f2f2f2;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}
.section {
  padding: 4.23rem 2.13rem 2.19rem;
  background-color: #00c29d;
  border-radius: 0 0 0.31rem 0.31rem;
}
.image {
  border-radius: 5.38rem;
  width: 8.13rem;
  height: 8.13rem;
}
.text {
  color: #000000;
  font-size: 2.25rem;
  font-family: SourceHanSansCN;
  line-height: 2.07rem;
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
.image_3 {
  width: 3.13rem;
  height: 2.69rem;
}
.image_1 {
  margin-right: 0.13rem;
}
.section_3 {
  padding: 2.06rem 1.56rem 2.13rem;
  background-color: #ffffff;
  border-radius: 0.63rem;
}
.image_5 {
  width: 3.13rem;
  height: 2.56rem;
}
.image_4 {
  margin-right: 0.13rem;
}
.list-item_2 {
  padding: 2.06rem 1.69rem 2.13rem 1.88rem;
  background-color: #ffffff;
  border-radius: 0.63rem;
}
.image_6 {
  width: 2.3rem;
  height: 2.31rem;
}
.font {
  font-size: 1.88rem;
  font-family: SourceHanSerifCN;
  line-height: 1.72rem;
  color: #000000;
}
.text_3 {
  line-height: 1.75rem;
}
.text_2 {
  line-height: 1.74rem;
}
.text_4 {
  line-height: 1.71rem;
}
</style>