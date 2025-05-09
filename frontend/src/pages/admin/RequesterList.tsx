import React,{useEffect,useState} from 'react';
import api from '@/api';
import {DataGrid,GridColDef} from '@mui/x-data-grid';

type Row={id:number; username:string; requests:number; city:string};

export default function RequesterList(){
  const [rows,setRows]=useState<Row[]>([]);
  useEffect(()=>{ api.get('/admin/requesters').then(r=>setRows(r.data)); },[]);

  const cols:GridColDef[]=[
    {field:'username',headerName:'Name',width:150,editable:true},
    {field:'city',headerName:'City',width:130,editable:true},
    {field:'requests',headerName:'Total Requests',width:130}
  ];
  const process=(p:any)=> api.put(`/admin/requesters/${p.id}`,{[p.field]:p.value});

  return(<section className="p-6">
    <h1 className="text-2xl font-semibold mb-4">Requesters</h1>
    <DataGrid rows={rows} columns={cols} autoHeight
              processRowUpdate={process}
              pageSizeOptions={[5,10,25]} paginationModel={{pageSize:10,page:0}}/>
  </section>);
}
