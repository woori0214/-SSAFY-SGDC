<template>
  <div>
      <div class="top">
          <h2> {{ userData.user_nickname }} 님 프로필</h2>
          <div v-if="userData.complain_cnt > 0">
              <img v-for="complain in userData.complain_cnt" :key="complain" src="@/assets/pinno.png" alt="pinno"
                  class="complain_img">
          </div>
      </div>
      <div class="middle">
          <!--사용자 이미지, 버튼-->
          <div class="middle1">
              <div class="user_img_badge">
                  <img :src="userData.user_img" alt="use_img" class="user_img">
                  <img :src="userBadgeImage" alt="badge_img" class="badge_img">
              </div>
              <div v-if="mypageUserId != loginUserId" class="myprofile_btns">
                  <div class="myprofile_btns">
                      <button class="myprofile_btn" @click="openSendMsg">도전장 보내기</button>
                      <button @click="toggleFollow()" class="myprofile_btn">{{ getButtonText(isFollowing) }} </button>
                  </div>
              </div>
              <div v-if="mypageUserId === loginUserId" class="myprofile_btns">
                  <button class="myprofile_btn" @click="openCompetMailbox">도전장함</button>

                  <RouterLink to="/mypageupdate" tag="button" class="myprofile_btn"
                      style="text-decoration: none; text-align: center; display: flex; flex-direction: column; align-items: center;">

                      <span style="margin-top: auto; margin-bottom: auto;">마이페이지 수정</span>

                  </RouterLink>

              </div>
          </div>
          <!--팔로우 수-->
          <div class="follow_cnt">
              <div class="follow_div">
                  <div class="follow_cnt_num">{{ ssallower_cnt }}</div>
                  <div>쌀로워</div>
              </div>
              <div class="follow_div">
                  <div class="follow_cnt_num">{{ ssallowing_cnt }}</div>
                  <div>쌀로잉</div>
              </div>
          </div>

          <!--도전장함-->
          <div v-if="isMailboxOpen" class="popup_mailbox">
              <div class="mailbox_content">
                  <CompetitionMailbox />
                  <button @click="isMailboxOpen = false" class="mailbox_close_btn">닫기</button>
              </div>
          </div>

          <!--도전장 보내기-->
          <div v-if="isopenSendMsg" class="popup_sendmsg">
              <div class="sendmsg_contents">
                  <div class="sendmsg_info">카테고리를 선택해주세요</div>
                  <div class="sendmsg_content">
                      <div class="sendmsg_selectcategory">선택한 카테고리 : {{ selectedCategoryNickname }}</div>
                      <div class="sendmsg_category">
                          <button v-for="category in categories" :key="category.id" @click="selectCategory(category)"
                              class="sendmsg_category_btn">
                              {{ category.name }}
                          </button>
                      </div>
                      <div class="sendmsg_btns">
                          <button @click="sendmsg()" class="sendmsg_btn">도전장 보내기</button>
                          <button @click="isopenSendMsg = false" class="sendmsg_btn">닫기</button>
                      </div>
                  </div>

              </div>
          </div>
      </div>
      <!--도전장 보냄 확인-->
      <div v-if="showResponseModal" class="send_msg_request">
          <div class="send_msg_request_contents">
              <div class="send_msg_request_content">
                  <h3>도전장 전송 완료</h3>
                  <button @click="closeResponseModal" class="mailbox_close_btn">닫기</button>
              </div>
          </div>
      </div>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue';
import { useUserStore } from '@/stores/user';
import { useBadgeStore } from '@/stores/badge';
import { useCompetionStore } from '@/stores/competition';
import { useFollowStore } from '@/stores/follow';
import CompetitionMailbox from '@/components/Competition/CompetitionMailbox.vue';

// 카테고리 이미지
import timerImage from '@/assets/wake.png';
import algoImage from '@/assets/algo.png';
import healthImage from '@/assets/health.png';
import studyImage from '@/assets/study.png';
import dietImage from '@/assets/diet.png';
import fightingImage from '@/assets/fighting.png';

