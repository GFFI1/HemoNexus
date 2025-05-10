import React,{useEffect,useState} from 'react';
import {DataGrid,GridColDef} from '@mui/x-data-grid';
import api from '@/api';

type Row={id:number; requester:string; bloodType:string; unitsNeeded:number;
          urgencyLevel:string; hospitalName:string; location:string};

export default function RequestList(){
  const [rows,setRows]=useState<Row[]>([]);
  const pull = ()=> api.get('/admin/requests').then(r=>setRows(r.data));
  useEffect(()=>{ pull(); },[]);

  const patch=(id:number,s:string)=> api
    .put(`/admin/requests/${id}`,{status:s}).then(pull);

  const cols:GridColDef[]=[
    {field:'requester',headerName:'By',minWidth:110},
    {field:'bloodType',headerName:'Type',minWidth:70},
    {field:'unitsNeeded',headerName:'Units',minWidth:80},
    {field:'urgencyLevel',headerName:'Urgency',minWidth:100},
    {field:'hospitalName',headerName:'Hospital',minWidth:150},
    {field:'location',headerName:'Location',minWidth:140},
    {field:'x',headerName:'',minWidth:160,renderCell:({row})=>(
      <>
        <button className="btn-sm mr-2" onClick={()=>patch(row.id,'ACCEPTED')}>Accept</button>
        <button className="btn-sm btn-outline" onClick={()=>patch(row.id,'CANCELLED')}>Cancel</button>
      </>
    )}
  ];

  return(
    <section className="p-6">
      <div className="card">
        <h2 className="card-header">Pending Requests</h2>
        <DataGrid rows={rows} columns={cols} autoHeight
                  pageSizeOptions={[5,10,25]} paginationModel={{page:0,pageSize:10}}/>
      </div>
    </section>);
}
