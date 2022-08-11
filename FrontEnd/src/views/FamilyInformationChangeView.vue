<template>
  <div class="family-info">

    <HeaderTitle title="가족정보수정"></HeaderTitle>

    <img src="@/assets/images/familyimg.png" alt="familyimg" class="rounded-circle">
    <InputBox @inputCheck="changeFamilyName" :inputValue="familyInfo.familyName" ></InputBox>
    <Button class="invitebtn" buttonText="가족 초대하기" buttonClass="big information" @click="goInvite"></Button>

    <h6>우리 가족의 애칭을 정해주세요</h6>
    
    <!-- <div v-for="(familyCallsign, index) in familyCallsigns" :key="index"> -->
    <div v-for="(familyCallsign, index) in familyCallsignList" :key="index">
      <div v-if="familyCallsign.toUserId!=userId">
        <p>{{familyCallsign.toUserName}} 님은</p>
        <!-- <InputBox @change="callsignChange(familyCallsign.familyCallsignId)" :inputValue="familyCallsign.callsign" :hasLabel="true" labelName="나의"></InputBox> -->
        <!-- <InputBox @inputCheck="callsignChange" :inputValue="familyCallsign.callsign" :hasLabel="true" labelName="나의"></InputBox> -->
        <label class="label-box" for="input-box">나의</label>
        <input class="input-box" v-model="familyCallsign.callsign" @change="callsignChange"/>
      </div>
    </div>
    <div class="btns">
      <Button buttonClass="small negative" buttonText="취소" @click="cancle"></Button>
      <Button buttonClass="small positive" buttonText="저장" @click="save"></Button>
      <Button buttonClass="small positive" buttonText="로그" @click="mylog"></Button>
    </div>
  </div>
</template>
<script>
import HeaderTitle from '@/components/common/HeaderTitle.vue'
import InputBox from '@/components/common/InputBox.vue'
import Button from '@/components/common/Button.vue'
import { mapState, mapActions } from "vuex"

const familyStore = "familyStore"
const userStore = 'userStore'

export default {

  name: 'familyInfoChange',

  components: { HeaderTitle, InputBox, Button },

  data () {

    return {

      familyId: null,
      userId: null,
      familyNameModified:false,
      familyName:null,
      updateFamilyRequestDto: {
        callsignModified: false,
        updateFamilyCallsignDtos : [],
        updatefamilyDto:{
        "familyId": null,
        "familyName": null
        }
      }

    }

  },

  computed: {

    ...mapState(familyStore, ["familyInfo", "familyCallsignList"]),
    ...mapState(userStore, ["userInfo"]),
    // familyCallsigns(){
    //   return this.familyCallsignList;
    // }
  },

  created() {

    this.familyId = localStorage.getItem('familyId');
    this.userId = localStorage.getItem('userId');

    this.callsignList(this.userId);
    this.getFamilyInfo(this.familyId);
  },

  // mounted() {

  //   this.familyCallsigns = this.familyCallsignList;
  //   console.log(this.familyCallsigns);
  //   console.log(this.familyCallsignList);
    
  // },

  methods: {

    ...mapActions(familyStore, ["callsignList", "getFamilyInfo", "updateFamilyInfo"]),

    mylog(){
      console.log("로그");

    // console.log(this.familyInfo);
    console.log(this.familyCallsignList);
    // console.log(this.familyCallsigns);
    },

    changeFamilyName(data){
      this.familyNameModified = true;
      this.familyName = data;
      console.log(this.familyName);
    },

    callsignChange(){
      this.updateFamilyRequestDto.callsignModified = true;
    },

    goInvite () {
      this.$router.push('familyInvite')
    },

    cancle () {
      alert('취소되었습니다.')
      this.$router.push('feed')
    },

    save () {
      if(this.updateFamilyRequestDto.callsignModified)
        this.updateFamilyRequestDto.familyCallsignList = this.familyCallsignList;

      this.updateFamilyRequestDto.updatefamilyDto.familyId = this.familyId;
      if(this.familyNameModified)
        this.updateFamilyRequestDto.updatefamilyDto.familyName = this.familyName;
      else
        this.updateFamilyRequestDto.updatefamilyDto.familyName = this.familyInfo.familyName;

      this.updateFamilyInfo(this.updateFamilyRequestDto);
    }
  }

}
</script>

<style scoped>
  .invitebtn{
    display: flex;
    justify-content: flex-end

  }

  .btns{
    display: flex;
    justify-content: space-around;
  }

  h6 {
    display: flex;
    justify-content: center;
  }

  .input-box {
    text-align: center;
    margin-bottom: 1%;
    width: 140px;
    border-radius: 5px;
    border: 1px solid #ae5f40;
  }
</style>
