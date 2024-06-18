<!--疫苗种类选择页面-->
<template>
<view class="flex-col page">
  <view class="flex-col">
    <view class="flex-col justify-start items-center relative section">
      <view class="flex-row items-center">
        <text class="ml-20 text">疫苗种类选择</text>
      </view>
    </view>
    <!-- 横向滚动的顶部导航栏 -->
	<uni-scroll-view class="nav-scroll" scroll-x :scroll-with-animation="true">
	  <view v-for="(item, index) in navItems" :key="index" @tap="switchTab(index)" :class="['nav-item', { 'active': currentIndex === index }]">
	    {{ item }}
	    <view v-if="currentIndex === index" class="active-indicator"></view> <!-- 活跃状态指示器 -->
	  </view>
	</uni-scroll-view>
  </view>
  <view class="horizontal-line"></view>
  
  <view class="mt-40 flex-row justify-between items-center section_4" v-for="(vaccine, index) in vaccines" :key="index" @tap="redirectToOtherPage(vaccine.vcNumber)">
    <text class="text_2">{{ vaccine.vcName }}</text>
    <image class="image_2" src="/static/user/reservation/enter.png" />
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
		navItems: ['流感疫苗', 'HPV疫苗', '乙肝疫苗','肺炎疫苗', '狂犬病疫苗', '甲肝疫苗','水痘疫苗', '破伤风疫苗'], // 顶部导航栏的选项
		vaccines:[
			  {
				"vcName": "四价流感病毒裂解疫苗",
			  },
			  {
				"vcName": "HPV疫苗",
			  }
			],
		currentIndex: 0, // 当前选中的导航栏索引
		scrollLeft: 0 // 横向滚动的位置
	};
  },
	mounted() {
	    // 在这里调用后端接口获取数据，然后更新dataList
	    this.getVaccineInfoByType();
	  },
  methods: {
	  switchTab(index) {
	        // 切换导航栏选项时更新当前索引，并滚动到对应的内容页面
	        this.currentIndex = index;
			this.getVaccineInfoByType();
	      },
		  getVaccineInfoByType(){
			const url = `vaccines/type?type=${this.currentIndex}`;
			request(url, "GET")
			  .then(responseData => {
				  if(responseData.code == 0){
						this.vaccines = responseData.data;
				  }else{
					  uni.showToast({
					          title: "Request failed:"+ responseData.message,
					          icon: 'none', // 不显示图标
					          duration: 2000 // 提示持续时间，单位毫秒
					        });

				  }
			  })
			  .catch(error => {
				console.error("Request failed:", error);

			  });

		  },
	  redirectToOtherPage(vcNumber) {
	      const url = `/pages/user/index/vaccine_reservation/vaccine_place_selection/vaccine_place_selection?name=${encodeURIComponent(vcNumber)}`;
	      uni.navigateTo({
	        url: url
	      });
	    }
  },
};
</script>

<style scoped lang="css">
	// 顶部滚动导航栏
	.nav-scroll {
	  white-space: nowrap;
	  overflow-x: scroll;
	  background-color: #f5f5f5;
	  padding: 10upx;
	}
	
	.nav-scroll view {
	  display: inline-block;
	  padding: 10upx;
	  cursor: pointer;
	}
	
	.nav-scroll view.active {
	  color: #007aff;
	}
	
	.nav-item {
	  display: inline-block;
	  padding: 10upx;
	  cursor: pointer;
	  position: relative; /* 添加这个以便于定位指示器 */
	}
	
	.active-indicator {
	  width: 20px; /* 小横杠的宽度 */
	  height: 1px; /* 调整小横杠的高度为1px */
	  padding: 5rpx !important;
	  background-color: #007aff; /* 小横杠的颜色 */
	  position: absolute;
	  bottom: -5px; /* 调整以适应你的布局，增加距离上方内容的间隔 */
	  left: 50%;
	  transform: translateX(-50%); /* 水平居中 */
	}



	.content-scroll {
	  white-space: nowrap;
	  height: 150upx; /* 内容区域高度 */
	  border: 1px solid #ddd;
	}
	
	.content-scroll .content-item {
	  display: inline-block;
	  width: 100%;
	  height: 150upx; /* 内容页面高度 */
	  text-align: center;
	  line-height: 150upx;
	}
	.horizontal-line {
	  width: 100%;
	  height: 1px; /* 根据需要调整 */
	  background-color: #cccccc; /* 水平线颜色 */
	  margin-top: 5px; /* 根据需要调整 */
	}

	
.page {
  padding-bottom: 39.13rem;
  background-color: #f2f2f2;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}
.section {
  padding: 2.13rem 0 2.06rem;
  background-color: #ffffff;
  box-shadow: 0rem 0.13rem 0.25rem #00000040;
}
.section_2 {
  padding: 0.25rem 0.56rem 0.25rem;
  background-color: #f5f5f5;
  border-radius: 0.63rem;
  width: 22.88rem;
}
.image {
  width: 2.5rem;
  height: 2.5rem;
}
.text {
  color: black;
  font-size: 1.5rem;
  font-family: SourceHanSerifCN;
  line-height: 0.93rem;
}
.section_3 {
  background-color: #ffffff;
  height: 3.56rem;
}
.section_4 {
  margin-left: 1.5rem;
  margin-right: 1.44rem;
  padding: 1.75rem 1.44rem 1.75rem 2.75rem;
  background-color: #ffffff;
  border-radius: 0.63rem;
}
.text_2 {
  color: #000000;
  font-size: 1.25rem;
  font-family: SourceHanSerifCN;
  line-height: 1.18rem;
}
.image_2 {
  width: 1.56rem;
  height: 1.56rem;
}
</style>