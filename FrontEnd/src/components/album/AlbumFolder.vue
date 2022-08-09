<template>
  <div>
    <HeaderTitle title="앨범"></HeaderTitle>
    <ul class="list-group list-group-flush" v-if="folderList.length">
        <li class="list-group-item" v-for="(folder, index) in folderList" :key="index" @click="seeFolder(folder.albumId, folder.albumName)">
            <img v-if="folder.thumbnailPath" :src="folder.thumbnailPath" class="img-fluid" alt="thumbnail">
            <img v-else src="@/assets/images/여행1.jpg" class="img-fluid" alt="empty thumbnail">
            <div class="travel-date-wrap">{{ folder.albumStartDate }} ~ {{ folder.albumEndDate }}</div>
            <div class="folder-name">{{ folder.albumName }}</div>
        </li>
    </ul>
    <div class="nothing-wrap" v-else>등록된 앨범이 없습니다.</div>
    <!-- Button trigger modal -->
    <button id="btn-modal" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
    <img style="width:25px;" src="@/assets/images/plus-lg.svg" alt="plus">
    </button>

    <!-- Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">앨범 정보</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <span>앨범 이름</span>
                <input v-model="folderName" type="text">
                <div>
                    <span>여행 날짜</span>
                    <Datepicker v-model="date.value" format='yyyy/MM/dd' modelType="yyyy-MM-dd" :enableTimePicker="false" range placeholder="여행 기간을 선택해주세요" selectText="선택" cancelText="취소"></Datepicker>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="createAlbum">만들기</button>
            </div>
            </div>
        </div>
    </div>
  </div>
</template>

<script>
import HeaderTitle from '../common/HeaderTitle.vue'
import Datepicker from '@vuepic/vue-datepicker'

import { mapState, mapActions } from 'vuex'
const albumStore = 'albumStore'
export default {
  components: { HeaderTitle, Datepicker },
  data () {
    return {
      startDate: new Date(),
      endDate: new Date(new Date().setDate(new Date().getDate() + 7)),
      date: [this.startDate, this.endDate],
      folderName: ''
    }
  },
  created () {
    this.getFolderList(localStorage.familyId)
  },
  computed: {
    ...mapState(albumStore, ['folderList'])
  },
  methods: {
    ...mapActions(albumStore, ['getFolderList', 'setAlbumInfo', 'makeAlbum']),
    createAlbum () {
      const info = {
        albumName: this.folderName,
        albumStartDate: this.date.value[0],
        albumEndDate: this.date.value[1],
        familyId: localStorage.familyId
      }
      this.makeAlbum(info)
    },
    seeFolder (albumId, albumName) {
      const info = {
        albumId,
        albumName
      }
      this.setAlbumInfo(info)
    }
  }
}
</script>

<style scoped lang="scss">
.nothing-wrap{
    text-align: center;
    font-size: x-large;
}
.header{
    margin-bottom: 5%;
}
ul{
    height: 91%;
    li{
        min-height: 20vh;
        height: 20%;
        padding: 0;
        img{
            width: 100%;
            height: 100%;
            max-height: 30vh;
            opacity: 0.5;
        }
    }
}
.travel-date-wrap{
    font-size: small;
    font-weight: bolder;
    position: absolute;
    top: 40%;
    left: 50%;
    transform: translate(-50%, -50%);

}
.folder-name{
    // color: white;
    font-size: x-large;
    font-weight: bolder;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
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
input {
  margin-bottom: 1%;
  width: -webkit-fill-available;
  border-radius: 5px;
  border: 1px solid #ae5f40;
}
</style>
