import React,{useState} from 'react';
import {Button, Grid, TextField} from '@mui/material';
import axios from 'axios';
export default function AddTodo(props){

    //사용자가 입력한 데이터를 저장할 상태변수
    //-- 1) 현재 item은 깡통아닌가? 아니면 추가될때마다 다시 title:""인 item 추가?
    const[item, setItem] = useState({title:""})

    //1. 사용자가 입력한 데이터를 가져오기
    // onChange = {onInputChange}
    // onChange 이벤트속성 : 해당 요소가 해당 이벤트를 발생했을때 이벤트 정보를 반환
    // onChange이벤트정보 e 를 onInputChange 함수로 매개변수 e 전달
    const onInputChange = function(e){
           // console.log(e); //해당 이벤트가 발생했을때 이벤트 정보
           // console.log(e.target); // 해당이벤트 발생된 요소
        setItem({title:e.target.value}); // 상태변경: 입력받은 값 가져와서 상태변수를 수정
     } //----? 1) Item이 생성되지않았는데 어떻게 set 이 되는가?

        //2. AppTodo에서 전달받은 addItem1 함수
        const addItem2 = props.addItem2

        //3. + 버튼 클릭시
        const onButtonClick= () =>{
            addItem2(item);
            setItem({title:""});
        }

        //4. 엔터를 입력했을 때
        const enterKeyEventHandler = (e) => {
            if(e.key === 'Enter'){
            onButtonClick();
            }
        }



    return(<>
        <Grid container style={{marginTop:20}}>
            <Grid xs={11} md={11}  item style={{paddingRight:16}}>
                <TextField placeholder="여기에 Todo작성" fullWidth
                onChange={ onInputChange }   onKeyPress = {enterKeyEventHandler}/>
            </Grid>

            <Grid xs={1} md={1}>
                <Button
                fullWidth
                style={{height : '100%'}}
                color="secondary"
                variant="outlined"
                onClick={onButtonClick}
                >
                +
                </Button>
            </Grid>
        </Grid>
    </>)
}