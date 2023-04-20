import React,{useState} from 'react';
import {ListItem, ListItemText, InputBase,
        Checkbox, ListItemSecondaryAction, IconButton} from '@mui/material';
import DeleteOutlined from '@mui/icons-material/DeleteOutlined'
/*
npm install @mui/material @emotion/react @emotion/styled
npm install @mui/material @mui/styled-engine-sc styled-components
npm install @mui/icons-material
*/

export default function Todo(props){

    // 1.Hook 상태관리 useState
    const [item, setItem] = useState(props.item);

    //2. props 전달된 삭제함수 변수로 이동
    const deleteItem = props.삭제함수

    //3. 삭제함수 이벤트처리 핸들러
    const deleteEventHandler= () =>{
        deleteItem(item);
    }

    //4. readOnly = true 초기화가 된 필드/변수 와 해당 필드를 수정할 수 있는 함수 setReadOnly [배열]
    const [readOnly, setReadOnly] = useState(true);

    //5. 읽기모드 해제 => 수정가능
    const turnOffReadOnly = () => {console.log("off")
        setReadOnly(false); //readOnly = true 수정불가능 false 수정 가능
    }

    //6. 엔터키를 눌렀을때 -> 수정금지
    const turnOnReadOnly = (e) =>{ console.log("enter")
        if(e.key == "Enter"){
            setReadOnly(true);
        }
    }

    let editItem = props.수정함수

    //7. 입력받은 값을 변경
    const editEventHandler = (e)=>{ console.log("edit")
        item.title = e.target.value; // InputBase 변경될때마다 상태변수에 입력한 값 저장
        setItem(item);
        editItem();
    }

    //8. 체크박스 업데이트
    const checkboxEventHandler = (e) => {
        item.done = e.target.checked; //
        editItem(); //
    }

    return (<>
          <ListItem>
            <Checkbox checked={item.done} onChange={checkboxEventHandler}/>
                <ListItemText>
                    <InputBase inputProps = {{readOnly : readOnly}}
                    onClick = {turnOffReadOnly}
                    onKeyDown = {turnOnReadOnly}
                    onChange = { editEventHandler }
                    type="text"
                    id={ item.id }
                    name={ item.id }
                    value={ item.title }
                    multiline={true}
                    fullWidth={true}
                    />
                </ListItemText>

                <ListItemSecondaryAction>
                    <IconButton onClick={deleteEventHandler}>
                        <DeleteOutlined/>
                    </IconButton>
                </ListItemSecondaryAction>

            </ListItem>
    </>)
}