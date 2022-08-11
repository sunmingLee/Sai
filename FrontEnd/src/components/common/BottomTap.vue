<template>
  <ul class="nav nav-pills nav-fill" id="pills-tab" role="tablist">
    <li class="nav-item" role="presentation">
      <button class="nav-link active" id="feed-tab" data-bs-toggle="pill" data-bs-target="#pills-home" type="button" role="tab" aria-controls="pills-home" aria-selected="true" @click="moveFeed">피드</button>
    </li>
    <li class="nav-item" role="presentation">
      <button class="nav-link" id="refride-tab" data-bs-toggle="pill" data-bs-target="#pills-profile" type="button" role="tab" aria-controls="pills-profile" aria-selected="false" @click="moveRefridge">냉장고</button>
    </li>
    <li class="nav-item" role="presentation">
      <button class="nav-link" id="album-tab" data-bs-toggle="pill" data-bs-target="#pills-contact" type="button" role="tab" aria-controls="pills-contact" aria-selected="false" @click="moveAlbum">앨범</button>
    </li>
    <li class="nav-item" role="presentation">
      <button class="nav-link" id="calendar-tab" data-bs-toggle="pill" data-bs-target="#pills-contact" type="button" role="tab" aria-controls="pills-contact" aria-selected="false" @click="moveCalendar">달력</button>
    </li>
  </ul>
</template>

<script>
export default {
//   $(document).ready(function() {
//     $('button[data-bs-toggle="pill"]').on('show.bs.tab', function(e){
// 	alert("이벤트 실행됨");
//     });
// });
  data () {
    return {
    }
  },
  methods: {
    moveFeed () {
      this.$router.push({ name: 'feed' })
    },
    moveRefridge () {
      this.$router.push({ name: 'memo' })
    },
    moveAlbum () {
      this.$router.push({ name: 'album' })
    },
    moveCalendar () {
      this.$router.push({ name: 'calendar' })
    }
  }
}
</script>

<style scoped lang="scss">
  .nav {
    /* display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: flex-end; */
    position: absolute;
    left: 0;
    bottom: 0;
    width: -webkit-fill-available;
    height: 50px;
    justify-content: space-around;
    --bs-nav-link-color: #7B371C;
    --bs-nav-link-hover-color: #7B371C;
    background-color: white;
    &-item{
      // border: 1px solid #7B371C;
    }
  }
  .nav-link.active{
    background-color: #7B371C;
  }
  button{
    height: 50px;
    font-weight: bold;
  }
</style>
