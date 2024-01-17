<template>
    <div>
        <div id="challenge-board">
            <h1>현황게시판</h1>
            <div id="current-board">

                <div id="solo">
                    <h2>솔로 모드 현황</h2>
                    <div id="solo-board">
                        <div id="success-graph">
                            <h3>전적그래프</h3>
                        </div>
                        <div id="categories">
                            <button id="category">기상</button>
                            <button id="category">알고리즘</button>
                            <button id="category">운동</button>
                            <button id="category">식단</button>
                            <button id="category">스터디</button>
                            <button id="category">절제</button>
                        </div>
                    </div>
                </div>
                <div id="compet">
                    <h2>경쟁 모드 현황</h2>
                    <div>
                        
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>

</script>

<style scoped>
#challenge-board {
    border: 1px black solid;
}
#current-board {
    display: flex;
}

#solo {
    border: 1px black solid;
    width: 50%;
    margin: 10px;
    text-align: center;
}

#compet {
    border: 1px black solid;
    width: 50%;
    margin: 10px;
}

#solo-board {
    display: flex;
}
#success-graph {
    border: 1px black solid;
    border-radius: 100%;
    width: 100px;
    height: 100px;
    text-align: center;
}

#category {
    margin: 3px;
}
</style>