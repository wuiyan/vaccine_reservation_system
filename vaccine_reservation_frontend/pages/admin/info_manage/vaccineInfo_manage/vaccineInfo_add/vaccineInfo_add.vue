<template>
  <view class="flex-col page">
    <view class="flex-col self-stretch">
      <view class="flex-col justify-start items-center text-wrapper"><text class="text">新增疫苗信息</text></view>
      <view class="shrink-0 section"></view>
      <view class="flex-col section_2">
        <view class="flex-col">
          <view class="flex-row items-center">
            <text class="font text_2">疫苗编号：</text>
            <view class="ml-18 section_3">
              <input type="text" v-model="inputForm.vcNumber" placeholder="请输入疫苗编号" />
            </view>
          </view>
          <view class="mt-10 flex-row items-center">
            <text class="font">疫苗名称：</text>
            <view class="ml-18 section_3">
              <input type="text" v-model="inputForm.vcName" placeholder="请输入疫苗名称" />
            </view>
          </view>
          <view class="mt-10 flex-row items-center">
            <text class="font">疫苗种类：</text>
            <view class="ml-18 section_3">
              <picker mode="selector" :range="doseOptions" :value="selectedDose" @change="onDoseChange">
                <view class="picker">
                  {{doseOptions[selectedDose]}}
                </view>
              </picker>
            </view>
          </view>
          <view class="mt-10 flex-row items-center">
            <text class="font text_3">参考价格：</text>
            <view class="ml-18 section_3">
              <input type="number" v-model="inputForm.price" placeholder="请输入疫苗参考价格" />
            </view>
          </view>
        </view>
        <view class="mt-10 flex-row justify-between items-center">
          <text class="font text_4">剂次：</text>
          <view class="section_3 view">
            <input type="number" v-model="inputForm.dose" placeholder="请输入疫苗剂次" />
          </view>
        </view>
        <view class="mt-10 flex-row items-center">
          <text class="font text_5">间隔时间：</text>
          <view class="ml-18 section_3">
            <input type="number" v-model="inputForm.spaceTime" placeholder="请输入疫苗接种间隔时间" />
          </view>
        </view>
        <view class="mt-10 flex-row justify-between items-center">
          <text class="font text_6">地点：</text>
          <view class="section_3 view_2">
            <input type="text" v-model="inputForm.place" placeholder="请输入疫苗接种地点" />
          </view>
        </view>
      </view>
    </view>
    <view class="mt-38 flex-col justify-start self-center text-wrapper_2" @tap="vaccineInfoSubmit" >
      <text class="font text_7">新增疫苗</text>
    </view>
  </view>
</template>

<script>
import {VACCINE_TYPE} from "@/common/constants";
import request from "@/api/api";
export default {
  components: {},
  props: {},
  data() {
    return {
      doseOptions: ['流感疫苗', 'HPV疫苗', '乙肝疫苗','肺炎疫苗', '狂犬病疫苗', '甲肝疫苗','水痘疫苗', '破伤风疫苗'],
      selectedDose: 0,
      // 输入表单信息
      inputForm:{
        vcNumber:"",
        vcName:"",
        type:"流感疫苗",
        price:null,
        dose:null,
        spaceTime:null,
        place:""
      },
    };
  },

  methods: {
    vaccineInfoSubmit() {
      let submitForm = this.inputForm;
      let strType = submitForm.type;
      for (const key in VACCINE_TYPE) {
        if (VACCINE_TYPE[key] === strType) {
          submitForm.type = key;
        }
      }
      console.log(submitForm);
      const url = `vaccines`;
      request(url, "POST",submitForm)
          .then(responseData => {
            if (responseData.code == 0) {
              console.log("Request successful:", responseData);
              // 新增信息成功后，显示需要点击的弹窗提示
              uni.showModal({
                title: '提示', // 弹窗标题
                content: '新增疫苗信息成功', // 弹窗内容
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
              // 新增信息成功后，显示需要点击的弹窗提示
              uni.showToast({
                title: "新增疫苗信息失败:" + responseData.message,
                icon: 'none', // 不显示图标
                duration: 2000 // 提示持续时间，单位毫秒
              });

            }
          })
          .catch(error => {
            console.error("Request failed:", error);
            // 新增信息成功后，显示需要点击的弹窗提示
            uni.showToast({
              title: "疫苗添加失败:" + error,
              icon: 'none', // 不显示图标
              duration: 2000 // 提示持续时间，单位毫秒
            });

          });
    },
    onDoseChange(event) {
      console.log(event.target.value);
      this.selectedDose = event.target.value; // 更新选择的剂次
      this.inputForm.type = this.doseOptions[this.selectedDose];
    },
  },
};
</script>

<style scoped lang="css">
.page {
  padding-bottom: 33.31rem;
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
  line-height: 1.17rem;
}
.section {
  background-color: #ffffff;
  height: 0.63rem;
  border-left: solid 0.063rem #e5e5e5;
  border-right: solid 0.063rem #e5e5e5;
  border-top: solid 0.063rem #e5e5e5;
}
.section_2 {
  padding: 0.38rem 3.13rem 1.88rem;
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
.text_2 {
  line-height: 0.93rem;
}
.section_3 {
  background-color: #ffffff;
  width: 11.44rem;
  height: 1.56rem;
  border-bottom: solid 0.05rem #000000;
}
.text_3 {
  line-height: 0.92rem;
}
.text_4 {
  line-height: 0.93rem;
}
.view {
  margin-right: 2.88rem;
}
.text_5 {
  line-height: 0.93rem;
}
.text_6 {
  line-height: 0.91rem;
}
.view_2 {
  margin-right: 2.88rem;
}
.text-wrapper_2 {
  padding: 0.88rem 0 0.75rem;
  background-color: #2a82e4;
  border-radius: 0.31rem;
}
.text_7 {
  margin-left: 0.5rem;
  margin-right: 0.38rem;
  color: #ffffff;
  line-height: 0.93rem;
}
</style>