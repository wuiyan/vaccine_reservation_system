<template>
  <view class="flex-col page">
    <view class="flex-col self-stretch section">
      <view class="flex-row group">
        <text class="font text">姓名</text>
        <view class="font_2 text_2 ml-103">
          <input type="text" v-model="user.name" placeholder="请输入用户姓名" />
        </view>
      </view>
      <view class="flex-col">
        <view class="flex-row justify-between group_2">
          <view class="flex-row self-start group_3">
            <text class="font text_3">性别</text>
              <picker class="font_2 text_4 ml-103" mode="selector" :range="genderOptions" :value="user.gender" @change="onGenderChange">
                <view class="picker">
                  {{genderOptions[user.gender]}}
                </view>
              </picker>
          </view>
          <image
              class="self-center image"
              src="/static/user/my/public/enter.png"
          />
        </view>
        <view class="flex-row justify-center items-center relative group_4">
          <text class="font pos">证件类型</text>
            <picker class="font_2 text-pv8YqwkT" mode="selector" :range="cardTypeOptions" :value="user.cardType" @change="onCardTypeChange">
              <view class="picker">
                {{cardTypeOptions[user.cardType]}}
              </view>
            </picker>
          <image
              class="image pos_2"
              src="/static/user/my/public/enter.png"
          />
        </view>
      </view>
      <view class="flex-row justify-between items-center group_5">
        <text class="font text_5">证件号码</text>
          <input class="font_3" type="text" v-model="user.card" placeholder="请输入证件号码" />
      </view>
      <view class="flex-row justify-between items-center group_6">
        <text class="font text_7">电话</text>
        <view class="font_3 text_8" >
          <input type="text" v-model="user.phone" placeholder="请输入手机号码" />
        </view>
      </view>
    </view>
    <view class="mt-36 flex-col justify-start items-center self-center text-wrapper" @tap="userInfoSubmit">
      <text class="font_2 text_9">保存</text>
    </view>
  </view>
</template>

<script>
import {CARD_TYPE, USER_GENDER} from "@/common/constants";
import request from "@/api/api";

export default {
  computed: {
    CARD_TYPE() {
      return CARD_TYPE
    },
    USER_GENDER() {
      return USER_GENDER
    }
  },
  components: {},
  props: {},
  onLoad() {
    this.user = uni.getStorageSync("userInfo");
    for (const key in USER_GENDER) {
      this.genderOptions.push(USER_GENDER[key]);
    }
    for (const key in CARD_TYPE) {
      this.cardTypeOptions.push(CARD_TYPE[key]);
    }
  },
  data() {
    return {
      user:{},
      genderOptions:[],
      cardTypeOptions:[],
    };
  },

  methods: {
    // 提交用户表单
    userInfoSubmit() {
      const url = `user`;
      request(url, "PUT",this.user)
          .then(responseData => {
            if (responseData.code == 0) {
              console.log("Request successful:", responseData);
              uni.setStorageSync("userInfo",this.user);
              // 新增信息成功后，显示需要点击的弹窗提示
              uni.showModal({
                title: '提示', // 弹窗标题
                content: '保存用户信息成功', // 弹窗内容
                showCancel: false, // 不显示取消按钮
                confirmText: '确定', // 确定按钮的文字，默认为"确定"
                success: function (res) {
                  if (res.confirm) {
                    uni.switchTab({
                      url:"/pages/user/my/my"
                    })
                  }
                }
              });
            } else {
              this.showToast("保存用户信息失败："+responseData.message)
            }
          })
          .catch(error => {
            this.showToast(error.errMsg);
          });
    },
    onGenderChange(event){
      this.user.gender = event.target.value; // 更新选择
    },
    onCardTypeChange(event){
      this.user.cardType = event.target.value; // 更新选择

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
.ml-103 {
  margin-left: 6.44rem;
}
.page {
  padding: 1.19rem 0.56rem 33.56rem;
  background-color: #f2f2f2;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}
.section {
  padding-bottom: 1.72rem;
  background-color: #ffffff;
  border-radius: 0.63rem;
}
.group {
  padding: 1.76rem 2.17rem 0.73rem;
  border-bottom: solid 0.063rem #a6a6a6;
}
.font {
  font-size: 1.25rem;
  font-family: SourceHanSansCN;
  line-height: 1.15rem;
  color: #000000;
}
.text {
  line-height: 1.15rem;
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
.group_2 {
  padding: 0.78rem 1.44rem 0.73rem 2.16rem;
  border-bottom: solid 0.063rem #a6a6a6;
}
.group_3 {
  margin-top: 0.4rem;
}
.text_3 {
  line-height: 1.16rem;
}
.text_4 {
  line-height: 1.11rem;
}
.image {
  width: 1.25rem;
  height: 1.25rem;
}
.group_4 {
  padding: 1.17rem 1.44rem 0.74rem 2.19rem;
  border-bottom: solid 0.063rem #a6a6a6;
}
.pos {
  position: absolute;
  left: 2.19rem;
  bottom: 0.79rem;
}
.text-pv8YqwkT {
  margin-left: -1rem;
}
.pos_2 {
  position: absolute;
  right: 1.44rem;
  top: 50%;
  transform: translateY(-50%);
}
.group_5 {
  padding: 1.19rem 2.19rem 0.73rem;
  border-bottom: solid 0.063rem #a6a6a6;
}
.text_5 {
  line-height: 1.15rem;
}
.font_3 {
  font-size: 1.25rem;
  font-family: SourceHanSerifCN;
  line-height: 0.95rem;
  color: #000000;
}
.group_6 {
  padding: 1.18rem 2.28rem 0.73rem;
  border-bottom: solid 0.063rem #a6a6a6;
}
.text_7 {
  line-height: 1.15rem;
}
.text_8 {
  line-height: 0.95rem;
}
.text-wrapper {
  padding: 1.14rem 0 1.02rem;
  background-color: #00c29d;
  border-radius: 0.63rem;
  width: 8.31rem;
}
.text_9 {
  color: #ffffff;
  line-height: 1.15rem;
}
</style>