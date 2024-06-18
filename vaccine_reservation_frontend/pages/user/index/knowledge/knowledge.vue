<template>
<view class="flex-col page">
  <view class="flex-col justify-start items-center relative section">
    <view class="flex-row items-center section_2">
      <image
        class="image"
        src="/static/global/searchBar/搜索.png"
      />
      <view class="text ml-19">
        <input type="text" v-model="title" @blur="getDataByTitle" placeholder="搜索标题" />
      </view>
    </view>
  </view>
  <view class="mt-20 flex-col list">
    <view class="flex-row mt-20 list-item" v-for="(item, index) in dataList" :key="index" @tap="redirectArticlePage(item.id)">
      <image
        class="image_2"
        :src=item.coverImage
      />
      <view class="flex-col flex-1 self-start group ml-33">
        <text class="self-start font">{{item.title}}</text>
        <text class="mt-54 self-end font_2">{{item.editTime}}</text>
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
      title:"",
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
    getDataByTitle(){
      let title = this.title.trim();
      if (title === ""){
        this.current=1;
        this.hasMoreData = true;
        this.dataList=[];
        this.getScienceData()
        return;
      }
      const url = `popular/search?title=${title}`;
      request(url, "GET")
          .then(responseData => {
            if (responseData.code == 0) {
              this.dataList = responseData.data; // 更新数据列表
              // 处理图片数据
              responseData.data.forEach((record, index) => {
                // 获取coverImage二进制数据
                let binaryData = record.coverImage;

                // 如果coverImage存在，则进行处理
                if (binaryData) {
                  // 在页面上显示图片
                  let imageDataUrl = "data:image/png;base64," + binaryData;

                  record.coverImage = imageDataUrl;

                }
              });
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
    onReachBottom() {
      this.loadMoreData();
    },
    redirectArticlePage(popularId){
      console.log(popularId);
      uni.navigateTo({
        url: `/pages/user/index/knowledge/article_display/article_display?popularId=${popularId}`,});

    }
  },
};
</script>

<style scoped lang="css">
.ml-19 {
  margin-left: 1.19rem;
}
.ml-33 {
  margin-left: 2.06rem;
}
.page {
  padding-bottom: 23.31rem;
  background-color: #f2f2f2;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}
.section {
  padding: 2.13rem 0;
  background-color: #ffffff;
  box-shadow: 0rem 0.13rem 0.25rem #00000040;
}
.section_2 {
  padding: 0.25rem 0.56rem;
  background-color: #f5f5f5;
  border-radius: 0.63rem;
  width: 22.88rem;
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
.list {
  padding: 0 1.5rem;
}
.list-item {
  background-color: #ffffff;
  border-radius: 0.63rem;
}
.list-item:first-child {
  margin-top: 0;
}
.image_2 {
  border-radius: 0.63rem;
  width: 8.56rem;
  height: 8rem;
}
.group {
  margin-right: 0.88rem;
  margin-top: 1.88rem;
}
.font {
  font-size: 1.25rem;
  font-family: SourceHanSerifCN;
  line-height: 1.16rem;
  color: #000000;
}
.font_2 {
  font-size: 1rem;
  font-family: MiSans;
  line-height: 0.79rem;
  color: #808080;
}
</style>