<template>
    <div class="create-wrap">
      <p>{{callsign}}</p>
        <HeaderTitle hasBack="true" title="게시글 작성" hasIcon="true"/>
        <!-- 사진 공간 -->
        <div class="flex">
          <input type="file" @change="fileCheck" id="file" multiple>
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
            <div class="record-flex">
              <div class="record-date">
                <p>언제?</p>
                <img :class="visibilityIcon" src="@/assets/images/calendar-check.svg" alt="calendar" style="width: 30px" @click="showDate">
              </div>
              <!-- 날짜 모달창 -->
              <div class="modal hidden" id="size">
                <div class="modal-overlay">
                  <div class="modal-content">
                    <p class="modal-title">날짜 선택</p>
                    <div class="modal-date">
                      <Datepicker placeholder="날짜를 선택해주세요." class="datepicker" :enableTimePicker="false" v-model="boardDate"/>
                    </div>
                    <div class="btn-wrap">
                      <button class="date-cancle" @click="dateCancle">취소</button>
                      <button class="date-confirm" @click="dateConfirm">확인</button>
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
                <img :class="visibilityIcon" src="@/assets/images/person-circle.svg" alt="calendar" style="width: 30px" @click="showFamily">
              </div>
              <!-- 사람 선택 모달창 -->
              <div class="person hidden">
                <div class="person-overlay">
                  <div class="person-content">
                    <p class="person-title">사람 선택</p>
                    <div class="person-date">
                      <div v-for="(callsign, index) in familyCallsignList" :key="index">
                        <label for="callsign-select">{{callsign.callsign}}</label>
                        <input type="checkbox" name="callsign" :value="family" class="callsign-select">
                      </div>
                    </div>
                    <div class="btn-wrap">
                      <button class="date-cancle" @click="personCancle">취소</button>
                      <button class="date-confirm" @click="personConfirm">확인</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div>
              <p v-if="date !== ''">시간 : {{date}}</p>
              <button class="delete-date" @click="deleteDate" v-if="date !== ''">삭제</button>
              <p>위치 : {{boardLocation}}</p>
              <button class="delete-date" @click="deleteLocation" v-if="boardLocation !== ''">삭제</button>
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
              <div v-for="(option, index) in pollOptions" :key="index">
                <input name="option" v-model="option.pollOption" placeholder="항목을 입력하세요">
              </div>
            </div>
            <!-- 투표 항목 추가 버튼 -->
            <button @click="addPollItem">항목 추가</button>
            <p>마감시간 설정</p>
            <input type="checkbox" class="poll-time" @click="pollTimeCheck">
            <Datepicker placeholder="날짜를 선택해주세요." class="datepicker" :minDate="new Date()" v-model="pollDatePicker" :disabled="pollDateDisabled"/>
          </div>
        </div>
        <button @click="check" style="color: red">테스트 확인</button>
        <button @click="feedCreate" style="color: blue">작성</button>
    </div>
</template>
<script>
import HeaderTitle from '@/components/common/HeaderTitle.vue'
import InputBox from '@/components/common/InputBox.vue'
import Button from '@/components/common/Button.vue'

import Datepicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'
import { mapState, mapActions } from 'vuex'

