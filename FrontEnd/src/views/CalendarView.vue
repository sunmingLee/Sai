<template>
  <div class="calendar-wrap">
    <!-- 색상 설명 -->
    <div class="explain-wrap">
      <span class="personal">빨강: 개인</span>
      <span class="family">초록: 가족</span>
      <span class="anniversary">파랑: 기념일</span>
    </div>
    <!-- 달력 -->
    <v-calendar @update:from-page="pageChange" is-expanded :rows="1" :attributes='planList' ref="calendar" :masks="{ dayPopover: ['YYYY MM DD, WWWW']}">
        <template v-slot:footer>
            <div class="footer-wrap">
                <Button buttonClass="small information" buttonText="오늘로" @click="moveToToday"></Button>
            </div>
        </template>
    </v-calendar>
    <!-- 일정 상세보기 -->
    <ul class="plan-list">
      <div v-for="plan in planDetail" :key="plan.mainPlanId">
        <li v-if="plan.mainPlanStartDatetime.slice(0, 4) == currentYear && plan.mainPlanStartDatetime.slice(5, 7) == currentMonth">
          <div v-if="plan.mainPlanEndDatetime == null || plan.mainPlanStartDatetime.slice(0, 10) === plan.mainPlanEndDatetime.slice(0, 10)">
            <span class="date">{{plan.mainPlanStartDatetime.slice(8, 10)}}</span>
            <span>{{plan.planTitle}}</span>
            <span class="place" v-if="plan.planPlace != ''">(장소: {{plan.planPlace}})</span>
            <button class="edit" data-bs-toggle="modal" data-bs-target="#editModal"><img src="@/assets/images/pencil-fill.svg" alt="edit" @click="setPlan(plan)"></button>
            <button type="button" class="btn-close" aria-label="Close" @click="erasePlan(plan.mainPlanId)"></button>
          </div>
          <div v-else>
            <span class="date">{{plan.mainPlanStartDatetime.slice(8, 10)}} ~ {{plan.mainPlanEndDatetime.slice(8, 10)}}</span>
            <span>{{plan.planTitle}}</span>
            <span class="place" v-if="plan.planPlace != ''">{{plan.planPlace}}</span>
            <button class="edit" data-bs-toggle="modal" data-bs-target="#editModal"><img src="@/assets/images/pencil-fill.svg" alt="edit" @click="setPlan(plan)"></button>
            <button type="button" class="btn-close" aria-label="Close" @click="erasePlan(plan.mainPlanId)"></button>
          </div>
        </li>
      </div>
    </ul>

    <!-- 일정 작성하기 -->
    <!-- Button trigger modal -->
    <button id="btn-modal" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
    <img style="width:25px;" src="@/assets/images/plus-lg.svg" alt="plus">
    </button>

    <!-- Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">일정 추가</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="flex">
                    <span>제목</span>
                    <input v-model="newPlan.planTitle" type="text">
                </div>
                <div class="flex">
                    <span>날짜</span>
                    <Datepicker v-model="newPlan.date" format='yyyy/MM/dd' modelType="yyyy-MM-dd HH:mm:ss" :enableTimePicker="false" range selectText="선택" cancelText="취소"></Datepicker>
                </div>
                <div class="flex">
                    <span>장소</span>
                    <input v-model="newPlan.planPlace" type="text">
                </div>
                <div>
                    <input type="radio" id="radioPersonal" name="planType" value="personal" v-model="newPlan.planType" />
                    <label for="radioPersonal">개인</label>
                    <input type="radio" id="radioFamily" name="planType" value="family" v-model="newPlan.planType"/>
                    <label for="radioFamily">가족</label>
                    <input type="radio" id="radioAnniversary" name="planType" value="anniversary" v-model="newPlan.planType"/>
                    <label for="radioAnniversary">기념일</label>
                    <img class="info" src="@/assets/images/circle-info-solid.svg" alt="information" @click="showInfo">
                </div>
                <div class="type-info" v-if="hasInfo">
                  개인 : 개인 약속 및 일정<br/>
                  가족 : 가족 여행, 제사 등 가족 행사<br/>
                  기념일 : 생일, 결혼기념일 등 축하 일정
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="hideInfo">취소</button>
                <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="writeCreate">일정추가</button>
            </div>
            </div>
        </div>
    </div>

    <!-- 일정 수정하기 -->
    <!-- Modal -->
    <div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editModalLabel">일정 수정</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="flex">
                    <span>제목</span>
                    <input v-model="modifiedPlan.planTitle" type="text">
                </div>
                <div class="flex">
                    <span>날짜</span>
                    <Datepicker v-model="modifiedPlan.date" format='yyyy/MM/dd' modelType="yyyy-MM-dd HH:mm:ss" :enableTimePicker="false" range selectText="선택" cancelText="취소"></Datepicker>
                </div>
                <div class="flex">
                    <span>장소</span>
                    <input v-model="modifiedPlan.planPlace" type="text">
                </div>
                <div>
                    <input type="radio" id="radioPersonal" name="planType" value="personal" v-model="modifiedPlan.planType" />
                    <label for="radioPersonal">개인</label>
                    <input type="radio" id="radioFamily" name="planType" value="family" v-model="modifiedPlan.planType"/>
                    <label for="radioFamily">가족</label>
                    <input type="radio" id="radioAnniversary" name="planType" value="anniversary" v-model="modifiedPlan.planType"/>
                    <label for="radioAnniversary">기념일</label>
                    <img class="info" src="@/assets/images/circle-info-solid.svg" alt="information" @click="showInfo">
                </div>
                <div class="type-info" v-if="hasInfo">
                  개인 : 개인 약속 및 일정<br/>
                  가족 : 가족 여행, 제사 등 가족 행사<br/>
                  기념일 : 생일, 결혼기념일 등 축하 일정
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="hideInfo">취소</button>
                <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="editPlan">일정수정</button>
            </div>
            </div>
        </div>
    </div>
    <BottomTap feedLink="nav-link" refrideLink="nav-link" albumLink="nav-link" calendarLink="nav-link active"></BottomTap>
  </div>
