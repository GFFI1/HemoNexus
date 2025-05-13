import React, { useEffect, useState } from 'react';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import api from '@/api';

type Row = { id:number; bloodType:string; units:number; status:string };

export default function RequestStatus() {
  const [rows, setRows] = useState<Row[]>([]);

  const pull = () => api.get('/requester/requests').then(r => setRows(r.data));
  useEffect(() => { pull(); const id = setInterval(pull, 30_000); return () => clearInterval(id); }, []);

  const cols: GridColDef[] = [
    { field:'bloodType', headerName:'Type', width:110 },
    { field:'units', headerName:'Units', width:90 },
    { field:'status', headerName:'Status', width:130 }
  ];

  return (
    <section className="p-6">
      <h1 className="text-2xl font-semibold mb-4">My Requests</h1>
<DataGrid
  rows={rows}
  columns={cols}
  autoHeight
  pageSizeOptions={[5, 10, 25]}
  paginationModel={{ pageSize: 10, page: 0 }}
/>
    </section>
  );
}
