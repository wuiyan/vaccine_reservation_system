<template>
  <view class="flex-col page">
    <view class="flex-col self-stretch section">
      <view class="flex-row items-center">
        <text class="font text">账号：</text>
        <view class="ml-2 flex-1 section_2">
          <input v-model="submitForm.account" type="text" placeholder="请输入账号名"/>
        </view>
      </view>
      <view class="flex-row items-center mt-29">
        <text class="font text_2">密码：</text>
        <view class="ml-2 flex-1 section_2">
          <input v-model="submitForm.password" type="password" placeholder="请输入密码"/>
        </view>
      </view>
    </view>
    <view class="mt-100 flex-col justify-start items-center self-center text-wrapper" @tap="adminLogin">
      <text class="font text_3">登录</text>
    </view>
  </view>
</template>

<script>
import request from "@/api/api";
import {BASEURL} from "@/common/constants";

export default {
  components: {},
  props: {},
  data() {
    return {
      submitForm:{
        account:"",
        password:""
      }
    };
  },

  methods: {
    adminLogin(){
      uni.request({
        url: BASEURL + "adminLogin",
        method:"POST",
        data:this.submitForm,
        success: (res) => {
          console.log(res);
          let responseData = res.data;
          if (responseData.code ===0){
            console.log("登录成功");
            // 获取响应头中的 Cookie 数据
            const setCookie = res.header['Set-Cookie'];
            let cookie = setCookie.split(";")[0];
            uni.setStorageSync('sessionId', cookie);
            uni.setStorageSync("adminInfo",responseData.data);

            this.showToast("登录成功");
            uni.redirectTo({
              url:"/pages/admin/index/index"
            })
          } else {
            this.showToast("登录失败");
          }
        },
        fail: (err) => {
          console.error('Request failed:', err);
        }
      });
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
.mt-29 {
  margin-top: 1.81rem;
}
.page {
  padding: 3.5rem 1rem 27.88rem;
  background-color: #ccf3eb;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}
.section {
  padding: 4.25rem 1.63rem 4.88rem;
  background-color: #ffffff;
  border-radius: 0.31rem;
}
.font {
  font-size: 1.38rem;
  font-family: SourceHanSansCN;
  line-height: 1.26rem;
  color: #000000;
}
.text {
  line-height: 1.24rem;
}
.section_2 {
  background-color: #ffffff;
  height: 2.25rem;
  border-left: solid 0.063rem #000000;
  border-right: solid 0.063rem #000000;
  border-top: solid 0.063rem #000000;
  border-bottom: solid 0.063rem #000000;
}
.text_2 {
  line-height: 1.28rem;
}
.text-wrapper {
  padding: 2.13rem 0 1.88rem;
  background-color: #ffffff;
  border-radius: 0.63rem;
  width: 13.06rem;
}
.text_3 {
  color: #00baad;
}
</style>