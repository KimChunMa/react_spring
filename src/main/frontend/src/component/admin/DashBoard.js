import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import Button from '@mui/material/Button';
import ProductTable from './ProductTable'
import Container from '@mui/material/Container';

export default function DashBoard( props ) {
    const setCategory = (e) => {
        let cname = document.querySelector(".cname");
        console.log(cname);
        axios.post('http://localhost:8080/board/category/write', { "cname" : cname.value })
            .then( (r)=> {
                if(r.data == true){ alert('카테고리 등록성공'); cname.value = '' }
            })
    }
    return (
    <>
        <Container>
            <h3> 관리자 페이지 </h3>
            <h6> 게시판 카테고리 추가 </h6>
            <input type="text" class="cname"/> <br/>
            <Button onClick={setCategory} type="button" variant="outlined" > 카테고리 등록 </Button>

            <ProductTable/>
        </Container>
    </>)
}