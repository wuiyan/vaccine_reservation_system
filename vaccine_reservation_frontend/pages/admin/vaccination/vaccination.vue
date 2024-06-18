<template>
  <view class="flex-col justify-start page">
    <view class="flex-col section">
      <view class="flex-col section_2">
        <view class="flex-col justify-start items-center self-stretch text-wrapper">
          <input v-model="code" placeholder="请输入校验码"/>
        </view>
        <view class="flex-col justify-start items-center self-center text-wrapper_2 mt-69" @tap="startCheck">
          <text class="text_2">开始校验</text>
        </view>
      </view>
      <view v-if="isShow" class="mt-80 flex-col section_3">
        <view class="flex-row items-center self-stretch">
          <text class="font">预约人：</text>
          <text class="ml-44 font_2 text_3">{{ info.name }}</text>
        </view>
        <view class="mt-20 flex-row justify-center items-center self-stretch relative group">
          <text class="font text_4 pos">手机号：</text>
          <text class="font_3">{{ info.phone }}</text>
        </view>
        <view class="mt-20 flex-row justify-center items-center self-stretch relative group_1">
          <text class="font text_5 pos_2">证件类型：</text>
          <text class="font_3">{{CARD_TYPE[info.cardType]}}</text>
        </view>
        <view class="mt-20 flex-row items-center self-stretch">
          <text class="font text_6">证件号：</text>
          <text class="font_2 ml-45">{{info.card}}</text>
        </view>
        <view class="mt-20 flex-row items-center self-stretch">
          <text class="font text_7">预约地点：</text>
          <text class="ml-26 font_2 text_8">{{info.place}}</text>
        </view>
        <view class="mt-20 flex-row items-center self-stretch">
          <text class="font text_9">预约疫苗：</text>
          <text class="ml-28 font_2 text_10">{{info.vcName}}</text>
        </view>
        <view class="mt-20 flex-row items-baseline self-start group_2">
          <text class="shrink-0 font">预约日期：</text>
          <text class="flex-1 font_2 text_11 ml-27">{{info.rvDateTime}}</text>
        </view>
        <view class="mt-20 flex-row items-center self-stretch">
          <text class="font text_12">预约状态：</text>
          <text class="ml-28 font_2">{{RESERVATION_STATUS[info.rvStatus]}}</text>
        </view>
        <view class="mt-20 flex-row justify-between self-stretch group_3">
          <view class="flex-col justify-start items-center text-wrapper_3" @tap="cancelConfirm">
            <text class="font_4 text_13">取消</text>
          </view>
          <view class="flex-col justify-start items-center text-wrapper_4" @tap="confirmVaccination">
            <text class="font_4 text_14">确认接种</text>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import {CARD_TYPE, RESERVATION_STATUS} from "@/common/constants";
import request from "@/api/api";

