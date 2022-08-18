<template>
 
    <div style="height: 100%">
       <FeedHeader />
      <div class="feed-wrap">
            <div class="feed-flex" v-if="this.feedList.length">
                <div v-for="(feed, index) in feedList" :key="index" class="feed-div">
                    <div class="header-flex">
                        <div class="content-header">
                            <div v-for="(callsign, index) in familyCallsignList" :key="index" class="famliy-callsign">
                                <div v-if="feed.viewBoardResponseDto.userId === callsign.toUserId">
                                    <span>{{callsign.callsign}}</span>
                                </div>
                                <div v-else style="display: none"></div>
                            </div>
                        </div>
                        <div class="header-date">
                          <span>{{feed.viewBoardResponseDto.boardRegDatetime.substring(0,10)}}</span>
                        </div>
                    </div>
                    <div class="flex body">
                        <div class="content-body">
                            <div v-if="feed.viewBoardResponseDto.pollYn" class="poll-body">
                              <table>
                                <tr class="poll-title">
                                  <th>{{feed.pollResponse.question}}</th>
                                </tr>
                                <tr class="poll-choice" v-for="(choice, index) in feed.pollResponse.choices" :key="index">
                                  <td>{{choice.text}}</td>
                                  <td><img class="poll-image" src="@/assets/images/person.svg" alt="user image">{{choice.voteCount}}</td>
                                </tr>
                              </table>
                            </div>
                            <div v-else-if="feed.viewBoardResponseDto.boardMediaYn" class="media-wrap">
                                <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
                                    <div class="carousel-inner" >
                                      <div class="carousel-inner" >
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
                                            <div v-if="src.boardMediaType.inclues('video')">
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
                            <div v-else>
                                <p>{{feed.viewBoardResponseDto.boardContent}}</p>
                            </div>
                        </div>
                    </div>
                    <div class="flex reaction">
                      <!-- 좋아요 -->
                        <div class="content-cnt">
                            <img v-if="feed.boardLiked" src="@/assets/images/suit-heart-fill.svg" @click="likeButton(feed.viewBoardResponseDto.boardId)" class="like-icon">
                            <img v-else src="@/assets/images/suit-heart.svg" @click="likeButton(feed.viewBoardResponseDto.boardId)" class="like-icon">
                            {{feed.viewBoardResponseDto.boardLikeCnt}}
                        </div>
                        <!-- 댓글 -->
                        <div class="content-reply">
                            <img src="@/assets/images/comment-regular.svg" alt="calendar" class="reply-icon">
                            {{feed.viewBoardResponseDto.boardReplyCnt}}
                        </div>
                        <div class="detail-button">
                          <Button buttonClass="small information" buttonText="상세보기" @click="goDetail(feed.viewBoardResponseDto.boardId)"></Button>
                        </div>
                    </div>
                    <!-- 첫번째 댓글 -->
                    <div v-if="feed.replyDto !== null" style="padding:2px 20px">
                      <div v-for="(callsign, index) in familyCallsignList" :key="index" class="famliy-callsign">
                        <div v-if="feed.replyDto.userId === callsign.toUserId">
                          <span style="font-weight: bold">{{callsign.callsign}}</span>
                        </div>
                      </div>
                      {{feed.replyDto.replyContent}}
                    </div>
                    <div v-else style="padding:2px 20px">
                      <span>등록된 댓글이 없습니다</span>
                    </div>
                </div>
                <div class="feed-more">
                  <Button buttonClass="small positive" buttonText="더보기" @click="feedMore"></Button>
                </div>
            </div>
            <div v-else>
                <h3>등록된 게시글이 없습니다</h3>
            </div>
        </div>
        <!-- <div class="feed-more">
                  <button>더보기</button>
                </div> -->
      <button id="btn-modal" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal" @click="goBoardCreate">
        <img style="width:25px;" src="@/assets/images/plus-lg.svg" alt="plus">
      </button>
    </div>
</template>
<script>
import { mapActions, mapState } from 'vuex'
import Button from './common/Button.vue'

const boardStore = 'boardStore'
const userStore = 'userStore'
const familyStore = 'familyStore'

