import React,{ useState , useEffect } from 'react';
import axios from 'axios';

import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';

export default function Reply(props){

    //댓글 출력
    const [reply , setReply] = useState({})

     //1. 세션스토리지 확인해서 로그인 정보
        const[ login, setLogin ] = useState(
                                   JSON.parse( sessionStorage.getItem("login_token") ) );

    console.log("props.bno : " + props.bno)
    //댓글 출력 effects
    useEffect( () => {
        axios.get('/board/replys', { params: {bno:props.bno} } )
        .then( r => {
            console.log(r)
            console.log(r.data)
        })
    },[])

    //댓글 입력 함수
    const onReply = ()=>{
        let reply = {
            rcontent : document.querySelector('#reply').value,
            bno : props.bno,
            mno : props.mno
        }

        axios.post('/board/reply',reply)
            .then( r => {
                if(r.data==true){
                   alert('등록됨');
                   setReply([...reply])
                   }else{
                   alert('오류')}
            })
    }

    //댓글 삭제
    /*
        reply.mno == login.mno
            ? <button> 삭제 </button>
            : <div></div>
    */
    const onDel = (rno) => {
        axios.delete('/board/reply', {params: {"rno": rno}})
            .then(r => {
                if(r.data==true){
                alert('삭제됨')
                }else{
                alert('오류')}
            })
    }

    return (<>
        <h3> 댓글란 </h3>
        <Container>
            <TextField  id="reply" label="내용"
                         rows={20}  defaultValue="" variant="standard"/>
            <Button variant="outlined" onClick={onReply}> 댓글 등록 </Button>
        </Container>
    </>)
}