export default {
  computed: {
    RESERVATION_STATUS() {
      return RESERVATION_STATUS
    },
    CARD_TYPE() {
      return CARD_TYPE
    }
  },
  components: {},
  props: {},
  data() {
    return {
      code:"",
      isShow:false,
      info:{}
    };
  },

  methods: {
    startCheck(){
      request(`vaccination?code=${this.code}`,"GET").then(responseData => {
          if (responseData.code === 0) {
            console.log("Request successful:", responseData);
            this.info = responseData.data; // 更新数据列表
            this.isShow = true;
          } else {
            this.showToast("Request failed:" + responseData.message);
          }
          })
          .catch(error => {
            console.error("Request failed:", error);
            this.showToast("Request failed:" + error);
          });
    },
    cancelConfirm(){
      setTimeout( () => {
        uni.redirectTo({url:"/pages/admin/index/index"});
      }, 500);
    },
    confirmVaccination(){
      request(`vaccination?reservationId=${this.info.id}`,"PUT").then(responseData => {
        if (responseData.code === 0) {
          console.log("Request successful:", responseData);
          this.info = responseData.data; // 更新数据列表
          this.showToast("接种成功");
          setTimeout( () => {
            uni.redirectTo({url:"/pages/admin/index/index"});
          }, 500);
        } else {
          this.showToast("Request failed:" + responseData.message);
        }
      })
          .catch(error => {
            console.error("Request failed:", error);
            this.showToast("Request failed:" + error);
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
.mt-69 {
  margin-top: 4.31rem;
}
.ml-45 {
  margin-left: 2.81rem;
}
.ml-27 {
  margin-left: 1.69rem;
}
.page {
  background-color: #ffffff;
  height: 100%;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
}
.section {
  padding: 4.08rem 0 9.89rem;
  background-color: #e7f5ff;
}
.section_2 {
  padding: 5.81rem 4.88rem 2.69rem 4.94rem;
  background-color: #ffffff;
  border-radius: 0.63rem;
}
.text-wrapper {
  padding: 1.18rem 0 0.89rem;
  background-color: #f5f5f5;
  border-radius: 0.63rem;
}
.font {
  font-size: 1rem;
  font-family: SourceHanSerifCN;
  line-height: 0.92rem;
  color: #383838;
}
.text {
  color: #808080;
  line-height: 0.93rem;
}
.text-wrapper_2 {
  padding: 0.98rem 0 0.85rem;
  background-color: #2a82e4;
  border-radius: 0.63rem;
  width: 7.06rem;
}
.text_2 {
  color: #ffffff;
  font-size: 1.13rem;
  font-family: SourceHanSansCN;
  line-height: 1.05rem;
}
.section_3 {
  margin-left: 1.03rem;
  margin-right: 0.97rem;
  padding: 0.67rem 3.37rem 0.67rem 3.49rem;
  background-color: #ffffff;
  border-radius: 0.63rem;
  border-left: solid 0.063rem #e5e5e5;
  border-right: solid 0.063rem #e5e5e5;
  border-top: solid 0.063rem #e5e5e5;
  border-bottom: solid 0.063rem #e5e5e5;
}
.group {
  padding-bottom: 0.21rem;
}
.text_4 {
  line-height: 0.92rem;
}
.pos {
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
}
.font_3 {
  font-size: 0.88rem;
  font-family: SourceHanSerifCN;
  line-height: 0.67rem;
  color: #808080;
}
.group_1 {
  padding-bottom: 0.21rem;
}
.text_5 {
  line-height: 0.92rem;
}
.pos_2 {
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
}
.text_6 {
  line-height: 0.92rem;
}
.text_7 {
  line-height: 0.93rem;
}
.text_9 {
  line-height: 0.93rem;
}
.font_2 {
  font-size: 0.88rem;
  font-family: SourceHanSerifCN;
  line-height: 0.81rem;
  color: #808080;
}
.text_8 {
  line-height: 0.81rem;
}
.text_3 {
  line-height: 0.81rem;
}
.text_10 {
  line-height: 0.82rem;
}
.group_2 {
  width: 14.88rem;
}
.text_11 {
  line-height: 1.06rem;
}
.text_12 {
  line-height: 0.92rem;
}
.group_3 {
  padding: 0 0.91rem;
}
.text-wrapper_3 {
  padding: 0.44rem 0 0.37rem;
  background-color: #ff5733;
  border-radius: 0.31rem;
  width: 5rem;
  height: 1.56rem;
}
.font_4 {
  font-size: 0.88rem;
  font-family: SourceHanSerifCN;
  line-height: 0.81rem;
  color: #ffffff;
}
.text_13 {
  font-size: 0.81rem;
  line-height: 0.75rem;
}
.text-wrapper_4 {
  margin-right: 2.14rem;
  padding: 0.44rem 0 0.37rem;
  background-color: #43cf7c;
  border-radius: 0.31rem;
  width: 5rem;
  height: 1.56rem;
}
.text_14 {
  font-size: 0.81rem;
  line-height: 0.75rem;
}
</style>