export default {
  name: 'FeedContent',
  components: {
    Button
  },
  created () {
    // 피드 조회
    const info = {
      userId: localStorage.getItem('userId'),
      familyId: localStorage.getItem('familyId'),
      page: this.page
    }
    // 피드 초기화부터 때리고
    this.feedReset()
    // 피드 전체 리스트 조회
    this.feedAllList(info)
    // 가족 콜사인 조회
    this.callsignList(info.userId)
    // 가족 정보 조회
    this.getFamilyInfo(info.familyId)
    // 유저 정보 조회
    this.checkUserInfo(info.userId)
    // this.list = this.list.concat(this.$store.state.boardStore.feedList)
    // console.log(this.list)
    // console.log(this.$store.state.boardStore)
    // console.log("얏호")
    // console.log(this.$store.state.boardStore.feedList)
    // console.log(this.$store.state.boardStore.feedList.length)
  },
  mounted () {
    
  },
  computed: {
    ...mapState(boardStore, ['feedList']),
    ...mapState(familyStore, ['familyCallsignList']),
    image() {
      return feedList.boardLiked
    }
  },
  methods: {
    ...mapActions(userStore, ['checkUserInfo']),
    ...mapActions(boardStore, ['feedAllList', 'setBoardId', 'upBoardLike', 'downBoardLike', 'feedReset']),
    ...mapActions(familyStore, ['callsignList', 'getFamilyInfo']),
    // 좋아요 버튼 클릭
    likeButton (boardId) {
      const info = {
        userId: localStorage.getItem('userId'),
        boardId: boardId,
        familyId: localStorage.getItem('familyId'),
        page: this.page
      }
      // console.log(this.feedList)
      const boardNum = boardId - 1
      console.log(this.feedList)
      console.log(this.feedList[0].boardLiked)
      for(let i = 0; i < this.feedList.length; i++) {
        if(this.feedList[i].viewBoardResponseDto.boardId === boardId) {
          if(!this.feedList[i].boardLiked) {
            console.log("좋아요")
            this.upBoardLike(info)
          } else {
            console.log("좋아요 취소")
            this.downBoardLike(info)
          }
        }
      }
    },
    // 글 작성 페이지 이동
    goBoardCreate () {
      this.$router.push({ name: 'feedCreate' })
    },
    // 글 상세보기 페이지 이동
    goDetail (boardId) {
    //   console.log('들어가기 전: ' + boardId)
      this.setBoardId(boardId)
    },
    //더보기
    feedMore() {
      this.page++
      const info = {
        userId: localStorage.getItem('userId'),
        page: this.page,
        familyId: localStorage.getItem('familyId')
      }
      this.feedAllList(info)
      
      // this.list = this.list.push(this.$store.state.boardStore.feedList)
      // console.log(this.list)
      // this.list = this.list.concat(this.$store.state.boardStore.feedList)
    }
  },
  data () {
    return {
      page: 0,
      list: []
    }
  }
}

</script>
<style lang="scss">
li {
  list-style: none;
}
p {
  margin: 0;
}
//캐러셀
.carousel-inner{
  width: auto;
  height: 250px;
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
  // width: auto!important;
  // height: 250px;
  //옆으로 길게 늘리기
  width: 100%;
  //사진의 일부  
  // height: 100%;
}

.media-wrap {
  min-height: 300px;
}

//콜사인과 날짜
.header-flex {
  display: flex;
  justify-content: space-around;
  .header-date {
    font-weight: bold;
    margin: 10px 0 10px 0;
  }
}
.content-header {
  font-weight: bold;
  margin: 10px 0 10px 0;
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
    // display: flex;
    // justify-content: center;
    // align-items: center;
    .content-body {
      .poll-body {
        table {
          margin: 0 auto;
          min-width: 300px;
          :nth:child(2) {

          }
        }
      }
    }
  }
}



// content
.feed-wrap {
  max-width: 900px;
  margin: 0 auto;
}
// flexbox
.feed-flex {
  display: flex;
  flex-wrap: wrap;
  gap: 2em;
}
// item
.feed-div {
  min-width: 300px;
  // min-height: 300px;
  min-height: auto;
  //항목의 초기 길이 -> 이 div의 처음 길이(너비)는 600px
  flex-basis: 600px;
  margin: 0 auto;
  border-bottom: 2px solid #AE5F40;
  border-top: 2px solid #AE5F40;
}
.feed-flex > *{
    flex-grow: 0;
}

//좋아요, 댓글, 상세보기
.flex {
  &.reaction {
    display: flex;
    padding: 10px 24px;
    // border: 1px solid black;
    // .detail-button {
    //   margin-left: 375px;
    // }
  }
  .content-cnt {
    padding-right: 20px;
    .like-icon {
      width: 25px;
    }
  }
  .content-reply {
    padding-right: 20px;
    .reply-icon {
      width: 25px;
    }
  }
} 
//투표
.poll-title {
  th {
    font-size: 23px;
  }
}
.poll-choice {
  td {
    border: 1px solid black;
  }
  :nth-child(1) {
    border-right: none;
  }
  :nth-child(2) {
    border-left: none;
    text-align: right;
    min-width: 30px;
  }
}
table {
  border-collapse: separate;
  border-spacing: 0 10px;
}

//글 작성 버튼
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
.feed-more {
  min-width: 300px;
  min-height: auto;
  flex-basis: 600px;
  margin: 0 auto;
  text-align: center;
}
// .feed-more {
//   height: 30px;
//   width: 120px;
//   color: white;
//   margin: 20px auto;
//   text-align: center;
//   button {
//     background-color: #7b371c;
//     height: 30px;
//     width: 120px;
//   }
// }
</style>
