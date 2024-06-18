<template>
<view class="flex-col page">
  <view class="flex-row justify-between items-center relative section">
    <view class="flex-row items-center section_2">
      <image
        class="image"
        src="/static/global/searchBar/搜索.png"
      />
      <view class="text ml-19">
        <input type="text" v-model="planName" @blur="getDataByPlanName" placeholder="搜索计划名称" />
      </view>
    </view>
    <view class="flex-col justify-start text-wrapper" @tap="redirectToOtherPage('/pages/admin/info_manage/reservation_plan_manage/reservation_plan_add/reservation_plan_add')">
      <text class="font text_2">添加预约计划</text></view>
  </view>
  <view class="mt-36 flex-col list">
    <view class="flex-col list-item mt-31" v-for="(plan, index) in plans" :key="index">
      <view class="flex-col justify-start self-stretch relative">
        <view class="section_3"></view>
        <text class="font_2 pos">{{ plan.planName }}</text>
      </view>
      <view class="flex-row self-stretch group view">
        <text class="font_3 text_3">疫苗名称：</text>
        <text class="font_4 text_4">{{ plan.vaccineName }}</text>
      </view>
      <view class="flex-row self-stretch group view_2">
        <text class="font_3">接种地点：</text>
        <text class="font_4">{{ plan.place }}</text>
      </view>
      <view class="flex-row items-center self-start group_2">
        <text class="shrink-0 font_3 text_5">创建日期：</text>
        <text class="flex-1 font_5">{{ plan.createTime }}</text>
      </view>
      <view class="flex-row justify-between self-stretch group_3">
        <view class="flex-col justify-start items-center text-wrapper_2" @tap="redirectToDetailPage(plan.id)">
          <text class="font text_6">详情</text></view>
        <view class="flex-col justify-start items-center text-wrapper_3" @tap="redirectToEditPage(plan.id)">
          <text class="font text_7">编辑</text></view>
        <view class="flex-col justify-start items-center text-wrapper_4" @tap="deleteReservationPlan(plan.id)">
          <text class="font text_8">删除</text></view>
      </view>
    </view>
  </view>
  <view v-if="loading" class="loading">加载中...</view>
  <view v-if="!hasMoreData" class="no-more-data">到底了</view>
</view>
</template>

<script>
import request from "@/api/api";