</template>

<script>
import { VCalendar } from 'v-calendar'
import 'v-calendar/dist/style.css'
import Datepicker from '@vuepic/vue-datepicker'
import BottomTap from '@/components/common/BottomTap.vue'
import Button from '@/components/common/Button.vue'

import { mapActions, mapState } from 'vuex'
const planStore = 'planStore'

export default {
  components: {
    VCalendar,
    Datepicker,
    BottomTap,
    Button
  },
  created () {
    // 일정 조회
    const familyId = localStorage.getItem('familyId')
    // 일정 전체 조회
    this.planAllList(familyId)
  },
  computed: {
    ...mapState(planStore, ['planList', 'planDetail'])
  },
  data () {
    return {
      newPlan: {
        userId: localStorage.getItem('userId'),
        familyId: localStorage.getItem('familyId'),
        planTitle: '',
        planPlace: '',
        planType: '',
        date: new Date(),
        planNotiYn: false
      },
      modifiedPlan: {
        userId: localStorage.getItem('userId'),
        familyId: localStorage.getItem('familyId'),
        planTitle: '',
        planPlace: '',
        planType: '',
        date: '',
        planNotiYn: false,
        mainPlanId: ''

      },
      currentYear: '',
      currentMonth: '',
      hasInfo: false
    }
  },
  methods: {
    ...mapActions(planStore, ['planAllList', 'planCreate', 'getOnePlan', 'planModify', 'planDelete']),
    // 설명 보여주기
    showInfo () {
      this.hasInfo = true
    },
    // 설명 숨기기
    hideInfo () {
      this.hasInfo = false
    },
    // 일정 만들기
    writeCreate () {
      if (this.newPlan.planTitle === '') {
        alert('제목을 작성해주세요.')
      } else {
        this.planCreate(this.newPlan)
      }
    },
    // 오늘날짜로 이동
    moveToToday () {
      this.$refs.calendar.move(new Date())
    },
    // 현재 보고있는 연도, 월 받아오기
    pageChange (page) {
      this.currentYear = page.year
      this.currentMonth = page.month
    },
    // 수정할 일정값 data에 넣기
    setPlan (plan) {
      this.modifiedPlan.mainPlanId = plan.mainPlanId
      this.modifiedPlan.planTitle = plan.planTitle
      this.modifiedPlan.planPlace = plan.planPlace
      this.modifiedPlan.planType = plan.planType
      const startDate = plan.mainPlanStartDatetime.slice(0, 10) + ' ' + plan.mainPlanStartDatetime.slice(11)
      let endDate = ''
      if (plan.mainPlanEndDatetime == null) {
        endDate = plan.mainPlanStartDatetime.slice(0, 10) + ' ' + plan.mainPlanStartDatetime.slice(11)
      } else {
        endDate = plan.mainPlanEndDatetime.slice(0, 10) + ' ' + plan.mainPlanEndDatetime.slice(11)
      }
      this.modifiedPlan.date = { 0: startDate, 1: endDate }
    },
    // 일정 수정하기
    editPlan () {
      if (this.modifiedPlan.planTitle === '') {
        alert('제목을 작성해주세요.')
      } else {
        this.planModify(this.modifiedPlan)
      }
    },
    // 일정 삭제하기
    erasePlan (planId) {
      this.planDelete(planId)
    }
  }
}
</script>

<style scoped lang="scss">
.calendar-wrap{
    width: 100%;
}
.explain-wrap{
  position: absolute;
  top: 6%;
  right: 3%;
  span{
    margin-left: 10px;
  }
  .personal{
    color: red
  }
  .family{
    color: green
  }
  .anniversary{
    color: blue
  }
}
.vc-container{
    top: 10%;
}
.footer-wrap{
    background-color: #F0EAE3;
    text-align: center;
    padding: 10px;
}
.today{
    background-color: #A57966;
}
.btn{
    height: 40px;
    &-primary{
    --bs-btn-bg: #7b371c;
    --bs-btn-border-color: #7b371c;
    --bs-btn-hover-bg: #54210d;
    --bs-btn-hover-border-color: #54210d;
    --bs-btn-focus-shadow-rgb: none;
    }
    &#btn-modal{
        position: absolute;
        right: 10%;
        bottom: 10%;
        border-radius: 50%;
        width: 50px;
        height: 50px;
    }
}
.flex{
    display: flex;
    justify-content: space-between;
    margin-bottom: 5%;
    .dp__main{
        width: 80%;
    }
    input {
    width: 80%;
    border-radius: 5px;
    border: 1px solid #ae5f40;
    }
}
label{
  margin-right: 10px;
}
.info{
  width: 20px;
  height: 20px;
  float: right;
}
.plan-list{
  position: relative;
  top: 12%;
  height: 33vh;
  overflow-y: scroll;
  background-color: #F0EAE3;
  padding: 0;
  li{
    padding: 10px;
  }
}
.date{
  font-size: large;
  font-weight: bold;
  margin-right: 15px;
}
.place{
  font-size: small;
  margin-left: 15px;
}
.btn-close{
  float: right;
}
.edit{
  margin-left: 15px;
  background-color: inherit;
}
.type-info{
  background-color: #F0EAE3;
  margin-top: 20px;
  padding: 15px;
}
</style>
