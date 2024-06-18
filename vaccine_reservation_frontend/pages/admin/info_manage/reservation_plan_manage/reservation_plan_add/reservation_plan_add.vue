<template>
  <view class="flex-col page">
    <view class="flex-col self-stretch">
      <view class="flex-col justify-start items-center text-wrapper">
        <text class="text">新增预约计划</text>
      </view>
      <view class="shrink-0 section"></view>
      <view class="flex-col section_2">
        <view class="flex-col">
          <view class="flex-row justify-between items-center">
            <text class="font text_2">标题：</text>
            <view class="section_3 view">
              <input v-model="showForm.planName" type="text" placeholder="请输入计划名称"/>
            </view>
          </view>
          <view class="mt-10 flex-row items-center">
            <text class="font text_3">疫苗名称：</text>
            <view class="section_3 ml-33" style="position: relative;">
              <input v-model="showForm.vaccineName" @input="getVaccinesPrompt('vaccine')" type="text" placeholder="请输入疫苗名称"/>
              <view v-if="vaccinePrompt.length > 0 && vcFlag===0" class="prompt-box">
                <ul>
                  <li v-for="(item, index) in vaccinePrompt" :key="index" @tap="selectVaccinePromptInfo(item)" >
                    {{ item.vcName }}
                  </li>
                </ul>
              </view>
            </view>
            </view>
          </view>
          <view class="mt-10 flex-row items-center">
            <text class="font text_4">疫苗编号：</text>
            <view class="section_3 ml-33">
              <input v-model="showForm.vaccineNumber" type="text" placeholder="请输入疫苗编号" disabled/>
            </view>
          </view>
          <view class="mt-10 flex-row items-center">
            <text class="font">接种地点：</text>
            <view class="section_3 ml-33" style="position: relative;">
                <input v-model="showForm.place" @tap="getVaccinesPrompt('place')" type="text" placeholder="请选择接种地点" disabled/>
                <view v-if="vaccinePrompt.length > 0 && vcFlag===1" class="prompt-box">
                  <ul>
                    <li v-for="(item, index) in vaccinePrompt" :key="index" @tap="selectPlaceInfo(item)" >
                      {{ item.place }}
                    </li>
                  </ul>
            </view>
          </view>
        </view>
        <view class="mt-10 flex-row items-center">
          <text class="font text_5">预约时间设置：</text>
          <view class="flex-row justify-between items-center input section_4" @tap="redirectToTimePage('/pages/admin/info_manage/reservation_plan_manage/reservation_plan_time/reservation_plan_time')">
            <text v-if="showForm.rvPlan.length===0" class="text_6">请配置可预约时段</text>
            <text v-else class="text_6">已配置</text>
            <image
                class="image"
                src="/static/global/common/enter.png"
            />
          </view>
        </view>
      </view>
    </view>
    <view class="flex-col justify-start items-center self-center text-wrapper_2 mt-116" @tap="submitReservationPlan">
      <text class="font text_7">新增</text>
    </view>
  </view>
</template>

<script>
import request from "@/api/api";

