<template>
  <view class="flex-col justify-start page">
    <view class="flex-col group">
      <view class="flex-col justify-start items-center self-stretch header">
        <text class="text">疫苗预约小程序</text>
      </view>
      <view class="flex-col justify-start items-center self-center text-wrapper_2" @tap="userLogin">
        <text class="font text_2">微信登录/注册</text>
      </view>
      <view class="flex-col justify-start items-center self-center text-wrapper_3" @tap="redirectToOtherPage('/pages/login_page/adminLogin_page/adminLogin_page')">
        <text class="font text_3">账号密码登录</text>
      </view>
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
    return {};
  },

  methods: {
    // 用户登录注册
    userLogin(){
      uni.login({
        "provider": "weixin",
        "onlyAuthorize": true, // 微信登录仅请求授权认证
        success: event => {
          const {code} = event;
          //客户端成功获取授权临时票据（code）,向业务服务器发起登录请求。
          uni.request({
            url: BASEURL + "userLogin",
            method:"POST",
            data:{code:code},
            success: (res) => {
              console.log(res);
              let responseData = res.data;
              if (responseData.code ===0){
                console.log("登录成功");
                // 获取响应头中的 Cookie 数据
                const setCookie = res.header['Set-Cookie'];
                let cookie = setCookie.split(";")[0];
                uni.setStorageSync('sessionId', cookie);
                uni.setStorageSync("userInfo",responseData.data);

                this.showToast("登录成功");
                uni.switchTab({
                  url:"/pages/user/index/index"
                })
              } else {
                this.showToast("登录失败");
              }
            },
            fail: (err) => {
              console.error('Request failed:', err);
            }
          });


          // request("userLogin","POST",{code:code}).then(response =>{
          //   if (response.code ===0){
          //     console.log("登录成功");
          //     this.showToast("登录成功");
          //     uni.switchTab({
          //       url:"/pages/user/index/index"
          //     })
          //   } else {
          //     this.showToast("登录失败");
          //   }
          // })
        },
        fail: function (err) {
          // 登录授权失败
          // err.code是错误码
          this.showToast(err.errMsg);
        }
      })

    },
    // 跳转到子页面
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
    }
  },
};
</script>

<style scoped lang="css">
.page {
  background-color: #ccf3eb;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}

.group {
  padding-bottom: 18.88rem;
  overflow-y: auto;
}

.header {
  padding: 6.51rem 0 12.25rem;
  background-image: url('/static/user/index/backgroundImage.png');
  background-size: 100% 100%;
  background-repeat: no-repeat;
}

.text {
  color: #ffffff;
  font-size: 2.25rem;
  font-family: HuXiaoBoNanShen;
  line-height: 2.11rem;
}

.text-wrapper_2 {
  margin-top: 3.38rem;
  padding: 2.56rem 0 2.28rem;
  background-color: #ffffff;
  border-radius: 0.63rem;
  width: 21.69rem;
}

.font {
  font-size: 1.38rem;
  font-family: SourceHanSansCN;
  color: #00baad;
}

.text_2 {
  line-height: 1.41rem;
}

.text-wrapper_3 {
  margin-top: 2.63rem;
  padding: 2.55rem 0 2.41rem;
  background-color: #ffffff;
  border-radius: 0.63rem;
  width: 21.69rem;
}

.text_3 {
  line-height: 1.29rem;
}
</style>