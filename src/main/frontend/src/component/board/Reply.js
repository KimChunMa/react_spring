import styles from "./Reply.css"

import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import {useParams} from 'react-router-dom';

import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
export default function Reply(props){

    //댓글 입력 함수
    const onReply = ()=>{
        props.onReply( document.querySelector('#reply').value,0 )
        document.querySelector('#reply').value = '';
    }

    //댓글 삭제
    const onDel = (e,rno) => {
        props.onDel(rno);
    }

    //댓글 수정
    const onUpdate = (e,rno) => {
       props.onUpdate(rno);
    }

    //대댓글 입력
    const onReply2 = (e,rindex) =>{
        console.log(rindex);
         props.onReply( document.querySelector('.rtext_'+rindex).value, rindex)
         document.querySelector('.rtext_'+rindex).value = '';
    }

    //대댓글
    const onReplyText = (e,rindex) => {

        document.querySelector('.rno_'+rindex).style.display = 'block'

    }



    return (<>
        <h3>댓글 창</h3>
        {
            props.replyDtoList.map( (r)=>{
                console.log(r)
                return(
                <div className="one_reply">
                    <span> {r.rcontent} </span>
                    <span> {r.rdata} </span>
                    <button onClick={ (e)=>onDel(e,r.rno) }> 삭제 </button>
                    <button onClick={ (e)=>onUpdate(e,r.rno) }> 수정 </button>
                    <button onClick={ (e)=>onReplyText(e,r.rno) }> 대댓글 </button>
                    <div className={"rno_" +r.rno} style={{ display : "none"}}>
                        <input type="text" className={"rtext_"+r.rno}/>
                        <button onClick={ (e) => onReply2(e,r.rno) }> 대댓글 </button>
                    </div>
                </div>)
            })
        }

        <h3> 댓글란 </h3>
        <Container>
            <TextField  id="reply" label="내용"
                         rows={20}  defaultValue="" variant="standard"/>
            <Button variant="outlined" onClick={onReply}> 댓글 등록 </Button>
        </Container>
    </>)
}