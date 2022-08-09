<template>
  <div class="feed-detail-wrap">
    <HeaderTitle hasBack="true"></HeaderTitle>
    <!-- 글 작성자 & 버튼 -->
    <div class="feed-head-wrap">
      <div v-for="(callsign, index) in familyCallsignList" :key="index">
        <div v-if="this.userId === callsign.toUserId">
          <FeedUser :name="callsign.callsign"></FeedUser>
        </div>
      </div>
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
    <table class="reply-wrap" v-if="replyList.length">
    <!-- <colgroup>
      <col width=40%>
      <col width=60%>
      <col width=100%>
      <col width=100%>
    </colgroup> -->
      <tr v-for="(reply, index) in replyList" :key="index">
        <div v-for="(callsign, index) in familyCallsignList" :key="index">
          <div v-if="reply.userId === callsign.toUserId">
            <td><span style="font-weight: bold;">{{ callsign.callsign }}</span></td>
            <td>{{ reply.replyContent }}</td>
          </div>
        </div>
      </tr>
    </table>
    <!-- 댓글 작성칸 -->
    <div class="reply-write-wrap">
      <input class="reply" type="text" placeholder="누군가를 부르고싶다면 @ 를 입력해보세요">
      <Button style="display: inline-block;" buttonClass="small positive" buttonText="등록"></Button>
    </div>
  </div>
</template>

<script>
import HeaderTitle from '@/components/common/HeaderTitle.vue'
import FeedUser from '@/components/FeedUser.vue'
import Button from '@/components/common/Button.vue'

import { mapState, mapActions } from 'vuex'
const boardStore = 'boardStore'
const familyStore = 'familyStore'

export default {
  components: { HeaderTitle, FeedUser, Button },
  data () {
    return {
      boardId: '',
      userId: ''
    }
  },
  async created () {
    // console.log(this.$route.params.boardId)
    // console.log(localStorage.getItem('boardId'))
    this.userId = localStorage.getItem('userId')
    // 댓글 목록 조회
    this.getReplyList(localStorage.getItem('boardId'))

    // 글쓴이 이름 찾기 (나중에 params에서 넘어온 아이디와 비교해야함)
    await this.callsignList(localStorage.getItem('userId'))
    // await this.callsignList('ssafy8')
    // if (this.familyCallsignList.length !== 0) {
    //   console.log(this.familyCallsignList)
    //   await this.familyCallsignList.forEach(callsign => {
    //     console.log(callsign)
    //     if (localStorage.getItem('userId') === callsign.toUserId) {
    //       this.writterName = callsign.callsign
    //     }
    //   })
    // }
  },
  computed: {
    ...mapState(boardStore, ['replyList']),
    ...mapState(familyStore, ['familyCallsignList'])
  },
  methods: {
    ...mapActions(boardStore, ['getReplyList']),
    ...mapActions(familyStore, ['callsignList'])
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
  // max-width: ;
  width: 60%;
  top: 10%;
  left: 20%;
}
.carousel-control{
  &-prev, &-next{
    top: 50%;
  }
}
.carousel-control-next-icon{
  background-image: url('@/assets/images/angle-right-solid.svg');
}
.carousel-control-prev-icon{
  top: 50%;
  background-image: url('@/assets/images/angle-left-solid.svg');
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
.reply-wrap{
  position: relative;
  top: 24%;
  .list-group-item{
    background-color: #fafafa;
    text-align: left;
    span{
      margin-right: 5%;
    }
  }
}
.reply-write-wrap{
  position: relative;
  top: 28%;
  // left: 10%;
  // bottom: 5%;
  .reply{
    display: inline-block;
    text-align: center;
    margin-right: 5px;
    width: 80%;
    border-radius: 5px;
    border: 1px solid #ae5f40;
  }
}
</style>
