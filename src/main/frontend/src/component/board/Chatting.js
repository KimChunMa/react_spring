import React,{ useState , useEffect , useRef } from 'react';
import axios from 'axios';
import Container from '@mui/material/Container';
import './chatting.css'

export default function Chatting(props){
    //현재 채팅중인 변수
    let [msgContent, setMsgContent ] = useState([]);
    let msgInput = useRef(null); //채티입력창 DOM 객체 제어 변수

    let [id, setId] = useState('익명');
    //1.재랜더링 될때마다 새로운 접속
    //let 클라이언트소켓 = new WebSocket("ws/localhost:8080/chat");
    //2. 재렌더링 될때 데이터 상태 유지 (접속은 한번)
    let ws = useRef( null );

    let fileForm = useRef(null); // 채팅 입력창 input DOM객체 제어 변수
    let fileInput = useRef(null); // 채팅 입력창 input DOM객체 제어 변수


    useEffect( () => {
        if(!ws.current){//만약 클라이언트소켓이 접속이 안되어 있을때
             ws.current = new WebSocket("ws://localhost:8080/chat");
             ws.current.onopen = () => {console.log('서버 접속했습니다.');
                 let randId =Math.floor(Math.random() * (9999 -1) +1 );
                 setId('익명 ' + randId);
             }
             //4. 나갈때
             ws.current.onclose = (e) => {console.log('서버 나갔습니다.')}
             //5. 오류
             ws.current.onerror = (e) => {console.log('소켓 오류')}
             //6. 받을때
             ws.current.onmessage = (e) => {
                 console.log('서버소켓으로 메세지 받음'); console.log(e)
                 /*msgContent.push(e.data); //배열에 내용 추가
                 setMsgContent([...msgContent]); // 재랜더링*/
                 let data = JSON.parse(e.data)
                 setMsgContent( (msgContent) => [...msgContent, data]) // 현재배열에서 e.data 추가  ?

             }
        }
    })


  // 4.메시지 전송
    const onSend = () =>{ // msgInput변수가 참조중인 <input ref={ msgInput } > 해당 input 를 DOM객체로 호출
        // 1. 메시지 전송
        let msgBox ={ id : id,  msg : msgInput.current.value,
                      time : new Date().toLocaleTimeString(), type : 'msg'  }

        // 내용이 있으면 메시지 전송
        if( msgBox.msg != ''){
                ws.current.send( JSON.stringify( msgBox ) ); // 클라이언트가 서버에게 메시지 전송 [ .send( ) ]
                msgInput.current.value = '';
        }

        // 2. 첨부파일 전송 [ axios 이용한 서버에게 첨부파일 업로드 ]
        if( fileInput.current.value != '' ){ // 첨부파일 존재하면
             let formData = new FormData( fileForm.current )
             fileAxios(  formData ) // 파일 전송
        }
    }

    //5. 렌더링 할때마다 스크롤 가장 하단으로 내리기
    useEffect ( () => {
    document.querySelector('.chatContentBox').scrollTop=
        document.querySelector('.chatContentBox').scrollHeight;
    },[msgContent])


    //6. 파일 전송 axios
        const fileAxios = (formData)=>{
              axios.post( "/chat/fileupload" , formData  )
                .then( r => {
                    console.log( r.data)
                    // 다른 소켓들에게 업로드 결과 전달
                    let msgBox ={ id : id, msg : msgInput.current.value,
                        time : new Date().toLocaleTimeString(), type : 'file'  ,
                        fileInfo : r.data // 업로드 후 응답받은 파일정보
                    }
                    ws.current.send( JSON.stringify( msgBox ) );
                    fileInput.current.value = '';
                } );
        }

    return(<>
            <Container>
                <h6> 익명 채팅방 </h6>
                <div
                    className="chatContentBox"
                    onDragEnter= { (e) => {console.log('엔터');
                        e.preventDefault(); {/* 상위 이벤트 제거 브라우저의 동일 이벤트 제거*/}
                    } }

                    onDragOver={ (e) => {console.log('오버');
                         e.preventDefault(); {/* 상위 이벤트 제거 */}
                         e.target.style.backgroundColor = '#e8e8e8'
                    } }

                    onDragLeave={ (e) => {console.log('리브');
                         e.preventDefault(); {/* 상위 이벤트 제거 */}
                          e.target.style.backgroundColor = '#ffffff'
                    } }

                    onDrop={ (e) => {
                         e.preventDefault(); {/* 상위 이벤트 제거 */}
                         console.log('드랍');
                         e.target.style.backgroundColor = '#ffffff'
                         {/* 드랍된 파일들을 호출 = e.dataTransfer.files */}
                         let files = e.dataTransfer.files;
                         for(let i = 0 ; i < files.length ; i++){
                            if(files[i] != null && files[i] != undefined){ {/*파일존재시*/}
                                let formData = new FormData()
                                formData.set('attachFile', files[i])
                                fileAxios(  formData )
                            }
                         }
                    } }
                 >
                {
                    msgContent.map( (m)=>{
                        return(<>
                            {/* 조건스타일링 : style={조건 ? {참} , {거짓} } */}
                            <div className="ChatContent" style={ m.id == id ? { backgroundColor: '#d46e6e' } : { backgroundColor: '#eeeeee'} }  >
                                <span className="id">{m.id} </span>
                                <span className="time">{m.time} </span>

                                 {
                                    m.type == 'msg' ? <span> { m.msg } </span>
                                    : (<>
                                        <span>
                                            <span> { m.fileInfo.originalFilename } </span>
                                            <span> { m.fileInfo.sizeKb } </span>
                                            <span> <a href={"/chat/filedownload?uuidFile=" + m.fileInfo.uuidFile
                                            +"&originalFilename="+m.fileInfo.originalFilename } > 저장 </a> </span>
                                        </span>
                                    </>)
                                 }
                            </div>
                        </>)
                    })
                }
                </div>

                <div className="chatInputBox">
                    <span> {id} </span>
                    <input ref={msgInput} type="text" className="msgInput"/>
                    <button onClick={onSend} className="btn"> 전송 </button>

                    <form ref={fileForm}>
                        <input ref={ fileInput} type="file" name="attachFile"/>
                    </form>

                </div>
            </Container>
        </>)
}