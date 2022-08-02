<template>
  <div class="feed-detail-wrap">
    <HeaderTitle hasBack="true"></HeaderTitle>
    <!-- 글 작성자 & 버튼 -->
    <div class="feed-head-wrap">
      <FeedUser nickName="이선민"></FeedUser>
      <Button buttonClass="small information" buttonText="수정"></Button>
      <span style="width: 5px"></span>
      <Button style="margin-right:5%" buttonClass="small information" buttonText="삭제"></Button>
    </div>
    <!-- 캐러셀 -->
    <div id="carouselExampleIndicators" class="carousel slide" >
      <div class="carousel-indicators">
          <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
          <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
          <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
      </div>
      <div class="carousel-inner">
          <div class="carousel-item active">
          <img src="@/assets/images/user-solid.svg" class="d-block w-100" alt="...">
          </div>
          <div class="carousel-item">
          <img src="@/assets/images/user-solid.svg" class="d-block w-100" alt="...">
          </div>
          <div class="carousel-item">
          <img src="@/assets/images/user-solid.svg" class="d-block w-100" alt="...">
          </div>
      </div>
      <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Previous</span>
      </button>
      <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Next</span>
      </button>
    </div>
    <!-- 추가기록  -->
    <div class="tag-wrap">
      <span>2022.07.12 </span>
      <span>제주도 </span>
      <span>엄마, 아빠</span>
    </div>
    <!-- 본문 -->
    <div class="content-wrap">
      <span>본문 본문<br/>여기도 본문 본문</span>
    </div>
    <!-- 좋아요 & 댓글 개수 -->
    <div class="reaction-wrap">
      <img src="@/assets/images/suit-heart-fill.svg" alt="filled heart">
      <span>2</span>
      <img src="@/assets/images/chat-right.svg" alt="reply">
      <span>2</span>
    </div>
    <div style="border-bottom: 1px solid black; margin:10px; position: relative; top: 25%;"></div>
    <!-- 댓글 목록 -->
    <div class="reply-wrap"></div>
    <!-- 댓글 작성칸 -->
    <div class="reply-write-wrap">
      <input class="reply" type="text" placeholder="누군가를 부르고싶다면 @ 를 입력해보세요">
    </div>
  </div>
</template>

<script>
import HeaderTitle from '@/components/common/HeaderTitle.vue'
import FeedUser from '@/components/FeedUser.vue'
import Button from '@/components/common/Button.vue'
export default {
  components: { HeaderTitle, FeedUser, Button},
  data () {
    return {
    }
  }
}
</script>

<style scoped lang="scss">
.feed-detail-wrap{
  text-align: center;
  &li{
    float:left;
  }
}
.feed-head-wrap{
  display: flex;
}
#carouselExampleIndicators{
  width: 60%;
  top: 10%;
  left: 20%;
}
.carousel-control-next-icon{
  /* background-image: url('@/assets/images/angle-right-solid.svg'); */
}
.tag-wrap{
  position: relative;
  text-align: right;
  top: 15%;
  right: 10%;
  &span{
    font-weight: 2px;
  }
}
.content-wrap{
  position: relative;
  text-align: left;
  top: 20%;
  left: 10%;
}
.reaction-wrap{
  position: relative;
  text-align: left;
  top: 25%;
  left: 10%;
  img, span{
    margin-right: 3px;
  }
}
.reply-write-wrap{
  position: absolute;
  left: 10%;
  bottom: 5%;
  .reply{
    text-align: center;
    // margin-bottom: 1%;
    width: 80%;
    border-radius: 5px;
    border: 1px solid #ae5f40;
  }
}
</style>
