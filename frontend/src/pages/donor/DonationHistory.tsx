import React, { useEffect, useState } from 'react';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import api from '@/api';

type Row = { id:number; date:string; location:string; units:number };

export default function DonationHistory() {
  const [rows, setRows] = useState<Row[]>([]);

  const fetchRows = () => api.get('/donor/history').then(r => setRows(r.data));
  useEffect(() => { fetchRows(); const id = setInterval(fetchRows, 30_000); return () => clearInterval(id); }, []);

  const cols: GridColDef[] = [
    { field:'date', headerName:'Date', width:120 },
    { field:'location', headerName:'Location', width:220 },
    { field:'units', headerName:'Units', width:90 }
  ];

  return (
    <section className="p-6">
      <h1 className="text-2xl font-semibold mb-4">Donation History</h1>
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
