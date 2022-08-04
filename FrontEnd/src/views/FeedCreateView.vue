<template>
    <div class="create-wrap">
        <HeaderTitle hasBack="true" title="게시글 작성" hasIcon="true"/>
        <!-- 사진 공간 -->
        <div class="flex">
          <div class="media-wrap" style="border: 1px solid black">
            <p>여긴 사진 공간이다 임마들아~!</p>
          </div>
        </div>
        <!-- 글(텍스트) 공간 -->
        <div class="flex">
          <textarea v-model="boardContent" name="" id="" cols="30" rows="10"></textarea>
        </div>
        <!-- 추가기록과 투표만들기 토글 -->
        <div class="flex">
          <div class="toggle-wrap">
            <button @click="record">추가 기록</button>
            <button @click="poll">투표 만들기</button>
          </div>
        </div>
        <div class="flex">
          <!-- 추가 기록 -->
          <div class="record-wrap" v-if="toggle">
            <p>기록하고 싶은 시간, 장소, 사람이 있나요?</p>
            <p>{{date}}</p>
            <p>{{boardLocation}}</p>
            <div class="record-flex">
              <div class="record-date">
                <p>언제?</p>
                <img :class="visibilityIcon" src="@/assets/images/calendar-check.svg" alt="calendar" style="width: 30px" data-bs-toggle="modal" data-bs-target="#withdraw">
              </div>
              <!-- 날짜 모달창 -->
              <div class="modal fade" id="withdraw" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h5 class="modal-title" id="exampleModalLabel">날짜 선택</h5>
                    </div>
                    <div class="modal-body">
                      <Datepicker placeholder="날짜를 선택해주세요." v-model="boardDate"/>
                    </div>
                    <div class="modal-footer">
                      <Button buttonClass="small positive" buttonText= "취소" class="btn" data-bs-dismiss="modal"></Button>
                      <Button buttonClass="small negative" buttonText="확인" class="btn" data-bs-dismiss="modal" @click="selectDate"></Button>
                    </div>
                  </div>
                </div>
              </div>
              <div class="record-location">
                <p>어디서?</p>
                <img :class="visibilityBack" src="@/assets/images/geo-alt.svg" alt="location" style="width: 30px" @click="showApi">
              </div>
              <div class="record-person">
                <p>누구랑?</p>
                <img :class="visibilityIcon" src="@/assets/images/person-circle.svg" alt="calendar" style="width: 30px">
              </div>
            </div>
          </div>
          <!-- 투표 만들기 -->
          <div v-else class="poll-wrap">
            <!-- 투표 제목 -->
            <div calss="poll-title-wrap">
              <input type="text" v-model="pollTitle" placeholder="투표 제목" class="poll-title-input">
            </div>
            <!-- 투표 항목 -->
            <p style="font-weight: bold">항목 입력</p>
            <div class="poll-item-wrap">
              <div>
                <input v-model="pollOption1" placeholder="항목을 입력하세요" class="poll-item-input">
              </div>
              <div>
                <input v-model="pollOption2" placeholder="항목을 입력하세요" class="poll-item-input">
              </div>
              <div>
                <input v-model="pollOption3" placeholder="항목을 입력하세요" class="poll-item-input">
              </div>
            </div>
            <!-- 투표 항목 추가 버튼 -->
            <button @click="addPollItem">항목 추가</button>
            <p>마감시간 설정</p>
            <input type="radio" class="poll-time">
          </div>
        </div>
        <button @click="check" style="color: red">확인</button>
        <!-- <router-link :to="{ name: 'feedCreateAdditional' }">추가 기록</router-link> |
        <router-link :to="{ name: 'feedCreatePoll' }">투표만들기</router-link>
        <router-view/> -->
    </div>
</template>
<script>
import HeaderTitle from '@/components/common/HeaderTitle.vue'
import InputBox from '@/components/common/InputBox.vue'
import Button from '@/components/common/Button.vue'

import Datepicker from 'vue3-datepicker'

