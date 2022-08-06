<template>
  <div>
    <HeaderTitle title="앨범"></HeaderTitle>
    <ul class="list-group list-group-flush">
        <li class="list-group-item"><img src="@/assets/images/여행1.jpg" class="img-fluid" alt="..."><div class="folder-name">NYC</div></li>
        <li class="list-group-item"><img src="@/assets/images/여행2.jpg" class="img-fluid" alt="..."><div class="folder-name">제주도</div></li>
        <li class="list-group-item"><img src="@/assets/images/여행3.jpg" class="img-fluid" alt="..."><div class="folder-name">일본</div></li>
        <li class="list-group-item"><img src="@/assets/images/여행1.jpg" class="img-fluid" alt="..."><div class="folder-name">NYC</div></li>
        <li class="list-group-item"><img src="@/assets/images/여행1.jpg" class="img-fluid" alt="..."><div class="folder-name">NYC</div></li>
    </ul>
    <!-- floating button -->
    <!-- <fab
        :position="position"
        :bg-color="bgColor"
        :actions="fabActions"
        @cache="cache"
        @alertMe="alert"
    ></fab> -->
    <!-- Button trigger modal -->
    <button id="btn-modal" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
    앨범 추가
    </button>

    <!-- Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">앨범명</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
            <input type="text">
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
            <button type="button" class="btn btn-primary" data-bs-dismiss="modal">만들기</button>
        </div>
        </div>
    </div>
    </div>
  </div>
</template>

<script>
import HeaderTitle from '../common/HeaderTitle.vue'
export default {
  components: { HeaderTitle }
}
</script>

<style scoped lang="scss">
.header{
    margin-bottom: 5%;
}
ul{
    height: 91%;
    li{
        height: 20%;
        padding: 0;
        img{
            width: 100%;
            height: 100%;
            opacity: 0.5;
        }
    }
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
    }
}
input {
  margin-bottom: 1%;
  width: -webkit-fill-available;
  border-radius: 5px;
  border: 1px solid #ae5f40;
}
</style>
