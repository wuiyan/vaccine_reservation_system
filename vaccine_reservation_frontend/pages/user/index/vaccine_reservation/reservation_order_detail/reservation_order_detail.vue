<template>
<view class="flex-col page">
  <view class="flex-col self-stretch list">
    <view class="flex-col section">
      <text class="self-center font">个人信息</text>
      <view class="flex-row self-stretch group">
        <text class="font_2 text">姓名</text>
        <text class="font_3 text_2 ml-103">{{ showUserForm.name }}</text>
      </view>
      <view class="flex-row self-stretch group_2">
        <text class="font_2 text_3">性别</text>
        <text class="font_3 text_4 ml-103">{{ USER_GENDER[showUserForm.gender] }}</text>
      </view>
      <view class="flex-row justify-between items-center self-stretch group_3">
        <text class="font_2 text_5">证件号码</text>
        <text class="font_4 text_6">{{ showUserForm.card }}</text>
      </view>
      <view class="flex-row justify-between items-center self-stretch group_4">
        <text class="font_2 text_7">电话</text>
        <text class="font_4 text_8">{{ showUserForm.phone }}</text>
      </view>
    </view>
    <view class="mt-58 flex-col section_2">
      <view class="flex-col justify-start items-center text-wrapper"><text class="font text_9">预约信息</text></view>
      <view class="flex-col group_5">
        <view class="flex-row justify-between self-stretch group_6">
          <text class="font_2">预约项目</text>
          <text class="font_3 text_10">{{ plan.vcName }}</text>
        </view>
        <view class="flex-row justify-between self-stretch group_7">
          <text class="font_2">预约地点</text>
          <text class="font_3 text_11">{{ plan.place }}</text>
        </view>
        <view class="flex-row justify-between items-center self-center group_8">
          <text class="font_2 text_12">接种时间</text>
          <text class="font_3 text_13">{{ plan.rvDateTime }}</text>
        </view>
        <view class="flex-row justify-center self-stretch relative group_9">
          <text class="font_2 text_14 pos">预约剂次</text>
          <text class="font_3 text_15">{{ plan.dose }}</text>
        </view>
      </view>
    </view>
  </view>
  <view class="mt-100 flex-col justify-start items-center self-center text-wrapper_2" @tap="submitReservationInfo">
    <text class="font_2 text_16">提交订单</text>
  </view>
</view>
</template>

<script>
import {USER_GENDER} from "@/common/constants";
import request from "@/api/api";