const mypageUser = ref([])

export default {
  props: {
      userId: String,
      loginUser: String,
  },

  setup(props) {

      const categories = ref([
          {
              id: 1,
              name: '기상',
              contents: 'SSAFY 입실체크 빨리하기',
              img: timerImage
          },
          {
              id: 2,
              name: '알고리즘',
              contents: '알고리즘 1일 1문제 풀기',
              img: algoImage
          },
          {
              id: 3,
              name: '운동',
              contents: '운동 인증샷 찍기(헬스장, 공원 등)',
              img: healthImage
          },
          {
              id: 4,
              name: '스터디',
              contents: '하루 몇시간 공부했는지 인증하기',
              img: studyImage
          },
          {
              id: 5,
              name: '식단',
              contents: '10층 샐러드 빈그릇 인증샷 찍기',
              img: dietImage
          },
          {
              id: 6,
              name: '절제',
              contents: '오늘 나는 ㅇㅇㅇ을 참았다.',
              img: fightingImage
          },
      ]);
      
      const userStore = useUserStore();
      const badgeStore = useBadgeStore();
      const competStore = useCompetionStore();
      const followStore = useFollowStore();

      const mypageUserId = ref(props.userId);
      const loginUserId = ref(props.loginUser);

      // 나중에 주석 풀기
      const userData = ref({})
      const ssallowing_cnt = ref(0);
      const ssallower_cnt = ref(0);
      const selectedCategory = ref(null);
      const selectedCategoryNickname = ref(null);

      const isMailboxOpen = ref(false);
      const isopenSendMsg = ref(false);
      const isFollowing = ref(false);
      const showResponseModal = ref(false);

      // 사용자의 뱃지 이미지 찾기
      const userBadgeImage = computed(() => {
          const badge = badgeStore.badgeList.find(badge => badge.badge_id === mypageUser.value.badge_id);
          return badge ? badge.badge_img : ''; // 일치하는 뱃지가 있으면 이미지 반환, 없으면 빈 문자열 반환
      });

      // 도전장함
      const openCompetMailbox = function () {
          isMailboxOpen.value = true;
      }

      // 도전장보내기
      const openSendMsg = function () {
          isopenSendMsg.value = true;
      };
      const selectCategory = (category) => {
          selectedCategory.value = category.id;
          selectedCategoryNickname.value = category.name;
          // console.log(selectedCategory.value)
          // console.log(selectedCategoryNickname.value)
      };
      const sendmsg = function () {
          // selectedCategory의 현재 값으로 작업을 수행
          if (selectedCategory.value !== null) {
              showResponseModal.value = true;
              const friendSendmsg = {
                  user_id: loginUser.value,
                  friend_id: mypageUser.value.user_id,
                  category_id: selectedCategory.value,
              }
              // console.log(friendSendmsg)
              competStore.friendSend(friendSendmsg)
                  .then((res) => {
                      console.log('도전장 보내기 성공')
                      isopenSendMsg.value = false;
                      showResponseModal.value = true;
                  })
                  .catch((err) => {
                      console.log(err)
                  })
          } else {
              alert('"카테고리가 선택되지 않았습니다."')
          }

      };

      const closeResponseModal = () => {
          showResponseModal.value = false;
      }

      // 버튼 토글
      const toggleFollow = () => {
          isFollowing.value = !isFollowing.value;
          if (isFollowing.value) {
              goSsallowing();
          } else {
              goUnssallow();
          }
      };

      const goSsallowing = function () {
          const ssallowingData = { user_id: mypageUserId.value, following_id: loginUserId.value };
          followStore.plusSsallowing(ssallowingData)
              .then((res) => {
                  console.log(res);
              })
              .catch((err) => {
                  console.log(err);
              })
      };

      const goUnssallow = function () {
          const UnssallowingData = { user_id: mypageUserId.value, following_id: loginUserId.value };
          followStore.deleteSsallowing(UnssallowingData)
              .then((res) => {
                  console.log(res);
              })
              .catch((err) => {
                  console.log(err)
              })
      }
      // 버튼 텍스트
      const getButtonText = (isFollowing) => {
          return isFollowing ? '언쌀로우' : '쌀로우';
      };

      onMounted(() => {
          userStore.userData(mypageUserId.value)
              .then((res) => {
                  userData.value = res.data.data
              })
              .catch((err) => {
                  console.log(err)
              })

          // 페이지 열었을 때 쌀로우 수
          followStore.getSsallowCount(mypageUserId.value)
              .then((res) => {
                  ssallowing_cnt.value = res.data.data.followingCount
                  ssallower_cnt.value = res.data.data.followerCount
              })
              .catch((err) => {
                  console.log(err)
              })

          // 팔로우 했는지 안했는지 확인
          const checkusers = { user_id: mypageUserId.value, following_id: loginUserId.value }
          followStore.checkSsallowing(checkusers)
              .then((res) => {
                  isFollowing.value = res.data.success
              })
              .catch((err) => {
                  console.log(err)
              })

      });

      return {
          categories,
          selectedCategoryNickname,
          mypageUserId, loginUserId,
          userData,
          userBadgeImage,
          isopenSendMsg,
          ssallowing_cnt, ssallower_cnt,
          isMailboxOpen,
          isFollowing,
          showResponseModal,
          closeResponseModal,
          toggleFollow, getButtonText,
          openSendMsg, selectCategory, sendmsg,
          openCompetMailbox,
      };
  },
  components: { CompetitionMailbox }
}
</script>

