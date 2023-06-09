import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';


import CategoryList from './CategoryList'

export default function Write( props ) {

    const setBoard = () => {
            let info = {
                btitle : document.querySelector("#btitle").value ,
                bcontent : document.querySelector("#bcontent").value ,
                cno : cno
            }
            axios.post('/board', info)
                .then(r=>{
                    console.log(r);
                    if(r.data == 1) {alert('카테고리 선택후 쓰기 가능')}
                    else if(r.data == 2) {alert('로그인 후 쓰기 가능')}
                    else if(r.data == 3) {alert('게시물작성실패 [관리자에게 문의]')}
                    else if(r.data == 4) {alert('글쓰기 성공');
                                          window.location.href="/board/list";}
                })
    }

    //카테고리 선택
    let [cno, setCno] = useState(0)
    const categoryChange = (cno) => { setCno(cno); }

    return (
    <>
        <Container>
              <CategoryList categoryChange={categoryChange} />
              <TextField  className="btitle" id="btitle" label="제목" variant="standard" />
              <TextField  className="bcontent" label="내용" id="bcontent"
                        multiline  rows={10}  defaultValue="" variant="standard"/>
              <Button variant="outlined" onClick={setBoard}> 등록 </Button>
              <Button variant="outlined"> 취소 </Button>

        </Container>
    </>)
}