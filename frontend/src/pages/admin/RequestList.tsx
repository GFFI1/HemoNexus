import React, { useEffect, useState } from 'react';
import api from '@/api';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { Button } from '@/components/ui/button';

type Row = { id:number; requester:string; bloodType:string; units:number;
             urgency:string; hospital:string; location:string };

export default function RequestList() {
  const [rows, setRows] = useState<Row[]>([]);
  const pull = () => api.get('/admin/requests').then(r => setRows(r.data));
  useEffect(()=>{ pull(); },[]);

  const update = (id:number,status:string)=>
    api.put(`/admin/requests/${id}`,{status})
       .then(()=> setRows(rows.filter(r=>r.id!==id)));

  const cols:GridColDef[]=[
    {field:'requester',headerName:'By',width:110},
    {field:'bloodType',headerName:'Type',width:80},
    {field:'units',headerName:'Units',width:80},
    {field:'urgency',headerName:'Urgency',width:100},
    {field:'hospital',headerName:'Hospital',width:150},
    {field:'location',headerName:'Location',width:150},
    {
      field:'action',headerName:'',width:170,
      renderCell:({row})=>(
        <>
          <Button size="sm" onClick={()=>update(row.id,'ACCEPTED')}>Accept</Button>
          <Button size="sm" variant="destructive"
                  onClick={()=>update(row.id,'DECLINED')}>Decline</Button>
        </>
      )
    }
  ];

  return (
    <section className="p-6">
      <h1 className="text-2xl font-semibold mb-4">Pending Requests</h1>
      <DataGrid
        rows={rows}
        columns={cols}
        autoHeight
        pageSizeOptions={[5,10,25]}
        paginationModel={{ pageSize:10, page:0 }}
      />
    </section>
  );
}
