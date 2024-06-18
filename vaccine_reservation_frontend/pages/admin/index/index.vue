<template>
<view class="flex-col page">
  <view class="flex-col justify-start items-center relative text-wrapper">
    <text class="text">疫苗预约管理系统</text>
  </view>
  <view class="flex-row equal-division mt-35">
    <view class="flex-col items-center group equal-division-item" @tap="redirectToOtherPage('/pages/admin/info_manage/userInfo_manage/userInfo_manage')">
      <image
        class="image_2"
        src="/static/admin/index/user.png"
      />
      <text class="font text_1 mt-13">用户管理</text>
    </view>
    <image
      class="image horiz-divider"
      src="/static/admin/index/divider.png"
    />
    <view class="flex-col items-center group group_2" @tap="redirectToOtherPage('/pages/admin/info_manage/vaccineInfo_manage/vaccineInfo_manage')">
      <image
        class="image_2"
        src="/static/admin/index/vaccine.png"
      />
      <text class="mt-14 font">疫苗管理</text>
    </view>
    <image
      class="image_1 horiz-divider"
      src="/static/admin/index/divider.png"
    />
    <view class="flex-col items-center group equal-division-item" @tap="redirectToOtherPage('/pages/admin/info_manage/popularScienceInfo_manage/popularScienceInfo_manage')">
      <image
        class="image_2"
        src="/static/admin/index/book.png"
      />
      <text class="font text_6 mt-13">科普管理</text>
    </view>
  </view>
  <view class="flex-col mt-35">
    <view class="flex-row justify-between section">
      <view class="flex-row items-center" @tap="redirectToOtherPage('/pages/admin/info_manage/reservationInfo_manage/reservationInfo_manage')">
        <image
          class="shrink-0 image_3"
          src="/static/admin/index/clock.png"
        />
        <text class="font text_2 ml-11">预约信息管理</text>
      </view>
      <image
        class="image_3 image_8"
        src="/static/admin/index/enter.png"
      />
    </view>
    <view class="mt-26 flex-row justify-between section_2" @tap="redirectToOtherPage('/pages/admin/info_manage/reservation_plan_manage/reservation_plan_manage')">
      <view class="flex-row items-center">
        <image
          class="shrink-0 image_3"
          src="/static/admin/index/plan.png"
        />
        <text class="ml-10 font text_3">预约计划管理</text>
      </view>
      <image
        class="image_3 image_5"
        src="/static/admin/index/enter.png"
      />
    </view>
    <view class="mt-26 flex-row justify-between section_3" @tap="redirectToOtherPage('/pages/admin/vaccination/vaccination')">
      <view class="flex-row items-center">
        <image
          class="shrink-0 image_3"
          src="/static/admin/index/vaccine.png"
        />
        <text class="font ml-13">疫苗接种管理</text>
      </view>
      <image
        class="image_3 image_6"
        src="/static/admin/index/enter.png"
      />
    </view>
  </view>
  <view class="tabBar-bottom">
    <CommonAdminTabBar :currentTab="currentTab"></CommonAdminTabBar>
  </view>
</view>
</template>

<script>

export default {
  components: {},
  props: {},
  data() {
    return {
      currentTab:"/pages/admin/index/index"
    };
  },

  methods: {
    // 跳转到子页面
    redirectToOtherPage(pathUrl){
      uni.navigateTo({url: pathUrl});
    }
  },
};
</script>

<style scoped lang="css">
.mt-35 {
  margin-top: 2.19rem;
}
.ml-11 {
  margin-left: 0.69rem;
}
.mt-13 {
  margin-top: 0.81rem;
}
.ml-13 {
  margin-left: 0.81rem;
}
.page {
  padding-bottom: 11.25rem;
  background-color: #e7f5ff;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}
.text-wrapper {
  padding: 6rem 0 5.37rem;
  background-color: #4dabf7;
  border-radius: 0rem 0rem 0.63rem 0.63rem;
  box-shadow: 0rem 0.13rem 0.25rem #00000040;
}
.text {
  color: #ffffff;
  font-size: 2.25rem;
  font-family: HuXiaoBoNanShen;
  line-height: 2.11rem;
}
.equal-division {
  padding: 0.23rem 0.18rem 0.23rem;
  background-color: #ffffff;
}
.group {
  flex: 1 1 8.84rem;
}
.equal-division-item {
  padding: 1.93rem 0 1.73rem;
}
.image_2 {
  width: 2.87rem;
  height: 2.87rem;
}
.image {
  margin: 0.36rem 0 0.36rem;
}
.group_2 {
  padding: 1.93rem 0 1.66rem;
}
.image_1 {
  margin: 0.36rem 0 0.36rem;
}
.horiz-divider {
  width: 0.063rem;
  height: 7.75rem;
}
.section {
  padding: 1.56rem 1.75rem 1.5rem;
  background-color: #ffffff;
  border-radius: 0.63rem;
}
.image_3 {
  width: 3.13rem;
  height: 3.13rem;
}
.image_8 {
  margin-right: 0.31rem;
}
.section_2 {
  padding: 1.56rem 1.81rem 1.5rem;
  background-color: #ffffff;
  border-radius: 0.63rem;
}
.font {
  font-size: 1.25rem;
  font-family: SourceHanSerifCN;
  line-height: 1.16rem;
  color: #000000;
}
.text_2 {
  line-height: 1.17rem;
}
.text_6 {
  line-height: 1.15rem;
}
.text_1 {
  line-height: 1.16rem;
}
.text_3 {
  line-height: 1.16rem;
}
.image_5 {
  margin-right: 0.25rem;
}
.section_3 {
  padding: 1.56rem 1.62rem 1.56rem;
  background-color: #ffffff;
  border-radius: 0.63rem;
}
.image_6 {
  margin-right: 0.44rem;
}
</style>