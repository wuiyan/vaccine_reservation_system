<template>
<view class="flex-col justify-start page">
  <view class="flex-col list">
    <view class="flex-col mt-24 list-item" v-for="(info, index) in infoList" :key="index">
      <view class="flex-row justify-between items-start">
        <text class="font">{{ info.vaccineName }}</text>
        <image
          class="image"
          src="/static/user/my/reservationInfo/预约详情.png"
		      @tap="redirectToDetailPage(info.id)"
        />
      </view>
      <view class="flex-row justify-between items-end group">
        <view class="flex-col">
          <view class="flex-row items-center">
            <text class="shrink-0 font_2 text">预约时间:</text>
            <text class="font_3 text_2 ml-7">{{info.dateTime}}</text>
          </view>
          <view class="mt-6 flex-row">
            <text class="font_2">接种地点:</text>
            <text class="font_2 ml-7">
              {{ info.place.length > 12 ? info.place.substring(0, 12) + '...' : info.place }}
            </text>
          </view>
        </view>
        <view>

        </view>
        <image v-if="info.status === 0" class="image_2" src="/static/user/my/reservationInfo/取消预约.png" @tap="cancelReservation(info.id)"/>
        <image v-else class="image_2" src="/static/user/my/reservationInfo/删除记录.png" @tap="deleteReservation(info.id)"/>

      </view>
      <view class="flex-row group_2">
        <text class="font_2 text_3">预约状态:</text>
        <text class="font_2 text_4 ml-7">{{ RESERVATION_STATUS[info.status] }}</text>
      </view>
    </view>
  </view>
</view>
</template>

<script>
import request from "@/api/api";
import {RESERVATION_STATUS} from "@/common/constants";

export default {
  computed: {
    RESERVATION_STATUS() {
      return RESERVATION_STATUS
    }
  },
  components: {},
  props: {},
  onLoad() {
    this.user = uni.getStorageSync("userInfo");
  },
  mounted() {
    this.getReservationInfoList(this.user.id);
  },
  data() {
    return {
      user:{},
      infoList: [{
        id:null,
        place:"xxx市yyyy区",
        vaccineName:"四价流感病毒裂解疫苗",
        dateTime:"2024-3-2 18:00~19:00",
        status:0
      }],
    };
  },

  methods: {
    // 获取预约信息
    getReservationInfoList(userId) {
      const url = `reservation?userId=${userId}`;
      request(url, "GET")
          .then(responseData => {
            if (responseData.code == 0) {
              this.infoList = responseData.data;

            } else {
              this.showToast("Request failed:" + responseData.message);
            }
          })
          .catch(error => {
            this.showToast("Request failed:" + error);
          });
    },
    cancelReservation(reservationId){
      const url = `reservation?reservationId=${reservationId}`;
      request(url,"PUT").then(response =>{
        if(response.code===0){
          this.showToast("预约取消成功！");
          setTimeout( () => {
            uni.redirectTo({url:"/pages/user/my/reservationInfo/reservationInfo"});
          }, 500)
        }else {
          this.showToast("预约取消失败！"+response.message);
        }
      }).catch(error=>{
        this.showToast("预约取消失败！"+error.errMsg);
      })
    },
    // 删除预约信息
    deleteReservation(reservationId){
      uni.showModal({
        title: '提示', // 弹窗标题
        content: '确认要删除此预约信息吗？此操作无法撤回！', // 弹窗内容
        showCancel: true, // 显示取消按钮
        confirmText: '确定', // 确定按钮的文字，默认为"确定"
        success: (res) => {
          if (res.confirm) {
            this.doDeleteReservationInfo(reservationId);
          }
        }
      });
    },
    // 确认删除预约信息
    doDeleteReservationInfo(reservationId){
      const url = `reservation?reservationId=${reservationId}`;
      request(url,"DELETE").then(response=>{
        if (response.code == 0){
          this.showToast("预约信息删除成功！");
          setTimeout( () => {
            uni.redirectTo({url:"/pages/user/my/reservationInfo/reservationInfo"});
          }, 500)

        }else {
          this.showToast("预约信息删除失败"+response.message);
        }
      }).catch(error=>{
        this.showToast(error.errMsg);
      })
    },
    // 展示提示信息
    showToast(message){
      uni.showToast({
        title: message,
        icon: 'none', // 不显示图标
        duration: 2000 // 提示持续时间，单位毫秒
      });
    },
    redirectToDetailPage(reservationId){
      let pathUrl =  `/pages/user/my/reservationInfo/reservationInfo_detail/reservationInfo_detail?reservationId=${encodeURIComponent(reservationId)}`;
      uni.navigateTo({url: pathUrl});
    },
	  redirectToOtherPage(pathUrl){
      uni.navigateTo({url: pathUrl});
	  }
  },
};
</script>

<style scoped lang="css">
.ml-7 {
  margin-left: 0.44rem;
}
.page {
  padding: 2.25rem 0 35.25rem;
  background-color: #f2f2f2;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}
.list {
  margin: 0 1.44rem;
}
.list-item {
  padding: 1.5rem 1.75rem 2.13rem;
  background-color: #ffffff;
  border-radius: 0.63rem;
}
.list-item:first-child {
  margin-top: 0;
}
.font {
  font-size: 1.25rem;
  font-family: SourceHanSerifCN;
  line-height: 1.18rem;
  color: #000000;
}
.image {
  margin-right: 0.4rem;
  margin-top: 0.5rem;
  width: 3.5rem;
  height: 1.5rem;
}
.group {
  margin-top: 0.25rem;
}
.font_2 {
  font-size: 0.88rem;
  font-family: SourceHanSerifCN;
  line-height: 0.81rem;
  color: #808080;
}
.text {
  line-height: 0.81rem;
}
.font_3 {
  font-size: 0.88rem;
  font-family: SourceHanSerifCN;
  line-height: 1.06rem;
  color: #808080;
}
.text_2 {
  width: 12rem;
}
.image_2 {
  margin-right: 0.38rem;
  width: 3.5rem;
  height: 1.5rem;
}
.group_2 {
  margin-top: 0.63rem;
}
.text_3 {
  line-height: 0.81rem;
}
.text_4 {
  line-height: 0.81rem;
}
</style>