const boardStore = 'boardStore'
const userStore = 'userStore'
const familyStore = 'familyStore'
          
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
      dateValid: false,
      boardContent: '', //글
      boardRegDatetime: '', //등록 시간
      boardMediaYn: 0, //미디어 파일 등록 여부
      pollYn: 0, //투표 등록 여부
      boardDate: '', //추가 기록 - 날짜
      boardLocation: '',  //추가 기록 - 위치
      boardTaggedYn: 0, //추가 기록 - 태그 여부
      date: '', //추가 기록(날짜) 선택했을 때 화면에 보여질 날짜
      toggle: true, //추가 기록과 투표 만들기를 토글하기 위함
      pollTitle: '', //투표 제목
      //투표 항목들
      pollOptions: [
        {pollOption: ''},
        {pollOption: ''},
      ],
      pollDatePicker: '', //투표 마감 날짜로 선택한 날(보내는 데이터 값은 아님)
      pollDateDisabled: true, //투표 마감 날짜 지정 여부..
      pollEndDate: '', //투표 마감 날짜
      //미디어 리스트
      mediaList: [
        
      ],
      //태그 리스트
      peopleList: [

      ],
      disabledDates :'',
      preventDisableDateSelection: true,
      format: ''
    }
  },
  created() {
    const userId = localStorage.getItem('userId')
    this.callsignList(userId)
    console.log(this.callsignList)
  },
  computed: {
    // ...mapState(userStore, ["userId", "userName"]),
    ...mapState(familyStore, ["familyCallsignList"])
  },
  methods: {
    ...mapActions(boardStore, ['boardCreate']),
    ...mapActions(familyStore, ['callsignList']),
    //추가기록과 투표만들기 토글
    fileCheck() {
      console.log("ㅎㅇ")
      const fileInput = document.getElementById("file")
      console.log(fileInput.files)

    },
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
      // console.log(this.)
      
    },
    //투표 항목 추가하기
    addPollItem() {
      if(this.pollOptions.length < 5) {
        this.pollOptions.push({pollOption : ''})
      }
      else {
        alert('투표 항목은 5개까지 가능합니다.')
      }
    },
    //투표 마감 시간 설정
    pollTimeCheck() {
      //투표 마감 시간을 설정한다고 체크
      if(!this.pollDateDisabled) {
        this.pollDateDisabled = true
      }
      else {
        this.pollDateDisabled = false
        // this.pollDate = this.pollDate
      }
    },
    //날짜 선택 모달창 출력
    showDate() {
      const modal = document.querySelector('.modal')
      modal.classList.remove('hidden')
    },
    //누구랑?
    showFamily() {
      const modal = document.querySelector('.person')
      modal.classList.remove('hidden')
    },
    //날짜 선택 모달창에서 확인 버튼 클릭
    dateConfirm() {
      const modal = document.querySelector('.modal')
      //year = 년 , month = 월 , day = 일
      const year = this.boardDate.getFullYear()
      let month = (1 + this.boardDate.getMonth())
      let day = this.boardDate.getDate()
      //MM-DD의 형태로 만들기 위해 10보다 적은 경우 0을 붙이게 함 (예 : 1일이면 01)
      month = month >= 10 ? month : `0${month}`
      day = day >= 10 ? day: `0${day}`
      this.date = `${year}-${month}-${day}`
      //모달창 숨기기
      modal.classList.add('hidden')
    },
    //날짜 선택 모달창에서 취소 버튼 클릭
    dateCancle() {
      const modal = document.querySelector('.modal')
      this.boardDate = ''
      modal.classList.add('hidden')
    },
    //날짜 삭제
    deleteDate() {
      this.date = ''
      this.boardDate = ''
    },
    //사람 태그 확인 
    personConfirm() {
      const test = document.getElementsByName('callsign')
      for(let i = 0; i < test.length; i++) {
        //체크 했을 때
        if(test[i].checked) {
          if(this.peopleList.indexOf(test[i].value) < 0) {
            this.peopleList.push(Object(test[i].value))
          }
        }
        else {
          //체크 해제의 경우
          if(this.peopleList.indexOf(test[i].value) > -1) {
            const index = this.peopleList.indexOf(test[i].value)
            this.peopleList.splice(index, 1)
          }
        }
      }
      //확인 버튼을 클릭했을 경우 모달창을 끈다
      const modal = document.querySelector('.person')
      modal.classList.add('hidden')
    },
    //사람 취소
    personCancle() {
      const modal = document.querySelector('.person')
      modal.classList.add('hidden')
    },
    //위치 선택
    showApi() {
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
    },
    //위치 삭제
    deleteLocation() {
      this.boardLocation = ''
    },
    //게시글 작성
    feedCreate() {
      //미디어 or 글 or 투표 중 하나라도 있어야 게시글 작성이 가능하다
      if(this.mediaList.length === 0 && this.boardContent === '' && this.pollYn === 0) {
        alert('글이나 사진을 등록해야 작성이 가능합니다.')
      }
      else {
        const createBoardRequestDto = {
          inputBoardRequestDto: {
            familyId: localStorage.getItem('familyId'),
            userId: localStorage.getItem('userId'),
            boardRegDatetime: new Date(),
            boardContent: this.boardContent,
            boardDate: this.boardDate,
            boardLocation: this.boardLocation,
            boardTaggedYn: this.boardTaggedYn,
            boardMediaYn: this.boardMediaYn,
            pollYn: this.pollYn
          }
        }

        //미디어 파일이 있다!
        if(this.mediaList.length !== 0) {
          this.boardMediaYn = 1
        }
        //투표는 최소한 두 항목이 적혀 있어야 투표가 있다고 할 수 있다
        if(this.pollTitle !== '' && this.pollOptions[0].pollOption !== '' && this.pollOptions[1].pollOption) {
          this.pollYn = 1
        }
        //투표가 있을 경우
        if(this.pollYn === 1) {
          let setPollDate = new Date()
          //투표 마감 시간 설정 안 했을 때
          if(this.pollDateDisabled) {
            //따로 마감 시간을 설정하지 않으면 현재 시간에서 3일 뒤에 끝나도록 설정
            setPollDate = setPollDate.setDate(setPollDate.getDate() + 3)
          }
          const year = new Date(setPollDate).getFullYear()
          let month = new Date(setPollDate).getMonth() + 1
          if(month < 10) {
            month = '0' + month
          }
          let date = new Date(setPollDate).getDate()
          if(date < 10) {
            date = '0' + date
          }
          let hours = new Date(setPollDate).getHours()
          if(hours < 10) {
            hours = '0' + hours
          }
          let minutes = new Date(setPollDate).getMinutes()
          if(minutes < 10) {
            minutes = '0' + minutes
          }
          let seconds = new Date(setPollDate).getSeconds()
          if(seconds < 10) {
            seconds = '0' + seconds
          }
          // console.log(setPollDate.toISOString())
          this.pollEndDate = year + '-' + month + '-' + date + 'T' + hours + ':' + minutes + ':' + seconds
          let pollChoice = []
          for(let i = 0; i < this.pollOptions.length; i++) {
            const option = {
              text : this.pollOptions[i].pollOption
            }
            pollChoice[i] = option
          }
          const pollResult = {
            // boardId: 0,
            question: this.pollTitle,
            expirationDateTime : this.pollEndDate,
            choices : pollChoice
          }
          Object.assign(createBoardRequestDto, {pollRequest: pollResult})
        }
        
        const peopleList = {}
        //추가 정보에서 사람 태그 여부
        let taggedResult = []
        //태그한 사람이 있을 경우
        console.log(this.peopleList.length)
        if(this.peopleList.length !== 0) {
          this.boardTaggedYn = 1
          for(let i = 0; i < this.peopleList.length; i++) {
            const people = {
              userId: this.peopleList[i]
            }
            taggedResult[i] = people
          }
          Object.assign(createBoardRequestDto, {inputBoardTaggedRequestDtos: taggedResult})
        }
        
        //미디어 파일이 있을 경우
        // const boardMediaInfo = this.mediaList
        // console.log(createBoardRequestDto)
        this.boardCreate(createBoardRequestDto)
      }
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
//날짜 선택 모달창 
.modal, .person {
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-content: center;
  &.hidden {
    display: none
  }
  .modal-overlay, person-overlay {
    background-color: rgba(0, 0, 0, 0.6);
    width: 100%;
    height: 100%;
    position: absolute;
    .modal-content, .person-content {
      margin: 0 auto;
      top: 40%;
      background-color: white;
      // padding: 50px 100px;
      text-align: center;
      position: relative;
      width: 17%;
      height: 200px;
      border-radius: 10px;
      box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
      .modal-date, .person-date {
        margin: 20px auto 35px auto;
        width: 250px;
      }
      .modal-title, .person-title {
        font-weight: bold;
        font-size: 18px;
        text-align: left;
        padding: 15px;
      }
      .btn-wrap {
        display: flex;
        justify-content: space-evenly;
        .date-confirm {
          width: 80px;
          color: white;
          background-color: #7b371c;
          text-decoration: none;
        }
        .date-cancle {
          width: 80px;
          background-color: #C6BFBF;
          color: black;
          text-decoration: none;
        }
      }
    }
  }
}
.person {
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-content: center;
  &.hidden {
    display: none
  }
  .person-overlay {
    background-color: rgba(0, 0, 0, 0.6);
    width: 100%;
    height: 100%;
    position: absolute;
    .person-content {
      margin: 0 auto;
      top: 40%;
      background-color: white;
      // padding: 50px 100px;
      text-align: center;
      position: relative;
      width: 17%;
      height: 200px;
      border-radius: 10px;
      box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
      .person-date {
        margin: 20px auto 35px auto;
        width: 250px;
      }
      .person-title {
        font-weight: bold;
        font-size: 18px;
        text-align: left;
        padding: 15px;
      }
      .btn-wrap {
        display: flex;
        justify-content: space-evenly;
        .date-confirm {
          width: 80px;
          color: white;
          background-color: #7b371c;
          text-decoration: none;
        }
        .date-cancle {
          width: 80px;
          background-color: #C6BFBF;
          color: black;
          text-decoration: none;
        }
      }
    }
  }
}
</style>
