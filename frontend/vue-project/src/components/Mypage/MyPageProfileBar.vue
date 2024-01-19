<template>
    <div>
        <div class="top">
            <h2>{{ user.nickname }} 님</h2>
            <div>
                <img v-for="complain in user.complain_cnt" :key="complain" src="@/assets/pinno.png" alt="pinno"
                    class="complain_img">
            </div>
        </div>
        <div class="middle">
            <div class="middle1">
                <div class="user_img_badge">
                    <img :src="user.img" alt="use_img" class="user_img">
                    <p>{{ user.badge_id }}</p>
                </div>
                <div>
                    <button class="friend_btn">도전장 보내기</button>
                    <button class="friend_btn">쌀로우 신청</button>
                </div>
            </div>
            <div class="follow_cnt">
                <div class="follow_div">
                    <h1>{{ user.ssallower_friends.length }}</h1>
                    <h3>쌀로워 수</h3>
                </div>
                <div class="follow_div">
                    <h1>{{ user.ssallowing_friends.length }}</h1>
                    <h3>쌀로잉 수</h3>
                </div>
            </div>

        </div>
    </div>
</template>

<script setup>
const user = {
    nickname: '우리다',
    img: './src/assets/image1.png',
    ssallower_friends: [
        {
            id: 1,
            name: '화석'
        },
        {
            id: 2,
            name: '태범'
        },
        {
            id: 3,
            name: '지은'
        },
    ],
    ssallowing_friends: [
        {
            id: 1,
            name: '현춘'
        },
        {
            id: 2,
            name: '수안'
        },
    ],
    complain_cnt: 2,
    badge_id: '분조장'

}
</script>

<style scoped>
.top {
    display: flex;
    justify-content: space-between;
    /* 중앙으로 맞춤 */
    align-items: center;
    margin: 0 10px;
}

.complain_img {
    border: 1px black solid;
    border-radius: 100px;
    background-color: aliceblue;
    width: 30px;
    height: 30px;
    margin: 2px;
}

.friend_btn {
    margin: 10px;
}

.user_img_badge {
    display: flex;
    align-items: center;
}

.user_img {
    border: 1px black solid;
    border-radius: 500px;
    widows: 130px;
    height: 130px;
    margin: 10px;
}

.middle {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.follow_cnt {
    display: flex;
    margin-right: 30px;
    text-align: center;
}

.follow_div {
    margin: 0 50px;
}

@media (max-width: 768px) {
    .top {
        flex-direction: column;
        align-items: center;
    }

    .complain_img {
        width: 20px;
        height: 20px;
        margin: 2px;
    }

    .middle {
        flex-direction: column;
        align-items: center;
    }

    .user_img {
        width: 100px;
        height: 100px;
        margin: 10px;
    }

    .follow_cnt {
        margin: 10px;
    }
}
</style>