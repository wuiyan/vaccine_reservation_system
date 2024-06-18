<template>
  <view class="flex-col page">
    <view class="flex-col self-stretch">
      <view class="flex-col justify-start items-center text-wrapper">
        <text class="text">编辑科普信息</text>
      </view>
      <view class="shrink-0 section"></view>
      <view class="flex-col section_2">
        <view class="flex-col">
          <view class="flex-row items-center">
            <text class="font">文章标题：</text>
            <view class="ml-18 section_3">
              <input type="text" v-model="inputForm.title" placeholder="请输入文章标题" />
            </view>
          </view>
          <view class="mt-10 flex-row">
            <text class="self-start font text_2">封面图：</text>
            <view class="self-center section_3 ml-35">
              <text style="color: gray;" @tap="onImageSelect">{{coverImagePath.length > maxLength ? coverImagePath.substring(0, maxLength) + '...' : coverImagePath}}</text>
            </view>
          </view>
        </view>
        <view class="mt-28 flex-col">
          <text class="self-start font text_3">正文内容：</text>
          <view class="mt-20 self-stretch section_4">
            <editor id="editor" placeholder="请输入正文内容……"
                    @ready="onEditorReady" @input="getEditorContent">
            </editor>
          </view>
        </view>
      </view>
    </view>
    <view class="flex-col justify-start text-area text-wrapper_2 mt-29" @tap="popularScienceInfoSubmit">
      <text class="font text_4">保存文章</text>
    </view>
  </view>
</template>

<script>
import request from "@/api/api";

export default {
  components: {},
  props: {},
  onLoad(options) {
    // 通过options对象接收传递的参数
    const popularId = decodeURIComponent(options.popularId); // 如果你之前使用了encodeURIComponent编码参数
    if(popularId){
      this.getPopularScienceInfoById(popularId);
    }
  },
  data() {
    return {
      coverImagePath:"点击更换封面图",
      maxLength:25,
      inputForm:{
        id:null,
        title:"",
        coverImage:null,
        content:""
      }
    };
  },

  methods: {
    // 获取科普信息
    getPopularScienceInfoById(popularId){
      const url = `popular/id?popularId=${popularId}`;
      request(url, "GET")
          .then(responseData => {
            if(responseData.code == 0){
              this.inputForm.id = responseData.data.id;
              this.inputForm.title = responseData.data.title;
              this.inputForm.content = responseData.data.content;
              this.inputForm.coverImage =  responseData.data.coverImage;

              this.coverImagePath = "http://tmp/" + responseData.data.coverImage.replaceAll("/","").substring(0,44);
            }else{
              this.showToast("获取科普信息失败："+responseData.message)
            }
          })
          .catch(error => {
            this.showToast("获取科普信息失败:"+error.errMsg);
          });
    },
    // 提交科普信息
    popularScienceInfoSubmit() {
      console.log(this.inputForm);
      const url = `popular`;
      request(url, "PUT",this.inputForm)
          .then(responseData => {
            if (responseData.code == 0) {
              console.log("Request successful:", responseData);
              // 新增信息成功后，显示需要点击的弹窗提示
              uni.showModal({
                title: '提示', // 弹窗标题
                content: '编辑科普信息成功', // 弹窗内容
                showCancel: false, // 不显示取消按钮
                confirmText: '确定', // 确定按钮的文字，默认为"确定"
                success: function (res) {
                  if (res.confirm) {
                    const pages = getCurrentPages();
                    const prevPage = pages[pages.length - 2]; // 上一个页面
                    uni.navigateBack({
                      success: () => {
                        prevPage.$vm.refresh(); // 直接调用上个页面的刷新方法
                      }
                    });                  }
                }
              });
            } else {
              this.showToast("编辑科普信息失败："+responseData.message)
            }
          })
          .catch(error => {
            this.showToast(error.errMsg);
          });
    },
    // 图片选择
    onImageSelect(){
      uni.chooseImage({
        // 图片张数
        count: 1,
        success:res=>{
          // 接受选择的图片地址
          this.coverImagePath= res.tempFiles[0].path;
          // 接收图片数据
          uni.getImageInfo({
            src: this.coverImagePath,
            success:  (getImageInfoRes) => {
              const ctx = uni.getFileSystemManager();
              ctx.readFile({
                filePath: getImageInfoRes.path,
                encoding: 'base64',
                success: (readFileRes) => {
                  console.log('图片的base64数据：', readFileRes.data);
                  this.inputForm.coverImage = readFileRes.data;
                }
              });
            }
          });
        }
      })
    },
    // 设置富文本框的初始值
    onEditorReady(){
      // 富文本节点渲染完成
      const query = uni.createSelectorQuery().in(this);
      query.select('#editor')
          .context(res => {
            this.editorCtx = res.context;
            if (this.inputForm.content) {
              this.editorCtx.setContents({
                html: this.inputForm.content
              });
            }
          })
          .exec(this);
    },
    getEditorContent(e){
      this.inputForm.content = e.detail.html;
    }
  },
};
</script>

<style scoped lang="css">
.ml-35 {
  margin-left: 2.19rem;
}
.mt-29 {
  margin-top: 1.81rem;
}
.page {
  padding-bottom: 28.19rem;
  background-color: #e7f5ff;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}
.text-wrapper {
  padding: 0.75rem 0 0.63rem;
  background-color: #f2f2f2;
  border-left: solid 0.063rem #e5e5e5;
  border-right: solid 0.063rem #e5e5e5;
  border-top: solid 0.063rem #e5e5e5;
  border-bottom: solid 0.072rem #ffffff;
}
.text {
  color: #000000;
  font-size: 1.25rem;
  font-family: SourceHanSerifCN;
  line-height: 1.16rem;
}
.section {
  background-color: #ffffff;
  height: 0.63rem;
  border-left: solid 0.063rem #e5e5e5;
  border-right: solid 0.063rem #e5e5e5;
  border-top: solid 0.063rem #e5e5e5;
}
.section_2 {
  padding: 0.38rem 3.13rem 2.25rem;
  background-color: #ffffff;
  border-left: solid 0.063rem #e5e5e5;
  border-right: solid 0.063rem #e5e5e5;
  border-bottom: solid 0.063rem #e5e5e5;
}
.font {
  font-size: 1rem;
  font-family: SourceHanSerifCN;
  line-height: 0.93rem;
  color: #383838;
}
.section_3 {
  background-color: #ffffff;
  width: 11.44rem;
  height: 1.56rem;
  border-bottom: solid 0.05rem #000000;
}
.text_2 {
  margin-top: 0.88rem;
  line-height: 0.91rem;
}
.text_3 {
  line-height: 0.92rem;
}
.section_4 {
  margin-left: 0.13rem;
  margin-right: 0.38rem;
  background-color: #ffffff;
  height: 12.11rem;
  border-left: solid 0.05rem #000000;
  border-right: solid 0.05rem #000000;
  border-top: solid 0.05rem #000000;
  border-bottom: solid 0.05rem #000000;
}
.text-area {
  align-self: center;
}
.text-wrapper_2 {
  padding: 0.88rem 0 0.75rem;
  background-color: #2a82e4;
  border-radius: 0.31rem;
}
.text_4 {
  margin-left: 0.63rem;
  margin-right: 0.38rem;
  color: #ffffff;
}
</style>