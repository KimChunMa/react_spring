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
                cno : 1
            }
            axios.post('/board', info)
                .then(r=>{console.log(r); })
    }

    return (
    <>
        <Container>
              <CategoryList/>
              <TextField  className="btitle" id="btitle" label="제목" variant="standard" />
              <TextField  className="bcontent" label="내용" id="bcontent"
                        multiline  rows={10}  defaultValue="" variant="standard"/>
              <Button variant="outlined" onClick={setBoard}> 등록 </Button>
              <Button variant="outlined"> 취소 </Button>

        </Container>
    </>)
}