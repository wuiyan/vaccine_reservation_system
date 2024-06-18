<template>
<view class="flex-col page">
  <view class="flex-col group">
    <text class="self-start text">{{title}}</text>
    <text class="self-end text_2 mt-29">{{editTime}}</text>
  </view>
  <image
    class="image"
    :src="coverImage"
  />
  <view class="flex-col items-start group_2">
    <rich-text :nodes="content"/>
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
      title:null,
      content:null,
      editTime:null,
      coverImage:null,
	};
  },
  onLoad(query) {
      // 从 query 中获取传递的参数
      const popularId = query.popularId;
  
      // 在页面中使用获取到的参数
      console.log(popularId);
	  this.getScienceData(popularId);
    },

  methods: {
	  getScienceData(popularId){
	    const url = `popular/id?popularId=${popularId}`;
	  		request(url, "GET")
	  		  .then(responseData => {
	  			  if(responseData.code == 0){
	  				  console.log("Request successful:", responseData);
	  				  this.title = responseData.data.title;
	  				  this.content = responseData.data.content;
	  				  this.editTime = responseData.data.editTime;
	  				  this.coverImage = "data:image/png;base64," + responseData.data.coverImage;
	  				  
	  			  }else{
	  				  console.error("Request failed:", responseData.message);
	  			  }
	  		  })
	  		  .catch(error => {
	  			console.error("Request failed:", error);
	  		  });
	  	}
  },
};
</script>

<style scoped lang="css">
.mt-29 {
  margin-top: 1.81rem;
}
.page {
  padding: 4.75rem 0 21.5rem;
  background-color: #ffffff;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}
.group {
  padding-left: 3.25rem;
  padding-right: 0.88rem;
}
.text {
  color: #000000;
  font-size: 2.25rem;
  font-family: AlibabaPuHuiTi;
  line-height: 2.03rem;
}
.text_2 {
  color: #857f7f;
  font-size: 1.13rem;
  font-family: AlibabaPuHuiTi;
  line-height: 0.8rem;
}
.image {
  margin: 1.25rem 1.25rem 0;
  width: 90.6977vw;
  height: 40.6977vw;
}
.group_2 {
  margin-top: 2.75rem;
  padding: 0 2.13rem;
}
.font {
  font-size: 1.25rem;
  font-family: SourceHanSerifCN;
  line-height: 1.5rem;
  color: #000000;
}
</style>