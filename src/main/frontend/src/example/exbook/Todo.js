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

    return (<>
          <ListItem>
            <Checkbox checked={item.done} />
                <ListItemText>
                    <InputBase
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