<style scoped>
.top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 10px 50px;
}

.complain_img {
  border: 1px black solid;
  border-radius: 100px;
  background-color: aliceblue;
  width: 40px;
  height: 40px;
  margin: 3px;
}

.friend_btn {
  margin: 10px;
}

.user_img_badge {
  align-items: center;
  position: relative;
  display: inline-block;
}

.user_img {
  background: #f8f9fb;
  border: #aecbeb 2px solid;
  border-radius: 500px;
  width: 130px;
  height: 130px;
  margin: 10px;
}

.badge_img {
  position: absolute;
  /* 절대 위치 설정 */
  bottom: -0px;
  /* 사용자 이미지의 오른쪽 아래에 위치 */
  right: -15px;
  width: 70px;
  /* 뱃지 크기 */
  height: 70px;
}

.middle {
  display: flex;
  justify-content: space-around;
  align-items: center;

  margin: 20px;
  border-radius: 20px;
  background-color: #f8f9fb;
}

.middle1 {
  display: flex;
  align-items: center;
  width: 50%;
}

.myprofile_btns {
  display: flex;
  flex-direction: column;
  margin: 30px;
}

.myprofile_btn {
  margin: 10px 20px;
  background: #e1ecf7;
  border: none;
  border-radius: 30px;
  color: black;
  width: 170px;
  height: 40px;
  font-size: medium;
  font-weight: bolder;
}

.myprofile_btn:hover {
  margin: 10px 20px;
  background: #83b0e1;
  color: white;
}

.follow_cnt {
  display: flex;
  text-align: center;
  font-size: 18px;
  font-weight: bolder;
}

.follow_div {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  text-align: center;
  margin: 10px 30px;
  padding-bottom: 5px;
  width: 100px;
  height: 100px;
  border-radius: 50%;
  background-color: #e1ecf7;
}

.follow_cnt_num {
  font-size: 30px;
}



/* 도전장함, 도전장 보내기 */
.popup_mailbox,
.popup_sendmsg {
  border: 2px solid red;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 100000;
}

.mailbox_content {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  text-align: center;
  background-color: #f8f9fb;
  border: #aecbeb 2px solid;
  border-radius: 20px;
  width: 50%;
  /* height: 60%; */
  height: calc(80% - 30px);
  padding: 30px;
}

.sendmsg_contents {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #e1ecf7;
  border-radius: 20px;
  width: 60%;
  padding: 10px;
  /* height: calc(70% - 10px); */
  text-align: center;
}

