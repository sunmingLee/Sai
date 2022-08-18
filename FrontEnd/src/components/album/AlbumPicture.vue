<template>
  <div>
    <HeaderTitle :title="albumName" hasBack="true"></HeaderTitle>
      <div class="picture-wrap">
        <div class="picture-flex" v-if="mediaList.length">
          <div class="image-group" v-for="(media, index) in mediaList" :key="index">
            <img v-if="media.albumMediaThumbnail" :src="media.albumMediaThumbnail" class="img-fluid" alt="thumbnail">
            <img v-else src="@/assets/images/image.svg" class="img-fluid" alt="empty thumbnail">
            <!-- <img src="@/assets/images/여행1.jpg" class="img-fluid" alt="empty thumbnail"> -->
          </div>
        </div>
        <div class="nothing-wrap" v-else>등록된 사진이 없습니다.</div>
     <!-- Button trigger modal -->
    <button id="btn-modal" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
      <img style="width:25px;" src="@/assets/images/plus-lg.svg" alt="plus">
    </button>
    <!-- Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">사진 추가</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <form action="">
            <input ref="imageUploader" type="file" class="form-control" id="customFile" @change="fileCheck" multiple />
          </form>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
            <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="addMedia">추가</button>
        </div>
        </div>
    </div>
    </div>
  </div>
</div>
</template>

<script>
import HeaderTitle from '../common/HeaderTitle.vue'
import heic2any from 'heic2any'

import { mapState, mapActions } from 'vuex'
const albumStore = 'albumStore'

// 파일 리스트
let fileList = []
export default {
  components: { HeaderTitle },
  data () {
    return {
      albumName: ''
    }
  },
  created () {
    this.albumName = localStorage.getItem('albumName')
    this.getMediaList()
  },
  computed: {
    ...mapState(albumStore, ['mediaList'])
  },
  methods: {
    ...mapActions(albumStore, ['getMediaList', 'insertMedia', 'deleteMedia']),
    // 파일 처리
    fileCheck (e) {
      // 파일을 다시 선택한다고 했을 때 이미 선택했던 파일을 다 없앤다
      // fileList -> 담아서 전송해줄 파일 데이터를 담는 배열
      fileList = []
      const fileInput = document.getElementById('customFile')
      // 선택한 파일의 정보 리스트
      const files = fileInput.files
      // heic 파일 확장자 변경
      // 선택한 파일의 개수만큼 돌아서 각각의 파일을 다 확인
      for (let i = 0; i < files.length; i++) {
        // 파일 하나 선택
        const file = files[i]
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
      }
    },
    // 미디어 등록
    addMedia () {
      // console.log(fileList)
      this.insertMedia(fileList)
      // 선택했던 파일 없애기
      this.$refs.imageUploader.value = null
      fileList = []
    },
    // 미디어 삭제
    eraseMedia (albumMediaId) {
      this.deleteMedia(albumMediaId)
    }
  }
}
</script>

<style scoped lang="scss">
.nothing-wrap{
    text-align: center;
    font-size: x-large;
}
.btn-close{
    position: absolute;
    top: 5%;
    right: 5%;
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
///수빈
.picture-wrap {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  .picture-flex {
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    .image-group {
      width: 235px;
      height: 210px;
      margin: 20px auto;
    }
  }
  img {
    height: 100%;
  }
}
</style>
