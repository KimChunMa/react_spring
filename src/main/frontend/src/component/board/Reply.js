import styles from "./board.css"

import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import {useParams} from 'react-router-dom';

import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';

import ReplyInput from './ReplyInput';

export default function Reply(props){
//
       //1. 세션스토리지 확인해서 로그인 정보 보자
    const[ login, setLogin ] = useState(
        JSON.parse( sessionStorage.getItem("login_token") ) );

    console.log(props);
    //props으로 부터 전달받은 댓글리스트
    let [replyList, setReplyList] = useState([]);
    //props의 대댓글이 변경되었을때 [axios가 실행될때]
    useEffect( () => { setReplyList( props.replyDtoList ) } , [props.replyDtoList] )

    //댓글 삭제
    const onDel = (e,rno) => { props.onDel(rno); }

    // 3. 답글 핸들러
    const onRereplyHandler= ( e , rno ) => {
          console.log( '답글' + rno );

          replyList.forEach( (r , i) => { // 전체 댓글
                // 전체 댓글 번호중에 본인 선택한 댓글번호와 같으면
                if( r.rno == rno ){
                    if( r.cusHTML == '' || r.cusHTML == undefined ){
                        // 새로운 필드에 새로운 html[JSX] 구성
                        replyList[i].cusHTML = <div>
                              { /* 답글 댓글[ rindex= 본인 선택한 댓글 = 답글을 작성할 [부모]댓글번호 ] 작성하는 input */ }
                             <ReplyInput onReply = { props.onReply }  rindex = { rno } />
                                { /* 답글 출력 */ }
                                {
                                    r.rereplyDtolist.map( (rr,j)=> {

                                        if( r.readOnly == undefined ){ r.readOnly = true ; }
                                            return(
                                                <div className="replyBox">
                                                        <span className="replyMname"> { rr.mname } </span>
                                                        <span className="replyRdate"> { rr.rdate } </span>
                                                        <input  value={ r.rcontent }  className="replyRcontent"
                                                        onChange={ (e)=> onRcontentChange( e , rr.rno , i),j }  readOnly = { r.readOnly }   />
                                                        <div class="replyBtn">
                                                            {  login != null && login.mno == r.mno
                                                                ?<>
                                                               <button onClick={ (e)=>onUpdateHandler( e , r.rno , i ) } >
                                                               { r.readOnly == true ? '수정' : '수정완료'}
                                                               </button>
                                                               <button onClick={ (e)=>onDel( e , r.rno ) } >삭제</button>
                                                               </>
                                                               :
                                                               <></>
                                                               }
                                                        </div>
                                                    </div>)

                                    })
                                }
                        </div>
                    }else{ // 해당 답글 구역 숨기기
                         replyList[i].cusHTML = ''
                    }
                }
          })
          setReplyList( [...replyList] );
    }


      // 4. 수정 핸들러
      const onUpdateHandler = ( e , rno , i,j ) => {
        console.log(i " + " j)
          // Rcontent 읽기모드 해제
          if( replyList[i].readOnly == true){
              replyList[i].readOnly = false;  alert('수정후 완료 버튼을 눌러주세요');
          }else{  // Rcontent 읽기모드 적용
              replyList[i].readOnly = true;
              // 수정처리
              props.onReplyUpdate( rno ,  replyList[i].rcontent )
          }
          setReplyList([...replyList])
      }

    // 5. 댓글 내용 수정
    const onRcontentChange = ( e , rno , i ) => {
        replyList[i].rcontent = e.target.value;
        setReplyList([...replyList])
    }

    console.log(props)
    return (<>
        <ReplyInput  onReply = { props.onReply } rindex={0} />
        <div className="replyCount"> 전체 댓글 : {replyList.length} 개 </div>

         {
            replyList.map( (r , i )=>{
            { /* rcontent 읽기모드 설정값 저장하는 [r.readOnly]필드 만들기 */}
            if( r.readOnly == undefined ){ r.readOnly = true ; }
            return(
                <div className="replyBox">
                    <span className="replyMname"> { r.mname } </span>
                    <span className="replyRdate"> { r.rdate } </span>
                    <input  value={ r.rcontent }  className="replyRcontent"
                    onChange={ (e)=> onRcontentChange( e , r.rno , i) }  readOnly = { r.readOnly }   />
                    <div class="replyBtn">
                        <button onClick={ (e)=>onRereplyHandler( e , r.rno ) } >답글</button>
                        {  login != null && login.mno == r.mno
                            ?<>
                           <button onClick={ (e)=>onUpdateHandler( e , r.rno , i ) } >
                           { r.readOnly == true ? '수정' : '수정완료'}
                           </button>
                           <button onClick={ (e)=>onDel( e , r.rno ) } >삭제</button>
                           </>
                           :
                           <></>
                           }
                    </div>
                </div>)
            })
        }
    </>)

    /*

   /* //댓글 입력 함수
    const onReply1 = ()=>{
        props.onReply( document.querySelector('#reply').value,0 )
        document.querySelector('#reply').value = '';
    }

     //대댓글 입력
        const onReply2 = (e,rindex) =>{
            console.log(rindex);
             props.onReply( document.querySelector('.rtext_'+rindex).value, rindex)
             document.querySelector('.rtext_'+rindex).value = '';
        }

        //대댓글 입력창
        const onReplyText = (e,rindex) => {
            document.querySelector('.rno_'+rindex).style.display = 'block'
        }

         <div className={"rno_" +r.rno} style={{ display : "none"}}>
                                <input type="text" className={"rtext_"+r.rno}/>
                                <button onClick={ (e) => onReply2(e,r.rno) }> 대댓글 </button>
         </div>
      */
}