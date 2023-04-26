import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import {useParams} from 'react-router-dom'; //HTTP 경로상의 매개변수 호출 해주는 함수
import Reply from './Reply';

export default function View(props){
    //http://localhost:8080/board/view/23 --> useParams(); --> {bno:26}
    const params = useParams(); // useParams()훅 : 경로[URL]상의 매개변수[객체] 반환

    //게시판 상세 정보 불러오기
    const [ board , setBoard ] = useState({});

    useEffect( () => {
        axios.get('/board/s_board',{params : {bno: params.bno}})
            .then( r => {
                console.log(r.data);
                setBoard(r.data);
            })
    },[])

    //삭제 함수
      const onDelete = () => {
            console.log('삭제')
            axios.delete("/board/b_del" , {params : {bno: params.bno}})
                .then( r=> {
                    console.log(r.data)
                    if(r.data == true){
                    alert('삭제됨'); window.location.href="/board/list";}
                    else{alert('삭제 실패')}
                })
        }

    //1. 세션스토리지 확인해서 로그인 정보 보자
    const[ login, setLogin ] = useState(
                               JSON.parse( sessionStorage.getItem("login_token") ) );
    // 1. 현재 로그인된 회원이 들어오면
    const btnBox =
                    login != null && login.mno == board.mno
                    ? <div><button onClick={ onDelete }> 삭제 </button>
                            <button> <a href={'/board/modify?bno='+board.bno}>수정</a> </button> </div>
                    : <div> </div>

    return( <>
        <div>
            <h3> 제목: {board.btitle} </h3>

            <h3> 내용 : {board.bcontent} </h3>
            {btnBox}
        </div>
        <div>
             <Reply bno={board.bno} mno={board.mno}/>
        </div>
    </>)
}

/*
   // useParams() 훅 : 경로[URL]상의 매개변수[객체] 반환
    // http://localhost:8080/board/view/26
    // http://localhost:8080/board/view/:bno    -----> useParams(); ----> { bno : 26 }
    // http://localhost:8080/board/view/26/안녕하세요
    // http://localhost:8080/board/view/:bno/:comment    -----> useParams(); ----> { bno : 26 , comment : 안녕하세요 }
*/