<template>
  <div class="family-info">
    <HeaderTitle title="가족정보수정"></HeaderTitle>
    <div class="family-flex">
      <!-- 미리보기가 없으면 -->
      <div v-if="srcList[0] == null" style="position: relative">
        <!-- 패밀리 이미지가 없으면 샘플 이미지 출력 -->
        <img v-if="familyInfo.familyImagePath == null" src="@/assets/images/familyimg.png" alt="가족 사진" class="profile img-thumbnail rounded-circle">
        <!-- 있으면 패밀리 이미지 출력 -->
        <img v-else class="profile img-thumbnail rounded-circle" :src="familyInfo.familyImagePath" alt="가족 사진">
        <label calss="file-label" for="customFile"><img src="@/assets/images/camera.svg" alt=""></label>
        <input style="display: none" type="file" class="form-control" id="customFile" @change="fileCheck"/>
      </div>
      <!-- 미리보기가 있으면 미리보기 출력 -->
      <div v-else style="position: relative">
        <img class="profile img-thumbnail rounded-circle" :src="srcList[0]" alt="가족 사진">
        <label calss="file-label" for="customFile"><img src="@/assets/images/camera.svg" alt=""></label>
        <input style="display: none" type="file" class="form-control" id="customFile" @change="fileCheck"/>
      </div>
      <!-- <label calss="file-label" for="customFile"><img src="@/assets/images/camera.svg" alt=""></label>
      <input style="display: none" type="file" class="form-control" id="customFile" @change="fileCheck"/> -->
    </div>
    <div class="family-title">
      <InputBox @inputCheck="changeFamilyName" :inputValue="familyInfo.familyName" />
      <span>{{familyNameLength}}/20</span>
    </div>
    <div class="invite-button">
      <Button class="invitebtn" buttonText="가족 초대하기" buttonClass="big information" @click="goInvite"></Button>
    </div>
    <hr>
    <h6>우리 가족의 애칭을 정해주세요</h6>
    <!-- <div v-for="(familyCallsign, index) in familyCallsigns" :key="index"> -->
    <div v-for="(familyCallsign, index) in familyCallsignList" :key="index">
      <div v-if="familyCallsign.toUserId!=userId" class="callsign-list">
        <div class="body-image">
          <img v-if="familyCallsign.toUserImage == null" src="@/assets/images/familyimg.png" alt="프로필 사진" class="profile img-thumbnail rounded-circle">
          <img v-else class="profile img-thumbnail rounded-circle" :src="familyCallsign.toUserImage" alt="프로필 사진">
        </div>
        <div class="body-callsign">
        <p><b>{{familyCallsign.toUserName}}</b> 님은</p>
          <label class="label-box" for="input-box">나의</label>
          <input class="input-box" v-model="familyCallsign.callsign" @change="callsignChange"/>
        </div>
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
import { mapState, mapActions } from 'vuex'

const familyStore = 'familyStore'
const userStore = 'userStore'
const fileList = []

export default {

  name: 'familyInfoChange',

  components: { HeaderTitle, InputBox, Button },

  data () {
    return {

      familyId: null,
      userId: null,

      familyNameModified: false,
      familyName: null,

      updateFamilyRequestDto: {
        isCallsignModified: false,
        updateFamilyCallsignDtos: [],
        updatefamilyDto: {
          familyId: null,
          familyName: null
        }
      },

      srcList: [],
      fileList: []

    }
  },

  computed: {

    ...mapState(familyStore, ['familyInfo', 'familyCallsignList']),
    ...mapState(userStore, ['userInfo']),
    // familyCallsigns(){
    //   return this.familyCallsignList;
    // }
    familyNameLength () {
      if (this.familyNameModified) { return this.familyName.length } else { return this.familyInfo.familyName.length }
    }

  },

  created () {
    this.familyId = localStorage.getItem('familyId')
    this.userId = localStorage.getItem('userId')

    this.callsignList(this.userId)
    this.getFamilyInfo(this.familyId)
  },
  methods: {

    ...mapActions(familyStore, ['callsignList', 'getFamilyInfo', 'updateFamilyInfo']),
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

    changeFamilyName (data) {
      this.familyNameModified = true
      this.familyName = data
      this.familyNameLength = data.length
    },

    callsignChange () {
      this.updateFamilyRequestDto.isCallsignModified = true
    },

    goInvite () {
      this.$router.push('familyInvite')
    },

    cancle () {
      this.$router.push('feed')
    },

    save () {
      if (this.familyNameLength > 20) {
        alert('가족 이름 길이는 20자를 넘을 수 없습니다.')
        return
      }

      const updateFamilyRequestDto = this.updateFamilyRequestDto

      if (updateFamilyRequestDto.isCallsignModified) { updateFamilyRequestDto.updateFamilyCallsignDtos = this.familyCallsignList }

      updateFamilyRequestDto.updatefamilyDto.familyId = this.familyId
      if (this.familyNameModified) { updateFamilyRequestDto.updatefamilyDto.familyName = this.familyName } else { updateFamilyRequestDto.updatefamilyDto.familyName = this.familyInfo.familyName }

      if (fileList.length !== 0) {
        this.updateFamilyInfo({ updateFamilyRequestDto, fileList })
      } else {
        this.updateFamilyInfo({ updateFamilyRequestDto })
      }
    }
  }

}
</script>

<style lang="scss" scoped>
  .invitebtn{
    display: flex;
    justify-content: center;
    margin-bottom: 20px;
  }

  .btns{
    display: flex;
    justify-content: space-evenly;
    margin-top: 25px;
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

  .family-flex {
    display: flex;
    justify-content: center;
    align-items: center;
    label {
      border: 1px solid #7b371c;
      background-color: #7b371c;
      border-radius: 38px;
      width: 44px;
      height: 41px;
      text-align: center;
      line-height: 31px;
      position: absolute;
      top: 54%;
      left: 76%;
      img {
        width: 35px;
      }
    }
  }
  .family-info{
    width: 900px;
  }
  .family-title {
    display: flex;
    justify-content: center;
    margin-bottom: 12px;
    min-width: 300px;
    max-width: 900px;
    .form-wrap {
      max-width: 160px;
    }
  }

::v-deep {
  table {
    width: 160px;
  }
  tr {
    td:nth-child(1) {
      display: none;
    }
  }
  colgroup {
    col:nth-child(2) {
      width: 5%!important;
    }
  }
}

.callsign-list{
  display: flex;
  justify-content: center;
  align-items: center;
  .body-callsign {
    margin-left: 20px;
    .input-box {
      margin-left: 5px;
    }
  }

}

hr {
  border-top: 3px solid #7b371c;
}

</style>
