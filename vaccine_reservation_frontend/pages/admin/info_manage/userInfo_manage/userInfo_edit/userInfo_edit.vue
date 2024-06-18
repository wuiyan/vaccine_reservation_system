<template>
  <view class="flex-col page">
    <view class="flex-col self-stretch">
      <view class="flex-col justify-start items-center text-wrapper">
        <text class="text">编辑/查看用户信息</text>
      </view>
      <view class="shrink-0 section"></view>
      <view class="flex-col section_2">
        <view class="flex-row justify-between items-center">
          <view class="flex-col items-center">
            <text class="font">用户：</text>
            <text class="mt-20 font text_2">性别：</text>
          </view>
          <view class="flex-col items-start group">
            <view class="section_3">
              <input type="text" v-model="inputForm.name" placeholder="请输入用户姓名" />
            </view>
            <view class="mt-10 section_3">
              <picker mode="selector" :range="genderOptions" :value="selectedGender" @change="onGenderChange">
                <view class="picker">
                  {{genderOptions[selectedGender]}}
                </view>
              </picker>
            </view>
          </view>
        </view>
        <view class="mt-10 flex-col">
          <view class="flex-row items-center">
            <text class="font text_3">证件类型：</text>
            <view class="ml-18 section_3">
              <picker mode="selector" :range="cardTypeOptions" :value="selectedCardType" @change="onCardTypeChange">
                <view class="picker">
                  {{cardTypeOptions[selectedCardType]}}
                </view>
              </picker>
            </view>
          </view>
          <view class="mt-10 flex-row items-center">
            <text class="font text_4">证件号码：</text>
            <view class="ml-18 section_3">
              <input type="text" v-model="inputForm.card" placeholder="请输入证件号码" />
            </view>
          </view>
          <view class="mt-10 flex-row items-center">
            <text class="font text_5">openID：</text>
            <view class="ml-14 section_3">
              <input type="text" v-model="inputForm.openid" placeholder="请输入openid" />
            </view>
          </view>
          <view class="mt-10 flex-row items-center">
            <text class="font text_6">手机号码：</text>
            <view class="ml-18 section_3">
              <input type="text" v-model="inputForm.phone" placeholder="请输入手机号码" />
            </view>
          </view>
        </view>
      </view>
    </view>
    <view class="mt-46 flex-col justify-start self-center text-wrapper_2" @tap="userInfoSubmit">
      <text class="font text_7">保存</text>
    </view>
  </view>
</template>

<script>
import {CARD_TYPE, USER_GENDER, VACCINE_TYPE} from "@/common/constants";
import request from "@/api/api";

