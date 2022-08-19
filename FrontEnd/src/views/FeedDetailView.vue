<template>
  <div class="feed-detail-wrap">
    <HeaderTitle hasBack="true"></HeaderTitle>
    <!-- 글 작성자 & 버튼 -->
    <div class="feed-head-wrap">
      <div v-for="(callsign, index) in familyCallsignList" :key="index">
        <div v-if="feed.viewBoardResponseDto.userId === callsign.toUserId">
          <FeedUser :name="callsign.callsign"></FeedUser>
        </div>
      </div>
      <Button v-if="this.feed.viewBoardResponseDto.userId === this.userId" buttonClass="small information" buttonText="수정" @click="goUpdate"></Button>
      <span style="width: 5px"></span>
      <Button v-if="this.feed.viewBoardResponseDto.userId === this.userId" style="margin-right:5%" buttonClass="small negative" buttonText="삭제" @click="eraseFeed"></Button>
    </div>
    <div class="flex body">
      <div class="content-body">
        <!-- 투표 -->
        <div v-if="feed.viewBoardResponseDto.pollYn" class="poll-body">
          <table>
            <colgroup>
              <col width="20%">
              <col width="30%">
              <col width="30%">
              <col width="5%">
            </colgroup>
            <tr class="poll-title">
              <th>{{feed.pollResponse.question}}</th>
            </tr>
            <tr class="poll-choice" v-for="(choice, index) in feed.pollResponse.choices" :key="index">
              <td><input type="radio" :id="choice.key" v-model="picked" :value="choice"/></td>
              <td>{{choice.text}}</td>
              <td><img class="poll-image" src="@/assets/images/person.svg" alt="user image">{{choice.voteCount}}</td>
              <td></td>
            </tr>
          </table>
          <Button buttonText="투표하기" buttonClass="small positive" @click="choose(picked.id)"></Button>
        </div>
        <!-- 미디어 -->
        <div v-if="feed.viewBoardResponseDto.boardMediaYn" class="media-wrap">
            <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-inner" style="margin: 0 auto">
                  <div class="carousel-inner" style="margin: 0 auto">
                    <div class="carousel-item active">
                        <div v-if="feed.viewBoardMediaResponseDto[0].boardMediaType.includes('video')">
                        <video autoplay playsinline loop muted :src="feed.viewBoardMediaResponseDto[0].boardMediaPath" class="d-block w-100"/>
                      </div>
                      <div v-else>
                        <img :src="feed.viewBoardMediaResponseDto[0].boardMediaPath" class="d-block w-100">
                        <!-- <img src="@/assets/images/byebye.png" class="d-block w-100"> -->
                        </div>
                    </div>
                    <div v-for="(src, index) in feed.viewBoardMediaResponseDto" :key="index"> 
                      <div v-if="index !== 0" class="carousel-item">
                        <div v-if="src.boardMediaType.includes('video')">
                          <video autoplay playsinline loop muted :src="src.boardMediaPath" class="d-block w-100"/>
                        </div>
                        <div v-else>
                        <img :src="src.boardMediaPath" class="d-block w-100">
                        <!-- <img src="@/assets/images/dog2.png" class="d-block w-100"> -->
                        </div>
                      </div>
                    </div>
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
        </div>
      </div>
    </div>
    <!-- 추가기록  -->
    <div class="tag-wrap">
      <div v-if="feed.viewBoardResponseDto.boardDate">{{feed.viewBoardResponseDto.boardDate}}</div>
      <div v-if="feed.viewBoardResponseDto.boardLocation">{{feed.viewBoardResponseDto.boardLocation}}</div>
      <div v-if="feed.viewBoardResponseDto.boardTaggedYn">
        <span v-for="(user, index) in feed.viewBoardTaggedResponseDto" :key="index">
          <span v-for="(callsign, index) in familyCallsignList" :key="index">
            <span v-if="user.userId === callsign.toUserId" class="tagged-user">{{callsign.callsign}}</span>
          </span>
        </span>
      </div>
    </div>
    <!-- 본문 -->
    <div class="content-wrap" v-if="feed.viewBoardResponseDto.boardContent">
      <span>{{feed.viewBoardResponseDto.boardContent}}</span>
    </div>
    <!-- 좋아요 & 댓글 개수 -->
    <div class="reaction-wrap">
      <img v-if="!feed.boardLiked" src="@/assets/images/suit-heart.svg" alt="empty heart" @click="changeHeart">
      <img v-else src="@/assets/images/suit-heart-fill.svg" alt="fill heart" @click="changeHeart">
      <span>{{feed.viewBoardResponseDto.boardLikeCnt}}</span>
      <img src="@/assets/images/chat-right.svg" alt="reply">
      <span>{{replyList.length}}</span>
    </div>
    <div style="border-bottom: 1px solid black; margin:10px; position: relative; top: 11%;"></div>
    <!-- 댓글 목록 -->
    <table class="reply-wrap" v-if="replyList.length">
      <tbody>
        <tr v-for="reply in replyList" :key="reply.replyId" @change="changeReplyList">
          <div v-for="(callsign, index) in familyCallsignList" :key="index" >
            <div v-if="reply.userId === callsign.toUserId">
              <td><button type="button" class="btn-close" aria-label="Close" @click="eraseReply(reply.replyId, reply.userId)"></button></td>
              <td style="padding-right: 15px"><span style="font-weight: bold;">{{ callsign.callsign }}</span></td>
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
      picked: '',
      checkedvote: false
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

    // 댓글 목록 조회
    this.getReplyList(this.boardId)

    // 가족 콜사인 찾기
    this.callsignList(localStorage.getItem('userId'))
  },
  computed: {
    ...mapState(boardStore, ['feed', 'replyList']),
    ...mapState(familyStore, ['familyCallsignList'])
  },
  methods: {
    ...mapActions(boardStore, ['getOneFeed', 'deleteFeed', 'getReplyList', 'createReply', 'updateReply', 'deleteReply', 'chooseVote', 'upBoardLike', 'downBoardLike']),
    ...mapActions(familyStore, ['callsignList']),
    // 게시글 수정
    goUpdate () {
      this.$router.push({ name: 'feedUpdate', params: { boardId: this.boardId } })
    },
    // 게시글 삭제
    eraseFeed () {
      this.deleteFeed(this.boardId)
    },
    // 투표 선택하기
    choose (choiceId) {
      const info = {
        pollId: this.feed.pollResponse.id,
        choiceId
      }
      if (this.checkedvote === false) {
        //console.log(this.checkedvote)
        this.checkedvote = true
        this.chooseVote(info)
      }
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
        // console.log('postsReply: ' + info.boardId)
        this.createReply(info)
        this.message = ''
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
    // 좋아요 변경
    changeHeart () {
      const info = {
        boardId: this.boardId,
        userId: this.userId
      }
      if (!this.feed.boardLiked) { // 좋아요 등록
        this.upBoardLike(info)
      } else { // 좋아요 취소
        this.downBoardLike(info)
      }
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
  height: 300px;
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
//캐러셀
.carousel-inner{
  width: 100%!important;
  height: 100%;
}
.carousel-item{
  // width: auto;
  // height: 100px;
  width: 100%;
  height: 100%;
}
.d-block {
  //사진 크기를 가운데
  margin: 0 auto;
  width: auto!important;
  height: 250px;
  //옆으로 길게 늘리기
  // width: 100%;
  //사진의 일부  
  // height: 100%;
}

.media-wrap {
  min-height: 300px;
}
.tag-wrap{
  position: relative;
  text-align: right;
  top: 2%;
  right: 3%;
  &span{
    font-weight: 2px;
  }
}
.content-wrap{
  position: relative;
  text-align: left;
  top: 5%;
  left: 3%;
}
.reaction-wrap{
  position: relative;
  text-align: left;
  top: 10%;
  left: 3%;
  img, span{
    margin-right: 3px;
  }
}
.reply-wrap{
  position: relative;
  top: 10%;
}
.reply-write-wrap{
  position: relative;
  top: 12%;
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
//글, 사진, 투표의 공간
.flex {
  &.body {
    // width: 500px;
    border: 1px solid #7b371c;
    padding: 10px;
    // height: 300px;
    height: auto;
    margin: 0 10px;
    margin-top: 5%;
    // display: flex;
    // justify-content: center;
    // align-items: center;
    .content-body {
      div {
        padding: 1%;
        // margin-bottom: 5%;
      }
      .poll-body {
        table {
          margin: 0 auto;
          min-width: 300px;
        }
      }
    }
  }
}
.poll-title{
  th{
    text-align: left;
  }
}
.poll-choice{
  :nth-child(1) {
    border-right: none;
    text-align: center;
  }
  :nth-child(2) {
    border-left: none;
    border-right: none;
    // min-width: 30px;
    text-align: left;
  }
  :nth-child(3) {
    border-left: none;
    border-right: none;
    text-align: right;
  }
  :nth-child(4) {
    border-left: none;
  }
}
table {
  border-collapse: separate;
  border-spacing: 0 10px;
}
.tagged-user{
  margin-left: 8px;
}
</style>
