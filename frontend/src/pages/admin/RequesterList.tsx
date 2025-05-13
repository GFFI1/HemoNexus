import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { useEffect, useState } from 'react';
import api from '@/api';

export default function RequesterList() {
  const [rows, setRows] = useState([]);

  useEffect(() => {
    api.get('/admin/requesters').then(r => setRows(r.data));
  }, []);

  const cols: GridColDef[] = [
    { field: 'id', headerName: '#', width: 70 },
    { field: 'name', headerName: 'Name', flex: 1 },   // <─ 'name' field
    { field: 'city', headerName: 'City', flex: 1 },
    { field: 'totalRequests', headerName: 'Requests', width: 120 },
  ];

  return <DataGrid autoHeight rows={rows} columns={cols}/>;
}
