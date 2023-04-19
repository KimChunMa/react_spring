import React from 'react';

export default function Clock(){
    //함수안에서 js문법 작성 가능
    // 함수안에 return () 안에서 js문법은 {} 처리 가능
    let clock = new Date().toLocaleTimeString();

    return (<>
        <div>
            <h3> 리액트 시계 </h3>
            <h4> 현재 시간 : {new Date().toLocaleTimeString()} </h4>
        </div>

    </>);
}