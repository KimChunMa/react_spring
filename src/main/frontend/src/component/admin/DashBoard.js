import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import Button from '@mui/material/Button';
import ProductTable from './ProductTable';
import ProductWrite from './ProductWrite';
import Container from '@mui/material/Container';

import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext';
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';


export default function DashBoard( props ) {
       //현재 탭 번호
      const [value, setValue] = React.useState('1');
      //탭 변경 함수
      const handleTabsChange  = (event, newValue) => {
        setValue(newValue);
      };


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

            <Box sx={{ width: '100%', typography: 'body1' }}>
              <TabContext value={value}>
                <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                  <TabList onChange={handleTabsChange} aria-label="lab API tabs example">
                    <Tab label="게시판 카테고리 등록" value="1" />
                    <Tab label="제품 등록" value="2" />
                    <Tab label="제품 통계" value="3" />
                  </TabList>
                </Box>
                <TabPanel value="1">
                    <h3> 관리자 페이지 </h3>
                    <h6> 게시판 카테고리 추가 </h6>
                    <input type="text" class="cname"/> <br/>
                    <Button onClick={setCategory} type="button" variant="outlined" > 카테고리 등록 </Button>
                </TabPanel>
                <TabPanel value="2"><ProductWrite handleChange={handleTabsChange } /> </TabPanel>
                <TabPanel value="3">Item Three</TabPanel>
              </TabContext>
            </Box>



            <ProductTable/>


        </Container>
    </>)
}