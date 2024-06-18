<template>
<view class="flex-col page">
  <view class="flex-col section">
    <text class="self-center font">个人信息</text>
    <view class="flex-row self-stretch group_14">
      <text class="font_2 text">姓名</text>
      <text class="font_3 text_2 ml-103">{{ info.name }}</text>
    </view>
    <view class="flex-row self-stretch group_2">
      <text class="font_2 text_3">性别</text>
      <text class="font_3 text_4 ml-103">{{USER_GENDER[info.gender]}}</text>
    </view>
    <view class="flex-row justify-center self-stretch relative group_1">
      <text class="font_2 pos">证件类型</text>
      <text class="font_3">{{CARD_TYPE[info.cardType]}}</text>
    </view>
    <view class="flex-row justify-between items-center self-stretch group_3">
      <text class="font_2 text_5">证件号码</text>
      <text class="font_4 text_6">{{info.card}}</text>
    </view>
    <view class="flex-row justify-between items-center self-stretch group_4">
      <text class="font_2 text_7">电话</text>
      <text class="font_4 text_8">{{info.phone}}</text>
    </view>
  </view>
  <view class="mt-16 flex-col section_2">
    <text class="self-center font text_9">预约信息</text>
    <view class="flex-row items-center self-stretch group">
      <text class="font_2 text_10">预约编号</text>
      <text class="ml-62 font_4">{{info.vcNumber}}</text>
    </view>
    <view class="flex-row justify-between self-stretch group_5">
      <text class="font_2">预约项目</text>
      <text class="font_3 text_11">{{info.vcName}}</text>
    </view>
    <view class="flex-row justify-between self-stretch group_15">
      <text class="font_2">预约地点</text>
      <view style="overflow-x: scroll;width: 15rem;">
        <text class="font_3 text_12" style="white-space: nowrap">{{info.place}}</text>
      </view>
    </view>
    <view class="flex-col input group_6">
      <view class="flex-row justify-between items-center self-center group_7">
        <text class="font_2 text_13">申请时间</text>
        <text style="margin-right: 1rem;" class="font_5">{{info.nowTime}}</text>
      </view>
      <view class="self-stretch divider"></view>
      <view class="flex-row justify-between items-center self-center group_8">
        <text class="font_2 text_15">接种时间</text>
        <text class="font_5">{{info.rvDateTime}}</text>
      </view>
    </view>
    <view class="flex-row justify-center self-stretch relative group_12">
      <text class="font_2 text_17 pos_2">预约剂次</text>
      <text class="font_3 text_18">{{info.nowDose}}</text>
    </view>
    <view class="flex-row justify-center self-stretch relative group_13">
      <text class="font_2 text_19 pos_3">预约状态</text>
      <text class="font_3 text_20">{{RESERVATION_STATUS[info.rvStatus]}}</text>
    </view>
    <view class="flex-row justify-center self-stretch relative group_13">
      <text class="font_2 text_19 pos_3" style="margin-right: 1rem;">校验码</text>
      <text class="font_3 text_20">{{code}}</text>
    </view>
  </view>
</view>
</template>

<script>
import request from "@/api/api";
import {CARD_TYPE, RESERVATION_STATUS, USER_GENDER} from "@/common/constants";

