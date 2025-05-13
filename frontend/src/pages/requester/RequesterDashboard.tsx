import React,{useEffect,useState} from 'react';
import {DataGrid,GridColDef} from '@mui/x-data-grid';
import api from '@/api';

type Row={id:number; bloodType:string; units:number; urgency:string; status:string};

export default function RequesterDashboard(){
  const [rows,setRows]=useState<Row[]>([]);
  const pull=()=>api.get('/requester/requests').then(r=>setRows(r.data));
  useEffect(()=>{ pull(); const id=setInterval(pull,30_000); return()=>clearInterval(id);},[]);
  const del=(id:number)=>api.delete(`/requester/requests/${id}`).then(pull);

  const cols:GridColDef[]=[
    {field:'bloodType',headerName:'Type',minWidth:80},
    {field:'units',headerName:'Units',minWidth:80},
    {field:'urgency',headerName:'Urgency',minWidth:110},
    {field:'status',headerName:'Status',minWidth:90},
    {field:'x',headerName:'',minWidth:90,renderCell:({row})=>
      <button onClick={()=>del(row.id)} className="btn-outline btn-sm">Delete</button>}
  ];

  return(
    <section className="p-6">
      <div className="card">
        <h2 className="card-header">My Requests</h2>
        <DataGrid rows={rows} columns={cols} autoHeight
                  pageSizeOptions={[5,10,25]}
                  paginationModel={{page:0,pageSize:10}}/>
      </div>
    </section>);
}
