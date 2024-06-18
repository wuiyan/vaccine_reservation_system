<template>
  <view class="tab-bar">
    <view v-for="(tab, index) in tabs" :key="index" @click="switchTab(tab)">
      <image :src="currentTab === tab.pagePath ? tab.selectedIconPath : tab.iconPath" mode="aspectFit"></image>
      <text :class="currentTab === tab.pagePath ? 'active' : ''">{{ tab.text }}</text>
    </view>
  </view>
</template>


<script>
export default {
  props: {
    currentTab: {
      type: String,
      required: true
    }
  },
  computed: {
    tabs() {
      return [
        {
          pagePath: '/pages/admin/index/index',
          iconPath: '/static/global/tabBar/home.png',
          selectedIconPath: '/static/global/tabBar/home_cur.png',
          text: '首页'
        },
        {
          pagePath: '/pages/admin/person_center/person_center',
          iconPath: '/static/global/tabBar/person.png',
          selectedIconPath: '/static/global/tabBar/person_cur.png',
          text: '个人中心'
        }
      ];
    }
  },
  methods: {
    switchTab(tab) {
      uni.reLaunch({
        url: tab.pagePath
      });
    }
  }
};
</script>


<style scoped>
.tab-bar {
  justify-content: space-around;
  align-items: center;
  height: 120upx;
  background-color: #f7f7f7;
  border-top: 1px solid #e6e6e6;
  position: fixed;
  bottom: 0;
  z-index: 999;
  width: 100%;
  display: flex;
  justify-content: space-around;
  padding-bottom: 30upx;
}

.tab-bar view {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.tab-bar image {
  width: 25px;
  height: 25px;
}

.tab-bar text {
  font-size: 12px;
  margin-top: 5px;
}

.tab-bar text.active {
  font-weight: bold;
}

</style>