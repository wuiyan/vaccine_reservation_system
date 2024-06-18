<template>
  <view class="flex-col justify-start page">
    <view class="flex-col group">
      <view class="flex-col justify-start items-center header"><text class="text">预约信息详情</text></view>
      <view class="flex-col group_1">
        <view class="flex-col justify-start items-center text-wrapper"><text class="font">预约者信息</text></view>
        <view class="flex-col section">
          <view class="flex-row justify-between items-center">
            <view class="flex-col items-center">
              <text class="font_2">姓名：</text>
                <text class="mt-20 font_2">性别：</text>
              </view>
              <view class="flex-col items-start group_2">
                <view class="section_2" style="position: relative;">
                    {{info.name}}
                </view>
                <view class="mt-10 section_2">
                  <text>{{USER_GENDER[info.gender]}}</text>
                </view>
              </view>
            </view>
            <view class="mt-10 flex-col">
              <view class="flex-row items-center">
                <text class="font_2 text_2">证件类型：</text>
                <view class="ml-18 flex-1 section_3">
                  <text>{{CARD_TYPE[info.cardType]}}</text>
                </view>
              </view>
              <view class="mt-10 flex-row items-center">
                <text class="font_2 text_3">证件号码：</text>
                <view class="ml-18 flex-1 section_3">
                  <text>{{info.card}}</text>
                </view>
              </view>
              <view class="mt-10 flex-row items-center">
                <text class="font_2 text_4">手机号码：</text>
                <view class="ml-18 flex-1 section_3">
                  <text>{{info.phone}}</text>
                </view>
              </view>
            </view>
          </view>
        </view>
        <view class="flex-col mt-45">
          <view class="flex-col justify-start items-center text-wrapper">
            <text class="font">预约信息</text>
          </view>
          <view class="flex-col section">
            <view class="flex-row items-center">
              <text class="font_2 text_5">疫苗名称：</text>
              <view class="ml-18 flex-1 section_3" style="position: relative;">
                <text>{{info.vcName}}</text>
              </view>
            </view>
            <view class="mt-10 flex-row items-center">
              <text class="font_2 text_6">疫苗编号：</text>
              <view class="ml-18 flex-1 section_3">
                <text>{{info.vcNumber}}</text>
              </view>
            </view>
            <view class="mt-10 flex-row items-center">
              <text class="font_2 text_7">预约地点：</text>
              <view class="ml-18 flex-1 section_3">
                <view class="ml-18 flex-1 section_3" style="position: relative;margin: 0;">
                  <text>{{info.place}}</text>
                </view>
              </view>
            </view>
            <view class="mt-10 flex-row items-center">
              <text class="font_2 text_8">参考价格：</text>
              <view class="ml-18 flex-1 section_3">
                <text>{{info.price}}</text>
              </view>
            </view>
            <view class="mt-10 flex-row items-center">
              <text class="font_2 text_9">预约剂次：</text>
              <view class="ml-18 flex-1 section_3" style="position: relative;">
                <text>{{info.nowDose}}</text>
              </view>
            </view>
            <view class="mt-10 flex-row items-center">
              <text class="font_2 text_9">申请时间：</text>
              <view class="ml-18 flex-1 section_3" style="position: relative;">
                <text>{{info.nowTime}}</text>
              </view>
            </view>
            <view class="mt-10 flex-row items-center">
              <text class="font_2 text_10">预约时间：</text>
              <view class="ml-18 flex-1 section_3" style="position: relative;">
                <text>{{info.rvDateTime}}</text>
              </view>
            </view>
            <view class="mt-10 flex-row items-center">
              <text class="font_2">预约状态：</text>
              <view class="ml-18 flex-1 section_3" style="position: relative;">
                <text>{{RESERVATION_STATUS[info.rvStatus]}}</text>
              </view>
            </view>
            <view class="mt-10 flex-row items-center">
              <text class="font_2">校验码：</text>
              <view class="ml-18 flex-1 section_3" style="position: relative;">
                <text>{{code}}</text>
              </view>
            </view>
          </view>
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
  onLoad(options) {
    // 通过options对象接收传递的参数
    const reservationId = decodeURIComponent(options.reservationId); // 如果你之前使用了encodeURIComponent编码参数
    if(reservationId){
      this.getReservationInfo(reservationId);
    }
  },
  props: {},
  data() {
    return {
      // 预约信息存放对象
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
      const url = `reservations/id?reservationId=${reservationId}`;
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
.page {
  background-color: #e7f5ff;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}
.group {
  padding-bottom: 14.53rem;
  overflow-y: auto;
}
.header {
  padding: 0.74rem 0 0.65rem;
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
  line-height: 1.17rem;
}
.group_1 {
  margin-top: 1.68rem;
}
.text-wrapper {
  padding: 0.54rem 0 0.29rem;
  background-color: #00baad80;
}
.font {
  font-size: 1.13rem;
  font-family: SourceHanSansCN;
  line-height: 1.05rem;
  color: #000000;
}
.section {
  padding: 0.99rem 3.21rem 1.85rem 3.33rem;
  background-color: #ffffff;
  border-left: solid 0.063rem #e5e5e5;
  border-right: solid 0.063rem #e5e5e5;
  border-top: solid 0.063rem #e5e5e5;
  border-bottom: solid 0.063rem #e5e5e5;
}
.font_2 {
  font-size: 1rem;
  font-family: SourceHanSerifCN;
  line-height: 0.92rem;
  color: #383838;
}
.group_2 {
  width: 14.07rem;
}
.section_2 {
  background-color: #ffffff;
  width: 11.44rem;
  height: 1.56rem;
  border-bottom: solid 0.05rem #000000;
}
.text_2 {
  line-height: 0.92rem;
}
.section_3 {
  margin-right: 1.43rem;
  background-color: #ffffff;
  height: 1.56rem;
  border-bottom: solid 0.05rem #000000;
}
.text_3 {
  line-height: 0.92rem;
}
.text_4 {
  line-height: 0.92rem;
}
.group_4 {
  margin-top: 2.78rem;
}
.section_4 {
  padding: 0.99rem 3.21rem 1.13rem 3.33rem;
  background-color: #ffffff;
  border-left: solid 0.063rem #e5e5e5;
  border-right: solid 0.063rem #e5e5e5;
  border-top: solid 0.063rem #e5e5e5;
  border-bottom: solid 0.063rem #e5e5e5;
}
.text_5 {
  line-height: 0.93rem;
}
.text_6 {
  line-height: 0.93rem;
}
.text_7 {
  line-height: 0.93rem;
}
.text_8 {
  line-height: 0.92rem;
}
.text_9 {
  line-height: 0.93rem;
}
.group_5 {
  padding: 0 0.11rem;
}
.text_10 {
  line-height: 0.92rem;
}
.text_11 {
  line-height: 0.93rem;
}
</style>