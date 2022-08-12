<template>
  <div class="family-info">

    <HeaderTitle title="가족정보수정"></HeaderTitle>

    <!-- <img v-if="familyInfo.familyImagePath == null && srcList[0] == null" src="@/assets/images/familyimg.png" alt="가족 사진" class="profile img-thumbnail rounded-circle">
    <div v-else>
      <img v-if="srcList[0] == null" class="profile img-thumbnail rounded-circle" :src="familyInfo.familyImagePath" alt="가족 사진">
      <img v-else class="profile img-thumbnail rounded-circle" :src="srcList[0]" alt="가족 사진">
    </div> -->

    <!-- 미리보기가 없으면 -->
    <div v-if="srcList[0] == null">
      <!-- 패밀리 이미지가 없으면 샘플 이미지 출력 -->
      <img v-if="familyInfo.familyImagePath == null" src="@/assets/images/familyimg.png" alt="가족 사진" class="profile img-thumbnail rounded-circle">
      <!-- 있으면 패밀리 이미지 출력 -->
      <img v-else class="profile img-thumbnail rounded-circle" :src="familyInfo.familyImagePath" alt="가족 사진">
    </div>
    <!-- 미리보기가 있으면 미리보기 출력 -->
    <img v-else class="profile img-thumbnail rounded-circle" :src="srcList[0]" alt="가족 사진">


    <input type="file" class="form-control" id="customFile" @change="fileCheck"/>
    

    
    <InputBox @inputCheck="changeFamilyName" :inputValue="familyInfo.familyName" ></InputBox>
    <Button class="invitebtn" buttonText="가족 초대하기" buttonClass="big information" @click="goInvite"></Button>

    <h6>우리 가족의 애칭을 정해주세요</h6>
    
    <!-- <div v-for="(familyCallsign, index) in familyCallsigns" :key="index"> -->
    <div v-for="(familyCallsign, index) in familyCallsignList" :key="index">
      <div v-if="familyCallsign.toUserId!=userId">
        <img v-if="familyCallsign.toUserImage == null" src="@/assets/images/familyimg.png" alt="프로필 사진" class="profile img-thumbnail rounded-circle">
        <img v-else class="profile img-thumbnail rounded-circle" :src="familyCallsign.toUserImage" alt="프로필 사진">
        <p>{{familyCallsign.toUserName}} 님은</p>
        <!-- <InputBox @change="callsignChange(familyCallsign.familyCallsignId)" :inputValue="familyCallsign.callsign" :hasLabel="true" labelName="나의"></InputBox> -->
        <!-- <InputBox @inputCheck="callsignChange" :inputValue="familyCallsign.callsign" :hasLabel="true" labelName="나의"></InputBox> -->
        <label class="label-box" for="input-box">나의</label>
        <input class="input-box" v-model="familyCallsign.callsign" @change="callsignChange"/>
      </div>
    </div>
    <div class="btns">
      <Button buttonClass="small negative" buttonText="돌아가기" @click="cancle"></Button>
      <Button buttonClass="small positive" buttonText="저장" @click="save"></Button>
      <!-- <Button buttonClass="small positive" buttonText="로그" @click="mylog"></Button> -->
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
const fileList = []

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
        isCallsignModified: false,
        updateFamilyCallsignDtos : [],
        updatefamilyDto:{
        "familyId": null,
        "familyName": null
        }
      },

      srcList: [],  
      fileList: [],

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

    // mylog(){
    //   console.log("로그");

    // // console.log(this.familyInfo);
    // console.log(this.familyCallsignList);
    // // console.log(this.familyCallsigns);
    // },

    // 파일 처리
    fileCheck (e) {
      this.changeFile()
      this.previewFile()
    },
    // 확장자 변경
    changeFile () {

      fileList.pop()

      const fileInput = document.getElementById('customFile')
      // 선택한 파일의 정보
      const files = fileInput.files

      // heic 파일 확장자 변경
      const file = files[0]

      let heicFile = ''
      // 파일의 확장자가 heic일 경우
      if (file.name.split('.')[1] === 'heic') {
        // file의 타입을 "image/jpg"로 바꾸고 이름 뒤에 확장자도 .jpg로 바꾼다
        heic2any({ blob: file, toType: 'image/jpg' })
          .then(function (resultBlob) {
            heicFile = new File([resultBlob], file.name.split('.')[0] + '.jpg', { type: 'image/jpg', lastModified: new Date().getTime() })
            fileList.push(heicFile)
          })
          .catch((err) => {
            console.log(err)
          })
      } else {
        fileList.push(file)
      }
    },
    // 미리보기
    previewFile () {
      this.srcList.pop()
      this.srcList.push(URL.createObjectURL(fileList[0]))
    },


    changeFamilyName(data){
      this.familyNameModified = true;
      this.familyName = data;
      console.log(this.familyName);
    },

    callsignChange(){
      this.updateFamilyRequestDto.isCallsignModified = true;
    },

    goInvite () {
      this.$router.push('familyInvite')
    },

    cancle () {
      this.$router.push('feed')
    },

    save () {
      const updateFamilyRequestDto = this.updateFamilyRequestDto

      if(updateFamilyRequestDto.isCallsignModified)
        updateFamilyRequestDto.updateFamilyCallsignDtos = this.familyCallsignList;

      updateFamilyRequestDto.updatefamilyDto.familyId = this.familyId;
      if(this.familyNameModified)
        updateFamilyRequestDto.updatefamilyDto.familyName = this.familyName;
      else
        updateFamilyRequestDto.updatefamilyDto.familyName = this.familyInfo.familyName;

      if(fileList.length !== 0){
        this.updateFamilyInfo( { updateFamilyRequestDto, fileList } );
      } else{
        this.updateFamilyInfo( { updateFamilyRequestDto } );
      }
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
