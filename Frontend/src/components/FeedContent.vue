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
                                <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel" style="height: 300px">
                                    <div class="carousel-inner">
                                      <div class="carousel-inner">
                                        <div class="carousel-item active">
                                          <div v-if="feed.viewBoardMediaResponseDto[0].boardMediaType.includes('video')" style="height: 100%">
                                            <video autoplay playsinline loop muted :src="feed.viewBoardMediaResponseDto[0].boardMediaPath" class="d-block w-100" style="height: 100%"/>
                                          </div>
                                          <div v-else style="height: 100%">
                                            <img :src="feed.viewBoardMediaResponseDto[0].boardMediaPath" class="d-block w-100" style="height: 100%">
                                            <!-- <img src="@/assets/images/byebye.png" class="d-block w-100" style="height: 100%; max-width=300px"> -->
                                          </div>
                                        </div>
                                        <div v-for="(src, index) in feed.viewBoardMediaResponseDto" :key="index"> 
                                          <div v-if="index !== 0" class="carousel-item">
                                            <div class="test" v-if="src.boardMediaType.includes('video')" style="height: 100%">
                                              <video autoplay playsinline loop muted :src="src.boardMediaPath" class="d-block w-100" style="height: 100%"/>
                                            </div>
                                            <div class="test" v-else style="height: 100%">
                                              <img :src="src.boardMediaPath" class="d-block w-100" style="height: 100%">
                                            <!-- <img src="@/assets/images/dog2.png" class="d-block w-100" style="height: 100%;"> -->
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
                      <!-- ????????? -->
                        <div class="content-cnt">
                            <img v-if="feed.boardLiked" src="@/assets/images/suit-heart-fill.svg" @click="likeButton(feed.viewBoardResponseDto.boardId)" class="like-icon">
                            <img v-else src="@/assets/images/suit-heart.svg" @click="likeButton(feed.viewBoardResponseDto.boardId)" class="like-icon">
                            {{feed.viewBoardResponseDto.boardLikeCnt}}
                        </div>
                        <!-- ?????? -->
                        <div class="content-reply">
                            <img src="@/assets/images/comment-regular.svg" alt="calendar" class="reply-icon">
                            {{feed.viewBoardResponseDto.boardReplyCnt}}
                        </div>
                        <div class="detail-button">
                          <Button buttonClass="small information" buttonText="????????????" @click="goDetail(feed.viewBoardResponseDto.boardId)"></Button>
                        </div>
                    </div>
                    <!-- ????????? ?????? -->
                    <div v-if="feed.replyDto !== null" style="padding:2px 20px">
                      <div v-for="(callsign, index) in familyCallsignList" :key="index" class="famliy-callsign">
                        <div v-if="feed.replyDto.userId === callsign.toUserId">
                          <span style="font-weight: bold">{{callsign.callsign}}</span>
                        </div>
                      </div>
                      {{feed.replyDto.replyContent}}
                    </div>
                    <div v-else style="padding:2px 20px">
                      <span>????????? ????????? ????????????</span>
                    </div>
                </div>
                <div class="feed-more">
                  <Button buttonClass="small positive" buttonText="?????????" @click="feedMore"></Button>
                </div>
            </div>
            <div v-else>
                <h3>????????? ???????????? ????????????</h3>
            </div>
        </div>
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
    // ?????? ??????
    const info = {
      userId: localStorage.getItem('userId'),
      familyId: localStorage.getItem('familyId'),
      page: this.page
    }
    // ?????? ??????????????? ?????????
    this.feedReset()
    // ?????? ?????? ????????? ??????
    this.feedAllList(info)
    // ?????? ????????? ??????
    this.callsignList(info.userId)
    // ?????? ?????? ??????
    this.getFamilyInfo(info.familyId)
    // ?????? ?????? ??????
    this.checkUserInfo(info.userId)
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
    // ????????? ?????? ??????
    likeButton (boardId) {
      const info = {
        userId: localStorage.getItem('userId'),
        boardId: boardId,
        familyId: localStorage.getItem('familyId'),
        page: this.page
      }
      for(let i = 0; i < this.feedList.length; i++) {
        if(this.feedList[i].viewBoardResponseDto.boardId === boardId) {
          if(!this.feedList[i].boardLiked) {
            this.upBoardLike(info)
          } else {
            this.downBoardLike(info)
          }
        }
      }
    },
    // ??? ?????? ????????? ??????
    goBoardCreate () {
      this.$router.push({ name: 'feedCreate' })
    },
    // ??? ???????????? ????????? ??????
    goDetail (boardId) {
      this.setBoardId(boardId)
    },
    //?????????
    feedMore() {
      this.page++
      const info = {
        userId: localStorage.getItem('userId'),
        page: this.page,
        familyId: localStorage.getItem('familyId')
      }
      this.feedAllList(info)
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
//?????????
.carousel-inner{
  width: 100%!important;
  // height: 250px;
  height: 100%;
  margin: 0 auto
}
.carousel-item{
  width: 100%;
  height: 100%;
}
.d-block {
  margin: 0 auto;
  width: 300px!important;
  height: 100%;
}

.media-wrap {
  min-height: 300px;
}

//???????????? ??????
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

//???, ??????, ????????? ??????
.flex {
  &.body {
    border: 1px solid #7b371c;
    padding: 10px;
    height: auto;
    margin: 0 10px;
    .content-body {
      .poll-body {
        table {
          margin: 0 auto;
          min-width: 300px;
        }
      }
    }
  }
}

.carousel-control-next, .carousel-control-prev {
  top: 150px;
}
.carousel-control-prev-icon { 
    background-image: url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23000' viewBox='0 0 8 8'%3E%3Cpath d='M5.25 0l-4 4 4 4 1.5-1.5-2.5-2.5 2.5-2.5-1.5-1.5z'/%3E%3C/svg%3E"); 
 }

.carousel-control-next-icon {
   background-image: url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23000' viewBox='0 0 8 8'%3E%3Cpath d='M2.75 0l-1.5 1.5 2.5 2.5-2.5 2.5 1.5 1.5 4-4-4-4z'/%3E%3C/svg%3E"); 
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
  //????????? ?????? ?????? -> ??? div??? ?????? ??????(??????)??? 600px
  flex-basis: 600px;
  margin: 0 auto;
  border-bottom: 2px solid #AE5F40;
  border-top: 2px solid #AE5F40;
}
.feed-flex > *{
    flex-grow: 0;
}

//?????????, ??????, ????????????
.flex {
  &.reaction {
    display: flex;
    padding: 10px 24px;
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
//??????
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
    padding: 7px;
    border-right: none;
  }
  :nth-child(2) {
    border-left: none;
    text-align: center;
    min-width: 30px;
  }
}
table {
  border-collapse: separate;
  border-spacing: 0 10px;
}

//??? ?????? ??????
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
</style>
