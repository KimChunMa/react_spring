import React,{useState} from 'react';
import {ListItem, ListItemText, InputBase, Checkbox} from '@mui/material';
/*
npm install @mui/material @emotion/react @emotion/styled
npm install @mui/material @mui/styled-engine-sc styled-components
*/

export default function Todo(props){

    // 1.Hook 상태관리 useState
    const [item, setItem] = useState(props.item);

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
            </ListItem>
    </>)
}