.sendmsg_info {
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 30px;
  background-color: #83b0e1;
  color: white;
  font-size: 25px;
  width: 400px;
  height: 50px;

}

.sendmsg_content {
  display: flex;
  flex-direction: column;
  background-color: #f8f9fb;
  border-radius: 20px;
  margin: 10px;
  padding: 30px;
  padding-top: 10px;
  width: 80%;
  height: calc(80% - 10px);
  text-align: center;
}

.sendmsg_selectcategory {
  font-size: 18px;
  font-weight: bold;
}

.sendmsg_category {
  background-color: #e1ecf7;
  border-radius: 20px;
  padding: 10px;
  margin-top: 15px;
}

.sendmsg_category_btn {
  cursor: pointer;
  padding: 10px;
  margin: 10px;

  background-color: #aecbeb;
  border-radius: 20px;
  border: none;

  width: 120px;
  color: black;
  font-size: 16px;
  font-weight: bolder;
}

.sendmsg_category_btn.active {
  background-color: #83b0e1;
  color: white;
}

.mailbox_close_btn {
  width: 150px;
  height: 50px;
  margin: 10px auto;
  margin-bottom: 0;

  background-color: #e1ecf7;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  color: black;
  font-size: 16px;
  font-weight: bold;
}

.mailbox_close_btn:hover {
  background-color: #83b0e1;
  color: white;
}


.sendmsg_btns {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.sendmsg_btn {
  text-align: center;

  margin: 20px;
  width: 200px;
  height: 50px;

  background-color: #e1ecf7;
  border: none;
  border-radius: 25px;
  color: black;
  font-size: larger;
  font-weight: bolder;

}

.sendmsg_btn:hover {
  background-color: #83b0e1;
  color: white;
}

.send_msg_request {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 100000;
}

.send_msg_request_contents {
  background-color: #e1ecf7;
  padding: 20px;
  border-radius: 10px;
  text-align: center;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}

.send_msg_request_content {
  background-color: #f8f9fb;
  padding: 20px;
  border-radius: 10px;
  text-align: center;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}

@media screen and (max-width: 768px) {

  .complain_img {
      width: 20px;
      height: 20px;
      margin: 2px;
  }

  .user_img {
      width: 100px;
      height: 100px;
      margin: 10px;
  }

  .badge_img {
      bottom: 10px;
      right: 0px;
      width: 40px;
      height: 40px;
  }

  .myprofile_btn {
      width: 150px;
      font-size: 15px;
  }

  .follow_cnt {
      display: flex;
      text-align: center;
      font-size: 17px;
      font-weight: bolder;
  }

  .follow_div {
      width: 80px;
      height: 80px;
      margin: 10px 20px;
  }

  .follow_cnt_num {
      font-size: 25px;
  }

  .sendmsg_btn {
      width: 150px;
      height: 40px;
      padding: 10px;
      font-size: 15px;
  }
}

@media screen and (max-width: 650px) {
  .top {
      margin: 10px 10px;
  }

  .middle {
      flex-direction: column;
      align-items: center;
      justify-content: center;
      margin-top: 10px;
  }

  .middle1 {
      flex-direction: column;
      align-items: center;
      justify-content: center;
      margin-top: 10px;
  }

  .myprofile_btns {
      justify-content: center;
      align-items: center;
  }

  .myprofile_btn {
      width: 180px;
      font-size: 12px;
      margin: 10px;
  }

  .follow_cnt {
      font-size: 16px;
  }

  .follow_div {
      width: 80px;
      height: 80px;
      padding-bottom: 5px;
  }

  .sendmsg_info {
      width: 200px;
      font-size: 15px;
  }

  .sendmsg_category {
      height: calc(80% - 10px);
  }

  .sendmsg_category_btn {
      width: 90%;
      font-size: 13px;
      margin: 3px;
  }

  .sendmsg_btns {
      flex-direction: column;
      align-items: center;
  }

  .sendmsg_btn {
      width: 90%;
      height: 30px;
      font-size: 13px;
      margin: 3px;
  }
}
</style>