 <template>
<view class="flex-col page">
  <view class="flex-col justify-start items-center relative section">
    <view class="flex-row items-center">
      <text class="ml-20 font1">疫苗预约地点列表</text>
    </view>
  </view>
  <view v-if="vaccines.length===0" style="margin: 10rem auto auto;">
    <text style="color: #b5470d;font-weight: bold;font-size: 16px;">当前疫苗无预约计划！</text>
  </view>
  <view class="flex-col section_3 mt-27" v-for="(vaccine, index) in vaccines" :key="index" @tap="redirectToOtherPage(vaccine.id)">
    <view class="flex-row justify-between items-baseline group">
      <text class="font_2 text">{{ vaccine.vcName }}</text>
      <text class="font_2 text_2">￥{{ vaccine.price }}</text>
    </view>
    <view class="flex-row items-center mt-21">
      <image class="image_2" src="/static/user/reservation/place.png" />
      <text class="font text_3 ml-5">{{ vaccine.place }}</text>
    </view>
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
		vaccines:[{
      "vcName":"四价流感病毒裂解疫苗",
      "price":192,
      "place":"XXX市级XXX路接种中心"
    }],
	};
  },
	onLoad(options) {
	    // 通过options对象接收传递的参数
	    const vcNumber = decodeURIComponent(options.name); // 如果你之前使用了encodeURIComponent编码参数
		if(vcNumber){
			this.getVaccineInfoByType(vcNumber);
		}
		
	  },
  methods: {
			redirectToOtherPage(id){
			const url = `/pages/user/index/vaccine_reservation/reservation_info_selection/reservation_info_selection?id=${id}`;
	  	  		  uni.navigateTo({
					  url: url
				  });
	  	  },
			getVaccineInfoByType(vcNumber){
				const url = `vaccines/vcNumber?vcNumber=${vcNumber}`;
				request(url, "GET")
				  .then(responseData => {
					  if(responseData.code == 0){
							this.vaccines = responseData.data;
					  }else{
						  console.error("Request failed:", responseData.message);
	  
					  }
				  })
				  .catch(error => {
					console.error("Request failed:", error);
	  
				  });
		  			  		    
		  },
  },
};
</script>

<style scoped lang="css">
.mt-27 {
  margin-top: 1.69rem;
}
.mt-21 {
  margin-top: 1.31rem;
}
.ml-5 {
  margin-left: 0.31rem;
}
.page {
  padding-bottom: 40.81rem;
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
.font {
  font-size: 1rem;
  font-family: SourceHanSerifCN;
  line-height: 0.93rem;
  color: #808080;
}
.font1 {
  font-size: 1.5rem;
  font-family: SourceHanSerifCN;
  line-height: 0.93rem;
  color: black;
}
.section_3 {
  margin-left: 1.5rem;
  margin-right: 1.44rem;
  padding: 2.06rem 1.22rem 2.06rem 1.75rem;
  filter: drop-shadow(0rem 0.13rem 0.13rem #00000040, 0rem 0.13rem 0.16rem #00000040);
  background-color: #ffffff;
  border-radius: 0.63rem;
}
.group {
  padding-left: 0.13rem;
}
.font_2 {
  font-size: 1.25rem;
  font-family: SourceHanSerifCN;
}
.text {
  color: #000000;
  line-height: 1.18rem;
}
.text_2 {
  color: #ff8d1a;
  line-height: 0.96rem;
}
.image_2 {
  width: 1.88rem;
  height: 1.88rem;
}
.text_3 {
  line-height: 0.92rem;
}
</style>