export default {
  components: {},
  props: {},
  onLoad() {
    uni.$on('getPlans',plans=>{
      let tempVar = JSON.parse(JSON.stringify(plans));
      this.showForm.rvPlan = tempVar.plans;
    })
  },
  onUnload() {
    uni.$off('getPlans');
    uni.$off('sendPlans');
  },
  data() {
    return {
      showForm:{
        planName:"",
        vaccineId:null,
        vaccineName:"",
        vaccineNumber:"",
        place:"",
        rvPlan:[],
      },
      submitForm:{
        planName:"",
        vaccineId:null,
        vaccineNumber:"",
        rvPlan:"",
      },

      vaccinePrompt:[],
      // 0 表示显示疫苗名称提示，1 显示地点信息提示
      vcFlag:0,
      // 最大提示信息数量
      size:5
    };
  },

  methods: {
    // 提交表单
    submitReservationPlan(){
      this.submitForm.planName = this.showForm.planName;
      this.submitForm.vaccineId = this.showForm.vaccineId;
      this.submitForm.vaccineNumber = this.showForm.vaccineNumber;
      this.submitForm.rvPlan = JSON.stringify(this.showForm.rvPlan);

      request("plan","POST",this.submitForm)
          .then(responseData => {
            if (responseData.code == 0) {
              console.log("Request successful:", responseData);
              // 新增信息成功后，显示需要点击的弹窗提示
              uni.showModal({
                title: '提示', // 弹窗标题
                content: '新增预约计划成功', // 弹窗内容
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
              this.showToast("新增预约计划失败："+responseData.message)
            }
          }).catch(error => {
        this.showToast(error.errMsg);
      });
    },
    // 选择选项
    selectPlaceInfo(item){
      this.showForm.place = item.place;
      this.vaccinePrompt = [];
    },
    selectVaccinePromptInfo(item){
      this.showForm.vaccineId = item.id;
      this.showForm.vaccineName = item.vcName;
      this.showForm.vaccineNumber = item.vcNumber;

      // 归零相应信息
      this.showForm.place = "";

      // 将提示列表归零
      this.vaccinePrompt = [];
    },
    // 通过疫苗名称获取预约计划提示信息
    getVaccinesPrompt(flag){
      if (flag === "vaccine"){
        this.vcFlag = 0;
      }else {
        this.vcFlag = 1;
      }
      let vcName = this.showForm.vaccineName.trim();
      const url = `vaccines/vcName?vcName=${vcName}&pageNum=1&size=${this.size}`;
      request(url, "GET")
          .then(responseData => {
            if (responseData.code == 0) {
              console.log("Request successful:", responseData);
                this.vaccinePrompt = responseData.data.records; // 更新数据列表
            } else {
              this.showToast("Request failed:" + responseData.message);
            }
          })
          .catch(error => {
            console.error("Request failed:", error);
            this.showToast("Request failed:" + error);
          });
    },
    // 跳转到子页面
    redirectToTimePage(pathUrl){
      uni.$emit('sendPlans', {
        plans: this.showForm.rvPlan
      });
      uni.navigateTo({url: pathUrl});
    },
  },
};
</script>

<style scoped lang="css">
/*提示框*/
.prompt-box {
  position: absolute;
  top: 100%;
  left: 0;
  width: 100%;
  z-index: 999; /* 可以根据需要设置合适的 z-index 值 */
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}


.ml-33 {
  margin-left: 2.06rem;
}

.mt-116 {
  margin-top: 7.25rem;
}

.page {
  padding-bottom: 30.44rem;
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
  padding: 0.38rem 2.63rem 4.13rem;
  background-color: #ffffff;
  border-left: solid 0.063rem #e5e5e5;
  border-right: solid 0.063rem #e5e5e5;
  border-bottom: solid 0.063rem #e5e5e5;
}

.font {
  font-size: 1rem;
  font-family: SourceHanSerifCN;
  line-height: 0.92rem;
  color: #383838;
}

.text_2 {
  line-height: 0.92rem;
}

.section_3 {
  background-color: #ffffff;
  width: 11.44rem;
  height: 1.56rem;
  border-bottom: solid 0.05rem #000000;
}

.view {
  margin-right: 3rem;
}

.text_3 {
  line-height: 0.93rem;
}

.text_4 {
  line-height: 0.93rem;
}

.text_5 {
  line-height: 0.93rem;
}

.input {
  flex: 1 1 0;
  margin-right: 3rem;
}

.section_4 {
  padding: 0.13rem 0 0.13rem 1.63rem;
  background-color: #ffffff;
  height: 1.56rem;
  border-bottom: solid 0.05rem #000000;
}

.text_6 {
  color: #ff8d1a;
  font-size: 0.75rem;
  font-family: SourceHanSerifCN;
  line-height: 0.69rem;
}

.image {
  width: 1.25rem;
  height: 1.25rem;
}

.text-wrapper_2 {
  padding: 0.88rem 0 0.75rem;
  background-color: #2a82e4;
  border-radius: 0.31rem;
  width: 5rem;
}

.text_7 {
  color: #ffffff;
  line-height: 0.92rem;
}
</style>