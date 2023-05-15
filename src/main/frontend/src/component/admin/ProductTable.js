import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import Button from '@mui/material/Button';
import { DataGrid } from '@mui/x-data-grid'; //npm install

export default function ProductTable(props){
    //1. product list 목록 가져오기
    const [rows,setRows] = useState([]);
    const getProduct = () => {axios.get("/product")
        .then( r =>{ setRows (r.data) })
    }
    useEffect (() => { getProduct();},[])


    //DataTable 필드 설정
    const columns = [
      { field: 'id', headerName: '제품 번호', width: 100 },
      { field: 'pname', headerName: '제품 명', width: 100 },
      { field: 'pprice', headerName: '가격', width: 100 },
      { field: 'pcategory', headerName: '카테고리', type: 'number', width: 100,},
      {
        field: 'pcomment',
        headerName: '제품 설명',
        description: 'This column has a value getter and is not sortable.',
        sortable: false,
        width: 100
      },
      { field: 'pmanufacturer', headerName: '제조사', width: 100 },
      { field: 'pstate', headerName: '상태', width: 100 },
      { field: 'pstock', headerName: '재고수량', width: 100 },
      { field: 'cdate', headerName: '최초등록일', type: 'number', width: 100,},
      { field: 'udate', headerName: '최근수정일', type: 'number', width: 100,},
    ];

    //4. 데이터테이블에서 선택된 제품 리스트
    const [rowSelectionModel, setRowSelectionModel] = React.useState([]);
    console.log(rowSelectionModel)

    //5. 삭제함수
    const onDeleteHandler = () => {
        let msg = window.confirm("삭제하시겠습니까? 복구가 불가능합니다.")
        if(msg==true){

        rowSelectionModel.forEach(r => {
            axios.delete("/product",{params:{id:r}})
                .then(r=>{getProduct();})
        })


        }
    }

    return (<>
           <button
                type="button" onClick={onDeleteHandler}
                disabled={ rowSelectionModel.length== 0 ? true : false }
           > 선택삭제 </button>

           <div style={{ height: 400, width: '100%' }}>
              <DataGrid
                rows={rows}
                columns={columns}
                initialState={{
                  pagination: {
                    paginationModel: { page: 0, pageSize: 5 },
                  },
                }}
                pageSizeOptions={[5, 10,15,20]}
                checkboxSelection
                onRowSelectionModelChange={(newRowSelectionModel) => {
                    setRowSelectionModel(newRowSelectionModel);
                }}
              />
            </div>


    </>)
}