export default {
  computed: {
    USER_GENDER() {
      return USER_GENDER
    }
  },
  components: {},
  props: {},
  onLoad() {
    let data = uni.getStorageSync('reservationInfo');//取出缓存数据
    console.log(data);
    this.plan = data;
    uni.removeStorageSync('reservationInfo');

    this.showUserForm = uni.getStorageSync('userInfo');

  },
  data() {
    return {
      showUserForm:{
        id:null,
        name:"",
        gender:null,
        cardType:null,
        card:"",
        phone:""
      },
      // 预约信息存放对象
      plan:{
        planId:null,
        rvDateTime:"",
        vaccineId:null,
        vcNumber:"",
        vcName:"",
        price:"",
        place:"",
        dose:"",
        rvStatus:""
      },
      submitForm:{
        userId: null,
        vaccineId: null,
        planId: null,
        nowDose: null,
        nowTime: null,
        rvStartDatetime: null,
        rvEndDatetime: null,
        rvStatus: null
      }
    };
  },

  methods: {
    submitReservationInfo(){
      this.submitForm.userId = this.showUserForm.id;
      this.submitForm.vaccineId = this.plan.vaccineId;
      this.submitForm.planId = this.plan.planId;
      this.submitForm.nowDose = this.plan.dose;
      this.submitForm.nowTime = new Date().getTime();
      let dateTime = this.plan.rvDateTime.split(" ");
      let date = dateTime[0];
      let startTime = dateTime[1].split("~")[0];
      let endTime = dateTime[1].split("~")[1];
      this.submitForm.rvStartDatetime = Date.parse(date + " " + startTime);
      this.submitForm.rvEndDatetime =  Date.parse(date + " " + endTime);
      this.submitForm.rvStatus = 0;

      request("reservation","POST",this.submitForm)
          .then(responseData => {
            if (responseData.code == 0) {
              console.log("Request successful:", responseData);
              // 新增信息成功后，显示需要点击的弹窗提示
              uni.showModal({
                title: '提示', // 弹窗标题
                content: '预约成功,可在个人中心查看预约信息。', // 弹窗内容
                showCancel: false, // 不显示取消按钮
                confirmText: '确定', // 确定按钮的文字，默认为"确定"
                success: function (res) {
                  if (res.confirm) {
                    uni.switchTab({url: "/pages/user/index/index"});
                  }
                }
              });
            } else {
              this.showToast("预约失败："+responseData.message)
            }
          }).catch(error => {
        this.showToast(error.errMsg);
      });
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
.ml-103 {
  margin-left: 6.44rem;
}
.page {
  padding: 0 0.56rem 7.69rem;
  background-color: #f2f2f2;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}
.list {
  padding-top: 1.19rem;
}
.section {
  padding: 1.02rem 0 2.16rem;
  background-color: #ffffff;
  border-radius: 0.63rem;
}
.font {
  font-size: 1.5rem;
  font-family: SourceHanSerifCN;
  line-height: 1.4rem;
  color: #000000;
}
.group {
  margin-top: 0.4rem;
  padding: 1.01rem 2.17rem 0.67rem;
  border-top: solid 0.063rem #a6a6a6;
  border-bottom: solid 0.063rem #a6a6a6;
}
.font_2 {
  font-size: 1.25rem;
  font-family: SourceHanSansCN;
  line-height: 1.15rem;
  color: #000000;
}
.text {
  line-height: 1.15rem;
}
.font_3 {
  font-size: 1.25rem;
  font-family: SourceHanSerifCN;
  line-height: 1.15rem;
  color: #000000;
}
.text_2 {
  line-height: 1.15rem;
}
.group_2 {
  padding: 1.18rem 2.16rem 0.73rem;
  border-bottom: solid 0.063rem #a6a6a6;
}
.text_3 {
  line-height: 1.16rem;
}
.text_4 {
  line-height: 1.11rem;
}
.group_3 {
  padding: 1.19rem 2.19rem 0.73rem;
  border-bottom: solid 0.063rem #a6a6a6;
}
.text_5 {
  line-height: 1.15rem;
}
.font_4 {
  font-size: 1.25rem;
  font-family: SourceHanSerifCN;
  line-height: 0.95rem;
  color: #000000;
}
.text_6 {
  margin-right: 1.31rem;
}
.group_4 {
  padding: 1.18rem 2.28rem 0.73rem;
  border-bottom: solid 0.063rem #a6a6a6;
}
.text_7 {
  line-height: 1.15rem;
}
.text_8 {
  margin-right: 4.68rem;
  line-height: 0.95rem;
}
.section_2 {
  padding-bottom: 3.07rem;
  background-color: #ffffff;
  border-radius: 0.63rem;
}
.text-wrapper {
  padding: 0.89rem 0 0.58rem;
  border-bottom: solid 0.063rem #a6a6a6;
}
.text_9 {
  line-height: 1.4rem;
}
.group_5 {
  padding-top: 0.13rem;
  border-bottom: solid 0.063rem #a6a6a6;
}
.group_6 {
  padding: 1.12rem 2rem 0.6rem 2.23rem;
}
.text_10 {
  line-height: 1.18rem;
}
.group_7 {
  padding: 1.06rem 2.23rem 0.67rem;
  border-top: solid 0.063rem #a6a6a6;
  border-bottom: solid 0.063rem #a6a6a6;
}
.text_11 {
  margin-right: 3.56rem;
  line-height: 1.15rem;
}
.group_8 {
  padding: 1.21rem 0 0.4rem;
  width: 22.28rem;
}
.text_12 {
  line-height: 1.15rem;
}
.text_13 {
  line-height: 1.5rem;
  width: 13.31rem;
}
.group_9 {
  padding: 1.06rem 2.23rem 0.69rem;
  border-top: solid 0.063rem #a6a6a6;
}
.text_14 {
  line-height: 1.16rem;
}
.pos {
  position: absolute;
  left: 2.23rem;
  bottom: 0.66rem;
}
.text_15 {
  line-height: 1.15rem;
}
.text-wrapper_2 {
  padding: 1.39rem 0 1.26rem;
  background-color: #0078f5;
  border-radius: 0.63rem;
  width: 11.88rem;
}
.text_16 {
  color: #ffffff;
  line-height: 1.16rem;
}
</style>