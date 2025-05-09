import React, { useEffect, useState } from 'react';
import api from '@/api';
import { DataGrid, GridColDef } from '@mui/x-data-grid';

type Row={id:number; username:string; bloodType:string; lastDonation:string};

export default function DonorList(){
  const [rows,setRows]=useState<Row[]>([]);
  useEffect(()=>{ api.get('/admin/donors').then(r=>setRows(r.data)); },[]);

  const cols:GridColDef[]=[
    {field:'username',headerName:'Name',width:140,editable:true},
    {field:'bloodType',headerName:'Type',width:90,editable:true},
    {field:'lastDonation',headerName:'Last Donation',width:140,editable:true}
  ];

  const process=(params:any)=> api.put(`/admin/donors/${params.id}`,{[params.field]:params.value});

  return (<section className="p-6">
    <h1 className="text-2xl font-semibold mb-4">Donors</h1>
    <DataGrid rows={rows} columns={cols} autoHeight
              processRowUpdate={process}
              pageSizeOptions={[5,10,25]} paginationModel={{pageSize:10,page:0}}/>
  </section>);
}
