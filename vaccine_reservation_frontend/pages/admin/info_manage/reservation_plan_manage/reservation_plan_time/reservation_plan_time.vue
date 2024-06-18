<template>
  <view class="flex-col page">
    <view class="flex-col justify-start items-center text-wrapper">
      <uni-calendar
          :insert="true"
          :showMonth="false"
          :selected="selected"
          @change="change"
      />
    </view>
    <view class="flex-col group">
      <view class="flex-col justify-start items-center text-wrapper_2"><text class="font">时段设置</text></view>
      <view class="mt-28 flex-col">
        <view class="flex-col mt-28 list-item" v-for="(selectDate, index) in selected" :key="index">
          <text class="self-center font text_2">{{ selectDate.date }}</text>
          <view class="flex-col self-stretch group_2" v-for="(plan,childIndex) in plans[index].plan" :key="childIndex">
            <view class="flex-row">
              <view class="flex-row justify-center items-center flex-1">
                <view class="section">
                  <picker mode="time" :value="plan" :id="index + '_' + childIndex" @change="bindStartTimeChange">
                    {{plan.startTime}}
                  </picker>
                </view>
                <text class="font_2 ml-7">~</text>
                <view class="section ml-7">
                  <picker mode="time" :value="plan" :id="index + '_' + childIndex" @change="bindEndTimeChange">
                    {{plan.endTime}}
                  </picker>
                </view>
              </view>
              <view class="shrink-0 section ml-23">
                <input v-model="plan.planNum" type="number"/>
              </view>
              <image
                  class="shrink-0 image ml-23"
                  src="/static/global/common/delete.png"
                  @tap="deleteTimeRange(index,childIndex)"
              />
            </view>
          </view>
          <text class="self-center font_3 text_3" @tap="addTimeRange(index)">添加时段</text>
        </view>
      </view>
    </view>
    <view class="flex-col justify-start items-center text-wrapper_3 fixed" @tap="saveTimeData">
      <text class="text_4">保存时间设置</text>
    </view>
  </view>
</template>

<script>
export default {
  components: {},
  props: {},
  onLoad() {
    let data = uni.getStorageSync('plans');//取出缓存数据
    let plans = JSON.parse(data);
    this.plans = plans;
    uni.removeStorageSync('plans');
    // 将其与已选择日期匹配
    for (let i = 0; i < this.plans.length; i++) {
      let date = this.plans[i].date;
      console.log(date);
      this.selected.push({
        date:date,
        info:'已设置'
      });
    }

  },
  data() {
    return {
      // 字符串格式的当前时间，格式为YYYY-MM-DD
      nowDate:new Date().toISOString().split('T')[0],
      // 已选择日期
      selected:[],
      // 计划信息存放
      plans: [],
    };
  },

  methods: {
    // 保存时间设置，将数据传递给父级
    saveTimeData(){
      let plans = this.plans;
      uni.$emit('getPlans', {
        plans: this.plans
      });
      // 返回上一级
      uni.navigateBack();
    },
    change(e) {
      console.log('change 返回:', e);
      // 若已选中日期则取消选中
      for (let i = 0; i <this.selected.length; i++) {
        if(this.selected[i].date === e.fulldate){
          // 移除指定下标的元素
          this.selected.splice(i,1);
          this.plans.splice(i,1);
          return;
        }
      }
      // 选中日期
      this.selected.push({
        date:e.fulldate,
        info:'已设置'
      });
      // 将日期加入计划中
      this.plans.push({
        date:e.fulldate,
        plan:[{
          startTime:"09:00",
          endTime:"18:00",
          planNum:50,
          bookedNum:0
        }]
      })
    },
    // 时间选择器的选中方法
    bindStartTimeChange(e){
      let id = e.target.id;
      let i = id.split("_")[0];
      let j = id.split("_")[1];
      this.plans[i].plan[j].startTime = e.detail.value;
    },
    bindEndTimeChange(e){
      let id = e.target.id;
      let i = id.split("_")[0];
      let j = id.split("_")[1];
      this.plans[i].plan[j].endTime = e.detail.value;
    },
    // 添加时间段
    addTimeRange(index){
      this.plans[index].plan.push({
        startTime:"09:00",
        endTime:"18:00",
        planNum:50,
        bookedNum:0
      });
    },
    // 删除时间段
    deleteTimeRange(index,childIndex){
      this.plans[index].plan.splice(childIndex,1);
    },
  },
};
</script>

<style scoped lang="css">
.fixed{
  position: fixed;
  bottom: 0;
  width: 100%;
}

.ml-7 {
  margin-left: 0.44rem;
}
.ml-23 {
  margin-left: 1.44rem;
}
.page {
  padding-top: 2rem;
  background-color: #e7f5ff;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}
.text-wrapper {
  background-color: #ffffff;
}
.text {
  color: #000000;
  font-size: 1.13rem;
  font-family: SourceHanSansCN;
  line-height: 0.98rem;
}
.group {
  padding: 4.25rem 0;
}
.text-wrapper_2 {
  padding: 1.63rem 0;
  background-color: #ffffff;
}
.font {
  font-size: 1.13rem;
  font-family: SourceHanSerifCN;
  line-height: 1.03rem;
  color: #000000;
}
.list-item {
  padding: 0.88rem 4rem;
  background-color: #ffffff;
}
.list-item:first-child {
  margin-top: 0;
}
.text_2 {
  line-height: 1.05rem;
}
.group_2 {
  margin-top: 0.63rem;
}
.section {
  background-color: #ffffff;
  width: 4.13rem;
  height: 1.94rem;
  border-left: solid 0.063rem #a6a6a6;
  border-right: solid 0.063rem #a6a6a6;
  border-top: solid 0.063rem #a6a6a6;
  border-bottom: solid 0.063rem #a6a6a6;
}
.font_2 {
  font-size: 1.13rem;
  font-family: SourceHanSerifCN;
  line-height: 0.17rem;
  color: #000000;
}
.image {
  width: 1.88rem;
  height: 1.88rem;
}
.font_3 {
  font-size: 1.13rem;
  font-family: SourceHanSerifCN;
  line-height: 1.03rem;
  color: #2a82e4;
}
.text_3 {
  margin-top: 1.13rem;
}
.text-wrapper_3 {
  padding: 0.88rem 0 0.75rem;
  background-color: #2a82e4;
  border-radius: 0.31rem;
}
.text_4 {
  color: #ffffff;
  font-size: 1rem;
  font-family: SourceHanSerifCN;
  line-height: 0.93rem;
}
</style>