export default {
  computed: {
    RESERVATION_STATUS() {
      return RESERVATION_STATUS
    },
    CARD_TYPE() {
      return CARD_TYPE
    },
    USER_GENDER() {
      return USER_GENDER
    }
  },
  components: {},
  props: {},
  onLoad(options) {
    // 通过options对象接收传递的参数
    const reservationId = decodeURIComponent(options.reservationId); // 如果你之前使用了encodeURIComponent编码参数
    if(reservationId){
      this.getReservationInfo(reservationId);
    }
  },
  data() {
    return {
      info:{
        id:null,
        name:"",
        gender:null,
        cardType:null,
        card:"",
        phone:"",
        nowTime:"",
        rvDateTime:"",
        vaccineId:null,
        vcNumber:"",
        vcName:"",
        price:"",
        place:"",
        nowDose:"",
        rvStatus:""
      },
      code:""
    };
  },

  methods: {
    // 获取预约信息
    getReservationInfo(reservationId){
      const url = `reservation/id?reservationId=${reservationId}`;
      request(url, "GET")
          .then(responseData => {
            if (responseData.code == 0) {
              console.log("Request successful:", responseData);
              this.info = responseData.data; // 更新数据列表
              this.generateCode();
            } else {
              this.showToast("Request failed:" + responseData.message);
            }
          })
          .catch(error => {
            console.error("Request failed:", error);
            this.showToast("Request failed:" + error);
          });
    },
    // 生成校验码
    generateCode(){
      const stringToEncode = this.info.id + "++" + this.info.nowDose;
      const encryptionStr = new Buffer(stringToEncode).toString('base64');
      console.log(encryptionStr);
      this.code = encryptionStr;
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
  padding: 1.19rem 0.56rem 11.56rem;
  background-color: #f2f2f2;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}
.section {
  padding: 1.14rem 0 1.66rem;
  background-color: #ffffff;
  border-radius: 0.63rem;
}
.font {
  font-size: 1.5rem;
  font-family: SourceHanSerifCN;
  line-height: 1.4rem;
  color: #000000;
}
.group_14 {
  margin-top: 0.28rem;
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
.group_1 {
  padding: 1.17rem 2.19rem 0.74rem;
  border-bottom: solid 0.063rem #a6a6a6;
}
.pos {
  position: absolute;
  left: 2.19rem;
  bottom: 0.79rem;
}
.group_3 {
  padding: 1.19rem 2.19rem 0.73rem;
  border-bottom: solid 0.063rem #a6a6a6;
}
.text_5 {
  line-height: 1.15rem;
}
.group_4 {
  padding: 1.18rem 2.28rem 0.73rem;
  border-bottom: solid 0.063rem #a6a6a6;
}
.text_7 {
  line-height: 1.15rem;
}
.section_2 {
  padding: 1.14rem 0 0.91rem;
  background-color: #ffffff;
  border-radius: 0.63rem;
}
.text_9 {
  line-height: 1.4rem;
}
.group {
  margin-top: 0.27rem;
  padding: 1.02rem 2.17rem 0.67rem;
  border-top: solid 0.063rem #a6a6a6;
  border-bottom: solid 0.063rem #a6a6a6;
}
.text_10 {
  line-height: 1.16rem;
}
.font_4 {
  font-size: 1.25rem;
  font-family: SourceHanSerifCN;
  line-height: 0.95rem;
  color: #000000;
}
.text_8 {
  margin-right: 4.68rem;
  line-height: 0.95rem;
}
.text_6 {
  margin-right: 1.31rem;
}
.group_5 {
  padding: 0.99rem 2.06rem 0.73rem 2.17rem;
  border-bottom: solid 0.063rem #a6a6a6;
}
.text_11 {
  line-height: 1.18rem;
}
.group_15 {
  padding: 1rem 2.17rem 0.73rem;
  border-bottom: solid 0.063rem #a6a6a6;
}
.text_12 {
  margin-right: 3.62rem;
  line-height: 1.15rem;
}
.input {
  align-self: stretch;
}
.group_6 {
  border-bottom: solid 0.063rem #a6a6a6;
}
.group_7 {
  padding: 1rem 0 0.4rem;
  width: 21.6rem;
}
.text_13 {
  line-height: 1.15rem;
}
.font_5 {
  font-size: 1.25rem;
  font-family: SourceHanSerifCN;
  line-height: 1.5rem;
  color: #000000;
}
.text_14 {
  width: 11.25rem;
}
.divider {
  background-color: #a6a6a6;
  height: 0.063rem;
}
.group_8 {
  padding: 1rem 0 0.4rem;
  width: 21.6rem;
}
.text_15 {
  line-height: 1.15rem;
}

.group_12 {
  padding: 1rem 2.17rem 0.75rem;
  border-bottom: solid 0.063rem #a6a6a6;
}
.text_17 {
  line-height: 1.16rem;
}
.pos_2 {
  position: absolute;
  left: 2.17rem;
  top: 50%;
  transform: translateY(-50%);
}
.text_18 {
  line-height: 1.15rem;
}
.group_13 {
  padding: 1.01rem 2.17rem 0.74rem;
  border-bottom: solid 0.063rem #a6a6a6;
}
.text_19 {
  line-height: 1.15rem;
}
.pos_3 {
  position: absolute;
  left: 2.17rem;
  top: 50%;
  transform: translateY(-50%);
}
.text_20 {
  line-height: 1.15rem;
}
</style>