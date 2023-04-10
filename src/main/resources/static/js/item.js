console.log('a');


onwrite()
function onwrite(){
    $.ajax({
            url:"/item/get",
            method:"get",
            success: (r)=>{
                let html ='';
                console.log(r);
                 html += `
                                        <tr>
                                             <th>번호</th>
                                             <th>이름</th>
                                             <th>내용</th>
                                             <th>비고</th>
                                        </tr>`;

                r.forEach((o)=>{
                    html += `
                                <tr>
                                    <td>${o.pno}</td>
                                    <td>${o.pname}</td>
                                    <td>${o.pcontent}</td>
                                     <td><button onclick="del(${o.pno})"> 삭제 </button>
                                         <button onclick="edit(${o.pno})"> 수정 </button>  </td>
                                </tr>
                              `
                })//foreach e

                html += `</table>`;

                document.querySelector(`.a`).innerHTML = html ;
            }

    })
}

function p_post(){
    let pname = document.querySelector('.pname').value;
    let pcontent = document.querySelector('.pcontent').value;

    $.ajax({
           url:"/item/write",
           method:"post",
           data: JSON.stringify( {"pname" : pname ,"pcontent": pcontent } ),
           contentType: "application/json",
           success:(r)=>{
           if(r==true){alert('등록 성공!');onwrite();}
           else{alert('실패!');}
           }
    })//ajax e
}

//삭제
function del(pno){
     $.ajax({
               url:"/item/delete",
               method:"delete",
               data: {"pno" : pno },
               success:(r)=>{
                if(r==true){alert('삭제 성공!');onwrite();}
                 else{alert('실패!');}
               }
        })//ajax e
}

//수정
function edit(pno){
    let pcontent = prompt("수정할 내용 : " );

    $.ajax({
             url: "/item/update",
             method: "put",
             data : JSON.stringify(  { "pno" : pno , "pcontent" : pcontent } ) ,
             contentType : "application/json" ,
             success : (r) => {
                console.log(r)
                if(r==true) {alert('수정!'); onwrite();}
                else{alert('수정 실패!')}

             }
         })
}
