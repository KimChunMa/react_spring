//comment List . js
import React from 'react';
import Comment from './Comment';
export default function CommentList(props){

    //ajax 이용한 서버로 부터 응답받은 데이터 json 예시
    let r =[
        {name:"유재석" , comment:"안녕"},
        {name:"강호동" , comment:"ㅎㅇ"},
        {name:"신동엽" , comment:"ㅎㅇ2"}
    ];

    console.log(r)
    // return 안에서 js문 사용시 {} => jsx 문법
    //jsx 주석 = {/* 주석 */}
    // map[return 가능] vs forEach [return 불가능]
    return(<>
        <div>
            {
                r.map((c) => {
                    return ( <Comment name={c.name} comment={c.comment}/> )
                })
            }// jsx 끝
        </div>
    </>);
}