export default {
  components: {},
  onLoad(options) {
    // 通过options对象接收传递的参数
    const userId = decodeURIComponent(options.userId); // 如果你之前使用了encodeURIComponent编码参数
    if(userId){
      this.getUserInfoById(userId);
    }
  },
  props: {},
  data() {
    return {
      // 证件类型信息
      cardTypeOptions:["身份证","普通护照"],
      // 证件类型选项
      selectedCardType:0,

      // 性别类型信息
      genderOptions:["女","男"],
      // 性别类型选项
      selectedGender:0,

      inputForm:{
        id:null,
        name:"",
        gender:"女",
        cardType:"身份证",
        card:"",
        openid:"",
        phone:""
      }
    };
  },

  methods: {
    // 获取用户信息
    getUserInfoById(userId){
      const url = `users/id?userId=${userId}`;
      request(url, "GET")
          .then(responseData => {
            if(responseData.code == 0){
              this.inputForm.id = responseData.data.id;
              this.inputForm.name = responseData.data.name;
              this.inputForm.gender = USER_GENDER[responseData.data.gender];
              this.inputForm.cardType = CARD_TYPE[responseData.data.cardType];
              this.inputForm.card = responseData.data.card;
              this.inputForm.openid = responseData.data.openid;
              this.inputForm.phone = responseData.data.phone;

              this.selectedGender = responseData.data.gender;
              this.selectedCardType = responseData.data.cardType;
            }else{
              this.showToast("获取用户信息失败："+responseData.message)
            }
          })
          .catch(error => {
            this.showToast("获取用户信息失败:"+error.errMsg);
          });
    },
    // 提交用户表单
    userInfoSubmit() {
      let submitForm = Object.assign({}, this.inputForm);

      let strGender = submitForm.gender;
      let strCardType = submitForm.cardType;
      for (const key in USER_GENDER) {
        if (USER_GENDER[key] === strGender) {
          submitForm.gender = key;
        }
      }
      for (const key in CARD_TYPE) {
        if (CARD_TYPE[key] === strCardType) {
          submitForm.cardType = key;
        }
      }

      console.log(submitForm);
      const url = `users`;
      request(url, "PUT",submitForm)
          .then(responseData => {
            if (responseData.code == 0) {
              console.log("Request successful:", responseData);
              // 新增信息成功后，显示需要点击的弹窗提示
              uni.showModal({
                title: '提示', // 弹窗标题
                content: '保存用户信息成功', // 弹窗内容
                showCancel: false, // 不显示取消按钮
                confirmText: '确定', // 确定按钮的文字，默认为"确定"
                success: function (res) {
                  if (res.confirm) {
                    const pages = getCurrentPages();
                    const prevPage = pages[pages.length - 2]; // 上一个页面
                    uni.navigateBack({
                      success: () => {
                        prevPage.$vm.refresh(); // 直接调用上个页面的刷新方法
                      }
                    });
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
    // 更改证件类型选项
    onCardTypeChange(event) {
      console.log(event.target.value);
      this.selectedCardType = event.target.value; // 更新选择的剂次
      this.inputForm.cardType = this.cardTypeOptions[this.selectedCardType];
    },
    // 更改性别类型选项
    onGenderChange(event) {
      console.log(event.target.value);
      this.selectedGender = event.target.value; // 更新选择的剂次
      this.inputForm.gender = this.genderOptions[this.selectedGender];
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
.ml-14 {
  margin-left: 1.5rem;
}

.page {
  padding-bottom: 34.81rem;
  background-color: #e7f5ff;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}

.text-wrapper {
  padding: 0.75rem 0 0.63rem;
  background-color: #f2f2f2;
  border-left: solid 0.063rem #e5e5e5;
  border-right: solid 0.063rem #e5e5e5;
  border-top: solid 0.063rem #e5e5e5;
  border-bottom: solid 0.072rem #ffffff;
}

.text {
  color: #000000;
  font-size: 1.25rem;
  font-family: SourceHanSerifCN;
  line-height: 1.16rem;
}

.section {
  background-color: #ffffff;
  height: 0.63rem;
  border-left: solid 0.063rem #e5e5e5;
  border-right: solid 0.063rem #e5e5e5;
  border-top: solid 0.063rem #e5e5e5;
}

.section_2 {
  padding: 0.38rem 3.13rem 2rem;
  background-color: #ffffff;
  border-left: solid 0.063rem #e5e5e5;
  border-right: solid 0.063rem #e5e5e5;
  border-bottom: solid 0.063rem #e5e5e5;
}

.font {
  font-size: 1rem;
  font-family: SourceHanSerifCN;
  line-height: 0.93rem;
  color: #383838;
}

.text_2 {
  line-height: 0.92rem;
}

.group {
  width: 14.32rem;
}

.text_3 {
  line-height: 0.92rem;
}

.text_4 {
  line-height: 0.92rem;
}

.text_5 {
  line-height: 0.99rem;
}

.section_3 {
  background-color: #ffffff;
  width: 11.44rem;
  height: 1.56rem;
  border-bottom: solid 0.05rem #000000;
}

.text_6 {
  line-height: 0.92rem;
}

.text-wrapper_2 {
  padding: 0.88rem 0 0.75rem;
  background-color: #2a82e4;
  border-radius: 0.31rem;
}

.text_7 {
  margin-left: 0.63rem;
  margin-right: 0.6rem;
  color: #ffffff;
}
</style>