export default {
  name: 'FeedCreateView',
  components: {
    HeaderTitle,
    InputBox,
    Datepicker,
    Button
  },
  data() {
    return {
      toggle: true,
      pollTitle: '',
      boardContent: '',
      boardLocation: '',
      boardDate: '',
      date: '',
      count: 1,
      pollOption: {
        pollOption1: '',
        pollOption2: '',
        pollOption3: '',
        pollOption4: '',
        pollOption5: ''
      }
    }
  },
  methods: {
    //추가기록과 투표만들기 토글
    record() {
      if(!this.toggle) {
        this.toggle = true
      }
    },
    poll() {
      if(this.toggle) {
        this.toggle = false
      }
    },
    check() {
      // console.log(this.pollTitle)
      console.log(this.boardContent)
      console.log(this.boardLocation)
      console.log(this.boardDate)
    },
    //투표 항목 추가하기
    addPollItem() {
      const pollItemCount = document.querySelector('.poll-item-wrap').childElementCount
      //항목은 5개까지 가능
      if(pollItemCount < 5) {
        console.log(pollItemCount)
        //div와 input을 만든다
        const pollItemDiv = document.createElement('div')
        const pollItemInput = document.createElement('input')
        //input 속성 설정
        pollItemInput.setAttribute('placeholder', '항목을 입력하세요')
        pollItemInput.setAttribute('class', 'poll-item-input')
        pollItemInput.setAttribute('v-model', 'pollOption4')

        //<div> <input> </div>가 되도록 div 안에 input을 자식으로 넣는다
        pollItemDiv.appendChild(pollItemInput)
        
        document.querySelector('.poll-item-wrap').appendChild(pollItemDiv)
      }
      else {
        alert('투표 항목은 5개까지 만들 수 있습니다.')
      }
    },
    //장소 선택
    selectDate() {
      //year = 년 , month = 월 , day = 일
      const year = this.boardDate.getFullYear()
      let month = (1 + this.boardDate.getMonth())
      let day = this.boardDate.getDate()
      //MM-DD의 형태로 만들기 위해 10보다 적은 경우 0을 붙이게 함 (예 : 1일이면 01)
      month = month >= 10 ? month : `0${month}`
      day = day >= 10 ? day: `0${day}`
      this.date = `${year}-${month}-${day}`
    },
    //위치 선택
    showApi() {
      console.log("주소")
      new window.daum.Postcode({
        oncomplete: (data) => {
          
          let fullRoadAddr = data.roadAddress; // 도로명 주소 변수
          let extraRoadAddr = ""; // 도로명 조합형 주소 변수

          if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
            extraRoadAddr += data.bname;
          }
    
          if (data.buildingName !== "" && data.apartment === "Y") {
            extraRoadAddr +=
              extraRoadAddr !== ""
                ? ", " + data.buildingName
                : data.buildingName;
          }
          if (extraRoadAddr !== "") {
            extraRoadAddr = " (" + extraRoadAddr + ")";
          }
          if (fullRoadAddr !== "") {
            fullRoadAddr += extraRoadAddr;
          }
          this.boardLocation = fullRoadAddr;
        },
      }).open();
    }
  }
}
</script>

<style lang="scss" scoped>
.create-wrap {
  width: 900px;
  .flex{
    display: flex;
    justify-content: center;
    text-align: center;
    width: 600px;
    margin: 0 auto;
    .media-wrap {
      width: 430px;
      height: 280px;
    }
    textarea {
      height: 300px;
      width: 500px;
    }
    button {
      margin: 0 10px 0 10px;
      color: black;
      background-color: white;
      text-decoration: underline;
    }
    .record-wrap {
      .record-flex {
        display: flex;
        justify-content: space-between;
      }
    }
    // .input-wrap{
    //   text-align: left;
    //   margin-bottom: 100px;
    // }
    // .name-wrap, .email-wrap, .id-wrap {
    //     margin: 10px 0 10px 0;
    //  }
  }
}
.record-date, .record-location, .record-person {
  display: inline-block;
}
</style>