export default {
  components: {},
  props: {},
  data() {
    return {
      planName:"",
      plans: [],

      // 分页展示相关信息
      total:0,
      size:5,
      current:1,
      loading: false,
      hasMoreData: true,
    };
  },
  mounted() {
    // 在这里调用后端接口获取数据，然后更新plans
    this.getReservationPlanList();
  },
  methods: {
    // 跳转到详情页
    redirectToDetailPage(planId){
      const pathUrl = `/pages/admin/info_manage/reservation_plan_manage/reservation_plan_detail/reservation_plan_detail?planId=${encodeURIComponent(planId)}`;
      uni.navigateTo({url: pathUrl});

    },
    // 跳转到编辑页
    redirectToEditPage(planId){
      const pathUrl = `/pages/admin/info_manage/reservation_plan_manage/reservation_plan_edit/reservation_plan_edit?planId=${encodeURIComponent(planId)}`;
      uni.navigateTo({url: pathUrl});
    },
    // 删除预约信息操作
    deleteReservationPlan(planId){
      uni.showModal({
        title: '提示', // 弹窗标题
        content: '确认要删除此预约计划吗？此操作无法撤回！', // 弹窗内容
        showCancel: true, // 显示取消按钮
        confirmText: '确定', // 确定按钮的文字，默认为"确定"
        success: (res) => {
          if (res.confirm) {
            this.doDeleteReservationPlan(planId);
          }
        }
      });

    },
    // 确认删除预约信息
    doDeleteReservationPlan(planId){
      const url = `plan?planId=${planId}`;
      request(url,"DELETE").then(response=>{
        if (response.code == 0){
          this.showToast("预约计划删除成功！");
          setTimeout( () => {
            uni.redirectTo({url:"/pages/admin/info_manage/reservation_plan_manage/reservation_plan_manage"});
          }, 500)

        }else {
          this.showToast("预约计划删除失败:"+response.message);
        }
      }).catch(error=>{
        this.showToast(error.errMsg);
      })
    },
    getDataByPlanName(){
      let planName = this.planName.trim();
      if (planName === ""){
        this.current=1;
        this.hasMoreData = true;
        this.plans=[];
        this.getReservationPlanList();
        return;
      }
      const url = `plan/search?planName=${planName}`;
      request(url, "GET")
          .then(responseData => {
            if (responseData.code == 0) {
              this.plans = responseData.data; // 更新数据列表
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
    getReservationPlanList() {
      const url = `plan?pageNum=${this.current}&size=${this.size}`;
      request(url, "GET")
          .then(responseData => {
            if (responseData.code == 0) {
              console.log("Request successful:", responseData);

              this.plans = this.plans.concat(responseData.data.records); // 更新数据列表
              this.total = responseData.data.total; // 更新总数
              this.current++; // 更新当前页数

              this.loading = false; // 请求完成，重置 loading 状态

              // 检查是否还有更多数据，根据实际情况设置 hasMoreData
              this.hasMoreData = this.plans.length < this.total;
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
        this.getReservationPlanList();
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
      uni.redirectTo({url:"/pages/admin/info_manage/reservation_plan_manage/reservation_plan_manage"});
    }
  },
};
</script>

<style scoped lang="css">
.ml-19 {
  margin-left: 1.19rem;
}
.mt-31 {
  margin-top: 1.94rem;
}
.page {
  padding-bottom: 24rem;
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
  padding: 0.25rem 0.56rem;
  background-color: #f5f5f5;
  border-radius: 0.63rem;
  width: 15rem;
  height: 3.13rem;
}
.image {
  width: 2.5rem;
  height: 2.5rem;
}
.text {
  color: #808080;
  font-size: 1rem;
  font-family: SourceHanSerifCN;
  line-height: 0.92rem;
}
.text-wrapper {
  padding: 0.63rem 0 0.5rem;
  background-color: #2a82e4;
  border-radius: 0.31rem;
  height: 1.88rem;
}
.font {
  font-size: 0.88rem;
  font-family: SourceHanSerifCN;
  line-height: 0.81rem;
  color: #ffffff;
}
.text_2 {
  margin-left: 0.38rem;
  margin-right: 0.25rem;
  font-size: 0.81rem;
  line-height: 0.75rem;
}
.list {
  padding: 0 1rem;
}
.list-item {
  padding-bottom: 0.25rem;
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
.section_3 {
  opacity: 0.7;
  background-color: #f2f2f2;
  border-radius: 0.63rem 0.63rem 0rem 0rem;
  height: 2.63rem;
  border-left: solid 0.063rem #e5e5e5;
  border-right: solid 0.063rem #e5e5e5;
  border-top: solid 0.063rem #e5e5e5;
  border-bottom: solid 0.063rem #e5e5e5;
}
.font_2 {
  font-size: 1.25rem;
  font-family: SourceHanSerifCN;
  line-height: 1.17rem;
  color: #000000;
}
.pos {
  position: absolute;
  left: 1.08rem;
  top: 50%;
  transform: translateY(-50%);
}
.group {
  padding: 0 0.88rem;
}
.view {
  margin-top: 1.25rem;
}
.font_3 {
  font-size: 0.88rem;
  font-family: SourceHanSerifCN;
  line-height: 0.81rem;
  color: #383838;
}
.text_3 {
  line-height: 0.81rem;
}
.font_4 {
  font-size: 0.88rem;
  font-family: SourceHanSerifCN;
  line-height: 0.81rem;
  color: #808080;
}
.text_4 {
  line-height: 0.82rem;
}
.view_2 {
  margin-top: 1rem;
}
.group_2 {
  margin-left: 0.88rem;
  margin-top: 1rem;
}
.text_5 {
  line-height: 0.8rem;
}
.font_5 {
  font-size: 0.88rem;
  font-family: SourceHanSerifCN;
  line-height: 1.06rem;
  color: #808080;
}
.group_3 {
  margin-top: 0.75rem;
  padding: 0 4rem;
}
.text-wrapper_2 {
  padding: 0.38rem 0;
  background-color: #43cf7c;
  border-radius: 0.31rem;
  width: 3.13rem;
  height: 1.56rem;
}
.text_6 {
  font-size: 0.81rem;
  line-height: 0.75rem;
}
.text-wrapper_3 {
  padding: 0.38rem 0;
  background-color: #ff8d1a;
  border-radius: 0.31rem;
  width: 3.13rem;
  height: 1.56rem;
}
.text_7 {
  font-size: 0.81rem;
  line-height: 0.75rem;
}
.text-wrapper_4 {
  margin-right: 1rem;
  padding: 0.38rem 0;
  background-color: #ff5733;
  border-radius: 0.31rem;
  width: 3.13rem;
  height: 1.56rem;
}
.text_8 {
  font-size: 0.81rem;
  line-height: 0.75rem;
}
</style>