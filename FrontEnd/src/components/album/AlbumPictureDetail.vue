<template>
  <div class="picture-detail-wrap">
    <HeaderTitle hasBack="true"></HeaderTitle>
    <div class="button-wrap">
        <Button style="margin-left:5px;" buttonClass="small information" buttonText="삭제" @click="eraseMedia"></Button>
    </div>
    <div class="img-wrap">
      <!-- <img src="@/assets/images/여행1.jpg"> -->
      <img :src="albumMediaPath" class="d-block w-100" alt="image">
    </div>
  </div>
</template>

<script>
import HeaderTitle from '../common/HeaderTitle.vue'
import Button from '../common/Button.vue'

import { mapActions } from 'vuex'
const albumStore = 'albumStore'
export default {
  components: { HeaderTitle, Button },
  data () {
    return {
      albumMediaId: '',
      albumMediaPath: ''
    }
  },
  created () {
    this.albumMediaId = JSON.parse(localStorage.getItem('picture')).albumMediaId
    this.albumMediaPath = JSON.parse(localStorage.getItem('picture')).albumMediaPath
  },
  methods: {
    ...mapActions(albumStore, ['downloadMedia', 'deleteMedia']),
    // 사진 삭제
    eraseMedia () {
      this.deleteMedia(this.albumMediaId)
    }
  }
}
</script>

<style scoped lang="scss">
.picture-detail-wrap{
    height: 90%;
    img {
      width: 100%!important;
      height: auto;
    }
}
.button-wrap{
    display: flex;
    justify-content: right;
    margin-bottom: 5%;
}
.img-wrap {
  display: flex;
  align-items: center;
  min-height: 550px;
  max-height: 500px;
}
img{
    height: 80%;
}
button{
    // font-weight: normal;
}
</style>
