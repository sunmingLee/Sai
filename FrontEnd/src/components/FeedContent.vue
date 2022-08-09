<template>
    <div class="feed-wrap">
        <FeedHeader />
        <div>
            <div class="feed-flex" v-if="feedList.length">
                <div v-for="(feed, index) in feedList" :key="index" class="feed-div">

                    <div class="flex">
                        <div class="content-header">
                            <div v-for="(callsign, index) in familyCallsignList" :key="index" class="famliy-callsign">
                                <div v-if="feed.viewBoardResponseDto.userId === callsign.toUserId">
                                    <span>{{callsign.callsign}}</span>
                                </div>
                            </div>
                            <span>{{feed.viewBoardResponseDto.boardRegDatetime.substring(0,10)}}</span>
                        </div>
                    </div>
                    <div class="flex">
                        <div class="content-body">
                            <div v-if="feed.viewBoardResponseDto.pollYn">
                                <h3>{{feed.pollResponse.question}}</h3>
                                <div v-for="(choice, index) in feed.pollResponse.choices" :key="index">
                                    <div>
                                        <span>{{choice.text}}</span>
                                        <span>{{choice.voteCount}}</span>
                                    </div>
                                </div>
                            </div>
                            <div v-else-if="feed.viewBoardResponseDto.boardMediaYn">
                                <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
                                    <div class="carousel-inner" >
                                        <div class="carousel-item active">
                                            <!-- {{feed.viewBoardMediaResponseDto[0].boardMediaPath}} -->
                                            <img :src="url + feed.viewBoardMediaResponseDto[0].boardMediaPath" id="img" class="d-block w-100">
                                        </div>
                                        <div v-for="(src, index) in feed.viewBoardMediaResponseDto" :key="index">
                                            <!-- {{feed.viewBoardMediaResponseDto[index].boardMediaPath}} -->
                                            <div v-if="index !== 0" class="carousel-item">
                                                <img :src="url + src.boardMediaPath" id="img" class="d-block w-100">
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
                    <div class="flex">
                        <div class="content-cnt">
                            <img v-if="feed.boardLiked" :src="like" @click="unlikeButton" class="like-icon">
                            <img v-else :src="unlike" @click="likeButton">
                            {{feed.viewBoardResponseDto.boardLikeCnt}}
                        </div>
                        <div class="content-reply">
                            <img src="@/assets/images/comment-regular.svg" alt="calendar" style="width: 18px">
                            {{feed.viewBoardResponseDto.boardReplyCnt}}
                        </div>
                    </div>
                    <button @click="goDetail(feed.viewBoardResponseDto.boardId)">상세보기</button>
                </div>
            </div>
            <div v-else>
                <h3>등록된 게시글이 없습니다</h3>
            </div>
        </div>
        <div class="flex">
            <button @click="goBoardCreate" style="color: red">글 작성</button>
        </div>

    </div>
</template>
<script>
import { mapActions, mapState } from 'vuex'

const boardStore = 'boardStore'
const userStore = 'userStore'
const familyStore = 'familyStore'

export default {
  name: 'FeedContent',
  components: {
  },
  created () {
    // 피드 조회
    const info = {
      userId: localStorage.getItem('userId'),
      familyId: localStorage.getItem('familyId')
    }
    // 피드 전체 리스트 조회
    this.feedAllList(info)
    // 가족 콜사인 조회
    this.callsignList(info.userId)
    // 가족 정보 조회
    this.getFamilyInfo(info.familyId)
    // 유저 정보 조회
    this.checkUserInfo(info.userId)
    const user = localStorage.getItem('userInfo')
    const userInfo = JSON.parse(user)
  },
  mounted () {

  },
  computed: {
    ...mapState(boardStore, ['feedList']),
    ...mapState(familyStore, ['familyCallsignList']),
    url () {
      return 'i7a305.p.ssafy.io:8080'
    }
  },
  methods: {
    ...mapActions(userStore, ['checkUserInfo']),
    ...mapActions(boardStore, ['feedAllList']),
    ...mapActions(familyStore, ['callsignList', 'getFamilyInfo']),
    // 좋아요 버튼 클릭
    likeButton () {
      const info = {
        userId: this.$store.state.userId,
        familyId: this.$store.state.familyId
      }
      const icon = document.querySelector('.like-icon')
      console.log(icon)
      this.unlike = require('@/assets/images/heart-fill.svg')
      if (this.$store.state.feedAllList.boardLiked) {
        console.log('좋아요')
        // console.log(document.querySelector('.like-icon'))
        // this.$store.dispatch('likeClick', info)
      } else {
        console.log('좋아요 취소')
        this.unlike = require('@/assets/images/heart.svg')
        // this.$store.dispatch('unlikeClick', info)
      }
    },
    // 좋아요 취소
    unlikeButton () {

    },
    // 글 작성 페이지 이동
    goBoardCreate () {
      this.$router.push({ name: 'feedCreate' })
    },
    // 글 상세보기 페이지 이동
    goDetail () {
      // this.$router.push({
      //     name: "detailvideo",
      //     params: { videoSeq: videoSeq },
      // });
    }
  },
  data () {
    return {
      test: '',
      like: require('@/assets/images/heart-fill.svg'),
      unlike: require('@/assets/images/heart.svg')
    }
  }
}

</script>
<style lang="scss">
.famliy-callsign {
    display: inline-block;
}
.feed-wrap {
  width: 900px;

  .flex{
    display: flex;
    justify-content: center;
    text-align: center;
  }
  .feed-flex {
    .feed-div {
        width: 600px;
        margin: 0 auto;
        border: 1px solid black;
    }
  }
}
</style>
