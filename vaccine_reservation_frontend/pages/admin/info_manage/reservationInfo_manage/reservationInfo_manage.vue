<template>
  <view class="flex-col page">
    <view class="flex-row justify-between items-center relative section">
      <view class="flex-row items-center section_2">
        <image
            class="shrink-0 image"
            src="/static/global/searchBar/搜索.png"
        />
        <view class="font text ml-11">
          <input type="text" v-model="username" @blur="getDataByUserName" placeholder="通过姓名搜索预约信息" />
        </view>
      </view>
      <view class="flex-col justify-start text-wrapper" @tap="redirectToOtherPage('/pages/admin/info_manage/reservationInfo_manage/reservationInfo_add/reservationInfo_add')">
        <text class="font_2 text_2">新增预约信息</text>
      </view>
    </view>
    <view class="mt-36 flex-col list">
      <view class="flex-col mt-34 list-item" v-for="(reservationInfo, index) in reservationInfos" :key="index">
        <view class="flex-row items-center group_1">
          <text class="font">预约人：</text>
          <text class="ml-24 font_3 text_3">{{ reservationInfo.person }}</text>
        </view>
        <view class="flex-row items-center group_2 mt-19">
          <text class="font text_4">预约疫苗：</text>
          <text class="ml-8 font_4">{{ reservationInfo.vaccine }}</text>
        </view>
        <view class="flex-row items-baseline mt-19">
          <text class="shrink-0 font">预约日期：</text>
          <text class="font_5 text_1 ml-7">{{ reservationInfo.dateTime }}</text>
        </view>
        <view class="flex-row items-center group_3 mt-19">
          <text class="font text_5">预约状态：</text>
          <text class="font_3 text_12 ml-7">{{ RESERVATION_STATUS[reservationInfo.status] }}</text>
        </view>
        <view class="flex-row justify-between group_4 mt-19">
          <view class="flex-col justify-start items-center text-wrapper_2" @tap="redirectToDetailPage(reservationInfo.id)">
            <text class="font_2 text_7">详情</text>
          </view>
          <view class="flex-col justify-start items-center text-wrapper_1" @tap="redirectToEditPage(reservationInfo.id)">
            <text class="font_2 text_8">编辑</text>
          </view>
          <view class="flex-col justify-start items-center text-wrapper_4" @tap="deleteReservationInfo(reservationInfo.id)">
            <text class="font_2 text_9">删除</text>
          </view>
        </view>
      </view>
    </view>
    <view v-if="loading" class="loading">加载中...</view>
    <view v-if="!hasMoreData" class="no-more-data">到底了</view>
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
  data() {
    return {
      username:"",
      reservationInfos: [],

      // 分页展示相关信息
      total:0,
      size:5,
      current:1,
      loading: false,
      hasMoreData: true,
    };
  },
  mounted() {
    // 在这里调用后端接口获取数据，然后更新reservationInfos
    this.getReservationInfoList();
  },
  methods: {
    // 跳转到详情页
    redirectToDetailPage(reservationId){
      const pathUrl = `/pages/admin/info_manage/reservationInfo_manage/reservationInfo_detail/reservationInfo_detail?reservationId=${encodeURIComponent(reservationId)}`;
      uni.navigateTo({url: pathUrl});

    },
    // 跳转到编辑页
    redirectToEditPage(reservationId){
      const pathUrl = `/pages/admin/info_manage/reservationInfo_manage/reservationInfo_edit/reservationInfo_edit?reservationId=${encodeURIComponent(reservationId)}`;
      uni.navigateTo({url: pathUrl});
    },
    // 删除预约信息操作
    deleteReservationInfo(reservationId){
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
      const url = `reservations?reservationId=${reservationId}`;
      request(url,"DELETE").then(response=>{
        if (response.code == 0){
          this.showToast("预约信息删除成功！");
          setTimeout( () => {
            uni.redirectTo({url:"/pages/admin/info_manage/reservationInfo_manage/reservationInfo_manage"});
          }, 500)

        }else {
          this.showToast("预约信息删除失败:"+response.message);
        }
      }).catch(error=>{
        this.showToast(error.errMsg);
      })
    },
    getDataByUserName(){
      let username = this.username.trim();
      if (username === ""){
        this.current=1;
        this.hasMoreData = true;
        this.reservationInfos=[];
        this.getReservationInfoList();
        return;
      }
      const url = `reservations/search?username=${username}`;
      request(url, "GET")
          .then(responseData => {
            if (responseData.code == 0) {
              this.reservationInfos = responseData.data; // 更新数据列表
            } else {
              this.showToast("Request failed:" + responseData.message);
            }
            this.hasMoreData = false;
          })
          .catch(error => {
            console.error("Request failed:", error);
            this.showToast("Request failed:" + error);
          });
    },
    // 获取预约信息
    getReservationInfoList() {
      const url = `reservations?pageNum=${this.current}&size=${this.size}`;
      request(url, "GET")
          .then(responseData => {
            if (responseData.code == 0) {
              console.log("Request successful:", responseData);

              this.reservationInfos = this.reservationInfos.concat(responseData.data.records); // 更新数据列表
              this.total = responseData.data.total; // 更新总数
              this.current++; // 更新当前页数

              this.loading = false; // 请求完成，重置 loading 状态

              // 检查是否还有更多数据，根据实际情况设置 hasMoreData
              this.hasMoreData = this.reservationInfos.length < this.total;
            } else {
              this.showToast("Request failed:" + responseData.message);
              // 处理请求失败的逻辑
              this.loading = false; // 请求失败，重置 loading 状态
            }
          })
          .catch(error => {
            console.error("Request failed:", error);
            this.showToast("Request failed:" + error);
            // 处理请求失败的逻辑
            this.loading = false; // 请求失败，重置 loading 状态
          });
    },
    loadMoreData() {
      if (this.hasMoreData && !this.loading) {
        this.loading = true;
        this.getReservationInfoList();
      }
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
    },
    // 刷新页面
    refresh(){
      uni.redirectTo({url:"/pages/admin/info_manage/reservationInfo_manage/reservationInfo_manage"});
    }
  },
  onReachBottom() {
    this.loadMoreData();
  }
};
</script>

