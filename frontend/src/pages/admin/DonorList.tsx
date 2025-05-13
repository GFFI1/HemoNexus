import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { useEffect, useState } from 'react';
import api from '@/api';

export default function DonorList() {
  const [rows, setRows] = useState([]);

  useEffect(() => {
    api.get('/admin/donors').then(r => setRows(r.data));
  }, []);

  const cols: GridColDef[] = [
    { field: 'id', headerName: '#', width: 70 },
    { field: 'firstName', headerName: 'First', flex: 1 },
    { field: 'lastName',  headerName: 'Last',  flex: 1 },
    { field: 'bloodType', headerName: 'Type', width: 110 },
    { field: 'totalDonations', headerName: 'Donations', width: 120 },
    { field: 'city', headerName: 'City', flex: 1 },
  ];

  return <DataGrid autoHeight rows={rows} columns={cols}/>;
}
