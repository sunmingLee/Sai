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
      <Button buttonClass="small information" buttonText="수정" @click="goUpdate"></Button>
      <span style="width: 5px"></span>
      <Button style="margin-right:5%" buttonClass="small negative" buttonText="삭제" @click="eraseFeed"></Button>
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
      <div v-if="feed.viewBoardResponseDto.boardDate">{{feed.viewBoardResponseDto.boardDate}}</div>
      <div v-if="feed.viewBoardResponseDto.boardLocation">{{feed.viewBoardResponseDto.boardLocation}}</div>
      <div v-if="feed.viewBoardResponseDto.boardTaggedYn">
        <span v-for="(user, index) in feed.viewBoardTaggedResponseDto" :key="index">{{user}}</span>
      </div>
    </div>
    <!-- 본문 -->
    <div class="content-wrap" v-if="feed.viewBoardResponseDto.boardContent">
      <span>{{feed.viewBoardResponseDto.boardContent}}</span>
    </div>
    <!-- 좋아요 & 댓글 개수 -->
    <div class="reaction-wrap">
      <img v-if="!onHeart && !feed.boardLiked" src="@/assets/images/suit-heart.svg" alt="empty heart" @click="changeHeart">
      <img v-else src="@/assets/images/suit-heart-fill.svg" alt="fill heart" @click="changeHeart">
      <span>{{feed.viewBoardResponseDto.boardLikeCnt}}</span>
      <img src="@/assets/images/chat-right.svg" alt="reply">
      <span>{{feed.viewBoardResponseDto.boardReplyCnt}}</span>
    </div>
    <div style="border-bottom: 1px solid black; margin:10px; position: relative; top: 25%;"></div>
    <!-- 댓글 목록 -->
    <table class="reply-wrap" v-if="replyList.length">
      <tbody>
    <!-- <colgroup>
      <col width=40%>
      <col width=60%>
      <col width=100%>
      <col width=100%>
    </colgroup> -->
        <tr v-for="reply in replyList" :key="reply.replyId">
          <div v-for="(callsign, index) in familyCallsignList" :key="index" >
            <div v-if="reply.userId === callsign.toUserId">
              <td><button type="button" class="btn-close" aria-label="Close" @click="eraseReply(reply.replyId, reply.userId)"></button></td>
              <td><span style="font-weight: bold;">{{ callsign.callsign }}</span></td>
              <td>{{ reply.replyContent }}</td>
              <!-- <td><img src="@/assets/images/pencil-fill.svg" alt="pencil" @click="changeReply(reply.replyId)"></td> -->
            </div>
          </div>
        </tr>
      </tbody>
    </table>
    <!-- 댓글 작성칸 -->
    <div class="reply-write-wrap">
      <input v-model="message" class="reply" type="text" placeholder="댓글을 입력해보세요" @keyup.enter="postReply">
      <Button style="display: inline-block;" buttonClass="small positive" buttonText="등록" @click="postReply"></Button>
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
      userId: '',
      message: '',
      onHeart: false
    }
  },
  created () {
    // console.log(this.$route.params.boardId)
    this.boardId = localStorage.getItem('boardId')
    this.userId = localStorage.getItem('userId')

    // 게시글 상세보기
    const info = {
      boardId: this.boardId,
      userId: this.userId
    }
    this.getOneFeed(info)

    // 댓글 목록 조회 (나중에 param에서 넘어온 boardId로 교체)
    this.getReplyList(localStorage.getItem('boardId'))

    // 가족 콜사인 찾기 (나중에 param에서 넘어온 아이디로 교체)
    this.callsignList(localStorage.getItem('userId'))
  },
  // async mounted () {
  //   if (this.familyCallsignList.length !== 0) {
  //     console.log(this.familyCallsignList)
  //     await this.familyCallsignList.forEach(callsign => {
  //       console.log(callsign)
  //       if (localStorage.getItem('userId') === callsign.toUserId) {
  //         this.writterName = callsign.callsign
  //       }
  //     })
  //   }
  //   console.log(this.writterName)
  // },
  computed: {
    ...mapState(boardStore, ['feed', 'replyList']),
    ...mapState(familyStore, ['familyCallsignList'])
  },
  methods: {
    ...mapActions(boardStore, ['getOneFeed', 'deleteFeed', 'getReplyList', 'createReply', 'updateReply', 'deleteReply']),
    ...mapActions(familyStore, ['callsignList']),
    // 게시글 수정
    goUpdate () {
      this.$router.push({ name: 'feedUpdate', params: { boardId: this.boardId } })
    },
    // 게시글 삭제
    eraseFeed () {
      this.deleteFeed(this.boardId)
    },
    // 댓글 작성
    postReply () {
      if (this.message.length === 0) {
        alert('댓글을 입력해주세요')
      } else {
        const info = {
          boardId: this.boardId,
          userId: this.userId,
          replyContent: this.message
        }
        this.createReply(info)
      }
    },
    // 댓글 수정 (미등록)
    changeReply (replyId) {
      this.updateReply()
    },
    // 댓글 삭제
    eraseReply (replyId, userId) {
      const info = {
        boardId: this.boardId,
        replyId: replyId,
        userId: userId
      }
      this.deleteReply(info)
    },
    // 하트 변경
    changeHeart () {
      this.onHeart = !this.onHeart
      console.log(this.onHeart)
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
    width: 70%;
    border-radius: 5px;
    border: 1px solid #ae5f40;
  }
}
</style>
