<template>
<view class="flex-col page">
  <view class="flex-col group">
    <view class="flex-col justify-start items-center text-wrapper"><text class="text">疫苗预约小程序</text></view>
    <view class="flex-col justify-start items-center relative section">
      <view class="flex-row">
        <view class="flex-col items-center equal-division-item" @tap="redirectSciencePage">
          <image
            class="image"
            src="../../../static/user/index/news_1.png"
          />
          <text class="font mt-17" >科普信息</text>
        </view>
        <view class="flex-col items-center equal-division-item" @tap="redirectVaccinePage">
          <image
            class="image"
            src="../../../static/user/index/news_2.png"
          />
          <text class="font mt-17">疫苗预约</text>
        </view>
      </view>
    </view>
  </view>
  <view v-for="(item, index) in dataList" :key="index" class="mt-70 flex-col justify-start items-center relative group_2" @tap="redirectArticlePage(item.id)">
        <view class="flex-col items-end section_2">
          <text class="text_2" style="width: 10rem">{{ item.title }}</text>
          <text class="text_3 mt-53">{{ item.editTime }}</text>
        </view>
        <image
          class="image_2 pos"
          style="height:100%"
          :src="item.coverImage"
        />
      </view>
	
	
	<view v-if="loading" class="loading">加载中...</view>
	<view v-if="!hasMoreData" class="no-more-data">到底了</view>
  <view class="tabBar-bottom">
    <commonUserTabBar :currentTab="currentTab"></commonUserTabBar>
  </view>
</view>
</template>

<script>
	import request from "@/api/api.js"
export default {
  components: {},
  props: {},
  data() {
    return {
      currentTab:"/pages/user/index/index",

      dataList: [],
      total:0,
      size:5,
      current:1,
      loading: false,
      hasMoreData: true,
	};
  },
  mounted() {
      // 在这里调用后端接口获取数据，然后更新dataList
      this.getScienceData();
    },
  methods: {
	  getScienceData(){
		  const url = `populars?pageNum=${this.current}&size=${this.size}`;
		        request(url, "GET")
		          .then(responseData => {
					  if(responseData.code == 0){
						  console.log("Request successful:", responseData);
						  
						  // 处理图片数据
						  responseData.data.records.forEach((record, index) => {
						        // 获取coverImage二进制数据
						        let binaryData = record.coverImage;
						  
						        // 如果coverImage存在，则进行处理
						        if (binaryData) {		
						          // 在页面上显示图片
						          let imageDataUrl = "data:image/png;base64," + binaryData;
								  
								  record.coverImage = imageDataUrl;
						  
						        }
						      });
						  this.dataList = this.dataList.concat(responseData.data.records); // 更新数据列表
						  this.total = responseData.data.total; // 更新总数
						  this.current++; // 更新当前页数
						  
						  this.loading = false; // 请求完成，重置 loading 状态
						  		  
						  // 检查是否还有更多数据，根据实际情况设置 hasMoreData
						  this.hasMoreData = this.dataList.length < this.total;
					  }else{
						  console.error("Request failed:", responseData.message);
						  // 处理请求失败的逻辑
						  this.loading = false; // 请求失败，重置 loading 状态
					  }
		          })
		          .catch(error => {
		            console.error("Request failed:", error);
		            // 处理请求失败的逻辑
		            this.loading = false; // 请求失败，重置 loading 状态
		          });
		      },
	  loadMoreData(){
			if (this.hasMoreData && !this.loading) {
				this.loading = true;
				this.getScienceData();

		}
	  },
	  redirectSciencePage(){
		  uni.navigateTo({
		           url: '/pages/user/index/knowledge/knowledge',});
	  },
	  redirectVaccinePage(){
      uni.navigateTo({url: '/pages/user/index/vaccine_reservation/vaccine_type_selection/vaccine_type_selection',});
	  },
	  redirectArticlePage(popularId){
		  console.log(popularId);
      uni.navigateTo({
        url: `/pages/user/index/knowledge/article_display/article_display?popularId=${popularId}`,});
    },
    onReachBottom() {
      this.loadMoreData();
    }
	}
};
</script>

<style scoped lang="css">
	.loading {
	  text-align: center;
	  padding: 10px;
	  color: #333;
	}
	
	.no-more-data {
	  text-align: center;
	  padding: 10px;
	  color: #999;
	}
	
	
.mt-17 {
  margin-top: 1.06rem;
}
.mt-53 {
  margin-top: 3.31rem;
}
.page {
  padding: 0.25rem 0 18rem;
  background-color: #ccf3eb;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}
.group {
  height: 26.75rem;
}
.text-wrapper {
  padding: 6.25rem 0 8.63rem;
  background-image: url("../../../static/user/index/backgroundImage.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
}
.text {
  color: #ffffff;
  font-size: 2.25rem;
  font-family: HuXiaoBoNanShen;
  line-height: 2.11rem;
}
.section {
  margin: -2.44rem 1.5rem 0;
  padding: 2.13rem 0 1.5rem;
  background-color: #ffffff;
  border-radius: 1.25rem;
}
.equal-division-item {
  padding: 0.38rem 0;
  width: 10.31rem;
}
.image {
  width: 5.73rem;
  height: 5.73rem;
}
.font {
  font-size: 1.13rem;
  font-family: SourceHanSerifCN;
  line-height: 1.04rem;
  color: #000000;
}
.group_2 {
  padding-top: 0.88rem;
}
.section_2 {
  padding: 1.88rem 0.63rem 0.75rem;
  background-color: #ffffff;
  width: 22.5rem;
}
.text_2 {
  margin-right: 0.38rem;
  color: #000000;
  font-size: 1rem;
  font-family: SourceHanSerifCN;
  line-height: 1.34rem;
}
.text_3 {
  color: #000000;
  font-size: 1rem;
  font-family: SourceHanSerifCN;
  line-height: 0.76rem;
}
.image_2 {
  width: 8.88rem;
  height: 8.88rem;
}
.pos {
  position: absolute;
  left: 2.19rem;
  bottom: 0;
}
</style>