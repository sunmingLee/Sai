<template>
  <div class="add-info-wrap">
    <HeaderTitle title="추가정보 입력"></HeaderTitle>
    <div class="user-flex">
      <div v-if="srcList[0] == null" style="position: relative">
        <img
          class="profile img-thumbnail rounded-circle"
          src="@/assets/images/user-solid.svg"
          alt="user image"
        />
        <label calss="file-label" for="customFile"><img src="@/assets/images/camera.svg" alt=""></label>
        <input
          style="display: none"
          type="file"
          class="form-control"
          id="customFile"
          @change="fileCheck"
        />
      </div>
      <div v-else style="position: relative">
        <!-- <img v-else-if="srcList[0] !== null" class="profile img-thumbnail rounded-circle" :src="srcList[0]" alt="user image"> -->
        <img
          class="profile img-thumbnail rounded-circle"
          :src="srcList[0]"
          alt="user image"
        />
        <!-- <label class="form-label" for="customFile">Default file input example</label> -->
        <label calss="file-label" for="customFile"><img src="@/assets/images/camera.svg" alt=""></label>
        <input
          style="display: none"
          type="file"
          class="form-control"
          id="customFile"
          @change="fileCheck"
        />
      </div>
    </div>

    <div class="date">
      <span>생일</span>
      <Datepicker
        v-model="addInfo.birthday"
        format="yyyy / MM / dd"
        :enableTimePicker="false"
        :maxDate="new Date()"
      ></Datepicker>
    </div>
    <div class="left">
      <span>양력/음력</span>
      <div class="lunar">
        <input
          type="radio"
          id="radioSolar"
          value="false"
          v-model="addInfo.lunar"
        />
        <label for="radioSolar">양력</label>
        <input
          type="radio"
          id="radioLunar"
          value="true"
          v-model="addInfo.lunar"
        />
        <label for="radioLunar">음력</label>
      </div>
    </div>

    <div class="user-message">
      <span>상태<br />메세지</span>
      <textarea v-model="addInfo.userMessage"></textarea>
    </div>

    <!-- 회원 가입 직후 추가 정보 입력-->
    <div class="button">
      <Button
        buttonClass="small information"
        buttonText="건너뛰기"
        @click="goFamilyCode"
      ></Button>
      <Button
        buttonClass="small positive"
        buttonText="확인"
        @click="onAdd"
      ></Button>
    </div>
  </div>
</template>

<script>
import HeaderTitle from "@/components/common/HeaderTitle.vue";
import Datepicker from "@vuepic/vue-datepicker";
import "@vuepic/vue-datepicker/dist/main.css";
import Button from "./common/Button.vue";
import heic2any from "heic2any";

import { mapActions } from "vuex";
// import userStore from '../store/modules/userStore'
const userStore = "userStore";
const fileList = [];
export default {
  components: { HeaderTitle, Datepicker, Button },
  data() {
    return {
      addInfo: {
        userId: "",
        lunar: "",
        userMessage: "",
        birthday: new Date(),
      },
      isProfilePic: false,
      srcList: [],
      fileList: [],
    };
  },
  created() {
    // 유저 정보 조회
    this.addInfo.userId = localStorage.getItem("userId");
    // fileList: []
  },
  computed: {
    // ...mapState(userStore, [isAddInfo])
  },
  methods: {
    ...mapActions(userStore, ["addUserInfo", "checkUserInfo"]),
    // 파일 처리
    fileCheck(e) {
      this.changeFile();
      this.previewFile();
    },
    // 확장자 변경
    changeFile() {
      const fileInput = document.getElementById("customFile");
      // 선택한 파일의 정보
      const files = fileInput.files;

      // heic 파일 확장자 변경
      const file = files[0];

      let heicFile = "";
      // 파일의 확장자가 heic일 경우
      if (file.name.split(".")[1] === "heic") {
        // file의 타입을 "image/jpg"로 바꾸고 이름 뒤에 확장자도 .jpg로 바꾼다
        heic2any({ blob: file, toType: "image/jpg" })
          .then(function (resultBlob) {
            heicFile = new File(
              [resultBlob],
              file.name.split(".")[0] + ".jpg",
              { type: "image/jpg", lastModified: new Date().getTime() }
            );
            // fileList.push(heicFile)
            fileList.pop();
            fileList.push(heicFile);
          })
          .catch((err) => {
          });
      } else {
        fileList.pop();
        fileList.push(file);
        // fileList.push(file)
      }
    },
    // 미리보기
    previewFile() {
      this.srcList.push(URL.createObjectURL(fileList[0]));
    },

    // 건너뛰기 버튼
    goFamilyCode() {
      this.$router.push({ name: "familyCode" });
    },
    // 확인버튼 눌렀을 때 추가
    onAdd() {
      const userInfo = {};
      if (fileList.length !== 0) {
        this.isProfilePic = true;
      }

      Object.assign(userInfo, this.addInfo);
      if (this.isProfilePic) {
        this.addUserInfo({ userInfo, fileList });
      } else {
        this.addUserInfo({ userInfo });
      }
      this.checkUserInfo(this.addInfo.userId);
    },
  },
};
</script>

<style scoped lang="scss">
.add-info-wrap {
  margin-top: 10%;
  height: 60%;
}
div {
  margin-bottom: 10px;
}
.left {
  text-align: left;
  // padding-left: 85px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  input,
  label {
    margin-right: 2%;
  }
}
.date {
  display: flex;
  justify-content: space-around;
}
.user-message {
  display: flex;
  justify-content: space-around;
  /* align-items: ; */
}
.valid-error {
  color: red;
  font-size: 12px;
}
textarea {
  border-radius: 5px;
  border: 1px solid #ae5f40;
  width: 210px;
  min-height: 20vh;
  resize: vertical;
}
span {
  text-align: center;
  color: #ae5f40;
}
.button {
  display: flex;
  justify-content: space-between;
  margin: 40px;
  margin-top: 30%;
}
.button-wrap {
  display: inline-block;
  vertical-align: top;
  /* margin-left: 1%; */
}
.user-flex {
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
.dp__input_wrap {
  margin: 0px 0px 0px 20px;
  width: 200px;
}
.container {
  position: absolute;
  overflow: visible;
  height: 100vh;
  width: 340px;
}
.lunar {
  width: 200px;
}
</style>