<style scoped lang="css">
.ml-11 {
  margin-left: 0.69rem;
}
.mt-19 {
  margin-top: 1.19rem;
}
.ml-7 {
  margin-left: 0.44rem;
}
.page {
  padding-bottom: 21.78rem;
  background-color: #e7f5ff;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}
.section {
  padding: 2.13rem 1.75rem 1.94rem 2.06rem;
  background-color: #ffffff;
  box-shadow: 0rem 0.13rem 0.25rem #00000040;
}
.section_2 {
  padding: 0.25rem 0.56rem 0.38rem;
  background-color: #f5f5f5;
  border-radius: 0.63rem;
  height: 3.13rem;
}
.image {
  width: 2.5rem;
  height: 2.5rem;
}
.font {
  font-size: 1rem;
  font-family: SourceHanSerifCN;
  line-height: 0.92rem;
  color: #383838;
}
.text {
  margin-right: 0.59rem;
  color: #808080;
  line-height: 0.93rem;
}
.text-wrapper {
  padding: 0.62rem 0 0.5rem;
  background-color: #2a82e4;
  border-radius: 0.31rem;
  height: 1.88rem;
}
.font_2 {
  font-size: 0.88rem;
  font-family: SourceHanSerifCN;
  line-height: 0.75rem;
  color: #ffffff;
}
.text_2 {
  margin-left: 0.41rem;
  margin-right: 0.28rem;
  font-size: 0.81rem;
  line-height: 0.76rem;
}
.list {
  padding-left: 0.97rem;
  padding-right: 0.97rem;
}
.list-item {
  padding: 1.6rem 0 0.6rem 1.5rem;
  background-color: #ffffff;
  border-radius: 0.63rem;
  border-left: solid 0.063rem #e5e5e5;
  border-right: solid 0.063rem #e5e5e5;
  border-top: solid 0.063rem #e5e5e5;
  border-bottom: solid 0.063rem #e5e5e5;
}
.list-item:first-child {
  margin-top: 0;
}
.group_1 {
  margin-right: 1.38rem;
}
.font_3 {
  font-size: 0.88rem;
  font-family: SourceHanSerifCN;
  line-height: 0.75rem;
  color: #808080;
}
.text_3 {
  line-height: 0.81rem;
}
.group_2 {
  margin-right: 1.38rem;
}
.text_4 {
  line-height: 0.93rem;
}
.font_4 {
  font-size: 0.88rem;
  font-family: SourceHanSerifCN;
  line-height: 0.82rem;
  color: #808080;
}
.font_5 {
  font-size: 0.88rem;
  font-family: SourceHanSerifCN;
  line-height: 1.06rem;
  color: #808080;
}
.text_1 {
  margin-right: 1rem;
}
.group_3 {
  margin-right: 1.38rem;
}
.text_5 {
  line-height: 0.92rem;
}
.text_12 {
  margin-right: 1rem;
  line-height: 0.81rem;
}
.group_4 {
  margin-right: 1.38rem;
  padding: 0 2.46rem;
}
.text-wrapper_2 {
  padding: 0.44rem 0 0.38rem;
  background-color: #43cf7c;
  border-radius: 0.31rem;
  width: 3.13rem;
  height: 1.56rem;
}
.text_7 {
  font-size: 0.81rem;
  line-height: 0.75rem;
}
.text-wrapper_1 {
  padding: 0.43rem 0 0.38rem;
  background-color: #ff8d1a;
  border-radius: 0.31rem;
  width: 3.13rem;
  height: 1.56rem;
}
.text_8 {
  font-size: 0.81rem;
}
.text-wrapper_4 {
  margin-right: 0.96rem;
  padding: 0.44rem 0 0.37rem;
  background-color: #ff5733;
  border-radius: 0.31rem;
  width: 3.13rem;
  height: 1.56rem;
}
.text_9 {
  font-size: 0.81rem;
  line-height: 0.75rem;
}
</style>